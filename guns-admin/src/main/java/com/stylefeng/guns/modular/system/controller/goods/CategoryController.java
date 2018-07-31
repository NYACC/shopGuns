package com.stylefeng.guns.modular.system.controller.goods;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.md.goods.model.Category;
import com.md.goods.service.ICategoryService;
import com.md.goods.warpper.CategoryWarpper;
import com.stylefeng.guns.common.constant.state.CategoryStatus;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 分类控制器
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

	private String PREFIX = "/goods/category/";

	@Resource
	ICategoryService categoryService;
	@Resource
	private GunsProperties gunsProperties;

	/**
	 * 跳转到部门管理首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "category.html";
	}

	/**
	 * 获取分类的tree列表
	 */
	@RequestMapping(value = "/tree")
	@ResponseBody
	public List<ZTreeNode> tree() {
		List<ZTreeNode> tree = this.categoryService.tree();
		tree.add(ZTreeNode.createParent());
		return tree;
	}

	/**
	 * 获取分类的tree列表
	 */
	@RequestMapping(value = "/treeByCid/{goodsIds}")
	@ResponseBody
	public List<ZTreeNode> treeByCid(@PathVariable String goodsIds) {
		List<ZTreeNode> tree = this.categoryService.tree();
		tree.add(ZTreeNode.createParent());
		if (ToolUtil.isEmpty(goodsIds)) {
			return tree;
		}
		for (ZTreeNode node : tree) {
			for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", goodsIds))) {
				node.setOpen(true);
				if (node.getId().equals(id)) {
					node.setChecked(true);
				}
			}
		}
		return tree;
	}

	/**
	 * 获取分类的子分类
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(Long pid) {
		List<Map<String, Object>> findSonMaps = categoryService.findSonMaps(pid);
		return new CategoryWarpper(findSonMaps).warp();
	}

	/**
	 * 跳转到分类的添加页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		return PREFIX + "add.html";
	}

	/**
	 * 添加分类
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Tip add(@Valid Category category, BindingResult result) {
		// 验证添加的分类的父级分类是否为第三级
		Integer level = 0;
		Category pCategory = categoryService.getById(category.getPid());
		if (pCategory != null) {
			level = pCategory.getLevel();
		}
		if (level >= 3) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}
		// 验证名称、排序、父级编号是否为空
		if (ToolUtil.isOneEmpty(category.getName(), category.getNum(), category.getPid())) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}
		// 验证添加的名称是否存在
		if (categoryService.checkNameExist(category.getId(), category.getName())) {
			throw new GunsException(BizExceptionEnum.NAME_SAME);
		}
		category.setLevel(level + 1);
		category.setStatus(CategoryStatus.ENABLE.getCode());
		categoryService.add(category);
		return SUCCESS_TIP;
	}

	/**
	 * 停用分类
	 */
	@RequestMapping("/disable")
	@ResponseBody
	public Tip disable(@RequestParam Long categoryId) {
		categoryService.disable(categoryId);
		return SUCCESS_TIP;
	}

	/**
	 * 启用分类
	 */
	@RequestMapping("/enable")
	@ResponseBody
	public Tip enable(@RequestParam Long categoryId) {
		categoryService.enable(categoryId);
		return SUCCESS_TIP;
	}

	/**
	 * 跳转到查看标签修改页面
	 */
	@RequestMapping("/toEdit/{id}")
	public String toEdit(@PathVariable Long id, Model model) {
		Category byId = categoryService.getById(id);
		Category pCategory = categoryService.getById(byId.getPid());
		model.addAttribute("category", byId);
		if (pCategory != null) {
			model.addAttribute("pName", pCategory.getName());
		} else {
			model.addAttribute("pName", "顶级");
		}
		return PREFIX + "edit.html";
	}

	/**
	 * 修改分类
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Tip edit(@Valid Category category, BindingResult result) {
		// 验证添加的分类的父级分类是否为第三级
		Integer level = 0;
		Category pCategory = categoryService.getById(category.getPid());
		if (pCategory != null) {
			level = pCategory.getLevel();
		}
		if (level >= 3) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}
		// 验证名称、排序、父级编号是否为空
		if (ToolUtil.isOneEmpty(category.getId(), category.getName(), category.getNum(), category.getPid())) {
			throw new GunsException(BizExceptionEnum.REQUEST_NULL);
		}
		// 验证添加的名称是否存在
		if (categoryService.checkNameExist(category.getId(), category.getName())) {
			throw new GunsException(BizExceptionEnum.NAME_SAME);
		}
		Category byId = categoryService.getById(category.getId());
		byId.setName(category.getName());
		byId.setPid(category.getPid());
		byId.setNum(category.getNum());
		byId.setLevel(level + 1);
		categoryService.edit(category);
		return SUCCESS_TIP;
	}

	/**
	 * 上传图片
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/upload")
	@ResponseBody
	public String upload(@RequestPart("file") MultipartFile picture) {
		// 保存文件
		String filename = picture.getOriginalFilename();
    	String[] name = filename.split("\\.");
    	String suffix = name[name.length-1];
        String pictureName = UUID.randomUUID().toString() + "." + suffix;
		try {
			String fileSavePath = gunsProperties.getFileUploadPath();
			File f = new File(fileSavePath+"/categoryImg");
            if(!f.exists()){
                f.mkdirs();//创建目录
            }
            picture.transferTo(new File(fileSavePath + "categoryImg/" + pictureName));
		} catch (Exception e) {
			throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
		}
		return "categoryImg/"+pictureName;
	}
}
