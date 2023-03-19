package com.studydemo.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product implements Serializable {
    /**
     * 
     */
    @TableId(value = "pid")
    private String pid;

    /**
     * 产品名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 产品价格
     */
    @TableField(value = "price")
    private String price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}