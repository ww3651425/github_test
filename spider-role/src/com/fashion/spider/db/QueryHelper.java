package com.fashion.spider.db;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fashion.spider.cache.ehcache.CacheManager;


/***
 * 数据库查询助手
 * 
 * @author 彭军
 *
 */
@SuppressWarnings({ "unchecked", "serial" })
public class QueryHelper {

	private final static Log log = LogFactory.getLog(QueryHelper.class);

	private final static QueryRunner _g_runner = new QueryRunner();
	private final static ColumnListHandler _g_columnListHandler = new ColumnListHandler() {
		@Override
		protected Object handleRow(ResultSet rs) throws SQLException {
			Object obj = super.handleRow(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}

	};
	private final static ScalarHandler _g_scaleHandler = new ScalarHandler() {
		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Object obj = super.handle(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}
	};
	/***
	 * 基本类型
	 */
	private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>() {
		{
			add(Long.class);
			add(Integer.class);
			add(String.class);
			add(java.util.Date.class);
			add(java.sql.Date.class);
			add(java.sql.Timestamp.class);
		}
	};

	/***
	 * 基本类型判断 int, double, float, long, short, boolean, byte, char， void
	 * 
	 * @param cls
	 * @return
	 */
	private final static boolean _IsPrimitive(Class<?> cls) {
		return cls.isPrimitive() || PrimitiveClasses.contains(cls);
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection(String key) {
		try {
			return Configurations.getConnection(key);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public final static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			log.error("Unabled to close connection!!! ", e);
		}
	}

	/**
	 * 读取某个对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T read(Class<T> beanClass, String key, String sql, Object... params) {
		Connection conn = getConnection(key);
		try {
			return (T) _g_runner.query(conn, sql,
					_IsPrimitive(beanClass) ? _g_scaleHandler : new BeanHandler(beanClass), params);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * 对象查询
	 * 
	 * @param <T>
	 * @param beanClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> query(Class<T> beanClass, String key, String sql, Object... params) {
		Connection conn = getConnection(key);
		try {
			return (List<T>) _g_runner.query(conn, sql,
					_IsPrimitive(beanClass) ? _g_columnListHandler : new BeanListHandler(beanClass), params);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}

	// /***
	// * 注意关闭连接
	// * @param <T>
	// * @param conn
	// * @param beanClass
	// * @param sql
	// * @param params
	// * @return
	// */
	// public static <T> List<T> query(Connection conn, Class<T> beanClass,
	// String sql, Object...params) {
	// try{
	// return (List<T>)_g_runner.query(conn, sql,
	// _IsPrimitive(beanClass)?_g_columnListHandler:new
	// BeanListHandler(beanClass), params);
	// }catch(SQLException e){
	// throw new DBException(e);
	// }
	// }

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
	public static <T> List<T> query_cache(Class<T> beanClass, String dbkey, String cache_region, Serializable key,
			String sql, Object... params) {
		List<T> objs = (List<T>) CacheManager.get(cache_region, key);
		if (objs == null) {
			objs = query(beanClass, dbkey, sql, params);
			CacheManager.set(cache_region, key, (Serializable) objs);
		}
		return objs;
	}

	/***
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 */
	public static List<Map<String, Object>> query(String key, String sql, Object... args) {
		Connection conn = getConnection(key);
		try {
			return _g_runner.query(conn, sql, new MapListHandler(), args);
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}

	public static List<Map<String, Object>> query_cache(String dbkey, String cache_region, Serializable key, String sql,
			Object... params) {
		List<Map<String, Object>> objs = (List<Map<String, Object>>) CacheManager.get(cache_region, key);
		if (objs == null) {
			objs = query(dbkey, sql, params);
			CacheManager.set(cache_region, key, (Serializable) objs);
		}
		return objs;
	}

	public static Map<String, Object> read(String key, String sql, Object... args) {
		Connection conn = getConnection(key);
		try {
			return _g_runner.query(conn, sql, new MapHandler(), args);
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}

	public static Map<String, Object> read_cache(String dbkey, String cache_region, Serializable key, String sql,
			Object... params) {
		Map<String, Object> objs = (Map<String, Object>) CacheManager.get(cache_region, key);
		if (objs == null) {
			objs = read(dbkey, sql, params);
			CacheManager.set(cache_region, key, (Serializable) objs);
		}
		return objs;
	}

	/**
	 * 分页查询
	 * 
	 * @param <T>
	 * @param beanClass
	 * @param sql
	 * @param page
	 * @param count
	 * @param params
	 * @return
	 */
	public static <T> List<T> query_slice(Class<T> beanClass, String key, String sql, int page, int count,
			Object... params) {
		if (page < 0 || count < 0)
			throw new IllegalArgumentException("Illegal parameter of 'page' or 'count', Must be positive.");
		int from = (page - 1) * count;
		count = (count > 0) ? count : Integer.MAX_VALUE;

		return query(beanClass, key, sql + " LIMIT ?,?", ArrayUtils.addAll(params, new Object[] { from, count }));
	}

	/***
	 * 
	 * @param key
	 * @param sql
	 * @param page
	 * @param count
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> query_slice(String key, String sql, int page, int count, Object... params) {
		if (page < 0 || count < 0)
			throw new IllegalArgumentException("Illegal parameter of 'page' or 'count', Must be positive.");
		int from = (page - 1) * count;
		count = (count > 0) ? count : Integer.MAX_VALUE;
		return query(key, sql + " LIMIT ?,?", ArrayUtils.addAll(params, new Object[] { from, count }));
	}

	/**
	 * 执行统计查询语句，语句的执行结果必须只返回一个数值
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static long stat(String key, String sql, Object... params) {
		Connection conn = getConnection(key);
		try {
			Number num = (Number) _g_runner.query(conn, sql, _g_scaleHandler, params);
			return (num != null) ? num.longValue() : -1;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * 执行INSERT/UPDATE/DELETE语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String key, String sql, Object... params) {
		Connection conn = getConnection(key);
		try {
			return _g_runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}

	/***
	 * 执行INSER 语句
	 * 
	 * @param sql
	 * @param params
	 * @return 主键自增
	 */
	public static int insert(String key, String sql, Object... params) {
		Connection conn = getConnection(key);
		int id = 0;
		boolean pmdKnownBroken = false;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			fillStatement(stmt, pmdKnownBroken, params);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			id = rs.next() ? rs.getInt(1) : -1;
		} catch (Exception e) {
			StringBuffer param = new StringBuffer("param:");
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					param.append(params[i] + ",");
				}
			}
			log.error("DBException " + key + " sql:" + sql + " " + param.toString());
			e.printStackTrace();
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
		return id;
	}

	private static void fillStatement(PreparedStatement stmt, boolean pmdKnownBroken, Object[] params)
			throws SQLException {
		if (params == null) {
			return;
		}
		ParameterMetaData pmd = null;
		if (!pmdKnownBroken) {
			pmd = stmt.getParameterMetaData();
			if (pmd.getParameterCount() < params.length) {
				throw new SQLException(
						"Too many parameters: expected " + pmd.getParameterCount() + ", was given " + params.length);
			}
		}
		for (int i = 0; i < params.length; i++) {
			// logger.debug(params[i]);
			if (params[i] != null) {
				stmt.setObject(i + 1, params[i]);
			} else {
				// VARCHAR works with many drivers regardless
				// of the actual column type. Oddly, NULL and
				// OTHER don't work with Oracle's drivers.
				int sqlType = Types.VARCHAR;
				if (!pmdKnownBroken) {
					try {
						sqlType = pmd.getParameterType(i + 1);
					} catch (SQLException e) {
						pmdKnownBroken = true;
					}
				}
				stmt.setNull(i + 1, sqlType);
			}
		}
	}

	/**
	 * 批量执行指定的SQL语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int[] batch(String key, String sql, Object[][] params) {
		Connection conn = getConnection(key);
		try {
			return _g_runner.batch(conn, sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}
	
	/***
	 * 
	 * @param key
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static int[] batSQL(String db, String sql[]) throws SQLException {
		Connection conn = getConnection(db);
		try {
			conn.setAutoCommit(false);
			Statement statement = conn.createStatement();
			for (String s : sql) {
				statement.addBatch(s);
			}
			int result[] = statement.executeBatch();
			conn.commit();
			return result;
		} catch (SQLException e) {
			conn.rollback();
			throw new DBException(e);
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * 添加实体
	 * 
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public static int add(String key, Object entity, String tableName) {
		List<Object> pams = new ArrayList<Object>();
		String sql = "";
		try {
			sql = BeanProcessor.insert(key, entity, pams, tableName);
			return insert(key, sql, pams.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 更新
	 * 
	 * @param key
	 * @param entity
	 * @param pKey
	 *            主键
	 * @return
	 */
	public static int save(String key, Object entity, String pKey, String tableName) {
		List<Object> pams = new ArrayList<Object>();
		try {
			String sql = BeanProcessor.update(key, entity, pams, pKey, tableName);
			return update(key, sql, pams.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}