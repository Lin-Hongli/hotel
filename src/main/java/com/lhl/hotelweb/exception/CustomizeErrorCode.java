package com.lhl.hotelweb.exception;

import net.bytebuddy.implementation.bind.annotation.Super;

//自定义枚举，一般只定义系统，服务级别的常用code，其他异常通过接口实现传入自定义内容
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    //枚举类，实现私有构造，用户只能调用已有的枚举类
    USER_NOT_FOUND("用户找不到异常"),
    LOGIN_FAIL("登录失败");


    private String message;

    //枚举类的私有构造，外界无法创建实例，只能调用枚举类
    CustomizeErrorCode(String message) {
        this.message = message;
    }

    //实现接口方法
    @Override
    public String getMessage() {
        return message;
    }

}
