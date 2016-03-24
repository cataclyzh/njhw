package com.cosmosource.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcCaaction;
import com.cosmosource.common.entity.TAcCauseraction;
import com.cosmosource.common.model.CAUserActionModel;
import com.cosmosource.common.service.CAMgrManager;

public class CaUserActionAction extends BaseAction<TAcCauseraction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2846589845509840250L;
	private TAcCauseraction entity = new TAcCauseraction();
	private Page<?> page = new Page(Constants.PAGESIZE);
	private CAUserActionModel userActionModel = new CAUserActionModel();

	private CAMgrManager caMgrManager;
	private List<TAcCaaction> actionList = new ArrayList<TAcCaaction>();
	private String[] _chk;
	
	public TAcCauseraction getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		Long causeractionid = entity.getCauseractionid();
		if (causeractionid != null) {
			entity = (TAcCauseraction)caMgrManager.findById(TAcCauseraction.class, causeractionid);
		} else {
			entity = new TAcCauseraction();
		}
	}
	
	public String init() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter init...");
		}
		
		return INIT;
	}
	
	public String edit() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter edit...");
		}
		
		return INPUT;
	}
	
	public String input() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter input...");
		}
		
		return INPUT;
	}
	
	public String update() {
		if(logger.isDebugEnabled()){
			logger.debug("enter update...");
		}
		try {
			caMgrManager.update(entity);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error("ca用户操作修改异常", e);
			setIsSuc("false");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 判断用户是否已经包含操作
	 * @param savedUserActionList
	 * @param actioncode
	 * @return
	 * @throws Exception
	 */
	private boolean isUserActionSaved(List<TAcCauseraction> savedUserActionList, String actioncode) throws Exception{
		if(savedUserActionList == null || savedUserActionList.isEmpty()){
			return false;
		}
		
		for(TAcCauseraction userAction : savedUserActionList){
			if(userAction.getActioncode().equals(actioncode)){
				return true;
			}
		}
		
		return false;
	}
	
	public String save() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter save ...");
		}
		String loginname = userActionModel.getLoginname();
		String isuseca = userActionModel.getIsuseca();
		String[] actioncodes = userActionModel.getActioncodes();
		logger.info("loginname: " + loginname);
		logger.info("isuseca: " + isuseca);
		logger.info("actioncodes: " + actioncodes);
		List<TAcCauseraction> savedUserActionList = caMgrManager.findCaUserActionByLoginname(loginname);
		if(actioncodes != null && actioncodes.length > 0){
			List<TAcCauseraction> userActionList = new ArrayList<TAcCauseraction>();
			for(int i = 0; i < actioncodes.length; i++){
				if(this.isUserActionSaved(savedUserActionList, actioncodes[i])){
					continue;
				}
				TAcCauseraction userAction = new TAcCauseraction();
				userAction.setLoginname(loginname);
				userAction.setIsuseca(isuseca);
				userAction.setActioncode(actioncodes[i]);
				logger.info("actioncode:" + actioncodes[i]);
				userActionList.add(userAction);
			}
			try{
				caMgrManager.saveCaUserActions(userActionList);
				setIsSuc("true");
			}
			catch(Exception e){
				setIsSuc("false");
				logger.error(e.getMessage(), e);
			}
		}
	
		return SUCCESS;
	}
	
	public String query() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter query...");
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();
		
		page = caMgrManager.findCaUserActionList(page, userActionModel, paraMap);
		
		return LIST;
	}
	
	public String deleteCaUserAction() {
		if(logger.isDebugEnabled()){
			logger.debug("enter delete caUserAction...");
		}
		String message = null;
		if(_chk != null && _chk.length>0){
			logger.info("CA用户操作功能数量:" + _chk.length);
			try{
				caMgrManager.deleteCaUserAction(_chk);
				message = "CA用户操作功能已删除成功";		
			}catch(Exception e){
				message = "CA用户操作功能删除失败";
			}
			this.addActionMessage(message);
		}
		else {
			logger.info("未获得选中CA用户操作功能");
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();
		page = caMgrManager.findCaUserActionList(page, userActionModel, paraMap);
		
		return SUCCESS;
	}
	
	public String getUserNotHaveActionList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter getUserNotHaveActionList...");
		}
		String loginname = getParameter("loginname");
		logger.info("loginname:" + loginname);
		List<?> actionList = caMgrManager.findUserNotHaveActionList(loginname);
		Struts2Util.renderJson(JsonUtil.beanToJson(actionList), "encoding:UTF-8", "no-cache:true");
		
		return null;
	}
	
	public String isUserExist() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter isUserExist...");
		}
		String loginname = getParameter("loginname");
		
		boolean result = caMgrManager.isUserExist(loginname);
		Struts2Util.renderText(String.valueOf(result));
		
		return null;
	}
		
	//--------------------------------service
	
	public void setCaMgrManager(CAMgrManager caMgrManager) {
		this.caMgrManager = caMgrManager;
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public CAUserActionModel getUserActionModel() {
		return userActionModel;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] _chk) {
		this._chk = _chk;
	}

	public List<TAcCaaction> getActionList() {
		return actionList;
	}

	public void setActionList(List<TAcCaaction> actionList) {
		this.actionList = actionList;
	}

}
