package com.x.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.x.miaosha.controller.viewobject.UserVO;
import com.x.miaosha.error.BusinessException;
import com.x.miaosha.error.EnumBusinessError;
import com.x.miaosha.response.CommonReturnType;
import com.x.miaosha.service.impl.UserInfoServiceImpl;
import com.x.miaosha.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
public class User extends BaseController{

    @Autowired
    UserInfoServiceImpl userInfoService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping("/getOpt")
    @ResponseBody
    public CommonReturnType getOpt(@RequestParam(name = "phone") String phone) throws BusinessException {

        if(StringUtils.isEmpty(phone)){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"手机号码不能为空");
        }

        Random random = new Random();
        int code = random.nextInt(8999);
        String opt = String.valueOf(code + 1000);

        httpServletRequest.getSession().setAttribute(phone,opt);
        System.out.println("phone:" + phone + ",code:" + opt);
        //发送短信省略

        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "name") String name,
        @RequestParam(name = "gender") Integer gender,
        @RequestParam(name = "age") Integer age,
        @RequestParam(name = "telphone") String telphone,
        @RequestParam(name = "password") String password,
        @RequestParam(name = "code") String code
        ) throws BusinessException {

        String validateCode = (String) httpServletRequest.getSession().getAttribute(telphone);

        if(!StringUtils.equals(validateCode,code)){
            throw new BusinessException(EnumBusinessError.UNKNOW_ERROR,"短信验证码错误");
        }

        UserModel userModel = new UserModel();
        userModel.setAge(age);
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setTelphone(telphone);

//        userModel.setEncrptPassword(MD5Encoder.encode(password.getBytes()));
        userModel.setEncrptPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        userInfoService.register(userModel);
        return CommonReturnType.create(null);
    }

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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "phone")String phone,
        @RequestParam(name = "password")String password
        ) throws BusinessException {

        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        UserModel userModel = userInfoService.login(phone,passwordMd5);
        httpServletRequest.getSession().setAttribute("userLonginInfo",userModel);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return CommonReturnType.create(userVO);
    }
}
