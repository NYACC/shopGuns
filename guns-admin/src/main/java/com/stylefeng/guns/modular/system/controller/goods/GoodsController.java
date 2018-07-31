package com.stylefeng.guns.modular.system.controller.goods;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.md.goods.constant.IsUse;
import com.md.goods.factory.CategoryFactory;
import com.md.goods.factory.GoodsFactory;
import com.md.goods.factory.PriceTagFactory;
import com.md.goods.model.Brand;
import com.md.goods.model.CategoryRelation;
import com.md.goods.model.Goods;
import com.md.goods.model.Param;
import com.md.goods.model.ParamItems;
import com.md.goods.model.Product;
import com.md.goods.model.Specification;
import com.md.goods.model.SpecificationItem;
import com.md.goods.model.Tag;
import com.md.goods.model.TagRelation;
import com.md.goods.model.UploadFile;
import com.md.goods.service.IBrandService;
import com.md.goods.service.ICategoryRelationService;
import com.md.goods.service.IGoodsService;
import com.md.goods.service.IParamItemsService;
import com.md.goods.service.IParamService;
import com.md.goods.service.IPriceTagService;
import com.md.goods.service.IProductService;
import com.md.goods.service.ISpecificationItemService;
import com.md.goods.service.ISpecificationService;
import com.md.goods.service.ITagRelationService;
import com.md.goods.service.ITagService;
import com.md.goods.service.IUploadFileService;
import com.md.goods.warpper.GoodsWarpper;
import com.md.goods.warpper.ProductWarpper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.CompressUtil;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;



/**
 * 商品控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    private String PREFIX = "/goods/goods/";

    @Resource
    IGoodsService goodsService;
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    IBrandService brandService;
    @Resource
    ITagService tagService;
    @Resource
    ISpecificationService specificationService;
    @Resource
    ISpecificationItemService specificationItemService;
    @Resource
    IParamService paramService;
    @Resource
    IParamItemsService paramItemsService;
    @Resource
    IUploadFileService uploadFileService;
    @Resource
    IProductService productService;
    @Resource
    ICategoryRelationService categoryRelationService;
    @Resource
    ITagRelationService tagRelationService;
    @Resource
    IPriceTagService priceTagService;
    
    /**
     * 跳转到商品管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 跳转到添加商品
     */
    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("sn",goodsService.getNewSn());
        model.addAttribute("brandList",getBrandList());
        model.addAttribute("tagList",tagService.list(new Tag()));
        return PREFIX + "add.html";
    }
    
    /**
     * 跳转到商品上传的页面
     */
    @RequestMapping(value = "/goods_upload")
    public String goods_upload() {
        return PREFIX + "upload.html";
    }
    
    /**
     * 获取商品的图片组
     */
    @RequestMapping(value = "/imgLoad")
    @ResponseBody
    public List<UploadFile> imgLoad(@RequestParam("imgIds") String imgIds) {
    	List<UploadFile> uploadFiles = new ArrayList<>();
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", imgIds))) {
        	UploadFile uploadFile= uploadFileService.getById(id);
        	uploadFiles.add(uploadFile);
        }
    	return uploadFiles;
    }
    
    
    /**
     * 获取所有品牌
     * @return
     */
    public List<Brand> getBrandList(){
        List<Map<String,Object>> list=brandService.find(new Brand());
        List<Brand> brandList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Brand brand=new Brand();
            brand.setId((Long)map.get("id"));
            brand.setName((String)map.get("name"));
            brandList.add(brand);
        }
        return brandList;
    }
    /**
     * 跳转到修改商品
     */
    @RequestMapping("/toEdit/{goodsId}")
    public String toEdit(@PathVariable Long goodsId, Model model) {
        //回填品牌
        model.addAttribute("brandList",getBrandList());
        //回填分类
        model.addAttribute("categoryName", CategoryFactory.me().getMultiplyName(GoodsFactory.me().getCategoryIds(goodsId)));
        model.addAttribute("categoryIds",GoodsFactory.me().getCategoryIds(goodsId));
        //回填标签
        model.addAttribute("tagList",tagService.list(new Tag()));
        model.addAttribute("tagIds",GoodsFactory.me().getTagIds(goodsId));
        //回填规格
        Product product=new Product();
        product.setGoodsId(goodsId);
        model.addAttribute("specList", JSONUtils.toJSONString(new ProductWarpper(productService.find(product)).warp()));
        //回填商品
        Goods goods = goodsService.findById(goodsId);
        model.addAttribute(goods);
        return PREFIX + "edit.html";
    }

    
    /**
     * 跳转到分类选择
     */
    @RequestMapping(value = "/category_select")
    public String category_select() {
        return PREFIX + "goods_category.html";
    }
    /**
     * 选择分类
     */
    @RequestMapping("/setCategory")
    @ResponseBody
    public String setAuthority(@RequestParam("ids") String ids) {
        List<Long> list=new ArrayList<>();
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            if(id!=0){
                list.add(id);
            }
        }
        String namesAndIds= CategoryFactory.me().getMultiplyNamesAndIds(list);
        return namesAndIds;
    }
    
    /**
     * 获取分类下所有规格
     */
    @RequestMapping("/specItems")
    @ResponseBody
    public Object findSpecItmsByCids(@RequestParam("categoryIds") String ids) {
        //截取ids
        if(ids.indexOf("[")!=-1){
            ids=ids.substring(ids.indexOf("[")+1,ids.lastIndexOf("]"));
        }
    	//获取分类的id集合
        List<Long> list=new ArrayList<>();
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            list.add(id);
        }
        //获取分类下所有的规格组集合
        List<Specification> specList = new ArrayList<>();
        for (Long id : list) {
        	List<Specification> findByCid = specificationService.findByCid(id);
        	specList.addAll(findByCid);
		}
        //获取所有规格组以及下面所有的规格项集合 
        List<Specification> specsList = new ArrayList<>();
        for (Specification specification : specList) {
			List<SpecificationItem> byPid = specificationItemService.getByPid(specification.getId());
			HashSet<SpecificationItem> items = new HashSet<>();
			items.addAll(byPid);
			specification.setItems(items);
			specsList.add(specification);
		}
        return specsList;
    }
    
    /**
     * 获取分类下所有参数
     */
    @RequestMapping("/paramItems")
    @ResponseBody
    public Object findParamItemsByCids(@RequestParam("categoryIds") String ids) {
        //截取ids
        if(ids.indexOf("[")!=-1){
            ids=ids.substring(ids.indexOf("[")+1,ids.lastIndexOf("]"));
        }
    	//获取分类的id集合
        List<Long> list=new ArrayList<>();
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            list.add(id);
        }
        //获取分类下所有的参数组集合
        List<Param> paramList = new ArrayList<>();
        for (Long id : list) {
        	List<Param> findByCid = paramService.findByCid(id);
        	paramList.addAll(findByCid);
		}
        //获取所有参数组以及下面所有的参数项集合
        List<Param> paramsList = new ArrayList<>();
        for (Param param : paramList) {
			List<ParamItems> findParamItems = paramItemsService.findParamItems(param);
			Set<ParamItems> items = new HashSet<>();
			if(findParamItems!=null){
                items.addAll(findParamItems);
            }
			param.setItems(items);
			paramsList.add(param);
		}
        return paramsList;
    }
    /**
     * 获取所有品牌列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String barcode) {
        Goods goods=new Goods();
    	goods.setName(condition);
    	List<Map<String, Object>> goodsList = this.goodsService.find(goods,barcode);
        return super.warpObject(new GoodsWarpper(goodsList));
    }

    /**
     * 修改商品
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public Object edit(Goods goods,String specs,String categoryIds,String tagIds) {
        //判断商品的名称是否存在
        if(goodsService.existGoods(goods.getName(),goods.getId())){
            throw new GunsException(BizExceptionEnum.NAME_SAME);
        }
        
        //将原先的规格商品的图片状态设为未使用
      	Product queryObj = new Product();
      	queryObj.setGoodsId(goods.getId());
      	List<Map<String, Object>> oldProducts = productService.find(queryObj);
      	for (Map<String, Object> map : oldProducts) {
      		if (ToolUtil.isNotEmpty(map.get("image"))) {
      			uploadFileService.unUse((Long)(map.get("image")));
      		}
      	}
      	
      	//将原先的商品的图片组设置为未使用
      	Goods findById = goodsService.findById(goods.getId());
      	if (ToolUtil.isNotEmpty(findById.getImages())) {
      		uploadFileService.allUnUse(Convert.toLongArray(true, Convert.toStrArray(",", findById.getImages())));
      	}
        
        //修改商品
        goods.setParamItems(HtmlUtils.htmlUnescape(goods.getParamItems()));
        goods.setSpecItems(HtmlUtils.htmlUnescape(goods.getSpecItems()));
        goods.setDetail(HtmlUtils.htmlUnescape(goods.getDetail()));
        goods.setCreateTime(new Timestamp(new Date().getTime()));
        goodsService.edit(goods);
        //修改商品图片的状态为使用
        if (ToolUtil.isNotEmpty(goods.getImages())) {
      		uploadFileService.allUse(Convert.toLongArray(true, Convert.toStrArray(",", goods.getImages())));
      	}
        
        //修改规格商品
        String htmlUnescape = HtmlUtils.htmlUnescape(specs);
        List<Product> productList = JSONArray.parseArray(htmlUnescape, Product.class);
        Set<Long> imgs=new HashSet<>();
        Set<Long> ids=new HashSet<>();
		for (Product product : productList) {
			product.setGoodsId(goods.getId());
			if(product.getId()==null){
				productService.add(product);
				//生成价格标签
				PriceTagFactory.me().createPriceTag(product);
			}else{
				productService.edit(product);
			}
			if(product.getImage()!=null){
				imgs.add(product.getImage());
			}
			ids.add(product.getId());
		}
		//删除product数据、及其关联的价格标签
        oldProducts.stream().filter(p->!ids.contains((Long)(p.get("id")))).collect(Collectors.toList()).stream().forEach(map->{
        	productService.deleteById((Long)(map.get("id")));
        	priceTagService.deleteByProductId((Long)map.get("id"));
        });
        //修改当前规格的图片状态为使用
        if(ToolUtil.isNotEmpty(imgs)){
			uploadFileService.allUse(imgs);
		}

        //修改商品分类关联
        Set<CategoryRelation> ctyRelationSet = new HashSet<>();
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", categoryIds))) {
            CategoryRelation categoryRelation = new CategoryRelation();
            categoryRelation.setCategoryId(id);
            categoryRelation.setGoodsId(goods.getId());
            ctyRelationSet.add(categoryRelation);
        }
        categoryRelationService.edit(goods.getId(),ctyRelationSet);


        //修改商品标签关联
        HashSet<TagRelation> tagRelationSet = new HashSet<>();
        if(ToolUtil.isNotEmpty(tagIds)){
            for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", tagIds))) {
                TagRelation tagRelation = new TagRelation();
                tagRelation.setTagId(id);
                tagRelation.setGoodsId(goods.getId());
                tagRelationSet.add(tagRelation);
            }
        }
        tagRelationService.edit(goods.getId(),tagRelationSet);
        return SUCCESS_TIP;
    }
    /**
     * 删除商品
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Long brandId) {
        //brandService.deleteById(brandId);
        return SUCCESS_TIP;
    }
    
    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
    	//保存文件
    	String filename = picture.getOriginalFilename();
    	String[] name = filename.split("\\.");
    	String suffix = name[name.length-1];
        String pictureName = UUID.randomUUID().toString() + "." + suffix;
        try {
        	String fileSavePath = gunsProperties.getFileUploadPath();
            File tempFile = new File(fileSavePath + pictureName);
            picture.transferTo(tempFile);
            File f = new File(fileSavePath+"/goodsImg");
            if(!f.exists()){
                f.mkdirs();//创建目录
            }
            //压缩图片
            CompressUtil.reduceImg(fileSavePath + pictureName, fileSavePath + "goodsImg/" + pictureName, 700, 700, null);
            tempFile.delete();
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        //将上传图片信息存到uploadfile数据表中
        UploadFile uploadFile = new UploadFile();
        uploadFile.setUrl("goodsImg/"+pictureName);
        uploadFile.setCreateTime(new Timestamp(new Date().getTime()));
        uploadFile.setIsUse(IsUse.NOTUSED.getCode());
        String pictureId = uploadFileService.add(uploadFile).toString();
        //返回图片在uploadfile数据表里的id
        return pictureId;
    }
    /**
     * 新增商品
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Goods goods,String specs,String categoryIds,String tagIds) {
        //判断商品的名称是否存在
    	if(goodsService.existGoods(goods.getName(),null)){
        	throw new GunsException(BizExceptionEnum.NAME_SAME);
        }
    	
    	//添加商品
        goods.setParamItems(HtmlUtils.htmlUnescape(goods.getParamItems()));
        goods.setSpecItems(HtmlUtils.htmlUnescape(goods.getSpecItems()));
    	goods.setDetail(HtmlUtils.htmlUnescape(goods.getDetail()));
    	goods.setCreateTime(new Timestamp(new Date().getTime()));
        Long goodsId=goodsService.add(goods);
        
        //添加规格商品
        String htmlUnescape = HtmlUtils.htmlUnescape(specs);
		List<Product> productList = JSONArray.parseArray(htmlUnescape, Product.class);
		Set<Long> imgs=new HashSet<>();
		for (Product product : productList) {
			product.setGoodsId(goodsId);
			if(product.getImage()!=null){
				imgs.add(product.getImage());
			}
			productService.add(product);
			PriceTagFactory.me().createPriceTag(product);
		}
		
		//修改上传图片的状态
		if (ToolUtil.isNotEmpty(goods.getImages())) {
			uploadFileService.allUse(Convert.toLongArray(true, Convert.toStrArray(",", goods.getImages())));
		}
		uploadFileService.allUse(imgs);
		
		//添加商品分类关联
        Set<CategoryRelation> ctyRelationSet = new HashSet<>();
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", categoryIds))) {
        	CategoryRelation categoryRelation = new CategoryRelation();
            categoryRelation.setCategoryId(id);
            categoryRelation.setGoodsId(goodsId);
            ctyRelationSet.add(categoryRelation);
        }
        categoryRelationService.add(ctyRelationSet);
        
        
        //添加商品标签关联
        HashSet<TagRelation> tagRelationSet = new HashSet<>();
        if(ToolUtil.isNotEmpty(tagIds)){
	        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", tagIds))) {
	        	TagRelation tagRelation = new TagRelation();
	            tagRelation.setTagId(id);
	            tagRelation.setGoodsId(goodsId);
	            tagRelationSet.add(tagRelation);
	        }
        }
        tagRelationService.add(tagRelationSet);
        
        return SUCCESS_TIP; 
    }
}
