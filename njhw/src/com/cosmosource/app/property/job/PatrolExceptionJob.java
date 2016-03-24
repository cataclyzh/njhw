package com.cosmosource.app.property.job;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cosmosource.app.property.model.Constant;
import com.cosmosource.app.property.service.PatrolRecordManager;
import com.cosmosource.app.threedimensional.service.PropertyManager;
import com.cosmosource.app.wirelessLocation.service.WirelessSqlManager;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;

/**
 * @description: 巡查记录定时任务
 * @author tangtq
 * @date 2013-8-25
 */
public class PatrolExceptionJob {
	
	private PatrolRecordManager patrolRecordManager;
	private WirelessSqlManager wirelessSqlManager;
	private PropertyManager propManager;

	public PropertyManager getPropManager() {
		return propManager;
	}

	public void setPropManager(PropertyManager propManager) {
		this.propManager = propManager;
	}

	public PatrolRecordManager getPatrolRecordManager() {
		return patrolRecordManager;
	}

	public void setPatrolRecordManager(PatrolRecordManager patrolRecordManager) {
		this.patrolRecordManager = patrolRecordManager;
	}

	public WirelessSqlManager getWirelessSqlManager() {
		return wirelessSqlManager;
	}

	public void setWirelessSqlManager(WirelessSqlManager wirelessSqlManager) {
		this.wirelessSqlManager = wirelessSqlManager;
	}

	@SuppressWarnings("unchecked")
	public void execute() throws ParseException {

		/**
		 * 获取所有排班信息跟实际走过路线信息
		 */

		List<Map<String, Object>> schenduleList = patrolRecordManager
				.findListBySql("PropertySQL.getAllPatrolSchenduleInfo", null);
		
		
		if (!schenduleList.isEmpty()) {
			/**
			 * 不做一一比对记录
			 * 直接删除原来所有异常记录
			 * 重新插入异常记录
			 */
			
			patrolRecordManager.deleteBySql("PropertySQL.clearExceptionRecords", null);
		}

		for (Map<String, Object> map : schenduleList) {
			
			/**
			 * 每个排班信息实际走过的路线信息，跟实际走过的路线 根据 员工 ID 跟 时间
			 */

			String userId = map.get("USER_ID").toString();
			String scheduleStartDate = map.get("SCHEDULE_START_DATE")
					.toString();
			String scheduleEndDate = map.get("SCHEDULE_END_DATE").toString();
			String scheduleStartTime = map.get("SCHEDULE_START_TIME")
					.toString();
			String scheduleEndTime = map.get("SCHEDULE_END_TIME").toString();

			// endTime = "2014/01/13 15:00:00";
			// 返回JsonObject.toString() 格式
			
			String result = propManager.patrolData(userId, scheduleStartDate
					+ " " + scheduleStartTime, scheduleEndDate + " "
					+ scheduleEndTime);

			JSONObject jsonObject = JSONObject.fromObject(result);

			/**
			 * jsonObject.put("deviceIds", builder.substring(0,
			 * builder.length()-1)); deviceIds 实际走过的点位信息
			 * 
			 * jsonObject.put("orderPoint",
			 * buffer.substring(0,buffer.length()-1)); orderPoint 理应走过的点位信息
			 */
			String patrolNodes = jsonObject.getString("orderPoint");
//			System.out.println("orderPoint : " + patrolNodes);
			
			String realCrossedPoints = jsonObject.getString("deviceIds");
//			System.out.println("arrivedPoint : " + realCrossedPoints);
			
			if (null == patrolNodes || "".equals(patrolNodes)) {
				continue;
			}

			boolean isNormal = true;
			String isException = "0";
			String exceptionDesc = "未经过的点:";
			String isduty = "1"; //是否缺勤

			if (!patrolNodes.equals(realCrossedPoints)) {
				// 有异常情况
				isNormal = false;
				String[] patrolNodesArray = patrolNodes.split(Constant.SPLIT_COMMA);
				
				if (null == realCrossedPoints || "".equals(realCrossedPoints)) {
					//一个点都没走被视为缺勤
					for (String patrolNodeId : patrolNodesArray) {
							// 未经过的点位信息
							exceptionDesc += patrolNodeId + Constant.SPLIT_LINE;
					}
					
				}else{
					
					//不是缺勤
					isduty = "0";
					
					String[] realCrossedPointsArray = realCrossedPoints.split(Constant.SPLIT_COMMA);
					
					
					//应该走过的点位集合
					List<String> patrolNodesList = new ArrayList<String>();
					
					for (int i = 0; i < patrolNodesArray.length; i++) {
						patrolNodesList.add(patrolNodesArray[i]);
					}
					
					/**
					 * 获取遗漏点位信息
					 * 从应该走过的点位集合当中去除已经走过的点位
					 */
					List<String> realCrossedPointsList = new ArrayList<String>();
					
					for (int i = 0; i < realCrossedPointsArray.length; i++) {
						realCrossedPointsList.add(realCrossedPointsArray[i]);
					}
					
					
					for (int i = 0; i < patrolNodesList.size(); i++) {
						for (int j = 0; j < realCrossedPointsList.size(); j++) {
							if (patrolNodesList.get(i).equals(realCrossedPointsList.get(j))) {
								//当前点已经走过，从原集合去除
								patrolNodesList.remove(i);
							}
						}
					}
					
					for (int i = 0; i < patrolNodesList.size(); i++) {
						exceptionDesc += patrolNodesList.get(i) + Constant.SPLIT_LINE;
					}
					
					
/*					for (String patrolNodeId : patrolNodesArray) {
						for (String crossedPoint : realCrossedPointsArray) {
							if (!crossedPoint.equals(patrolNodeId)) {
								// 未经过的点位信息
								// yyyy-MM-dd HH:mm:ss
								exceptionDesc += patrolNodeId + Constant.SPLIT_LINE;

							}
						}

					}*/
					
					
				}

				if (!isNormal) {
					isException = "1";
				}
				
				
				// 插入异常信息
				patrolRecordManager.addGrPatrolExceptionInfo(
						Long.valueOf(map.get("SCHEDULE_ID").toString()), 
						Long.valueOf(map.get("ORG_ID").toString()), 
						map.get("ORG_NAME").toString(),
						Long.valueOf(userId), 
						map.get("USER_NAME").toString(),
						Long.valueOf(map.get("PATROL_LINE_ID").toString()), 
						map.get("PATROL_LINE_NAME").toString(),
						map.get("PATROL_LINE_DESC") == null ? "" : map.get("PATROL_LINE_DESC").toString(), 
						DateUtil.str2Date(scheduleStartDate + " " + scheduleStartTime,"yyyy/MM/dd HH:mm:ss"), 
						DateUtil.str2Date(scheduleEndDate + " " + scheduleEndTime,"yyyy/MM/dd HH:mm:ss"),
						exceptionDesc.substring(0, exceptionDesc.length() - 1),
						isException,isduty);
			}

		}

		/**
		 * 获取所有已同步的走过点位信息
		 */

		// List pointList =
		// patrolRecordManager.findListBySql("PropertySQL.getAllPointsData",
		// null);

		/*
		 * List<PatrolRecord> patrolRecordList = patrolRecordManager
		 * .getPatrolRecordInfoEveryDay(); SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); for
		 * (PatrolRecord patrolRecord : patrolRecordList) { String cardNo =
		 * patrolRecord.getCardNo(); String tagmac = "";
		 * if(cardNo!=null&&!cardNo.equals("")){ tagmac =
		 * patrolRecordManager.getTagMacByCardno(cardNo); } String patrolNodes =
		 * patrolRecord.getPatrolNodes(); String[] patrolNodesArray =
		 * patrolNodes.split(Constant.SPLIT_COMMA); Date startTime =
		 * patrolRecord.getScheduleStartTime(); Date endTime =
		 * patrolRecord.getScheduleEndTime(); boolean isNormal = true; String
		 * isException = "0"; String exceptionDesc = ""; List<TimeCoordId>
		 * timeCoordIdList = wirelessSqlManager
		 * .getqueryHistoryListObject(tagmac, startTime, endTime);
		 */

		// timeCoordIdList = new ArrayList<TimeCoordId>();
		// TimeCoordId timeCoordId1 = new TimeCoordId();
		// timeCoordId1.setCoordinatesId("201");
		// timeCoordId1.setWriteTime(sdf.format(startTime));
		// timeCoordIdList.add(timeCoordId1);
		//
		// TimeCoordId timeCoordId2 = new TimeCoordId();
		// timeCoordId2.setCoordinatesId("202");
		// timeCoordId2.setWriteTime(sdf.format(startTime));
		// timeCoordIdList.add(timeCoordId2);
		//
		// TimeCoordId timeCoordId3 = new TimeCoordId();
		// timeCoordId3.setCoordinatesId("204");
		// timeCoordId3.setWriteTime(sdf.format(startTime));
		// timeCoordIdList.add(timeCoordId3);

		/*
		 * for (String patrolNodeId : patrolNodesArray) { boolean flag = true;
		 * String accessTimeTemp = "";
		 * if(timeCoordIdList!=null&&timeCoordIdList.size()>0){ for (TimeCoordId
		 * timeCoordId : timeCoordIdList) { if
		 * (timeCoordId.getCoordinatesId().trim().equals( patrolNodeId.trim()))
		 * { Date accessTime = sdf.parse(timeCoordId.getWriteTime()); if
		 * (!(accessTime.before(startTime) || accessTime .after(endTime))) {
		 * flag = true; accessTimeTemp = accessTime.toLocaleString(); break; }
		 * else { flag = false; accessTimeTemp = accessTime.toLocaleString();
		 * break; } } else { flag = false; accessTimeTemp = ""; } } }
		 * 
		 * exceptionDesc += patrolNodeId + Constant.SPLIT_LINE + accessTimeTemp
		 * + Constant.SPLIT_COMMA;
		 * 
		 * isNormal = isNormal & flag;
		 * 
		 * } patrolRecordManager.editGrPatrolRecordInfo(patrolRecord
		 * .getRecordId(), Constant.GR_PATROL_RECORD_IS_DEAL);
		 * 
		 * if (!isNormal){ isException = "1"; }
		 * patrolRecordManager.addGrPatrolExceptionInfo(patrolRecord
		 * .getScheduleId(), patrolRecord.getOrgId(), patrolRecord
		 * .getOrgName(), patrolRecord.getUserId(), patrolRecord .getUserName(),
		 * patrolRecord.getPatrolLineId(), patrolRecord.getPatrolLineName(),
		 * patrolRecord .getPatrolLineDesc(), patrolRecord
		 * .getScheduleStartTime(), patrolRecord .getScheduleEndTime(),
		 * exceptionDesc.substring(0, exceptionDesc.length()-1), isException); }
		 */
	}
}
