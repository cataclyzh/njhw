package com.cosmosource.app.attendance.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cosmosource.app.attendance.service.NewCityAttendanceManager;
import com.cosmosource.app.attendance.vo.Type;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("rawtypes")
public class NewCityAttendanceAction extends BaseAction {

	/**
	 * 设置各单位领导 Action 单位领导能查看本单位所有人员考勤 部门领导能查看本部门所有人员考勤 普通用户只能查看自己的考勤
	 * 
	 * @author ChunJing 2014-03-27
	 */
	private static final long serialVersionUID = -458718007429457276L;

	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录
	// 定义注入对象
	private NewCityAttendanceManager newCityAttendanceManager;
	// 部门列表
	private Map departmentSelect = new LinkedHashMap();

	// 查询考勤记录
	@SuppressWarnings("unchecked")
	public String getAttendancesList() {
		
		String name = getRequest().getParameter("name");
		String selectDepartment = getRequest().getParameter("selectDepartment");
		String organizationSelect = getRequest().getParameter("organizationSelect");

		String startTime = getRequest().getParameter(
				"startTime");
		String endTime = getRequest().getParameter(
				"endTime");
		
		String type = getRequest().getParameter(
				"type");

		if (startTime == null
				|| startTime.trim().length() == 0) {
			startTime = DateUtils.getFirstDayOfMonth();
		}

		if (endTime == null
				|| endTime.trim().length() == 0) {
			endTime = DateUtils.getCurrentDateStr();
		}
		
		getRequest().setAttribute("opt", getRequest().getParameter("opt"));
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("startTime",
				startTime);
		getRequest().setAttribute("endTime",
				endTime);
		getRequest().setAttribute("selectDepartment", selectDepartment);
		getRequest().setAttribute("organizationSelect", organizationSelect);

		// 根据当前登陆用户ID来判断，具体显示的内容
		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		String org_id = this.getRequest().getSession()
				.getAttribute(Constants.ORG_ID).toString();
		
		Map paramsMap = new HashMap();
		paramsMap.put("user_id", uid);
		paramsMap.put("top_org_id", org_id);
		
		if (null != name && name.length()>0) {
			// 按姓名过滤
			paramsMap.put("user_name", name);
		}
		if (null != selectDepartment && !"0".equals(selectDepartment)) {
			// 按部门过滤
			paramsMap.put("org_id", selectDepartment);
		}
		
		if (null != startTime 
				&& startTime.length() > 0) {
			// 按姓名过滤
			paramsMap.put("start_time", startTime);
		}
		if (null != endTime
				&& endTime.length()>0) {
			// 按姓名过滤
			paramsMap.put("end_time", endTime);
		}
		
		//添加周六、周日信息，统计的时候不包括周六、周日
//		paramsMap.put("weekends", AttendanceUtil.getWeekends(startTime,endTime));
		
		// 2 ： 部门领导
		// 1 ：单位领导
		List leaderList = newCityAttendanceManager.checkLeader(uid);
		

		String leaderLevel = "0"; // 默认为普通用户
		int leaderShip = 0;
		Map leaderMap = null;
		Iterator<HashMap> iter = null;
		String top_org_id = "";

		if (!leaderList.isEmpty() && leaderList.size() > 0) {
			try {
				leaderMap = (Map) leaderList.get(0);
				leaderLevel = ((BigDecimal) leaderMap.get("STAGE"))
						.toPlainString();
				BigDecimal topOrgId = (BigDecimal) leaderMap.get("TOP_ORG_ID");
				top_org_id = topOrgId.toPlainString();
				leaderShip = Integer.valueOf(leaderLevel);
			} catch (Exception e) {
				logger.error(DateUtil.date2Str(new Date(), "yyyy-MM-dd")
						+ " : " + e.getMessage());
			}

		}
		
		//判断是否是考勤管理员
		if (newCityAttendanceManager.isOrgAttendanceAdmin(uid)) {
			leaderShip = 1;
		}
		//判断是否是大厦考勤管理员
		if (newCityAttendanceManager.isBuildingAtt(uid)) {
			leaderShip = 3;
		}
		
		/**
		 * 获取考勤实时统计数据
		 * 按大厦、单位、部门统计
		 */
		List<Integer> realTimeAttendanceList = new LinkedList<Integer>();
		
		//leaderShip = 1;
		
		switch (leaderShip) {

		case 1:
			// 根据登陆 id 来查找部门列表
			List deptList = newCityAttendanceManager.getDeptList(top_org_id);

			iter = deptList.iterator();
			departmentSelect.put("0", "全部");
			while (iter.hasNext()) {
				HashMap hashMap = (HashMap) iter.next();
				departmentSelect.put(hashMap.get("ORG_ID").toString(),
						hashMap.get("NAME"));
			}

			/**
			 * 单位领导， 可查看单位所有人员考勤信息， 
			 * 截止到昨天的考勤历史考勤记录
			 *  默认展示当前月考勤信息 
			 *  去除节假日信息
			 *   添加查询条件
			 */
			
			//获取单位考勤统计记录
			this.page = newCityAttendanceManager.getUnitAttendanceList(page,paramsMap,leaderShip,type);
			
			break;
			
		case 2:
			/**
			 * 部门领导，
			 * 可查看部门所有人员考勤信息，
			 * 截止到昨天的考勤历史考勤记录 
			 * 默认展示当前月考勤信息
			 *  去除节假日信息 
			 *  添加查询条件
			 */
			
			page = newCityAttendanceManager.queryDeptAttendances(page, paramsMap,type);

			break;
			
			
		case 3:
			/**
			 * 是大厦考勤管理员
			 * 可以查看大厦所有单位人员考勤信息
			 */
			
			//单位列表
			Map unitMap = newCityAttendanceManager.getUnitListForNewCityAdmin();
			getRequest().setAttribute("organization", unitMap);
			
			
			if (null != organizationSelect && !"0".equals(organizationSelect)) {
				// 按单位过滤
				paramsMap.put("org_id", organizationSelect);
			}
			if (null != selectDepartment && !"0".equals(selectDepartment)) {
				// 按部门过滤
				paramsMap.put("department_id", selectDepartment);
			}
			
			/**
			 * 人员考勤信息
			 * 可按照单位、部门、时间来过滤
			 */
			
			this.page = newCityAttendanceManager.queryBuildingAttendances(page, paramsMap,type);
			break;
		}
		
		
		//统计实时考勤情况
		realTimeAttendanceList = newCityAttendanceManager.queryRealTimeAttendanceList(paramsMap,leaderShip);

		//实时考勤统计
		getRequest().setAttribute("realTimeAttendanceList", realTimeAttendanceList);
		
		//判断是否为单位管理员
		int isDanweiAdmin = 0;
		try{
			if(newCityAttendanceManager.isOrgAttendanceAdmin(uid)){
				//单位管理员
				isDanweiAdmin = 1;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		getRequest().setAttribute("isDanweiAdmin", isDanweiAdmin);
		
		//判断是否是单位领导
		int isLinDao = 0;
		try{
			int userType = newCityAttendanceManager.getUserType(Long.valueOf(uid)); 
			if(userType == 1 || userType == 2){
				//单位管理员
				isLinDao = 1;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		getRequest().setAttribute("isLinDao", isLinDao);

		return SUCCESS;

	}
	
	
	/**
	 * 获取部门、单位、大厦实时人员信息
	 * 按照大厦之内、跟之外分
	 */
	public String realTimeEmpDetail(){
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String scope = getRequest().getParameter("scope");
		String status = getRequest().getParameter("status_hidden");
		//人员类型，内部人员或者临时人员
		String personType = getRequest().getParameter("personType");
		
		if (personType != null && personType.length() > 0){
			condition.put("personType", personType);
		}
		
		// 根据当前登陆用户ID来判断，具体显示的内容
		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		String org_id = this.getRequest().getSession()
				.getAttribute(Constants.ORG_ID).toString();
		
		
		
		if (scope != null && scope.length() > 0) {
			condition.put("scope", scope);
			condition.put("user_id", uid);
			condition.put("top_org_id", org_id);  //当前登录用户单位 ID
		}
		
		if (status != null && !status.equals("ALL")){
			if("1".equals(status)){
				condition.put("status", "in_building");  //查询大厦之内人员
			}else if("0".equals(status)){
				condition.put("status", "out_of_building");  //查询大厦之外人员
			}else{
				condition.put("status", "unkonwn");
			}
		}
		
		getRequest().setAttribute("status_checked", status);
		getRequest().setAttribute("scope", scope);
		getRequest().setAttribute("personType", personType);
		
		/**
		 * 1、代表大厦
		 * 2、代表单位
		 * 3.代表部门
		 * 默认为 0 
		 */
		//大厦实时人员统计
		this.page = newCityAttendanceManager.getRealTimeEmpDetail(page,condition,scope);
		
		return SUCCESS;
		
	}
	

	public String attendanceManagement() {
		/*
		 * 1: 单位领导(单位管理员) 2： 部门领导 3： 普通用户
		 */

		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		int userType = newCityAttendanceManager.getUserType(uid);
		
		try{
			if(newCityAttendanceManager.isOrgAttendanceAdmin(uid)){
				//单位管理员
				userType = 1;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		/*
		 * 0: 不显示部门考勤
		 * 1: 显示部门考勤
		 */
		int isNeedBumenkaoqin = 0;
		
		/* 0: 不显示单位考勤
		 * 1: 显示单位考勤
		 */
		int isNeedDanweikaoqin = 0;
		
		/*
		 * 0: 不显示新城考勤
		 * 1: 显示新城考勤
		 */
		int isNeedXinChengkaoqin = 0;
		
		boolean isBuildingAtt = false;
		try{
			//新城考勤管理员
			isBuildingAtt = newCityAttendanceManager.isBuildingAtt(uid);
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
		}
		
		if(isBuildingAtt){
			isNeedXinChengkaoqin = 1;
			isNeedDanweikaoqin = 0;
			isNeedBumenkaoqin = 0;
		}else if(userType == 1){
			isNeedXinChengkaoqin = 0;
			isNeedDanweikaoqin = 1;
			isNeedBumenkaoqin = 0;
		}else if(userType == 2){
			isNeedXinChengkaoqin = 0;
			isNeedDanweikaoqin = 0;
			isNeedBumenkaoqin = 1;
		}else if(userType == 3){
			isNeedXinChengkaoqin = 0;
			isNeedDanweikaoqin = 0;
			isNeedBumenkaoqin = 0;
		}
		
		//isNeedDanweikaoqin = 1;

		/*
		 * 0: 代表 不显示[我的申请]
		 * 1: 代表 显示[我的申请]
		 */
		int isNeedShenqing = 1;
		try{
			if(newCityAttendanceManager.isContainsApprovers(uid)){
				isNeedShenqing = 1;
			}else{
				isNeedShenqing = 0;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}

		/*
		 * 0: 代表 不显示[申请审批]  
		 * 1: 代表 显示[申请审批]
		 */
		int isNeedShenpi = 1;
		try{
			if(newCityAttendanceManager.isContainsUsers(uid)){
				isNeedShenpi = 1;
			}else{
				isNeedShenpi = 0;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}

		String contextPath = getRequest().getContextPath();
		logger.debug("contextPath: " + contextPath);
		String navPage = contextPath + "/app/attendance/attendance_nav.jsp";
		navPage = navPage + "?isNeedShenqing=" + isNeedShenqing
				+ "&isNeedShenpi=" + isNeedShenpi 
				+ "&isNeedBumenkaoqin=" + isNeedBumenkaoqin
				+ "&isNeedDanweikaoqin=" + isNeedDanweikaoqin
				+ "&isNeedXinChengkaoqin=" + isNeedXinChengkaoqin;

		logger.info("navPage: " + navPage);
		getRequest().setAttribute("navPage", navPage);
		return SUCCESS;
	}

	/**
	 * 获取我的申请
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryApplyInfo() {
		/**
		 * 查询条件 状态 status 上午异常情况处理标志:0:无需处理 1:未处理 2:已处理 3:正在申请 类型 type 对应
		 * ATTEN_MORNING_TYPE,ATTEN_AFTERNOON_TYPE 字段 上午异常类型 1:病假 2:事假 3:公休 4:其它
		 * 开始日期 结束日期
		 */

		String status = getRequest().getParameter("status");
		String type = getRequest().getParameter("type");
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");

		getRequest().setAttribute("typeList", Type.LEAVE_LIST);
		getRequest().setAttribute("statusList", Type.STATUS_LIST);

		// 根据当前登陆用户ID来判断，具体显示的内容
		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());

		Map paramMap = new HashMap();

		if (null != status && !"all".equals(status)) {
			// 按姓名过滤
			paramMap.put("status", status);
		}
		if (null != type && !"all".equals(type)) {
			// 按部门过滤
			paramMap.put("type", type);
		}
		if (startTime == null || startTime.trim().length() == 0) {
			startTime = DateUtils.getFirstDayOfMonth();
		}

		if (endTime == null || endTime.trim().length() == 0) {
//			endTime = DateUtils.getCurrentDateStr();
			endTime = DateUtil.getStrMonthLastDay();
		}

		
		//返回给页面显示的查询条件
		getRequest().setAttribute("status", status);
		getRequest().setAttribute("type", type);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		
		//数据库查询
		paramMap.put("user_id", uid);
		paramMap.put("start_time", startTime);
		paramMap.put("end_time", endTime);

		this.page = newCityAttendanceManager.queryApplyInfoByUserId(page,
				paramMap);


		return SUCCESS;
	}

	/**
	 * 查看申请详细
	 * 
	 * @return
	 */
	public String queryApplyDetail() {

		return SUCCESS;
	}

	/**
	 * 申请审批
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAcceptInfo() {
		String name = getRequest().getParameter("name");
		String department = getRequest().getParameter("department");
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String status = getRequest().getParameter("status");

		
		//获取部门列表
		// 根据登陆 id 来查找部门列表
		String org_id = this.getRequest().getSession()
				.getAttribute(Constants.ORG_ID).toString();
				
		List deptList = newCityAttendanceManager.getDeptList(org_id);
		
		Iterator iter = null;
		
		if (deptList != null && deptList.size()>0) {
			iter = deptList.iterator();
			this.departmentSelect.put("all", "全部");
			while (iter.hasNext()) {
				HashMap hashMap = (HashMap) iter.next();
				this.departmentSelect.put(hashMap.get("ORG_ID").toString(),
						hashMap.get("NAME"));
			}
		}
		
		logger.info(departmentSelect.toString());
		
		getRequest().setAttribute("statusList", Type.STATUS_LIST);

		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);
		paramMap.put("top_org_id", org_id); //当前登录人员组织 ID 信息中心 如 70
		
		
		//按名称过滤
		if (null != name && name.length() != 0) {
			paramMap.put("user_name", name);
		}
		
		//按部门过滤
		if (null != department && !"all".equals(department)) {
			paramMap.put("org_id", department);
		}
		
		if (null != status && !"all".equals(status)) {
			// 按状态过滤
			paramMap.put("status", status);
		}
		
		if (startTime == null || startTime.trim().length() == 0) {
			startTime = DateUtils.getFirstDayOfMonth();
		}

		if (endTime == null || endTime.trim().length() == 0) {
			endTime = DateUtil.getStrMonthLastDay();
		}

		paramMap.put("start_time", startTime);
		paramMap.put("end_time", endTime);
		
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("department", department);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("status", status);
		
		this.page = newCityAttendanceManager.getApproversInfo(page, paramMap);
		return SUCCESS;
	}

	/**
	 * 查看申请审批
	 * 
	 * @return
	 */
	public String queryAcceptInfo() {

		String name = getRequest().getParameter("name");
		String department = getRequest().getParameter("department");
		String startTime = getRequest().getParameter("startTime");
		String endTime = getRequest().getParameter("endTime");
		String status = getRequest().getParameter("status");

		getRequest().setAttribute("name", name);
		getRequest().setAttribute("department", department);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("status", status);

		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", uid);

		if (null != name) {
			paramMap.put("userName", name);
		}
		this.page = newCityAttendanceManager.getAcceptInfoByUserId(page,
				paramMap);
		return SUCCESS;
	}

	/**
	 * 查看审批详细
	 * 
	 * @return
	 */
	public String queryAcceptDetail() {

		return SUCCESS;

	}

	/**
	 * 拒绝考勤申请
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String rejectApprove() {
		Map result = new HashMap();
		String id = getRequest().getParameter("id");
		String text = getRequest().getParameter("rejectText");
		try {
			Map cMap = new HashMap();
			cMap.put("id", id);
			cMap.put("desc", text);
			newCityAttendanceManager.updateRejectApprove(cMap);
			result.put("result", "ok");
		} catch (Exception e) {
			result.put("result", e.getMessage());
		}

		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}

	/**
	 * 同意考勤申请
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String passApprove() {
		Map result = new HashMap();
		String id = getRequest().getParameter("id");
		try {
			Map cMap = new HashMap();
			cMap.put("id", id);
			newCityAttendanceManager.updatePassApprove(cMap);
			result.put("result", "ok");
		} catch (Exception e) {
			result.put("result", e.getMessage());
		}
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	/**
	 * 申请审批详细信息
	 */
	public String approveDetail(){
		
		String id = getRequest().getParameter("id");
		
		Map result = newCityAttendanceManager.getApproversInfoById(id);
		if(result != null){
			getRequest().setAttribute("startTime", result.get("STARTTIMESTR"));
			getRequest().setAttribute("endTime", result.get("ENDTIMESTR"));
			getRequest().setAttribute("s1", result.get("S1"));
			getRequest().setAttribute("s2", result.get("S2"));
			getRequest().setAttribute("userDesc", result.get("USERDESC"));
			getRequest().setAttribute("typeStr", result.get("TYPESTR"));
			getRequest().setAttribute("id", id);
			getRequest().setAttribute("rejectText", result.get("APPROVERDESC"));
			
			logger.info("approve id: " + id);
			
			Map gongChuInfo = newCityAttendanceManager.getGongChuJia(id);
			if(gongChuInfo != null){
				String captureImg = String.valueOf(gongChuInfo.get("CAPTURE_IMG"));
				String locationLabel = String.valueOf(gongChuInfo.get("LOCATION_LABEL"));
				String mediaId = String.valueOf(gongChuInfo.get("MEDIA_ID"));
				getRequest().setAttribute("captureImg", captureImg);
				getRequest().setAttribute("locationLabel", locationLabel);
				getRequest().setAttribute("mediaId", mediaId);
				
				String locationUrl = "http://221.226.86.204/gongchu_img/" + mediaId + ".png";
				getRequest().setAttribute("locationUrl", locationUrl);
				
				logger.info("captureImg: " + captureImg);
				logger.info("locationLabel: " + locationLabel);
				logger.info("mediaId: " + mediaId);
				logger.info("locationUrl: " + locationUrl);
				
			}
		}
		
		return SUCCESS;
	}

	/**
	 * 查看考勤详细
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String attendanceDetail() throws Exception {

		String userId = getRequest().getParameter("userId");
		String startTime = getRequest().getParameter(
				"startTime");
		String endTime = getRequest().getParameter(
				"endTime");

		getRequest().setAttribute("userId", userId);
		getRequest().setAttribute("startTime",
				startTime);
		getRequest().setAttribute("endTime",
				endTime);

		Map paramMap = new HashMap();


		if (userId != null) {
			paramMap.put("user_id", userId);
		}
		if (startTime != null) {
			paramMap.put("start_time", startTime);
		}
		if (endTime != null) {
			paramMap.put("end_time", endTime);
		}

		//添加周六、周日信息，统计的时候不包括周六、周日
//		paramMap.put("weekends", AttendanceUtil.getWeekends(startTime,endTime));
		
		
		/**
		 * 事假 List 字段顺序 name time text 病假 List 字段顺序 name time text 公出 List 字段顺序
		 * name time text 其它 List 字段顺序 name time text
		 */

		List shijiaList = newCityAttendanceManager
				.queryAffairLeaveListByUserId(paramMap);
		List bingjiaList = newCityAttendanceManager
				.querySickLeaveListByUserId(paramMap);
		List gongchuList = newCityAttendanceManager
				.queryBusinessLeaveListByUserId(paramMap);
		List otherList = newCityAttendanceManager
				.queryOtherLeaveListByUserId(paramMap);
		
		/**
		 * 迟到、早退、缺勤统计
		 */
		
		//缺勤
		List absentList = newCityAttendanceManager.queryAbsentListByUserId(paramMap);
		//迟到		
		List lateList = newCityAttendanceManager.queryLateListByUserId(paramMap);
		//早退
		List leaveEarlyList =  newCityAttendanceManager.queryLeaveEarlylListByUserId(paramMap);

		getRequest().setAttribute("shijiaList", shijiaList);
		getRequest().setAttribute("bingjiaList", bingjiaList);
		getRequest().setAttribute("gongchuList", gongchuList);
		getRequest().setAttribute("otherList", otherList);
		getRequest().setAttribute("absentList", absentList);
		getRequest().setAttribute("lateList", lateList);
		getRequest().setAttribute("leaveEarlyList", leaveEarlyList);
		

		return SUCCESS;

	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public NewCityAttendanceManager getNewCityAttendanceManager() {
		return newCityAttendanceManager;
	}

	public void setNewCityAttendanceManager(
			NewCityAttendanceManager newCityAttendanceManager) {
		this.newCityAttendanceManager = newCityAttendanceManager;
	}

	public Map getDepartmentSelect() {
		return departmentSelect;
	}

	public void setDepartmentSelect(Map departmentSelect) {
		this.departmentSelect = departmentSelect;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public Object getModel() {
		return null;
	}
	
	/**
	 * @description 检查页面传来的String参数 
	 * @param param		参数变量,参数不能为null,不能为空
	 * @param errorInfo	出错时的提示信息
	 * @return			true 验证通过
	 * 					false 验证不通过
	 */
	private boolean checkStringParameter(String param, String errorInfo){
		boolean result = false;
		if(param == null || param.trim().length() == 0){
			Map jsonMap = new HashMap();
			jsonMap.put("success", "false");
			jsonMap.put("msg", errorInfo);
			Struts2Util.renderJson(jsonMap, "encoding:UTF-8", "no-cache:true");
		}else{
			param = param.trim();
			result = true;
		}
		return result;
	}
	
	private long checkLongParameter(String param, String errorInfo){
		long value = 0;
		try{
			value = Long.valueOf(param);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		if(value == 0){
			Map jsonMap = new HashMap();
			jsonMap.put("success", "false");
			jsonMap.put("msg", errorInfo + "[" + param + "]");
			Struts2Util.renderJson(jsonMap, "encoding:UTF-8", "no-cache:true");
		}
		return value;
	}

	/*
	 * 申请一条审批信息
	 */
	@SuppressWarnings("unchecked")
	public String approve() {
		Map result = new HashMap();
		String user_id = getRequest().getParameter("user_id");
		String begin_date = getRequest().getParameter("begin_date");
		String day_begin_part = getRequest().getParameter("day_begin_part");
		String end_date = getRequest().getParameter("end_date");
		String day_end_part = getRequest().getParameter("day_end_part");
		String type = getRequest().getParameter("type");
		String accepter = getRequest().getParameter("accepter");
		String reason = getRequest().getParameter("reason");
		
		long userId = checkLongParameter(user_id, "用户ID错误");
		if(userId == 0){
			return null;
		}
		
		long accepterId = checkLongParameter(accepter, "审批人ID错误");
		if(accepterId == 0){
			return null;
		}
		
		if(!checkStringParameter(begin_date, "开始日期错误")){
			return null;
		}
		
		if(!checkStringParameter(end_date, "结束日期错误")){
			return null;
		}
		
		if(!checkStringParameter(day_begin_part, "开始日期标记错误")){
			return null;
		}
		
		if(!checkStringParameter(day_end_part, "结束日期标记错误")){
			return null;
		}
		
		if(!checkStringParameter(type, "TYPE字段错误")){
			return null;
		}
		
		if(!checkStringParameter(reason, "请假理由错误")){
			return null;
		}
		
		int sf = 0;
		int ef = 0;
		try{
			sf = Integer.parseInt(day_begin_part);
			ef = Integer.parseInt(day_end_part);
		}catch(Exception e){
			result.put("success", "false");
			result.put("msg", "day_begin_part,day_end_part字段错误");
			Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
			return null;
		}
		
		//检查请假日期是否和考勤记录有冲突
		try{
			String checkResultStr = newCityAttendanceManager.checkAttendanceTimeConflict
				(userId, begin_date, end_date, sf, ef);
			if(checkResultStr != null){
				result.put("success", "false");
				result.put("msg", checkResultStr);
				Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
				return null;
			}
		}catch(Exception e){
			result.put("success", "false");
			result.put("msg", e.getMessage());
			Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
			return null;
		}
		
		
		//验证申请的请假时间是否冲突
		try{
			String checkResultStr = newCityAttendanceManager.checkTimeConflictForApprove
					(userId, begin_date, end_date, sf, ef);
			if(checkResultStr != null){
				result.put("success", "false");
				result.put("msg", checkResultStr);
				Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
				return null;
			}
		}catch(Exception e){
			result.put("success", "false");
			result.put("msg", e.getMessage());
			Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
			return null;
		}
		
		try {
			Map param = new HashMap();
			param.put("USER_DESC", reason);
			param.put("USER_ID", user_id);
			param.put("APPROVER_ID", accepter);
			param.put("STATUS", "0");
			param.put("START_DATE", begin_date);
			param.put("END_DATE", end_date);
			param.put("TYPE", type);
			param.put("START_TYPE", day_begin_part);
			param.put("END_TYPE", day_end_part);
			param.put("APPROVER_TIME", DateUtils.getCurrentTime());
			
			/*
			 * 当提交者为考勤管理员
			 * 或者
			 * 请假申请提交给申请人自己
			 * 则提交的申请无需审批,直接设置为审批通过状态
			 */
			if(isAdminForApprove(accepterId) || accepterId == userId){
				param.put("STATUS", "1");
				newCityAttendanceManager.addApproversInfo(param, 2, 2);
			}else{
				newCityAttendanceManager.addApproversInfo(param, 3, 1);
			}
			
			result.put("success", "true");
		} catch (Exception e) {
			result.put("success", "false");
			result.put("msg", e.getMessage());
		}
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * @description 判断是否是Admin在为单位领导提交考勤申请
	 * @param approverId
	 * @param userId
	 * @return
	 */
	private boolean isAdminForLinDao(long approverId, long userId){
		boolean result = false;
		
		try{
			if(newCityAttendanceManager.isOrgAttendanceAdmin(approverId)){
				if(newCityAttendanceManager.getUserType(userId) == 1){
					result = true;
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * @description 判断是否是Admin在提交请假申请
	 * @param approverId
	 * @param userId
	 * @return
	 */
	private boolean isAdminForApprove(long approverId){
		boolean result = false;
		try{
			long uid = Long.valueOf(this.getRequest().getSession()
					.getAttribute(Constants.USER_ID).toString());
			
			if(newCityAttendanceManager.isOrgAttendanceAdmin(approverId)
					&& newCityAttendanceManager.isOrgAttendanceAdmin(uid)){
				result = true;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

	/*
	 * 获取审批人员
	 * 
	 * 根据userId查询其对应的审批人员信息
	 */
	public String getAccepters(){
		String userId = getRequest().getParameter("applicant_id");
		JSONObject jsonObject = null;
		JSONObject jsonAllData = null;
		try {
			JSONArray jsonArray = new JSONArray();
			long uid = Long.parseLong(userId); 
			List<Users> list = newCityAttendanceManager.queryApproversByUId(uid);
			
			for(Users u : list){
				jsonObject = new JSONObject();
				jsonObject.put("user_id", u.getUserid());
				jsonObject.put("username", u.getDisplayName().trim());
				jsonArray.add(jsonObject);
			}
			
			jsonAllData = new JSONObject();
			jsonAllData.put("accepters", "'"+jsonArray.toString()+"'");
			logger.info(jsonAllData.toString());
			
		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("msg", "错误信息["+e.getMessage()+"]");
			Struts2Util.renderJson(jsonObject.toString(),  "encoding:UTF-8", "no-cache:true");
			return null;
		}
		
		Struts2Util.renderJson(jsonAllData.toString(),  "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/*
	 * 获取指定单位下的部门列表
	 */
	@SuppressWarnings("unchecked")
	public String getDepartment(){
		
		String orgIdStr = getRequest().getParameter("orgId");
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		if(orgIdStr.equals("0")){
			jsonObject = new JSONObject();
			jsonObject.put("key", "0");
			jsonObject.put("value", "全部");
			jsonArray.add(jsonObject);
		}else{
			//根据单位 org_id 获取单位部门信息
			List<Map> deptList = newCityAttendanceManager.getDeptList(orgIdStr);
			
			Iterator<Map> iter = deptList.iterator();
			jsonObject = new JSONObject();
			jsonObject.put("key", "0");
			jsonObject.put("value", "全部");
			jsonArray.add(jsonObject);
			
			while (iter.hasNext()) {
				Map tMap = iter.next();
				jsonObject = new JSONObject();
				jsonObject.put("key", tMap.get("ORG_ID").toString());
				jsonObject.put("value", tMap.get("NAME"));
				jsonArray.add(jsonObject);
			}
		}
		Struts2Util.renderJson(jsonArray.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/**
	 * 获取未读审批数目
	 */
	public void getUnReadApproveCount(){
		
		
		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		
		//如果有未审批信息则统计数目，页面按钮右上角显示
		int approveCount = newCityAttendanceManager.getUnReadApproveCountByUserId(uid); 
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("approveCount", approveCount);
		
		Struts2Util.renderJson(jsonObject.toString(), "encoding:UTF-8", "no-cache:true");

	}
	

}


