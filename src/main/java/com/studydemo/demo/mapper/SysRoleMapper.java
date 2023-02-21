package com.studydemo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.demo.model.entity.SysRoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 自定义XX接口
 * @Author teronb
 * @Date 2023/1/31 16:37
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleInfo> {
    List<SysRoleInfo> listUserRoles(long userId);
    int saveUserRole(@Param(value = "id") String id,@Param(value = "userId")long userId,@Param(value = "roleId")String roleId);
}
