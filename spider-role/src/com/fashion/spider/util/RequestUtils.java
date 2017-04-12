package com.fashion.spider.util;


import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 用于处理HTTP请求的工具类
 * @author pj
 */
public class RequestUtils {

	/**
	 * 判断是否为搜索引擎
	 * @param req
	 * @return
	 */
	public static boolean isRobot(HttpServletRequest req){
		String ua = req.getHeader("user-agent");
		if(StringUtils.isBlank(ua)) return false;
		return (ua != null
				&& (ua.indexOf("Baiduspider") != -1 || ua.indexOf("Googlebot") != -1
						|| ua.indexOf("sogou") != -1
						|| ua.indexOf("sina") != -1
						|| ua.indexOf("iaskspider") != -1
						|| ua.indexOf("ia_archiver") != -1
						|| ua.indexOf("Sosospider") != -1
						|| ua.indexOf("YoudaoBot") != -1
						|| ua.indexOf("yahoo") != -1 
						|| ua.indexOf("yodao") != -1
						|| ua.indexOf("MSNBot") != -1
						|| ua.indexOf("spider") != -1
						|| ua.indexOf("Twiceler") != -1
						|| ua.indexOf("Sosoimagespider") != -1
						|| ua.indexOf("naver.com/robots") != -1
						|| ua.indexOf("Nutch") != -1
						|| ua.indexOf("spider") != -1));	
	}
	
	/***
	 * 是否为手机
	 * @param req
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest req){
		String ua = req.getHeader("user-agent");
		if(StringUtils.isBlank(ua)) return false;
		return (ua.indexOf("iPhone") != -1 
				|| ua.indexOf("Android") != -1 
				|| ua.indexOf("UCWEB") != -1 
				|| ua.indexOf("Nokia") != -1 
				|| ua.indexOf("Mobile") != -1 
				|| ua.indexOf("SAMSUNG") != -1 
				|| ua.indexOf("SonyEricsson") != -1 
				|| ua.indexOf("MOT") != -1 
				|| ua.indexOf("BlackBerry") != -1 
				|| ua.indexOf("LG") != -1 
				|| ua.indexOf("HTC") != -1 
				|| ua.indexOf("J2ME") != -1 
				|| ua.indexOf("Opera Mini") != -1 
		);
	}

	/**
	 * 获取COOKIE
	 * 
	 * @param name
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null)	return null;
		for (Cookie ck : cookies) {
			if (StringUtils.equalsIgnoreCase(name,ck.getName())) 
				return ck;			
		}
		return null;
	}

	/**
	 * 获取COOKIE
	 * 
	 * @param name
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null)	return null;
		for (Cookie ck : cookies) {
			if (StringUtils.equalsIgnoreCase(name,ck.getName())) 
				return ck.getValue();			
		}
		return null;
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(request, response, name, value, maxAge, true);
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge, boolean all_sub_domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		if(all_sub_domain){
			String serverName = request.getServerName();
			String domain = getDomainOfServerName(serverName);
			if(domain!=null && domain.indexOf('.')!=-1){
				cookie.setDomain('.' + domain);
			}
		}
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean all_sub_domain) {
		setCookie(request,response,name,"",0,all_sub_domain);
	}
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		deleteCookie(request, response, name, true);
	}

	/**
	 * 获取用户访问URL中的根域名
	 * 例如: www.dlog.cn -> dlog.cn
	 * @param req
	 * @return
	 */
	public static String getDomainOfServerName(String host){
		if(isIPAddr(host))
			return null;
		String[] names = StringUtils.split(host, '.');
		int len = names.length;
		if(len==1) return null;
		if(len==3){
			return makeup(names[len-2],names[len-1]);
		}
		if(len>3){
			String dp = names[len-2];
			if(dp.equalsIgnoreCase("com")||dp.equalsIgnoreCase("gov")||dp.equalsIgnoreCase("net")||dp.equalsIgnoreCase("edu")||dp.equalsIgnoreCase("org"))
				return makeup(names[len-3],names[len-2],names[len-1]);
			else
				return makeup(names[len-2],names[len-1]);
		}
		return host;
	}

	/**
	 * 判断字符串是否是一个IP地址
	 * @param addr
	 * @return
	 */
	public static boolean isIPAddr(String addr){
		if(StringUtils.isEmpty(addr))
			return false;
		String[] ips = StringUtils.split(addr, '.');
		if(ips.length != 4)
			return false;
		try{
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
					&& ipc <= 255 && ipd >= 0 && ipd <= 255;
		}catch(Exception e){}
		return false;
	}
	
	private static String makeup(String...ps){
		StringBuilder s = new StringBuilder();
		for(int idx = 0; idx < ps.length; idx++){
			if(idx > 0)
				s.append('.');
			s.append(ps[idx]);
		}
		return s.toString();
	}
	
	/***
	 * 
	 * @param uri
	 * @param param 参数名
	 * @return
	 */
	public static String getURIparamValue(String url, String param){
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			return null;
		}
		if( uri == null ){
			return null;
		}
		String query = uri.getQuery();
		if( StringUtil.isBlank(query) ){
			return null;
		}
		String[] q = query.split("&");
		for (int i = 0; i < q.length; i++) {
			String[] p = q[i].split("=");
			if( p[0].equals(param) ){
				return p[1];
			}
		}
		return "";
	}
	
	/***
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		try {
			if( request == null ){
				return "";
			}
			String ip = request.getHeader("X-Real-IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("x-forwarded-for");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)){ //获取浏览器版本
				ip = request.getHeader("user-agent");  
				if(!StringUtil.isBlank(ip)){
					ip = ip.substring(ip.indexOf("(")+1,ip.indexOf("(") + 20);
				}
			}
			return ip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}