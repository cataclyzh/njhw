package com.cosmosource.app.integrateservice.service;

import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.NjhwAdlistGroup;
import com.cosmosource.app.entity.NjhwUsersAlist;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("unchecked")
public class PAddressBookManager extends BaseManager{
	/**
	 * @description:加载用户通讯录分组
	 * @author zh
	 * @return Page<NjhwAdlistGroup>
	 */
//	public Page<NjhwAdlistGroup> loadGroupList(Page<NjhwAdlistGroup> page, Map map) {
//		return sqlDao.findPage(page, "PABSQL.loadGroupList", map);
//	}
	public List loadGroupList(Map map) {
		return sqlDao.findList("PABSQL.loadGroupListAndCount", map);
	}
	
	public List loadNoGroupPersonList(Map map) {
		return sqlDao.findList("PABSQL.loadAddListByGId", map);
	}
	
	/**
	 * @description:加载指定分组通讯录详情
	 * @author zh
	 * @return Page<NjhwAdlistGroup>
	 */
	public Page<NjhwUsersAlist> loadAddListByGId(Page<NjhwUsersAlist> page, Map map) {
		return sqlDao.findPage(page, "PABSQL.loadAddListByGId", map);
	}
	public List loadAddListByGId(Map map) {
		return sqlDao.findList("PABSQL.loadAddListByGId", map);
	}
	
	/**
	 * @description:保存通讯录信息
	 * @author zh
	 * @return Page<NjhwAdlistGroup>
	 */
	public void saveAddress(NjhwUsersAlist njhwUsersAlist) {
		try {
			NjhwUsersAlist usersA = null;
			if (njhwUsersAlist.getNuaId() != null && njhwUsersAlist.getNuaId() > 0) {
				usersA = (NjhwUsersAlist) dao.findById(NjhwUsersAlist.class, njhwUsersAlist.getNuaId());
			} else {
				usersA = new NjhwUsersAlist();
				usersA.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				usersA.setInsertDate(DateUtil.getSysDate());
			}
			usersA.setNuaName(njhwUsersAlist.getNuaName());
			usersA.setUserid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			usersA.setUserName(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
			usersA.setNuaName1(njhwUsersAlist.getNuaName1());
			usersA.setNuaPhone(njhwUsersAlist.getNuaPhone());
			usersA.setNuaTel1(njhwUsersAlist.getNuaTel1());
			usersA.setNuaTel2(njhwUsersAlist.getNuaTel2());
			usersA.setNuaTel3(njhwUsersAlist.getNuaTel3());
			usersA.setNuaGroup(njhwUsersAlist.getNuaGroup());
			usersA.setNuaMail(njhwUsersAlist.getNuaMail());
			
			if (njhwUsersAlist.getNuaId() != null && njhwUsersAlist.getNuaId() > 0)  dao.update(usersA);
			else dao.save(usersA);
			//dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @description:批量删除
	 * @author zh
	 * @param ids
	 * @date 2013-05-3
	 */
	public void deleteAddress(String[] ids){
		dao.deleteByIds(NjhwUsersAlist.class, ids);
		//dao.flush();
	}
	
	/**
	 * @description:保存通讯录信息
	 * @author zh
	 * @return Page<NjhwAdlistGroup>
	 */
	public void saveGroup(String nagId, String nagName) {
		try {
			NjhwAdlistGroup group = null;
			if (StringUtil.isNotEmpty(nagId)) group = (NjhwAdlistGroup) dao.findById(NjhwAdlistGroup.class, Long.parseLong(nagId));
			else group = new NjhwAdlistGroup();
			
			group.setNagName(nagName);
			group.setNagType(NjhwAdlistGroup.NAG_TYPE_CUSTOM);
			group.setNagUser(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			
			if (StringUtil.isNotEmpty(nagId))  dao.update(group);
			else dao.save(group);
			
			//dao.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @description:批量删除
	 * @author zh
	 * @param ids
	 * @date 2013-05-3
	 */
	public void deleteGroup(String[] ids){
		// 删除掉组与人员关联关系
		dao.batchExecute("update NjhwUsersAlist t set t.nuaGroup = ? where t.nuaGroup = ?", 0L, Long.parseLong(ids[0]));
		
		// 删组
		dao.deleteByIds(NjhwAdlistGroup.class, ids);
		//dao.flush();
	}
}
