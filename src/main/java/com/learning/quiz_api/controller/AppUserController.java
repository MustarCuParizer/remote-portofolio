package com.learning.quiz_api.controller;

import com.learning.quiz_api.DTOs.requests.LoginUserDto;
import com.learning.quiz_api.DTOs.requests.RegisterUserDto;
import com.learning.quiz_api.DTOs.responses.LoginUserResponseDto;
import com.learning.quiz_api.impl.AuthService;
import com.learning.quiz_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/v1/user")
@RequiredArgsConstructor
public class AppUserController {
    private final UserService userService; // Keep if other user-related operations exist
    private final AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<Void> register(@RequestBody RegisterUserDto registerUserDto) {
        authService.registerUser(registerUserDto);
        return ResponseEntity.noContent().build();
    }

     @GetMapping("/verify-email/{email}/{code}")
     public ResponseEntity<Void> verifyEmailAndRegister(@PathVariable String email, @PathVariable String code) {
         authService.verifyEmail(email, code);
         return ResponseEntity.noContent().build();
     }

    @PostMapping("/login")
    private ResponseEntity<LoginUserResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok(authService.loginUser(loginUserDto));
    }
}