package com.cosmosource.app.personnel.service;

import java.util.HashMap;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * 
* @description: 管理局管理服务类
* @author herb
* @date May 3, 2013 4:35:49 PM
 */
public class PropertyManager extends BaseManager {
	
	/**
	 * 
	* @title: getRoomDistributeInfo 
	* @description: 查询管理局房间根本情况
	* @author herb
	* @date May 25, 2013 10:17:33 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap<String,String>> getRoomDistributeInfo(final Page<HashMap<String,String>> page) {
		return sqlDao.findPage(page, "PersonnelPropertySQL.getRoomDistributeInfo",null);
	}
	
}
