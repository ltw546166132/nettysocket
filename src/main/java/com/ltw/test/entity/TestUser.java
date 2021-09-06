package com.ltw.test.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestUser {
    private Long id;
    private String name;
    private LocalDateTime localDateTime;
}
