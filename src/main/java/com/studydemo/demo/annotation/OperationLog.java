package com.studydemo.demo.annotation;

import com.studydemo.demo.em.OperTypeEnum;

import java.lang.annotation.*;

/**
 * @author by
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OperationLog {
    /**内容*/
    String content() default "";

    //方法描述
    String action() default "";
    //操作类型
    OperTypeEnum opType() default OperTypeEnum.OTHER;

    SysType sysType() default SysType.PLATFORM;

    enum SysType{
        PLATFORM("sys_platform","网络平台"),
        APP("sys_app","移动app");
        String code;
        String comment;

        SysType(String code,String comment){
            this.code = code;
            this.comment = comment;
        }
        public String getCode(){
            return code;
        }
    }
}
