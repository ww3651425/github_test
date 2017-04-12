package com.fashion.spider.web.listener;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import com.fashion.spider.util.Property;

/**
 * 启动加载 初始化系统数据
 * @author pengjun
 *
 */
public class ServletContextLoaderListener implements ServletContextListener {
	private static final Logger log = Logger.getLogger(ServletContextLoaderListener.class);
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("start 系统初始化...");
		

		ServletContext servletContext = servletContextEvent.getServletContext();
		String rootPath = servletContext.getRealPath("/");
		Map<String, String> sysMap = Property.getProperties(rootPath + "/WEB-INF/classes/config.properties");
		sysMap.put("rootPath", rootPath);
		servletContext.setAttribute("sysMap", sysMap);
		
		
        log.info("end 系统初始化...");
	}

    public void contextDestroyed(ServletContextEvent servletContextEvent) {  
    	//释放资源
    }   

}
