package com.ltw.module.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ltw.module.test.model.dto.UserDTO;
import com.ltw.module.test.model.entity.User;
import com.ltw.module.test.service.AuthService;
import com.ltw.module.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final String demoUserName = "admin";
    private final String demoPassword = "admin";

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userService.getUserByAccount(account);
        UserDTO userDTO = new UserDTO();
        if(user == null){
            throw new UsernameNotFoundException("用户存在");
        }
        BeanUtil.copyProperties(user, userDTO);
        return userDTO;
    }

}
