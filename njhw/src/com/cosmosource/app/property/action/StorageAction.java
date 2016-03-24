package com.cosmosource.app.property.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.GrDevice;
import com.cosmosource.app.entity.GrDeviceType;
import com.cosmosource.app.entity.GrStorage;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.app.property.service.DeviceManager;
import com.cosmosource.app.property.service.DeviceTypeManager;
import com.cosmosource.app.property.service.InOutStorageManager;
import com.cosmosource.app.property.service.StorageManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("unchecked")
public class StorageAction extends BaseAction {

	private Long inoutStorageId;
	private Long deviceTypeId;
	private Long deviceId;
	private String inoutStorageInDetail;
	private Long inoutStorageInNumber;
	private String inoutStorageOutDetail;
	private Long inoutStorageOutNumber;
	
	private Long userid;
	private Long orgId;
	private String orgName;
	private String outName;

	private GrStorage storage;
	private Long storageInventory;
	private Long storageId;
	private GrDevice device;
	private List<GrStorage> storageList = new ArrayList<GrStorage>();
	private List<GrDevice> deviceList = new ArrayList<GrDevice>();
	private List<GrDevice> deviceDisplayList = new ArrayList<GrDevice>();
	private List<GrDeviceType> deviceTypeList = new ArrayList<GrDeviceType>();
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private StorageManager storageManager;
	private DeviceManager deviceManager;
	private DeviceTypeManager deviceTypeManager;
	private InOutStorageManager inOutStorageManager;
	
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
	 * @title: storageIndex
	 * @description: 库存首页
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String storageIndex() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: getStorageInfoList
	 * @description: 分页得到库存信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getStorageInfoList() {
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		deviceDisplayList = deviceManager.initGrDeviceList();
		page = storageManager.getGrStorageList(page, null);
		return SUCCESS;
	}

	/**
	 * 
	 * @title: queryStorageInfoList
	 * @description: 分页得到库存信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String queryStorageInfoList() {
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		deviceDisplayList = deviceManager.initGrDeviceList();
		HashMap parMap = new HashMap();
		String[] deviceTypeIdArray = getRequest().getParameterValues(
				"deviceTypeId");
		String[] deviceIdArray = getRequest().getParameterValues("deviceId");
		if (deviceTypeIdArray != null) {
			if (!deviceTypeIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("deviceTypeId", Long.parseLong(deviceTypeIdArray[0]));
				deviceList = deviceManager.loadGrDeviceInfoByDeviceTypeId(Long.parseLong(deviceTypeIdArray[0]));
			}
		}

		if (deviceIdArray != null) {
			if (!deviceIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("deviceId", Long.parseLong(deviceIdArray[0]));
			}
		}
		
		String type = getRequest().getParameter("Z6578706f7274");
		
		if(type != null ){
//			page.setPageSize(0);
//			page = storageManager.getGrStorageList(page, parMap);
			
			List list = storageManager.getGrStorageListExport(parMap);
			
			page.setResult(list);
//			page.setTotalCount(list.size());
			
		}else{
			page = storageManager.getGrStorageList(page, parMap);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toInStorage
	 * @description: 跳转至入库页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toInStorage() {
		try {
			Long deviceId = Long.parseLong(getParameter("deviceId"));
			storage = storageManager.loadGrStorageInfoByDeviceId(deviceId);
			device = deviceManager.loadGrDeviceInfoByDeviceId(deviceId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: inStorage
	 * @description: 入库
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String inStorage() {
		try {
			// 更新库存数量
			Long storageInventoryTotal = this.storageInventory
					+ this.inoutStorageInNumber;
			storageManager.updateGrStorageInfoByStorageId(this.storageId,
					storageInventoryTotal);
			// 插入入库记录
			Integer inoutStorageFlag = Constant.GR_IN_STORAGE_FLAG;
			Timestamp inoutStorageTime = new Timestamp(System
					.currentTimeMillis());
			Long lenderUserId = this.userid;
			String lenderUserName = this.outName;
			Long lenderUserOrg = this.orgId;
			String lenderUserOrgName = this.orgName;
			Long authorUserId = Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString());
			inOutStorageManager.addGrInStorageInfo(this.deviceTypeId,this.deviceId,lenderUserId,lenderUserName,lenderUserOrg,lenderUserOrgName,
					authorUserId, inoutStorageFlag, this.inoutStorageInDetail,
					this.inoutStorageInNumber, inoutStorageTime);
			setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toInStorage
	 * @description: 跳转至出库页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toOutStorage() {
		try {
			Long deviceId = Long.parseLong(getParameter("deviceId"));
			storage = storageManager.loadGrStorageInfoByDeviceId(deviceId);
			device = deviceManager.loadGrDeviceInfoByDeviceId(deviceId);
			deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: outStorage
	 * @description: 出库
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String outStorage() {
		try {
			// 更新库存数量
			Long storageInventoryTotal = this.storageInventory
					- this.inoutStorageOutNumber;
			storageManager.updateGrStorageInfoByStorageId(this.storageId,
					storageInventoryTotal);
			// 插入出库记录
			Integer inoutStorageFlag = Constant.GR_OUT_STORAGE_FLAG;
			Timestamp inoutStorageTime = new Timestamp(System
					.currentTimeMillis());
			Long lenderUserId = this.userid;
			String lenderUserName = this.outName;
			Long lenderUserOrg = this.orgId;
			String lenderUserOrgName = this.orgName;
			Long authorUserId = Long.parseLong(Struts2Util.getSession()
					.getAttribute(Constants.USER_ID).toString());
			inOutStorageManager.addGrOutStorageInfo(this.deviceTypeId,this.deviceId,
					lenderUserId,lenderUserName,lenderUserOrg,lenderUserOrgName, authorUserId, inoutStorageFlag,
					this.inoutStorageOutDetail, this.inoutStorageOutNumber,
					inoutStorageTime);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toViewDevice
	 * @description: 跳转至库存信息查看页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewStorage() {
		try {
			Long deviceId = Long.parseLong(getParameter("deviceId"));
			storage = storageManager.loadGrStorageInfoByDeviceId(deviceId);
			device = deviceManager.loadGrDeviceInfoByDeviceId(deviceId);
			deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public GrStorage getStorage() {
		return storage;
	}

	public void setStorage(GrStorage storage) {
		this.storage = storage;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public StorageManager getStorageManager() {
		return storageManager;
	}

	public void setStorageManager(StorageManager storageManager) {
		this.storageManager = storageManager;
	}

	public void setStorageList(List<GrStorage> storageList) {
		this.storageList = storageList;
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

	public List<GrStorage> getStorageList() {
		return storageList;
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

	public GrDevice getDevice() {
		return device;
	}

	public void setDevice(GrDevice device) {
		this.device = device;
	}

	public Long getInoutStorageId() {
		return inoutStorageId;
	}

	public void setInoutStorageId(Long inoutStorageId) {
		this.inoutStorageId = inoutStorageId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getInoutStorageInDetail() {
		return inoutStorageInDetail;
	}

	public void setInoutStorageInDetail(String inoutStorageInDetail) {
		this.inoutStorageInDetail = inoutStorageInDetail;
	}

	public Long getInoutStorageInNumber() {
		return inoutStorageInNumber;
	}

	public void setInoutStorageInNumber(Long inoutStorageInNumber) {
		this.inoutStorageInNumber = inoutStorageInNumber;
	}

	public String getInoutStorageOutDetail() {
		return inoutStorageOutDetail;
	}

	public void setInoutStorageOutDetail(String inoutStorageOutDetail) {
		this.inoutStorageOutDetail = inoutStorageOutDetail;
	}

	public Long getInoutStorageOutNumber() {
		return inoutStorageOutNumber;
	}

	public void setInoutStorageOutNumber(Long inoutStorageOutNumber) {
		this.inoutStorageOutNumber = inoutStorageOutNumber;
	}

	public Long getStorageInventory() {
		return storageInventory;
	}

	public void setStorageInventory(Long storageInventory) {
		this.storageInventory = storageInventory;
	}

	public InOutStorageManager getInOutStorageManager() {
		return inOutStorageManager;
	}

	public void setInOutStorageManager(InOutStorageManager inOutStorageManager) {
		this.inOutStorageManager = inOutStorageManager;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
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

	public List<GrDevice> getDeviceDisplayList() {
		return deviceDisplayList;
	}

	public void setDeviceDisplayList(List<GrDevice> deviceDisplayList) {
		this.deviceDisplayList = deviceDisplayList;
	}
}
