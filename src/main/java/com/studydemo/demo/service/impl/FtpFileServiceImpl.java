package com.studydemo.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.studydemo.demo.em.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.service.RemoteFileService;
import com.studydemo.demo.utils.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @Classname FtpFileService
 * @Description TODO
 * @Date 2023/3/10 12:17
 * @Created by baiyang
 */
@Slf4j
public class FtpFileServiceImpl implements RemoteFileService {
    private FtpUtil ftpUtil;

    public FtpFileServiceImpl(FtpUtil ftpUtil) {
        this.ftpUtil = ftpUtil;
    }

    @Override
    public String upload(String dest, String name, File file) {
        if(StrUtil.isEmpty(name)){
            name = file.getName();
        }
        FTPClient ftpClient =null;
        try {
            ftpClient = ftpUtil.createFtpClient();
            //切换工作目录，并检查目录是否存在
            boolean isExistDir = ftpClient.changeWorkingDirectory(dest);
            if(!isExistDir){
                //不存在时创建目录
                ftpClient.makeDirectory(dest);
                ftpClient.changeWorkingDirectory(dest);
            }
            //保存文件
            ftpClient.storeFile(name,new FileInputStream(file));
            log.info("文件上传成功！");
        }catch (IOException e){
             e.printStackTrace();
        }finally {
            ftpUtil.close(ftpClient);
        }
        return null;
    }

    @Override
    public String  pathUpload(String path, File outFile) {
        String[] split = path.split(File.separator);
        int size = split.length;
        FTPClient ftpClient=null;
        try{
            ftpClient=ftpUtil.createFtpClient();
            for (int i=0;i<size-2;i++) {
                String tempPath=split[i];
                if (StrUtil.isEmpty(tempPath)) {
                    continue;
                }
                if (!ftpClient.changeWorkingDirectory(tempPath)) {
                    ftpClient.makeDirectory(tempPath);
                    ftpClient.changeWorkingDirectory(tempPath);
                }
            }
            //保存文件
            ftpClient.storeFile(split[size-1],new FileInputStream(outFile));
            log.info("上传文件成功");
            return path;
        }catch (Exception e){
            log.error("上传文件失败,{}",e);
        }finally {
            ftpUtil.close(ftpClient);
        }
        return null;
    }

    @Override
    public void download(String dest,String fileName, File outFile) {
        pathDownload(dest+fileName,outFile);
    }
    @Override
    public void pathDownload(String path, File outFile) {
        FTPClient ftpClient=null;
        try {
            ftpClient=ftpUtil.createFtpClient();
            ftpClient.retrieveFile(path,new FileOutputStream(outFile));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ftpUtil.close(ftpClient);
        }
    }
}
