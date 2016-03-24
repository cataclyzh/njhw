package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.displaytag.tags.TableTagParameters;

import com.cosmosource.app.entity.GrDevice;
import com.cosmosource.app.entity.GrDeviceType;
import com.cosmosource.app.entity.GrInOutStorage;
import com.cosmosource.app.entity.GrStorage;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.app.property.service.DeviceManager;
import com.cosmosource.app.property.service.DeviceTypeManager;
import com.cosmosource.app.property.service.InOutStorageManager;
import com.cosmosource.app.property.service.RepairManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;

@SuppressWarnings("unchecked")
public class InOutStorageAction extends BaseAction {

	private GrInOutStorage inOutStorage;
	private Long inOutStorageId;
	private Long deviceId;
	private List<GrStorage> storageList = new ArrayList<GrStorage>();
	private List<GrDevice> deviceList = new ArrayList<GrDevice>();
	private List<GrDevice> deviceDisplayList = new ArrayList<GrDevice>();
	private List<GrDeviceType> deviceTypeList = new ArrayList<GrDeviceType>();
	private List<GrInOutStorage> inOutStorageList = new ArrayList<GrInOutStorage>();
	private List<Org> orgList = new ArrayList<Org>();
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private InOutStorageManager inOutStorageManager;
	private DeviceManager deviceManager;
	private DeviceTypeManager deviceTypeManager;
    private RepairManager repairManager;
	private Long lenderUserId;
	private String lenderUserName;
	private Long lenderUserOrg;
	private String lenderUserOrgName;
	private Long deviceTypeId;
	private String inOutStorageStartTime;
	private String inOutStorageEndTime;
	private String inoutStorageFlag;
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
	 * @title: getInOutStorageList
	 * @description: 分页得到出入库信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getInOutStorageInfoList() {
		orgList = repairManager.getOrgInfo();
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		deviceDisplayList = deviceManager.initGrDeviceList();
		page = inOutStorageManager.getGrInOutStorageList(page, null);
		return SUCCESS;
	}

	/**
	 * 
	 * @title: queryInOutStorageInfoList
	 * @description: 分页得到出入库信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String queryInOutStorageInfoList() {
		orgList = repairManager.getOrgInfo();
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
        deviceDisplayList = deviceManager.initGrDeviceList();
		HashMap parMap = new HashMap();
		String[] deviceTypeIdArray = getRequest().getParameterValues(
				"deviceTypeId");
		String lenderUserName = getRequest().getParameter("lenderUserName");
		String[] deviceIdArray = getRequest().getParameterValues("deviceId");
		String[] orgIdArray = getRequest().getParameterValues("orgIdSelect");
		String inOutStorageStartTime = getParameter("inOutStorageStartTime");
		String inOutStorageEndTime = getParameter("inOutStorageEndTime");
		String inoutStorageFlag = getParameter("inoutStorageFlag");

		if (deviceTypeIdArray != null) {
			if (!deviceTypeIdArray[0].equalsIgnoreCase("0")) {
				parMap
						.put("deviceTypeId", Long
								.parseLong(deviceTypeIdArray[0]));
				deviceList = deviceManager.loadGrDeviceInfoByDeviceTypeId(Long.parseLong(deviceTypeIdArray[0]));
			}
		}

		if (deviceIdArray != null) {
			if (!deviceIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("deviceId", Long.parseLong(deviceIdArray[0]));
			}
		}
		
		if (orgIdArray != null) {
			if (!orgIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("lenderUserOrg", Long.parseLong(orgIdArray[0]));
			}
		}

		if ((inOutStorageStartTime != null)	&& !"".equals(inOutStorageStartTime)) {
			parMap.put("inOutStorageStartTime", inOutStorageStartTime);
		}

		if ((inOutStorageEndTime != null) && !"".equals(inOutStorageEndTime)) {
			parMap.put("inOutStorageEndTime", inOutStorageEndTime);
		}
		
		if ((lenderUserName != null) && !"".equals(lenderUserName)) {
			parMap.put("lenderUserName", lenderUserName.trim());
		}

		if (inoutStorageFlag != null && !"".equals(inoutStorageFlag) && !"All".equals(inoutStorageFlag)) {
			if (Integer.parseInt(inoutStorageFlag) == Constant.GR_IN_STORAGE_FLAG) {
				parMap.put("inoutStorageFlag", Constant.GR_IN_STORAGE_FLAG);
				
			}

			if (Integer.parseInt(inoutStorageFlag) == Constant.GR_OUT_STORAGE_FLAG) {
				parMap.put("inoutStorageFlag", Constant.GR_OUT_STORAGE_FLAG);
			}
		} 
		page = inOutStorageManager.getGrInOutStorageList(page, parMap);
		if (getRequest().getParameter(TableTagParameters.PARAMETER_EXPORTING) != null){
			System.out.println("su");
			List listMap = new ArrayList();
			try {
				listMap = inOutStorageManager.findGrInOutStorageList(parMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			page.setResult(listMap);
		}
        
		return SUCCESS;
	}

	/**
	 * 
	 * @title: deleteInOutStorage
	 * @description: 删除出入库信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String deleteInOutStorage() {
		try {
			long inoutStorageId = inOutStorage.getInoutStorageId();
			inOutStorageManager
					.deleteGrInOutStorageInfoByInOutStorageId(inoutStorageId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toAddInOutStorage
	 * @description: 跳转至出入库信息新增页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:56:47
	 * @throws
	 */
	public String toAddInOutStorage() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toViewInOutStorage
	 * @description: 跳转至出入库信息查看页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewInOutStorage() {
		try {
			Long inoutStorageId = Long
					.parseLong(getParameter("inOutStorageId"));
			deviceList = deviceManager.initGrDeviceList();
			inOutStorage = inOutStorageManager.loadGrInOutStorageInfoByInOutStorageId(inoutStorageId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public GrInOutStorage getInOutStorage() {
		return inOutStorage;
	}

	public void setInOutStorage(GrInOutStorage inOutStorage) {
		this.inOutStorage = inOutStorage;
	}

	public Long getInOutStorageId() {
		return inOutStorageId;
	}

	public void setInOutStorageId(Long inOutStorageId) {
		this.inOutStorageId = inOutStorageId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public InOutStorageManager getInOutStorageManager() {
		return inOutStorageManager;
	}

	public void setInOutStorageManager(InOutStorageManager inOutStorageManager) {
		this.inOutStorageManager = inOutStorageManager;
	}

	public void setInOutStorageList(List<GrInOutStorage> inOutStorageList) {
		this.inOutStorageList = inOutStorageList;
	}

	public List<GrStorage> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<GrStorage> storageList) {
		this.storageList = storageList;
	}

	public List<GrDevice> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<GrDevice> deviceList) {
		this.deviceList = deviceList;
	}

	public List<GrDeviceType> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<GrDeviceType> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public DeviceTypeManager getDeviceTypeManager() {
		return deviceTypeManager;
	}

	public void setDeviceTypeManager(DeviceTypeManager deviceTypeManager) {
		this.deviceTypeManager = deviceTypeManager;
	}

	public List<GrInOutStorage> getInOutStorageList() {
		return inOutStorageList;
	}

	public Long getLenderUserId() {
		return lenderUserId;
	}

	public void setLenderUserId(Long lenderUserId) {
		this.lenderUserId = lenderUserId;
	}

	public String getLenderUserName() {
		return lenderUserName;
	}

	public void setLenderUserName(String lenderUserName) {
		this.lenderUserName = lenderUserName;
	}

	public Long getLenderUserOrg() {
		return lenderUserOrg;
	}

	public void setLenderUserOrg(Long lenderUserOrg) {
		this.lenderUserOrg = lenderUserOrg;
	}

	public String getLenderUserOrgName() {
		return lenderUserOrgName;
	}

	public void setLenderUserOrgName(String lenderUserOrgName) {
		this.lenderUserOrgName = lenderUserOrgName;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getInOutStorageStartTime() {
		return inOutStorageStartTime;
	}

	public void setInOutStorageStartTime(String inOutStorageStartTime) {
		this.inOutStorageStartTime = inOutStorageStartTime;
	}

	public String getInOutStorageEndTime() {
		return inOutStorageEndTime;
	}

	public void setInOutStorageEndTime(String inOutStorageEndTime) {
		this.inOutStorageEndTime = inOutStorageEndTime;
	}

	public String getInoutStorageFlag() {
		return inoutStorageFlag;
	}

	public void setInoutStorageFlag(String inoutStorageFlag) {
		this.inoutStorageFlag = inoutStorageFlag;
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

	public Long getOrgIdSelect() {
		return orgIdSelect;
	}

	public void setOrgIdSelect(Long orgIdSelect) {
		this.orgIdSelect = orgIdSelect;
	}

	public List<GrDevice> getDeviceDisplayList() {
		return deviceDisplayList;
	}

	public void setDeviceDisplayList(List<GrDevice> deviceDisplayList) {
		this.deviceDisplayList = deviceDisplayList;
	}
}
