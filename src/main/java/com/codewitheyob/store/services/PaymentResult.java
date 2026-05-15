package com.codewitheyob.store.services;

import com.codewitheyob.store.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
