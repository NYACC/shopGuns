package com.md.pay.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.md.order.model.Order;

import weixin.popular.bean.paymch.MchPayNotify;

@TableName("shop_payment_order")
public class PaymentOrder {

	/**
	 * 支付记录主键Id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 设备号
	 */
	private String deviceInfo;
	/**
	 * 用户标识
	 */
	private String openId;
	/**
	 * 是否关注公众账户
	 */
	private String isSubscribe;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 付款银行
	 */
	private String bankType;
	/**
	 * 订单金额
	 */
	private Integer totalFee;
	/**
	 * 应结订单金额
	 */
	private Integer settlementTotalFee;
	/**
	 * 货币种类
	 */
	private String feeType;
	/**
	 * 现金支付金额
	 */
	private Integer cashFee;
	/**
	 * 微信支付订单号
	 */
	private String transactionId;
	/**
	 * 支付完成时间
	 */
	private String timeEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Integer getCashFee() {
		return cashFee;
	}

	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public String toString() {
		return "Payment {id=" + id + ", outTradeNo=" + outTradeNo + ", deviceInfo=" + deviceInfo + ", openId=" + openId
				+ ", isSubscribe=" + isSubscribe + ", tradeType=" + tradeType + ", bankType=" + bankType + ", totalFee="
				+ totalFee + ", settlementTotalFee=" + settlementTotalFee + ", feeType=" + feeType + ", cashFee="
				+ cashFee + ", transactionId=" + transactionId + ", timeEnd=" + timeEnd + "}";
	}

	public PaymentOrder(Order order,MchPayNotify mchPayNotify) {
		super();
		this.outTradeNo = mchPayNotify.getOut_trade_no();
		this.deviceInfo = mchPayNotify.getDevice_info();
		this.openId = mchPayNotify.getOpenid();
		this.isSubscribe = mchPayNotify.getIs_subscribe();
		this.tradeType = mchPayNotify.getTrade_type();
		this.bankType = mchPayNotify.getBank_type();
		this.totalFee = mchPayNotify.getTotal_fee();
		this.settlementTotalFee = mchPayNotify.getSettlement_total_fee();
		this.feeType = mchPayNotify.getFee_type();
		this.cashFee = mchPayNotify.getCash_fee();
		this.transactionId = mchPayNotify.getTransaction_id();
		this.timeEnd = mchPayNotify.getTime_end();
	}
	
	public PaymentOrder() {
		
	}
	

}
