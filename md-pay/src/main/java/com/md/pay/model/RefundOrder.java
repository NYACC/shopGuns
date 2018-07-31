package com.md.pay.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.md.order.model.Order;

import weixin.popular.bean.paymch.MchPayRefundNotify;

@TableName("shop_refund_order")
public class RefundOrder {

	/**
	 * 退款记录主键Id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 微信退款单号
	 */
	private String refundId;
	/**
	 * 订单金额
	 */
	private Integer totalFee;
	/**
	 * 应结订单金额
	 */
	private Integer settlementTotalFee;
	/**
	 * 申请退款金额
	 */
	private Integer refundFee;
	/**
	 * 退款金额
	 */
	private Integer settlementRefundFee;
	/**
	 * 退款状态
	 */
	private String refundStatus;
	/**
	 * 微信支付订单号
	 */
	private String transactionId;
	/**
	 * 退款完成时间
	 */
	private String successTime;
	/**
	 * 退款资金来源
	 */
	private String refundAccount;
	/**
	 * 退款发起来源
	 */
	private String refundRequestSource;

	public RefundOrder() {

	}

	public RefundOrder(Order orderInfo, MchPayRefundNotify payNotify) {
		super();
		this.outTradeNo = payNotify.getReqInfo().getOut_trade_no();
		this.refundId = payNotify.getReqInfo().getRefund_id();
		this.totalFee = payNotify.getReqInfo().getTotal_fee();
		this.settlementTotalFee = payNotify.getReqInfo().getSettlement_total_fee();
		this.refundFee = payNotify.getReqInfo().getRefund_fee();
		this.settlementRefundFee = payNotify.getReqInfo().getSettlement_refund_fee();
		this.refundStatus = payNotify.getReqInfo().getRefund_status();
		this.transactionId = payNotify.getReqInfo().getTransaction_id();
		this.successTime = payNotify.getReqInfo().getSuccess_time();
		this.refundAccount = payNotify.getReqInfo().getRefund_account();
		this.refundRequestSource = payNotify.getReqInfo().getRefund_request_source();
	}

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

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
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

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public Integer getSettlementRefundFee() {
		return settlementRefundFee;
	}

	public void setSettlementRefundFee(Integer settlementRefundFee) {
		this.settlementRefundFee = settlementRefundFee;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}

	public String getRefundRequestSource() {
		return refundRequestSource;
	}

	public void setRefundRequestSource(String refundRequestSource) {
		this.refundRequestSource = refundRequestSource;
	}

	@Override
	public String toString() {
		return "RefundOrder {id=" + id + ", outTradeNo=" + outTradeNo + ", refundId=" + refundId + ", totalFee="
				+ totalFee + ", settlementTotalFee=" + settlementTotalFee + ", refundFee=" + refundFee
				+ ", settlementRefundFee=" + settlementRefundFee + ", refundStatus=" + refundStatus + ", transactionId="
				+ transactionId + ", successTime=" + successTime + ", refundAccount=" + refundAccount
				+ ", refundRequestSource=" + refundRequestSource + "}";
	}

}
