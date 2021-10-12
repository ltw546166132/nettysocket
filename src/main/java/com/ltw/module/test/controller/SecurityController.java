package com.ltw.module.test.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ltw.common.api.CommonResult;
import com.ltw.module.test.model.bo.UserAddBO;
import com.ltw.module.test.model.entity.User;
import com.ltw.module.test.service.AuthService;
import com.ltw.module.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/auth")
public class SecurityController {

    @Autowired
    private UserService userService;

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

    @PostMapping("/user")
    public CommonResult<Boolean> addUser(UserAddBO bo){
        User user = userService.addUser(bo);
        if(ObjectUtil.isNotNull(user)){
            return CommonResult.success(true);
        }
        return CommonResult.failed();
    }
}
