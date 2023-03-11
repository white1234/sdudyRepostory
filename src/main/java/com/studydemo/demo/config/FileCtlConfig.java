package com.studydemo.demo.config;

import cn.hutool.extra.ssh.Sftp;
import com.studydemo.demo.service.RemoteFileService;
import com.studydemo.demo.service.impl.FtpFileServiceImpl;
import com.studydemo.demo.service.impl.SftpFileServiceImpl;
import com.studydemo.demo.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Classname FileCtlConfig
 * @Description
 * @Date 2023/3/10 22:43
 * @Created by baiyang
 */
@Configuration
public class FileCtlConfig {
    @Autowired(required = false)
    private Sftp sftp;
    @Autowired(required = false)
    private FtpUtil ftpUtil;
    /**
     条件符合时，创建
     */
    @Bean("fileService")
    @Profile("ftp")
    RemoteFileService ftp(){
        return new FtpFileServiceImpl(ftpUtil);
    }
    /**
     条件符合时，创建
     */
    @Bean("fileService")
    @Profile("ssh")
    RemoteFileService sftp(){
        return new SftpFileServiceImpl(sftp);
    }
}
