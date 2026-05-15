package com.codewitheyob.store.services;

import com.codewitheyob.store.dtos.CheckoutRequest;
import com.codewitheyob.store.dtos.CheckoutResponse;
import com.codewitheyob.store.entities.Order;
import com.codewitheyob.store.entities.PaymentStatus;
import com.codewitheyob.store.exceptions.CartNotFoundException;
import com.codewitheyob.store.exceptions.CartEmptyException;
import com.codewitheyob.store.exceptions.PaymentException;
import com.codewitheyob.store.repositories.CartRepository;
import com.codewitheyob.store.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        if (cart.isEmpty()) throw new CartEmptyException();

        var order = Order.formatCart(cart,authService.getCurrentUser());

        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(),session.getCheckoutUrl());
        } catch (PaymentException ex){
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    Order order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });
    }
}
