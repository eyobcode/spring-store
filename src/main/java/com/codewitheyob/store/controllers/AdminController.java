package com.codewitheyob.store.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    @GetMapping("/hello")
    public ResponseEntity<?> index(){
        return ResponseEntity.ok("Hello Admin");
    }

}
