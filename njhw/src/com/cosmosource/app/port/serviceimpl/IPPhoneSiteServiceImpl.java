package com.cosmosource.app.port.serviceimpl;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import com.cosmosource.app.port.service.IPPhoneManager;
import com.cosmosource.app.port.service.IPPhoneSiteService;
import com.cosmosource.app.utils.XmlUtil;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;


/**
 * @ClassName:IPPhoneSiteServiceImpl
 * @Description：政务系统与IP电话内部接口实现
 * @Author：cjw
 * @Date:2013-4-18
 */


public class IPPhoneSiteServiceImpl implements IPPhoneSiteService{
	
	    private static final  Log log = LogFactory.getLog(IPPhoneSiteServiceImpl.class);
	    public DoorControlToAppService getDoorControlToAppService() {
			return doorControlToAppService;
		}


		public void setDoorControlToAppService(
				DoorControlToAppService doorControlToAppService) {
			this.doorControlToAppService = doorControlToAppService;
		}


		public IPPhoneManager getiPPhoneManager() {
			return iPPhoneManager;
		}


		public void setiPPhoneManager(IPPhoneManager iPPhoneManager) {
			this.iPPhoneManager = iPPhoneManager;
		}

		private DoorControlToAppService doorControlToAppService;
	    private IPPhoneManager iPPhoneManager;

		
		/**
		* @Description：开门（控制门禁）
		* @Author：qyq
		* @Date：2013-4-18
		* @param xml 参数报文：xml字符串
		* @return
		* @throws Exception
		**/
		@Override
		public  String controlDoor(String xml){
			if(StringUtils.isBlank(xml)){
				log.info("所填写的xmlStr参数不能为空");
				return null;
			}
			String mac="";
			String msgId="";
			Iterator<Element> iter = XmlUtil.parseStringXml(xml);
			try {
				while (iter.hasNext()) {
					 Element e = (Element) iter.next();
					 
					 mac=e.elementTextTrim("mac");
					 msgId=e.elementTextTrim("msg");
				}
				String doorId = "";
				
				String iseMac = "";
				if (mac.length() > 0) {
					iseMac = StringUtils.substring(mac, 3);
				}
				
				// 通过mac得到门的id
				HashMap<String, String> phoneMacInfo = iPPhoneManager.getPhoneMacInfo(iseMac);
				
				if (phoneMacInfo != null && StringUtil.isNotBlank(phoneMacInfo.get("roomId"))) {
					doorId = phoneMacInfo.get("roomId");
				} else {
					doorId = iPPhoneManager.controlDoor(mac);
				}
				
				
				if(StringUtils.isNotEmpty(doorId)){
					doorControlToAppService.controlDoor(doorId, Constants.DBMAP.get("WS_DOORORDER_OPEN"), mac, msgId);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
			return  xml;
		}
		
		public String controlDoor_wx(String xml){
			if(StringUtils.isBlank(xml)){
				log.error("所填写的xmlStr参数不能为空");
				return null;
			}
			System.out.println("controlDoor_wx["+xml+"]");
			
			Iterator<Element> iter = XmlUtil.parseStringXml(xml);
			try {
				String personId = null;
				String roomName = null;
				while (iter.hasNext()) {
					 Element e = (Element) iter.next();
					 personId=e.elementTextTrim("personId");
					 roomName=e.elementTextTrim("roomName");
				}
				
				//通过房间号获取doorId
				String doorId = iPPhoneManager.getDoorInfoByRoomName(roomName);
				System.out.println("doorId["+doorId+"]");
				
				if(StringUtils.isNotEmpty(doorId)){
					doorControlToAppService.controlDoor(doorId, Constants.DBMAP.get("WS_DOORORDER_OPEN"), 
							personId, UUID.randomUUID().toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			return xml;
		}
		
		/**
		 * 
		* @Title: checkDoorAuth  
		* @Description: 定时检查门锁锁内存储地址
		* @author HJ
		* @date 2013-7-28
		* @param     
		* @return void 
		* @throws
		 */
		@Override
		public String checkDoorAuth() {
			// 临时卡过期门锁失效处理
			doorControlToAppService.tmpCardTimeout();
			
			// 临时人员过期门锁失效处理
			doorControlToAppService.tmpUserTimeout();
			
			// 检查门锁锁内存储地址
			doorControlToAppService.checkDoorAuth();
			
			return null;
		}

		/**
		 * 
		* @Title: checkCommAndDoorStatus  
		* @Description: 定时检查通信机门锁连接状态
		* @author HJ
		* @date 2013-7-31
		* @param     
		* @return void 
		* @throws
		 */
		@Override
		public void checkCommAndDoorStatus() {
			// 定时检查通信机门锁连接状态
			doorControlToAppService.checkCommAndDoorStatus();
		}
}
