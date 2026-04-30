package com.codewitheyob.store.mappers;

import com.codewitheyob.store.dtos.CartDto;
import com.codewitheyob.store.dtos.CartItemDto;
import com.codewitheyob.store.entities.Cart;
import com.codewitheyob.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
