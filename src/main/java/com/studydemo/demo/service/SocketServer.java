package com.studydemo.demo.service;

import com.studydemo.demo.handler.SocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 3.编写socket服务
 * @Author teronb
 * @Date 2023/3/8 21:53
 */
@Component
@Slf4j
public class SocketServer {
    @Resource
    private SocketInitializer socketInitializer;

    @Getter
    private ServerBootstrap serverBootstrap;

    /**
     * netty服务监听端口
     */
    @Value("${netty.port:8088}")
    private int port;
    /**
     * 主线程组数量
     */
    @Value("${netty.bossThread:1}")
    private int bossThread;

    /**
     * 启动netty服务器
     */
    public void start() {
        this.init();
        this.serverBootstrap.bind(this.port);
        log.info("Netty started on port: {} (TCP) with boss thread {}", this.port, this.bossThread);
    }

    /**
     * @Description 初始化netty配置，线程组，配置channel类型，初始化器
     * @Param null
     * @Return {@link null}
     * @Author teronb
     * @Date 2023/3/8 22:04
     */
    private void init() {
        // 创建两个线程组，bossGroup为接收请求的线程组，一般1-2个就行
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(bossThread);

        // 实际工作的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(socketInitializer);
    }
}
