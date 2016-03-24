package com.cosmosource.common.model;

public class CaapplyModel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1313475069788697476L;
	
	private Long caid;
	
	private String applyno;
	
	private String stepcode;
	
	private String type;
	
	private Long applynum;

	public Long getCaid() {
		return caid;
	}

	public void setCaid(Long caid) {
		this.caid = caid;
	}

	public String getApplyno() {
		return applyno;
	}

	public void setApplyno(String applyno) {
		this.applyno = applyno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStepcode() {
		return stepcode;
	}

	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
	}

	public Long getApplynum() {
		return applynum;
	}

	public void setApplynum(Long applynum) {
		this.applynum = applynum;
	}
	
}
