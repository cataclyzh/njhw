package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.GrAttendanceSchedule;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;

/** 
* @description: 考勤排班
* @author chengyun
* @date 2013-07-015
*/ 
@SuppressWarnings("unchecked")
public class AttendanceScheduleManager extends BaseManager {
	
	public String getMesOrgUserTreeDataSub(String orgid, String ids, String type) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");
		getMesOrgUserTreeDocSub(orgid, ids, root, type);
		return doc.asXML();		
	}
	
	public void getMesOrgUserTreeDocSub(String orgid, String ids, Element rootElement, String type){	
		   try {
			   
			if ("init".equals(type)) {		
				Org org = (Org)this.findById(Org.class,Long.valueOf(orgid));
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"-o");
	            el.addAttribute("open", "1");
	            getMesOrgUserTreeDocSub(org.getOrgId().toString(), ids, el,"open");
			} else {			
				List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid));
				for (Org org : list) {
		            Element el=rootElement.addElement("item");   
		            el.addAttribute("text", org.getName());
		            el.addAttribute("id", org.getOrgId()+"-o");
		            getMesOrgUserTreeDocSub(org.getOrgId().toString(), ids, el, type);
		        }
			      
		    	List<Users> userList = dao.findByHQL("select t from Users t where t.orgId=? order by orderNum", new Long(orgid));
		    	String[] idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
				for (Users user : userList) {	            		
	        		Element el=rootElement.addElement("item");   
		            el.addAttribute("text", user.getDisplayName());
		            el.addAttribute("id", user.getUserid()+"-u");
		            el.addAttribute("im0", "user.gif");		
		            el.addAttribute("im1", "user.gif");		
		            el.addAttribute("im2", "user.gif");
		            if(idsArray != null && idsArray.length > 0) {
		            	for (String strID : idsArray) {
		            		if (user.getUserid().toString().equals(strID)) {
			            		el.addAttribute("checked", "1");
			            		break;
			            	}
						}
		            }
				}
	        }
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
	/**
	 * 
	* @Title: getMesOrgUserTreeData 
	* @Description: 弹出页面机构及用户树checkbox:发送消息
	* @author WXJ
	* @date 2013-5-20 上午11:20:40 
	* @param @param orgid
	* @param @param ctx
	* @param @param roomid
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getMesOrgUserTreeData(String orgid, String ids, String ctx, String type) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");
		getMesOrgUserTreeDoc(orgid, ids,root,type);
		return doc.asXML();		
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeDoc 
	* @Description: 弹出页面机构及用户树checkbox:发送消息
	* @author WXJ
	* @date 2013-5-20 上午11:19:47 
	* @param @param orgid
	* @param @param roomid
	* @param @param rootElement    
	* @return void 
	* @throws
	 */
	public void getMesOrgUserTreeDoc(String orgid, String ids,Element rootElement, String type){	
	   try {
		   
		if ("init".equals(type)) {		
			Map pMap = new HashMap();
			pMap.put("userid", "118");
			List<HashMap> list = this.findListBySql("PersonnelSQL.getTopOrgIdByUserId", pMap);
			if(list.size()>0){
				HashMap map = list.get(0);
				Org org = (Org)this.findById(Org.class,Long.valueOf(map.get("TOP_ORG_ID").toString()));
			
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"-o");
	            el.addAttribute("open", "1");
	            
	            getMesOrgUserTreeDoc(org.getOrgId().toString(), ids, el,"open");
			}
		} else {			
			List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid));
					
			for (Org org : list) {
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"-o");
	            getMesOrgUserTreeDoc(org.getOrgId().toString(), ids, el,type);
	        }
		      
	    	List<Users> userList = dao.findByHQL("select t from Users t where t.orgId=? order by orderNum", new Long(orgid));
	    	String[] idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
			for (Users user : userList) {	            		
        		Element el=rootElement.addElement("item");   
	            el.addAttribute("text", user.getDisplayName());
	            el.addAttribute("id", user.getUserid()+"-u");
	            el.addAttribute("im0", "user.gif");		
	            el.addAttribute("im1", "user.gif");		
	            el.addAttribute("im2", "user.gif");
	            if(idsArray != null && idsArray.length > 0) {
	            	for (String strID : idsArray) {
	            		if (user.getUserid().toString().equals(strID)) {
		            		el.addAttribute("checked", "1");
		            		break;
		            	}
					}
	            }
			}
        }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Map> getAttendanceOrgInfoById(Long attendanceScheduleUserId){
		Map map = new HashMap();
		map.put("attendanceScheduleUserId", attendanceScheduleUserId);
		List<Map> result = sqlDao.findList("PropertySQL.getAttendanceOrgInfoById", map);
		return result;
	}
	
	public List<Map> getAttendanceRecord(String date, String cardNo){
		Map map = new HashMap();
		String[] t = date.split("-");
		map.put("year", t[0]);
		map.put("month", t[1]);
		map.put("date", t[2]);
		map.put("cardNo", cardNo);
		List<Map> result = sqlDao.findList("PropertySQL.getAttendanceRecord_new", map);
		return result;
	}
	
	public List<Map> getBeforeDayRecord(String date, String cardNo){
		Map map = new HashMap();
		String[] t = date.split("-");
		map.put("year", t[0]);
		map.put("month", t[1]);
		map.put("date", t[2]);
		map.put("cardNo", cardNo);
		List<Map> result = sqlDao.findList("PropertySQL.getBeforeDayRecord_new", map);
		return result;
	}
	
	public List<Map> getAfterDayRecord(String date, String cardNo){
		Map map = new HashMap();
		String[] t = date.split("-");
		map.put("year", t[0]);
		map.put("month", t[1]);
		map.put("date", t[2]);
		map.put("cardNo", cardNo);
		List<Map> result = sqlDao.findList("PropertySQL.getAfterDayRecord_new", map);
		return result;
	}
	
	public List<Map> getAttendanceRecordList(Long attendanceScheduleUserId,String attendanceScheduleInTime,String attendanceScheduleOutTime){
		Map map = new HashMap();
		map.put("attendanceScheduleUserId", attendanceScheduleUserId);
		map.put("attendanceScheduleInTime", attendanceScheduleInTime);
		map.put("attendanceScheduleOutTime", attendanceScheduleOutTime);
		List<Map> result = sqlDao.findList("PropertySQL.getAttendanceRecordList", map);
		return result;
	}

	public void suspendAllAttendanceSchedulesStateByTime(String attendanceScheduleOutTime){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("attendanceScheduleState", "0");
		map.put("attendanceScheduleOutTime", attendanceScheduleOutTime);
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.suspendAllAttendanceSchedulesStateByTime",list);
	}
	
	public void updateAttendanceScheduleStateById(Long attendanceScheduleId,String attendanceScheduleState){
		List<Map> list = new ArrayList();
		Map map=new HashMap();
		map.put("attendanceScheduleId", attendanceScheduleId);
		map.put("attendanceScheduleState", attendanceScheduleState);
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateAttendanceScheduleStateById", list);
	}

	public void addOneAttendanceSchedule(GrAttendanceSchedule attendanceSchedule){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("attendanceScheduleUserId", attendanceSchedule.getAttendanceScheduleUserId());
		map.put("attendanceScheduleUserName", attendanceSchedule.getAttendanceScheduleUserName());
		map.put("attendanceScheduleOrgId", attendanceSchedule.getAttendanceScheduleOrgId());
		map.put("attendanceScheduleOrgName", attendanceSchedule.getAttendanceScheduleOrgName());
		map.put("attendanceScheduleAdminId", attendanceSchedule.getAttendanceScheduleAdminId());
		map.put("attendanceScheduleAdminName", attendanceSchedule.getAttendanceScheduleAdminName());
		map.put("attendanceScheduleInTime", attendanceSchedule.getAttendanceScheduleInTime());
		map.put("attendanceScheduleOutTime", attendanceSchedule.getAttendanceScheduleOutTime());
		map.put("attendanceScheduleState", attendanceSchedule.getAttendanceScheduleState());
		map.put("resBak1", attendanceSchedule.getResBak1());
		map.put("resBak2", attendanceSchedule.getResBak2());
		map.put("resBak3", attendanceSchedule.getResBak3());
		map.put("resBak4", attendanceSchedule.getResBak4());
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertAttendanceSchedule",list);
	}
	
	public void addAttendanceSchedules(List<GrAttendanceSchedule> attendanceSchedules){
		List<Map> list = new ArrayList();
		Map map=null; 
		for(GrAttendanceSchedule attendanceSchedule:attendanceSchedules){
			map= new HashMap();
			map.put("attendanceScheduleUserId", attendanceSchedule.getAttendanceScheduleUserId());
			map.put("attendanceScheduleUserName", attendanceSchedule.getAttendanceScheduleUserName());
			map.put("attendanceScheduleOrgId", attendanceSchedule.getAttendanceScheduleOrgId());
			map.put("attendanceScheduleOrgName", attendanceSchedule.getAttendanceScheduleOrgName());
			map.put("attendanceScheduleAdminId", attendanceSchedule.getAttendanceScheduleAdminId());
			map.put("attendanceScheduleAdminName", attendanceSchedule.getAttendanceScheduleAdminName());
			map.put("attendanceScheduleInTime", attendanceSchedule.getAttendanceScheduleInTime());
			map.put("attendanceScheduleOutTime", attendanceSchedule.getAttendanceScheduleOutTime());
			map.put("attendanceScheduleState", attendanceSchedule.getAttendanceScheduleState());
			map.put("resBak1", attendanceSchedule.getResBak1());
			map.put("resBak2", attendanceSchedule.getResBak2());
			map.put("resBak3", attendanceSchedule.getResBak3());
			map.put("resBak4", attendanceSchedule.getResBak4());
			list.add(map);
		}
		sqlDao.batchInsert("PropertySQL.insertAttendanceSchedule",list);
	}
	
	public void deleteOneAttendanceSchedule(Long attendanceScheduleId){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("attendanceScheduleId", attendanceScheduleId);
		list.add(map);
		sqlDao.batchDelete("PropertySQL.deleteAttendanceSchedule", list);
	}
	
	public void deleteAttendanceSchedules(Long[] attendanceScheduleIds){
		List<Map> list = new ArrayList();
		Map map =null;
		for(Long attendanceScheduleId: attendanceScheduleIds){
			map= new HashMap();
			map.put("attendanceScheduleId", attendanceScheduleId);
			list.add(map);
		}
		sqlDao.batchDelete("PropertySQL.deleteAttendanceSchedule", list);
	}
	
	public void updateOneAttendanceSchedule(GrAttendanceSchedule attendanceSchedule){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("attendanceScheduleId", attendanceSchedule.getAttendanceScheduleId());
		map.put("attendanceScheduleUserId", attendanceSchedule.getAttendanceScheduleUserId());
		map.put("attendanceScheduleUserName", attendanceSchedule.getAttendanceScheduleUserName());
		map.put("attendanceScheduleOrgId", attendanceSchedule.getAttendanceScheduleOrgId());
		map.put("attendanceScheduleOrgName", attendanceSchedule.getAttendanceScheduleOrgName());
		map.put("attendanceScheduleAdminId", attendanceSchedule.getAttendanceScheduleAdminId());
		map.put("attendanceScheduleAdminName", attendanceSchedule.getAttendanceScheduleAdminName());
		map.put("attendanceScheduleInTime", attendanceSchedule.getAttendanceScheduleInTime());
		map.put("attendanceScheduleOutTime", attendanceSchedule.getAttendanceScheduleOutTime());
		map.put("attendanceScheduleState", attendanceSchedule.getAttendanceScheduleState());
		map.put("resBak1", attendanceSchedule.getResBak1());
		map.put("resBak2", attendanceSchedule.getResBak2());
		map.put("resBak3", attendanceSchedule.getResBak3());
		map.put("resBak4", attendanceSchedule.getResBak4());
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateAttendanceSchedule",list);
	}
	
	public void updateAttendanceSchedules(List<GrAttendanceSchedule> attendanceSchedules){
		List<Map> list = new ArrayList();
		Map map=null;
		for(GrAttendanceSchedule attendanceSchedule:attendanceSchedules){
			map= new HashMap();
			map.put("attendanceScheduleId", attendanceSchedule.getAttendanceScheduleId());
			map.put("attendanceScheduleUserId", attendanceSchedule.getAttendanceScheduleUserId());
			map.put("attendanceScheduleUserName", attendanceSchedule.getAttendanceScheduleUserName());
			map.put("attendanceScheduleOrgId", attendanceSchedule.getAttendanceScheduleOrgId());
			map.put("attendanceScheduleOrgName", attendanceSchedule.getAttendanceScheduleOrgName());
			map.put("attendanceScheduleAdminId", attendanceSchedule.getAttendanceScheduleAdminId());
			map.put("attendanceScheduleAdminName", attendanceSchedule.getAttendanceScheduleAdminName());
			map.put("attendanceScheduleInTime", attendanceSchedule.getAttendanceScheduleInTime());
			map.put("attendanceScheduleOutTime", attendanceSchedule.getAttendanceScheduleOutTime());
			map.put("attendanceScheduleState", attendanceSchedule.getAttendanceScheduleState());
			map.put("resBak1", attendanceSchedule.getResBak1());
			map.put("resBak2", attendanceSchedule.getResBak2());
			map.put("resBak3", attendanceSchedule.getResBak3());
			map.put("resBak4", attendanceSchedule.getResBak4());
			list.add(map);
		}
		sqlDao.batchUpdate("PropertySQL.updateAttendanceSchedule",list);
	}
	
	public List<GrAttendanceSchedule> queryAttendanceSchedules1(String attendanceScheduleUserName,String attendanceScheduleOrgName,
																String attendanceScheduleAdminName,String attendanceScheduleInTime,
																String attendanceScheduleOutTime,String attendanceScheduleState){
		Map map = new HashMap();
		map.put("attendanceScheduleUserName", attendanceScheduleUserName);
		map.put("attendanceScheduleOrgName", attendanceScheduleOrgName);
		map.put("attendanceScheduleAdminName", attendanceScheduleAdminName);
		map.put("attendanceScheduleInTime", attendanceScheduleInTime);
		map.put("attendanceScheduleOutTime", attendanceScheduleOutTime);
		map.put("attendanceScheduleState", attendanceScheduleState);
		List<Map> list = sqlDao.findList("PropertySQL.selectAllAttendanceSchedules", map);
		if(list.isEmpty())
			return null;
		else {
			return mapToAttendanceSchedule(list);
		}
	}
	
	public List<Map> queryAttendanceSchedules(){
		Map map = new HashMap();
		// 考勤排班状态 attendanceScheduleState 1 为活动，0为停止
		map.put("attendanceScheduleState", "1");
		return sqlDao.findList("PropertySQL.selectAllAttendanceSchedules", map);
	}
	
	public List<GrAttendanceSchedule> mapToAttendanceSchedule(List<Map> list){
		List<GrAttendanceSchedule> result = new ArrayList<GrAttendanceSchedule>();
		GrAttendanceSchedule attendanceSchedule;
		for(Map map:list){
			attendanceSchedule = new GrAttendanceSchedule();
			attendanceSchedule.setAttendanceScheduleId(Long.parseLong(String.valueOf(map.get("ATTENDANCE_SCHEDULE_ID"))));
			attendanceSchedule.setAttendanceScheduleUserId(Long.parseLong(String.valueOf(map.get("ATTENDANCE_SCHEDULE_USERID"))));
			attendanceSchedule.setAttendanceScheduleUserName((String)map.get("ATTENDANCE_SCHEDULE_USERNAME"));
			attendanceSchedule.setAttendanceScheduleOrgId(Long.parseLong(String.valueOf(map.get("ATTENDANCE_SCHEDULE_ORGID"))));
			attendanceSchedule.setAttendanceScheduleOrgName((String)map.get("ATTENDANCE_SCHEDULE_ORGNAME"));
			attendanceSchedule.setAttendanceScheduleAdminId(Long.parseLong(String.valueOf(map.get("ATTENDANCE_SCHEDULE_ADMINID"))));
			attendanceSchedule.setAttendanceScheduleAdminName((String)map.get("ATTENDANCE_SCHEDULE_ADMINNAME"));
			if(map.get("ATTENDANCE_SCHEDULE_INTIME")!=null)
				attendanceSchedule.setAttendanceScheduleInTime(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_SCHEDULE_INTIME")), "yyyy-MM-dd HH:mm:ss"));
			else
				attendanceSchedule.setAttendanceScheduleInTime(null);
			if(map.get("ATTENDANCE_SCHEDULE_OUTTIME")!=null)
				attendanceSchedule.setAttendanceScheduleOutTime(DateUtil.str2Date(String.valueOf(map.get("ATTENDANCE_SCHEDULE_OUTTIME")),"yyyy-MM-dd HH:mm:ss"));
			else
				attendanceSchedule.setAttendanceScheduleOutTime(null);
			attendanceSchedule.setAttendanceScheduleState((String)map.get("ATTENDANCE_SCHEDULE_STATE"));
			attendanceSchedule.setResBak1((String)map.get("RES_BAK1"));
			attendanceSchedule.setResBak2((String)map.get("RES_BAK2"));
			attendanceSchedule.setResBak3((String)map.get("RES_BAK3"));
			attendanceSchedule.setResBak4((String)map.get("RES_BAK4"));
			
			result.add(attendanceSchedule);
		}
		
		return result;
	}
	
	public GrAttendanceSchedule findAttendanceScheduleById(Long attendanceScheduleId){
		Map map = new HashMap();
		map.put("attendanceScheduleId", attendanceScheduleId);
		List<Map> list = sqlDao.findList("PropertySQL.findAttendanceScheduleById", map);
		List<GrAttendanceSchedule> result;
		GrAttendanceSchedule attendanceSchedule=null;
		if(list.isEmpty())
			return null;
		else {
			result = mapToAttendanceSchedule(list);
			attendanceSchedule = (GrAttendanceSchedule)result.get(0);
		}
		return attendanceSchedule;
	}
	
	public Page<HashMap> queryAllAttendanceSchedules(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectAllAttendanceSchedules", parMap);
	}

	public boolean queryAttendanceScheduleIsExist(Long attendanceScheduleUserId,String attendanceScheduleInTime,String attendanceScheduleOutTime,String attendanceScheduleState){
		Map map = new HashMap();
		map.put("attendanceScheduleUserId", attendanceScheduleUserId);
		map.put("attendanceScheduleInTime", attendanceScheduleInTime);
		map.put("attendanceScheduleOutTime", attendanceScheduleOutTime);
		map.put("attendanceScheduleState", attendanceScheduleState);
		List<Map> list = new ArrayList();
		list = sqlDao.findList("PropertySQL.queryAttendanceScheduleIsExist", map);
		
		if(list==null){
			return false;
		}
		else{
			if(list.isEmpty())
				return false;
			else
				return true;
		}
	}
	/**
	 * 查询该时间段是否 有排班记录
	 * 开发者：ywl
	 * @return
	 */
	public boolean queryNewAttendanceScheduleIsExist(Long attendanceScheduleUserId,String attendanceScheduleInTime,String attendanceScheduleOutTime,String attendanceScheduleState){
		Map map = new HashMap();
		map.put("attendanceScheduleUserId", attendanceScheduleUserId);
		map.put("attendanceScheduleInTime", attendanceScheduleInTime);
		map.put("attendanceScheduleOutTime", attendanceScheduleOutTime);
		map.put("attendanceScheduleState", attendanceScheduleState);
		List<Map> list = new ArrayList();
		list = sqlDao.findList("PropertySQL.queryNewAttendanceScheduleIsExist", map);
		
		if(list==null){
			return false;
		}
		else{
			if(list.isEmpty())
				return false;
			else
				return true;
		}
	}
	
	public boolean modifyAttendanceScheduleIsExist(Long attendanceScheduleId,Long attendanceScheduleUserId,String attendanceScheduleInTime,String attendanceScheduleOutTime,String attendanceScheduleState){
		Map map = new HashMap();
		map.put("attendanceScheduleUserId", attendanceScheduleUserId);
		map.put("attendanceScheduleInTime", attendanceScheduleInTime);
		map.put("attendanceScheduleOutTime", attendanceScheduleOutTime);
		map.put("attendanceScheduleState", attendanceScheduleState);
		List<Map> list = new ArrayList();
		list = sqlDao.findList("PropertySQL.queryAttendanceScheduleIsExist", map);
		for(Map resultMap:list){
			if(Long.parseLong(String.valueOf(resultMap.get("ATTENDANCE_SCHEDULE_ID")))!=attendanceScheduleId)
				return true;
		}
		
		return false;
	}
}
