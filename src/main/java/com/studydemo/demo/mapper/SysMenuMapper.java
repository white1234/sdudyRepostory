package com.studydemo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.demo.model.entity.SysMenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/1/31 16:38
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuInfo> {
    List<String> listMenuByRoles(List<String> roleIds);
}
