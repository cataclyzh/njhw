package com.cosmosource.app.personnel.service;

import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Org;
import com.cosmosource.base.service.BaseManager;

/**
 * 
 * @description: 机构管理类
 * @author SQS
 * @date 2013-3-17 下午06:32:02
 */
public class DepRoomAssignmentsManager extends BaseManager {
	/**
	 * 
	* @title: 
	* @description: 初始化物业房间分配页面--下拉框取得机构名称
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Org> findOrgName(){
		List list = dao.findByHQL(" from Org o where o.levelNum = ?", Org.LEVELNUM_2);
		return list;
	}
	
	/**
	 * 
	 * @title: 
	 * @description: 初始化物业房间分配页面--下拉框取得已分配的房间
	 * @author SQS
	 * @return
	 * @throws Exception
	 * @date 2013-3-17 下午06:27:26     
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<EmOrgRes> findAssignedOrgRoom(Map<String,String> map){
		return sqlDao.findList("PersonnelSQL.selectOrgRoom", map);
	}
}