package com.studydemo.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studydemo.demo.model.bo.UserTestBo;
import com.studydemo.demo.model.entity.SysUserInfo;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:43
 */
public interface ISysUserService extends IService<SysUserInfo> {
    SysUserInfo getByUsername(String name);

    String getUserAuthorityInfo(long id);

    void registUser(SysUserInfo userInfo);

    void testThreadPoolAdd(List<UserTestBo> testBoList);

    void testTransmittableThreadLocal();
}
