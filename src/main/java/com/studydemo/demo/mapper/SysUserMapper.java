package com.studydemo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.demo.cache.MybatisRedisCache;
import com.studydemo.demo.model.entity.SysUserInfo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//@CacheNamespace(implementation = MybatisRedisCache.class,eviction = MybatisRedisCache.class)
public interface SysUserMapper extends BaseMapper<SysUserInfo> {
}
