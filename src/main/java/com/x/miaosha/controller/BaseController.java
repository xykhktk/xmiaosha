package com.x.miaosha.controller;

import com.x.miaosha.error.BusinessException;
import com.x.miaosha.error.EnumBusinessError;
import com.x.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class BaseController {

    //@ExceptionHandler :由这个方法来处理未被controller处理的异常
    //@ResponseStatus 即便是抛出了异常，返回的错误码也不能是500，而是200
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception ex){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setSuccess(false);

        HashMap<String,Object> data = new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            data.put("errorCode",businessException.getErrorCode());
            data.put("errorMessage",businessException.getErrorMessage());

        }else{
            data.put("errorCode", EnumBusinessError.UNKNOW_ERROR.getErrorCode());
            data.put("errorMessage",EnumBusinessError.UNKNOW_ERROR.getErrorMessage());
        }
        commonReturnType.setData(data);
        return commonReturnType;
    }

}
