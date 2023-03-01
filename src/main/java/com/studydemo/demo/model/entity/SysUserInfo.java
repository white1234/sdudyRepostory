package com.studydemo.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author teronb
 * @Date 2023/3/1 14:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserInfo {
    private Long id;
    private String username;
    private String password;
}
