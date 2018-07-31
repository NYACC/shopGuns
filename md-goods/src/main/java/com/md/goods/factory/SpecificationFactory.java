package com.md.goods.factory;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.md.goods.dao.SpecificationItemMapper;
import com.md.goods.model.SpecificationItem;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 品牌创建工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class SpecificationFactory {
	private SpecificationItemMapper specificationItemMapper=SpringContextHolder.getBean(SpecificationItemMapper.class);
	public static SpecificationFactory me() {
        return SpringContextHolder.getBean(SpecificationFactory.class);
    }

	/**
	 * 获取规格组的所有的规格项
	 * @param id
	 * @return
	 */
	public List<SpecificationItem> findSpecification(Long id) {
		Wrapper<SpecificationItem> wrapper = new EntityWrapper<SpecificationItem>();
		wrapper.eq("pid", id);
		wrapper.orderBy("sequence", true);
		List<SpecificationItem> selectList = specificationItemMapper.selectList(wrapper);
		return selectList;
	}

}
