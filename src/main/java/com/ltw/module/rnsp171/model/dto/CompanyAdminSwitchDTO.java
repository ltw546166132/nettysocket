package com.ltw.module.rnsp171.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyAdminSwitchDTO {
    private String phone;
    private String password;
}
