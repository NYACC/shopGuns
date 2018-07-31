package com.md.goods.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.md.goods.dao.UploadFileMapper;
import com.md.goods.model.UploadFile;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**
 * 品牌创建工厂
 *
 * @author 
 * @date 
 */
@Component
@DependsOn("springContextHolder")
public class UploadFactory {
	private UploadFileMapper uploadFileMapper=SpringContextHolder.getBean(UploadFileMapper.class);
	public static UploadFactory me() {
        return SpringContextHolder.getBean(UploadFactory.class);
    }

	/**
	 * 获取图片地址
	 * @param id
	 * @return
	 */
	public String getUploadFileUrl(Long id) {
		if(id!=null){
			UploadFile selectById = uploadFileMapper.selectById(id);
			return selectById.getUrl();
		}
		return "/kaptcha/girl.gif";
	}

	/**
	 * 获取商品图图片地址
	 * @param id
	 * @return
	 */
	public String getAllUploadFileUrl(String images) {
		String imageUrl = "";
		if(images!=null){
			String[] arr = images.split(",");
			for (int i = 0; i < arr.length; i++) {
				if(!arr[i].equals("")) {
					imageUrl += getUploadFileUrl(Long.parseLong(arr[i])) + ",";
				}
			}
		}
		return imageUrl;
	}
}
