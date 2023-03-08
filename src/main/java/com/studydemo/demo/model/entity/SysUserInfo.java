package com.studydemo.demo.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 17:59
 */
@Data
@NoArgsConstructor
public class SysUserInfo {
    private Long id;

    private String username;

    private String password;

    private LocalDate birthday;
}
