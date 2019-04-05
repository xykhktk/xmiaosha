package com.x.miaosha.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidateResult {

    private boolean hasError = false;
    private Map<String,String> errorMessageMap = new HashMap<>();

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrorMessageMap() {
        return errorMessageMap;
    }

    public void setErrorMessageMap(Map<String, String> errorMessageMap) {
        this.errorMessageMap = errorMessageMap;
    }

    public String getErrorMessage(){
        return StringUtils.join(errorMessageMap.values().toArray(),",");
    }

}
