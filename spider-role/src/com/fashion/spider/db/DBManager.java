package com.fashion.spider.db;

import java.sql.*;
import java.util.*;
import java.lang.reflect.*;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库管理
 */
public class DBManager {

	private final static Log log = LogFactory.getLog(DBManager.class);
	private static boolean show_sql = false;
	private static Properties dbProperties = DBConfig.getInstance()
			.getProperties();

	private static Hashtable<String, DataSource> _dataSourceManager = new Hashtable<String, DataSource>();

	public static DataSource buildDataSource(String key) {
		DataSource dataSource = null;
		try {
			if (key == null || key.equals("")) {
				throw new DBException("未指定数据库:" + key);
			}
			dataSource = _dataSourceManager.get(key);
			if (dataSource == null) {
				synchronized (_dataSourceManager) {
					Properties cp_props = new Properties();
					for (Object k : dbProperties.keySet()) {
						String skey = (String) k;
						if (skey.startsWith(key + ".")) {
							String name = skey.substring(key.length() + 1);
							cp_props.put(name, dbProperties.getProperty(skey));
							if ("show_sql".equalsIgnoreCase(name)) {
								show_sql = "true".equalsIgnoreCase(dbProperties
										.getProperty(skey));
							}
						}
					}
					if (cp_props.size() >= 3) {
						for (Object k : dbProperties.keySet()) {
							String skey = (String) k;
							if (skey.startsWith("jdbc.")) {
								String name = skey.substring(5);
								if (!cp_props.contains(name)) {
									cp_props.put(name,
											dbProperties.getProperty(skey));
								}
							}
						}
						cp_props.put("alias", key + "_mysql");
					} else {
						return buildDataSource(null);
					}

					// oracle
					if (key.equals("group3")) {
						cp_props.put("driver",
								"oracle.jdbc.driver.OracleDriver");
					}

					dataSource = (DataSource) Class.forName(
							cp_props.getProperty("datasource")).newInstance();
					log.info("Using DataSource : "
							+ dataSource.getClass().getName());
					BeanUtils.populate(dataSource, cp_props);
					_dataSourceManager.put(key, dataSource);
				}
			}
		} catch (Exception e) {
			throw new DBException(e);
		}
		return dataSource;
	}

	private final static String getRef(String key) {
		String ref = dbProperties.getProperty(key + ".ref", "");
		if (key.equals("group")) {
			key = "group";
		} else if (key.equals("group2")) {
			key = "group2";
		} else if (key.equals("group3")) {
			key = "group3";
		}else{
			key="group4";
		}
		return key;
	}

	/***
	 * 获取连接
	 * 
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public final static Connection getConnection(String key)
			throws SQLException {

		Connection conn = buildDataSource(getRef(key)).getConnection();

		return (show_sql && !Proxy.isProxyClass(conn.getClass())) ? new _DebugConnection(
				conn).getConnection() : conn;
	}

	/***
	 * 用于跟踪执行的SQL语句
	 * 
	 * @author PengJun
	 */
	static class _DebugConnection implements InvocationHandler {

		private final static Log log = LogFactory
				.getLog(_DebugConnection.class);
		private Connection conn = null;

		public _DebugConnection(Connection conn) {
			this.conn = conn;
		}

		/**
		 * Returns the conn.
		 * 
		 * @return Connection
		 */
		public Connection getConnection() {
			return (Connection) Proxy.newProxyInstance(conn.getClass()
					.getClassLoader(), conn.getClass().getInterfaces(), this);
		}

		public Object invoke(Object proxy, Method m, Object[] args)
				throws Throwable {
			try {
				String method = m.getName();
				if ("prepareStatement".equals(method)
						|| "createStatement".equals(method))
					log.info("[SQL] >>> " + args[0]);
				return m.invoke(conn, args);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
	}

}