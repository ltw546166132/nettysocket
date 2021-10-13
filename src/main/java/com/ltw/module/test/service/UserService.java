package com.ltw.module.test.service;

import com.ltw.module.test.model.bo.UserAddBO;
import com.ltw.module.test.model.entity.User;

public interface UserService {
    User addUser(UserAddBO bo);

    User getUserByAccount(String account);
}
