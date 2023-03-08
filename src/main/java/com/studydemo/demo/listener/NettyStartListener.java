package com.studydemo.demo.listener;

import com.studydemo.demo.service.SocketServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author teronb
 * @Date 2023/3/8 23:06
 */
@Component
public class NettyStartListener implements ApplicationRunner {

    @Resource
    private SocketServer socketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        socketServer.start();
    }
}
