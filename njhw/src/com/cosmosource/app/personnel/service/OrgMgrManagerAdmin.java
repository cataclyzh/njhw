/**
* <p>文件名: OrgMgrManager.java</p>
* <p>描述：机构管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2013-4-26 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.app.personnel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Hibernate;

import com.cosmosource.app.entity.BmMonitor;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.NjhwUsersPlatenum;
import com.cosmosource.app.entity.ObjPermMap;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * @类描述:  机构的管理类
 * 调用通用DAO对机构的CRUD，及机构树数据的查询
 * 
 * @作者： WXJ
 */
/** 
* @ClassName: OrgMgrManager 
* @Description: TODO 
* @author WXJ
* @date 2013-5-6 下午01:51:53 
*  
*/ 
@SuppressWarnings({"unchecked","rawtypes"})
public class OrgMgrManagerAdmin extends BaseManager{
	private UnitAdminManager unitAdminManager;
	private DoorControlToAppService doorControlToAppService;
	
	public UnitAdminManager getUnitAdminManager() {
		return unitAdminManager;
	}

	public void setUnitAdminManager(UnitAdminManager unitAdminManager) {
		this.unitAdminManager = unitAdminManager;
	}

	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}
	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}
	
	/**
	 * 保存用户房间信息
	 * @param entity
	 */
	public void saveRoomUser(String roomid,String ids, String loginId){	
		String roomInfo = ((Objtank)this.findById(Objtank.class, NumberUtil.strToLong(roomid))).getName();
		
		if (ids==null || ids.length()==0){
			List<NjhwUsersExp> expList = dao.findByProperty(NjhwUsersExp.class, "roomId", NumberUtil.strToLong(roomid));
			
			if (expList != null && expList.size() > 0) {
				for (NjhwUsersExp uep : expList) {
					doorControlToAppService.delDoorAuth(String.valueOf(uep.getUserid()), loginId, roomid, null);
				}
			}

			//删除所有人员
			dao.batchExecute("update NjhwUsersExp set roomId=null,roomInfo=null where roomId=? ", NumberUtil.strToLong(roomid));
			
			//写操作日志：删除所有人员某房间门锁权限
			BmMonitor bm = new BmMonitor();
			bm.setBmType(Objtank.EXT_RES_TYPE_3);
			bm.setBmDetail("删除门锁权限：删除所有人员房间("+roomInfo+":"+roomid+")门锁权限");
			bm.setInsertId(new Long(Struts2Util.getSessionAttribute(Constants.USER_ID).toString()));
			bm.setInsertName(Struts2Util.getSessionAttribute(Constants.USER_NAME).toString());
			bm.setInsertDate(DateUtil.StringToDate(DateUtil.getDateTime()));
			dao.save(bm);
		}else{					
			String orgUserId="";
			StringBuffer useridArr=new StringBuffer();
			StringTokenizer st = new StringTokenizer(ids,",");
						
			//得到房间的全部门锁
			List<Map> resList = unitAdminManager.getRoomRes(Objtank.EXT_RES_TYPE_3,roomid);
			
			while (st.hasMoreTokens()) { 
				orgUserId = st.nextToken();
	//			if (orgid.endsWith("-o")){
	//				Map pMap = new HashMap();
	//				pMap.put("orgid", orgid.substring(0, orgid.indexOf("-o")));
	//				List<Users> usersList = this.findListBySql("PersonnelSQL.getOrgAllUsers", pMap);
	//				for (Users user : usersList){
	//					useridArr.append(user.getUserid());
	//					useridArr.append(",");
	//				}
	//			}
				if (orgUserId.endsWith("-u")){
					useridArr.append(orgUserId.substring(0, orgUserId.indexOf("-u")));
					useridArr.append(",");
					
					String userid = orgUserId.substring(0, orgUserId.indexOf("-u"));		
					
					//默认授予门锁权限
					List<ObjPermMap> objList = this.findByHQL("from ObjPermMap where personId=? and nodeId=? and type='user' and permCode='obj_vis' and denyFlag='1'", NumberUtil.strToLong(userid), NumberUtil.strToLong(resList.get(0).get("NODE_ID").toString()));
						
					if (objList==null || objList.size()==0) {
						doorControlToAppService.addDoorAuth(userid, loginId, roomid, null);	
					}
					
				}
			}
			
			String useridArrStr = useridArr.toString().substring(0, useridArr.toString().length()-1);					
			
			//清除之前勾选本次未勾选的门锁权限
			List<NjhwUsersExp> expList = dao.findByProperty(NjhwUsersExp.class, "roomId", NumberUtil.strToLong(roomid));
				
			if (expList != null && expList.size() > 0) {
				for (NjhwUsersExp uep : expList) {
					if (useridArrStr.indexOf(String.valueOf(uep.getUserid())) == -1) {
						doorControlToAppService.delDoorAuth(String.valueOf(uep.getUserid()), loginId, roomid, null);
					}
				}
			}
			
			//清除之前勾选本次未勾选的
			dao.batchExecute("update NjhwUsersExp set roomId=null,roomInfo=null where roomId=? and userid not in ("+useridArrStr+")", NumberUtil.strToLong(roomid));
			//更新本次勾选的
			dao.batchExecute("update NjhwUsersExp set roomId=?,roomInfo=? where userid in ("+useridArrStr+")", NumberUtil.strToLong(roomid), roomInfo);	
			//插入用户扩展表中不存在的用户
			List<Users> usersList = this.findByHQL("from Users where userid not in (select t.userid from NjhwUsersExp t) and userid in ("+useridArrStr+")");
			for (Users user : usersList){
				NjhwUsersExp ue = new NjhwUsersExp();
				ue.setUserid(user.getUserid());
				ue.setRoomId(new Long(roomid));
				ue.setRoomInfo(roomInfo);
				dao.save(ue);			
			}
			
			//写操作日志：授予人员某房间门锁权限
			BmMonitor bm = new BmMonitor();
			bm.setBmType(Objtank.EXT_RES_TYPE_3);
			bm.setBmDetail("授予门锁权限：授予人员("+useridArrStr+")房间("+roomInfo+":"+roomid+")门锁权限，同时删除其他人员本房间门锁权限");
			bm.setInsertId(new Long(Struts2Util.getSessionAttribute(Constants.USER_ID).toString()));
			bm.setInsertName(Struts2Util.getSessionAttribute(Constants.USER_NAME).toString());
			bm.setInsertDate(DateUtil.StringToDate(DateUtil.getDateTime()));
		}
		
		
	}
	
	/**
	 * 保存用户门锁信息
	 * @param entity
	 */
	public void saveLockUser(String roomid,String ids){		
		String orgUserId="";
		StringBuffer useridArr=new StringBuffer();
		StringTokenizer st = new StringTokenizer(ids,",");
		//得到房间的全部门锁
		List<Map> resList = unitAdminManager.getRoomRes(Objtank.EXT_RES_TYPE_3,roomid);
		while (st.hasMoreTokens()) { 
			orgUserId = st.nextToken();
			if (orgUserId.endsWith("-u")){
				String userid = orgUserId.substring(0, orgUserId.indexOf("-u"));		
				
				for (Map mp : resList) {	
					List<ObjPermMap> objList = this.findByHQL("from ObjPermMap where personId=? and nodeId=? and type='user' and permCode='obj_vis' and denyFlag='1'", NumberUtil.strToLong(userid), NumberUtil.strToLong(mp.get("NODE_ID").toString()));
					
					if (objList==null || objList.size()==0) {
						//插入本次勾选的	
						dao.batchExecute("insert into ObjPermMap(mapid,personId,nodeId,permCode,denyFlag,type) " +
								"select cast((max(mapid)+1) as long),cast(" + userid+" as long),cast("+mp.get("NODE_ID")+" as long),'obj_vis',cast(1 as long),'user'"+
								"from ObjPermMap");		
					}					
				}

				useridArr.append(orgUserId.substring(0, orgUserId.indexOf("-u")));
				useridArr.append(",");
			}
		}
		if (useridArr.length()>0){
			String useridArrStr = useridArr.toString().substring(0, useridArr.toString().length()-1);
			for (Map mp : resList) {	
				//清除之前勾选本次未勾选的
				dao.batchExecute("delete ObjPermMap m where exists (select 1 from Objtank o where o.nodeId=m.nodeId and o.extResType='"+Objtank.EXT_RES_TYPE_3+"')" +
								" and m.personId not in ("+useridArrStr+") and nodeId=? and type='user' and permCode='obj_vis' and denyFlag='1'", NumberUtil.strToLong(mp.get("NODE_ID").toString()));
			}
		}else{
			for (Map mp : resList) {	
				//清除之前勾选本次未勾选的
				dao.batchExecute("delete ObjPermMap where nodeId=? and type='user' and permCode='obj_vis' and denyFlag='1'", NumberUtil.strToLong(mp.get("NODE_ID").toString()));
			}
		}
		
	}
	/**
	 * 添加门锁相关的权限信息
	 * @param nodeId
	 * @param userId
	 */
	public void addObjPermMapForDoor(String userId, String nodeId) {
		dao.batchExecute("insert into ObjPermMap(mapid,personId,nodeId,permCode,denyFlag,type) " +
				"select cast((max(mapid)+1) as long),cast(" + userId+" as long),cast("+nodeId+" as long),'obj_vis',cast(1 as long),'user'"+
				"from ObjPermMap");		
	}	
	
	/**
	 * 添加门锁相关的权限信息
	 * @param nodeId
	 * @param userId
	 */
	public void updateDenyForObjPermMap(String userId, String nodeId, Long deny) {
		dao.batchExecute("update ObjPermMap set denyFlag='"+deny+"' where personId=? and nodeId=? and type='user' and permCode='obj_vis'", NumberUtil.strToLong(userId), NumberUtil.strToLong(nodeId));		
	}	
	
	/**
	 * 删除门锁相关的权限信息
	 * @param nodeId
	 * @param userId
	 */
	public void deleteObjPermMapForDoor(String userId, String nodeId) {
		dao.batchExecute("delete ObjPermMap m where exists (select 1 from Objtank o where o.nodeId=m.nodeId and o.extResType='"+Objtank.EXT_RES_TYPE_3+"')" +
				" and m.personId = '"+userId+"' and nodeId=? and type='user' and permCode='obj_vis' and denyFlag='1'", NumberUtil.strToLong(nodeId));
	
	}	
	
	/**
	 * 重置用户门锁信息
	 * @param entity
	 */
	public void resetLockUser(String roomid,String ids){		
		String orgUserId="";
		StringBuffer useridArr=new StringBuffer();
		StringTokenizer st = new StringTokenizer(ids,",");
		//得到房间的全部门锁
		List<Map> resList = unitAdminManager.getRoomRes(Objtank.EXT_RES_TYPE_3,
				roomid);

		for (Map mp : resList) {
			// 清除全部门锁信息
			dao.batchExecute(
							"delete ObjPermMap where nodeId=? and type='user' and permCode='obj_vis' and denyFlag='1'",
							NumberUtil.strToLong(mp.get("NODE_ID").toString()));
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (st.hasMoreTokens()) { 
			orgUserId = st.nextToken();
			if (orgUserId.endsWith("-u")){
				String userid = orgUserId.substring(0, orgUserId.indexOf("-u"));		
				
				for (Map mp : resList) {	
					//插入本次勾选的	
					dao.batchExecute("insert into ObjPermMap(mapid,personId,nodeId,permCode,denyFlag,type) " +
						"select cast((max(mapid)+1) as long),cast(" + userid+" as long),cast("+mp.get("NODE_ID")+" as long),'obj_vis',cast(1 as long),'user'"+
						"from ObjPermMap");						
				}

				useridArr.append(orgUserId.substring(0, orgUserId.indexOf("-u")));
				useridArr.append(",");
			}
		}
	}
	
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveOrg(Org entity){
		//设置本节点的父节点ExtIsBottom字段
		if (entity.getPId() != null && entity.getPId() > 0) {
			Org parent =(Org)dao.get(Org.class, entity.getPId());
			Long levelNum = Long.valueOf(parent.getLevelNum().trim());	
			// 根据父节点的LevelNum计算节点的LevelNum
			long plvl = levelNum != null ? levelNum : 0;
			entity.setLevelNum((plvl+1)+"");
		} else {
			entity.setLevelNum("1");	// 当没有上级节点时，默认为顶级
		}
		
		if (null == entity.getOrgId())
		{
			entity.setOrderNum(getMaxOrg(entity.getPId()).getOrderNum()+1);
			dao.save(entity);
		}
		else 
		{
			dao.update(entity);
		}
			
		
	}
	
	/**
	 * 部门排序最大
	* @title: getMax 
	* @description: TODO
	* @author zhangqw
	* @param orgId 组织id
	* @return
	* @date 2013年7月3日17:22:20
	* @throws
	 */
	private Org getMaxOrg(Long orgId){
		Map pMap = new HashMap();
		pMap.put("orgId", orgId);
		
		List<Org> list = this.findListBySql("PersonnelSQL.getMaxOrg", pMap);
		if (list != null && list.size() > 0 && null != list.get(0).getOrderNum() ) 
		{
			return list.get(0);
		}
		else
		{   
			Org org  = new Org();
			org.setOrderNum(0L);
			return org;
		}
	}
	
	/**
	 * 查询机构列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<Org> queryOrgs(final Page<Org> page, final Org model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from Org t ")
		.append("where t.PId=? ");
		lPara.add(model.getOrgId());
		if(StringUtil.isNotBlank(model.getOrgCode())){
			sbHql.append(" and t.orgCode like ? ");
			lPara.add("%"+model.getOrgCode()+"%");
		}
		if(StringUtil.isNotBlank(model.getName())){
			sbHql.append("and t.name like ? ");
			lPara.add("%"+model.getName()+"%");
		}
		if(StringUtil.isNotBlank(model.getShortName())){
			sbHql.append("and t.shortName like ? ");
			lPara.add("%"+model.getShortName()+"%");
		}
				
		sbHql.append(" order by t.orderNum ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	
	
	
	
	
	/**
	 * @描述: 删除选中的机构
	 * 如果机构下有用户，角色，权限，或是子机构则此机构不能被删除
	 * 整个过程是一个事务
	 * @param ids　选中的机构ＩＤ数组
	 */
	public Long deleteOrgs(String[] ids){
		Long lVari = checkOrgChildren(ids);
		if(lVari==null){
			Org ent = null;
			for(int i=0;i<ids.length;i++){
				ent = (Org)dao.get(Org.class, new Long(ids[i]));
			}
			dao.deleteByIds(Org.class, ids);
			return new Long(0);
		}else{
			return lVari;
		}

	}
	
	public String parentOrgName(Long orgId)
	{  
		String parentOrgName = null;
		List<Org>  org = dao.findByProperty(Org.class, "orgId", orgId);
		if (null != org && org.size()>0)
		{
			parentOrgName = org.get(0).getName();
		}
		return parentOrgName;
	}
	
	
	/**
	 * @描述: 判断此机构是否有关联信息不能删除
	 * @作者： WXJ
	 * @param ids
	 * @return
	 */
	public Long checkOrgChildren(String[] ids){
		List list = null;
		Org ent = null;
		for(int i=0;i<ids.length;i++){
			
			ent = (Org)dao.get(Org.class, new Long(ids[i]));
			if(ent!=null){

				//判断子机构
				list = dao.findByProperty(Org.class, "PId", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
				//判断用户
				list = dao.findByProperty(Users.class, "orgId", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
				
				//判断角色
				
				//判断权限
				list = dao.findByHQL("from ObjPermMap where type='org' and personId=?", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
			
			}
		}
		
		return null;
	}
	
	/**
	 * 
	* @Title: getOrgTreeData 
	* @Description: 组织机构（一级单位）管理
	* @author WXJ
	* @date 2013-5-6 下午01:50:43 
	* @param @param orgid
	* @param @param ctx
	* @param @param type
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getOrgTreeData(String orgid, String ctx, String type) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");

			//Org org = (Org) dao.findById(Org.class, 2L);
			Org org = (Org) dao.findById(Org.class, 1L);
			Element el = root.addElement("item");
			el.addAttribute("text", org.getName().trim());
			el.addAttribute("id", org.getOrgId() + "");
			el.addAttribute("open", "1");
			el.addAttribute("child", "1");
			Element elx = el.addElement("userdata");
			elx.addAttribute("name", "url");
			elx.addText(ctx + "/app/per/orgList.act?orgId=" + org.getOrgId()+"&levelNum="+(new Long(org.getLevelNum().trim())+1));
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",orgid);
			List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid));
//			if(list.size()<=200){
				for (Org org : list) {
		            Element el=root.addElement("item");
		            el.addAttribute("text", org.getName());
		            el.addAttribute("id", org.getOrgId()+"");
		            el.addAttribute("child", orgid);
		            Element elx = el.addElement("userdata");
		            elx.addAttribute("name", "url");
		            elx.addText(ctx+"/app/per/orgList.act?orgId="+ org.getOrgId()+"&levelNum="+(new Long(org.getLevelNum().trim())+1));
		        }
//			}
			return root.asXML();
		}
	}
	
	/**
	 * 
	* @Title: getOrgTreeUserListData 
	* @Description: 组织机构-人员信息查询
	* @author WXJ
	* @date 2013-5-6 下午01:51:57 
	* @param @param orgid
	* @param @param ctx
	* @param @param type
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getOrgTreeUserListData(String orgid, String ctx, String type) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");

			Org org = (Org) dao.findById(Org.class, 2L);
			Element el = root.addElement("item");
			el.addAttribute("text", org.getName().trim());
			el.addAttribute("id", org.getOrgId() + "-o");
			el.addAttribute("open", "1");
			el.addAttribute("child", "1");
			Element elx = el.addElement("userdata");
			elx.addAttribute("name", "url");
			elx.addText(ctx + "/app/personnel/queryPerInfo.act?orgId=" + org.getOrgId());
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",orgid);
			List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid.substring(0, orgid.indexOf("-o"))));
//			if(list.size()<=200){		
				for (Org org : list) {
		            Element el=root.addElement("item");   
		            el.addAttribute("text", org.getName());
		            el.addAttribute("id", org.getOrgId()+"-o");
		            el.addAttribute("child", "1");		
		            Element elx = el.addElement("userdata");
		            elx.addAttribute("name", "url");            
		            elx.addText(ctx+"/app/personnel/queryPerInfo.act?orgId="+ org.getOrgId());
		        }
//			}			
			List<Users> userList = dao.findByHQL("select t from Users t where t.orgId=? order by orderNum", new Long(orgid.substring(0, orgid.indexOf("-o"))));
//			if(userList.size()<=200){		
				for (Users user : userList) {
		            Element el=root.addElement("item");   
		            el.addAttribute("text", user.getDisplayName());
		            el.addAttribute("id", user.getUserid()+"-u");
		            el.addAttribute("im0", "user.gif");		
		            el.addAttribute("im1", "user.gif");		
		            el.addAttribute("im2", "user.gif");		
		            	            		            
		        }
//			}
			return root.asXML();
		}
	}
	
	public String getOrgTreeUserCheckinData(String orgid, String ctx, String type,Integer telType) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");
			
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<Org> list = this.findListBySql("PersonnelSQL.getOrgByManager", pMap);
			
			for (Org org : list) {	            
	            Element el = root.addElement("item");
				el.addAttribute("text", org.getName().trim());
				el.addAttribute("id", org.getOrgId() + "-o");
				el.addAttribute("open", "1");
				el.addAttribute("child", "1");
				Element elx = el.addElement("userdata");
				elx.addAttribute("name", "url");
				elx.addText(ctx+"/app/personnel/orguser/orgIndex.act?orgId="+ org.getOrgId());		
	        }
			
			return doc.asXML();			
			
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",orgid);
			List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid.substring(0, orgid.indexOf("-o"))));
			if(list.size()<=200){		
				for (Org org : list) {
		            Element el=root.addElement("item");   
		            el.addAttribute("text", org.getName());
		            el.addAttribute("id", org.getOrgId()+"-o");
		            el.addAttribute("child", "1");		
		            Element elx = el.addElement("userdata");
		            elx.addAttribute("name", "url");            
		            elx.addText(ctx+"/app/personnel/orguser/orgIndex.act?orgId="+ org.getOrgId());
		        }
			}			
			
			String sql = "";
			sql = "select u.userid,u.display_name,u.org_id, exp.tel_num, exp.uep_fax, exp.web_fax ,u.order_num from  users u , njhw_users_exp exp where u.userid = exp.userid  and u.org_id = ? order by u.order_num asc";
			List<Object[]> objList = dao.getSession().createSQLQuery(sql)
					.addScalar("userid", Hibernate.LONG)
					.addScalar("display_name",Hibernate.STRING)
					.addScalar("org_id", Hibernate.LONG)
					.addScalar("tel_num", Hibernate.STRING)
					.addScalar("uep_fax", Hibernate.STRING)
					.addScalar("web_fax", Hibernate.STRING)
					.setLong(0, new Long(orgid.substring(0, orgid.indexOf("-o"))))
					.list();
				
//			if(objList.size()<=200){		
				for (Object[] obj : objList) {
//					Users  user = (Users)obj[0];
//					NjhwUsersExp  exp = (NjhwUsersExp)obj[1];
//					TcIpTel  ttt = (TcIpTel)obj[2];
					
					Long userId =obj[0]==null?null: Long.parseLong(obj[0].toString());
					String displayName = obj[1]==null?null:String.valueOf(obj[1]);
					Long orgId = obj[2]==null?null:Long.parseLong(obj[2].toString());
					String telNum = obj[3]==null?null:String.valueOf(obj[4]);
					String uepFax = obj[4]==null?null:String.valueOf(obj[4]);
					String webFax = obj[5]==null?null:String.valueOf(obj[4]);
		            Element el=root.addElement("item");   
		            if(telType!=null&&telType!=0){
		            		if(telType.equals(1)){
		            			if(telNum!=null){
		            				el.addAttribute("text", displayName+"[已分配]");
		            			}else{
		            				el.addAttribute("text", displayName+"[未分配]");
		            			}
		            		}else if(telType.equals(2)){
		            			if(uepFax!=null){
		            				el.addAttribute("text", displayName+"[已分配]");
		            			}else{
		            				el.addAttribute("text", displayName+"[未分配]");
		            			}
		            		}else if(telType.equals(3)){
		            			if(webFax!=null){
		            				el.addAttribute("text", displayName+"[已分配]");
		            			}else{
		            				el.addAttribute("text", displayName+"[未分配]");
		            			}
		            		}else{
		            			el.addAttribute("text", displayName+"[未分配]");
		            		}
		            
		            }else{
		            	el.addAttribute("text", displayName);
		            }
		            el.addAttribute("id", userId+"-u");
		            el.addAttribute("im0", "user.gif");		
		            el.addAttribute("im1", "user.gif");		
		            el.addAttribute("im2", "user.gif");		
		            
		            Element elx = el.addElement("userdata");
		            elx.addAttribute("name", "url");            
		            elx.addText(ctx+"/app/per/inputRegister.act?orgId="+orgId+"&userId="+ userId);	            
		            	            		            
		        }
//			}
			return root.asXML();
		}
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeData 
	* @Description: 弹出页面机构及用户树checkbox
	* @author WXJ
	* @date 2013-5-6 上午11:20:40 
	* @param @param orgid
	* @param @param ctx
	* @param @param roomid
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getOrgUserTreeData(String orgid, String ctx, String roomid, String dtype) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getOrgUserTreeDoc(orgid,roomid,root,dtype);
		return doc.asXML();		
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeDoc 
	* @Description: 弹出页面机构及用户树checkbox
	* @author WXJ
	* @date 2013-5-5 上午11:19:47 
	* @param @param orgid
	* @param @param roomid
	* @param @param rootElement    
	* @return void 
	* @throws
	 */
	public void getOrgUserTreeDoc(String orgid,String roomid,Element rootElement, String dtype){	
		if (orgid==null) {
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<Org> list = this.findListBySql("PersonnelSQL.getOrgByManager", pMap);
		
			for (Org org : list) {
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"-o");
	            el.addAttribute("open", "1");
	            
	            getOrgUserTreeDoc(org.getOrgId().toString(),roomid,el,dtype);
	        }
			
		} else {			
			List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid));
					
			for (Org org : list) {
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"-o");
	            getOrgUserTreeDoc(org.getOrgId().toString(),roomid,el,dtype);
	        }
		
			
            if ("room".equals(dtype)){
            	List<Users> userList = dao.findByHQL("select t from Users t where t.orgId=? order by orderNum", new Long(orgid));
    				
				for (Users user : userList) {
					
		            List ueList = dao.findByHQL("select t from NjhwUsersExp t where t.userid=? ", user.getUserid());							            
		            if(ueList!=null && ueList.size()>0){
		            	NjhwUsersExp uep = (NjhwUsersExp)ueList.get(0);
		            	if (uep.getRoomId()==null ||(uep.getRoomId()!=null && roomid.equals(uep.getRoomId().toString()))){
		            		
		            		Element el=rootElement.addElement("item");   
				            el.addAttribute("text", user.getDisplayName());
				            el.addAttribute("id", user.getUserid()+"-u");
				            el.addAttribute("im0", "user.gif");		
				            el.addAttribute("im1", "user.gif");		
				            el.addAttribute("im2", "user.gif");	
				            
				            if (uep.getRoomId()!=null && roomid.equals(uep.getRoomId().toString())){
				            	el.addAttribute("checked", "1");
				            }
				            
		            	}	   
		            }
		            		            
				}
			}
            
			if ("lock".equals(dtype)){
				List<Users> userList = dao.findByHQL("select t from Users t where t.orgId=? order by orderNum", new Long(orgid));
				
				for (Users user : userList) {
					Element el=rootElement.addElement("item");   
		            el.addAttribute("text", user.getDisplayName());
		            el.addAttribute("id", user.getUserid()+"-u");
		            el.addAttribute("im0", "user.gif");		
		            el.addAttribute("im1", "user.gif");		
		            el.addAttribute("im2", "user.gif");	
			            
					//选中
					//得到房间的全部门锁
					List<Map> resList = unitAdminManager.getRoomRes(Objtank.EXT_RES_TYPE_3,roomid);
					for (Map mp : resList) {
						 List oList = dao.findByHQL("select t from ObjPermMap t where t.type='user' and t.personId=? and t.nodeId=?", user.getUserid(), new Long(mp.get("NODE_ID").toString()));							            
			            if(oList!=null && oList.size()>0){
			            	 el.addAttribute("checked", "1");
			            	 break;
			            }
					}
				}		            
			}	        	       	
        }
	}
	
	
	/**
	 * 
	* @Title: getMesOrgUserTreeData 
	* @Description: 弹出页面机构及用户树checkbox:发送消息
	* @author WXJ
	* @date 2013-5-20 上午11:20:40 
	* @param @param orgid
	* @param @param ctx
	* @param @param roomid
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getMesOrgUserTreeData(String orgid, String ids, String ctx, String type) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getMesOrgUserTreeDoc(orgid, ids,root,type);
		return doc.asXML();		
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeDoc 
	* @Description: 弹出页面机构及用户树checkbox:发送消息
	* @author WXJ
	* @date 2013-5-20 上午11:19:47 
	* @param @param orgid
	* @param @param roomid
	* @param @param rootElement    
	* @return void 
	* @throws
	 */
	public void getMesOrgUserTreeDoc(String orgid, String ids,Element rootElement, String type){	
	   try {
		
		if ("init".equals(type)) {		
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<HashMap> list = this.findListBySql("PersonnelSQL.getTopOrgIdByUserId", pMap);
			if(list.size()>0){
				HashMap map = list.get(0);
				Org org = (Org)this.findById(Org.class,Long.valueOf(map.get("TOP_ORG_ID").toString()));
			
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"-o");
	            el.addAttribute("open", "1");
	            
	            getMesOrgUserTreeDoc(org.getOrgId().toString(), ids, el,"open");
			}
		} else {			
			List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid));
					
			for (Org org : list) {
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"-o");
	            getMesOrgUserTreeDoc(org.getOrgId().toString(), ids, el,type);
	        }
		      
	    	List<Users> userList = dao.findByHQL("select t from Users t where t.orgId=? order by orderNum", new Long(orgid));
	    	String[] idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
			for (Users user : userList) {	            		
        		Element el=rootElement.addElement("item");   
	            el.addAttribute("text", user.getDisplayName());
	            el.addAttribute("id", user.getUserid()+"-u");
	            el.addAttribute("im0", "user.gif");		
	            el.addAttribute("im1", "user.gif");		
	            el.addAttribute("im2", "user.gif");
	            if(idsArray != null && idsArray.length > 0) {
	            	for (String strID : idsArray) {
	            		if (user.getUserid().toString().equals(strID)) {
		            		el.addAttribute("checked", "1");
		            		break;
		            	}
					}
	            }
			}
        }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	* @Title: getOrgTreeSelectData 
	* @Description: 一级页面机构树checkbox
	* @author WXJ
	* @date 2013-5-6 上午11:19:11 
	* @param @param orgid
	* @param @param ctx
	* @param @param roomid
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getOrgTreeSelectData(String orgid, String ctx, String roomid) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getOrgTreeSelectDoc(orgid,roomid,root);
		return doc.asXML();		
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeDoc 
	* @Description: 一级页面机构树checkbox
	* @author WXJ
	* @date 2013-5-6 上午11:18:15 
	* @param @param orgid
	* @param @param roomid
	* @param @param rootElement    
	* @return void 
	* @throws
	 */
	public void getOrgTreeSelectDoc(String orgid,String roomid,Element rootElement){		
		if (orgid==null) {
			Map pMap = new HashMap();
			pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List<Org> list = this.findListBySql("PersonnelSQL.getOrgByManager", pMap);
			
			for (Org org : list) {
	            Element el=rootElement.addElement("item");   
	            el.addAttribute("text", org.getName());
	            el.addAttribute("id", org.getOrgId()+"");
	            el.addAttribute("open", "1");
	            
	            getOrgTreeSelectDoc(org.getOrgId().toString(),roomid,el);
	        }
			
		} else {			
			List<Org> list =   dao.findByHQL("select t from Org t where t.PId=? order by orderNum", new Long(orgid));
//			if(list.size()<=200){		
				for (Org org : list) {
		            Element el=rootElement.addElement("item");   
		            el.addAttribute("text", org.getName());
		            el.addAttribute("id", org.getOrgId()+"");
		            getOrgTreeSelectDoc(org.getOrgId().toString(),roomid,el);
		        }
//			}
        	       	
        }
	}	

	/**
	 * 检查纳税人识别号码是否唯一.
	 *
	 * @return taxno在数据库中唯一或等于oldtaxno时返回true.
	 */
	public boolean isTaxnoUnique(String newtaxno, String oldtaxno) {
		return dao.isPropertyUnique(Org.class,"taxno", newtaxno, oldtaxno);
	}
	/**
	 * 
	 * @描述: TODO 修改机构信息--邮箱，企业名，联系人，等几项。
	 * @param org
	 */
	public void updateOrg(Org org){
		if(org != null){
			dao.saveOrUpdate(org);
		}
	}
	
	public Boolean deleteUser(long userid,String loginid,DoorControlToAppService doorControlToAppService){
		
		boolean res = true;
		try{
			String cardId = null;
			List list = dao.findByHQL("select t from NjhwTscard t  where t.userId = ?", userid);
			HashMap<String, String>  pMap = new HashMap<String, String>();
			if(null != list  && list.size()>0)
			{
			  	cardId = ((NjhwTscard)list.get(0)).getCardId();
			  	pMap.put("cardId",cardId);
			}
			delplateNum(userid);
			List uepList = dao.findByProperty(NjhwUsersExp.class, "userid", userid);
			
			if (uepList != null && uepList.size() > 0) {
				NjhwUsersExp uep = (NjhwUsersExp) uepList.get(0);
				if ("2".equals(uep.getCardType())) {
					cardId = uep.getTmpCard();
				}
			}
			
			if(null != cardId && !StringUtil.isBlank(cardId))
			{
				List<Map> listRoom = this.findListBySql("PersonnelSQL.getRoomIdByCardId", pMap);
				if (null != listRoom  && listRoom.size() > 0)
				{   
					// 删除老卡权限
					for (int i = 0; i < listRoom.size(); i++)
					{
						doorControlToAppService.delDoorAuth(String.valueOf(userid),loginid, listRoom.get(i).get("ROOM_ID").toString(),cardId,true);
					}
				}
			}
			
			delExp(userid);
			delCards(userid);
			
			Users exp = new Users(); 
			List expList = dao.findByProperty(Users.class, "userid", userid);
			if(expList != null && expList.size()>=1){
				exp = (Users)expList.get(0);
			}
			if(exp!=null){
				dao.deleteById(Users.class, exp.getUserid());
			}
			
			res = true;
		}catch(Exception e){
			e.printStackTrace();
			res = false;
		}
	 return res;
	}
	
	private void delExp(long userid){
		NjhwUsersExp exp = null; 
		List expList = dao.findByProperty(NjhwUsersExp.class, "userid", userid);
		if(expList != null && expList.size()>=1){
			exp = (NjhwUsersExp)expList.get(0);
		
		}
		if(exp!=null){
		dao.deleteById(NjhwUsersExp.class, exp.getUepId());
		}
	}
   private void delplateNum(long userid){
	  
	   NjhwUsersPlatenum exp = new NjhwUsersPlatenum(); 
		List expList = dao.findByProperty(NjhwUsersPlatenum.class, "userid", userid);
		for(int i =0;i<expList.size();i++){
			exp = (NjhwUsersPlatenum)expList.get(i);
			dao.deleteById(NjhwUsersPlatenum.class, exp.getNupPn());
			
		}
		
   }
   private void delCards(long userid){
	   
	  // dao.deleteById(NjhwTscard.class, userid);
	   
	   NjhwTscard exp = null; 
		List expList = dao.findByProperty(NjhwTscard.class, "userId", userid);
		if(expList != null && expList.size()>=1){
			exp = (NjhwTscard)expList.get(0);	
		}
		if(exp!=null){
		dao.deleteById(NjhwTscard.class, exp.getCardId());
		}
   }
   
   
   /**
    * 组织上移
   * @title: upOrg 
   * @description: TODO
   * @author gxh
   * @param orgId
   * @return
   * @date 2013-5-12 下午09:32:54     
   * @throws
    */
	public int updateUpOrg(Long orgId) {
		int res = 0;
		Org exp =  new Org();
		Org getMin = new Org();
		Org exp2 = new Org();

		List expList = dao.findByProperty(Org.class, "orgId", orgId);
		if (expList != null && expList.size() >= 1) {
			exp = (Org) expList.get(0);
		}
		if (exp != null && null != exp.getOrderNum()) {
			long numId1 = exp.getOrderNum();
			getMin=getMin(exp.getLevelNum(),exp.getPId());
			
			
			if (exp.getOrderNum() > getMin.getOrderNum()) {
				List<Org> list = dao					   
				.findByHQL(
						"select t from Org t where t.levelNum = ? and t.orderNum < ? and t.PId = ? order by t.orderNum desc",
						exp.getLevelNum(), exp.getOrderNum() ,exp.getPId());
				if (list != null && list.size() >= 1) {
					exp2 = (Org) list.get(0);
				}
				if(exp2!=null){
					long numId2= exp2.getOrderNum();
					System.out.print(numId1);
					System.out.print(numId2);
					
					exp2.setOrderNum(numId1);					
					exp.setOrderNum(numId2);
					dao.update(exp);
				
					//dao.flush();    
					dao.update(exp2);
					//dao.flush();
					res = 0; 
				}
				
			}else{
				
				res= 2;//不能上移
			}

		}else{
			res = 1;//失败
			
		}
     return res;
	}
	/**
	 * 组织下移
	* @title: downOrg 
	* @description: TODO
	* @author gxh
	* @param orgId
	* @return
	* @date 2013-5-12 下午09:33:15     
	* @throws
	 */
	public int updateDownOrg(Long orgId) {
		int res = 0;
		Org exp =  new Org();
		Org expMax = new Org();
		Org exp2 = new Org();

		List expList = dao.findByProperty(Org.class, "orgId", orgId);
		if (expList != null && expList.size() >= 1) {
			exp = (Org) expList.get(0);
		}
		if (exp != null && null != exp.getOrderNum()) {
			long numId1 = exp.getOrderNum();
			expMax=getMax(exp.getLevelNum(),exp.getPId());
			
			
			if (exp.getOrderNum() < expMax.getOrderNum()) {
				List<Org> list = dao					   
				.findByHQL(
						"select t from Org t where t.levelNum = ? and t.orderNum > ? and t.PId = ? order by t.orderNum asc",
						exp.getLevelNum(), exp.getOrderNum() ,exp.getPId());
				if (list != null && list.size() >= 1) {
					exp2 = (Org) list.get(0);
				}
				if(exp2!=null){
					long numId2= exp2.getOrderNum();
					//System.out.print(numId1);
					//System.out.print(numId2);
					
					exp.setOrderNum(numId2);
					exp2.setOrderNum(numId1);					
					dao.update(exp2);
							
					//dao.flush();    
					
					dao.update(exp);
					//dao.flush();
					res = 0; 
				}
				
			}else{
				
				res= 2;//不能下移
			}

		}else{
			res = 1;//失败
			
		}
     return res;
	}
	
	
private Org getMax(String levelNum ,Long PId){
	Map pMap = new HashMap();
	pMap.put("PId", PId);
	pMap.put("levelNum", levelNum);
	
	Org o  =new Org();
	List<Org> list = this.findListBySql("PersonnelSQL.getMax", pMap);
	
	if (list != null && list.size() >= 1) {
		
	return o =list.get(0);
		
	}else{
		
		return null;
	}
}

private Org getMin(String levelNum ,Long PId){
	Map pMap = new HashMap();
	pMap.put("PId", PId);
	pMap.put("levelNum", levelNum);
	
	Org o  =new Org();
	List<Org> list = this.findListBySql("PersonnelSQL.getMin", pMap);
	
	if (list != null && list.size() >= 1) {
		
	return o =list.get(0);
		
	}else{
		
		return null;
	}
}
/**
 * 查找最小的用户排序
* @title: getMinUser 
* @description: TODO
* @author gxh
* @param orgId
* @return
* @date 2013-5-23 下午03:46:03     
* @throws
 */
private Users getMinUser(Long orgId){
	Map pMap = new HashMap();
	pMap.put("orgId", orgId);
		
	Users o  =new Users();
	List<Users> list = this.findListBySql("PersonnelSQL.getMinUser", pMap);
	
	if (list != null && list.size() >= 1) {
		
	return o =list.get(0);
		
	}else{
		
		return null;
	}
}

/**
 * 用户排序最大
* @title: getMax 
* @description: TODO
* @author gxh
* @param levelNum
* @param PId
* @return
* @date 2013-5-23 下午04:56:25     
* @throws
 */
private Users getMaxUser(Long orgId){
	Map pMap = new HashMap();
	pMap.put("orgId", orgId);
	
	
	Users o  = new Users();
	List<Users> list = this.findListBySql("PersonnelSQL.getMaxUser", pMap);
	
	if (list != null && list.size() >= 1) {
		
	return o =list.get(0);
		
	}else{
		
		return null;
	}
}
/**
 * 根据用户姓名模糊查找
* @title: findOrgUserId 
* @description: TODO
* @author gxh
* @param map
* @return
* @date 2013-5-14 下午10:20:28     
* @throws
 */
@SuppressWarnings("unchecked")
public List<Map> findUsers(String displayName,List orgIdList){
	Map<String,Object> vMap = new HashMap<String,Object>();
	vMap.put("displayName", displayName);
	
	vMap.put("orgIdList", orgIdList);
	List<Map> list = sqlDao.findList("PersonnelSQL.getUsers", vMap);
	if(list!=null&&list.size()>0){
		return list;
		
	}else{
		return null;
	}
}

	 
	/** 
	* @title: queryUsersLimit 
	* @description: 查询人员权限信息
	* @author cjw
	* @param page
	* @param displayName
	* @param orgIdList
	* @return
	* @date 2013-5-19 下午11:40:31     
	* @throws 
	*/ 
	public Page<HashMap<String,String>> queryUsersLimit(final Page<HashMap<String,String>> page,String displayName,List<Long> orgIdList) {
		Map<String,Object> vMap = new HashMap<String,Object>();
		vMap.put("displayName", displayName);
		vMap.put("orgIdList", orgIdList);
		return sqlDao.findPage(page, "PersonnelSQL.getUsersLimit", vMap);
	}

/**
 * 用户上移
* @title: upUsers 
* @description: TODO
* @author gxh
* @param userid
* @return
* @date 2013-5-23 下午04:04:29     
* @throws
 */
	public int updateUpUser(Long userid) {
		int res = 0;
		 Users exp =  new Users();
		 Users getMin = new Users();
		 Users exp2 = new Users();

		List expList = dao.findByProperty(Users.class, "userid", userid);
		if (expList != null && expList.size() >= 1) {
			exp = (Users) expList.get(0);
		}
		if (exp != null && null != exp.getOrderNum()) {
			int numId1 = exp.getOrderNum();
			getMin=getMinUser(exp.getOrgId());
			
			if (exp.getOrderNum() > getMin.getOrderNum()) {
				List<Users> list = dao					   
				.findByHQL(
						"select t from Users t where t.orgId = ? and t.orderNum < ?  order by t.orderNum desc",
						exp.getOrgId(), exp.getOrderNum());
				if (list != null && list.size() >= 1) {
					exp2 = (Users) list.get(0);
				}
				if(exp2!=null){
					Integer numId2= exp2.getOrderNum();
					System.out.print(numId1);
					System.out.print(numId2);
					
					exp2.setOrderNum(numId1);					
					exp.setOrderNum(numId2);
					dao.update(exp);
				
					//dao.flush();    
					dao.update(exp2);
					//dao.flush();
					res = 0; 
				}
				
			}else{
				
				res= 2;//不能上移
			}

		}else{
			res = 1;//失败
			
		}
     return res;
	}
	
	/**
	 * 
	* @title: downUsers 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-5-23 下午04:45:04     
	* @throws
	 */
	public int updateDownUser(Long userid){
		
		int res = 0;
		Users exp =  new Users();
		Users expMax = new Users();
		Users exp2 = new Users();

		List expList = dao.findByProperty(Users.class, "userid", userid);
		if (expList != null && expList.size() >= 1) {
			exp = (Users) expList.get(0);
		}
		if (exp != null && null != exp.getOrderNum()) {
			Integer numId1 = exp.getOrderNum();
			expMax=getMaxUser(exp.getOrgId());
			if (exp.getOrderNum() < expMax.getOrderNum()) {
				List<Users> list = dao					   
				.findByHQL(
						"select t from Users t where t.orgId = ? and t.orderNum > ? order by t.orderNum asc",
						exp.getOrgId(), exp.getOrderNum());
				if (list != null && list.size() >= 1) {
					exp2 = (Users) list.get(0);
				}
				if(exp2!=null){
					Integer numId2= exp2.getOrderNum();
					//System.out.print(numId1);
					//System.out.print(numId2);
					
					exp.setOrderNum(numId2);
					exp2.setOrderNum(numId1);					
					dao.update(exp2);
							
					//dao.flush();    
					
					dao.update(exp);
					//dao.flush();
					res = 0; 
				}
				
			}else{
				
				res= 2;//不能下移
			}

		}else{
			res = 1;//失败
			
		}
     return res;
		
	}

	/**
	 * 
	* @title: isAdmin 
	* @description: 判断此userid是否是单位管理员
	* @author nj
	* @param userId 
	* @return boolean
	* @date 2013-7-25
	* @throws
	 */
	public boolean isAdmin(long userId) {
		boolean isAdmin = true;
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		Map<String,Object> condtion = new HashMap<String,Object>();						
		condtion.put("orgId", orgId);
		condtion.put("userId", userId);
		List<Map> listMap = sqlDao.findList("PersonnelUnitSQL.isUnitAdminByUserid", condtion);
		if (null == listMap || listMap.size() == 0) {
			isAdmin= false;
		}
		return isAdmin;
	}
	
	
	
}
