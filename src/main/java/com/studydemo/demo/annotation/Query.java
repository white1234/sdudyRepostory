package com.studydemo.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 * @author czy
 * @date 2022-8-20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /**
     * 查询字段，可不带下划线，可省略
     */
    String field() default "";

    /**
     * 查询类型 默认EQUAL
     */
    Type type() default Type.EQ;


    /**
     * 多字段模糊搜索，英文逗号分割，字段可不带下划线
     */
    String likes() default "";

    enum Type {
        // EQUAL 等于
        EQ
        // GREATER THAN OR EQUAL 大于等于
        , GE
        // LESS THAN OR EQUAL 小于等于
        , LE
        // 模糊查询
        , LIKE
        // 左模糊查询
        , LEFT_LIKE
        // 右模糊查询
        , RIGHT_LIKE
        // 不like
        , NOT_LIKE
        // LT LESS THAN 小于
        , LT
        // 包含
        , IN
        // 不包含
        , NOT_IN
        // 不等于
        , NE
        // between
        , BETWEEN
        // not between
        , NOT_BETWEEN
        // 不为空
        , NOT_NULL
        // 为空
        , IS_NULL
    }
}
