package com.cosmosource.app.property.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrRepairCost;
import com.cosmosource.app.entity.Org;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @description: 维修耗材记录的增删改查接口
 * @author tangtq
 * @date 2013-7-3 17:16:13
 */
public class RepairCostManager extends BaseManager {

	/**
	 * 
	 * @title: loadGrRepairCostInfo
	 * @description: 得到维修耗材记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:48:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrRepairCost> loadGrRepairCostInfo() {
		return dao.findByHQL("select t from GrRepairCost t");
	}

	/**
	 * 
	 * @title: addGrRepairCostInfo
	 * @description: 插入维修耗材记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrRepairCostInfo(Long repairId, Long deviceCostId,
			Long orgId, String orgName, Long repairCostReportUserId, String reportUserName,
			Long repairCostRepairUserId, String repairCostTitle,
			Long deviceNumber, Timestamp repairCostTime) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairCostId",  Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_REPAIR_COST_SEQ_VALUE"))));
			parMap.put("repairId", repairId);
			parMap.put("deviceCostId", deviceCostId);
			parMap.put("orgId", orgId);
			parMap.put("orgName", orgName);
			parMap.put("repairCostReportUserId", repairCostReportUserId);
			parMap.put("reportUserName", reportUserName);
			parMap.put("repairCostRepairUserId", repairCostRepairUserId);
			parMap.put("repairCostTitle", repairCostTitle);
			parMap.put("deviceNumber", deviceNumber);
			parMap.put("repairCostTime", repairCostTime);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertRepairCost", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: updateGrRepairCostInfoByRepairCostId
	 * @description: 根据维修耗材单号修改维修耗材记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:52:15
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean updateGrRepairCostInfoByRepairCostId(long repairCostId,
			long repairId, long deviceCostId, long orgId,
			long repairCostReportUserId, long repairCostRepairUserId,
			String repairCostTitle, long deviceNumber, Timestamp repairCostTime) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairCostId", repairCostId);
			parMap.put("repairId", repairId);
			parMap.put("deviceCostId", deviceCostId);
			parMap.put("orgId", orgId);
			parMap.put("repairCostReportUserId", repairCostReportUserId);
			parMap.put("repairCostRepairUserId", repairCostRepairUserId);
			parMap.put("repairCostTitle", repairCostTitle);
			parMap.put("deviceNumber", deviceNumber);
			parMap.put("repairCostTime", repairCostTime);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updateRepairCost", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: deleteGrRepairCostInfoByRepairId
	 * @description: 根据维修耗材ID删除耗材记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:55:37
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrRepairCostInfoByRepairId(long repairCostId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairCostId", repairCostId);
			parList.add(parMap);
			sqlDao.batchDelete("PropertySQL.deleteRepairCost", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: loadGrStorageCostInfoByDeviceId
	 * @description: 根据耗材设备ID得到维修耗材记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrRepairCost loadGrRepairInfoByDeviceId(long repairCostId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrRepairCost repairCost = new GrRepairCost();
		try {
			Map parMap = new HashMap();
			parMap.put("repairCostId", repairCostId);
			resultList = sqlDao.findList("PropertySQL.selectRepairCostById",
					parMap);
			if (resultList.size() > 0) {
				if (resultList.get(0).get("REPAIR_COST_ID") != null) {
					Long repairCostIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("REPAIR_COST_ID")));
					repairCost.setRepairCostId(repairCostIdTemp);
				}

				if (resultList.get(0).get("REPAIR_ID") != null) {
					Long repairIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("REPAIR_ID")));
					repairCost.setRepairId(repairIdTemp);
				}

				if (resultList.get(0).get("DEVICE_COST_ID") != null) {
					Long deviceCostIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0)
									.get("DEVICE_COST_ID")));
					repairCost.setDeviceCostId(deviceCostIdTemp);
				}

				if (resultList.get(0).get("ORG_ID") != null) {
					Long orgIdTemp = Long.parseLong(String.valueOf(resultList.get(0)
							.get("ORG_ID")));
					repairCost.setOrgId(orgIdTemp);
				}

				if (resultList.get(0).get("ORG_NAME") != null) {
					String orgNameTemp = String.valueOf(resultList.get(0).get(
							"ORG_NAME"));
					repairCost.setOrgName(orgNameTemp);
				}

				if (resultList.get(0).get("REPAIR_COST_REPORT_USER_ID") != null) {
					Long repairCostReportUserIdTemp = Long.parseLong(String.valueOf(resultList
							.get(0).get("REPAIR_COST_REPORT_USER_ID")));
					repairCost.setRepairCostReportUserId(repairCostReportUserIdTemp);
				}

				if (resultList.get(0).get("REPORT_USER_NAME") != null) {
					String reportUserNameTemp = String.valueOf(resultList.get(0)
							.get("REPORT_USER_NAME"));
					repairCost.setReportUserName(reportUserNameTemp);
				}

				if (resultList.get(0).get("REPAIR_COST_REPAIR_USER_ID") != null) {
					Long repairCostRepairUserIdTemp = Long.parseLong(String.valueOf(resultList.get(0)
							.get("REPAIR_COST_REPAIR_USER_ID")));
					repairCost.setRepairCostReportUserId(repairCostRepairUserIdTemp);
				}

				if (resultList.get(0).get("REPAIR_COST_TITLE") != null) {
					String repairCostTitleTemp = String.valueOf(resultList.get(0)
							.get("REPAIR_COST_TITLE"));
					repairCost.setRepairCostTitle(repairCostTitleTemp);
				}

				if (resultList.get(0).get("DEVICE_NUMBER") != null) {
					Long deviceNumberTemp = Long.parseLong(String.valueOf(resultList.get(0).get(
							"DEVICE_NUMBER")));
					repairCost.setDeviceNumber(deviceNumberTemp);
				}

				if (resultList.get(0).get("REPAIR_COST_TIME") != null) {
					Date repairCostTimeTemp = (Date) resultList.get(0)
							.get("REPAIR_COST_TIME");
					repairCost.setRepairCostTime(repairCostTimeTemp);
				}
				}
			}catch (Exception e) {
		}

		return repairCost;
	}

	/**
	 * 
	 * @title: getGrRepairList
	 * @description: 分页得到维修耗材记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrRepairCostList(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectAllRepairCost", parMap);
	}
	
	/**
	 * 
	 * @title: getOrgInfo
	 * @description: 初始化单位信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Org> getOrgInfo() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<Org> orgList = new ArrayList<Org>();
		try {
			resultList = sqlDao.findList("PropertySQL.selectAllOrgInfo",null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Org org = new Org();
					if (resultList.get(i).get("ORG_ID") != null) {
						org.setOrgId(Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID"))));
					}

					if (resultList.get(i).get("NAME") != null) {
						org.setName(String.valueOf(resultList.get(i).get(
								"NAME")));
					}
					orgList.add(org);
				}
			}
		} catch (Exception e) {
		}
		return orgList;
	}
}
