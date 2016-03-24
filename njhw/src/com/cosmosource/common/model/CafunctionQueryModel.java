package com.cosmosource.common.model;

public class CafunctionQueryModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4728766987549597437L;
	//功能名称
	private String actionname;
	//功能描述
	private String actiondesc;
	//功能编码
	private String actioncode;

	public String getActionname() {
		return actionname;
	}
	public void setActionname(String actionname) {
		this.actionname = actionname;
	}
	public String getActiondesc() {
		return actiondesc;
	}
	public void setActiondesc(String actiondesc) {
		this.actiondesc = actiondesc;
	}
	public String getActioncode() {
		return actioncode;
	}
	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}
	
}


