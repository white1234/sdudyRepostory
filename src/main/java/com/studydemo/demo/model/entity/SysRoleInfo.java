package com.studydemo.demo.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 16:34
 */
@Data
@TableName("sys_role_info")
public class SysRoleInfo {
    private String id;

    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "role_express")
    private String roleExpress;
}
