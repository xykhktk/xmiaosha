package com.x.miaosha.error;

import javax.lang.model.element.NestingKind;

public interface CommonError {
//    void setErrorCode(int code);
    int getErrorCode();

    CommonError setErrorMessage(String Message);
    String getErrorMessage();

}
