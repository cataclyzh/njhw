package com.cosmosource.app.personnel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
/** 
* @description: 分配房间给部门
* @author zh
* @date 2013-03-23
*/
@SuppressWarnings("unchecked")
public class AllotRoomManager extends BaseManager {
	
	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}

	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}

	private DoorControlToAppService doorControlToAppService;
	
	/**
	 * @description:加载一级委办局
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-04-07
	 */
	public Page<HashMap> queryOrgList(Page<HashMap> page, HashMap<String, String> parMap) {
		return dao.findPage(page, "select t from Org t where t.levelNum = ? and t.PId = '2' order by t.orderNum asc", Org.LEVELNUM_2);
	}
	
	/**
	 * @description:查询房间分配情况
	 * @author zh
	 * @param page
	 * @param parMap
	 * @return Page<HashMap>
	 * @date 2013-04-07
	 */
	public Page<HashMap> queryAllotRooms(Page<HashMap> page, HashMap<String, String> parMap) {
		page = sqlDao.findPage(page, "PersonnelSQL.allotRooms", parMap);
		
		// 当查询已分配的房间信息时，统计每个房间现有人数
		List roomList = new ArrayList();
		for (HashMap map : page.getResult()) {
			if (map.get("RES_ID") != null)
				roomList.add(Long.parseLong(map.get("RES_ID").toString()));
		}

		List<HashMap> countPersonList = new ArrayList<HashMap>();
		if (roomList.size() > 0) {
			Map map = new HashMap();
			map.put("len", roomList.size());
			map.put("ids", roomList);
			countPersonList = sqlDao.findList(
					"PersonnelSQL.countPersonForRoom", map);
		}

		for (HashMap map : page.getResult()) {
			String num = "0";
			for (HashMap countMap : countPersonList) {
				if (map.get("RES_ID") != null && map.get("RES_ID").toString().equals(
						countMap.get("ROOM_ID").toString())) {
					num = countMap.get("NUM").toString();
					break;
				}
			}
			map.put("COUNT_PERSON", num);
		}
		return page;
	}
	
	/**
	 * @description:查询房间分配数量
	 * @author hj
	 * @param isAllot
	 * @return Long
	 * @date 2013-09-05
	 */
	public Long countAllotRooms(String isAllot) {
		Long rv = 0l;
		Map map = new HashMap();
		if (StringUtil.isNotBlank(isAllot)) {
			map.put("isAllot", isAllot);
		}
		
		List<Map> lm = sqlDao.findList(
				"PersonnelSQL.countAllotRooms", map);
		
		if (lm != null && lm.size() > 0) {
			rv = lm.get(0).get("COUNT") == null ? 0l : Long.parseLong(lm.get(0).get("COUNT").toString());
		}
		
		return rv;
	}
	
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public Object findUnique(final String hql, final Object... values) {
		return super.dao.findUnique(hql, values);
	}

	/**
	* @Title: getObjTreeSelectData 
	* @Description: 一级页面资源树chechbox
	* @author WXJ
	* @date 2013-5-6 下午01:01:57 
	* @param @param orgid
	* @param @param ctx
	* @param @param type
	* @param @return    
	* @return String 
	* @throws
	*/
	public String getObjTreeSelectData(String objId, String ids, String type) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getObjTreeDoc(objId, ids, root, type);
		return doc.asXML();		
	}
	
	public void getObjTreeDoc(String objId,  String ids,  Element root, String type) {
		if (objId==null) {	
			List<Objtank> list = this.findByHQL(" from Objtank where nodeId=?", 6L);
			
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getTitle());
	            el.addAttribute("id", obj.getNodeId()+"_"+obj.getTitle()+"_"+obj.getExtResType());
	            el.addAttribute("open", "1");
	            
	            getObjTreeDoc(obj.getNodeId().toString(), ids, el, type);
	        }
		} else {
			String nodeId = objId;
			List<Objtank> list = this.findByHQL(" from Objtank where extResType in ('"+
					Objtank.EXT_RES_TYPE_S+"','"+
					Objtank.EXT_RES_TYPE_B+"','"+
					Objtank.EXT_RES_TYPE_F+"'"+
					") and PId=?", new Long(nodeId));
			
			String[] idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getTitle());
	            el.addAttribute("id", obj.getNodeId()+"_"+obj.getTitle()+"_"+obj.getExtResType());
	            if("1".equals(obj.getExtIsBottom())){
	            	if("S".equals(obj.getExtResType()))  el.addAttribute("open", "1");
	            }
	            if(idsArray != null && idsArray.length > 0) {
	            	 for (String strID : idsArray) {
	 	            	if (obj.getNodeId().toString().equals(strID)) {
	 	            		el.addAttribute("checked", "1");
	 	            		break;
	 	            	}
	 				}
	            }
	           
	            getObjTreeDoc(obj.getNodeId().toString(),  ids, el, type);
	        }
		}		
	}
	
	/**
	 * @description:加载楼座
	 * @author zh
	 * @return List
	 * @date 2013-03-24
	 */
	public List loadBalcony() {
		return dao.findByHQL("select t from Objtank t where t.extResType = ?", Objtank.EXT_RES_TYPE_B);
	}
	
	/**
	 * @description:根据楼座ID加载楼层
	 * @author zh
	 * @return List
	 * @date 2013-03-24
	 */
	public List loadFloorByBid(long bid) {
		return dao.findByHQL("select t.nodeId, t.name from Objtank t where t.extResType = ? and t.PId = ?", Objtank.EXT_RES_TYPE_F, bid);
	}
	
	/**
	 * @description:根据orgID获取单位名称
	 * @author zh
	 * @return List
	 * @date 2013-03-24
	 */
	public String getOrgName(String orgId) {
		List orgList = dao.findByHQL("select t.name from Org t where t.orgId = ?", Long.parseLong(orgId));
		String orgName = "";
		if (orgList != null && orgList.size() > 0 && orgList.get(0) != null) {
			orgName = orgList.get(0).toString();
		}
		return orgName;
	}
	
	/**
	 * @description:分配房间
	 * @author zh
	 * @return
	 * @date 2013-03-24
	 */
	public void saveAllotRoom(String allNodeId, String chkNodeId, long orgId) {
		// 删除之前分配给机构未入住人员的所有房间
		List<Long> delIds = new ArrayList<Long>();
		for (String str : allNodeId.split(",")) {
			if (StringUtil.isNotEmpty(str)) {
				String [] strArray = str.split("_");
				if ("0".equals(strArray[2].toString())) {
					if (!"".equals(strArray[0])) delIds.add(Long.parseLong(strArray[0]));
				}
			}
		}
		if (delIds.size() > 0) {
			HashMap map = new HashMap();
			map.put("resIds", delIds);
			map.put("eorType", EmOrgRes.EOR_TYPE_ROOM);
			map.put("orgId", orgId);
			sqlDao.getSqlMapClientTemplate().delete("PersonnelSQL.deleteAlreadyRooms", map);
		}
		
		for(String str : chkNodeId.split(",")) {
			if (StringUtil.isEmpty(str)) continue;
			
			String [] strArray = str.split("_");
			// 当前房间已经有人入住
			if (Integer.parseInt(strArray[2].toString()) > 0) continue;
			
			EmOrgRes emOrgRes = new EmOrgRes();
			emOrgRes.setOrgId(orgId);
			
			Org org = (Org)dao.findById(Org.class, orgId);
			emOrgRes.setOrgName(org.getName());
			
			emOrgRes.setResId(Long.parseLong(strArray[0].toString()));
			emOrgRes.setResName(strArray[1]);
			
			emOrgRes.setEorType("1");	// 分配房间
			emOrgRes.setPorFlag("1");	// 有效
			emOrgRes.setInsertDate(DateUtil.getSysDate());
			emOrgRes.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			try {
				dao.save(emOrgRes);
				//dao.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
//	/**
//	 * @description:查询房间分配信息
//	 * @author zh
//	 * @param page
//	 * @param parMap
//	 * @return Page<HashMap>
//	 * @date 2013-03-23
//	 */
//	public Page<HashMap> queryRoomList(final Page<HashMap> page, HashMap<String, String> parMap) {
//		return sqlDao.findPage(page, "PersonnelSQL.RoomList", parMap);
//	}
//	
//	/**
//	 * @description:加载一级机构（委办局）
//	 * @author zh
//	 * @return List
//	 * @date 2013-03-24
//	 */
//	public List<Org> loadOrg() {
//		return dao.findByHQL("select t from Org t where t.levelNum = ?", Org.LEVELNUM_2);
//	}
//	
//	/**
//	 * @description:根据楼层ID加载未分配的房间
//	 * @author zh
//	 * @return List
//	 * @date 2013-03-24
//	 */
//	public List loadRoomByFid(long fid) {
//		// 获取已分配的房间ID
//		List<Long> list = dao.findByHQL("select t.resId from EmOrgRes t where t.eorType = ?", EmOrgRes.EOR_TYPE_ROOM);
//		List ids = new ArrayList();
//		for (Long resId : list) {
//			if (resId != null && resId > 0) ids.add(Long.parseLong(resId.toString()));
//		}
//		// 取得未分配的房间
//		HashMap map = new HashMap();
//		map.put("ids", ids);
//		map.put("pid", fid);
//		map.put("len", ids.size());
//		return sqlDao.findList("PersonnelSQL.NoAllotList", map);
//	}
//	
//	/**
//	 * @description:批量删除
//	 * @author zh
//	 * @param ids
//	 * @date 2013-03-23
//	 */
//	public void deleteRoomAllot(String[] ids){
//		dao.deleteByIds(EmOrgRes.class, ids);
//	}
//	
//	/**
//	 * @description:分配房间前判断选定的房间是否已分配给其他委办局
//	 * @author zh
//	 * @param ids
//	 * @date 2013-03-23
//	 */
//	public List<HashMap> checkRoomIsAllot(String ids, long orgId){
//		List roomIDS = new ArrayList();
//		for(String str : ids.split(",")) {
//			int index = str.indexOf("_");
//			roomIDS.add(Long.parseLong(str.substring(0,index)));
//		}
//		
//		HashMap map = new HashMap();
//		map.put("ids", roomIDS);
//		map.put("len", roomIDS.size());
//		map.put("orgId", orgId);
//		return sqlDao.findList("PersonnelSQL.validRoom", map);
//	}
//	
//	/**
//	 * @description:分配房间
//	 * @author zh
//	 * @return
//	 * @date 2013-03-24
//	 */
//	public void allotSave(EmOrgRes emOrgResinfo, String roomIds, String skipRoomIds) {
//		for(String str : roomIds.split(",")) {
//			int index = str.indexOf("_");
//			Long resId = Long.parseLong(str.substring(0,index));
//			boolean isSkip = false;
//			// 跳过已分配的房间
//			for(String skipRoomId : skipRoomIds.split(",")){
//				if (!"".equals(skipRoomId.toString()) && resId == Long.parseLong(skipRoomId.toString())) {
//					isSkip = true;
//					break;
//				}
//			}
//			if (isSkip) continue;
//			
//			EmOrgRes emOrgRes = new EmOrgRes();
//			emOrgRes.setPorSt(emOrgResinfo.getPorSt());
//			emOrgRes.setPorEt(emOrgResinfo.getPorEt());
//			emOrgRes.setOrgId(emOrgResinfo.getOrgId());
//			emOrgRes.setOrgName(emOrgResinfo.getOrgName());
//			emOrgRes.setResId(resId);
//			emOrgRes.setResName(str.substring(index+1));
//			emOrgRes.setEorType("1");	// 分配房间
//			emOrgRes.setPorFlag("1");	// 有效
//			emOrgRes.setInsertDate(DateUtil.getSysDate());
//			emOrgRes.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
//			try {
//				dao.save(emOrgRes);
//				dao.flush();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	/**
	 * @description:删除已经分配的房间
	 * @author hj
	 * @return
	 * @date 2013-09-04
	 */
	public String allotDeleteSave(String chkNodeId, long orgId) {
		String rtnVal = "";
		
		List<Long> delIds = new ArrayList<Long>();
		for(String str : chkNodeId.split(",")) {
			if (StringUtil.isEmpty(str)) continue;
			
			String [] strArray = str.split("_");
			// 当前房间已经有人入住
			if (Integer.parseInt(strArray[2].toString()) > 0) {
				rtnVal += strArray[1] + ",";
			} else {
				delIds.add(Long.parseLong(strArray[0]));
				Map map = new HashMap();
				map.put("nodeId", strArray[0]);
				List<Map> lm = sqlDao.findList("PersonnelSQL.getAuthUserByRoomId", map);
				if (lm != null && lm.size() > 0) {
					for (Map m : lm) {
						if (m != null && m.get("USERID") != null)
							doorControlToAppService.delDoorAuth(m.get("USERID")
									.toString(),
									Struts2Util.getSession().getAttribute(
											Constants.USER_ID).toString(),
									strArray[0], null);
					}
				}
			}
		}
		
		if (delIds.size() > 0) {
			HashMap map = new HashMap();
			map.put("resIds", delIds);
			map.put("eorType", EmOrgRes.EOR_TYPE_ROOM);
			map.put("orgId", orgId);
			sqlDao.getSqlMapClientTemplate().delete("PersonnelSQL.deleteAlreadyRooms", map);
		}
		
		return StringUtil.isNotBlank(rtnVal) ? StringUtil.chop(rtnVal) : "";
	}

	/**
	 * @description:查询可分配的房间
	 * @author hj
	 * @return
	 * @date 2013-09-04
	 */
	public Long countRoomsCanAllot() {
		Long rv = 0l;
		Map map = new HashMap();
		List<Map> lm = sqlDao.findList("PersonnelSQL.countRoomsCanAllot", map);
		if (lm != null && lm.size() > 0) {
			rv = lm.get(0).get("COUNT") == null ? 0l : Long.parseLong(lm.get(0).get("COUNT").toString());
		}
		return rv;
	}

	/**
	 * @description:添加未分配的房间
	 * @author hj
	 * @return
	 * @date 2013-09-04
	 */
	public void allotRoomSave(String chkNodeId, long orgId) {
		for(String str : chkNodeId.split(",")) {
			if (StringUtil.isEmpty(str)) continue;
			
			String [] strArray = str.split("_");
			
			EmOrgRes emOrgRes = new EmOrgRes();
			emOrgRes.setOrgId(orgId);
			
			Org org = (Org)dao.findById(Org.class, orgId);
			emOrgRes.setOrgName(org.getName());
			
			emOrgRes.setResId(Long.parseLong(strArray[0].toString()));
			emOrgRes.setResName(strArray[1]);
			
			emOrgRes.setEorType("1");	// 分配房间
			emOrgRes.setPorFlag("1");	// 有效
			emOrgRes.setInsertDate(DateUtil.getSysDate());
			emOrgRes.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			try {
				dao.save(emOrgRes);
				//dao.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		dao.flush();
	}
}
