package com.fashion.spider.util;

import java.util.List;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * Freemarker自定义方法
 */
public class EncodeURLMethod implements TemplateMethodModel {

	/***
	 * 执行方法
	 */
	@SuppressWarnings("unchecked")
	public Object exec(List argList) throws TemplateModelException {
		if(argList.size() != 1 )
		{
			throw new TemplateModelException("Wrong arguments!");
		}
		try {
			return java.net.URLEncoder.encode((String)argList.get(0), "UTF-8");
		} catch (Exception e) {
			return (String)argList.get(0);
		}
	}
}