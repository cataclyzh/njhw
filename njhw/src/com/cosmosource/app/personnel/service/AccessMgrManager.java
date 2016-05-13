package com.cosmosource.app.personnel.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.NjhwUsersAccess;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.transfer.serviceimpl.AccessAndGateService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
/** 
* @description: 门禁闸机权限管理
* @author hj
* @date 2013-08-16
*/
@SuppressWarnings("unchecked")
public class AccessMgrManager extends BaseManager {
	private AccessAndGateService accessAndGateService;
	
	public AccessAndGateService getAccessAndGateService() {
		return accessAndGateService;
	}

	public void setAccessAndGateService(AccessAndGateService accessAndGateService) {
		this.accessAndGateService = accessAndGateService;
	}

	public Page queryUserAccess(Page page, HashMap map) {
		page.setAutoCount(false);
		sqlDao.findPage(page, "PersonnelSQL.queryUserAccess", map);
		List<Map> cl = sqlDao.findList("PersonnelSQL.countUserAccess", map);
		page.setTotalCount(Long.parseLong(cl.get(0).get("COUNT").toString()));
		return page;
	}

	public void flush() {
		super.dao.flush();
	}
	
	public void saveUpdateAccess(NjhwUsersAccess entity) {
		super.dao.saveOrUpdate(entity);
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeData 
	* @Description: 弹出页面机构及用户树
	* @author HJ
	* @date 2013-8-19
	* @param @param orgid
	* @param @param ctx
	* @param @return    
	* @return String 
	* @throws
	 */
	public String getOrgUserTreeData(String orgid) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getOrgUserTreeDoc(orgid, root);
		return doc.asXML();		
	}
	
	/**
	 * 
	* @Title: getOrgUserTreeDoc 
	* @Description: 弹出页面机构及用户树
	* @author HJ
	* @date 2013-8-19
	* @param @param orgid
	* @param @param root
	* @param @param userid    
	* @return void 
	* @throws
	 */
	public void getOrgUserTreeDoc(String orgid,Element rootElement){	
		if (orgid==null) {
			String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
			Org org = (Org) dao.findById(Org.class, Long.parseLong(orgId));

	        Element el=rootElement.addElement("item");   
	        el.addAttribute("text", org.getName());
	        el.addAttribute("id", org.getOrgId()+"-o");
	        el.addAttribute("open", "1");
	            
	        getOrgUserTreeDoc(org.getOrgId().toString(), el);
			
		} else {
			List<Org> list = dao.findByHQL(
					"select t from Org t where t.PId=? order by orderNum",
					new Long(orgid));

			for (Org org : list) {
				Element el = rootElement.addElement("item");
				el.addAttribute("text", org.getName());
				el.addAttribute("id", org.getOrgId() + "-o");
				getOrgUserTreeDoc(org.getOrgId().toString(), el);
			}

			List<Users> userList = dao.findByHQL(
					"select t from Users t where t.orgId=? order by orderNum",
					new Long(orgid));

			for (Users user : userList) {
				Element el = rootElement.addElement("item");
				el.addAttribute("text", user.getDisplayName());
				el.addAttribute("id", user.getUserid() + "-u");
				el.addAttribute("im0", "user.gif");
				el.addAttribute("im1", "user.gif");
				el.addAttribute("im2", "user.gif");
			}
		}
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
	public String getGuardTreeData(String ids) {		
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("tree");
		root.addAttribute("id", "0");

		getGuardTreeDoc(ids, root);
		return doc.asXML();
	}
	
	public void getGuardTreeDoc(String ids,  Element root) {
		Element el=root.addElement("item");   
        el.addAttribute("text", "闸机");
        el.addAttribute("id", "guard");
        el.addAttribute("open", "1");
        
        Map map = new HashMap();
		map.put("type", "1");
		List<Map> gList = sqlDao.findList("PersonnelSQL.getGuardInfo", map);
		
		if (gList != null && gList.size() > 0) {
			String[] idsArray = null;
			if (!"all".equals(ids)) {
				idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
			}
			
			for (Map m : gList) {
				if (m.get("RES_BAK") != null && !"".equals(m.get("RES_BAK").toString())) {
					Element g = el.addElement("item");
		            g.addAttribute("text", m.get("RES_NAME") == null? "" : m.get("RES_NAME").toString());
		            g.addAttribute("id", m.get("RES_BAK") == null? "" : m.get("RES_BAK").toString());
		            if ("all".equals(ids)) {
		            	g.addAttribute("checked", "1");
		            } else {
			            if(idsArray != null && idsArray.length > 0) {
			            	 for (String strID : idsArray) {
			 	            	if (m.get("RES_BAK").toString().equals(strID)) {
			 	            		g.addAttribute("checked", "1");
			 	            		break;
			 	            	}
			 				}
			            }
		            }
				}
			}
		}	
	}
	
	/**
	* @Title: getObjTreeSelectData 
	* @Description: 一级页面资源树chechbox
	* @author HJ
	* @date 2013-8-20
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
			
			String[] idsArray = null;
			if (!"all".equals(ids)) {
				idsArray = (ids != "" && ids != null) ? ids.split(",") : null;
			}
			for (Objtank obj : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", obj.getTitle());
	            el.addAttribute("id", obj.getNodeId()+"_"+obj.getTitle()+"_"+obj.getExtResType());
	            if("1".equals(obj.getExtIsBottom())){
	            	if("S".equals(obj.getExtResType()))  el.addAttribute("open", "1");
	            }
				if ("all".equals(ids)) {
					el.addAttribute("checked", "1");
				} else {
					if (idsArray != null && idsArray.length > 0) {
						for (String strID : idsArray) {
							if (obj.getNodeId().toString().equals(strID)) {
								el.addAttribute("checked", "1");
								break;
							}
						}
					}
				}
	           
	            getObjTreeDoc(obj.getNodeId().toString(),  ids, el, type);
	        }
		}		
	}

	/**
	* @Title: getAccessInfo 
	* @Description: 获取门禁信息
	* @author HJ
	* @date 2013-8-20
	* @param @param id
	* @param @return    
	* @return String 
	* @throws
	*/
	public List getAccessGuardInfo(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		List list = sqlDao.findList("PersonnelSQL.queryUserAccessById", map);
		return list;
	}

	/**
	 * 
	* @title: 
	* @description: 批量注销选中的申请信息
	* @author HJ
	* @return
	* @throws Exception
	* @date 2013-8-20
	* @throws
	 */
	public String deleteAccess(String[] chk) {
		String failName = "";
		for (String s : chk) {
			String id = s.split("-")[0];
			String lv = s.split("-")[1];
			NjhwUsersAccess entity = (NjhwUsersAccess)this.findById(NjhwUsersAccess.class, Long.parseLong(id));
			if (Long.parseLong(lv) == entity.getLockVer().longValue()) {
				entity.setAppStatus("4");
				entity.setUpdateId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				entity.setUpdateDate(new Date());
				entity.setLockVer(entity.getLockVer() + 1);
				super.dao.saveOrUpdate(entity);
			} else {
				Users u = (Users)this.findById(Users.class, entity.getUserid());
				failName += u.getDisplayName() + ",";
			}
		}
		
		if (failName.endsWith(",")) {
			failName = StringUtil.chop(failName);
		}
		
		return failName;
	}
	
	/**
	 * 
	* @title: 
	* @description: 退回申请信息
	* @author HJ
	* @return
	* @throws Exception
	* @date 2013-8-21
	* @throws
	 */
	public String refuseAccessApply(String id, String lockVer, String refuseReason) {
		String failName = "";
		NjhwUsersAccess entity = (NjhwUsersAccess)this.findById(NjhwUsersAccess.class, Long.parseLong(id));
		if (Long.parseLong(lockVer) == entity.getLockVer().longValue()) {
			entity.setAppStatus("3");
			entity.setAppBak(refuseReason);
			entity.setUpdateId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
			entity.setUpdateDate(new Date());
			entity.setLockVer(entity.getLockVer() + 1);
			super.dao.saveOrUpdate(entity);
			super.dao.flush();
		} else {
			Users u = (Users)this.findById(Users.class, entity.getUserid());
			failName = u.getDisplayName();
		}
		return failName;
	}
	
	/**
	 * 
	* @title: 
	* @description: 批量退回申请信息
	* @author HJ
	* @return
	* @throws Exception
	* @date 2013-8-21
	* @throws
	 */
	public String refuseAccessApplyBatch(String[] chk, String refuseReason) {
		String failName = "";
		for (String s : chk) {
			String id = s.split("-")[0];
			String lv = s.split("-")[1];
			NjhwUsersAccess entity = (NjhwUsersAccess)this.findById(NjhwUsersAccess.class, Long.parseLong(id));
			if (Long.parseLong(lv) == entity.getLockVer().longValue()) {
				entity.setAppStatus("3");
				entity.setAppBak(refuseReason);
				entity.setUpdateId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				entity.setUpdateDate(new Date());
				entity.setLockVer(entity.getLockVer() + 1);
				super.dao.saveOrUpdate(entity);
			} else {
				Users u = (Users)this.findById(Users.class, entity.getUserid());
				failName += u.getDisplayName() + ",";
			}
		}
		super.dao.flush();
		return failName;
	}
	
	/**
	 * 
	* @title: 
	* @description: 同意申请信息
	* @author HJ
	* @return
	* @throws Exception
	* @date 2013-8-21
	* @throws
	 */
	public Map agreeAccessApply(String id, String lockVer) {
 		Map m = new HashMap();
		NjhwUsersAccess entity = (NjhwUsersAccess)this.findById(NjhwUsersAccess.class, Long.parseLong(id));
		
		//锁 Version lockVer   (423,9) (2984)
		if (Long.parseLong(lockVer) == entity.getLockVer().longValue()) {
			
			logger.info("entity.getUserid() ：" + entity.getUserid().toString() + "门禁权限串数组(门禁) ： "
						+ entity.getAuthIdsA() + "门禁权限串数组(闸机)：" + entity.getAuthIdsG()
					);
			String r = accessAndGateOpt(entity.getUserid(), entity.getAuthIdsA(), entity.getAuthIdsG());
			if (StringUtil.isNotBlank(r)) {
				entity.setAppStatus("5");
				entity.setUpdateId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				entity.setUpdateDate(new Date());
				entity.setLockVer(entity.getLockVer() + 1);
				entity.setAppBak(r);
				super.dao.saveOrUpdate(entity);
				Users u = (Users)this.findById(Users.class, entity.getUserid());
				m.put("name", u.getDisplayName());
				m.put("msg", r);
			} else {
				entity.setAppStatus("2");
				entity.setUpdateId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
				entity.setUpdateDate(new Date());
				entity.setLockVer(entity.getLockVer() + 1);
				super.dao.saveOrUpdate(entity);
			}
		} else {
			Users u = (Users)this.findById(Users.class, entity.getUserid());
			m.put("name", u.getDisplayName());
		}
		super.dao.flush();
		return m;
	}
	
	/**
	 * 
	* @title: 
	* @description: 批量同意申请信息
	* @author HJ
	* @return
	* @throws Exception
	* @date 2013-8-22
	* @throws
	 */
	public List<Map> agreeAccessApplyBatch(String[] chk) {
		List<Map> l = new ArrayList<Map>();
		for (String s : chk) {
			String id = s.split("-")[0];
			String lv = s.split("-")[1];
			NjhwUsersAccess entity = (NjhwUsersAccess)this.findById(NjhwUsersAccess.class, Long.parseLong(id));
			if (Long.parseLong(lv) == entity.getLockVer().longValue()) {
				String r = accessAndGateOpt(entity.getUserid(), entity.getAuthIdsA(), entity.getAuthIdsG());
				if (StringUtil.isNotBlank(r)) {
					entity.setAppStatus("5");
					entity.setUpdateId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
					entity.setUpdateDate(new Date());
					entity.setLockVer(entity.getLockVer() + 1);
					super.dao.saveOrUpdate(entity);
					Users u = (Users)this.findById(Users.class, entity.getUserid());
					Map m = new HashMap();
					m.put("name", u.getDisplayName());
					m.put("msg", r);
					l.add(m);
				} else {
					entity.setAppStatus("2");
					entity.setUpdateId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
					entity.setUpdateDate(new Date());
					entity.setLockVer(entity.getLockVer() + 1);
					super.dao.saveOrUpdate(entity);
				}
			} else {
				Users u = (Users)this.findById(Users.class, entity.getUserid());
				Map m = new HashMap();
				m.put("name", u.getDisplayName());
				l.add(m);
			}
		}
		super.dao.flush();
		return l;
	}
	
	/**
	 * @ClassName:AccessAndGateOperation
	 * @Description：闸机门禁添加、删除和更改授权
	 * @param: String userid
	 * @param: String denyFlg "1":启用闸机  "0":禁用闸机 其他：删除
	 * @Author：hj
	 * @Date:2013-8-21
	 */
	public String accessAndGateOpt(Long userid, String denyFlg) {
		return accessAndGateOpt(userid, "", "", denyFlg);
	}
	
	/**
	 * @ClassName:AccessAndGateOperation
	 * @Description：闸机门禁添加、删除和更改授权
	 * @param: String userid
	 * @param: String accessIds
	 * @param: String gateIds
	 * @Author：hj
	 * @Date:2013-8-21
	 */
	public String accessAndGateOpt(Long userid, String accessIds, String gateIds) {
		return accessAndGateOpt(userid, accessIds, gateIds, null);
	}
	
	/**
	 * @ClassName:AccessAndGateOperation
	 * @Description：闸机门禁添加、删除和更改授权
	 * @param: String userid
	 * @param: String accessIds
	 * @param: String gateIds
	 * @param: String denyFlg "1":启用闸机  "0":禁用闸机
	 * @Author：hj
	 * @Date:2013-8-21
	 */
	public String accessAndGateOpt(Long userid, String accessIds, String gateIds, String denyFlg) {
		String failMsg = "";
		
		Map map = new HashMap();
		map.put("userid", userid);
		List list = sqlDao.findList("PersonnelSQL.queryUserAccessInfo", map);
		
		if(list == null || list.size() == 0){
			return "";
		}
		
		Map m = (Map) list.get(0);
		Map<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("ORGNAME", m.get("SHORT_NAME") == null ? "" : m.get("SHORT_NAME").toString());
		userInfo.put("ACCESSID", m.get("ACCESS_ID") == null ? "" : m.get("ACCESS_ID").toString());
		String loginId = "";
		if (Struts2Util.getSession() != null && Struts2Util.getSession().getAttribute(Constants.USER_ID) != null) {
			loginId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		}
		userInfo.put("LOGINID", loginId);
		userInfo.put("DISPLAYNAME", m.get("DISPLAY_NAME") == null ? "" : m.get("DISPLAY_NAME").toString().trim());
		userInfo.put("SEX", m.get("UEP_SEX") == null ? "" : m.get("UEP_SEX").toString());
		userInfo.put("CARDID", m.get("CARD_ID") == null ? "" : m.get("CARD_ID").toString());
		userInfo.put("RESIDENTNO", m.get("RESIDENT_NO") == null ? "" : m.get("RESIDENT_NO").toString());
			
		//删除闸机,门禁权限
		if (StringUtil.isBlank(accessIds) && StringUtil.isBlank(gateIds) || "0".equals(denyFlg)) {
			failMsg = accessAndGateService.accessAndGateOperation(userInfo, "delete");
				
			//补充注册身份证
			String residentNo = userInfo.get("RESIDENTNO");
			if(residentNo != null && residentNo.trim().length() > 0){
				addResidentGateWay(userInfo, "delete");
			}
			
			if(!StringUtil.isBlank(failMsg)){
				return failMsg;
			}

			if ("0".equals(denyFlg) && m.get("ACCESS_ID") != null && StringUtil.isNotBlank(m.get("ACCESS_ID").toString())) {
				// update access_perm_map
				List<Map> l = new ArrayList();
				Map c = new HashMap();
				c.put("personId", m.get("ACCESS_ID").toString());
				c.put("denyFlg", denyFlg);
				l.add(c);
				sqlDao.batchUpdate("PersonnelSQL.updateAccessPermMap", l);
			} else {
				// delete access_perm_map
				try{
					List<Map> l = new ArrayList();
					Map c = new HashMap();
					c.put("personId", m.get("ACCESS_ID").toString());
					l.add(c);
					sqlDao.batchDelete("PersonnelSQL.deleteAccessPermMap", l);
					NjhwUsersExp uep = (NjhwUsersExp) super.dao.findByProperty(NjhwUsersExp.class, "userid", userid).get(0);
					uep.setAccessId("");
					super.dao.saveOrUpdate(uep);
				}catch(Exception e){
					logger.error("ERROR: delete access_perm_map", e);
				}
			}
		} else if (StringUtil.isBlank(userInfo.get("ACCESSID")) || "1".equals(denyFlg)) {
			if (!"1".equals(denyFlg)) {
				userInfo.put("ACCESSID", "xc" + userid);
			}
			failMsg = addOrUpdateAccessAndGate(userInfo, accessIds, gateIds, "add");
			
			if (StringUtil.isBlank(failMsg)) {
				NjhwUsersExp uep = (NjhwUsersExp) super.dao.findByProperty(NjhwUsersExp.class, "userid", userid).get(0);
				uep.setAccessId(userInfo.get("ACCESSID").toString());
				super.dao.saveOrUpdate(uep);
				if ("1".equals(denyFlg)) {
					// update access_perm_map
					List<Map> l = new ArrayList();
					Map c = new HashMap();
					c.put("personId", m.get("ACCESS_ID").toString());
					c.put("denyFlg", denyFlg);
					l.add(c);
					sqlDao.batchUpdate("PersonnelSQL.updateAccessPermMap", l);
				} else {
					// insert access_perm_map
					List<Map> lm = new ArrayList();
					if (StringUtil.isNotBlank(accessIds)) {
						String[] sl = accessIds.split(",");
						for (String ss : sl) {
							Map mm = new HashMap();
							mm.put("personId", "xc" + userid);
							mm.put("nodeId", ss);
							mm.put("permCode", "A");
							mm.put("denyFlg", "1");
							lm.add(mm);
						}
					}
					if (StringUtil.isNotBlank(gateIds)) {
						String[] sl = gateIds.split(",");
						for (String ss : sl) {
							Map mm = new HashMap();
							mm.put("personId", "xc" + userid);
							mm.put("nodeId", ss);
							mm.put("permCode", "G");
							mm.put("denyFlg", "1");
							lm.add(mm);
						}
					}
					sqlDao.batchInsert("PersonnelSQL.insertAccessPermMap", lm);
				}
			}
		} else if (StringUtil.isNotBlank(userInfo.get("ACCESSID"))) {
			failMsg = addOrUpdateAccessAndGate(userInfo, accessIds,
					gateIds, "modify");
			// delete access_perm_map
			List<Map> l = new ArrayList();
			Map c = new HashMap();
			c.put("personId", userInfo.get("ACCESSID"));
			l.add(c);
			sqlDao.batchDelete("PersonnelSQL.deleteAccessPermMap", l);
			// insert access_perm_map
			List<Map> lm = new ArrayList();
			if (StringUtil.isNotBlank(accessIds)) {
				String[] sl = accessIds.split(",");
				for (String ss : sl) {
					Map mm = new HashMap();
					mm.put("personId", userInfo.get("ACCESSID"));
					mm.put("nodeId", ss);
					mm.put("permCode", "A");
					mm.put("denyFlg", "1");
					lm.add(mm);
				}
			}
			if (StringUtil.isNotBlank(gateIds)) {
				String[] sl = gateIds.split(",");
				for (String ss : sl) {
					Map mm = new HashMap();
					mm.put("personId", userInfo.get("ACCESSID"));
					mm.put("nodeId", ss);
					mm.put("permCode", "G");
					mm.put("denyFlg", "1");
					lm.add(mm);
				}
			}
			sqlDao.batchInsert("PersonnelSQL.insertAccessPermMap", lm);
		}
		
		return failMsg;
	}
	
	/**
	 * @ClassName:addAccessAndGate
	 * @Description：闸机门禁添加授权
	 * @Author：hj
	 * @Date:2013-8-21
	 */
	public String addOrUpdateAccessAndGate(Map<String, String> userInfo, String accessIds, String gateIds, String opt) {
		if ("all".equals(accessIds) && "all".equals(gateIds)) {
			userInfo.put("RIGHTLEVEL", "5");
		} else {
			String right = "";
			if ("all".equals(accessIds)) {
				Map map = new HashMap();
				map.put("equipType", "2");
				List list = sqlDao.findList("PersonnelSQL.queryEquipRight", map);
				if (list != null && list.size() > 0) {
					Map m = (Map) list.get(0);
					CLOB c = (CLOB) m.get("RIGHT_IDS");
					try {
						if(c != null){
							right = c.getSubString(1, (int) c.length());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else if (StringUtil.isNotBlank(accessIds)) {
				Map map = new HashMap();
				map.put("resBak", accessIds);
				List list = sqlDao.findList("PersonnelSQL.queryEquipRight", map);
				if (list != null && list.size() > 0) {
					Map m = (Map) list.get(0);
					CLOB c = (CLOB) m.get("RIGHT_IDS");
					try {
						if(c != null){
							right = c.getSubString(1, (int) c.length());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if ("all".equals(gateIds)) {
				Map map = new HashMap();
				map.put("equipType", "1");
				List list = sqlDao.findList("PersonnelSQL.queryEquipRight", map);
				if (list != null && list.size() > 0) {
					Map m = (Map) list.get(0);
					CLOB c = (CLOB) m.get("RIGHT_IDS");
					String r = "";
					try {
						if(c != null){
							r = c.getSubString(1, (int) c.length());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (StringUtil.isNotBlank(right)) {
						right = right + "," + r;
					} else {
						right = r;
					}
				}
			} else if (StringUtil.isNotBlank(gateIds)) {
				Map map = new HashMap();
				map.put("resBak", gateIds);
				List list = sqlDao.findList("PersonnelSQL.queryEquipRight", map);
				if (list != null && list.size() > 0) {
					Map m = (Map) list.get(0);
					CLOB c = (CLOB) m.get("RIGHT_IDS");
					String r = "";
					try {
						if(c != null){
							r = c.getSubString(1, (int) c.length());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (StringUtil.isNotBlank(right)) {
						right = right + "," + r;
					} else {
						right = r;
					}
				}
			}
			userInfo.put("RIGHTLEVEL", right);
		}
		
		String result = accessAndGateService.accessAndGateOperation(userInfo, opt);
		
		//补充注册身份证
		String residentNo = userInfo.get("RESIDENTNO");
		if(residentNo != null && residentNo.trim().length() > 0){
			result = addResidentGateWay(userInfo, opt);
		}
		
		if(result.equals("找不到该卡片信息")){
			result = addResidentGateWay(userInfo, "add");
			if(residentNo != null && residentNo.trim().length() > 0){
				result = addResidentGateWay(userInfo, "add");
			}
		}
		return result;
	}

	private String addResidentGateWay(Map<String,String> userInfo, String type){
		String result = null;
		//组装闸机身份证注册信息
		Map<String,String> residentUserInfo = new HashMap<String,String>();
		residentUserInfo.putAll(userInfo);
		residentUserInfo.put("CARDID", userInfo.get("RESIDENTNO"));
		result = accessAndGateService.accessAndGateOperation(residentUserInfo, type);
		return result;
	}

	/**
	 * @ClassName:initUserAccessInfo
	 * @Description：初始化人员门禁闸机信息
	 * @Author：hj
	 * @Date:2013-8-22
	 */
	public Map<String, String> initUserAccessInfo(String userid) {
		Map<String, String> result = new HashMap<String, String>();
		Map map = new HashMap();
		map.put("userid", userid);
		List<Map> al = sqlDao.findList("PersonnelSQL.countApplyAccessInfo", map);
		if (al != null && al.size() > 0 && Long.parseLong(((Map)al.get(0)).get("COUNT").toString()) > 0) {
			result.put("error", "repeat");
			return result;
		}
		
		List<Map> list = sqlDao.findList("PersonnelSQL.getAccessId", map);
		
		if (list == null || list.size() == 0) {
			List l = sqlDao.findList("PersonnelSQL.countUnnormalCard", map);
			result.put("isNone", "true");
			if (l != null && l.size() > 0 && Long.parseLong(((Map)l.get(0)).get("COUNT").toString()) > 0) {
				result.put("error", "error");
			} else {
				result.put("opt", "1");
				
				List<NjhwUsersExp> nueList = null;
				NjhwUsersExp uep = null;
				try {
					nueList = super.dao.findByProperty(NjhwUsersExp.class, "userid", Long.parseLong(userid));
					
					if (!nueList.isEmpty()) {
						uep = nueList.get(0);
						if (!"04".equals(uep.getUepType())) {
							result.put("idsA", "all");
							result.put("access", "全部");
							result.put("idsG", "all");
							result.put("gate", "全部");
						}
					}
//					uep = (NjhwUsersExp) super.dao.findByProperty(NjhwUsersExp.class, "userid", Long.parseLong(userid)).get(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			String idsA = "";
			String idsG = "";
			for (Map m : list) {
				String type = m.get("TYPE") == null ? "" : m.get("TYPE").toString();
				String nodeId = m.get("NODE_ID") == null ? "" : m.get("NODE_ID").toString();
				if ("A".equals(type)) {
					idsA += nodeId + ",";
				} else if ("G".equals(type)) {
					idsG += nodeId + ",";
				}
			}
			
			if (StringUtil.endsWith(idsA, ",")) {
				idsA = StringUtil.chop(idsA);
			}
			
			if ("all".equals(idsA)) {
				result.put("idsA", "all");
				result.put("access", "全部");
			} else if (StringUtil.isNotBlank(idsA)) {
				Map m = new HashMap();
				m.put("ids", idsA);
				List l = sqlDao.findList("PersonnelSQL.getAccessInfo", m);
				if (l != null && l.size() > 0) {
					CLOB b = (CLOB)((Map)l.get(0)).get("ACCESS_IDS");
					String access = "";
					try {
						access = b.getSubString(1, (int)b.length());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					result.put("idsA", idsA);
					result.put("access", access);
				}
			}
			
			if (StringUtil.endsWith(idsG, ",")) {
				idsG = StringUtil.chop(idsG);
			}
			
			if ("all".equals(idsG)) {
				result.put("idsG", "all");
				result.put("gate", "全部");
			} else if (StringUtil.isNotBlank(idsG)) {
				Map m = new HashMap();
				m.put("ids", idsG);
				List l = sqlDao.findList("PersonnelSQL.getGateInfo", m);
				if (l != null && l.size() > 0) {
					CLOB b = (CLOB)((Map)l.get(0)).get("GATE_IDS");
					String gate = "";
					try {
						gate = b.getSubString(1, (int)b.length());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					result.put("idsG", idsG);
					result.put("gate", gate);
				}
			}
		}
		
		return result;
	}
}
