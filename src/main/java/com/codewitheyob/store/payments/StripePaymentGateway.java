package com.codewitheyob.store.payments;

import com.codewitheyob.store.entities.Order;
import com.codewitheyob.store.entities.OrderItem;
import com.codewitheyob.store.entities.PaymentStatus;
import com.codewitheyob.store.repositories.OrderRepository;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StripePaymentGateway implements PaymentGateway {

    private final OrderRepository orderRepository;

    @Value("${websiteUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;


    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder =  SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .putMetadata("order_id", order.getId().toString());

            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());

        } catch (StripeException e) {
            throw new PaymentException();
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            String payload = request.getPayload();
            String signature =  request.getHeader().get("Stripe-Signature");
            Event event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));

                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));

                default -> Optional.empty();
            };

        } catch (SignatureVerificationException e) {
            throw new RuntimeException("Invalid Stripe signature", e);
        }
    }

    private Long extractOrderId(Event event) {
        if (event == null)  throw new PaymentException("Event is null");

        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        try {
            // Preferred: Safe deserialization
            if (deserializer.getObject().isPresent()) {
                stripeObject = deserializer.getObject().get();
            } else {
                // Fallback
                stripeObject = deserializer.deserializeUnsafe();
            }
        } catch (EventDataObjectDeserializationException e) {
            throw new PaymentException("Failed to deserialize Stripe event. API version mismatch. " +
                    "Raw JSON: " + e.getRawJson());
        }

        if (stripeObject == null) {
            throw new PaymentException("Could not deserialize Stripe event");
        }

        // Handle PaymentIntent
        if (stripeObject instanceof PaymentIntent pi) {
            String orderIdStr = pi.getMetadata() != null ? pi.getMetadata().get("order_id") : null;

            if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                throw new PaymentException("Missing 'order_id' in metadata");
            }

            try {
                return Long.parseLong(orderIdStr);
            } catch (NumberFormatException e) {
                throw new PaymentException("Invalid order_id: " + orderIdStr);
            }
        }

        throw new PaymentException("Unsupported event type or object: " + event.getType());
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setUnitAmountDecimal(
                        item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setCurrency("usd")
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName()).build();
    }
}
