package com.studydemo.demo.handler;

import cn.hutool.json.JSONUtil;
import com.studydemo.demo.exp.CaptchaException;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

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
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String errorMessage = "用户名或密码错误";
        BaseResponse response;
        if(e instanceof CaptchaException){
            errorMessage = "验证码错误";
            response = RespGenerator.fail(errorMessage);
        }else {
            response = RespGenerator.fail(errorMessage);
        }
        outputStream.write(JSONUtil.toJsonStr(response).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
