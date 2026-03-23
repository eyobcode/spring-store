package com.codewitheyob.store;

import com.codewitheyob.store.services.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringStoreApplication {

    public static void main(String[] args) {

       ApplicationContext context =  SpringApplication.run(SpringStoreApplication.class, args);

        var product = context.getBean(ProductService.class);

//        product.createNewProductNewCategory();
//        product.createNewProduct();
//        product.addAllProductToWishlist();
        product.deleteProduct();
    }

}
