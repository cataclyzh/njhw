package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.CsRepairFault;
import com.cosmosource.app.entity.Org;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

/** 
* @description: 物业呼叫中心
* @author zh
* @date 2013-03-21
*/ 
@SuppressWarnings("unchecked")
public class CallCenterManager extends BaseManager {
	/**
	 * @description:查询所有报修单
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-03-21
	 */
	public Page<HashMap> querySheet(final Page<HashMap> page, HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectList", parMap);
	}
	
	/**
	 * @description:我的维修单
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-03-21
	 */
	public Page<HashMap> mySheet(final Page<HashMap> page, HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectList", parMap);
	}

	/**
	 * @description:显示一键报修页面，加载所有的设备类型信息
	 * @author zh
	 * @return List
	 * @date 2013-03-21
	 */
	public List loadAssetype() {
		return dao.findByHQL("select t from TcAssetype t");
	}
	
	/**
	 * @description:根据设备类型ID，加载所有的设备信息
	 * @author zh
	 * @return List
	 * @date 2013-03-21
	 */
	public List loadAssetinfo(String aetId) {
		return dao.findByHQL("select t.easId, t.easName from EmAssetinfo t where t.aetId = ?", Long.parseLong(aetId));
	}

	/**
	 * @description:保存报销单信息
	 * @author zh
	 * @return
	 * @date 2013-03-21
	 */
	public void save(CsRepairFault csRepairFault) {
		csRepairFault.setCrfFlag(CsRepairFault.CRF_FLAG_APP);		// 申请
		csRepairFault.setCrfRt(DateUtil.getSysDate());
		csRepairFault.setUserId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		csRepairFault.setUserName(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
		dao.save(csRepairFault);
		//dao.flush();
	}
	
	/**
	 * @description:根据ID获取报修单信息
	 * @author zh
	 * @return CsRepairFault
	 * @date 2013-03-21
	 */
	public CsRepairFault getCsRepairFaultById(long crfid) {
		return (CsRepairFault)dao.get(CsRepairFault.class, crfid);
	}
	
	/**
	 * @description:加载维修人员
	 * @author zh
	 * @return CsRepairFault
	 * @date 2013-03-21
	 */
	public List getMaintainPerson() {
		return dao.findByHQL("select t from Users t, Org o where t.orgId= o.orgId and o.name = ?", "工程部");
	}
	
	/**
	 * @description:分派报修单给指定的维修人员
	 * @author zh
	 * @param crfId 报修单ID
	 * @param crfBak1 维修人员ID
	 * @return
	 * @date 2013-03-21
	 */
	public int saveAllot(CsRepairFault csRepairFault) {
		int status = 0;
		CsRepairFault csRepair = (CsRepairFault) dao.get(CsRepairFault.class, csRepairFault.getCrfId());
		if ("01".equals(csRepair.getCrfFlag())) {
			csRepair.setCrfUid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));	// 派单人ID
			csRepair.setCrfUname(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());	// 派单人名称
			csRepair.setCrfDt(DateUtil.getSysDate());
			csRepair.setCrfFlag(CsRepairFault.CRF_FLAG_SURE);		// 分派
			csRepair.setCrfBak1(csRepairFault.getCrfBak1());
			csRepair.setCrfBak2(csRepairFault.getCrfBak2());
			dao.update(csRepair);
			//dao.flush();
		} else {
			status = 1;
		}
		return status;
	}
	
	/**
	 * @description:完成报修
	 * @author zh
	 * @param crfId 报修单ID
	 * @param crfDesc 回执内容
	 * @return
	 * @date 2013-03-21
	 */
	public int saveComplete(long crfId, String crfRdesc) {
		int status = 0;
		CsRepairFault csRepair = (CsRepairFault) dao.get(CsRepairFault.class, crfId);
		if ("02".equals(csRepair.getCrfFlag())) {
			csRepair.setCrfPt(DateUtil.getSysDate());
			csRepair.setCrfFlag(CsRepairFault.CRF_FLAG_FINISH);		// 完成
			csRepair.setCrfRdesc(crfRdesc);
			dao.update(csRepair);
			//dao.flush();
		} else {
			status = 1;
		}
		return status;
	}
	
	/**
	 * @description:报修评价
	 * @author zh
	 * @param crfId 报修单ID
	 * @param crfComment 评价内容
	 * @return
	 * @date 2013-03-21
	 */
	public int saveAssess(long crfId, String crfSatis, String crfComment) {
		int status = 0;
		CsRepairFault csRepair = (CsRepairFault) dao.get(CsRepairFault.class, crfId);
		if ("03".equals(csRepair.getCrfFlag())) {
			csRepair.setCrfCt(DateUtil.getSysDate());
			csRepair.setCrfFlag(CsRepairFault.CRF_FLAG_EVAL);		// 评价
			csRepair.setCrfSatis(crfSatis);
			csRepair.setCrfComment(crfComment);
			dao.update(csRepair);
			//dao.flush();
		} else {
			status = 1;
		}
		return status;
	}
	
	/**
	 * @description:删除报修单
	 * @author zh
	 * @param crfid 报修单ID
	 * @return
	 * @date 2013-03-21
	 */
	public int delete(long crfId) {
		CsRepairFault csRepair = (CsRepairFault) dao.get(CsRepairFault.class, crfId);
		if (CsRepairFault.CRF_FLAG_APP.equals(csRepair.getCrfFlag())) {
			dao.delete(csRepair);
			//dao.flush();
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * @description:统计报修
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-03-21
	 */
	public List statisticsSheet(HashMap parMap) {
		// 取得指定委办局及下属机构的ID
		List<Long> list = dao.findByHQL("select t.orgId from Org t where t.PId = ?", Long.parseLong(parMap.get("orgId").toString()));
		List ids = new ArrayList();
		for (Long resId : list) {
			if (resId != null && resId > 0) ids.add(Long.parseLong(resId.toString()));
		}
		ids.add(Long.parseLong(parMap.get("orgId").toString()));
		parMap.put("orgIds", ids);
		return sqlDao.findList("PropertySQL.statisticsSQL", parMap);
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
	 * @description:加载数据库中所有的报修年份
	 * @author zh
	 * @return List
	 * @date 2013-03-29
	 */
	public List<HashMap> loadYears() {
		return sqlDao.findList("PropertySQL.loadYearsSQL", null);
	}
	
	/**
	 * @description:加载数据库中所有的报修年份
	 * @author zh
	 * @return List
	 * @date 2013-03-29
	 */
	public List<CsRepairFault> loadCsRepairFaultsTop5() {
		String hql=" select c.easName,c.crfFlag,c.userName,c.orgName,rownum from CsRepairFault c where rownum <10 order by c.crfRt desc";
		return dao.findByHQL(hql);
	}
}
