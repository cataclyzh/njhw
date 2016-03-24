package test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.cosmosource.base.util.DateUtil;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class TestVisitorHistory {

	@Test
	public void testGetHistory() throws Exception {
		String tagMac = "B0:8E:1A:01:00:OF";
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = com.cosmosource.app.utils.SqlserverJdbcUtils.getConnectionWireless();
//		sqljdbc.wireless.url=jdbc:sqlserver://10.252.3.220:1433;DatabaseName=nanjing
//		sqljdbc.wireless.username=sa
//		sqljdbc.wireless.password=123456
		Connection conn = DriverManager.getConnection(
				"jdbc:sqlserver://10.252.3.220:1433;DatabaseName=nanjing",
				"sa",
				"123456");
		
		StringBuffer sb = new StringBuffer();
		if (tagMac != null && !tagMac.equals("")) {
			// sb.append("select history_T.WriteTime,history_T.CoordinatesId "+
			// "from device_Tag device_t "+
			// "left join history_TagPositionLog history_T "+
			// "  on device_t.Id = history_T.TagId "+
			// " where device_t.TagMac = ? ");
			sb.append("select * " + "from device_Tag device_t " + "left join history_TagPositionLog history_T "
					+ "  on device_t.Id = history_T.TagId " + " where device_t.TagMac = ? ");
			// if(beginTime !=null)
			// {
			// sb.append( " and history_T.WriteTime>= ? ");
			// }
			// if(endTime !=null)
			// {
			// sb.append( " and history_T.WriteTime<= ?");
			// }
			sb.append(" order by history_T.WriteTime desc ");
		}

		List<Map> list = null;
		Map m = null;
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, tagMac);
			// if(beginTime !=null)
			// {
			// ps.setDate(2, new java.sql.Date(beginTime.getTime()));
			// }
			// if(beginTime !=null && endTime !=null){
			// ps.setDate(3, new java.sql.Date(endTime.getTime()));
			// }
			//
			// if(beginTime ==null && endTime !=null){
			// ps.setDate(2, new java.sql.Date(endTime.getTime()));
			// }
			rs = ps.executeQuery();
			list = new ArrayList<Map>();
			while (rs.next()) {
				m = new HashMap();
				m.put("WriteTime", rs.getTimestamp("WRITETIME"));
				m.put("CoordinatesId", rs.getLong("COORDINATESID"));
				list.add(m);

				System.out.println("###" + m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){
				}
			}
			if(ps != null){
				try{
					ps.close();
				}catch(Exception e){
				}
			}
			if(rs != null){
				try{
					rs.close();
				}catch(Exception e){
				}
			}
		}
	}
	
	@Test
	public void testGetHistoryByiBatis() throws Exception{
		String resource = "com/cosmosource/app/property/SqlMapConfig.xml";
		Reader reader = Resources.getResourceAsReader (resource);
		SqlMapClient sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		String startTimeStr = "2014-07-01 00:00:00";
		String endTimeStr = "2014-07-31 23:59:59";
//		SimpleDateFormat format = new SimpleDateFormat("");
		
		Date st = DateUtils.parseDate(startTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
		Date et = DateUtils.parseDate(endTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
		
		Map param = new HashMap();
//		param.put("tagMac", "B0:8E:1A:01:00:0C");
		param.put("startTime", st);
		param.put("endTime", et);
		List<Map> result = sqlMapClient.queryForList("MsWirelessSql.getPositionHistory", param);
		for(Map m: result){
			System.out.println("###"+m);
		}
		System.out.println("count: " + result.size());
	}
}
