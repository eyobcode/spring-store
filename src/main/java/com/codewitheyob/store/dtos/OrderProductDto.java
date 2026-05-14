package com.codewitheyob.store.dtos;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
}