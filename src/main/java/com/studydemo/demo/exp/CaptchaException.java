package com.studydemo.demo.exp;


import org.springframework.security.core.AuthenticationException;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 13:11
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg){
        super(msg);
    }
}
