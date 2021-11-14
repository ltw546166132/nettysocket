package com.ltw.module.test.model.dto;

import com.ltw.module.test.model.query.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class LoginUser extends User {

    private UserVO target;

    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserVO target) {
        super(username, password, authorities);
        this.target = target;
    }

    public UserVO getTarget() {
        return target;
    }

    public void setTarget(UserVO target) {
        this.target = target;
    }
}
