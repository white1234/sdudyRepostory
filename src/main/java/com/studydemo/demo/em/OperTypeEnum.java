package com.studydemo.demo.em;

public enum OperTypeEnum {
    OTHER("other","其他"),
    LOGIN("login","登录"),
    ADD("add","增加"),
    DELETE("delete","删除"),
    MODIFY("modify","修改"),
    SELECT("select","查询"),
    QUERY("query","查找");

    String code;
    String comment;
    OperTypeEnum(String code,String comment){
        this.code = code;
        this.comment = comment;
    }

    public String getCode(){
        return this.code;
    }
}
