package com.studydemo.demo.controller;

import com.studydemo.demo.model.bo.ProductDetailBO;
import com.studydemo.demo.model.vo.DeleteProductVO;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "获取产品详情信息")
    @GetMapping("/getProductDetail")
    @ApiImplicitParam(name = "pid", value = "产品id", paramType = "String")
    public BaseResponse<ProductDetailBO> getProductDetail(@RequestParam(value = "pid") String pid) {
        return RespGenerator.returnOK("成功");
    }

    @ApiOperation(value = "获取产品列表信息")
    @PostMapping("/getProductList")
    public BaseResponse<List<ProductDetailBO>> getProductList() {
        return RespGenerator.returnOK("成功");
    }

    @ApiOperation(value = "删除产品")
    @PostMapping("/deleteProductList")
    public BaseResponse<Integer> deleteProductList(@RequestBody DeleteProductVO deleteProductVO) {
        return RespGenerator.returnOK("成功");
    }

}
