package com.cosmosource.app.port.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * ISE MAC门控制接口
 * 
 * @author ptero
 * 
 */
@WebService(targetNamespace = "http://service.fnjhw.com/")
public interface OpenDoorOnlyMacService {

	/**
	 * ISE门控制接口
	 * 
	 * @param macAddress
	 * @return reponse XML
	 */
	@WebMethod
	public String openDoorOnlyMac(
			@WebParam(name = "macAddress", mode = WebParam.Mode.IN) String macAddress);

}