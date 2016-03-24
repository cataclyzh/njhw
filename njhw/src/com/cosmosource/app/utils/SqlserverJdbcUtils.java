package com.cosmosource.app.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlserverJdbcUtils {

	private static Properties ps = new Properties();// Properties类表示了一个持久的属性集
	static {
		try {
			
			String classPath = SqlserverJdbcUtils.class.getResource("/").getPath();
			String webInfoTemp = classPath.substring(0, classPath.lastIndexOf("/"));
			String webInfo = classPath.substring(1, webInfoTemp.lastIndexOf("/"));
			
			//for test
			//webInfo = "/home/njhw/webapps/app_njhw/WEB-INF/";
			//webInfo = "D:/workspace3/njhw3/WebRoot/WEB-INF";
			
//			String config  = webInfo + "/conf/jdbc.properties";
			
//			String config  = "C:\\wk\\njhw\\njhw\\WebRoot\\WEB-INF\\conf\\jdbc.properties";
			
			String config  = "/home/njhw/webapps/app_njhw/WEB-INF/conf/jdbc.properties";
			InputStream in = new BufferedInputStream (new FileInputStream(config));

			ps.load(in);// 从输入流中读取属性列表（键和元素对）。
			Class.forName(ps.getProperty("sqljdbc.driver"));// getResourceAsStream查找具有给定名称的资源。
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {// Connection与特定数据库的连接（会话）。
		try {
			
			String url = ps.getProperty("sqljdbc.url");
			String name = ps.getProperty("sqljdbc.username");
			String pwd = ps.getProperty("sqljdbc.password");
			
			return DriverManager.getConnection(url, name, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection getConnectionWireless() {
		try {
			return DriverManager.getConnection(
					ps.getProperty("sqljdbc.wireless.url"),
					ps.getProperty("sqljdbc.wireless.username"), 
					ps.getProperty("sqljdbc.wireless.password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getParkingConnection() {// Connection与特定数据库的连接（会话）。
		try {
			return DriverManager.getConnection(ps.getProperty("sqlparkingjdbc.url"),// DriverManager管理一组
					// JDBC
					// 驱动程序的基本服务。
					
					ps.getProperty("sqlparkingjdbc.username"), ps.getProperty("sqlparkingjdbc.password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn, PreparedStatement stm, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
