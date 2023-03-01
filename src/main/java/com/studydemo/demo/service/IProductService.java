package com.studydemo.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.studydemo.demo.model.Pagination;
import com.studydemo.demo.model.bo.ProductDetailBO;
import com.studydemo.demo.model.entity.Product;

public interface IProductService extends IService<Product> {

    IPage<ProductDetailBO> queryProductList(Pagination pagination, Product product);
}
