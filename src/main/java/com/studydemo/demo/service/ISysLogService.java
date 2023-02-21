package com.studydemo.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studydemo.demo.model.entity.SysLog;

import java.util.List;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/2/3 22:40
 */

public interface ISysLogService extends IService<SysLog> {
    void saveLogAsync(SysLog log);
    void batchSave(List<SysLog> logs);
}
