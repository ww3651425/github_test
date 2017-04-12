package com.fashion.spider.cache.ehcache;

/**
 * 缓存异常
 */
@SuppressWarnings("serial")
public class CacheException extends RuntimeException {

	public CacheException(String s) {
		super(s);
	}

	public CacheException(String s, Throwable e) {
		super(s, e);
	}

	public CacheException(Throwable e) {
		super(e);
	}
	
}
