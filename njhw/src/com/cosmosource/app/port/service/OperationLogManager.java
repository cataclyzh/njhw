package com.cosmosource.app.port.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.BmMonitor;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

public class OperationLogManager extends BaseManager{

	
	/**
	* @Description：查询log日志
	* @Author：hp
	* @Date：2013-6-1
	* @return
	* @throws Exception
	**/
	@SuppressWarnings("unchecked")
	public Page<Map> findLogs(Map map,Page page) throws Exception{
		Page<Map> monitors = this.findPageListBySql(page, "PortSQL.logQuery", null, null, map);
		List<Map> l = monitors.getResult();
		for(Map m:l){
			m.put("BMTYPE", com.cosmosource.app.entity.BmMonitor.getDeviceTypeMap().get(m.get("BMTYPE")));
		}
		return monitors;
	}
	
	 /**
	* @Description 日志列表
	* @Author：pingxianghua
	* @Date 2013-8-14 上午09:34:38 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public List<HashMap<String, Object>> findLogList(Map map){
		List<HashMap<String, Object>> list = sqlDao.findList("PortSQL.logQueryList", map);
		//List list = findListBySql("PortSQL.logQuery", map);
		return list;
	}
	
	
	
	/**
	* @Description：删除log日志
	* @Author：hp
	* @Date：2013-6-2
	* @param _chk
	* @throws Exception
	**/
	@SuppressWarnings("unchecked")
	public void deleteLogs(String[] _chk) throws Exception{
		Map map = new HashMap();
		map.put("chk", _chk);
		List<BmMonitor> list = this.findListBySql("PortSQL.logDelete", map);
		if(list != null){
			for (BmMonitor bmMonitor : list) {
				dao.delete(bmMonitor);
			}
		}
	}
}
