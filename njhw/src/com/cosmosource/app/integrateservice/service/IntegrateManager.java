package com.cosmosource.app.integrateservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.CsRepairFault;
import com.cosmosource.app.entity.FsDishes;
import com.cosmosource.app.entity.NjhwAdlistGroup;
import com.cosmosource.app.entity.Org;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcDictdeta;
import com.cosmosource.common.entity.TAcDicttype;

@SuppressWarnings("unchecked")
public class IntegrateManager extends BaseManager{
	
	//----------------------------------------数据加载部分------------------------------------
	/**
	 * @description:我的消息
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> queryMsgBox() {
		Map map = new HashMap();
		map.put("receiverid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		map.put("status", 0);
		return sqlDao.findList("IntegrateSQL.queryMsgBox", map);
	}
	
	/**
	 * @description:找指定类型的设备ID
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> loadDeviceId(Map map) {
		return sqlDao.findList("IntegrateSQL.loadDeviceId", map);
	}
	
	/**
	 * @description:	直接根据房间ID找对应的门锁ID
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> loadDeviceByRoomId(Map map) {
		return sqlDao.findList("IntegrateSQL.loadDeviceByRoomId", map);
	}
	
	/**
	 * @description:	取得数据库中最新的日期
	 * @author zh
	 * @return String
	 */
	public String getLastTime() {
		String date = "";
		// 取得数据库中最新的交通更新日期
		List<HashMap> lastSendTimeList = sqlDao.findList("IntegrateSQL.getLastSendTime", null);
		if (lastSendTimeList.size() > 0)	date = lastSendTimeList.get(0).get("SEND_TIME").toString();
		return date;
	}
	
	/**
	 * @description:加载交通
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> queryTraffic() {
		List<HashMap> finalRstList = new ArrayList<HashMap>();
		String date = getLastTime();
		
		if (date != null && StringUtil.isNotEmpty(date)) {
			TAcDicttype dictTypeInfo = (TAcDicttype)dao.findByHQL("select t from TAcDicttype t where t.dicttypecode = ?", "ROAD_INFO").get(0);			// 路况分类ID
			TAcDicttype dictTypeLocation = (TAcDicttype)dao.findByHQL("select t from TAcDicttype t where t.dicttypecode = ?", "ROAD_LOCATION").get(0);	// 交通分类ID
			
			// 根据路况分类ID取得指定路况字典
			List<TAcDictdeta> listInfo = dao.findByHQL("select t from TAcDictdeta t where t.dicttype = ?", dictTypeInfo.getDicttypeid());
			// 根据交通分类ID取得指定交通字典
			List<TAcDictdeta> listLocation = dao.findByHQL("select t from TAcDictdeta t where t.dicttype = ?", dictTypeLocation.getDicttypeid());
	
			// 取得路况CODE及CODE对应的道路名称
			List roadIds = new ArrayList();
			HashMap CNMapInfo = new HashMap();
			for (TAcDictdeta tAcDictdeta : listInfo) {
				roadIds.add(tAcDictdeta.getDictcode().toString());
				CNMapInfo.put(tAcDictdeta.getDictcode().toString(), tAcDictdeta.getDictname().toString());
			}
			// 取得交通CODE对应的交通情况
			HashMap CNMapLocation = new HashMap();
			for (TAcDictdeta tAcDictdeta : listLocation) {
				CNMapLocation.put(tAcDictdeta.getDictcode().toString(), tAcDictdeta.getDictname().toString());
			}
			
			Map map = new HashMap();
			System.out.println(date.substring(0, 10));
			//map.put("today", DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd"));
			map.put("today", date.substring(0, 10));
			map.put("roadIds", roadIds);
			List<HashMap> rstList = sqlDao.findList("IntegrateSQL.queryTraffic", map);
			
			for (HashMap rstMap : rstList) {
				rstMap.put("ROAD_NAME", CNMapInfo.get(rstMap.get("ROADID").toString()).toString());
				rstMap.put("ROAD_CONDITION_NAME", CNMapLocation.get(rstMap.get("ROAD_CONDITION").toString()).toString());
				finalRstList.add(rstMap);
			}
		}
		return finalRstList;
	}
	
	/**
	 * @description:我的来访
	 * @author zh
	 * @return List<HashMap>
	 */
	/*public List<HashMap> queryVmVisit() {
		Map map = new HashMap();
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		map.put("vsFlag", VmVisit.VS_FLAG_APP);
		return sqlDao.findList("IntegrateSQL.queryVmVisit", map);
	}*/
	
	/**
	 * @description:加载直属人员
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> loadPerson(long orgId) {
		Map map = new HashMap();
		map.put("orgId", orgId);
		return sqlDao.findList("IntegrateSQL.loadPerson", map);
	}
	
	/**
	 * @description:加载下级部门
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> loadOrg(long orgId) {
		Map map = new HashMap();
		map.put("orgId", orgId);
		return sqlDao.findList("IntegrateSQL.loadOrg", map);
	}
	
	/**
	 * @description:筛选大厦/部门通讯录
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> queryContact(String tjVal, String type, String orgId) {
		Map map = new HashMap();
		map.put("tjVal", tjVal);
		map.put("type", type);
		map.put("orgId", orgId);
		return sqlDao.findList("IntegrateSQL.queryContact", map);
	}
	
	/**
	 * @description:筛选大厦/部门通讯录
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> queryE4Contact(String tjVal, String type, String orgId) {
		Map map = new HashMap();
		map.put("tjVal", tjVal);
		map.put("type", type);
		map.put("orgId", orgId);
		return sqlDao.findList("IntegrateSQL.queryE4Contact", map);
	}
	
	/**
	 * @description:筛选个人通讯录
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> queryAddress(String tjVal) {
		Map map = new HashMap();
		map.put("tjVal", tjVal);
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return sqlDao.findList("IntegrateSQL.queryAddress", map);
	}
	
	/**
	 * @description:加载单位
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> loadFirstOrg() {
		Map map = new HashMap();
		//map.put("level_num", Org.LEVELNUM_2);
		map.put("orgId", "2");
		return sqlDao.findList("IntegrateSQL.loadFirstOrg", map);
	}
	
	/**
	 * @description: 加载新城一期的部门
	 * @return List<HashMap>
	 */
	public List<HashMap> loadE4List() {
		Map map = new HashMap();
		map.put("orgId", "1070");
		return sqlDao.findList("IntegrateSQL.loadFirstOrg", map);
	}
	
	/**
	 * @description:公告信息
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> queryMsgBoard() {
		return sqlDao.findList("IntegrateSQL.queryMsgBoard", null);
	}
	
	/**
	 * @description:未分派报修单
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> queryUntreatedSheet() {
		Map map = new HashMap();
		map.put("crfFlag", CsRepairFault.CRF_FLAG_APP);
		return sqlDao.findList("IntegrateSQL.queryUntreatedSheet", map);
	}
	
	/**
	 * @description: 加载个人通讯录分组
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> loadGroup() {
		Map map = new HashMap();
		map.put("typeCus", Long.parseLong(NjhwAdlistGroup.NAG_TYPE_CUSTOM));
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return sqlDao.findList("PABSQL.loadGroupList", map);
	}
	
	/**
	 * @description: 加载指定通讯录人员信息
	 * @author zh
	 * @return List<HashMap>
	 */
	public List<HashMap> loadPersonByGid(long gid) {
		Map map = new HashMap();
		map.put("gid", gid);
		map.put("name", "");
		map.put("num", "");
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return sqlDao.findList("PABSQL.loadAddListByGId", map);
	}

	/**
	* 查询菜单
	* @title: queryMenus 
	* @description: TODO
	* @author gxh
	* @param fdiType
	* @return
	* @date 2013-5-3 下午01:55:49     
	* @throws
	*/
	/*public List<HashMap> queryMenus(String fdiType) {
		Map map = new HashMap();
		map.put("fdiType", fdiType);
		return sqlDao.findList("IntegrateSQL.queryMenus", map);
	}*/
	public List<List> queryMenus(String fdiType,String fdFlag){
		
		Map<String,String> vMap = new HashMap<String,String>();
		vMap.put("fdiType", fdiType);
		vMap.put("fdFlag", fdFlag);	
		List<Map> list = sqlDao.findList("IntegrateSQL.queryMenus", vMap);
		List<List> returnList = new ArrayList<List>();
		String fmClass = "";
		int i = -1;
		String desc = "";
		if(list!=null&&list.size()>0){
			for (Map menu: list) {
				String fmC = menu.get("FD_CLASS") == null? "": menu.get("FD_CLASS").toString();
				if (!fmC.equals(fmClass)) {
					i++;
					fmClass = fmC;
					List l = new ArrayList<Map>();
					l.add(menu);
					returnList.add(l);
					desc = menu.get("FD_NAME") == null ? "": menu.get("FD_NAME").toString();
				} else {
					if (menu.get("FD_NAME") != null) {
						desc += ", " + menu.get("FD_NAME");
					}
					((Map) ((List) returnList.get(i)).get(0)).put("FD_NAME", desc);
					((List) returnList.get(i)).add(menu);
				}
			}
			
			return returnList;
			
		}else{
			return null;
		}
		
	}
	
	 public FsDishes menusOneQuery(Long fdId){
			
		 FsDishes visit = new FsDishes(); 
			List visitList = dao.findByProperty(FsDishes.class, "fdId", fdId);
			if(visitList != null && visitList.size()>=1){
				visit = (FsDishes)visitList.get(0);
				return visit;
			}
			return null;
		}
}
