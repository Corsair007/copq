package com.honeywell.poi.util;

public enum DimensionType {
	Rework_Replan(1, ""),
	Rework_RA(2, ""),
	Rework_Test_Develoment(3, ""),
	Rework_RFS_SC(4, "");
	
	int index;
	String desc;
	DimensionType(int index, String desc){
		this.index = index;
		this.desc = desc;
	}
	
	public String getValue(){
		return this.desc;
	}
	public int getIndex(){
		return this.index;
	}
}
