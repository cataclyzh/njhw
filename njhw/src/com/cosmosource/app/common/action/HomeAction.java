/**
 * <p>文件名: HomeAction.java</p>
 * <p>描述：首页Action</p>
 * <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
 * <p>公司: Holytax Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间： 2011-10-27 上午10:50:24 
 * @作者：sjy
 * @版本： V1.0
 * <p>类修改者	jtm	 修改日期	2012-02-07		修改说明 
 * 更新首页呈现方式
 * </p>   
 * 
 */
package com.cosmosource.app.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.app.common.model.NavigationModel;
import com.cosmosource.app.common.model.TaskInfoModel;
import com.cosmosource.app.common.service.HomeManager;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;

/**
 * @类描述: 首页Action
 * @作者： WXJ
 */
public class HomeAction extends BaseAction<Object> {

	private static final long serialVersionUID = 4227875753301925460L;
	
	private String navigationCfgPath;
	
	private HomeManager homeManager;
	
	private DevicePermissionToAppService devicePermissionToApp;
	
	private int messageCount;
	
	private int bulletinCount;
	
	private int knowledgeCount;

	/**
	 * @描述:初始化页面
	 * @作者：WXJ
	 * @日期：2011-9-27
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception {
		return INIT;
	}
	
	/**
	 * @描述:获取快捷按钮列表
	 * @作者：WXJ
	 * @日期：2012-4-17
	 * @return
	 * @throws Exception
	 */
	public String loadShortcutButtonList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter loadShortcutButtonList...");
		}
		String navigationPath = getServletContext().getRealPath(navigationCfgPath);
		List<NavigationModel> navigationList = homeManager.getNavigationList(getMenuAction(),navigationPath);
		Struts2Util.renderJson(JsonUtil.beanToJson(navigationList), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * @描述:获取工作清单列表
	 * @作者：WXJ
	 * @日期：2012-4-17
	 * @return
	 * @throws Exception
	 */
	public String loadTaskList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter loadTaskList...");
		}
		List<TaskInfoModel> taskList = homeManager.getTaskList(getSession(), getMenuActionNew());
		Struts2Util.renderJson(JsonUtil.beanToJson(taskList), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/**
	 * 
	* @Title: getMenuActionNew 
	* @Description: 获得当前用户所有级别的权限菜单
	* @author WXJ
	* @date 2013-3-28 下午03:25:37 
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getMenuActionNew(){
		List<Objtank> objList = devicePermissionToApp.getAuthAllMenuCollection(getSession().getAttribute(Constants.USER_ID).toString());
	
		StringBuffer menuactionBuffer = new StringBuffer("");
		for (Objtank obj : objList) {
			menuactionBuffer.append(obj.getLink());
		}
		return menuactionBuffer.toString();
	}
	
	/**
	 * @描述:获得权限字符串，用来权限判断
	 * @作者：jtm
	 * @日期：2012-02-14
	 */
	@SuppressWarnings("unchecked")
	public String getMenuAction(){
		
		List<String> menus = (List<String>)getSession().getAttribute(Constants.ROLES_MENUS);
		StringBuffer menuactionBuffer = new StringBuffer("");
		for (String menu : menus) {
			menuactionBuffer.append(menu);
		}
		return menuactionBuffer.toString();
	}
	/**
	 * @描述:查询知识列表
	 * @作者：WXJ
	 * @日期：2012-04-23
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String knowledgeList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter knowledgeList...");
		}
		
		try{
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("state", "2");
			List knowledgeList = homeManager.homeKnowledgeList(knowledgeCount, paraMap);
			Struts2Util.renderJson(JsonUtil.beanToJson(knowledgeList), "encoding:UTF-8", "no-cache:true");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public String messageList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter messageList...");
		}
		
		try{
			String loginName = (String)getSession().getAttribute(Constants.LOGIN_NAME);
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("receiver", loginName);
			paraMap.put("status", "0");
			List messageList = homeManager.findMsgByNotRead(messageCount, paraMap);
			Struts2Util.renderJson(JsonUtil.beanToJson(messageList), "encoding:UTF-8", "no-cache:true");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	public String bulletinList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter bulletinList...");
		}
		
		try{
			String company = (String)getSession().getAttribute(Constants.COMPANY);
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("company", company);
			List bulletinList = homeManager.findBulletin(bulletinCount, paraMap);
			Struts2Util.renderJson(JsonUtil.beanToJson(bulletinList), "encoding:UTF-8", "no-cache:true");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	public void setNavigationCfgPath(String navigationCfgPath) {
		this.navigationCfgPath = navigationCfgPath;
	}

	public HomeManager getHomeManager() {
		return homeManager;
	}

	public void setHomeManager(HomeManager homeManager) {
		this.homeManager = homeManager;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public void setBulletinCount(int bulletinCount) {
		this.bulletinCount = bulletinCount;
	}

	public void setKnowledgeCount(int knowledgeCount) {
		this.knowledgeCount = knowledgeCount;
	}
	
	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}

	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}
}
