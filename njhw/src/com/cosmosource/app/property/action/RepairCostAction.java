package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.GrDevice;
import com.cosmosource.app.entity.GrDeviceType;
import com.cosmosource.app.entity.GrRepairCost;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.service.DeviceManager;
import com.cosmosource.app.property.service.DeviceTypeManager;
import com.cosmosource.app.property.service.RepairCostManager;
import com.cosmosource.app.property.service.RepairManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;

@SuppressWarnings("unchecked")
public class RepairCostAction extends BaseAction {

	private GrRepairCost repairCost;
	private Long repairCostId;
	private List<GrRepairCost> repairCostList = new ArrayList<GrRepairCost>();
	private List<GrDeviceType> deviceTypeList = new ArrayList<GrDeviceType>();
	private List<GrDevice> deviceList = new ArrayList<GrDevice>();
	private List<Org> orgList = new ArrayList<Org>();
	private String reportUserName;
	private String orgName;
	private String repairCostStartTime;
	private String repairCostEndTime;
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private RepairCostManager repairCostManager;
	private DeviceTypeManager deviceTypeManager;
	private DeviceManager deviceManager;
	private RepairManager repairManager;
	private Long orgIdSelect;
	

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
	 * @title: getRepairCostInfoList
	 * @description: 分页得到维修耗材信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getRepairCostInfoList() {
		orgList = repairManager.getOrgInfo();
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		deviceList = deviceManager.initGrDeviceList();
		page = repairCostManager.getGrRepairCostList(page, null);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: queryRepairCostInfoList
	 * @description: 分页得到维修耗材信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String queryRepairCostInfoList() {
		HashMap parMap = new HashMap();
		orgList = repairManager.getOrgInfo();
		String reportUserName = getParameter("reportUserName");
		String[] orgIdArray = getRequest().getParameterValues("orgIdSelect");
		String repairCostStartTime = getParameter("repairCostStartTime");
		String repairCostEndTime = getParameter("repairCostEndTime");
		if (reportUserName != null && !"".equals(reportUserName)){
			parMap.put("reportUserName", reportUserName.trim());
		}
		
		if (orgIdArray != null) {
			if (!orgIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("orgId", Long.parseLong(orgIdArray[0]));
			}
		}
		
		if (repairCostStartTime != null && !"".equals(repairCostStartTime)){
			parMap.put("repairCostStartTime", repairCostStartTime);
		}
		
		if (repairCostEndTime != null && !"".equals(repairCostEndTime)){
			parMap.put("repairCostEndTime", repairCostEndTime);
		}
		deviceList = deviceManager.initGrDeviceList();
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		page = repairCostManager.getGrRepairCostList(page, parMap);
		return SUCCESS;
	}

	public List<GrRepairCost> getRepairCostList() {
		return repairCostList;
	}

	/**
	 * 
	 * @title: toViewRepairCost
	 * @description: 跳转至维修耗材信息查看页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewRepairCost() {
		try {
			deviceList = deviceManager.initGrDeviceList();
			this.repairCostId = Long.parseLong(getParameter("repairCostId")); 
			this.repairCost = repairCostManager.loadGrRepairInfoByDeviceId(this.repairCostId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public GrRepairCost getRepairCost() {
		return repairCost;
	}

	public void setRepairCost(GrRepairCost repairCost) {
		this.repairCost = repairCost;
	}

	public Long getRepairCostId() {
		return repairCostId;
	}

	public void setRepairCostId(Long repairCostId) {
		this.repairCostId = repairCostId;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public RepairCostManager getRepairCostManager() {
		return repairCostManager;
	}

	public void setRepairCostManager(RepairCostManager repairCostManager) {
		this.repairCostManager = repairCostManager;
	}

	public void setRepairCostList(List<GrRepairCost> repairCostList) {
		this.repairCostList = repairCostList;
	}

	public List<GrDevice> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<GrDevice> deviceList) {
		this.deviceList = deviceList;
	}

	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public List<GrDeviceType> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<GrDeviceType> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	public DeviceTypeManager getDeviceTypeManager() {
		return deviceTypeManager;
	}

	public void setDeviceTypeManager(DeviceTypeManager deviceTypeManager) {
		this.deviceTypeManager = deviceTypeManager;
	}

	public String getReportUserName() {
		return reportUserName;
	}

	public void setReportUserName(String reportUserName) {
		this.reportUserName = reportUserName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRepairCostStartTime() {
		return repairCostStartTime;
	}

	public void setRepairCostStartTime(String repairCostStartTime) {
		this.repairCostStartTime = repairCostStartTime;
	}

	public String getRepairCostEndTime() {
		return repairCostEndTime;
	}

	public void setRepairCostEndTime(String repairCostEndTime) {
		this.repairCostEndTime = repairCostEndTime;
	}

	public Long getOrgIdSelect() {
		return orgIdSelect;
	}

	public void setOrgIdSelect(Long orgIdSelect) {
		this.orgIdSelect = orgIdSelect;
	}

	public List<Org> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}

	public RepairManager getRepairManager() {
		return repairManager;
	}

	public void setRepairManager(RepairManager repairManager) {
		this.repairManager = repairManager;
	}
}
