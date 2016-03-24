package com.cosmosource.app.port.serviceimpl;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import com.cosmosource.app.utils.TransferWebService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;

/** 
* @description: ip电话提供服务端()
* @author cjw
* @date 2013-5-8 上午09:40:57     
*/ 
public class IPPhoneSiteToAppService extends BaseManager{
	private static final  Log log = LogFactory.getLog(IPPhoneSiteToAppService.class);
	private String xml;
	private String[] strs;
	
	//private String url="http://59.201.4.207:8090/ipphoneweb/webservice/SendMsg";
	private String url="http://10.250.51.5:8080/ipphoneweb/webservice/SendMsg";
	
	/** 
	* @title: dail 
	* @description:页面直接拨号
	* @author cjw
	* @date 2013-5-6 下午03:34:36     
	* @throws 
	*/ 
	public boolean dail(String caller,String mac,String called){
		boolean res = false;
		if(StringUtil.isBlank(caller) || StringUtil.isBlank(mac) || StringUtil.isBlank(called) ){
			log.info("参数不能为空!");
			return false;
		}
		try {
			// 组xml格式的参数 
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version='1.0' encoding='UTF-8'?><root><param>");
			sb.append("<snum>").append(caller).append("</snum>");
			sb.append("<mac>").append(mac).append("</mac>");
			sb.append("<dnum>").append(called).append("</dnum>");
			sb.append("</param></root>");
			// 调用ip电话接口推送给ip电话
			//
			xml = TransferWebService.transferIPPhoneService(Constants.DBMAP.get("IPPHONE_DAIL"),"dail",sb.toString());
			if(StringUtil.isNotBlank(xml)){
				log.info(xml);
				Iterator<Element> iter = TransferWebService.parseStringXml(xml);
				while (iter.hasNext()) {
					 Element e = (Element) iter.next();
					 caller=e.elementTextTrim("result");
				 }
				if(caller.equals("0")){
					res = true;
				}
			}
			
		}catch (Exception e) {
			log.info("拨打电话:"+e);
			e.printStackTrace();
		}
		return res;
	}
	public static void main(String[] args) {
		IPPhoneSiteToAppService i = new IPPhoneSiteToAppService();
		i.dail("18701664166","SEP20BBC0204941","18701664166");
	}
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String[] getStrs() {
		return strs;
	}

	public void setStrs(String[] strs) {
		this.strs = strs;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
