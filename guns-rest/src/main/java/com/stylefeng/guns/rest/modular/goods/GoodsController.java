package com.stylefeng.guns.rest.modular.goods;

import com.md.goods.model.Goods;
import com.md.goods.service.IGoodsService;
import com.md.goods.warpper.GoodsWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;


/**
 * 商品控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Resource
    IGoodsService goodsService;

    /**
     * 获取所有品牌列表
     */
    @RequestMapping(value = "/list")
    public Object list(@RequestParam String condition,@RequestParam String barcode) {
        Goods goods=new Goods();
    	goods.setName(condition);
    	List<Map<String, Object>> goodsList = this.goodsService.find(goods,barcode);
        return super.warpObject(new GoodsWarpper(goodsList));
    }

}
