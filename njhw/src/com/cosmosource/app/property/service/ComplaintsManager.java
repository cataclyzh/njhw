package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrComplaints;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/** 
* @description: 投诉处理
* @author chengyun
* @date 2013-07-03
*/ 
@SuppressWarnings("unchecked")
public class ComplaintsManager extends BaseManager {
	
	public void addOneComplaint(Map map){
		List<Map> list = new ArrayList();
		list.add(map);
		sqlDao.batchInsert("PropertySQL.insertComplaint", list);
	}
	
	public void addComplaints(List<GrComplaints> complaints){
		List<Map> list = new ArrayList();
		Map map = null;
		for(GrComplaints complaint:complaints){
			map=new HashMap();
			map.put("complaintsTitle", complaint.getComplaintsTitle());
			map.put("complaintsInTime", complaint.getComplaintsInTime());
			map.put("complaintsUserId", complaint.getComplaintsUserId());
			map.put("complaintsUser", complaint.getComplaintsUser());
			map.put("complaintsTelephone", complaint.getComplaintsTelephone());
			map.put("complaintsSatisfy", complaint.getComplaintsSatisfy());
			map.put("complaintsDetail", complaint.getComplaintsDetail());
			map.put("complaintsFeedback", complaint.getComplaintsFeedback());
			map.put("complaintsState", complaint.getComplaintsState());
			map.put("complaintsOverTime", complaint.getComplaintsOverTime());
			map.put("complaintsProcessUser", complaint.getComplaintsProcessUser());
			map.put("complaintsProcessPhone", complaint.getComplaintsProcessPhone());
			map.put("complaintsProcessResult", complaint.getComplaintsProcessResult());

			list.add(map);
		}
		sqlDao.batchInsert("PropertySQL.insertComplaint", list);
	}
	
	public void deleteOneComplaint(Long complaintsId){
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		map.put("complaintsId", complaintsId);
		list.add(map);
		
		sqlDao.batchDelete("PropertySQL.deleteComlaint", list);
	}
	
	public void deleteComplaints(Long[] complaintsIds){
		List<Map> list = new ArrayList();
		Map map = null;
		for(Long complaintsId:complaintsIds){
			map = new HashMap();
			map.put("complaintsId", complaintsId);
			list.add(map);
		}
		sqlDao.batchDelete("PropertySQL.deleteComlaint", list);
	}
	
	public void updateOneComplaint(Map map){
		List<Map> list = new ArrayList();
		list.add(map);
		sqlDao.batchUpdate("PropertySQL.updateComlaint", list);
	}
	
	public void updateComplaints(List<GrComplaints> complaints){
		List<Map> list = new ArrayList();
		Map map = null;
		for(GrComplaints complaint : complaints){
			map = new HashMap();
			map.put("complaintsId", complaint.getComplaintsId());
			map.put("complaintsTitle", complaint.getComplaintsTitle());
			map.put("complaintsInTime", complaint.getComplaintsInTime());
			map.put("complaintsUserId", complaint.getComplaintsUserId());
			map.put("complaintsUser", complaint.getComplaintsUser());
			map.put("complaintsTelephone", complaint.getComplaintsTelephone());
			map.put("complaintsSatisfy", complaint.getComplaintsSatisfy());
			map.put("complaintsDetail", complaint.getComplaintsDetail());
			map.put("complaintsFeedback", complaint.getComplaintsFeedback());
			map.put("complaintsState", complaint.getComplaintsState());
			map.put("complaintsOverTime", complaint.getComplaintsOverTime());
			map.put("complaintsProcessUser", complaint.getComplaintsProcessUser());
			map.put("complaintsProcessPhone", complaint.getComplaintsProcessPhone());
			map.put("complaintsProcessResult", complaint.getComplaintsProcessResult());
	
			list.add(map);
		}
		sqlDao.batchUpdate("PropertySQL.updateComlaint", list);
	}
	
//	public List<GrComplaints> queryComplaints(String complaintsTitle,Date complaintsInTime,Date complaintsOverTime,String complaintsUser,String complaintsState){
//		Map map = new HashMap();
//		map.put("complaintsTitle", complaintsTitle);
//		map.put("complaintsInTime", complaintsInTime);
//		map.put("complaintsOverTime", complaintsOverTime);
//		map.put("complaintsUser", complaintsUser);
//		map.put("complaintsState", complaintsState);
//		List<Map> list = sqlDao.findList("PropertySQL.selectAllComplaints", map);
//		if(list.isEmpty())
//			return null;
//		else {
//			return mapToComplaints(list);
//		}		
//	}
	
	public List<GrComplaints> mapToComplaints(List<Map> list){
		List<GrComplaints> result = new ArrayList<GrComplaints>();
		GrComplaints complaint;
		for(Map map: list){
			complaint = new GrComplaints();
			complaint.setComplaintsId(Long.parseLong(String.valueOf(map.get("COMPLAINTS_ID"))));
			complaint.setComplaintsTitle((String)map.get("COMPLAINTS_TITLE"));
			complaint.setComplaintsInTime((Date)map.get("COMPLAINTS_INTIME"));
			if(map.get("COMPLAINTS_USERID")!=null)
				complaint.setComplaintsUserId(Long.parseLong((String)map.get("COMPLAINTS_USERID")));
			complaint.setComplaintsUser((String)map.get("COMPLAINTS_USER"));
			complaint.setComplaintsTelephone((String)map.get("COMPLAINTS_TELEPHONE"));
			complaint.setComplaintsSatisfy((String)map.get("COMPLAINTS_SATISFY"));
			complaint.setComplaintsDetail((String)map.get("COMPLAINTS_DETAIL"));
			complaint.setComplaintsFeedback((String)map.get("COMPLAINTS_FEEDBACK"));
			complaint.setComplaintsState((String)map.get("COMPLAINTS_STATE"));
			if(map.get("COMPLAINTS_OVERTIME")!=null)
				complaint.setComplaintsOverTime((Date)map.get("COMPLAINTS_OVERTIME"));
			else
				complaint.setComplaintsOverTime(null);
			complaint.setComplaintsProcessUser((String)map.get("COMPLAINTS_PROCESS_USER"));
			complaint.setComplaintsProcessPhone((String)map.get("COMPLAINTS_PROCESS_PHONE"));
			complaint.setComplaintsProcessResult((String)map.get("COMPLAINTS_PROCESS_RESULT"));
			
			
			result.add(complaint);
		}
		return result;
	}
	
	public GrComplaints findComplaintById(Long complaintsId){
		Map map = new HashMap();
		map.put("complaintsId", complaintsId);
		List<Map> list = sqlDao.findList("PropertySQL.findComplaintById", map);
		List<GrComplaints> result;
		GrComplaints complaint =null;
		if(list.isEmpty())
			return null;
		else {
			result = mapToComplaints(list);
			complaint = (GrComplaints)result.get(0);
		}
		return complaint;
	}
	
	public Page<HashMap> queryAllComplaints(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectAllComplaints", parMap);
	}
	
	public Page<HashMap> queryMyComplaints(final Page<HashMap> page, HashMap<String, String> parMap){
		return sqlDao.findPage(page, "PropertySQL.selectMyComplaints", parMap);
	}
}
