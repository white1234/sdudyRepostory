package com.studydemo.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.mapper.SysUserMapper;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author teronb
 * @Date 2023/3/1 14:51
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserInfo> implements ISysUserService {
}
