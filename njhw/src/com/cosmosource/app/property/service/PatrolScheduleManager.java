package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrPatrolSchedule;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;

/**
 * @description: 巡查排班计划的增删改查接口
 * @author tangtq
 * @date 2013-7-8 10:28:49
 */
public class PatrolScheduleManager extends BaseManager {

	/**
	 * 
	 * @title: loadGrPatrolScheduleInfo
	 * @description: 得到巡查排班记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:48:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolSchedule> loadGrPatrolScheduleInfo() {
		return dao.findByHQL("select t from GrPatrolSchedule t");
	}
	
	/**
	 * 获取预插入排班表  sequence 值
	 * @return SEQ_GR_PATROL_SCHEDULE.Nextval
	 */
	public Long getSequenceNextValue(){
		return Long.parseLong(String.valueOf(sqlDao
				.getSqlMapClientTemplate().queryForObject(
						"PropertySQL.GET_GR_PATROL_SCHEDULE_SEQ_VALUE")));
	}
	

	/**
	 * 
	 * @title: addGrPatrolScheduleInfo
	 * @description: 插入巡查排班信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean addGrPatrolScheduleInfo(Long sequenceNextVal,Long orgId,
			String orgName, Long userId, String userName, Long patrolLineId,
			String patrolLineName, String patrolLineDesc, String patrolNodes,
			String scheduleStartDate, String scheduleEndDate,
			String scheduleStartTime, String scheduleEndTime,
			String scheduleDesc,String patrolScheduleState) {
//		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			List parList = new ArrayList();
			parMap.put("scheduleId", sequenceNextVal);
/*			parMap.put("scheduleId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_PATROL_SCHEDULE_SEQ_VALUE"))));*/
			parMap.put("orgId", orgId);
			parMap.put("orgName", orgName);
			parMap.put("userId", userId);
			parMap.put("userName", userName);
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("patrolLineName", patrolLineName);
			parMap.put("patrolLineDesc", patrolLineDesc);
			parMap.put("patrolNodes", patrolNodes);
			parMap.put("scheduleStartDate", scheduleStartDate);
			parMap.put("scheduleEndDate", scheduleEndDate);
			parMap.put("scheduleStartTime", scheduleStartTime);
			parMap.put("scheduleEndTime", scheduleEndTime);
			parMap.put("scheduleDesc", scheduleDesc);
			parMap.put("patrolScheduleState", patrolScheduleState);
			parList.add(parMap);
			sqlDao.batchInsert("PropertySQL.insertPatrolSchedule", parList);
//			sqlDao.findList("PropertySQL.insertPatrolSchedule", parMap);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 按天拆分排班日期，插入数据库
	 * @param sequenceNextVal
	 * @param scheduleStartDate
	 * @param scheduleEndDate
	 * @param scheduleEndTime 
	 * @param scheduleStartTime 
	 * @param startTime 
	 * @param endTime
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addPatrolScheduleTimeSplit(Long sequenceNextVal,
			String scheduleStartDate, String scheduleEndDate, 
			String scheduleStartTime, String scheduleEndTime) {
		
		//判断是否跨天,24小时制直接比较大小
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date before = DateUtil.str2Date(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " " + scheduleStartTime, pattern);
		Date after = DateUtil.str2Date(DateUtil.date2Str(new Date(), "yyyy-MM-dd") + " " + scheduleEndTime, pattern);
		boolean crossNight = true;
		List<Date> dayList = null;
		Map paramMap = null;
		List<Map> list = new ArrayList<Map>();
		
		/**
		 * 插入数据记录判断
		 * 如果是跨天则插入的数据要少一条：如
		 * 2014-01-06 23:00:00 至 2014-01-07 08:00:00  一条数据
		 * 2014-01-06 08:00:00 至 2014-01-07 18:00:00  两条数据
		 */
		if (before.getTime() > after.getTime()) {
			//获取间隔日期集合
			dayList = DateUtil.getDiffDaysList(
					scheduleStartDate, scheduleEndDate, "yyyy-MM-dd",crossNight);
			
			//插入记录格式为 2014-01-06 23:00:00  2014-01-07 08:00:00
			for (Date date : dayList) {
				paramMap = new HashMap();
				paramMap.put("schedule_ID", sequenceNextVal);
				paramMap.put("schedule_start_date", DateUtil.date2Str(date, "yyyy/MM/dd"));
				paramMap.put("schedule_end_date", DateUtil.date2Str(DateUtil.getNextDate(date), "yyyy/MM/dd"));
				paramMap.put("schedule_start_time", scheduleStartTime);
				paramMap.put("schedule_end_time", scheduleEndTime);
				list.add(paramMap);
			}
		}else {
			dayList = DateUtil.getDiffDaysList(
					scheduleStartDate, scheduleEndDate, "yyyy-MM-dd",!crossNight);
			//插入记录格式为 2014-01-06 08:00:00  2014-01-06 18:00:00
			for (Date date : dayList) {
				paramMap = new HashMap();
				paramMap.put("schedule_ID", sequenceNextVal);
				paramMap.put("schedule_start_date", DateUtil.date2Str(date, "yyyy/MM/dd"));
				paramMap.put("schedule_end_date", DateUtil.date2Str(date, "yyyy/MM/dd"));
				paramMap.put("schedule_start_time", scheduleStartTime);
				paramMap.put("schedule_end_time", scheduleEndTime);
				list.add(paramMap);
			}
		}
		sqlDao.batchInsert("PropertySQL.insertPatrolScheduleTimeSplit", list);
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addPatrolScheduleUserSplit(Long sequenceNextVal,Long userID){
		
		List<Map> list = new ArrayList<Map>();
		Map paramMap = new HashMap();
		paramMap.put("user_id", userID);
		paramMap.put("schedule_id", sequenceNextVal);
		list.add(paramMap);
		sqlDao.batchInsert("PropertySQL.insertPatrolScheduleUserSplit", list);
		
	}
	/**
	 * 
	 * @title: editGrPatrolScheduleInfo
	 * @description: 编辑巡查排班信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean editGrPatrolScheduleInfo(Long scheduleId,
			Long patrolLineId, String scheduleStartDate,
			String scheduleEndDate, String scheduleStartTime,
			String scheduleEndTime, String scheduleDesc) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("scheduleId", scheduleId);
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("scheduleStartDate", scheduleStartDate);
			parMap.put("scheduleEndDate", scheduleEndDate);
			parMap.put("scheduleStartTime", scheduleStartTime);
			parMap.put("scheduleEndTime", scheduleEndTime);
			parMap.put("scheduleDesc", scheduleDesc);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updatePatrolSchedule", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: deleteGrPatrolScheduleInfo
	 * @description: 删除巡查排班信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrPatrolScheduleInfo(Long scheduleId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("scheduleId", scheduleId);
			parMap.put("patrolScheduleState", Constant.GR_PATROL_SCHEDULE_STATE_STOPPED);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.stopPatrolSchedule", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: initGrPatrolScheduleInfo
	 * @description: 初始化巡查班次记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolSchedule> initGrPatrolScheduleInfo() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrPatrolSchedule> patrolScheduleList = new ArrayList<GrPatrolSchedule>();
		try {
			Map parMap = new HashMap();
			resultList = sqlDao.findList(
					"PropertySQL.selectPatrolScheduleInfo",
					parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrPatrolSchedule grPatrolSchedule = new GrPatrolSchedule();

					if (resultList.get(i).get("SCHEDULE_ID") != null) {
						Long scheduleIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("SCHEDULE_ID")));
						grPatrolSchedule.setScheduleId(scheduleIdTemp);
					}

					if (resultList.get(i).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID")));
						grPatrolSchedule.setOrgId(orgId);
					}

					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(i).get(
								"ORG_NAME"));
						grPatrolSchedule.setOrgName(orgName);
					}

					if (resultList.get(i).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(i).get("USER_ID")));
						grPatrolSchedule.setUserId(userId);
					}

					if (resultList.get(i).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(i).get(
								"USER_NAME"));
						grPatrolSchedule.setUserName(userName);
					}

					if (resultList.get(i).get("PATROL_LINE_ID") != null) {
						Long patrolLineId = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("PATROL_LINE_ID")));
						grPatrolSchedule.setPatrolLineId(patrolLineId);
					}

					if (resultList.get(i).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String.valueOf(resultList
								.get(i).get("PATROL_LINE_NAME"));
						grPatrolSchedule.setPatrolLineName(patrolLineName);
					}

					if (resultList.get(i).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String.valueOf(resultList
								.get(i).get("PATROL_LINE_DESC"));
						grPatrolSchedule.setPatrolLineDesc(patrolLineDesc);
					}

					if (resultList.get(i).get("PATROL_NODE_ID") != null) {
						String patrolNodes = String.valueOf(resultList.get(i).get("PATROL_NODE_ID"));
						grPatrolSchedule.setPatrolNodes(patrolNodes);
					}

					if (resultList.get(0).get("SCHEDULE_START_DATE") != null) {
						Date scheduleStartDate = (Date) resultList.get(0).get(
								"SCHEDULE_START_DATE");
						grPatrolSchedule
								.setScheduleStartDate(scheduleStartDate);
					}

					if (resultList.get(0).get("SCHEDULE_END_DATE") != null) {
						Date scheduleEndDate = (Date) resultList.get(0).get(
								"SCHEDULE_END_DATE");
						grPatrolSchedule.setScheduleEndDate(scheduleEndDate);
					}

					if (resultList.get(0).get("SCHEDULE_START_TIME") != null) {
						String scheduleStartTime = String.valueOf(resultList.get(0).get(
								"SCHEDULE_START_TIME"));
						grPatrolSchedule
								.setScheduleStartTime(scheduleStartTime);
					}

					if (resultList.get(0).get("SCHEDULE_END_TIME") != null) {
						String scheduleEndTime = String.valueOf(resultList.get(0).get(
								"SCHEDULE_END_TIME"));
						grPatrolSchedule.setScheduleEndTime(scheduleEndTime);
					}

					if (resultList.get(i).get("SCHEDULE_DESC") != null) {
						String scheduleDesc = String.valueOf(resultList.get(i)
								.get("SCHEDULE_DESC"));
						grPatrolSchedule.setScheduleDesc(scheduleDesc);
					}
					
					if (resultList.get(i).get("PATROL_SCHEDULE_STATE") != null) {
						String patrolScheduleState = String.valueOf(resultList.get(i)
								.get("PATROL_SCHEDULE_STATE"));
						grPatrolSchedule.setPatrolScheduleState(patrolScheduleState);
					}
					patrolScheduleList.add(grPatrolSchedule);
				}

			}
		} catch (Exception e) {
		}

		return patrolScheduleList;
	}

	
	/**
	 * 
	 * @title: initGrPatrolScheduleInfo
	 * @description: 每日获取当天班次信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolSchedule> getGrPatrolScheduleInfoEveryDay() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrPatrolSchedule> patrolScheduleList = new ArrayList<GrPatrolSchedule>();
		try {
			Map parMap = new HashMap();
			resultList = sqlDao.findList(
					"PropertySQL.selectPatrolScheduleInfoEveryDay",
					parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrPatrolSchedule grPatrolSchedule = new GrPatrolSchedule();

					if (resultList.get(i).get("SCHEDULE_ID") != null) {
						Long scheduleIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("SCHEDULE_ID")));
						grPatrolSchedule.setScheduleId(scheduleIdTemp);
					}

					if (resultList.get(i).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID")));
						grPatrolSchedule.setOrgId(orgId);
					}

					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(i).get(
								"ORG_NAME"));
						grPatrolSchedule.setOrgName(orgName);
					}

					if (resultList.get(i).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(i).get("USER_ID")));
						grPatrolSchedule.setUserId(userId);
					}

					if (resultList.get(i).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(i).get(
								"USER_NAME"));
						grPatrolSchedule.setUserName(userName);
					}

					if (resultList.get(i).get("PATROL_LINE_ID") != null) {
						Long patrolLineId = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("PATROL_LINE_ID")));
						grPatrolSchedule.setPatrolLineId(patrolLineId);
					}

					if (resultList.get(i).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String.valueOf(resultList
								.get(i).get("PATROL_LINE_NAME"));
						grPatrolSchedule.setPatrolLineName(patrolLineName);
					}

					if (resultList.get(i).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String.valueOf(resultList
								.get(i).get("PATROL_LINE_DESC"));
						grPatrolSchedule.setPatrolLineDesc(patrolLineDesc);
					}

					if (resultList.get(i).get("PATROL_NODES") != null) {
						String patrolNodes = String.valueOf(resultList.get(i).get("PATROL_NODES"));
						grPatrolSchedule.setPatrolNodes(patrolNodes);
					}

					if (resultList.get(i).get("SCHEDULE_START_DATE_TIME") != null) {
						String scheduleStartDateTime = String.valueOf(resultList.get(i).get(
								"SCHEDULE_START_DATE_TIME"));
						grPatrolSchedule
								.setScheduleStartTime(scheduleStartDateTime);
					}

					if (resultList.get(i).get("SCHEDULE_END_DATE_TIME") != null) {
						String scheduleEndDateTime = String.valueOf(resultList.get(i).get(
								"SCHEDULE_END_DATE_TIME"));
						grPatrolSchedule.setScheduleEndTime(scheduleEndDateTime);
					}

					if (resultList.get(i).get("SCHEDULE_DESC") != null) {
						String scheduleDesc = String.valueOf(resultList.get(i)
								.get("SCHEDULE_DESC"));
						grPatrolSchedule.setScheduleDesc(scheduleDesc);
					}
					
					if (resultList.get(i).get("PATROL_SCHEDULE_STATE") != null) {
						String patrolScheduleState = String.valueOf(resultList.get(i)
								.get("PATROL_SCHEDULE_STATE"));
						grPatrolSchedule.setPatrolScheduleState(patrolScheduleState);
					}
					patrolScheduleList.add(grPatrolSchedule);
				}

			}
		} catch (Exception e) {
		}

		return patrolScheduleList;
	}
	
	
	/**
	 * 
	 * @title: loadGrPatrolScheduleInfoByScheduleId
	 * @description: 根据班次ID得到巡查班次记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrPatrolSchedule loadGrPatrolScheduleInfoByScheduleId(
			Long scheduleId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrPatrolSchedule grPatrolSchedule = new GrPatrolSchedule();
		try {
			Map parMap = new HashMap();
			parMap.put("scheduleId", scheduleId);
			resultList = sqlDao.findList(
					"PropertySQL.selectPatrolScheduleInfoByScheduleId",
					parMap);
			if (resultList.size() > 0) {

					if (resultList.get(0).get("SCHEDULE_ID") != null) {
						Long scheduleIdTemp = Long.parseLong(String
								.valueOf(resultList.get(0).get("SCHEDULE_ID")));
						grPatrolSchedule.setScheduleId(scheduleIdTemp);
					}

					if (resultList.get(0).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(0).get("ORG_ID")));
						grPatrolSchedule.setOrgId(orgId);
					}

					if (resultList.get(0).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(0).get(
								"ORG_NAME"));
						grPatrolSchedule.setOrgName(orgName);
					}

					if (resultList.get(0).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(0).get("USER_ID")));
						grPatrolSchedule.setUserId(userId);
					}

					if (resultList.get(0).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(0).get(
								"USER_NAME"));
						grPatrolSchedule.setUserName(userName);
					}

					if (resultList.get(0).get("PATROL_LINE_ID") != null) {
						Long patrolLineId = Long.parseLong(String
								.valueOf(resultList.get(0)
										.get("PATROL_LINE_ID")));
						grPatrolSchedule.setPatrolLineId(patrolLineId);
					}

					if (resultList.get(0).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String.valueOf(resultList
								.get(0).get("PATROL_LINE_NAME"));
						grPatrolSchedule.setPatrolLineName(patrolLineName);
					}

					if (resultList.get(0).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String.valueOf(resultList
								.get(0).get("PATROL_LINE_DESC"));
						grPatrolSchedule.setPatrolLineDesc(patrolLineDesc);
					}

					if (resultList.get(0).get("PATROL_NODES") != null) {
						String patrolNodes = String.valueOf(resultList.get(0).get("PATROL_NODES"));
						grPatrolSchedule.setPatrolNodes(patrolNodes);
					}

					
					if (resultList.get(0).get("SCHEDULE_START_DATE") != null) {
						Date scheduleStartDate = (Date) resultList.get(0).get(
								"SCHEDULE_START_DATE");
						grPatrolSchedule
								.setScheduleStartDate(scheduleStartDate);
					}

					if (resultList.get(0).get("SCHEDULE_END_DATE") != null) {
						Date scheduleEndDate = (Date) resultList.get(0).get(
								"SCHEDULE_END_DATE");
						grPatrolSchedule.setScheduleEndDate(scheduleEndDate);
					}

					if (resultList.get(0).get("SCHEDULE_START_TIME") != null) {
						String scheduleStartTime = String.valueOf(resultList.get(0).get(
								"SCHEDULE_START_TIME"));
						grPatrolSchedule
								.setScheduleStartTime(scheduleStartTime);
					}

					if (resultList.get(0).get("SCHEDULE_END_TIME") != null) {
						String scheduleEndTime = String.valueOf(resultList.get(0).get(
								"SCHEDULE_END_TIME"));
						grPatrolSchedule.setScheduleEndTime(scheduleEndTime);
					}

					if (resultList.get(0).get("SCHEDULE_DESC") != null) {
						String scheduleDesc = String.valueOf(resultList.get(0)
								.get("SCHEDULE_DESC"));
						grPatrolSchedule.setScheduleDesc(scheduleDesc);
					}
					
					if (resultList.get(0).get("PATROL_SCHEDULE_STATE") != null) {
						String patrolScheduleState = String.valueOf(resultList.get(0)
								.get("PATROL_SCHEDULE_STATE"));
						grPatrolSchedule.setPatrolScheduleState(patrolScheduleState);
					}

			}
		} catch (Exception e) {
		}

		return grPatrolSchedule;
	}
	
	/**
	 * 
	 * @title: getOrgInfoByUserId
	 * @description: 根据员工ID获取部门信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public HashMap getOrgInfoByUserId(
			Long userId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		HashMap resultMap = new HashMap();
		try {
			Map parMap = new HashMap();
			parMap.put("userId", userId);
			resultList = sqlDao.findList(
					"PropertySQL.getOrgInfoByUserId",
					parMap);
			if (resultList.size() > 0) {

					if (resultList.get(0).get("ORG_ID") != null) {
						Long orgId  = Long.parseLong(String
								.valueOf(resultList.get(0).get("ORG_ID")));
						resultMap.put("orgId", orgId);
					}

					if (resultList.get(0).get("NAME") != null) {
						String orgName = String.valueOf(resultList
								.get(0).get("NAME"));
						resultMap.put("orgName", orgName);
					}

					if (resultList.get(0).get("DISPLAY_NAME") != null) {
						String userName = String.valueOf(resultList.get(0).get(
								"DISPLAY_NAME"));
						resultMap.put("userName", userName);
					}

			}
		} catch (Exception e) {
		}

		return resultMap;
	}

	/**
	 * 
	 * @title: getGrPatrolScheduleList
	 * @description: 分页得到维修记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrPatrolScheduleList(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectAllPatrolSchedule",
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

}
