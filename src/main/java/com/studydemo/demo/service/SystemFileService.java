package com.studydemo.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studydemo.demo.model.entity.SysFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/2/23 17:05
 */
public interface SystemFileService extends IService<SysFile> {
    String saveFileToLocalDefault(MultipartFile file) throws FileNotFoundException;
}
