package com.fashion.spider.util;

public enum EnumCompany {
	COMP_00("太屋", "11"), COMP_01("菁英", "12"), COMP_02("华銮", "13"), COMP_03(
			"太屋网络", "14");
	private String key;
	private String value;

	// 自定义的构造函数，参数数量，名字随便自己取
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	private EnumCompany(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// 重新toString方法，默认的toString方法返回的就是枚举变量的名字，和name()方法返回值一样
	@Override
	public String toString() {
		return this.key + ":" + this.value;

	}
}
