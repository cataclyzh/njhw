package com.cosmosource.app.property.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrDeviceType;
import com.cosmosource.app.property.service.DeviceManager;
import com.cosmosource.app.property.service.DeviceTypeManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/**
 * 
 * @description: 设备类型管理页面
 * @author tangtq
 * @date 2013-7-10 14:03:32
 */
@SuppressWarnings("unchecked")
public class DeviceTypeAction extends BaseAction {
	private GrDeviceType deviceType;
	private long deviceTypeId;
	private String deviceTypeNo;
	private String deviceTypeName;
	private String deviceType_No;
	private String deviceType_Name;
	public String getDeviceType_No() {
		return deviceType_No;
	}

	public void setDeviceType_No(String deviceTypeNo) {
		deviceType_No = deviceTypeNo;
	}

	public String getDeviceType_Name() {
		return deviceType_Name;
	}

	public void setDeviceType_Name(String deviceTypeName) {
		deviceType_Name = deviceTypeName;
	}

	private String deviceTypeDescribe;
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
	 * @title: getDeviceTypeInfoList
	 * @description: 分页得到设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String getDeviceTypeInfoList() throws Exception {
		
		
		page = deviceTypeManager.getGrDeviceTypeList(page, null);
		return SUCCESS;
	}
	public String deviceTypeListForQuery() throws Exception {
		
		String deviceTypeNo=getRequest().getParameter("deviceType_No");
		String deviceTypeName=getRequest().getParameter("deviceType_Name");
		Map map=new HashMap();
		
		if(deviceTypeNo!=null){
			map.put("deviceTypeNo", deviceTypeNo.trim());
		}
		if(deviceTypeName!=null){
			map.put("deviceTypeName", deviceTypeName.trim());
		}
		page = deviceTypeManager.getGrDeviceTypeList(page, map);
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 
	 * @title: addDeviceType
	 * @description: 增加设备类型
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String addDeviceType() {
		try {
			String deviceTypeNo = null;
			if(this.deviceTypeNo!=null){
				deviceTypeNo=this.deviceTypeNo.trim();
			}
			String deviceTypeName = null;
            if(this.deviceTypeName!=null){
        	  deviceTypeName=this.deviceTypeName.trim();
            }
            String deviceTypeDescribe =null ;
			if(this.deviceTypeDescribe!=null){
				deviceTypeDescribe=this.deviceTypeDescribe.trim();
			}
			
			
			deviceTypeManager.addGrDeviceTypeInfo(deviceTypeNo,deviceTypeName,
					deviceTypeDescribe);
		
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: editDeviceType
	 * @description: 编辑设备类型
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String editDeviceType() {
		try {
			String deviceTypeNo = null;
			if(this.deviceTypeNo!=null){
				deviceTypeNo=this.deviceTypeNo.trim();
			}
			String deviceTypeName = null;
            if(this.deviceTypeName!=null){
        	  deviceTypeName=this.deviceTypeName.trim();
            }
            String deviceTypeDescribe =null ;
			if(this.deviceTypeDescribe!=null){
				deviceTypeDescribe=this.deviceTypeDescribe.trim();
			}

			deviceTypeManager.updateGrDeviceTypeInfoByDeviceTypeId(
					this.deviceTypeId,deviceTypeNo,deviceTypeName,
					deviceTypeDescribe);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: deleteDeviceType
	 * @description: 删除设备类型
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String deleteDeviceType() {
		try {
			Long deviceTypeId = Long.parseLong(getParameter("deviceTypeId"));
			deviceTypeManager.deleteGrDeviceTypeInfoByDeviceTypeId(deviceTypeId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: getDeviceCountByDeviceTypeId
	 * @description: 根据设备类型查询设备数量
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String getDeviceCountByDeviceTypeId(){
		JSONObject json = new JSONObject();
		Long deviceTypeId = Long.parseLong(getParameter("deviceTypeId"));
		int deviceCountByDeviceTypeId = deviceManager.loadGrDeviceCountInfoByDeviceTypeId(deviceTypeId);
		try {
			json.put("deviceCountByDeviceTypeId", deviceCountByDeviceTypeId);
		} catch (JSONException e) {
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8","no-cache:true");
		return null;	
	}

	/**
	 * 
	 * @title: toAddDeviceType
	 * @description: 跳转至设备类型新增页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:56:47
	 * @throws
	 */
	public String toAddDeviceType() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toEditDeviceType
	 * @description: 跳转至设备类型修改页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toEditDeviceType() {
		try {
			Long deviceTypeId = Long.parseLong(String
					.valueOf(getParameter("deviceTypeId")));
			deviceType = deviceTypeManager
					.loadGrDeviceTypeInfoByDeviceTypeId(deviceTypeId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toViewDeviceType
	 * @description: 跳转至设备类型查看页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 17:06:35
	 * @throws
	 */
	public String toViewDeviceType() {
		try {
			Long deviceTypeId = Long.parseLong(String
					.valueOf(getParameter("deviceTypeId")));
			deviceType = deviceTypeManager
					.loadGrDeviceTypeInfoByDeviceTypeId(deviceTypeId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	
	public String checkDeviceType() throws JSONException{
		JSONObject json = new JSONObject();
		String deviceTypeNo = Struts2Util.getParameter("deviceTypeNo").trim();
		String deviceTypeName = Struts2Util.getParameter("deviceTypeName").trim();
		HashMap map = new HashMap();
		map.put("deviceTypeNo", deviceTypeNo);
		map.put("deviceTypeName", deviceTypeName);
		page = deviceTypeManager.checkDeviceType(page, map);
		if(page.getResult().size()!=0){
			json.put("status",0);
			json.put("message", "设备类型编号或名称已存在!");
		}else{
			json.put("status",1);

		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}
	
	public String checkDeviceTypeModify() throws JSONException{
		JSONObject json = new JSONObject();
		String deviceTypeNo = Struts2Util.getParameter("deviceTypeNo").trim();
		String deviceTypeName = Struts2Util.getParameter("deviceTypeName").trim();
		String deviceTypeId = Struts2Util.getParameter("deviceTypeId");

		HashMap map = new HashMap();
		map.put("deviceTypeNo", deviceTypeNo);
		map.put("deviceTypeName", deviceTypeName);
		map.put("deviceTypeId", deviceTypeId);

		page = deviceTypeManager.checkDeviceTypeModify(page, map);
		if(page.getResult().size()!=0){
			json.put("status",0);
			json.put("message", "设备类型编号或名称已存在!");
		}else{
			json.put("status",1);

		}

		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}
	
	
	
	
	
	public GrDeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(GrDeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public DeviceTypeManager getDeviceTypeManager() {
		return deviceTypeManager;
	}

	public void setDeviceTypeManager(DeviceTypeManager deviceTypeManager) {
		this.deviceTypeManager = deviceTypeManager;
	}

	public List<GrDeviceType> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<GrDeviceType> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getDeviceTypeDescribe() {
		return deviceTypeDescribe;
	}

	public void setDeviceTypeDescribe(String deviceTypeDescribe) {
		this.deviceTypeDescribe = deviceTypeDescribe;
	}

	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public String getDeviceTypeNo() {
		return deviceTypeNo;
	}

	public void setDeviceTypeNo(String deviceTypeNo) {
		this.deviceTypeNo = deviceTypeNo;
	}
}
