package com.codewitheyob.store.controllers;

import com.codewitheyob.store.dtos.CheckoutRequest;
import com.codewitheyob.store.dtos.CheckoutResponse;
import com.codewitheyob.store.dtos.ErrorDto;
import com.codewitheyob.store.entities.Order;
import com.codewitheyob.store.entities.OrderItem;
import com.codewitheyob.store.entities.OrderStatus;
import com.codewitheyob.store.repositories.CartRepository;
import com.codewitheyob.store.repositories.OrderRepository;
import com.codewitheyob.store.services.AuthService;
import com.codewitheyob.store.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) return ResponseEntity.badRequest().body(
                new ErrorDto("Cart not found.")
        );
        if (cart.getItems().isEmpty()) return ResponseEntity.badRequest().body(
                new ErrorDto("Cart is empty.")
        );
        var order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());
        order.setCustomer(authService.getCurrentUser());

        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice() );
            order.addItem(orderItem);
        });

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}
