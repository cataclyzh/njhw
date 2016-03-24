package com.cosmosource.app.transfer.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.BmMonitor;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.CSVUtil;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.StringUtil;

/**
 * @ClassName:LogControlRecord
 * @Description：日志记录
 * @Author：hp 
 * @Date:2013-3-25
 */
public class LogControlRecord extends BaseManager{
	
	private static final Log log = LogFactory.getLog(LogControlRecord.class);
	
	public CSVUtil csvUtil;

	public CSVUtil getCsvUtil() {
		return csvUtil;
	}

	public void setCsvUtil(CSVUtil csvUtil) {
		this.csvUtil = csvUtil;
	}

	/**
	* @Description：记录操作人和操作时间
	* @Author：hp
	* @Date：2013-3-25
	* @param operationPerson
	* @return
	**/
	public BmMonitor recordLog(String operationPerson){
		if(StringUtils.isBlank(operationPerson)){
			log.info("操作人参数不能为空!");
			return null;
		}
		BmMonitor monitor = new BmMonitor();
		try{
			List<Users> users = dao.findByHQL("from Users u where u.userid = ?", Long.valueOf(operationPerson));
			if(CollectionUtils.isNotEmpty(users)){
				for (Users user : users) {
					monitor.setInsertId(user.getUserid());
					monitor.setInsertName(user.getLoginUid());
					monitor.setInsertDate(new Date());
					dao.save(monitor);
				}
			}
		}catch(NumberFormatException e){
			log.info("数字不能转换字符串:"+operationPerson+"不能是Long型的uerid");
		}catch(ClassCastException e){
			log.info("数据库中的数据不正确!");
		}
		return monitor;
	}
	
	
	/**
	* @Description：记录操作人和操作时间还有资源ID
	* @Author：hp
	* @Date：2013-3-25
	* @param operationPerson
	* @param siteId
	* @return
	**/
	public BmMonitor recordLog(String operationPerson,String siteId){
		if(StringUtils.isBlank(operationPerson) || StringUtils.isBlank(siteId)){
			log.info("操作人和设备号参数不能为空!");
			return null;
		}
		BmMonitor monitor = recordLog(operationPerson);
		monitor.setResId(Long.valueOf(siteId));
		monitor.setBmType("3");
		return monitor;
	}
	
	
	/**
	* @Description：记录控制方向 
	* @Author：hp
	* @Date：2013-3-25
	* @param operationPerson
	* @param siteId
	* @param operationDirection
	* @return
	**/
	public BmMonitor recordLog(String operationPerson,String siteId,String operationDirection){
		BmMonitor monitor = recordLog(operationPerson,siteId);
		monitor.setBmExp2(operationDirection);
		return monitor;
	}
	
	
	/**
	* @Description：记录控制内容
	* @Author：hp
	* @Date：2013-3-25
	* @param operationPerson
	* @param siteId
	* @param operationDirection
	* @param operationContent
	* @return
	**/
	@SuppressWarnings("unchecked")
	public void recordLog(String operationPerson,String siteId,String operationDirection,String operationContent){
		try{
			List<Users> users = dao.findByHQL("from Users u where u.userid = ?", Long.valueOf(operationPerson));
			if(CollectionUtils.isNotEmpty(users)){
				for (Users user : users) {
					BmMonitor monitor = new BmMonitor();
					monitor.setInsertId(user.getUserid());
					monitor.setInsertName(user.getLoginUid());
					monitor.setInsertDate(new Date());
					monitor.setResId(Long.valueOf(siteId));
					monitor.setBmType("3");
					monitor.setBmExp2(operationDirection);
					monitor.setBmDetail(operationContent);
					dao.save(monitor);
				}
			}
		}catch(NumberFormatException e){
			log.info("数字不能转换字符串:"+operationPerson+"不能是Long型的uerid");
		}catch(ClassCastException e){
			log.info("数据库中的数据不正确!");
		}
	}
	
	/**
	* @Description：记录门锁控制内容
	* @Author：hp
	* @Date：2013-3-25
	* @param operationPerson
	* @param userId
	* @param siteId
	* @param operationDirection
	* @param operationContent
	* @param msgid
	* @param isSuccess
	* @return
	**/
	@SuppressWarnings("unchecked")
	public void recordLog(String operationPerson, String userId, String siteId, String operationDirection, String order,String msgid, int bak, boolean isSuccess){
		try{
			String fileName = "doorMonitorLog";
			
			String operationContent = "";
			if ("36FF".equals(order)) {
				operationContent = "发送删除门锁授权指令";
			} else if ("3602".equals(order)) {
				operationContent = "发送添加门锁授权指令";
			} else if ("38".equals(order)) {
				operationContent = "发送门锁开门指令";
			} else if ("39".equals(order)) {
				operationContent = "发送门锁开门指令";
			} else if ("3C".equals(order)) {
				operationContent = "读取门锁状态";
			} else if ("32".equals(order)) {
				operationContent = "上传授权码指令";
			}
			
			if (StringUtils.isNumeric(operationPerson)) {
				List<Users> users = dao.findByHQL("from Users u where u.userid = ?", Long.valueOf(operationPerson));
				
				if(CollectionUtils.isNotEmpty(users)){
					for (Users user : users) {
						BmMonitor monitor = new BmMonitor();
						monitor.setInsertId(user.getUserid());
						monitor.setInsertName(user.getLoginUid());
						monitor.setInsertDate(new Date());
						monitor.setResId(Long.valueOf(siteId));
						monitor.setBmType("3");
						monitor.setBmExp2(operationDirection);
						monitor.setBmExp3(msgid);
						if (!"32".equals(order)) {
							monitor.setBmExp4(isSuccess? "YES" : "NO");
						}
						monitor.setBmDetail(operationContent);
						dao.save(monitor);
					}
				}
			} else {
				
				BmMonitor monitor = new BmMonitor();
				monitor.setInsertDate(new Date());
				monitor.setResId(Long.valueOf(siteId));
				monitor.setBmType("3");
				monitor.setBmExp2(operationDirection);
				monitor.setBmExp3(msgid);
				if (!"32".equals(order)) {
					monitor.setBmExp4(isSuccess? "YES" : "NO");
				}
				monitor.setBmDetail(operationContent);
				dao.save(monitor);
			}
			
			//csvUtil.WriteHeader(new String[]{"操作时间", "房间", "人员", "部门", "卡号", "电话", "操作", "操作结果", "备注", "操作人"}, fileName, true);
			
			String[] doorMonitor = {"","","","","","","","","",""};
			
			doorMonitor[0] = DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd HH:mm:ss:SS");
			
			if (StringUtils.isNotBlank(siteId)) {
				Map roomMap =  new HashMap<String, String>();
				roomMap.put("roomId", siteId);
				List<Map> roomList =  this.findListBySql("PortSQL.getRoomInfoByRoomId", roomMap);
				if (roomList != null && roomList.size() > 0) {
					doorMonitor[1] = String.valueOf(roomList.get(0).get("NAME"));
				}
			}
			
			if (StringUtils.isNotBlank(userId)) {
				Map userMap =  new HashMap<String, String>();
				userMap.put("userid", Long.parseLong(userId));
				List<Map> userList =  this.findListBySql("PortSQL.getUserInfo", userMap);
				if (userList != null && userList.size() > 0) {
					doorMonitor[2] = StringUtils.defaultString((String) userList.get(0).get("DISPLAY_NAME")).trim();
					doorMonitor[3] = StringUtils.defaultString((String) userList.get(0).get("SHORT_NAME")).trim();
					doorMonitor[4] = StringUtils.defaultString((String) userList.get(0).get("CARDID")).trim();
					doorMonitor[5] = StringUtils.defaultString((String) userList.get(0).get("TEL_NUM")).trim();
				}
			}
			
			doorMonitor[6] = operationContent;
			if (!"32".equals(order)) {
				doorMonitor[7] = isSuccess? "成功" : "失败";
				if (bak != 1) {
					if (StringUtils.isNotBlank(siteId)) {
						dao.batchExecute("update NjhwDoorcontrolSet set adrsNum= '' "
								+ "where adrsComm = (select t.adrsComm from NjhwDoorcontrolSet t, Objtank ot "
								+ "where t.nodeId = ot.nodeId and ot.PId = ? and ot.extResType = '3') and adrsNum = '1'", NumberUtil.strToLong(siteId));	
					}
				}
				if (bak == 1) {
					if (StringUtils.isNotBlank(siteId)) {
						dao.batchExecute("update NjhwDoorcontrolSet set adrsNum= '1' "
								+ "where adrsComm = (select t.adrsComm from NjhwDoorcontrolSet t, Objtank ot "
								+ "where t.nodeId = ot.nodeId and ot.PId = ? and ot.extResType = '3')", NumberUtil.strToLong(siteId));	
					}
					doorMonitor[8] = "通信机连接异常！";
				} else if (bak == 2) {
					if (StringUtils.isNotBlank(siteId)) {
						dao.batchExecute("update NjhwDoorcontrolSet set adrs_Num= '2' "
								+ "where nodeId = (select t.nodeId from NjhwDoorcontrolSet t, Objtank ot "
								+ "where t.nodeId = ot.nodeId and ot.PId = ? and ot.extResType = '3')", NumberUtil.strToLong(siteId));	
					}
					doorMonitor[8] = "门锁接收超时！";
				} else if (bak == 3) {
					doorMonitor[8] = "未知异常！";
				} else if (bak == 0) {
					if (StringUtils.isNotBlank(siteId)) {
						dao.batchExecute("update NjhwDoorcontrolSet set adrsNum= '' "
								+ "where nodeId = (select t.nodeId from NjhwDoorcontrolSet t, Objtank ot "
								+ "where t.nodeId = ot.nodeId and ot.PId = ? and ot.extResType = '3')", NumberUtil.strToLong(siteId));	
					}
				}
			}
			
			if (StringUtils.isNumeric(operationPerson)) {
				List<Users> users = dao.findByHQL("from Users u where u.userid = ?", Long.valueOf(operationPerson));
				
				if (users != null && users.size() > 0) {
					doorMonitor[9] = StringUtils.defaultString((String) users.get(0).getDisplayName());
				}
				
				//csvUtil.WriteOneLine(doorMonitor, fileName, true);

			} else {
				if (StringUtils.isNotBlank(operationPerson) ) {
					Map telInfo = new HashMap();
					telInfo.put("mac", operationPerson);
					List<Map> lists= this.findListBySql("PortSQL.getUserInfoByMac", telInfo);
					if (lists != null && lists.size() > 0) {
						doorMonitor[2] = StringUtils.defaultString((String) lists.get(0).get("DISPLAY_NAME")).trim();
						doorMonitor[3] = StringUtils.defaultString((String) lists.get(0).get("SHORT_NAME")).trim();
						doorMonitor[5] = StringUtils.defaultString((String) lists.get(0).get("TEL_NUM")).trim();
						doorMonitor[9] = StringUtils.defaultString((String) lists.get(0).get("DISPLAY_NAME")).trim();
					}
				}
				
				//csvUtil.WriteOneLine(doorMonitor, fileName, true);
			}
		}catch(NumberFormatException e){
			log.error("数字不能转换字符串:"+operationPerson+"不能是Long型的uerid");
		}catch(ClassCastException e){
			log.error("数据库中的数据不正确!");
		}catch(Exception e) {
			log.error(e);
		}
	}
	
	/**
	* @Description：记录门禁闸机操作记录
	* @Author：hj
	* @Date：2013-09-05
	* @param operationPerson
	* @param siteId
	* @return
	**/
	public void recordAccessLog(Map userInfo){
		
		BmMonitor monitor = new BmMonitor();
		monitor.setBmType("1");
	
		if ("add".equals(userInfo.get("opt").toString())) {
			monitor.setBmDetail("添加门禁闸机授权");
			monitor.setBmExp3(userInfo.get("RIGHTLEVEL").toString());
		} else if ("modify".equals(userInfo.get("opt").toString())) {
			monitor.setBmDetail("更新门禁闸机授权");
			monitor.setBmExp3(userInfo.get("RIGHTLEVEL").toString());
		} else if ("delete".equals(userInfo.get("opt").toString())) {
			monitor.setBmDetail("删除门禁闸机授权");
		}
		
		if (StringUtil.isNotBlank(userInfo.get("result").toString())) {
			StringBuffer sb = new StringBuffer();
			sb.append("操作结果：失败；用户名：").append(userInfo.get("DISPLAYNAME")).append("；用户编号：");
			sb.append(userInfo.get("ACCESSID"));
			sb.append("；失败理由：").append(userInfo.get("result"));
			
			monitor.setBmExp2(sb.toString());
			monitor.setBmExp4("NO");
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("操作结果：成功；用户名：").append(userInfo.get("DISPLAYNAME")).append("；用户编号：");
			sb.append(userInfo.get("ACCESSID"));
			monitor.setBmExp2(sb.toString());
			monitor.setBmExp4("YES");
		}
		
		if (StringUtil.isNotBlank(userInfo.get("LOGINID").toString())) {
			monitor.setInsertId(Long.parseLong(userInfo.get("LOGINID").toString()));
			Users u = (Users) dao.findById(Users.class, Long.parseLong(userInfo.get("LOGINID").toString()));
			if (u != null) {
				monitor.setInsertName(u.getDisplayName().trim());
			}
		} else {
			monitor.setInsertName("黑名单定时任务");
		}
		monitor.setInsertDate(new Date());
		
		dao.save(monitor);
	}
}
