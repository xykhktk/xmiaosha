package com.x.miaosha.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.x.miaosha.dao.GoodsDOMapper;
import com.x.miaosha.dao.StockDOMapper;
import com.x.miaosha.dataobject.GoodsDO;
import com.x.miaosha.dataobject.StockDO;
import com.x.miaosha.error.BusinessException;
import com.x.miaosha.error.EnumBusinessError;
import com.x.miaosha.service.GoodsService;
import com.x.miaosha.service.model.GoodsModel;
import com.x.miaosha.validator.ValidateResult;
import com.x.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    ValidatorImpl validator;

    @Autowired
    GoodsDOMapper goodsDOMapper;

    @Autowired
    StockDOMapper stockDOMapper;

    @Override
    @Transactional
    public GoodsModel addGoods(GoodsModel goodsModel) throws BusinessException {
        ValidateResult validateResult = validator.validate(goodsModel);
        if(validateResult.isHasError()){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,validateResult.getErrorMessage());
        }

        GoodsDO goodsDO = convertModelToGoodsDo(goodsModel);
        goodsDOMapper.insertSelective(goodsDO);

        StockDO stockDO = new StockDO();
        stockDO.setGoodsId(goodsDO.getId());
        stockDOMapper.insertSelective(stockDO);

        return convertDoToModel(goodsDO,stockDO);
    }

    @Override
    public PageInfo<ArrayList<GoodsModel>> selectList(int page) {
        ArrayList<GoodsDO> goodsDOs =goodsDOMapper.selectList();
        ArrayList<GoodsModel> goodsModels = new ArrayList<>();
        for(GoodsDO goodsDO : goodsDOs){
            StockDO stockDO = stockDOMapper.selectByGoodsId(goodsDO.getId());//-_-! 联查更好些吧
            GoodsModel goodsModel = convertDoToModel(goodsDO,stockDO);
            goodsModels.add(goodsModel);
        }

        PageHelper.startPage(page,10);
        return new PageInfo(goodsModels);
    }

    private GoodsDO convertModelToGoodsDo(GoodsModel goodsModel){
        if(goodsModel == null) return null;
        GoodsDO goodsDO = new GoodsDO();
        BeanUtils.copyProperties(goodsModel,goodsDO);
        return goodsDO;
    }

    private GoodsModel convertDoToModel(GoodsDO goodsDO,StockDO stockDO){
        GoodsModel goodsModel = new GoodsModel();
        BeanUtils.copyProperties(goodsDO,goodsModel);
        goodsModel.setStock(stockDO.getStock());
        return goodsModel;
    }
}
