package com.md.content.service;

import com.baomidou.mybatisplus.service.IService;
import com.md.content.model.AboutUs;

public interface IAboutUsService extends IService<AboutUs>{

	/**
	 * 获取关于我们
	 * @return
	 */
	AboutUs getAboutUs();
	
	/**
	 * 修改关于我们
	 */
	void editAboutUs(AboutUs aboutUs);
}
