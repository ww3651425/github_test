package com.fashion.spider.db;

import java.sql.Connection;
import java.sql.SQLException;


public class Configurations {
	
	public final static Connection getConnection(String key) throws SQLException {
		return DBManager.getConnection(key);
	}
}
