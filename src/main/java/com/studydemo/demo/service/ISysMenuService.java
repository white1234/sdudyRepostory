package com.studydemo.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studydemo.demo.model.entity.SysMenuInfo;


import java.util.List;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/1/31 16:51
 */
public interface ISysMenuService  extends IService<SysMenuInfo> {
    List<String> listMenuByRoles(List<String> roleIds);
}
