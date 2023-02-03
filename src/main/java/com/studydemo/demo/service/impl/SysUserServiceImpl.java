package com.studydemo.demo.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.mapper.SysUserMapper;
import com.studydemo.demo.model.entity.SysRoleInfo;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.service.ISysMenuService;
import com.studydemo.demo.service.ISysRoleService;
import com.studydemo.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:44
 */

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserInfo> implements ISysUserService {

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    ISysMenuService sysMenuService;

    @Override
    public SysUserInfo getByUsername(String name) {
        if(StrUtil.isNotEmpty(name)) {
            return this.getOne(new LambdaQueryWrapper<SysUserInfo>().eq(SysUserInfo::getUsername, name));
        }
        return null;
    }

    @Override
    public String getUserAuthorityInfo(long id) {
        List<SysRoleInfo> userRoles = sysRoleService.listUserRoles(id);
        if(ArrayUtil.isEmpty(userRoles)){
            return null;
        }
        List<String> roles = userRoles.stream().map(role -> role.getId()).collect(Collectors.toList());
        var roleStr = userRoles.stream()
                .map( s-> "ROLE_" + s.getRoleExpress())
                .collect(Collectors.joining(","));
        List<String> menu = sysMenuService.listMenuByRoles(roles);
        if(ArrayUtil.isEmpty(menu)){
            return roleStr;
        }else {
            return roleStr+","+menu.stream().collect(Collectors.joining());
        }
    }
}
