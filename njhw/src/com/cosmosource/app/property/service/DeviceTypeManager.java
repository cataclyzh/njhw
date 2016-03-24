package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrDeviceType;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @description: 设备类型的增删改查接口
 * @author tangtq
 * @date 2013-7-3 13:35:40
 */
public class DeviceTypeManager extends BaseManager {

	/**
	 * 
	 * @title: loadGrDeviceTypeInfo
	 * @description: 得到设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-3 13:32:01
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrDeviceType> loadGrDeviceTypeInfo() {
		return dao.findByHQL("select t from GrDeviceType t");
	}

	/**
	 * 
	 * @title: addGrDeviceTypeInfo
	 * @description: 新增设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-11 10:52:46
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrDeviceTypeInfo(String deviceTypeNo,String deviceTypeName,
			String deviceTypeDescribe) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("deviceTypeId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_DEVICE_TYPE_SEQ_VALUE"))));
			parMap.put("deviceTypeNo", deviceTypeNo);
			parMap.put("deviceTypeName", deviceTypeName);
			parMap.put("deviceTypeDescribe", deviceTypeDescribe);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertDeviceType", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: updateGrDeviceTypeInfoByDeviceTypeId
	 * @description: 根据设备类型ID修改设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-11 10:52:46
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean updateGrDeviceTypeInfoByDeviceTypeId(Long deviceTypeId,String deviceTypeNo,
			String deviceTypeName, String deviceTypeDescribe) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("deviceTypeId", deviceTypeId);
			parMap.put("deviceTypeNo", deviceTypeNo);
			parMap.put("deviceTypeName", deviceTypeName);
			parMap.put("deviceTypeDescribe", deviceTypeDescribe);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updateDeviceType", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: deleteGrDeviceTypeInfoByDeviceTypeId
	 * @description: 根据设备类型ID删除设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-11 10:52:46
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrDeviceTypeInfoByDeviceTypeId(long deviceTypeId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("deviceTypeId", deviceTypeId);
			parList.add(parMap);
			sqlDao.batchDelete("PropertySQL.deleteDeviceType", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: loadGrDeviceTypeInfo
	 * @description: 根据设备类型ID得到设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:50:15
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrDeviceType loadGrDeviceTypeInfoByDeviceTypeId(
			long deviceTypeId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrDeviceType deviceType = new GrDeviceType();
		try {
			Map parMap = new HashMap();
			parMap.put("deviceTypeId", deviceTypeId);
			resultList = sqlDao.findList("PropertySQL.selectDeviceTypeByDeviceTypeId",
					parMap);
			if (resultList.size() > 0) {
				if (resultList.get(0).get("DEVICE_TYPE_ID") != null) {
					deviceType.setDeviceTypeId(Long.parseLong(String
							.valueOf(resultList.get(0).get("DEVICE_TYPE_ID"))));
				}
				
				if (resultList.get(0).get("DEVICE_TYPE_NO") != null) {
					deviceType.setDeviceTypeNo(String
							.valueOf(resultList.get(0).get("DEVICE_TYPE_NO")));
				}

				if (resultList.get(0).get("DEVICE_TYPE_NAME") != null) {
					deviceType.setDeviceTypeName(String.valueOf(resultList.get(
							0).get("DEVICE_TYPE_NAME")));
				}

				if (resultList.get(0).get("DEVICE_TYPE_DESCRIBE") != null) {
					deviceType.setDeviceTypeDescribe(String.valueOf(resultList
							.get(0).get("DEVICE_TYPE_DESCRIBE")));
				}
			}
		} catch (Exception e) {
		}

		return deviceType;
	}

	/**
	 * 
	 * @title: loadGrDeviceTypeInfo
	 * @description: 分页得到设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:23:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrDeviceTypeList(final Page<HashMap> page,
			Map map) {
		return sqlDao.findPage(page, "PropertySQL.selectAllDeviceType", map);
	}

	/**
	 * 
	 * @title: initGrDeviceTypeList
	 * @description: 初始化设备类型列表
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrDeviceType> initGrDeviceTypeList() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrDeviceType> deviceTypeList = new ArrayList<GrDeviceType>();
		try {
			resultList = sqlDao.findList("PropertySQL.selectDeviceType",
					null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrDeviceType deviceType = new GrDeviceType();
					if (resultList.get(i).get("DEVICE_TYPE_ID") != null) {
						deviceType.setDeviceTypeId(Long.parseLong(String
								.valueOf(resultList.get(i).get("DEVICE_TYPE_ID"))));
					}
					
					if (resultList.get(i).get("DEVICE_TYPE_NO") != null) {
						deviceType.setDeviceTypeNo(String
								.valueOf(resultList.get(i).get("DEVICE_TYPE_NO")));
					}

					if (resultList.get(i).get("DEVICE_TYPE_NAME") != null) {
						deviceType.setDeviceTypeName(String.valueOf(resultList.get(
								i).get("DEVICE_TYPE_NAME")));
					}

					if (resultList.get(i).get("DEVICE_TYPE_DESCRIBE") != null) {
						deviceType.setDeviceTypeDescribe(String.valueOf(resultList
								.get(i).get("DEVICE_TYPE_DESCRIBE")));
					}
					deviceTypeList.add(deviceType);
				}
			}
		} catch (Exception e) {
		}
		return deviceTypeList;
	}
	
	public Page<HashMap> checkDeviceType(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.checkDeviceType", parMap);
	}
	
	public Page<HashMap> checkDeviceTypeModify(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.checkDeviceTypeModify", parMap);
	}
}
