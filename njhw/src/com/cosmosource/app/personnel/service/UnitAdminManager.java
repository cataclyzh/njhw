package com.cosmosource.app.personnel.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import oracle.sql.CLOB;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.cosmosource.app.entity.AttendanceApprovers;
import com.cosmosource.app.entity.BuildingAttendancers;
import com.cosmosource.app.entity.NjhwDoorcontrolExp;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.HexAndByte;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * 
* @description: 单位管理员首页
* @author herb
* @date May 3, 2013 4:35:49 PM
 */
public class UnitAdminManager extends BaseManager {
	
	private DoorControlToAppService doorControlToAppService;
	
	private OrgMgrManager orgMgrManager;

	public void getUnitRes() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	* @title: getUnitResRooms 
	* @description: 查询单位分配的所有房间
	* @author herb
	* @param orgId
	* @param eorTypeRoom
	* @return
	* @date May 3, 2013 8:00:19 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> getUnitResRooms(Page<Map> page,Long orgId, String eorTypeRoom) {
		Page list = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orgId", orgId);
			map.put("eorTypeRoom", eorTypeRoom);
			list = sqlDao.findPage(page,"PersonnelUnitSQL.getUnitResRooms", map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	* @title: findRoomTelMacList 
	* @description: 得到分配到房间的ip电话[mac]列表
	* @author herb
	* @param roomId
	* @return
	* @date May 4, 2013 4:42:01 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findRoomTelMacList(String roomId) {
		Map condtion = new HashMap();
		condtion.put("roomId", roomId);
		return sqlDao.findList("PersonnelUnitSQL.findRoomTelMacList", condtion);
	}
	
	/**
	 * 
	* @title: findRoomUserList 
	* @description: 得到分配到房间的人员列表
	* @author herb
	* @param roomId
	* @return
	* @date May 4, 2013 4:42:01 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findRoomUserList(String roomId) {
		Map condtion = new HashMap();
		condtion.put("roomId", roomId);
		return sqlDao.findList("PersonnelUnitSQL.findRoomUserList", condtion);
	}
	
	/**
	 * 
	* @title: findInnerTelMacList 
	* @description: 得到接入的ip电话[mac]列表
	* @author herb
	* @param roomId
	* @return
	* @date May 4, 2013 4:43:13 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findInnerTelMacList(String roomId) {
		Map condtion = new HashMap();
		condtion.put("roomId", roomId);
		return sqlDao.findList("PersonnelUnitSQL.findInnerTelMacList", condtion);
	}
	
	/**
	 * 
	* @title: roomEditSave 
	* @description: 修改房间信息
	* @author herb
	* @param tank
	* @date May 4, 2013 6:41:36 PM     
	* @throws
	 */
	public void roomEditSave(Objtank tank) {
		if (null != tank && tank.getNodeId() > 0){
			dao.update(tank);
			dao.flush();
		}
	}
	
	/**
	 * 
	* @title: getRoomRes 
	* @description: 查询房间资源 视图【V_OBJTANK_ROOM】
	* @author herb
	* @param type 资源视图定义
	* @param roomId 房间节点id
	* @date May 5, 2013 5:40:12 PM     
	* @throws
	 */
	public List<Map> getRoomRes(String type, String roomId) {
		Map condtion = new HashMap();
		condtion.put("roomId", roomId);
		condtion.put("type", type);
		return sqlDao.findList("PersonnelUnitSQL.getRoomRes", condtion);
	}
	
	/**
	 * 获得审核人的ids, id之间以逗号隔开
	 * @param userid
	 * @return approver userids
	 */
	public String getApprovers(String userid){
		Object o = dao.findById(AttendanceApprovers.class, Long.valueOf(userid));
		if(null != o )
		{
			return ((AttendanceApprovers)o).getApprovers();
		}
		else
		{
			return null;
		}
		
	}
	
	
	/**
	 * 获得审核人的ids, id之间以逗号隔开
	 * @param userid
	 * @return approver userids
	 */
	public String getBuildingAtt(Long orgId){
		Object o = dao.findById(BuildingAttendancers.class, orgId);
		if(null != o )
		{
			return ((BuildingAttendancers)o).getUserid();
		}
		else
		{
			return null;
		}
		
	}
	
	public List<Map> getResPermission(String type ,String nodeId){
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		Map condtion = new HashMap();
		condtion.put("nodeId", nodeId);
		condtion.put("type", type);
		condtion.put("orgId", orgId);
		return sqlDao.findList("PersonnelUnitSQL.getResPermission", condtion);
	}
	
	/**
	 * 删除多余的授权失败信息
	 * @param nodeId
	 */
	public void removeErrorDoorInfo(String nodeId) {
		//删除添加授权未成功的数据
		dao.batchExecute("delete from NjhwDoorcontrolExp exp where exp.dlockId = '" + NumberUtil.strToLong(nodeId) + "' and (exp.exp1 = '1' or exp.exp1 = '2')");
		//更新删除授权未成功的数据
		dao.batchExecute("update NjhwDoorcontrolExp set exp1 = null  where dlock_id = '"
				+ NumberUtil.strToLong(nodeId) + "' and (exp1 = '3' or exp1 = '4')");
	}
	
	
	/**
	 * @描述:根据操作时间查询开门是否成功
	 * @作者：zhangqw
	 * @日期：2013年6月22日17:47:35
	 * @return
	 */
	public List<Map> getDoorStatus(String msgId){
		Map condtion = new HashMap();
		condtion.put("msgId",msgId);
		return sqlDao.findList("PersonnelUnitSQL.getDoorStatus", condtion);
	}
	
	
	/**
	 * @描述:房间分配及授权管理清单导出
	 * @作者：qiyanqiang
	 * @日期：2013-05-16
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public   List exportRoomInfo(Map map) {
		List<HashMap> list = sqlDao.findList("PersonnelUnitSQL.exportList", map);
		if (list != null && list.size() > 0)
			return list;
		else
			return null;
	}

	
	
	
	/**
	 * 
	 * @描述:查询用户的车牌
	 * @param map
	 * @author qiyanqiang
	 * @时间  2013- 05 -17
	 */
   	
	@SuppressWarnings("unchecked")
	public  List<HashMap> searchUserLicensePlate(Map map) {
		List<HashMap> list = sqlDao.findList("PersonnelUnitSQL.searchUserLicensePlate", map);
		if (list != null && list.size() > 0)
			return list;
		else
			return null;
	}
	
	
	/**
	 * 
	 * @描述:  查询用户闸机、门禁、门锁：
	 * @param map
	 * @author qiyanqiang
	 * @时间  2013- 05 -17
	 */
   	
	@SuppressWarnings("unchecked")
	public  List  searchAllFacility(Map map) {
		List<HashMap> list = sqlDao.findList("PersonnelUnitSQL.searchAllFacility", map);
		if (list != null && list.size() > 0)
			return list;
		else
			return null;
	}
	
	
	

	/**
	 * 
	 * @描述: 根据用户的userid查询用户的姓名和卡号
	 * @param map
	 * @author qiyanqiang
	 * @时间  2013- 05 -17
	 */
   	
	@SuppressWarnings("unchecked")
	public  List  searchCardPrivilegesNumber(Map map) {
		List<HashMap> list = sqlDao.findList("PersonnelUnitSQL.searchCardPrivilegesNumber", map);
		if (list != null && list.size() > 0)
			return list;
		else
			return null;
	}
	
	
	
	
	
	/**
	 * 
	 * @描述:  根据car表的carId查询 userId
	 * @param map
	 * @author qiyanqiang
	 * @时间  2013- 05 -19
	 
   	
	@SuppressWarnings("unchecked")
	public  List  searchUserId(Map map) {
		List<HashMap> list = sqlDao.findList("PersonnelUnitSQL.searchUserId", map);
		if (list != null && list.size() > 0)
			return list;
		else
			return null;
	}
	
	*/
	
	/**
	 * 
	 * @title: findUsersExpById
	 * @description: 根据car表的carId查询 userId
	 * @author qiyanqiang
	 * @param
	 * @return
	 * @date 2013-5-11
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Long  searchUserId(String cardId) {
		 
		List<NjhwTscard > list = dao.findByProperty(NjhwTscard.class,"cardId", cardId);
		if (list != null && list.size() >= 1) {
			return list.get(0).getUserId();
		} else {
			List<NjhwUsersExp > list1 = dao.findByProperty(NjhwUsersExp.class,"tmpCard", cardId);
			 if(list1 !=null && list1.size()>=1)
				 return list1.get(0).getUserid();
			
			
		}
		return 0l;

	}
	
	/**
	 * 
	* @title: findUserExp 
	* @description: 根据用户id查询用户扩展信息
	* @author herb
	* @param userid
	* @return
	* @date May 24, 2013 4:54:15 PM     
	* @throws
	 */
	public 	NjhwUsersExp findUserExp(long userid){
		NjhwUsersExp exp = null;
		List<NjhwUsersExp > list1 = dao.findByProperty(NjhwUsersExp.class,"userid", userid);
		if(list1 !=null && list1.size()>=1){
			exp = list1.get(0);
		}
		return exp;
	}
	/**
	 * 
	* @title: findUserCard 
	* @description: 根据用户id,查询市民卡号
	* @author herb
	* @param userid
	* @return
	* @date May 24, 2013 4:56:57 PM     
	* @throws
	 */
	public 	NjhwTscard findUserCard(long userid){
		NjhwTscard exp = null;
		List<NjhwTscard > list1 = dao.findByProperty(NjhwTscard.class,"userId", userid);
		if(list1 !=null && list1.size()>=1){
			exp = list1.get(0);
		}
		return exp;
	}
	
	/**
	 * @description:取得当前登陆用户所属的顶级单位
	 * @author zh
	 * @param HashMap map
	 * @return 
	 */
	public HashMap getTopOrgByUserId(long userId) {
		Map pMap = new HashMap();
		pMap.put("userid", userId);
		List<HashMap> list = this.findListBySql("PersonnelSQL.getTopOrgIdByUserId", pMap);
		if (null != list && list.size() > 0) return list.get(0);
		return null;
	}
	
	
	/**
	 * 
	* @Title: getPlayCardRoomsLocks 
	* @Description: TODO
	* @author WXJ
	* @date 2013-5-26 上午12:34:49 
	* @param @param page
	* @param @param orgId
	* @param @return    
	* @return Page<Map> 
	* @throws
	 */
	public List<Map> getPlayCardRoomsLocks(String orgId,String cardId) {
		List<Map> list = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();	
			map.put("cardId", cardId);
			//根据cardId找到userId
			String userId = getUserIdByCardId(cardId);
			if (userId!=null ){				
				map.put("userId", userId);			
				map.put("orgId", orgId);
				
				list = this.findListBySql("PersonnelUnitSQL.getUnitResRoomsLocks", map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	* @Title: getPlayCardRoomsLocks 
	* @Description: TODO
	* @author WXJ
	* @date 2013-5-26 上午12:34:49 
	* @param @param page
	* @param @param orgId
	* @param @return    
	* @return Page<Map> 
	* @throws
	 */
	public List<Map> getPlayCardRoomsLocks(String orgId) {
		List<Map> list = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();						
			map.put("orgId", orgId);
			
			list = this.findListBySql("PersonnelUnitSQL.getUnitResRoomsLocks", map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	* @Title: getPlayCardRoomsLocks 
	* @Description: TODO
	* @author WXJ
	* @date 2013-5-26 上午12:34:49 
	* @param @param page
	* @param @param orgId
	* @param @return    
	* @return Page<Map> 
	* @throws
	 */
	public void saveAutoCreateUser(String cardId) {
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		try{
			Map<String,Object> map = new HashMap<String,Object>();			
			map.put("orgId", orgId);
			map.put("cardType", "3");
			
			List<Map> tkNumList = this.findListBySql("PersonnelUnitSQL.getPubCardNo", map);
			
			int tkNum = 0;
			if (tkNumList == null || tkNumList.size() == 0) {
				tkNum = 1;
			} else {
				for (int i = 0; i < tkNumList.size(); i++) {
					if (i+1  < Integer.parseInt((String) tkNumList.get(i).get("PUBCARDNO"))) {
						tkNum = i+1;
						break;
					}
					if (i+1 == tkNumList.size()) {
						tkNum = i+2;
					}
				}
			}
			
			
			Users user = new Users();						// 用户基本信息
			user.setIsSystem(0l);
			user.setActiveFlag((long)1);
			user.setChangeLoginPwdFlag((long)1);
			user.setLoginPwd(DigestUtils.md5Hex(PropertiesUtil.getAnyConfigProperty("user.default.pwd", PropertiesUtil.NJHW_CONFIG)).substring(0,20).toUpperCase());
			user.setOrgId(Long.parseLong(orgId));
			user.setLoginUid("tongK"+orgId+"_"+tkNum);
			user.setDisplayName("通卡"+tkNum);
			user.setCreator(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			user.setCreatDate(DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd"));
			dao.save(user);
			
			NjhwUsersExp userExp = new NjhwUsersExp();		// 用户扩展信息
			userExp.setUserid(user.getUserid());
			userExp.setUepType(NjhwUsersExp.USER_TYPE_OFFICE);
			userExp.setCardType("3");
			userExp.setUepBak1(tkNum+"");
			userExp.setTmpCard(cardId);
			userExp.setInsertDate(DateUtil.getSysDate());
			userExp.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			dao.save(userExp);
			
			dao.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: saveAutoCreateUserForAll 
	* @Description: TODO
	* @author HJ
	* @date 2013-8-29
	* @param @param page
	* @param @param orgId
	* @param @return    
	* @return Page<Map> 
	* @throws
	 */
	public void saveAutoCreateUserForAll(String cardId) {
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		try{
			Map<String,Object> map = new HashMap<String,Object>();			
			map.put("cardType", "4");
			
			List<Map> tkNumList = this.findListBySql("PersonnelUnitSQL.getPubCardNo", map);
			
			int tkNum = 0;
			if (tkNumList == null || tkNumList.size() == 0) {
				tkNum = 1;
			} else {
				for (int i = 0; i < tkNumList.size(); i++) {
					if (i+1  < Integer.parseInt((String) tkNumList.get(i).get("PUBCARDNO"))) {
						tkNum = i+1;
						break;
					}
					if (i+1 == tkNumList.size()) {
						tkNum = i+2;
					}
				}
			}
			
			
			Users user = new Users();						// 用户基本信息
			user.setIsSystem(0l);
			user.setActiveFlag((long)1);
			user.setChangeLoginPwdFlag((long)1);
			user.setLoginPwd(DigestUtils.md5Hex(PropertiesUtil.getAnyConfigProperty("user.default.pwd", PropertiesUtil.NJHW_CONFIG)).substring(0,20).toUpperCase());
			user.setOrgId(Long.parseLong(orgId));
			user.setLoginUid("tongKAll_"+tkNum);
			user.setDisplayName("全楼通卡"+tkNum);
			user.setCreator(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			user.setCreatDate(DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd"));
			dao.save(user);
			
			NjhwUsersExp userExp = new NjhwUsersExp();		// 用户扩展信息
			userExp.setUserid(user.getUserid());
			userExp.setUepType(NjhwUsersExp.USER_TYPE_OFFICE);
			userExp.setCardType("4");
			userExp.setUepBak1(tkNum+"");
			userExp.setTmpCard(cardId);
			userExp.setInsertDate(DateUtil.getSysDate());
			userExp.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			dao.save(userExp);
			
			dao.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: checkPubCard
	* @Description: 检查通卡异常状态
	* @author HJ
	* @date 2013-7-4 
	* @param cardId
	* @param opt
	* @return String
	* @throws
	 */
	public String checkPubCard(String cardId, String opt) {
		String errorCode = null;
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		int num = this.findByHQL("select t from NjhwUsersExp t, Users t1 where t1.userid = t.userid and t1.orgId = ? and t.tmpCard = ?", Long.parseLong(orgId), cardId).size();
		if ("add".equals(opt) && num > 0) {
			errorCode = "1";
		}
		int tkNum = this.findByHQL("select t from NjhwUsersExp t, Users t1 where t1.userid = t.userid and t1.orgId = ? and t.tmpCard = ? and t.cardType = ?", Long.parseLong(orgId), cardId, "3").size();
		if ("upd".equals(opt) && tkNum == 0) {
			errorCode = "2";
		}
		
		return errorCode;
	}
	
	/**
	 * 
	* @Title: playCardSave 
	* @Description: TODO
	* @author zhangquanwei
	* @date 2013年6月21日20:07:24
	* @param @param cardId
	* @param @param chk   room lockid 
	* @param loginId
	* @return void 
	* @throws
	 */
	public void savePlayCard(String cardId, String[] chk, String loginId){
		Map<String,Object> map = new HashMap<String,Object>();			
		map.put("cardId", cardId);

		//根据cardId找到userId
		String userId = getUserIdByCardId(cardId);
		
		// 查询之前未勾选此次勾选的房间信息
		List<String> listNodeSelect = new ArrayList<String>();
        List<String> allNodeList = new ArrayList<String>();
        if (chk != null && chk.length>0)
        {
        	for (int i=0; i<chk.length; i++ )
			{
				
				allNodeList.add(chk[i]);
				
				// 构造查询条件
				Map<String,Object> hashMap = new HashMap<String, Object>();
				hashMap.put("userid", userId);
				hashMap.put("nodeid", chk[i]);
				// 根据roomid查询查询通信相关信息
				List<Map> useridCount = this.findListBySql("PortSQL.selectCheckedId", hashMap);
				if (null != useridCount  &&  0 == Integer.parseInt(String.valueOf(useridCount.get(0).get("USERNUM"))))
				{
					listNodeSelect.add(chk[i]);
				}
			}
        }
        
        
        Map<String,String> nodeMap = null;
        
        // 根据listNodeSelect 查 询出通信组装指令
        for(int i=0;i<listNodeSelect.size();i++)
        {
        	nodeMap = new HashMap<String, String>();
        	nodeMap.put("nodeid", listNodeSelect.get(i));
            String roomId = String.valueOf(((Map) this.findListBySql("PortSQL.getRoomIdByNodeId", nodeMap).get(0)).get("ROOMID"));
            doorControlToAppService.addDoorAuth(userId, loginId, roomId,null);
        }

        
        // 查询之前勾选此次未勾选的 nodeid 
		Map<String, Object> contmMap  = new HashMap<String, Object>();
		contmMap.put("nodeid", allNodeList);
		contmMap.put("userid", userId);
		
		List<Map>  nodeidNotselect= this.findListBySql("PortSQL.nodeidNotSel", contmMap);
		for (int i = 0; i < nodeidNotselect.size(); i++)
		{
			nodeMap = new HashMap<String, String>();
			String nodeId = String.valueOf(((Map) nodeidNotselect.get(i)).get("NODEID"));
        	nodeMap.put("nodeid", nodeId);
        	List<Map> roomInfoList = this.findListBySql("PortSQL.getRoomIdByNodeId", nodeMap);
        	if (roomInfoList != null && roomInfoList.size() > 0 && roomInfoList.get(0).get("ROOMID") != null) {
        		String roomId = ((Map) this.findListBySql("PortSQL.getRoomIdByNodeId", nodeMap).get(0)).get("ROOMID").toString();
                doorControlToAppService.delDoorAuth(userId, loginId, roomId,null);
        	}
		}
	}
	
	/**
	 * 
	* @Title: retryPlayCardSave 
	* @Description: TODO
	* @author hj
	* @date 2013年9月9日
	* @param cardId
	* @param nodeIds
	* @param loginId
	* @return void 
	* @throws
	 */
	public void retryPlayCardSave(String cardId, String nodeIds, String loginId) {
		//根据cardId找到userId
		String userId = getUserIdByCardId(cardId);
		
        for (String nodeId : nodeIds.split(",")) {
        	doorControlToAppService.addDoorAuth(userId, loginId, nodeId, null);
        }
	}
	
	/**
	 * 
	* @Title: deleteCardSave 
	* @Description: TODO
	* @author hj
	* @date 2013年9月9日
	* @param cardId
	* @param loginId
	* @return void 
	* @throws
	 */
	public void deleteCardSave(String cardId, String loginId) {
		//删除添加授权未成功的数据
		List<Map> l = new ArrayList();
		Map m = new HashMap();
		m.put("cardId", cardId);
		l.add(m);
		sqlDao.batchDelete("PersonnelUnitSQL.deleteDoorAuthFailInfo", l);
		
		dao.flush();
		
		//根据cardId找到userId
		String userId = getUserIdByCardId(cardId);
		
        for (String nodeId : this.findDoorLockInfo(null)) {
        	doorControlToAppService.delDoorAuth(userId, loginId, nodeId, null, true, false);
        }
	}
	
	/**
	 * 
	* @Title: savePlayCardForAll 
	* @Description: TODO
	* @author HJ
	* @date 2013年8月29日
	* @param @param cardId
	* @param loginId
	* @return void 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public void savePlayCardForAll(String cardId, String loginId){

		//根据cardId找到userId
		String userId = getUserIdByCardId(cardId);
		
		List<String> ls = this.findDoorLockInfo(null);
		
		for (String nodeId : ls) {
			doorControlToAppService.addDoorAuth(userId, loginId, nodeId, null);
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
	public String convertOrderInfo(String doorId,String order,String doorAddress,String commMachineAddress,String card,String type,String arsnum){
		if(StringUtils.isBlank(doorId) || StringUtils.isBlank(order)){
			 // log.info("传入的参数为空!!");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(order).append(convertHex(doorAddress)).append(convertHex(commMachineAddress)).append(DoorControlToAppService.EIGHTAUTHORITY);
		if(type.equals(DoorControlToAppService.INIT)){
			if(card.length() == 16){
				sb.append(card).append(convertHex(arsnum));
			}else if(card.length() == 12){
				sb.append("0000"+card).append(convertHex(arsnum));
			}
			sb.append(DoorControlToAppService.FIRSTCARDTYPE);
		}
		if(type.equals(DoorControlToAppService.DELETE)){
			sb.append(DoorControlToAppService.DELETELOCK).append(convertHex(arsnum));
			sb.append(DoorControlToAppService.FIRSTEXTCODE);
		}
		sb.append(getXorVerificationCode(sb.toString()));
		return sb.toString();
	}
	
	
	/**
	* @Description：把十进制字符串转换成16进制字符串,传递命令需要
	* @Author：hp
	* @Date：2013-5-7
	* @param addr
	* @return
	**/
	public static String convertHex(String order) {
		if(order == null || order == ""){
			// log.info("参数不能为空!");
			return null;
		}
//		String od = Integer.toHexString(Integer.parseInt(order));
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
	* @Description：生成异或验证码
	* @Author：hp
	* @Date：2013-3-19
	* @param xorText
	* @return
	**/
	public static String getXorVerificationCode(String xorText){
		char[] b = xorText.toCharArray();
		int t = 0;
		int a = 0;
		int c = 0;
		String hexadecimal = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F";
		for (int i = 0; i < b.length; i++) {
			if(StringUtils.contains(hexadecimal, b[i])){
				if (i % 2 == 0) {
					t = Integer.parseInt(String.valueOf(b[i]), 16);
					a^=t;
				}else{
					t = Integer.parseInt(String.valueOf(b[i]), 16);
					c^=t;
				}
			}
		}
		String result = Integer.toHexString(a)+Integer.toHexString(c);
		return result.toUpperCase();
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
			List<NjhwDoorcontrolExp> doorExps = this.findByHQL("from NjhwDoorcontrolExp t where t.cardId = '"+map.get("CARD").toString()+"' and t.dlockId = "+map.get("NODEID").toString());
			if(CollectionUtils.isEmpty(doorExps)){
				//根据和seq表做关联后找出某一个门锁中未授权的1字节存储地址
				List<Map> doorSetExps = this.findListBySql("PortSQL.doorAdrNum", map);
				if(CollectionUtils.isNotEmpty(doorSetExps)){
						result.put("ADRSNUM", doorSetExps.get(0).get("DSEQ").toString());
				}
			}else{
				map.put("status", "1");
			}
			result.putAll(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public void setDoorControlToAppService(DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}
	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}
	
	
	/**
	 * 获取通卡编号
	 * @return
	 */
	public String getPubCardNo() {
		List<Map> listMap = this.sqlDao.findList("PersonnelUnitSQL.getPubCardNo", null);
		if (listMap!=null && listMap.size() > 0){
			Map mp = listMap.get(0);
			String pubCardNo = mp.get("PUBCARDNO")!=null?mp.get("PUBCARDNO").toString():"";
			return Integer.valueOf(pubCardNo) + 1 + "";
		}
		return null;
	}
	
	/**
	* @Description：查询部门内所有通卡的信息
	* @Author：hj
	* @Date：2013-7-5
	* @return List<Map>
	**/
	public List<Map> getPubCardInfos() {
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		Map<String,Object> condtion = new HashMap<String,Object>();						
		condtion.put("orgId", orgId);
		condtion.put("cardType", "3");
		
		List<Map> result = sqlDao.findList("PersonnelUnitSQL.getPubCardInfos", condtion);
		List<Map> tkList = new ArrayList<Map>();
		String userId = new String();
		int j = -1;
		if (result != null && result.size() > 0) {
			for (int i=0; i < result.size(); i++) {
				if (!userId.equals(String.valueOf(result.get(i).get("USERID")))) {
					userId = String.valueOf(result.get(i).get("USERID"));
					j++;
					Map tkInfo = new HashMap();
					tkInfo.put("USERID", result.get(i).get("USERID"));
					tkInfo.put("PUBCARDNAME", result.get(i).get("PUBCARDNAME"));
					tkInfo.put("CARDID", result.get(i).get("CARDID"));
					tkInfo.put("NODEID", result.get(i).get("NODEID"));
					tkInfo.put("NODENAME", result.get(i).get("NODENAME"));
					tkList.add(tkInfo);
				} else {
					String nodeName = String.valueOf(tkList.get(j).get("NODENAME"));
					nodeName += "," + result.get(i).get("NODENAME");
					tkList.get(j).put("NODENAME", nodeName);
					String nodeId = String.valueOf(tkList.get(j).get("NODEID"));
					nodeId += "," + result.get(i).get("NODEID");
					tkList.get(j).put("NODEID", nodeId);
				}
			}
		}
		
		return tkList;
	}
	
	/**
	* @Description：查询部门内所有通卡的信息
	* @Author：hj
	* @Date：2013-7-5
	* @return List<Map>
	**/
	public List<Map> getPubCardInfosForAll() {
		Map<String,Object> condtion = new HashMap<String,Object>();						
		
		List<Map> result = sqlDao.findList("PersonnelUnitSQL.getPubCardAllInfos", condtion);
		List<Map> tkList = new ArrayList<Map>();
		String userId = new String();
		int j = -1;
		if (result != null && result.size() > 0) {
			for (Map m : result) {
				if (m.get("CARDID") != null && StringUtil.isNotBlank(m.get("CARDID").toString())) {
					String cardId = m.get("CARDID").toString();
					List<Map> lm = this.getPubCardConfirmInfo(cardId, "", "2");
					String roomId = "";
					String nodeName = "";
					if (lm != null && lm.size() > 0) {
						for (Map mm : lm) {
							roomId += mm.get("ID") + ",";
							nodeName += mm.get("NAME") + ",";
						}
					}
					
					if (roomId.endsWith(",")) {
						roomId = StringUtil.chop(roomId);
					}
					
					if (nodeName.endsWith(",")) {
						nodeName = StringUtil.chop(nodeName);
					}
					
					Map tkMap = new HashMap();
					tkMap.put("CARDID", cardId);
					tkMap.put("ROOMID", roomId);
					tkMap.put("NODENAME", nodeName);
					tkList.add(tkMap);
				}
				
			}
		}
		
		return tkList;
	}
	
	/**
	* @Description：确认通卡制作的结果
	* @Author：hj
	* @Date：2013-7-5
	* @return boolean
	**/
	public boolean confirmPubCard(String cardId, String nodeId, String opt) {
		boolean isSuccess = true;
		Map<String,Object> condtion = new HashMap<String,Object>();
		condtion.put("cardId", cardId);
		condtion.put("nodeId", Integer.parseInt(nodeId));
		if ("add".equals(opt)) {
			Long startTime = System.nanoTime();
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (System.nanoTime() - startTime > TimeUnit.NANOSECONDS.convert(5000, TimeUnit.MILLISECONDS)) {
					isSuccess = false;
					break;
				}
				List<Map> listMap = this.sqlDao.findList("PersonnelUnitSQL.getAuthDoorStatus", condtion);
				if (null == listMap || listMap.size() == 0) {
					isSuccess = false;
					break;
				} else {
					if (null == listMap.get(0).get("EXP1") || StringUtil.isBlank(String.valueOf(listMap.get(0).get("EXP1")))) {
						break;
					} else if ("2".equals(String.valueOf(listMap.get(0).get("EXP1")))){
						isSuccess = false;
						break;
					}
				}
			}
		} else if ("del".equals(opt)) {
			Long startTime = System.nanoTime();
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (System.nanoTime() - startTime > TimeUnit.NANOSECONDS.convert(5000, TimeUnit.MILLISECONDS)) {
					isSuccess = false;
					break;
				}
				List<Map> listMap = this.sqlDao.findList("PersonnelUnitSQL.getAuthDoorStatus", condtion);
				if (null == listMap || listMap.size() == 0) {
					break;
				} else {
					if (null != listMap.get(0).get("EXP1") && "3".equals(String.valueOf(listMap.get(0).get("EXP1")))) {
						isSuccess = false;
						break;
					}
				}
			}
		}
		
		return isSuccess;
	}
	
	/**
	* @Description：判断是否是本单位管理员用户
	* @Author：hj
	* @Date：2013-7-5
	* @return boolean
	**/
	public boolean isAdmin(String cardId) {
		boolean isAdmin = true;
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		Map<String,Object> condtion = new HashMap<String,Object>();						
		condtion.put("orgId", orgId);
		condtion.put("cardId", cardId);
		
		
		/**
		 * 部分卡号特殊处理，可以刷任何管理员确认界面
		 */
		List specialAdminList = sqlDao.findList("PersonnelUnitSQL.checkSpecialAdminCard", condtion);
		if (!specialAdminList.isEmpty() && specialAdminList.size() > 0) {
			return true;
		}
		
		
		List<Map> listMap = sqlDao.findList("PersonnelUnitSQL.isUnitAdmin", condtion);
		if (null == listMap || listMap.size() == 0) {
			isAdmin= false;
		}
		return isAdmin;
	}
	
	/**
	 * 
	* @Title: deletePubCard 
	* @Description: 删除通卡
	* @author hj
	* @date 2013年7月11日
	* @param @param cardId
	* @return void 
	* @throws
	 */
	public void deletePubCard(String cardId) {
		//根据cardId找到userId
		String userId = getUserIdByCardId(cardId);
		orgMgrManager.deleteUser(Long.parseLong(userId),Struts2Util.getSession().getAttribute(Constants.USER_ID).toString(),doorControlToAppService);
	}
	
	/**
	 * 
	* @Title: findDoorLockInfo 
	* @Description: 查找所有门锁通信机信息
	* @author hj
	* @date 2013年9月6日
	* @return List 
	* @throws
	 */
	public List<String> findDoorLockInfo(String nodeIds) {
		List<String> ls = new ArrayList<String>();
		Map<String,Object> condtion = new HashMap<String,Object>();
		condtion.put("nodeIds", nodeIds);

		List<Map> listMap = sqlDao.findList("PersonnelUnitSQL.findDoorLockInfo", condtion);
		
		List<String[]> ll = new ArrayList<String[]>();
		
		if (listMap != null && listMap.size() > 0) {
			for (Map m : listMap) {
				CLOB c = (CLOB) m.get("IDS");
				String r = "";
				try {
					r = c.getSubString(1, (int) c.length());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				ll.add(r.split(","));
			}
		}
		
		int i = 0;
		while(true) {
			boolean canBreak = true;
			for (String[] ss : ll) {
				if (ss.length > i) {
					canBreak = false;
					ls.add(ss[i]);
				}
			}
			
			if (canBreak) {
				break;
			}
			i++;
		}
		
		return ls;
	}
	
	public void setOrgMgrManager(OrgMgrManager orgMgrManager) {
		this.orgMgrManager = orgMgrManager;
	}
	public OrgMgrManager getOrgMgrManager() {
		return orgMgrManager;
	}
	
	/**
	 * 
	* @Title: getPubCardConfirmInfo 
	* @Description: 查找通卡授权信息
	* @author hj
	* @date 2013年9月6日
	* @return List 
	* @throws
	 */
	public List<Map> getPubCardConfirmInfo(String cardId, String nodeIds, String type) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("cardId", cardId);
		condition.put("nodeIds", nodeIds);
		condition.put("type", type);
		
		List<Map> listMap = sqlDao.findList("PersonnelUnitSQL.getPubCardConfirmInfo", condition);
		return listMap;
	}
	
	/**
	 * 
	* @Title: getPubCardConfirmInfo 
	* @Description: 查找通卡授权数量
	* @author hj
	* @date 2013年9月6日
	* @return List 
	* @throws
	 */
	public int getPubCardDoorNum(String cardId) {
		
		List l = dao.findByProperty(NjhwDoorcontrolExp.class, "cardId", cardId);
		// TODO Auto-generated method stub
		return l == null ? 0 : l.size();
	}

}
