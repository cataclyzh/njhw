package com.cosmosource.app.threedimensional.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.cosmosource.app.threedimensional.service.PropertyManager;
import com.cosmosource.app.utils.Constant;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings({ "unchecked", "serial" })
public class PropertyAction extends BaseAction {
	
	private Page<HashMap> page = new Page<HashMap>(5);// 默认每页5条记录
	private PropertyManager propManager;
	private HashMap patrolMap = new HashMap();

	public String getUserByOrg(){
		try {
            JSONObject json = new JSONObject();
            Long orgId = Long.parseLong(getParameter("orgId"));
            List<HashMap> list = new ArrayList<HashMap>();
            list = propManager.getUserByOrg(orgId);
            
            json.put("mapList", list);
            Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String getTimeByUser(){
		try {
            JSONObject json = new JSONObject();
            Long orgId = Long.parseLong(getParameter("orgId"));
            Long userId = Long.parseLong(getParameter("userId"));
            List<HashMap> list = new ArrayList<HashMap>();
            list = propManager.getTimeByUser(orgId, userId);
            json.put("mapList", list);
            Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String getLineByOrg(){
		try {
            JSONObject json = new JSONObject();
            Long orgId = Long.parseLong(getParameter("orgId"));
            List<HashMap> list = new ArrayList<HashMap>();
            list = propManager.getLineByOrg(orgId);

            json.put("mapList", list);
            Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String getUserByLine(){
		try {
            JSONObject json = new JSONObject();
            Long orgId = Long.parseLong(getParameter("orgId"));
            Long lineId = Long.parseLong(getParameter("lineId"));
            List<HashMap> list = new ArrayList<HashMap>();
            list = propManager.getUserByLine(orgId, lineId);
            
            json.put("mapList", list);
            Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String getTime(){
		try {
            JSONObject json = new JSONObject();
            Long orgId = Long.parseLong(getParameter("orgId"));
            Long lineId = Long.parseLong(getParameter("lineId"));
            Long userId = Long.parseLong(getParameter("userId"));
            List<HashMap> list = new ArrayList<HashMap>();
            list = propManager.getTime(orgId, lineId, userId);
        
            json.put("mapList", list);
            Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public void getOrgIds() {
		try {
			JSONObject json = new JSONObject();
			List<HashMap> list = new ArrayList<HashMap>();
			HashMap map = new HashMap();
			map.put("orgId", Constant.ORG_CLEANING_ID);
			map.put("orgName", "保洁");
			list.add(map);
			map = new HashMap();
			map.put("orgId", Constant.ORG_PATROL_ID);
			map.put("orgName", "巡更");
			list.add(map);
			map = new HashMap();
			map.put("orgId", Constant.ORG_REPAIR_ID);
			map.put("orgName", "维修");
			list.add(map);

			json.put("mapList", list);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
					"no-cache:true");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String patrolExceptionList(){
		int p1 = 1;
		p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
				.valueOf(this.getRequest().getParameter("pageNo").trim());
		page.setPageNo(p1);
		try {
			this.page = propManager.patrolExceptionList(page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return SUCCESS;
	}
	
	public String patrolExceptionDetail() {
		Long scheduleId = Long.parseLong(getParameter("scheduleId"));
		//除去 ID 还需要 日期时间信息
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		
		patrolMap = propManager.patrolExceptionDetail(scheduleId,startTime,endTime);
		return SUCCESS;
	}
	
	//根据物业人员的姓名和当班时间段获取执勤路线和经过信标的ID
/*	public String getPropertyInformation()
	{
		try {
			net.sf.json.JSONObject json = new net.sf.json.JSONObject();
			//String staffName = getRequest().getParameter("name");
			String time = getRequest().getParameter("time"); 
			String t [] = time.split(",");
			String beginTime = t [0];
			String endTime = t [1];
			String staffID = getRequest().getParameter("staffID"); 
			//获取物业人员执勤的开始和结束时间
			//String beginTime = getRequest().getParameter("");
			//String endTime = getRequest().getParameter("");
			//String beginTime ="2013/8/20 14:00:00"; 
		    //String endTime ="2013/8/20 17:00:00";
			List<HashMap<String,String>> property = propManager.getPropertyInfo(staffID, beginTime, endTime);
			//从对象中取出路线描述信息和路线名称
			String lineDescription = "";
			String lineName = "";
			String exceptionDescription = "";
			String orgName = "";
			String staffName = "";
			String ids []={};
			String ids2 [] = {};
			//预设的点位
			String deviceIDs = "";
			//实际走过的点位
			String deviceIDs2 = "";
			if(property.size()>0)
			{
				lineDescription = property.get(0).get("PATROL_LINE_DESC");
				lineName = property.get(0).get("PATROL_LINE_NAME");
				exceptionDescription = property.get(0).get("EXCEPTION_DESC");
				orgName = property.get(0).get("ORG_NAME");
				staffName = property.get(0).get("USER_NAME");
				if(!("".equals(exceptionDescription)))
				{
					ids = exceptionDescription.split(",");
					ids2 = exceptionDescription.split(",");
					for (int i = 0; i < ids.length; i++) {
						int j =  ids[i].indexOf("|");
						ids[i] = ids[i].substring(0,j).trim().toString();
						deviceIDs += ids[i]+",";
					}
					List list = new ArrayList();
					for (int i = 0; i < ids2.length; i++) {
						 int j =  ids2[i].indexOf("|");
						if(ids2[i].length()>j+1)
						{
							list.add(ids2[i].substring(0,j));
						}
					}
					for (int i = 0; i < list.size(); i++) {
						deviceIDs2 +=  list.get(i).toString()+",";
					}
				}
				json.put("orgName",orgName);
				json.put("staffName",staffName);
				json.put("lineName",lineName);
				json.put("lineDescription", lineDescription);
				//json.put("ids", ids);
				json.put("deviceIDs", deviceIDs.substring(0,deviceIDs.length()-1));
				if(null!=deviceIDs2 && !"".equals(deviceIDs2))
				{
					json.put("deviceIDs2", deviceIDs2.substring(0,deviceIDs2.length()-1));
				}else
				{
					json.put("deviceIDs2", "");	
				}
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
			}else
			{
				json.put("lineName", "loss");
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
			}
		} catch (net.sf.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	
	public void getPropertyInformation(){
//	public void getPatrolData(){
		String staffID = getParameter("staffID"); //员工 ID 
//		staffID = "4508";
//		String beginTime = getParameter("beginTime");
//		beginTime= "2014/01/13 14:00:00";
//		String endTime = getParameter("endTime"); 2014/01/13 14:00:00,2014/01/13 15:00:00
//		endTime = "2014/01/13 15:00:00";
		
		String time = getRequest().getParameter("time"); 
		String t [] = time.split(",");
		String beginTime = t [0];
		String endTime = t [1];
		
		if (StringUtil.isNotBlank(staffID) && StringUtil.isNotBlank(beginTime) && StringUtil.isNotBlank(endTime)) {
			
			String json = propManager.patrolData(staffID, beginTime, endTime);
			logger.info(json);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		}
	}
	

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getModel() {
		return null;
	}
	
	public void setPropManager(PropertyManager propManager) {
		this.propManager = propManager;
	}
	
	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public HashMap getPatrolMap() {
		return patrolMap;
	}

	public void setPatrolMap(HashMap patrolMap) {
		this.patrolMap = patrolMap;
	}
}
