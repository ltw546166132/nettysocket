package com.ltw.module.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ltw.module.test.model.entity.Permission;
import com.ltw.module.test.model.entity.User;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getAllUserPermission(Long userId);
}
