package com.ltw.module.test.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class UserAddBO {
    @NotBlank
    private String account;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
