package com.studydemo.demo.utils;

import com.studydemo.demo.service.impl.SysUserServiceImpl;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:23
 */
@Component
public class TokenUtils {

    @Autowired
    RedisUtils redisUtils;

    /**
     * 静态变量：系统日志
     */
    private static final Log logger = LogFactory.getLog(SysUserServiceImpl.class);
    //token存活时间10小时
    //private static final long EXPIRE_TIME= 10*60*60*1000;
    //密钥盐
    //private static final String TOKEN_SECRET="ljdyaishijin**3nkjnj??";

    /**
     * token验证
     *
     * @param token
     * @return
     */
    public Boolean verify(String token) {

        try {
            //创建token验证器
           /* Claims claim = jwtUtils.getClaimsByToken(token);
            if (claim == null) {
                throw new BaseException(BaseErrorEnum.USER_INVALID);
            }
            String username = claim.getSubject();
            String redisToken = redisUtils.getStr("token:" + username);
            if(null==redisToken){
                throw new BaseException(BaseErrorEnum.USER_INVALID);
            }
            logger.info("认证通过："+"username: " + username+",日期："+ DateUtil.formatDateTime(new Date()));
            */

        } catch (IllegalArgumentException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }

}
