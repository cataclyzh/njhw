package com.cosmosource.app.message.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class FaxService {
	
	private static final Log log = LogFactory.getLog(FaxService.class);
	
	//默认参数设置,配置在config.properties文件中
	public String url = "http://10.254.101.100/ipcom/index.php/Api/";	
//	public String usr = "admin";	
//	public String pwd = "011c945f30ce2cbafc452f39840f025693339c42";
	
	public static final String LOGIN = "login";
	
	public static final String GETLIST = "fi_list";
	
	public static final String SENDLIST = "fo_list";
	
	private String sessionId;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public long getGetUnReadNum(String usr, String pwd){		
		long result = 0;
		
		try{
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", usr));
			nvps.add(new BasicNameValuePair("password", pwd));
			JSONObject jObject = send(nvps, LOGIN);
			if (!jObject.get("data").toString().equals("null")) {
				JSONObject dataObj = (JSONObject) jObject.get("data");
				Object obj = dataObj.get("session_id");
				if(obj != null){
					sessionId = obj.toString();
				}
			}
			
			List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
			nvps2.add(new BasicNameValuePair("session_id", sessionId));
			nvps2.add(new BasicNameValuePair("username", usr));
//			nvps2.add(new BasicNameValuePair("password", pwd));
			nvps2.add(new BasicNameValuePair("is_read", "0"));
			
			jObject = send(nvps2, GETLIST);
			if (!jObject.get("data").toString().equals("null")) {
				JSONObject dataObj = (JSONObject) jObject.get("data");
				Object obj = dataObj.get("total");
				if(obj != null){
					result = parseLongValue(obj);
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	
	public List<Map> getTitleInfo(String usr , String pwd) throws Exception {
		List<Map> resultList = new ArrayList<Map>();
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", usr));
		//nvps.add(new BasicNameValuePair("password", pwd));
		JSONObject jObject = send(nvps, LOGIN);
		if (!jObject.get("data").toString().equals("null")) {
			JSONObject dataObj = (JSONObject) jObject.get("data");
			Object obj = dataObj.get("session_id");
			if(obj != null){
				sessionId = obj.toString();
			}
		}
		
		List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
		nvps2.add(new BasicNameValuePair("session_id", sessionId));
		nvps2.add(new BasicNameValuePair("username", usr));
		//nvps2.add(new BasicNameValuePair("password", pwd));
		nvps2.add(new BasicNameValuePair("page_size", "5"));
		//nvps2.add(new BasicNameValuePair("is_read", "0"));
		
		jObject = send(nvps2, GETLIST);
		if (!jObject.get("data").toString().equals("null")) {
			JSONObject dataObj = (JSONObject) jObject.get("data");
//			Object obj = dataObj.get("total");
			if(dataObj.get("list").equals("null")){
				return null;
			}
			JSONArray list = (JSONArray) dataObj.get("list");
			for(int i=0; i<list.size(); i++){
				Map m = new HashMap();
				JSONObject o = (JSONObject) list.get(i);
				//m.put("id", o.get("id"));
				m.put("created_on", o.get("created_on"));
				//m.put("tel_line", o.get("tel_line"));
				//m.put("user_id", o.get("user_id"));
				m.put("caller", o.get("caller"));
				//m.put("called", o.get("called"));
				//m.put("extn", o.get("extn"));
				//m.put("from_csid", o.get("from_csid"));
				//m.put("from_name", o.get("from_name"));
				//m.put("duration", o.get("duration"));
				//m.put("pages", o.get("pages"));
				//m.put("path", o.get("path"));
				//m.put("status", o.get("status"));
				//m.put("forward_status", o.get("forward_status"));
				resultList.add(m);
			}
		}
		return resultList;
	}

	private JSONObject send(List<NameValuePair> nvps, String command) throws Exception {
		JSONObject result = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url + command);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpPost, responseHandler);
			//System.out.println(Unicode.decodeUnicode(responseBody));
			JSONObject jObject = JSONObject.fromObject(responseBody);
			
			//System.out.println(jObject.toString());
			result = jObject;
		} finally {
			httpPost.releaseConnection();
		}
		return result;
	}
	
	private long parseLongValue(Object obj){
		long result = 0;
		if(obj != null){
			try {
				result = Long.parseLong(obj.toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		FaxService faxService = new FaxService();
//		List<Map> list = faxService.getTitleInfo("zouyang", "011c945f30ce2cbafc452f39840f025693339c42");
		List<Map> list = faxService.getTitleInfo("admin", "");
		
		for(Map m : list){
			System.out.println(m);
		}
	}
}
