package com.cosmosource.app.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.ExtInDlockLog;
import com.cosmosource.app.entity.NjhwDoorcontrolExp;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.ObjPermMap;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.Struts2Util;

public class CommonManager extends BaseManager{
	
	
	public static final Log log = LogFactory.getLog(CommonManager.class);
	
	private DoorControlToAppService doorControlToAppService;

	/**
	* @Description：保存门锁扩展表
	* @Author：hp
	* @Date：2013-5-19
	* @param maps
	**/
	@SuppressWarnings("unchecked")
	public void saveDoor(Map map){
		NjhwDoorcontrolExp exp = new NjhwDoorcontrolExp();
		if (map.get("CARD") != null) {
			exp.setCardId(map.get("CARD").toString());
		}
		exp.setDlockId(Long.valueOf(map.get("NODEID").toString()));
		if (map.get("ADRSNUM") != null) {
			exp.setAdrsNum(map.get("ADRSNUM").toString());
		}
		if (map.get("EXP1") != null) {
			exp.setExp1(map.get("EXP1").toString());
		}
		if (map.get("EXP2") != null) {
			exp.setExp2(map.get("EXP2").toString());
		}
		if (map.get("EXP3") != null) {
			exp.setExp3(map.get("EXP3").toString());
		}
		dao.save(exp);
		dao.flush();
	}
	
	
	/**
	* @Description：检索门锁扩展表
	* @Author：hp
	* @Date：2013-5-19
	* @param maps
	**/
	@SuppressWarnings("unchecked")
	public NjhwDoorcontrolExp searchDoor(Map map){
		NjhwDoorcontrolExp exp = (NjhwDoorcontrolExp) dao.findUnique("from NjhwDoorcontrolExp e where e.cardId = ? and e.dlockId = ?", map.get("CARD").toString(),Long.valueOf(map.get("NODEID").toString()));
		return exp;
	}
	
	/**
	* @Description：检索门锁扩展表中的卡号
	* @Author：hp
	* @Date：2013-5-19
	* @param maps
	**/
	@SuppressWarnings("unchecked")
	public List<NjhwDoorcontrolExp> searchDoorCard(Map map){
		List<NjhwDoorcontrolExp> expList = dao.findByHQL("from NjhwDoorcontrolExp e where e.adrsNum = ? and e.dlockId = ?", map.get("ADRSNUM").toString(),Long.valueOf(map.get("NODEID").toString()));
		return expList;
	}
	
	/**
	* @Description：删除门锁扩展表
	* @Author：hp
	* @Date：2013-5-19
	* @param maps
	**/
	@SuppressWarnings("unchecked")
	public void deleteDoor(Map map){
		List<NjhwDoorcontrolExp> expList = dao.findByHQL("from NjhwDoorcontrolExp e where e.cardId = ? and e.dlockId = ?", map.get("CARD").toString(),Long.valueOf(map.get("NODEID").toString()));
		
		if (expList != null) {
			for (NjhwDoorcontrolExp exp : expList) {
				dao.delete(exp);
			}
		}
		
		dao.flush();
	}
	
	/**
	* @Description：根据ID删除门锁扩展表
	* @Author：hj
	* @Date：2013-8-1
	* @param id
	**/
	@SuppressWarnings("unchecked")
	public void deleteDoor(Long id){
		dao.deleteById(NjhwDoorcontrolExp.class, id);
		dao.flush();
	}
	
	/**
	* @Description：更新门锁扩展表
	* @Author：hp
	* @Date：2013-5-19
	* @param maps
	**/
	@SuppressWarnings("unchecked")
	public void updateDoor(Map map, String exp1, String adrsNum){
		List<NjhwDoorcontrolExp> expList = dao.findByHQL("from NjhwDoorcontrolExp e where e.cardId = ? and e.dlockId = ?", map.get("CARD").toString(),Long.valueOf(map.get("NODEID").toString()));
		
		if (expList != null && expList.size() > 0) {
			for (NjhwDoorcontrolExp exp : expList) {
				exp.setExp1(exp1);
				if (adrsNum != null) {
					exp.setAdrsNum(adrsNum);
				}
				if(exp != null){
					dao.update(exp);
				}
			}
		}

		dao.flush();
	}
	
	
	/**
	* @Description：根据门锁id查询一字节码
	* @Author：hp
	* @Date：2013-5-20
	* @param map
	* @return
	**/
	@SuppressWarnings("unchecked")
	public Map findAdrsNum(Map map){
		Map result = new HashMap();
		try {
			// 根据和seq表做关联后找出某一个门锁中未授权的1字节存储地址
			List<Map> doorSetExps = this.findListBySql("PortSQL.doorAdrNum", map);
			if (doorSetExps != null && doorSetExps.size() > 0) {
				for (int i = 0; i < 255; i++) {
					int j = Integer.parseInt(String.valueOf(doorSetExps.get(i).get("ADRS_NUM")));
					if (i < j) {
						result.put("ADRSNUM", i);
						break;
					}
				}
			} else {
				result.put("ADRSNUM", 0);
			}
			result.putAll(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	/** 
	* @Title: findAdrsNumCancel 
	* @Description: 删除门锁权限
	* @author HP
	* @date 2013-5-28 上午11:32:06 
	* @param @param map
	* @param @return    
	* @return Map 
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public Map findAdrsNumCancel(Map map){
		Map result = new HashMap();
		try {
			List<NjhwDoorcontrolExp> doorExps = this.findByHQL("from NjhwDoorcontrolExp t where t.cardId = '"+map.get("CARD").toString()+"' and t.dlockId = "+map.get("NODEID").toString());
			if(CollectionUtils.isNotEmpty(doorExps)){
				result.put("ADRSNUM", doorExps.get(0).getAdrsNum());
			}else{
				map.put("status", "1");
			}
			result.putAll(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	* @Description：解析门的状态保存库
	* @Author：hp
	* @Date：2013-5-13
	* @param result
	* @param logid
	* @return
	**/
	public ExtInDlockLog saveDoorStatus(String result,String logid){
		if(StringUtils.isBlank(result)){
			log.info("参数不能为空!");
			return null;
		}
		ExtInDlockLog doorLog = new ExtInDlockLog();
		try {
			doorLog.setAdrsStore(result.substring(2, 4));
			doorLog.setAdrsComm(result.substring(4, 6));
			doorLog.setDlStatus(result.substring(6, 8));
			doorLog.setCardId(result.substring(8, 24));
			String time = result.substring(24,32);
			time = String.valueOf(Integer.parseInt(time,16));
			Date d = new Date();
			String s = new SimpleDateFormat("yyyy").format(d);
			Date dlDate = new SimpleDateFormat().parse(s.substring(0,2)+time);
			doorLog.setDlDate(dlDate);
			doorLog.setDlFlag(result.substring(32, 34));
			doorLog.setInsertDate(new Date());
			if(StringUtils.isNotBlank(logid)){
				doorLog.setInsertId(Long.parseLong(logid));
			}
			dao.save(doorLog);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return doorLog;
	}
	
	
	/**
	 * 
	* @Title: delAuthority 
	* @Description: 取消门锁、门禁、闸机权限
	* @author WXJ
	* @date 2013-5-28 下午05:30:27 
	* @param @param cardId    
	* @return void 
	* @throws
	 */
	public void delAuthority(String cardId){
		Long userId = Long.valueOf(this.getUserIdByCardId(cardId));
		if (userId!=null) this.delAuthority(userId);
	}
	
	/**
	 * 
	* @Title: delAuthority 
	* @Description: 取消门锁、门禁、闸机权限
	* @author WXJ
	* @date 2013-5-28 下午05:25:19 
	* @param @param userId    
	* @return void 
	* @throws
	 */
	public void delAuthority(Long userId){			
		String loginId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		Map<String,Object> conMap = new HashMap<String, Object>();
		conMap.put("personId", userId);
		conMap.put("denyFlag", "1");
		List<Map> objPermList = this.findListBySql("PortSQL.getAuthRoom", conMap);							            
	    if(objPermList!=null && objPermList.size()>0){
	    	for (int j = 0; j < objPermList.size(); j++)
			{
	    		doorControlToAppService.delDoorAuth(String.valueOf(userId), loginId, objPermList.get(j).get("ROOM_ID").toString(), null, true, true);
			}
	    }
	}
	
	/**
	 * 
	* @Title: addAuthority 
	* @Description: 授予门锁、门禁、闸机权限
	* @author WXJ
	* @date 2013-5-28 下午05:31:14 
	* @param @param cardId    
	* @return void 
	* @throws
	 */
	public void addAuthority(String cardId){
		Long userId = Long.valueOf(this.getUserIdByCardId(cardId));
		if (userId!=null) this.addAuthority(userId);
	}
		
	/**
	 * 
	* @Title: addAuthority 
	* @Description: 授予门锁、门禁、闸机权限
	* @author WXJ
	* @date 2013-5-28 下午05:27:45 
	* @param @param userId    
	* @return void 
	* @throws
	 */
	public void addAuthority(Long userId){
		String loginId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		//授予门锁权限
		Map<String,Object> conMap = new HashMap<String, Object>();
		conMap.put("personId", userId);
		conMap.put("denyFlag", "0");
		List<Map> objPermList = this.findListBySql("PortSQL.getAuthRoom", conMap);							            
	    if(objPermList!=null && objPermList.size()>0){
	    	for (int j = 0; j < objPermList.size(); j++)
			{
	    		doorControlToAppService.addDoorAuth(String.valueOf(userId), loginId, objPermList.get(j).get("ROOM_ID").toString(), null, true, true);
			}
	    	
	    }
	}

	/**
	 * 
	* @Title: getUserIdByCardId 
	* @Description: TODO
	* @author WXJ
	* @date 2013-5-26 下午03:58:07 
	* @param @param cardId
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getUserIdByCardId(String cardId){
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		//根据cardId找到userId
		Map<String,Object> map = new HashMap<String,Object>();			
		map.put("cardId", cardId);
		map.put("orgId", orgId);
		List<Map> listMap = this.sqlDao.findList("PersonnelUnitSQL.getUserIdByCardId", map);
		if (listMap!=null && listMap.size() > 0){
			Map mp = listMap.get(0);
			String userId = mp.get("USERID")!=null?mp.get("USERID").toString():"";
			return userId;
		}
		return null;
	}

	/**
	* @Description：根据门锁id查询一字节码
	* @Author：hp
	* @Date：2013-5-20
	* @param map
	* @return
	**/
	@SuppressWarnings("unchecked")
	public Map getRoomInfo(String adrsStore, String adrsRoom){
		Map result = new HashMap();
		Map map = new HashMap();
		map.put("adrsStore", adrsStore);
		map.put("adrsRoom", adrsRoom);
		
		List<Map> roomInfo = this.findListBySql("PortSQL.getRoomInfo", map);
		
		result.put("nodeId", roomInfo.get(0).get("NODEID").toString());
		result.put("roomId", roomInfo.get(0).get("ROOMID").toString());
		result.put("name", roomInfo.get(0).get("NAME").toString());
		
		return result;
	}
	
	public void setDoorControlToAppService(DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}
	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}

}
