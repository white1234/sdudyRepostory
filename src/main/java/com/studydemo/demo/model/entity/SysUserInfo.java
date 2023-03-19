package com.studydemo.demo.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 17:59
 */
@Data
@NoArgsConstructor
public class SysUserInfo implements Serializable {
    private Long id;

    private String username;

    private String password;

    @TableField(exist = false)
    private LocalDate birthday;
}
