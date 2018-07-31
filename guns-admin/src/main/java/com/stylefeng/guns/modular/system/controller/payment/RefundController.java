package com.stylefeng.guns.modular.system.controller.payment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.pay.model.RefundOrder;
import com.md.pay.service.IRefundOrderService;
import com.md.pay.warpper.RefundOrderWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiImplicitParam;

/**
 * 退款控制器
 *
 */
@Controller
@RequestMapping("/refund")
public class RefundController extends BaseController {

    private String PREFIX = "/payment/refund/";

    @Resource
    IRefundOrderService refundOrderServiceImpl;
    
    
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
    @ApiImplicitParam(name = "refundOrder", value = "付款订单信息", required = true, dataType = "refundOrder", paramType = "body")
    public Object list(RefundOrder refundOrder , Model model) {	
    	List<Map<String, Object>> refundOrders = refundOrderServiceImpl.find(refundOrder);	
    	return super.warpObject(new RefundOrderWarpper(refundOrders));
    }
}
