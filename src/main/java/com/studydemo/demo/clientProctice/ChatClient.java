package com.studydemo.demo.clientProctice;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Description 聊天客户端
 * @Author teronb
 * @Date 2023/3/8 23:35
 */
public class ChatClient {
    public void start(String name) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8088));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        //监听服务端消息
        new Thread(new ClientThread(selector)).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            if (StringUtils.hasText(message)) {
                socketChannel.write(StandardCharsets.UTF_8.encode(name + ": " + message));
            }
        }

    }
}
