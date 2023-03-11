package com.studydemo.demo.em;


public enum BaseErrorEnum implements BaseErrorInfoInterface {

    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    THREAD_ERROR("500","线程错误!"),
    INTERNAL_SERVER_ERROR("500","服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!"),
    REQUEST_METHOD_SUPPORT_ERROR("40001", "当前请求方法不支持"),
    REQUEST_DATA_NULL("200904", "当前请求参数为空!"),
    USER_NOT_EXISTS("10010", "该用户不存在!"),
    USER_INVALID("401","当前登录信息已失效,请重新登录！"),
    PASSWORD_ERROR("10011", "密码错误"),
    REQUIRED_ITEM_NULL("10018", "必传项为空！"),
    USER_NAME_LOCK("100002","该账号已被锁定!"),
    FILE_UPLOAD_FAIL("30001","文件上传失败!"),
    FILE_UPLOAD_EMPTY("30002","文件内容不能为空!"),
    FILE_DISCONNECT_FALSE("30003","文件连接关闭异常");

    //错误码
    private String code;
    //错误提示信息
    private String message;


    //构造方法
    BaseErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return message;
    }
}
