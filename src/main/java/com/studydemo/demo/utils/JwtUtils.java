package com.studydemo.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 12:36
 */
@Component
@Data
@ConfigurationProperties(prefix = "studydemo.jwt")
public class JwtUtils {
    private long expire;
    private String secret;
    private String header;

    /**
     * @Description 创建token
     * @Param username
     * @Return {@link String}
     * @Author teronb
     * @Date 2023/1/31 13:00
     */
    public String generateToken(String username){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime()+1000*expire);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * @Description 解析token
     * @Param jwt
     * @Return {@link Claims}
     * @Author teronb
     * @Date 2023/1/31 12:59
     */
    public Claims getClaimsByToken(String jwt){
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * @Description 判断jwt是否过期
     * @Param claims
     * @Return {@link boolean}
     * @Author teronb
     * @Date 2023/1/31 12:52
     */
    public boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }
}
