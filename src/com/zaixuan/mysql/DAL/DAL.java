package com.zaixuan.mysql.DAL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import snaq.db.ConnectionPool;
import snaq.db.ConnectionPoolManager;
/*
 * mysql dal operation
 */
public class DAL {
	
	private static ConnectionPoolManager cpm = null;
	public static void initConnectionPoolManager(String FilePath) throws IOException {

		// 取连接池管理器，用文件初始化
		try {
			cpm = ConnectionPoolManager.getInstance(new File(
					FilePath));
		} catch (IOException ex) {
			ex.printStackTrace();
			cpm = null;
			throw new IOException("cann't get connection");
		}
	}
	
	/**
	 * 取得连接池对象函�?	 * @param str
	 * @return ConnectionPool
	 * @throws SQLException
	 */
	public static synchronized Connection getConnection(String PropFilePath, String poolStr)
			throws SQLException {

		// 初始化连接池管理�?		try {
			if (cpm == null) {
				initConnectionPoolManager(PropFilePath);
			}
		} catch (IOException e) {
			throw new SQLException(e.getMessage());
		}

		// 从管理器中取�?��连接池，使用配置文件中的命名
		ConnectionPool pool = cpm.getPool(poolStr);
		if (pool == null) {
			throw new SQLException("Get Connection pool error: " + poolStr);
		}

		// 从连接池中连�?		Connection con = pool.getConnection();
		if (con == null) {
			throw new SQLException("Invalid DB Connection: " + poolStr);
		}
		return con;
	}
	
}
