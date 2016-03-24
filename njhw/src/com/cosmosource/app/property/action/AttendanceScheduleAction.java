package com.cosmosource.app.property.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrAttendanceSchedule;
import com.cosmosource.app.personnel.service.OrgMgrManager;
import com.cosmosource.app.personnel.service.PersonRegOutManager;
import com.cosmosource.app.property.service.AttendanceScheduleManager;
import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * @description: 考勤排班
 * @author cehngyun
 * @date 2013-07-11
 */
@SuppressWarnings("unchecked")
public class AttendanceScheduleAction extends BaseAction<Object> {
	
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录
	private OrgMgrManager orgMgrManager;
	private PersonRegOutManager personROManager;
	
	// 定义注入对象
	private AttendanceScheduleManager attendanceScheduleManager;
	private GrAttendanceSchedule view_attendanceSchedule = new GrAttendanceSchedule();
	private GrAttendanceSchedule modify_attendanceSchedule = new GrAttendanceSchedule();
	Long userid;
	Long orgId;
	String orgName;
	String outName;
	private String list_attendanceScheduleOrgName;
	private String list_attendanceScheduleUserName;
	private String list_attendanceScheduleInTime;
	private String list_attendanceScheduleOutTime;
	private String list_attendanceScheduleState;
	private String res_bak1;
	
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRes_bak1() {
		return res_bak1;
	}

	public void setRes_bak1(String res_bak1) {
		this.res_bak1 = res_bak1;
	}


	public String getRes_bak2() {
		return res_bak2;
	}


	public void setRes_bak2(String res_bak2) {
		this.res_bak2 = res_bak2;
	}


	private String res_bak2;
	
	/**
	 * 
	* @Title: orgTree 
	* @Description: 组织机构-人员信息查询
	* @author WXJ
	* @date 2013-5-3 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String attendanceOrgTreeSelect() throws Exception {
//		String ids = getParameter("treeUserId") != null ? getParameter("treeUserId").toString() : "";
		String ids = "300";
		getRequest().setAttribute("ids", ids);
		return SUCCESS;
	}
	
	public static String getGid(String userId){
		String gid = "300";
		//7637 shebeiweihu1
		//7614 kezhanga2
		if(userId.equals("7637") || userId.equals("7614")){
			gid="220"; //工程部
		//7638 zhuguana1
		//7639 zhuguanb1
		//7601 zhuguanb2
		}else if(userId.equals("7638") || userId.equals("7639") || userId.equals("7601")){
			gid="215"; //安保部
		//7591 zhuguana2
		}else if(userId.equals("7591")){
			gid="214"; //客服部
		//7592 zhuguana3 
		//7593 zhuguana4
		//7600 guanliyuan2
		}else if(userId.equals("7592") || userId.equals("7593") || userId.equals("7600")){
			gid="219"; //保洁部
		}else{
		}
		return gid;
	}
	
	/**
	 * 
	 * @Title: getAttendanceOrgUserTreeData 
	 * @Description: 机构数 发消息
	 * @author SQS
	 * @date 2013-5-6 上午11:11:11 
	 * @param @return
	 * @param @throws Exception    
	 * @return String 
	 * @throws
	 */
	public String getAttendanceOrgUserTreeData() throws Exception {
		String ids = getParameter("ids");
		//System.out.println("ids:"+ids);
		
//		String gid  = null;
		// 找到当前用户的顶级部门
//		List<HashMap> list = this.personROManager.getTopOrgId();
//		if (list.size() > 0) gid = list.get(0).get("TOP_ORG_ID") != null ? list.get(0).get("TOP_ORG_ID").toString() : null;
//		gid="300";
		
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		System.out.println("userId: " + userId);

		String type = getParameter("type");
		Struts2Util.renderXml(
			attendanceScheduleManager.getMesOrgUserTreeData(getGid(userId), ids, getContextPath(), type),
				"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	public String attendanceScheduleIndex(){
		return SUCCESS;
	}
	
	public String showAttendanceSchedulesList(){
		
		HashMap map =new HashMap();
		
		if(this.list_attendanceScheduleOrgName!=null){
			map.put("attendanceScheduleOrgName", this.list_attendanceScheduleOrgName.trim());
		}
		if(this.list_attendanceScheduleUserName!=null){
			map.put("attendanceScheduleUserName", this.list_attendanceScheduleUserName.trim());
		}
		if(this.res_bak1 != null){
			map.put("res_bak1", this.res_bak1.trim());
		}else{
			map.put("res_bak1", this.res_bak1);
		}
		if(this.res_bak2 != null){
			map.put("res_bak2", this.res_bak2.trim());
		}else{
			map.put("res_bak2", this.res_bak2);
		}
		map.put("attendanceScheduleInTime", this.list_attendanceScheduleInTime);
		map.put("attendanceScheduleOutTime", this.list_attendanceScheduleOutTime);
		
		String attendanceScheduleState = this.list_attendanceScheduleState;
		if ((attendanceScheduleState != null)&& !"".equals(attendanceScheduleState)) {
			if (attendanceScheduleState.equalsIgnoreCase("all"))
				map.put("attendanceScheduleState", null);
			
			if (attendanceScheduleState.equalsIgnoreCase("0"))
				map.put("attendanceScheduleState", "0");
			
			if (attendanceScheduleState.equalsIgnoreCase("1"))
				map.put("attendanceScheduleState", "1");
			
		} else {
			//map.put("attendanceScheduleState", null);
			setList_attendanceScheduleState("1");
			map.put("attendanceScheduleState", "1");
		}
		
		String pageNum = getRequest().getParameter("pageNum");
		try {
			page.setPageNo(Integer.parseInt(pageNum));
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
//		page.setPageSize(40);
//		page.setTotalPages(20);
		page = attendanceScheduleManager.queryAllAttendanceSchedules(page, map);
		return SUCCESS;
	}
	
	public String queryAttendanceScheduleById(){
		Long attendanceScheduleId = Long.parseLong(getRequest().getParameter("attendanceScheduleId"));
		view_attendanceSchedule = (GrAttendanceSchedule)attendanceScheduleManager.findAttendanceScheduleById(attendanceScheduleId);
		
		if(view_attendanceSchedule!=null){
			getRequest().setAttribute("view_attendanceSchedule", view_attendanceSchedule);
			return SUCCESS;
		}else {
			getRequest().setAttribute("view_attendanceSchedule", null);
			return ERROR;
		}
	}
	
	public String addOneAttendanceSchedulePrepare(){
		getRequest().setAttribute("nowDate", DateUtil.date2Str(new Date(), "yyyy-MM-dd"));
		getRequest().setAttribute("nowTime", DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return SUCCESS;
	}
	
	private boolean isTimeUsefull(String st, String et){
		if((st == null || st.trim().length() == 0 ) 
				|| (et == null || et.trim().length() == 0)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 添加考勤排班记录
	 * @return
	 */
	public String addOneAttendanceSchedule(){
		String treeUserId = getParameter("treeUserId");// ID
		String treeUserName = getParameter("receiver");// NAME
		
		//考勤日期
		String add_attendanceScheduleInTime_dayStart = getRequest().getParameter("add_attendanceScheduleInTime_dayStart");
		String add_attendanceScheduleOutTime_dayEnd = getRequest().getParameter("add_attendanceScheduleOutTime_dayEnd");
		
		//考勤时间
//		String add_attendanceScheduleInTime_hourStart = getRequest().getParameter("add_attendanceScheduleInTime_hourStart");
//		String add_attendanceScheduleOutTime_hourEnd = getRequest().getParameter("add_attendanceScheduleOutTime_hourEnd");
		String[] startTimes = new String[5];
		String[] endTimes = new String[5];
		
		for(int i=0; i<5; i++){
			startTimes[i] = getRequest().getParameter("startTime"+i);
			endTimes[i] = getRequest().getParameter("endTime"+i);
			logger.info("startTimes["+i+"]: " + startTimes[i]);
			logger.info("endTimes["+i+"]: " + endTimes[i]);
		}
		
		Date attendanceScheduleInTime = DateUtil.str2Date(add_attendanceScheduleInTime_dayStart, "yyyy-MM-dd");//+" "+add_attendanceScheduleInTime_hourStart
		Date attendanceScheduleOutTime = DateUtil.str2Date(add_attendanceScheduleOutTime_dayEnd, "yyyy-MM-dd");//+" "+add_attendanceScheduleOutTime_hourEnd
		String resBak4 = getParameter("add_attendanceScheduleRemark");
		//考勤添加人信息
		Long attendanceScheduleAdminId = Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		String attendanceScheduleAdminName = Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString();
		//考勤用户信息
		String[] userIdTree = treeUserId.split(",");
		String[] userName = treeUserName.split(",");
			
		try{
			//排版日期类型
			String datetype = getParameter("datetype");
			if(datetype != null && datetype.equals("single")){
				List<String> list = DateUtils.getSingleDate(add_attendanceScheduleInTime_dayStart, add_attendanceScheduleOutTime_dayEnd);
				for(String s : list){
					for(int i=0; i<5; i++){
						String st = startTimes[i];
						String et = endTimes[i];
						if(isTimeUsefull(st, et)){
							logger.info(s + "|" + st + "|" + et);
							Date ss = DateUtil.str2Date(s, "yyyy-MM-dd");
							addMultiUserOneTimeSchedule(attendanceScheduleAdminId, 
									attendanceScheduleAdminName,
									ss,
									ss,
									st,
									et,				
									resBak4,
									userIdTree, userName);
						}
					}
				}
			}else if(datetype != null && datetype.equals("double")){
				List<String> list = DateUtils.getDoubleDate(add_attendanceScheduleInTime_dayStart, add_attendanceScheduleOutTime_dayEnd);
				for(String s : list){
					for(int i=0; i<5; i++){
						String st = startTimes[i];
						String et = endTimes[i];
						if(isTimeUsefull(st, et)){
							logger.info(s + "|" + st + "|" + et);
							Date ss = DateUtil.str2Date(s, "yyyy-MM-dd");
							addMultiUserOneTimeSchedule(attendanceScheduleAdminId, 
									attendanceScheduleAdminName,
									ss,
									ss,
									st,
									et,				
									resBak4,
									userIdTree, userName);
						}
					}
				}
			}else{
				for(int i=0; i<5; i++){
					String st = startTimes[i];
					String et = endTimes[i];
					
					if(isTimeUsefull(st, et)){
						logger.info(attendanceScheduleInTime + "|" 
								+ attendanceScheduleOutTime + "|"
								+ st + "|" + et);
						
						addMultiUserOneTimeSchedule(attendanceScheduleAdminId, 
								attendanceScheduleAdminName,
								attendanceScheduleInTime,
								attendanceScheduleOutTime,
								st,
								et,				
								resBak4,
								userIdTree, userName);
					}
					
				}
			}
		}catch(Exception e){
			logger.error("排版出错", e);
		}
		return SUCCESS;
	}
	
	private void addMultiUserOneTimeSchedule(Long attendanceScheduleAdminId, 
			String attendanceScheduleAdminName,
			Date attendanceScheduleInTime,
			Date attendanceScheduleOutTime,
			String resBak1,
			String resBak2,
			String resBak4,
			String[] userIdTree, String[] userName){
		
		if(resBak1 == null || resBak1.trim().length() < 8){
			logger.warn("无效start时间:" + resBak1);
			return;
		}
		if(resBak2 == null || resBak2.trim().length() < 8){
			logger.warn("无效end时间:" + resBak2);
			return;
		}
		
		// 0：为白班 1：为夜班
		String resBak3 = null;
		int cdate = resBak1.compareTo(resBak2);
		if(cdate <= 0){
			resBak3 = "0";
		}else{
			resBak3 = "1";
		}
		
		String attendanceScheduleState = "1";
		Long attendanceScheduleUserId = new Long(0);		
		Long attendanceScheduleOrgId=new Long(0);
		String attendanceScheduleUserName=null;
		String attendanceScheduleOrgName=null;
		
		
		for (int i = 0; i < userIdTree.length; i++) {
			attendanceScheduleUserId = Long.parseLong(userIdTree[i]);
			attendanceScheduleUserName = userName[i];		
			List<Map> orgInfo = attendanceScheduleManager.getAttendanceOrgInfoById(attendanceScheduleUserId);
			//if(orgInfo==null)
				//System.out.println("tsee");
			for(Map map:orgInfo){
				attendanceScheduleOrgId = Long.parseLong((String.valueOf(map.get("ORG_ID"))));
				attendanceScheduleOrgName = (String)map.get("NAME");
			}
			GrAttendanceSchedule attendanceSchedule = new GrAttendanceSchedule();
			attendanceSchedule.setAttendanceScheduleUserId(attendanceScheduleUserId);
			attendanceSchedule.setAttendanceScheduleUserName(attendanceScheduleUserName);
			attendanceSchedule.setAttendanceScheduleOrgId(attendanceScheduleOrgId);
			attendanceSchedule.setAttendanceScheduleOrgName(attendanceScheduleOrgName);
			attendanceSchedule.setAttendanceScheduleAdminId(attendanceScheduleAdminId);
			attendanceSchedule.setAttendanceScheduleAdminName(attendanceScheduleAdminName);
			attendanceSchedule.setAttendanceScheduleInTime(attendanceScheduleInTime);
			attendanceSchedule.setAttendanceScheduleOutTime(attendanceScheduleOutTime);
			attendanceSchedule.setAttendanceScheduleState(attendanceScheduleState);
			//attendanceSchedule.setResBak1(add_attendanceScheduleInTime_hourStart);//attendanceSchedule.getAttendanceScheduleInTime()
			//attendanceSchedule.setResBak2(add_attendanceScheduleOutTime_hourEnd);//attendanceSchedule.getAttendanceScheduleOutTime()
			attendanceSchedule.setResBak1(resBak1);
			attendanceSchedule.setResBak2(resBak2);
			attendanceSchedule.setResBak3(resBak3);
			attendanceSchedule.setResBak4(resBak4);
			
			attendanceScheduleManager.addOneAttendanceSchedule(attendanceSchedule);
			
		}
	}
	
	public String modifyOneAttendanceSchedulePrepare(){
		Long attendanceScheduleId = Long.parseLong(getRequest().getParameter("attendanceScheduleId"));
		view_attendanceSchedule = (GrAttendanceSchedule)attendanceScheduleManager.findAttendanceScheduleById(attendanceScheduleId);
		
		if(view_attendanceSchedule!=null){
			getRequest().setAttribute("view_attendanceSchedule", view_attendanceSchedule);
			getRequest().setAttribute("nowDate", DateUtil.date2Str(new Date(), "yyyy-MM-dd"));
			getRequest().setAttribute("nowTime", DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return SUCCESS;
		}else {
			getRequest().setAttribute("nowDate", DateUtil.date2Str(new Date(), "yyyy-MM-dd"));
			getRequest().setAttribute("nowTime", DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
			getRequest().setAttribute("view_attendanceSchedule", null);
			return ERROR;
		}
	}
	
	public String modifyOneAttendanceSchedule(){
		Long attendanceScheduleId = Long.parseLong(getRequest().getParameter("modify_attendanceScheduleId"));
		GrAttendanceSchedule temp_attendanceSchedule=(GrAttendanceSchedule)attendanceScheduleManager.findAttendanceScheduleById(attendanceScheduleId);
//		Long attendanceScheduleUserId = this.userid;		
//		Long attendanceScheduleOrgId = this.orgId;
//		String attendanceScheduleUserName = this.outName;
//		String attendanceScheduleOrgName = this.orgName;
//		Long attendanceScheduleAdminId = Long.parseLong(Struts2Util.getSession()
//				.getAttribute(Constants.USER_ID).toString());
//		String attendanceScheduleAdminName = Struts2Util.getSession()
//		.getAttribute(Constants.USER_NAME).toString();
		String modify_attendanceScheduleInTime_dayStart = getRequest().getParameter("modify_attendanceScheduleInTime_dayStart");
		String modify_attendanceScheduleOutTime_dayEnd = getRequest().getParameter("modify_attendanceScheduleOutTime_dayEnd");
		String modify_attendanceScheduleInTime_hourStart = getRequest().getParameter("modify_attendanceScheduleInTime_hourStart");
		String modify_attendanceScheduleOutTime_hourEnd = getRequest().getParameter("modify_attendanceScheduleOutTime_hourEnd");
		
		Date attendanceScheduleInTime = DateUtil.str2Date(modify_attendanceScheduleInTime_dayStart, "yyyy-MM-dd");//+" "+modify_attendanceScheduleInTime_hourStart
		Date attendanceScheduleOutTime = DateUtil.str2Date(modify_attendanceScheduleOutTime_dayEnd, "yyyy-MM-dd");//+" "+modify_attendanceScheduleOutTime_hourEnd
		String attendanceScheduleState = "1";
		String resBak1 =null;
		String resBak2 =null;
		String resBak3 =null;
		int cdate = modify_attendanceScheduleInTime_hourStart.compareTo(modify_attendanceScheduleOutTime_hourEnd);
		if(cdate <= 0){
			resBak3 = "0";
		}else{
			resBak3 = "1";
		}
		String resBak4 =getRequest().getParameter("modify_resBak4");
		
		GrAttendanceSchedule attendanceSchedule = new GrAttendanceSchedule();
		attendanceSchedule.setAttendanceScheduleId(attendanceScheduleId);
		attendanceSchedule.setAttendanceScheduleUserId(temp_attendanceSchedule.getAttendanceScheduleUserId());
		attendanceSchedule.setAttendanceScheduleUserName(temp_attendanceSchedule.getAttendanceScheduleUserName());
		attendanceSchedule.setAttendanceScheduleOrgId(temp_attendanceSchedule.getAttendanceScheduleOrgId());
		attendanceSchedule.setAttendanceScheduleOrgName(temp_attendanceSchedule.getAttendanceScheduleOrgName());
		attendanceSchedule.setAttendanceScheduleAdminId(temp_attendanceSchedule.getAttendanceScheduleAdminId());
		attendanceSchedule.setAttendanceScheduleAdminName(temp_attendanceSchedule.getAttendanceScheduleAdminName());
		attendanceSchedule.setAttendanceScheduleInTime(attendanceScheduleInTime);
		attendanceSchedule.setAttendanceScheduleOutTime(attendanceScheduleOutTime);
		attendanceSchedule.setAttendanceScheduleState(attendanceScheduleState);
		attendanceSchedule.setResBak1(modify_attendanceScheduleInTime_hourStart);//DateUtil.date2Str(attendanceSchedule.getAttendanceScheduleInTime(), "yyyy-MM-dd HH:mm:ss")
		attendanceSchedule.setResBak2(modify_attendanceScheduleOutTime_hourEnd);//DateUtil.date2Str(attendanceSchedule.getAttendanceScheduleOutTime(), "yyyy-MM-dd HH:mm:ss")
		attendanceSchedule.setResBak3(resBak3);
		attendanceSchedule.setResBak4(resBak4);
		
		attendanceScheduleManager.updateOneAttendanceSchedule(attendanceSchedule);
		return SUCCESS;
	}
	
	//暂停、终止考勤排班
	public String suspendOneAttendanceSchedule(){
		Long attendanceScheduleId = Long.parseLong(getRequest().getParameter("attendanceScheduleId"));
		String attendanceScheduleState = "0";
		attendanceScheduleManager.updateAttendanceScheduleStateById(attendanceScheduleId, attendanceScheduleState);
		return SUCCESS;
	}
	
	public String queryAttendanceScheduleIsExist(){
		String attendanceScheduleInTime = getRequest().getParameter("scheduleInTime");
		String attendanceScheduleOutTime = getRequest().getParameter("scheduleOutTime");
		String scheduleTreeUserId = getRequest().getParameter("scheduleTreeUserId");
		String userIdTree[] = scheduleTreeUserId.split(",");
		Long attendanceScheduleUserId;
		boolean isExistFlag = false;
				
		for(int i=0;i<userIdTree.length;i++){
			attendanceScheduleUserId = Long.parseLong(userIdTree[i]);
			
			if(attendanceScheduleManager.queryAttendanceScheduleIsExist(attendanceScheduleUserId,attendanceScheduleInTime,attendanceScheduleOutTime,"1"))
				isExistFlag = true;
			
			if(isExistFlag)
				break;
		}
		
		if(isExistFlag)
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		else
			Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		
		return null;
	}
	/**
	 * 查询该时间段是否 有排班记录
	 * 开发者：ywl
	 * @return
	 */
	public String queryNewAttendanceScheduleIsExist(){
		String attendanceScheduleInTime = getRequest().getParameter("scheduleInTime");
		String attendanceScheduleOutTime = getRequest().getParameter("scheduleOutTime");
		String scheduleTreeUserId = getRequest().getParameter("scheduleTreeUserId");
		String userIdTree[] = scheduleTreeUserId.split(",");
		Long attendanceScheduleUserId;
		boolean isExistFlag = false;
				
		for(int i=0;i<userIdTree.length;i++){
			attendanceScheduleUserId = Long.parseLong(userIdTree[i]);
			
			if(attendanceScheduleManager.queryNewAttendanceScheduleIsExist(attendanceScheduleUserId,attendanceScheduleInTime,attendanceScheduleOutTime,"1"))
				isExistFlag = true;
			
			if(isExistFlag)
				break;
		}
		
		if(isExistFlag)
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		else
			Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		
		return null;
	}
	
	public String modifyAttendanceScheduleIsExist(){
		Long attendanceScheduleId = Long.parseLong(getRequest().getParameter("attendanceScheduleId"));
		String attendanceScheduleInTime = getRequest().getParameter("scheduleInTime");
		String attendanceScheduleOutTime = getRequest().getParameter("scheduleOutTime");
		Long attendanceScheduleUserId = Long.parseLong(getRequest().getParameter("attendanceScheduleUserId"));
		boolean isExistFlag = false;
		
		if(attendanceScheduleManager.modifyAttendanceScheduleIsExist(attendanceScheduleId,attendanceScheduleUserId,attendanceScheduleInTime,attendanceScheduleOutTime,"1"))
			isExistFlag = true;
		
			if(isExistFlag)
				Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
			else
				Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		
		return null;
	}

	public String getList_attendanceScheduleOrgName() {
		return list_attendanceScheduleOrgName;
	}


	public void setList_attendanceScheduleOrgName(
			String listAttendanceScheduleOrgName) {
		list_attendanceScheduleOrgName = listAttendanceScheduleOrgName;
	}


	public String getList_attendanceScheduleUserName() {
		return list_attendanceScheduleUserName;
	}


	public void setList_attendanceScheduleUserName(
			String listAttendanceScheduleUserName) {
		list_attendanceScheduleUserName = listAttendanceScheduleUserName;
	}


	public String getList_attendanceScheduleInTime() {
		return list_attendanceScheduleInTime;
	}


	public void setList_attendanceScheduleInTime(String listAttendanceScheduleInTime) {
		list_attendanceScheduleInTime = listAttendanceScheduleInTime;
	}


	public String getList_attendanceScheduleOutTime() {
		return list_attendanceScheduleOutTime;
	}


	public void setList_attendanceScheduleOutTime(
			String listAttendanceScheduleOutTime) {
		list_attendanceScheduleOutTime = listAttendanceScheduleOutTime;
	}


	public String getList_attendanceScheduleState() {
		return list_attendanceScheduleState;
	}


	public void setList_attendanceScheduleState(String listAttendanceScheduleState) {
		list_attendanceScheduleState = listAttendanceScheduleState;
	}


	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public AttendanceScheduleManager getAttendanceScheduleManager() {
		return attendanceScheduleManager;
	}

	public void setAttendanceScheduleManager(
			AttendanceScheduleManager attendanceScheduleManager) {
		this.attendanceScheduleManager = attendanceScheduleManager;
	}

	public GrAttendanceSchedule getView_attendanceSchedule() {
		return view_attendanceSchedule;
	}

	public void setView_attendanceSchedule(
			GrAttendanceSchedule viewAttendanceSchedule) {
		view_attendanceSchedule = viewAttendanceSchedule;
	}

	public GrAttendanceSchedule getModify_attendanceSchedule() {
		return modify_attendanceSchedule;
	}

	public void setModify_attendanceSchedule(
			GrAttendanceSchedule modifyAttendanceSchedule) {
		modify_attendanceSchedule = modifyAttendanceSchedule;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}


	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}


	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}


	public OrgMgrManager getOrgMgrManager() {
		return orgMgrManager;
	}


	public void setOrgMgrManager(OrgMgrManager orgMgrManager) {
		this.orgMgrManager = orgMgrManager;
	}


	public PersonRegOutManager getPersonROManager() {
		return personROManager;
	}


	public void setPersonROManager(PersonRegOutManager personROManager) {
		this.personROManager = personROManager;
	}

}
