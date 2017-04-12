package com.fashion.spider.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {

	private static final ThreadLocal<Map<String, Object>> contextHolder = new ThreadLocal<Map<String, Object>>();
	 
	public static void setObject(String key, Object obj) {
		Map<String, Object> map= contextHolder.get();
		if(map == null){
			map = new HashMap<String, Object>();
			contextHolder.set(map);
		}
		map.put(key, obj);
	}


	public static Object getObject(String key) {
//		if(  contextHolder.get() != null ){ 
//			Map<String, Object> map = contextHolder.get();
//			for (String keyMap : map.keySet()) {
//				   System.out.println("key= "+ keyMap + " and value= " + map.get(keyMap));
//			}
//		}
		return contextHolder.get() == null ? null :contextHolder.get().get(key); 
	}
	

	public static void remove() {
		contextHolder.remove();
	}
} 
