package com.ltw.module.test.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.ltw.common.api.CommonResult;
import com.ltw.module.test.model.bo.UserAddBO;
import com.ltw.module.test.model.entity.User;
import com.ltw.module.test.service.AuthService;
import com.ltw.module.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;

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
    public CommonResult<Boolean> addUser(@RequestBody @Validated UserAddBO bo){
//        log.info(DateTime.of(bo.getBirthday()).toString("yyyy-MM-dd HH:mm:ss"));
        log.info(bo.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        User user = userService.addUser(bo);

        if(ObjectUtil.isNotNull(user)){
            return CommonResult.success(true);
        }
        return CommonResult.failed();
    }
}
