package com.studydemo.demo.clientProctice;

import java.io.IOException;

/**
 * @Description
 * @Author teronb
 * @Date 2023/3/8 23:40
 */
public class Client1 {
    public static void main(String[] args) throws IOException {
        new ChatClient().start("李四");
    }
}
