package com.cosmosource.app.personnel.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;

/**
 * 
* @description:电话和号码管理 manager
* @author herb
* @date May 15, 2013 10:10:55 PM
 */
public class TelAndNumManager extends BaseManager  {
	
	public Page<HashMap> selectTelAndNumPageList(Page<HashMap> page,Map condition){
		page =  sqlDao.findPage(page, "PersonnelUnitSQL.selectTelAndNumPageList", condition);
		List<HashMap> l = page.getResult();
		Map map = null;
		String mac = "";
		Iterator<HashMap> i = l.iterator();
		while(i.hasNext()){
			map = i.next();
			mac =  (String)map.get("TEL_MAC");
			mac = StringUtils.replace(mac, "SEP", "");
			mac = StringUtil.SplitWithChar(mac, 2, "-");
			map.put("TEL_MAC", mac);
		}
		page.setResult(l);
		return page;
	}
	
	/**
	 *获取未分配电话数
	 * @param condition
	 * @return
	 */
	public Integer getUnallocatedTel(Map condition){
		condition.put("disStatus", "2");
		List list = sqlDao.findList( "PersonnelUnitSQL.selectTelAndNumPageList", condition);
		if(list!=null){
			return list.size();
		}
		return 0;
	}

	/**
	 *获取已分配电话数
	 * @param condition
	 * @return
	 */
	public Integer getAllocatedTel(Map condition){
		condition.put("disStatus", "1");
		List list = sqlDao.findList( "PersonnelUnitSQL.selectTelAndNumPageList", condition);
		if(list!=null){
			return list.size();
		}
		return 0;
	}
	
	/**
	 * 
	* @title: findUnitSub 
	* @description: 查询当前机构的全部下级机构
	* @author herb
	* @param valueOf
	* @return
	* @date May 16, 2013 4:20:59 PM     
	* @throws
	 */
	public List<Org> findUnitSub(Long orgId) {
		return dao.findByProperty(Org.class, "PId", orgId);
	}
	
	
	/**
	 * 
	* @title: findUnit 
	* @description: 查询当前机构
	* @author huangyongfa
	* @param valueOf
	* @return
	* @date May 16, 2013 4:20:59 PM     
	* @throws
	 */
	public Org findUnitById(Long orgId) {
		return (Org)dao.findUniqueBy(Org.class, "orgId", orgId);
	}
	
	public Users findUsersByUserId(String userid) {
		return (Users)dao.findUniqueBy(Users.class, "userid", Long.valueOf(userid));
	}
	
	/**
	 * 
	* @title: updateCancelUserTel 
	* @description: 取消电话分配
	* @author herb
	* @param telID
	* @date May 16, 2013 8:46:48 PM     
	* @throws
	 */
	public void updateCancelUserTel(String telID) {
		try{
			List<NjhwUsersExp> list = dao.findByProperty(NjhwUsersExp.class, "telId", Long.valueOf(telID));
			if (null == list)return;
			NjhwUsersExp exp = list.get(0);
			exp.setTelId(null);
			exp.setTelMac(null);
			exp.setTelNum(null);
			dao.update(exp);
			//dao.flush();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @title: findUserAllInfo 
	* @description: 查询用户全部信息
	* @author herb
	* @param valueOf
	* @return
	* @date May 18, 2013 10:23:27 AM     
	* @throws
	 */
	public NjhwUsersExp findUserAllInfo(Long userId) {
		return findUsersExpByUserid(userId);
	}
	
	/**
	 * 根据被访者id找到扩展信息
	* @title: findUsersExpByUserid 
	* @description: TODO
	* @author gxh
	* @param userid
	* @return
	* @date 2013-4-12 上午11:33:15     
	* @throws
	 */
	public NjhwUsersExp findUsersExpByUserid(Long userid){
		NjhwUsersExp exp = new NjhwUsersExp(); 
		List expList = dao.findByProperty(NjhwUsersExp.class, "userid", userid);
		if(expList != null && expList.size()>=1){
			exp = (NjhwUsersExp)expList.get(0);
			return exp;
		}
		return null;
	}
	
	/**
	 * 
	* @title: updateUserTel 
	* @description: 给人员分配ip电话
	* @author herb
	* @param userId
	* @param telId
	* @date May 18, 2013 11:11:07 PM     
	* @throws
	 */
	public void updateUserTel(String userId, String telId) {
		NjhwUsersExp u = findUsersExpByUserid(Long.valueOf(userId));
		TcIpTel tel = (TcIpTel)dao.findById(TcIpTel.class, Long.valueOf(telId));
		if (null != tel) {
			//u.setReqTelId(telId);
			u.setTelId(null);
			if(tel.getTelType()!=null){
				if(tel.getTelType().equals("1")){
					u.setTelNum(telId);
				}else if(tel.getTelType().equals("2")){
					u.setUepFax(telId);
				}else if(tel.getTelType().equals("3")){
					u.setWebFax(telId);
				}
			}
			u.setTelMac(tel.getTelMac());
			
		}
		dao.update(u);
		//dao.flush();

	}
	
	/**
	 * 
	* @title: saveRemoveUserTel 
	* @description: 取消电话分配
	* @author herb
	* @param userId
	* @date May 19, 2013 2:26:48 AM     
	* @throws
	 */
	public void saveRemoveUserTel(String userId,String telType) {
		NjhwUsersExp ou = findUsersExpByUserid(Long.valueOf(userId));
		ou.setTelId(null);
		ou.setTelMac("");
		if(telType!=null){
			if(telType.equals("1")){
				ou.setTelNum("");
			}else if(telType.equals("2")){
				ou.setUepFax("");
			}else if(telType.equals("3")){
				ou.setWebFax("");
			}
		}
		dao.update(ou);
		//dao.flush();
	}
	
	
	/**
	 * 
	* @title: updateTelInfo 
	* @description: 修改ip电话
	* @author herb
	* @param tel
	* @date May 18, 2013 11:21:36 PM     
	* @throws
	 */
	public void updateTelInfo(TcIpTel tel) {
		dao.update(tel);
	}
	
	/**
	 * 
	* @title: saveUnitTel 
	* @description: 保存单位电话申请
	* @author herb
	* @param tel
	* @date May 19, 2013 11:59:27 PM     
	* @throws
	 */
	public void saveUnitTel(TcIpTel tel) {
		dao.save(tel);
	}
}
