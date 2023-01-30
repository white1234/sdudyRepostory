package com.studydemo.demo.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 17:59
 */
@Data
@NoArgsConstructor
public class User {
    private String uid;

    private String username;

    private String password;
}
