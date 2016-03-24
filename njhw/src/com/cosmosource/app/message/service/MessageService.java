package com.cosmosource.app.message.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.service.BaseManager;

public class MessageService extends BaseManager{
	//初始预约
	public static final String ChuShiYuYue 		= "00";
	//预约申请
	public static final String YuYueShenQing 	= "01";
	//申请确认
	public static final String ShenQingQueRen 	= "02";
	//申请拒绝
	public static final String ShenQingJuJue	= "03";
	//到访
	public static final String DaoFang 			= "04";
	//正常结束
	public static final String ZhengChangJieShu = "05";
	//异常结束
	public static final String YiChangJieShu 	= "06";
	//取消预约
	public static final String QuXiaoYuYue 		= "99";

	public void addMsgs(List<Map> list){
		sqlDao.batchInsert("MessageSql.insertMsg", list);
	}
	
	public List<Map> queryTitleMessage(Map map){
		List<Map> list = sqlDao.findList("MessageSql.queryTitleMsg", map);
		return list;
	}
	
	public boolean isFgwUser(String userId){
		Map params = new HashMap();
		params.put("userId", userId);
		List<Map> list = sqlDao.findList("MessageSql.isFgwAdmin", params);
		
		if(list.size() != 0){
			return true;
		}else{
			return false;
		}
	}
	
	public List<Map<String, String>> getFgwOA(String userId){
		Map params = new HashMap();
		params.put("userId", userId);
		List<Map<String, String>> list = sqlDao.findList("MessageSql.getFgwOA", params);
		return list;
	}
	
	public long getFgwOACount(String userId){
		Map params = new HashMap();
		params.put("userId", userId);
		List<Map<String, String>> list = sqlDao.findList("MessageSql.getFgwOACount", params);
		Map m = list.get(0);
		return Long.parseLong(m.get("COUNT").toString());
	}
	
	public Map<String, String> getFgwOAById(String id){
		Map params = new HashMap();
		params.put("id", id);
		List<Map<String, String>> list = sqlDao.findList("MessageSql.getFgwOAById", params);
		
		return list.get(0);
	}
	
	/**
	 * 查询首页我的访客消息显示
	 * @开发者：ywl
	 * @param map
	 * @return list
	 * @时间：2013-7-8
	 */
	public List<Map> queryTitleVisit(Map param){
		List<Map> list = sqlDao.findList("MessageSql.queryTitleVisit", param);
		return list;
	}
	
	/**
	 * 通过查询Message信息
	 * @param message
	 * @return
	 */
	public List<Map> queryMessage(Map params){
		List<Map> list = sqlDao.findList("MessageSql.queryMessage", params);
		return list;
	}
	
//	public List<MsgBox> queryMsg(MsgBox msg){
//		Map pa = null;
//		if(msg != null){
//			pa = msg.getMapVo();
//		}
//		List<Map> list = sqlDao.findList("MessageSql.queryMsg", pa);
//		
//		return mapToMsg(list);
//	}
	
//	public List queryCount(MsgBox msg){
//		Map pa = null;
//		if(msg != null)
//			pa = msg.getMapVo();
//		List count = sqlDao.findList("MessageSql.queryCountMsg", pa);
//		return count;
//	}
	
	public List queryCountBroad(Map map){
		List count = sqlDao.findList("MessageSql.queryCountVisit",map);
		return count;
	}
	
//	public List<MsgBox> mapToMsg(List<Map> list){
//		List<MsgBox> result = new ArrayList<MsgBox>();
//		
//		for(Map m : list){
//			result.add(new MsgBox().setFromMap(m));
//		}
//		
//		return result;
//	}
	
	/**
	 * 通过查询Message信息
	 * @param message
	 * @return
	 */
	public List<Map> queryVisitorDetailInfo(Map param){
		List<Map> list = sqlDao.findList("MessageSql.queryVisitorDetailInfo", param);
		return list;
	}
	
	/**
	 * 查询公告接收者是否收到
	 * @param message
	 * @return
	 */
//	public List<Map> queryRead(MsgRead msgRead){
//		Map pa = null;
//		if(msgRead != null){
//			pa = msgRead.getMapVo();
//		}
//		List<Map> list = sqlDao.findList("MessageSql.queryRead", pa);
//		return list;
//	}
	
//	public void addMessageWithReceivers(Message message, List<User> reciver){
//		
//		List<Map> messageList = new ArrayList<Map>();
//		messageList.add(message.getMapVo());
//		
//		List<Map> messageUsersList = new ArrayList<Map>();
//		for(User u : reciver){
//			MessageUsers mu = new MessageUsers();
//			mu.setMessageId(message.getId());
//			mu.setUsersId(u.getId());
//			//status 0 表示信息还没有被读取
//			mu.setStatus("0");
//			
//			messageUsersList.add(mu.getMapVo());
//		}
//		
//		sqlDao.batchInsert("MessageSql.insertMessage", messageList);
//		
//		sqlDao.batchInsert("MessageSql.insertMessageUsers", messageUsersList);
//	}
	
	public List<Map> queryVisitor(long userId){
		Map map = new HashMap();
		map.put("userId", userId);
		List<Map> list = sqlDao.findList("MessageSql.queryVisitor", map);
		return list;
	}
	
	public List<Map> queryVisitor(Map map){
		List<Map> list = sqlDao.findList("MessageSql.queryVisitor", map);
		return list;
	}
	
	public List<Map> queryOA(long userId){
		
		String userName = getUserName(userId);
		
		if(userName == null){
			return null;
		}
		
		Map map = new HashMap();
		map.put("name", userName);
		List<Map> list = sqlDao.findList("MessageSql.queryOA", map);
		return list;
	}
	
	public long getMsgCount(long userId){
		Map map = new HashMap();
		map.put("userId", userId);
		List<Map> list = sqlDao.findList("MessageSql.queryMsgCount", map);
		Map m = list.get(0);
		return parseLongValue(m.get("NUM"));
	}
	
	public long getVisitorCount(long userId){
		Map map = new HashMap();
		map.put("userId", userId);
		List<Map> list = sqlDao.findList("MessageSql.queryVisitorCount", map);
		Map m = list.get(0);
		return parseLongValue(m.get("NUM"));
	}
	
	/**
	 * 查询未读的visitor记录
	 * @param userId
	 * @return
	 */
	public long getVisitorNotReadCount(long userId){
		Map map = new HashMap();
		map.put("userId", userId);
		List<Map> list = sqlDao.findList("MessageSql.queryVisitorNotReadCount", map);
		Map m = list.get(0);
		return parseLongValue(m.get("NUM"));
	}
	
	public long getOACount(long userId){
		Map map = new HashMap();
		map.put("userId", userId);
		List<Map> list = sqlDao.findList("MessageSql.queryOACount", map);
		Map m = list.get(0);
		return parseLongValue(m.get("NUM"));
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
	
	public String getUserName(long id){
		Map map = new HashMap();
		map.put("id", id);
		List<Map> list = sqlDao.findList("MessageSql.getUserName", map);
		if(list == null || list.size() == 0){
			return null;
		}else{
			Map m = list.get(0);
			return m.get("NAME").toString();
		}
	}
	
	public long queryMsgNotRead(long id){
		Map map = new HashMap();
		map.put("receiverId", id);
		List<Map> list = sqlDao.findList("MessageSql.queryNotReadMsgCount", map);
		if(list == null || list.size() == 0){
			return 0;
		}else{
			Map m = list.get(0);
			return parseLongValue(m.get("XX").toString());
		}
	}
	
	//queryMsgCount
	public long queryMsgCount(Map map){
		List<Map> list = sqlDao.findList("MessageSql.queryCountMsg", map);
		if(list == null || list.size() == 0){
			return 0;
		}else{
			Map m = list.get(0);
			return parseLongValue(m.get("XX").toString());
		}
	}
	
	// 根据ID 修改我的访客消息的状态只读/未读
	public void updateVisitRead(long id){
		Map map = new HashMap();
		map.put("vsId", id);
		List<Map> list = new ArrayList<Map>();		
		list.add(map);
		sqlDao.batchUpdate("MessageSql.updateVisitRead", list);
	}
	
	//根据ID 删除我的消息
	public void deleteMsgs(String[] ids){
		List<String> list = Arrays.asList(ids);
		sqlDao.batchDelete("MessageSql.delById", list);
	}
	
	public String getUcode(String id){
		Map map = new HashMap();
		map.put("id", id);
		List<Map> list = sqlDao.findList("MessageSql.queryUCode", map);
		if(list == null || list.size() == 0){
			return null;
		}else{
			Map m = list.get(0);
			return m.get("UCODE").toString();
		}
	}
	
	public List<Map> queryVisitSortForTilte(String userId){
		List<Map> result = new LinkedList<Map>();
		
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("vsFlag", YuYueShenQing);
		result.addAll(sqlDao.findList("MessageSql.queryVisitSortForTilte", params));
		if(result.size() >= 5){
			return result;
		}
		
		params.put("vsFlag", DaoFang);
		result.addAll(sqlDao.findList("MessageSql.queryVisitSortForTilte", params));
		if(result.size() >= 5){
			return result;
		}
		
		params.remove("vsFlag");
		params.put("anyFlag", "anyFlag");
		result.addAll(sqlDao.findList("MessageSql.queryVisitSortForTilte", params));
		
		return result;
		
	}
	
	public List<Map> test(){
		List<Map> list = sqlDao.findList("MessageSql.test", new HashMap());
		return list;
	}
	
	public Map getUsrPwdByUserId(Map param){
		List<Map> list = sqlDao.findList("MessageSql.getUsrPwdByUserId", param);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}