package com.fashion.spider.cache.ehcache;

public class EhCacheRegion {

	/***
	 *  timeToLiveSeconds="15"
	 */
	public final static String Live_Sec = "live_sec";
	
	/***
	 * timeToLiveSeconds="900"
	 */
	public final static String Live_Quarter = "live_quarter";
	
	/***
	 * timeToLiveSeconds="3600"
	 */
	public final static String Live_Hour = "live_hour";
	
	/***
	 * timeToLiveSeconds="86400"
	 */
	public final static String Live_Day = "live_day";
	
	/***
	 * 永远不过期
	 * eternal = true
	 */
	public final static String Eternal = "eternal";
	
	
	
}
