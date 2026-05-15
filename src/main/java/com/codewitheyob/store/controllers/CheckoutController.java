package com.codewitheyob.store.controllers;

import com.codewitheyob.store.dtos.CheckoutRequest;
import com.codewitheyob.store.dtos.ErrorDto;
import com.codewitheyob.store.exceptions.CartNotFoundException;
import com.codewitheyob.store.exceptions.CartEmptyException;
import com.codewitheyob.store.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request){
        try {
            return ResponseEntity.ok(checkoutService.checkout(request));
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto("Error creating a checkout session"));
        }
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
