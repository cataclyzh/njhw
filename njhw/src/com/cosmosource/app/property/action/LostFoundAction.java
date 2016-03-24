package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrLostFound;
import com.cosmosource.app.property.service.LostFoundManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * @description: 失物招领
 * @author cehngyun
 * @date 2013-07-15
 */
@SuppressWarnings("unchecked")
public class LostFoundAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录

	// 定义注入对象
	private LostFoundManager lostFoundManager;
	private GrLostFound view_lostfound = new GrLostFound();
	private GrLostFound modify_lostfound = new GrLostFound();
	private GrLostFound claim_lostfound = new GrLostFound();
	private String notClaimCountNumber = "0";
	private String list_lostFoundTitle;
	private String list_lostFoundThingName;
	private String list_lostFoundIntime;
	private String list_lostFoundOvertime;
    private String list_lostFoundState;

	public String showNotClaimCountNumber() {
		notClaimCountNumber = lostFoundManager.showNotClaimCountNumber();
		return null;
	}

	public GrLostFound getView_lostfound() {
		return view_lostfound;
	}

	public void setView_lostfound(GrLostFound viewLostfound) {
		view_lostfound = viewLostfound;
	}

	public GrLostFound getModify_lostfound() {
		return modify_lostfound;
	}

	public void setModify_lostfound(GrLostFound modifyLostfound) {
		modify_lostfound = modifyLostfound;
	}

	public GrLostFound getClaim_lostfound() {
		return claim_lostfound;
	}

	public void setClaim_lostfound(GrLostFound claimLostfound) {
		claim_lostfound = claimLostfound;
	}

	/**
	 * 默认执行该方法
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: lostFoundIndex
	 * @description: 失物招领首页
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String lostFoundIndex() {
		return SUCCESS;
	}	
	
	public String showLostFoundsList() {
		HashMap map = new HashMap();
		
		if(this.list_lostFoundTitle!=null){
			map.put("lostFoundTitle", this.list_lostFoundTitle.trim());
		}
		if(this.list_lostFoundThingName!=null){
			map.put("lostFoundThingName", this.list_lostFoundThingName.trim());
		}
		
		map.put("lostFoundIntime", this.list_lostFoundIntime);
		map.put("lostFoundOverTime", this.list_lostFoundOvertime);
		String[] lostFoundStates = getRequest().getParameterValues(
				"list_lostFoundState");
		if (lostFoundStates != null) {
			if (!lostFoundStates[0].equalsIgnoreCase("all"))
				map.put("lostFoundState", lostFoundStates[0]);
			else
				map.put("lostFoundState", null);
		} else
			map.put("lostFoundState", null);

		page = lostFoundManager.queryAllLostFounds(page, map);
		return SUCCESS;
	}

	public String showMoreLostFounds() {
		HashMap map = new HashMap();
		
		if(getRequest().getParameter("list_lostFoundTitle")!=null){
			map.put("lostFoundTitle", getRequest().getParameter(
			"list_lostFoundTitle").trim());
		}
		if(getRequest().getParameter("list_lostFoundThingName")!=null){
			map.put("lostFoundThingName", getRequest().getParameter(
			"list_lostFoundThingName").trim());
		}
		

		
		map.put("lostFoundIntime", getRequest().getParameter(
				"list_lostFoundIntime"));
		map.put("lostFoundOverTime", getRequest().getParameter(
				"list_lostFoundOverTime"));
		String[] lostFoundStates = getRequest().getParameterValues(
				"list_lostFoundState");
		if (lostFoundStates != null) {
			if (!lostFoundStates[0].equalsIgnoreCase("all"))
				map.put("lostFoundState", lostFoundStates[0]);
			else
				map.put("lostFoundState", null);
		} else
			map.put("lostFoundState", null);

		page = lostFoundManager.queryAllLostFounds(page, map);

		getRequest().setAttribute("lostFoundTitle",
				getParameter("list_lostFoundTitle"));
		getRequest().setAttribute("lostFoundThingName",
				getParameter("list_lostFoundThingName"));
		getRequest().setAttribute("lostFoundIntime",
				getParameter("list_lostFoundIntime"));
		getRequest().setAttribute("lostFoundOverTime",
				getParameter("list_lostFoundOverTime"));
		// getRequest().setAttribute("conferenceState",
		// getParameter("list_conferenceState"));
		return SUCCESS;
	}

	public String queryLostFoundsList() {
		List<GrLostFound> lostFoundsList = lostFoundManager.queryLostFounds(
				getRequest().getParameter("lostFoundTitle"), getRequest()
						.getParameter("lostFoundThingName"), DateUtil
						.StringToDateFormat(getRequest().getParameter(
								"lostFoundIntime")), DateUtil
						.StringToDateFormat(getRequest().getParameter(
								"lostFoundOverTime")), getRequest()
						.getParameter("lostFoundState"));
		getRequest().setAttribute("lostFoundsList", lostFoundsList);
		return SUCCESS;
	}


	/**
	 * 返回首页JSON 数据
	 * @return
	 */
	public String queryLostFoundsListJSON(){
		List<GrLostFound> lostFoundsList = lostFoundManager.queryLostFounds(getRequest().getParameter("lostFoundTitle"),
				getRequest().getParameter("lostFoundThingName"), DateUtil.StringToDateFormat(getRequest().getParameter("lostFoundIntime")),
				DateUtil.StringToDateFormat(getRequest().getParameter("lostFoundOverTime")), getRequest().getParameter("lostFoundState"));
		Struts2Util.renderJson(lostFoundsList, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	public String queryLostFoundById(){
		Long lostFoundId = Long.parseLong(getRequest().getParameter("lostFoundId"));
		view_lostfound = (GrLostFound)lostFoundManager.findLostFoundById(lostFoundId);
		if(view_lostfound!=null){
			getRequest().setAttribute("view_lostfound", view_lostfound);
			return SUCCESS;
		} else
			return ERROR;
	}
	
	/**
	 * 物业首页物业失误招领列表页批量删除
	 */
	public String deleteOneLostFound()throws Exception {
		//Long lostFoundId = Long.parseLong(getRequest().getParameter("lostFoundId"));
		String idStr = getRequest().getParameter("idStr");
		String [] idStrs = idStr.split(",");
		
		try {
			List list = new ArrayList();
			if(idStrs.length > 0 && idStrs != null){
				for(int i=0;i<idStrs.length;i++){
					Map param = new HashMap();
					param.put("lostFoundId", idStrs[i]);
					list.add(param);
				}
				lostFoundManager.deleteBatchBySql("PropertySQL.deleteLostFound", list);
				Struts2Util.renderText("删除物业失误招领信息成功！","");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Struts2Util.renderText("删除物业失误招领信息失败！","");
		}
		return null;
	}

	public String addOneLostFoundPrepare() {
		getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd"));

		return SUCCESS;
	}

	public String addOneLostFound() {
		String lostFoundTitle = getRequest().getParameter("add_lostFoundTitle");
		String lostFoundThingName = getRequest().getParameter(
				"add_lostFoundThingName");
		Date lostFoundIntime = DateUtil.str2Date(getRequest()
				.getParameter("add_lostFoundIntime"),"yyyy-MM-dd HH:mm:ss");
		String lostFoundPickUser = getRequest().getParameter(
				"add_lostFoundPickUser");
		String lostFoundPickUserOrg = getRequest().getParameter(
				"add_lostFoundPickUserOrg");
		String lostFoundLocation = getRequest().getParameter(
				"add_lostFoundLocation");
		String lostFoundDetail = getRequest().getParameter(
				"add_lostFoundDetail");
		String lostFoundKeeper = getRequest().getParameter(
				"add_lostFoundKeeper");
		String lostFoundState = "0";
		String lostFoundLostUser = "";
		String lostFoundLostUserOrg = "";
		Date lostFoundOverTime = null;
		String lostFoundPhone = "";
		String resBak1 = null;
		String resBak2 = null;
		String resBak3 = null;
		String resBak4 = null;
		GrLostFound lostfound = new GrLostFound();
		
		if(lostFoundTitle!=null){
			lostfound.setLostFoundTitle(lostFoundTitle.trim());
		}
		if(lostFoundThingName!=null){
			lostfound.setLostFoundThingName(lostFoundThingName.trim());
		}
		if(lostFoundPickUser!=null){
			lostfound.setLostFoundPickUser(lostFoundPickUser.trim());
		}
		if(lostFoundPickUserOrg!=null){
			lostfound.setLostFoundPickUserOrg(lostFoundPickUserOrg.trim());
		}
		if(lostFoundLocation!=null){
			lostfound.setLostFoundLocation(lostFoundLocation.trim());
		}
		if(lostFoundDetail!=null){
			lostfound.setLostFoundDetail(lostFoundDetail.trim());
		}
		if(lostFoundKeeper!=null){
			lostfound.setLostFoundKeeper(lostFoundKeeper.trim());
		}
		if(lostFoundState!=null){
			lostfound.setLostFoundState(lostFoundState.trim());
		}
		if(lostFoundLostUser!=null){
			lostfound.setLostFoundLostUser(lostFoundLostUser.trim());
		}
		if(lostFoundLostUserOrg!=null){
			lostfound.setLostFoundLostUserOrg(lostFoundLostUserOrg.trim());
		}
		if(lostFoundPhone!=null){
			lostfound.setLostFoundPhone(lostFoundPhone.trim());
		}
		if(resBak1!=null){
			lostfound.setResBak1(resBak1.trim());
		}
		if(resBak2!=null){
			lostfound.setResBak2(resBak2.trim());
		}
	    if(resBak3!=null){
			lostfound.setResBak3(resBak3.trim());
	    }
		if(resBak4!=null){
			lostfound.setResBak4(resBak4.trim());
		}
		
		lostfound.setLostFoundIntime(lostFoundIntime);
		
		lostfound.setLostFoundOverTime(lostFoundOverTime);
		lostFoundManager.addOneLostFound(lostfound);
		return SUCCESS;
	}

	public String updateLostFoundStateById() {
		Long lostFoundId = Long.parseLong(getRequest().getParameter(
				"lostFoundId"));
		lostFoundManager.updateLostFoundStateById(lostFoundId);
		return SUCCESS;
	}

	public String modifyOneLostFoundPrepare() {
		getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd"));

		Long lostFoundId = Long.parseLong(getRequest().getParameter(
				"lostFoundId"));
		view_lostfound = (GrLostFound) lostFoundManager
				.findLostFoundById(lostFoundId);
		if (view_lostfound != null) {
			getRequest().setAttribute("view_lostfound", view_lostfound);
			return SUCCESS;
		} else
			return ERROR;
	}

	public String modifyOneLostFound() {
		Long lostFoundId = Long.parseLong(getRequest().getParameter(
				"modify_lostFoundId"));
		String lostFoundTitle = getRequest().getParameter(
				"modify_lostFoundTitle");
		String lostFoundThingName = getRequest().getParameter(
				"modify_lostFoundThingName");
		Date lostFoundIntime = DateUtil.str2Date(getRequest()
				.getParameter("modify_lostFoundIntime"),"yyyy-MM-dd HH:mm:ss");
		String lostFoundPickUser = getRequest().getParameter(
				"modify_lostFoundPickUser");
		String lostFoundPickUserOrg = getRequest().getParameter(
				"modify_lostFoundPickUserOrg");
		String lostFoundLocation = getRequest().getParameter(
				"modify_lostFoundLocation");
		String lostFoundDetail = getRequest().getParameter(
				"modify_lostFoundDetail");
		String lostFoundKeeper = getRequest().getParameter(
				"modify_lostFoundKeeper");
		String lostFoundState = "0";
		String lostFoundLostUser = "";
		String lostFoundLostUserOrg = "";
		Date lostFoundOverTime = null;
		String lostFoundPhone = "";
		String resBak1 = null;
		String resBak2 = null;
		String resBak3 = null;
		String resBak4 = null;
		GrLostFound lostfound = new GrLostFound();
		lostfound.setLostFoundId(lostFoundId);
		
		if(lostFoundTitle!=null){
			lostfound.setLostFoundTitle(lostFoundTitle.trim());
		}
		if(lostFoundThingName!=null){
			lostfound.setLostFoundThingName(lostFoundThingName.trim());
		}
		if(lostFoundPickUser!=null){
			lostfound.setLostFoundPickUser(lostFoundPickUser.trim());
		}
		if(lostFoundPickUserOrg!=null){
			lostfound.setLostFoundPickUserOrg(lostFoundPickUserOrg.trim());
		}
		if(lostFoundLocation!=null){
			lostfound.setLostFoundLocation(lostFoundLocation.trim());
		}
		if(lostFoundDetail!=null){
			lostfound.setLostFoundDetail(lostFoundDetail.trim());
		}
		if(lostFoundKeeper!=null){
			lostfound.setLostFoundKeeper(lostFoundKeeper.trim());
		}
		if(lostFoundState!=null){
			lostfound.setLostFoundState(lostFoundState.trim());
		}
		if(lostFoundLostUser!=null){
			lostfound.setLostFoundLostUser(lostFoundLostUser.trim());
		}
		if(lostFoundLostUserOrg!=null){
			lostfound.setLostFoundLostUserOrg(lostFoundLostUserOrg.trim());
		}
		if(lostFoundPhone!=null){
			lostfound.setLostFoundPhone(lostFoundPhone.trim());
		}
		if(resBak1!=null){
			lostfound.setResBak1(resBak1.trim());
		}
		if(resBak2!=null){
			lostfound.setResBak2(resBak2.trim());
		}
		if(resBak3!=null){
			lostfound.setResBak3(resBak3.trim());
		}
		if(resBak4!=null){
			lostfound.setResBak4(resBak4.trim());
		}
		
		lostfound.setLostFoundIntime(lostFoundIntime);
		
		
		lostfound.setLostFoundOverTime(lostFoundOverTime);
		lostFoundManager.updateOneLostFound(lostfound);
		return SUCCESS;
	}

	public String claimOneLostFoundPrepare() {
		getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd"));

		Long lostFoundId = Long.parseLong(getRequest().getParameter(
				"lostFoundId"));
		view_lostfound = lostFoundManager.findLostFoundById(lostFoundId);
		if (view_lostfound != null) {
			getRequest().setAttribute("view_lostfound", view_lostfound);
			return SUCCESS;
		} else
			return ERROR;
	}

	public String claimOneLostFound() {
		Long lostFoundId = Long.parseLong(getRequest().getParameter(
				"claim_lostFoundId"));
		GrLostFound temp_lostfound = lostFoundManager
				.findLostFoundById(lostFoundId);
		String lostFoundTitle = temp_lostfound.getLostFoundTitle();
		String lostFoundThingName = temp_lostfound.getLostFoundThingName();
		Date lostFoundIntime = temp_lostfound.getLostFoundIntime();
		String lostFoundPickUser = temp_lostfound.getLostFoundPickUser();
		String lostFoundPickUserOrg = temp_lostfound.getLostFoundPickUserOrg();
		String lostFoundLocation = temp_lostfound.getLostFoundLocation();
		String lostFoundDetail = temp_lostfound.getLostFoundDetail();
		String lostFoundKeeper = temp_lostfound.getLostFoundKeeper();

		String lostFoundState = "1";

		String lostFoundLostUser = getRequest().getParameter(
				"claim_lostFoundLostUser");
		String lostFoundLostUserOrg = getRequest().getParameter(
				"claim_lostFoundLostUserOrg");
		Date lostFoundOverTime = DateUtil.str2Date(getRequest()
				.getParameter("claim_lostFoundOverTime"),"yyyy-MM-dd HH:mm:ss");
		String lostFoundPhone = getRequest().getParameter(
				"claim_lostFoundPhone");
		String resBak1 = null;
		String resBak2 = null;
		String resBak3 = null;
		String resBak4 = null;

		GrLostFound lostfound = new GrLostFound();
		lostfound.setLostFoundId(lostFoundId);
		lostfound.setLostFoundIntime(lostFoundIntime);
		lostfound.setLostFoundOverTime(lostFoundOverTime);
		if(lostFoundTitle!=null){
			lostfound.setLostFoundTitle(lostFoundTitle.trim());
		}
		if(lostFoundThingName!=null){
			lostfound.setLostFoundThingName(lostFoundThingName.trim());
		}
		if(lostFoundPickUser!=null){
			lostfound.setLostFoundPickUser(lostFoundPickUser.trim());
		}
		if(lostFoundPickUserOrg!=null){
			lostfound.setLostFoundPickUserOrg(lostFoundPickUserOrg.trim());
		}
		if(lostFoundLocation!=null){
			lostfound.setLostFoundLocation(lostFoundLocation.trim());
		}
		if(lostFoundDetail!=null){
			lostfound.setLostFoundDetail(lostFoundDetail.trim());
		}
		if(lostFoundKeeper!=null){
			lostfound.setLostFoundKeeper(lostFoundKeeper.trim());
		}
		if(lostFoundState!=null){
			lostfound.setLostFoundState(lostFoundState.trim());
		}
		if(lostFoundLostUser!=null){
			lostfound.setLostFoundLostUser(lostFoundLostUser.trim());
		}
		if(lostFoundLostUserOrg!=null){
			lostfound.setLostFoundLostUserOrg(lostFoundLostUserOrg.trim());
		}
		if(lostFoundPhone!=null){
			lostfound.setLostFoundPhone(lostFoundPhone.trim());
		}
		if(resBak1!=null){
			lostfound.setResBak1(resBak1.trim());
		}
		if(resBak2!=null){
			lostfound.setResBak2(resBak2.trim());
		}
		if(resBak3!=null){
			lostfound.setResBak3(resBak3.trim());
		}
		if(resBak4!=null){
			lostfound.setResBak4(resBak4.trim());
		}		
		lostFoundManager.updateOneLostFound(lostfound);

		return SUCCESS;
	}

	public String updateOneLostFound() {
		Long lostFoundId = Long.parseLong(getRequest().getParameter(
				"modify_lostFoundId"));
		String lostFoundTitle = getRequest().getParameter(
				"modify_lostFoundTitle");
		String lostFoundThingName = getRequest().getParameter(
				"modify_lostFoundThingName");
		Date lostFoundIntime = DateUtil.str2Date(getRequest()
				.getParameter("modify_lostFoundIntime"),"yyyy-MM-dd HH:mm:ss");
		String lostFoundPickUser = getRequest().getParameter(
				"modify_lostFoundPickUser");
		String lostFoundPickUserOrg = getRequest().getParameter(
				"modify_lostFoundPickUserOrg");
		String lostFoundLocation = getRequest().getParameter(
				"modify_lostFoundLocation");
		String lostFoundDetail = getRequest().getParameter(
				"modify_lostFoundDetail");
		String lostFoundKeeper = getRequest().getParameter(
				"modify_lostFoundKeeper");
		String lostFoundState = getRequest().getParameter(
				"modify_lostFoundState");
		String lostFoundLostUser = getRequest().getParameter(
				"modify_lostFoundLostUser");
		String lostFoundLostUserOrg = getRequest().getParameter(
				"modify_lostFoundLostUserOrg");
		Date lostFoundOverTime = DateUtil.str2Date(getRequest()
				.getParameter("modify_lostFoundOverTime"),"yyyy-MM-dd HH:mm:ss");
		String lostFoundPhone = getRequest().getParameter(
				"modify_lostFoundPhone");
		String resBak1 = null;
		String resBak2 = null;
		String resBak3 = null;
		String resBak4 = null;
		GrLostFound lostfound = new GrLostFound();
		lostfound.setLostFoundId(lostFoundId);
		lostfound.setLostFoundIntime(lostFoundIntime);
		lostfound.setLostFoundOverTime(lostFoundOverTime);
        
		if(lostFoundTitle!=null){
			lostfound.setLostFoundTitle(lostFoundTitle.trim());
		}
		if(lostFoundThingName!=null){
			lostfound.setLostFoundThingName(lostFoundThingName.trim());
		}
		if(lostFoundPickUser!=null){
			lostfound.setLostFoundPickUser(lostFoundPickUser.trim());
		}
		if(lostFoundPickUserOrg!=null){
			lostfound.setLostFoundPickUserOrg(lostFoundPickUserOrg.trim());
		}
		if(lostFoundLocation!=null){
			lostfound.setLostFoundLocation(lostFoundLocation.trim());
		}
		if(lostFoundDetail!=null){
			lostfound.setLostFoundDetail(lostFoundDetail.trim());
		}
		if(lostFoundKeeper!=null){
			lostfound.setLostFoundKeeper(lostFoundKeeper.trim());
		}
		if(lostFoundState!=null){
			lostfound.setLostFoundState(lostFoundState.trim());
		}
		if(lostFoundLostUser!=null){
			lostfound.setLostFoundLostUser(lostFoundLostUser.trim());
		}
		if(lostFoundLostUserOrg!=null){
			lostfound.setLostFoundLostUserOrg(lostFoundLostUserOrg.trim());
		}
		if(lostFoundPhone!=null){
			lostfound.setLostFoundPhone(lostFoundPhone.trim());
		}
		if(resBak1!=null){
			lostfound.setResBak1(resBak1.trim());
		}
		if(resBak2!=null){
			lostfound.setResBak2(resBak2.trim());
		}
		if(resBak3!=null){
			lostfound.setResBak3(resBak3.trim());
		}
		if(resBak4!=null){
			lostfound.setResBak4(resBak4.trim());
		}

		
		lostFoundManager.updateOneLostFound(lostfound);
		return SUCCESS;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public LostFoundManager getLostFoundManager() {
		return lostFoundManager;
	}

	public void setLostFoundManager(LostFoundManager lostFoundManager) {
		this.lostFoundManager = lostFoundManager;
	}
	
	public String getNotClaimCountNumber() {
		return notClaimCountNumber.trim();
	}

	public void setNotClaimCountNumber(String notClaimCountNumber) {
		this.notClaimCountNumber = notClaimCountNumber;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}


	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getList_lostFoundTitle() {
		return list_lostFoundTitle;
	}

	public void setList_lostFoundTitle(String listLostFoundTitle) {
		list_lostFoundTitle = listLostFoundTitle;
	}

	public String getList_lostFoundThingName() {
		return list_lostFoundThingName;
	}

	public void setList_lostFoundThingName(String listLostFoundThingName) {
		list_lostFoundThingName = listLostFoundThingName;
	}

	public String getList_lostFoundIntime() {
		return list_lostFoundIntime;
	}

	public void setList_lostFoundIntime(String listLostFoundIntime) {
		list_lostFoundIntime = listLostFoundIntime;
	}

	public String getList_lostFoundOvertime() {
		return list_lostFoundOvertime;
	}

	public void setList_lostFoundOvertime(String listLostFoundOvertime) {
		list_lostFoundOvertime = listLostFoundOvertime;
	}

	public String getList_lostFoundState() {
		return list_lostFoundState;
	}

	public void setList_lostFoundState(String listLostFoundState) {
		list_lostFoundState = listLostFoundState;
	}
}
