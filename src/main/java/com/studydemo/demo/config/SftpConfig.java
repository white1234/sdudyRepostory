package com.studydemo.demo.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.Session;
import com.studydemo.demo.model.SshFileProperties;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname SftpConfig
 * @Description TODO
 * @Date 2023/3/10 12:10
 * @Created by baiyang
 */
@Configuration
@Slf4j
public class SftpConfig {
    @Autowired
    private SshFileProperties sshFileProperties;
    @Value("${file.useftp}")
    private Boolean useftp;

    @Bean
    public Sftp getSftp(){
        if(useftp){
            return null;
        }
        return createSftp(sshFileProperties.getHost(),sshFileProperties.getPort(),sshFileProperties.getUsername(),sshFileProperties.getPassword());
    }

    @Bean
    public Session getSession() {
        if(useftp){
            return null;
        }
        return createSession(sshFileProperties.getHost(),
                sshFileProperties.getPort(),
                sshFileProperties.getUsername(),
                sshFileProperties.getPassword());
    }

    public static Sftp createSftp(String sshHost, int sshPort, String sshUser, String sshPass) {
        return JschUtil.createSftp(sshHost, sshPort, sshUser, sshPass);
    }
    public static Session createSession(String sshHost, int sshPort, String sshUser, String sshPass) {
        return JschUtil.getSession(sshHost, sshPort, sshUser, sshPass);
    }
}
