package com.learning.quiz_api.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginUserResponseDto {
    private Long userId;
    private String jwt;
    private boolean isAdmin;
}
