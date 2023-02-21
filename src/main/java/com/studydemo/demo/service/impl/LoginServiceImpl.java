package com.studydemo.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.studydemo.demo.em.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.model.bo.UserDetailBO;
import com.studydemo.demo.model.bo.UserLoginBO;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.service.ILoginService;
import com.studydemo.demo.service.ISysUserService;
import com.studydemo.demo.utils.JwtUtils;
import com.studydemo.demo.utils.RedisUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 13:28
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ISysUserService userService;

    /**
     * 静态变量：系统日志
     */
    private static final Log logger = LogFactory.getLog(LoginServiceImpl.class);

    /**
     * 什么时候被锁定
     */
    private final static Integer LOCKEDNUMBER = 3;


    public UserLoginBO login(String username, String password) {
        //判断账户是否锁定
        String errorNumber;
        if(redisUtils.get("errorNumber")!=null){
            errorNumber = String.valueOf(redisUtils.get("errorNumber"));
        }else {
            errorNumber = "0";
        }

        if(Integer.parseInt(errorNumber)>=LOCKEDNUMBER){
            logger.info(username + "账户已被锁定");
            throw new BaseException(BaseErrorEnum.USER_NAME_LOCK);
        }

        QueryWrapper<SysUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        SysUserInfo sysUserInfo = userService.getOne(wrapper);
        if(sysUserInfo ==null){
            logger.info(username+"用户登录失败");
            throw new BaseException(BaseErrorEnum.USER_NOT_EXISTS);
        }else if (!sysUserInfo.getPassword().equals(password)){
            //密码错误
            String errorTime = String.valueOf(Integer.parseInt(errorNumber));
            if(redisUtils.get("errorNumber")!=null){
                redisUtils.update("errorNumber",errorTime);
            }else {
                redisUtils.set("errorNumber",errorTime);
            }
            throw new BaseException(BaseErrorEnum.PASSWORD_ERROR);
        }else {
            UserDetailBO userDetailBO = new UserDetailBO();
            BeanUtils.copyProperties(sysUserInfo,userDetailBO);
            UserLoginBO loginBO = new UserLoginBO();
            loginBO.setUserDetailBO(userDetailBO);
            //包装token
            //String token = TokenUtils.sign(user);
            String token = jwtUtils.generateToken(sysUserInfo.getUsername());
            //存入redis
            //redisUtils.set("token:"+username,token,1800);
            loginBO.setToken(token);
            logger.info(username+"用户登录成功");
            return loginBO;
        }
    }

    @Override
    public UserLoginBO loginAop(String username, String password){
        LoginServiceImpl loginService = (LoginServiceImpl) AopContext.currentProxy();
        return loginService.login(username,password);
    }
}
