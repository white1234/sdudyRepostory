package com.studydemo.demo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/2 22:59
 */
@Data
@Component
@ConfigurationProperties(prefix = "rsa.properties")
public  class RsaProperties {
    private String publicKey;
    private String privateKey;
}

