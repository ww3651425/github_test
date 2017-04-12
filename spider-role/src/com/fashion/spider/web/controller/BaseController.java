package com.fashion.spider.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fashion.spider.model.User;
import com.fashion.spider.util.Constants;

public class BaseController {
	
	protected static final Logger logger = Logger.getLogger(BaseController.class);

	protected final int pageSize = Constants.DEFAULT_PAGE_SIZE;
	
	/***
	 * 获取request
	 * @return
	 */
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/***
	 * 获取session
	 * @return
	 */
	public HttpSession getSession(){
		return getRequest().getSession();
	}
	
	public String param(String name, String... def_value) {
		String v = getRequest().getParameter(name);
		return (v != null) ? v : ((def_value.length > 0) ? def_value[0] : null);
	}
	
	public Integer paramInt(String name, Integer... def_value) {
		String v = param(name);
		Integer _v = (def_value.length > 0) ? def_value[0] : null;
		if( v == null ) return _v;
		try {
			_v = Integer.parseInt(v);
		} catch (Exception e) {}
		return _v;
	}
	
	public User getSessionUser(){
		return (User) getSession().getAttribute("user");
	}
	
	public int getSessionUserId(){
		if( getSessionUser() != null ){
			return getSessionUser().getId();
		} 
		return 0;
	}
	
}
