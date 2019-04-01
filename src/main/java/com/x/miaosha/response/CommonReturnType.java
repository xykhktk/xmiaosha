package com.x.miaosha.response;

public class CommonReturnType {

    private boolean success;
    private Object data;

     public static CommonReturnType create(Object data){
        return CommonReturnType.create(data,true);
    }

    public static CommonReturnType create(Object data, boolean success){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setData(data);
        commonReturnType.setSuccess(success);
        return commonReturnType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
