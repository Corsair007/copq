package com.honeywell.poi.conf;

import java.io.IOException;
import java.util.Properties;

public class Config {

	private static Properties p;
	public static Object demoDir;
	public static String shellDir;
	public static String gitTempDir;
	public static String appprojectDir;
	public static String javaprojectDir;
	static {
		p = new Properties();
		try {
			p.load(Config.class.getResourceAsStream("/conf/config.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// splice path
		demoDir = p.get("demoDir");
		shellDir = demoDir + "shell/";
		appprojectDir = demoDir + "appprojects/";
		javaprojectDir = demoDir + "javaprojects/";
		gitTempDir = demoDir + "git_temp/";
	}

	public Properties getP(){
		if (Config.p==null) {
			p = new Properties();
			try {
				p.load(Config.class.getResourceAsStream("/conf/config.properties"));
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return Config.p;
	}
	
	public static void main(String[] args) {
		System.out.println(Config.p);
	}
}