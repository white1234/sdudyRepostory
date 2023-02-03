package com.studydemo.demo.model;

import com.studydemo.demo.utils.RSAUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/2 16:53
 */
@NoArgsConstructor
public class PasswordEncoder extends BCryptPasswordEncoder {
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 接收到的前端的密码
        String pwd = rawPassword.toString();

        // 进行rsa解密
        try {
            pwd = RSAUtils.decryptByPrivateKey(pwd.replace( " " , "+" ),RsaProperties.privateKey);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
        if (encodedPassword != null && encodedPassword.length() != 0) {
            return BCrypt.checkpw(pwd, encodedPassword);
        } else {
            return false;
        }
    }
}
