package com.cosmosource.app.test.action;

import com.cosmosource.app.port.serviceimpl.GatesControlToAppService;
import com.cosmosource.base.action.BaseAction;

@SuppressWarnings({ "serial", "unchecked" })
public class myGatesAction extends BaseAction {

	private  String  gatesNo; // 闸机号
	private  String state; //状态
	private GatesControlToAppService  gatesControlToAppService;
	
	private String result;
	
	
	public String init() {
		return INIT;
	}
	
public String getGatesNo() {
		return gatesNo;
	}

	public void setGatesNo(String gatesNo) {
		this.gatesNo = gatesNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

public String  controlGates() throws Exception {

		
	    String  gatesNo = getRequest().getParameter("gatesNo");
	    String  state = getRequest().getParameter("state");
	    System.out.println("打印输出的数值：");
	    System.out.println(gatesNo+"\t");
	    System.out.println(state +"\t");
		
	    result =  gatesControlToAppService.controlGates(gatesNo, state, "qiangge");
	
	  
	   return    SUCCESS;
	   
		  
	}
	
	
	
	public GatesControlToAppService getGatesControlToAppService() {
		return gatesControlToAppService;
	}

	public void setGatesControlToAppService(
			GatesControlToAppService gatesControlToAppService) {
		this.gatesControlToAppService = gatesControlToAppService;
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
