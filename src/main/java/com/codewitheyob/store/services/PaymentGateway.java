package com.codewitheyob.store.services;

import com.codewitheyob.store.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
