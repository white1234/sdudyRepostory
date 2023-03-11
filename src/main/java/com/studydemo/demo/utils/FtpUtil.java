package com.studydemo.demo.utils;

import cn.hutool.core.util.StrUtil;
import com.studydemo.demo.em.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.model.SshFileProperties;
import groovy.util.logging.Log4j2;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Classname FtpUtil
 * @Description Ftp工具类，创建及关闭ftpClient
 * @Date 2023/3/10 11:54
 * @Created by baiyang
 */
@Configuration
public class FtpUtil {
    @Autowired
    private SshFileProperties sshFileProperties;

    @Value("${file.useftp}")
    private Boolean useftp;

    /*
     * @Description 创建ftp客户端
     * @Param []
     * @return org.apache.commons.net.ftp.FTPClient
     * @date 2023/3/10 12:04
     * @author baiyang
     */
    public FTPClient createFtpClient(){
        if(!useftp){
            return null;
        }
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(sshFileProperties.getHost(),sshFileProperties.getPort());
            ftpClient.enterLocalPassiveMode();
            // 设置上传目录
            ftpClient.setBufferSize(1024);
            ftpClient.setConnectTimeout(10 * 1000);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            return ftpClient;
        }catch (IOException e){
           e.printStackTrace();
        }
        return null;
    }

    /*
     * @Description 关闭ftp客户端链接
     * @Param [ftpClient]
     * @return void
     * @date 2023/3/10 12:08
     * @author baiyang
     */
    public void close(FTPClient ftpClient){
        try {
            if(ftpClient!=null){
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new BaseException(BaseErrorEnum.FILE_DISCONNECT_FALSE);
        }
    }
}
