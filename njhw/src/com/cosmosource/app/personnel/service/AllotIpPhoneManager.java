package com.cosmosource.app.personnel.service;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Org;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
/** 
* @description: 分配Ip电话号码给部门
* @author zh
* @date 2013-03-23
*/
@SuppressWarnings("unchecked")
public class AllotIpPhoneManager extends BaseManager {
	/**
	 * @description:加载一级委办局
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-03-23
	 */
	public Page<HashMap> queryOrgList(Page<HashMap> page, HashMap<String, String> parMap) {
		return dao.findPage(page, "select t from Org t where t.levelNum = ?  order by t.orderNum asc", Org.LEVELNUM_2);
	}
	
	/**
	 * @description:查询Ip电话号码分配信息
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-03-23
	 */
	public Page<HashMap> queryCarNumList(final Page<HashMap> page, HashMap<String, String> parMap) {
		parMap.put("eortype", EmOrgRes.EOR_TYPE_TEL_NO);
		return sqlDao.findPage(page, "PersonnelSQL.queryNumList", parMap);
	}
	
	/**
	 * @description:加载一级机构（委办局）
	 * @author zh
	 * @return List
	 * @date 2013-03-24
	 */
	public List<Org> loadOrg() {
		return dao.findByHQL("select t from Org t where t.levelNum = ?", Org.LEVELNUM_2);
	}
	
	/**
	 * @description:批量删除
	 * @author zh
	 * @param ids
	 * @date 2013-03-23
	 */
	public void deleteCarNum(String[] ids){
		dao.deleteByIds(EmOrgRes.class, ids);
	}
	
	/**
	 * @description:保存/修改
	 * @author zh
	 * @return
	 * @date 2013-03-23
	 */
	public void save(EmOrgRes emOrgRes) {
		for (String resName : emOrgRes.getResName().split(",")) {
			if (emOrgRes.getEorId() != null){
				EmOrgRes emOR = (EmOrgRes) dao.findById(EmOrgRes.class, emOrgRes.getEorId());
				emOR.setResName(resName);
				emOR.setModifyDate(DateUtil.getSysDate());
				emOR.setModifyId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				dao.update(emOR);
				//dao.flush();
			} else {
				int num = dao.findByHQL("select t from EmOrgRes t where t.eorType = ? and t.resName = ?", EmOrgRes.EOR_TYPE_TEL_NO, resName).size();
				if (num > 0) continue;
				EmOrgRes emOR = new EmOrgRes();
				emOR.setOrgId(emOrgRes.getOrgId());
				emOR.setOrgName(emOrgRes.getOrgName());
				emOR.setResName(resName);
				emOR.setEorType(EmOrgRes.EOR_TYPE_TEL_NO);	// 分配Ip电话号码
				emOR.setPorFlag("1");	// 有效
				emOR.setInsertDate(DateUtil.getSysDate());
				emOR.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				dao.save(emOR);
				//dao.flush();
			}
		}
	}
	
	/**
	 * @description:加载Ip电话号码分配信息
	 * @author zh
	 * @return List
	 * @date 2013-03-25
	 */
	public List loadIpPhoneNumInfo(String resName) {
		return dao.findByHQL("select t.orgName from EmOrgRes t where t.eorType = ? and t.resName = ?", EmOrgRes.EOR_TYPE_TEL_NO, resName);
	}
	
	/**
	 * @description:提交前验证所有Ip电话号码是否存在已分配的
	 * @author zh
	 * @return List<HashMap>
	 * @date 2013-03-25
	 */
	public List<HashMap> validAllIpPhoneNum(String resNames) {
		List<HashMap> list = null;
		try {
			String [] carNums = resNames.split(",");
			HashMap map = new HashMap();
			map.put("nums", carNums);
			map.put("eortype", EmOrgRes.EOR_TYPE_TEL_NO);
			if (carNums.length > 0) list = sqlDao.findList("PersonnelSQL.preSaveValid", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
