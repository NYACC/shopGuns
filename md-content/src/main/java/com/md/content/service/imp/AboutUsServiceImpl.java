package com.md.content.service.imp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.md.content.dao.AboutUsMapper;
import com.md.content.model.AboutUs;
import com.md.content.service.IAboutUsService;

@Service
public class AboutUsServiceImpl extends ServiceImpl<AboutUsMapper, AboutUs> implements IAboutUsService {

	@Resource
	AboutUsMapper aboutUsMapper;

	@Override
	public AboutUs getAboutUs() {
		List<AboutUs> aboutUss = aboutUsMapper.selectList(null);
		if (aboutUss.size() > 0) {
			return aboutUss.get(0);
		}
		return new AboutUs();
	}

	@Override
	public void editAboutUs(AboutUs aboutUs) {
		aboutUs.setModifyTime(new Timestamp(new Date().getTime()));
		List<AboutUs> aboutUss = aboutUsMapper.selectList(null);
		if (aboutUss.size() > 0) {
			AboutUs editAboutUs = aboutUss.get(0);
			editAboutUs.setContent(aboutUs.getContent());
			editAboutUs.setModifyTime(aboutUs.getModifyTime());
			aboutUsMapper.updateById(editAboutUs);
		} else {
			aboutUsMapper.insert(aboutUs);
		}
	}

}
