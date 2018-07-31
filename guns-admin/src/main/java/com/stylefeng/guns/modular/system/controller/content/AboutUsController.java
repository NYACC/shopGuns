package com.stylefeng.guns.modular.system.controller.content;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.md.content.model.AboutUs;
import com.md.content.service.IAboutUsService;
import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/aboutUs")
public class AboutUsController extends BaseController {

	@Resource
	IAboutUsService aboutUsService;

	private String PREFIX = "/content/aboutUs/";

	@RequestMapping("")
	public String index(Model model) {
		AboutUs aboutUs = aboutUsService.getAboutUs();
		model.addAttribute("aboutUs", aboutUs);
		return PREFIX + "aboutUs.html";
	}
	

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(AboutUs aboutUs) {
		aboutUs.setContent(HtmlUtils.htmlUnescape(aboutUs.getContent()));
		aboutUsService.editAboutUs(aboutUs);
		return SUCCESS_TIP;
	}

}
