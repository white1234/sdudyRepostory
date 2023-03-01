package com.studydemo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.demo.model.entity.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/3/1 14:51
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserInfo> {
}
