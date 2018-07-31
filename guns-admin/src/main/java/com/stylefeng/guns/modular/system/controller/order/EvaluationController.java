package com.stylefeng.guns.modular.system.controller.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.goods.service.IShopService;
import com.md.order.model.Evaluation;
import com.md.order.service.IEvaluationService;
import com.md.order.warpper.EvaluationWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;



/**
 * 评价控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/evaluation")
public class EvaluationController extends BaseController {

	@Resource
	IEvaluationService evaluationService;
	@Resource
	IShopService shopService;

    private String PREFIX = "/order/evaluation/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    
    /**
     * 获取评价列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Evaluation evaluation) {
    	//获取当前用户所在部门对应的门店
    	Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
    	evaluation.setShopId(shopId);
    	List<Map<String, Object>> evaluations = evaluationService.findList(evaluation);
    	return new EvaluationWarpper(evaluations).warp();
    }
    
    
}
