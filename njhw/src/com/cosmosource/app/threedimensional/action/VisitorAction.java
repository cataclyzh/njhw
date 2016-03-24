package com.cosmosource.app.threedimensional.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cosmosource.app.buildingmon.service.BuildMonManager;
import com.cosmosource.app.threedimensional.service.VisitorManager;
import com.cosmosource.app.wirelessLocation.service.WirelessManager;
import com.cosmosource.app.wirelessLocation.service.WirelessSqlManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings({ "unchecked", "serial" })
public class VisitorAction extends BaseAction {
	private WirelessSqlManager wirelessSqlManager;
	private WirelessManager wirelessManager;
	private Page<HashMap> page = new Page<HashMap>(5);// 默认每页5条记录
	private VisitorManager visitorManager;
	private BuildMonManager buildMonManager;
	// 访客列表
	public String visitorList() {
		int p1 = 1;
		p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
				.valueOf(this.getRequest().getParameter("pageNo").trim());
		page.setPageNo(p1);
		try {
			this.page = visitorManager.getVisitorList(page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return SUCCESS;
	}

	// 访客异常列表
	public String visitorErrorList() {
		int p1 = 1;
		p1 = (null == this.getRequest().getParameter("pageNo")) ? 1 : Integer
				.valueOf(this.getRequest().getParameter("pageNo").trim());
		page.setPageNo(p1);
		try {
			this.page = visitorManager.getVisitorErrorList(page);
			List<HashMap> list = page.getResult();
			HashMap map = new HashMap();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					map = list.get(i);
					if (((String) map.get("SHORT")).length() > 5) {
						map.put("SHORT", ((String) map.get("SHORT")).substring(
								0, 4)
								+ "...");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return SUCCESS;
	}
	//访客在实际访问开始和结束的时间内经过的一组设备ID
	public String visitorLineIds() 
	{
		JSONObject json = new JSONObject();
		try {
		//String visitorName = getRequest().getParameter("");
		JSONArray jsonArray = null;
	    Date beginTime = null;	
	    Date endTime = null;
	    String ids = "";		
		String visitorID = "4571";
		String time = getRequest().getParameter("time"); 
		if(!"".equals(time)&& null!=time)
		{
		String t [] = time.split(",");
		String beginTim = t [0];
		String endTim = t [1];
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		beginTime = sdf.parse(beginTim);
		endTime = sdf.parse(endTim);
		}
		List <HashMap<String,String>> visitorList = visitorManager.getVistorIDs(visitorID);
		List <HashMap<String,String>> auxiList = visitorManager.getAuxiIDs(visitorID);
		if(visitorList.size()>0)
		{
			for (int i = 0; i < visitorList.size(); i++) {
				ids += visitorList.get(i).get("SET_NO")+",";
			}
			//判断访客有无照片
			String photo = visitorList.get(0).get("RES_BAK2");
			if("".equals(photo))
			{
				visitorList.get(0).put("RES_BAK2", "NONE");
			}
			//通过无线定位卡和时间判断访客经过的一组信标点位
			String cardNum = visitorList.get(0).get("RES_BAK3");
			String tagMac = wirelessManager.getqueryTagMacByIDCradNum(cardNum);
			if(tagMac == null || tagMac.trim().length() == 0){
				visitorList.get(0).put("DEVICEID", "NONE");
			}else{
				jsonArray = wirelessSqlManager.getqueryHistoryList(tagMac,beginTime,endTime);
			}
		}   
		if(null!=auxiList && auxiList.size()>0)
		{
			for (int i = 0; i < auxiList.size(); i++) {
				ids += auxiList.get(i).get("SET_NO")+",";
			}
			String cardNum = auxiList.get(0).get("VA_BAK2");
			//随行人员无照片
			auxiList.get(0).put("RES_BAK2", "NONE");
			//通过无线定位卡和时间判断访客经过的一组信标点位
			String tagMac = wirelessManager.getqueryTagMacByIDCradNum(cardNum);
			if("".equals(tagMac))
			{
				visitorList.get(0).put("DEVICEID", "NONE");
			} 
			else
			{
				jsonArray = wirelessSqlManager.getqueryHistoryList(tagMac,beginTime,endTime);
			}
		}   
		JSONArray jsonArrayVisitor = JSONArray.fromObject(visitorList);
		JSONArray jsonArrayAuxi = JSONArray.fromObject(auxiList);
		json.put("jsonArrayVisitor", jsonArrayVisitor);
		json.put("jsonArrayAuxi", jsonArrayAuxi);
		//json.put("ids", ids.substring(0,ids.length()-1));
		json.put("jsonArray", jsonArray);
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (net.sf.json.JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	//访客在实际访问开始和结束的时间内经过的一组设备ID 及访客的信息
	public String visitorIds() {
		try {
			JSONObject json = new JSONObject();
			String visitorId = getRequest().getParameter("visitorID");
			String timeIn = getRequest().getParameter("timeIn");
			//从访客表中查询
			List<Map<String ,String>> visitorInfoList = visitorManager.getVisitByVisitorIdAndTimeIn(Long.parseLong(visitorId), timeIn);
			//JSONArray jsonArrayVisitor =  JSONArray.fromObject(visitorInfoList);
			//json.put("jsonArrayVisitor", jsonArrayVisitor);
			if(visitorInfoList != null && visitorInfoList.size() > 0){
				Map<String, String> visitorInfo = visitorInfoList.get(0);
				String cardNum = visitorInfo.get("WIRELESS_CARD");
				String tagMac = wirelessManager.getqueryTagMacByIDCradNum(cardNum);
				if(tagMac == null || tagMac.trim().length() == 0){
					visitorInfo.put("DEVICEID", "NONE");
				}else{
					String endTime = null;
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String endTime1 = visitorInfo.get("TIME_OUT1");
					String endTime2 = visitorInfo.get("TIME_OUT2");
					if(endTime1 != null && endTime1.trim().length() > 0) {
						endTime = endTime1;
					}else{
						endTime = endTime2;
					}
					
					Map history = wirelessSqlManager.getVisitHistory(tagMac, timeIn, endTime);
					history = wirelessSqlManager.changePointResult(history);
					
					Iterator it = history.entrySet().iterator();
					String pointIds = "";
					while(it.hasNext()){
						Map.Entry<String, String> entry = (Entry<String, String>) it.next();
						pointIds = pointIds + entry.getKey() + ",";
					}
					if(pointIds.length() > 0){
						visitorInfo.put("DEVICEID", pointIds.substring(0, pointIds.length()-1));
					}else{
						visitorInfo.put("DEVICEID", "NONE");
					}
				}
				JSONArray jsonArrayVisitor =  JSONArray.fromObject(visitorInfoList);
				json.put("jsonArrayVisitor", jsonArrayVisitor);
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	//根据访客证件号查询访客经过的一组ID
	public String getVisitIDs()
	{
		JSONObject json = new JSONObject();
		JSONArray jsonArray = null;
		String ids = "";
		String cardType = getRequest().getParameter("cardType");
		String cardId = getRequest().getParameter("cardId");
		List<Map<String,String>> list = visitorManager.getVistorCardNumAndTimes(cardId, cardType);
		if(null!=list && list.size()>0)
		{
			try {
			Date endTime = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String beginTim = list.get(0).get("BEGINTIME");
			String endTim = list.get(0).get("ENDTIME");
			String cardNum = list.get(0).get("CARDNUM");
			if(null== endTim || "".equals(endTim))
			{
				endTime = DateUtil.getSysDate();
			}
			else{
			 endTime = sdf.parse(endTim);
			}
			Date beginTime = sdf.parse(beginTim);
			String tagMac = wirelessManager.getqueryTagMacByIDCradNum(cardNum);
			if(tagMac == null || tagMac.trim().length() == 0){
				return null;
			}
			jsonArray = wirelessSqlManager.getqueryHistoryList(tagMac,beginTime,endTime);
			if(jsonArray!=null && !"".equals(jsonArray) && jsonArray.size()>0)
			{
				for (int i = 0; i < jsonArray.size(); i++) {
					ids += jsonArray.getJSONObject(i).get("coordinatesId")+",";
				}
				json.put("ids", ids.substring(0, ids.length()-1));
			}
		//	json.put("ids", "10199,10197,10188");
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
			}catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//根据访客信息取历史轨迹
	public String getHistoryList() {
		try {
			String cardId = getRequest().getParameter("cardId");
			String start = getRequest().getParameter("startTime");
			String end = getRequest().getParameter("endTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (cardId != null && start != null && end != null) {
				String startTime = sdf.format(new Date(start));
				String endTime = sdf.format(new Date(end));

				List<HashMap> list = visitorManager.getHistoryByVisitor(cardId,
						startTime, endTime);
				if (list != null && list.size() > 0) {
					JSONArray json = wirelessSqlManager.getqueryHistoryList(
							(String) list.get(0).get("BAK"), sdf.parse(start.replaceAll("/", "-")),
							sdf.parse(end.replaceAll("/", "-")));
					Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
							"no-cache:true");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getModel() {
		return null;
	}
	

	public BuildMonManager getBuildMonManager() {
		return buildMonManager;
	}

	public void setBuildMonManager(BuildMonManager buildMonManager) {
		this.buildMonManager = buildMonManager;
	}

	public WirelessManager getWirelessManager() {
		return wirelessManager;
	}

	public void setWirelessManager(WirelessManager wirelessManager) {
		this.wirelessManager = wirelessManager;
	}

	public WirelessSqlManager getWirelessSqlManager() {
		return wirelessSqlManager;
	}

	public void setWirelessSqlManager(WirelessSqlManager wirelessSqlManager) {
		this.wirelessSqlManager = wirelessSqlManager;
	}

	public VisitorManager getVisitorManager() {
		return visitorManager;
	}

	public void setVisitorManager(VisitorManager visitorManager) {
		this.visitorManager = visitorManager;
	}
	
	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}
}
