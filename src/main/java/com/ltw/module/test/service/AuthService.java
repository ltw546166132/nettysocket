package com.ltw.module.test.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService{
    String login(String username, String password);

    String refreshToken(String token);
}
