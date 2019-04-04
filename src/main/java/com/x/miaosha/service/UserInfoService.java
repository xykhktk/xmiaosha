package com.x.miaosha.service;

import com.x.miaosha.controller.viewobject.UserVO;
import com.x.miaosha.error.BusinessException;
import com.x.miaosha.service.model.UserModel;

public interface UserInfoService {

    UserModel getUserInfoById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}
