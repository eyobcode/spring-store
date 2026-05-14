package com.codewitheyob.store.mappers;

import com.codewitheyob.store.dtos.OrderDto;
import com.codewitheyob.store.entities.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
