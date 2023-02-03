package com.studydemo.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.mapper.SysMenuMapper;
import com.studydemo.demo.model.entity.SysMenuInfo;
import com.studydemo.demo.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 16:53
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuInfo> implements ISysMenuService {

    @Override
    public List<String> listMenuByRoles(List<String> roleIds) {
        if(roleIds!=null){
            return this.baseMapper.listMenuByRoles(roleIds);
        }
        return null;
    }
}
