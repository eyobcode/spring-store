package com.codewitheyob.store.controllers;

import com.codewitheyob.store.dtos.CheckoutRequest;
import com.codewitheyob.store.dtos.CheckoutResponse;
import com.codewitheyob.store.dtos.ErrorDto;
import com.codewitheyob.store.exceptions.CartNotFoundException;
import com.codewitheyob.store.exceptions.CartEmptyException;
import com.codewitheyob.store.exceptions.PaymentException;
import com.codewitheyob.store.repositories.OrderRepository;
import com.codewitheyob.store.services.CheckoutService;
import com.codewitheyob.store.services.WebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String,String> header,
            @RequestBody String payload) {

        checkoutService.handleWebhookEvent(new WebhookRequest(header, payload));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }


    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
