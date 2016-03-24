package com.cosmosource.app.caller.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.base.service.BaseManager;
/**
 * 没有预约的访客前台登记类
* @description: TODO
* @author gxh
* @date 2013-4-2 下午11:31:25
 */
public class NoOrderManager extends BaseManager{
	
	/**
	 * 描述：保存对象
	 *  日期：2012-3-17
	 * @param t
	 */
	public void save(Object t){
		super.dao.saveOrUpdate(t);
		//super.dao.flush();
	}
	/**
	* @描述: 根据ID取实体
	* @作者：gxh
	* @日期：2013-3-16 
	* @param entityClass
	* @param id
	* @return
	* @return Object
	 */
	@SuppressWarnings("rawtypes")
	public Object findById(final Class entityClass,final Serializable id) {
		return dao.findById(entityClass, id);
	}
	
	/**
	 * @说明：通过身份证号得到访客基本信息对象
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VmVisitorinfo findVmVisitorinfoByCardId(String carId) {
		List<VmVisitorinfo> list =dao.findByProperty(VmVisitorinfo.class,"residentNo", carId);
		if(list!=null && list.size()>0){
			return list.get(0);
			
			
		}else{
			return null;
		}
	}
	/**
	 * @说明：通过市民卡号得到访客基本信息对象
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VmVisitorinfo findVmVisitorinfoByCityCard(String carId) {
		List<VmVisitorinfo> list =dao.findByProperty(VmVisitorinfo.class,"resBak1", carId);
		if(list!=null && list.size()>0){
			return list.get(0);
			
		}else{
			return null;
		}
	}
	/**
	 * @说明：通过证件号码得到访客基本信息对象
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VmVisitorinfo findVmVisitorinfoByCertificateNo(String carId) {
		List<VmVisitorinfo> list =dao.findByProperty(VmVisitorinfo.class,"resBak3", carId);
		if(list!=null && list.size()>0){
			return list.get(0);
			
		}else{
			return null;
		}
	}
	/**
	 * @说明：通过市民卡号得到市民卡信息对象
	 * @author gxh
	 * @param cardId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean exsitNjhwTscardByCardId(String cardId){
		List<NjhwTscard> list =super.dao.findByProperty(NjhwTscard.class,"cardId", cardId);
		if(list!=null && list.size()>0){
			return true;
		}else{
			return false;
		}
			
	}
	
	/**
	 * 
	 * @title: playOrgList
	 * @description: 把机构做层次缩进
	 * @author gxh
	 * @param tempList
	 * @return
	 * @date 2013-4-2 下午04:10:08
	 * @throws
	 */
	private void playOrgList(List<Org> orgs, List<Org> orderList, Long orgId,
			String level) {
		try {
			level += "--";
			for (Org org : orgs) {
				if (org.getPId() != null && org.getPId().equals(orgId)) {
					org.setName(level + org.getName());
					orderList.add(org);
					playOrgList(orgs, orderList, org.getOrgId(), level);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @title: getAllOrgs 
	* @description: 得到所有的组织机构
	* @author cjw
	* @return
	* @date 2013-3-25 下午02:49:28     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Org> getAllOrgs(){
		List<Org> orgs = new ArrayList<Org>();
		List<Org> orderList = new ArrayList<Org>();
		orgs = dao.findByHQL("select o from Org o order by o.orgId ");
		if(null != orgs && orgs.size()>0){
			playOrgList(orgs, orderList, orgs.get(0).getOrgId(), "");
		}
		return orgs;
	}
	
	public String orgName(Long orgId){
		//try{
		List<Org> orgs = new ArrayList<Org>();
		orgs = dao.findByHQL("select o from Org o where o.orgId= "+orgId);
		if(null != orgs && orgs.size()>0){
       	return orgs.get(0).getName();
		}
		//}catch(Exception e){
			//e.printStackTrace();
			
		//}
		return null;
		
	}
	
	public String userName(Long userId){
		try{
		List<Users> users = new ArrayList<Users>();
		users = dao.findByHQL("select u from Users u where u.userid ="+userId);
		if(null != users && users.size()>0){
       	return users.get(0).getDisplayName();
		}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}
	
	/**
	 * 
	* @title: getUsersByOrgId 
	* @description: 根据组织的id得到用户对象
	* @author cjw
	* @param orgId
	* @return
	* @date 2013-3-21 下午07:08:06     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Users> getUsersByOrgId(String orgId) {
		return dao.findByHQL("select u.userid, u.displayName from Users u where u.orgId = ?",Long.parseLong(orgId));
	}
	
//	/**
//	 * @description:根据组织的id得到用户的信息
//	 * @author cjw
//	 * @return List
//	 * @date 2013-03-21
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Users> getUsersByOrgId(String orgId) {
//		return dao.findByHQL("select u.userid, u.displayName from Users u where u.orgId = ?",Long.parseLong(orgId));
//	}
	
	/**
	 * 
	* @title: exsitVmVisit 
	* @description: 判断该访客是否已经预约
	* @author gxh
	* @param userId
	* @param orgId
	* @param viId
	* @return
	* @date 2013-3-28 下午05:28:39     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean  exsitVmVisit(String cardId){
		List<NjhwTscard> list =super.dao.findByProperty(NjhwTscard.class,"cardId", cardId);
		if(list!=null && list.size()>=1){
			return true;
		} else{
			return false;
		}
	}
	/**
	 * 
	* @title: findVmVisitorinfoByCardId 
	* @description: 通过身份证号得到访客基本信息对象
	* @author gxh
	* @param carId
	* @return
	* @date 2013-3-20 下午06:26:36     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public VmVisitorinfo findVmVisitorinfoByUserId(Long userId) {
		List<VmVisitorinfo> list =dao.findByProperty(VmVisitorinfo.class,"nvrId",userId);
		if(list!=null && list.size()>=1){
			return list.get(0);
		}else{
			return null;
		}
	}
}
