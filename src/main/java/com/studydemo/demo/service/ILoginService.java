package com.studydemo.demo.service;

import com.studydemo.demo.model.bo.UserLoginBO;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/1/31 13:26
 */
public interface ILoginService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    UserLoginBO login(String username, String password);
}
