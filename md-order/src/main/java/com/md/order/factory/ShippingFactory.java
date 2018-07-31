package com.md.order.factory;

import com.md.order.dao.ShippingDao;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发货单工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class ShippingFactory {
	private ShippingDao shippingDao=SpringContextHolder.getBean(ShippingDao.class);
	public static ShippingFactory me() {
        return SpringContextHolder.getBean(ShippingFactory.class);
    }

	/**
	 * 获取数据库最新的流水号
	 * @return
	 */
	public String getNewSn() {
		String currentTime=new SimpleDateFormat("yyyyMMdd").format(new Date());
		//获取数据库中最大的是sn码(判断是否为空)
		String maxSn=shippingDao.getMaxSn(currentTime);
		if(ToolUtil.isNotEmpty(maxSn)){
			//如果最大sn为空则为当天的第二条
			return (Long.parseLong(maxSn)+1L)+"";
		}
		return currentTime+"0001";
	}
}
