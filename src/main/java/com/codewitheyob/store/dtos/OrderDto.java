package com.codewitheyob.store.dtos;

import com.codewitheyob.store.entities.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private PaymentStatus status;
    private LocalDate createdAt;
    private BigDecimal totalPrice;
    private List<OrderItemDto> items;

}
