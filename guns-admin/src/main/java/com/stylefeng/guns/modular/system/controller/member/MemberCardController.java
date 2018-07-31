package com.stylefeng.guns.modular.system.controller.member;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.member.factory.MemberCardFactory;
import com.md.member.factory.MemberFactory;
import com.md.member.model.Member;
import com.md.member.model.MemberCard;
import com.md.member.service.ICardLevelService;
import com.md.member.service.IMemberCardService;
import com.md.member.service.IMemberService;
import com.md.member.warpper.MemberCardWarpper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.constant.Status;
import com.stylefeng.guns.core.util.ExcelUtil;

/**
 * 会员卡的controller
 *
 * @author 
 * @Date 
 */
@Controller
@RequestMapping("/memberCard")
public class MemberCardController extends BaseController {

	@Resource
	IMemberCardService memberCardService;
    @Resource
    ICardLevelService cardLevelService;
    @Resource
    IMemberService memberService;
    private String PREFIX = "/member/memberCard/";

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("cardLevels",cardLevelService.list());
        return PREFIX + "list.html";
    }
    
    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("cardLevels",cardLevelService.list());
        return PREFIX + "add.html";
    }

    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("/toEdit/{memberCardId}")
    public String toEdit(@PathVariable Long memberCardId,Model model) {
        MemberCard memberCard = memberCardService.getMemberCard(memberCardId);
        model.addAttribute("memberCard",memberCard);
        model.addAttribute("memberName", MemberFactory.me().getMemberName(memberCard.getMemberId()));
        model.addAttribute("cardLevelName", MemberCardFactory.me().getCardlevelName(memberCard.getCardLevelId()));
        model.addAttribute("cardLevels",cardLevelService.list());
        return PREFIX + "edit.html";
    }
    
    /**
     * 获取会员卡列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(MemberCard memberCard) {
        List<Map<String, Object>> memberCards = memberCardService.find(memberCard);
        return super.warpObject(new MemberCardWarpper(memberCards));
    }
    
    /**
     * 添加会员卡
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Long cardLevel,Integer cardAmount) {
        for(int i=0;i<cardAmount;i++){
            MemberCard card = new MemberCard();
            card.setCardSn(String.valueOf(new Date().getTime()));
            card.setCardLevelId(cardLevel);
            card.setStatus(Status.DISABLE.getCode());
            memberCardService.add(card);
        }
        return SUCCESS_TIP;
    }
    
    /**
     * 修改会员卡
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(Long id,Long cardLevel,Long memberId,String memberName) {
        MemberCard memberCard =new MemberCard();
        memberCard.setId(id);
        memberCard.setCardLevelId(cardLevel);
        Member member = new Member();
        member.setId(memberId);
        member.setName(memberName);
        memberService.update(member);
        memberCardService.update(memberCard);
        return SUCCESS_TIP;
    }
    /**
     * Excel数据导出
     * @return
     */
    @RequestMapping("/memberCard_output")
    public String output(MemberCard queryData,HttpServletResponse response) throws IOException {
        System.out.println(queryData.getCardSn());
        String fileName = "会员卡数据";
        Map<String,Object> map = new HashMap<>();
        map.put("sheetName","sheet1");
        List<Map<String, Object>> memberCards =(List<Map<String, Object>>) super.warpObject(new MemberCardWarpper(memberCardService.find(queryData)));
        List<Map<String, Object>> projectsaList = new ArrayList<>();
        projectsaList.add(map);
        projectsaList.addAll(memberCards);
        String columnNames[] = { "会员卡号", "会员昵称", "会员卡等级", "会员卡状态"};// 列名
        String keys[] = { "cardSn", "memberName", "cardLevel", "status" };// map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(projectsaList, keys, columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }
}
