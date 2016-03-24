package com.cosmosource.app.personnel.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.integrateservice.service.IntegrateManager;
import com.cosmosource.app.personnel.model.CusServiceCountInfo;
import com.cosmosource.app.personnel.service.CusServiceManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Struts2Util;

/**
 * @description: 物业客服人员首页
 * @author herb
 * @date 2013-03-23
 */
public class CusServiceIndexAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6680076522371166415L;

	private CusServiceManager cusServiceManager;
	private IntegrateManager integrateManager;

	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public Object getModel() {
		return null;
	}

	/**
	 * 
	 * @title: index
	 * @description: 初始化首页
	 * @author herb
	 * @return
	 * @date May 3, 2013 3:41:13 PM
	 * @throws
	 */
	public String index() {
		try {
			// 大厦统计信息
			refreshCountInfo();
			// 公告信息
			List<HashMap> resultList = integrateManager.queryMsgBoard();
			List<HashMap> limitMapList = new ArrayList<HashMap>();
			if (null != resultList && resultList.size() > 5) {// 只显示5条
				for (int i = 0; i < 5; i++) {
					limitMapList.add(resultList.get(i));
				}
			} else {
				limitMapList = resultList;
			}

			// 失物招领
			loadCmsLostFound();

			getRequest().setAttribute("msgBoardList", limitMapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: loadCmsLostFound
	 * @description: cms 失物招领
	 * @author qyq
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void loadCmsLostFound() {
		try {
		// 失物招领
		List<HashMap> resultList = cusServiceManager.loadCmsLostFound();
		List<HashMap> limitMapList = new ArrayList<HashMap>();
		if (null != resultList && resultList.size() > 4) {// 只显示4条
			for (int i = 0; i < 4; i++) {
				limitMapList.add(resultList.get(i));
			}
		} else {
			limitMapList = resultList;
		}
		getRequest().setAttribute("cmsLostFoundList", limitMapList);
		getRequest().setAttribute("lostFoundTotal", resultList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 失物认领 (新版功能 首页展示专用)
	 * @开发者：ywl
	 * @时间：2013-7-13
	 * @return null
	 */
	public String queryArticleJSON(){
		try {
			// 失物招领
			List<HashMap> resultList = cusServiceManager.loadCmsLostFoundAll();
			List<HashMap> limitMapList = new ArrayList<HashMap>(); 
			if (null != resultList && resultList.size() > 4) {// 只显示4条
				for (int i = 0; i < 4; i++) {
					limitMapList.add(resultList.get(i));
				}
			} else {
				limitMapList = resultList;
			}
			getRequest().setAttribute("cmsLostFoundList", limitMapList);
			getRequest().setAttribute("lostFoundTotal", resultList.size());
			
			for(Map m :limitMapList){
				m.put("CONTENT","");
			}
			Struts2Util.renderJson(limitMapList, "encoding:UTF-8", "no-cache:true");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public String refreshWorkSheet() {
		try {
		// 申请类型
		String appType = this.getRequest().getParameter("appType");
		// 查询未处理的物业报修列表
		List<Map> sheetList = cusServiceManager.queryUntreatedSheet(appType);
		
		List<Map> limitMap = new ArrayList<Map>();
		if (null != sheetList && sheetList.size() > 5) {// 只显示5条
			for (int i = 0; i < 5; i++) {
				limitMap.add(sheetList.get(i));
			}
		} else {
			limitMap = sheetList;
		}
		this.getRequest().setAttribute("sheetList", limitMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: countInfo
	 * @description:
	 * @author 刷新大厦统计信息
	 * @return
	 * @date May 10, 2013 5:56:20 PM
	 * @throws
	 */
	public void refreshCountInfo() {
		CusServiceCountInfo info = new CusServiceCountInfo();

		List<Map> unitList = cusServiceManager.findUnitList();
		if (null != unitList)
			info.setInnerUnit(unitList.size());

		int innerUser = cusServiceManager.selectOrgUserCount(null);
		info.setInnerUser(innerUser);

		int sumVisitor = cusServiceManager.selectVisitCount();
		info.setSumVisitor(sumVisitor);

		this.getRequest().setAttribute("info", info);

	}

	public CusServiceManager getCusServiceManager() {
		return cusServiceManager;
	}

	public void setCusServiceManager(CusServiceManager cusServiceManager) {
		this.cusServiceManager = cusServiceManager;
	}

	public IntegrateManager getIntegrateManager() {
		return integrateManager;
	}

	public void setIntegrateManager(IntegrateManager integrateManager) {
		this.integrateManager = integrateManager;
	}
}
