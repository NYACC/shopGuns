package com.stylefeng.guns.modular.system.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.member.model.CardLevel;
import com.md.member.service.ICardLevelService;
import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * 会员卡等级controller
 *
 * @author 
 * @Date 
 */
@Controller
@RequestMapping("/cardLevel")
public class CardLevelController extends BaseController {

	@Resource
	ICardLevelService cardLevelService;
	
    private String PREFIX = "/member/cardLevel/";

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
    @RequestMapping("toEdit/{cardLevelId}")
    public String toEdit(@PathVariable Long cardLevelId,Model model) {
    	CardLevel cardLevel=cardLevelService.getById(cardLevelId);
    	model.addAttribute("cardLevel", cardLevel);
        return PREFIX + "edit.html";
    }
    
    /**
     * 获取会员卡等级列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() { 
        List<Map<String, Object>> list = cardLevelService.list();
    	return list ;
    }
    
    /**
     * 添加会员卡等级
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(CardLevel cardLevel) { 
    	cardLevelService.add(cardLevel);
        return SUCCESS;
    }
    
    /**
     * 修改会员卡等级
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(CardLevel cardLevel) { 
    	cardLevelService.update(cardLevel);
        return SUCCESS;
    }
    
    /**
     * 删除会员卡等级
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Long cardLevelId) { 
    	cardLevelService.deleteById(cardLevelId);
        return SUCCESS;
    }
    
    
}
