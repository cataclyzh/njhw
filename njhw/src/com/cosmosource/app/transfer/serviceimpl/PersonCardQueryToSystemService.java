package com.cosmosource.app.transfer.serviceimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @ClassName:PersonCardQueryToSystemService
 * @Description：市民卡---外部系统的服务操作
 * @Author：hp 
 * @Date:2013-3-27
 */
public class PersonCardQueryToSystemService {

	private static final Log log = LogFactory.getLog(PersonCardQueryToSystemService.class);
	/**
	* @Description： 查询市民卡信息
	* @Author：hp
	* @Date：2013-3-27
	* @param cards  市民卡号
	* @return
	* @throws RemoteException
	* @throws MalformedURLException
	* @throws ServiceException
	**/
	public String queryPersonCardInfo(String cards) throws RemoteException, MalformedURLException, ServiceException{
		log.debug("调用市民卡接口，查询市民卡信息 ----"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setOperationName(new QName(com.cosmosource.base.util.Constants.DBMAP.get("WS_PERSONNALCARD_NAMESPACE"),
				com.cosmosource.base.util.Constants.DBMAP.get("WS_PERSONNALCARD_METHOD")));
		call.setTargetEndpointAddress(new URL(com.cosmosource.base.util.Constants.DBMAP.get("WS_PERSONNALCARD_URL")));
		call.addParameter("pubpara", Constants.XSD_STRING, ParameterMode.IN);
		call.setReturnType(Constants.XSD_STRING);
		String xmlStr = call.invoke(new Object[] {cards}).toString();
		return xmlStr;
	}
	
	
	/**
	* @Description：根据市民卡号查询对应的图片
	* @Author：hp
	* @Date：2013-4-18
	* @param cards
	* @return
	* @throws RemoteException
	* @throws MalformedURLException
	* @throws ServiceException
	**/
	public byte[] queryPersonCardMap(String cards) throws RemoteException, MalformedURLException, ServiceException{
		log.info("调用市民卡接口，市民卡号查询对应的图片 ----"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(com.cosmosource.base.util.Constants.DBMAP.get("WS_PERSONNALCARD_URL")));
		call.setOperationName(new QName(com.cosmosource.base.util.Constants.DBMAP.get("WS_PERSONNALCARD_NAMESPACE"),com.cosmosource.base.util.Constants.DBMAP.get("WS_PERSONNALMAP_METHOD")));
		call.addParameter("pubpara", Constants.XSD_STRING, ParameterMode.IN);
		call.setReturnType(Constants.SOAP_BASE64BINARY);
		byte[] xmlStr = (byte[])call.invoke(new Object[] {cards});
		return xmlStr;
	}
	
	
	
}
