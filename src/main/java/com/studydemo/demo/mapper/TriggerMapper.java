package com.studydemo.demo.mapper;


import com.studydemo.demo.model.entity.Trigger;
import com.studydemo.demo.model.entity.TriggerKey;

public interface TriggerMapper {
    int deleteByPrimaryKey(TriggerKey key);

    int insert(Trigger record);

    int insertSelective(Trigger record);

    Trigger selectByPrimaryKey(TriggerKey key);

    int updateByPrimaryKeySelective(Trigger record);

    int updateByPrimaryKeyWithBLOBs(Trigger record);

    int updateByPrimaryKey(Trigger record);
}