package com.ltw.module.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltw.common.exception.ApiException;
import com.ltw.module.test.mapper.UserMapper;
import com.ltw.module.test.model.bo.UserAddBO;
import com.ltw.module.test.model.dto.UserDTO;
import com.ltw.module.test.model.entity.User;
import com.ltw.module.test.service.UserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(UserAddBO bo){
        User one = getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, bo.getAccount()));
        if(ObjectUtil.isNotNull(one)){
            throw new ApiException("账号已存在！");
        }
        User user = new User();
        BeanUtil.copyProperties(bo,user);
        String encode = passwordEncoder.encode(bo.getPassword());
        user.setPassword(encode);
        boolean save = save(user);
        if(save){
            return user;
        }
        return null;
    }

    @Override
    public User getUserByAccount(String account) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getAccount, account));
    }
}
