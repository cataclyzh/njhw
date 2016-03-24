package com.cosmosource.app.energyint.service;

import java.util.List;

import com.cosmosource.base.service.BaseManager;
/** 
* @description: 大厦能耗管理主页
* @author sqs
* @date 2013-03-31
*/
@SuppressWarnings("unchecked")
public class EnergyManageManager extends BaseManager {
	/** 
	* @title: loadSWData
	* @description: 加载三维数据
	* @author zh
	* @date 2013-04-21
	*/ 
	public List loadSWData(String energy, long orgId) {
		List list = null;
		if ("耗水量".equals(energy))  list = sqlDao.findList("EnergyintSQL.loadSWData_01", null);	// 耗水量
		if ("耗电量".equals(energy))  list = sqlDao.findList("EnergyintSQL.loadSWData_02", null);	// 耗电量
		System.out.println(list);
		return list;
	}
}
