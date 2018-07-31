package com.md.goods.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 标签创建工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class TagFactory {
	//private TagMapper tagMapper=SpringContextHolder.getBean(TagMapper.class);
	public static TagFactory me() {
        return SpringContextHolder.getBean(TagFactory.class);
    }

}
