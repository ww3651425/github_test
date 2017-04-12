package com.fashion.spider.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/***
 * 定时任务
 * @author PengJun
 *
 */
@Service
public class QuartzService 
{

	protected static final Logger logger = Logger.getLogger(QuartzService.class);
	
	
	/***
	 * 写入搜索关键词
	 */
	public void flushSearch(){
		
		System.out.println("定时任务 Quartz");
		
	}
	
	
}
