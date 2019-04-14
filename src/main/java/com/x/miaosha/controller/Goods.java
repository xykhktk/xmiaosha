package com.x.miaosha.controller;

import com.github.pagehelper.PageInfo;
import com.x.miaosha.error.BusinessException;
import com.x.miaosha.response.CommonReturnType;
import com.x.miaosha.service.GoodsService;
import com.x.miaosha.service.impl.GoodsServiceImpl;
import com.x.miaosha.service.model.GoodsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller("goods")
@RequestMapping("/goods")
public class Goods extends BaseController{

    @Autowired
    GoodsServiceImpl goodsService;

    @RequestMapping(value = "/addGoods",method = RequestMethod.POST )
    @ResponseBody
    public CommonReturnType addGoods(@RequestParam(name="name") String name,
        @RequestParam(name="price") BigDecimal price,
        @RequestParam(name="descript") String descript,
        @RequestParam(name="imgUrl") String imgUrl
        ) throws BusinessException {
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setDescript(descript);
        goodsModel.setName(name);
        goodsModel.setImgUrl(imgUrl);
        goodsModel.setPrice(price);

        GoodsModel goodsModel1 =goodsService.addGoods(goodsModel);

        return CommonReturnType.create(goodsModel1);
    }

    @RequestMapping("/list")
    @ResponseBody
    public CommonReturnType list(@RequestParam(name = "page",required = false)int page){
        int aa = page;

        PageInfo<ArrayList<GoodsModel>> arrayListPageInfo =  goodsService.selectList(page);
        return CommonReturnType.create(arrayListPageInfo);
    }

}
