package com.studydemo.demo.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.em.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.mapper.SystemFileMapper;
import com.studydemo.demo.model.entity.SysFile;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.SystemFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/23 17:06
 */
@Service("systemFileService")
public class SystemFileServiceImpl extends ServiceImpl<SystemFileMapper, SysFile> implements SystemFileService {

    @Value("${system.baseDir}")
    private String baseDir;

    @Override
    public String saveFileToLocalDefault(MultipartFile file) throws FileNotFoundException {
        //获取原文件名称
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        String datePath = DateUtil.format(new Date(), DateTimeFormatter.ofPattern(DatePattern.PURE_DATE_PATTERN));
        //加上
        String newPath = realPath+baseDir+"/"+datePath;

        byte[] bytes;
        File targetFile;
        FileOutputStream outputStream;
        BufferedOutputStream bufferedOutputStream;
        try {
            bytes = file.getBytes();
            targetFile = new File(newPath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            outputStream = new FileOutputStream(targetFile);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            outputStream.close();
        }catch (IOException e){
            throw new BaseException(BaseErrorEnum.FILE_UPLOAD_FAIL);
        }

        return UUID.randomUUID().node()+fileSuffix;
    }



    public void save(String url,String fileName){

    }
}
