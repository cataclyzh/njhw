package com.cosmosource.app.property.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrInOutStorage;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @description: 库存进出库记录增删改查接口
 * @author tangtq
 * @date 2013-7-3 15:11:39
 */
public class InOutStorageManager extends BaseManager {

	/**
	 * 
	 * @title: loadGrInOutStorageInfo
	 * @description: 得到出入库记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:22:13
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrInOutStorage> loadGrInOutStorageInfo() {
		return dao.findByHQL("select t from GrInOutStorage t");
	}

	/**
	 * 
	 * @title: addGrInStorageInfo
	 * @description: 新增入库记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-11 10:52:46
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrInStorageInfo(Long deviceTypeId,Long deviceId, Long lenderUserId,
			String lenderUserName, Long lenderUserOrg,
			String lenderUserOrgName, Long authorUserId, Integer inoutStorageFlag,
			String inoutStorageInDetail, Long inoutStorageInNumber,
			Timestamp inoutStorageTime) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("inOutStorageId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_INOUT_STORAGE_SEQ_VALUE"))));
			parMap.put("deviceTypeId", deviceTypeId);
			parMap.put("deviceId", deviceId);
			parMap.put("lenderUserId", lenderUserId);
			parMap.put("lenderUserName", lenderUserName);
			parMap.put("lenderUserOrg", lenderUserOrg);
			parMap.put("lenderUserOrgName", lenderUserOrgName);
			parMap.put("authorUserId", authorUserId);
			parMap.put("inoutStorageFlag", inoutStorageFlag);
			parMap.put("inoutStorageInDetail", inoutStorageInDetail);
			parMap.put("inoutStorageInNumber", inoutStorageInNumber);
			parMap.put("inoutStorageTime", inoutStorageTime);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertInStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: addGrOutStorageInfo
	 * @description: 新增出库记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-11 10:52:46
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrOutStorageInfo(Long deviceTypeId,Long deviceId, Long lenderUserId,
			String lenderUserName, Long lenderUserOrg,
			String lenderUserOrgName,
			Long authorUserId, Integer inoutStorageFlag,
			String inoutStorageOutDetail, Long inoutStorageOutNumber,
			Timestamp inoutStorageTime) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("inOutStorageId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_INOUT_STORAGE_SEQ_VALUE"))));
			parMap.put("deviceTypeId", deviceTypeId);
			parMap.put("deviceId", deviceId);
			parMap.put("lenderUserId", lenderUserId);
			parMap.put("lenderUserName", lenderUserName);
			parMap.put("lenderUserOrg", lenderUserOrg);
			parMap.put("lenderUserOrgName", lenderUserOrgName);
			parMap.put("authorUserId", authorUserId);
			parMap.put("inoutStorageFlag", inoutStorageFlag);
			parMap.put("inoutStorageOutDetail", inoutStorageOutDetail);
			parMap.put("inoutStorageOutNumber", inoutStorageOutNumber);
			parMap.put("inoutStorageTime", inoutStorageTime);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertOutStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: updateGrInOutStorageInfoByInOutStorageId
	 * @description: 根据出入库记录ID修改出入库信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:43:39
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean updateGrInOutStorageInfoByInOutStorageId(
			long inoutStorageId, long deviceTypeId,long deviceId, long lenderUserId,
			long authorUserId, long inoutStorageFlag,
			String inoutStorageInDetail, long inoutStorageInNumber,
			Timestamp inoutStorageTime, String inoutStorageOutDetail,
			long inoutStorageOutNumber) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("inoutStorageId", inoutStorageId);
			parMap.put("deviceTypeId", deviceTypeId);
			parMap.put("deviceId", deviceId);
			parMap.put("lenderUserId", lenderUserId);
			parMap.put("authorUserId", authorUserId);
			parMap.put("inoutStorageFlag", inoutStorageFlag);
			parMap.put("inoutStorageInDetail", inoutStorageInDetail);
			parMap.put("inoutStorageInNumber", inoutStorageInNumber);
			parMap.put("inoutStorageTime", inoutStorageTime);
			parMap.put("inoutStorageOutDetail", inoutStorageOutDetail);
			parMap.put("inoutStorageOutNumber", inoutStorageOutNumber);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updateInOutStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: deleteGrInOutStorageInfoByInOutStorageId
	 * @description: 根据出入库记录单号删除出入库记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:21
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrInOutStorageInfoByInOutStorageId(long inoutStorageId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("inoutStorageId", inoutStorageId);
			parList.add(parMap);
			sqlDao.batchDelete("PropertySQL.deleteInOutStorage", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: loadGrInOutStorageInfoByInOutStorageId
	 * @description: 根据出入库记录ID得到出入库记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrInOutStorage loadGrInOutStorageInfoByInOutStorageId(
			long inoutStorageId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrInOutStorage inoutStorage = new GrInOutStorage();
		try {
			Map parMap = new HashMap();
			parMap.put("inoutStorageId", inoutStorageId);
			resultList = sqlDao.findList("PropertySQL.selectInOutStorageByInOutStorageId",
					parMap);
			if (resultList.size() > 0) {
				if (resultList.get(0).get("INOUT_STORAGE_ID") != null) {
					inoutStorage.setInoutStorageId(Long.parseLong(String.valueOf(resultList.get(0).get("INOUT_STORAGE_ID"))));
				}
				
				if (resultList.get(0).get("DEVICE_TYPE_ID") != null) {
					inoutStorage.setDeviceTypeId(Long.parseLong(String.valueOf(resultList.get(0).get("DEVICE_TYPE_ID"))));
				}
				
				if (resultList.get(0).get("DEVICE_ID") != null) {
					inoutStorage.setDeviceId(Long.parseLong(String.valueOf(resultList.get(0).get("DEVICE_ID"))));
				}
				
				if (resultList.get(0).get("LENDER_USER_ID") != null) {
					inoutStorage.setLenderUserId(Long.parseLong(String.valueOf(resultList.get(0).get("LENDER_USER_ID"))));
				}
				
				if (resultList.get(0).get("LENDER_USER_NAME") != null) {
					inoutStorage.setLenderUserName(String.valueOf(resultList.get(0).get("LENDER_USER_NAME")));
				}
				
				if (resultList.get(0).get("LENDER_USER_ORG") != null) {
					inoutStorage.setLenderUserOrg(Long.parseLong(String.valueOf(resultList.get(0).get("LENDER_USER_ORG"))));
				}
				
				if (resultList.get(0).get("LENDER_USER_ORG_NAME") != null) {
					inoutStorage.setLenderUserOrgName(String.valueOf(resultList.get(0).get("LENDER_USER_ORG_NAME")));
				}
				
				if (resultList.get(0).get("AUTHOR_USER_ID") != null) {
					inoutStorage.setAuthorUserId(Long.parseLong(String.valueOf(resultList.get(0).get("AUTHOR_USER_ID"))));
				}
				
				if (resultList.get(0).get("INOUT_STORAGE_FLAG") != null) {
					inoutStorage.setInoutStorageFlag(Long.parseLong(String.valueOf(resultList.get(0).get("INOUT_STORAGE_FLAG"))));
				}
				
				if (resultList.get(0).get("INOUT_STORAGE_IN_DETAIL") != null) {
					inoutStorage.setInoutStorageInDetail(String.valueOf(resultList.get(0).get("INOUT_STORAGE_IN_DETAIL")));
				}
				
				if (resultList.get(0).get("INOUT_STORAGE_IN_NUMBER") != null) {
					inoutStorage.setInoutStorageInNumber(Long.parseLong(String.valueOf(resultList.get(0).get("INOUT_STORAGE_IN_NUMBER"))));
				}
				
				if (resultList.get(0).get("INOUT_STORAGE_TIME") != null) {
					inoutStorage.setInoutStorageTime((Date) resultList.get(0).get("INOUT_STORAGE_TIME"));
				}
				
				if (resultList.get(0).get("INOUT_STORAGE_OUT_DETAIL") != null) {
					inoutStorage.setInoutStorageOutDetail(String.valueOf(resultList.get(0).get("INOUT_STORAGE_OUT_DETAIL")));
				}
				
				if (resultList.get(0).get("INOUT_STORAGE_OUT_NUMBER") != null) {
					inoutStorage.setInoutStorageOutNumber(Long.parseLong(String.valueOf(resultList.get(0).get("INOUT_STORAGE_OUT_NUMBER"))));
				}
			}
		} catch (Exception e) {
		}

		return inoutStorage;
	}

	/**
	 * 
	 * @title: getGrInOutStorageList
	 * @description: 分页得到出入库记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:06:35
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrInOutStorageList(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectAllInOutStorage",
				parMap);
	}
	
	public List findGrInOutStorageList(HashMap<String, String> parMap){
		return sqlDao.findList("PropertySQL.selectAllInOutStorage",parMap);
	}
}
