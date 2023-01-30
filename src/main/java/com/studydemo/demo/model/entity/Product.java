package com.studydemo.demo.model.entity;

import com.studydemo.demo.annotation.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 17:58
 */
@Data
@NoArgsConstructor
public class Product {

    @Query
    private String pid;

    @Query(field = "product_name",type = Query.Type.LIKE)
    private String productName;

    @Query(field = "price")
    private String price;
}
