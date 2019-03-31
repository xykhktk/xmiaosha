package com.x.miaosha.service.impl;

import com.x.miaosha.dao.UserInfoDOMapper;
import com.x.miaosha.dao.UserPasswordDOMapper;
import com.x.miaosha.dataobject.UserInfoDO;
import com.x.miaosha.dataobject.UserPasswordDO;
import com.x.miaosha.service.UserInfoService;
import com.x.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    UserInfoDOMapper userInfoDOMapper;

    @Autowired
    UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserInfoById(Integer id) {
        UserInfoDO userInfoDO =  userInfoDOMapper.selectByPrimaryKey(id);

        if(userInfoDO == null){
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userInfoDO.getId());

        UserModel userModel = convertFromUserDataObject(userInfoDO,userPasswordDO);
        return userModel;
    }

    private UserModel convertFromUserDataObject(UserInfoDO userInfoDO, UserPasswordDO userPasswordDO){
        if(userInfoDO == null) return null;

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfoDO,userModel);

        if(userPasswordDO == null){
            return  userModel;
        }

        userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        return  userModel;
    }
}
