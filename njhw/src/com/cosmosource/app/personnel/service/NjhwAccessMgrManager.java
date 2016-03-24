package com.cosmosource.app.personnel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.NjhwUsersAccess;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
/** 
* @description: 门禁闸机权限管理
* @author hj
* @date 2013-08-16
*/
@SuppressWarnings("unchecked")
public class NjhwAccessMgrManager extends BaseManager {
	public Page queryUserAccess(Page page, HashMap map) {
		map.put("isNjhw", "true");
		page.setAutoCount(false);
		page.setResult(sqlDao.findList("PersonnelSQL.queryUserAccess", map));
		List<Map> cl = sqlDao.findList("PersonnelSQL.countUserAccess", map);
		page.setTotalCount(Long.parseLong(cl.get(0).get("COUNT").toString()));
		return page;
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
		List list = sqlDao.findList("PersonnelSQL.queryUserAccess", map);
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
}
