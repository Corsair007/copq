package com.honeywell.gb.copq;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.honeywell.poi.entry.CopqData;
import com.honeywell.poi.entry.InitialData;
import com.honeywell.poi.entry.RowColumn;
import com.honeywell.poi.util.Constant;
import com.honeywell.poi.util.DimensionType;
import com.honeywell.poi.util.ExcelUtil;

public class App {
    public static void main( String[] args ) throws Exception {
    	mainStart("F:/GB/0727-0731-SH-1.xls", "SVV_DG_F7X_LD15.3_SH", "F7X_INAV_LD15.3", "F:/GB/HTSC_SVV_DnG_Quality_Data_Metrics-1.xlsx");
    }

	public static String mainStart(String path, String program, String functions, String template) {
		String result = "Congratulations! Program is finished!";
		try {
			Workbook wb = WorkbookFactory.create(new File(path.trim()));
	    	Map<String, String> paramMap = getParamMap(path.trim());
			Sheet sheet = wb.getSheetAt(0);
	    	RowColumn rowColumn = getIndexByName(path.trim(), Constant.PROGRAM);
	    	//String program = "SVV_DG_F7X_LD15.3_SH";
	    	String remarks = "REPLT;RARW;RAR;REW;RFSF;RRW;RFSFR;RFSFRW;SCRW";
	    	//String functions = "F7X_INAV_LD15.3";
	    	Map<String, DimensionType> dimensionMap = bulidDimensionMap();
	    	
	    	Set<String> remarkSet = new HashSet<String>(Arrays.asList(remarks.split(";")));
	    	Set<String> functionSet = new HashSet<String>(Arrays.asList(functions.trim().split(";")));
	    	if(rowColumn!=null){
	    		if (rowColumn.getProgramColumnIndex()==null) {
					result = "This column name 'WBS Element Description' can not be found! Please check the column name of source file!";
				}else if (rowColumn.getFunctionColumnIndex()==null) {
					result = "This column name 'Act Desc' can not be found! Please check the column name of source file!";
				}else {
					if (rowColumn.isNotNull()) {
		    			List<InitialData> datas = new ArrayList<InitialData>();
			    		Map<String, Double> totalActualMap = new HashMap<String, Double>(functionSet.size());
			    		System.out.println("=======================获取原始数据========================");
			    		getInitalData(sheet, rowColumn, program.trim(), dimensionMap, remarkSet,
								functionSet, datas, totalActualMap);
						if (datas.size()>0) {
							///合并记录
							System.out.println("=======================合并记录========================");
							List<CopqData> dataList = getCopqData(sheet, program.trim(), functionSet, datas,
									totalActualMap);
							System.out.println("=======================写结果========================");
							ExcelUtil.getInstance().exportObj2Excel1(dataList, CopqData.class, template.trim());
							System.out.println("=======================END========================");
						}else {
							result = "This program or function can not be find, please check!";
						}
					}else {
						result = "Please check the column name of source file!";
					}
				}
	    		
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR: "+e.getMessage();
		}
		return result;
	}
//Map<String, String> paramMap,
	private static List<CopqData> getCopqData(Sheet sheet, String program,
			Set<String> functionSet, List<InitialData> datas,
			Map<String, Double> totalActualMap) {
		CopqData copqData = null;//
		Iterator<String> functionIterator = functionSet.iterator();
		List<CopqData> dataList = new ArrayList<CopqData>(functionSet.size());
		while (functionIterator.hasNext()) {
			String function = functionIterator.next();
			copqData = new CopqData();
			//copqData.setMonth(getMonth(paramMap.get("num")));
			copqData.setMonth(sheet.getSheetName());
			copqData.setProgram(program);
			copqData.setFunction(function);
			copqData.setTotalActualEfforts(totalActualMap.get(function));
			double h1=0,h2=0,h3=0,h4=0;
			for(InitialData data : datas){
				if(data.getFunction().equalsIgnoreCase(function)&&data.getType()!=null){
					switch (data.getType().getIndex()) {
					case 1:
						h1+=data.getHours();
						break;
					case 2:
						h2+=data.getHours();			
						break;
					case 3:
						h3+=data.getHours();
						break;
					case 4:
						h4+=data.getHours();
						break;

					default:
						break;
					}
				}
			}
			copqData.setReworkReplan(h1);
			copqData.setReworkRA(h2);
			copqData.setReworkTestDevelopment(h3);
			copqData.setReworkRFS_SC(h4);
			copqData.setTotalReworkEfforts(h1+h2+h3+h4);
			copqData.setCopq(getPercent(copqData.getTotalReworkEfforts()/copqData.getTotalActualEfforts()));
			dataList.add(copqData);
		}
		return dataList;
	}

	private static void getInitalData(Sheet sheet, RowColumn rowColumn,
			String program, Map<String, DimensionType> dimensionMap,
			Set<String> remarkSet, Set<String> functionSet,
			List<InitialData> datas, Map<String, Double> totalActualMap) {
		if (sheet!=null) {
			InitialData initialData = null;
			int rowCount = sheet.getLastRowNum();//sheet.getPhysicalNumberOfRows();
			for(int i=rowColumn.getRowIndex()+1;i<=rowCount;i++){
				Row headerRow =sheet.getRow(i);
				if(headerRow!=null){
					//int columnCount = headerRow.getPhysicalNumberOfCells();
					Cell pCell = headerRow.getCell(rowColumn.getProgramColumnIndex());
					if(pCell!=null){
						pCell.setCellType(HSSFCell.CELL_TYPE_STRING);
						String myProgram = pCell.getStringCellValue().trim();
						if(myProgram.equalsIgnoreCase(program)){
							Cell fCell = headerRow.getCell(rowColumn.getFunctionColumnIndex());
							Cell hCell = headerRow.getCell(rowColumn.getHoursColumnIndex());
							if(fCell!=null){
								fCell.setCellType(HSSFCell.CELL_TYPE_STRING);
								String function = fCell.getStringCellValue().trim();
								if(isNotContains(functionSet, function)){
									initialData = new InitialData();
									double totalActualEfforts = 0;
									Cell rCell = headerRow.getCell(rowColumn.getRemarkColumnIndex());
									if(rCell!=null){
										rCell.setCellType(HSSFCell.CELL_TYPE_STRING);
										String remark = rCell.getStringCellValue().trim();
										int index = remark.indexOf(":");
										String r = "";
										if(index!=-1){
											r = remark.substring(0, index);
										}else {
											index = remark.indexOf(" ");
											if (index!=-1) {
												r = remark.substring(0, index);
											}else {
												r = remark;
											}
										}
										if(isNotContains(remarkSet, r)){
											initialData.setProgram(myProgram);
											initialData.setRemark(rCell.getStringCellValue().trim());
											initialData.setType(dimensionMap.get(r.toUpperCase()));
											initialData.setFunction(function);
											if(hCell!=null){
												//hCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
												hCell.setCellType(HSSFCell.CELL_TYPE_STRING);
												String hour = hCell.getStringCellValue().trim();
												if ("".equals(hCell)) {
													hour = "0";
												}
												initialData.setHours(Double.valueOf(hour));
											}
											//System.out.println(i+":"+initialData.getRemark()+"--->"+initialData.getHours());
											datas.add(initialData);
										}
									}
									if(hCell!=null){
										//hCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
										hCell.setCellType(HSSFCell.CELL_TYPE_STRING);
										String h = hCell.getStringCellValue().trim();
										if ("".equals(hCell)) {
											h = "0";
										}
										//计算总的hours
										double hour = Double.valueOf(h);
										totalActualEfforts+=hour;
										Double total = totalActualMap.get(function);
										if (total==null) {
											totalActualMap.put(function, totalActualEfforts);
										}else {
											totalActualMap.put(function, total+totalActualEfforts);
										}
									}
								}
								
							}
						}
					}
					
				}
			}
		}
	}
    
    private static boolean isNotContains(Set<String> set, String name){
    	Iterator<String> iterator = set.iterator();
    	while (iterator.hasNext()) {
    		if (iterator.next().equalsIgnoreCase(name)) {
				return true;
			}
		}
    	return false;
    }
    
	private static Map<String, DimensionType> bulidDimensionMap() {
		Map<String, DimensionType> dimensionMap = new HashMap<String, DimensionType>(9);
    	dimensionMap.put("REPLT", DimensionType.Rework_Replan);
    	dimensionMap.put("RAR", DimensionType.Rework_RA);
    	dimensionMap.put("RARW", DimensionType.Rework_RA);
    	dimensionMap.put("REW", DimensionType.Rework_Test_Develoment);
    	dimensionMap.put("RFSF", DimensionType.Rework_RFS_SC);
    	dimensionMap.put("RRW", DimensionType.Rework_RFS_SC);
    	dimensionMap.put("RFSFR", DimensionType.Rework_RFS_SC);
    	dimensionMap.put("RFSFRW", DimensionType.Rework_RFS_SC);
    	dimensionMap.put("SCRW", DimensionType.Rework_RFS_SC);
    	return dimensionMap;
	}
    private static RowColumn getIndexByName(String path, String labelName) throws Exception{
		Workbook wb = WorkbookFactory.create(new File(path));
		Sheet sheet = wb.getSheetAt(0);
		RowColumn rowColumn = new RowColumn();
		if (sheet!=null) {
			int rowCount = sheet.getPhysicalNumberOfRows();
			for(int i=0;i<rowCount;i++){
				Row headerRow =sheet.getRow(i);
				if(headerRow!=null){
					int columnCount = headerRow.getPhysicalNumberOfCells();
					for(int j=0; j<columnCount; j++){
						Cell cell = headerRow.getCell(j);
						if(cell!=null){
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							if(cell.getStringCellValue().equalsIgnoreCase(Constant.PROGRAM)){
								rowColumn.setRowIndex(i);
								rowColumn.setProgramColumnIndex(j);
							}else if(cell.getStringCellValue().equalsIgnoreCase(Constant.REMARK)){
								rowColumn.setRemarkColumnIndex(j);
							}else if(cell.getStringCellValue().equalsIgnoreCase(Constant.HOURS)){
								rowColumn.setHoursColumnIndex(j);						
							}else if(cell.getStringCellValue().equalsIgnoreCase(Constant.FUNCTION)){
								rowColumn.setFunctionColumnIndex(j);
							}
							if(rowColumn.isNotNull()){
								break;
							}
						}
					}
					if(rowColumn.isRightRow()){
						break;
					}
				}
			}
		}
		return rowColumn;
	}
    public static String getMonth(String num){
		String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May",
				"Jun", "Jul", "Aug", "Sep", "Oct", "Nov","Dec" };
		if (num.charAt(0)=='0') {
			return monthName[Integer.valueOf(num.substring(1))-1];
		}else {
			return monthName[Integer.valueOf(num)-1];
		}
	}
    public static Map<String, String> getParamMap(String path){
    	Map<String, String> paramMap = new HashMap<String, String>(2);
		if (path.contains("/")) {
			path = path.substring(path.lastIndexOf("/")+1);
		}else if (path.contains("\\")) {
			path = path.substring(path.lastIndexOf("\\")+1);
		}
		paramMap.put("sheetName", path.substring(0, path.lastIndexOf(".")));
		paramMap.put("num", path.substring(0, 2));
		return paramMap;
	}
    public static String getPercent(double percent){
 	   //获取格式化对象
 	   NumberFormat nt = NumberFormat.getPercentInstance();
 	   //设置百分数精确度2即保留两位小数
 	   nt.setMinimumFractionDigits(2);
 	   //最后格式化并输出
 	   return nt.format(percent);
 	}
}
