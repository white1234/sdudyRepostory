package com.studydemo.demo.handler;

import cn.hutool.json.JSONUtil;
import com.studydemo.demo.config.BaseErrorEnum;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.utils.JwtUtils;
import com.studydemo.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
 * @Date 2023/1/31 13:01
 */

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException{
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        //生成JWT,并放置在请求头中
        String token = jwtUtils.generateToken(authentication.getName());
        httpServletResponse.setHeader(jwtUtils.getHeader(),token);

        BaseResponse response = RespGenerator.success(BaseErrorEnum.SUCCESS);
        outputStream.write(JSONUtil.toJsonStr(response).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
