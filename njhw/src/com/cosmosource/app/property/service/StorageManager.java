package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrStorage;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @description: 库存信息的增删改查接口
 * @author tangtq
 * @date 2013-7-3 14:43:41
 */
public class StorageManager extends BaseManager {

	/**
	 * 
	 * @title: loadGrStorageInfo
	 * @description: 得到库存信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:48:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrStorage> loadGrStorageInfo() {
		return dao.findByHQL("select t from GrStorage t");
	}

	/**
	 * 
	 * @title: addGrStorageInfo
	 * @description: 插入库存信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrStorageInfo(long deviceId, long deviceTypeId,
			String storageInventory) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("storageId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_STORAGE_SEQ_VALUE"))));
			parMap.put("deviceId", deviceId);
			parMap.put("deviceTypeId", deviceTypeId);
			parMap.put("storageInventory", storageInventory);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/**
	 * 
	 * @title: 
	 * @description: 库存设备类型修改
	 * @author 
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean modifyGrStorageInfo(long deviceId, long deviceTypeId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("deviceId", deviceId);
			parMap.put("deviceTypeId", deviceTypeId);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.modifyStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/**
	 * 
	 * @title: 
	 * @description: 出入库库存设备类型修改
	 * @author 
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean modifyInOutGrStorageInfo(long deviceId, long deviceTypeId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("deviceId", deviceId);
			parMap.put("deviceTypeId", deviceTypeId);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.modifyInOutStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/**
	 * 
	 * @title: updateGrStorageInfoByStorageId
	 * @description: 根据库存ID修改库存数量
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:52:15
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean updateGrStorageInfoByStorageId(Long storageId,
			Long storageInventory) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("storageId", storageId);
			parMap.put("storageInventory", storageInventory);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updateStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: deleteGrDeviceInfoByDeviceId
	 * @description: 根据库存ID删除库存信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:55:37
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrStorageInfoByStorageId(long storageId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("storageId", storageId);
			parList.add(parMap);
			sqlDao.batchDelete("PropertySQL.deleteStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: loadGrStorageInfoByDeviceId
	 * @description: 根据设备ID得到设备库存信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrStorage loadGrStorageInfoByDeviceId(Long deviceId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrStorage storage = new GrStorage();
		try {
			Map parMap = new HashMap();
			parMap.put("deviceId", deviceId);
			resultList = sqlDao.findList(
					"PropertySQL.getStorageInfoByDeviceId", parMap);
			if (resultList.size() > 0) {
				if (resultList.get(0).get("STORAGE_ID") != null) {
					storage.setStorageId(Long.parseLong(String.valueOf(resultList
							.get(0).get("STORAGE_ID"))));
				}

				if (resultList.get(0).get("DEVICE_ID") != null) {
					storage.setDeviceId(Long.parseLong(String.valueOf(resultList
							.get(0).get("DEVICE_ID"))));
				}

				if (resultList.get(0).get("DEVICE_TYPE_ID") != null) {
					storage.setDeviceTypeId(Long.parseLong(String
							.valueOf(resultList.get(0).get("DEVICE_TYPE_ID"))));
				}

				if (resultList.get(0).get("STORAGE_INVENTORY") != null) {
					storage.setStorageInventory(Long.parseLong(String.valueOf(resultList.get(0)
							.get("STORAGE_INVENTORY"))));
				}
				
			}
		} catch (Exception e) {}

		return storage;
	}

	/**
	 * 
	 * @title: getGrStorageList
	 * @description: 分页得到库存信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrStorageList(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectAllStorage", parMap);
	}
	
	public List getGrStorageListExport(Map parMap){
		return sqlDao.findList("PropertySQL.selectAllStorage", parMap);
	}
}
