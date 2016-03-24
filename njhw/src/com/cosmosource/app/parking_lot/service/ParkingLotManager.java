package com.cosmosource.app.parking_lot.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

public class ParkingLotManager extends BaseManager{
	
	private static final Log log = LogFactory.getLog(ParkingLotManager.class);
	
	/**
	 * 历史定位点
	 * @param cardName
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public JSONArray getPlacecheckHistoryJson(String termId,Date beginTime,Date endTime) throws Exception{
//		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = com.cosmosource.app.utils.SqlserverJdbcUtils.getConnection();
		
//		Connection conn = null ;
		/**
		 * 
		 * sqljdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
			sqljdbc.url=jdbc:sqlserver://10.252.3.220:1433;DatabaseName=nanjing
			sqljdbc.username=sa
			sqljdbc.password=123456
		 * 
		 */
//		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		String url = "jdbc:sqlserver://10.250.41.246:1433;DatabaseName=BSPatrol";
//		conn = DriverManager.getConnection(url,"sa", "333");
		
/*		String
		sb = "select t.CheckDateTime,t.CardName,t.PlaceId,b.Name         "+
			"  from BC_patrolrecord t,bc_place b  	  				"+
//			" where   t.CheckDateTime >= ?  and t.PlaceId =b.ID			"+
			" where   t.CheckDateTime >= ? "+
			"   and t.CheckDateTime <= ?             	"+
			"   and t.TermId = ?                    	"+
			" order by t.CheckDateTime desc           	";*/
		
		//远程服务器查询指定时间段内的，点位信息
		String sql = "select t.CheckDateTime,t.CardName"+
				"  from BC_patrolrecord t"+
				" where   t.CheckDateTime >= ?"+
				"   and t.CheckDateTime <= ?"+
				"   and t.TermId = ?"+
				" order by t.CheckDateTime asc";
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, new java.sql.Timestamp(beginTime.getTime()));
			ps.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
			ps.setString(3, termId);
			rs= ps.executeQuery();
			while(rs.next()){
				String checkDateTime = rs.getTimestamp("CheckDateTime").toString();
				String cardName = rs.getString("CardName").toString();
				jo = new JSONObject();
				jo.put("CheckDateTime",checkDateTime);
				jo.put("CardName",cardName);
//				jo.put("PlaceId",rs.getInt("PlaceId"));
//				jo.put("Name",rs.getInt("Name"));
				ja.add(jo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			com.cosmosource.app.utils.SqlserverJdbcUtils.close(conn, ps, rs);
		}
		return ja;
	}
	
	/**
	 * 实时定位点
	 * @param cardName
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws SQLException 
	 */
	public JSONArray getPlacecheckJson(String cardName,Date beginTime,Date endTime) throws Exception{
//		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = com.cosmosource.app.utils.SqlserverJdbcUtils.getParkingConnection();
		String
		sb = "select *                                 "+
			"  from TermRecord t, placecheckdetail p  "+
			" where t.RecordNumber = p.RecordNumber   "+
			"   and t.TDate >= ?                      "+
			"   and t.TDate <= ?                      "+
			"   and t.TTime >= ?                      "+
			"   and t.TTime <= ?                      "+
			"   and t.CardName = ?                    "+
			" order by Termid, RecordNumber           ";
			 
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setDate(1, new java.sql.Date(beginTime.getTime()));
			ps.setDate(2, new java.sql.Date(endTime.getTime()));
			ps.setDate(3, new java.sql.Date(beginTime.getTime()));
			ps.setDate(4, new java.sql.Date(endTime.getTime()));
			ps.setString(5, cardName);
			rs= ps.executeQuery();
			if(rs.next()){
				Integer tDate = rs.getInt("TDate");
				Integer tTime = rs.getInt("TTime");
				String writeTime = tDate +" " +tTime;
				jo = new JSONObject();
				jo.put("writeTime",writeTime);
				jo.put("coordinatesId",rs.getString("PlaceName"));
				ja.add(jo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		com.cosmosource.app.utils.SqlserverJdbcUtils.close(conn, ps, rs);
		
		return ja;
	}
	
	public static void main(String[] args) throws Exception{
		ParkingLotManager manager = new ParkingLotManager();
		String termId = "1309100009";
		String beginTime = "2014-01-08";
		String endTime = "2014-01-14";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		JSONArray  jsonArray = manager.getPlacecheckHistoryJson(termId, format.parse(beginTime), format.parse(endTime));
		System.out.println(jsonArray.toString());
	}
	

	public String getquerySnoBySname(String sname){
		Map map = new HashMap();
		map.put("sname", sname);
		List<Map> list=null;
		try {
			if(sname!=null && !"".equals(sname))
			{
				list = sqlDao.findList("PropertySQL.selectSnoBySname", map);
				return (String) list.get(0).get("S_NO");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryParkinglotDailyCounts(String date) {
		Map map = new HashMap();
		map.put("date",date);
		return sqlDao.findList("PropertySQL.getDailyCarCount", map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<HashMap> queryAllCounts(Page page) {
		Map map = new HashMap();
		return sqlDao.findPage(page, "PropertySQL.getDailyCarCount", map);
	}
}
