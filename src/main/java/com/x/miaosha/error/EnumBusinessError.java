package com.x.miaosha.error;

public enum EnumBusinessError implements CommonError{

    UNKNOW_ERROR(10001,"未知错误"),
    PARAMETER_VALIDATION_ERROR(10002,"参数不合法"),

    USER_NOT_EXIST(10001,"用户不存在"),
    USER_NOT_EXIST_OR_PASSWORD_ERROR(10001,"用户不存在或密码错误"),

    ;
    private int code;
    private String message;

    EnumBusinessError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public CommonError setErrorMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String getErrorMessage() {
        return this.message;
    }
}
