package test.patrol.remote;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.base.util.DateUtil;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class TestRemoteSQLServer {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws Exception{
		TestRemoteSQLServer trss = new TestRemoteSQLServer();
		trss.test();
	}

	public void test() throws Exception{
		log.info("开始远程数据库同步 巡更点位信息...");
		
//		sqljdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
//				sqljdbc.url=jdbc:sqlserver://10.250.41.246:1433;DatabaseName=BSPatrol
//				sqljdbc.username=sa
//				sqljdbc.password=333
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		String url = "jdbc:sqlserver://10.250.41.246:1433;DatabaseName=BSPatrol";
		String name = "sa";
		String pwd = "333";

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = DriverManager.getConnection(url, name, pwd);

		// 获取远程服务器termId
		String sql1 = "select t.TermId as remote_term_id from BC_patrolrecord t group by t.TermId";

		Map<String, Object> remote_map = null;

		List<Map<String, Object>> remote_list = new ArrayList<Map<String, Object>>();

		log.info("开始查询获取远程服务器巡更棒编号...");
		try {

			ps = conn.prepareStatement(sql1);
			rs = ps.executeQuery();

			while (rs.next()) {
				remote_map = new HashMap<String, Object>();
				remote_map.put("remote_term_id", rs.getLong(1));
				remote_list.add(remote_map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		log.info("开始比对远程本地数据...");
		// 获取已经同步过的本地数据
		List<Map<String, Object>> localData = getLocalSynchronizedData();

		List<Map<String, Object>> sqllist = new ArrayList<Map<String, Object>>();
		Map<String, Object> termRecordMap;
		// 遍历 取出需要落取数据的term_id 对应的本地最大recordId
		for (int i = 0; i < remote_list.size(); i++) {
			boolean existFlag = false;
			for (int j = 0; j < localData.size(); j++) {
				Long local_term_id = Long.parseLong(localData.get(j).get(
						"TERM_ID") != null ? localData.get(j).get("TERM_ID")
						.toString() : 0 + "L");
				int local_max_record_id = Integer.parseInt(localData.get(j)
						.get("MAX_RECORD_ID") != null ? localData.get(j)
						.get("MAX_RECORD_ID").toString() : 0 + "");
				if (Long.parseLong(remote_list.get(i).get("remote_term_id")
						.toString()) == local_term_id) {
					termRecordMap = new HashMap<String, Object>();
					termRecordMap.put("termId", local_term_id);
					termRecordMap.put("maxRecordId", local_max_record_id);
					sqllist.add(termRecordMap);
					existFlag = true;
					break;
				}
			}
			if (!existFlag) {
				termRecordMap = new HashMap<String, Object>();
				termRecordMap.put("termId",
						remote_list.get(i).get("remote_term_id").toString());
				termRecordMap.put("maxRecordId", 0);
				sqllist.add(termRecordMap);
			}
		}

		log.info("开始查询需要同步的数据...");
		// 远程服务器查询点位信息 ##where DATEDIFF([day],CheckDateTime,{ fn NOW() })<=30
		String sql2 = "select t.ID,t.TermId,t.RecordId,t.CheckDateTime,t.DealFlag,t.CardName,t.EditFlag"
				+ " from BC_patrolrecord t";

		if (sqllist.size() > 0) {
			sql2 += " where";
			for (int i = 0; i < sqllist.size(); i++) {

				sql2 += " (t.TermId = " + sqllist.get(i).get("termId");
				sql2 += " and t.RecordId > "
						+ sqllist.get(i).get("maxRecordId") + ")";

				if (i + 1 < sqllist.size()) {
					sql2 += " or";
				}
			}
		}
		log.info(sql2);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		try {
			ps = conn.prepareStatement(sql2);
			rs = ps.executeQuery();

			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put("ID", rs.getInt(1));
				map.put("TermId", rs.getLong(2));
				map.put("RecordId", rs.getInt(3));
				map.put("CheckDateTime", DateUtil.date2Str(rs.getTimestamp(4),
						"yyyy-MM-dd HH:mm:ss"));
				map.put("DealFlag", rs.getInt(5));
				map.put("CardName", rs.getString(6));
				map.put("EditFlag", DateUtil.date2Str(rs.getTimestamp(7),
						"yyyy-MM-dd HH:mm:ss"));

				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}

		log.info("开始插入数据...");

		if (!list.isEmpty()) {
			// 插入本地数据库
//			patrolRecordManager.insertBatchBySql(
//					"PropertySQL.insertPatrolSyncData", list);
		}

		log.info("同步结束,同步了" + list.size() + "条数据");
	}
	
	public List<Map<String, Object>> getLocalSynchronizedData() throws Exception{
		Reader reader = Resources.getResourceAsReader ("SqlMapConfig.xml");
		SqlMapClient sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		List<Map<String, Object>> result = sqlMapClient.queryForList("patrol.getLocalSynchronizedData");
		return result;
	}
	
	public void close(Connection conn, PreparedStatement stm, ResultSet rs) {
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
