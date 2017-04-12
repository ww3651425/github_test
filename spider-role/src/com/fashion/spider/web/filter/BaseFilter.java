package com.fashion.spider.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * 项目默认过滤器，过滤所有路径
 * 
 * @author 彭军
 * 
 */
public class BaseFilter implements Filter {
	private static final Logger log = Logger.getLogger(BaseFilter.class);

	public void destroy() {
		log.debug("destroy BaseFilter");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("init BaseFilter");
	}
}
