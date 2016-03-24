package com.cosmosource.app.personnel.action;

import java.util.HashMap;

import com.cosmosource.app.personnel.service.PropertyManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;

/**
 * 
* @description: 管理局人员功能
* @author herb
* @date May 25, 2013 9:03:52 PM
 */
@SuppressWarnings("unchecked")
public class PropertyAction extends BaseAction {

	private Page<HashMap<String,String>> page = new Page<HashMap<String,String>>(50);//默认每页20条记录
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PropertyManager propertyManager;

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 
	* @title: init
	* @description: 初始化
	* @author hj
	* @date 2013-08-07
	*/ 
	public String init() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: authorityRoomDistribute 
	* @description: 物业房间分配
	* @author herb
	* @return
	* @date May 25, 2013 9:12:04 PM     
	* @throws
	 */
	public String authorityRoomDistribute() {
		page=propertyManager.getRoomDistributeInfo(page);
		return SUCCESS;
	}

	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(PropertyManager propertyManager) {
		this.propertyManager = propertyManager;
	}

	public Page<HashMap<String, String>> getPage() {
		return page;
	}

	public void setPage(Page<HashMap<String, String>> page) {
		this.page = page;
	}
}
