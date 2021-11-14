package com.ltw.module.test.model.query;

import com.ltw.module.test.model.entity.Permission;
import com.ltw.module.test.model.entity.Role;
import com.ltw.module.test.model.entity.User;
import lombok.Data;
import java.util.List;

@Data
public class UserVO extends User {
    private List<Role> roles;
    private List<Permission> allUserPermissions;
}
