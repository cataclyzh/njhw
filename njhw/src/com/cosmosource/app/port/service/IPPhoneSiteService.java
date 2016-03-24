package com.cosmosource.app.port.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/** 
* @description: 政务系统与IP电话内部接口实现
* @author cjw
* @date 2013-5-2 下午12:02:42     
*/ 
@WebService(targetNamespace="http://service.fnjhw.com/")
public interface IPPhoneSiteService {
	
	/**
	* @Description：开门
	* @Date：2013-4-18
	* @param xmlStr 参数报文：xml字符串
	* @return
	* @throws Exception
	**/
	@WebMethod
	public String controlDoor( @WebParam(name="xml",mode=WebParam.Mode.IN) String xml);
	
	@WebMethod
	public String controlDoor_wx( @WebParam(name="xml",mode=WebParam.Mode.IN) String xml);
	
	/**
	 * 
	* @Title: checkDoorAuth  
	* @Description: 定时检查门锁锁内存储地址
	* @author HJ
	* @date 2013-7-28
	* @param     
	* @return void 
	* @throws
	 */
	@WebMethod
	public String checkDoorAuth();	
	
	/**
	 * 
	* @Title: checkDoorAuth  
	* @Description: 定时检查通信机门锁连接状态
	* @author HJ
	* @date 2013-7-31
	* @param     
	* @return void 
	* @throws
	 */
	@WebMethod
	public void checkCommAndDoorStatus();	
}