package com.cosmosource.app.property.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrPatrolException;
import com.cosmosource.app.entity.GrPatrolRecord;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.model.PatrolRecord;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;

/**
 * @description: 巡查记录的增删改查接口
 * @author tangtq
 * @date 2013-7-8 11:47:16
 */
public class PatrolRecordManager extends BaseManager {
	/**
	 * 
	 * @title: loadGrPatrolRecordInfo
	 * @description: 得到巡查记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:48:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolRecord> loadGrPatrolRecordInfo() {
		return dao.findByHQL("select t from GrPatrolRecord t");
	}

	/**
	 * 
	 * @title: addGrPatrolRecordInfo
	 * @description: 插入巡查记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrPatrolRecordInfo(Long scheduleId, Long orgId,
			String orgName, Long userId, String userName, Long patrolLineId,
			String patrolLineName, String patrolLineDesc, String patrolNodes,
			String scheduleStartTime, String scheduleEndTime, String isDeal) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("recordId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_PATROL_RECORD_SEQ_VALUE"))));
			parMap.put("scheduleId", scheduleId);
			parMap.put("orgId", orgId);
			parMap.put("orgName", orgName);
			parMap.put("userId", userId);
			parMap.put("userName", userName);
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("patrolLineName", patrolLineName);
			parMap.put("patrolLineDesc", patrolLineDesc);
			parMap.put("patrolNodes", patrolNodes);
			parMap.put("scheduleStartTime", scheduleStartTime);
			parMap.put("scheduleEndTime", scheduleEndTime);
			parMap.put("isDeal", isDeal);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertPatrolRecord", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public boolean addGrPatrolRecordHistory(Long scheduleId, Long orgId,
			String orgName, Long userId, String userName, Long patrolLineId,
			String patrolLineName, String patrolLineDesc, String patrolNodes,
			Date scheduleStartTime, Date scheduleEndTime, String isDeal) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("recordId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_PATROL_RECORD_SEQ_VALUE"))));
			parMap.put("scheduleId", scheduleId);
			parMap.put("orgId", orgId);
			parMap.put("orgName", orgName);
			parMap.put("userId", userId);
			parMap.put("userName", userName);
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("patrolLineName", patrolLineName);
			parMap.put("patrolLineDesc", patrolLineDesc);
			parMap.put("patrolNodes", patrolNodes);
			parMap.put("scheduleStartTime", scheduleStartTime);
			parMap.put("scheduleEndTime",scheduleEndTime);
			parMap.put("isDeal", isDeal);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertPatrolRecordHistory", parList);
			sqlDao.batchUpdate("PropertySQL.deletePatrolRecord", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public boolean addGrPatrolExceptionHistory(Long scheduleId, Long orgId,
			String orgName, Long userId, String userName, Long patrolLineId,
			String patrolLineName, String patrolLineDesc,
			Date scheduleStartTime, Date scheduleEndTime,String exceptionDesc, String isNormal) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("exceptionId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_PATROL_EXCEPTION_SEQ_VALUE"))));
			parMap.put("scheduleId", scheduleId);
			parMap.put("orgId", orgId);
			parMap.put("orgName", orgName);
			parMap.put("userId", userId);
			parMap.put("userName", userName);
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("patrolLineName", patrolLineName);
			parMap.put("patrolLineDesc", patrolLineDesc);
			parMap.put("scheduleStartTime", scheduleStartTime);
			parMap.put("scheduleEndTime", scheduleEndTime);
			parMap.put("exceptionDesc", exceptionDesc);
			parMap.put("isNormal", isNormal);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertPatrolExceptionHistory", parList);
			sqlDao.batchUpdate("PropertySQL.deletePatrolException", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public List<GrPatrolRecord> getGrPatrolRecord() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrPatrolRecord> patrolRecordList = new ArrayList<GrPatrolRecord>();
		try {
			Map parMap = new HashMap();
			resultList = sqlDao.findList(
					"PropertySQL.selectGrPatrolRecord",
					parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrPatrolRecord grPatrolRecord = new GrPatrolRecord();
					
					if (resultList.get(i).get("RECORD_ID") != null) {
						Long recordId = Long.parseLong(String
								.valueOf(resultList.get(i).get("RECORD_ID")));
						grPatrolRecord.setRecordId(recordId);
					}
					
					if (resultList.get(i).get("SCHEDULE_ID") != null) {
						Long scheduleIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("SCHEDULE_ID")));
						grPatrolRecord.setScheduleId(scheduleIdTemp);
					}

					if (resultList.get(i).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID")));
						grPatrolRecord.setOrgId(orgId);
					}

					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(i).get(
								"ORG_NAME"));
						grPatrolRecord.setOrgName(orgName);
					}

					if (resultList.get(i).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(i).get("USER_ID")));
						grPatrolRecord.setUserId(userId);
					}

					if (resultList.get(i).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(i).get(
								"USER_NAME"));
						grPatrolRecord.setUserName(userName);
					}

					if (resultList.get(i).get("PATROL_LINE_ID") != null) {
						Long patrolLineId = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("PATROL_LINE_ID")));
						grPatrolRecord.setPatrolLineId(patrolLineId);
					}

					if (resultList.get(i).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String.valueOf(resultList
								.get(i).get("PATROL_LINE_NAME"));
						grPatrolRecord.setPatrolLineName(patrolLineName);
					}

					if (resultList.get(i).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String.valueOf(resultList
								.get(i).get("PATROL_LINE_DESC"));
						grPatrolRecord.setPatrolLineDesc(patrolLineDesc);
					}

					if (resultList.get(i).get("PATROL_NODES") != null) {
						String patrolNodes = String.valueOf(resultList.get(i).get("PATROL_NODES"));
						grPatrolRecord.setPatrolNodes(patrolNodes);
					}

					if (resultList.get(i).get("SCHEDULE_START_TIME") != null) {
						String scheduleStartTime = String.valueOf(resultList.get(i).get(
								"SCHEDULE_START_TIME"));
						
						grPatrolRecord
								.setScheduleStartTime(DateUtil.str2Date(scheduleStartTime, "yyyy-mm-dd HH:mm:ss"));
					}

					if (resultList.get(i).get("SCHEDULE_END_TIME") != null) {
						String scheduleEndTime = String.valueOf(resultList.get(i).get(
								"SCHEDULE_END_TIME"));
						grPatrolRecord.setScheduleEndTime(DateUtil.str2Date(scheduleEndTime, "yyyy-mm-dd HH:mm:ss"));
					}

					if (resultList.get(i).get("IS_DEAL") != null) {
						String isDeal = String.valueOf(resultList.get(i)
								.get("IS_DEAL"));
						grPatrolRecord.setIsDeal(isDeal);
					}
					
					if (resultList.get(i).get("RES_BAK1") != null) {
						String resBak1 = String.valueOf(resultList.get(i)
								.get("RES_BAK1"));
						grPatrolRecord.setResBak1(resBak1);
					}
					if (resultList.get(i).get("RES_BAK2") != null) {
						String resBak2 = String.valueOf(resultList.get(i)
								.get("RES_BAK2"));
						grPatrolRecord.setResBak2(resBak2);
					}
					if (resultList.get(i).get("RES_BAK3") != null) {
						String resBak3 = String.valueOf(resultList.get(i)
								.get("RES_BAK3"));
						grPatrolRecord.setResBak3(resBak3);
					}
					if (resultList.get(i).get("RES_BAK4") != null) {
						String resBak4 = String.valueOf(resultList.get(i)
								.get("RES_BAK4"));
						grPatrolRecord.setResBak4(resBak4);
					}
					patrolRecordList.add(grPatrolRecord);
				}

			}
		} catch (Exception e) {
		}

		return patrolRecordList;
	}
	
	public List<GrPatrolException> getGrPatrolException() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrPatrolException> patrolExceptionList = new ArrayList<GrPatrolException>();
		try {
			Map parMap = new HashMap();
			resultList = sqlDao.findList(
					"PropertySQL.selectGrPatrolException",
					parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrPatrolException grPatrolException = new GrPatrolException();
					
					if (resultList.get(i).get("EXCEPTION_ID") != null) {
						Long exceptionId = Long.parseLong(String
								.valueOf(resultList.get(i).get("EXCEPTION_ID")));
						grPatrolException.setExceptionId(exceptionId);
					}
					
					if (resultList.get(i).get("SCHEDULE_ID") != null) {
						Long scheduleIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("SCHEDULE_ID")));
						grPatrolException.setScheduleId(scheduleIdTemp);
					}

					if (resultList.get(i).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID")));
						grPatrolException.setOrgId(orgId);
					}

					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(i).get(
								"ORG_NAME"));
						grPatrolException.setOrgName(orgName);
					}

					if (resultList.get(i).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(i).get("USER_ID")));
						grPatrolException.setUserId(userId);
					}

					if (resultList.get(i).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(i).get(
								"USER_NAME"));
						grPatrolException.setUserName(userName);
					}

					if (resultList.get(i).get("PATROL_LINE_ID") != null) {
						Long patrolLineId = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("PATROL_LINE_ID")));
						grPatrolException.setPatrolLineId(patrolLineId);
					}

					if (resultList.get(i).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String.valueOf(resultList
								.get(i).get("PATROL_LINE_NAME"));
						grPatrolException.setPatrolLineName(patrolLineName);
					}

					if (resultList.get(i).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String.valueOf(resultList
								.get(i).get("PATROL_LINE_DESC"));
						grPatrolException.setPatrolLineDesc(patrolLineDesc);
					}

					if (resultList.get(i).get("SCHEDULE_START_DATE") != null) {
						String scheduleStartDate = String.valueOf(resultList.get(i).get(
								"SCHEDULE_START_DATE"));
						
						grPatrolException
								.setScheduleStartDate(DateUtil.str2Date(scheduleStartDate,"yyyy-mm-dd HH:mm:ss"));
					}

					if (resultList.get(i).get("SCHEDULE_END_DATE") != null) {
						String scheduleEndDate = String.valueOf(resultList.get(i).get(
								"SCHEDULE_END_DATE"));
						grPatrolException.setScheduleEndDate(DateUtil.str2Date(scheduleEndDate,"yyyy-mm-dd HH:mm:ss"));
					}
					
					if (resultList.get(i).get("EXCEPTION_DESC") != null) {
						String exceptionDesc = String.valueOf(resultList.get(i)
								.get("EXCEPTION_DESC"));
						grPatrolException.setExceptionDesc(exceptionDesc);
					}
					
					if (resultList.get(i).get("IS_NORMAL") != null) {
						String isNormal = String.valueOf(resultList.get(i)
								.get("IS_NORMAL"));
						grPatrolException.setIsNormal(isNormal);
					}
					
					if (resultList.get(i).get("RES_BAK1") != null) {
						String resBak1 = String.valueOf(resultList.get(i)
								.get("RES_BAK1"));
						grPatrolException.setResBak1(resBak1);
					}
					if (resultList.get(i).get("RES_BAK2") != null) {
						String resBak2 = String.valueOf(resultList.get(i)
								.get("RES_BAK2"));
						grPatrolException.setResBak2(resBak2);
					}
					if (resultList.get(i).get("RES_BAK3") != null) {
						String resBak3 = String.valueOf(resultList.get(i)
								.get("RES_BAK3"));
						grPatrolException.setResBak3(resBak3);
					}
					if (resultList.get(i).get("RES_BAK4") != null) {
						String resBak4 = String.valueOf(resultList.get(i)
								.get("RES_BAK4"));
						grPatrolException.setResBak4(resBak4);
					}
					patrolExceptionList.add(grPatrolException);
				}

			}
		} catch (Exception e) {
		}

		return patrolExceptionList;
	}
	
	/**
	 * 
	 * @title: addGrPatrolExceptionInfo
	 * @description: 插入巡查异常信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrPatrolExceptionInfo(Long scheduleId, Long orgId,
			String orgName, Long userId, String userName, Long patrolLineId,
			String patrolLineName, String patrolLineDesc,
			Date scheduleStartDate, Date scheduleEndDate,
			String exceptionDesc, String isNormal,String isduty) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("exceptionId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_PATROL_EXCEPTION_SEQ_VALUE"))));
			parMap.put("scheduleId", scheduleId);
			parMap.put("orgId", orgId);
			parMap.put("orgName", orgName);
			parMap.put("userId", userId);
			parMap.put("userName", userName);
			parMap.put("patrolLineId", patrolLineId);
			parMap.put("patrolLineName", patrolLineName);
			parMap.put("patrolLineDesc", patrolLineDesc);
			parMap.put("scheduleStartDate", scheduleStartDate);
			parMap.put("scheduleEndDate", scheduleEndDate);
			parMap.put("exceptionDesc", exceptionDesc);
			parMap.put("isNormal", isNormal);
			parMap.put("res_bak1", isduty);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertPatrolException", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: addGrPatrolRecordInfo
	 * @description: 插入巡查记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean editGrPatrolRecordInfo(Long recordId,String isDeal) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("recordId", recordId);
			parMap.put("isDeal", isDeal);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updatePatrolRecord", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: loadGrPatrolRecordInfoByScheduleId
	 * @description: 根据记录ID得到巡查记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolRecord> loadGrPatrolRecordInfoByRecordId(Long recordId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrPatrolRecord> patrolRecordList = new ArrayList<GrPatrolRecord>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Map parMap = new HashMap();
			parMap.put("recordId", recordId);
			resultList = sqlDao.findList("PropertySQL.selectPatrolRecordInfoByRecordId",
					parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrPatrolRecord grPatrolRecord = new GrPatrolRecord();
					if (resultList.get(i).get("RECORD_ID") != null) {
						Long recordTempId = Long.parseLong(String
								.valueOf(resultList.get(i).get("RECORD_ID")));
						grPatrolRecord.setRecordId(recordTempId);
					}

					if (resultList.get(i).get("SCHEDULE_ID") != null) {
						Long scheduleIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("SCHEDULE_ID")));
						grPatrolRecord.setScheduleId(scheduleIdTemp);
					}

					if (resultList.get(i).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID")));
						grPatrolRecord.setOrgId(orgId);
					}

					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(i).get(
								"ORG_NAME"));
						grPatrolRecord.setOrgName(orgName);
					}

					if (resultList.get(i).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(i).get("USER_ID")));
						grPatrolRecord.setUserId(userId);
					}

					if (resultList.get(i).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(i).get(
								"USER_NAME"));
						grPatrolRecord.setUserName(userName);
					}

					if (resultList.get(i).get("PATROL_LINE_ID") != null) {
						Long patrolLineId = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("PATROL_LINE_ID")));
						grPatrolRecord.setPatrolLineId(patrolLineId);
					}

					if (resultList.get(i).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String.valueOf(resultList
								.get(i).get("PATROL_LINE_NAME"));
						grPatrolRecord.setPatrolLineName(patrolLineName);
					}

					if (resultList.get(i).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String.valueOf(resultList
								.get(i).get("PATROL_LINE_DESC"));
						grPatrolRecord.setPatrolLineDesc(patrolLineDesc);
					}

					if (resultList.get(i).get("PATROL_NODES") != null) {
						String patrolNodes = String
								.valueOf(resultList.get(i)
										.get("PATROL_NODES"));
						grPatrolRecord.setPatrolNodes(patrolNodes);
					}

					if (resultList.get(0).get("SCHEDULE_START_TIME") != null) {
						Date scheduleStartTime = sdf.parse(String.valueOf(resultList.get(0).get(
								"SCHEDULE_START_TIME")));
						grPatrolRecord.setScheduleStartTime(scheduleStartTime);
					}

					if (resultList.get(0).get("SCHEDULE_END_TIME") != null) {
						Date scheduleEndTime =  sdf.parse(String.valueOf(resultList.get(0).get(
								"SCHEDULE_END_TIME").toString()));
						grPatrolRecord.setScheduleEndTime(scheduleEndTime);
					}

					if (resultList.get(i).get("IS_DEAL") != null) {
						String isDeal = String.valueOf(resultList.get(i).get(
								"IS_DEAL"));
						grPatrolRecord.setIsDeal(isDeal);
					}
					patrolRecordList.add(grPatrolRecord);
				}

			}
		} catch (Exception e) {
		}

		return patrolRecordList;
	}
	
	/**
	 * 
	 * @title: getPatrolRecordInfoEveryDay
	 * @description: 根据记录ID得到巡查记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PatrolRecord> getPatrolRecordInfoEveryDay() {
		List<Map> resultList = new ArrayList<Map>();
		List<PatrolRecord> patrolRecordList = new ArrayList<PatrolRecord>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Map parMap = new HashMap();
			resultList = sqlDao.findList("PropertySQL.selectPatrolRecordInfoEveryDay",null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					PatrolRecord grPatrolRecord = new PatrolRecord();
					if (resultList.get(i).get("RECORD_ID") != null) {
						Long recordTempId = Long.parseLong(String
								.valueOf(resultList.get(i).get("RECORD_ID")));
						grPatrolRecord.setRecordId(recordTempId);
					}

					if (resultList.get(i).get("SCHEDULE_ID") != null) {
						Long scheduleIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("SCHEDULE_ID")));
						grPatrolRecord.setScheduleId(scheduleIdTemp);
					}

					if (resultList.get(i).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID")));
						grPatrolRecord.setOrgId(orgId);
					}

					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(i).get(
								"ORG_NAME"));
						grPatrolRecord.setOrgName(orgName);
					}

					if (resultList.get(i).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(i).get("USER_ID")));
						grPatrolRecord.setUserId(userId);
					}
					
					if (resultList.get(i).get("PATROL_POSITION_CARD_NO") != null) {
						String cardNo = String.valueOf(resultList
								.get(i).get("PATROL_POSITION_CARD_NO"));
						grPatrolRecord.setCardNo(cardNo);
					}

					if (resultList.get(i).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(i).get(
								"USER_NAME"));
						grPatrolRecord.setUserName(userName);
					}

					if (resultList.get(i).get("PATROL_LINE_ID") != null) {
						Long patrolLineId = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("PATROL_LINE_ID")));
						grPatrolRecord.setPatrolLineId(patrolLineId);
					}

					if (resultList.get(i).get("PATROL_LINE_NAME") != null) {
						String patrolLineName = String.valueOf(resultList
								.get(i).get("PATROL_LINE_NAME"));
						grPatrolRecord.setPatrolLineName(patrolLineName);
					}

					if (resultList.get(i).get("PATROL_LINE_DESC") != null) {
						String patrolLineDesc = String.valueOf(resultList
								.get(i).get("PATROL_LINE_DESC"));
						grPatrolRecord.setPatrolLineDesc(patrolLineDesc);
					}

					if (resultList.get(i).get("PATROL_NODES") != null) {
						String patrolNodes = String
								.valueOf(resultList.get(i)
										.get("PATROL_NODES"));
						grPatrolRecord.setPatrolNodes(patrolNodes);
					}

					if (resultList.get(i).get("SCHEDULE_START_TIME") != null) {
						Date scheduleStartTime = sdf.parse(String.valueOf(resultList.get(i).get(
								"SCHEDULE_START_TIME")));
						grPatrolRecord.setScheduleStartTime(scheduleStartTime);
					}

					if (resultList.get(i).get("SCHEDULE_END_TIME") != null) {
						Date scheduleEndTime = sdf.parse(String.valueOf(resultList.get(i).get(
								"SCHEDULE_END_TIME")));
						grPatrolRecord.setScheduleEndTime(scheduleEndTime);
					}

					if (resultList.get(i).get("IS_DEAL") != null) {
						String isDeal = String.valueOf(resultList.get(i).get(
								"IS_DEAL"));
						grPatrolRecord.setIsDeal(isDeal);
					}
					patrolRecordList.add(grPatrolRecord);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return patrolRecordList;
	}

	/**
	 * 
	 * @title: getGrPatrolRecordList
	 * @description: 分页得到巡查记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrPatrolRecordList(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectAllPatrolRecord",
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
	
	public String getTagMacByCardno(String cardno){
		Map<String,String> map = new HashMap<String, String>();
		map.put("cardno", cardno);
		List list = sqlDao.findList("PropertySQL.getTagMacByCardno", map);
		if(list!=null&&list.size()>0){
			return (String)(((Map)list.get(0)).get("TAGMAC"));
		}
		return null;
	}
}
