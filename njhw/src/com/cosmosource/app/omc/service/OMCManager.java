package com.cosmosource.app.omc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.omc.model.OMCDeviceInfo;
import com.cosmosource.app.omc.model.OMCRepairInfo;
import com.cosmosource.app.omc.model.OMCSluiceFlowInfo;
import com.cosmosource.app.omc.model.OMCVisitorInfo;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * 运营管理主页 业务处理
 * @author Administrator
 *
 */
public class OMCManager extends BaseManager
{
    /**
     * 查询所有设备的数量，以及状态
     * @return 
     */
    public OMCDeviceInfo queryDeviceInfo()
    {
        OMCDeviceInfo omcDeviceInfo = null;
        
        omcDeviceInfo = (OMCDeviceInfo)sqlDao.getSqlMapClientTemplate().queryForObject("OMC_SQL.QUERY_GATE_LOCK_STATUS");
        
        return omcDeviceInfo;
    }
    
    /**
     * 查询维修设备信息
     * 
     */
    public OMCRepairInfo queryrRepairInfo()
    {
        OMCRepairInfo omcRepairInfo = (OMCRepairInfo)sqlDao.getSqlMapClientTemplate().queryForObject("OMC_SQL.QUERY_STATISTICAL_REPAIR");
        return omcRepairInfo;
    }
    
    /**
     * 查看闸机人流
     * 
     */
    public OMCSluiceFlowInfo queryrGateFlow()
    {
        OMCSluiceFlowInfo omcSluiceFlowInfo = (OMCSluiceFlowInfo)sqlDao.getSqlMapClientTemplate().queryForObject("OMC_SQL.QUERY_SLUICE_FLOW");
       return omcSluiceFlowInfo;
    }
    
    /**
     * 查询访客流量信息
     * 
     * @return
     */
    public OMCVisitorInfo queryVisitorInfo()
    {
        OMCVisitorInfo omcVisitorInfo = null;
        omcVisitorInfo = (OMCVisitorInfo)sqlDao.getSqlMapClientTemplate().queryForObject("OMC_SQL.QUERY_VISITOR_IFNO");
        return omcVisitorInfo;
    }
    
    /**
     * 查询最新上访人的信息
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<OMCVisitorInfo> queryTopVisitor(int top)
    {
        List<OMCVisitorInfo> resultList = sqlDao.getSqlMapClientTemplate().queryForList("OMC_SQL.QUERY_TOP_VISITOR",top);
        return resultList;
    }
    
    /**
     * 查询设备状态信息
     * 
     * @param page
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<HashMap> queryDeviceInfoToView(Page<HashMap> page, Map<String, Integer> map)
    {
        return sqlDao.findPage(page, "OMC_SQL.QUERY_DEVEICE_TO_VIEW", map);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Page<HashMap> queryAllParkinglotInfo(Page<HashMap> page, Map map) {
		return sqlDao.findPage(page, "PropertySQL.getDailyCarCount", map);
	}
}
