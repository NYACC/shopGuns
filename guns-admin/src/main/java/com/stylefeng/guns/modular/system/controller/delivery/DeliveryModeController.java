package com.stylefeng.guns.modular.system.controller.delivery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.md.delivery.factory.DeliveryCostFactory;
import com.md.delivery.model.Area;
import com.md.delivery.model.DeliveryCost;
import com.md.delivery.model.DeliveryMode;
import com.md.delivery.service.IAreaService;
import com.md.delivery.service.IDeliveryCostService;
import com.md.delivery.service.IDeliveryModeService;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.ExcelUtil;

/**
 * 配送方法controller
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/deliveryMode")
public class DeliveryModeController extends BaseController {

	@Resource
	IDeliveryModeService deliveryModeService;
	@Resource
	IAreaService areaService;
	@Resource
	IDeliveryCostService deliveryCostService;
	@Resource
	private GunsProperties gunsProperties;

	private String PREFIX = "/delivery/deliveryMode/";

	@RequestMapping("")
	public String index() {
		return PREFIX + "list.html";
	}

	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list() {
		List<Map<String, Object>> deliveryModes = deliveryModeService.list();
		return deliveryModes;
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAdd")
	public String toAdd() {
		return PREFIX + "add.html";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(DeliveryMode deliveryMode) {
		deliveryMode.setCreateTime(new Timestamp(new Date().getTime()));
		deliveryModeService.add(deliveryMode);
		// 初始化‘北京东城区’到各个地区配送费用
		List<Area> areas = areaService.countyList();
		// 初始化配送费对象
		List<DeliveryCost> deliveryCosts = new ArrayList<>();
		for (Area area : areas) {
			DeliveryCost deliveryCost = new DeliveryCost();
			deliveryCost.setCreateTime(new Timestamp(new Date().getTime()));
			deliveryCost.setModifyTime(new Timestamp(new Date().getTime()));
			deliveryCost.setModeId(deliveryMode.getId());
			deliveryCost.setIsdelivery(false);
			deliveryCost.setYkg(BigDecimal.ZERO);
			deliveryCost.setStartPrice(BigDecimal.ZERO);
			deliveryCost.setAddedWeight(BigDecimal.ZERO);
			deliveryCost.setAddedPrice(BigDecimal.ZERO);
			deliveryCost.setDeliveryArea(Long.parseLong("2"));
			deliveryCost.setAreaId(area.getId());
			deliveryCosts.add(deliveryCost);
		}
		deliveryCostService.insertBatch(deliveryCosts);
		return SUCCESS_TIP;
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@RequestMapping("toEdit/{deliveryModeId}")
	public String toEdit(@PathVariable Long deliveryModeId, Model model) {
		DeliveryMode deliveryMode = deliveryModeService.getById(deliveryModeId);
		model.addAttribute("deliveryMode", deliveryMode);
		return PREFIX + "edit.html";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Object edit(DeliveryMode deliveryMode) {
		deliveryModeService.update(deliveryMode);
		return SUCCESS_TIP;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(Long deliveryModeId) {
		deliveryModeService.deleteById(deliveryModeId);
		deliveryCostService.deleteByMode(deliveryModeId);
		return SUCCESS_TIP;
	}

	/**
	 * 跳转到配送费配置页面
	 * 
	 * @return
	 */
	@RequestMapping("toCost/{deliveryModeId}")
	public String toCost(@PathVariable Long deliveryModeId, Model model) {
		DeliveryMode deliveryMode = deliveryModeService.getById(deliveryModeId);
		model.addAttribute("deliveryMode", deliveryMode);
		return PREFIX + "deliveryCost.html";
	}

	/**
	 * 获取地区下的配送列表
	 */
	@RequestMapping(value = "/costs")
	@ResponseBody
	public Object costs(Long province, Long city, Long county, Long modeId, Long deliveryPro, Long deliveryCity,
			Long deliveryCou, Boolean isdelivery) {
		// 获取所有的查询地区的Ids
		List<Long> areaIds = areaService.findCountyIds(province, city, county);
		List<Long> deliveryArea = areaService.findCountyIds(deliveryPro, deliveryCity, deliveryCou);
		List<Map<String, Object>> costs = deliveryCostService.findCosts(modeId, areaIds, deliveryArea, isdelivery);
		return costs;
	}

	/**
	 * 跳转到修改配送费配置页面
	 * 
	 * @return
	 */
	@RequestMapping("toCostEdit/{deliveryCostId}")
	public String toCostEdit(@PathVariable Long deliveryCostId, Model model) {
		DeliveryCost deliveryCost = deliveryCostService.getById(deliveryCostId);
		deliveryCost.setModifyTime(new Timestamp(new Date().getTime()));
		model.addAttribute("deliveryCost", deliveryCost);
		model.addAttribute("areaName", DeliveryCostFactory.me().getAreaName(deliveryCost.getAreaId()));
		return PREFIX + "costEdit.html";
	}

	/**
	 * 修改配送费
	 */
	@RequestMapping(value = "/editCost")
	@ResponseBody
	public Object editCost(DeliveryCost deliveryCost) {
		deliveryCostService.update(deliveryCost);
		return SUCCESS_TIP;
	}

	/**
	 * 下载配送费导入模板
	 */
	@RequestMapping("/template")
	public String export(HttpServletResponse response, Long province, Long city, Long county, Long modeId,
			Long deliveryPro, Long deliveryCity, Long deliveryCou, Boolean isdelivery) throws IOException {
		String fileName = "配送费导入模版";
		Map<String, Object> map = new HashMap<>();
		map.put("sheetName", "sheet1");
		// 获取所有的查询地区的Ids
		List<Long> areaIds = areaService.findCountyIds(province, city, county);
		List<Long> deliveryArea = areaService.findCountyIds(deliveryPro, deliveryCity, deliveryCou);
		List<Map<String, Object>> costs = deliveryCostService.findCosts(modeId, areaIds, deliveryArea, isdelivery);
		List<Map<String, Object>> projectsaList = new ArrayList<>();
		projectsaList.add(map);
		projectsaList.addAll(costs);
		String columnNames[] = { "派货地区", "配送地区", "是否配送(是/否)", "首重", "首价格", "续重", "续价" };// 列名
		String keys[] = { "deliveryAreaName", "areaName", "isdelivery", "ykg", "startPrice", "addedWeight",
				"addedPrice" };// map中的key
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
	 * 跳转到导入页面
	 * 
	 * @return
	 */
	@RequestMapping("toImport/{deliveryModeId}")
	public String toImport(@PathVariable Long deliveryModeId, Model model) {
		DeliveryMode deliveryMode = deliveryModeService.getById(deliveryModeId);
		model.addAttribute("deliveryMode", deliveryMode);
		return PREFIX + "import.html";
	}

	/**
	 * 批量导入配送费
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/importCost")
	@ResponseBody
	public Object importCode(@RequestPart("file") MultipartFile xmlFile, Long deliveryModeId) {
		try {
			String name = new Date().getTime() + ".xlsx";
			String fileSavePath = gunsProperties.getFileUploadPath();
			
			File f = new File(fileSavePath + "/deliveryCostXml");
			if (!f.exists()) {
				f.mkdirs();// 创建目录
			}
			File file = new File(fileSavePath + "/deliveryCostXml/" + name);

			FileUtils.copyInputStreamToFile(xmlFile.getInputStream(), file);

			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			// 读取excel
			List<String[]> list = null;
			list = ExcelUtil.read2003Excel(sheet, 1, (short) 0, (short) 7);

			for (String[] row : list) {
				DeliveryCost deliveryCost = new DeliveryCost();
				deliveryCost.setModeId(deliveryModeId);
				deliveryCost.setDeliveryArea(areaService.getByFullName(row[0]).getId());
				deliveryCost.setAreaId(areaService.getByFullName(row[1]).getId());
				deliveryCost.setIsdelivery(row[2].equals("1"));
				deliveryCost.setYkg(BigDecimal.valueOf(Double.valueOf(row[3])));
				deliveryCost.setStartPrice(BigDecimal.valueOf(Double.valueOf(row[4])));
				deliveryCost.setAddedWeight(BigDecimal.valueOf(Double.valueOf(row[5])));
				deliveryCost.setAddedPrice(BigDecimal.valueOf(Double.valueOf(row[6])));
				deliveryCostService.updateByModeArea(deliveryCost);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS_TIP;
	}
}
