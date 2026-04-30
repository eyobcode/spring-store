package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    boolean existsCartById(UUID id);

    List<Cart> findCartById(UUID id);

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT c FROM Cart c WHERE c.id = :cartId")
    Optional<Cart> getCartWithId(@Param("cartId") UUID cartId);
}
