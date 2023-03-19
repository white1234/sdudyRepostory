package com.studydemo.demo.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.studydemo.demo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Classname MyRedisCache
 * @Description TODO
 * @Date 2023/3/11 16:01
 * @Created by baiyang
 */
@Slf4j
public class MybatisRedisCache implements Cache {

    /** todo 切换分布式读写锁 */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private final String id;
    private RedisUtils redisCache;

    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }



    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        checkRedis();
        if(null!=value){
            redisCache.hset(id,key.toString(),value);
        }
    }

    @Override
    public Object getObject(Object o) {
        checkRedis();
        try {
            //根据key从redis中获取数据
            return redisCache.getMapObject(id,o.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错 ");
        }
        return null;

    }

    @Override
    public Object removeObject(Object key) {
        checkRedis();
        if(key!=null){
            redisCache.delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        checkRedis();
        log.debug("清空缓存");
        Set<String> keys = redisCache.hashKeys("*:" + this.id + "*");
        if (!CollectionUtil.isEmpty(keys)) {
            redisCache.delete(keys);
        }
    }

    @Override
    public int getSize() {
        Long size = (Long)redisCache.exec((RedisCallback<Long>) RedisServerCommands::dbSize);
        return size == null ? 0 : size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    public void checkRedis() {
        if (redisCache == null) {
            try {
                redisCache = SpringUtil.getBean("redisUtils");
            } catch (Exception ignored) {
            }
        }
    }

}
