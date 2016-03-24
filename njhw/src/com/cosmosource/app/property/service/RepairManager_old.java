package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.List;

import com.cosmosource.app.entity.CsRepairFault;
import com.cosmosource.app.entity.EmAssetinfo;
import com.cosmosource.app.entity.TcAssetype;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

public class RepairManager_old extends BaseManager{
	
	
	public void saveObj(Object obj){
		if(obj != null){
			dao.saveOrUpdate(obj);
		}
	}
	
	public void delObj(Object obj){
		if(obj != null){
			dao.delete(obj);
		}
	}
	
	/**
	 * 
	* @title: loadTcAsstypeInfo 
	* @description: 得到所有的设备类型
	* @author cjw
	* @return
	* @date 2013-3-22 下午01:05:02     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<TcAssetype> loadTcAsstypeInfo() {
		return dao.findByHQL("select t from TcAssetype t");
	}
	
	
	/**
	 * 
	* @title: loadAssetinfo 
	* @description: 根据设备类型ID，加载所有的设备信息
	* @author cjw
	* @param aetId
	* @return
	* @date 2013-3-21 下午07:08:06     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<EmAssetinfo> loadEmAssetinfoByid(Long aetId) {
		return dao.findByHQL("select t.easId, t.easName from EmAssetinfo t where t.aetId = ?",aetId);
	}
	
	
	/**
	 * 
	* @title: loadAssetinfo 
	* @description: 根据设备类型ID，加载所有的设备信息
	* @author cjw
	* @param aetId
	* @return
	* @date 2013-3-21 下午07:08:06     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<EmAssetinfo> getmAssetinfoById(Long aetId) {
		return dao.findByHQL("select t from EmAssetinfo t where t.aetId = ?",aetId);
	}
	
	@SuppressWarnings("unchecked")
	public Page<CsRepairFault> getCsRepairFaultList(final Page<CsRepairFault> page,final CsRepairFault obj,String beginTime,String endTime){
		List<Object> list = new ArrayList<Object>();
		//条件是申请的预约 状态是 1的(主动预约)
		String hql=" select c from CsRepairFault c where 1=1 ";
		if(obj.getCrfFlag()!=null && !obj.getCrfFlag().equals("all")){
			hql+=" and c.crfFlag = ? ";
			list.add(obj.getCrfFlag());
		}if(StringUtil.isNotBlank(beginTime) && StringUtil.isBlank(endTime)){
			hql+=" and  c.crfRt >= ?";
			list.add(DateUtil.StringToDateFormat(beginTime));
		}if(StringUtil.isBlank(beginTime) && StringUtil.isNotBlank(endTime)){
			hql+=" and  c.crfRt <= ?";
			list.add(DateUtil.StringToDateFormat(endTime));
		}if(StringUtil.isNotBlank(beginTime) && StringUtil.isNotBlank(endTime)){
			hql+=" and  c.crfRt >= ?";
			list.add(DateUtil.StringToDateFormat(beginTime));
			hql+=" and  c.crfRt <= ?";
			list.add(DateUtil.StringToDateFormat(endTime));
		}
		hql+=" order by c.crfId desc";
		return super.dao.findPage(page, hql, list.toArray());
	}

	/**
	 * 
	* @title: save 
	* @description: 保存报销单信息
	* @author cjw
	* @param csRepairFault
	* @date 2013-3-22 上午09:55:15     
	* @throws
	 */
	public void save(CsRepairFault csRepairFault){
		// 类型名称
		if (csRepairFault.getAetId() > 0) {
			TcAssetype assetype = (TcAssetype) dao.get(TcAssetype.class, csRepairFault.getAetId());
			csRepairFault.setAetName(assetype.getAetName());
		}
		// 设备名称
		System.err.println(csRepairFault.getEasId());
		if (csRepairFault.getEasId() > 0) {
			EmAssetinfo assetinfo = (EmAssetinfo) dao.get(EmAssetinfo.class, csRepairFault.getEasId());
			csRepairFault.setEasName(assetinfo.getEasName());
		}
		csRepairFault.setCrfFlag("01");		// 申请
		csRepairFault.setUserId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		csRepairFault.setUserName(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
		csRepairFault.setCrfRt(DateUtil.getSysDate());
		dao.saveOrUpdate(csRepairFault);
	}
}
