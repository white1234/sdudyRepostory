package com.studydemo.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/22 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String orderName;
    private Double price;
    private Date createTime;
    private String desc;
    private Integer seq;
}
