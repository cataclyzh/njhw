package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrPatrolLine;
import com.cosmosource.app.entity.GrPatrolSchedule;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.app.property.service.PatrolLineManager;
import com.cosmosource.app.property.service.PatrolPositionCardManager;
import com.cosmosource.app.property.service.PatrolScheduleManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings("unchecked")
public class PatrolScheduleAction extends BaseAction {

	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private GrPatrolSchedule patrolSchedule;
	private List<GrPatrolSchedule> patrolScheduleList = new ArrayList<GrPatrolSchedule>();
	private List<GrPatrolLine> patrolLineList = new ArrayList<GrPatrolLine>();
	private List<Org> orgList = new ArrayList<Org>();
	private PatrolScheduleManager patrolScheduleManager;
	private PatrolLineManager patrolLineManager;
	private PatrolPositionCardManager patrolPositionCardManager;

	private Long scheduleId;
	private Long userId;
	private String treeUserId;
	private String receiver;
	private String userName;
	private Long patrolLineId;
	private String patrolLineName;
	private String scheduleStartDate;
	private String scheduleEndDate;
	private String scheduleStartTime;
	private String scheduleEndTime;
	private String scheduleDesc;

	private String patrolScheduleState;
	private String scheduleStartQueryDate;
	private String scheduleEndQueryDate;
	private String scheduleStartQueryTime;
	private String scheduleEndQueryTime;
	private String orgIdArray;
	private String patrolLineIdArray;
	private Long patrolLineIdSelect;

	private String patrolScheduleName;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}

	/**
	 * 
	 * @title: patrolIndex
	 * @description: 巡查首页
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String patrolIndex() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: initPatrolScheduleList
	 * @description: 初始化获取巡查路线信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String initPatrolScheduleList() {
		patrolScheduleList = patrolScheduleManager.initGrPatrolScheduleInfo();
		return SUCCESS;
	}

	/**
	 * 
	 * @title: getPatrolScheduleInfoList
	 * @description: 分页得到巡查排班信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getPatrolScheduleInfoList() {
		orgList = patrolPositionCardManager.getOrgInfo();
		patrolLineList = patrolLineManager.initGrPatrolLineInfo();
		page = patrolScheduleManager.getGrPatrolScheduleList(page, null);
		return SUCCESS;
	}

	/**
	 * 
	 * @title: queryRepairInfoList
	 * @description: 根据搜索条件查询巡查排班信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String queryPatrolScheduleInfoList() {
		orgList = patrolPositionCardManager.getOrgInfo();
		patrolLineList = patrolLineManager.initGrPatrolLineInfo();
		HashMap parMap = new HashMap();
		String userName = getRequest().getParameter("userName");
		String scheduleStartQueryDate = getRequest().getParameter(
				"scheduleStartQueryDate");
		String scheduleEndQueryDate = getRequest().getParameter(
				"scheduleEndQueryDate");
		String scheduleStartQueryTime = getRequest().getParameter(
				"scheduleStartQueryTime");
		String scheduleEndQueryTime = getRequest().getParameter(
				"scheduleEndQueryTime");
		String[] orgIdArray = getRequest().getParameterValues("orgIdSelect");
		String[] patrolLineIdArray = getRequest().getParameterValues(
				"patrolLineIdSelect");
		String[] patrolScheduleState = getRequest().getParameterValues(
				"patrolScheduleState");
		if (userName != null && !"".equals(userName)) {
			parMap.put("userName", userName);
		}

		if (orgIdArray != null) {
			if (!orgIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("orgId", Long.parseLong(orgIdArray[0]));
			}
		}

		if (patrolLineIdArray != null) {
			if (!patrolLineIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("patrolLineId", Long.parseLong(patrolLineIdArray[0]));
			}
		}

		if (patrolScheduleState != null) {
			if (!patrolScheduleState[0].equalsIgnoreCase("All")) {
				parMap.put("patrolScheduleState", patrolScheduleState[0]);
			}
		}

		if (scheduleStartQueryDate != null
				&& !"".equals(scheduleStartQueryDate)) {
			parMap.put("scheduleStartQueryDate", scheduleStartQueryDate);
		}

		if (scheduleEndQueryDate != null && !"".equals(scheduleEndQueryDate)) {
			parMap.put("scheduleEndQueryDate", scheduleEndQueryDate);
		}

		if (scheduleStartQueryTime != null
				&& !"".equals(scheduleStartQueryTime)) {
			parMap.put("scheduleStartQueryTime", scheduleStartQueryTime);
		}

		if (scheduleEndQueryTime != null && !"".equals(scheduleEndQueryTime)) {
			parMap.put("scheduleEndQueryTime", scheduleEndQueryTime);
		}
		page = patrolScheduleManager.getGrPatrolScheduleList(page, parMap);
		return SUCCESS;
	}

	/**
	 * 
	 * @title: addPatrolSchedule
	 * @description: 新增排班
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String addPatrolSchedule() {
		patrolLineList = patrolLineManager.initGrPatrolLineInfo();
		List<Map> list = new ArrayList();
		List<Map> list2 = new ArrayList();
		String result = "";
		JSONObject json = new JSONObject();
		try {
			String userIds = getRequest().getParameter("treeUserId");
			String userNames = getRequest().getParameter("receiver");
			String[] patrolLineIdArray = getRequest().getParameterValues(
					"patrolLineId");
			String scheduleStartDate = getRequest().getParameter(
					"scheduleStartDate"); // 2014-01-21
			String scheduleEndDate = getRequest().getParameter(
					"scheduleEndDate"); // 2014-01-21
			String scheduleStartTime = getRequest().getParameter(
					"scheduleStartTime"); // 14:55:00
			String scheduleEndTime = getRequest().getParameter(
					"scheduleEndTime");
			String scheduleDesc = getRequest().getParameter("scheduleDesc");
			String startTime = scheduleStartDate + " " + scheduleStartTime; // 2014-01-21 14:55:00
			String endTime = scheduleEndDate + " " + scheduleEndTime;
			GrPatrolLine patrolLine = new GrPatrolLine();

			// 有查询到线路信息
			if (patrolLineIdArray != null) {

				// 线路是否有效
				if (!patrolLineIdArray[0].equalsIgnoreCase("0")) {
					patrolLineId = Long.parseLong(patrolLineIdArray[0]);
					patrolLine = patrolLineManager
							.loadGrPatrolLineInfoByPatrolLineId(patrolLineId);
				}
			}

			String[] userNameArray = {};

			if (userIds != null && !"".equals(userIds)) {
				String[] userIdArray = userIds.split(",");
				if (userNames != null && !"".equals(userNames)) {
					userNameArray = userNames.split(",");
				}
				if (userIdArray != null && userIdArray.length > 0) {
					for (int i = 0; i < userIdArray.length; i++) {
						Long userId = Long.parseLong(userIdArray[i]);
						// 根据用户ID查询部门信息
						HashMap orgMap = patrolScheduleManager
								.getOrgInfoByUserId(userId);
						String userName = userNameArray[i];
						Long orgId = Long.parseLong(String.valueOf(orgMap
								.get("orgId")));
						String orgName = String.valueOf(orgMap.get("orgName"));
						String patrolLineName = patrolLine.getPatrolLineName();
						String patrolLineDesc = patrolLine.getPatrolLineDesc();
						String patrolNodes = patrolLine.getPatrolNodes();
						String patrolScheduleState = Constant.GR_PATROL_SCHEDULE_STATE_RUNNING;

						// 检查排班是否冲突
						list2 = patrolLineManager.findConfictListById(userId,
								patrolLineId, startTime, endTime);
						
						logger.info(list2.toString());
						if (list2 != null && list2.size() > 0) {
							result += list2.get(0).get("USER_NAME") + ",";
							getRequest().setAttribute(
									"message",
									result.substring(0, result.length() - 1)
											+ "在"
											+ list2.get(0).get(
													"SCHEDULE_START_DATE")
											+ " "
											+ list2.get(0).get(
													"SCHEDULE_START_TIME")
											+ "至"
											+ list2.get(0).get(
													"SCHEDULE_END_DATE")
											+ " "
											+ list2.get(0).get(
													"SCHEDULE_END_TIME")
											+ "已排过班！");
							
							this.isSuc = "false";
						} else {
							
							//获取预插入
							Long sequenceNextVal = patrolScheduleManager.getSequenceNextValue();
							
							//插入排班记录
							boolean flag  = patrolScheduleManager.addGrPatrolScheduleInfo(
									sequenceNextVal,
									orgId, orgName, userId, userName,
									patrolLineId, patrolLineName,
									patrolLineDesc, patrolNodes,
									scheduleStartDate, scheduleEndDate,
									scheduleStartTime, scheduleEndTime,
									scheduleDesc, patrolScheduleState);
							
							/**
							 * 拆分排班记录保存到 GR_PATROL_SCHEDULE_SPLIT表
							 * 2014-01-21 14:55:00  2014-01-31 16:55:00
							 */
							if (flag) {
								patrolScheduleManager.addPatrolScheduleTimeSplit(
										sequenceNextVal,
										scheduleStartDate,scheduleEndDate,
										scheduleStartTime,scheduleEndTime);
								
								/**
								 * 员工与排班对应关系保存至 GR_PATROL_SCHEDULE_USER 表，多对多关系
								 */
								
								patrolScheduleManager.addPatrolScheduleUserSplit(sequenceNextVal, userId);
							}
							
							//控制页面跳转
							this.isSuc = "true";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: editPatrolSchedule
	 * @description: 编辑排班
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String editPatrolSchedule() {

		try {
			this.scheduleId = Long.parseLong(getRequest().getParameter(
					"scheduleId"));
			this.scheduleStartDate = getRequest().getParameter(
					"scheduleStartDate");
			this.scheduleEndDate = getRequest().getParameter("scheduleEndDate");
			this.scheduleStartTime = getRequest().getParameter(
					"scheduleStartTime");
			this.scheduleEndTime = getRequest().getParameter("scheduleEndTime");
			this.scheduleDesc = getRequest().getParameter("scheduleDesc");
			String[] patrolLineIdArray = getRequest().getParameterValues(
					"patrolLineId");
			if (patrolLineIdArray != null) {
				if (!patrolLineIdArray[0].equalsIgnoreCase("0")) {
					this.patrolLineId = Long.parseLong(patrolLineIdArray[0]);
				}
			}
			patrolScheduleManager.editGrPatrolScheduleInfo(this.scheduleId,
					this.patrolLineId, this.scheduleStartDate,
					this.scheduleEndDate, this.scheduleStartTime,
					this.scheduleEndTime, this.scheduleDesc);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: deletePatrolSchedule
	 * @description: 删除排班
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String deletePatrolSchedule() {
		try {
			Long scheduleId = Long.parseLong(getRequest().getParameter(
					"scheduleId"));
			patrolScheduleManager.deleteGrPatrolScheduleInfo(scheduleId);
			setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toAddPatrolSchedule
	 * @description: 跳转至制定新增排班页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:56:47
	 * @throws
	 */
	public String toAddPatrolSchedule() {
		patrolLineList = patrolLineManager.initGrPatrolLineInfo();
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toEditPatrolSchedule
	 * @description: 跳转至编辑排班页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toEditPatrolSchedule() {
		try {
			Long scheduleId = Long.parseLong(getRequest().getParameter(
					"scheduleId"));
			patrolLineList = patrolLineManager.initGrPatrolLineInfo();
			patrolSchedule = patrolScheduleManager
					.loadGrPatrolScheduleInfoByScheduleId(scheduleId);
			this.userName = patrolSchedule.getUserName();
			this.patrolLineId = patrolSchedule.getPatrolLineId();
			this.scheduleStartDate = String.valueOf(patrolSchedule
					.getScheduleStartDate());
			this.scheduleEndDate = String.valueOf(patrolSchedule
					.getScheduleEndDate());
			this.scheduleStartTime = patrolSchedule.getScheduleStartTime();
			this.scheduleEndTime = patrolSchedule.getScheduleEndTime();
			this.scheduleDesc = patrolSchedule.getScheduleDesc();
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toViewPatrolSchedule
	 * @description: 跳转至查看排班页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewPatrolSchedule() {
		try {
			Long scheduleId = Long.parseLong(getRequest().getParameter(
					"scheduleId"));
			patrolLineList = patrolLineManager.initGrPatrolLineInfo();
			patrolSchedule = patrolScheduleManager
					.loadGrPatrolScheduleInfoByScheduleId(scheduleId);
			this.userName = patrolSchedule.getUserName();
			this.patrolLineId = patrolSchedule.getPatrolLineId();
			this.patrolLineName = patrolSchedule.getPatrolLineName();
			this.scheduleStartDate = String.valueOf(patrolSchedule
					.getScheduleStartDate());
			this.scheduleEndDate = String.valueOf(patrolSchedule
					.getScheduleEndDate());
			this.scheduleStartTime = patrolSchedule.getScheduleStartTime();
			this.scheduleEndTime = patrolSchedule.getScheduleEndTime();
			this.scheduleDesc = patrolSchedule.getScheduleDesc();
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public GrPatrolSchedule getPatrolSchedule() {
		return patrolSchedule;
	}

	public void setPatrolSchedule(GrPatrolSchedule patrolSchedule) {
		this.patrolSchedule = patrolSchedule;
	}

	public List<GrPatrolSchedule> getPatrolScheduleList() {
		return patrolScheduleList;
	}

	public void setPatrolScheduleList(List<GrPatrolSchedule> patrolScheduleList) {
		this.patrolScheduleList = patrolScheduleList;
	}

	public PatrolScheduleManager getPatrolScheduleManager() {
		return patrolScheduleManager;
	}

	public void setPatrolScheduleManager(
			PatrolScheduleManager patrolScheduleManager) {
		this.patrolScheduleManager = patrolScheduleManager;
	}

	public String getPatrolScheduleName() {
		return patrolScheduleName;
	}

	public void setPatrolScheduleName(String patrolScheduleName) {
		this.patrolScheduleName = patrolScheduleName;
	}

	public String getTreeUserId() {
		return treeUserId;
	}

	public void setTreeUserId(String treeUserId) {
		this.treeUserId = treeUserId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Long getPatrolLineId() {
		return patrolLineId;
	}

	public void setPatrolLineId(Long patrolLineId) {
		this.patrolLineId = patrolLineId;
	}

	public String getScheduleStartDate() {
		return scheduleStartDate;
	}

	public void setScheduleStartDate(String scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}

	public String getScheduleEndDate() {
		return scheduleEndDate;
	}

	public void setScheduleEndDate(String scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}

	public String getScheduleStartTime() {
		return scheduleStartTime;
	}

	public void setScheduleStartTime(String scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	public String getScheduleEndTime() {
		return scheduleEndTime;
	}

	public void setScheduleEndTime(String scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}

	public String getScheduleDesc() {
		return scheduleDesc;
	}

	public void setScheduleDesc(String scheduleDesc) {
		this.scheduleDesc = scheduleDesc;
	}

	public List<GrPatrolLine> getPatrolLineList() {
		return patrolLineList;
	}

	public void setPatrolLineList(List<GrPatrolLine> patrolLineList) {
		this.patrolLineList = patrolLineList;
	}

	public PatrolLineManager getPatrolLineManager() {
		return patrolLineManager;
	}

	public void setPatrolLineManager(PatrolLineManager patrolLineManager) {
		this.patrolLineManager = patrolLineManager;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPatrolLineName() {
		return patrolLineName;
	}

	public void setPatrolLineName(String patrolLineName) {
		this.patrolLineName = patrolLineName;
	}

	public List<Org> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}

	public PatrolPositionCardManager getPatrolPositionCardManager() {
		return patrolPositionCardManager;
	}

	public void setPatrolPositionCardManager(
			PatrolPositionCardManager patrolPositionCardManager) {
		this.patrolPositionCardManager = patrolPositionCardManager;
	}

	public String getPatrolScheduleState() {
		return patrolScheduleState;
	}

	public void setPatrolScheduleState(String patrolScheduleState) {
		this.patrolScheduleState = patrolScheduleState;
	}

	public String getScheduleEndQueryDate() {
		return scheduleEndQueryDate;
	}

	public void setScheduleEndQueryDate(String scheduleEndQueryDate) {
		this.scheduleEndQueryDate = scheduleEndQueryDate;
	}

	public String getScheduleStartQueryTime() {
		return scheduleStartQueryTime;
	}

	public void setScheduleStartQueryTime(String scheduleStartQueryTime) {
		this.scheduleStartQueryTime = scheduleStartQueryTime;
	}

	public String getScheduleEndQueryTime() {
		return scheduleEndQueryTime;
	}

	public void setScheduleEndQueryTime(String scheduleEndQueryTime) {
		this.scheduleEndQueryTime = scheduleEndQueryTime;
	}

	public String getOrgIdArray() {
		return orgIdArray;
	}

	public void setOrgIdArray(String orgIdArray) {
		this.orgIdArray = orgIdArray;
	}

	public String getPatrolLineIdArray() {
		return patrolLineIdArray;
	}

	public void setPatrolLineIdArray(String patrolLineIdArray) {
		this.patrolLineIdArray = patrolLineIdArray;
	}

	public Long getPatrolLineIdSelect() {
		return patrolLineIdSelect;
	}

	public void setPatrolLineIdSelect(Long patrolLineIdSelect) {
		this.patrolLineIdSelect = patrolLineIdSelect;
	}

	public String getScheduleStartQueryDate() {
		return scheduleStartQueryDate;
	}

	public void setScheduleStartQueryDate(String scheduleStartQueryDate) {
		this.scheduleStartQueryDate = scheduleStartQueryDate;
	}
}