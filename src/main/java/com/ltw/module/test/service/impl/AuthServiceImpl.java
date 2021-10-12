package com.ltw.module.test.service.impl;

import com.ltw.module.test.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final String demoUserName = "admin";
    private final String demoPassword = "admin";
}
