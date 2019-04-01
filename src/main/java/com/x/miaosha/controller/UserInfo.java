package com.x.miaosha.controller;

import com.x.miaosha.controller.viewobject.UserVO;
import com.x.miaosha.response.CommonReturnType;
import com.x.miaosha.service.impl.UserInfoServiceImpl;
import com.x.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("user")
@RequestMapping("/user")
public class UserInfo {

    @Autowired
    UserInfoServiceImpl userInfoService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUserInfo(@RequestParam(name = "id") Integer id){

        UserModel userModel =  userInfoService.getUserInfoById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return CommonReturnType.create(userVO);
    }

}