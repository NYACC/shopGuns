package weixin.popular.bean.card.code.deposit;

import weixin.popular.bean.BaseResult;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 投放卡券－导入code接口－响应参数
 * @author Moyq5
 * @date 2016年7月27日
 */
public class CodeDepositResult extends BaseResult {

	/**
	 * 成功个数
	 */
	@JSONField(name = "succ_code")
	private String[] succCode;
	
	/**
	 * 重复导入数
	 */
	@JSONField(name = "duplicate_code")
	private String[] duplicateCode;
	
	/**
	 * 	失败个数
	 */
	@JSONField(name = "fail_code")
	private String[] failCode;

	public String[] getSuccCode() {
		return succCode;
	}

	public void setSuccCode(String[] succCode) {
		this.succCode = succCode;
	}

	public String[] getDuplicateCode() {
		return duplicateCode;
	}

	public void setDuplicateCode(String[] duplicateCode) {
		this.duplicateCode = duplicateCode;
	}

	public String[] getFailCode() {
		return failCode;
	}

	public void setFailCode(String[] failCode) {
		this.failCode = failCode;
	}

	
}
