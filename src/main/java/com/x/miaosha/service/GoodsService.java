package com.x.miaosha.service;

import com.github.pagehelper.PageInfo;
import com.x.miaosha.error.BusinessException;
import com.x.miaosha.service.model.GoodsModel;

import java.util.ArrayList;

public interface GoodsService {

    GoodsModel addGoods(GoodsModel goodsModel) throws BusinessException;

    PageInfo<ArrayList<GoodsModel>> selectList(int page);

}
