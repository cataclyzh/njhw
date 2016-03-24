package com.cosmosource.common.action;


import java.io.PrintWriter;



import com.cosmosource.base.action.BaseAction;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

import com.opensymphony.webwork.ServletActionContext;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class validationcaptchaAction extends BaseAction {

	/**
	 * 验证验证码
	 */
	private static final long serialVersionUID = -1344325884742104232L;
	private CaptchaService captchaService;

	public CaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	private String j_captcha;

	public String getJ_captcha() {
		return j_captcha;
	}

	public void setJ_captcha(String jCaptcha) {
		j_captcha = jCaptcha;
	}

	public void validationcaptcha() {
	
		try{			
		//System.out.println(j_captcha);
	    Boolean isResponseCorrect = Boolean.TRUE;	 
		String captchaID = this.getRequest().getSession().getId();
		PrintWriter out = this.getResponse().getWriter();
	    isResponseCorrect = captchaService.validateResponseForID(captchaID,
			j_captcha);

		if (isResponseCorrect) {

			out.print("true");

		} else {
	
		    	out.print("false"); 
	
		}
		out.flush();
    	out.close();
	
}
		catch(Exception e){
	
			System.out.print(e);
		}
	

	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	}
		
	

