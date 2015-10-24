package com.honeywell.poi.entry;
import com.honeywell.poi.util.DimensionType;
public class InitialData {
	private String program;
	private String function;
	private String remark;
	private Double hours;
	private DimensionType type;
	
	public DimensionType getType() {
		return type;
	}
	public void setType(DimensionType type) {
		this.type = type;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getHours() {
		return hours;
	}
	public void setHours(Double hours) {
		this.hours = hours;
	}
	
}
