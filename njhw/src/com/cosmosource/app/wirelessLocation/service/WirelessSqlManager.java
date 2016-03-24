package com.cosmosource.app.wirelessLocation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.cosmosource.app.utils.SqlserverJdbcUtils;
import com.cosmosource.app.wirelessLocation.model.TimeCoordId;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.DateUtil;

/** 
* @ClassName: WirelessService 
* @Description: 无线定位的操作 
* @author pingxianghua
* @date 2013-8-6 上午09:32:19 
*  
*/
public class WirelessSqlManager extends BaseManager{
	
	private static final Log log = LogFactory.getLog(WirelessSqlManager.class);

	
	 /**
	* @Description 查询历史轨迹
	* @Author：pingxianghua
	* @Date 2013-8-5 下午02:58:53 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	 * @throws SQLException 
	**/
	public JSONArray getqueryHistoryList(String TagMac,Date beginTime,Date endTime){
		Map map = new HashMap();
		map.put("TagMac", TagMac);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn = com.cosmosource.app.utils.SqlserverJdbcUtils.getConnectionWireless();
		StringBuffer sb = new StringBuffer();
		if(TagMac != null && !TagMac.equals("")){
//			sb.append("select history_T.WriteTime,history_T.CoordinatesId "+
//					"from device_Tag device_t "+
//					"left join history_TagPositionLog history_T "+
//					"  on device_t.Id = history_T.TagId "+
//			" where device_t.TagMac = ? ");
			sb.append("select * "+
					"from device_Tag device_t "+
					"left join history_TagPositionLog history_T "+
					"  on device_t.Id = history_T.TagId "+
			" where device_t.TagMac = ? ");
			if(beginTime !=null)
			{
				sb.append( " and history_T.WriteTime>= ? ");
			}
			if(endTime !=null)
			{
				sb.append( " and history_T.WriteTime<= ?");
			}
			sb.append( " order by history_T.WriteTime desc ");
		}
       
		 List<Map> list=null;
		 Map m=null;
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, TagMac);
			if(beginTime !=null)
			{
				ps.setDate(2, new java.sql.Date(beginTime.getTime()));
			}
			if(beginTime !=null && endTime !=null){
				ps.setDate(3, new java.sql.Date(endTime.getTime()));
			}
			
			if(beginTime ==null && endTime !=null){
				ps.setDate(2, new java.sql.Date(endTime.getTime()));
			}
			 rs= ps.executeQuery();
			list = new ArrayList<Map>();
			while(rs.next()){
				m=new HashMap();
				m.put("WriteTime", rs.getTimestamp("WRITETIME"));
				m.put("CoordinatesId", rs.getLong("COORDINATESID"));
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		com.cosmosource.app.utils.SqlserverJdbcUtils.close(conn, ps, rs);
//		List<Map> list = sqlDao.findList("WirelessSql.selectHistoryList", map);
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		Map m2 = null;
		for(int i=0;i<list.size();i++){
			m2 = (Map)list.get(i);
			jo = new JSONObject();
			jo.put("writeTime",DateUtil.date2Str((Date)m2.get("WriteTime"), "yyyy-MM-dd HH:mm:ss"));
			jo.put("coordinatesId",m2.get("CoordinatesId"));
			ja.add(jo);
		}
		return ja;
	}
	
	 /**
	* @Description 查询历史轨迹返回LIST
	* @Author：pingxianghua
	* @Date 2013-8-22 下午04:54:15 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public List<TimeCoordId> getqueryHistoryListObject(String TagMac,Date beginTime,Date endTime){
		Map map = new HashMap();
		map.put("TagMac", TagMac);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		Connection conn = com.cosmosource.app.utils.SqlserverJdbcUtils.getConnection();
		StringBuffer sb = new StringBuffer();
		if(TagMac != null && !TagMac.equals("")){
			sb.append("select history_T.WriteTime,history_T.CoordinatesId "+
					"from device_Tag device_t "+
					"left join history_TagPositionLog history_T "+
					"  on device_t.Id = history_T.TagId "+
			" where device_t.TagMac = ? ");
			if(beginTime !=null)
			{
				sb.append( " and history_T.WriteTime>= ? ");
			}
			if(endTime !=null)
			{
				sb.append( " and history_T.WriteTime<= ?");
			}
			sb.append( " order by history_T.WriteTime desc ");
		}
       
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Map> list=null;
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, TagMac);
			if(beginTime !=null)
			{
				ps.setDate(2, new java.sql.Date(beginTime.getTime()));
			}
			if(beginTime !=null && endTime !=null){
				ps.setDate(3, new java.sql.Date(endTime.getTime()));
			}
			
			if(beginTime ==null && endTime !=null){
				ps.setDate(2, new java.sql.Date(endTime.getTime()));
			}
			
			rs = ps.executeQuery();
			list = new ArrayList<Map>();
			Map m=null;
			while(rs.next()){
				m=new HashMap();
				m.put("WriteTime", rs.getTimestamp("WRITETIME"));
				m.put("CoordinatesId", rs.getLong("COORDINATESID"));
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		com.cosmosource.app.utils.SqlserverJdbcUtils.close(conn, ps, rs);
		List<TimeCoordId> listObject=null;
		Map m2 = null;
		TimeCoordId  tc=null;
		for(int i=0;i<list.size();i++){
			m2 = (Map)list.get(i);
			tc=new TimeCoordId();
//			jo = new JSONObject();
//			jo.put("writeTime",DateUtil.date2Str((Date)m.get("WriteTime"), "yyyy-MM-dd HH:mm:ss"));
//			jo.put("coordinatesId",m.get("CoordinatesId"));
//			ja.put(jo);
			tc.setWriteTime(DateUtil.date2Str((Date)m2.get("WriteTime"), "yyyy-MM-dd HH:mm:ss"));
			tc.setCoordinatesId(m2.get("CoordinatesId").toString());
			listObject.add(tc);
		}
		return listObject;
	}
	
	
	public List getTest(){
		List list = dao.getSession().createSQLQuery("select visitorName from vis_VisitorInfo").addScalar("visitorName", Hibernate.STRING).list();
		return list;
	}
	
	public Map<String,String> getVisitHistory(String tagMac, String beginTime, String endTime){
		Map<String,String> result = new HashMap<String,String>();
		
		Connection conn = SqlserverJdbcUtils.getConnectionWireless();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
//		String tagMac = "B0:8E:1A:01:00:1C";

		sb.append("select c.coordinatesName, CONVERT(varchar, b.WriteTime, 120 ) as WriteTime from device_Tag a, history_TagPositionLog b, map_coordinates c ");
		sb.append("where a.Id = b.TagId and b.coordinatesid = c.id ");
		sb.append("and a.TagMac = ? ");
		sb.append("and CONVERT(varchar, b.WriteTime, 120 ) >= ? ");
		sb.append("and CONVERT(varchar, b.WriteTime, 120 ) <= ? ");
		sb.append("order by c.coordinatesName desc");
		
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, tagMac);
			ps.setString(2, beginTime);
			ps.setString(3, endTime);

			rs = ps.executeQuery();
			while (rs.next()) {
				String pointName = rs.getString("coordinatesName");
				String pointTime = rs.getString("WRITETIME");
				String resultTime = result.get(pointName);
				if(resultTime == null){
					result.put(pointName, pointTime);
				}else{
					result.put(pointName, resultTime+","+pointTime);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally{
			SqlserverJdbcUtils.close(conn, ps, rs);
		}
		
//		Iterator it = result.entrySet().iterator();
//		Map<String,String> newResult = new HashMap<String,String>();
//		while(it.hasNext()){
//			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
//			String placeId = entry.getKey();
//			Map<String,String> pointResult = getPointByPlaceId(placeId);
//			String pointId = pointResult.get("pointId");
//			newResult.put(pointId, result.get(placeId));
//		}
		
		return result;
	}
	
	public Map<String,String> changePointResult(Map<String,String> result){
		Iterator it = result.entrySet().iterator();
		Map<String,String> newResult = new HashMap<String,String>();
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String placeId = entry.getKey();
			Map<String,String> pointResult = getPointByPlaceId(placeId);
			String pointId = pointResult.get("pointId");
			if(pointId != null){
				newResult.put(pointId, result.get(placeId));
			}
			logger.debug(placeId + ":" + pointId);
		}
		return newResult;
	}
	
	private Map getPointByPlaceId(String placeId){
		Map<String,String> result = new HashMap<String,String>();
		try{
			if(placeId != null && placeId.trim().length() != 0){
				//
				String newPlaceId = placeId.substring(placeId.length() - 3);
				Map map = new HashMap();
				map.put("placeId", newPlaceId);
				List<Map> list = sqlDao.findList("WirelessSql.selectFloorByCoordinates", map);
				Map<String,Object> resMap  = new HashMap<String, Object>();
				if(list != null && list.size() > 0){
					resMap = list.get(0);
					String pointId = String.valueOf(resMap.get("ID"));
					String pointName = String.valueOf(resMap.get("PLACE"));
					result.put("pointId", pointId);
					result.put("pointName", pointName);
				}
			}
			//result.put("CoordinatesId", placeId);
		}catch(Exception e){
			log.error(e);
		}
		return result;
	}
}
