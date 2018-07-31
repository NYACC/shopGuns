package com.md.order.factory;

import com.md.goods.dao.ShopMapper;
import com.md.goods.model.Shop;
import com.md.member.dao.MemberMapper;
import com.md.order.dao.OrderDao;
import com.md.order.dao.OrderMapper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class OrderFactory {
	private OrderDao orderDao=SpringContextHolder.getBean(OrderDao.class);
	private ShopMapper shopMapper = SpringContextHolder.getBean(ShopMapper.class);
	private MemberMapper memberMapper=SpringContextHolder.getBean(MemberMapper.class);
	private OrderMapper orderMapper = SpringContextHolder.getBean(OrderMapper.class);
	public static OrderFactory me() {
        return SpringContextHolder.getBean(OrderFactory.class);
    }

	/**
	 * 获取数据库最新的流水号
	 * @return
	 */
	public String getNewSn() {
		String currentTime=new SimpleDateFormat("yyyyMMdd").format(new Date());
		//获取数据库中最大的是sn码(判断是否为空)
		String maxSn=orderDao.getMaxSn(currentTime);
		if(ToolUtil.isNotEmpty(maxSn)){
			//如果最大sn为空则为当天的第二条
			return (Long.parseLong(maxSn)+1L)+"";
		}
		return currentTime+"0001";
	}
	public String getShopName(Long shopId){
		Shop shop = shopMapper.selectById(shopId);
		if(ToolUtil.isEmpty(shop)){
			return null;
		}
		return shop.getName();
	}
	public String getCustomerName(Long customerId){
		if(ToolUtil.isEmpty(customerId)){
			return null;
		}
		return memberMapper.selectById(customerId).getName();
	}
	
	public String getOrderSn(Long orderId){
		if(ToolUtil.isEmpty(orderId)){
			return null;
		}
		return orderMapper.selectById(orderId).getSn();
	}
}
