package com.cosmosource.app.port.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.common.service.CommonManager;
import com.cosmosource.app.entity.ExtInDlockLog;
import com.cosmosource.app.entity.NjhwDoorcontrolExp;
import com.cosmosource.app.entity.NjhwDoorcontrolSet;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.ObjPermMap;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.personnel.service.OrgMgrManager;
import com.cosmosource.app.personnel.service.PersonRegOutManager;
import com.cosmosource.app.personnel.service.UnitAdminManager;
import com.cosmosource.app.transfer.serviceimpl.DoorControlToSystemService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.CSVUtil;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.LockCommandData;
import com.cosmosource.base.util.SocketPool;
import com.cosmosource.base.util.StringUtil;

/**s
 * @ClassName:DoorControlToAppService
 * @Description：门锁操作
 * @Author：hp 
 * @Date:2013-3-18
 */
public class DoorControlToAppService extends BaseManager{
	
	private static final Log log = LogFactory.getLog(DoorControlToAppService.class);
	
	public static final String EIGHTAUTHORITY = "347A6B3EFE86DBB8";//八字节授权码
	public static final String REVERSELOCK  = "02";//可以开反锁 
	public static final String DELETELOCK = "FFFFFFFFFFFFFFFF";//删除锁中卡的指令
	public static final String FIRSTEXTCODE = "FF";//1字节备用
	public static final String FIRSTCARDTYPE = "02";//1字节卡类
	public static final String INIT = "init";//初始化数据权限
	public static final String DELETE = "delete";//删除数据权限
	public static final String QUERY = "query";//查询状态
	public static final String UPLOADAUTHCODE = "AA551331347A6B3EFE86DBB8318319B6";//查询状态
	
	private DoorControlToSystemService doorControlToSystemService;
	private CommonManager commonManager;
	
	private UnitAdminManager unitAdminManager;
	private PersonRegOutManager personROManager;
	private OrgMgrManager orgPerMgrManager;
	
	public UnitAdminManager getUnitAdminManager() {
		return unitAdminManager;
	}

	public void setUnitAdminManager(UnitAdminManager unitAdminManager) {
		this.unitAdminManager = unitAdminManager;
	}
	
	public CSVUtil csvUtil;

	
	public CSVUtil getCsvUtil() {
		return csvUtil;
	}

	public void setCsvUtil(CSVUtil csvUtil) {
		this.csvUtil = csvUtil;
	}

	/**
	* @Description：控制门锁开关
	* @Author：hp
	* @Date：2013-3-25
	* @param doorId   门锁id
	* @param order    开关命令
	* @param loginId  登录人id
	* @param msgId  messaga id
	* @return
	* @throws Exception
	**/
	public void controlDoor(String doorId, String order,String loginId,String msgId){
		try {
			if(StringUtils.isBlank(doorId) || StringUtils.isBlank(order) || StringUtils.isBlank(loginId)){
				log.info("控制门方法传入的参数为空!!");
				return; 
			}
			//根据房间id查询出门锁地址和通讯机地址及其ip地址
			NjhwDoorcontrolSet doorControl = queryDoorEntity(doorId);
			
			//组合命令字符
			StringBuffer sb = new StringBuffer();
			String orderInfo = convertOrderInfo(doorId, order, doorControl.getAdrsStore(), doorControl.getAdrsComm());
			String shutdown = convertOrderInfo(doorId, Constants.DBMAP.get("WS_DOORORDER_CLOSE"), doorControl.getAdrsStore(), doorControl.getAdrsComm());
			//这个地方的order，应该改成异或校验码
			sb.append(orderInfo).append(getXorVerificationCode(orderInfo));
			shutdown  = shutdown+getXorVerificationCode(shutdown);
			// result = doorControlToSystemService.controlDoor(doorId, sb.toString(),shutdown, loginId, doorControl.getAdrsIp());
			
		
			LockCommandData openData = new LockCommandData(true, sb.toString(),
					TimeUnit.NANOSECONDS.convert(0, TimeUnit.SECONDS));
			openData.setOperationPerson(loginId);
			openData.setMsgId(msgId);
			SocketPool.commSocketThreadMap.get(doorControl.getAdrsComm()).addMsgToQueue(openData);
			
			//关门
			LockCommandData closeData = new LockCommandData(true, shutdown,
					TimeUnit.NANOSECONDS.convert(6, TimeUnit.SECONDS));
			log.info("shutdown:"+shutdown);
			closeData.setOperationPerson(loginId);
			SocketPool.commSocketThreadMap.get(doorControl.getAdrsComm()).addMsgToQueue(closeData);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	/**
	* @Description：根据门号和指令查询门的状态，有两个状态   :01开门、00关门,空的表示错误
	* @Author：hp
	* @Date：2013-5-14
	* @param doorIds
	* @param order
	* @param loginId  此参数可为空
	* @return
	**/
	@SuppressWarnings("unchecked")
	public List<Map> queryDoorStatus(List<Map> doorIds,String order,String loginId){
		if(doorIds == null || doorIds.size() == 0){
			log.info("初始化传入的参数为空!!");
			return null;
		}
		List<Map> result = new ArrayList<Map>();
		try {
			//组合命令
			Map<Object,List<Map>> maps = queryIpForCard(doorIds);
			Set set = maps.keySet();
			for (Object key : set) {
				result = doorControlToSystemService.findDoorStatus(key.toString(),doorIds,loginId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return result;	
	}
	
	/**
	* @Description：删除卡的权限
	* @Author：hp
	* @Date：2013-3-19
	* @param doorId
	* @param order
	* @param card
	* @return
	**/
	@SuppressWarnings("unchecked")
	public List<Map> deleteCardAuthority(List<Map> doorIds){
		if(doorIds == null || doorIds.size() == 0){
			log.info("初始化传入的参数为空!!");
			return null;
		}
		List<Map> result = null;
		try {
			
			//组合命令
			Map<Object,List<Map>> maps = queryIpForCard(doorIds);
			Set set = maps.keySet();
			for (Object key : set) {
				//得到构造有命令的list集合，判断返回空的话就不用执行查询了
				result = doorControlToSystemService.initDoor(key.toString(),doorIds,DoorControlToAppService.DELETE);
			}
		} catch (Exception e) {
			for (Map map : doorIds) {
				map.put("status", "1");
				result.add(map);
			}
			log.info(e.getMessage());
		}
		return result;
	}
	
	/**
	* @Description：门锁授权，取消授权
	* @Author：zhangqw
	* @Date：2013年6月21日9:54:29
	* @param checkedIds  此次被选中的userid
	* @param roomid  房间号
	**/
	@SuppressWarnings("unchecked")
	public  void initDoorAuthority(String roomid,String checkedIds, String loginId)
	{  
		
		log.info("checkedIds::"+checkedIds);
		List<String> addcardidList = new ArrayList<String>();
		String userid= null;
		StringTokenizer userStringTokenizer = new StringTokenizer(checkedIds,",");
		List<Map> resList = unitAdminManager.getRoomRes(Objtank.EXT_RES_TYPE_3,roomid);

		Map<String, String> roomidMap = new HashMap<String, String>();
		roomidMap.put("roomid", roomid);
		
		List<String> alluseriList = new ArrayList<String>();
		// 查询此次选中但数据库中没有记录的userid add by zhangqw begin
		while (userStringTokenizer.hasMoreTokens())
		{    
			userid = userStringTokenizer.nextToken();
			if(userid.endsWith("-u"))
			{   
				userid = userid.substring(0, userid.indexOf("-u"));
				alluseriList.add(userid);
				Map<String, String> useridmap = new HashMap<String, String>();
				useridmap.put("userid", userid);
				useridmap.put("nodeid", String.valueOf((resList.get(0).get("NODE_ID"))));
				log.info("useridmap:::"+useridmap);
				// 根据roomid查询查询通信相关信息
				List<Map> useridCount = this.findListBySql("PortSQL.selectCheckedId", useridmap);
				if (null != useridCount  &&  0 == Integer.parseInt(String.valueOf(useridCount.get(0).get("USERNUM"))))
				{
					addcardidList.add(userid);
				}
			}
		}
		
		try 
		{ 
			// 发送新增授权
			for(int i = 0;i<addcardidList.size();i++)
			{   
				addDoorAuth(addcardidList.get(i), loginId, roomid,null);
			}	
		} 
		catch (Exception e)
		{
			log.info(e);
		}
		
		List<String> deletecardidList = new ArrayList<String>();
		log.info("alluseriList:::"+alluseriList.toString());
		
		Map<String, Object> useridmap = new HashMap<String, Object>();
		useridmap.put("userids", alluseriList);
		useridmap.put("nodeid", String.valueOf(resList.get(0).get("NODE_ID")));
		
		// 查询此次没有选中但之前选中的userid
		List<Map> useridNotselect= this.findListBySql("PortSQL.notSelCheckedId", useridmap);
		
		for (int i = 0; i < useridNotselect.size(); i++) 
		{  	
			deletecardidList.add(String.valueOf(useridNotselect.get(i).get("PERSON_ID")));
		}
		
		log.info("deletecardidList:::"+deletecardidList.toString());
		try 
		{   
			// 取消授权
			for(int i = 0;i<deletecardidList.size();i++)
			{   
				delDoorAuth(deletecardidList.get(i), loginId, roomid,null);
			}	
		} 
		catch (Exception e)
		{
			log.info(e);
		}
		
	}
	
	/**
	 * 删除单个用户的门锁权限
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId 市民卡号
	 */
	public void addDoorAuth(String userId, String loginId, String roomId,String cardId) {
		addDoorAuth(userId, loginId, roomId, cardId, false, false);
	}
	
	/**
	 * 删除单个用户的门锁权限
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId 市民卡号
	 */
	public void addDoorAuth(String userId, String loginId, String roomId,String cardId, boolean isForce) {
		addDoorAuth(userId, loginId, roomId, cardId, isForce, false);
	}
	
	/**
	 * 添加单个用户的门锁权限
	 * @param userId
	 * @param loginId
	 * @param roomId
	 */
	public void addDoorAuth(String userId, String loginId, String roomId, String cardId, boolean isForce, boolean isDeny) {
		try {
			String newOrder = null;

			Map<String, String> roomidMap = new HashMap<String, String>();
			roomidMap.put("roomid", roomId);
			// 根据roomid查询查询通信相关信息
			Map doorInfo = new HashMap();
			
			List l = this.findListBySql("PortSQL.doorInitAuth",
					roomidMap);
			if (l != null && l.size() > 0) {
				doorInfo = (Map) l.get(0);
			} else {
				return;
			}
			
			List<Map> cardidList = null;
			Map<String, Object> useridMap = new HashMap<String, Object>();
			if (StringUtils.isEmpty(cardId)) {
				useridMap.put("userid", userId);
				cardidList = this.findListBySql("PortSQL.selcrIDByusID",
						useridMap);
				if (null != cardidList && cardidList.size() > 0) {
					cardId = String.valueOf(cardidList.get(0).get("CARD"));
				}
			}

			if (null == cardId || "null".equals(cardId)) {
				List<Map> unnormalCard = this.findListBySql(
						"PortSQL.getUnnormaCard", useridMap);
				if (unnormalCard != null && unnormalCard.size() > 0) {
					Map uunormalCardInfo = unnormalCard.get(0);
					Map doorExpMap = new HashMap();
					doorExpMap.put("CARD", String.valueOf(uunormalCardInfo
							.get("CARD_ID")));
					doorExpMap.put("NODEID", doorInfo.get("NODEID").toString());
					doorExpMap.put("ADRSNUM", "256");
					doorExpMap.put("EXP1", "2");
					// 市民卡状态错误
					if (!"0".equals(String.valueOf(uunormalCardInfo
							.get("CARDSTATUS")))) {
						doorExpMap.put("EXP3", "0");
					}
					// 市民卡是黑名单
					if (!"0".equals(String.valueOf(uunormalCardInfo
							.get("CARD_LOSTED")))) {
						doorExpMap.put("EXP3", "2");
					}
					commonManager.saveDoor(doorExpMap);

					log.error("该用户卡号异常");
				} else {
					Map doorExpMap = new HashMap();
					doorExpMap.put("EXP2", userId);
					doorExpMap.put("NODEID", doorInfo.get("NODEID").toString());
					doorExpMap.put("EXP1", "2");
					commonManager.saveDoor(doorExpMap);

					log.error("该用户没有绑定任何卡号");
				}

				return;
			} else if ("noTempCard".equals(cardId)) {
				Map doorExpMap = new HashMap();
				doorExpMap.put("EXP2", userId);
				doorExpMap.put("NODEID", doorInfo.get("NODEID").toString());
				doorExpMap.put("EXP1", "2");
				commonManager.saveDoor(doorExpMap);

				log.error("该用户没有绑定任何卡号");
				return;
			}
			
			if (doorInfo.get("NODEID") != null && StringUtil.isNotBlank(doorInfo.get("NODEID").toString())) {
				List<ObjPermMap> expList = dao.findByHQL("from ObjPermMap m where m.personId = ? and nodeId=? and type='user' " +
						"and permCode='obj_vis' and denyFlag='1'", Long.valueOf(userId), Long.valueOf(doorInfo.get("NODEID").toString()));
				
				if (expList != null && expList.size() > 0) {
					log.info("该用户已经对门锁添加过授权！");
				} else {
					newOrder = convertOrderInfo(doorInfo.get("NODEID").toString(),
							Constants.DBMAP.get("WS_DOORLOCK_INITDELAUTHORDER"),
							doorInfo.get("ADRSSTORE").toString(), doorInfo.get(
									"ADRSCOMM").toString(), String.valueOf(cardId),
							DoorControlToAppService.INIT, "00");
					
					Map doorExpMap = new HashMap();
					doorExpMap.put("CARD", cardId);
					doorExpMap.put("NODEID", doorInfo.get("NODEID").toString());
					doorExpMap.put("EXP1", "0");
					doorExpMap.put("ADRSNUM", "256");
					commonManager.saveDoor(doorExpMap);

					LockCommandData openData = new LockCommandData(true, newOrder,
							TimeUnit.NANOSECONDS.convert(0, TimeUnit.SECONDS));
					openData.setUserId(userId);
					openData.setOperationPerson(loginId);
					openData.setCardId(cardId);
					openData.setForce(isForce);
					openData.setDeny(isDeny);
					if (SocketPool.commSocketThreadMap.get(doorInfo.get(
							"ADRSCOMM").toString()) != null
							&& SocketPool.commSocketThreadMap.get(
									doorInfo.get("ADRSCOMM").toString())
									.isAliveFlag()) {
						SocketPool.commSocketThreadMap.get(
								doorInfo.get("ADRSCOMM").toString())
								.addMsgToQueue(openData);
					} else {
						commonManager.updateDoor(doorExpMap, "2", "");
					}

					log.info(userId + ":newOrder:" + newOrder);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 删除单个用户的门锁权限
	 * 
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId
	 *            市民卡号
	 */
	public void delDoorAuth(String userId, String loginId, String roomId,
			String cardId) {
		delDoorAuth(userId, loginId, roomId, cardId, false, false);
	}

	/**
	 * 删除单个用户的门锁权限
	 * 
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId
	 *            市民卡号
	 */
	public void delDoorAuth(String userId, String loginId, String roomId,
			String cardId, boolean isForce) {
		delDoorAuth(userId, loginId, roomId, cardId, isForce, false);
	}

	/**
	 * 删除单个用户的门锁权限
	 * 
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId
	 *            市民卡号
	 */
	public void delDoorAuth(String userId, String loginId, String roomId,
			String cardId, boolean isForce, boolean isDeny) {
		try {
			Map<String, String> roomidMap = new HashMap<String, String>();
			roomidMap.put("roomid", roomId);
			// 根据roomid查询查询通信相关信息
			Map doorInfo = (Map) this.findListBySql("PortSQL.doorInitAuth",
					roomidMap).get(0);

			Map<String, Object> useridMap = new HashMap<String, Object>();
			useridMap.put("userid", userId);
			if (StringUtils.isEmpty(cardId)) {
				List<Map> cardidList = this.findListBySql(
						"PortSQL.selcrIDByusID", useridMap);
				cardId = String.valueOf(cardidList.get(0).get("CARD"));
			}

			Map<String, String> useAdsumMap = new HashMap<String, String>();
			useAdsumMap.put("nodeid", doorInfo.get("NODEID").toString());
			useAdsumMap.put("cardid", cardId);
			List<Map> adrsnumList = this.findListBySql(
					"PortSQL.doorDeleteAuth", useAdsumMap);

			if (adrsnumList != null && adrsnumList.size() > 0) {
				for (Map adrsnum : adrsnumList) {
					String newOrder = convertOrderInfo(
							doorInfo.get("NODEID").toString(),
							Constants.DBMAP.get("WS_DOORLOCK_INITDELAUTHORDER"),
							doorInfo.get("ADRSSTORE").toString(),
							doorInfo.get("ADRSCOMM").toString(), cardId,
							DoorControlToAppService.DELETE,
							adrsnum.get("ADRSNUM").toString());

					LockCommandData openData1 = new LockCommandData(true,
							newOrder, TimeUnit.NANOSECONDS.convert(0,
									TimeUnit.SECONDS));
					openData1.setUserId(userId);
					openData1.setOperationPerson(loginId);
					openData1.setCardId(cardId);
					openData1.setForce(isForce);
					openData1.setDeny(isDeny);
					if (SocketPool.commSocketThreadMap.get(doorInfo.get(
							"ADRSCOMM").toString()) != null
							&& SocketPool.commSocketThreadMap.get(
									doorInfo.get("ADRSCOMM").toString())
									.isAliveFlag()) {
						SocketPool.commSocketThreadMap.get(
								doorInfo.get("ADRSCOMM").toString())
								.addMsgToQueue(openData1);
					} else {
						Map doorExpMap = new HashMap();
						doorExpMap.put("CARD", cardId);
						doorExpMap.put("NODEID", doorInfo.get("NODEID").toString());
						commonManager.updateDoor(doorExpMap, "3", null);
					}
					log.info("deletenewOrder:" + newOrder);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public boolean uploadDoorAuthCode(String commId) {
		if (SocketPool.commSocketThreadMap.get(commId) != null
				&& SocketPool.commSocketThreadMap.get(commId).isAliveFlag()) {
			LockCommandData openData1 = new LockCommandData(false,
					DoorControlToAppService.UPLOADAUTHCODE,
					TimeUnit.NANOSECONDS.convert(0, TimeUnit.SECONDS));

			SocketPool.commSocketThreadMap.get(commId).addMsgToQueue(openData1);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 临时卡过期失效处理
	 * 
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId
	 *            市民卡号
	 */
	public void tmpCardTimeout() {
		List<Map> tmpCardList = this.findListBySql("PortSQL.getTimoutTmpCard",
				null);
		String optType = "logout";

		if (tmpCardList != null && tmpCardList.size() > 0) {
			for (Map tmpCard : tmpCardList) {
				personROManager
						.updateOut((Long) tmpCard.get("USERID"), optType);
			}
		}
	}

	/**
	 * 临时人员过期失效处理
	 * 
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId
	 *            市民卡号
	 */
	public void tmpUserTimeout() {
		List<Map> tmpCardList = this.findListBySql("PortSQL.getTimoutTmpUser",
				null);
		String optType = "logout";

		if (tmpCardList != null && tmpCardList.size() > 0) {
			for (Map tmpCard : tmpCardList) {
				personROManager
						.updateOut((Long) tmpCard.get("USERID"), optType);
			}
		}
	}

	/**
	 * 检查门锁锁内存储地址
	 * 
	 * @param userId
	 * @param loginId
	 * @param roomId
	 * @param cardId
	 *            市民卡号
	 */
	public void checkDoorAuth() {
		List<Map> allDoorInfo = this.findListBySql(
				"PortSQL.getAllDoorAndCommInfo", null);

		if (allDoorInfo != null && allDoorInfo.size() > 0) {
			String[] headers = { "通信机号", "通信机状态", "房间号", "门锁状态", "存储地址",
					"实际卡号", "数据库中存储卡号", "卡号存储是否异常" };
			csvUtil.WriteHeader(headers, "checkDoorAuth", true);

			for (Map doorInfo : allDoorInfo) {
				for (int i = 0; i < 256; i++) {
					String newOrder = convertOrderInfo(
							String.valueOf(doorInfo.get("NODE_ID")), "3A",
							String.valueOf(doorInfo.get("ADRS_STORE")),
							String.valueOf(doorInfo.get("ADRS_COMM")));
					newOrder = newOrder + convertHex(String.valueOf(i));
					newOrder = newOrder + getXorVerificationCode(newOrder);
					LockCommandData openData = new LockCommandData(true,
							newOrder, TimeUnit.NANOSECONDS.convert(0,
									TimeUnit.SECONDS));
					openData.setNodeId(String.valueOf(doorInfo.get("NODE_ID")));
					SocketPool.commSocketThreadMap.get(
							doorInfo.get("ADRS_COMM").toString())
							.addMsgToQueue(openData);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
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
	public void checkCommAndDoorStatus() {
		String fileName = "checkCommAndDoorStatus";

		csvUtil.DeleteCsvFile(fileName);
		String[] headers = { "通信机号", "通信机状态", "房间号", "门锁名称", "门锁状态" };
		csvUtil.WriteHeader(headers, fileName, false);

		List<NjhwDoorcontrolSet> doorList = dao
				.findByHQL("from NjhwDoorcontrolSet");

		if (doorList != null && doorList.size() > 0) {
			for (NjhwDoorcontrolSet lockInfo : doorList) {
				String[] doorStatus = { "", "", "", "", "", "" };
				doorStatus[0] = lockInfo.getAdrsComm();
				if ("1".equals(lockInfo.getAdrsNum())) {
					doorStatus[1] = "异常";
				} else {
					doorStatus[1] = "正常";
				}
				doorStatus[2] = lockInfo.getAdrsStore();
				doorStatus[3] = lockInfo.getName();
				if ("2".equals(lockInfo.getAdrsNum())
						|| "1".equals(lockInfo.getAdrsNum())) {
					doorStatus[4] = "异常";
				} else {
					doorStatus[4] = "正常";
				}

				csvUtil.WriteOneLine(doorStatus, fileName, false);
			}
		}
	}

	// /**
	// * @Description：校对时间，每5分钟一次 系统定时
	// * @Author：hp
	// * @Date：2013-3-31
	// **/
	// @SuppressWarnings("unchecked")
	// public void checkTheTime(){
	// try {
	// StringBuffer sb = new StringBuffer();
	// String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	// String orders =
	// sb.append(Constants.DBMAP.get("WS_DOORLOCK_CHECKTIME")).append(DoorControlToAppService.EIGHTAUTHORITY).append(date).toString();
	// orders = orders+getXorVerificationCode(orders);
	// //从数据库查询到ip地址，循环调用通信层方法执行校对任务
	// List<Map> list = queryDoorEntity();
	// for (Map map : list) {
	// // doorControlToSystemService.checkTime(orders,
	// String.valueOf(map.get("adrsComm")));
	// String userId =
	// Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
	// LockCommandData openData1 = new LockCommandData(false, orders,
	// TimeUnit.NANOSECONDS.convert(0, TimeUnit.SECONDS));
	// SocketPool.commSocketThreadMap.get(String.valueOf(map.get("adrsComm"))).addMsgToQueue(openData1);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// log.info(e.getMessage());
	// }
	// }

	/**
	 * @Description：转换指令信息 -------主要针对的是所有功能的公共部分
	 * @Author：hp
	 * @Date：2013-3-30
	 * @param doorId
	 *            门的id
	 * @param order
	 *            命令
	 * @param doorAddress
	 *            门锁地址
	 * @param commMachineAddress
	 *            通讯机地址
	 * @return
	 **/
	public String convertOrderInfo(String doorId, String order,
			String doorAddress, String commMachineAddress) {
		if (StringUtils.isBlank(doorId) || StringUtils.isBlank(order)) {
			log.info("传入的参数为空!!");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(order).append(convertHex(doorAddress))
				.append(convertHex(commMachineAddress))
				.append(DoorControlToAppService.EIGHTAUTHORITY).toString();
		return sb.toString();
	}

	/**
	 * @Description：转换命令信息
	 * @Author：hp
	 * @Date：2013-3-30
	 * @param doorId
	 * @param order
	 * @param doorAddress
	 * @param commMachineAddress
	 * @param card
	 * @return
	 **/
	public String convertOrderInfo(String doorId, String order,
			String doorAddress, String commMachineAddress, String card,
			String type, String arsnum) {
		if (StringUtils.isBlank(doorId) || StringUtils.isBlank(order)) {
			log.info("传入的参数为空!!");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(order).append(convertHex(doorAddress))
				.append(convertHex(commMachineAddress))
				.append(DoorControlToAppService.EIGHTAUTHORITY);
		if (type.equals(DoorControlToAppService.INIT)) {
			if (card.length() == 16) {
				sb.append(card).append(convertHex(arsnum));
			} else if (card.length() == 12) {
				sb.append("0000" + card).append(convertHex(arsnum));
			}
			sb.append(DoorControlToAppService.FIRSTCARDTYPE);
		}
		if (type.equals(DoorControlToAppService.DELETE)) {
			sb.append(DoorControlToAppService.DELETELOCK).append(
					convertHex(arsnum));
			sb.append(DoorControlToAppService.FIRSTEXTCODE);
		}
		sb.append(getXorVerificationCode(sb.toString()));
		return sb.toString();
	}

	/**
	 * @Description：根据门的id查询数据库得到对象
	 * @Author：hp
	 * @Date：2013-3-30
	 * @param doorId
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	public NjhwDoorcontrolSet queryDoorEntity(String doorId) {
		if (StringUtils.isBlank(doorId)) {
			log.info("传入的参数为空!");
			return null;
		}
		Map map = new HashMap();
		map.put("nodeId", doorId);
		NjhwDoorcontrolSet doorControl = (NjhwDoorcontrolSet) this.sqlDao
				.getSqlMapClientTemplate().queryForObject("PortSQL.doorAuth",
						map);
		return doorControl;
	}

	/**
	 * @Description：查询所有door表中的数据
	 * @Author：hp
	 * @Date：2013-3-30
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	public List<Map> queryDoorEntity() {
		return this.findListBySql("PortSQL.doorAllAuth", null);
	}

	/**
	 * @Description：查询所有door表对应通信机中的数据
	 * @Author：hp
	 * @Date：2013-3-30
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	public List<Map> queryDoorEntityByComm(String adrsComm) {
		Map map = new HashMap();
		map.put("adrsComm", adrsComm);
		return this.findListBySql("PortSQL.doorAllAuth", map);
	}

	/**
	 * @Description：把十进制字符串转换成16进制字符串,传递命令需要
	 * @Author：hp
	 * @Date：2013-5-7
	 * @param addr
	 * @return
	 **/
	public static String convertHex(String order) {
		if (order == null || order == "") {
			log.info("参数不能为空!");
			return null;
		}
		// String od = Integer.toHexString(Integer.parseInt(order));
		String od = Long.toHexString(Long.parseLong(order));
		String result = "";
		if (od.length() == 1) {
			result = "0" + od;
		} else {
			result = od;
		}
		return result.toUpperCase();
	}

	/**
	 * @Description：查询出ip对应的卡还有门锁地址、通讯机地址
	 * @Author：hp
	 * @Date：2013-3-30
	 * @param doorIds
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	public Map<Object, List<Map>> queryIpForCard(List<Map> doorIds) {
		if (doorIds == null || doorIds.size() == 0) {
			log.info("传入的参数为空!");
			return null;
		}
		Map<Object, List<Map>> orderMap = new HashMap<Object, List<Map>>();
		List<Map> list = new ArrayList<Map>();
		for (Map map : doorIds) {
			if (orderMap.containsKey(map.get("ADRSIP"))) {
				List<Map> listTemp = orderMap.get(map.get("ADRSIP"));
				listTemp.add(map);
				orderMap.put(map.get("ADRSIP"), listTemp);
			} else {
				list.add(map);
				orderMap.put(map.get("ADRSIP"), list);
			}
		}
		return orderMap;
	}

	/**
	 * @Description：初始化和删除等操作用的转换指令操作
	 * @Author：hp
	 * @Date：2013-3-19
	 * @param doorId
	 * @param order
	 * @param card
	 * @param type
	 * @return
	 **/
	public String convertOrderInfo(String doorId, String order, String card,
			String type, String doorAddress, String commMachineAddress) {
		if (StringUtils.isBlank(doorId) || StringUtils.isBlank(order)
				|| StringUtils.isBlank(card)) {
			log.info("传入的参数为空!!");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		String common = convertOrderInfo(doorId, order, doorAddress,
				commMachineAddress);
		// 根据市民卡查询库中1字节存放地址的二字节数
		String firstByte = "";
		String result = "";
		if (type.equals(DoorControlToAppService.INIT)) {
			result = sb.append(common).append(card).append(firstByte)
					.append(DoorControlToAppService.REVERSELOCK).toString();
		}
		if (type.equals(DoorControlToAppService.DELETE)) {
			result = sb.append(common).append(card)
					.append(DoorControlToAppService.DELETELOCK)
					.append(firstByte)
					.append(DoorControlToAppService.FIRSTEXTCODE).toString();
		}
		return result;
	}

	/**
	 * @Description：保存门锁扩展表
	 * @Author：hp
	 * @Date：2013-5-19
	 * @param maps
	 **/
	@SuppressWarnings("unchecked")
	public void saveDoor(List<Map> maps) {
		NjhwDoorcontrolExp exp = new NjhwDoorcontrolExp();
		for (Map map : maps) {
			exp.setCardId(map.get("CARD").toString());
			exp.setDlockId(Long.valueOf(map.get("NODEID").toString()));
			exp.setAdrsNum(map.get("ADRSNUM").toString());
		}
		dao.save(exp);
		dao.flush();
	}

	/**
	 * @Description：删除门锁扩展表
	 * @Author：hp
	 * @Date：2013-5-19
	 * @param maps
	 **/
	@SuppressWarnings("unchecked")
	public void deleteDoor(List<Map> maps) {
		for (Map map2 : maps) {
			NjhwDoorcontrolExp exp = (NjhwDoorcontrolExp) dao
					.findUnique(
							"from NjhwDoorcontrolExp e where e.cardId = ? and e.dlockId = ?",
							map2.get("CARD").toString(),
							Long.valueOf(map2.get("NODEID").toString()));
			if (exp != null) {
				dao.delete(exp);
				dao.flush();
			}
		}
	}

	/**
	 * @Description：删除门锁扩展表
	 * @Author：hp
	 * @Date：2013-5-19
	 * @param maps
	 **/
	@SuppressWarnings("unchecked")
	public List findCardByUserID(StringTokenizer stringTokenizer) {
		List<NjhwTscard> cardList = new ArrayList<NjhwTscard>();
		String userid = null;
		while (stringTokenizer.hasMoreTokens()) {
			if (stringTokenizer.nextToken().endsWith("-u")) {
				userid = stringTokenizer.nextToken();
				userid = userid.substring(0, userid.indexOf("-u"));
			}

			NjhwTscard exp = (NjhwTscard) dao.findUnique(
					"from NjhwTscard e where e.userId = ? ",
					Long.valueOf(stringTokenizer.nextToken()));
			cardList.add(exp);
		}
		return cardList;
	}

	/**
	 * @Description：解析传递的结果得到门的状态 00表示关门 ，01表示开门
	 * @Author：hp
	 * @Date：2013-5-6
	 * @param result
	 * @return 刷卡开门：3C011F0100009701700488394D8BD6DD01BC,,远程开门：3
	 *         C011F0100000000000000014D8BD72E011C
	 **/
	public String findStatus(String result) {
		StringBuffer sb = new StringBuffer();
		sb.append(result.substring(6, 8));
		String order = sb.toString();
		if (order.equals("00") || order.equals("01")) {
			return sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * @Description：解析门的状态保存库
	 * @Author：hp
	 * @Date：2013-5-13
	 * @param result
	 * @param logid
	 * @return
	 **/
	public ExtInDlockLog saveDoorStatus(String result, String logid) {
		if (StringUtils.isBlank(result)) {
			log.info("参数不能为空!");
			return null;
		}
		if (result.substring(8, 24).toUpperCase().equals("FFFFFFFFFFFFFFFF")) {
			log.info("此条刷卡记录为空！");
			return null;
		}
		ExtInDlockLog doorLog = new ExtInDlockLog();
		try {
			doorLog.setAdrsStore(result.substring(2, 4));
			doorLog.setAdrsComm(result.substring(4, 6));
			doorLog.setDlStatus(Integer.parseInt(result.substring(6, 8)) + "");
			doorLog.setCardId(result.substring(8, 24));
			String time = result.substring(24, 32);
			time = String.valueOf(Integer.parseInt(time, 16));
			Date d = new Date();
			String s = new SimpleDateFormat("yyyy").format(d);
			Date dlDate = new SimpleDateFormat("yyyyMMddHHmm").parse(s
					.substring(0, 2) + time);
			doorLog.setDlDate(dlDate);
			doorLog.setDlFlag(Integer.parseInt(result.substring(32, 34)) + "");
			doorLog.setInsertDate(new Date());
			if (StringUtils.isNotBlank(logid)) {
				doorLog.setInsertId(Long.parseLong(logid));
			}
			dao.save(doorLog);
		} catch (ParseException e) {
			log.error(e);
		}
		return doorLog;
	}

	/**
	 * @Description：生成异或验证码
	 * @Author：hp
	 * @Date：2013-3-19
	 * @param xorText
	 * @return
	 **/
	public static String getXorVerificationCode(String xorText) {
		char[] b = xorText.toCharArray();
		int t = 0;
		int a = 0;
		int c = 0;
		String hexadecimal = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F";
		for (int i = 0; i < b.length; i++) {
			if (StringUtils.contains(hexadecimal, b[i])) {
				if (i % 2 == 0) {
					t = Integer.parseInt(String.valueOf(b[i]), 16);
					a ^= t;
				} else {
					t = Integer.parseInt(String.valueOf(b[i]), 16);
					c ^= t;
				}
			}
		}
		String result = Integer.toHexString(a) + Integer.toHexString(c);
		return result.toUpperCase();
	}

	public static void main(String[] args) throws Exception {
		DoorControlToAppService dct = new DoorControlToAppService();// 3C011F0000009701700488394D8BD6E10181
		// String result =
		// dct.findStatus("3C011F0100009701700488394D8BD6DD01BC");
		String result = "3C011F0100009701700488394D8BD6DD01BC";
		System.out.println(result.substring(2, 4));// 得到门锁地址
		System.out.println(result.substring(4, 6));// 通讯机地址
		System.out.println(result.substring(8, 24));// 卡号
		System.out.println(result.substring(24, 32));// 时间
		System.out.println(result.substring(32, 34));// 是否非法开门
		// System.out.println(Integer.parseInt("4DCABC06",16));201305132040
		Date da = new SimpleDateFormat("yyyyMMddHHmm").parse("201305132040");
		System.out.println(da);
		int time = Integer.parseInt("4DCABC06", 16);

	}

	public DoorControlToSystemService getDoorControlToSystemService() {
		return doorControlToSystemService;
	}

	public void setDoorControlToSystemService(
			DoorControlToSystemService doorControlToSystemService) {
		this.doorControlToSystemService = doorControlToSystemService;
	}

	public CommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}

	public void setPersonROManager(PersonRegOutManager personROManager) {
		this.personROManager = personROManager;
	}

	public PersonRegOutManager getPersonROManager() {
		return personROManager;
	}

	public void setOrgPerMgrManager(OrgMgrManager orgPerMgrManager) {
		this.orgPerMgrManager = orgPerMgrManager;
	}

	public OrgMgrManager getOrgPerMgrManager() {
		return orgPerMgrManager;
	}

}
