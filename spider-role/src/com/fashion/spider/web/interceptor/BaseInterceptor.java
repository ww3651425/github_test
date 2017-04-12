package com.fashion.spider.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fashion.spider.util.ThreadLocalUtil;

/**
 * 自定义拦截器
 * @author pengjun
 * @date Nov 5, 2011
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 最后执行，可用于释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		ThreadLocalUtil.remove();
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * 显示视图前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * Controller之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {	
		
		return super.preHandle(request, response, handler);
	}
	
	
}