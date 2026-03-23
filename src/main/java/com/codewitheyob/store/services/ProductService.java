package com.codewitheyob.store.services;

import com.codewitheyob.store.entities.Category;
import com.codewitheyob.store.entities.Product;
import com.codewitheyob.store.entities.User;
import com.codewitheyob.store.repositories.CategoryRepository;
import com.codewitheyob.store.repositories.ProductRepository;
import com.codewitheyob.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public void createNewProductNewCategory(){
        var category = Category.builder()
                .name("electronics")
                .build();

        var product = Product.builder()
                .name("iPhone 14 pro max")
                .price(BigDecimal.valueOf(1000))
                .build();

        category.addProduct(product);
        categoryRepository.save(category);
    }
    @Transactional
    public void createNewProduct(){
        Category category = categoryRepository.findById((byte) 5).orElseThrow();

        Product product = Product.builder()
                .name("I pad")
                .price(BigDecimal.valueOf(100))
                .build();

      category.addProduct(product);
      productRepository.save(product);
    }

    @Transactional
    public void addAllProductToWishlist() {
        User user = userRepository.findById(1L).orElseThrow();
        List<Product> products = productRepository.findAll();

        user.getWishlist().addAll(products);

        userRepository.save(user);
    }
    public void deleteProduct(){
        productRepository.deleteById(5L);
    }
}
