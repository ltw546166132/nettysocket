package com.ltw.module.test.model.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAddBO {
    @NotBlank
    private String account;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
