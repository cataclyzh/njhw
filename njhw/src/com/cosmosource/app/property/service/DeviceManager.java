package com.cosmosource.app.property.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrDevice;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @description: 设备的增删改查接口
 * @author tangtq
 * @date 2013-7-3 13:47:18
 */
public class DeviceManager extends BaseManager {
	private StorageManager storageManager;

	/**
	 * 
	 * @title: initGrDeviceList
	 * @description: 初始化设备列表
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrDevice> initGrDeviceList() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrDevice> deviceList = new ArrayList<GrDevice>();
		try {
			resultList = sqlDao.findList("PropertySQL.selectDevice", null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrDevice device = new GrDevice();
					if (resultList.get(i).get("DEVICE_ID") != null) {
						device.setDeviceId(Long.parseLong(String
								.valueOf(resultList.get(i).get("DEVICE_ID"))));
					}
					
					if (resultList.get(i).get("DEVICE_NO") != null) {
						device.setDeviceNo(String.valueOf(resultList.get(i).get("DEVICE_NO")));
					}

					if (resultList.get(i).get("DEVICE_TYPE_ID") != null) {
						device.setDeviceTypeId(Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("DEVICE_TYPE_ID"))));
					}

					if (resultList.get(i).get("DEVICE_NAME") != null) {
						device.setDeviceName(String.valueOf(resultList.get(i)
								.get("DEVICE_NAME")));
					}

					if (resultList.get(i).get("DEVICE_DESCRIBE") != null) {
						device.setDeviceDescribe(String.valueOf(resultList.get(
								i).get("DEVICE_DESCRIBE")));
					}

					if (resultList.get(i).get("DEVICE_PRODUCE_TIME") != null) {
						device.setDeviceProduceTime((Date) resultList.get(i)
								.get("DEVICE_PRODUCE_TIME"));
					}

					if (resultList.get(i).get("DEVICE_BUY_TIME") != null) {
						device.setDeviceBuyTime((Date) resultList.get(i).get(
								"DEVICE_BUY_TIME"));
					}

					if (resultList.get(i).get("DEVICE_WARRANTY_TIME") != null) {
						device.setDeviceWarrantyTime(Long.parseLong(String
								.valueOf(resultList.get(i).get(
										"DEVICE_WARRANTY_TIME"))));
					}

					if (resultList.get(i).get("DEVICE_COMPANY") != null) {
						device.setDeviceCompany(String.valueOf(resultList
								.get(i).get("DEVICE_COMPANY")));
					}

					if (resultList.get(i).get("DEVICE_SEQUENCE") != null) {
						device.setDeviceSequence(String.valueOf(resultList.get(
								i).get("DEVICE_SEQUENCE")));
					}
					deviceList.add(device);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceList;
	}

	/**
	 * 
	 * @title: loadGrDeviceInfo
	 * @description: 得到设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:22:13
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrDevice> loadGrDeviceInfo() {
		return dao.findByHQL("select t from GrDevice t");
	}

	/**
	 * 
	 * @title: addGrDeviceInfo
	 * @description: 新增设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-11 10:52:46
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrDeviceInfo(long deviceTypeId,String deviceNo, String deviceName,
			String deviceDescribe, Timestamp deviceProduceTime,
			Timestamp deviceBuyTime, long deviceWarrantyTime,
			String deviceCompany, String deviceSequence) {
		List parList;
		boolean result = true;

		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			Long deviceId = Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_DEVICE_SEQ_VALUE")));
			if (deviceId != null){
				parMap.put("deviceId", deviceId);
			}
			
			if (deviceNo != null) {
				parMap.put("deviceNo", deviceNo);
			}

			parMap.put("deviceTypeId", deviceTypeId);

			if (deviceName != null) {
				parMap.put("deviceName", deviceName);
			}

			if (deviceDescribe != null) {
				parMap.put("deviceDescribe", deviceDescribe);
			}

			if (deviceProduceTime != null) {
				parMap.put("deviceProduceTime", deviceProduceTime);
			}

			if (deviceBuyTime != null) {
				parMap.put("deviceBuyTime", deviceBuyTime);
			}
            if(deviceWarrantyTime==0L){
    			parMap.put("deviceWarrantyTime", null);
            }else{
    			parMap.put("deviceWarrantyTime", deviceWarrantyTime);
            }

			if (deviceCompany != null) {
				parMap.put("deviceCompany", deviceCompany);
			}

			if (deviceSequence != null) {
				parMap.put("deviceSequence", deviceSequence);
			}
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertDevice", parList);
			storageManager.addGrStorageInfo(deviceId, deviceTypeId,
					Constant.GR_STORAGE_INVENTORY_INIT);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: updateGrDeviceInfoByDeviceId
	 * @description: 根据设备ID修改设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:43:39
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean updateGrDeviceInfoByDeviceId(long deviceId,String deviceNo,
			long deviceTypeId, String deviceName, String deviceDescribe,
			Timestamp deviceProduceTime, Timestamp deviceBuyTime,
			long deviceWarrantyTime, String deviceCompany, String deviceSequence) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("deviceId", deviceId);
			
			if (deviceNo != null) {
				parMap.put("deviceNo", deviceNo);
			}

			parMap.put("deviceTypeId", deviceTypeId);

			if (deviceName != null) {
				parMap.put("deviceName", deviceName);
			}

			if (deviceDescribe != null) {
				parMap.put("deviceDescribe", deviceDescribe);
			}

			if (deviceProduceTime != null) {
				parMap.put("deviceProduceTime", deviceProduceTime);
			}

			if (deviceBuyTime != null) {
				parMap.put("deviceBuyTime", deviceBuyTime);
			}

			 if(deviceWarrantyTime==0L){
	    			parMap.put("deviceWarrantyTime", null);
	            }else{
	    			parMap.put("deviceWarrantyTime", deviceWarrantyTime);
	            }
		
			if (deviceCompany != null) {
				parMap.put("deviceCompany", deviceCompany);
			}

			if (deviceSequence != null) {
				parMap.put("deviceSequence", deviceSequence);
			}
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updateDevice", parList);
			storageManager.modifyGrStorageInfo(deviceId, deviceTypeId);
			storageManager.modifyInOutGrStorageInfo(deviceId, deviceTypeId);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: deleteGrDeviceInfoByDeviceId
	 * @description: 根据设备ID删除设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:21
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrDeviceInfoByDeviceId(long deviceId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("deviceId", deviceId);
			parList.add(parMap);
			sqlDao.batchDelete("PropertySQL.deleteDevice", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	
	public void deleteDeviceById(List list){
		try {
			sqlDao.batchDelete("PropertySQL.grrepairDelete", list);
			sqlDao.batchDelete("PropertySQL.grinoutstorageDelete", list);
			sqlDao.batchDelete("PropertySQL.storageDelete", list);
			dao.flush();
			sqlDao.batchDelete("PropertySQL.inventoryDelete", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @title: loadGrDeviceInfoByDeviceTypeId
	 * @description: 根据设备ID得到设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrDevice loadGrDeviceInfoByDeviceId(long deviceId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrDevice device = new GrDevice();
		try {
			Map parMap = new HashMap();
			parMap.put("deviceId", deviceId);
			resultList = sqlDao.findList("PropertySQL.selectDeviceByDeviceId",
					parMap);
			if (resultList.size() > 0) {
				if (resultList.get(0).get("DEVICE_ID") != null) {
					device.setDeviceId(Long.parseLong(String.valueOf(resultList
							.get(0).get("DEVICE_ID"))));
				}
				
				if (resultList.get(0).get("DEVICE_NO") != null) {
					device.setDeviceNo(String.valueOf(resultList
							.get(0).get("DEVICE_NO")));
				}

				if (resultList.get(0).get("DEVICE_TYPE_ID") != null) {
					device.setDeviceTypeId(Long.parseLong(String
							.valueOf(resultList.get(0).get("DEVICE_TYPE_ID"))));
				}

				if (resultList.get(0).get("DEVICE_NAME") != null) {
					device.setDeviceName(String.valueOf(resultList.get(0).get(
							"DEVICE_NAME")));
				}

				if (resultList.get(0).get("DEVICE_DESCRIBE") != null) {
					device.setDeviceDescribe(String.valueOf(resultList.get(0)
							.get("DEVICE_DESCRIBE")));

				}

				if (resultList.get(0).get("DEVICE_PRODUCE_TIME") != null) {
					device.setDeviceProduceTime((Date) resultList.get(0).get(
							"DEVICE_PRODUCE_TIME"));
				}

				if (resultList.get(0).get("DEVICE_BUY_TIME") != null) {
					device.setDeviceBuyTime((Date) resultList.get(0).get(
							"DEVICE_BUY_TIME"));
				}

				if (resultList.get(0).get("DEVICE_WARRANTY_TIME") != null) {
					device.setDeviceWarrantyTime(Long.parseLong(String
							.valueOf(resultList.get(0).get(
									"DEVICE_WARRANTY_TIME"))));
				}

				if (resultList.get(0).get("DEVICE_COMPANY") != null) {
					device.setDeviceCompany(String.valueOf(resultList.get(0)
							.get("DEVICE_COMPANY")));
				}

				if (resultList.get(0).get("DEVICE_SEQUENCE") != null) {
					device.setDeviceSequence(String.valueOf(resultList.get(0)
							.get("DEVICE_SEQUENCE")));
				}
			}
		} catch (Exception e) {
		}

		return device;
	}

	/**
	 * 
	 * @title: loadGrDeviceCountInfoByDeviceTypeId
	 * @description: 根据设备类型ID得到设备个数
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public int loadGrDeviceCountInfoByDeviceTypeId(long deviceTypeId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		try {
			Map parMap = new HashMap();
			parMap.put("deviceTypeId", deviceTypeId);
			resultList = sqlDao.findList(
					"PropertySQL.selectDeviceByDeviceTypeId", parMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList.size();
	}

	/**
	 * 
	 * @title: loadGrDeviceInfoByDeviceTypeId
	 * @description: 根据设备ID得到设备信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrDevice> loadGrDeviceInfoByDeviceTypeId(long deviceTypeId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrDevice> deviceList = new ArrayList<GrDevice>();
		try {
			Map parMap = new HashMap();
			parMap.put("deviceTypeId", deviceTypeId);
			resultList = sqlDao.findList(
					"PropertySQL.selectDeviceByDeviceTypeId", parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrDevice device = new GrDevice();
					if (resultList.get(i).get("DEVICE_ID") != null) {
						device.setDeviceId(Long.parseLong(String
								.valueOf(resultList.get(i).get("DEVICE_ID"))));
					}
					
					if (resultList.get(i).get("DEVICE_NO") != null) {
						device.setDeviceNo(String
								.valueOf(resultList.get(i).get("DEVICE_NO")));
					}

					if (resultList.get(i).get("DEVICE_TYPE_ID") != null) {
						device.setDeviceTypeId(Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("DEVICE_TYPE_ID"))));
					}

					if (resultList.get(i).get("DEVICE_NAME") != null) {
						device.setDeviceName(String.valueOf(resultList.get(i)
								.get("DEVICE_NAME")));
					}

					if (resultList.get(i).get("DEVICE_DESCRIBE") != null) {
						device.setDeviceDescribe(String.valueOf(resultList.get(
								i).get("DEVICE_DESCRIBE")));

					}

					if (resultList.get(i).get("DEVICE_PRODUCE_TIME") != null) {
						device.setDeviceProduceTime((Date) resultList.get(i)
								.get("DEVICE_PRODUCE_TIME"));
					}

					if (resultList.get(i).get("DEVICE_BUY_TIME") != null) {
						device.setDeviceBuyTime((Date) resultList.get(i).get(
								"DEVICE_BUY_TIME"));
					}

					if (resultList.get(i).get("DEVICE_WARRANTY_TIME") != null) {
						device.setDeviceWarrantyTime(Long.parseLong(String
								.valueOf(resultList.get(i).get(
										"DEVICE_WARRANTY_TIME"))));
					}

					if (resultList.get(i).get("DEVICE_COMPANY") != null) {
						device.setDeviceCompany(String.valueOf(resultList
								.get(i).get("DEVICE_COMPANY")));
					}

					if (resultList.get(i).get("DEVICE_SEQUENCE") != null) {
						device.setDeviceSequence(String.valueOf(resultList.get(
								i).get("DEVICE_SEQUENCE")));
					}
					deviceList.add(device);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return deviceList;
	}

	/**
	 * 
	 * @title: getGrDeviceList
	 * @description: 分页得到设备类型信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:06:35
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrDeviceList(final Page<HashMap> page,
			Map map) {
		return sqlDao.findPage(page, "PropertySQL.selectAllDevice", map);
	}

	public StorageManager getStorageManager() {
		return storageManager;
	}

	public void setStorageManager(StorageManager storageManager) {
		this.storageManager = storageManager;
	}
	@SuppressWarnings("unchecked")
	public Page<HashMap> checkDevice(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.checkDevice", parMap);
	}
	
	@SuppressWarnings("unchecked")
	public Page<HashMap> checkDeviceModify(final Page<HashMap> page, HashMap parMap){
		return sqlDao.findPage(page, "PropertySQL.checkDeviceModify", parMap);
	}
}
