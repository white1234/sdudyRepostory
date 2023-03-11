package com.studydemo.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.Sftp;
import com.studydemo.demo.service.RemoteFileService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Classname SftpFileServiceImpl
 * @Description TODO
 * @Date 2023/3/10 23:08
 * @Created by baiyang
 */
@Slf4j
public class SftpFileServiceImpl implements RemoteFileService {
    private Sftp sftp;
    public SftpFileServiceImpl(Sftp sftp){
        this.sftp=sftp;
    }
    @Override
    public String upload(String dest,String fileName, File file) {
        if(StrUtil.isEmpty(fileName)){
            fileName=file.getName();
        }
        try {
            sftp.cd(dest);
        } catch (Exception e) {
            log.info("该文件夹不存在，自动创建");
            sftp.mkdir(dest);
        }
        try{
            sftp.getClient().put(
                    new FileInputStream(file),
                    dest+fileName
            );
            log.info(">>>文件上传成功");
            return dest+fileName;
        }catch (Exception e){
            log.error("文件上传失败,{}",e);
            return null;
        }
    }
    @Override
    public String pathUpload(String path, File file) {
        try{
            sftp.getClient().put(new FileInputStream(file),path);
        }catch (Exception e){
            log.error("文件上传失败,{}",e);
            return null;
        }
        return null;
    }
    @Override
    public void download(String dest,String fileName, File outFile) {
        sftp.download(dest+fileName,outFile);
    }
    @Override
    public void pathDownload(String path, File outFile) {
        sftp.download(path,outFile);
    }
}
