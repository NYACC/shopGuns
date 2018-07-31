package com.md.settlement.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.md.coupon.constant.CodeStatus;
import com.md.coupon.constant.PromotionModel;
import com.md.coupon.model.Coupon;
import com.md.coupon.model.CouponCode;
import com.md.coupon.model.Promotion;
import com.md.coupon.service.ICouponCodeService;
import com.md.coupon.service.ICouponService;
import com.md.coupon.service.IPromotionPriceTagService;
import com.md.coupon.service.IPromotionService;
import com.md.delivery.model.DeliveryCost;
import com.md.delivery.model.DeliveryMode;
import com.md.delivery.service.IDeliveryCostService;
import com.md.goods.model.PriceTag;
import com.md.goods.model.Product;
import com.md.goods.model.Shop;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.IShopService;
import com.md.member.model.Address;
import com.md.order.model.Order;
import com.md.order.model.OrderItem;
import com.md.order.model.ShopItem;
import com.md.settlement.exception.SettlementExceptionEnum;
import com.md.settlement.service.IAccountService;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.ToolUtil;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

	@Resource
	IPriceTagService priceTagService;
	@Resource
	IPromotionPriceTagService promotionPriceTagService;
	@Resource
	IPromotionService promotionService;
	@Resource
	IDeliveryCostService deliveryCostService;
	@Resource
	IProductService productService;
	@Resource
	IShopService shopService;
	@Resource
	ICouponCodeService couponCodeService;
	@Resource
	ICouponService couponService;

	@Override
	public Order amount(Long shopId, List<ShopItem> shopItems, DeliveryMode deliveryMode, Address address,
			String couponCode) {
		// 获取购买清单的价格签
		Map<Long, PriceTag> priceTags = this.findPriceTag(shopItems);
		Set<OrderItem> orderItems = new HashSet<>();
		// 普通结算
		for (ShopItem shopItem : shopItems) {
			OrderItem orderItem = new OrderItem(shopItem, priceTags.get(shopItem.getProductId()));
			orderItem.setProduct(productService.findById(shopItem.getProductId()));
			orderItems.add(orderItem);
		}

		// 获取购买清单的促销分组信息
		Map<Long, List<Long>> promotions = this.findPromotions(priceTags);
		// 促销结算
		if (ToolUtil.isNotEmpty(promotions)) {
			orderItems = this.promotionSattlement(orderItems, promotions);
		}

		Order order = new Order(orderItems);
		order.setShop(shopService.findById(shopId));
		order.setShopId(shopId);

		// 优惠卷结算
		if (ToolUtil.isEmpty(promotions) && ToolUtil.isNotEmpty(couponCode)) {
			order = this.couponSattlement(order, couponCode);
		}

		// 配送结算
		if (ToolUtil.isNotEmpty(deliveryMode.getId()) && ToolUtil.isNotEmpty(address.getId())) {
			order = this.deliverySattlement(order, deliveryMode, address, shopId);
		}
		return order;
	}

	@Override
	public Boolean inventoryEnough(List<ShopItem> shopItems, Map<Long, PriceTag> priceTags) {
		Boolean flag = true;
		for (ShopItem shopItem : shopItems) {
			// 判断库存是否大于购买数量
			if (shopItem.getQuantity() > priceTags.get(shopItem.getProductId()).getInventory()) {
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public Map<Long, PriceTag> findPriceTag(List<ShopItem> shopItems) {
		Map<Long, PriceTag> priceTags = new HashMap<>();
		shopItems.stream().forEach(shopItem -> {
			PriceTag priceTag = new PriceTag();
			priceTag.setShopId(shopItem.getShopId());
			priceTag.setProductId(shopItem.getProductId());
			priceTag = priceTagService.findOne(priceTag);
			priceTags.put(shopItem.getProductId(), priceTag);
		});
		return priceTags;
	}

	@Override
	public Map<Long, List<Long>> findPromotions(Map<Long, PriceTag> priceTags) {
		Map<Long, List<Long>> promotions = new HashMap<>();
		for (PriceTag priceTag : priceTags.values()) {
			// 获取该商品参与的所有促销
			List<Long> promotionIds = promotionPriceTagService.findPromotionIds(priceTag.getId());
			// 将促销信息分组
			for (Long promotionId : promotionIds) {
				List<Long> ids = new ArrayList<>();
				Promotion promotion = promotionService.getById(promotionId);
				// 判断该促销是否正在进行
				Timestamp nowTime = new Timestamp(new Date().getTime());
				if (promotion.getStartTime().getTime() < nowTime.getTime()
						&& promotion.getEndTime().getTime() > nowTime.getTime()) {
					ids.add(priceTag.getId());
					if (promotions.containsKey(promotionId)) {
						ids.addAll(promotions.get(promotionId));

					}
					promotions.put(promotionId, ids);
				}

			}
		}
		return promotions;
	}

	public Set<OrderItem> promotionSattlement(Set<OrderItem> orderItems, Map<Long, List<Long>> promotions) {
		// 遍历每一个促销，对每个促销下的商品进行优惠
		for (Long promotionId : promotions.keySet()) {
			Promotion promotion = promotionService.getById(promotionId);
			// 判断该促销是否是满减
			if (promotion.getModel() == PromotionModel.REDUCE.getCode()) {
				List<Long> ids = promotions.get(promotionId);
				// 获取该促销下的商品的总价格，总件数
				BigDecimal sum = BigDecimal.ZERO;
				Integer quantity = 0;
				for (OrderItem orderItem : orderItems) {
					if (ids.contains(orderItem.getPriceTag().getId())) {
						sum = sum.add(orderItem.getAmount());
						quantity += orderItem.getQuantity();
					}
				}
				// 如果达到满减的价格，修改订单信息
				if (sum.compareTo(promotion.getFulfil()) >= 0) {
					// 计算每一件商品得到优惠价格
					BigDecimal reduce = promotion.getReduce().divide(new BigDecimal(quantity));
					// 将优惠价格分配到每一件商品上面
					for (OrderItem orderItem : orderItems) {
						if (ids.contains(orderItem.getPriceTag().getId())) {
							orderItem.setActualPrice(orderItem.getActualPrice().subtract(reduce));
							orderItem.setAmount(
									orderItem.getActualPrice().multiply(new BigDecimal(orderItem.getQuantity()))
											.setScale(2, BigDecimal.ROUND_HALF_UP));
						}
						orderItem.setPromotionId(promotionId);
					}
				}
			}

			// 判断促销是否是折扣
			if (promotion.getModel() == PromotionModel.DISCOUNT.getCode()) {
				List<Long> ids = promotions.get(promotionId);
				// 对每一件商品进行折扣
				for (OrderItem orderItem : orderItems) {
					if (ids.contains(orderItem.getPriceTag().getId())) {
						orderItem.setActualPrice(orderItem.getActualPrice().multiply(promotion.getDiscount()));
						orderItem.setAmount(orderItem.getActualPrice().multiply(new BigDecimal(orderItem.getQuantity()))
								.setScale(2, BigDecimal.ROUND_HALF_UP));
						orderItem.setPromotionId(promotionId);
					}
				}
			}
		}
		return orderItems;
	}

	@Override
	public DeliveryCost getDeliveryCost(DeliveryMode deliveryMode, Address address, Long shopId) {
		Shop shop = shopService.findById(shopId);
		DeliveryCost cost = deliveryCostService.getCost(deliveryMode.getId(), address.getCounty(), shop.getCountyId());
		/*if (cost == null) {
			throw new GunsException(SettlementExceptionEnum.NO_DELIVERYCOST);
		} else {
			return cost;
		}*/
		return cost;
	}

	public Order deliverySattlement(Order order, DeliveryMode deliveryMode, Address address, Long shopId) {
		// 获取配送费对象
		DeliveryCost deliveryCost = this.getDeliveryCost(deliveryMode, address, shopId);
		// 计算出订单的总重量
		for (OrderItem orderItem : order.getOrderItems()) {
			Product product = productService.findById(orderItem.getProductId());
			order.setWeight(order.getWeight().add(product.getWeight().multiply(new BigDecimal(orderItem.getQuantity())))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		// 计算运费
		if (order.getWeight().compareTo(deliveryCost.getYkg()) <= 0) {
			order.setDiliveryPay(deliveryCost.getStartPrice());
		} else {
			BigDecimal overstepWeight = order.getWeight().subtract(deliveryCost.getYkg());
			BigDecimal overStepPrice = new BigDecimal(
					Math.ceil(overstepWeight.divide(deliveryCost.getAddedWeight()).doubleValue()))
							.multiply(deliveryCost.getAddedPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			order.setDiliveryPay(deliveryCost.getStartPrice().add(overStepPrice));
		}
		order.setActualPay(order.getActualPay().add(order.getDiliveryPay()));
		order.setConsigneeName(address.getConsigeeName());
		order.setAddress(address.getAddressName() + address.getDetail());
		order.setPhoneNum(address.getPhone());
		order.setDiliveryId(deliveryMode.getId());
		return order;
	}

	public Order couponSattlement(Order order, String code) {
		// 获取优惠码
		CouponCode couponCode = couponCodeService.getByCode(code);
		if (ToolUtil.isNotEmpty(code)) {
			// 判断优惠码是否使用过
			if (couponCode.getStatus().equals(CodeStatus.USED)) {
				throw new GunsException(SettlementExceptionEnum.COUPONCODE_USED);
			}
			Coupon coupon = couponService.getById(couponCode.getCouponId());
			// 判断该优惠卷是否过期
			Date date = new Date();
			if (!(coupon.getUseStart().before(date) && coupon.getUseEnd().after(date))) {
				throw new GunsException(SettlementExceptionEnum.COUPON_OVERDUE);
			}
			// 判断订单是否符合该优惠满减金额
			if (order.getActualPay().compareTo(coupon.getFulfil()) >= 0) {
				order.setDue(order.getDue().subtract(coupon.getReduce()));
				order.setCouponId(coupon.getId());
			}
		}
		return order;
	}

}
