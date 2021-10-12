package com.ltw.module.test.service.impl;

import com.ltw.module.test.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final String demoUserName = "admin";
    private final String demoPassword = "admin";

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        return null;
    }

}
