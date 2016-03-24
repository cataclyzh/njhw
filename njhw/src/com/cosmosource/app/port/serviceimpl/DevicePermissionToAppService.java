package com.cosmosource.app.port.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.entity.NjhwScheduleAuth;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.model.SiteStatus;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @ClassName:DevicePermissionToAppService
 * @Description：对资源进行操作
 * @Author：hp 
 * @Date:2013-3-25
 */
public class DevicePermissionToAppService extends BaseManager{
	
	private static final Log log = LogFactory.getLog(DevicePermissionToAppService.class);
	
	private DoorControlToAppService doorControlToAppService;

	/**
	* @Description：得到资源列表中资源集合
	* @Author：hp
	* @Date：2013-3-25
	* @param siteType  设备类型
	* @param userId    用户id
	* @return
	**/
	@SuppressWarnings("unchecked")

	public Page<Objtank> getAuthSiteCollection(Page<Objtank> pageObj,String siteType,Long pId,String userId){	
		try {
			Map map = new HashMap();
			map.put("extResType", siteType);
			map.put("pId", pId);
			pageObj = this.sqlDao.findPage(pageObj, "PortSQL.siteAuth", map);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		
		return pageObj;
	}
	
	
	/**
	* @Description：得到主子菜单
	* @Author：hp
	* @Date：2013-3-26
	* @param pId
	* @param userId
	* @return
	**/
	@SuppressWarnings("unchecked")
	public List<Objtank> getAuthMenuCollection(Long pId,String userId){	
		List<Objtank> objtanks = null;
		try {
			Map map = new HashMap();
			map.put("userId", userId);
			if (pId==null) {
				objtanks = this.findListBySql("PortSQL.mainMenuAuth", map);
			}else{
				map.put("pId", pId);
				objtanks = this.findListBySql("PortSQL.subMenuAuth", map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	
		return objtanks;
	}
	
	public List<Map> getAuthMenuCollection1(Long pId,String userId){	
		List<Map> objtanks = null;
		try {
			Map map = new HashMap();
			map.put("userId", userId);
			if (pId==null) {
				objtanks = this.findListBySql("PortSQL.mainMenuAuth1", map);
			}else{
				map.put("pId", pId);
				objtanks = this.findListBySql("PortSQL.subMenuAuth1", map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	
		return objtanks;
	}
	
	public List<Map> queryNavMenuByUserId(String userId){	
		List<Map> objtanks = null;
		try {
			Map map = new HashMap();
			map.put("userId", userId);
			objtanks = this.findListBySql("PortSQL.queryNavMenuByUserId", map);		
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return objtanks;
	}
	
	public List<Map> queryAllNavMenu(){	
		List<Map> objtanks = null;
		try {
			Map map = new HashMap();
			objtanks = this.findListBySql("PortSQL.queryAllNavMenu", map);		
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return objtanks;
	}
	
	/**
	 * 
	* @Title: getAuthAllMenuCollection 
	* @Description: 获得所有授权的功能菜单
	* @author WXJ
	* @date 2013-3-28 下午03:14:55 
	* @param @param userId
	* @param @return    
	* @return List<Objtank> 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Objtank> getAuthAllMenuCollection(String userId){	
		List<Objtank> objtanks = null;
		try {
			Map map = new HashMap();
			map.put("userId", userId);
			objtanks = this.findListBySql("PortSQL.allMenuAuth", map);		
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return objtanks;
	}
	
	/**
	* @Description：得到用户拥有的权限,发给外网设备系统   （针对内部人员）
	* @Author：hp
	* @Date：2013-4-1
	* @param card    卡号
	* @param roomId  房间id
	* @return
	**/
	@SuppressWarnings("unchecked")
	public void getAuthDeviceList(String card ,String roomId){ 
		try {
			//授予门锁权限
			List<Map> devices = transferList(card, roomId, "3");
//			List<Map> orders = doorControlToAppService.initDoorAuthority(roomId, checkedIds, loginId);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		
	}
	
	/**
	* @Description：得到用户拥有的权限,发给外网设备系统   （针对访客）
	* @Author：hp
	* @Date：2013-4-2
	* @param card
	* @param roomId
	**/
	public void getAuthDeviceOtherList(String card ,String roomId){ 
		//授予闸机和门锁权限同上面内部人员处理
	}
	
	
	/**
	* @Description：取消权限    （针对内部人员）
	* @Author：hp
	* @Date：2013-4-2
	* @param card
	* @param roomId
	**/
	@SuppressWarnings("unchecked")
	public void cancelAuthDevice(String card,String roomId){
		List<Map> devices = transferList(card, roomId, "3");
		List<Map> orders = doorControlToAppService.deleteCardAuthority(devices);
		for (Map map : orders) {
			//保存数据到调度任务表中，以便对于未成功的数据进行下次调度
			NjhwScheduleAuth auth = new NjhwScheduleAuth();
			auth.setDbid(Long.valueOf(map.get("dbid").toString()));
			auth.setInTime(new Date());
			auth.setPortSys("3");
			auth.setSendTime(new Date());
			auth.setCardId(map.get("card").toString());
			auth.setNodeId(Long.valueOf(map.get("nodeId").toString()));
			auth.setOptType("0");
			dao.save(auth);
		}
		//停车场取消权限
	}
	
	/**
	* @Description：取消权限    （针对访客）
	* @Author：hp
	* @Date：2013-4-2
	* @param card
	* @param roomId
	**/
	public void cancelAuthDeviceOther(String card,String roomId){
		//取消闸机和门锁权限同上面内部人员处理
	}
	
	
	/**
	* @Description：查询所有设备当前状态
	* @Author：hp
	* @Date：2013-4-4
	* @return
	**/
	public List<SiteStatus> querySiteStatus(){
		 List<SiteStatus> siteStatus = new ArrayList<SiteStatus>();
		return siteStatus;
	}
	
	/**
	* @Description：分设备功能，返回查询的设备的list集合
	* @Author：hp
	* @Date：2013-4-1
	* @param card   市民卡
	* @param roomId 门锁id
	* @param type    设备类型
	* @return
	**/
	@SuppressWarnings("unchecked")
	public List<Map> transferList(String card ,String roomId,String type){
		Map map = new HashMap();
		map.put("card",card);
		map.put("roomId", roomId);
		List<Map> devices = new ArrayList<Map>();
		if(type.equals("3")){
			map.put("type", type);
			devices = findAuthDevice(map);
		}
		//下面应该是闸机和门禁的权限授予，同门锁
		return devices;
	}

	
	
	/**
	* @Description：把门锁集合转换成逗号隔开的string类型
	* @Author：hp
	* @Date：2013-3-26
	* @param maps
	* @param type
	* @return
	**/
	@SuppressWarnings("unchecked")
	public String parseNodeIdToString(List<Map> maps,String field){
		StringBuffer sb = new StringBuffer();
		for (Map map : maps) {
			sb.append(map.get(field));
			sb.append(",");
		}
		return sb.toString();
	}
	
	
	/**
	* @Description：根据条件查询符合权限的设备集合
	* @Author：hp
	* @Date：2013-3-26
	* @param map
	* @return
	**/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> findAuthDevice(Map map){
		return this.findListBySql("PortSQL.deviceAssignment", map);
	}
	
	/**
	 * 根据ID 查询用户是否是管理员
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> queryAdminUserByLoginId(Map map){
		return this.findListBySql("PortSQL.queryAdminUserMenu", map);
	}
	/**
	 * 根据ID 查询密码
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> queryPasswordByUserID(Map map){
		return this.findListBySql("PortSQL.getPasswordByUserID", map);
	}
	
	


	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}


	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}
	
	
	
	
}
