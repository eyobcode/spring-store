package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}