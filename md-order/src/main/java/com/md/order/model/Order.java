package com.md.order.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.md.goods.model.Shop;
import com.md.member.model.Member;
import com.md.order.constant.OrderStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@TableName("shop_order")
public class Order {
	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	// 流水号
	private String sn;
	// 日期
	private Timestamp createTime;
	// 应付款
	private BigDecimal due;
	// 实付款
	private BigDecimal actualPay;
	// 总件数
	private Integer packages;
	// 支付方式
	private String payment;
	// 顾客(顾客对象id)
	private Long memberId;
	// 会员卡号
	private String vipCardId;
	// 所属门店(门店对象id)
	private Long shopId;
	// 开票号
	private Long billId;
	// 二维码(二维码对象)
	@TableField(exist = false)
	private QRCode qrCode;
	// 配送方式(配送对象)
	private Long diliveryId;
	// 使用卡卷
	private Long couponId;
	// 总重量
	private BigDecimal weight = BigDecimal.ZERO;
	// 总运费
	private BigDecimal diliveryPay;
	// 收货人名称
	private String consigneeName;
	// 收货人地址
	private String address;
	// 收货人联系方式
	private String phoneNum;
	// 备注
	private String remark;
	// 状态（待付款、待审核、待发货、待收货、完成、关闭）
	private Integer status;
	// 所属门店
	@TableField(exist = false)
	private Shop shop;
	// 所属用户
	@TableField(exist = false)
	private Member member;
	// 明细(订单明细对象)
	@TableField(exist = false)
	private Set<OrderItem> orderItems;
	// 自定义明细(订单明细对象)
	@TableField(exist = false)
	private Object itemObject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getDue() {
		return due;
	}

	public void setDue(BigDecimal due) {
		this.due = due;
	}

	public BigDecimal getActualPay() {
		return actualPay;
	}

	public void setActualPay(BigDecimal actualPay) {
		this.actualPay = actualPay;
	}

	public Integer getPackages() {
		return packages;
	}

	public void setPackages(Integer packages) {
		this.packages = packages;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getVipCardId() {
		return vipCardId;
	}

	public void setVipCardId(String vipCardId) {
		this.vipCardId = vipCardId;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
		this.shopId = shop.getId();
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public QRCode getQrCode() {
		return qrCode;
	}

	public void setQrCode(QRCode qrCode) {
		this.qrCode = qrCode;
	}

	public BigDecimal getDiliveryPay() {
		return diliveryPay;
	}

	public void setDiliveryPay(BigDecimal diliveryPay) {
		this.diliveryPay = diliveryPay;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
		this.memberId = member.getId();
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getDiliveryId() {
		return diliveryId;
	}

	public void setDiliveryId(Long diliveryId) {
		this.diliveryId = diliveryId;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Order() {
		super();
	}

	public Order(Set<OrderItem> items) {
		super();
		this.due = BigDecimal.ZERO;
		this.actualPay = BigDecimal.ZERO;
		this.packages = 0;
		this.orderItems = items;
		this.createTime = new Timestamp(new Date().getTime());
		this.status = OrderStatus.OBLIGATION.getCode();
		for (OrderItem orderItem : items) {
			this.due = this.due.add(orderItem.getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity()))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			this.actualPay = this.actualPay.add(orderItem.getAmount());
			this.packages += orderItem.getQuantity();
		}
	}

	public Object getItemObject() {
		return itemObject;
	}

	public void setItemObject(Object itemObject) {
		this.itemObject = itemObject;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

}
