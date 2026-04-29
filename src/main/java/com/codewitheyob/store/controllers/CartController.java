package com.codewitheyob.store.controllers;

import com.codewitheyob.store.dtos.AddItemToCartRequest;
import com.codewitheyob.store.dtos.CartDto;
import com.codewitheyob.store.dtos.CartItemDto;
import com.codewitheyob.store.entities.Cart;
import com.codewitheyob.store.entities.CartItem;
import com.codewitheyob.store.mappers.CartMapper;
import com.codewitheyob.store.repositories.CartRepository;
import com.codewitheyob.store.repositories.ProductRepository;
import com.codewitheyob.store.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProfileRepository profileRepository;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cart.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request){

        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) return ResponseEntity.notFound().build();

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if(product == null) return ResponseEntity.badRequest().build();
        var cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }
        cartRepository.save(cart);
        var cartItemDto = cartMapper.toDto(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);

    }
}
