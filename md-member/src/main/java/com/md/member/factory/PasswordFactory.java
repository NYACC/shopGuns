package com.md.member.factory;

import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.core.util.SpringContextHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 密码工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class PasswordFactory {
	public static PasswordFactory me() {
        return SpringContextHolder.getBean(PasswordFactory.class);
    }
	
	public String initPassowrd(String password){
		return MD5Util.encrypt(password);
	}
	public String initPassowrd(){
		return "88888888";
	}
	
	public String resetPassword(){
		return "88888888";
	}
}
