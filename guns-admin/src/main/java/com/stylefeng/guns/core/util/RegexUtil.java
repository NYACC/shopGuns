package com.stylefeng.guns.core.util;

/**
 * 
 * 正则表达式工具类
 * 
 * @author 包晓蓬
 *
 */
public class RegexUtil {
	
	/**
	 * 
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean numberRegex(String str){
		String regex = "[0-9]*(\\.?)[0-9]*";
		return str.matches(regex);
	}
	
	/**
	 * 
	 * 判断字符串是否是整数
	 * @param str
	 * @return
	 */
	public static boolean intRegex(String str){
		String regex = "^[0-9]*$";
		return str.matches(regex);
	}
	
	
}
