package com.ltw.module.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestUser {
    private Long id;
    private String name;
    private String idCard;
    private LocalDateTime localDateTime;
}