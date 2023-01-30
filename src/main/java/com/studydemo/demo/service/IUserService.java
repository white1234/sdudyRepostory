package com.studydemo.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studydemo.demo.model.bo.UserLoginBO;
import com.studydemo.demo.model.entity.User;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:43
 */
public interface IUserService extends IService<User> {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    UserLoginBO login(String username, String password);
}
