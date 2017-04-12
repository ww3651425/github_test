package com.fashion.spider.db;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fashion.spider.util.TransientField;

/***
 * 实体 sql 语句转换
 * @author pj
 *
 */
public class BeanProcessor {
	
	private static final HashMap<String, String> Column_Filter = new HashMap<String, String>();
	static{
		Column_Filter.put("class", "");
	}

	/***
	 * insert
	 * @param type
	 * @param pams
	 * @return
	 * @throws SQLException
	 */
	public static String insert(String dbName, Object type, List<Object> pams, String tableName) throws SQLException {
		StringBuffer sql = new StringBuffer("insert into ").append(getTableName(dbName, tableName)).append("(");
		sql.append(getColumns(type, pams, null, ","));
		sql.append(") values(");
		sql.append(toString(pams.size()));
		sql.append(")");
		return sql.toString();
	}
	
	/***
	 * update 
	 * @param type
	 * @param pams
	 * @param pKey
	 * @return
	 * @throws SQLException
	 */
	public static String update(String dbName, Object type, List<Object> pams, String pKey, String tableName) throws SQLException {
		StringBuffer sql = new StringBuffer("update ").append(getTableName(dbName, tableName)).append(" set ");
		sql.append(getColumns(type, pams, pKey, "=?,"));
		sql.append(" where ");
		sql.append(pKey);
		sql.append("=?");
		return sql.toString();
	}
	
	private static String getColumns(Object type, List<Object> pams, String pKey, String op) throws SQLException {
		Class<?> clazz = type.getClass();
		PropertyDescriptor[] props = propertyDescriptors(clazz);
		Method getter;
		Object value;
		StringBuffer sql = new StringBuffer();
		Object pValue = null;
		for (PropertyDescriptor p : props) {
			if( Column_Filter.containsKey(p.getName().toLowerCase()) ){
				continue;
			}
			try {
				Field field = clazz.getDeclaredField(p.getName());
				if(field.isAnnotationPresent(TransientField.class)) {
					continue;
				}
			} catch (Exception e) {
				throw new SQLException("不能获取字段" + p.getName());
			}
			
			if( p.getName().toLowerCase().matches("(.+?)_i$") ){
				continue;
			}
			if( p.getName().toLowerCase().matches("(.+?)_s$") ){
				continue;
			}
			getter = p.getReadMethod();
			try {
				value = getter.invoke(type, new Object[]{});
			} catch (Exception e) {
				throw new SQLException("不能读取" + p.getName() + "的值");
			}
			//if (value != null && !value.equals("")) {
			if (value != null ) {
				if (p.getName().toLowerCase().equals(pKey))
					pValue = value;
				else {
					sql.append(p.getName()).append(op);
					pams.add(value);
				}
			}
		}
		if (pKey != null)
			pams.add(pValue);
		return sql.substring(0, sql.length() - 1);
	}

	/**
	 * 获得 beans PropertyDescriptor。
	 * @param c
	 * @return
	 * @throws SQLException
	 */
	private static PropertyDescriptor[] propertyDescriptors(Class<?> c) throws SQLException {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(c);
		} catch (IntrospectionException e) {
			throw new SQLException("Bean introspection failed: " + e.getMessage());
		}
		return beanInfo.getPropertyDescriptors();
	}
	
	/***
	 * 格式化
	 * @param length
	 * @return
	 */
	public static String toString(int length) {
		if (length == 0 ) return "";
		Object pam = "?";
		StringBuffer buf = new StringBuffer(length * 12);
		for (int i = 0; i < length - 1; i++) {
			buf.append(pam).append(", ");
		}
		return buf.append(pam).toString();
	}
	
	/***
	 * 表名
	 * @param type
	 * @return
	 */
	private static String getTableName(String dbName, String tableName) {
		if( StringUtils.isNotBlank(dbName) ) {
			tableName = dbName + "." + tableName;
		}
		return tableName;
	}
	
}
