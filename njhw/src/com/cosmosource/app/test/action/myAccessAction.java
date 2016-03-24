package com.cosmosource.app.test.action;

import com.cosmosource.app.port.serviceimpl.AccessControlToAppService;
import com.cosmosource.base.action.BaseAction;

@SuppressWarnings({ "serial", "unchecked" })
public class myAccessAction extends BaseAction {
	
	    private  String  accessNO; // 门禁号
		private  String state; //状态
		private AccessControlToAppService  accessControlToAppService;
		private String result;
		
      public String init() {
		return INIT;
	}
	
	
	public AccessControlToAppService getAccessControlToAppService() {
		return accessControlToAppService;
	}


	public void setAccessControlToAppService(
			AccessControlToAppService accessControlToAppService) {
		this.accessControlToAppService = accessControlToAppService;
	}


	public String getAccessNO() {
		return accessNO;
	}


	public void setAccessNO(String accessNO) {
		this.accessNO = accessNO;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String controlAccess() throws Exception {

		
	    String  accessNO = getRequest().getParameter("accessNO");
	    String  state = getRequest().getParameter("state");
	    System.out.println("打印输出的数值：");
	    System.out.println(accessNO+"\t");
	    System.out.println(state +"\t");
		
	 
	  result =  accessControlToAppService.controlAccess(accessNO, state, "qq");
	  
	   return    SUCCESS;
	   
		  
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


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}

}
