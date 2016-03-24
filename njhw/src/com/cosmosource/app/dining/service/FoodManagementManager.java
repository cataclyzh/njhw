package com.cosmosource.app.dining.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import com.cosmosource.app.entity.FsDishes;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;

public class FoodManagementManager extends BaseManager {
	
	/**
	 * 
	* @title: saveUpdateDishes 
	* @description: 保存或修改菜肴信息
	* @author sqs
	* @param obj
	* @date 2013-3-19 下午09:05:19    
	* @throws
	 */
	public void saveUpdateDishes(Object obj){
		super.dao.saveOrUpdate(obj);
		//dao.flush();
	}
	
	/**
	 * 
	* @title: queryDishes 
	* @description: 查询按钮使用
	* @author sqs
	* @param page
	* @param obj
	* @return
	* @date 2013-3-19 下午09:23:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<FsDishes> queryDishes(final Page<FsDishes> page,final Map<String,Object> obj){
		Map map = new HashMap();
		map.put("fdName", obj.get("fdName"));
		map.put("fdClass", obj.get("fdClass"));
		if (obj.get("selectArr") != null && StringUtil.isNotBlank(obj.get("selectArr").toString())) {
			map.put("fdIds", obj.get("selectArr").toString());
		}
		return sqlDao.findPage(page, "FoodManagementSQL.queryDishes", map);
	}
	/**
	 * 
	* @title: deleteDishes 
	* @description: 通过主键批量删除
	* @author sqs
	* @param ids
	* @date 2013-3-19 下午06:15:25     
	* @throws
	 */
	public void deleteDishes(String[] ids){
		super.dao.deleteByIds(FsDishes.class, ids);
	}

	/**
	 * 
	* @title: getFsClassMap 
	* @description: 查询菜单类别
	* @author hj
	* @return
	* @date 2013-8-11
	* @throws
	 */
	public Map<String, String> getFsClassMap() {
		Map<String, String> fsClassMap = new LinkedHashMap<String, String>();
		
		Map condition = new HashMap();
		List<Map> fsClassList = sqlDao.findList("FoodManagementSQL.getFsClass", condition);
		
		if (fsClassList != null && fsClassList.size() > 0) {
			for (Map fsClass: fsClassList) {
				if (fsClass.get("FM_ID") != null && fsClass.get("FM_NAME") != null) {
					fsClassMap.put(fsClass.get("FM_ID").toString(), fsClass.get("FM_NAME").toString());
				}
			}
		}
		
		return fsClassMap;
	}

	/**
	 * 
	* @title: getSelectDish 
	* @description: 查询选中的菜单
	* @author hj
	* @return
	* @date 2013-8-13
	* @throws
	 */
	public String getSelectDish(String fdiType, String fdiFlag) {
		String returnVal = "";
		Map condition = new HashMap();
		condition.put("fdiType", fdiType);
		condition.put("fdiFlag", fdiFlag);
		List<Map> fsClassList = sqlDao.findList("FoodManagementSQL.getSelectDish", condition);
		
		if (fsClassList != null && fsClassList.size() > 0) {
			for (Map m : fsClassList) {
				if (m.get("FD_ID") != null) {
					returnVal += m.get("FD_ID").toString() + ",";
				}
			}
			if (returnVal.endsWith(",")) {
				returnVal = StringUtil.chop(returnVal);
			}
		}
		return returnVal;
	}

	/**
	 * 
	* @title: queryPreviewDishes 
	* @description: 查询预览菜单
	* @author hj
	* @return
	* @date 2013-8-13
	* @throws
	 */
	public List queryPreviewDishes(String fdIds) {
		Map condition = new HashMap();
		condition.put("fdIds", fdIds);
		List<Map> list = sqlDao.findList("FoodManagementSQL.getPreviewDish", condition);
		return list;
	}
	

}
