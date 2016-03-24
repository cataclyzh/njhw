/**
* <p>文件名: OrgMgrManager.java</p>
* <p>描述：机构管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.FileUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcUser;

/**
 * @类描述:  机构的管理类
 * 调用通用DAO对机构的CRUD，及机构树数据的查询
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class OrgMgrManager extends BaseManager{
	private String company;//公司代码
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveOrg(TAcOrg entity){
		//设置是否是叶子节点
		TAcOrg parent =(TAcOrg)dao.get(TAcOrg.class, entity.getParentid());
		parent.setIsbottom("1");
		dao.update(parent);
		
		if(entity.getOrgid()==null){
			entity.setIsbottom("0");
		}
		
		//设置总部ＩＤ
		if(parent.getCompany()!=null&&!"Cosmosource".equals(parent.getCompany())){
			entity.setCompany(parent.getCompany());
		}
		dao.saveOrUpdate(entity);
		//如果是总部的话，默认增加总部功能菜单
		if("1".equals(entity.getOrgtype())){
			List list = dao.findByProperty(TAcMenu.class, "orgid", entity.getOrgid());
			if(list==null||list.isEmpty()){
				TAcMenu entMenu = new TAcMenu();
				entMenu.setMenucode(entity.getOrgcode()+"menu");
				entMenu.setMenuname(entity.getOrgname()+"功能菜单");
				entMenu.setMenulabel(entity.getOrgname()+"功能菜单");
				entMenu.setTreelevel(new Long(0));
				entMenu.setParentid(new Long(0));
				entMenu.setOrgid(entity.getOrgid());
				dao.save(entMenu);
			}
		}
	}
	/**
	 * 更改机构的层级关系字段
	 * @param entity
	 */
	public void updatetreelayer(TAcOrg entity){
		TAcOrg parent = (TAcOrg)dao.get(TAcOrg.class, entity.getParentid());
		entity.setTreelayer(parent.getTreelayer()+"."+entity.getOrgid());
		dao.update(entity);
	}
	
	/**
	 * 查询机构列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcOrg> queryOrgs(final Page<TAcOrg> page, final TAcOrg model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcOrg t ")
		.append("where t.parentid=? ");
		lPara.add(model.getParentid());
		if(StringUtil.isNotBlank(model.getOrgcode())){
			sbHql.append(" and t.orgcode like ? ");
			lPara.add("%"+model.getOrgcode()+"%");
		}
		if(StringUtil.isNotBlank(model.getOrgname())){
			sbHql.append("and t.orgname like ? ");
			lPara.add("%"+model.getOrgname()+"%");
		}
		if(StringUtil.isNotBlank(model.getTaxno())){
			sbHql.append("and t.taxno like ? ");
			lPara.add("%"+model.getTaxno()+"%");
		}
		if(StringUtil.isNotBlank(model.getTaxname())){
			sbHql.append("and t.taxname like ? ");
			lPara.add("%"+model.getTaxname()+"%");
		}
		sbHql.append(" order by t.orgid ");

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
			TAcOrg ent = null;
			for(int i=0;i<ids.length;i++){
				ent = (TAcOrg)dao.get(TAcOrg.class, new Long(ids[i]));
				if(ent!=null){
					//如果是总部
					if("1".equals(ent.getOrgtype())){
						dao.batchExecute("delete from TAcMenu t where t.orgid = ?", ent.getOrgid());
					}
				}

			}
			dao.deleteByIds(TAcOrg.class, ids);
			return new Long(0);
		}else{
			return lVari;
		}

	}
	/**
	 * @描述: 判断此机构是否有关联信息不能删除
	 * @作者： WXJ
	 * @param ids
	 * @return
	 */
	public Long checkOrgChildren(String[] ids){
		List list = null;
		TAcOrg ent = null;
		for(int i=0;i<ids.length;i++){
			
			ent = (TAcOrg)dao.get(TAcOrg.class, new Long(ids[i]));
			if(ent!=null){

				//判断子机构
				list = dao.findByProperty(TAcOrg.class, "parentid", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
				//判断用户
				list = dao.findByProperty(TAcUser.class, "orgid", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
				//判断角色
				list = dao.findByProperty(TAcRole.class, "orgid", new Long(ids[i]));
				if(list!=null&&list.size()>0){
					return new Long(ids[i]);
				}
				
				//如果是总部
				if("1".equals(ent.getOrgtype())){
					//判断权限
					list = dao.findByProperty(TAcMenu.class, "orgid", new Long(ids[i]));
					if(list!=null&&list.size()>1){
						return new Long(ids[i]);
					}
				}else{
					//判断权限
					list = dao.findByProperty(TAcMenu.class, "orgid", new Long(ids[i]));
					if(list!=null&&list.size()>0){
						return new Long(ids[i]);
					}
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
	public String getOrgTreeData(String orgid, String ctx, String type) {
		if ("init".equals(type)) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("tree");
			root.addAttribute("id", "0");

			TAcOrg org = (TAcOrg) dao.findById(TAcOrg.class, new Long(orgid));
			Element el = root.addElement("item");
			el.addAttribute("text", org.getOrgname());
			el.addAttribute("id", org.getOrgid() + "");
//			el.addAttribute("open", "1");
			el.addAttribute("child", "1");
			Element elx = el.addElement("userdata");
			elx.addAttribute("name", "url");
			elx.addText(ctx + "/common/orgMgr/list.act?nodeId=" + org.getOrgid()
					+ "&treelevel=" + (org.getTreelevel().longValue() + 1)
					+ "&parentOrgtype=" + org.getOrgtype() + "&company="
					+ org.getCompany());
			return doc.asXML();
		} else {
			Element root= DocumentHelper.createElement("tree"); 
			root.addAttribute("id",orgid);
			List<TAcOrg> list =   dao.findByHQL("select t from TAcOrg t where t.parentid="+orgid+" order by orgcode ");
			if(list.size()<=200){		
				for (TAcOrg org : list) {
		            Element el=root.addElement("item");   
		            el.addAttribute("text", org.getOrgname());
		            el.addAttribute("id", org.getOrgid()+"");
		            Element elx = el.addElement("userdata");
		            elx.addAttribute("name", "url");
		            if("1".equals(org.getIsbottom())){
		            	el.addAttribute("child", "1");
		            }else{
		            	el.addAttribute("child", "0");
		            }
		            elx.addText(ctx+"/common/orgMgr/list.act?nodeId="+ org.getOrgid()
		                	+"&treelevel="+(org.getTreelevel().longValue()+1)+"&parentOrgtype="+org.getOrgtype()+"&company="+org.getCompany());
		        }
			}
			return root.asXML();
		}
	}
	/**
	 * @描述: 查询DOC
	 * @param orgId
	 * @param root
	 */
	public void getOrgTreeDoc(Long orgId,Element root,String ctx){
		
		List<TAcOrg> list =   dao.findByHQL("select t from TAcOrg t where t.parentid="+orgId);
		if(list.size()<=100){		
			for (TAcOrg org : list) {
	            Element el=root.addElement("item");   
	            el.addAttribute("text", org.getOrgname());
	            el.addAttribute("id", org.getOrgid()+"");
	            Element elx = el.addElement("userdata");
	            elx.addAttribute("name", "url");
	            elx.addText(ctx+"/common/orgMgr/list.act?nodeId="+ org.getOrgid()
	                	+"&treelevel="+(org.getTreelevel().longValue()+1)+"&parentOrgtype="+org.getOrgtype()+"&company="+org.getCompany());
	            if("1".equals(org.getIsbottom())){
	            	getOrgTreeDoc(org.getOrgid(), el,ctx);
	            }
	        }
		}
	}
	/**
	 * 检查纳税人识别号码是否唯一.
	 *
	 * @return taxno在数据库中唯一或等于oldtaxno时返回true.
	 */
	public boolean isTaxnoUnique(String newtaxno, String oldtaxno) {
		return dao.isPropertyUnique(TAcOrg.class,"taxno", newtaxno, oldtaxno);
	}
	/**
	 * 
	 * @描述: TODO 修改机构信息--邮箱，企业名，联系人，等几项。
	 * @param org
	 */
	public void updateOrg(TAcOrg org){
		if(org != null){
			dao.saveOrUpdate(org);
		}
	}
	/**
	 * @描述:生成数据
	 * @作者：WXJ
	 * @日期：2011-02-15
	 * @param company
	 * @return
	 */
	public boolean genJsonFiles(String pCompany,String pType) {
		
		if(StringUtil.isBlank(pCompany)||StringUtil.isBlank(pType)){
			return false;
		}
		String jsonDir = pCompany.replace("admin", "")+".json.taxno";

		List list =  null;
		Map map = null;
		TAcOrg org = null;
		List lTaxno = null;
		try {
			//大区
			List listOrg = dao.findByHQL("select t from TAcOrg t where t.orgtype = '4' and t.company='"+pCompany+"' ");

			String path = PropertiesUtil.getConfigProperty("dir.root","common")+PropertiesUtil.getConfigProperty(jsonDir,"common");
			if(listOrg!=null){
				Long orgid = null;
				for(int j =0;j<listOrg.size();j++){
					org =  (TAcOrg)listOrg.get(j);
					orgid = org.getOrgid();
					list = dao.findByHQL("select t from TAcOrg t where t.parentid = ? and  (t.orgtype = '5' or t.orgtype = '6') ",orgid);
					map = new HashMap();
					lTaxno = new ArrayList();
					for(int i =0;i<list.size();i++){
						map = new HashMap();
						TAcOrg ee = (TAcOrg)list.get(i);
						if("1".equals(pType)){
							map.put("taxno", ee.getTaxno());
							map.put("taxname",  ee.getTaxname());
						}else if("2".equals(pType)){
							map.put("orgcode", ee.getOrgcode());
							map.put("taxname",  ee.getTaxname());
						}
						lTaxno.add(map);
					}
					
					FileUtil.createDirs(path, true);
					if("1".equals(pType)){
						Struts2Util.renderJsonFile(path+"taxno_"+orgid.longValue()+".json", lTaxno);
					}else if("2".equals(pType)){
						Struts2Util.renderJsonFile(path+"orgcode_"+orgid.longValue()+".json", lTaxno);
					}
				}
			}
			//总部
			listOrg = dao.findByHQL("select t from TAcOrg t where t.orgtype = '1' and t.company='"+pCompany+"' ");
			
			if(listOrg!=null){
				for(int j =0;j<listOrg.size();j++){
					org =  (TAcOrg)listOrg.get(j);
					list = dao.findByHQL("select t from TAcOrg t where (t.orgtype = '5' or t.orgtype = '6') and t.treelayer like ? ",org.getTreelayer()+"%");
					map = new HashMap();
					lTaxno = new ArrayList();
					for(int i =0;i<list.size();i++){
						map = new HashMap();
						TAcOrg ee = (TAcOrg)list.get(i);
						if("1".equals(pType)){
							map.put("taxno", ee.getTaxno());
							map.put("taxname",  ee.getTaxname());
						}else if("2".equals(pType)){
							map.put("orgcode", ee.getOrgcode());
							map.put("taxname",  ee.getTaxname());
						}
						lTaxno.add(map);
					}
					
					FileUtil.createDirs(path, true);
					
					if("1".equals(pType)){
						Struts2Util.renderJsonFile(path+"taxno_"+org.getOrgid().longValue()+".json", lTaxno);
					}else if("2".equals(pType)){
						Struts2Util.renderJsonFile(path+"orgcode_"+org.getOrgid().longValue()+".json", lTaxno);
					}
				}
			}
			//购方或是购销双方的
			
			listOrg = dao.findByHQL("select t from TAcOrg t where (t.orgtype = '5' or t.orgtype = '6') and t.company='"+pCompany+"' ");
			
			if(listOrg!=null){
				for(int j =0;j<listOrg.size();j++){
					lTaxno = new ArrayList();
					org =  (TAcOrg)listOrg.get(j);
					map = new HashMap();

					FileUtil.createDirs(path, true);
					if("1".equals(pType)){
						map.put("taxno", org.getTaxno());
						map.put("taxname",  org.getTaxname());
						lTaxno.add(map);
					}else if("2".equals(pType)){
						map.put("orgcode", org.getOrgcode());
						map.put("taxname",  org.getTaxname());
						lTaxno.add(map);
					}
					if("1".equals(pType)){
						Struts2Util.renderJsonFile(path+"taxno_"+org.getOrgid().longValue()+".json", lTaxno);
					}else if("2".equals(pType)){
						Struts2Util.renderJsonFile(path+"orgcode_"+org.getOrgid().longValue()+".json", lTaxno);
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @描述:根据供应商税号获取供应商编码
	 * @作者：fjy
	 * @日期：2012-03-31
	 * @param taxno
	 * @return
	 */
	public String getOrgCodeByTaxno(String taxno){
		List listOrg = dao.createSQLQuery("select t.orgcode from t_ac_org t where t.taxno='"+ taxno +"'").list();
		if(listOrg.size() == 0){
			return "";
		}else
			return listOrg.get(0) == null ? "":listOrg.get(0).toString();
	}
	
//	/**
//	 * @描述:生成数据
//	 * @作者：WXJ
//	 * @日期：2011-02-15
//	 * @param company
//	 * @return
//	 */
//	public void getGenJsonFiles(){
//		this.genJsonFiles(company);
//	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
