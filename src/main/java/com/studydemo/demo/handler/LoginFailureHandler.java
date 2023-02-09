package com.studydemo.demo.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.studydemo.demo.config.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.exp.CaptchaException;
import com.studydemo.demo.model.LoginLimitProperties;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 13:08
 */
@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    RedisUtils redisUtils;

    @Resource
    LoginLimitProperties limitProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String errorMessage = "";
        BaseResponse response;
        if(e instanceof CaptchaException) {
            errorMessage = "验证码错误!";
        }else if (e instanceof UsernameNotFoundException){
            errorMessage = e.getMessage();
        }else {
            errorMessage = "账号密码错误!";
           // errorMessage = updateFailureTimes(httpServletRequest);
        }
        response = RespGenerator.fail(errorMessage);
        outputStream.write(JSONUtil.toJsonStr(response).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * @Description 更新密码验证失败次数
     * @Param request
     * @Return
     * @Author teronb
     * @Date 2023/2/4 20:25
     */
    private String updateFailureTimes(HttpServletRequest request){
        String username = request.getParameter("username");
        String lockKey = "lock:"+username;
        int limitLockNumber = limitProperties.getLockNumber();
        long limitLockDuration = limitProperties.getLockDuration();
        long intervalTime = limitProperties.getIntervalTime();
        //判断账户是否锁定
        String errorNumber;
        long lockDuration;
        if(redisUtils.get(lockKey)!=null){
            errorNumber = String.valueOf(redisUtils.get(lockKey));
        }else {
            errorNumber = "1";
        }
        if(Integer.parseInt(errorNumber)>=limitLockNumber){
            log.info("账户"+username+"已被锁定!");
            //throw new BaseException(BaseErrorEnum.USER_NAME_LOCK);
            lockDuration = redisUtils.getTime(lockKey);
            return "账户"+username+"已被锁定!请于"+lockDuration/60+"分钟"+lockDuration%60+"秒后重新输入进行登录!";
        }else if(limitLockNumber-Integer.parseInt(errorNumber)==1){
            redisUtils.set(lockKey, String.valueOf(limitLockNumber),limitLockDuration);
            return "账户"+username+"已连续"+limitLockNumber+"次验证未通过，账户已被锁定!请于"+limitLockDuration/60+"分钟"+limitLockDuration%60+"秒后重新尝试!";
        }else{
            if(redisUtils.get(lockKey)!=null){
                redisUtils.set(lockKey, String.valueOf(Integer.parseInt(errorNumber)+1),intervalTime);
                return "密码错误,还有"+(limitLockNumber-(Integer.parseInt(errorNumber)+1))+"次验证机会";
            }else {
                redisUtils.set(lockKey,errorNumber,intervalTime);
                return "密码错误,还有"+(limitLockNumber-1)+"次验证机会!";
            }
        }
    }
}
