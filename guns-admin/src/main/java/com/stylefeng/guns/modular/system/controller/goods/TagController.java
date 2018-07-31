package com.stylefeng.guns.modular.system.controller.goods;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.md.goods.model.Tag;
import com.md.goods.service.ITagService;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.exception.GunsException;

/**
 * 标签控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/tag")
public class TagController extends BaseController {

	@Resource
	private ITagService iTagService;

	private static String PREFIX = "/goods/tag/";

	/**
	 * 跳转到查看标签列表的页面
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "tag.html";
	}

	/**
	 * 跳转到查看标签添加页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return PREFIX + "add.html";
	}

	/**
	 * 跳转到查看标签修改页面
	 */
	@RequestMapping("/toEdit/{id}")
	public String toEdit(@PathVariable Long id, Model model) {
		Tag tag = iTagService.getById(id);
		model.addAttribute("tag", tag);
		return PREFIX + "edit.html";
	}

	/**
	 * 查询标签列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(String condition) {
		Tag tag = new Tag();
		tag.setName(condition);
		List<Tag> list = iTagService.list(tag);
		return list;
	}

	/**
	 * 添加标签
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Tip add(@Valid Tag tag, BindingResult result) {
		// 验证添加的名称是否存在
		if (iTagService.checkNameExist(tag.getId(),tag.getName())) {
			throw new GunsException(BizExceptionEnum.NAME_SAME);
		}
		tag.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		iTagService.add(tag);
		return SUCCESS_TIP;
	}

	/**
	 * 删除标签
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Tip delete(@RequestParam Long tagId) {
		iTagService.delete(tagId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改标签
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Tip edit(@Valid Tag tag, BindingResult result) {
		//验证名称修改后的名称已经存在。
		if (iTagService.checkNameExist(tag.getId(), tag.getName())) {
			throw new GunsException(BizExceptionEnum.NAME_SAME);
		}
		iTagService.edit(tag);
		return SUCCESS_TIP;
	}

}
