package com.learning.quiz_api.utils;

import com.learning.quiz_api.DTOs.requests.RegisterUserDto;
import com.learning.quiz_api.entities.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUser registerUserDtoToAppUser(RegisterUserDto registerUserDto) {
        AppUser appUser = new AppUser();
        appUser.setName(registerUserDto.getName());
        appUser.setEmail(registerUserDto.getEmail());
        appUser.setPassword(registerUserDto.getPassword());
        appUser.setAdmin(registerUserDto.isAdmin());

        return appUser;
    }
}
