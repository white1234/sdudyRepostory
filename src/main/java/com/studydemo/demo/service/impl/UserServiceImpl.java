package com.studydemo.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.mapper.UserMapper;
import com.studydemo.demo.model.bo.UserDetailBO;
import com.studydemo.demo.model.bo.UserLoginBO;
import com.studydemo.demo.model.entity.User;
import com.studydemo.demo.service.IUserService;
import com.studydemo.demo.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:44
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    @Override
    public UserLoginBO login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        wrapper.eq("password",password);
        User user = this.baseMapper.selectOne(wrapper);
        if(user==null){
            return null;
        }else {
            UserDetailBO userDetailBO = new UserDetailBO();
            BeanUtils.copyProperties(user,userDetailBO);
            UserLoginBO loginBO = new UserLoginBO();
            loginBO.setUserDetailBO(userDetailBO);
            //包装token
            String token = TokenUtils.sign(user);
            loginBO.setToken(token);
            return loginBO;
        }
    }
}
