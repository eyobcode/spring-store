package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    boolean existsCartById(UUID id);
}
