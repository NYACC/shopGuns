package com.stylefeng.guns.modular.system.controller.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.member.factory.MemberFactory;
import com.md.member.model.Integral;
import com.md.member.model.Member;
import com.md.member.service.IIntegralService;
import com.md.member.service.IMemberService;
import com.md.member.warpper.IntegralWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * 积分的controller
 *
 * @author 
 * @Date 
 */
@Controller
@RequestMapping("/integral")
public class IntegralController extends BaseController {
    @Resource
    IIntegralService integralService;
    @Resource
    IMemberService memberService;
    private String PREFIX = "/member/integral/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    
    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("/toEdit/{integralId}")
    public String toEdit(@PathVariable Long integralId,Model model) {
        Integral integral = integralService.getById(integralId);
        model.addAttribute(integral);
        model.addAttribute("phoneNum", MemberFactory.me().getPhoneNum(integral.getMemberId()));
        model.addAttribute("memberName",MemberFactory.me().getMemberName(integral.getMemberId()));
        return PREFIX + "edit.html";
    }
    
    /**
     * 获取积分列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String phoneNum) {
        Member member = new Member();
        member.setPhoneNum(phoneNum);
        List<Map<String,Object>> memberList = memberService.find(member);
        List<Map<String,Object>> integralList = new ArrayList<>();
        for(Map<String,Object> memberMap : memberList){
            Integral integral = new Integral();
            integral.setMemberId((Long)memberMap.get("id"));
            integralList.addAll(integralService.find(integral));
        }
        return super.warpObject(new IntegralWarpper(integralList));
    }

    /**
     * 修改积分
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Integral integral) {
        integralService.update(integral);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到积分日志页面
     * @param integral
     * @return
     */
    @RequestMapping(value = "/detail")
    public Object detail(Integral integral) {
        return PREFIX + "log.html";
    }
}
