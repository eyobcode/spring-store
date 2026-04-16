package com.codewitheyob.store.dtos;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String email;
    private String Password;
}
