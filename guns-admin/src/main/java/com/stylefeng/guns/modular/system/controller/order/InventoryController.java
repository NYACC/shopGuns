package com.stylefeng.guns.modular.system.controller.order;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONArray;
import com.md.goods.factory.GoodsFactory;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.Shop;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.goods.warpper.PriceTagWarpper;
import com.md.goods.warpper.ProductWarpper;
import com.md.order.constant.InventoryType;
import com.md.order.factory.InventoryFactory;
import com.md.order.model.Inventory;
import com.md.order.service.IInventoryService;
import com.md.order.warpper.InventoryWarpper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.service.IUserService;




/**
 * 库存控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/inventory")
public class InventoryController extends BaseController {
	@Resource
	IProductService productService;
	@Resource
	IPriceTagService priceTagService;
    @Resource
    IInventoryService iInventoryService;
    @Resource
    IShopService shopService;
    @Resource
    IUserService userService;
    private String PREFIX = "/order/inventory/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    /**
     * 获取订单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Inventory inventory, String shopName, String operater, String startTime, String endTime) {
    	//获取当前用户所在部门对应的门店
    	Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
        //获取操作员编号集合
        User user = new User();
        user.setName(operater);
        List<Map<String, Object>> users = userService.find(user);
        List<Integer> operatorIds = new ArrayList<>();
        users.stream().forEach(u->{
            operatorIds.add((Integer) u.get("id"));
        });
        //转换时间
        Timestamp startDate = null;
        Timestamp endDate = null;
        if(ToolUtil.isNotEmpty(startTime)&&ToolUtil.isNotEmpty(endTime)){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date start = formatter.parse(startTime,pos);
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos1 = new ParsePosition(0);
            Date end = formatter1.parse(endTime,pos1);
            startDate = new Timestamp(start.getTime());
            endDate = new Timestamp(end.getTime());
        }
        List<Map<String,Object>> inventoryList = iInventoryService.find(inventory,shopId,operatorIds,startDate,endDate);
        return super.warpObject(new InventoryWarpper(inventoryList));
    }
    
    @RequestMapping("/toInventory/{type}")
    public String toInventory(@PathVariable Integer type, Model model) {
        model.addAttribute("type",type);
        model.addAttribute("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return PREFIX + "inventory.html";
    }
    
    @RequestMapping(value = "/findProduct/{key}")
    @ResponseBody
    public Object findProduct(@PathVariable String key) {
    	List<Map<String, Object>> products = productService.findByBarcode(key);
        return super.warpObject(new ProductWarpper(products));
    }
    
    @RequestMapping(value = "/findPriceTag")
    @ResponseBody
    public Object findProduct(Long productId) {
    	if (ToolUtil.isEmpty(productId)) {
    		return null;
		}
    	//获取当前用户所在部门对应的门店
    	Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
    	PriceTag priceTag=new PriceTag();
    	priceTag.setShopId(shopId);
    	priceTag.setProductId(productId);
    	List<Map<String, Object>> priceTags = priceTagService.findList(priceTag);
    	return super.warpObject(new PriceTagWarpper(priceTags));
    }

    /**
     * 跳转到详情页面
     * @return
     */
    @RequestMapping("/detail/{inventoryId}")
    public String detail(@PathVariable Long inventoryId, Model model) {
        Inventory inventory = iInventoryService.getById(inventoryId);
        model.addAttribute("inventory",inventory);
        Shop shop = shopService.findById(inventory.getShopId());
        model.addAttribute("shop",shop);
        User user = userService.getById(inventory.getOperatorId());
        model.addAttribute("user",user);
        return PREFIX + "detail.html";
    }
    @RequestMapping("/add")
    @ResponseBody
    public String add(String inventorys,Long productId) {
        String htmlUnescape = HtmlUtils.htmlUnescape(inventorys);
        List<Inventory> inventoryList = JSONArray.parseArray(htmlUnescape, Inventory.class);
        inventoryList.stream().filter(inventory->inventory.getAmount()!=0).forEach(inventory->{
            inventory.setSn(InventoryFactory.me().getNewSn());
            iInventoryService.add(inventory);
            if(InventoryType.OUTPUT.getCode()==inventory.getType()){
                priceTagService.reduceInventory(productId,inventory.getShopId(),inventory.getAmount());
            }
            if(InventoryType.INPUT.getCode()==inventory.getType()){
                priceTagService.addInventory(productId,inventory.getShopId(),inventory.getAmount());
            }
        });
        return SUCCESS;
    }
    @RequestMapping("/getGoodsName")
    @ResponseBody
    public String getGoodsName(Long productId){
        Product product = productService.findById(productId);
        return GoodsFactory.me().getNameById(product.getGoodsId())+product.getName();
    }
}
