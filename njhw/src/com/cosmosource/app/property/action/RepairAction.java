package com.cosmosource.app.property.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.GrDevice;
import com.cosmosource.app.entity.GrDeviceType;
import com.cosmosource.app.entity.GrRepair;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.app.property.model.DeviceCost;
import com.cosmosource.app.property.service.DeviceManager;
import com.cosmosource.app.property.service.DeviceTypeManager;
import com.cosmosource.app.property.service.RepairCostManager;
import com.cosmosource.app.property.service.RepairManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("unchecked")
public class RepairAction extends BaseAction {

	private GrRepair repair;
	private Long repairId;
	private Long deviceTypeId;
	private Long deviceId;
	private Long userid;
	private String outName;
	private Long orgId;
	private String orgName;
	private String phonespan;
	private String completeRepairReceipt;
	private List<GrDeviceType> deviceTypeList = new ArrayList<GrDeviceType>();
	private List<Org> orgList = new ArrayList<Org>();
	private List<GrDevice> deviceList = new ArrayList<GrDevice>();
	private List<GrRepair> repairList = new ArrayList<GrRepair>();
	private List<DeviceCost> deviceCostList = new ArrayList<DeviceCost>();
	private String deviceContent;
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private RepairManager repairManager;
	private DeviceTypeManager deviceTypeManager;
	private DeviceManager deviceManager;
	private RepairCostManager repairCostManager;
	private String repairDetail;
	private String repairTheme;
	private String repairSatisfy;
	private String repairEvaluate;
	private Long reportUserId;
	private Long repairUserId;
	private Long reportUserOrg;
	private String reportUserName;
	private String repairState;
	private Long deviceTypeIdSelect;
	private Long deviceIdSelect;
	private Long orgIdSelect;
	private String repairStartTime;
	private String repairEndTime;

	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
	 * @title: repairIndex
	 * @description: 报修首页
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String repairIndex() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: initRepairList
	 * @description: 初始化获取维修信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String initRepairList() {
		orgList = repairManager.getOrgInfo();
		repairList = repairManager.initGrRepairList();
		return SUCCESS;
	}

	/**
	 * 
	 * @title: getRepairInfoList
	 * @description: 分页得到维修信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getRepairInfoList() {
		orgList = repairManager.getOrgInfo();
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		page = repairManager.getGrRepairList(page, null,null);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: queryRepairInfoList
	 * @description: 分页得到维修信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String queryRepairInfoList() {
		orgList = repairManager.getOrgInfo();
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		HashMap parMap = new HashMap();
		String[] deviceTypeIdArray = getRequest().getParameterValues("deviceTypeIdSelect");
		String[] deviceIdArray = getRequest().getParameterValues("deviceIdSelect");
		String reportUserName = getRequest().getParameter("reportUserName");
	    String[] orgIdArray = getRequest().getParameterValues("orgIdSelect");
	    String repairStartTime = getParameter("repairStartTime");
	    String repairEndTime = getParameter("repairEndTime");
	    String type = getParameter("type");
	    this.repairState = getParameter("repairState");

	    if (deviceTypeIdArray != null) {
			if (!deviceTypeIdArray[0].equalsIgnoreCase("0")) {
				deviceList = deviceManager.loadGrDeviceInfoByDeviceTypeId(Long.parseLong(deviceTypeIdArray[0]));
				parMap.put("deviceTypeId", Long.parseLong(deviceTypeIdArray[0]));
			}
		}
	    
	    if (orgIdArray != null) {
			if (!orgIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("orgId", Long.parseLong(orgIdArray[0]));
			}
		}
	    
	    if (deviceIdArray != null) {
			if (!deviceIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("deviceId", Long.parseLong(deviceIdArray[0]));
			}
		}
		
		if (reportUserName != null && !"".equals(reportUserName)) {
			parMap.put("reportUserName", reportUserName.trim());
		}

		if ((repairStartTime != null) && !"".equals(repairStartTime)) {
			parMap.put("repairStartTime", repairStartTime);
		}
		
		if ((repairEndTime != null) && !"".equals(repairEndTime)) {
			parMap.put("repairEndTime", repairEndTime);
		}

		if (this.repairState != null && !"".equals(this.repairState)) {
			if (this.repairState.equals(Constant.GR_REPAIR_STATE_CONFIRMED)) {
				parMap.put("repairState", Constant.GR_REPAIR_STATE_CONFIRMED);
			}

			if (this.repairState.equals(Constant.GR_REPAIR_STATE_DISTRIBUTED)) {
				parMap.put("repairState", Constant.GR_REPAIR_STATE_DISTRIBUTED);
			}

			if (this.repairState.equals(Constant.GR_REPAIR_STATE_COMPLETED)) {
				parMap.put("repairState", Constant.GR_REPAIR_STATE_COMPLETED);
			}

			if (this.repairState.equals(Constant.GR_REPAIR_STATE_EVALUATED)) {
				parMap.put("repairState", Constant.GR_REPAIR_STATE_EVALUATED);
			}

			if (this.repairState.equals(Constant.GR_REPAIR_STATE_SUSPENDED)) {
				parMap.put("repairState", Constant.GR_REPAIR_STATE_SUSPENDED);
			}
		}

		page = repairManager.getGrRepairList(page, parMap,type);
		return SUCCESS;
	}
	

	/**
	 * 
	 * @title: addRepair
	 * @description: 增加维修信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String addRepair() {
		try {
			Long reportUserOrg = this.orgId;
			String orgName = this.orgName;
			Long deviceTypeId = this.deviceTypeId;
			Long deviceId = this.deviceId;
			Long reportUserId = this.userid;
			String reportUserName = this.outName;
			String reportUserTel = this.phonespan;
		
			String repairTheme = this.repairTheme;
			String repairDetail = this.repairDetail;

			//开放人员录入
			reportUserName = getRequest().getParameter("userName");
			orgName = getRequest().getParameter("userOrgName");
			
			Timestamp repairStartTime = new Timestamp(System
					.currentTimeMillis());
			String repairState = Constant.GR_REPAIR_STATE_CONFIRMED;
			
			if(repairTheme!=null){
				long reportUserIdL = 0;
				long reportUserOrgL = 0;
				
				repairManager.addGrRepairInfo(deviceTypeId,deviceId, reportUserOrgL, orgName,
						reportUserIdL, reportUserName, reportUserTel, repairTheme.trim(),
						repairDetail, repairStartTime, repairState);
			}
			
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: distributionRepair
	 * @description: 派发
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String distributionRepair() {
		try {
			Long repairId = this.repairId;
			Long repairUserId = this.userid;
			String repairUserName = this.outName;
			Long distributeUserId = Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString());
			Timestamp repairDistributeTime = new Timestamp(System.currentTimeMillis());
			String repairState = Constant.GR_REPAIR_STATE_DISTRIBUTED;
			
			repairUserId = 0L;
			repairUserName = getRequest().getParameter("repairUserName");
			
			repairManager.distributionRepair(repairId, repairUserId,
					repairUserName, distributeUserId, repairDistributeTime,
					repairState);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: completeRepair
	 * @description: 完成
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String completeRepair() {
		try {
			Long repairId = this.repairId;
			Long orgId = this.reportUserOrg;
			Long repairCostReportUserId = this.reportUserId;
			Long repairCostRepairUserId = this.repairUserId;
			String repairCostTitle = this.repairTheme;
			String reportUserName = this.reportUserName;
			String orgName = this.orgName;
			
			String completeRepairReceipt = this.completeRepairReceipt;
			Timestamp repairEndTime = new Timestamp(System.currentTimeMillis());
			String repairState = Constant.GR_REPAIR_STATE_COMPLETED;
			String deviceCostContent = this.deviceContent;
			String[] deviceCostContentList = null;
			if (deviceCostContent != null && (!"".equals(deviceCostContent))){
				deviceCostContentList = deviceCostContent.split(Constant.SPLIT_COMMA); 
			}
			
			if (deviceCostContentList != null){
				for (int i=0;i<deviceCostContentList.length;i++){
					DeviceCost deviceCost = new DeviceCost();
					String[] temp = deviceCostContentList[i].split("\\$");
					Long deviceId = Long.parseLong(temp[0]);
					Long deviceNumber = Long.parseLong(temp[1]);
					deviceCost.setDeviceId(deviceId);
					deviceCost.setDeviceNumber(deviceNumber);
					deviceCostList.add(deviceCost);
				}
			}
			
			repairManager.completeRepair(repairId, completeRepairReceipt,
					repairEndTime, repairState);
			if (deviceCostList != null && deviceCostList.size()>0) {
				for(DeviceCost deviceCost:deviceCostList){
					Timestamp repairCostTime = new Timestamp(System.currentTimeMillis());
					Long deviceCostId = deviceCost.getDeviceId();
					Long deviceCostNumber = deviceCost.getDeviceNumber();
					repairCostManager.addGrRepairCostInfo(repairId, deviceCostId, orgId, orgName, repairCostReportUserId, reportUserName, repairCostRepairUserId, repairCostTitle, deviceCostNumber, repairCostTime);
				}
			}
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}
	

	/**
	 * 
	 * @title: evaluateRepair
	 * @description: 回访
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String evaluateRepair() {
		try {
			Long repairId = this.repairId;
			String repairSatisfy = this.repairSatisfy;
			String repairEvaluate = this.repairEvaluate;
			String repairState = Constant.GR_REPAIR_STATE_EVALUATED;
			repairManager.evaluateRepair(repairId, repairSatisfy,repairEvaluate, repairState);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: suspendRepair
	 * @description: 中止
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String suspendRepair() {
		String repairState = Constant.GR_REPAIR_STATE_SUSPENDED;
		Long repairId = Long.parseLong(getParameter("repairId"));
		repairManager.suspendRepair(repairId, repairState);
		return SUCCESS;
	}

	/**
	 * 
	 * @title: deleteRepair
	 * @description: 删除维修信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String deleteRepair() {
		try {
			long repairId = repair.getRepairId();
			repairManager.deleteGrRepairInfoByRepairId(repairId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toAddRepair
	 * @description: 跳转至维修信息新增页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:56:47
	 * @throws
	 */
	public String toAddRepair() {
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toEditRepair
	 * @description: 跳转至维修信息修改页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toEditRepair() {
		try {
			Long repairId = Long.valueOf(getParameter("repairId"));
			deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
			deviceList = deviceManager.initGrDeviceList();
			repair = repairManager.loadGrRepairInfoByRepairId(repairId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toViewRepair
	 * @description: 跳转至维修信息查看页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewRepair() {
		try {
			Long repairId = Long.valueOf(getParameter("repairId"));
			deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
			deviceList = deviceManager.initGrDeviceList();
			repair = repairManager.loadGrRepairInfoByRepairId(repairId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public GrRepair getRepair() {
		return repair;
	}

	public void setRepair(GrRepair repair) {
		this.repair = repair;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public RepairManager getRepairManager() {
		return repairManager;
	}

	public void setRepairManager(RepairManager repairManager) {
		this.repairManager = repairManager;
	}

	public void setRepairList(List<GrRepair> repairList) {
		this.repairList = repairList;
	}

	public List<GrDeviceType> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<GrDeviceType> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	public List<GrDevice> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<GrDevice> deviceList) {
		this.deviceList = deviceList;
	}

	public DeviceTypeManager getDeviceTypeManager() {
		return deviceTypeManager;
	}

	public void setDeviceTypeManager(DeviceTypeManager deviceTypeManager) {
		this.deviceTypeManager = deviceTypeManager;
	}

	public Long getRepairId() {
		return repairId;
	}

	public void setRepairId(Long repairId) {
		this.repairId = repairId;
	}

	public List<GrRepair> getRepairList() {
		return repairList;
	}

	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
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

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPhonespan() {
		return phonespan;
	}

	public void setPhonespan(String phonespan) {
		this.phonespan = phonespan;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getRepairDetail() {
		return repairDetail;
	}

	public void setRepairDetail(String repairDetail) {
		this.repairDetail = repairDetail;
	}

	public String getRepairTheme() {
		return repairTheme;
	}

	public void setRepairTheme(String repairTheme) {
		this.repairTheme = repairTheme;
	}

	public String getCompleteRepairReceipt() {
		return completeRepairReceipt;
	}

	public void setCompleteRepairReceipt(String completeRepairReceipt) {
		this.completeRepairReceipt = completeRepairReceipt;
	}

	public String getRepairSatisfy() {
		return repairSatisfy;
	}

	public void setRepairSatisfy(String repairSatisfy) {
		this.repairSatisfy = repairSatisfy;
	}

	public String getRepairEvaluate() {
		return repairEvaluate;
	}

	public void setRepairEvaluate(String repairEvaluate) {
		this.repairEvaluate = repairEvaluate;
	}

	public List<DeviceCost> getDeviceCostList() {
		return deviceCostList;
	}

	public void setDeviceCostList(List<DeviceCost> deviceCostList) {
		this.deviceCostList = deviceCostList;
	}

	public RepairCostManager getRepairCostManager() {
		return repairCostManager;
	}

	public void setRepairCostManager(RepairCostManager repairCostManager) {
		this.repairCostManager = repairCostManager;
	}

	public Long getReportUserId() {
		return reportUserId;
	}

	public void setReportUserId(Long reportUserId) {
		this.reportUserId = reportUserId;
	}

	public Long getRepairUserId() {
		return repairUserId;
	}

	public void setRepairUserId(Long repairUserId) {
		this.repairUserId = repairUserId;
	}

	public Long getReportUserOrg() {
		return reportUserOrg;
	}

	public void setReportUserOrg(Long reportUserOrg) {
		this.reportUserOrg = reportUserOrg;
	}

	public String getDeviceContent() {
		return deviceContent;
	}

	public void setDeviceContent(String deviceContent) {
		this.deviceContent = deviceContent;
	}

	public String getReportUserName() {
		return reportUserName;
	}

	public void setReportUserName(String reportUserName) {
		this.reportUserName = reportUserName;
	}

	public String getRepairState() {
		return repairState;
	}

	public void setRepairState(String repairState) {
		this.repairState = repairState;
	}

	public Long getDeviceTypeIdSelect() {
		return deviceTypeIdSelect;
	}

	public void setDeviceTypeIdSelect(Long deviceTypeIdSelect) {
		this.deviceTypeIdSelect = deviceTypeIdSelect;
	}

	public Long getDeviceIdSelect() {
		return deviceIdSelect;
	}

	public void setDeviceIdSelect(Long deviceIdSelect) {
		this.deviceIdSelect = deviceIdSelect;
	}

	public String getRepairStartTime() {
		return repairStartTime;
	}

	public void setRepairStartTime(String repairStartTime) {
		this.repairStartTime = repairStartTime;
	}

	public String getRepairEndTime() {
		return repairEndTime;
	}

	public void setRepairEndTime(String repairEndTime) {
		this.repairEndTime = repairEndTime;
	}

	public List<Org> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}

	public Long getOrgIdSelect() {
		return orgIdSelect;
	}

	public void setOrgIdSelect(Long orgIdSelect) {
		this.orgIdSelect = orgIdSelect;
	}
}
