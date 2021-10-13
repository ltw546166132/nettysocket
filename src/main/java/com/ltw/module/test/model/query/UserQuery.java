package com.ltw.module.test.model.query;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.ltw.common.model.bean.BaseQuery;
import com.ltw.module.test.model.entity.User;
import lombok.Data;

@Data
public class UserQuery extends BaseQuery<User> {

    private Long id;
    private String account;
    private String username;

    @Override
    public Wrapper<User> constructQuery() {
        return null;
    }
}
