package com.learning.quiz_api.DTOs.requests;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String email;
    private String password;
    private String name;
    private boolean admin; // TODO: Delete this just for test
}


