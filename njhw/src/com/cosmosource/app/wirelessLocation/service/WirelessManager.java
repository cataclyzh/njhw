package com.cosmosource.app.wirelessLocation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;

/** 
* @ClassName: WirelessService 
* @Description: 无线定位的操作 
* @author pingxianghua
* @date 2013-8-6 上午09:32:19 
*  
*/
public class WirelessManager extends BaseManager{
	
	private static final Log log = LogFactory.getLog(WirelessSqlManager.class);
	private String url;
	private String wirelessLocation;
	private String soapActionURI;
	
	/**
	* @Description 实时的位置
	* @Author：zhujiabiao
	* @Date 2013-8-13 下午02:30:40 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public JSONObject wirelessLocation(String tagMac){
		url = Constants.DBMAP.get("WIRELESS_URL");
		url = "http://10.252.3.220:8080/ServiceApi.asmx";
		wirelessLocation = Constants.DBMAP.get("WIRELESS_LOCATION");
		if(wirelessLocation == null || wirelessLocation.trim().length() == 0){
			wirelessLocation = "GetTagStatus";
		}
		soapActionURI = Constants.DBMAP.get("WIRELESS_SOAPACTIONURI");
		if(soapActionURI == null || soapActionURI.trim().length() == 0){
			soapActionURI = "http://www.uradiosys.com/";
		}
		
		JSONObject result = new JSONObject();
		String placeId = null;
		
	    Service service = new Service();
		try {
			Call call = (Call)service.createCall();
		    call.setTargetEndpointAddress(new java.net.URL(url));
		    call.setOperation(wirelessLocation);
		    call.setUseSOAPAction(true);
		    call.setSOAPActionURI(soapActionURI+wirelessLocation);   
		    call.setOperationName(new QName(soapActionURI,wirelessLocation));     
		    //设置要传递的参数
			call.addParameter(new QName(soapActionURI,"tagMac"),XMLType.XSD_STRING,ParameterMode.IN);
			call.setReturnType(XMLType.XSD_SCHEMA);
			call.setUseSOAPAction(true);
			Object res = (Object)call.invoke(new Object[]{tagMac});
			Schema schema = (Schema)res;
			MessageElement[] msgele = schema.get_any();
			placeId = msgele[8].getValue();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		try{
			if(placeId != null && placeId.trim().length() != 0){
				//
				String newPlaceId = placeId.substring(placeId.length() - 3);
				Map map = new HashMap();
				map.put("placeId", newPlaceId);
				List<Map> list = sqlDao.findList("WirelessSql.selectFloorByCoordinates", map);
				Map<String,Object> resMap  = new HashMap<String, Object>();
				if(list != null && list.size() > 0){
					resMap = list.get(0);
					String pointId = String.valueOf(resMap.get("ID"));
					String pointName = String.valueOf(resMap.get("PLACE"));
					result.put("pointId", pointId);
					result.put("pointName", pointName);
				}
			}
			//result.put("CoordinatesId", placeId);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	 /**
	* @Description 根据证件号查询TagMac
	* @Author：pingxianghua
	* @Date 2013-8-5 下午03:27:20 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String getqueryTagMacByIDCradNum(String iDCradNum){
		Map map = new HashMap();
		map.put("iDCradNum", iDCradNum);
		
		if(iDCradNum != null && iDCradNum.startsWith("0000")){
			iDCradNum = iDCradNum.substring(4);
		}
		
		List<Map> list=null;
		try {
			if(iDCradNum!=null && !"".equals(iDCradNum))
			{
				list = sqlDao.findList("WirelessSql.selectTagMacByIDCradNum", map);
				if(list.size() == 0){
					return null;
				}
				return (String) list.get(0).get("TAGMAC");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e+"["+iDCradNum+"]");
		}
		return "";
	}
	
	public List getTest(){
		List list = dao.getSession().createSQLQuery("select u.display_name from users u").addScalar("display_name", Hibernate.STRING).list();
		return list;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWirelessLocation() {
		return wirelessLocation;
	}
	
	public void setWirelessLocation(String wirelessLocation) {
		this.wirelessLocation = wirelessLocation;
	}

	public String getSoapActionURI() {
		return soapActionURI;
	}

	public void setSoapActionURI(String soapActionURI) {
		this.soapActionURI = soapActionURI;
	}
	
}
