package com.learning.quiz_api.impl;


import com.learning.quiz_api.DTOs.requests.LoginUserDto;
import com.learning.quiz_api.DTOs.requests.RegisterUserDto;
import com.learning.quiz_api.DTOs.responses.LoginUserResponseDto;
import com.learning.quiz_api.entities.AppUser;
import com.learning.quiz_api.repositories.UserRepository;
import com.learning.quiz_api.services.UserService;
import com.learning.quiz_api.utils.AppUserMapper;
import com.learning.quiz_api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;
    private final JwtUtil jwtUtil;

    @Override
    public AppUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public boolean isAdmin(Long userId) {
        return userRepository.existsByIdAndAdminTrue(userId);
    }
}

