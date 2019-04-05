package com.x.miaosha.service.impl;

import com.x.miaosha.dao.UserInfoDOMapper;
import com.x.miaosha.dao.UserPasswordDOMapper;
import com.x.miaosha.dataobject.UserInfoDO;
import com.x.miaosha.dataobject.UserPasswordDO;
import com.x.miaosha.error.BusinessException;
import com.x.miaosha.error.EnumBusinessError;
import com.x.miaosha.service.UserInfoService;
import com.x.miaosha.service.model.UserModel;
import com.x.miaosha.validator.ValidatorImpl;
import com.x.miaosha.validator.ValidateResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    UserInfoDOMapper userInfoDOMapper;

    @Autowired
    UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    ValidatorImpl validator;

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

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
//        if(userModel == null
//                || StringUtils.isEmpty(userModel.getName())
//                || StringUtils.isEmpty(userModel.getEncrptPassword())
//                || StringUtils.isEmpty(userModel.getTelphone())
//                || userModel.getGender() == null
//                || userModel.getAge() == null){
//            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        ValidateResult validateResult =  validator.validate(userModel);
        if(validateResult.isHasError()){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,validateResult.getErrorMessage());
        }

        UserInfoDO userInfoDO= convertFromModel(userModel);
        userInfoDOMapper.insertSelective(userInfoDO);


        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDO.setUserInfoId(userInfoDO.getId());
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    @Override
    public UserModel login(String telphone, String password) throws BusinessException {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByTelphone(telphone);
        if(userInfoDO == null){
            throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        }

        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userInfoDO.getId());
        if(!userPasswordDO.getEncrptPassword().equals(password)){
            throw new BusinessException(EnumBusinessError.USER_NOT_EXIST_OR_PASSWORD_ERROR);
        }

        UserModel userModel = convertFromUserDataObject(userInfoDO,userPasswordDO);
        return userModel;
    }

    private UserInfoDO convertFromModel(UserModel userModel){
        UserInfoDO userInfoDO = new UserInfoDO();
        BeanUtils.copyProperties(userModel,userInfoDO);
        return  userInfoDO;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserInfoId(userModel.getId());
        return userPasswordDO;
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
