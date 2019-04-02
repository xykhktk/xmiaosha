package com.x.miaosha.error;

//包装器  BusinessException和EnumBusinessError都实现同个接口
//可以通过new BusinessException  或者 new EnumBusinessError 2种方式来实现自定义错误
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    public BusinessException(CommonError commonError,String message){
        super();
        this.commonError = commonError;
        this.commonError.setErrorMessage(message);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public CommonError setErrorMessage(String Message) {
        return this.commonError.setErrorMessage(Message);
    }

    @Override
    public String getErrorMessage() {
        return this.commonError.getErrorMessage();
    }
}
