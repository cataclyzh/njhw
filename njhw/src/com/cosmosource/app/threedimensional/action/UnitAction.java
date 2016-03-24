package com.cosmosource.app.threedimensional.action;

import com.cosmosource.app.threedimensional.service.UnitManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings("unchecked")
public class UnitAction extends BaseAction {

	private static final long serialVersionUID = 1533830008983298926L;
	private UnitManager unitManager;

	public String getOrgId() {
		String orgNname = getParameter("orgName");
		try {
			JSONObject json = new JSONObject();
			String orgId = unitManager.getOrgIdByOrgName(orgNname);
			json.put("orgId", orgId);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public UnitManager getUnitManager() {
		return unitManager;
	}

	public void setUnitManager(UnitManager unitManager) {
		this.unitManager = unitManager;
	}

}
