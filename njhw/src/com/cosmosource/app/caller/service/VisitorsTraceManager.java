package com.cosmosource.app.caller.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;

/**
 * 
* @description: 访客管理类
* @author SQS
* @date 2013-3-17 下午06:32:02
 */
public class VisitorsTraceManager extends BaseManager {
	/** 
	* @title: 
	* @description: 查询访客列表数据
	* @author SQS
	* ${tags}
	* @date 2013-3-17   
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public Page<VmVisit> getRegister(final Page<VmVisit> page,final Map<String,Object> obj,String beginTime,String endTime,String isLeave){
//		List<Object> list = new ArrayList<Object>();
//		String hql=" select v from VmVisit v where 1=1 ";
//		if(obj.get("cardId") != null && obj.get("cardId") != ""){
//			hql+=" and v.cardId like ? ";
//			list.add("%"+obj.get("cardId")+"%");
//		}
//		if(obj.get("viName") != null && obj.get("viName") != ""){
//			hql+=" and v.viName like ? ";
//			list.add("%"+obj.get("viName")+"%");
//		}
//		if(StringUtil.isNotBlank(beginTime) && StringUtil.isBlank(endTime)){
//			hql+=" and  v.vsSt >= ?";
//			list.add(DateUtil.StringToDateFormat(beginTime));
//		}
//		if(StringUtil.isBlank(beginTime) && StringUtil.isNotBlank(endTime)){
//			hql+=" and  v.vsSt <= ?";
//			list.add(DateUtil.StringToDateFormat(endTime));
//		}
//		if(StringUtil.isNotBlank(beginTime) && StringUtil.isNotBlank(endTime)){
//			hql+=" and  v.vsSt >= ?";
//			list.add(DateUtil.StringToDateFormat(beginTime));
//			hql+=" and  v.vsSt <= ?";
//			list.add(DateUtil.StringToDateFormat(endTime));
//		}
//		if(obj.get("vsFlag") != null && obj.get("vsFlag")!= "" &&  !obj.get("vsFlag").equals("all")){
//			hql+=" and v.vsFlag = ? ";
//			list.add(obj.get("vsFlag"));
//		}
//		if(obj.get("vsType") != null && obj.get("vsType")!= "" &&  !obj.get("vsType").equals("all")){
//			hql+=" and v.vsType = ? ";
//			list.add(obj.get("vsType"));
//		}
		Map map = new HashMap();
		map.put("cardId", obj.get("cardId"));
		map.put("viName", obj.get("viName"));
		
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("isLeave",isLeave);
		map.put("vsFlag", obj.get("vsFlag"));
		map.put("vsType", obj.get("vsType"));
		map.put("cardType", obj.get("cardType"));
		map.put("vsReturn", obj.get("vsReturn"));
		map.put("userName", obj.get("userName"));
		List<String> vsFlagList = new ArrayList<String>();
		vsFlagList.add(VmVisit.VS_FLAG_SURE);// 确定
		vsFlagList.add(VmVisit.VS_FLAG_COME);// 到访的
		vsFlagList.add(VmVisit.VS_FLAG_FINISH);// 正常结束
		vsFlagList.add(VmVisit.VS_FLAG_ERROR);// 异常结束
		//vsFlagList.add(VmVisit.VS_FLAG_CANCELED);// 取消
        map.put("vsFlagList",vsFlagList);
		
		return sqlDao.findPage(page, "CallerSQL.selectTraceCallerPageList", map);
//		return super.dao.findPage(page, hql, list.toArray());
	}
	
	/**
	 * 
	* @title: 
	* @description:   查看访客详细信息
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:28:51     
	* @throws
	 */
	@SuppressWarnings({"unchecked" })
	public Object findById(final Class entityClass,final Serializable id) {
		return dao.findById(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public Page<VmVisit> selectVmVisit(final Page<VmVisit> page, final VmVisit vmv){
		List list = new ArrayList();
		StringBuilder sHql = new StringBuilder();
		sHql.append("select v from VmVisit v ")
		.append("where v.cardId=? ");
		list.add(vmv.getCardId());
		if(StringUtil.isNotBlank(vmv.getViName())){
			sHql.append("or v.viname =? ");
			list.add(vmv.getViName());
		}
		if(StringUtil.isNotBlank(vmv.getVsFlag())){
			sHql.append("or v.vsFlag ? ");
			list.add(vmv.getVsFlag());
		}
		sHql.append(" order by v.cardId ");
		return dao.findPage(page, sHql.toString(), list.toArray());
	}
	
	/**
	 * @description: 加载访客位置点ID
	 * @author zh
	 * @param visitorName
	 * @return List<HashMap>
	 */
	public List<HashMap> loadVisitorPoints(long vsId) {
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("vsId", vsId);
		return sqlDao.findList("CallerSQL.loadVisitorPoints", map);
	}
}