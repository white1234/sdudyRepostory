package com.studydemo.demo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/4 12:22
 */
@Data
@Component
@ConfigurationProperties(prefix = "login.limit.properties")
public class LoginLimitProperties {
    private int lockNumber;
    private long lockDuration;  //单位秒
    private long intervalTime; //单位秒
}
