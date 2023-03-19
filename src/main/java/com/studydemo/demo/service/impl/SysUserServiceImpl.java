package com.studydemo.demo.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.mapper.SysUserMapper;
import com.studydemo.demo.model.UserContext;
import com.studydemo.demo.model.bo.UserTestBo;
import com.studydemo.demo.model.entity.SysRoleInfo;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.service.ISysMenuService;
import com.studydemo.demo.service.ISysRoleService;
import com.studydemo.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:44
 */

@Service(value = "sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserInfo> implements ISysUserService {

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    ISysMenuService sysMenuService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUserInfo getByUsername(String name) {
        if (StrUtil.isNotEmpty(name)) {
            return this.getOne(new LambdaQueryWrapper<SysUserInfo>().eq(SysUserInfo::getUsername, name));
        }
        return null;
    }

    @Override
    public String getUserAuthorityInfo(long id) {
        List<SysRoleInfo> userRoles = sysRoleService.listUserRoles(id);
        if (ArrayUtil.isEmpty(userRoles)) {
            return null;
        }
        List<String> roles = userRoles.stream().map(role -> role.getId()).collect(Collectors.toList());
        var roleStr = userRoles.stream()
                .map(s -> "ROLE_" + s.getRoleExpress())
                .collect(Collectors.joining(","));
        List<String> menu = sysMenuService.listMenuByRoles(roles);
        if (ArrayUtil.isEmpty(menu)) {
            return roleStr;
        } else {
            return roleStr + "," + menu.stream().collect(Collectors.joining());
        }
    }

    /**
     * @Description 用户注册
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/2/16 13:09
     */
    @Transactional
    @Override
    public void registUser(SysUserInfo userInfo) {
        try {
            userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
            sysRoleService.signInUserRole(userInfo.getId());
            this.save(userInfo);
        } catch (Exception e) {
            throw new BaseException("500", "用户注册失败");
        }
    }

    @Override
    public void testThreadPoolAdd(List<UserTestBo> testBoList) {
        System.out.println(Thread.currentThread().getName() + "添加用户信息共:" + testBoList.size() + ";用户编号自" + testBoList.get(0).getId() +
                "开始," + testBoList.get(testBoList.size() - 1).getId() + "结束");
    }

    @Override
    public void testTransmittableThreadLocal() {
        System.out.println(Thread.currentThread().getName() + "添加用户信息:" + UserContext.get());
    }

    @Override
    public PageInfo<SysUserInfo> listUsers(int start,int end) {
        PageHelper.startPage(start,end);
        List<SysUserInfo> sysUserInfos = sysUserMapper.selectList(null);
        PageInfo<SysUserInfo> result = new PageInfo<>(sysUserInfos);
        return result;
    }

    public List<SysUserInfo> getUserList(){
        SysUserInfo s1 = new SysUserInfo();
        s1.setId(0L);
        s1.setUsername("");
        s1.setPassword("");
        s1.setBirthday(LocalDate.now());


        List<SysUserInfo> sysUserInfolist=Lists.newArrayList();
        return sysUserInfolist;
    }
}
