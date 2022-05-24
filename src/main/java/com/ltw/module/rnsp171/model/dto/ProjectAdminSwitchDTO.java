package com.ltw.module.rnsp171.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectAdminSwitchDTO {

    private String phone;

    private Long projectId;
}
