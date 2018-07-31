package com.md.goods.factory;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.goods.dao.CategoryMapper;
import com.md.goods.model.Category;
import com.stylefeng.guns.core.constant.Status;
import com.stylefeng.guns.core.support.StrKit;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 分类创建工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class CategoryFactory {
	private CategoryMapper categoryMapper=SpringContextHolder.getBean(CategoryMapper.class);
	public static CategoryFactory me() {
        return SpringContextHolder.getBean(CategoryFactory.class);
    }
	
	/**
     * 获取分类的状态名称
     * @param status
     * @return
     */
	public String getCategoryState(Integer status) {
		return Status.valueOf(status);
	}
	/**
	 * 获取单个分类的名称
	 * @param categoryId
	 * @return
	 */
	public String getCategoryName(Long categoryId) {
		Category category = categoryMapper.selectById(categoryId);
		if (ToolUtil.isNotEmpty(category) && ToolUtil.isNotEmpty(category.getName())) {
			return category.getName();
		}
		return "";
	}

	/**
	 * 获取最底层的分类的名称和id
	 * @param categoryIds
	 * @return
	 */
	public String getMultiplyNamesAndIds(List<Long> categoryIds){
		if(ToolUtil.isEmpty(categoryIds)){
			return null;
		}
		StringBuilder names = new StringBuilder();
		StringBuilder ids=new StringBuilder();
		for(Long id:categoryIds){
			Wrapper<Category> wrapper = new EntityWrapper<>();
			wrapper.eq("pid", id);
			Integer selectCount = categoryMapper.selectCount(wrapper);
			if (selectCount==0) {
				names.append(categoryMapper.selectById(id).getName()).append(",");
				ids.append(id+",");
			}			
		}
		String jsonStr="{\"categoryNames\":\""+StrKit.removeSuffix(names.toString(), ",")+"\",\"categoryIds\":\""+StrKit.removeSuffix(ids.toString(), ",")+"\"}";
		return jsonStr;
	}
	
	/**
	 * 获取多个子分类名称
	 * @param categoryIds
	 * @return
	 */
	public String getMultiplyName(List<Long> categoryIds){
		if(ToolUtil.isEmpty(categoryIds)){
			return null;
		}
		StringBuilder names = new StringBuilder();
		for(Long id:categoryIds){
			names.append(categoryMapper.selectById(id).getName()).append(",");
		}
		return StrKit.removeSuffix(names.toString(), ",");
	}
}
