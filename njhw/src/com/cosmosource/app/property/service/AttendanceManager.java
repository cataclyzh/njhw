package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrAttendance;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;

/** 
* @description: 考勤管理
* @author chengyun
* @date 2013-07-05
*/ 
@SuppressWarnings("unchecked")
public class AttendanceManager extends BaseManager {
	
	public List<Map<String,Object>> exportAttendance(Map parMap){
		
		List<Map<String,Object>> resultList = new ArrayList();
		List<Map> list = sqlDao.findList("PropertySQL.selectAllAttendances2", parMap);
		
//		logger.info(list.toString());
		Map<String,Object> resultMap = null ;
		if (list != null && list.size() > 0){
			for(Map map : list){
				resultMap = new HashMap();
				resultMap.put("ATTENDANCE_ID", Long.parseLong((String.valueOf(map.get("ATTENDANCE_ID")))));
				resultMap.put("ATTENDANCE_SCHEDULE_ID", Long.parseLong((String.valueOf(map.get("ATTENDANCE_SCHEDULE_ID")))));
				resultMap.put("ATTENDANCE_USERID", Long.parseLong((String.valueOf(map.get("ATTENDANCE_USERID")))));
				resultMap.put("ATTENDANCE_USERNAME", (String)map.get("ATTENDANCE_USERNAME"));
				resultMap.put("ATTENDANCE_ORGID", Long.parseLong((String.valueOf(map.get("ATTENDANCE_ORGID")))));
				resultMap.put("ATTENDANCE_ORGNAME", (String)map.get("ATTENDANCE_ORGNAME"));
				resultMap.put("ATTENDANCE_SCHEDULE_ADMINID",Long.parseLong((String.valueOf(map.get("ATTENDANCE_SCHEDULE_ADMINID")))));
				resultMap.put("ATTENDANCE_SCHEDULE_ADMINNAME", (String)map.get("ATTENDANCE_SCHEDULE_ADMINNAME"));
				
				if(map.get("ATTENDANCE_SCHEDULE_INTIME")!=null)	{				
					resultMap.put("ATTENDANCE_SCHEDULE_INTIME", DateUtil.date2Str(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_SCHEDULE_INTIME")),"yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
				}else{
					resultMap.put("ATTENDANCE_SCHEDULE_INTIME", "");
				}
				
				if(map.get("ATTENDANCE_SCHEDULE_OUTTIME")!=null){
					resultMap.put("ATTENDANCE_SCHEDULE_OUTTIME", DateUtil.date2Str(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_SCHEDULE_OUTTIME")),"yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
				}else{
					resultMap.put("ATTENDANCE_SCHEDULE_OUTTIME", "");
				}
				
				if(map.get("ATTENDANCE_INTIME")!=null){
					resultMap.put("ATTENDANCE_INTIME",DateUtil.date2Str(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_INTIME")),"yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
				}else{
					resultMap.put("ATTENDANCE_INTIME", "");
				}
				
	
				if(map.get("ATTENDANCE_OUTTIME")!=null){
					resultMap.put("ATTENDANCE_OUTTIME", DateUtil.date2Str(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_OUTTIME")),"yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
				}else{
					resultMap.put("ATTENDANCE_OUTTIME", "");
				}
				
				
				if(map.get("RES_BAK1")!=null){
					
//					logger.info(map.get("RES_BAK1").toString());
					
					if(((String)map.get("RES_BAK1")).equalsIgnoreCase("0")){
						resultMap.put("RES_BAK1", "正常");
					}
					if(((String)map.get("RES_BAK1")).equalsIgnoreCase("1")){
						resultMap.put("RES_BAK1", "迟到");
					}
					if(((String)map.get("RES_BAK1")).equalsIgnoreCase("2")){
						resultMap.put("RES_BAK1", "早退");
					}
					if(((String)map.get("RES_BAK1")).equalsIgnoreCase("3")){
						resultMap.put("RES_BAK1", "迟到、早退");
					}
					if(((String)map.get("RES_BAK1")).equalsIgnoreCase("4")){
						resultMap.put("RES_BAK1", "数据异常");
					}
					if(((String)map.get("RES_BAK1")).equalsIgnoreCase("5")){
						resultMap.put("RES_BAK1", "缺勤");
					}
				}else{
					resultMap.put("RES_BAK1", "");
				}
				
				resultMap.put("RES_BAK2", (String)map.get("RES_BAK2"));
				resultMap.put("RES_BAK3", (String)map.get("RES_BAK3"));
				resultMap.put("RES_BAK4", (String)map.get("RES_BAK4"));
				
				
				resultList.add(resultMap);
			}
			
		}
			return resultList;
	}
	
	public void addOneAttendance(GrAttendance attendance){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("attendanceScheduleId", attendance.getAttendanceScheduleId());
		map.put("attendanceUserId", attendance.getAttendanceUserId());
		map.put("attendanceUserName", attendance.getAttendanceUserName());
		map.put("attendanceOrgId", attendance.getAttendanceOrgId());
		map.put("attendanceOrgName", attendance.getAttendanceOrgName());
		map.put("attendanceScheduleAdminId", attendance.getAttendanceScheduleAdminId());
		map.put("attendanceScheduleAdminName", attendance.getAttendanceScheduleAdminName());
		map.put("attendanceScheduleInTime", attendance.getAttendanceScheduleInTime());
		map.put("attendanceScheduleOutTime", attendance.getAttendanceScheduleOutTime());
		map.put("attendanceInTime", attendance.getAttendanceInTime());
		map.put("attendanceOutTime", attendance.getAttendanceOutTime());
		map.put("resBak1", attendance.getResBak1());
		map.put("resBak2", attendance.getResBak2());
		map.put("resBak3", attendance.getResBak3());
		map.put("resBak4", attendance.getResBak4());
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertAttendance", list);
	}
	
	public void addAttendance(HashMap map){
		List<Map> list = new ArrayList();
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertAttendance", list);
	}
	
	public void addAttendanceList(List<Map> list){
		sqlDao.batchInsert("PropertySQL.addAttendance", list);
	}
	
	public boolean isAttendanceExist(long aScheduleId, String s){
		Map param = new HashMap();
		param.put("aScheduleId", aScheduleId);
		param.put("resBak2", s);
		List result = sqlDao.findList("PropertySQL.getOneAttendance", param);
		if(result.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public void addAttendances(List<GrAttendance> attendances){
		List<Map> list = new ArrayList();
		Map map=null;
		for(GrAttendance attendance : attendances){
			map = new HashMap();
			map.put("attendanceScheduleId", attendance.getAttendanceScheduleId());
			map.put("attendanceUserId", attendance.getAttendanceUserId());
			map.put("attendanceUserName", attendance.getAttendanceUserName());
			map.put("attendanceOrgId", attendance.getAttendanceOrgId());
			map.put("attendanceOrgName", attendance.getAttendanceOrgName());
			map.put("attendanceScheduleAdminId", attendance.getAttendanceScheduleAdminId());
			map.put("attendanceScheduleAdminName", attendance.getAttendanceScheduleAdminName());
			map.put("attendanceScheduleInTime", attendance.getAttendanceScheduleInTime());
			map.put("attendanceScheduleOutTime", attendance.getAttendanceScheduleOutTime());
			map.put("attendanceInTime", attendance.getAttendanceInTime());
			map.put("attendanceOutTime", attendance.getAttendanceOutTime());
			map.put("resBak1", attendance.getResBak1());
			map.put("resBak2", attendance.getResBak2());
			map.put("resBak3", attendance.getResBak3());
			map.put("resBak4", attendance.getResBak4());
			list.add(map);
		}
		sqlDao.batchInsert("PropertySQL.insertAttendance", list);
	}
	
	public void deleteOneAttendance(Long attendanceId){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("attendanceId", attendanceId);
		list.add(map);
		
		sqlDao.batchDelete("PropertySQL.deleteAttendance", list);
	}
	
	public void deleteAttendances(Long[] attendanceIds){
		List<Map> list = new ArrayList();
		Map map = null;
		for(Long attendanceId: attendanceIds){
			map=new HashMap();
			map.put("attendanceId", attendanceId);
			list.add(map);
		}
		sqlDao.batchDelete("PropertySQL.deleteAttendance", list);
	}
	
	public void updateOneAttendance(GrAttendance attendance){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		
		map.put("attendanceId", attendance.getAttendanceId());
		map.put("attendanceScheduleId", attendance.getAttendanceScheduleId());
		map.put("attendanceUserId", attendance.getAttendanceUserId());
		map.put("attendanceUserName", attendance.getAttendanceUserName());
		map.put("attendanceOrgId", attendance.getAttendanceOrgId());
		map.put("attendanceOrgName", attendance.getAttendanceOrgName());
		map.put("attendanceScheduleAdminId", attendance.getAttendanceScheduleAdminId());
		map.put("attendanceScheduleAdminName", attendance.getAttendanceScheduleAdminName());
		map.put("attendanceScheduleInTime", attendance.getAttendanceScheduleInTime());
		map.put("attendanceScheduleOutTime", attendance.getAttendanceScheduleOutTime());
		map.put("attendanceInTime", attendance.getAttendanceInTime());
		map.put("attendanceOutTime", attendance.getAttendanceOutTime());
		map.put("resBak1", attendance.getResBak1());
		map.put("resBak2", attendance.getResBak2());
		map.put("resBak3", attendance.getResBak3());
		map.put("resBak4", attendance.getResBak4());
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateAttendance", list);
	}
	
	public void updateAttendances(List<GrAttendance> attendances){
		List<Map> list = new ArrayList();
		Map map = null;
		for(GrAttendance attendance : attendances){
			map= new HashMap();
			
			map.put("attendanceId", attendance.getAttendanceId());
			map.put("attendanceScheduleId", attendance.getAttendanceScheduleId());
			map.put("attendanceUserId", attendance.getAttendanceUserId());
			map.put("attendanceUserName", attendance.getAttendanceUserName());
			map.put("attendanceOrgId", attendance.getAttendanceOrgId());
			map.put("attendanceOrgName", attendance.getAttendanceOrgName());
			map.put("attendanceScheduleAdminId", attendance.getAttendanceScheduleAdminId());
			map.put("attendanceScheduleAdminName", attendance.getAttendanceScheduleAdminName());
			map.put("attendanceScheduleInTime", attendance.getAttendanceScheduleInTime());
			map.put("attendanceScheduleOutTime", attendance.getAttendanceScheduleOutTime());
			map.put("attendanceInTime", attendance.getAttendanceInTime());
			map.put("attendanceOutTime", attendance.getAttendanceOutTime());
			map.put("resBak1", attendance.getResBak1());
			map.put("resBak2", attendance.getResBak2());
			map.put("resBak3", attendance.getResBak3());
			map.put("resBak4", attendance.getResBak4());
			list.add(map);
		}
		sqlDao.batchUpdate("PropertySQL.updateAttendance", list);
	}
	
	public List<GrAttendance> queryAttendances(Long attendanceScheduleId,Long attendanceUserName,Long attendanceOrgName,String attendanceIntime,String attendanceOuttime){
		Map map = new HashMap();
		map.put("attendanceScheduleId", attendanceScheduleId);
		map.put("attendanceUserName", attendanceUserName);
		map.put("attendanceOrgName", attendanceOrgName);
		map.put("attendanceIntime", attendanceIntime);
		map.put("attendanceOuttime", attendanceOuttime);
		List<Map> list = sqlDao.findList("PropertySQL.selectAllAttendances", map);
		if(list.isEmpty())
			return null;
		else {
			return maptoAttendance(list);
		}
	}
	
	public List<GrAttendance> maptoAttendance(List<Map> list){
		List<GrAttendance> result = new ArrayList<GrAttendance>();
		GrAttendance attendance;
		for(Map map: list){
			attendance = new GrAttendance();
			attendance.setAttendanceId(Long.parseLong((String.valueOf(map.get("ATTENDANCE_ID")))));
			attendance.setAttendanceScheduleId(Long.parseLong((String.valueOf(map.get("ATTENDANCE_SCHEDULE_ID")))));
			attendance.setAttendanceUserId(Long.parseLong((String.valueOf(map.get("ATTENDANCE_USERID")))));
			attendance.setAttendanceUserName((String)map.get("ATTENDANCE_USERNAME"));
			attendance.setAttendanceOrgId(Long.parseLong((String.valueOf(map.get("ATTENDANCE_ORGID")))));
			attendance.setAttendanceOrgName((String)map.get("ATTENDANCE_ORGNAME"));
			attendance.setAttendanceScheduleAdminId(Long.parseLong((String.valueOf(map.get("ATTENDANCE_SCHEDULE_ADMINID")))));
			attendance.setAttendanceScheduleAdminName((String)map.get("ATTENDANCE_SCHEDULE_ADMINNAME"));
			if(map.get("ATTENDANCE_SCHEDULE_INTIME")!=null)
				attendance.setAttendanceScheduleInTime(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_SCHEDULE_INTIME")), "yyyy-MM-dd HH:mm:ss"));
			else
				attendance.setAttendanceScheduleInTime(null);
			if(map.get("ATTENDANCE_SCHEDULE_OUTTIME")!=null)
				attendance.setAttendanceScheduleOutTime(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_SCHEDULE_OUTTIME")),"yyyy-MM-dd HH:mm:ss"));
			else
				attendance.setAttendanceScheduleOutTime(null);
			if(map.get("ATTENDANCE_INTIME")!=null)				
				attendance.setAttendanceInTime(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_INTIME")), "yyyy-MM-dd HH:mm:ss"));
			else
				attendance.setAttendanceInTime(null);
			if(map.get("ATTENDANCE_OUTTIME")!=null)				
				attendance.setAttendanceOutTime(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_OUTTIME")), "yyyy-MM-dd HH:mm:ss"));
			else
				attendance.setAttendanceOutTime(null);
			attendance.setResBak1((String)map.get("RES_BAK1"));
			attendance.setResBak2((String)map.get("RES_BAK2"));
			attendance.setResBak3((String)map.get("RES_BAK3"));
			attendance.setResBak4((String)map.get("RES_BAK4"));
			
			result.add(attendance);
		}
		return result;
	}
	
	public GrAttendance findAttendanceById(Long attendanceId){
		Map map = new HashMap();
		map.put("attendanceId", attendanceId);
		List<Map> list = sqlDao.findList("PropertySQL.findAttendanceById", map);
		List<GrAttendance> result;
		GrAttendance attendance=null;
		if(list.isEmpty())
			return null;
		else {
			result= maptoAttendance(list);
			attendance=(GrAttendance)result.get(0);
		}
		return attendance;
	}
	
	public Page<HashMap> queryAllAttendances(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectAllAttendances", parMap);
	}
	
	public Page<HashMap> queryMyAttendances(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectMyAttendances", parMap);
	}
}