package com.cosmosource.app.port.serviceimpl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.ExtInFaxList;
import com.cosmosource.app.entity.ExtOutFaxAttach;
import com.cosmosource.app.transfer.serviceimpl.FaxToSystemService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;

public class FaxToAppService extends BaseManager{
	
	private static final Log log = LogFactory.getLog(TravelInformationToAppService.class);
	private  FaxToSystemService  faxToSystemService;
	private final static String USERNAME = "admin";
	private final static String PASSWORD = "011c945f30ce2cbafc452f39840f025693339c42";
	
	/**
	 * 
	 * @Description: 传真登陆模拟
	 * @Author：cjw
	 * @Date：2013-5-05
	 * @return           
	 **/
	
	public 	String  requestDataForLogin(String userName,String password){
		    String  requestResult = null;	
		     try {
		    	 //请求数据体"http://10.254.101.100/ipcom/index.php/Api/login"
		    	 requestResult=	new FaxToSystemService().sendPostForLogin(Constants.DBMAP.get("FAX_LOGIN"),userName,password);
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
	         return  requestResult;
	 }

	/**
	 * 
	 * @Description: 发送传真
	 * @Author：cjw
	 * @Date：2013-5-05
	 * @return           
	 **/
	
	@SuppressWarnings("rawtypes")
	public String sendFax(String session_id, String fo_to, String urgent,
			String fo_subject, String fo_body, File[] fax_attach,
			List fax_attachFileName,List fax_attachContentType,String need_confirm) {
		
		    String  requestResult = null;	
		     try {
		    	 //请求发送"http://10.254.101.100/ipcom/index.php/Api/send_fax"
		    	 requestResult=	new FaxToSystemService().sendFax(Constants.DBMAP.get("SEND_FAX"),
		    			 		session_id, fo_to, urgent,
		    			 		fo_subject, fo_body, fax_attach, fax_attachFileName,
		    			 		fax_attachContentType,need_confirm);
		    	 
		    	 
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
	         return  requestResult;
	 }
	
	
	/**
	 * 
	 * @Description: 下载接收传真附件
	 * @Author：cjw
	 * @Date：2013-5-05
	 * @return           
	 **/
	
	public 	String  receiveFax(String sessionId,String id,String filePath){
		    String  requestResult = null;	
		     try {
		    	 //请求发送"
		    	 requestResult=	new FaxToSystemService().receiveFax("http://10.254.101.100/ipcom/index.php/Api/fi_download?session_id="+sessionId+"&&id="+id,
		    			 filePath);
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
	         return  requestResult;
	 }
	
	/**
	 * 
	 * @Description: 查询收到的传真列表
	 * @Author：cjw
	 * @Date：2013-5-05
	 * @return           
	 **/
	
	public 	String  requestDataForReceiveSearch(String sessionId){
	    String  requestResult = null;	
	     try {
	    	 //http://10.254.101.100/ipcom/index.php/Api/fi_list
	    	 //请求数据体
	    	 requestResult=	faxToSystemService.sendPostForReceiveSearch(Constants.DBMAP.get("FAX_RECEIVE"),sessionId);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
         return  requestResult;
	 }
	
	
	
	
	/**
	 * 
	 * @Description: 查询外发的传真列表
	 * @Author：cjw
	 * @Date：2013-5-05
	 * @return           
	 **/
	
	public 	String  currentFaxForSendSearch(String sessionId,String receiver,String subject){
	    String  requestResult = null;	
	     try {
	    	 //http://10.254.101.100/ipcom/index.php/Api/fo_list
	    	 //请求数据体
			requestResult = faxToSystemService.currentFaxForSendSearch(
					Constants.DBMAP.get("FAX_SEND"), sessionId, receiver,
					subject);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
         return  requestResult;
	 }
	
	/**
	 * @Description: 下载外发的传真附件
	 * @Author：cjw
	 * @Date：2013-5-05
	 * @return           
	 **/
	
	public String sendFaxForDownLoad(String sessionId,String id){
		    String requestResult = null;	
		     try {
		    	 //http://10.254.101.100/ipcom/index.php/Api/fo_download
		    	 //请求发送
		    	 requestResult=	faxToSystemService.sendFaxForDownload(Constants.DBMAP.get("FAX_SEND_DOWNLOAD")+"?session_id="+sessionId+"&&id="+id,
		    			 id);
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
	         return  requestResult;
	 }
	
	
	/**
	 * @Description: 下载发送传真的附件
	 * @Author：cjw
	 * @Date：2013-5-05
	 * @return           
	 **/
	
//	public 	String  sendFaxForDownLoad(String sessionId,String id,String filePath){
//		    String  requestResult = null;	
//		     try {
//		    	 //请求发送http://10.254.101.100/ipcom/index.php/Api/fo_download
//		    	 requestResult=	faxToSystemService.sendFaxForDownload(Constants.DBMAP.get("FAX_SEND_DOWNLOAD")+"?session_id="+sessionId+"&&id="+id,
//		    			 filePath);
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.info(e.getMessage());
//			}
//	         return  requestResult;
//	 }
	
	
	/** 
	* @title: requestDataForSessionId 
	* @description: 返回登陆者的sessionId （for test）
	* @author cjw
	* @param userName
	* @param password
	* @return
	* @date 2013-5-8 下午05:08:23     
	* @throws 
	*/ 
	public String requestDataForSessionId(String userName,String password) {
		String sessionId = "";
		try {
			/*
			 * 登陆传真系统
			 * 返回格式如 {"error":null,"data":{"session_id":"5adb1825c452485edd06d98e25f7115a"}}
			 */
			String json = new FaxToAppService().requestDataForLogin(userName,password);
			
			/*if(StringUtil.isBlank(json)){
				log.info("登陆传真系统失败");
				return  null;
			}
			if(json.indexOf("session_id")<=0){
				log.info("登陆传真系统失败可能是密码错误活该用户不存在");
				return null;
			}*/
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("null".equals(jsonObject.getString("data"))){
				log.info("登陆传真系统失败");
				return null;
			}
			JSONObject data= JSONObject.fromObject(jsonObject.get("data"));
			sessionId = data.get("session_id").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionId;
	}
	/** 
	* @title: synchronousFaxInfo 
	* @description: 同步收到的传真列表
	* @author cjw
	* @date 2013-5-6 下午06:07:44     
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public  void saveSynchronousReceiveFaxInfo(String userName,String password,Long userId,String mac) {
		if(StringUtil.isBlank(userName) || StringUtil.isBlank(password)||userId ==null){
			log.info("传递的参数不能为空!");
			return;
		}
		String sessionId = "";
		try {
			//登陆传真系统
			String json = requestDataForLogin(userName,password);
			if(StringUtil.isBlank(json)){
				log.info("登陆传真系统失败");
				return;
			}
			if(json.indexOf("session_id")<=0){
				log.info("登陆传真系统失败可能是密码错误活该用户不存在");
				return;
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("null".equals(jsonObject.get("data"))||null==jsonObject.get("data")){
				log.info("登陆传真系统失败:sessionid为空");
				return;
			}
			JSONObject data= JSONObject.fromObject(jsonObject.get("data"));
			sessionId=data.get("session_id").toString();
			if(StringUtil.isBlank(sessionId)){
				log.info("登陆传真系统失败:sessionid为空");
				return;
			}
			// 调用传真列表
			String josnList = requestDataForReceiveSearch(sessionId);
			log.info(josnList);
			JSONObject faxJson = JSONObject.fromObject(josnList);
			JSONObject faxJsonData = JSONObject.fromObject(faxJson.get("data"));
			log.info(faxJsonData.get("list").toString());
			if("null".endsWith(faxJsonData.get("list").toString())){
				log.info("无传真数据!");
				return;
			}
			log.info("符合条件的全部总数:"+faxJsonData.get("total").toString());
			JSONArray faxJsonList = JSONArray.fromObject(faxJsonData.get("list"));
				if(faxJsonList instanceof JSONArray){
					Iterator<JSONObject> it = ((JSONArray) faxJsonList).iterator();
					while(it.hasNext()){
						JSONObject jsonObject2 = it.next();
						ExtInFaxList  eil=null;
						String id = jsonObject2.get("id").toString();
						if(StringUtil.isBlank(id)){
							log.info("传真号不存在!");
							break;
						}
						//通过传真号得到传真记录
						List<ExtInFaxList> eils = dao.findByHQL("select e from ExtInFaxList e where e.faxId=? and e.flFlag=?", Long.valueOf(id),"1");
						if(eils!=null && eils.size()>0){
							eil = eils.get(0);
						}else{
							eil = new ExtInFaxList();
						}
						eil.setUserid(userId); // 设置userid njhw
						eil.setMacAdr(mac); //设置mac地址
						eil.setFlFlag("1");//收发传真类型（1 收；2 发）
						eil.setReadFlag("0");//0未读  1已读
						eil.setTotal(Integer.valueOf(faxJsonData.get("total").toString()));///设置传真的总数
						eil.setCaller(jsonObject2.get("caller").toString());
						log.info("主叫号："+jsonObject2.get("caller").toString());
						eil.setCalled(jsonObject2.get("called").toString());
						log.info("被叫号："+jsonObject2.get("called").toString());
						eil.setExtn(jsonObject2.get("extn").toString());
						log.info("分机号："+jsonObject2.get("extn").toString());
						eil.setFromCsid(jsonObject2.get("from_csid").toString());
						log.info("发送者CSID："+jsonObject2.get("from_csid").toString());
						eil.setFromName(jsonObject2.get("from_name").toString().trim());
						log.info("发送者姓名："+jsonObject2.get("from_name").toString().trim());
						eil.setDuration(Integer.valueOf(jsonObject2.get("duration").toString()));
						log.info("接收花费时间（秒）："+jsonObject2.get("duration").toString());
						eil.setPages(Integer.valueOf(jsonObject2.get("pages").toString()));
						log.info("接收到的页数："+jsonObject2.get("pages").toString());
						eil.setStatus(jsonObject2.get("status").toString());
						log.info("接收状态(200成功,400失败)："+jsonObject2.get("status").toString());
						eil.setForwardStatus(jsonObject2.get("forward_status").toString());
						log.info("邮件转发状态(0未转发，200转发成功，4xx转发失败)："+jsonObject2.get("forward_status").toString());
						eil.setUserId(jsonObject2.get("user_id").toString());
						log.info("传真接收账户："+jsonObject2.get("user_id").toString());
						eil.setUsername(jsonObject2.get("userName").toString());
						log.info("传真接收名称："+jsonObject2.get("userName").toString());
						eil.setCreateOn(DateUtil.str2Date(jsonObject2.get("created_on").toString(), "yyyy-MM-dd HH:mm:ss"));
						log.info("发送时间："+jsonObject2.get("created_on").toString());
						eil.setFaxId(Long.valueOf(jsonObject2.get("id").toString()));
						log.info("传真编号："+jsonObject2.get("id").toString());
						//保存入库
						dao.saveOrUpdate(eil);
					}
				} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/** 
	* @title: synchronousFaxInfo 
	* @description: 同步发送的传真列表
	* @author cjw
	* @date 2013-5-6 下午06:07:44     
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public  void saveSynchronousSendeFaxInfo(String userName,String password,Long userId,String mac) {
		if(StringUtil.isBlank(userName) || StringUtil.isBlank(password) ||userId ==null){
			log.info("传递的参数不能为空!");
			return;
		}
		String sessionId = "";
		boolean res =false;
		try {
			//登陆传真系统
			String json = requestDataForLogin(userName,password);
			if(StringUtil.isBlank(json)){
				log.info("登陆传真系统失败");
				return;
			}
			if(json.indexOf("session_id")<=0){
				log.info("登陆传真系统失败可能是密码错误");
				return;
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("null".equals(jsonObject.get("data"))||null==jsonObject.get("data")){
				log.info("登陆传真系统失败:sessionid为空");
				return;
			}
			JSONObject data= JSONObject.fromObject(jsonObject.get("data"));
			sessionId=data.get("session_id").toString();
			if(StringUtil.isBlank(sessionId)){
				log.info("登陆传真系统失败:sessionId为空");
				return;
			}
			// 调用传真列表
			//String josnList =requestDataForSendSearch(sessionId);
			String josnList = "JSONLIST" ;
			if(StringUtil.isBlank(josnList)){
				log.info("查询发送的传真列表为空!");
				return;
			}
			log.info(josnList);
			JSONObject faxJson = JSONObject.fromObject(josnList);
			JSONObject faxJsonData = JSONObject.fromObject(faxJson.get("data"));
			if("null".endsWith(faxJsonData.get("list").toString())){
				log.info("无传真数据!");
				return;
			}
			JSONArray faxJsonList = JSONArray.fromObject(faxJsonData.get("list"));
			log.info(faxJsonData.get("list").toString());
				if(faxJsonData.get("list") instanceof JSONArray){
					Iterator<JSONObject> it = ((JSONArray) faxJsonList).iterator();
					while(it.hasNext()){
						JSONObject jsonObject2 = it.next();
						ExtInFaxList  e =null;
						String id = jsonObject2.get("id").toString();
						if(StringUtil.isBlank(id)){
							log.info("传真号不存在!");
							return;
						}
						//通过传真号得到传真记录
						List<ExtInFaxList> eils = dao.findByHQL("select e from ExtInFaxList e where e.faxId=? and e.flFlag=?", Long.valueOf(id),"2");
						log.info(eils.size());
						if(eils!=null && eils.size()>0){
							e = eils.get(0);
						}else{
							e = new ExtInFaxList();
						}
						e.setUserid(userId); // 设置userid njhw
						e.setMacAdr(mac); //设置mac地址
						e.setFlFlag("2");//收发传真类型（1 收；2 发）
						e.setTotal(Integer.valueOf(faxJsonData.get("total").toString()));///设置传真的总数
						log.info("符合条件的全部总数:"+faxJsonData.get("total").toString());
						e.setCaller(jsonObject2.get("caller").toString());
						log.info("主叫号："+jsonObject2.get("caller").toString());
						e.setCalled(jsonObject2.get("called").toString());
						log.info("被叫号："+jsonObject2.get("called").toString());
						e.setNextProcTime(jsonObject2.get("last_proc_time").toString());
						log.info("下一次处理时间："+jsonObject2.get("last_proc_time").toString());
						e.setSendCount(Integer.valueOf(jsonObject2.get("send_count").toString()));
						log.info("已尝试发送次数："+jsonObject2.get("send_count").toString());
						e.setReceiver(jsonObject2.get("receiver").toString());
						log.info("收件人姓名："+jsonObject2.get("receiver").toString());
						if(jsonObject2.get("send_interval")!=null){
							e.setDuration(Integer.valueOf(jsonObject2.get("send_interval").toString()));
							log.info("失败后再次尝试发送的间隔（秒）："+jsonObject2.get("send_interval").toString());
						}
						e.setSubject(jsonObject2.get("subject").toString());
						log.info("传真主题："+jsonObject2.get("subject").toString());
						e.setUserId(jsonObject2.get("user_id").toString());
						log.info("发送者账户："+jsonObject2.get("user_id").toString());
						e.setUsername(jsonObject2.get("userName").toString().trim());
						log.info("发送者名称："+jsonObject2.get("userName").toString().trim());
						e.setCreateOn(DateUtil.str2Date(jsonObject2.get("created_on").toString(), "yyyy-MM-dd HH:mm:ss"));
						log.info("发送时间："+jsonObject2.get("created_on").toString());
						e.setReceiver(jsonObject2.get("receiver").toString());
						log.info("收件人姓名："+jsonObject2.get("receiver").toString());
						e.setReplyAddress(jsonObject2.get("reply_address").toString());
						log.info("传真发送成功失败的回复邮件地址："+jsonObject2.get("reply_address").toString());
						e.setPriority(jsonObject2.get("priority").toString());
						log.info("传真优先级："+jsonObject2.get("priority").toString());
						e.setStatus(jsonObject2.get("status").toString());
						log.info("发送状态和中文名称："+jsonObject2.get("status").toString());
						e.setStatusname(jsonObject2.get("statusName").toString());
						log.info("中文名称（1xx发送中，200成功，3xx失败待重试，4xx最终失败）："+jsonObject2.get("statusName").toString());
						e.setForwardStatus(jsonObject2.get("statusName").toString());
						log.info("邮件回复状态（0未转发，200成功，4xx失败）："+jsonObject2.get("reply_status").toString());
						e.setFaxId(Long.valueOf(jsonObject2.get("id").toString()));
						log.info("传真编号："+jsonObject2.get("id").toString());
						//保存入库
						dao.saveOrUpdate(e);
						String subject = jsonObject2.get("subject").toString();
						//res = sendFaxForDownLoad(sessionId, id,subject);
						res = true;
						if(res){
							ExtOutFaxAttach a=null;// 附件表
							List<ExtOutFaxAttach> es = dao.findByHQL("select e from ExtOutFaxAttach e where e.eofcId=?",e.getFlId());
							log.info(es.size());
							if(es!=null && es.size()>0){
								a = es.get(0);
							}else{
								a = new ExtOutFaxAttach();
							}
							a.setInsertDate(DateUtil.getSysDate());//设置插入时间
							a.setEofaFtype("2");
							a.setEofcId(e.getFlId());
							a.setEofaFname(id+subject+".zip");
							// 常量的配置 文件存储路径
							// c:/
							a.setEofaDesc(Constants.DBMAP.get("FAX_FILEPATH")+id+subject+".zip");
							dao.saveOrUpdate(a);
						}
					}
				} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String ascii2native(String ascii) {  
	    int n = ascii.length() / 6;  
	    StringBuilder sb = new StringBuilder(n);  
	    for (int i = 0, j = 2; i < n; i++, j += 6) {  
	        String code = ascii.substring(j, j + 4);  
	        char ch = (char) Integer.parseInt(code, 16);  
	        sb.append(ch);  
	    }  
	    return sb.toString();  
	}
	
	public FaxToSystemService getFaxToSystemService() {
		return faxToSystemService;
	}

	public void setFaxToSystemService(FaxToSystemService faxToSystemService) {
		this.faxToSystemService = faxToSystemService;
	}

}
