package com.stylefeng.guns.modular.system.controller.payment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.order.model.Order;
import com.md.order.model.RefundApply;
import com.md.order.service.IOrderService;
import com.md.order.service.IRefundApplyService;
import com.md.order.warpper.RefundApplyWarpper;
import com.md.pay.model.PaymentOrder;
import com.md.pay.service.IPaymentOrderService;
import com.md.pay.service.IRefundOrderService;
import com.md.pay.service.IwxPayService;
import com.md.pay.warpper.RefundOrderWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;

import io.swagger.annotations.ApiImplicitParam;
import weixin.popular.bean.paymch.SecapiPayRefundResult;

/**
 * 退款申请控制器
 *
 */
@Controller
@RequestMapping("/refundApply")
public class RefundApplyController extends BaseController {

    private String PREFIX = "/payment/refundApply/";

    @Resource
    IRefundApplyService refundApplyServiceImpl;
    @Resource
    IOrderService orderServiceImpl;
    @Resource
    IRefundOrderService refundOrderServiceImpl;
    @Resource
    IwxPayService wxPayServiceImpl;
    @Resource
    IPaymentOrderService paymentOrderServiceImpl;
    
    /**
     * 跳转管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    
    /**
     * 获取退款申请记录列表
     */
    @RequestMapping("list")
    @ResponseBody
    @ApiImplicitParam(name = "refundApply", value = "付款订单信息", required = true, dataType = "refundApply", paramType = "body")
    public Object list(RefundApply refundApply , Model model) {	
    	List<Map<String, Object>> refundApplys = refundApplyServiceImpl.find(refundApply);	
    	return super.warpObject(new RefundApplyWarpper(refundApplys));
    }
    
    /**
     * 发起退款
     */
    @RequestMapping("refund")
    @ResponseBody
    public Object refund(Long orderId,String money) {
    	JSONObject jb = new JSONObject();
    	Order order = orderServiceImpl.selectById(orderId);
    	PaymentOrder paymentOrder = paymentOrderServiceImpl.selectByOrdersn(order.getSn());
    	SecapiPayRefundResult secapiPayRefundResult = wxPayServiceImpl.wxPayRefund(order, paymentOrder, money);
    	jb.put("code", secapiPayRefundResult.getReturn_code());
		jb.put("msg", secapiPayRefundResult.getReturn_msg());
		return jb;
    }
}
