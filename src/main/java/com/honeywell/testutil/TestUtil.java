package com.honeywell.testutil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.honeywell.poi.entry.ScriptEntry;
import com.honeywell.poi.util.Constant;

public class TestUtil {
	public static final SimpleDateFormat defultFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	public static String executeINAVTracking(String trackingSheetName, String inva){
		long startTime = System.currentTimeMillis();
		List<ScriptEntry> scriptEntries = getSourceData(trackingSheetName, Constant.Sheet_Name_INAV_RFS);
		String INAV = inva;
		List<String> resultList = new ArrayList<String>();
		for(ScriptEntry entry:scriptEntries){
			doCheck(entry, INAV, resultList);
		}
		Collections.sort(resultList);
		for(String result: resultList){
			 try {
				FileUtils.writeStringToFile(new File("/Result_INAV_EASE_tracking-"+defultFormat.format(new Date())+".txt"), result+"\n", true);
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		return "Finished! Total consumption"+(System.currentTimeMillis()-startTime)/1000+" second.";
	}
	
	public static String executeDALIBracking(String trackingSheetName, String dalib){
		long startTime = System.currentTimeMillis();
		List<ScriptEntry> scriptEntries = getSourceData(trackingSheetName, Constant.SHEET_NAME_DALIB_RFS);
		String DALIB = dalib;
		List<String> resultList = new ArrayList<String>();
		for(ScriptEntry entry:scriptEntries){
			doCheck(entry, DALIB, resultList);
		}
		Collections.sort(resultList);
		for(String result: resultList){
			 try {
				FileUtils.writeStringToFile(new File("/Result_DALIB_EASE_tracking-"+defultFormat.format(new Date())+".txt"), result+"\n", true);
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		return "Finished! Total consumption"+(System.currentTimeMillis()-startTime)/1000+" second.";
	}
	
public static void execute(){
	long startTime = System.currentTimeMillis();
	List<ScriptEntry> scriptEntries = getSourceData("F:/F7XEAS_INAV_Load15.3_TrackingSheet (5).xls", Constant.Sheet_Name_INAV_RFS);
	String INAV = "F:/Dassualt_EASE_EDS_INAV_Tracking_V23.xls";
	String DALIB = "F:/Dassualt_EASE_EDS_DALIB_Tracking_V37.xlsx";
	System.out.println("----------------------------Start check...------------------------------");
	System.out.println();
	List<String> resultList = new ArrayList<String>();
	for(ScriptEntry entry:scriptEntries){
		doCheck(entry, INAV, resultList);
	}
	List<ScriptEntry> scriptEntryList = getSourceData("F:/F7XEAS_INAV_Load15.3_TrackingSheet (5).xls", Constant.SHEET_NAME_DALIB_RFS);
	for(ScriptEntry entry:scriptEntryList){
		doCheck(entry, DALIB, resultList);
	}
	//Collections.sort(resultList, Collections.reverseOrder());
	Collections.sort(resultList);
	for(String result: resultList){
		 try {
			FileUtils.writeStringToFile(new File("F:/check.txt"), result+"\n", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println(result);
	}
	System.out.println();
	System.out.println("----------------------------End check!------------------------------");
	System.out.println("-------cost "+(System.currentTimeMillis()-startTime)/1000+" second----------");
}
	public static void doCheck(ScriptEntry entry, String path, List<String> resultList){
		try {
			Workbook wb = WorkbookFactory.create(new File(path));
			// 获取excel中的某一个数据表
			Sheet sheet = wb.getSheetAt(0);
			int testModuleIndex = getColumnIndexByHeadName(sheet, Constant.TEST_NAME);
			int easeIndex = getColumnIndexByHeadName(sheet, Constant.EASE);
			int paragraphTagIndex = getColumnIndexByHeadName(sheet, Constant.TAG_NAME);
			int lastRowNum = sheet.getLastRowNum();
			//boolean flag = false;
			int scriptCount = 0;
			for(int i=1; i<lastRowNum-1; i++){
				Row row = sheet.getRow(i);
				if(row!=null){
					Cell cell1 = row.getCell(testModuleIndex);
					//判断该脚本号+后缀是否存在，若不存在则判断是否没有加后缀名
					//首先判断该脚本号是否包含后缀名
					if(entry.getName().contains(".tsf")){
						if (entry.getName().equals(getCellValue(cell1).trim())) {
							scriptCount++;
						}else if(entry.getName().equals(getCellValue(cell1).trim().concat(".tsf"))){
							scriptCount++;
							resultList.add("SCRIPT-WARNING:--------> 【"+entry.getName()+"】 no extension in 【"+ path.substring(path.indexOf(":")+2) +"】\n");
						}
					}else {
						if (entry.getName().concat(".tsf").equals(getCellValue(cell1).trim())) {
							scriptCount++;
						}else if(entry.getName().equals(getCellValue(cell1).trim())){
							scriptCount++;
							resultList.add("SCRIPT-WARNING:--------> 【"+entry.getName()+"】 no extension in 【"+ path.substring(path.indexOf(":")+2) +"】\n");
						}
					}
					if(scriptCount>=1&&(entry.getName().contains(getCellValue(cell1).trim())||getCellValue(cell1).trim().contains(entry.getName()))){

						Cell cell2 = row.getCell(paragraphTagIndex);
						String ptag = getCellValue(cell2);
						if(ptag.trim().equals(entry.getParagraphTags())){
							//检查P_Tag是否以空格结尾
							if (ptag!=null&&ptag.endsWith(" ")) {
								resultList.add("PARAMGRAPH_TAG-WARNING:-------------->"+" 【"+getCellValue(cell1)+"】 ["+ptag+"] end with white space in ("+(i+1)+" row) of 【"+ path.substring(path.indexOf(":")+2) +"】\n");
							}
							//检查EASE Usage是否正确
							Cell cell3 = row.getCell(easeIndex);
							String easeVal = getCellValue(cell3).trim();
							if(StringUtils.isNotBlank(entry.getEaseUsage())){
								if(StringUtils.isBlank(easeVal)||
										("Y".equalsIgnoreCase(entry.getEaseUsage())&&!"YES".equalsIgnoreCase(easeVal))||
										("N".equalsIgnoreCase(entry.getEaseUsage())&&!"NO".equalsIgnoreCase(easeVal))||
										("fix complete".equalsIgnoreCase(entry.getEaseUsage())&&!"YES".equalsIgnoreCase(easeVal))){
									resultList.add("EASE_USAGE-ERROR:-------------->"+" 【"+entry.getName()+"】 EASE Usage mismatch in ("+(i+1)+" row) of 【"+ path.substring(path.indexOf(":")+2) +"】\n");
								}
							}
						}else {
							resultList.add("PARAMGRAPH_TAG-WARNING:-------------->"+" 【"+entry.getName()+"】 paramgraph tag mismatch in ("+(i+1)+" row) of 【"+ path.substring(path.indexOf(":")+2) +"】\n");
						}
					
					}
				}
			}
			if(scriptCount==0){
				resultList.add("SCRIPT-WARNING:--------> 【"+entry.getName()+"】   can not find in 【"+ path.substring(path.indexOf(":")+2) +"】\n");
			}else if(scriptCount > 1){
				resultList.add("SCRIPT-WARNING:--------> 【"+entry.getName()+"】   there are multiple in 【"+ path.substring(path.indexOf(":")+2) +"】\n");
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public static List<ScriptEntry> getSourceData(String sourcePath, String sheetName){
		try {
			// WorkbookFactory可以自动根据文档的类型打开一个excel
			Workbook wb = WorkbookFactory
					.create(new File(sourcePath));
			// 获取excel中的某一个数据表
			//Sheet sheet = wb.getSheetAt(0);
			Sheet sheet = wb.getSheet(sheetName);
			int anchorIndex = getColumnIndexByHeadName(sheet, Constant.TESTANCHOR);
			int pTagIndex = getColumnIndexByHeadName(sheet, Constant.PTAGS);
			int easeIndex = getColumnIndexByHeadName(sheet, Constant.EASE_USAGE);
			int lastRowNum = sheet.getLastRowNum();
			List<ScriptEntry> scriptEntries = new ArrayList<ScriptEntry>();
			// 获取数据表中的某一行
			for(int i=2; i<lastRowNum-1; i++){
				Row row = sheet.getRow(i);
				System.out.println("--->"+i);
				if(row!=null){
					Cell anchor = row.getCell(anchorIndex);
					if(StringUtils.isNotBlank(getCellValue(anchor))){
						ScriptEntry entry = new ScriptEntry();
						Cell cell1 = row.getCell(anchorIndex);
						entry.setName(getCellValue(cell1).trim());
						Cell cell2 = row.getCell(pTagIndex);
						entry.setParagraphTags(getCellValue(cell2).trim());
						Cell cell3 = row.getCell(easeIndex);
						//cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
						entry.setShallTags(getCellValue(cell3)==null?"":getCellValue(cell3).trim());
						scriptEntries.add(entry);
					}
				}
			}
			return scriptEntries;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}
	private static int getColumnIndexByHeadName(Sheet sheet, String headName){
		Row headerRow =sheet.getRow(0);
		int columnCount = headerRow.getPhysicalNumberOfCells();
		for(int i=0; i<columnCount; i++){
			Cell cell = headerRow.getCell(i);
			if(cell.getStringCellValue().contains(headName)){
				return cell.getColumnIndex();
			}
		}
		return 0;
	}

	private static String getCellValue(Cell c) {
		String o = null;
		if(c!=null){
			switch (c.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				o = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				o = String.valueOf(c.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				o = String.valueOf(c.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				o = String.valueOf(c.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				o = c.getStringCellValue();
				break;
			default:
				o = null;
				break;
			}
		}
		return o;
	}

}
