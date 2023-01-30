package com.studydemo.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studydemo.demo.mapper.ProductMapper;
import com.studydemo.demo.model.Pagination;
import com.studydemo.demo.model.bo.ProductDetailBO;
import com.studydemo.demo.model.entity.Product;
import com.studydemo.demo.service.IProductService;
import com.studydemo.demo.utils.QueryUtils;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 8:51
 */

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    public IPage<ProductDetailBO> queryProductList(Pagination pagination , Product product) {
        QueryWrapper queryWrapper = QueryUtils.buildQueryWrapper(product);
        IPage page = this.page(new Page<>(pagination.getCurrentPage(), pagination.getPageSize()), queryWrapper);

        return page;
    }
}
