package com.stylefeng.guns.modular.system.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.cart.service.ICartService;
import com.md.member.factory.PasswordFactory;
import com.md.member.model.Balance;
import com.md.member.model.Integral;
import com.md.member.model.Member;
import com.md.member.model.MemberCard;
import com.md.member.service.IBalanceService;
import com.md.member.service.ICardLevelService;
import com.md.member.service.IFavoriteService;
import com.md.member.service.IIntegralService;
import com.md.member.service.IMemberCardService;
import com.md.member.service.IMemberService;
import com.md.member.warpper.MemberWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.constant.Status;

/**
 * 客户的controller
 *
 * @author 
 * @Date 
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Resource
	IMemberService memberService;
	@Resource
	ICardLevelService cardLevelService;
	@Resource
	IMemberCardService memberCardService;
	@Resource
	IIntegralService integralService;
	@Resource
	IBalanceService balanceService;
	@Resource
	ICartService cartService;
	@Resource
	IFavoriteService favoriteService;
	
    private String PREFIX = "/member/member/";

    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }
    
    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) {
    	model.addAttribute("cardLevels",cardLevelService.list());
        return PREFIX + "add.html";
    }
    
    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("toEdit/{memberId}")
    public String toEdit(@PathVariable Long memberId,Model model) {
    	Member member=memberService.findById(memberId);
    	model.addAttribute("member",member);
        return PREFIX + "edit.html";
    }
    
    /**
     * 获取客户列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Member member) { 
        List<Map<String, Object>> members = memberService.find(member);
        return super.warpObject(new MemberWarpper(members));
    }
    
    /**
     * 添加客户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Member member,Boolean isCard,Long cardLevel) { 
    	//设置初始化密码
    	member.setPassword(PasswordFactory.me().initPassowrd());
        memberService.add(member);
        //判断是否分配会员卡
    	if(isCard){
    		MemberCard card = memberCardService.init(member,cardLevel);
    		member.setCardSn(card.getCardSn());
    	}
    	//分配积分账户
    	Integral integral =integralService.init(member);
    	member.setIntegralSn(integral.getSn());
    	//分配余额账户
    	Balance balance =balanceService.init(member);
    	member.setBalanceSn(balance.getBalanceSn());
    	//分配一辆购物车
    	cartService.init(member);
    	//分配一个收藏夹
    	favoriteService.init(member);
    	memberService.update(member);
        return SUCCESS_TIP;
    }
    
    /**
     * 修改客户
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(Member member) { 
        memberService.update(member);
        return SUCCESS_TIP;
    }
    
    /**
     * 启用客户
     */
    @RequestMapping(value = "/enable")
    @ResponseBody
    public Object enable(Long memberId) { 
    	Member member = memberService.findById(memberId);
    	member.setStatus(Status.ENABLED.getCode());
        memberService.update(member);
        return SUCCESS;
    }
    
    /**
     * 停用客户
     */
    @RequestMapping(value = "/disable")
    @ResponseBody
    public Object disable(Long memberId) { 
    	Member member = memberService.findById(memberId);
    	member.setStatus(Status.DISABLE.getCode());
        memberService.update(member);
        return SUCCESS_TIP;
    }
    
    /**
     * 重置密码
     */
    @RequestMapping(value = "/resetPwd")
    @ResponseBody
    public Object resetPwd(Long memberId) { 
    	Member member = memberService.findById(memberId);
    	member.setPassword(PasswordFactory.me().resetPassword());
        memberService.update(member);
        return SUCCESS_TIP;
    }
}
