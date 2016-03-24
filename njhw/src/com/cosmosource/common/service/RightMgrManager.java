/**
* <p>文件名: RightMgrManager.java</p>
* <p>描述：角色管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-9-1 下午02:29:21 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.dom4j.Element;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.NumberUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcRoleright;
import com.cosmosource.common.entity.TAcUser;
import com.cosmosource.common.entity.TAcUserrole;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

/**
 * @类描述:  角色的管理类
 * 调用通用DAO对角色的CRUD，及机构树数据的查询
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class RightMgrManager extends BaseManager{
	
	/**
	 * 保存角色权限信息
	 * @param entity
	 */
	public void saveRolerights(Long roleid,String ids){
		
		TAcRoleright entity = null;
		List list = null;
		Long menuid = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TAcRoleright t ")
			.append("where t.roleid=? and t.menuid=? ");
		dao.batchExecute("delete from TAcRoleright t where t.roleid=?", roleid);
		StringTokenizer st = new StringTokenizer(ids,",");
		while (st.hasMoreTokens()) { 
			menuid = NumberUtil.strToLong(st.nextToken());
			list = dao.findByHQL(sb.toString(),roleid,menuid);
			if(list==null||list.isEmpty()){
				entity = new TAcRoleright();
				entity.setMenuid(menuid);
				entity.setRoleid(roleid);
				dao.save(entity);
			}
		}
	}

	/**
	 * 保存用户信息到角色
	 * 
	 * @param roleid 角色ID
	 * @param ids	所选中的用户ID
	 */
	public void saveUsersToRole(Long roleid,String[] ids){
		
		TAcUserrole ent = null;
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcUserrole t ")
		.append("where t.roleid=? ")
		.append("and t.userid=? ");
		List lRes = null;
		for(int i = 0 ;i< ids.length ; i++){
			lRes = dao.findByHQL(sbHql.toString(), roleid, NumberUtil.strToLong(ids[i]));
			if(lRes==null||lRes.isEmpty()){
				ent = new TAcUserrole();
				ent.setRoleid(roleid);
				ent.setUserid(NumberUtil.strToLong(ids[i]));
				dao.save(ent);		
			}

			
		}
	}
	
	/**
	 * 从角色中删除用户
	 * 
	 * @param roleid 角色ID
	 * @param ids	所选中的用户ID
	 */
	public void deleteUsersFromRole(Long roleid,String[] ids){
		StringBuilder sbDelHql = new StringBuilder();
		sbDelHql.append("delete from TAcUserrole t ")
			.append("where t.roleid=? ")
			.append("and t.userid=? ");
		
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcUserrole t ")
			.append("where t.roleid=? ")
			.append("and t.userid=? ");
		List lRes = null;
		for(int i = 0 ;i< ids.length ; i++){
			lRes = dao.findByHQL(sbHql.toString(), roleid, NumberUtil.strToLong(ids[i]));
			if(lRes!=null&&!lRes.isEmpty()){
				dao.batchExecute(sbDelHql.toString(), roleid, NumberUtil.strToLong(ids[i]));
			}
		}
	}
	/**
	 * 查询用户列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcUser> queryUsers(final Page<TAcUser> page, final Map map) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select b,c.orgname from TAcUserrole a,TAcUser b, TAcOrg c  ")
		.append("where a.userid=b.userid and b.orgid=c.orgid and a.roleid=? ");
		lPara.add((Long)map.get("roleid"));
		if(StringUtil.isNotBlank((String)map.get("usercode"))){
			sbHql.append(" and b.usercode like ? ");
			lPara.add("%"+(String)map.get("usercode")+"%");
		}
		
		if(StringUtil.isNotBlank((String)map.get("username"))){
			sbHql.append("and b.username like ? ");
			lPara.add("%"+(String)map.get("username")+"%");
		}
		
		sbHql.append(" order by b.userid ");
		
		

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	/**
	 * 查询没有分配的用户列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcUser> queryNoRightUsers(final Page<TAcUser> page, final Map map) {
		
		return sqlDao.findPage(page, "CommonSQL.findUsers4Right", map);
	}
	
	/**
	 * 查询角色列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcRole> queryRoles(final Page<TAcRole> page, final TAcRole model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcRole t ")
		.append("where t.orgid=? ");
		lPara.add(model.getOrgid());
		if(StringUtil.isNotBlank(model.getRolecode())){
			sbHql.append(" and t.rolecode like ? ");
			lPara.add("%"+model.getRolecode()+"%");
		}
		
		if(StringUtil.isNotBlank(model.getRolename())){
			sbHql.append("and t.rolename like ? ");
			lPara.add("%"+model.getRolename()+"%");
		}
		
		sbHql.append(" order by t.roleid ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	public void deleteRoles(String[] ids){
		dao.deleteByIds(TAcRole.class, ids);
	}

	
	
	/**
	 * @描述: 生成xml数据
	 * @param nodeId
	 * @param orgid
	 * @return
	 */
	public String getOrgTreeData(String orgid,String ctx){
	  Document  doc = DocumentHelper.createDocument();
	  Element root=doc.addElement("tree");
	  root.addAttribute("id", "0"); 
	  	  
	  TAcOrg org = (TAcOrg)dao.findById(TAcOrg.class, new Long(orgid));
      Element el=root.addElement("item");   
      el.addAttribute("text", org.getOrgname());
      el.addAttribute("id", org.getOrgid()+"");
      el.addAttribute("open", "1");
      Element elx = el.addElement("userdata");
      elx.addAttribute("name", "url");
      elx.addText(ctx+"/common/rightMgr/roleList.act?nodeId="+ org.getOrgid()+"&company="+org.getCompany());  
      
	  
	  getOrgTreeDoc(new Long(orgid),el, ctx);
	  return doc.asXML();
	}
	/**
	 * @描述: 查询DOC
	 * @param orgId
	 * @param root
	 */
	public void getOrgTreeDoc(Long orgId,Element root,String ctx){
		
		List<TAcOrg> list =   dao.findByHQL("select t from TAcOrg t where t.parentid="+orgId +" and t.treelevel<2");
		for (TAcOrg org : list) {
            Element el=root.addElement("item");   
            el.addAttribute("text", org.getOrgname());
            el.addAttribute("id", org.getOrgid()+"");
            Element elx = el.addElement("userdata");
            elx.addAttribute("name", "url");
            elx.addText(ctx+"/common/rightMgr/roleList.act?nodeId="+ org.getOrgid()+"&company="+org.getCompany());  
            if("1".equals(org.getIsbottom())){
            	getOrgTreeDoc(org.getOrgid(), el,ctx);
            }
            
        	       	
        }
	}

	/**
	 * @描述: 生成xml数据
	 * @param nodeId
	 * @param orgid
	 * @return
	 */
	public String getMenuTreeData(String nodeId,Long orgid,Long roleid){
	  Document  doc = DocumentHelper.createDocument();
	  Element root=doc.addElement("tree");
	  root.addAttribute("id", "0"); 
//      Element el=root.addElement("item");   
//      el.addAttribute("text", "功能菜单");
//      el.addAttribute("id", "org");
//
//      el.addAttribute("call", "1");
	  getMenuTreeDoc(orgid,roleid,new Long(0),root); 
	  return doc.asXML();
	}
	/**
	 * @描述: 查询DOC
	 * @param orgId
	 * @param rootElement
	 */
	public void getMenuTreeDoc(Long orgid,Long roleid,Long menuid,Element rootElement){
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TAcRoleright t ")
		.append("where t.roleid=? and t.menuid=? ");
		//查询当前公司下的所有菜单
		List<TAcMenu> list =   dao.findByHQL("select a from TAcMenu a where a.parentid="+menuid +" and a.orgid="+orgid + " order by a.sortno");
		List lRights = null;
		for (TAcMenu menu : list) {
			//查询当前菜单在所选角色中有没有权限
			lRights = dao.findByHQL(sb.toString(),roleid,menu.getMenuid());
            Element el=rootElement.addElement("item");   
            el.addAttribute("text", menu.getMenuname());
            el.addAttribute("id", menu.getMenuid()+"");
            //打开第一层
            if(menu.getTreelevel()==0){
            	el.addAttribute("open", "1");
            }
            //有权限的选中
            if(lRights!=null&&lRights.size()>0&&"0".equals(menu.getIsbottom())){
            	 el.addAttribute("checked", "1");
            }
            
            Element elx = el.addElement("userdata");
            elx.addAttribute("name", "url");
            if("1".equals(menu.getIsbottom())){
            	getMenuTreeDoc(orgid,roleid,menu.getMenuid(), el);
            }
        	       	
        }
	}
	
	
}
