package com.cosmosource.app.threedimensional.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cosmosource.app.parking_lot.service.ParkingLotManager;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;

public class PropertyManager extends BaseManager {

	private ParkingLotManager parkingLotManager;
	
	// 根据部门ID取人员信息
	@SuppressWarnings("unchecked")
	public List<HashMap> getUserByOrg(Long orgId) {
		Map parMap = new HashMap();
		List<HashMap> resultList = new ArrayList<HashMap>();
		parMap.put("orgId", orgId);
		resultList = sqlDao.findList(
				"ThreeDimensionalSQL.SELECT_USER_BY_ORGID", parMap);

		return resultList;
	}

	// 根据人员信息取当班时段
	@SuppressWarnings("unchecked")
	public List<HashMap> getTimeByUser(Long orgId, Long userId) {
		Map parMap = new HashMap();
		List<HashMap> resultList = new ArrayList<HashMap>();
		parMap.put("userId", userId);
		parMap.put("orgId", orgId);
		resultList = sqlDao.findList(
				"ThreeDimensionalSQL.SELECT_TIME_BY_USERID", parMap);

		return resultList;
	}

	// 根据部门ID取当班路线
	@SuppressWarnings("unchecked")
	public List<HashMap> getLineByOrg(Long orgId) {
		Map parMap = new HashMap();
		List<HashMap> resultList = new ArrayList<HashMap>();
		parMap.put("orgId", orgId);
		resultList = sqlDao.findList(
				"ThreeDimensionalSQL.SELECT_PATROLLINE_BY_ORGID", parMap);

		return resultList;
	}

	// 取人员信息
	@SuppressWarnings("unchecked")
	public List<HashMap> getUserByLine(Long orgId, Long lineId) {
		Map parMap = new HashMap();
		List<HashMap> resultList = new ArrayList<HashMap>();
		parMap.put("orgId", orgId);
		parMap.put("lineId", lineId);
		resultList = sqlDao.findList("ThreeDimensionalSQL.SELECT_USER_BY_LINE",
				parMap);

		return resultList;
	}

	// 取当班时段
	@SuppressWarnings("unchecked")
	public List<HashMap> getTime(Long orgId, Long lineId, Long userId) {
		Map parMap = new HashMap();
		List<HashMap> resultList = new ArrayList<HashMap>();
		parMap.put("orgId", orgId);
		parMap.put("lineId", lineId);
		parMap.put("userId", userId);
		resultList = sqlDao.findList("ThreeDimensionalSQL.SELECT_TIME", parMap);

		return resultList;
	}

	// 取巡查路线信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<HashMap> patrolExceptionList(final Page<HashMap> page) {
		
		Page<HashMap> resultPage = sqlDao.findPage(page,
				"ThreeDimensionalSQL.SELECT_PATROL_EXCEPTION", null);
		
		List<HashMap> resultList = resultPage.getResult();
		
		if (resultList != null) {
			
			for (int i = 0; i < resultList.size(); i++) {
			HashMap map = resultList.get(i);
			
			/**
			 * 数据库字段是是否缺勤标志
			 * 是否缺勤(1:是；0:不是)
			 */
			if ("1".equals(map.get("RES_BAK1").toString())) {
				//缺勤 
				resultList.get(i).put("EXCEPTION", "缺勤");
			}else{
				//出勤
				resultList.get(i).put("EXCEPTION", "未到达");
			}
			
	/*			for (int i = 0; i < resultList.size(); i++) {
					String[] desc = ((String) resultList.get(i).get("DESCRIBE"))
							.split(",");
					if (desc != null) {
						String[] temp;
						int flag = 0;
						for (int j = 0; j < desc.length; j++) {
							temp = desc[j].split("|");
							if (temp[1] != null && temp[1].trim().length() > 0) {
								flag = 1;
							}
						}
						if (flag == 0) {
							resultList.get(i).put("EXCEPTION", "缺勤");
						} else {
							resultList.get(i).put("EXCEPTION", "未到达");
						}
					}
				}*/
			}
		}
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getPropertyInfo(String staffID,
			String beginTime, String endTime) {
		List<HashMap<String, String>> staffList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();

		// Date beginTimes = sdf.parse(beginTime);
		// Date endTimes = sdf.parse(endTime);
		paramMap.put("staffID", staffID); // 人员 ID
		paramMap.put("beginTime", beginTime); // 开始时间
		paramMap.put("endTime", endTime); // 结束时间
		try {
			staffList = sqlDao
					.findList(
							"ThreeDimensionalSQL.SELECT_PROPERTY_STAFF_INFO_BY_NAME_AND_TIME",
							paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return staffList;
	}

	// 取巡查异常详情
	@SuppressWarnings("unchecked")
	public HashMap patrolExceptionDetail(Long scheduleId,String startTime,String endTime) {
		Map parMap = new HashMap();
		List<HashMap> resultList = new ArrayList<HashMap>();
		parMap.put("scheduleId", scheduleId);
		parMap.put("startTime", startTime);
		parMap.put("endTime", endTime);
		resultList = sqlDao.findList(
				"ThreeDimensionalSQL.SELECT_PATROL_EXCEPTION_BY_USER", parMap);
		
		// 查询结果来自于 PatrolExceptionJob 定时任务
		if (resultList != null && resultList.size() > 0) {
			HashMap map = resultList.get(0);
			String exceptionDesc = (String) map.get("DESCRIBE");
			String[] describe = exceptionDesc.split(":")[1].split("\\|");
			String nodes = "";
			int flag = 0;
			
			//未经过的点:2000234|2000234...
			if (describe != null && describe.length > 0) {
				for (int i = 0; i < describe.length; i++) {
//					String key = describe[i].substring(0,describe[i].indexOf("|"));
						nodes += (describe[i] + ",");
					
/*					String time = describe[i].substring(describe[i].indexOf("|") + 1, describe[i].length());
					if (key != null && time != null) {
						if (time.trim().length() == 0) {
						} else {
							flag = 1;
						}
					}*/
				}
			}
			
			/**
			 * 数据库字段是是否缺勤标志
			 * 是否缺勤(1:是；0:不是)
			 */
			if ("1".equals(map.get("RES_BAK1").toString())) {
				//缺勤 
				map.put("ISDUTY", "是");
			}else{
				//出勤
				map.put("ISDUTY", "否");
			}
			
			if (nodes.trim().length() > 0) {
				nodes = nodes.substring(0, (nodes.length() - 1));
			}
			map.put("NODES", nodes);
			
/*			if (flag == 0) {
				map.put("ISDUTY", "否");
			} else {
				map.put("ISDUTY", "是");
			}*/
			return map;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String patrolData(String staffID,
			String beginTime, String endTime) {
		
		List<Map<String, String>> resultList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("staffID", staffID); // 人员 ID
		try {
			
			/**
			 * 巡更棒号 ，根据人员找出对应的巡更棒号
			 * 执勤人员，执勤路线，路线描述
			 * GR_PATROL_LINE 巡更路线
			 * PropertySQL.getOrgInfoByUserId  部门信息
			 * PropertySQL.checkPatrolStickList  巡更棒分配信息
			 * SELECT * FROM GR_PATROL_STICK_user 巡更棒管理
			 *  SELECT * from GR_PATROL_STICK 
			 */
			Object termId = null;
			Object orgName = null;
			Object staffName = null;
			Map map =  null;
			List<Map> list = sqlDao.findList("ThreeDimensionalSQL.getPatrolStickByUserId", paramMap);
			
			JSONObject jsonObject = new JSONObject();
			
			if (!list.isEmpty()) {
				map =  list.get(0);
				termId  = map.get("S_NO");
				orgName = map.get("SHORT_NAME");
				staffName = map.get("DISPLAY_NAME");
				//部门信息
				jsonObject.put("orgName", orgName);
				//人员名称
				jsonObject.put("staffName", staffName.toString().replaceAll("\\s*", ""));
			}
			
			/**
			 * 路线、路线描述 gr_patrol_schedule 表 
			 * 查询被排班人路线信息
			 * 还包括路线经过的相应的点位信息
			 */
			
			/**
                  info.push("执勤人员： ,"+json.staffName);
                  info.push("所属部门： , "+json.orgName);
                  info.push("执勤路线： ,"+json.lineName);
                  info.push("路线描述： ,"+json.lineDescription);
                  Security.ShowInfo = info;
                  //Security.PathId = "1"; TH修改接口后不需要PathId需要OrderPoint
                  Security.OrderPoint = "10199,10197,10188"; 
                  Security.OrderPoint = "10199,10197,10188"; 
                  Security.DeviceIds = json.orderPoint;
                  Security.DeviceIds = json.deviceIds;
			 * 
			 * 
			 */
			resultList= sqlDao.findList("ThreeDimensionalSQL.getPatrolLineInfoByUserId", paramMap);
			
			//应该经过的点
			String orderPoint = "";
			//实际走过的点
			String deviceIds = "";
			if (!resultList.isEmpty()) {
				map =  resultList.get(0);
				String lineName = (String) map.get("PATROL_LINE_NAME");
				String lineDescription = (String) map.get("PATROL_LINE_DESC");
				
				// 2000234,2000235,2000232,2000233,2000236... 预设点位
				String shouldCrossPoints = (String) map.get("PATROL_NODES");
				jsonObject.put("lineName", lineName);
				jsonObject.put("lineDescription", lineDescription);
				
				StringBuffer buffer =  new StringBuffer();
				for (String str : shouldCrossPoints.split(",")) {
					buffer.append(str+",");
				}
				orderPoint = buffer.substring(0,buffer.length()-1);
			}
			jsonObject.put("orderPoint", orderPoint);
			
			/**
			 * [{"CheckDateTime":"2014-01-13 14:42:26.0","CardName":"67008B2766"},....
			 * 远程数据库获取历史点位信息 --> 修改为本地数据库获取数据，远程数据库定时同步数据
			 */
			
//			JSONArray jsonArray = parkingLotManager.getPlacecheckHistoryJson(termId != null ? termId.toString() : "",
//					DateUtil.str2Date(beginTime, "yyyy/MM/dd HH:mm:ss"), DateUtil.str2Date(endTime, "yyyy/MM/dd HH:mm:ss"));
			
			paramMap.clear();
			paramMap.put("termId", termId);
			paramMap.put("beginTime", beginTime);
			paramMap.put("endTime", endTime);
			
			/**
			 *  根据 CardName 值，从 patrol 表获取 点位 信息
			 *  获取的点位信息为实际走过的路线
			 */
			resultList = sqlDao.findList("ThreeDimensionalSQL.getPatrolLineHistoryPoints", paramMap);
			
			Iterator iterator = resultList.iterator();
//			List<String> realCrossedPoints = new ArrayList<String>();
			Set<String> realCrossedPoints = new TreeSet<String>();
			Map tmpMap = null;
			
			while (iterator.hasNext()) {
				tmpMap = (Map) iterator.next();
				realCrossedPoints.add(tmpMap.get("POINT_ID").toString()); 
			}
			
			// 排班路线跟实际路线点位比较
			String[] array4Point = orderPoint.split(","); //应该经过的点位
			
			StringBuilder builder  = new StringBuilder(); //最后返回的实际走过点位信息
			
			for (int i = 0; i < array4Point.length; i++) {
				for (String str : realCrossedPoints) {
					if (array4Point[i].equals(str)) {
						//走过的计划中的点
						builder.append(str + ",");
					}
				}
			}
			
			if (builder.length() > 1) {
				deviceIds = builder.substring(0, builder.length()-1);
			}
			jsonObject.put("deviceIds", deviceIds);
			
			/**
			 *  [{POINT_ID=2000225, CARD_NAME=650000CBEC, POSITION=负二层1号点}, 
			 *  获取跟 cardNameList 一一对应的 point_id
			 *  cardNameList : [67008B2766, 67008B67FD, 67008B2766, ...
			 */
/*			List realCrossedPoints = sqlDao.findList("ThreeDimensionalSQL.getPointIDByCardName", paramMap);
			
			String deviceIds = null;
			
			if (realCrossedPoints.size() != 0) {
				StringBuilder builder  = new StringBuilder();
				Iterator<Map> iter = null;
				paramMap.clear();
				
				for (Object object : cardNameList) {
					iter = realCrossedPoints.iterator();
					while (iter.hasNext()) {
						paramMap = (Map) iter.next();
						if (paramMap.get("CARD_NAME").equals(object)) {
							builder.append(paramMap.get("POINT_ID")+",");
							break;
						}
					}
				}
				
				deviceIds = builder.substring(0, builder.length()-1);
				
				if (StringUtil.isNotBlank(orderPoint) && StringUtil.isNotBlank(deviceIds)) {
					
					String[] array4Point = orderPoint.split(","); //应该经过的点位
					
					StringBuffer buffer = new StringBuffer();
					
					for (int j = 0; j < array4Point.length; j++) {
						if (deviceIds.contains(array4Point[j])) {
							buffer.append(array4Point[j] + ",");
						}
					}
					jsonObject.put("deviceIds", buffer.substring(0, builder.length()-1));
				}
			}*/
			
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ParkingLotManager getParkingLotManager() {
		return parkingLotManager;
	}

	public void setParkingLotManager(ParkingLotManager parkingLotManager) {
		this.parkingLotManager = parkingLotManager;
	}
}
