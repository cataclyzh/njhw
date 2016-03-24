package com.cosmosource.app.property.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.CsRepairFault;
import com.cosmosource.app.property.service.CallCenterManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/** 
* @description: 物业呼叫中心
* @author zh
* @date 2013-03-21
*/ 
@SuppressWarnings("unchecked")
public class CallCenterAction extends BaseAction<Object> {
	
	// 定义全局变量
	private static final long serialVersionUID = 4227875753301925460L;
	// 定义实体变量
	private CsRepairFault csRepairFault = new CsRepairFault();
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录
	// 定义注入对象
	private CallCenterManager callCenterManager;
	
	/** 
	* @title: init
	* @description: 初始化报修单
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String init() {
		return SUCCESS;
	}
	
	/** 
	* @title: list
	* @description: 报修单
	* @author zh
	* @date 2013-03-21
	*/ 
	public String list() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		
		// 从首页快捷跳转过来
		String indexPage = getParameter("isIndexPage");
		String crfFlag = "";
		if ("y".equals(indexPage))	
			crfFlag = CsRepairFault.CRF_FLAG_APP;
		else 
			crfFlag = getParameter("crfFlag");
		
		map.put("flag", crfFlag);
		map.put("beginTime", getParameter("beginTime"));
		map.put("endTime", getParameter("endTime"));
		map.put("userName", getParameter("userName"));
		map.put("selectType", "list");
		page = callCenterManager.querySheet(page, map);
		
		// 向前端传递查询条件
		getRequest().setAttribute("user_id", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		getRequest().setAttribute("beginTime", getParameter("beginTime"));
		getRequest().setAttribute("endTime", getParameter("endTime"));
		getRequest().setAttribute("crfFlag", crfFlag);
		return SUCCESS;
	}
	
	/** 
	* @title: mySheetinit
	* @description: 初始化我的维修单
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String mySheetInit() {
		return SUCCESS;
	}
	
	/** 
	* @title: list
	* @description: 查询我的维修单
	* @author zh
	* @date 2013-03-21
	*/ 
	
	public String mySheet() throws Exception {
		// 从首页快捷跳转过来
		String indexPage = getParameter("isIndexPage");
		String crfFlag = "";
		if ("y".equals(indexPage))	
			crfFlag = CsRepairFault.CRF_FLAG_SURE;
		else 
			crfFlag = getParameter("crfFlag");
		
		HashMap map = new HashMap();
		map.put("flag", crfFlag);
		map.put("beginTime", getParameter("beginTime"));
		map.put("endTime", getParameter("endTime"));
		map.put("userName", getParameter("userName"));
		map.put("selectType", "mySheet");
		map.put("crfBak1", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		page = callCenterManager.querySheet(page, map);
		
		// 向前端传递查询条件
		getRequest().setAttribute("beginTime", getParameter("beginTime"));
		getRequest().setAttribute("endTime", getParameter("endTime"));
		getRequest().setAttribute("crfFlag", crfFlag);
		return SUCCESS;
	}
	
	/** 
	* @title: showInput
	* @description: 显示一键报修页面
	* @author zh
	* @date 2013-03-21
	*/ 
	public String showInput() {
		getRequest().setAttribute("typeList", callCenterManager.loadAssetype());
		return SUCCESS;
	}
	
	/** 
	* @title: loadAssetinfo
	* @description: 根据设备类型ID加载设备
	* @author zh
	* @date 2013-03-21
	*/ 
	public String loadAssetinfo() {
		JSONObject json = new JSONObject();
		List list = callCenterManager.loadAssetinfo(getParameter("aetId"));
		try {
			if (list != null &&  list.size() > 0) {
				json.put("list", list);
				json.put("status", 0);
			} else {
				json.put("status", 1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: save
	* @description: 保存报修单信息
	* @author zh
	* @date 2013-03-21
	*/ 
	public String save() {
		try {
			callCenterManager.save(csRepairFault);
			Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* @title: optShow
	* @description: 显示操作页面
	* @author zh
	* @date 2013-03-21
	*/
	public String optShow() {
		csRepairFault = callCenterManager.getCsRepairFaultById(Long.parseLong(getParameter("crfid")));
		if (csRepairFault != null) {
			getRequest().setAttribute("opt", getParameter("opt"));
			getRequest().setAttribute("csRepairFault", csRepairFault);
			System.out.println(callCenterManager.getMaintainPerson());
			if ("allot".equals(getParameter("opt")))
					getRequest().setAttribute("personList", callCenterManager.getMaintainPerson());
			return SUCCESS;
		} else return ERROR;
	}
	
	/** 
	* @title: allot
	* @description: 派单
	* @author zh
	* @date 2013-03-21
	*/
	public String allot() {
		int status = 0;
		try {
			status = callCenterManager.saveAllot(csRepairFault);
			if (status == 0) setIsSuc("true");
			else setIsSuc("statusError");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 
	* @title: complete
	* @description: 完成报修
	* @author zh
	* @date 2013-03-21
	*/
	public String complete() {
		int status = 0;
		long crfid = Long.parseLong(getParameter("crfId"));
		String crfRdesc = getParameter("crfRdesc");
		try{
			status = callCenterManager.saveComplete(crfid, crfRdesc);
			if (status == 0) setIsSuc("true");
			else setIsSuc("statusError");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 
	* @title: assess
	* @description: 报修评价
	* @author zh
	* @date 2013-03-21
	*/
	public String assess() {
		int status = 0;
		long crfid = Long.parseLong(getParameter("crfId"));
		String crfSatis = getParameter("crfSatis");
		String crfComment = getParameter("crfComment");
		try{
			status = callCenterManager.saveAssess(crfid, crfSatis, crfComment);
			if (status == 0) setIsSuc("true");
			else setIsSuc("statusError");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/** 
	* @title: showSheet
	* @description: 查看报修单
	* @author zh
	* @date 2013-03-21
	*/
	public String showSheet() {
		csRepairFault = callCenterManager.getCsRepairFaultById(Long.parseLong(getParameter("crfid")));
		if (csRepairFault != null) {
			getRequest().setAttribute("csRepairFault", csRepairFault);
			return SUCCESS;
		} else return ERROR;
	}
	
	/** 
	* @title: delete
	* @description: 删除报修单
	* @author zh
	* @date 2013-03-21
	*/
	public String delete() {
		int status = callCenterManager.delete(Long.parseLong(getParameter("crfid")));
		if (status == 0)  Struts2Util.renderText("true");
		else Struts2Util.renderText("false");
		return null;
	}
	
	/** 
	* @title: showInput
	* @description: 显示报修统计页面
	* @author zh
	* @date 2013-03-21
	*/ 
	public String showStatistics() {
		getRequest().setAttribute("yearList", callCenterManager.loadYears());		// 年
		getRequest().setAttribute("orgList", callCenterManager.loadOrg());			// 机构
		getRequest().setAttribute("typeList", callCenterManager.loadAssetype());	// 设备类型
		return SUCCESS;
	}
	
	/**
	* @title: statistics
	* @description: 报修统计
	* @author zh
	* @date 2013-03-22
	*/
	@SuppressWarnings("unchecked")
	public String statistics() {
		// 参数处理
		HashMap map = new HashMap();
		String month = getParameter("month");
		if (Integer.parseInt(month) <=9)	map.put("month", "0"+month);
		map.put("year",  getParameter("year"));
		map.put("orgId", getParameter("orgId"));
		map.put("aetId", getParameter("aetId"));
		
		// 统计数据
		List rstList = callCenterManager.statisticsSheet(map);
		if (rstList.size() > 0 && rstList != null) {
			getRequest().setAttribute("rstList", rstList);
		}
		
		// 向前端传递查询条件
		map.put("month", month);
		getRequest().setAttribute("params", map);
		getRequest().setAttribute("yearList", callCenterManager.loadYears());		// 年
		getRequest().setAttribute("orgList", callCenterManager.loadOrg());			// 机构
		getRequest().setAttribute("typeList", callCenterManager.loadAssetype());	// 设备类型
		return SUCCESS;
	}
	
	public CsRepairFault getCsRepairFault() {
		return csRepairFault;
	}

	public void setCsRepairFault(CsRepairFault csRepairFault) {
		this.csRepairFault = csRepairFault;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public CallCenterManager getCallCenterManager() {
		return callCenterManager;
	}

	public void setCallCenterManager(CallCenterManager callCenterManager) {
		this.callCenterManager = callCenterManager;
	}

	@Override
	public CsRepairFault getModel() {
		return csRepairFault;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
	}
}
