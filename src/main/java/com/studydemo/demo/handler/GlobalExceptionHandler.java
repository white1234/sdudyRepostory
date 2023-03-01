package com.studydemo.demo.handler;

import com.studydemo.demo.em.BaseErrorEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.response.BaseResponse;
import com.studydemo.demo.response.RespGenerator;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Description ExceptionHandlerMethodResolver.getMappedMethod()
 * 会首先找到可以匹配处理异常的所有方法信息，然后对其进行从小到大的排序，
 * 最后取最小的那一个匹配的方法(即匹配度最高的那个)。
 * @Author teronb
 * @Date 2023/1/30 17:38
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    /** 静态变量，系统日志**/
    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    //处理自定义异常
    @ExceptionHandler(value = BaseException.class)
    public BaseResponse<Object> baseExceptionHandler(BaseException e){
        logger.error("发生业务异常！原因是："+e.getMessage());
        return RespGenerator.fail(e.getCode(),e.getMessage());
    }

    //处理空指针异常
    @ExceptionHandler(value = NullPointerException.class)
    public BaseResponse<Object> exceptionHandler(NullPointerException e){
        logger.error("发生空指针异常！原因是：",e);
        return RespGenerator.fail(BaseErrorEnum.BODY_NOT_MATCH);
    }

    //处理其他异常
    @ExceptionHandler(value = Exception.class)
    public BaseResponse<Object> exceptionHandler(Exception e){
        logger.error("未知异常！原因是：",e);
        return RespGenerator.fail(BaseErrorEnum.INTERNAL_SERVER_ERROR);
    }
}
