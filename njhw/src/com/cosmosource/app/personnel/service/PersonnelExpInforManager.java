package com.cosmosource.app.personnel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
/**
 * 人员扩展信息Manager
* @description: TODO
* @author gxh
* @date 2013-4-2 下午11:03:44
 */
public class PersonnelExpInforManager extends BaseManager {
	private DevicePermissionToAppService devicePermissionToApp;

	/**
	 *  新增或是更新保存扩展信息
	* @title: saveEntity 
	* @description: TODO
	* @author gxh
	* @param entity
	* @date 2013-4-2 下午11:04:49     
	* @throws
	 */
	
	public void saveEntity(NjhwUsersExp entity) {

		/* herb 添加门锁权限 */
		if (null != entity && entity.getRoomId() != null
				&& entity.getRoomId() > 0) {
			List<NjhwTscard> tsCardList = dao.findByProperty(NjhwTscard.class,
					"userId", entity.getUserid());
			NjhwTscard tsCard = null;
			if (null != tsCardList && tsCardList.size() > 0) {
				tsCard = tsCardList.get(0);
				devicePermissionToApp.getAuthDeviceList(tsCard.getCardId(),
						entity.getRoomId().toString()); // 赋予市民卡权限
			}
		}
		if (null != entity.getUepId() && entity.getUepId() > 0) {
			dao.update(entity);
		} else {
			dao.saveOrUpdate(entity);
		}

	}

	public void saveEntity1(Object entity) {
		dao.save(entity);
		
	}
	/**
	 * 根据用户id得到扩展对象
	* @title: getpsByid 
	* @description: TODO
	* @author gxh
	* @param userid
	* @return
	* @date 2013-4-2 下午11:05:01     
	* @throws
	 */
	
	@SuppressWarnings("unchecked")
	public NjhwUsersExp getpsByid(Long userid) {

		NjhwUsersExp nue = new NjhwUsersExp();
		List nueList = dao.findByProperty(NjhwUsersExp.class, "userid", userid);
		if (nueList != null && nueList.size() >= 1) {
			nue = (NjhwUsersExp) nueList.get(0);
			return nue;
		}
		return null;
	}
	/**
	 * 根据用户id得到用户
	* @title: getUserByid 
	* @description: TODO
	* @author gxh
	* @param userid
	* @return
	* @date 2013-5-7 下午02:03:47     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public Users getUserByid(Long userid) {

		Users nue = new Users();
		List nueList = dao.findByProperty(Users.class, "userid", userid);
		if (nueList != null && nueList.size() >= 1) {
			nue = (Users) nueList.get(0);
			return nue;
		}
		return null;
	}
/**
 * 根据用户id得到扩展对象和用户对象
* @title: getperManagerByid 
* @description: TODO
* @author gxh
* @param userid
* @return
* @date 2013-5-6 下午11:05:24     
* @throws
 */
	public Map getperManagerByid(String userid) {

		Map<String,String> vMap = new HashMap<String,String>();
		vMap.put("userid", userid);
		List<Map> list = sqlDao.findList("PersonnelSQL.findperManagerByid", vMap);
		if(list!=null&&list.size()>0){
			return list.get(0);
			
		}else{
			return null;
		}
	}
	/**
	 *  根据id得到分配的房间,电话,车牌
	* @title: getEmOrgResByid 
	* @description: TODO
	* @author gxh
	* @param orgid
	* @param eorType
	* @return
	* @date 2013-4-2 下午11:05:14     
	* @throws
	 */
	 
	@SuppressWarnings("unchecked")
	public List<EmOrgRes> getEmOrgResByid(Long orgid, String eorType) {
		try {
			return dao.findByHQL(
					"select t from EmOrgRes t where orgId=? and eorType=? ",
					orgid, eorType);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 
	* @title: exsitNjhwTscardByCardId 
	* @description: 通过市民卡号判断市民卡信息是否存在
	* @author gxh
	* @param cardId
	* @return
	* @date 2013-3-20 下午06:24:48     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean exsitNjhwTscardByCardId(String cardId){
		List<NjhwTscard> list =super.dao.findByProperty(NjhwTscard.class,"cardId", cardId);
		if(list!=null && list.size()>=1){
			return true;
		} else{
			return false;
		}
	}
	/**
	 * 根据用户id找到市民卡对象
	* @title: NjhwTscardByUserid 
	* @description: TODO
	* @author gxh
	* @param userid
	* @return
	* @date 2013-4-16 下午04:16:20     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public NjhwTscard NjhwTscardByUserid(Long userId){
		List<NjhwTscard> list =super.dao.findByProperty(NjhwTscard.class,"userId", userId);
		if(list!=null && list.size()>=1){
			return list.get(0);
		} else{
			return null;
		}
	}
	/**
	 * 通过市民卡号找到市民卡信息对象
	* @title: exsitNjhwTscardByCardId 
	* @description: TODO
	* @author gxh
	* @param cardId
	* @return
	* @date 2013-4-8 下午02:25:58     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public NjhwTscard njhwTscardByCardId(String cardId){
		NjhwTscard c = new NjhwTscard();
		List<NjhwTscard> list =super.dao.findByProperty(NjhwTscard.class,"cardId", cardId);
		if(list!=null && list.size()>=1){
			c = (NjhwTscard)list.get(0);
			return c;
		} else{
			return null;
		}
	}
	
	
	
	
	/**
	 * 
	* @title: saveNjhwTscard 
	* @description: 保存市民卡
	* @author herb
	* @param card
	* @date Apr 2, 2013 6:02:39 PM     
	* @throws
	 */
	public void saveNjhwTscard(NjhwTscard card) {
		dao.save(card);
	}

	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}

	
	/**
	 * 查询某部门下的所有子部门
	* @title: getSonOrgs 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-5-5 下午01:50:55     
	* @throws
	 */
	
	public Page<HashMap> getSonOrgs(final Page<HashMap> page, String orgId){
		if(null == orgId || orgId.trim().length() < 1){
			return page;
		}
		Map<String,Object> cMap = new HashMap<String,Object>();
		cMap.put("orgId", orgId);	
		
		return sqlDao.findPage(page, "PersonnelSQL.getSonOrgs", cMap);
		 
	}
	/**
	 * 查询某部门下所有人员
	* @title: getAllPerByOrg 
	* @description: TODO
	* @author gxh
	* @param page
	* @param orgId
	* @return
	* @date 2013-5-6 下午01:04:34     
	* @throws
	 */
	public Page<HashMap> getAllPerByOrg(final Page<HashMap> page, String orgId){
		if(null == orgId || orgId.trim().length() < 1){
			return page;
		}
		Map<String,Object> cMap = new HashMap<String,Object>();
		cMap.put("orgId", orgId);		
		
		return sqlDao.findPage(page, "PersonnelSQL.getAllPerByOrg", cMap);
	}
	
	
	/**
	 * 根据组织id得到组织对象
	* @title: modOrgByid 
	* @description: TODO
	* @author gxh
	* @param orgId
	* @return
	* @date 2013-5-6 下午08:32:46     
	* @throws
	 */
  public Org modOrgByid(Long orgId){
		List<Org> list =super.dao.findByProperty(Org.class,"orgId", orgId);
		if(list!=null && list.size()>=1){
			return list.get(0);
		} else{
			return null;
		}
	 
  }
  /**
   * 保存组织机构
  * @title: saveOrg 
  * @description: TODO
  * @author gxh
  * @param o
  * @date 2013-5-6 下午08:54:03     
  * @throws
   */
  public Org updateOrg(Long orgId) {
	  Org o = (Org) dao.findById(Org.class, orgId);
	  return o;
		
	}
  
  public void saveEntit1(Object entity) {
	  dao.saveOrUpdate(entity);
  }
}
