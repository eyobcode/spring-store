package com.codewitheyob.store.dtos;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CartProductDto {
    private Long id;
    private String name;
    private BigInteger price;
}
