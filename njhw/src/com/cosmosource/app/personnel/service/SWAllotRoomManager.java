package com.cosmosource.app.personnel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
/** 
* @description: 三维——分配房间给部门
* @author zh
* @date 2013-03-23
*/
@SuppressWarnings("unchecked")
public class SWAllotRoomManager extends BaseManager {
	/**
	 * @description:加载一级机构（委办局）
	 * @author zh
	 * @return List
	 * @date 2013-03-24
	 */
	public List<Org> loadOrg() {
		return dao.findByHQL("select t from Org t where t.levelNum = ?  order by t.orderNum asc", Org.LEVELNUM_2);
	}
	
	/**
	 * @description:分配房间前判断选定的房间是否已分配给其他委办局
	 * @author zh
	 * @param ids
	 * @date 2013-03-23
	 */
	public List<HashMap> checkRoomIsAllot(String ids, long orgId){
		List roomIDS = new ArrayList();
		for(String str : ids.split(",")) {
			//int index = str.indexOf("_");
			//roomIDS.add(Long.parseLong(str.substring(0,index)));
			roomIDS.add(Long.parseLong(str));
		}
		
		HashMap map = new HashMap();
		map.put("ids", roomIDS);
		map.put("len", roomIDS.size());
		map.put("orgId", orgId);
		map.put("eortype", EmOrgRes.EOR_TYPE_ROOM);
		return sqlDao.findList("PersonnelSQL.validRoom", map);
	}
	
	/**
	 * @description:分配房间
	 * @author zh
	 * @return
	 * @date 2013-03-24
	 */
	public void saveSWAllotRoom(long orgId, String orgName, String cancelIds, String allotIds, String skipRoomIds) {
		try {
//			 取消房间分配
//			List<Long> cancelValidIds = new ArrayList<Long>();
//			for (String id : cancelIds.split(",")) {
//				if (!"".equals(id)) cancelValidIds.add(Long.parseLong(id));
//			}
//			if (cancelValidIds.size() > 0) {
//				HashMap map = new HashMap();
//				map.put("resIds", cancelValidIds);
//				map.put("orgId", orgId);
//				map.put("eorType", EmOrgRes.EOR_TYPE_ROOM);
//				sqlDao.getSqlMapClientTemplate().delete("PersonnelSQL.deleteAlreadyRooms", map);
//			}
			// 删除之前分配给委办局的房间
			HashMap map = new HashMap();
			map.put("orgId", orgId);
			map.put("eorType", EmOrgRes.EOR_TYPE_ROOM);
			sqlDao.getSqlMapClientTemplate().delete("PersonnelSQL.swDeleteAlreadyRooms", map);
			
			// 保存新加房间
			for(String str : allotIds.split(",")) {
				//int index = str.indexOf("_");
				//Long resId = Long.parseLong(str.substring(0,index));
				Long resId = Long.parseLong(str);
				if (skipRoomIds != null && StringUtil.isNotBlank(skipRoomIds)) {
					boolean isSkip = false;
					// 跳过已分配的房间
					for(String skipRoomId : skipRoomIds.split(",")){
						if (resId == Long.parseLong(skipRoomId.toString())) {
							isSkip = true;
							break;
						}
					}
					if (isSkip) continue;
				}
				
//				int index = str.indexOf("_");
//				long resId = Long.parseLong(str.substring(0,index));
//				List<EmOrgRes> listEmOrgRes = dao.findByHQL("select t from EmOrgRes t where t.resId = ? and t.eorType = ? and t.orgId = ?", resId, "1", orgId);
//				if (listEmOrgRes.size() > 0 && listEmOrgRes != null)  continue;
				
				Objtank objTank = (Objtank) dao.findById(Objtank.class, resId);
				if (objTank == null) continue;
				
				EmOrgRes emOrgRes = new EmOrgRes();
				emOrgRes.setOrgId(orgId);
				emOrgRes.setOrgName(orgName);
				emOrgRes.setResId(resId);
				emOrgRes.setResName(objTank.getName());
				emOrgRes.setEorType("1");	// 分配房间
				emOrgRes.setPorFlag("1");	// 有效
				emOrgRes.setInsertDate(DateUtil.getSysDate());
				emOrgRes.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				dao.save(emOrgRes);
				//dao.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @title: loadOrgByUserOrgId
	 * @description: TODO
	 * @author cjw
	 * @param orgId
	 * @return
	 * @date 2013-4-2 下午02:19:23
	 * @throws
	 */
	public List<Org> loodOrgsByUserOrgId(Long orgId) {
		List<Org> orderList = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			List<Org> tempList = sqlDao.findList("PersonnelSQL.getOrgByUserOrgId", map);
			if (null != tempList && tempList.size() > 0) {
				orderList = new ArrayList<Org>();
				orderList.add(tempList.get(0));
				playOrgList(tempList, orderList, orgId, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
		// return
		// dao.findByHQL("select t from Org t where t.orgId = "+orgId+" or t.PId="+orgId+" order by t.orgId desc");
	}

	/**
	 * 
	 * @title: playOrgList
	 * @description: 把机构做层次缩进
	 * @author cjw
	 * @param tempList
	 * @return
	 * @date 2013-4-2 下午04:10:08
	 * @throws
	 */
	private void playOrgList(List<Org> tempList, List<Org> orderList, Long pid,
			String level) {
		try {
			level += "--";
			for (Org org : tempList) {
				if (org.getPId() != null && org.getPId().equals(pid)) {
					org.setName(level + org.getName());
					orderList.add(org);
					playOrgList(tempList, orderList, org.getOrgId(), level);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @description:根据orgId得到该部门没有分配房间的人员的信息
	 * @author cjw
	 * @return List
	 * @date 2013-04-02
	 */
	public List<Users> loadUsersByOrgIdUnAllot(Long orgId) {
	return dao.findByHQL("select distinct u from Users u,NjhwUsersExp nu where u.userid = nu.userid and nu.roomId is null and u.orgId="+ orgId);
	
	}
	/**
	 * @description:根据orgId得到该部门分配了其他房间的人员的信息
	 * @author cjw
	 * @return List
	 * @date 2013-04-02
	 */
	public List<Users>loadUsersByOrgIdAllotOtherRoom(Long orgId,Long roomId){
		return dao.findByHQL("select distinct u from Users u,NjhwUsersExp nu where u.userid = nu.userid and nu.roomId is not null and nu.roomId !="+roomId+" and u.orgId="+ orgId);
	}
	/**
	 * @description:根据orgId得到该部门分配了该房间的人员的信息
	 * @author cjw
	 * @return List
	 * @date 2013-04-02
	 */
	public List<Users>loadUsersByOrgIdAllotThisRoom(Long orgId,Long roomId){
		return dao.findByHQL("select distinct u from Users u,NjhwUsersExp nu where u.userid = nu.userid and nu.roomId ="+roomId+" and u.orgId="+ orgId);
	}
	public void updateUsersByRoomId(String roomId,String userIds,String orgId){
		if(StringUtil.isNotBlank(userIds)){
			String ids[] = userIds.split(",");
			String delHql=" update NjhwUsersExp nu set nu.roomId ='' where nu.roomId=? and nu.userid in (select u.userid from Users u where u.orgId=?)";
			Query delQuery = dao.createQuery(delHql,Long.parseLong(roomId),Long.parseLong(orgId));
			delQuery.executeUpdate();
			for(int i=0;i<ids.length; i++){
				String updateHql ="update NjhwUsersExp nu set nu.roomId = ? where nu.userid = ?";
				Query updateQuery = dao.createQuery(updateHql,Long.parseLong(roomId), Long.parseLong(ids[i]));
				updateQuery.executeUpdate();
				
			}
		}
		
	}
}
