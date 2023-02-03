package com.studydemo.demo.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.ILoginService;
import com.studydemo.demo.utils.RedisUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 13:24
 */
@RestController
@RequestMapping()
public class LoginController {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    ILoginService loginService;

    @Autowired
    Producer producer;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "String")
    })
    public BaseResponse<HashMap> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return RespGenerator.success(loginService.login(username,password));
    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public BaseResponse<Integer> logout() {
        return RespGenerator.success("退出成功");
    }


    @GetMapping("/captcha")
    public BaseResponse Captcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        Base64.Encoder encoder = Base64.getMimeEncoder();
        //Base64.Decoder decoder = Base64.getMimeDecoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encodeToString(outputStream.toByteArray());
        redisUtils.set("captcha:"+key, code, 120);

        return RespGenerator.success(
                MapUtil.builder()
                        .put("userKey", key)
                        .put("captcherImg", base64Img)
                        .build()
        );
    }

}
