package com.fashion.spider.db;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fashion.spider.cache.ehcache.CacheManager;
import com.fashion.spider.util.Constants;
import com.fashion.spider.util.Page;
import com.fashion.spider.util.ThreadLocalUtil;

@SuppressWarnings("unchecked")
public abstract class BaseDao<T> {

	protected static final Log log = LogFactory.getLog(BaseDao.class);

	private Class<T> entityClass;

	/**
	 * 主键ID
	 */
	protected static final String PRIMARY_KEY = "id";

	/***
	 * 实例
	 */
	
	public BaseDao() {
		Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		this.entityClass = (Class<T>) actualTypeArguments[0];
	}


	/***
	 * 添加某个对象
	 * 
	 * @param entity
	 * @return
	 */
	protected int add(String db, T entity, String tableName) {
		return QueryHelper.add(db, entity, tableName);
	}

	/***
	 * 修改某个对象
	 * 
	 * @param entity
	 * @return
	 */
	protected int update(String db, T entity, String tableName) {
		return QueryHelper.save(db, entity, PRIMARY_KEY, tableName);
	}
	
	/***
	 * 修改某个对象
	 * 
	 * @param entity
	 * @return
	 */
	protected int update(String db, T entity, String tableName, String pKey) {
		return QueryHelper.save(db, entity, pKey, tableName);
	}


	/**
	 * 执行INSERT/UPDATE/DELETE语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String db, String sql, Object... params) {
	
		return QueryHelper.update(db, sql, params);
	}

	/**
	 * 读取某个对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public T read(String db, String sql, Object... params) {
		return QueryHelper.read(entityClass, db, sql, params);
	}

	/**
	 * 执行统计查询语句，语句的执行结果必须只返回一个数字
	 * 
	 * @param db
	 * @param sql
	 * @param params
	 * @return
	 */
	protected long stat(String db, String sql, Object... params) {
		return QueryHelper.stat(db, sql, params);
	}

	/***
	 * 查询列表
	 * 
	 * @param key
	 * @param sql
	 * @param params
	 * @return
	 */
	protected List<T> query_entity(String db, String sql, Object... params) {
		return QueryHelper.query(entityClass, db, sql, params);
	}

	/***
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> query(String db, String sql, Object... args) {
		return QueryHelper.query(db, sql, args);
	}

	public List<T> queryList(String db, String sql, Object... args) {
		return QueryHelper.query(entityClass, db, sql, args);
	}

	/**
	 * 支持缓存的对象查询
	 * 
	 * @param <T>
	 * @param beanClass
	 * @param dbkey
	 * @param cache_region
	 * @param key
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<T> query_cache(String db, String cache_region, Serializable key, String sql, Object... params) {
		List<T> objs = (List<T>) CacheManager.get(cache_region, key);
		if (objs == null || objs.size() == 0) {
			objs = queryList(db, sql, params);
			CacheManager.set(cache_region, key, (Serializable) objs);
		}
		return objs;
	}

	/***
	 * 分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	protected Page<T> query_page(String db, int pageNo, int pageSize, String sqlString, final Object... values) {
		Page<T> page = new Page<T>(pageNo, pageSize);
		String fromClause = sqlString.substring(sqlString.indexOf("from"));
		String countSql = "select count(*) " + fromClause;
		Long totalCount = stat(db, countSql, values);
		page.setTotalCount(totalCount.intValue());
		if (totalCount > 0) {
			List<T> list = QueryHelper.query_slice(entityClass, db, sqlString, pageNo, pageSize, values);
			page.setList(list);
		}
		return page;
	}

	/***
	 * 分页查询
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	protected Page<Map<String, Object>> query_map_page(String db, int pageNo, int pageSize, String sqlString,
			final Object... values) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageNo, pageSize);
		String fromClause = sqlString.substring(sqlString.indexOf("from"));
		String countSql = "select count(*) " + fromClause;
		Long totalCount = stat(db, countSql, values);
		page.setTotalCount(totalCount.intValue());
		if (totalCount > 0) {
			List<Map<String, Object>> list = QueryHelper.query_slice(db, sqlString, pageNo, pageSize, values);
			page.setList(list);
		}
		return page;
	}

	/***
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> queryMaps_cache(String db, String cache_region, Serializable key, String sql,
			Object... params) {
		List<Map<String, Object>> objs = (List<Map<String, Object>>) CacheManager.get(cache_region, key);
		if (objs == null || objs.size() == 0) {
			objs = query(db, sql, params);
			CacheManager.set(cache_region, key, (Serializable) objs);
		}
		return objs;
	}


	/**
	 * 分页
	 * 
	 * @param beanClass
	 * @param key
	 * @param sql
	 * @param page
	 * @param count
	 * @param params
	 * @return
	 */
	public List<T> query_slice(String db, String sql, int page, int count, Object... params) {
		return QueryHelper.query_slice(entityClass, db, sql, page, count, params);
	}
	
	/***
	 * 批量执行sql
	 * 
	 * @param db
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected int[] batSQL(String db, String sql[]) throws SQLException {
		return QueryHelper.batSQL(db, sql);
	}

}