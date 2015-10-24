package com.honeywell.poi.entry;

public class RowColumn {
	private Integer rowIndex;
	private Integer programColumnIndex;
	private Integer remarkColumnIndex;
	private Integer hoursColumnIndex;
	private Integer functionColumnIndex;
	
	public RowColumn() {
	}
	public Integer getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
	public Integer getProgramColumnIndex() {
		return programColumnIndex;
	}
	public void setProgramColumnIndex(Integer programColumnIndex) {
		this.programColumnIndex = programColumnIndex;
	}
	public Integer getRemarkColumnIndex() {
		return remarkColumnIndex;
	}
	public void setRemarkColumnIndex(Integer remarkColumnIndex) {
		this.remarkColumnIndex = remarkColumnIndex;
	}
	public Integer getHoursColumnIndex() {
		return hoursColumnIndex;
	}
	public void setHoursColumnIndex(Integer hoursColumnIndex) {
		this.hoursColumnIndex = hoursColumnIndex;
	}
	public Integer getFunctionColumnIndex() {
		return functionColumnIndex;
	}
	public void setFunctionColumnIndex(Integer functionColumnIndex) {
		this.functionColumnIndex = functionColumnIndex;
	}
	
	public boolean isNotNull(){
		if(this.rowIndex!=null&&this.programColumnIndex!=null&&this.remarkColumnIndex!=null&&this.hoursColumnIndex!=null){
			return true;
		}
		return false;
	}
	
}
