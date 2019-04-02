package com.x.miaosha.controller;

import com.x.miaosha.controller.viewobject.UserVO;
import com.x.miaosha.error.BusinessException;
import com.x.miaosha.error.EnumBusinessError;
import com.x.miaosha.response.CommonReturnType;
import com.x.miaosha.service.impl.UserInfoServiceImpl;
import com.x.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("user")
@RequestMapping("/user")
public class UserInfo extends BaseController{

    @Autowired
    UserInfoServiceImpl userInfoService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUserInfo(@RequestParam(name = "id") Integer id) throws BusinessException {

        UserModel userModel =  userInfoService.getUserInfoById(id);

//        userModel.setAge(20);
        if(userModel == null){
            throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return CommonReturnType.create(userVO);
    }
}
