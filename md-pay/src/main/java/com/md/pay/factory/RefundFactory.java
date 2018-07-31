package com.md.pay.factory;

import java.math.BigDecimal;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 客户创建工厂
 *
 * @author
 * @date
 */
@Component
@DependsOn("springContextHolder")
public class RefundFactory {

	public static RefundFactory me() {
		return SpringContextHolder.getBean(RefundFactory.class);
	}

	public BigDecimal fenConvertYuan(Integer fen) {
		return new BigDecimal(fen).divide(new BigDecimal(100));
	}
}
