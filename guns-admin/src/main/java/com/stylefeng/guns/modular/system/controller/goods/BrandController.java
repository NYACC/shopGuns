package com.stylefeng.guns.modular.system.controller.goods;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.md.goods.model.Brand;
import com.md.goods.service.IBrandService;
import com.md.goods.warpper.BrandWarpper;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.CompressUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.constant.Status;
/**
 * 品牌控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/brand")
public class BrandController extends BaseController {

    private String PREFIX = "/goods/brand/";
    
    @Autowired
    private IBrandService brandService;

    @Resource
    private GunsProperties gunsProperties;
    /**
     * 跳转到品牌管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 跳转到添加品牌
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return PREFIX + "add.html";
    }

    /**
     * 跳转到修改品牌
     */
    @RequestMapping("/toEdit/{brandId}")
    public String toEdit(@PathVariable Long brandId, Model model) {
        Brand brand = brandService.findById(brandId);
        model.addAttribute(brand);
        return PREFIX + "edit.html";
    }

    /**
     * 新增品牌
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Brand brand) {
        if (ToolUtil.isOneEmpty(brand, brand.getName())) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        brand.setIntroduce(HtmlUtils.htmlUnescape(brand.getIntroduce()));
        brand.setStatus(Status.ENABLED.getCode());
        return this.brandService.insert(brand);
    }

    /**
     * 获取所有品牌列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	Brand brand=new Brand();
    	brand.setName(condition);
    	List<Map<String, Object>> brands = this.brandService.find(brand);
        return super.warpObject(new BrandWarpper(brands));
    }

    /**
     * 根据品牌编号查询品牌
     */
    @RequestMapping(value = "/detail/{brandId}")
    @ResponseBody
    public Object detail(@PathVariable("brandId") Long brandId) {
        return brandService.findById(brandId);
    }
    /**
     * 修改品牌
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(Brand brand) {
        if (ToolUtil.isEmpty(brand) || brand.getId() == null) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        brandService.update(brand);
        return SUCCESS_TIP;
    }
    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
    	String filename = picture.getOriginalFilename();
    	String[] name = filename.split("\\.");
    	String suffix = name[name.length-1];
        String pictureName = UUID.randomUUID().toString() + "." + suffix;
        try {
        	String fileSavePath = gunsProperties.getFileUploadPath();
        	File f = new File(fileSavePath+"/brandImg");
            if(!f.exists()){
                f.mkdirs();//创建目录
            }
            picture.transferTo(new File(fileSavePath + "brandImg/" + pictureName));
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return  "brandImg/" + pictureName;
    }
    @RequestMapping(method = RequestMethod.POST, path = "/ckedit")
    @ResponseBody
    public String ckeditUpload(@RequestPart("upload") MultipartFile picture,String CKEditorFuncNum) {
    	String filename = picture.getOriginalFilename();
    	String[] name = filename.split("\\.");
    	String suffix = name[name.length-1];
        String pictureName = UUID.randomUUID().toString() +"."+ suffix;
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            File f = new File(fileSavePath+"/richTextImg");
            if(!f.exists()){
                f.mkdirs();//创建目录
            }
            picture.transferTo(new File(fileSavePath + "richTextImg/" + pictureName));
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }

        String result ="<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+ CKEditorFuncNum + ",'"+gunsProperties.getProjectWebPath()+"/kaptcha/richTextImg/"+ pictureName + "','')</script>";
        return result;
    }
    /**
     * 删除品牌
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Long brandId) {
        brandService.deleteById(brandId);
        return SUCCESS_TIP;
    }
    /**
     * 禁用品牌
     */
    @RequestMapping("/disable")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip disable(@RequestParam Long brandId) {
        if (ToolUtil.isEmpty(brandId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Brand brand=this.brandService.findById(brandId);;
        brand.setStatus(Status.DISABLE.getCode());
        this.brandService.update(brand);
        return SUCCESS_TIP;

    }

    /**
     * 启用品牌
     */
    @RequestMapping("/enable")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip enable(@RequestParam Long brandId) {
        if (ToolUtil.isEmpty(brandId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Brand brand=this.brandService.findById(brandId);;
        brand.setStatus(Status.ENABLED.getCode());
        this.brandService.update(brand);
        return SUCCESS_TIP;
    }
}
