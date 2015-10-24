package com.honeywell.poi.entry;

import com.honeywell.poi.util.ExcelResources;

public class CopqData {
//	public static void main(String[] args) {
//		CopqData copqData = new CopqData();
//		copqData.setMonth("Jan");
//		copqData.setProgram("XXX");
//		copqData.setFunction("AAA");
//		copqData.setReworkReplan("0");
//		copqData.setReworkRA("0");
//		copqData.setReworkTestDevelopment("10");
//		copqData.setReworkRFS_SC("5");
//	}
	private String sn;
	private String month;
	private String site;
	private String program;
	private String function;
	private Double reworkReplan;
	private Double reworkRA;
	private Double reworkTestDevelopment;
	private Double reworkRFS_SC;
	private Double totalReworkEfforts;
	private Double totalActualEfforts;
	private String copq;
	private String copqGoal;
	//@ExcelResources(title="SN",order=1)
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	@ExcelResources(title="Month",order=1)
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	//@ExcelResources(title="Site",order=3)
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	@ExcelResources(title="Program",order=2)
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	@ExcelResources(title="Function",order=3)
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	@ExcelResources(title="Rework - Replan",order=4)
	public Double getReworkReplan() {
		return reworkReplan;
	}
	public void setReworkReplan(Double reworkReplan) {
		this.reworkReplan = reworkReplan;
	}
	@ExcelResources(title="Rework- RA",order=5)
	public Double getReworkRA() {
		return reworkRA;
	}
	public void setReworkRA(Double reworkRA) {
		this.reworkRA = reworkRA;
	}
	@ExcelResources(title="Rework - Test Development",order=6)
	public Double getReworkTestDevelopment() {
		return reworkTestDevelopment;
	}
	public void setReworkTestDevelopment(Double reworkTestDevelopment) {
		this.reworkTestDevelopment = reworkTestDevelopment;
	}
	@ExcelResources(title="Rework - RFS/SC",order=7)
	public Double getReworkRFS_SC() {
		return reworkRFS_SC;
	}
	public void setReworkRFS_SC(Double reworkRFS_SC) {
		this.reworkRFS_SC = reworkRFS_SC;
	}
	@ExcelResources(title="Total rework Efforts",order=8)
	public Double getTotalReworkEfforts() {
		return totalReworkEfforts;
	}
	public void setTotalReworkEfforts(Double totalReworkEfforts) {
		this.totalReworkEfforts = totalReworkEfforts;
	}
	@ExcelResources(title="Total Actual Efforts",order=9)
	public Double getTotalActualEfforts() {
		return totalActualEfforts;
	}
	public void setTotalActualEfforts(Double totalActualEfforts) {
		this.totalActualEfforts = totalActualEfforts;
	}
	
	@ExcelResources(title="COPQ",order=10)
	public String getCopq() {
		return copq;
	}
	public void setCopq(String copq) {
		this.copq = copq;
	}
	@ExcelResources(title="COPQ Goal",order=11)
	public String getCopqGoal() {
		return copqGoal;
	}
	public void setCopqGoal(String copqGoal) {
		this.copqGoal = copqGoal;
	}
	
}
