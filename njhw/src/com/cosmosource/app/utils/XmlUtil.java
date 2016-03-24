package com.cosmosource.app.utils;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class XmlUtil {
	private static final Log log = LogFactory.getLog(XmlUtil.class);
	/** 
	* @title: stringToXml 
	* @description: 生成xml(返回结果)
	* @author cjw
	* @param methodName
	* @param value
	* @return
	* @date 2013-4-21 下午09:27:28     
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public static String stringToXml(String methodName){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?><root>");
		// 开门
		if(methodName.equals("controlDoor")){
			sb.append("<result>0</result>");
			sb.append("<msg>开门成功</msg>");
		}else if(methodName.equals("controlDoor_false")){
			sb.append("<result>1</result>");
			sb.append("<msg>开门失败</msg>");
		}
		sb.append("</root>");
		System.err.println(sb.toString());
		return sb.toString();
	}
	/** 
	* @title: parseStringXml 
	* @description: 解析xml方法
	* @author cjw
	* @param xml
	* @return
	* @date 2013-5-27 下午05:35:55     
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
