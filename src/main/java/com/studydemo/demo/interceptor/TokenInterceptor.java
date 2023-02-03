/*
package com.studydemo.demo.interceptor;

import com.studydemo.demo.config.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.utils.RedisUtils;
import com.studydemo.demo.utils.TokenUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:39
 *//*

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        if (token!=null){
            //boolean result= TokenUtils.verify(token);
            if(redisUtils.get("token").equals(token)){
                redisUtils.expire("token",1800);
                return true;
            }else {
                System.out.println("未通过拦截器");
                throw new BaseException(BaseErrorEnum.USER_INVALID);
            }
            */
/*if (result){
                System.out.println("通过拦截器");
                return true;
            }*//*

        }
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json=new JSONObject();
            json.put("msg","token verify fail");
            json.put("code","500");
            response.getWriter().append(json.toString());
            System.out.println("认证失败，未通过拦截器");
        } catch (Exception e) {
            return false;
        }
        */
/**
         * 还可以在此处检验用户存不存在等操作
         *//*

        return false;
    }
}*/
