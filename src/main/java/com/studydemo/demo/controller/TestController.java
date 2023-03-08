package com.studydemo.demo.controller;

import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.threadtest.TransmittableThreadLocalTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author teronb
 * @Date 2023/3/8 8:44
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    @Qualifier("transmittableThreadLocalTest")
    TransmittableThreadLocalTest transmittableThreadLocalTest;

    @PostMapping("/transmittableTest")
    public BaseResponse transmittableThreadLocalTest() {
        transmittableThreadLocalTest.testTransmittableThreadLocal();
        return RespGenerator.success("用户批量新增成功!");
    }

    @PostMapping("/testPoolAdd")
    public BaseResponse testThreadPoolAdd() {
        transmittableThreadLocalTest.testThreadPoolAdd();
        return RespGenerator.success("用户批量新增成功!");
    }

}
