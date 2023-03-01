package com.studydemo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.demo.model.entity.SysFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/2/23 18:25
 */
@Mapper
public interface SystemFileMapper extends BaseMapper<SysFile> {
}
