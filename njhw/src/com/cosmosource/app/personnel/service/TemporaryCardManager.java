package com.cosmosource.app.personnel.service;

import java.util.HashMap;
import java.util.Map;

import com.cosmosource.app.entity.TcTempcardRfid;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

public class TemporaryCardManager extends BaseManager {
	
	/**
	 * 
	* @title: saveUpdateIpTel 
	* @description: 保存或修改临时卡信息
	* @author sqs
	* @param obj
	* @date 2013-3-19 下午09:05:19    
	* @throws
	 */
	public void saveUpdateTeCard(Object obj){
		super.dao.saveOrUpdate(obj);
	}
	
	/**
	 * 
	* @title: queryTcTempcard 
	* @description: 查询按钮使用
	* @author sqs
	* @param page
	* @param obj
	* @return
	* @date 2013-3-19 下午09:23:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<TcTempcardRfid> queryTcTempcard(final Page<TcTempcardRfid> page,final Map<String,Object> obj){
		Map map = new HashMap();
		map.put("cardId", obj.get("cardId"));
		map.put("rfid", obj.get("rfid"));
		return sqlDao.findPage(page, "PersonnelSQL.queryTcTempcard", map);
	}
	/**
	 * 
	* @title: deleteUsers 
	* @description: 通过主键批量删除
	* @author sqs
	* @param ids
	* @date 2013-3-19 下午06:15:25     
	* @throws
	 */
	public void deleteTemporaryCard(String[] ids){
		super.dao.deleteByIdsForString(TcTempcardRfid.class, ids);
	}
}
