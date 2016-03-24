package com.cosmosource.app.property.service;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.cosmosource.app.entity.GrPatrolLine;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * @description: 巡查线路的增删改查接口
 * @author tangtq
 * @date 2013-7-8 11:47:16
 */
public class PatrolLineManager extends BaseManager {
	
	private static SqlMapClient sqlMapClient;
	
	public PatrolLineManager(){
		if(sqlMapClient == null){
			init();
		}
	}
	
	private void init(){
		try{
			logger.info("=== PatrolLineManager init start ===");
			String resource = "com/cosmosource/app/property/SqlMapConfig.xml";
			//String resource = "/home/njhw/webapps/app_njhw/WEB-INF/classes/com/cosmosource/app/property/SqlMapConfig.xml";
			Reader reader = Resources.getResourceAsReader (resource);
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
			logger.info("=== PatrolLineManager init end ===");
		}catch(Exception e){
			logger.error("初始化SqlMapClient出错", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @title: loadGrPatrolLineInfo
	 * @description: 得到巡查线路
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:48:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolLine> loadGrPatrolLineInfo() {
		return dao.findByHQL("select t from GrPatrolLine t");
	}

	/**
	 * 
	 * @title: addGrPatrolLineInfo
	 * @description: 插入巡查线路信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrPatrolLineInfo(String patrolLineName,String patrolLineDesc,String patrolNodes) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("patrolLineId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_PATROL_LINE_SEQ_VALUE"))));
			parMap.put("patrolLineName", patrolLineName);
			parMap.put("patrolLineDesc", patrolLineDesc);
			parMap.put("patrolNodes", patrolNodes);
			parMap.put("isAvaliable", Constant.GR_PATROL_LINE_STATE_RUNNING);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertPatrolLine", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: editGrPatrolLineInfo
	 * @description: 编辑巡查线路信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean editGrPatrolLineInfo(Long patrolLineId,String patrolLineName,String patrolLineDesc,String patrolNodes) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("patrolLineName", patrolLineName);
			parMap.put("patrolLineDesc", patrolLineDesc);
			parMap.put("patrolNodes", patrolNodes);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updatePatrolLine", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: deleteGrPatrolLineInfo
	 * @description: 删除巡查线路信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrPatrolLineInfo(Long patrolLineId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("isAvaliable", Constant.GR_PATROL_LINE_STATE_STOPPED);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updatePatrolLineState", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: initGrPatrolLineInfo
	 * @description: 初始化巡查线路
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolLine> initGrPatrolLineInfo() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrPatrolLine> patrolLineList = new ArrayList<GrPatrolLine>();
		try {
			Map parMap = new HashMap();
			resultList = sqlDao.findList("PropertySQL.selectPatrolLineInfo",parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrPatrolLine grPatrolLine = new GrPatrolLine();
					if (resultList.get(i).get("PATROL_LINE_ID") != null) {
						Long patrolLineTempId = Long.parseLong(String
								.valueOf(resultList.get(i).get("PATROL_LINE_ID")));
						grPatrolLine.setPatrolLineId(patrolLineTempId);
					}

					if (resultList.get(i).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String
								.valueOf(resultList.get(i).get("PATROL_LINE_NAME"));
						grPatrolLine.setPatrolLineName(patrolLineName);
					}
					
					if (resultList.get(i).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String
								.valueOf(resultList.get(i).get("PATROL_LINE_DESC"));
						grPatrolLine.setPatrolLineDesc(patrolLineDesc);
					}
					
					if (resultList.get(i).get("PATROL_NODES") != null) {
						String patrolNodes = String
								.valueOf(resultList.get(i).get("PATROL_NODES"));
						grPatrolLine.setPatrolNodes(patrolNodes);
					}
					
					if (resultList.get(i).get("IS_AVALIABLE") != null) {
						String isAvaliable = String
								.valueOf(resultList.get(i).get("IS_AVALIABLE"));
						grPatrolLine.setIsAvaliable(isAvaliable);
					}

					patrolLineList.add(grPatrolLine);
				}

			}
		} catch (Exception e) {
		}

		return patrolLineList;
	}

	public List<Map> findConfictList(String userNames,String patrolLineName,String startTime, String endTime)
	{
		Map vMap = new HashMap();
		List<Map> list = new ArrayList();

		vMap.put("userNames", userNames);
		vMap.put("patrolLineName", patrolLineName);
		vMap.put("beginTime", startTime);
		vMap.put("endTime", endTime);

		list = sqlDao.findList("PropertySQL.findConfictList", vMap);
		return list;
	}
	
	public List<Map> findConfictListById(Long userId,Long patrolLineID,String startTime, String endTime)
	{
		Map vMap = new HashMap();
		List<Map> list = new ArrayList();

		vMap.put("userId", userId);
		vMap.put("patrolLineID", patrolLineID);
		vMap.put("startTime", startTime.replaceAll("-", "\\/"));
		vMap.put("endTime", endTime.replaceAll("-", "\\/"));

		list = sqlDao.findList("PropertySQL.findConfictListById", vMap);
		return list;
	}
	
	/**
	 * 
	 * @title: loadGrPatrolLineInfoByPatrolLineId
	 * @description: 根据线路ID得到巡查线路
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrPatrolLine loadGrPatrolLineInfoByPatrolLineId(Long patrolLineId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrPatrolLine grPatrolLine = new GrPatrolLine();
		try {
			Map parMap = new HashMap();
			parMap.put("patrolLineId", patrolLineId);
			resultList = sqlDao.findList(
					"PropertySQL.selectPatrolLineInfoByScheduleLineId", parMap);
			if (resultList.size() > 0) {
				if (resultList.get(0).get("PATROL_LINE_ID") != null) {
					Long patrolLineTempId = Long.parseLong(String
							.valueOf(resultList.get(0).get("PATROL_LINE_ID")));
					grPatrolLine.setPatrolLineId(patrolLineTempId);
				}

				if (resultList.get(0).get("PATROL_LINE_NAME") != null) {
					String patrolLineName = String.valueOf(resultList.get(0)
							.get("PATROL_LINE_NAME"));
					grPatrolLine.setPatrolLineName(patrolLineName);
				}

				if (resultList.get(0).get("PATROL_LINE_DESC") != null) {
					String patrolLineDesc = String.valueOf(resultList.get(0)
							.get("PATROL_LINE_DESC"));
					grPatrolLine.setPatrolLineDesc(patrolLineDesc);
				}

				if (resultList.get(0).get("PATROL_NODES") != null) {
					String patrolNodes = String.valueOf(resultList.get(0).get(
							"PATROL_NODES"));
					grPatrolLine.setPatrolNodes(patrolNodes);
				}
				
				if (resultList.get(0).get("IS_AVALIABLE") != null) {
					String isAvaliable = String
							.valueOf(resultList.get(0).get("IS_AVALIABLE"));
					grPatrolLine.setIsAvaliable(isAvaliable);
				}
			}
		} catch (Exception e) {
		}

		return grPatrolLine;
	}

	/**
	 * 
	 * @title: getGrPatrolLineList
	 * @description: 分页得到线路信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrPatrolLineList(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectAllPatrolLine",
				parMap);
	}

	/**
	 * 
	 * @title: getOrgInfo
	 * @description: 初始化单位信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Org> getOrgInfo() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<Org> orgList = new ArrayList<Org>();
		try {
			resultList = sqlDao.findList("PropertySQL.selectAllOrgInfo", null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Org org = new Org();
					if (resultList.get(i).get("ORG_ID") != null) {
						org.setOrgId(Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID"))));
					}

					if (resultList.get(i).get("NAME") != null) {
						org.setName(String.valueOf(resultList.get(i)
								.get("NAME")));
					}
					orgList.add(org);
				}
			}
		} catch (Exception e) {
		}
		return orgList;
	}
	
	public List<Map> getHistoryPosition(Map param) throws Exception{
		logger.info("=== getHistoryPosition start ===");
		logger.info("sqlMapClient: " + sqlMapClient);
		logger.info("param: " + param);
		List<Map> result = sqlMapClient.queryForList("MsWirelessSql.getPositionHistory", param);
		logger.info("=== getHistoryPosition end ===");
		return result;
	}
	
	public List<Map> getHistoryPosition2(Map param) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		System.out.println("=== conn start ===");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection(
				"jdbc:sqlserver://10.250.252.150:1433;DatabaseName=nanjing", "sa", "123456");
		System.out.println("=== conn end ===");

//		SELECT A.TAGMAC, B.WRITETIME, C.COORDINATESNAME 
//		FROM DEVICE_TAG A, HISTORY_TAGPOSITIONLOG B, MAP_COORDINATES C
//		WHERE A.ID = B.TAGID AND B.COORDINATESID = C.ID
//		<dynamic>
//			<isNotEmpty property="tagMac" prepend="and">
//				a.tagMac = #tagMac#
//			</isNotEmpty>
//			<isNotEmpty property="startTime" prepend="and" >
//			<![CDATA[	
//				b.writetime >= #startTime#
//			]]>
//			</isNotEmpty>
//			<isNotEmpty property="endTime" prepend="and" >
//			<![CDATA[
//				b.writetime <= #endTime#
//			]]>				
//			</isNotEmpty>
//		</dynamic>
//		order by b.WriteTime desc
		
//		String coordinatesName = String.valueOf(m.get("COORDINATESNAME"));
//		String tagMac = String.valueOf(m.get("TAGMAC"));
//		String time = String.valueOf(m.get("WRITETIME"));
//		Date d1 = (Date)m.get("WRITETIME");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.TAGMAC, B.WRITETIME, C.COORDINATESNAME ");
		sql.append("FROM DEVICE_TAG A, HISTORY_TAGPOSITIONLOG B, MAP_COORDINATES C ");
		sql.append("WHERE A.ID = B.TAGID AND B.COORDINATESID = C.ID ");
		
		String tagMac = null;
		if(param.get("tagMac") != null){
			tagMac = param.get("tagMac").toString();
			sql.append("AND a.tagMac = '" + tagMac + "' ");
		}
		
//		String startTime = null;
//		if(param.get("startTime") != null){
//			startTime = param.get("startTime").toString();
//			sql.append("AND a.writetime >= " + startTime + " ");
//		}
//		
//		String endTime = null;
//		if(param.get("endTime") != null){
//			endTime = param.get("endTime").toString();
//			sql.append("AND a.writetime <= " + endTime + " ");
//		}
		
		sql.append("order by b.WriteTime desc");

		List<Map> result = new ArrayList<Map>();
		try {
			System.out.println("=== execute sql start ===");
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			System.out.println("=== execute sql end ===");
			while (rs.next()) {
//				String coordinatesName = String.valueOf(m.get("COORDINATESNAME"));
//				String tagMac = String.valueOf(m.get("TAGMAC"));
//				String time = String.valueOf(m.get("WRITETIME"));
//				Date d1 = (Date)m.get("WRITETIME");
				Map m = new HashMap();
				m.put("TAGMAC", rs.getString(1));
				m.put("WRITETIME", rs.getString(2));
				m.put("COORDINATESNAME", rs.getString(3));
				
				result.add(m);
			}
		} catch (SQLException e) {
			logger.error("查询定位服务器MSSQL错误", e);
			throw e;
		} finally {
			com.cosmosource.app.utils.SqlserverJdbcUtils.close(conn, ps, rs);
		}
		return result;
	}
	
	public List<Map> getPositionUser(String userName){
		List<Map> result = new ArrayList<Map>();
		
		Map param = new HashMap();
		param.put("userName", userName);
		
		result = sqlDao.getSqlMapClientTemplate()
				.queryForList("PropertySQL.getPositionUser", param);
		
		return result;
	}
	
	public Map getPositionName(String placeId){
		Map result = new HashMap();
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
		return result;
	}
}
