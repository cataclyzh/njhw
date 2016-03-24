package com.cosmosource.app.property.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrRepair;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @description: 维修记录的增删改查接口
 * @author tangtq
 * @date 2013-7-3 16:38:13
 */
public class RepairManager extends BaseManager {

	/**
	 * 
	 * @title: loadGrRepairInfo
	 * @description: 得到维修记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:48:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrRepair> loadGrRepairInfo() {
		return dao.findByHQL("select t from GrRepair t");
	}

	/**
	 * 
	 * @title: addGrRepairInfo
	 * @description: 插入维修记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean addGrRepairInfo(long deviceTypeId,long deviceId, long reportUserOrg, String orgName,
			long reportUserId, String reportUserName,String reportUserTel,String repairTheme, String repairDetail,
			Timestamp repairStartTime, String repairState) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairId", Long.parseLong(String.valueOf(sqlDao
					.getSqlMapClientTemplate().queryForObject(
							"PropertySQL.GET_GR_REPAIR_SEQ_VALUE"))));
			parMap.put("deviceTypeId", deviceTypeId);
			parMap.put("deviceId", deviceId);
			parMap.put("reportUserOrg", reportUserOrg);
			parMap.put("orgName", orgName);
			parMap.put("reportUserId", reportUserId);
			parMap.put("reportUserName", reportUserName);
			parMap.put("reportUserTel", reportUserTel);
			parMap.put("repairTheme", repairTheme);
			parMap.put("repairDetail", repairDetail);
			parMap.put("repairStartTime", repairStartTime);
			parMap.put("repairState", Constant.GR_REPAIR_STATE_CONFIRMED);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.insertRepair", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: distributionRepair
	 * @description: 派发维修单
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:52:15
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean distributionRepair(Long repairId, Long repairUserId,String repairUserName,Long distributeUserId,Timestamp distributeTime,String repairState) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairId", repairId);
			parMap.put("repairUserId", repairUserId);
			parMap.put("repairUserName", repairUserName);
			parMap.put("distributeUserId", distributeUserId);
			parMap.put("distributeTime", distributeTime);
			parMap.put("repairState", repairState);			
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.distributionRepair", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: completeRepair
	 * @description: 完成
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:52:15
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean completeRepair(Long repairId, String completeRepairReceipt, Timestamp repairEndTime,String repairState) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairId", repairId);
			parMap.put("completeRepairReceipt", completeRepairReceipt);
			parMap.put("repairEndTime", repairEndTime);
			parMap.put("repairState", repairState);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.completeRepair", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: evaluateRepair
	 * @description: 回访
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:52:15
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean evaluateRepair(Long repairId,String repairSatisfy,String repairEvaluate,String repairState) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairId", repairId);
			parMap.put("repairSatisfy", repairSatisfy);
			parMap.put("repairEvaluate", repairEvaluate);
			parMap.put("repairState", repairState);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.evaluateRepair", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @title: suspendRepair
	 * @description: 中止
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:52:15
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean suspendRepair(Long repairId, String repairState) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairId", repairId);
			parMap.put("repairState", repairState);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.suspendRepair", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: deleteGrRepairInfoByRepairId
	 * @description: 根据维修ID删除维修信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:55:37
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrRepairInfoByRepairId(long repairId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("repairId", repairId);
			parList.add(parMap);
			sqlDao.batchDelete("PropertySQL.deleteRepair", parList);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: loadGrRepairInfoByRepairId
	 * @description: 根据维修记录ID得到维修记录
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public GrRepair loadGrRepairInfoByRepairId(Long repairId) {
		List<HashMap> resultList = new ArrayList<HashMap>();
		GrRepair repair = new GrRepair();
		try {
			Map parMap = new HashMap();
			parMap.put("repairId", repairId);
			resultList = sqlDao.findList(
					"PropertySQL.selectRepairInfoByRepairId", parMap);
			if (resultList.size() > 0) {
				if (resultList.get(0).get("REPAIR_ID") != null) {
					Long repairIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("REPAIR_ID")));
					repair.setRepairId(repairIdTemp);
				}
				
				if (resultList.get(0).get("DEVICE_TYPE_ID") != null) {
					Long deviceTypeIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("DEVICE_TYPE_ID")));
					repair.setDeviceTypeId(deviceTypeIdTemp);
				}

				if (resultList.get(0).get("DEVICE_ID") != null) {
					Long deviceIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("DEVICE_ID")));
					repair.setDeviceId(deviceIdTemp);
				}

				if (resultList.get(0).get("DISTRIBUTE_USER_ID") != null) {
					Long distributeUserIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0)
									.get("DISTRIBUTE_USER_ID")));
					repair.setDistributeUserId(distributeUserIdTemp);
				}

				if (resultList.get(0).get("REPAIR_DETAIL") != null) {
					String repairDetailTemp = String.valueOf(resultList.get(0)
							.get("REPAIR_DETAIL"));
					repair.setRepairDetail(repairDetailTemp);
				}

				if (resultList.get(0).get("REPAIR_END_TIME") != null) {
					Date repairEndTimeTemp = (Date) resultList.get(0).get(
							"REPAIR_END_TIME");
					repair.setRepairEndTime(repairEndTimeTemp);
				}

				if (resultList.get(0).get("REPAIR_EVALUATE") != null) {
					String repairEvaluateTemp = String.valueOf(resultList
							.get(0).get("REPAIR_EVALUATE"));
					repair.setRepairEvaluate(repairEvaluateTemp);
				}

				if (resultList.get(0).get("REPAIR_THEME") != null) {
					String repairThemeTemp = String.valueOf(resultList.get(0)
							.get("REPAIR_THEME"));
					repair.setRepairTheme(repairThemeTemp);
				}

				if (resultList.get(0).get("REPAIR_RECEIPT") != null) {
					String repairReceiptTemp = String.valueOf(resultList.get(0)
							.get("REPAIR_RECEIPT"));
					repair.setRepairReceipt(repairReceiptTemp);
				}

				if (resultList.get(0).get("REPAIR_SATISFY") != null) {
					String repairSatisfyTemp = String.valueOf(resultList.get(0)
							.get("REPAIR_SATISFY"));
					repair.setRepairSatisfy(repairSatisfyTemp);
				}

				if (resultList.get(0).get("REPAIR_START_TIME") != null) {
					Date repairStartTimeTemp = (Date) resultList.get(0).get(
							"REPAIR_START_TIME");
					repair.setRepairStartTime(repairStartTimeTemp);
				}

				if (resultList.get(0).get("REPAIR_STATE") != null) {
					String repairStateTemp = String.valueOf(resultList.get(0)
							.get("REPAIR_STATE"));
					repair.setRepairState(repairStateTemp);
				}

				if (resultList.get(0).get("REPORT_USER_ORG") != null) {
					Long reportUserOrgTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("REPORT_USER_ORG")));
					repair.setReportUserOrg(reportUserOrgTemp);
				}
				
				if (resultList.get(0).get("ORG_NAME") != null) {
					String orgName = String
							.valueOf(resultList.get(0).get("ORG_NAME"));
					repair.setOrgName(orgName);
				}
				
				if (resultList.get(0).get("REPORT_USER_NAME") != null) {
					String reportUserName = String
							.valueOf(resultList.get(0).get("REPORT_USER_NAME"));
					repair.setReportUserName(reportUserName);
				}
				
				if (resultList.get(0).get("REPORT_USER_TEL") != null) {
					String reportUserTel = String
							.valueOf(resultList.get(0).get("REPORT_USER_TEL"));
					repair.setReportUserTel(reportUserTel);
				}
				
				if (resultList.get(0).get("REPAIR_USER_NAME") != null) {
					String repairUserName = String
							.valueOf(resultList.get(0).get("REPAIR_USER_NAME"));
					repair.setRepairUserName(repairUserName);
				}

				if (resultList.get(0).get("REPORT_USER_ID") != null) {
					Long reportUserIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("REPORT_USER_ID")));
					repair.setReportUserId(reportUserIdTemp);
				}

				if (resultList.get(0).get("DISTRIBUTE_TIME") != null) {
					Date distributeTimeTemp = (Date) resultList.get(0).get(
							"DISTRIBUTE_TIME");
					repair.setDistributeTime(distributeTimeTemp);
				}

				if (resultList.get(0).get("REPAIR_USER_ID") != null) {
					Long repairUserIdTemp = Long.parseLong(String
							.valueOf(resultList.get(0).get("REPAIR_USER_ID")));
					repair.setRepairUserId(repairUserIdTemp);
				}
			}
		} catch (Exception e) {
		}

		return repair;
	}

	/**
	 * 
	 * @title: getGrRepairList
	 * @description: 分页得到维修记录信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrRepairList(final Page<HashMap> page,
			HashMap<String, String> parMap,String type) {
		if ("export".equals(type)) {
			page.setResult(sqlDao.findList("PropertySQL.selectAllRepair", parMap));
			return page;
		}else {
			return sqlDao.findPage(page, "PropertySQL.selectAllRepair", parMap);
		}
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

	/**
	 * 
	 * @title: initGrRepairList
	 * @description: 初始化获取维修列表
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 15:10:52
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrRepair> initGrRepairList() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrRepair> repairList = new ArrayList<GrRepair>();
		try {
			resultList = sqlDao.findList("PropertySQL.selectAllRepairInfoList",
					null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrRepair repair = new GrRepair();
					if (resultList.get(i).get("REPAIR_ID") != null) {
						Long repairIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("REPAIR_ID")));
						repair.setRepairId(repairIdTemp);
					}
					
					if (resultList.get(i).get("DEVICE_TYPE_ID") != null) {
						Long deviceTypeIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("DEVICE_TYPE_ID")));
						repair.setDeviceTypeId(deviceTypeIdTemp);
					}

					if (resultList.get(i).get("DEVICE_ID") != null) {
						Long deviceIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get("DEVICE_ID")));
						repair.setDeviceId(deviceIdTemp);
					}

					if (resultList.get(i).get("DISTRIBUTE_USER_ID") != null) {
						Long distributeUserIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get(
										"DISTRIBUTE_USER_ID")));
						repair.setDistributeUserId(distributeUserIdTemp);
					}

					if (resultList.get(i).get("REPAIR_DETAIL") != null) {
						String repairDetailTemp = String.valueOf(resultList
								.get(i).get("REPAIR_DETAIL"));
						repair.setRepairDetail(repairDetailTemp);
					}

					if (resultList.get(i).get("REPAIR_END_TIME") != null) {
						Date repairEndTimeTemp = (Date) resultList.get(i).get(
								"REPAIR_END_TIME");
						repair.setRepairEndTime(repairEndTimeTemp);
					}

					if (resultList.get(i).get("REPAIR_EVALUATE") != null) {
						String repairEvaluateTemp = String.valueOf(resultList
								.get(i).get("REPAIR_EVALUATE"));
						repair.setRepairEvaluate(repairEvaluateTemp);
					}

					if (resultList.get(i).get("REPAIR_THEME") != null) {
						String repairThemeTemp = String.valueOf(resultList.get(
								i).get("REPAIR_THEME"));
						if(repairThemeTemp.length()>4){
							repair.setRepairTheme(repairThemeTemp.substring(0,4)+"..");
						}else{
							repair.setRepairTheme(repairThemeTemp);
						}
						
						
					}

					if (resultList.get(i).get("REPAIR_RECEIPT") != null) {
						String repairReceiptTemp = String.valueOf(resultList
								.get(i).get("REPAIR_RECEIPT"));
						repair.setRepairReceipt(repairReceiptTemp);
					}

					if (resultList.get(i).get("REPAIR_SATISFY") != null) {
						String repairSatisfyTemp = String.valueOf(resultList
								.get(i).get("REPAIR_SATISFY"));
						repair.setRepairSatisfy(repairSatisfyTemp);
					}

					if (resultList.get(i).get("REPAIR_START_TIME") != null) {
						
						  
				            Date repairStartTimeTemp = (Date)resultList.get(i)
							.get("REPAIR_START_TIME"); 

						repair.setRepairStartTime(repairStartTimeTemp);
					}

					if (resultList.get(i).get("REPAIR_STATE") != null) {
						String repairStateTemp = String.valueOf(resultList.get(
								i).get("REPAIR_STATE"));
						repair.setRepairState(repairStateTemp);
					}

					if (resultList.get(i).get("REPORT_USER_ORG") != null) {
						Long reportUserOrgTemp = Long.parseLong(String
								.valueOf(resultList.get(i).get(
										"REPORT_USER_ORG")));
						repair.setReportUserOrg(reportUserOrgTemp);
					}

					if (resultList.get(i).get("REPORT_USER_ID") != null) {
						Long reportUserIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("REPORT_USER_ID")));
						repair.setReportUserId(reportUserIdTemp);
					}

					if (resultList.get(i).get("DISTRIBUTE_TIME") != null) {
						Date distributeTimeTemp = (Date) resultList.get(i).get(
								"DISTRIBUTE_TIME");
						repair.setDistributeTime(distributeTimeTemp);
					}

					if (resultList.get(i).get("REPAIR_USER_ID") != null) {
						Long repairUserIdTemp = Long.parseLong(String
								.valueOf(resultList.get(i)
										.get("REPAIR_USER_ID")));
						repair.setRepairUserId(repairUserIdTemp);
					}
					
					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String
								.valueOf(resultList.get(i).get("ORG_NAME"));
						repair.setOrgName(orgName);
					}
					
					if (resultList.get(i).get("REPORT_USER_NAME") != null) {
						String reportUserName = String
								.valueOf(resultList.get(i).get("REPORT_USER_NAME"));
						
						if(reportUserName.length()>4){
							repair.setReportUserName(reportUserName.substring(0,4)+"..");
						}else{
							repair.setReportUserName(reportUserName);
						}
						
						
					}
					
					if (resultList.get(i).get("REPORT_USER_TEL") != null) {
						String reportUserTel = String
								.valueOf(resultList.get(i).get("REPORT_USER_TEL"));
						repair.setReportUserTel(reportUserTel);
					}
					
					if (resultList.get(i).get("REPAIR_USER_NAME") != null) {
						String repairUserName = String
								.valueOf(resultList.get(i).get("REPAIR_USER_NAME"));
						repair.setRepairUserName(repairUserName);
					}
					repairList.add(repair);
				}
			}
		} catch (Exception e) {
		}
		return repairList;
	}
}
