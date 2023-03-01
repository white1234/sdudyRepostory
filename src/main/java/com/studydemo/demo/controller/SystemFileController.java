package com.studydemo.demo.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studydemo.demo.annotation.OperationLog;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.SystemFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/23 17:03
 */
@Slf4j
@Api(tags = "系统文件接口")
@RestController
@RequestMapping("/system")
public class SystemFileController {

    @ApiOperation("文件上传")
    //@OperationLog(content = "文件上传到服务器",action = "文件上传到本地服务器")
    @PostMapping("/upload")
    //将上传的文件放在tomcat目录下面的file文件夹中
    public String upload(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取到原文件全名
        String originalFilename = multipartFile.getOriginalFilename();
        // request.getServletContext()。getRealPath("")这里不能使用这个，这个是获取servlet的对象，并获取到的一个临时文件的路径，所以这里不能使用这个
        //这里获取到我们项目的根目录，classpath下面
        String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        //文件夹路径,这里以时间作为目录
        String path = realPath + "static/" + format;
        //判断文件夹是否存在，存在就不需要重新创建，不存在就创建
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        //转换成对应的文件存储，new File第一个参数是目录的路径，第二个参数是文件的完整名字
        multipartFile.transferTo(new File(file, originalFilename));

        //上传文件的全路径
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + format + "/" + originalFilename;
        return url;
    }

    @PostMapping("uploadBatch")
    //将上传的文件放在tomcat目录下面的file文件夹中
    public String uploadBatch(MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> list = new ArrayList<>();
        //System.out.println(files.length);
        for (MultipartFile multipartFile : files) {
            //获取到文件全名
            String originalFilename = multipartFile.getOriginalFilename();
            // request.getServletContext()。getRealPath("")这里不能使用这个，这个是获取servlet的对象，并获取到的一个临时文件的路径，所以这里不能使用这个
            //这里获取到我们项目的根目录，classpath下面
            String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            //文件夹路径,这里以时间作为目录
            String path = realPath + "static/" + format;
            //判断文件夹是否存在，存在就不需要重新创建，不存在就创建
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            //转换成对应的文件存储，new File第一个参数是目录的路径，第二个参数是文件的完整名字
            multipartFile.transferTo(new File(file, originalFilename));

            //上传文件的全路径，一般是放在tomcat中
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + format + "/" + originalFilename;
            //System.out.println("url=》》》》》"+url);
            list.add(url);
        }
        return new ObjectMapper().writeValueAsString(list);
    }

}
