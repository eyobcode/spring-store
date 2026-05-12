package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
