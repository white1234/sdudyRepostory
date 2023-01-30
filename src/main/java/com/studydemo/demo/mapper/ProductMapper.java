package com.studydemo.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.studydemo.demo.model.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
