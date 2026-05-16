package com.codewitheyob.store.payments;

import com.codewitheyob.store.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
