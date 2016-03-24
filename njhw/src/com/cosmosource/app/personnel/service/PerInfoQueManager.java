package com.cosmosource.app.personnel.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
/**
 *人员查询
* @description: TODO
* @author gxh
* @date 2013-5-3 上午09:50:23
 */
public class PerInfoQueManager extends BaseManager 
{
	/**
	* @title: queryPerInfo 
	* @description: 人员查询
	* @author gxh
	* @param page
	* @param displayName
	* @param residentNo
	* @param name
	* @return Page<HashMap>
	* @date 2013-5-3 上午09:51:02     
	*/
	public Page<HashMap> queryPerInfo(Page<HashMap> page, HashMap cMap) {
		return sqlDao.findPage(page, "PersonnelSQL.peInforQuery", cMap);
		
//		if (recu==null || !"on".equals(recu)) {
//			return sqlDao.findPage(page, "PersonnelSQL.peInforQuery", cMap);
//		}else{
//			return sqlDao.findPage(page, "PersonnelSQL.peInforQueryRecu", cMap);
//		}
	}
	
	/**
	* @描述: 根据ID取实体
	* @作者：zhangquanwei 
	* @日期：2013年8月19日14:47:12
	* @param entityClass
	* @param id
	* @return
	* @return Object
	 */
	public Object findById(final Class entityClass,final Serializable id) {
		return dao.findById(entityClass, id);
	}
	
	
	/**
	 * 
	* @Title: getRootOrgId 
	* @Description: 查询根部门
	* @author zhangqw
	* @date 2013年7月5日13:46:11
	* @param  orgId 子部门
	 */
	public List<Map> getRootOrgId(String orgId)
	{
		// 查询跟单位的id 
		Map<String,String> pMap =new HashMap<String, String>();
		pMap.put("orgId", orgId);
		List<Map> listOrg = this.findListBySql("PersonnelSQL.getRootOrgId", pMap);
		return listOrg;
		
	}
}
