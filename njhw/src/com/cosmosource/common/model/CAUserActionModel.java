package com.cosmosource.common.model;

public class CAUserActionModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 292503210141786582L;
	private String loginname;
	private String actioncode;
	private String[] actioncodes;
	private String actionname;
	private String isuseca;
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getActioncode() {
		return actioncode;
	}
	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}
	public String getActionname() {
		return actionname;
	}
	public void setActionname(String actionname) {
		this.actionname = actionname;
	}
	public String getIsuseca() {
		return isuseca;
	}
	public void setIsuseca(String isuseca) {
		this.isuseca = isuseca;
	}
	public String[] getActioncodes() {
		return actioncodes;
	}
	public void setActioncodes(String[] actioncodes) {
		this.actioncodes = actioncodes;
	}
}
