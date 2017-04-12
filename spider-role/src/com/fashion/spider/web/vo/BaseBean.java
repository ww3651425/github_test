package com.fashion.spider.web.vo;

/**
 * 
 * 基础 vo 其实和把map转成json效果一样
 * @author 彭军
 *
 */
public class BaseBean {

	private String key; //键
	private Object value; //值
	
	public BaseBean() {
	}
	
	public BaseBean(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
