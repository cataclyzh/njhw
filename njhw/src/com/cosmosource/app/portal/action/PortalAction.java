package com.cosmosource.app.portal.action;

import com.cosmosource.app.entity.Users;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.common.service.AuthorityManager;
/**
 * @description 导航临时action
 * @author herb
 *
 */
@SuppressWarnings("unchecked")
public class PortalAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3751081925102171205L;
	private AuthorityManager authorityManager;
	
	public AuthorityManager getAuthorityManager() {
		return authorityManager;
	}

	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}
	
	/*
	 * 到导航页面
	 */
	public String portal(){
		return SUCCESS;
	}
	
	/*
	 * 楼宇导航页面
	 */
	public String building(){
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: index
	* @description: 到平台主页
	* @author herb
	* @return
	* @date Mar 21, 2013 11:58:07 AM     
	* @throws
	 */
	public String index() {
		//首页 用户名与登录名
		String loginname = (String)this.getSession().getAttribute(Constants.LOGIN_NAME);
		String userName = (String)this.getSession().getAttribute(Constants.USER_NAME);
		Users user = (Users)authorityManager.findUserByLoginName(loginname);
		String username = userName + " (" + loginname + ") "; 
		getRequest().setAttribute("username", username);
		getRequest().setAttribute("loginname", loginname);
		getRequest().setAttribute("loginpwd", user.getLoginPwd());		
		
		return SUCCESS;
	}

}
