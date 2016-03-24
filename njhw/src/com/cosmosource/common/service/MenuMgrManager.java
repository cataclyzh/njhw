/**
* <p>文件名: MenuMgrManager.java</p>
* <p>描述：功能菜单管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午05:27:12 
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
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRoleright;

/**
 * @类描述:  功能菜单的管理类
 * 调用通用DAO对功能菜单的CRUD，及机构树数据的查询
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class MenuMgrManager extends BaseManager{
	
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveMenu(TAcMenu entity){
//		Long parentLevel = ((TAcMenu)dao.get(TAcMenu.class, entity.getParentid())).getTreelevel();
		TAcMenu parent =(TAcMenu)dao.get(TAcMenu.class, entity.getParentid());
		entity.setTreelevel(parent.getTreelevel()+1);
		parent.setIsbottom("1");
		dao.update(parent);
		if(entity.getMenuid()==null){
			entity.setIsbottom("0");
		}
		dao.saveOrUpdate(entity);
		
	}
	/**
	 * 查询功能菜单列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcMenu> queryMenus(final Page<TAcMenu> page, final TAcMenu model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcMenu t ")
		.append("where t.parentid=? ");
//		if(model.getOrgid()!=null){
//			sbHql.append(" and  t.orgid=?");
//			lPara.add(model.getOrgid());
//		}

		lPara.add(model.getParentid());
		if(StringUtil.isNotBlank(model.getMenucode())){
			sbHql.append(" and t.menucode like ? ");
			lPara.add("%"+model.getMenucode()+"%");
		}
		
		if(StringUtil.isNotBlank(model.getMenuname())){
			sbHql.append("and t.menuname like ? ");
			lPara.add("%"+model.getMenuname()+"%");
		}
		
		sbHql.append(" order by t.menuid ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	public Long deleteMenus(String[] ids){
		Long lVari = checkMenuRelate(ids);
		if(checkMenuRelate(ids)==null){
			dao.deleteByIds(TAcMenu.class, ids);
			return new Long(0);
		}else{
			return lVari;
		}
	}
	
	/**
	 * @描述: 判断此菜单是否有关联信息不能删除
	 * @作者： WXJ
	 * @param ids
	 * @return
	 */
	public Long checkMenuRelate(String[] ids){
		List list = null;
		TAcMenu ent = null;
		for(int i=0;i<ids.length;i++){
			
			ent = (TAcMenu)dao.get(TAcMenu.class, new Long(ids[i]));
			if(ent!=null){

				//判断子菜单
				list = dao.findByProperty(TAcMenu.class, "parentid", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
				//判断权限
				list = dao.findByProperty(TAcRoleright.class, "menuid", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
			
			}
		}
		
		return null;
	}

	/**
	 * @描述: 生成xml数据
	 * @param nodeId
	 * @param orgid
	 * @return
	 */
	public String getOrgTreeData(Long orgid,String ctx){
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
      elx.addText(ctx+"/common/menuMgr/menuSubFrame.act?nodeId="+org.getOrgid());  
	  
	  getOrgTreeDoc(orgid,el,ctx); 
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
            if(org.getParentid()==0){
            	el.addAttribute("open", "1");
            }
            Element elx = el.addElement("userdata");
            elx.addAttribute("name", "url");
            elx.addText(ctx+"/common/menuMgr/menuSubFrame.act?nodeId="+org.getOrgid());             
            if("1".equals(org.getIsbottom())){
            	getOrgTreeDoc(org.getOrgid(), el,ctx);
            }
        	       	
        }
	}
	/**
	 * @描述: 取菜单树的xml 数据
	 * @param nodeId
	 * @param orgid
	 * @return
	 */
	public String getMenuTreeData(Long nodeId,Long orgid,String ctx){
	  Document  doc = DocumentHelper.createDocument();
	  Element root=doc.addElement("tree");
	  root.addAttribute("id", "0"); 
//      Element el=root.addElement("item");   
//      el.addAttribute("text", "功能菜单");
//      el.addAttribute("id", "org");
//      Element elx = el.addElement("userdata");
//      elx.addAttribute("name", "url");
//      elx.addText("/common/menuMgr/list.act?nodeId=0"+"&amp;orgid="+orgid); 
	  
//      el.addAttribute("call", "1");

	  getMenuTreeDoc(orgid,new Long(0),root,ctx); 
      
	  return doc.asXML();
	}
	/**
	 * @描述: 查询DOC
	 * @param orgId
	 * @param rootElement
	 */
	public void getMenuTreeDoc(Long orgid,Long menuid,Element rootElement,String ctx){
		
		List<TAcMenu> list =   dao.findByHQL("select t from TAcMenu t where t.parentid="+menuid+" and t.orgid="+orgid);
		for (TAcMenu menu : list) {
            Element el=rootElement.addElement("item");   
            el.addAttribute("text", menu.getMenuname());
            el.addAttribute("id", menu.getMenuid()+"");
            if( menu.getTreelevel()==0){
            	el.addAttribute("open", "1");
            }
            
            
//            el.addAttribute("call", "1");
            Element elx = el.addElement("userdata");
            elx.addAttribute("name", "url");
            elx.addText(ctx+"/common/menuMgr/list.act?nodeId="+menu.getMenuid()+"&amp;orgid="+orgid); 
            if("1".equals(menu.getIsbottom())){
            	getMenuTreeDoc(orgid,menu.getMenuid(), el,ctx);
            }
        	       	
        }
	}

}
