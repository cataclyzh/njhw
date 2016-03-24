package com.cosmosource.app.test.action;

import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.action.BaseAction;

@SuppressWarnings({ "serial", "unchecked" })
public class myDoorAction extends BaseAction {

	
	private  String  myHome; // 房间号
	private  String state; //状态
	private  DoorControlToAppService doorControlToAppService;
	private String result;



	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}


	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}


	public String getState() {
		return state;
	}


	public String getMyHome() {
		return myHome;
	}


	public void setMyHome(String myHome) {
		this.myHome = myHome;
	}


	public void setState(String state) {
		this.state = state;
	}

	public String init() {
		return INIT;
	}
	
	
	public String  controlDoor() throws Exception {

		
	    String  myHomeId = getRequest().getParameter("myHome");
	    String  state = getRequest().getParameter("state");
	    System.out.println("打印输出的数值：");
	    System.out.println(myHomeId+"\t");
	    System.out.println(state +"\t");
		
//	     result=  doorControlToAppService.controlDoor(myHomeId,state, "xiaoqiang");
	   
	  
	   return    SUCCESS;
	   
		  
	}
	

public String queryDoorStatus() throws Exception {

		
	    String  myHomeId = getRequest().getParameter("myHome");
	    String  state = getRequest().getParameter("state");
	    System.out.println("打印输出的数值：");
	    System.out.println(myHomeId+"\t");
	    System.out.println(state +"\t");
//	    result = doorControlToAppService.queryDoorStatus(myHomeId, state);
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
