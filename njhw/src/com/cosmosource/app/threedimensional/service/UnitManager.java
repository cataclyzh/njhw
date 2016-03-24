package com.cosmosource.app.threedimensional.service;

import com.cosmosource.base.service.BaseManager;

public class UnitManager extends BaseManager 
{
   //根据房间单位名称取房间单位ID
   public String getOrgIdByOrgName(String orgName)
   {
	   return (String)sqlDao.getSqlMapClientTemplate().queryForObject("ThreeDimensionalSQL.SELECT_ORGID_BY_ORGNAME", orgName);
   }
   
}
