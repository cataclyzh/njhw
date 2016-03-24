package com.cosmosource.app.property.action;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.cosmosource.app.entity.GrComplaints;
import com.cosmosource.app.property.service.ComplaintsManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

/** 
* @description: 投诉管理
* @author chengyun
* @date 2013-07-09
*/ 
@SuppressWarnings("unchecked")
public class ComplaintsAction extends BaseAction<GrComplaints> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GrComplaints complaint = new GrComplaints();

	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	
	// 定义注入对象
	private ComplaintsManager complaintsManager;
	
	private String complaints_Title;
	
	private String complaints_User;
	
	private String complaintsState;
	
	private String complaintsIn_Time;
	
	private String complaintsOver_Time;
	
	private String complaintsDetail;

	private String complaintsProcessResult;
	
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 默认执行方法
	 */
	public String execute(){
		return SUCCESS;
	}
	public String complaintsIndex(){
		return SUCCESS;
	}
	
	public String showComplaints(){
		HashMap map = new HashMap();

		
		String complaintsTitle = getRequest().getParameter("complaints_Title");
		
		String complaintsInTime=getRequest().getParameter("complaintsIn_Time");
		
		String complaintsOverTime=getRequest().getParameter("complaintsOver_Time");

		
		String complaintsUser = getRequest().getParameter("complaints_User");


		
	  if(complaintsTitle!=null){
			map.put("complaintsTitle",complaintsTitle.trim());

	  }
	  if(complaintsUser!=null){
			map.put("complaintsUser",complaintsUser.trim());

	  }
		
		
			map.put("complaintsInTime",complaintsInTime);
			map.put("complaintsOverTime",complaintsOverTime);
			
			
			String[] complaintsState = getRequest().getParameterValues(
			"complaintsState");
	if (complaintsState != null) {
		if (!complaintsState[0].equalsIgnoreCase("all"))
			map.put("complaintsState", complaintsState[0]);
		else
			map.put("complaintsState", null);
	} else
		map.put("complaintsState", null);
			
			
			
			
			page = complaintsManager.queryAllComplaints(page, map);

			
			return SUCCESS;

	}
	
	
	public String showComplaintstwo(){
       
		
		
			page = complaintsManager.queryAllComplaints(page, null);

			
			return SUCCESS;

	}
	
	
	
	public String showComplaintsList() throws Exception{
		
		

		String complaintsTitle = getRequest().getParameter("complaintsTitle");
		java.util.Date complaintsInTime=DateUtil.str2Date(getRequest()
				.getParameter("complaintsInTime"), "yyyy-MM-dd");
		java.util.Date complaintsOverTime=DateUtil.str2Date(getRequest()
				.getParameter("complaintsOverTime"), "yyyy-MM-dd");


		String complaintsUser = getRequest().getParameter("complaintsUser");

		String complaintsState = getRequest().getParameter("complaintsState");

		
		String satis1="";
		
		if(complaintsState!=null){
			if(complaintsState.equals("未处理")){
			      satis1="0";	
			}
			else if(complaintsState.equals("已处理")){
				satis1="1";
				
			}else if(complaintsState.equals("已结束")){
				satis1="2"; 
			}
		}
		
		
		
			HashMap map = new HashMap();
			map.put("complaintsTitle",complaintsTitle);
			map.put("complaintsInTime",complaintsInTime);
			map.put("complaintsOverTime",complaintsOverTime);
			map.put("complaintsUser",complaintsUser);
			map.put("complaintsState",satis1);
			
			
			page = complaintsManager.queryAllComplaints(page, map);

			
			return SUCCESS;

	
	}
	
	public String viewComplaintsPage() throws Exception {
		try {
			String id = getParameter("complaintsId");
			complaint = (GrComplaints)complaintsManager.findById(GrComplaints.class,Long.valueOf(id));
			getRequest().setAttribute("complaint", complaint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String processComplaintsPage() throws Exception {
		try {
			String id = getParameter("complaintsId");
			complaint = (GrComplaints)complaintsManager.findById(GrComplaints.class,Long.valueOf(id));
			getRequest().setAttribute("complaint", complaint);
			getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String feedbackComplaintsPage() throws Exception {
		try {
			String id = getParameter("complaintsId");
			complaint = (GrComplaints)complaintsManager.findById(GrComplaints.class,Long.valueOf(id));
			getRequest().setAttribute("complaint", complaint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	
	public String processComplaintsPageSub() throws Exception {
		try {
			Map map = new HashMap();

			String complaintsTitle = getRequest().getParameter("complaintsTitle");
			
			java.util.Date complaintsInTime=DateUtil.str2Date(getRequest()
					.getParameter("complaintsInTime"), "yyyy-MM-dd");
			
			String complaintsUser = getRequest().getParameter("complaintsUser");
			String complaintsTelephone=getRequest().getParameter("complaintsTelephone");
			Long complaintsId =  Long.parseLong(getRequest().getParameter("complaintsId"));
			 
			java.util.Date complaintsOverTime=DateUtil.str2Date(getRequest()
					.getParameter("complaintsOverTime"), "yyyy-MM-dd");
			
			String complaintsDetail=getRequest().getParameter("complaintsDetail");

			String complaintsProcessUser=getRequest().getParameter("complaintsProcessUser");
			String complaintsProcessPhone=getRequest().getParameter("complaintsProcessPhone");
			String complaintsProcessResult=getRequest().getParameter("complaintsProcessResult");
	
			String complaintsState="1";
			
			map.put("complaintsTitle",complaintsTitle);
			map.put("complaintsInTime",complaintsInTime);

			map.put("complaintsUser",complaintsUser);
			map.put("complaintsTelephone",complaintsTelephone);
			map.put("complaintsDetail",complaintsDetail);			
			map.put("complaintsId",complaintsId);
			map.put("complaintsOverTime", complaintsOverTime);
			
			if(complaintsProcessUser!=null){
				map.put("complaintsProcessUser", complaintsProcessUser.trim());

			}
			if(complaintsProcessPhone!=null){
				map.put("complaintsProcessPhone", complaintsProcessPhone.trim());
			}
			if(complaintsProcessResult!=null){
				map.put("complaintsProcessResult", complaintsProcessResult);

			}
			
	        map.put("complaintsState", complaintsState);
			
			complaintsManager.updateOneComplaint(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	
	}
	
	public String feedbackComplaintsPageSub() throws Exception {
		try {
			String complaintsTitle = getRequest().getParameter("complaintsTitle");
			
			java.util.Date complaintsInTime=DateUtil.str2Date(getRequest()
					.getParameter("complaintsInTime"), "yyyy-MM-dd");
			 
			
			String complaintsUser = getRequest().getParameter("complaintsUser");
			String complaintsTelephone=getRequest().getParameter("complaintsTelephone");
			Long complaintsId =  Long.parseLong(getRequest().getParameter("complaintsId"));
			
			java.util.Date complaintsOverTime=DateUtil.str2Date(getRequest()
					.getParameter("complaintsOverTime"), "yyyy-MM-dd");  
			
			String complaintsDetail=getRequest().getParameter("complaintsDetail");

			String complaintsProcessUser=getRequest().getParameter("complaintsProcessUser");
			String complaintsProcessPhone=getRequest().getParameter("complaintsProcessPhone");
			String complaintsProcessResult=getRequest().getParameter("complaintsProcessResult");
			
			
			String complaintsSatisfy=getRequest().getParameter("complaintsSatisfy");
			String complaintsFeedback=getRequest().getParameter("complaintsFeedback");
	
			String complaintsState="2";
			String satis1="";
			
			if(complaintsSatisfy.equals("非常满意")){
			      satis1="0";	
			}
			else if(complaintsSatisfy.equals("满意")){
				satis1="1";
				
			}else if(complaintsSatisfy.equals("不满意")){
				satis1="2"; 
			}
			
			Map map = new HashMap();
			map.put("complaintsTitle",complaintsTitle);
			map.put("complaintsInTime",complaintsInTime);
			map.put("complaintsSatisfy",satis1);
			map.put("complaintsUser",complaintsUser);
			map.put("complaintsTelephone",complaintsTelephone);
			map.put("complaintsDetail",complaintsDetail);			
			map.put("complaintsId",complaintsId);
			map.put("complaintsOverTime", complaintsOverTime);
			map.put("complaintsProcessUser", complaintsProcessUser);
			map.put("complaintsProcessPhone", complaintsProcessPhone);
			map.put("complaintsProcessResult", complaintsProcessResult);
	        map.put("complaintsState", complaintsState);
			if(complaintsFeedback!=null){
				map.put("complaintsFeedback",complaintsFeedback.trim());

			}

			complaintsManager.updateOneComplaint(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String showMyComplaints(){
		HashMap map = new HashMap();
		map.put("complaintsUserId", Long.parseLong(Struts2Util.getSession().getAttribute("Constants.USER_ID").toString()));
		page=complaintsManager.queryMyComplaints(page, map);
		return SUCCESS;
	}
	
//	public String queryComplaints(){
//		HashMap map = new HashMap();
//		map.put("complaintsTitle", getRequest().getParameter("complaintsTitle"));
//		map.put("complaintsInTime", getRequest().getParameter("complaintsInTime"));
//		map.put("complaintsOverTime", getRequest().getParameter("complaintsOverTime"));
//		map.put("complaintsUser", getRequest().getParameter("complaintsUser"));
//		map.put("complaintsState", getRequest().getParameter("complaintsState"));
//		
//		List<GrComplaints> complaintsList = complaintsManager.queryComplaints(getRequest().getParameter("complaintsTitle"),
//				getRequest().getParameter("complaintsInTime"),
//				getRequest().getParameter("complaintsOverTime"),
//				getRequest().getParameter("complaintsUser"), getRequest().getParameter("complaintsState"));
//		getRequest().setAttribute("complaintsList", complaintsList);
//		return SUCCESS;
//	}
	
	public String queryComplaintById(){
		Long complaintsId = Long.parseLong(getRequest().getParameter("complaintsId"));
		GrComplaints complaint = complaintsManager.findComplaintById(complaintsId);
		
		if(complaint!=null){
			getRequest().setAttribute("complaintsId", complaintsId);
			return SUCCESS;
		}else
			return ERROR;
	}
	
	public String deleteOneComplaint(){
		Long complaintsId = Long.parseLong(getRequest().getParameter("complaintsId"));
		complaintsManager.deleteOneComplaint(complaintsId);
		return SUCCESS;
	}
	public String addComplaintsPage(){
		getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd"));

		return SUCCESS;
	}
	
	
	
	public String addOneComplaint() throws ParseException{
		Map map = new HashMap();

		String complaintsTitle = getRequest().getParameter("complaintsTitle");
		
		java.util.Date complaintsInTime=DateUtil.str2Date(getRequest().getParameter("complaintsInTime"), "yyyy-MM-dd"); 
		
				
		String complaintsUser = getRequest().getParameter("outName");
		String complaintsTelephone=getRequest().getParameter("phonespan");
		String complaintsDetail=getRequest().getParameter("complaintsDetail");

		String complaintsState="0";
		
		
		if(complaintsTitle!=null){
			map.put("complaintsTitle",complaintsTitle.trim());
		}
		if(complaintsUser!=null){
			map.put("complaintsUser", complaintsUser.trim());
		}
		if(complaintsTelephone!=null){
			map.put("complaintsTelephone", complaintsTelephone.trim());
		}
		if(complaintsDetail!=null){
			map.put("complaintsDetail", complaintsDetail.trim());

		}
		
		map.put("complaintsInTime", complaintsInTime);
        map.put("complaintsState", complaintsState);
		complaintsManager.addOneComplaint(map);
		return SUCCESS;
	}
	

	

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public ComplaintsManager getComplaintsManager() {
		return complaintsManager;
	}

	public void setComplaintsManager(ComplaintsManager complaintsManager) {
		this.complaintsManager = complaintsManager;
	}

	public GrComplaints getComplaint() {
		return complaint;
	}

	public void setComplaint(GrComplaints complaint) {
		this.complaint = complaint;
	}

	
	public String getComplaints_Title() {
		return complaints_Title;
	}
	public void setComplaints_Title(String complaintsTitle) {
		complaints_Title = complaintsTitle;
	}
	public String getComplaints_User() {
		return complaints_User;
	}
	public void setComplaints_User(String complaintsUser) {
		complaints_User = complaintsUser;
	}

	public String getComplaintsState() {
		return complaintsState;
	}
	public void setComplaintsState(String complaintsState) {
		this.complaintsState = complaintsState;
	}
	public String getComplaintsIn_Time() {
		return complaintsIn_Time;
	}
	public void setComplaintsIn_Time(String complaintsInTime) {
		complaintsIn_Time = complaintsInTime;
	}
	public String getComplaintsOver_Time() {
		return complaintsOver_Time;
	}
	public void setComplaintsOver_Time(String complaintsOverTime) {
		complaintsOver_Time = complaintsOverTime;
	}
	public String getComplaintsDetail() {
		return complaintsDetail;
	}
	public void setComplaintsDetail(String complaintsDetail) {
		this.complaintsDetail = complaintsDetail;
	}
	public String getComplaintsProcessResult() {
		return complaintsProcessResult;
	}
	public void setComplaintsProcessResult(String complaintsProcessResult) {
		this.complaintsProcessResult = complaintsProcessResult;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GrComplaints getModel() {
		// TODO Auto-generated method stub
		return complaint;
	}

}
