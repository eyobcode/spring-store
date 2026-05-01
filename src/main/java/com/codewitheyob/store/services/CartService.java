package com.codewitheyob.store.services;

import com.codewitheyob.store.dtos.CartDto;
import com.codewitheyob.store.dtos.CartItemDto;
import com.codewitheyob.store.entities.Cart;
import com.codewitheyob.store.exceptions.CartNotFoundException;
import com.codewitheyob.store.exceptions.ProductNotFoundException;
import com.codewitheyob.store.mappers.CartMapper;
import com.codewitheyob.store.repositories.CartRepository;
import com.codewitheyob.store.repositories.ProductRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {
    private CartMapper cartMapper;
    private CartRepository cartRepository;
    ProductRepository productRepository;


    public CartDto createCart(){

        var cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId,Long productId){
        var cart = cartRepository.getCartWithId(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        var product = productRepository.findById(productId).orElse(null);
        if(product == null) throw new ProductNotFoundException();

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartRepository.getCartWithId(cartId).orElse(null);
        if(cart == null) throw new CartNotFoundException();
        return cartMapper.toDto(cart);
    }
}
