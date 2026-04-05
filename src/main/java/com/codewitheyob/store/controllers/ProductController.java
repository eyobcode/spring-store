package com.codewitheyob.store.controllers;

import com.codewitheyob.store.dtos.ProductDto;
import com.codewitheyob.store.entities.Product;
import com.codewitheyob.store.mappers.ProductMapper;
import com.codewitheyob.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(required = false,name = "categoryId") Byte categoryId
    ) {
        List<Product> product;
        if (categoryId == null){
            product = productRepository.findAll();
        }else {
            product = productRepository.findAllWithCategoryId(categoryId);
        }
        return product.stream().map(productMapper::toDto).toList();
    }
}
