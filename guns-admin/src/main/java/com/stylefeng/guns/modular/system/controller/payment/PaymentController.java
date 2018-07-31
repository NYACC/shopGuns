package com.stylefeng.guns.modular.system.controller.payment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.order.warpper.OrderItemWarpper;
import com.md.pay.model.PaymentOrder;
import com.md.pay.service.IPaymentOrderService;
import com.md.pay.service.impl.PaymentOrderServiceImpl;
import com.md.pay.warpper.PaymentOrderWarpper;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.dictmap.DeptDict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.DeptMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.DeptDao;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.warpper.DeptWarpper;

import io.swagger.annotations.ApiImplicitParam;

/**
 * 付款控制器
 *
 */
@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    private String PREFIX = "/payment/payment/";

    @Resource
    IPaymentOrderService paymentOrderServiceImpl;
    
    
    /**
     * 跳转到部门管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    
    /**
     * 获取付款记录列表
     */
    @RequestMapping("list")
    @ResponseBody
    @ApiImplicitParam(name = "paymentOrder", value = "付款订单信息", required = true, dataType = "paymentOrder", paramType = "body")
    public Object list(PaymentOrder paymentOrder , Model model) {	
    	List<Map<String, Object>> paymentOrders = paymentOrderServiceImpl.find(paymentOrder);	
    	return super.warpObject(new PaymentOrderWarpper(paymentOrders));
    }
    
    

}
