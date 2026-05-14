package com.codewitheyob.store.dtos;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class OrderItemDto {
    private OrderProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}