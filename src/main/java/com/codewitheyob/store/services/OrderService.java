package com.codewitheyob.store.services;

import com.codewitheyob.store.dtos.OrderDto;
import com.codewitheyob.store.exceptions.OrderNotFoundException;
import com.codewitheyob.store.mappers.OrderMapper;
import com.codewitheyob.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrders(Long orderId) {
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();

        if (!order.isPlacedBy(user))
            throw new AccessDeniedException("You don't have access to this order.");

        return orderMapper.toDto(order);
    }
}
