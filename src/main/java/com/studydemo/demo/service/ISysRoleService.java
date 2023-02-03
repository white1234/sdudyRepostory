package com.studydemo.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studydemo.demo.model.entity.SysRoleInfo;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 16:33
 */
public interface ISysRoleService extends IService<SysRoleInfo> {
    List<SysRoleInfo> listUserRoles(long userId);
}
