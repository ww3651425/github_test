package com.fashion.spider.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 彭军
 * 
 */
public class StringUtil {
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String s){
		return !isBlank(s);
	}
	

	public static String subString(String content, String s, String s1) {
		if( isBlank(content) )
			return "";
		if (content.indexOf(s) != -1){
			content = content.substring(content.indexOf(s)+s.length(), content.length());
		}else{
			return "";
		}
		if (content.indexOf(s1) != -1){
			content = content.substring(0, content.indexOf(s1));
		}else{
			return "";
		}
		return content;
	}
	
	/**
	 * 正则匹配
	 * @param data
	 * @param pattern
	 * @param repeat 是否允许重复
	 * @return
	 */
	public static List<String> getRex(String data, String pattern, boolean repeat) {
		List<String> dataList = new ArrayList<String>();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(data);
		while (m.find()) {
			String str = m.group(1);
			//不能为空 
			if (StringUtil.isBlank(str)) {
				continue;
			}
			//是否可以重复
			if (repeat) {
				dataList.add(str);
			} else if (!dataList.contains(str)) {
				dataList.add(str);
			}
		}
		return dataList;
	}
	
	public static List<String> getRex(String data, String pattern) {
		return getRex(data, pattern, false);
	}
	
	public static String getRexOnly(String data, String pattern) {
		List<String> list = getRex(data, pattern, false);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return "";
	} 
	
	public static Map<String, String> getUrlParam (String url){
		Map<String, String> param = new HashMap<String, String>();
		try {
			URI uri = new URI(url);
			String query = uri.getQuery();
			if( query != null && !query.equals("") ){
				String[] q = query.split("&");
				for (int i = 0; i < q.length; i++) {
					String[] p = q[i].split("=");
					if( p.length == 2 ){
						param.put(p[0], p[1]);
					}
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	public static String firstCharLowerCase(String s) {
		if (s == null || "".equals(s)) {
			return ("");
		}
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

}
