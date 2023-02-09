package com.studydemo.demo;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true) //
@EnableScheduling
//@MapperScan({"com.studydemo.demo.mapper"})
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(new Class[]{DemoApplication.class, SpringUtil.class}, args);
        System.out.println("dev第一次提交");
    }

}
