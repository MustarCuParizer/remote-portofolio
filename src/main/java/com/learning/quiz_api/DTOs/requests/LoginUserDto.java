package com.learning.quiz_api.DTOs.requests;

import lombok.Data;

@Data
public class LoginUserDto {
   private String email;
   private String password;
}
