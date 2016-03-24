package com.cosmosource.app.dining.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.FsDishes;
import com.cosmosource.app.entity.FsMenu;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;

public class MenuManagementManager extends BaseManager {
	
	/**
	 * 
	* @title: saveUpdateDishes 
	* @description: 保存或修改菜肴类型信息
	* @author sqs
	* @param obj
	* @date 2013-3-19 下午09:05:19    
	* @throws
	 */
	public void saveUpdateMenu(Object obj){
		super.dao.saveOrUpdate(obj);
		//dao.flush();
	}
	
	/**
	 * 
	* @title: queryMenus 
	* @description: 查询按钮使用
	* @author hj
	* @param page
	* @param obj
	* @return
	* @date 2013-08-09
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<FsMenu> queryMenus(final Page<FsMenu> page,final Map<String,Object> obj){
		Map map = new HashMap();
		map.put("fmName", obj.get("fmName"));
		return sqlDao.findPage(page, "FoodManagementSQL.queryMenu", map);
	}
	/**
	 * 
	* @title: deleteMenus 
	* @description: 通过主键批量删除
	* @author hj
	* @param ids
	* @date 2013-8-9
	* @throws
	 */
	public String deleteMenus(String[] ids){
		String returnVal = "";
		String idsString = "";
		for (int i=0; i<ids.length; i++) {
			FsMenu fsMenu = (FsMenu)super.dao.findById(FsMenu.class, Long.parseLong(ids[i]));
			List<FsDishes> listDishes = (List<FsDishes>)super.dao.findByProperty(FsDishes.class, "fdClass", ids[i]);
			
			if (listDishes != null && listDishes.size() > 0) {
				returnVal += fsMenu.getFmName() + ",";
				continue;
			} else {
				idsString += ids[i] + ",";
			}
			
			Map map = new HashMap();
			map.put("start", fsMenu.getFmBak1());
			List<Map> list = new ArrayList<Map>();
			list.add(map);
			sqlDao.batchUpdate("FoodManagementSQL.updateMenuOrder2", list);
		}
		if (idsString.endsWith(",")) {
			idsString = StringUtil.chop(idsString);
		}
		
		super.dao.deleteByIds(FsMenu.class, idsString.split(","));
		
		if (returnVal.endsWith(",")) {
			returnVal = StringUtil.chop(returnVal);
		}
		
		return returnVal;
	}

	/**
	 * 
	* @title: updateOrderNum 
	* @description: 查询排序码
	* @author hj
	* @param fmbak1
	* @param oldOrder
	* @return
	* @date 2013-08-11
	* @throws
	 */
	public String updateOrderNum(String fmbak1, String oldOrder) {
		String orderNum = "";
		
		if (StringUtil.isBlank(oldOrder) || Long.parseLong(fmbak1) < Long.parseLong(oldOrder)) {
			Map map = new HashMap();
			List l = sqlDao.findList("FoodManagementSQL.findMaxMenuOrder", map);

			if (l == null || ((Map) l.get(0)).get("NUM") == null) {
				orderNum = "1";
			} else {
				String maxNum = ((Map) l.get(0)).get("NUM").toString();
				if (Long.parseLong(fmbak1) > Long.parseLong(maxNum)) {
					orderNum = (Long.parseLong(maxNum) + 1) + "";
				} else {
					orderNum = fmbak1;
					oldOrder = (Long.parseLong(maxNum) + 1) + "";
					map.put("start", fmbak1);
					map.put("end", oldOrder);
					List<Map> list = new ArrayList<Map>();
					list.add(map);
					sqlDao.batchUpdate("FoodManagementSQL.updateMenuOrder", list);
				}
			}
		} else if (Long.parseLong(fmbak1) == Long.parseLong(oldOrder)) {
			orderNum = fmbak1;
		} else if (Long.parseLong(fmbak1) > Long.parseLong(oldOrder)) {
			Map map = new HashMap();
			List l = sqlDao.findList("FoodManagementSQL.findMaxMenuOrder", map);

			if (l == null || ((Map) l.get(0)).get("NUM") == null) {
				orderNum = "1";
			} else {
				String maxNum = ((Map) l.get(0)).get("NUM").toString();
				if (Long.parseLong(fmbak1) >= Long.parseLong(maxNum)) {
					orderNum = Long.parseLong(maxNum) + "";
				} else {
					orderNum = fmbak1;
				}
				map.put("start", oldOrder);
				map.put("end", fmbak1);
				List<Map> list = new ArrayList<Map>();
				list.add(map);
				sqlDao.batchUpdate("FoodManagementSQL.updateMenuOrder2", list);
			}
		}
		
		return orderNum;
	}
	

}
