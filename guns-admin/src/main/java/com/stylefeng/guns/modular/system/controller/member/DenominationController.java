package com.stylefeng.guns.modular.system.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.member.model.Denomination;
import com.md.member.service.IDenominationService;
import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * 面额的controller
 *
 * @author 
 * @Date 
 */
@Controller
@RequestMapping("/denomination")
public class DenominationController extends BaseController {

	@Resource
	IDenominationService denominationService;
	
    private String PREFIX = "/member/denomination/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    
    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd() {
        return PREFIX + "add.html";
    }
    
    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("toEdit/{denominationId}")
    public String toEdit(@PathVariable Long denominationId,Model model) {
    	Denomination denomination=denominationService.getById(denominationId);
    	model.addAttribute("denomination", denomination);
        return PREFIX + "edit.html";
    }
    
    /**
     * 获取面额列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() { 
        List<Map<String, Object>> list = denominationService.list();
    	return list;
    }
    
    /**
     * 添加面额
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Denomination denomination) { 
    	denominationService.add(denomination);
        return SUCCESS_TIP;
    }
    
    /**
     * 修改面额
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(Denomination denomination) { 
    	denominationService.update(denomination);
        return SUCCESS_TIP;
    }
    
    /**
     * 删除面额
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Long denominationId) { 
    	denominationService.delete(denominationId);
        return SUCCESS_TIP;
    }
    
    
}
