package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrConference;
import com.cosmosource.app.entity.GrConferencePackage;
import com.cosmosource.app.property.service.ConferenceManager;
import com.cosmosource.app.property.service.ConferencePackageManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/**
 * @description: 会务管理
 * @author cehngyun
 * @date 2013-07-11
 */
@SuppressWarnings("unchecked")
public class ConferenceAction extends BaseAction<Object> {

	private String list_conferenceName;

	private String list_conferenceUserName;

	private String list_conferenceStartTime;

	private String list_conferenceEndTime;

	private String conferenceStartTime;

	private String optionConferenceState;

	private String list_conferenceState;

	private String list_conferenceHasService;

	private Long optionConferenceId;

	private String list_conferencePackageId;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getList_conferenceState() {
		return list_conferenceState;
	}

	public void setList_conferenceState(String listConferenceState) {
		list_conferenceState = listConferenceState;
	}

	public String getList_conferenceStartTime() {
		return list_conferenceStartTime;
	}

	public void setList_conferenceStartTime(String listConferenceStartTime) {
		list_conferenceStartTime = listConferenceStartTime;
	}

	public String getList_conferenceEndTime() {
		return list_conferenceEndTime;
	}

	public void setList_conferenceEndTime(String listConferenceEndTime) {
		list_conferenceEndTime = listConferenceEndTime;
	}

	public String getList_conferenceHasService() {
		return list_conferenceHasService;
	}

	public void setList_conferenceHasService(String listConferenceHasService) {
		list_conferenceHasService = listConferenceHasService;
	}

	public String getList_conferencePackageId() {
		return list_conferencePackageId;
	}

	public void setList_conferencePackageId(String listConferencePackageId) {
		list_conferencePackageId = listConferencePackageId;
	}

	public Long getOptionConferenceId() {
		return optionConferenceId;
	}

	public void setOptionConferenceId(Long optionConferenceId) {
		this.optionConferenceId = optionConferenceId;
	}

	public String getOptionConferenceState() {
		return optionConferenceState;
	}

	public void setOptionConferenceState(String optionConferenceState) {
		this.optionConferenceState = optionConferenceState;
	}

	public String getConferenceStartTime() {
		return conferenceStartTime;
	}

	public void setConferenceStartTime(String conferenceStartTime) {
		this.conferenceStartTime = conferenceStartTime;
	}

	public String getConferenceEndTime() {
		return conferenceEndTime;
	}

	public void setConferenceEndTime(String conferenceEndTime) {
		this.conferenceEndTime = conferenceEndTime;
	}

	private String conferenceEndTime;

	public String getList_conferenceUserName() {
		return list_conferenceUserName;
	}

	public void setList_conferenceUserName(String listConferenceUserName) {
		list_conferenceUserName = listConferenceUserName;
	}

	public String getList_conferenceName() {
		return list_conferenceName;
	}

	public void setList_conferenceName(String listConferenceName) {
		list_conferenceName = listConferenceName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private GrConference conference = new GrConference();
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录

	private Page<HashMap> pagepackage = new Page<HashMap>(10000);

	// 定义注入对象
	private ConferenceManager conferenceManager;
	private ConferencePackageManager conferencePackageManager;
	private GrConference view_conference = new GrConference();
	private GrConference modify_conference = new GrConference();
	private GrConference value_conference = new GrConference();
	private List<GrConferencePackage> view_packages = new ArrayList<GrConferencePackage>();

	public ConferencePackageManager getConferencePackageManager() {
		return conferencePackageManager;
	}

	public void setConferencePackageManager(
			ConferencePackageManager conferencePackageManager) {
		this.conferencePackageManager = conferencePackageManager;
	}

	public List<GrConferencePackage> getView_packages() {
		return view_packages;
	}

	public void setView_packages(List<GrConferencePackage> viewPackages) {
		view_packages = viewPackages;
	}

	public GrConference getModify_conference() {
		return modify_conference;
	}

	public void setModify_conference(GrConference modifyConference) {
		modify_conference = modifyConference;
	}

	public GrConference getValue_conference() {
		return value_conference;
	}

	public void setValue_conference(GrConference valueConference) {
		value_conference = valueConference;
	}

	public GrConference getView_conference() {
		return view_conference;
	}

	public void setView_conference(GrConference viewConference) {
		view_conference = viewConference;
	}

	/**
	 * 默认执行该方法
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String toIndex() {
		return SUCCESS;
	}
	
	public String toEvaluateConference() throws Exception{
		String conferenceId = getRequest().getParameter("conferenceId");
		Map conference = conferenceManager.getConferenceInfoById(conferenceId);
		getRequest().setAttribute("conference", conference);
		return SUCCESS;
	}
	
	public String saveEvaluateConference() throws Exception{
		String conferenceId = getRequest().getParameter("conferenceId");
		String evaluate = getRequest().getParameter("conferenceSatisfy");
		Map m = new HashMap();
		m.put("id", conferenceId);
		m.put("evaluate", evaluate);
		conferenceManager.saveEvaluateConference(m);
		return SUCCESS;
	}

	/**
	 * 分页查询,根据前台传过来的参数查询所有符合条件的会务list 物业人员
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 */
	public String showConferencesList() throws Exception {

		String nowDate = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");

		conferenceManager.updatePassConferenceState(nowDate);

		HashMap map = new HashMap();
		map.put("conferenceStartTime", this.list_conferenceStartTime);
		map.put("conferenceEndTime", this.list_conferenceEndTime);
		if (this.list_conferenceName != null) {
			map.put("conferenceName", this.list_conferenceName.trim());
		}
		if (this.list_conferenceUserName != null) {
			map.put("conferenceUserName", this.list_conferenceUserName.trim());

		}

		String conferenceHasServices[] = getRequest().getParameterValues(
				"list_conferenceHasService");
		if (conferenceHasServices != null) {
			if (!conferenceHasServices[0].equalsIgnoreCase("all"))
				map.put("conferenceHasService", conferenceHasServices[0]);
			else
				map.put("conferenceHasService", null);
		} else {
			map.put("conferenceHasService", null);
		}

		String conferenceStates[] = getRequest().getParameterValues(
				"list_conferenceState");
		if (conferenceStates != null) {
			this.optionConferenceState = conferenceStates[0];
			if (!conferenceStates[0].equalsIgnoreCase("all"))
				map.put("conferenceState", conferenceStates[0]);
			else
				map.put("conferenceState", null);
		} else {
			map.put("conferenceState", null);
		}

		String conferencePackageIds[] = getRequest().getParameterValues(
				"list_conferencePackageId");
		if (conferencePackageIds != null) {
			if (!conferencePackageIds[0].equalsIgnoreCase("all")) {
				this.optionConferenceId = Long
						.parseLong(conferencePackageIds[0]);
				map.put("conferencePackageId", Long
						.parseLong(conferencePackageIds[0]));
			} else {
				map.put("conferencePackageId", null);
			}
		} else {
			map.put("conferencePackageId", null);
		}
		String type = getRequest().getParameter("type");
		page = conferenceManager.queryAllConferences(page, map,type);

		view_packages = conferencePackageManager.queryConferencePackages(null,
				null, null, null, null);
		getRequest().setAttribute("view_packages", view_packages);
		return SUCCESS;
	}

	/**
	 * 查询所有我的会务list 供委办局人员操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showMyConferences() throws Exception {
		String nowDate = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");

		conferenceManager.updatePassConferenceState(nowDate);
		HashMap map = new HashMap();
		map.put("conferenceStartTime", getRequest().getParameter(
				"conferenceStartTime"));
		map.put("conferenceEndTime", getRequest().getParameter(
				"conferenceEndTime"));
		if (getRequest().getParameter("list_conferenceName") != null) {
			map.put("conferenceName", getRequest().getParameter(
					"list_conferenceName").trim());
		}
		if (getRequest().getParameter("list_conferenceUserName") != null) {
			map.put("conferenceUserName", getRequest().getParameter(
					"list_conferenceUserName").trim());
		}

		String conferenceHasServices[] = getRequest().getParameterValues(
				"list_conferenceHasService");
		if (conferenceHasServices != null) {
			if (!conferenceHasServices[0].equalsIgnoreCase("all"))
				map.put("conferenceHasService", conferenceHasServices[0]);
			else
				map.put("conferenceHasService", null);
		} else {
			map.put("conferenceHasService", null);
		}

		String conferenceStates[] = getRequest().getParameterValues(
				"list_conferenceState");
		if (conferenceStates != null) {
			this.optionConferenceState = conferenceStates[0];
			if (!conferenceStates[0].equalsIgnoreCase("all"))
				map.put("conferenceState", conferenceStates[0]);
			else
				map.put("conferenceState", null);
		} else {
			map.put("conferenceState", null);
		}

		String conferencePackageIds[] = getRequest().getParameterValues(
				"list_conferencePackageId");
		if (conferencePackageIds != null) {
			if (!conferencePackageIds[0].equalsIgnoreCase("all")) {
				this.optionConferenceId = Long
						.parseLong(conferencePackageIds[0]);
				map.put("conferencePackageId", Long
						.parseLong(conferencePackageIds[0]));
			} else {
				map.put("conferencePackageId", null);
			}
		} else {
			map.put("conferencePackageId", null);
		}

		map.put("conferenceUserId", Long.parseLong(Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString()));
		// map.put("conferenceUserId",
		// Long.parseLong(Struts2Util.getSession().getAttribute("Constants.USER_ID").toString()));
		page = conferenceManager.queryMyConferences(page, map);
		view_packages = conferencePackageManager.queryConferencePackages(null,
				null, null, null, null);
		getRequest().setAttribute("view_packages", view_packages);
		return SUCCESS;
	}

	/**
	 * 根据前台传过来的参数查询所有符合条件的会务list，没有分页功能
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryConferencesList() throws Exception {

		String conferenceHasServices[] = getRequest().getParameterValues(
				"list_conferenceHasService");
		String conferenceHasService = null;
		if (conferenceHasServices != null
				&& !conferenceHasServices[0].equalsIgnoreCase("all")) {
			conferenceHasService = conferenceHasServices[0];
		}

		String conferenceStates[] = getRequest().getParameterValues(
				"list_conferenceState");
		String conferenceState = null;
		if (conferenceStates != null
				&& !conferenceStates[0].equalsIgnoreCase("all")) {
			conferenceState = conferenceStates[0];
		}

		String conferencePackageIds[] = getRequest().getParameterValues(
				"list_conferencePackageId");
		Long conferencePackageId = null;
		if (conferencePackageIds != null
				&& !conferencePackageIds[0].equalsIgnoreCase("all")) {
			conferencePackageId = Long.parseLong(conferencePackageIds[0]);
		}

		List<GrConference> conferenceslist = conferenceManager
				.queryConferences(getRequest().getParameter("conferenceName"),
						DateUtil.StringToDate(getRequest().getParameter(
								"conferenceStartTime")), DateUtil
								.StringToDate(getRequest().getParameter(
										"conferenceEndTime")), getRequest()
								.getParameter("conferenceUserName"),
						conferenceHasService, conferenceState,
						conferencePackageId);

		getRequest().setAttribute("conferenceslist", conferenceslist);
		return SUCCESS;
	}

	/**
	 * 根据前台传过来的conferenceId查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryConferenceById() throws Exception {
		// JSONObject json = new JSONObject();
		// Map map = new HashMap();
		// List<Map> mapList = new ArrayList<Map>();

		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		view_conference = (GrConference) conferenceManager
				.findConferenceById(conferenceId);

		if (view_conference != null) {
			HashMap map = new HashMap();
			map.put("conferencePackageName", getRequest().getParameter(
					"list_conferencePackageName"));
			map.put("conferencePackageRoom", getRequest().getParameter(
					"list_conferencePackageRoom"));
			map.put("conferencePackageLocation", getRequest().getParameter(
					"list_conferencePackageLocation"));
			map.put("conferencePackageStyle", getRequest().getParameter(
					"list_conferencePackageStyle"));
			map.put("conferencePackageId", view_conference
					.getConferencePackageId());

			map.put("conferencePackageState", null);

			getRequest().setAttribute("operationAuthor",
					getRequest().getParameter("operationAuthor"));
			page = conferencePackageManager.queryAllConferencePackages(page,
					map);
			getRequest().setAttribute("view_conference", view_conference);
			return SUCCESS;
		} else {
			getRequest().setAttribute("view_conference", null);
			return ERROR;
		}
	}

	public String deleteOneConference() throws Exception {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		conferenceManager.deleteOneConference(conferenceId);
		if (getRequest().getParameter("isadmin").equalsIgnoreCase("yes"))
			return "adminpage";// 和showConferencesList页面相同
		else
			return "supplierpage";// 和showMyConferences页面相同
	}

	public String addOnePrepare() {
		HashMap map = new HashMap();
		if (getRequest().getParameter("list_conferencePackageName") != null) {
			map.put("conferencePackageName", getRequest().getParameter(
					"list_conferencePackageName").trim());
		}
		if (getRequest().getParameter("list_conferencePackageRoom") != null) {
			map.put("conferencePackageRoom", getRequest().getParameter(
					"list_conferencePackageRoom").trim());
		}
		if (getRequest().getParameter("list_conferencePackageLocation") != null) {
			map.put("conferencePackageLocation", getRequest().getParameter(
					"list_conferencePackageLocation").trim());
		}
		if (getRequest().getParameter("list_conferencePackageStyle") != null) {
			map.put("conferencePackageStyle", getRequest().getParameter(
					"list_conferencePackageStyle").trim());
		}

		map.put("conferencePackageId", null);

		map.put("conferencePackageState", Long.parseLong("1"));

		getRequest().setAttribute("operationAuthor",
				getRequest().getParameter("operationAuthor"));
		pagepackage = conferencePackageManager.queryAllConferencePackages(pagepackage, map);
		getRequest().setAttribute("nowDate",
				DateUtil.getDateTime("yyyy-MM-dd HH:mm"));
		return SUCCESS;

	}

	public Page<HashMap> getPagepackage() {
		return pagepackage;
	}

	public void setPagepackage(Page<HashMap> pagepackage) {
		this.pagepackage = pagepackage;
	}

	public String checkOneConference() throws JSONException {
		JSONObject json = new JSONObject();
		String beginTime = Struts2Util.getParameter("beginTime");
		String endTime = Struts2Util.getParameter("endTime");
		String packageId = Struts2Util.getParameter("packageId");
		HashMap map = new HashMap();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("packageId", packageId);
		page = conferenceManager.queryOneConferencesEx(page, map);
		if (page.getResult().size() != 0) {
			json.put("status", 0);
			json.put("message", "会议时间冲突");
		} else {
			json.put("status", 1);

		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	public String addOneConference() throws Exception {

		String conferenceName = getRequest().getParameter("add_conferenceName");
		Date conferenceStartTime = DateUtil.StringToDate(getRequest()
				.getParameter("add_conferenceStartTime"));
		Date conferenceEndTime = DateUtil.StringToDate(getRequest()
				.getParameter("add_conferenceEndTime"));
		Long conferenceUserId = Long.parseLong(Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString());
		String conferenceUserName = getRequest().getParameter("outName");
		String conferenceUserORG = getRequest().getParameter("orgName");
		
		conferenceUserName = getRequest().getParameter("proposerName");

		String conferenceUserPhone = "";
		if (getRequest().getParameter("phonespan") != null)
			conferenceUserPhone = getRequest().getParameter("phonespan");

		long conferencePackageId = Long.parseLong(getRequest().getParameter(
				"conferencePackageId"));

		String conferenceRoom = null;
		String conferenceManCount = null;

		String conferenceState = "0";

		String conferenceHasService = null;
		String conferenceDetailService = getRequest().getParameter(
				"add_conferenceDetailService");

		String conferenceSatisfy = "";
		String conferenceClientValue = "";
		String resBak1 = null;
		if(getRequest().getParameter("userid") != null){
			resBak1 = getRequest().getParameter("userid");
		}
		String resBak2 = null;
		if(getRequest().getParameter("orgId") != null){
			resBak2 = getRequest().getParameter("orgId");
		}
		String resBak3 = null;
		String resBak4 = null;
		GrConference conference = new GrConference();

		if (conferenceName != null) {
			conference.setConferenceName(conferenceName.trim());
		}
		if (conferenceUserName != null) {
			conference.setOutName(conferenceUserName.trim());
		}
		if (conferenceUserORG != null) {
			conference.setOrgName(conferenceUserORG.trim());
		}
		if (conferenceUserPhone != null) {
			conference.setPhonespan(conferenceUserPhone.trim());
		}
		if (conferenceRoom != null) {
			conference.setConferenceRoom(conferenceRoom.trim());
		}
		if (conferenceManCount != null) {
			conference.setConferenceManCount(conferenceManCount.trim());
		}
		if (conferenceHasService != null) {
			conference.setConferenceHasService(conferenceHasService.trim());
		}
		if (conferenceDetailService != null) {
			conference.setConferenceDetailService(conferenceDetailService
					.trim());
		}
		if (conferenceClientValue != null) {
			conference.setConferenceClientValue(conferenceClientValue.trim());
		}
		if (resBak1 != null) {
			conference.setResBak1(resBak1.trim());
		}
		if (resBak2 != null) {
			conference.setResBak2(resBak2.trim());
		}
		if (resBak3 != null) {
			conference.setResBak3(resBak3.trim());
		}
		if (resBak4 != null) {
			conference.setResBak4(resBak4.trim());
		}

		conference.setConferenceStartTime(conferenceStartTime);
		conference.setConferenceEndTime(conferenceEndTime);
		conference.setConferenceUserId(conferenceUserId);

		conference.setConferenceState(conferenceState);
		conference.setConferenceSatisfy(conferenceSatisfy);
		conference.setConferencePackageId(conferencePackageId);

		conferenceManager.addOneConference(conference);
		// conferenceManager.saveObj(conference);
		return SUCCESS;

	}

	public String updateConferenceStateById() throws Exception {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		String confrenceState = "1";// 完成
		conferenceManager.updateConferenceStateById(conferenceId,
				confrenceState);
		return SUCCESS;
	}

	public String confirmConferenceStateById() throws Exception {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		String confrenceState = "4";// 完成
		conferenceManager.updateConferenceStateById(conferenceId,
				confrenceState);
		return SUCCESS;
	}

	public String cancelConferenceStateById() throws Exception {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		String confrenceState = "3";// 0,申请，1完成，2满意度，3取消,4已确认
		conferenceManager.updateConferenceStateById(conferenceId,
				confrenceState);

		return SUCCESS;
	}

	public String modifyOnePrepare() {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		view_conference = (GrConference) conferenceManager
				.findConferenceById(conferenceId);

		if (view_conference != null) {
			HashMap map = new HashMap();

			if (getRequest().getParameter("list_conferencePackageName") != null) {
				map.put("conferencePackageName", getRequest().getParameter(
						"list_conferencePackageName").trim());
			}
			if (getRequest().getParameter("list_conferencePackageRoom") != null) {
				map.put("conferencePackageRoom", getRequest().getParameter(
						"list_conferencePackageRoom").trim());
			}
			if (getRequest().getParameter("list_conferencePackageLocation") != null) {
				map.put("conferencePackageLocation", getRequest().getParameter(
						"list_conferencePackageLocation").trim());
			}
			if (getRequest().getParameter("list_conferencePackageStyle") != null) {
				map.put("conferencePackageStyle", getRequest().getParameter(
						"list_conferencePackageStyle").trim());
			}

			map.put("conferencePackageId", null);

			map.put("conferencePackageState", null);

			getRequest().setAttribute("operationAuthor",
					getRequest().getParameter("operationAuthor"));
			pagepackage = conferencePackageManager.queryAllConferencePackages(pagepackage,
					map);
			getRequest().setAttribute("view_conference", view_conference);
			getRequest().setAttribute("nowDate",
					DateUtil.getDateTime("yyyy-MM-dd HH:mm"));
			return SUCCESS;
		} else {
			return null;
		}

	}

	public String modifyOneConference() {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"modify_conferenceId"));
		String conferenceName = getRequest().getParameter(
				"modify_conferenceName");
		Date conferenceStartTime = DateUtil.StringToDate(getRequest()
				.getParameter("modify_conferenceStartTime"));
		Date conferenceEndTime = DateUtil.StringToDate(getRequest()
				.getParameter("modify_conferenceEndTime"));
		Long conferenceUserId = Long.parseLong(Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString());
		String conferenceUserName = getRequest().getParameter("outName");
		String conferenceUserORG = getRequest().getParameter("orgName");

		String conferenceUserPhone = "";
		if (getRequest().getParameter("phonespan") != null)
			conferenceUserPhone = getRequest().getParameter("phonespan");

		String conferenceRoom = getRequest().getParameter(
				"modify_conferenceRoom");
		String conferenceManCount = getRequest().getParameter(
				"modify_conferenceManCount");
		String[] conferenceHasServices = null;
		conferenceHasServices = getRequest().getParameterValues(
				"modify_conferenceHasService");
		// System.out.println(conferenceHasServices);
		String conferenceHasService = "0";

		String conferenceState = "0";
		if (conferenceHasServices != null)
			conferenceHasService = conferenceHasServices[0];

		String conferenceDetailService = "";
		if (getRequest().getParameter("modify_conferenceDetailService") != null)
			conferenceDetailService = getRequest().getParameter(
					"modify_conferenceDetailService");

		Long conferencePackageId = Long.parseLong(getRequest().getParameter(
				"conferencePackageId"));
		String conferenceSatisfy = "";
		String conferenceClientValue = "";
		String resBak1 = null;
		String resBak2 = null;
		String resBak3 = null;
		String resBak4 = null;
		GrConference conference = new GrConference();

		if (conferenceName != null) {
			conference.setConferenceName(conferenceName.trim());
		}
		if (conferenceUserName != null) {
			conference.setOutName(conferenceUserName.trim());
		}
		if (conferenceUserORG != null) {
			conference.setOrgName(conferenceUserORG.trim());
		}
		if (conferenceUserPhone != null) {
			conference.setPhonespan(conferenceUserPhone.trim());
		}
		if (conferenceRoom != null) {
			conference.setConferenceRoom(conferenceRoom.trim());
		}
		if (conferenceManCount != null) {
			conference.setConferenceManCount(conferenceManCount.trim());
		}
		if (conferenceHasService != null) {
			conference.setConferenceHasService(conferenceHasService.trim());
		}
		if (conferenceDetailService != null) {
			conference.setConferenceDetailService(conferenceDetailService
					.trim());
		}
		if (conferenceClientValue != null) {
			conference.setConferenceClientValue(conferenceClientValue.trim());
		}
		if (resBak1 != null) {
			conference.setResBak1(resBak1.trim());
		}
		if (resBak2 != null) {
			conference.setResBak2(resBak2.trim());
		}
		if (resBak3 != null) {
			conference.setResBak3(resBak3);
		}
		if (resBak4 != null) {
			conference.setResBak4(resBak4);
		}

		conference.setConferenceId(conferenceId);
		conference.setConferenceStartTime(conferenceStartTime);
		conference.setConferenceEndTime(conferenceEndTime);
		conference.setConferenceUserId(conferenceUserId);
		conference.setConferenceState(conferenceState);
		conference.setConferenceSatisfy(conferenceSatisfy);
		conference.setConferencePackageId(conferencePackageId);

		conferenceManager.updateOneConference(conference);
		// conferenceManager.saveObj(conference);
		return SUCCESS;
	}

	public String valueOnePrepare() {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		view_conference = (GrConference) conferenceManager
				.findConferenceById(conferenceId);

		if (view_conference != null) {
			HashMap map = new HashMap();

			if (getRequest().getParameter("list_conferencePackageName") != null) {
				map.put("conferencePackageName", getRequest().getParameter(
						"list_conferencePackageName").trim());
			}
			if (getRequest().getParameter("list_conferencePackageRoom") != null) {
				map.put("conferencePackageRoom", getRequest().getParameter(
						"list_conferencePackageRoom").trim());
			}
			if (getRequest().getParameter("list_conferencePackageLocation") != null) {
				map.put("conferencePackageLocation", getRequest().getParameter(
						"list_conferencePackageLocation").trim());
			}
			if (getRequest().getParameter("list_conferencePackageStyle") != null) {
				map.put("conferencePackageStyle", getRequest().getParameter(
						"list_conferencePackageStyle").trim());
			}

			map.put("conferencePackageId", view_conference
					.getConferencePackageId());

			map.put("conferencePackageState", null);

			getRequest().setAttribute("operationAuthor",
					getRequest().getParameter("operationAuthor"));
			page = conferencePackageManager.queryAllConferencePackages(page,
					map);
			getRequest().setAttribute("view_conference", view_conference);
			return SUCCESS;
		} else {
			return null;
		}

	}

	public String valueOneConference() {
		Long conferenceId = Long.parseLong(getRequest().getParameter(
				"conferenceId"));
		String conferenceState = "2";
		String conferenceClientValue = getRequest().getParameter(
				"value_conferenceClientValue");
		String conferenceSatisfy = getRequest().getParameter(
				"value_conferenceSatisfy");
		conferenceManager.valeOneConference(conferenceId, conferenceState,
				conferenceClientValue, conferenceSatisfy);

		return SUCCESS;
	}

	// public String updateOneConference() throws Exception{
	// Long conferenceId =
	// Long.parseLong(getRequest().getParameter("conferenceId"));
	// String conferenceName = getRequest().getParameter("conferenceName");
	// Date conferenceStartTime = new
	// Date(getRequest().getParameter("conferenceStartTime"));
	// Date conferenceEndTime = new
	// Date(getRequest().getParameter("conferenceEndTime"));
	// Long conferenceUserId =
	// Long.parseLong(Struts2Util.getSession().getAttribute("Constants.USER_ID").toString());
	// String conferenceUserName =
	// getRequest().getParameter("conferenceUserName");
	// String conferenceUserORG =
	// getRequest().getParameter("conferenceUserORG");
	// String conferenceUserPhone =
	// getRequest().getParameter("conferenceUserPhone");
	// String conferenceRoom=getRequest().getParameter("conferenceRoom");
	// String conferenceManCount =
	// getRequest().getParameter("conferenceManCount");
	// String conferenceState= getRequest().getParameter("conferenceState");
	// String conferenceHasService =
	// getRequest().getParameter("conferenceHasService");
	// String conferenceDetailService =
	// getRequest().getParameter("conferenceDetailService");
	// String conferenceSatisfy =
	// getRequest().getParameter("conferenceSatisfy");
	// String conferenceClientValue =
	// getRequest().getParameter("conferenceClientValue");
	// String resBak1 = getRequest().getParameter("resBak1");
	// String resBak2 = getRequest().getParameter("resBak2");
	// String resBak3 = getRequest().getParameter("resBak3");
	// String resBak4 = getRequest().getParameter("resBak4");
	// GrConference conference = new GrConference();
	// conference.setConferenceId(conferenceId);
	// conference.setConferenceName(conferenceName);
	// conference.setConferenceStartTime(conferenceStartTime);
	// conference.setConferenceEndTime(conferenceEndTime);
	// conference.setConferenceUserId(conferenceUserId);
	// conference.setConferenceUserName(conferenceUserName);
	// conference.setConferenceUserORG(conferenceUserORG);
	// conference.setConferenceUserPhone(conferenceUserPhone);
	// conference.setConferenceRoom(conferenceRoom);
	// conference.setConferenceManCount(conferenceManCount);
	// conference.setConferenceState(conferenceState);
	// conference.setConferenceHasService(conferenceHasService);
	// conference.setConferenceDetailService(conferenceDetailService);
	// conference.setConferenceSatisfy(conferenceSatisfy);
	// conference.setConferenceClientValue(conferenceClientValue);
	// conference.setResBak1(resBak1);
	// conference.setResBak2(resBak2);
	// conference.setResBak3(resBak3);
	// conference.setResBak4(resBak4);
	//		
	// conferenceManager.updateOneConference(conference);
	// return "adminpage";
	// }

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

	public ConferenceManager getConferenceManager() {
		return conferenceManager;
	}

	public void setConferenceManager(ConferenceManager conferenceManager) {
		this.conferenceManager = conferenceManager;
	}

}
