package com.fashion.spider.db;

import java.io.InputStream;
import java.util.Properties;


public class DBConfig {

	private static DBConfig instance = null;
	
	private Properties properties = null;
	
	private DBConfig() {
		init();
	}
	
	public static DBConfig getInstance() {
		
		if (instance == null) {
			instance = new DBConfig();
		}
		return instance;
	}
	
	/**
	 * 初始化配置文件
	 */
	public void init(){
		try{
			InputStream is = DBConfig.class.getResourceAsStream("/db.properties");
			properties = new Properties();
			properties.load(is);
		}catch (Exception e){
			throw new RuntimeException("Failed to get properties!");
		}
	}
	
	/**
	 * 根据key值取得对应的value值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key, "");
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
	
}
