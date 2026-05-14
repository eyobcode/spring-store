package com.codewitheyob.store.services;

import com.codewitheyob.store.dtos.CheckoutRequest;
import com.codewitheyob.store.dtos.CheckoutResponse;
import com.codewitheyob.store.entities.Order;
import com.codewitheyob.store.exceptions.CartNotFoundException;
import com.codewitheyob.store.exceptions.CartEmptyException;
import com.codewitheyob.store.repositories.CartRepository;
import com.codewitheyob.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        if (cart.isEmpty()) throw new CartEmptyException();

        var order = Order.formatCart(cart,authService.getCurrentUser());

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
