package com.cosmosource.app.personnel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * 
 * @description: 机构管理类
 * @author SQS
 * @date 2013-3-17 下午06:32:02
 */
public class PersonnelOrgAssignManager extends BaseManager {
	/**
	 * @title:
	 * @description: 取消已经分配给部门的门禁
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	public void deleteEmOrgResByOrgId(Long orgId,String nodeId) {
		String nodeIds[] = nodeId.split(",");
		String hql = " delete  EmOrgRes e where e.orgId=? and e.resId = ? and e.eorType=?";
		for(int i=0; i<nodeIds.length; i++){
			Query query = dao.createQuery(hql, orgId,Long.parseLong(nodeIds[i]) ,Objtank.EXT_RES_TYPE_2);
			query.executeUpdate();
		}
		
		
	}

	/**
	 * @title:
	 * @description: 取消已经分配给部门的IP电话
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	public void deleteEmOrgResByTellId(Long orgId,Long resId) {
		String hql = " delete  EmOrgRes e where e.orgId = ? and e.resId= ?";
		Query query = dao.createQuery(hql, orgId, resId);
		query.executeUpdate();
	}

	/**
	 * @title:
	 * @description: 查询机构列表数据 分配门禁时用
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getOrgan(final Page<HashMap> page,
			HashMap<String, String> map) {
		return sqlDao.findPage(page, "PersonnelSQL.selectOrganList", map);
	}

	/**
	 * @title:
	 * @description: 查询机构列表数据 分配IP电话时用
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> initOrgan(final Page<HashMap> page,
			HashMap<String, String> map) {
		return sqlDao.findPage(page, "PersonnelSQL.selectOrganListE", map);
	}

	/**
	 * @description:加载一级机构（委办局）
	 * @author SQS
	 * @return List
	 * @date 2013-03-19
	 */
	@SuppressWarnings("unchecked")
	public List<Org> loadOrg() {
		return dao.findByHQL("select t from Org t where t.levelNum = 2");
	}

	/**
	 * @description:加载已经分配IP电话的机构
	 * @author SQS
	 * @return List
	 * @date 2013-03-19
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> queryIpTellNumList(final Page<HashMap> page,
			HashMap<String, Long> parMap) {
		return sqlDao.findPage(page, "PersonnelSQL.IpTellList", parMap);
	}
	
	/**
	 * @description:IpPhone
	 * @author SQS
	 * @return List
	 * @date 2013年9月10日14:59:29
	 */

	@SuppressWarnings("unchecked")
	public Page<TcIpTel> loadIpPhone(final Page<TcIpTel> page,
			HashMap<String, String> map) {
		if (StringUtil.isNotEmpty(map.get("resType")) && "2".equals(map.get("resType")))
		{
			return sqlDao.findPage(page, "PersonnelSQL.selectFAXListAll", map);
		}
		else if (StringUtil.isNotEmpty(map.get("resType")) && "3".equals(map.get("resType")))
		{
			return sqlDao.findPage(page, "PersonnelSQL.selectWEBFAXListAll", map);
		}
		else
		{
			return sqlDao.findPage(page, "PersonnelSQL.selectTellListAll", map);
		}
		
	}
	
	
	/**
	 * @description:加载all ip电话
	 * @author zhangquanwei
	 * @return ListAll
	 * @date 2013年9月11日10:26:22
	 */

	@SuppressWarnings("unchecked")
	public List loadIpPhoneAll(HashMap<String, String> map) {
		if (StringUtil.isNotEmpty(map.get("resType")) && "2".equals(map.get("resType")))
		{
			return sqlDao.findList("PersonnelSQL.selectFAXListAll", map);
		}
		else if (StringUtil.isNotEmpty(map.get("resType")) && "3".equals(map.get("resType")))
		{
			return sqlDao.findList("PersonnelSQL.selectWEBFAXListAll", map);
		}
		else
		{
			return sqlDao.findList("PersonnelSQL.selectTellListAll", map);
		}
		
	}
	
	/**
	 * @description:加载未分配的ip电话
	 * @author SQS
	 * @return List
	 * @date 2013-03-19
	 */

	@SuppressWarnings("unchecked")
	public List loadIpPhone(HashMap<String, String> map) {
		if (StringUtil.isNotEmpty(map.get("resType")) && "2".equals(map.get("resType")))
		{
			return sqlDao.findList("PersonnelSQL.selectFAXList", map);
		}
		else if (StringUtil.isNotEmpty(map.get("resType")) && "3".equals(map.get("resType")))
		{
			return sqlDao.findList("PersonnelSQL.selectWEBFAXList", map);
		}
		else
		{
			return sqlDao.findList("PersonnelSQL.selectTellList", map);
		}
		
	}
	
	/**
	 * @description:加载电话
	 * @author SQS
	 * @return List
	 * @date 2013-03-19
	 */
	@SuppressWarnings("unchecked")
	public List loadPhone() {
		// 获取已分配的IP电话ID
		List<Long> list = dao.findByHQL(
				"select t.resId from EmOrgRes t where t.eorType = ?", "3");
		List ids = new ArrayList();
		for (Long resId : list) {
			if (resId != null && resId > 0)
				ids.add(Long.parseLong(resId.toString()));
		}
		// 取得未分配的IP电话
		HashMap map = new HashMap();
		map.put("ids", ids);
		map.put("len", ids.size());
		return sqlDao.findList("PersonnelSQL.NoAllotIPList", map);
	}

	/**
	 * @description:分配Ip电话到机构
	 * @author SQS
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-03-19
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public void saveAllotIpTel(String ids, String orgId) {
		// 分解字符串为数组
		String[] strs = StringUtil.chop(ids).split(",");

		for (String telInfo : strs) {
			String[] tel = telInfo.split("_");
			List<EmOrgRes> list = dao
					.findByHQL(
							"select t from EmOrgRes t where t.orgId = ? and t.resId = ? ",
							Long.valueOf(orgId), Long.valueOf(tel[0]));

			if (null == list || list.size() < 1) {
				TcIpTel t = (TcIpTel) dao.findById(TcIpTel.class, Long.parseLong(tel[0]));
				Org o = (Org) dao.findById(Org.class, Long.parseLong(orgId));
				EmOrgRes emOrgRes = new EmOrgRes();
				emOrgRes.setOrgId(Long.valueOf(orgId));
				emOrgRes.setOrgName(o.getShortName());
				emOrgRes.setResId(Long.valueOf(t.getTelId()));
				emOrgRes.setResName(t.getTelNum());
				emOrgRes.setEorType(EmOrgRes.EOR_TYPE_PHONE); // 分配IP电话
				emOrgRes.setPorFlag("1"); // 1为有效 0为无效
				emOrgRes.setInsertDate(DateUtil.getSysDate());
				emOrgRes.setInsertId(Long.parseLong(Struts2Util.getSession()
						.getAttribute(Constants.USER_ID).toString()));

				try {
					dao.save(emOrgRes);
					// dao.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @description:删除已经分配的IP电话
	 * @author SQS
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-03-19
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public String saveDeleteAllotIpTel(String ids, String orgId) {
		String failNum = "";
		for (String telInfo : StringUtil.chop(ids).split(",")) {
			String[] tel =  telInfo.split("_");
			
			if ("4".equals(tel[2])) {
				failNum += tel[1] + ",";
			} else {
				deleteEmOrgResByTellId(Long.valueOf(orgId),Long.valueOf(tel[0]));
			}
		}
		
		if (failNum.endsWith(",")) {
			failNum = StringUtil.chop(failNum);
		}
		
		return failNum;
	}
	
	/**
	 * @title:
	 * @description: 查询IP电话列表数据
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getPhone(final Page<HashMap> page,
			HashMap<String, String> map) {
		return sqlDao.findPage(page, "PersonnelSQL.selectPhoneList", map);
	}

	/**
	 * @title:
	 * @description: 查询机构门禁列表数据
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getDoorCard(final Page<HashMap> page, HashMap map) {
		return sqlDao.findPage(page, "PersonnelSQL.selectDoorList", map);
	}

	/**
	 * @title:
	 * @description: 查询机构门禁列表数据(点击下一页)
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getPageList(final Page<HashMap> page, HashMap map) {
		return sqlDao.findPage(page, "PersonnelSQL.selectPageList", map);
	}

	/**
	 * @title:
	 * @description: 找到机构对象
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Org findOrg(String orgId) {
		List<Org> list = super.dao.findByProperty(Org.class, "orgId", Long
				.parseLong(orgId));
		if (list != null && list.size() >= 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @title:
	 * @description: 把IP电话和门禁保存到EmOrgRes中
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	public void save(Object o) {
		super.dao.saveOrUpdate(o);
	}

	/**
	 * @title:
	 * @description: 批量删除IP电话
	 * @author SQS ${tags}
	 * @date 2013-3-19
	 * @throws
	 */
	public void deleteIpTellNum(String[] ids) {
		dao.deleteByIds(EmOrgRes.class, ids);
	}

}