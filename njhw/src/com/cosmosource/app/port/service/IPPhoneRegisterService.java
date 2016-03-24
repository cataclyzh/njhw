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
public interface IPPhoneRegisterService {

	/**
	 * ISE门控制接口
	 * 
	 * @param macAddress
	 * @return reponse XML
	 */
	@WebMethod
	public String onlineAlert(
			@WebParam(name = "macAddress", mode = WebParam.Mode.IN) String macAddress);

}