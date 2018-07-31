package com.stylefeng.guns.modular.system.controller.member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.member.model.Balance;
import com.md.member.model.Member;
import com.md.member.service.IBalanceService;
import com.md.member.service.IDenominationService;
import com.md.member.service.IMemberService;
import com.md.member.warpper.BalanceWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * 余额的controller
 *
 * @author 
 * @Date 
 */
@Controller
@RequestMapping("/balance")
public class BalanceController extends BaseController {

	@Resource 
	IBalanceService balanceService;
	@Resource
	IMemberService memberService;
	@Resource
	IDenominationService denominationService;
	
    private String PREFIX = "/member/balance/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    
    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("balance_recharge/{balanceId}")
    public String balance_recharge(@PathVariable Long balanceId,Model model) {
    	List<Map<String, Object>> denominations = denominationService.list();
    	Balance balance = balanceService.getById(balanceId);
    	model.addAttribute("denominations",denominations);
    	model.addAttribute("balance", balance);
    	model.addAttribute("member",memberService.findById(balance.getMemberId()));
        return PREFIX + "recharge.html";
    }
    
    /**
     * 获取余额账户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String phoneNum) { 
    	//根据电话号码找到用户
    	Member member = new Member();
    	member.setPhoneNum(phoneNum);
    	List<Map<String, Object>> members = memberService.find(member);
    	//根据用户找到余额账户
    	List<Map<String,Object>> balances = new ArrayList<>();
        for(Map<String,Object> memberMap : members){
        	Balance balance=new Balance();
        	balance.setMemberId((Long)memberMap.get("id"));
            balances.addAll(balanceService.find(balance));
        }
        return super.warpObject(new BalanceWarpper(balances));
    }
    
    /**
     * 充值
     */
    @RequestMapping(value = "/recharge")
    @ResponseBody
    public Object recharge(Balance balance,BigDecimal money) { 
        balanceService.recharge(balance, money);
        return SUCCESS;
    }
}
