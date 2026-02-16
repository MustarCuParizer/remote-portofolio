package com.learning.quiz_api.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

@Getter
public class AuthenticatedUser extends AbstractAuthenticationToken {
    private final Long userId;

    public AuthenticatedUser(Long userId){
        super(Collections.emptyList());
        this.userId = userId;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}
