package com.studydemo.demo.service.impl;

import com.studydemo.demo.model.AccountUser;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 15:42
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUserInfo sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名:"+username+"未找到");
        }
        return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));

    }

    /**
     * 获取用户权限信息（角色、菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        // 实际怎么写以数据表结构为准，这里只是写个例子
        // 角色(比如ROLE_admin)，菜单操作权限(比如sys:user:list)
        String authority = sysUserService.getUserAuthorityInfo(userId);     // 比如ROLE_admin,ROLE_normal,sys:user:list,...

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
