package com.cosmosource.app.personnel.action;

import com.cosmosource.base.action.BaseAction;

/** 
* @description: 委办局管理员机构人员管理
* @author herb
* @date 2013-03-23
*/ 
public class UnitOrgUserAction extends BaseAction<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9167370822309871516L;
	private String orgId;
	private String userId;

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 
	* @title: index 
	* @description: 用户管理导航，实现与.net交互
	*               1.type:org,admin,user三种
	*				2.id：ORG_ID,USER_ID
	*				3.ope:add[新增],select[查询]
	* @author herb
	* @return
	* @date May 6, 2013 11:14:33 AM     
	* @throws
	 */
	public String index() {
		String type = getParameter("type");
		String id = getParameter("id");
		String ope = getParameter("ope");
		if  (null != type && type.trim().equals("org")){//机构管理查询:显示人员列表和机构列表
			orgId = id;
			return "orgUserList";
		} else if (ope != null && !ope.trim().equals("add") && null != type && (type.trim().equals("admin")||type.trim().equals("user"))) {//人员修改
			userId = id;
			return "userEdit";
		} else if (ope != null && ope.trim().equals("add")) {//人员添加
			orgId = id;
			return "userAdd";
		}
		return SUCCESS;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
