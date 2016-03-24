package com.cosmosource.app.property.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrDevice;
import com.cosmosource.app.entity.GrDeviceType;
import com.cosmosource.app.property.service.DeviceManager;
import com.cosmosource.app.property.service.DeviceTypeManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings("unchecked")
public class DeviceAction extends BaseAction {

	private GrDevice device;
	private Long deviceId;
	private String deviceNo;
	private String device_No;
	private Long deviceTypeId;
	private Long deviceType_Id;
	private String deviceName;
	private String device_Name;
	private String deviceDescribe;
	private Date deviceProduceTime;
	private Date deviceBuyTime;
	private Long deviceWarrantyTime;
	private String deviceCompany;
	private String deviceSequence;
	private List<GrDevice> deviceList = new ArrayList<GrDevice>();
	private List<GrDeviceType> deviceTypeList = new ArrayList<GrDeviceType>();
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private DeviceManager deviceManager;
	private DeviceTypeManager deviceTypeManager;

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
	 * @title: getDeviceInfoList
	 * @description: 分页得到设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getDeviceInfoList() {
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		page = deviceManager.getGrDeviceList(page, null);
		return SUCCESS;
	}

	public String deviceListForQuery() {
		Long deviceTypeId=null;

		if(0==Long.parseLong(getRequest().getParameter("deviceType_Id"))){
		deviceTypeId=null;
		}else{
	    deviceTypeId=Long.parseLong(getRequest().getParameter("deviceType_Id"));
		}
		String deviceNo=getRequest().getParameter("device_No");
		String deviceName=getRequest().getParameter("device_Name");
		Map map=new HashMap();
		if (deviceTypeId != null){
		    map.put("deviceTypeId", deviceTypeId);
		}
		if (deviceNo != null && !"".equals(deviceNo)){
			map.put("deviceNo", deviceNo.trim());
		}
		
		if (deviceName != null && !"".equals(deviceName)){
			map.put("deviceName", deviceName.trim());
		}
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		page = deviceManager.getGrDeviceList(page, map);
		return SUCCESS;
	}
	
	/**批量删除出入库设备
	 * 开发者：ywl
	 * 时间：2013-9-23
	 */
	public String inventoryDelete(){
		String idStr = getRequest().getParameter("idStr");
		String[] idStrs = idStr.split(",");
		try {
			if(idStrs != null && idStrs.length > 0){
				//idStr 可为数组形式 进行批量删除
				//删除之前先查询出该设备是否已经被关联
				List list = new ArrayList();
				for(int i=0;i<idStrs.length;i++){
					Map pMap = new HashMap();
					pMap.put("deviceTypeId", idStrs[i]);
					list.add(pMap);
				}
				
				deviceManager.deleteDeviceById(list);
				
				Struts2Util.renderText("删除设备成功！","");
				/**
				ist<Map> listUse = deviceManager.findListBySql("PropertySQL.queryInventory", pMap); //查询出选中的id所关联的数据
				List<Map> listById = new ArrayList();
				if(listUse.size() > 0 && listUse != null){
					for(int i=0;i<idStrs.length;i++){
						for(int j=0;j<listUse.size();j++){
							//该判断是否存在，如果存在记录存在的ID 用来返回页面给用户提示,不存在的则删除掉
							if(idStrs[i].equals(listUse.get(j).get("DEVICE_TYPE_ID"))){
								//把已有关联的id储存到list中
								Map newMap = new HashMap();
								newMap.put("deviceIds", idStrs[i]);
								listById.add(newMap);
							}else{
								Map trueMap = new HashMap();
								trueMap.put("deviceTypeId", Integer.parseInt(idStrs[i]));
								flag = deviceManager.deleteBySql("PropertySQL.inventoryDelete", trueMap);
							}
						}
					}
				}else{
					for(int i=0;i<idStrs.length;i++){
						Map pm = new HashMap();
						pm.put("deviceTypeId", Integer.parseInt(idStrs[i]));
						flag = deviceManager.deleteBySql("PropertySQL.inventoryDelete", pm);
					}
					
				}
				List<Map> listInfo = new ArrayList();//保存已关联的id所查询出来的数据
				if(listById.size() > 0 && listById != null){
					//将list里的id 拼接成数组放到sql中查询
					String listId = "";
					for(int i=0;i<listById.size();i++){
						if(listId == ""){
							listId = listById.get(i).get("deviceIds").toString();
						}else{
							listId += "," + listById.get(i).get("deviceIds").toString();
						}
					}
					Map mapId = new HashMap();
					mapId.put("deviceTypeIds", listId);
					listInfo = deviceManager.findListBySql("PropertySQL.queryInventory", mapId); //查询出选中的id所关联的数据
				}
				
				// !=0 删除成功！ 0 删除失败
				if(flag > 0){
					if(listInfo.size() > 0 && listInfo != null){
						//保存没有被删除的名称，用于给用户提示！
						String names = "";
						for(int i=0;i<listInfo.size();i++){
							if(names == ""){
								names = listInfo.get(i).get("DEVICE_TYPE_NAME").toString();
							}else{
								names += "," + listInfo.get(i).get("DEVICE_TYPE_NAME").toString();
							}
							
						}
						Struts2Util.renderText("名称为：" + names + "的设备删除时失败，已被使用，请先删除被关联的数据！","");
					}else{
						Struts2Util.renderText("删除设备成功！","");
					}
				}else{
					Struts2Util.renderText("删除设备失败！","");
				}
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Util.renderText("删除设备失败！","");
		}
		return null;
	}
	
	/**
	 * 
	 * @title: getDeviceListByDeviceTypeId
	 * @description: 根据设备类型ID获取设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getDeviceListByDeviceTypeId() {
		JSONObject json = new JSONObject();
		Long deviceTypeId = Long.parseLong(getParameter("deviceTypeId"));
		List<GrDevice> deviceList = deviceManager
				.loadGrDeviceInfoByDeviceTypeId(deviceTypeId);
		List<Map> mapList = new ArrayList<Map>();
		for (int i = 0; i < deviceList.size(); i++) {
			Map map = new HashMap();
			map.put("DEVICE_ID", deviceList.get(i).getDeviceId());
			map.put("DEVICE_NAME", deviceList.get(i).getDeviceName());
			mapList.add(map);
		}
		try {
			json.put("mapList", mapList);
		} catch (JSONException e) {
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 
	 * @title: addDevice
	 * @description: 增加设备
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String addDevice() {
		try {
			String deviceNo = null;
			if(this.deviceNo!=null){
				deviceNo=this.deviceNo.trim();
			}
			String deviceName=null;
			if(this.deviceName!=null){
				deviceName=this.deviceName.trim();
			}
			String deviceDescribe=null;
			if(this.deviceDescribe!=null){
				deviceDescribe=this.deviceDescribe.trim();
			}
		    String deviceCompany=null;
		    if(this.deviceCompany!=null){
		    	deviceCompany=this.deviceCompany.trim();
		    }
			String deviceSequence=null;
			if(this.deviceSequence!=null){
				deviceSequence=this.deviceSequence.trim();
			}
			
			Timestamp deviceProduceTimeStamp = null;
			Timestamp deviceBuyTimeStamp = null;
			if (this.deviceProduceTime != null){
				deviceProduceTimeStamp = new Timestamp(this.deviceProduceTime.getTime());
            }
			
			if (this.deviceBuyTime != null){
				deviceBuyTimeStamp = new Timestamp(this.deviceBuyTime.getTime());
            }
			Long deviceWarrantyTime;
			if(this.deviceWarrantyTime!=null){
				deviceWarrantyTime=this.deviceWarrantyTime;
			}else{
				deviceWarrantyTime=0L;
			}
			
		
			deviceManager.addGrDeviceInfo(this.deviceTypeId,deviceNo,deviceName,deviceDescribe, deviceProduceTimeStamp, deviceBuyTimeStamp,deviceWarrantyTime,deviceCompany,deviceSequence);
			setIsSuc("true");
		} catch (Exception e) {
            e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: editDevice
	 * @description: 编辑设备
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String editDevice() {
		try {
			String deviceNo=null;
			if(this.deviceNo!=null){
				deviceNo=this.deviceNo.trim();
			}
			String deviceName=null;
			if(this.deviceName!=null){
				deviceName=this.deviceName.trim();
			}
			String deviceDescribe=null;
			if(this.deviceDescribe!=null){
				deviceDescribe=this.deviceDescribe.trim();
			}
			String deviceCompany=null;
			if(this.deviceCompany!=null){
				deviceCompany=this.deviceCompany.trim();
			}
			String deviceSequence=null;
			if(this.deviceSequence!=null){
				deviceSequence=this.deviceSequence.trim();
			}
			
			
			
			
			
			Timestamp deviceProduceTimeStamp = null;
			Timestamp deviceBuyTimeStamp = null;
			if (this.deviceProduceTime != null) {
               deviceProduceTimeStamp = new Timestamp(this.deviceProduceTime.getTime());
			}

			if (this.deviceBuyTime != null) {
               deviceBuyTimeStamp = new Timestamp(this.deviceBuyTime.getTime());
			}
			
			Long deviceWarrantyTime;
			if(this.deviceWarrantyTime!=null){
				deviceWarrantyTime=this.deviceWarrantyTime;
			}else{
				deviceWarrantyTime=0L;
			}		
			deviceManager.updateGrDeviceInfoByDeviceId(this.deviceId,
					deviceNo, this.deviceTypeId,deviceName,
					deviceDescribe, deviceProduceTimeStamp, deviceBuyTimeStamp,
					deviceWarrantyTime,deviceCompany,
					deviceSequence);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: deleteDevice
	 * @description: 删除设备
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String deleteDevice() {
		try {
			long deviceId = device.getDeviceId();
			deviceManager.deleteGrDeviceInfoByDeviceId(deviceId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toAddDevice
	 * @description: 跳转至设备新增页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:56:47
	 * @throws
	 */
	public String toAddDevice() {
		deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
		getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd"));
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toEditDevice
	 * @description: 跳转至设备修改页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toEditDevice() {
		try {
			deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
			Long deviceId = Long.parseLong(String
					.valueOf(getParameter("deviceId")));
			device = deviceManager.loadGrDeviceInfoByDeviceId(deviceId);
			getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd"));

			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toViewDevice
	 * @description: 跳转至设备查看页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewDevice() {
		try {
			deviceTypeList = deviceTypeManager.initGrDeviceTypeList();
			Long deviceId = Long.parseLong(String
					.valueOf(getParameter("deviceId")));
			device = deviceManager.loadGrDeviceInfoByDeviceId(deviceId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	
	public String checkDevice() throws JSONException{
		JSONObject json = new JSONObject();
		String deviceNo = Struts2Util.getParameter("deviceNo").trim();
		String deviceName = Struts2Util.getParameter("deviceName").trim();
		HashMap map = new HashMap();
		map.put("deviceNo", deviceNo);
		map.put("deviceName", deviceName);
		page = deviceManager.checkDevice(page, map);
		if(page.getResult().size()!=0){
			json.put("status",0);
			json.put("message", "设备编号或名称已存在!");
		}else{
			json.put("status",1);

		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}
	
	public String checkDeviceModify() throws JSONException{
		JSONObject json = new JSONObject();
		String deviceNo = Struts2Util.getParameter("deviceNo").trim();
		String deviceName = Struts2Util.getParameter("deviceName").trim();
		String deviceId = Struts2Util.getParameter("deviceId");

		HashMap map = new HashMap();
		map.put("deviceNo", deviceNo);
		map.put("deviceName", deviceName);
		map.put("deviceId", deviceId);

		page = deviceManager.checkDeviceModify(page, map);
		if(page.getResult().size()!=0){
			json.put("status",0);
			json.put("message", "设备编号或名称已存在!");
		}else{
			json.put("status",1);

		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}
	
	
	
	
	
	public GrDevice getDevice() {
		return device;
	}

	public void setDevice(GrDevice device) {
		this.device = device;
	}

	public Long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
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

	public DeviceTypeManager getDeviceTypeManager() {
		return deviceTypeManager;
	}

	public void setDeviceTypeManager(DeviceTypeManager deviceTypeManager) {
		this.deviceTypeManager = deviceTypeManager;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceDescribe() {
		return deviceDescribe;
	}

	public void setDeviceDescribe(String deviceDescribe) {
		this.deviceDescribe = deviceDescribe;
	}

	public Date getDeviceProduceTime() {
		return deviceProduceTime;
	}

	public void setDeviceProduceTime(Date deviceProduceTime) {
		this.deviceProduceTime = deviceProduceTime;
	}

	public Date getDeviceBuyTime() {
		return deviceBuyTime;
	}

	public void setDeviceBuyTime(Date deviceBuyTime) {
		this.deviceBuyTime = deviceBuyTime;
	}

	public Long getDeviceWarrantyTime() {
		return deviceWarrantyTime;
	}

	public void setDeviceWarrantyTime(Long deviceWarrantyTime) {
		this.deviceWarrantyTime = deviceWarrantyTime;
	}

	public String getDeviceCompany() {
		return deviceCompany;
	}

	public void setDeviceCompany(String deviceCompany) {
		this.deviceCompany = deviceCompany;
	}

	public String getDeviceSequence() {
		return deviceSequence;
	}

	public void setDeviceSequence(String deviceSequence) {
		this.deviceSequence = deviceSequence;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDevice_No() {
		return device_No;
	}

	public void setDevice_No(String deviceNo) {
		device_No = deviceNo;
	}

	public Long getDeviceType_Id() {
		return deviceType_Id;
	}

	public void setDeviceType_Id(Long deviceTypeId) {
		deviceType_Id = deviceTypeId;
	}

	public String getDevice_Name() {
		return device_Name;
	}

	public void setDevice_Name(String deviceName) {
		device_Name = deviceName;
	}
}
