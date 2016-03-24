package com.cosmosource.app.personnel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.CmsArticle;
import com.cosmosource.app.entity.CsRepairFault;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.utils.Constant;
import com.cosmosource.base.service.BaseManager;

/**
 * 
* @description: 客服人员服务类
* @author herb
* @date May 10, 2013 6:18:25 PM
 */
public class CusServiceManager extends BaseManager {
	
	/**
	 * 
	* @title: findUnitList 
	* @description: 查询单位列表
	* @author herb
	* @param string
	* @param map
	* @return
	* @date May 10, 2013 6:25:25 PM     
	* @throws
	 */
	public List<Map> findUnitList() {
		Map map = new HashMap();
		map.put("level_num", Org.LEVELNUM_2);
		return sqlDao.findList("IntegrateSQL.loadFirstOrg", map);
	}
	
	/**
	 * 
	* @title: selectOrgUserCount 
	* @description: 查询机构总人数
	* @author herb
	* @param object
	* @date May 10, 2013 8:00:19 PM     
	* @throws
	 */
	public int selectOrgUserCount(String orgId) {
		Map map = new HashMap();
		map.put("orgId", orgId);
		List<Map> list = sqlDao.findList("PersonnelUnitSQL.selectOrgUserCount", map);
		int result = 0;
		if (null != list) {
			result = Integer.valueOf(list.get(0).get("C").toString());
		}
		return result;
	}
	
	/**
	 * 
	* @title: selectVisitCount 
	* @description: 查询访客总数
	* @author herb
	* @return
	* @date May 10, 2013 8:10:08 PM     
	* @throws
	 */
	public int selectVisitCount() {
		List<Map> list = sqlDao.findList("PersonnelUnitSQL.selectVisitCount", null);
		int result = 0;
		if (null != list) {
			result = Integer.valueOf(list.get(0).get("C").toString());
		}
		return result;
	}
	
	/**
	 * 
	* @title: queryUntreatedSheet 
	* @description: 查询未处理的报修单据列表
	* @author herb
	* @return
	* @date May 10, 2013 10:11:39 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map> queryUntreatedSheet(String appType) {
		Map map = new HashMap();
		map.put("crfFlag", CsRepairFault.CRF_FLAG_APP);
		return sqlDao.findList("IntegrateSQL.queryUntreatedSheet", map);
	}
	
	/**
	 * @description: 加载CMS 失物招领
	 * @author qiyanqiang
	 * @return List<HashMap>
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> loadCmsLostFound() {
		Map map = new HashMap();
		map.put("cmsLostFound", Constant.CMS_LOST_AND_FOUND);
		map.put("cartFlag", CmsArticle.NO_CLAIM);	// 未认领
		
		return sqlDao.findList("IntegrateSQL.loadCmsLostFound", map);
	}
	
	/**
	 * @description: 加载CMS 失物招领
	 * @author qiyanqiang
	 * @return List<HashMap>
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap> loadCmsLostFoundAll() {
		Map map = new HashMap();
		
		return sqlDao.findList("IntegrateSQL.loadCmsLostFound", map);
	}
}
