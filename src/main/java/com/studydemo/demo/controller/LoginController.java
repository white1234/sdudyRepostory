package com.studydemo.demo.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.studydemo.demo.annotation.OperationLog;
import com.studydemo.demo.em.OperTypeEnum;
import com.studydemo.demo.model.entity.SysUserInfo;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import com.studydemo.demo.service.ILoginService;
import com.studydemo.demo.service.ISysUserService;
import com.studydemo.demo.utils.RedisUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/31 13:24
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    @Qualifier("redisUtils")
    private RedisUtils redisUtils;

    @Autowired
    ILoginService loginService;

    @Autowired
    @Qualifier("sysUserService")
    private ISysUserService userService;

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Producer producer;

    @ApiOperation(value = "用户注册")
    @PostMapping("/signIn")
    @OperationLog(content = "用户注册接口",action = "添加用户",opType = OperTypeEnum.ADD)
    public BaseResponse regist(SysUserInfo userInfo){
        userInfo.setId(UUID.fastUUID().getLeastSignificantBits());
        userService.registUser(userInfo);
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return RespGenerator.success("账户注册成功!");
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "String")
    })
    public BaseResponse<HashMap> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return RespGenerator.success(loginService.loginAop(username,password));
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
        redisUtils.set("captcha:"+key, code, 1200);

        return RespGenerator.success(
                MapUtil.builder()
                        .put("userKey", key)
                        .put("captcherImg", base64Img)
                        .build()
        );
    }

}
