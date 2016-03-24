package com.cosmosource.app.property.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.GrConferencePackage;
import com.cosmosource.app.property.service.ConferenceManager;
import com.cosmosource.app.property.service.ConferencePackageManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/** 
* @description: 会务套餐
* @author cehngyun
* @date 2013-07-11
*/ 
@SuppressWarnings("unchecked")
public class ConferencePackageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	//定义注入对象
	private ConferencePackageManager conferencePackageManager;
	private ConferenceManager conferenceManager;

	private GrConferencePackage view_conferencePackage = new GrConferencePackage();
	private GrConferencePackage modify_conferencePackage = new GrConferencePackage();
	private GrConferencePackage suspend_conferencePackage = new GrConferencePackage();
	
	private String list_conferencePackageName;
	private String list_conferencePackageRoom;
	private String list_conferencePackageState;
	
	/**
	 * 默认执行该方法
	 */
	public String execute() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 分页查询,根据前台传过来的参数查询所有符合条件的会议套餐list
	 * 物业人员
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String showConferencePackagesList()throws Exception{
		HashMap map = new HashMap();
		
		if(getRequest().getParameter("list_conferencePackageName")!=null){
			map.put("conferencePackageName", getRequest().getParameter("list_conferencePackageName").trim());
		}
		if(getRequest().getParameter("list_conferencePackageRoom")!=null){
			map.put("conferencePackageRoom", getRequest().getParameter("list_conferencePackageRoom").trim());
		}
		if(getRequest().getParameter("list_conferencePackageLocation")!=null){
			map.put("conferencePackageLocation", getRequest().getParameter("list_conferencePackageLocation").trim());
		}
		if(getRequest().getParameter("list_conferencePackageStyle")!=null){
			map.put("conferencePackageStyle", getRequest().getParameter("list_conferencePackageStyle").trim());

		}
		
		
		
		String conferencePackageStates[] = getRequest().getParameterValues("list_conferencePackageState");
		if(conferencePackageStates!=null){
			if(!conferencePackageStates[0].equalsIgnoreCase("all"))
				map.put("conferencePackageState", conferencePackageStates[0]);
			else
				map.put("conferencePackageState", null);
		}else{
			map.put("conferencePackageState", null);
		}

		
		getRequest().setAttribute("operationAuthor", getRequest().getParameter("operationAuthor"));
		page = conferencePackageManager.queryAllConferencePackages(page, map);
		return SUCCESS;
	}
	
	
	public String queryConferencePackagesList() throws Exception{
		
		List<GrConferencePackage> conferencePackagesList = conferencePackageManager.queryConferencePackages(
				getRequest().getParameter("conferencePackageName"), getRequest().getParameter("conferencePackageRoom"),
				getRequest().getParameter("conferencePackageLocation"),getRequest().getParameter("conferencePackageStyle") ,
				getRequest().getParameter("conferencePackageState"));
		
		getRequest().setAttribute("conferencePackagesList", conferencePackagesList);
		return null;
	}
	
	public String queryConferencePackageById() throws Exception{
		Long conferencePackageId = Long.parseLong(getRequest().getParameter("conferencePackageId"));
		view_conferencePackage = conferencePackageManager.findGrConferencePackageById(conferencePackageId);
		
		if(view_conferencePackage!=null){
			getRequest().setAttribute("view_conferencePackage", view_conferencePackage);
			return SUCCESS;
		}else {
			getRequest().setAttribute("view_conferencePackage", null);
			return ERROR;
		}
	}
	
	
	public String suspendOneConferencePackage(){
		Long conferencePackageId = Long.parseLong(getRequest().getParameter("conferencePackageId"));
//		String conferencePackageState = getRequest().getParameter("conferencePackageState");
		conferencePackageManager.updateConferencePackageStateById(conferencePackageId, "0");
		
		conferencePackageManager.updateConferencePackageState(conferencePackageId, "3");
		
		return SUCCESS;
	}
	
	
	public String activeOneConferencePackage(){
		Long conferencePackageId = Long.parseLong(getRequest().getParameter("conferencePackageId"));
		conferencePackageManager.updateConferencePackageStateById(conferencePackageId, "1");
		return SUCCESS;
	}
	
	
	public String modifyOneConferencePackagePrepare(){
		Long conferencePackageId = Long.parseLong(getRequest().getParameter("conferencePackageId"));
		view_conferencePackage = conferencePackageManager.findGrConferencePackageById(conferencePackageId);
		
		if(view_conferencePackage!=null){
			getRequest().setAttribute("view_conferencePackage", view_conferencePackage);
			return SUCCESS;
		}else {
			getRequest().setAttribute("view_conferencePackage", null);
			return ERROR;
		}
	}
	
	public String modifyOneConferencePackage(){
		
		Long conferencePackageId = Long.parseLong(getRequest().getParameter("modify_conferencePackageId"));
		String conferencePackageName = getRequest().getParameter("modify_conferencePackageName");
		String conferencePackageRoom = getRequest().getParameter("modify_conferencePackageRoom");
		String conferencePackageLocation = getRequest().getParameter("modify_conferencePackageLocation");
		String conferencePackageSeat = getRequest().getParameter("modify_conferencePackageSeat");
		String conferencePackageStyle = getRequest().getParameter("modify_conferencePackageStyle");
		String conferencePackageFacility = getRequest().getParameter("modify_conferencePackageFacility");
		String conferencePackagePrice = getRequest().getParameter("modify_conferencePackagePrice");
		String conferencePackageService = getRequest().getParameter("modify_conferencePackageService");
		String conferencePackageState = getRequest().getParameter("modify_conferencePackageState");
		
		 String resBak1 = null;
		 String resBak2 = null;
		 String resBak3 = null;
		 String resBak4 = null;
		 
		GrConferencePackage conferencePackage = new GrConferencePackage();
		conferencePackage.setConferencePackageId(conferencePackageId);
		
		
		if(conferencePackageName!=null){
			conferencePackage.setConferencePackageName(conferencePackageName.trim());
		}
		if(conferencePackageRoom!=null){
			conferencePackage.setConferencePackageRoom(conferencePackageRoom.trim());
		}
		if(conferencePackageLocation!=null){
			conferencePackage.setConferencePackageLocation(conferencePackageLocation.trim());
		}
		if(conferencePackageSeat!=null){
			conferencePackage.setConferencePackageSeat(conferencePackageSeat.trim());
		}
		if(conferencePackageStyle!=null){
			conferencePackage.setConferencePackageStyle(conferencePackageStyle.trim());
		}
		if(conferencePackageFacility!=null){
			conferencePackage.setConferencePackageFacility(conferencePackageFacility.trim());
		}
		if(conferencePackagePrice!=null){
			conferencePackage.setConferencePackagePrice(conferencePackagePrice.trim());
		}
		if(conferencePackageService!=null){
			conferencePackage.setConferencePackageService(conferencePackageService.trim());
		}
		if(conferencePackageState!=null){
			conferencePackage.setConferencePackageState(conferencePackageState.trim());
		}
		if(resBak1!=null){
			conferencePackage.setResBak1(resBak1.trim());
		}
		if(resBak2!=null){
			conferencePackage.setResBak2(resBak2.trim());
		}
		if(resBak3!=null){
			conferencePackage.setResBak3(resBak3.trim());
		}
		if(resBak4!=null){
			conferencePackage.setResBak4(resBak4.trim());
		}
		
		conferencePackageManager.updateOneConferencePackage(conferencePackage);
		return SUCCESS;
	}
	
	public String addOneConferencePackagePrepare(){
		return SUCCESS;
	}
	
	public String addOneConferencePackage(){
		String conferencePackageName = getRequest().getParameter("add_conferencePackageName");
		String conferencePackageRoom = getRequest().getParameter("add_conferencePackageRoom");
		String conferencePackageLocation = getRequest().getParameter("add_conferencePackageLocation");
		String conferencePackageSeat = getRequest().getParameter("add_conferencePackageSeat");
		String conferencePackageStyle = getRequest().getParameter("add_conferencePackageStyle");
		String conferencePackageFacility = getRequest().getParameter("add_conferencePackageFacility");
		String conferencePackagePrice = getRequest().getParameter("add_conferencePackagePrice");
		String conferencePackageService = getRequest().getParameter("add_conferencePackageService");
		String conferencePackageState = "1";
		

		 String resBak1 = null;
		 String resBak2 = null;
		 String resBak3 = null;
		String resBak4 = null;
		
		GrConferencePackage conferencePackage = new GrConferencePackage();
		
		if(conferencePackageName!=null){
			conferencePackage.setConferencePackageName(conferencePackageName.trim());
		}
		if(conferencePackageRoom!=null){
			conferencePackage.setConferencePackageRoom(conferencePackageRoom.trim());
		}
		if(conferencePackageLocation!=null){
			conferencePackage.setConferencePackageLocation(conferencePackageLocation.trim());
		}
		if(conferencePackageSeat!=null){
			conferencePackage.setConferencePackageSeat(conferencePackageSeat.trim());
		}
		if(conferencePackageSeat!=null){
			conferencePackage.setConferencePackageSeat(conferencePackageSeat.trim());
		}
		if(conferencePackageStyle!=null){
			conferencePackage.setConferencePackageStyle(conferencePackageStyle.trim());
		}
		if(conferencePackageFacility!=null){
			conferencePackage.setConferencePackageFacility(conferencePackageFacility.trim());
		}
		if(conferencePackagePrice!=null){
			conferencePackage.setConferencePackagePrice(conferencePackagePrice.trim());
		}
		if(conferencePackageService!=null){
			conferencePackage.setConferencePackageService(conferencePackageService.trim());
		}
		if(conferencePackageState!=null){
			conferencePackage.setConferencePackageState(conferencePackageState.trim());
		}
		if(resBak1!=null){
			conferencePackage.setResBak1(resBak1.trim());
		}
		if(resBak2!=null){
			conferencePackage.setResBak2(resBak2.trim());
		}
		if(resBak3!=null){
			conferencePackage.setResBak3(resBak3.trim());
		}
		if(resBak4!=null){
			conferencePackage.setResBak4(resBak4.trim());
		}
		
		conferencePackageManager.addOneConferencePackage(conferencePackage);
		return SUCCESS;
	}
	
	public String showOnePackage(){
		HashMap map = new HashMap();
		map.put("conferencePackageId", getRequest().getParameter("conferencePackageId"));
		
		
			page = conferencePackageManager.queryOneConferences(page, map);

		
	
		return SUCCESS;
	}
	
	public String checkConferencePackage() throws JSONException{
		JSONObject json = new JSONObject();
		String conferencePackageId = getRequest().getParameter("conferencePackageId");
		HashMap map = new HashMap();
		map.put("conferencePackageId", conferencePackageId);
		page = conferencePackageManager.checkConference(page, map);
		if(page.getResult().size()!=0){
			json.put("status",0);
			json.put("message", "该套餐存在已确认的会议，是否停用？");
		}else{
			json.put("status",1);

		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}
	public String checkAddConferencePackage() throws JSONException{
		JSONObject json = new JSONObject();
		String add_conferencePackageName = getRequest().getParameter("add_conferencePackageName").trim();
		String add_conferencePackageRoom = getRequest().getParameter("add_conferencePackageRoom").trim();
		HashMap map = new HashMap();
		map.put("add_conferencePackageName", add_conferencePackageName);
		map.put("add_conferencePackageRoom", add_conferencePackageRoom);
		page = conferencePackageManager.checkAddConference(page, map);
		if(page.getResult().size()!=0){
			json.put("status",0);
			json.put("message", "会议室名称或编号冲突！");
		}else{
			json.put("status",1);

		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}
	
	public String checkConferencePackageName(){
		String conferencePackageName = getRequest().getParameter("conferencePackageName");
		String oldConferencePackageName = getRequest().getParameter("oldConferencePackageName");
		
		if(oldConferencePackageName!=null&&conferencePackageName.equals(oldConferencePackageName)){
			try {
				Struts2Util.getResponse().getWriter().print(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Struts2Util.getResponse().getWriter().print(conferencePackageManager.checkConferencePackageName(conferencePackageName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String checkConferencePackageRoom(){
		String conferencePackageRoom = getRequest().getParameter("conferencePackageRoom");
		String oldConferencePackageRoom = getRequest().getParameter("oldConferencePackageRoom");
		
		if(oldConferencePackageRoom!=null&&conferencePackageRoom.equals(oldConferencePackageRoom)){
			try {
				Struts2Util.getResponse().getWriter().print(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Struts2Util.getResponse().getWriter().print(conferencePackageManager.checkConferencePackageRoom(conferencePackageRoom));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public ConferenceManager getConferenceManager() {
		return conferenceManager;
	}

	public void setConferenceManager(ConferenceManager conferenceManager) {
		this.conferenceManager = conferenceManager;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public ConferencePackageManager getConferencePackageManager() {
		return conferencePackageManager;
	}

	public void setConferencePackageManager(
			ConferencePackageManager conferencePackageManager) {
		this.conferencePackageManager = conferencePackageManager;
	}

	public GrConferencePackage getView_conferencePackage() {
		return view_conferencePackage;
	}

	public void setView_conferencePackage(GrConferencePackage viewConferencePackage) {
		view_conferencePackage = viewConferencePackage;
	}

	public GrConferencePackage getModify_conferencePackage() {
		return modify_conferencePackage;
	}

	public void setModify_conferencePackage(
			GrConferencePackage modifyConferencePackage) {
		modify_conferencePackage = modifyConferencePackage;
	}

	public GrConferencePackage getSuspend_conferencePackage() {
		return suspend_conferencePackage;
	}

	public void setSuspend_conferencePackage(
			GrConferencePackage suspendConferencePackage) {
		suspend_conferencePackage = suspendConferencePackage;
	}

	public String getList_conferencePackageName() {
		return list_conferencePackageName;
	}

	public void setList_conferencePackageName(String listConferencePackageName) {
		list_conferencePackageName = listConferencePackageName;
	}

	public String getList_conferencePackageRoom() {
		return list_conferencePackageRoom;
	}

	public void setList_conferencePackageRoom(String listConferencePackageRoom) {
		list_conferencePackageRoom = listConferencePackageRoom;
	}

	public String getList_conferencePackageState() {
		return list_conferencePackageState;
	}

	public void setList_conferencePackageState(String listConferencePackageState) {
		list_conferencePackageState = listConferencePackageState;
	}
}
