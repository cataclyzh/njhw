package com.cosmosource.app.property.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.GrPatrolPositionCard;
import com.cosmosource.app.entity.GrPatrolStick;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.VisRfidandid;
import com.cosmosource.app.property.model.Constant;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;

/**
 * @description: 员工定位卡的增删改查接口
 * @author tangtq
 * @date 2013-7-8 11:47:16
 */
public class PatrolPositionCardManager extends BaseManager {
	/**
	 * 
	 * @title: loadGrPatrolPositionCardInfo
	 * @description: 得到员工定位卡
	 * @return
	 * @date 2013-7-12 14:48:55
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolPositionCard> loadGrPatrolPositionCardInfo() {
		return dao.findByHQL("select t from GrPatrolPositionCard t");
	}

	public void addGrPatrolPositionCard(String cardNo, Long ...userIds){
		
		List<Map> list = new ArrayList<Map>();
		for(Long uId:userIds){
			Map parMap = new HashMap();
//			parMap.put("visId", visId);
			parMap.put("cardNo", cardNo);
			parMap.put("userId", uId);
			
			parMap.put("status", 0);
			list.add(parMap);
		}
		sqlDao.batchInsert("PropertySQL.insertPatrolPositionCard", list);
		// 设置 vis_rfidandid status 字段为 0 
		// sqlDao.batchUpdate("PropertySQL.updateVisRfidandid", list);
	}
	
	public String getPositionCardMac(String cardNo){
		String result = null;
		List<Map> list = sqlDao.getSqlMapClientTemplate().queryForList("PropertySQL.queryPositionCard", cardNo);
		if(list != null && list.size() > 0){
			result = list.get(0).get("CARD_MAC").toString();
		}
		return result;
	}
	
	public String getPositionCardId(String mac){
		String result = null;
		List<Map> list = sqlDao.getSqlMapClientTemplate().queryForList("PropertySQL.queryPositionCardByMac", mac);
		if(list != null && list.size() > 0){
			result = list.get(0).get("CARD_ID").toString();
		}
		return result;
	}
	
	public boolean isUsedGrPatrolPositionCard(String cardNo){
		List list = sqlDao.getSqlMapClientTemplate().queryForList("PropertySQL.selectUsedGrPatrolPositionCard", cardNo);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 检查GR_PATROL_POSITION_CARD表中的用户
	 * @param userIds
	 * @return
	 */
	public String checkBrPatrolPositionUser(Long ...userIds){
		String userName = null;
		for(Long uId: userIds){
			Map parMap = new HashMap();
			parMap.put("userId", uId);
			List<Map> list = sqlDao.getSqlMapClientTemplate()
					.queryForList("PropertySQL.selectUserFromGrPatrolPositionCard", uId);
			if(list != null && list.size() > 0){
				userName = list.get(0).get("USER_NAME").toString();
				return userName;
			}
		}
		return userName;
	}
	
	public void addGrPatrolStickUser(Long sId,Long ... userIds){
	
		for(Long uId:userIds){
			Map parMap = new HashMap();
			List<Map> list = new ArrayList<Map>();
			parMap.put("sId", sId);
			parMap.put("uId", uId);
			list.add(parMap);
			sqlDao.batchInsert("PropertySQL.insertGrPatrolStickUser", list);
		}
	}

	/**
	 * 
	 * @title: editGrPatrolPositionCardInfo
	 * @description: 编辑员工定位卡信息，提交操作
	 * @description：恢复之前的卡状态、修改新分配卡状态
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean editGrPatrolPositionCardInfo(Long patrolPositionCardId,
			String cardNo) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("patrolPositionCardId", patrolPositionCardId);
			parMap.put("cardNo", cardNo);
			
//			parMap.put("status", 1);
			parList.add(parMap);
			
			//修改 VIS_RFIDANDID status 字段 
//			sqlDao.batchUpdate("PropertySQL.updateVisRfidandid", parList);
			//恢复 VIS_RFIDANDID 表之前绑定相应字段为 1
//			sqlDao.batchUpdate("PropertySQL.recoverVisRfidandid", parList);
			
			sqlDao.batchUpdate("PropertySQL.updatePatrolPositionCard", parList);
			
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean editGPatrolStick(Long suId,
			Long sId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("suId", suId);
			parMap.put("sId", sId);
			parList.add(parMap);
			sqlDao.batchUpdate("PropertySQL.updatePatrolStickUser", parList);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: deleteGrPatrolLineInfo
	 * @description: 删除员工定位卡信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 14:50:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteGrPatrolLineInfo(String[] patrolPositionCardId) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("patrolPositionCardId", patrolPositionCardId);
			parList.add(parMap);
			
			if(patrolPositionCardId!=null){
				//恢复被删除 vis_rfidandid 对应记录 status 字段值
				//sqlDao.batchUpdate("PropertySQL.batchRecoverVisRfidandid", parList);
				
				//删除对应记录
				sqlDao.batchUpdate("PropertySQL.deletePatrolPositionCard",parList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public boolean deleteGrPatrolStick(String[] patrolStickIds) {
		List parList;
		boolean result = true;
		try {
			Map parMap = new HashMap();
			parList = new ArrayList();
			parMap.put("patrolStickIds", patrolStickIds);
			parList.add(parMap);
			if(patrolStickIds!=null){
				sqlDao.batchUpdate("PropertySQL.deletePatrolPositionStick",parList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @title: initGrPatrolPositionCardInfo
	 * @description: 初始化员工定位卡
	 * @author tangtq
	 * @return
	 * @date 2013-7-12 13:47:56
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<GrPatrolPositionCard> initGrPatrolPositionCardInfo() {
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<GrPatrolPositionCard> patrolPositionCardList = new ArrayList<GrPatrolPositionCard>();
		try {
			Map parMap = new HashMap();
			resultList = sqlDao.findList(
					"PropertySQL.selectPatrolPositionCardInfoList", parMap);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					GrPatrolPositionCard patrolPositionCard = new GrPatrolPositionCard();
					if (resultList.get(i).get("PATROL_POSITION_CARD_ID") != null) {
						Long patrolPositionCardId = Long.parseLong(String
								.valueOf(resultList.get(i).get(
										"PATROL_POSITION_CARD_ID")));
						patrolPositionCard
								.setPatrolPositionCardId(patrolPositionCardId);
					}

					if (resultList.get(i).get("ORG_ID") != null) {
						Long orgId = Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID")));
						patrolPositionCard.setOrgId(orgId);
					}

					if (resultList.get(i).get("ORG_NAME") != null) {
						String orgName = String.valueOf(resultList.get(i).get(
								"ORG_NAME"));
						patrolPositionCard.setOrgName(orgName);
					}

					if (resultList.get(i).get("USER_ID") != null) {
						Long userId = Long.parseLong(String.valueOf(resultList
								.get(i).get("USER_ID")));
						patrolPositionCard.setUserId(userId);
					}

					if (resultList.get(i).get("USER_NAME") != null) {
						String userName = String.valueOf(resultList.get(i).get(
								"USER_NAME"));
						patrolPositionCard.setUserName(userName);
					}

					if (resultList.get(i).get("PATROL_POSITION_CARD_NO") != null) {
						String patrolPositionCardNo = String.valueOf(resultList
								.get(i).get("PATROL_POSITION_CARD_NO"));
						patrolPositionCard
								.setPatrolPositionCardNo(patrolPositionCardNo);
					}

					if (resultList.get(i).get("IS_AVALIABLE") != null) {
						String isAvaliable = String.valueOf(resultList.get(i)
								.get("IS_AVALIABLE"));
						patrolPositionCard.setIsAvaliable(isAvaliable);
					}

					patrolPositionCardList.add(patrolPositionCard);
				}
			}
		} catch (Exception e) {
		}
		return patrolPositionCardList;
	}

	/**
	 * 查询定位卡信息分页
	 * zhujiabiao
	 * 2013-9-24
	 * @param page
	 * @param parMap
	 * @return
	 */
	public Page<HashMap> getGrPatrolPositionCardPage(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectPatrolPositionCardList",
				parMap);
	}
	
	public Page<HashMap> getPatrolStickRecord(final Page<HashMap> page, Map param){
		return sqlDao.findPage(page, "PropertySQL.getPatrolStickRecord", param);
	}
	
	public List<HashMap> getPatrolStickRecord(Map param){
		return sqlDao.getSqlMapClientTemplate().queryForList("PropertySQL.getPatrolStickRecord", param);
	}

	/**
	 * 
	 * @title: getGrPatrolPositionCardList
	 * @description: 分页得到员工巡更棒信息
	 * @author zhujiabiao
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Page<HashMap> getGrPatrolStickList(final Page<HashMap> page,
			HashMap<String, String> parMap) {
		return sqlDao.findPage(page, "PropertySQL.selectPatrolStickList",parMap);
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
			resultList = sqlDao.findList("PropertySQL.selectPropertyOrgInfo",
					null);
			if (resultList.size() > 0) {
				for (int i = 0; i < resultList.size(); i++) {
					Org org = new Org();
					if (resultList.get(i).get("ORG_ID") != null) {
						org.setOrgId(Long.parseLong(String.valueOf(resultList
								.get(i).get("ORG_ID"))));
					}

					if (resultList.get(i).get("NAME") != null) {
						org.setName(String.valueOf(resultList.get(i)
								.get("NAME")));
					}
					orgList.add(org);
				}
			}
		} catch (Exception e) {
		}
		return orgList;
	}

	public List<GrPatrolStick> getGrPatrolStickList(){
		return (List<GrPatrolStick>)dao.getSession().createSQLQuery("select * from gr_patrol_stick g where g.is_avaliable = '1' order by g.s_id ").addEntity(GrPatrolStick.class).list();
	}
	
	
	public List<VisRfidandid> getVisRfidandidList(){
		return (List<VisRfidandid>)dao.getSession().createSQLQuery("select t.* from vis_rfidandid t where t.status = '1' order by t.visid ").addEntity(VisRfidandid.class).list();
	}
	
	public Map findPatrolPositionCardById(Long patrolPositionCardId){
		Map parMap = new HashMap();
		parMap.put("patrolPositionCardId", patrolPositionCardId);
		List list = sqlDao.findList("PropertySQL.selectPatrolPositionCardList",
				parMap);
		if(list!=null&&list.size()>0){
			return (Map)list.get(0);
		}
		return null;
	}
	
	public List getCheckPatrolPositionCardList(String[] treeUIds){
		Map parMap = new HashMap();
		parMap.put("treeUIds", treeUIds);
		return sqlDao.findList("PropertySQL.checkPatrolPositionCardList",parMap);
	}

	public List getCheckPatrolStickList(String[] treeUIds){
		Map parMap = new HashMap();
		parMap.put("treeUIds", treeUIds);
		return sqlDao.findList("PropertySQL.checkPatrolStickList",parMap);
	}
	
	public Map findPatrolStickById(Long patrolStick){
		Map parMap = new HashMap();
		parMap.put("patrolStickUserId", patrolStick);
		List list = sqlDao.findList("PropertySQL.selectPatrolStickList",parMap);
		if(list!=null&&list.size()>0){
			return (Map)list.get(0);
		}
		return null;
	}
}
