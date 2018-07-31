package com.md.goods.factory;

import com.md.goods.dao.BrandMapper;
import com.stylefeng.guns.core.constant.Status;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 品牌创建工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class BrandFactory {
	private BrandMapper brandMapper=SpringContextHolder.getBean(BrandMapper.class);
	public static BrandFactory me() {
        return SpringContextHolder.getBean(BrandFactory.class);
    }

	/**
	 * 获取品牌状态
	 * @param status
	 * @return
	 */
	public String getBrandStatusName(Integer status) {
		return Status.valueOf(status);
	}
	public String getBrandName(Long brandId){
		if(ToolUtil.isEmpty(brandId)){
			return null;
		}
		return brandMapper.selectById(brandId).getName();
	}
}
