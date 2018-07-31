package com.stylefeng.guns.modular.system.controller.goods;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.goods.factory.PriceTagFactory;
import com.md.goods.model.Shop;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IShopService;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.dao.DeptMapper;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.service.IDeptService;

/**
 * 门店控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/stores")
public class ShopController extends BaseController {

    private String PREFIX = "/goods/stores/";
    
    @Resource
    IShopService storesService;
    @Resource
    IPriceTagService priceTagService;
    @Resource
    IDeptService deptService;
    @Resource
    DeptMapper deptMapper;
    /**
     * 跳转到门店管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 跳转到添加门店
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 跳转到修改门店
     */
    @RequestMapping("/toEdit/{storesId}")
    public String toEdit(@PathVariable Long storesId, Model model) {
        Shop stores=storesService.findById(storesId);
        model.addAttribute(stores);
        return PREFIX + "edit.html";
    }

    /**
     * 新增门店
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Shop stores) {
    	//初始化该门店部门
    	Dept dept = new Dept();
    	dept.setFullname(stores.getName());
    	dept.setSimplename(stores.getName());
    	dept.setPid(30);
    	dept.setNum(1);
    	deptService.deptSetPids(dept);
        deptMapper.insert(dept);
        //获取门店坐标
    	if(ToolUtil.isNotEmpty(stores.getAddress())){
    		double[] point = MapUtil.addressToGPS(stores.getAddress());
    		if (ToolUtil.isNotEmpty(point)) {
    			stores.setLat(point[1]);
            	stores.setLng(point[0]);
			}
    	}
    	stores.setDeptId(dept.getId());
    	storesService.insert(stores);
        //为门店初始化所有的价格标签
        PriceTagFactory.me().createPriceTag(stores.getId());
        return SUCCESS_TIP;
    }

    /**
     * 获取所有门店列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Shop stores=new Shop();
        stores.setName(condition);
        return storesService.find(stores);
    }
    /**
     * 修改门店
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(Shop stores) {
    	if(ToolUtil.isNotEmpty(stores.getAddress())){
    		double[] point = MapUtil.addressToGPS(stores.getAddress());
    		if (ToolUtil.isNotEmpty(point)) {
    			stores.setLat(point[1]);
            	stores.setLng(point[0]);
			}
    	}
        storesService.edit(stores);
        
        //初始化该门店部门
    	Dept dept = new Dept();
    	dept.setId(stores.getDeptId());
    	dept.setFullname(stores.getName());
    	dept.setSimplename(stores.getName());
    	dept.setPid(30);
    	deptService.deptSetPids(dept);
        deptMapper.updateById(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除门店
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Long storesId) {
        //删除门店下的商品
        priceTagService.deleteByStoreId(storesId);
        //刪除该门店对应的部门
        Shop shop = storesService.findById(storesId);
        //缓存被删除的部门名称
        if (ToolUtil.isNotEmpty(shop.getDeptId())) {
        	LogObjectHolder.me().set(ConstantFactory.me().getDeptName(shop.getDeptId()));
            deptService.deleteDept(shop.getDeptId());
		}
        //删除门店
        storesService.delete(storesId);
        return SUCCESS_TIP;
    }

    /**
     * 获取门店的tree列表
     */
    @RequestMapping(value = "/treeByShopIds/{shopIds}")
    @ResponseBody
    public List<ZTreeNode> treeByShopIds(@PathVariable String shopIds) {
        List<ZTreeNode> tree = this.storesService.tree();
        if(ToolUtil.isEmpty(shopIds)){
        	return tree;
        }
        for (ZTreeNode node : tree) {
        	for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", shopIds))) {
        		node.setOpen(true);
        		if (node.getId().equals(id)) {
					node.setChecked(true);
				}
            }
		}
        return tree;
    }

}
