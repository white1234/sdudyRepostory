package com.studydemo.demo.model;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.studydemo.demo.model.bo.UserTestBo;

/**
 * 基于线程上下文的用户信息管理
 */
public class UserContext {
    private static final ThreadLocal<UserTestBo> context = new TransmittableThreadLocal<>();

    /**
     * 设置用户信息
     *
     * @param user -- 用户信息
     */
    public static void set(UserTestBo user) {
        context.set(user);
    }

    /**
     * 获取用户信息
     *
     * @return -- 用户信息
     */
    public static UserTestBo get() {
        return context.get();
    }

    /**
     * 移除用户信息
     */
    public static void remove() {
        context.remove();
    }
}
