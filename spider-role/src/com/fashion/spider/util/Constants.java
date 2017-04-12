package com.fashion.spider.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统常量
 * 
 */
public class Constants {
	
	public static final int DEFAULT_PAGE_SIZE = 15; // 分页控件 默认页面大小
	
	public static final int DEFAULT_SIZE = Integer.MAX_VALUE; // 最大整型
	
	public static final String IMG_DOMAIN = "http://img.wangpan007.com";	//"http://7sbsle.com1.z0.glb.clouddn.com";

	public static final String BAIDU_APP_KEY = "wv6dZRvYIh9Tb2eMUlxKvLLj";
	
	public static final String BAIDU_SECRET_KEY = "kZ9pfop0pWuZsL0FAZ9mT5Pr6wGFeDNt";
	
	
	public static final String VERIFYEMAIL_KEY = "wangpan007.img";
	
	
	public static Map<Integer, Date> AccountFillMap = new  ConcurrentHashMap<Integer, Date>(); 	//充值续费 - 
	
	
	public static final String COOKIE_NAME = "re_user";
}
