package com.ltw.module.test.controller;

import com.ltw.module.test.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class SecurityController {

    @Autowired
    private AuthService authService;

    @GetMapping("/mobil")
    public String query(){
        return "mobile";
    }

    @GetMapping("/salary")
    public String salary(){
        return "salary";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
