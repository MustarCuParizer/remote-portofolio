package com.learning.quiz_api.impl;

import com.learning.quiz_api.DTOs.requests.LoginUserDto;
import com.learning.quiz_api.DTOs.requests.RegisterUserDto;
import com.learning.quiz_api.DTOs.responses.LoginUserResponseDto;
import com.learning.quiz_api.entities.AppUser;
import com.learning.quiz_api.impl.EmailService;
import com.learning.quiz_api.impl.VerificationService;
import com.learning.quiz_api.repositories.UserRepository;
import com.learning.quiz_api.utils.AppUserMapper;
import com.learning.quiz_api.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final EmailService emailService;
    private final VerificationService verificationService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;
    private final Map<String, RegisterUserDto> pendingRegistrations = new ConcurrentHashMap<>();
    private final Map<String, LoginUserDto> pendingLogins = new ConcurrentHashMap<>(); // Temporary storage for pending logins

    public AuthService(EmailService emailService, VerificationService verificationService, JwtUtil jwtUtil, UserRepository userRepository, AppUserMapper appUserMapper) {
        this.emailService = emailService;
        this.verificationService = verificationService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.appUserMapper = appUserMapper;
    }

    public void initiateEmailVerification(String email, String code) {
        verificationService.storeVerificationCode(email, code);
        emailService.sendVerificationEmail(email, code);
        System.out.println("Verification code for " + email + ": " + code); // For testing
    }

    public void storePendingRegistration(String email, RegisterUserDto registerUserDto) {
        pendingRegistrations.put(email, registerUserDto);
    }

    public void registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The specified email is already taken");
        }

        AppUser newUser = appUserMapper.registerUserDtoToAppUser(registerUserDto);
        String verificationCode = EmailService.generateVerificationCode();
        newUser.setVerificationCode(verificationCode);
        newUser.setEmailVerified(false);
        userRepository.save(newUser);
        initiateEmailVerification(registerUserDto.getEmail(), verificationCode);
    }

    public void verifyEmail(String email, String verificationCode){
        AppUser user = userRepository.findByEmail(email).orElseThrow();
        if(!user.getVerificationCode().equals(verificationCode))
            throw new RuntimeException("Incorrect verification code");

        user.setEmailVerified(true);
        userRepository.save(user);
    }

    public LoginUserResponseDto loginUser(LoginUserDto loginUserDto) {
        AppUser appuser = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow();

        if(!appuser.isEmailVerified())
            throw new RuntimeException("Check your email for verification code!");

        if (!appuser.getPassword().equals(loginUserDto.getPassword()))
            throw new RuntimeException("Incorrect email or password.");

        String jwt =  jwtUtil.generateToken(appuser.getId());
        return new LoginUserResponseDto(appuser.getId(), jwt, appuser.getAdmin());
    }
}