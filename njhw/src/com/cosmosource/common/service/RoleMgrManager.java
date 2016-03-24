/**
* <p>文件名: RoleMgrManager.java</p>
* <p>描述：角色管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午05:21:23 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRole;

/**
 * @类描述:  角色的管理类
 * 调用通用DAO对角色的CRUD，及机构树数据的查询
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class RoleMgrManager extends BaseManager{
	
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveRole(TAcRole entity){
		dao.saveOrUpdate(entity);
		
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
      elx.addText(ctx+"/common/roleMgr/list.act?nodeId="+org.getOrgid());  

      getOrgTreeDoc(new Long(orgid),el,ctx);
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
            elx.addText(ctx+"/common/roleMgr/list.act?nodeId="+org.getOrgid());  
            if("1".equals(org.getIsbottom())){
            	getOrgTreeDoc(org.getOrgid(), el,ctx);
            }
        }
	}
//	/**
//	 * 初始化User的延迟加载关联roleList.
//	 */
//	public void initRole(TAcRole role) {
//		dao.initProxyObject(role.getUserList());
//	}
}
