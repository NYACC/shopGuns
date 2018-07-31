package com.md.order.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("shop_refund_apply")
public class RefundApply {
	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户id
	 */
	private Long memberId;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 申请原因
	 */
	private String applyWhy;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private String createTime;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getApplyWhy() {
		return applyWhy;
	}

	public void setApplyWhy(String applyWhy) {
		this.applyWhy = applyWhy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "RefundApply {id=" + id + ", memberId=" + memberId + ", orderId=" + orderId
				+ ", applyWhy=" + applyWhy + ", status=" + status + ", createTime=" + createTime + "}";
	}

	

}
