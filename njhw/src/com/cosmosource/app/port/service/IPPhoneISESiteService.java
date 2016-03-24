package com.cosmosource.app.port.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * ISE门控制接口
 * 
 * @author ptero
 * 
 */
@WebService(targetNamespace = "http://service.fnjhw.com/")
public interface IPPhoneISESiteService {

	/**
	 * ISE门控制接口
	 * 
	 * @param macAddress
	 * @return reponse XML
	 */
	@WebMethod
	public String controlDoor(
			@WebParam(name = "macAddress", mode = WebParam.Mode.IN) String macAddress);

	/**
	 * ISE门控制接口
	 * 
	 * @return
	 */
	@WebMethod
	public String checkDoorAuth();

	/**
	 * ISE门控制接口
	 */
	@WebMethod
	public void checkCommAndDoorStatus();
}