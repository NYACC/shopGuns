package com.md.settlement.service;

import java.util.List;
import java.util.Map;

import com.md.delivery.model.DeliveryCost;
import com.md.delivery.model.DeliveryMode;
import com.md.goods.model.PriceTag;
import com.md.member.model.Address;
import com.md.order.model.Order;
import com.md.order.model.ShopItem;

public interface IAccountService {
	/**
	 * 结算
	 * 
	 * @param shopId
	 *            购买门店
	 * @param shopItems
	 *            购买清单
	 * @param address
	 *            配送地址
	 * @param deliveryMode
	 *            配送方式
	 * @param couponCode
	 *            优惠卷码
	 * @return
	 */
	Order amount(Long shopId, List<ShopItem> shopItems, DeliveryMode deliveryMode, Address address, String couponCode);

	/**
	 * 判断库存是否充足
	 * 
	 * @param shopItems
	 * @param priceTags
	 * @return
	 */
	Boolean inventoryEnough(List<ShopItem> shopItems, Map<Long, PriceTag> priceTags);

	/**
	 * 获取购物清单的价格签
	 * 
	 * @param shopItems
	 * @return
	 */
	Map<Long, PriceTag> findPriceTag(List<ShopItem> shopItems);

	/**
	 * 获取购物清单的促销信息
	 * 
	 * @param priceTags
	 * @return
	 */
	Map<Long, List<Long>> findPromotions(Map<Long, PriceTag> priceTags);

	/**
	 * 获取配送费信息
	 */
	DeliveryCost getDeliveryCost(DeliveryMode deliveryMode, Address address, Long shopId);
}
