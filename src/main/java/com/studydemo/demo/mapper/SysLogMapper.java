package com.studydemo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.demo.model.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 自定义操作日志接口
 * @Author teronb
 * @Date 2023/2/3 22:39
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
