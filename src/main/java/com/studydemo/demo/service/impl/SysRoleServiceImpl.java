package com.studydemo.demo.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.mapper.SysRoleMapper;
import com.studydemo.demo.model.entity.SysRoleInfo;
import com.studydemo.demo.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 16:52
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleInfo> implements ISysRoleService {
    @Override
    public List<SysRoleInfo> listUserRoles(long userId) {
        return this.baseMapper.listUserRoles(userId);
    }

    @Override
    public int signInUserRole(long userId) {
        SysRoleInfo roleInfo = this.baseMapper.selectOne(new LambdaQueryWrapper<SysRoleInfo>().eq(SysRoleInfo::getRoleExpress, "initial_role"));
        return this.baseMapper.saveUserRole(UUID.fastUUID().toString(),userId,roleInfo.getId());
    }
}
