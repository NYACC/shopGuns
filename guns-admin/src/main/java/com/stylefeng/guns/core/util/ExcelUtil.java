package com.stylefeng.guns.core.util;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导出Excel文档工具类
 * @author 那位先生
 * @date 2014-8-6
 * */
public class ExcelUtil {

	/**
	 * 
	 * @param sheet
	 * @param startRow 开始行
	 * @param startColumn 开始列
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]>  read2007Excel(XSSFSheet sheet,int startRow,short startColumn) {
		
		List<String[]> result = new ArrayList<String[]>();
//		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
//		XSSFSheet sheet = xwb.getSheetAt(0);
		if(sheet==null){
			return result;
		}
		for(int rowIndex=startRow;rowIndex<=sheet.getLastRowNum();rowIndex++){
			XSSFRow row = sheet.getRow(rowIndex);
			if(row==null){
				continue;
			}
			String[] rowArray = new String[row.getLastCellNum()-startColumn];
			for(short columnIndex=startColumn;columnIndex<row.getLastCellNum();columnIndex++){
				String value = "";
				XSSFCell cell = row.getCell(columnIndex);//得到的每行的每个具体的值 
				if(cell!=null){
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
						if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = new DecimalFormat("0").format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if (!cell.getStringCellValue().equals("")) {
							value = cell.getStringCellValue();
						} else {
							value = cell.getNumericCellValue() + "";
						}
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue() == true ? "Y" : "N");
						break;
					default:
						value = "";
						break;
					}
				}
				rowArray[columnIndex-startColumn]=value;
			}
			result.add(rowArray);
		}
		return result;
	}
	
	/**
	 * 
	 * @param sheet
	 * @param startRow 开始行
	 * @param startColumn 开始列
	 * @param endColumn 结束列
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]>  read2007Excel(XSSFSheet sheet,int startRow,short startColumn,short endColumn) {
		
		List<String[]> result = new ArrayList<String[]>();
		if(sheet==null){
			return result;
		}
		for(int rowIndex=startRow;rowIndex<=sheet.getLastRowNum();rowIndex++){
			XSSFRow row = sheet.getRow(rowIndex);
			if(row==null){
				continue;
			}
			String[] rowArray = new String[endColumn-startColumn];
			for(short columnIndex=startColumn;columnIndex<endColumn;columnIndex++){
				String value = "";
				XSSFCell cell = row.getCell(columnIndex);//得到的每行的每个具体的值 
				if(cell!=null){
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
						if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = new DecimalFormat("0").format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if (!cell.getStringCellValue().equals("")) {
							value = cell.getStringCellValue();
						} else {
							value = cell.getNumericCellValue() + "";
						}
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue() == true ? "Y" : "N");
						break;
					default:
						value = "";
						break;
					}
				}
				rowArray[columnIndex-startColumn]=value;
			}
			result.add(rowArray);
		}
		return result;
	}

	/**
	 * 
	 * @param sheet
	 * @param startRow 开始行
	 * @param startColumn 开始列
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> read2003Excel(HSSFSheet sheet,int startRow,short startColumn) throws FileNotFoundException, IOException{
		
		List<String[]> result = new ArrayList<String[]>();
		//创建新的 excel工作簿
//		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
//		HSSFSheet sheet = hwb.getSheetAt(0);
		if(sheet==null){
			return result;
		}
		//循环每行
		for(int rowIndex=startRow;rowIndex<=sheet.getLastRowNum();rowIndex++){
			HSSFRow row = sheet.getRow(rowIndex);//得到每一行的数据 
			if(row==null){
				continue;
			}
			String[] rowArray = new String[row.getLastCellNum()-startColumn];
			//循环每列
			for (short columnIndex = startColumn; columnIndex <row.getLastCellNum(); columnIndex++) {
				String value = "";
				HSSFCell cell  = row.getCell(columnIndex);
				if(cell!=null){
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							Date date = cell.getDateCellValue();
							if(date!=null){
								value = new SimpleDateFormat("yyyy-MM-dd").format(date);
							}else{
								value = "";
							}
						}else{
							value = new DecimalFormat("0").format(cell.getNumericCellValue());
						}
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if(!cell.getStringCellValue().equals("")){
							value = cell.getStringCellValue();
						}else{
							value = cell.getNumericCellValue()+"";
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue()==true?"Y":"N");
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					default:
						value = cell.toString();
					}
				}
				rowArray[columnIndex-startColumn]=value;
			}
			result.add(rowArray);
		}
		return result;
	}
	
	/**
	 * 
	 * @param sheet
	 * @param startRow 开始行
	 * @param startColumn 开始列
	 * @param endColumn 结束列
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> read2003Excel(HSSFSheet sheet,int startRow,short startColumn,short endColumn) throws FileNotFoundException, IOException{
		
		List<String[]> result = new ArrayList<String[]>();
		if(sheet==null){
			return result;
		}
		//循环每行
		for(int rowIndex=startRow;rowIndex<=sheet.getLastRowNum();rowIndex++){
			HSSFRow row = sheet.getRow(rowIndex);//得到每一行的数据 
			if(row==null){
				continue;
			}
			String[] rowArray = new String[endColumn-startColumn];
			//循环每列
			for (short columnIndex = startColumn; columnIndex <endColumn; columnIndex++) {
				String value = "";
				HSSFCell cell  = row.getCell(columnIndex);
				if(cell!=null){
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							Date date = cell.getDateCellValue();
							if(date!=null){
								value = new SimpleDateFormat("yyyy-MM-dd").format(date);
							}else{
								value = "";
							}
						}else{
							value = new DecimalFormat("0").format(cell.getNumericCellValue());
						}
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if(!cell.getStringCellValue().equals("")){
							value = cell.getStringCellValue();
						}else{
							value = cell.getNumericCellValue()+"";
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue()==true?"Y":"N");
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					default:
						value = cell.toString();
					}
				}
				rowArray[columnIndex-startColumn]=value;
			}
			result.add(rowArray);
		}
		return result;
	}
	
	/**
	 * 
	 * 
	 * @param exportPath 导出路径
	 * @param columnHead 列头
	 * @param list 数据
	 * @param title 标题
	 * @throws IOException
	 */
	public static void export2007Excel(String exportPath,String[] columnHead,List<List<String>> list,String title) throws IOException{
		XSSFWorkbook xwb = new XSSFWorkbook();
		XSSFSheet sheet = xwb.createSheet(title);
		XSSFRow  rowHead = sheet.createRow(1);
		Map<String,Integer> columnWidthMap = new HashMap<String,Integer>();
		for(int i=0;i<columnHead.length;i++){
			XSSFCell cell = rowHead.createCell(i);	
			String value = columnHead[i];
			cell.setCellValue(value);
			Integer columnWidth = columnWidthMap.get(i+"");
			if(columnWidth!=null && value!=null && !value.equals("")){
				Integer strLength = value.getBytes().length;
				if(columnWidth<strLength){
					columnWidthMap.put(i+"", strLength);
				}
			}else if(value!=null && !value.equals("")){
				columnWidthMap.put(i+"", value.getBytes().length);
			}
		}
		for(int rowCount=0;rowCount<list.size();rowCount++){
			List<String> rowList = list.get(rowCount);
			XSSFRow  row = sheet.createRow(rowCount+2);
			for(int columnCount=0;columnCount<rowList.size();columnCount++){
				XSSFCell cell = row.createCell(columnCount);
				String value = rowList.get(columnCount);
				cell.setCellValue(value);
				Integer columnWidth = columnWidthMap.get(columnCount+"");
				if(columnWidth!=null && value!=null && !value.equals("")){
					Integer strLength = value.getBytes().length;
					if(columnWidth<strLength){
						columnWidthMap.put(columnCount+"", strLength);
					}
				}else if(value!=null && !value.equals("")){
					columnWidthMap.put(columnCount+"", value.getBytes().length);
				}
			}
		}
		//设置适合列宽
		for(int i=0;i<columnHead.length;i++){
			sheet.setColumnWidth(i, (columnWidthMap.get(i+"")+2)*256);
		}
		FileOutputStream fos = new FileOutputStream(exportPath);
		xwb.write(fos);
		fos.close();
	}
	
	/**
	 * @param normalHead 表头
	 * @param exportPath 导出路径
	 * @param columnHead 列头
	 * @param list 数据
	 * @param sheetName 标题
	 * @throws IOException
	 */
	public static void export2007Excel(String normalHead,String exportPath,String[] columnHead,List<List<String>> list,String sheetName) throws IOException{
		XSSFWorkbook xwb = new XSSFWorkbook();
		XSSFSheet sheet = xwb.createSheet(sheetName);
		
		XSSFRow row1 =  sheet.createRow(0);
		XSSFCell cell1 = row1.createCell(0);
		cell1.setCellValue(normalHead);
		XSSFCellStyle style = xwb.createCellStyle();
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cell1.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)columnHead.length-1));
		XSSFRow  rowHead = sheet.createRow(1);
		Map<String,Integer> columnWidthMap = new HashMap<String,Integer>();
		for(int i=0;i<columnHead.length;i++){
			XSSFCell cell = rowHead.createCell(i);	
			String value = columnHead[i];
			cell.setCellValue(value);
			Integer columnWidth = columnWidthMap.get(i+"");
			if(columnWidth!=null && value!=null && !value.equals("")){
				Integer strLength = value.getBytes().length;
				if(columnWidth<strLength){
					columnWidthMap.put(i+"", strLength);
				}
			}else if(value!=null && !value.equals("")){
				columnWidthMap.put(i+"", value.getBytes().length);
			}
		}
		for(int rowCount=0;rowCount<list.size();rowCount++){
			List<String> rowList = list.get(rowCount);
			XSSFRow  row = sheet.createRow(rowCount+2);
			for(int columnCount=0;columnCount<rowList.size();columnCount++){
				XSSFCell cell = row.createCell(columnCount);
				String value = rowList.get(columnCount);
				if(value!=null && RegexUtil.numberRegex(value)){
					cell.setCellValue(Double.parseDouble(value));
				}else{
					cell.setCellValue(value);
				}
				Integer columnWidth = columnWidthMap.get(columnCount+"");
				if(columnWidth!=null && value!=null && !value.equals("")){
					Integer strLength = value.getBytes().length;
					if(columnWidth<strLength){
						columnWidthMap.put(columnCount+"", strLength);
					}
				}else if(value!=null && !value.equals("")){
					columnWidthMap.put(columnCount+"", value.getBytes().length);
				}
			}
		}
		//设置适合列宽
		for(int i=0;i<columnHead.length;i++){
			sheet.setColumnWidth(i, (columnWidthMap.get(i+"")+2)*256);
		}
		FileOutputStream fos = new FileOutputStream(exportPath);
		xwb.write(fos);
		fos.close();
	}
	
	
	/**
	 * @param normalHead 表头
	 * @param exportPath 导出路径
	 * @param columnHead 列头
	 * @param list 数据
	 * @param sheetName 标题
	 * @throws IOException
	 */
	public static void export2003Excel(String normalHead,String exportPath,String[] columnHead,List<List<String>> list,String sheetName) throws IOException{
		HSSFWorkbook xwb = new HSSFWorkbook();
		HSSFSheet sheet = xwb.createSheet(sheetName);
		
		int beginrow = 0;
		if(normalHead!=null && !"".equals(normalHead)){
			HSSFRow row1 =  sheet.createRow(beginrow);
			HSSFCell cell1 = row1.createCell(0);
			cell1.setCellValue(normalHead);
			HSSFCellStyle style = xwb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cell1.setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)columnHead.length-1));
			beginrow ++;
		}
		
		
		Map<String,Integer> columnWidthMap = new HashMap<String,Integer>();
		HSSFRow  rowHead = sheet.createRow(beginrow);
		for(int i=0;i<columnHead.length;i++){
			HSSFCell cell = rowHead.createCell(i);	
			String value = columnHead[i];
			cell.setCellValue(value);
			Integer columnWidth = columnWidthMap.get(i+"");
			if(columnWidth!=null && value!=null && !value.equals("")){
				Integer strLength = value.getBytes().length;
				if(columnWidth<strLength){
					columnWidthMap.put(i+"", strLength);
				}
			}else if(value!=null && !value.equals("")){
				columnWidthMap.put(i+"", value.getBytes().length);
			}
		}
		beginrow++;
		for(int rowCount=0;rowCount<list.size();rowCount++){
			List<String> rowList = list.get(rowCount);
			HSSFRow  row = sheet.createRow(rowCount+beginrow);
			for(int columnCount=0;columnCount<rowList.size();columnCount++){
				HSSFCell cell = row.createCell(columnCount);
				String value = rowList.get(columnCount);
//				if(value!=null && RegexUtil.numberRegex(value)){
//					cell.setCellValue(Double.parseDouble(value));
//				}else{
					cell.setCellValue(value);
//				}
				Integer columnWidth = columnWidthMap.get(columnCount+"");
				if(columnWidth!=null && value!=null && !value.equals("")){
					Integer strLength = value.getBytes().length;
					if(columnWidth<strLength){
						columnWidthMap.put(columnCount+"", strLength);
					}
				}else if(value!=null && !value.equals("")){
					columnWidthMap.put(columnCount+"", value.getBytes().length);
				}
			}
		}
		//设置适合列宽
		for(int i=0;i<columnHead.length;i++){
			Integer cwidth = columnWidthMap.get(i+"");
			if(cwidth!=null){
				if(cwidth>255){
					cwidth=250;
				}
				sheet.setColumnWidth(i, (cwidth+2)*256);
			}

		}
		FileOutputStream fos = new FileOutputStream(exportPath);
		xwb.write(fos);
		fos.close();
	}
	
	/**
	 * @param normalHead 表头
	 * @param exportPath 导出路径
	 * @param columnHead 列头
	 * @param list 数据
	 * @param sheetName 标题
	 * @throws IOException
	 */
	public static void export2003ExcelByPhoneBillImport(String[] normalHead,String exportPath,String[] columnHead,List<List<String>> list,String sheetName, int beginColumn) throws IOException{
		HSSFWorkbook xwb = new HSSFWorkbook();
		HSSFSheet sheet = xwb.createSheet(sheetName);
		
		int beginrow = 0;
		if(normalHead!=null){
			HSSFRow row1 =  sheet.createRow(beginrow);
			for(int i = 0; i < normalHead.length; i++){
				HSSFCell cell = null;
				if(i == 0){
					cell = row1.createCell(i);
					sheet.addMergedRegion(new CellRangeAddress(0,0,0,beginColumn));
					beginColumn++;
				} else {
					cell = row1.createCell(beginColumn);
					sheet.addMergedRegion(new CellRangeAddress(0,0,beginColumn,beginColumn+1));
					beginColumn += 2;
				}
				cell.setCellValue(normalHead[i]);
				HSSFCellStyle style = xwb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cell.setCellStyle(style);
			}
			beginrow ++;
		}
		
		
		Map<String,Integer> columnWidthMap = new HashMap<String,Integer>();
		HSSFRow  rowHead = sheet.createRow(beginrow);
		for(int i=0;i<columnHead.length;i++){
			HSSFCell cell = rowHead.createCell(i);	
			String value = columnHead[i];
			cell.setCellValue(value);
			Integer columnWidth = columnWidthMap.get(i+"");
			if(columnWidth!=null && value!=null && !value.equals("")){
				Integer strLength = value.getBytes().length;
				if(columnWidth<strLength){
					columnWidthMap.put(i+"", strLength);
				}
			}else if(value!=null && !value.equals("")){
				columnWidthMap.put(i+"", value.getBytes().length);
			}
		}
		beginrow++;
		for(int rowCount=0;rowCount<list.size();rowCount++){
			List<String> rowList = list.get(rowCount);
			HSSFRow  row = sheet.createRow(rowCount+beginrow);
			for(int columnCount=0;columnCount<rowList.size();columnCount++){
				HSSFCell cell = row.createCell(columnCount);
				String value = rowList.get(columnCount);
//				if(value!=null && RegexUtil.numberRegex(value)){
//					cell.setCellValue(Double.parseDouble(value));
//				}else{
					cell.setCellValue(value);
//				}
				Integer columnWidth = columnWidthMap.get(columnCount+"");
				if(columnWidth!=null && value!=null && !value.equals("")){
					Integer strLength = value.getBytes().length;
					if(columnWidth<strLength){
						columnWidthMap.put(columnCount+"", strLength);
					}
				}else if(value!=null && !value.equals("")){
					columnWidthMap.put(columnCount+"", value.getBytes().length);
				}
			}
		}
		//设置适合列宽
		for(int i=0;i<columnHead.length;i++){
			Integer cwidth = columnWidthMap.get(i+"");
			if(cwidth!=null){
				if(cwidth>255){
					cwidth=250;
				}
				sheet.setColumnWidth(i, (cwidth+2)*256);
			}

		}
		FileOutputStream fos = new FileOutputStream(exportPath);
		xwb.write(fos);
		fos.close();
	}
	
	/**
	 * 合并单元格
	 * @param sheet
	 * @param rowfrom
	 * @param rowto
	 * @param colfrom
	 * @param colto
	 */
	public static void mergeCells(HSSFSheet sheet,int rowfrom,int rowto,int colfrom,int colto){
		 CellRangeAddress region = new CellRangeAddress(rowfrom,rowto,colfrom,colto);
		 sheet.addMergedRegion(region);
		 HSSFCell cell=sheet.getRow(rowfrom).getCell(colfrom);
		 cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
	 }
	
	/**
	 * 设置合并单元格的样式
	 * @param sheet
	 * @param region
	 * @param cs
	 */
	public static void setRegionStyle(HSSFSheet sheet, Region region , HSSFCellStyle cs) {
       int toprowNum = region.getRowFrom();
       for (int i = region.getRowFrom(); i <= region.getRowTo(); i ++) {
           HSSFRow row = HSSFCellUtil.getRow(i, sheet);
           for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
               HSSFCell cell = HSSFCellUtil.getCell(row, (short)j);
               cell.setCellStyle(cs);
           }
       }
	}
	
	/**
     * 创建excel文档，
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

}