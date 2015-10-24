package com.honeywell.poi.entry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

public class BaseEntry {
	private String name;
	private String ParagraphTags;
	private String shallTags;
	
	public BaseEntry() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseEntry(String name, String paragraphTags, String shallTags) {
		super();
		this.name = name;
		ParagraphTags = paragraphTags;
		this.shallTags = shallTags;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParagraphTags() {
		return ParagraphTags;
	}
	public void setParagraphTags(String paragraphTags) {
		ParagraphTags = paragraphTags;
	}
	public String getShallTags() {
		return shallTags;
	}
	public void setShallTags(String shallTags) {
		this.shallTags = shallTags;
	}
	
	@SuppressWarnings("rawtypes")
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        String className = null;
        try {
             className = this.getClass().getSimpleName();
             buffer.append(className + "{");
             Field[] fs = this.getClass().getDeclaredFields();
            
             Class fType = null ;
             String fName = null ;
             Object fValue = null ;
             for(Field f : fs){
                  fType = f.getType();
                  if((fType.isPrimitive()
                            || (fType == Integer.class)
                            || (fType == Long.class)
                            || (fType == String.class)
                            || (fType == Date.class)) && !Modifier.isStatic(f.getModifiers())){
                       fName = f.getName();
                       f.setAccessible(true);
                       fValue = f.get(this);
                       buffer.append(fName + ":" + fValue + ",");
                  }
             }
             buffer.append("}");
        } catch (Exception e) {
             e.printStackTrace();
        }
        return buffer.toString() ;
   }
}
