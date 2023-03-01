package com.studydemo.demo.controller;

import com.studydemo.demo.annotation.OperationLog;
import com.studydemo.demo.em.OperTypeEnum;
import com.studydemo.demo.model.Pagination;
import com.studydemo.demo.model.bo.ProductDetailBO;
import com.studydemo.demo.model.entity.Product;
import com.studydemo.demo.model.vo.DeleteProductVO;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/29 23:01
 */
@Api(tags = "产品接口")
@RestController
@RequestMapping("/productController")
public class ProductController {

    @Autowired
    IProductService productService;

    @ApiOperation(value = "获取产品详情信息")
    @GetMapping("/getProductDetail")
    @ApiImplicitParam(name = "pid", value = "产品id", paramType = "String")
    public BaseResponse<ProductDetailBO> getProductDetail(@RequestParam(value = "pid") String pid) {
        return RespGenerator.success("成功");
    }

    @ApiOperation(value = "获取产品列表信息")
    @GetMapping ("/getProductList")
    @OperationLog(content = "获取产品列表",action = "获取产品列表信息",opType = OperTypeEnum.QUERY)
    public BaseResponse<List<ProductDetailBO>> getProductList(Pagination pagination, Product product) {
        return RespGenerator.success(productService.queryProductList(pagination,product));
    }

    @ApiOperation(value = "删除产品")
    @PostMapping("/deleteProductList")
    public BaseResponse<Integer> deleteProductList(@RequestBody DeleteProductVO deleteProductVO) {
        return RespGenerator.success("成功");
    }

}
