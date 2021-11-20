package com.ltw.module.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ltw.common.utils.JwtTokenUtil;
import com.ltw.module.test.mapper.PermissionMapper;
import com.ltw.module.test.model.dto.LoginUser;
import com.ltw.module.test.model.entity.Permission;
import com.ltw.module.test.model.entity.User;
import com.ltw.module.test.model.query.UserVO;
import com.ltw.module.test.service.AuthService;
import com.ltw.module.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final String demoUserName = "admin";
    private final String demoPassword = "admin";
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userService.getUserByAccount(account);
        if(user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        List<Permission> allUserPermission = permissionMapper.getAllUserPermission(userVO.getId());
        List<String> collect = allUserPermission.stream().map(permission -> permission.getCode()).collect(Collectors.toList());
        String[] objects = (String[]) collect.toArray();
        UserDetails userDetails = LoginUser.withUsername(userVO.getUsername()).password(userVO.getPassword()).authorities(objects).build();
        return userDetails;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

}
