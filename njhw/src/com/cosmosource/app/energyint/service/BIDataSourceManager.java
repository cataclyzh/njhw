package com.cosmosource.app.energyint.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.cosmosource.base.listener.StartupListener;
import com.cosmosource.base.util.PropertiesUtil;
import com.eyeq.pivot4j.datasource.PooledOlapDataSource;
import com.eyeq.pivot4j.datasource.SimpleOlapDataSource;

/**
 * BI数据源类
 * 
 */
public class BIDataSourceManager {

	private static final Log log = LogFactory.getLog(BIDataSourceManager.class);

	// BI的schema配置文件
	public static final String BI_DATA_SOURCE_CONFIG = "/com/cosmosource/app/energyint/service/bidatasource.properties";

	private static BIDataSourceManager biDataSourceManager = new BIDataSourceManager();

	private PooledOlapDataSource pooleddataSource = null;

	public PooledOlapDataSource getPooleddataSource() {
		return pooleddataSource;
	}

	public void setPooleddataSource(PooledOlapDataSource pooleddataSource) {
		this.pooleddataSource = pooleddataSource;
	}

	private BIDataSourceManager() {
		initDataSource();
	}

	public static BIDataSourceManager getBIDataSourceManager() {
		return biDataSourceManager;
	}

	/**
	 * 初始化数据源
	 */
	private void initDataSource() {
		if (pooleddataSource == null) {
			String host = PropertiesUtil.getAnyConfigProperty(
					"ei.bi.datasource.host", BI_DATA_SOURCE_CONFIG);
			String database = PropertiesUtil.getAnyConfigProperty(
					"ei.bi.datasource.db", BI_DATA_SOURCE_CONFIG);
			String port = PropertiesUtil.getAnyConfigProperty(
					"ei.bi.datasource.port", BI_DATA_SOURCE_CONFIG);
			String user = PropertiesUtil.getAnyConfigProperty(
					"ei.bi.datasource.user", BI_DATA_SOURCE_CONFIG);
			String password = PropertiesUtil.getAnyConfigProperty(
					"ei.bi.datasource.password", BI_DATA_SOURCE_CONFIG);

			String driverName = "mondrian.olap4j.MondrianOlap4jDriver";

			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				String msg = "Failed to load JDBC driver : " + driverName;
				throw new RuntimeException(msg, e);
			}

			String path = null;
			try {
				path = URLDecoder.decode(this.getClass().getResource("")
						.getPath(), "UTF-8");
			} catch (UnsupportedEncodingException e) {

			}

			String fileName = path + "ENERGY.xml";

			StringBuilder builder = new StringBuilder();
			builder.append("jdbc:mondrian:");
			builder.append("jdbc=jdbc:oracle:thin:");
			builder.append(user + "/" + password);
			builder.append("@(DESCRIPTION=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=10.101.1.107)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.101.1.108)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=orcl))(FAILOVER_MODE=(TYPE=select)(METHOD=basic)))");
			builder.append(";");
			builder.append("JdbcDrivers=oracle.jdbc.driver.OracleDriver;");
			builder.append("Catalog=file:");
			builder.append(fileName);
			builder.append(";");

			String url = builder.toString();

			log.info("能源数据源配置信息 : " + url);

			SimpleOlapDataSource dataSource = new SimpleOlapDataSource();
			dataSource.setConnectionString(url);

			GenericObjectPool.Config config = new GenericObjectPool.Config();
			config.maxActive = 3;
			config.maxIdle = 3;

			pooleddataSource = new PooledOlapDataSource(dataSource, config);

		}
	}
}
