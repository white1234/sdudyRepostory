package com.studydemo.demo.config;

public interface BaseErrorInfoInterface {

    /**
     * 错误码
     *
     * @return
     */
    String getResultCode();

    /**
     * 错误信息
     *
     * @return
     */
    String getResultMsg();
}
