package com.stylefeng.guns.modular.system.controller.goods;

import com.alibaba.fastjson.JSONArray;
import com.md.goods.exception.GoodsExceptionEnum;
import com.md.goods.factory.ParamFactory;
import com.md.goods.model.Category;
import com.md.goods.model.Param;
import com.md.goods.model.ParamItems;
import com.md.goods.service.ICategoryService;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IParamItemsService;
import com.md.goods.service.IParamService;
import com.md.goods.warpper.ParamWarpper;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 参数组控制器
 *
 * @author fengshuonan
 * @Date 2017年4月26日 12:55:31
 */
@Controller
@RequestMapping("/param")
public class ParamController extends BaseController {

    private String PREFIX = "/goods/param/";

    @Resource
    IParamService paramService;
    @Resource
    IParamItemsService itemsService;
    @Resource
    ICategoryService categoryService;
    @Resource
    IGoodsService goodsService;
    @Resource
    IParamItemsService paramItemsService;
    /**
     * 跳转到参数管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 跳转到添加参数
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 跳转到修改参数
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/toEdit/{paramId}")
    public String toEdit(@PathVariable Long paramId, Model model) {
        Param param = paramService.findById(paramId);
        model.addAttribute("param", param);
        Category category=categoryService.getById(param.getCategoryId());
        model.addAttribute("category",category);
        List<ParamItems> subItems=new ArrayList<>();
        if(param.getItemId()!=null){
            String[] itemId=param.getItemId().split(",");
            for(String id:itemId){
                if(id!=null){
                    ParamItems  item=itemsService.findByid(Long.parseLong(id));
                    subItems.add(item);
                }
            }
        }
        model.addAttribute("subItems", subItems);
        return PREFIX + "edit.html";
    }

    /**
     * 新增参数
     *
     */
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object add(String paramName, String paramValues,Long categoryId) {
    	if (ToolUtil.isOneEmpty(paramName,categoryId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
    	Param param =new Param();
    	param.setName(paramName);
    	param.setCategoryId(categoryId);
        List<ParamItems> itemsList= JSONArray.parseArray(HtmlUtils.htmlUnescape(paramValues),ParamItems.class);
        param.setItemId(ParamFactory.me().getItemsIds(itemsList));
        this.paramService.insert(param);
        paramItemsService.edit(itemsList);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有参数列表
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(String condition,@RequestParam(required = false) Long categoryId) {
       Param param=new Param();
       param.setName(condition);
       param.setCategoryId(categoryId);
    	List<Map<String, Object>> list=paramService.find(param);
        return new ParamWarpper(list).warp();
    }
    /**
     * 参数详情
     */
    @RequestMapping(value = "/detail/{paramId}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable("paramId") Integer paramId) {
        return null;//dictMapper.selectById(dictId);
    }

    /**
     * 修改参数
     */
    @RequestMapping(value = "/edit")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object edit(Long paramId, String paramName, String paramValues,Long categoryId) {
        if (ToolUtil.isOneEmpty(paramId, paramName, categoryId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //判断该分类下的参数组下是否有商品
        List<Map<String, Object>> goodsList=goodsService.findGoodsByParamId(categoryId);
        if(ToolUtil.isNotEmpty(goodsList)){
            throw new GunsException(GoodsExceptionEnum.EXIST_GOODS);
        }
        List<ParamItems> itemsList= JSONArray.parseArray(HtmlUtils.htmlUnescape(paramValues),ParamItems.class);
        Param param=new Param();
        param.setId(paramId);
        param.setName(paramName);
        param.setCategoryId(categoryId);
        //修改参数组(需要将参数项的id用逗号拼接好再set进参数组对象中)
        param.setItemId(ParamFactory.me().getItemsIds(itemsList));
        paramService.edit(param);
        //修改参数项，为参数项填值
        paramItemsService.edit(itemsList);
        return SUCCESS_TIP;
    }
    /**
     * 新增参数项
     */
    @RequestMapping(value = "/addItem")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object addItem() {
        ParamItems paramItems=paramItemsService.insert();
        return paramItems.getId();
    }
    /**
     * 删除参数项
     */
    @RequestMapping(value = "/deleteItem")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object deleteItem(Long ItemId) {
        if (ToolUtil.isEmpty(ItemId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //删除参数项之前要先判断改参数项下是否有商品
        List<Map<String,Object>> goodsList=goodsService.findGoodsByParamId(ItemId);
        if(ToolUtil.isEmpty(goodsList)){
            paramItemsService.delete(ItemId);
        }else{
            throw new GunsException(GoodsExceptionEnum.EXIST_GOODS);
        }
        return SUCCESS_TIP;
    }
    /**
     * 删除参数
     */
    @RequestMapping(value = "/delete")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delete(@RequestParam Long paramId) {
        Param param=this.paramService.findById(paramId);
        List<Map<String,Object>> goodsList=goodsService.findGoodsByParamId(param.getId());
        if(ToolUtil.isEmpty(goodsList)){
            this.paramService.delete(paramId);

        }
        return SUCCESS_TIP;
    }

}
