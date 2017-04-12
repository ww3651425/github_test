package com.fashion.spider.util;

import java.util.Map;

import javax.servlet.ServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * spring 工具类
 *
 */
public class SpringUtil {

	/**
	 * 通过spring得到ServletContext
	 * @return
	 */
	public static ServletContext getServletContext() {
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		return servletContext;
	}
	
	/**
	 * 得到真实路径
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path) {
		ServletContext servletContext = getServletContext();
		if (StringUtil.isBlank(path)) {
			path = "/";
		}
		return servletContext.getRealPath(path);
	}
	
	/***
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public static <T> T getBean(Class<T> entityClass) {
		ServletContext servletContext = getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		return webApplicationContext.getBean(entityClass);
	}
	
	/**
	 * 得到sysMap中的值
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getAttr(String key) {
		Map<String, String> map = (Map<String, String>) SpringUtil.getServletContext().getAttribute("sysMap");
		return map.get(key);
	}
	
}
