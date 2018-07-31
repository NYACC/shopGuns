package com.stylefeng.guns.modular.system.controller.coupon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.md.coupon.constant.CodeStatus;
import com.md.coupon.exception.CouponExceptionEnum;
import com.md.coupon.model.Coupon;
import com.md.coupon.model.CouponCode;
import com.md.coupon.service.ICouponCodeService;
import com.md.coupon.service.ICouponService;
import com.md.coupon.warpper.CouponCodeWarpper;
import com.md.coupon.warpper.CouponWarpper;
import com.md.goods.model.Shop;
import com.md.goods.service.IShopService;
import com.md.member.model.Member;
import com.md.member.service.IMemberService;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ExcelUtil;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 优惠卷controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

	@Resource
	ICouponService couponService;
	@Resource
	ICouponCodeService couponCodeService;
	@Resource
	IMemberService memberService;
	@Resource
	private GunsProperties gunsProperties;
	@Resource
	IShopService shopService;

	private String PREFIX = "/coupon/coupon/";

	@RequestMapping("")
	public String index() {
		return PREFIX + "list.html";
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAdd")
	public String toAdd(Model model) {
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
		if (ToolUtil.isNotEmpty(shopId)) {
			Shop shop = shopService.findById(shopId);
			model.addAttribute("shop", shop);
		}else{
			model.addAttribute("shop", null);
		}
		return PREFIX + "add.html";
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@RequestMapping("toEdit/{couponId}")
	public String toEdit(@PathVariable Long couponId, Model model) {
		if (couponCodeService.isOperable(couponId)) {
			Coupon coupon = couponService.getById(couponId);
			model.addAttribute("coupon", coupon);
			model.addAttribute("shop", shopService.findById(coupon.getShopId()));
			return PREFIX + "edit.html";
		} else {
			throw new GunsException(CouponExceptionEnum.NOT_OPERABLE);
		}
	}

	/**
	 * 跳转到生成页面
	 * 
	 * @return
	 */
	@RequestMapping("toProduce/{couponId}")
	public String toProduce(@PathVariable Long couponId, Model model) {
		Coupon coupon = couponService.getById(couponId);
		model.addAttribute("coupon", coupon);
		return PREFIX + "produce.html";
	}

	/**
	 * 跳转到导出页面
	 * 
	 * @return
	 */
	@RequestMapping("toExport/{couponId}")
	public String toExport(@PathVariable Long couponId, Model model) {
		Coupon coupon = couponService.getById(couponId);
		model.addAttribute("coupon", coupon);
		return PREFIX + "export.html";
	}

	/**
	 * 跳转到导入页面
	 * 
	 * @return
	 */
	@RequestMapping("toImport/{couponId}")
	public String toImport(@PathVariable Long couponId, Model model) {
		Coupon coupon = couponService.getById(couponId);
		model.addAttribute("coupon", coupon);
		return PREFIX + "import.html";
	}

	/**
	 * 获取优惠卷列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(Coupon coupon) {
		Long shopId = shopService.getShopIdByDeptId(ShiroKit.getUser().getDeptId());
		coupon.setShopId(shopId);
		List<Map<String, Object>> coupons = couponService.find(coupon);
		return new CouponWarpper(coupons).warp();
	}

	/**
	 * 添加优惠卷
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(Coupon coupon,String shopIds) {
		for (Long shopId : Convert.toLongArray(true, Convert.toStrArray(",", shopIds))) {
			coupon.setId(null);
			coupon.setShopId(shopId);
			couponService.add(coupon);
		}
		return SUCCESS;
	}

	/**
	 * 修改优惠卷
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(Coupon coupon) {
		couponService.update(coupon);
		return SUCCESS;
	}

	/**
	 * 删除优惠卷
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(Long couponId) {
		couponService.deleteById(couponId);
		return SUCCESS;
	}

	/**
	 * 生成优惠卷
	 */
	@RequestMapping(value = "/produce")
	@ResponseBody
	public Object produce(Long couponId, Integer quantity, Boolean isSend) {
		if (!isSend) {
			for (int i = 0; i < quantity; i++) {
				CouponCode couponCode = new CouponCode();
				couponCode.setCouponId(couponId);
				couponCode.setStatus(CodeStatus.CREATED.getCode());
				couponCode.setCode(String.valueOf(new Date().getTime()));
				couponCodeService.produce(couponCode);
			}
		} else {
			List<Map<String, Object>> members = memberService.find(new Member());
			for (Map<String, Object> member : members) {
				CouponCode couponCode = new CouponCode();
				couponCode.setCouponId(couponId);
				couponCode.setMemberId((Long) member.get("id"));
				couponCode.setStatus(CodeStatus.RECEIVED.getCode());
				couponCode.setReceviceTime(new Timestamp(new Date().getTime()));
				couponCode.setCode(String.valueOf(new Date().getTime()));
				couponCodeService.produce(couponCode);
			}
		}
		return SUCCESS;
	}

	/**
	 * 导出优惠码
	 */
	@RequestMapping("/export")
	public String export(Long couponId, Integer quantity, HttpServletResponse response) throws IOException {
		String fileName = "优惠卷数据";
		Map<String, Object> map = new HashMap<>();
		map.put("sheetName", "sheet1");
		List<Map<String, Object>> coupons = (List<Map<String, Object>>) super.warpObject(
				new CouponCodeWarpper(couponCodeService.create(couponService.getById(couponId), quantity)));
		List<Map<String, Object>> projectsaList = new ArrayList<>();
		projectsaList.add(map);
		projectsaList.addAll(coupons);
		String columnNames[] = { "优惠码", "优惠名称", "优惠内容", "使用开始时间", "使用结束时间", "介绍" };// 列名
		String keys[] = { "code", "couponName", "couponContent", "useStart", "useEnd", "remark" };// map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createWorkBook(projectsaList, keys, columnNames).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	/**
	 * 批量导入优惠码
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/importCode")
	@ResponseBody
	public Object importCode(@RequestPart("file") MultipartFile xmlFile,Long couponId) {
		try {
			String name = new Date().getTime() + ".xlsx";
			String fileSavePath = gunsProperties.getFileUploadPath();
			
			File f = new File(fileSavePath + "/codeXml");
			if (!f.exists()) {
				f.mkdirs();// 创建目录
			}
			File file = new File(fileSavePath + "/codeXml/" + name);

			FileUtils.copyInputStreamToFile(xmlFile.getInputStream(), file);

			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			// 读取excel
			List<String[]> list = null;
			list = ExcelUtil.read2007Excel(sheet, 1, (short) 0, (short) 2);

			for (String[] row : list) {
				CouponCode couponCode = new CouponCode();
				couponCode.setCouponId(couponId);
				couponCode.setStatus(CodeStatus.RECEIVED.getCode());
				couponCode.setCode(row[0]);
				couponCodeService.produce(couponCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS_TIP;
	}
	
	/**
	 * 跳转到门店树
	 */
	@RequestMapping(value = "/shop_select")
	public String shop_select() {
		return PREFIX + "shopTree.html";
	}
}
