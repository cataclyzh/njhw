package com.cosmosource.app.dining.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.dining.service.NewTheMenuManager;
import com.cosmosource.app.entity.FsDishesIssue;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.Struts2Util;



@SuppressWarnings("serial")
public class addOrUpdateNewTheMenuAction extends BaseAction<FsDishesIssue> {

	private FsDishesIssue fsDishesIssue = new FsDishesIssue();
	private NewTheMenuManager newTheMenuManager = new NewTheMenuManager();
	@Override
	protected void prepareModel() throws Exception {
	}
	@Override
	public FsDishesIssue getModel() {
		return fsDishesIssue;
	}
	
	/**
	 * 
	* @title: initNewTheMenu 
	* @description: 初始化录入页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	public String initNewTheMenu(){
		try {
			List<HashMap> list = new ArrayList();
			HashMap localMap =  (HashMap) ConvertUtils.pojoToMap(fsDishesIssue);
			list = newTheMenuManager.queryNewTheMenu(list,localMap);
			ArrayList<ArrayList<HashMap>> typeList = new ArrayList<ArrayList<HashMap>>(21);
			for (int i = 0 ;i<21;i++) {
				typeList.add(new ArrayList<HashMap>());
			}
			//typeList.add(e);
			for(int i=0;i<list.size();i++){
				HashMap map = list.get(i);
				if (map.get("FDI_TYPE").equals("1") && map.get("FDI_FLAG").equals("1")){
					typeList.get(0).add(map);
				}
				if (map.get("FDI_TYPE").equals("1") && map.get("FDI_FLAG").equals("2")){
					typeList.get(1).add(map);
				}
				if (map.get("FDI_TYPE").equals("1") && map.get("FDI_FLAG").equals("3")){
					typeList.get(2).add(map);
				}
				if (map.get("FDI_TYPE").equals("2") && map.get("FDI_FLAG").equals("1")){
					typeList.get(3).add(map);
				}
				if (map.get("FDI_TYPE").equals("2") && map.get("FDI_FLAG").equals("2")){
					typeList.get(4).add(map);
				}
				if (map.get("FDI_TYPE").equals("2") && map.get("FDI_FLAG").equals("3")){
					typeList.get(5).add(map);
				}
				if (map.get("FDI_TYPE").equals("3") && map.get("FDI_FLAG").equals("1")){
					typeList.get(6).add(map);
				}
				if (map.get("FDI_TYPE").equals("3") && map.get("FDI_FLAG").equals("2")){
					typeList.get(7).add(map);
				}
				if (map.get("FDI_TYPE").equals("3") && map.get("FDI_FLAG").equals("3")){
					typeList.get(8).add(map);
				}
				if (map.get("FDI_TYPE").equals("4") && map.get("FDI_FLAG").equals("1")){
					typeList.get(9).add(map);
				}
				if (map.get("FDI_TYPE").equals("4") && map.get("FDI_FLAG").equals("2")){
					typeList.get(10).add(map);
				}
				if (map.get("FDI_TYPE").equals("4") && map.get("FDI_FLAG").equals("3")){
					typeList.get(11).add(map);
				}
				if (map.get("FDI_TYPE").equals("5") && map.get("FDI_FLAG").equals("1")){
					typeList.get(12).add(map);
				}
				if (map.get("FDI_TYPE").equals("5") && map.get("FDI_FLAG").equals("2")){
					typeList.get(13).add(map);
				}
				if (map.get("FDI_TYPE").equals("5") && map.get("FDI_FLAG").equals("3")){
					typeList.get(14).add(map);
				}
				if (map.get("FDI_TYPE").equals("6") && map.get("FDI_FLAG").equals("1")){
					typeList.get(15).add(map);
				}
				if (map.get("FDI_TYPE").equals("6") && map.get("FDI_FLAG").equals("2")){
					typeList.get(16).add(map);
				}
				if (map.get("FDI_TYPE").equals("6") && map.get("FDI_FLAG").equals("3")){
					typeList.get(17).add(map);
				}
				if (map.get("FDI_TYPE").equals("7") && map.get("FDI_FLAG").equals("1")){
					typeList.get(18).add(map);
				}
				if (map.get("FDI_TYPE").equals("7") && map.get("FDI_FLAG").equals("2")){
					typeList.get(19).add(map);
				}
				if (map.get("FDI_TYPE").equals("7") && map.get("FDI_FLAG").equals("3")){
					typeList.get(20).add(map);
				}
			}
			this.getRequest().setAttribute("typeList", typeList);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public FsDishesIssue getFsDishesIssue() {
		return fsDishesIssue;
	}
	public void setFsDishesIssue(FsDishesIssue fsDishesIssue) {
		this.fsDishesIssue = fsDishesIssue;
	}
	public NewTheMenuManager getNewTheMenuManager() {
		return newTheMenuManager;
	}
	public void setNewTheMenuManager(NewTheMenuManager newTheMenuManager) {
		this.newTheMenuManager = newTheMenuManager;
	}
	
}
