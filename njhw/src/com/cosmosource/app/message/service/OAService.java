package com.cosmosource.app.message.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.base.util.DateUtil;

public class OAService {
	
	private static final Log log = LogFactory.getLog(OAService.class);

	//默认参数设置,配置在config.properties文件中
	private String targetUrl = "http://59.201.3.11/NJOAWebservice/OAWebService.asmx";
	private String operationName = "http://tempuri.org/";
	
	private String maxNum = "100";
	
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public long getShouWenNum(String name){
		return oaShouWen(name).size();
	}
	
	public long getFaWenNum(String name){
		return oaFaWen(name).size();
	}
	
	public List<Map<String,String>> oaShouWen(String name){
		String result = sendRemoteRequest(name, "GetUnHandleList_ShouWen");
		List<Map<String,String>> list = backWorkXmlForValue(result);
		return list;
	}
	
	public List<Map<String,String>> oaFaWen(String name){
		String result = sendRemoteRequest(name, "GetUnHandleList_FaWen");
		List<Map<String,String>> list = backWorkXmlForValue(result);
		return list;	
	}
	
	public String sendRemoteRequest(String name, String funcName){
		String strxml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" 
				+ "<paras><UserID>"+name+"</UserID><KeyWord></KeyWord><CurrentDateTime>"
				+ DateUtil.getSysDate().toString() 
				+ "</CurrentDateTime>"
				+"<PageSize>"+maxNum+"</PageSize>" 
				+ "<HandleType>0</HandleType>"
				+ "<CurrentPageIndex>2</CurrentPageIndex>"
				+ "</paras>";		
		
		Service service = new Service();
		String result="";
	     try { 
			Call call = ( Call ) service.createCall();
			call.setTargetEndpointAddress(new URL(targetUrl));
			call.setOperationName(new QName(operationName,funcName));
			call.addParameter(new QName(operationName,"ParasXml"), 
								org.apache.axis.encoding.XMLType.XSD_STRING,
								javax.xml.rpc.ParameterMode.IN); 
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); 
			call.setUseSOAPAction(true); 
			call.setSOAPActionURI(operationName+funcName);
			result=(String) call.invoke(new Object[]{strxml});
	     } catch (Exception e) {   
	    	log.info(e);
			//e.printStackTrace();
	     } 
		return result;
	}
	
	public static void main(String[] args) {
		OAService w = new OAService();
		List<Map<String,String>> list = w.oaShouWen("zhouying");
		
		
		for(int i=0; i<list.size(); i++){
			Map<String,String> m = list.get(i);
			System.out.println("==================== " + (i+1));
			Iterator it = m.entrySet().iterator(); 
			while(it.hasNext()){
				Entry e = (Entry) it.next();
				System.out.println(e.getKey() + " : " + e.getValue());
			}
		}
	}
	
	public static List<Map<String,String>>  backWorkXmlForValue(String xml){
		List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		Iterator<Element> iter=null;
		try {
			Document document =  DocumentHelper.parseText(xml);
			Element rootElt = document.getRootElement(); // 获取根节点
			Element data= rootElt.element("DATA"); // 获取根节点下的子节点
			Element returnInfo = data.element("ReturnInfo");
			Element status = returnInfo.element("Status");
			//Element description = returnInfo.element("Description");
			if("True".equals(status.getText())){
				Element userArea = data.element("UserArea");
				Element handleList = userArea.element("HandleList");
				iter = handleList.elementIterator("Handle");
				while (iter.hasNext()) {
					Map<String,String> map = new HashMap<String,String>();
					Element e = (Element) iter.next();
					map.put("MESSAGEITEMGUID", e.elementTextTrim("MessageItemGuid"));
					map.put("LASTFILEDATE", e.elementTextTrim("LastFileDate"));
					map.put("TITLE", e.elementTextTrim("Title"));
					map.put("ARCHIVENO", e.elementTextTrim("ArchiveNo"));
					map.put("FROMDISPNAME", e.elementTextTrim("FromDispName"));
					map.put("GENERATEDATE", e.elementTextTrim("GenerateDate"));
					lists.add(map);
				 }
			}
		} catch (DocumentException e) {
			//e.printStackTrace();
			log.info(e);
		}
		return lists;
	}
}
