package com.lhl.hotelweb.exception;

public class CustomizeException extends RuntimeException {
    private String message;


    //此构造用于传入自定义错误信息
    //即当枚举不满足需求时，可以只传入自定义错误提示
    public CustomizeException(String message) {
        this.message = message;
    }

    //此构造用于传入 自定义的接口实现，或已实现此接口的实现类
    //即可以使用已有枚举类的错误信息，当枚举类不满足需求时，可以通过内部类实现接口自定义错误内容
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }


}
