package com.stylefeng.guns.modular.system.controller.goods;

import com.alibaba.fastjson.JSONArray;
import com.md.goods.exception.GoodsExceptionEnum;
import com.md.goods.model.Category;
import com.md.goods.model.Specification;
import com.md.goods.model.SpecificationItem;
import com.md.goods.service.ICategoryService;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.ISpecificationItemService;
import com.md.goods.service.ISpecificationService;
import com.md.goods.warpper.SpecificationWarpper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 规格组控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/specification")
public class SpecificationController extends BaseController {

	@Resource
	private ISpecificationService iSpecificationService;
	@Resource
	private ISpecificationItemService iSpecificationItemService;
	@Resource
	private ICategoryService iCategoryService;
	@Resource
	private IGoodsService goodsService;

	private static String PREFIX = "/goods/specification/";

	/**
	 * 跳转到查看规格列表的页面
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "specification.html";
	}

	/**
	 * 跳转到查看规格添加页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return PREFIX + "add.html";
	}

	/**
	 * 跳转到查看规格修改页面
	 */
	@RequestMapping("/toEdit/{id}")
	public String toEdit(@PathVariable Long id, Model model) {
		Specification specification = iSpecificationService.getById(id);
		model.addAttribute("specification", specification);
		List<SpecificationItem> items= iSpecificationItemService.getByPid(id);
		model.addAttribute("items",items);
		Category category = iCategoryService.getById(specification.getCategoryId());
		model.addAttribute("categoryName",category.getName());
		return PREFIX + "edit.html";
	}

	/**
	 * 查询规格列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(String name,Long categoryId) {
		Specification specification=new Specification();
		specification.setName(name);
		specification.setCategoryId(categoryId);
		List<Map<String, Object>> specifications = iSpecificationService.findList(specification);
        return new SpecificationWarpper(specifications).warp();
	}

	/**
	 * 添加规格
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Tip add(@Valid Specification specification,String specificationValues) {
		//验证名称是否为空,绑定的分类是否存在
		if (ToolUtil.isEmpty(specification.getName())||iCategoryService.getById(specification.getCategoryId())==null) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}
		// 验证添加的名称是否存在
		if (iSpecificationService.checkNameOnCategory(specification.getCategoryId(),specification.getId(),specification.getName())) {
			throw new GunsException(BizExceptionEnum.NAME_SAME);
		}
		//将json字符转成规格项集合
		String htmlUnescape = HtmlUtils.htmlUnescape(specificationValues);
		List<SpecificationItem> parseArray = JSONArray.parseArray(htmlUnescape, SpecificationItem.class);
		HashSet<SpecificationItem> specificationSet = new HashSet<>();
		specificationSet.addAll(parseArray);
		//执行添加
		iSpecificationService.add(specification,specificationSet);
		return SUCCESS_TIP;
	}

	/**
	 * 删除规格
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Tip delete(@RequestParam Long specificationId) {
		//判断该规格项目是否被使用
		if(goodsService.specIsUse(specificationId)){
			throw new GunsException(GoodsExceptionEnum.SPEC_USED);
		}
		iSpecificationService.delete(specificationId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改规格
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Tip edit(@Valid Specification specification, String specificationValues) {
		//验证名称是否为空,绑定的分类是否存在
		if (ToolUtil.isEmpty(specification.getName())||iCategoryService.getById(specification.getCategoryId())==null) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}
		//验证名称修改后的规格组名称是否已经存在。
		if (iSpecificationService.checkNameOnCategory(specification.getCategoryId(),specification.getId(),specification.getName())) {
			throw new GunsException(BizExceptionEnum.NAME_SAME);
		}
		
		//修改规格组，判断规格是否使用
		if(goodsService.specIsUse(specification.getId())){
			Specification byId = iSpecificationService.getById(specification.getId());
			//如果已被使用，修改了名称或所属分类，是不允许的。
			if((!byId.getName().equals(specification.getName()))
					||(!byId.getCategoryId().equals(specification.getCategoryId()))){
				throw new GunsException(GoodsExceptionEnum.SPEC_USED);
			}
		}else{
			//如果没有使用，允许修改
			iSpecificationService.edit(specification);
		}
		
		//将json字符转成规格项集合
		String htmlUnescape = HtmlUtils.htmlUnescape(specificationValues);
		List<SpecificationItem> parseArray = JSONArray.parseArray(htmlUnescape, SpecificationItem.class);
		
		//修改规格项
		for (SpecificationItem specificationItem : parseArray) {
			if(!goodsService.specIsUse(specificationItem.getId())){
				iSpecificationItemService.update(specificationItem);
			}
		}
		
		return SUCCESS_TIP;
	}
	/**
	 * 实时的创建一个规格项
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public Object addItem(@RequestParam Long pid) {
		SpecificationItem specificationItem = new SpecificationItem();
		specificationItem.setPid(pid);
		Long itemId=iSpecificationItemService.add(specificationItem);
		return itemId;
	}
	
	/**
	 * 实时的删除一个规格项
	 */
	@RequestMapping("/deleteItem")
	@ResponseBody
	public Tip deleteItem(@RequestParam Long itemId) {
		//判断该规格项目是否被使用
		if(goodsService.specIsUse(itemId)){
			throw new GunsException(GoodsExceptionEnum.SPEC_USED);
		}
		iSpecificationItemService.delete(itemId);
		return SUCCESS_TIP;
	}

}
