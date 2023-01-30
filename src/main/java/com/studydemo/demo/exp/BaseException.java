package com.studydemo.demo.exp;

import com.studydemo.demo.config.BaseErrorEnum;
import lombok.Data;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 16:36
 */
@Data
public class BaseException extends RuntimeException{
    private String code;
    private String message;

    public BaseException(BaseErrorEnum baseErrorEnum){
        super(baseErrorEnum.getResultCode());
        this.code = baseErrorEnum.getResultCode();
        this.message = baseErrorEnum.getResultMsg();
    }

}
