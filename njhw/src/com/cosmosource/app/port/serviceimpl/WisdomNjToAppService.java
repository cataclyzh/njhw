package com.cosmosource.app.port.serviceimpl;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.port.model.WisdomNj;
import com.cosmosource.app.transfer.serviceimpl.WisdomNjToSystemService;
import com.cosmosource.base.service.BaseManager;


/**
 * @ClassName:WisdomNjToAppService
 * @Description：智慧南京to app的内部调用类
 * @Author：hp 
 * @Date:2013-3-24
 */
public class WisdomNjToAppService extends BaseManager{
	
	
	private WisdomNjToSystemService wisdomNjToSystemService;
	private static final Log log = LogFactory.getLog(WisdomNjToAppService.class);

	 /**
	* @Description：查询周围环境的视频影像
	* @Author：hp
	* @Date：2013-3-23
	* @param pointNo
	* @return
	**/
	public String queryVideo(String pointNo){
		try {
			 if(StringUtils.isBlank(pointNo)){
				 log.info("基站编号不能为空!");
				 return null;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		 //调用智慧南京的接口组合成xml文件给视频的url地址
		 return null;
	 }
	
	
	/**
	* @Description：查询周边环境信息
	* @Author：hp
	* @Date：2013-3-23
	* @param areaIds
	* @return
	**/
	public Collection<WisdomNj> queryAroundInformation(String areaIds){
		 String aroundInfo = null;
		 Collection<WisdomNj> result = null;
		 String field = null;
		 try {
			 if(StringUtils.isBlank(areaIds)){
				 log.info("区域编号不能为空!");
				 return null;
			 }
			 aroundInfo = wisdomNjToSystemService.getWisdomNjWeatherInfo(WisdomNjToSystemService.ENVIRONMENT, areaIds);
			 result = parseDataJson(aroundInfo);
			 field = parseResultJson(areaIds);
		} catch (RemoteException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (ServiceException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		 return getLogInfo(field,result);
	}
	
	
	/**
	* @Description：查询路况信息
	* @Author：hp
	* @Date：2013-3-23
	* @param roadIds
	* @return
	**/
	public Collection<WisdomNj> queryRoadCondition(String roadIds){
		if(StringUtils.isBlank(roadIds)){
			 log.info("道路编号不能为空!");
			 return null;
		 }
		 String aroundInfo = null;
		 Collection<WisdomNj> result = null;
		 String field = null;
		 try {
			 aroundInfo = wisdomNjToSystemService.getWisdomNjTrafficInfo(roadIds);
			 result = parseDataJson(aroundInfo);
			 field = parseResultJson(roadIds);
		} catch (RemoteException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (ServiceException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		 return getLogInfo(field,result);
	}
	
	
	
	/**
	* @Description：查询周边天气信息
	* @Author：hp
	* @Date：2013-3-23
	* @param areaIds
	* @return
	**/
	public Collection<WisdomNj> queryWeatherInformation(String areaIds){
		if(StringUtils.isBlank(areaIds)){
			 log.info("区域编号不能为空!");
			 return null;
		 }
		 String aroundInfo = null;
		 Collection<WisdomNj> result = null;
		 String field = null;
		 try {
			 aroundInfo = wisdomNjToSystemService.getWisdomNjWeatherInfo(WisdomNjToSystemService.WEATHER, areaIds);
			 result = parseDataJson(aroundInfo);
			 field = parseResultJson(areaIds);
		} catch (RemoteException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} catch (ServiceException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		 return getLogInfo(field,result);
	}
	
	
	/**
	* @Description：根据结果解析json字符串得到一个智慧南京对象集合
	* @Author：hp
	* @Date：2013-3-24
	* @param jsonStr
	* @param dataType
	* @return
	**/
	@SuppressWarnings({ "unchecked", "static-access" })
	public Collection<WisdomNj> parseDataJson(String jsonStr) throws JSONException{
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		JSONArray jsonArray = (JSONArray) jsonObject.get("data");
		Collection<WisdomNj> c = jsonArray.toCollection(jsonArray, WisdomNj.class);
		return c;
	}
	
	
	/**
	* @Description：根据结果解析json字符串得到一个智慧南京结果
	* @Author：hp
	* @Date：2013-3-24
	* @param jsonStr
	* @return
	**/
	public String parseResultJson(String jsonStr) throws JSONException{
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String result =  (String) jsonObject.get("statecode");
		return result;
	}
	
	
	/**
	* @Description：把结果json组合成xml传递给视频的url
	* @Author：hp
	* @Date：2013-3-24
	* @param jsonStr
	* @return
	* @throws JSONException
	**/
	public String convertXml(String jsonStr) throws JSONException{
		Collection<WisdomNj> wisdom = this.parseDataJson(jsonStr);
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0'?>");
		sb.append("<Parament>");
		for (WisdomNj wisdomNj : wisdom) {
			sb.append("<DeviceIP>").append(wisdomNj.getServIp()).append("</DeviceIP>");
			sb.append("<DevicePort>").append(wisdomNj.getServPort()).append("</DevicePort>");
			sb.append("<DeviceType>").append(wisdomNj.getType()).append("</DeviceType>");
			sb.append("<User>").append(wisdomNj.getAdmin()).append("</User>");
			sb.append("<Password>").append(wisdomNj.getPass()).append("</Password>");
			sb.append("<ChannelNum>").append(wisdomNj.getTongdao()).append("</ChannelNum>");
			sb.append("<ProtocolType>0</ProtocolType>");
			sb.append("<StreamType>1</StreamType>");
			sb.append("<Transmits>");
			sb.append("<Transmit><SrvIp>" + wisdomNj.getRadioIp() + ":" + wisdomNj.getRadioPort() + "</SrvIp></Transmit>");
			sb.append("</Transmits>");
		}
		sb.append("</Parament>");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	/**
	* @Description：记录结果日志
	* @Author：hp
	* @Date：2013-3-27
	* @param field
	**/
	public Collection<WisdomNj> getLogInfo(String field,Collection<WisdomNj> result){
		try {
			if(field.equals("2")){
				log.info("查询结果失败");
				result = null;
			}
			if(field.equals("3")){
				log.info("查询参数不合法");
				result = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		String jsonStr = "{\"statecode\":\"1\",\"data\":["+ 
	         "{  \"servIp\":\"10.201.11.121\",\"servPort\":\"8000\",\"tongdao\":\"0\","+
	           "\"admin\":\"admin\",\"pass\":\"12345\",\"creamId\":\"366\",\"type\":\"1\","+
	            "\"radioIp\":\"59.201.5.47\",\"radioPort\":\"554\"}]}";
		new WisdomNjToAppService().convertXml(jsonStr);
	}


	public WisdomNjToSystemService getWisdomNjToSystemService() {
		return wisdomNjToSystemService;
	}


	public void setWisdomNjToSystemService(
			WisdomNjToSystemService wisdomNjToSystemService) {
		this.wisdomNjToSystemService = wisdomNjToSystemService;
	}
	
}
