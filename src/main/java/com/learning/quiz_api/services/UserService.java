package com.learning.quiz_api.services;

import com.learning.quiz_api.DTOs.requests.LoginUserDto;
import com.learning.quiz_api.DTOs.requests.RegisterUserDto;
import com.learning.quiz_api.DTOs.responses.LoginUserResponseDto;
import com.learning.quiz_api.entities.AppUser;

public interface UserService {
    AppUser findById(Long id);
    boolean isAdmin(Long userId);
}