package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ===== BASIC STRING =====
    List<Product> findByName(String name);
    List<Product> findByNameLike(String name);
    List<Product> findByNameNotLike(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByNameIgnoreCase(String name);

    // ===== NUMERIC =====
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal price, BigDecimal price2);

    // MULTIPLE CONDITIONS
    List<Product> findByNameAndPrice(String name, BigDecimal price);
    List<Product> findByNameOrPrice(String name, BigDecimal price);

    // ORDERING
    List<Product> findByNameOrderByPriceAsc(String name);
    List<Product> findByNameOrderByPriceDesc(String name);

    //COUNT / EXISTS
    long countByName(String name);
    boolean existsByName(String name);

    // IN / NOT IN
    List<Product> findByNameIn(List<String> names);
    List<Product> findByNameNotIn(List<String> names);


    // Custom queries
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max ORDER BY p.name")
    List<Product> findProducts(@Param("min") BigDecimal min,
                               @Param("max") BigDecimal max);

    // Like search
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    List<Product> searchByName(String keyword);

    // Native
    @Query(value = "SELECT * FROM products WHERE price > :price", nativeQuery = true)
    List<Product> findNative(BigDecimal price);

}