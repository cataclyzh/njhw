package com.cosmosource.app.transfer.serviceimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * @ClassName:WisdomNjToSystemService
 * @Description：智慧南京----外部系统的操作
 * @Author：hp 
 * @Date:2013-3-27
 */
public class WisdomNjToSystemService {

	public static final String ENVIRONMENT = "environment"; // 周边环境
	public static final String TRAFFIC = "traffic"; // 道路信息
	public static final String WEATHER = "weather"; // 天气信息

	/**
	* @Description：得到智慧南京天气、周边环境信息
	* @Author：hp
	* @Date：2013-3-27
	* @param methodType  方法类型
	* @param fields      参数 ----  多个参数用逗号隔开
	* @return
	* @throws RemoteException
	* @throws MalformedURLException
	* @throws ServiceException
	**/
	public String getWisdomNjWeatherInfo(String methodType,String fields) throws RemoteException, MalformedURLException, ServiceException {
		String method = null;
		if (methodType.equals(WisdomNjToSystemService.WEATHER)) {
			method = com.cosmosource.base.util.Constants.DBMAP.get("WS_WISDOMNJWEATHER_METHOD");
		}
		if (methodType.equals(WisdomNjToSystemService.ENVIRONMENT)) {
			method = com.cosmosource.base.util.Constants.DBMAP.get("WS_WISDOMNJENVIROMENT_METHOD");
		}
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(com.cosmosource.base.util.Constants.DBMAP.get("WS_WISDOMNJWE_URL")));
		call.addParameter("areaIds", Constants.XSD_STRING, ParameterMode.IN);
		call.setReturnType(Constants.XSD_STRING);
		call.setOperationName(method);
		String xmlStr = call.invoke(new Object[] {fields}).toString();
		return xmlStr;
	}

	
	
	/**
	* @Description：得到周边交通情况信息
	* @Author：hp
	* @Date：2013-3-27
	* @param fields      参数-----多个参数用逗号隔开
	* @return
	* @throws RemoteException
	* @throws MalformedURLException
	* @throws ServiceException
	**/
	public String getWisdomNjTrafficInfo(String fields) throws RemoteException, MalformedURLException, ServiceException {
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(com.cosmosource.base.util.Constants.DBMAP.get("WS_WISDOMNJTRAFFIC_URL")));
		call.addParameter("roadIds", Constants.XSD_STRING, ParameterMode.IN);
		call.setReturnType(Constants.XSD_STRING);
		call.setOperationName(com.cosmosource.base.util.Constants.DBMAP.get("WS_WISDOMNJTRAFFIC_METHOD"));
		String xmlStr = call.invoke(new Object[] { fields }).toString();
		return xmlStr;
	}
	

}
