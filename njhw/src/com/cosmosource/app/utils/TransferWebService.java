package com.cosmosource.app.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.port.serviceimpl.IPPhoneSiteToAppService;

/** 
* @description: 调用外部的接口
* @author cjw
* @date 2013-5-8 上午09:57:04     
*/ 
public class TransferWebService {
	
	private static final  Log log = LogFactory.getLog(TransferWebService.class);
	
	/** 
	* @title: transferIPPhoneService 
	* @description: 调用ip电话的接口 （供下推使用）
	* @author cjw
	* @param url
	* @param method
	* @param xml
	* @return
	* @date 2013-5-8 上午10:00:34     
	* @throws 
	*/ 
	public static String transferIPPhoneService(String url,String method,String xml){
		String result="";
		try {
		String targetEendPoint = url;
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setOperationName(new QName(targetEendPoint,method));
		call.setTargetEndpointAddress(new URL(targetEendPoint));
		result = (String) call.invoke(new Object[] { xml });//SEP1CAA07100B49
		} catch (ServiceException e) {
			log.info("拨打电话"+e);
			e.printStackTrace();
		}catch (MalformedURLException e) {
			log.info("拨打电话"+e);
			e.printStackTrace();
		}catch (RemoteException e) {
			log.info("拨打电话"+e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/** 
	* @title: parseStringXml 
	* @description: 解析xml文件
	* @author cjw
	* @param xml
	* @return
	* @date 2013-5-8 下午10:20:51     
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public static Iterator<Element> parseStringXml(String xml){
		Iterator<Element> iter=null;
		try {
			Document document =  DocumentHelper.parseText(xml);
			Element rootElt = document.getRootElement(); // 获取根节点
			iter = rootElt.elementIterator("param"); // 获取根节点下的子节点
//			while (iter.hasNext()) {
//				 Element e = (Element) iter.next();
//				 System.err.println(e.elementTextTrim("mac"));
//			 }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return iter;
	}
}
