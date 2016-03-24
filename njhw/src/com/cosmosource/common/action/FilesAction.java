/**
* <p>文件名: FilesAction.java</p>
* <p>描述：文件处理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-12-14 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.TreeMap;

import javax.servlet.http.HttpSession;

//import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//import org.springframework.util.PropertiesPersister;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.dao.ComDao;
import com.cosmosource.base.service.SpringContextHolder;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcRole;

/**
* @类描述: 文件处理Action
* @作者： WXJ
*/
@SuppressWarnings({"rawtypes" })
public class FilesAction extends BaseAction{

	private static final long serialVersionUID = -7130663132286599479L;
	/**
	 * 取得数据以json的形式传送到页面
	 * @作者：WXJ
	 * @日期：2010-12-06
	 * @return 
	 * @throws Exception
	 */	
	public String getTaxnoJson()  {
		String type = getParameter("type");
		try {
//			String orgtype = (String)getSessionAttribute(Constants.ORG_TYPE);
			String company = ((String)getSessionAttribute(Constants.COMPANY)).replace("admin", "");
			String jsonDir = "";
			
			if(StringUtil.isBlank(type)){
				jsonDir = PropertiesUtil.getConfigProperty("dir.root","common")+PropertiesUtil.getConfigProperty(company+".json.taxno","common")+"taxno_"+getSessionAttribute(Constants.COMPANY_ID)+".json";
			}else{
				if("0".equals(type)){//8 代表销方 如果是销方取compan_id命名的json
					jsonDir = PropertiesUtil.getConfigProperty("dir.root","common")+PropertiesUtil.getConfigProperty(company+".json.taxno","common")+"taxno_"+getSessionAttribute(Constants.ORG_ID)+".json";
				}else if("1".equals(type)){
					jsonDir = PropertiesUtil.getConfigProperty("dir.root","common")+PropertiesUtil.getConfigProperty(company+".json.taxno","common")+"taxno_"+getSessionAttribute(Constants.COMPANY_ID)+".json";
				}else if("2".equals(type)){
					jsonDir = PropertiesUtil.getConfigProperty("dir.root","common")+PropertiesUtil.getConfigProperty(company+".json.taxno","common")+"orgcode_"+getSessionAttribute(Constants.ORG_ID)+".json";
				}else if("3".equals(type)){
					jsonDir = PropertiesUtil.getConfigProperty("dir.root","common")+PropertiesUtil.getConfigProperty(company+".json.taxno","common")+"orgcode_"+getSessionAttribute(Constants.COMPANY_ID)+".json";
				}
			}
			
			Struts2Util.renderFile(jsonDir, "taxno.json", "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取得数据以json的形式传送到页面
	 * @作者：WXJ
	 * @日期：2010-12-06
	 * @return 
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public String getMenusJson()  {
		
		HttpSession session = getSession();
		List<TAcRole> roles = (List<TAcRole>)session.getAttribute("_roles");
	
		if(roles!=null&&!roles.isEmpty()&&session.getAttribute("_orgRoot")!=null){
			Long orgid = (Long)session.getAttribute("_orgRoot");
			StringBuilder sbIn = new StringBuilder();
			sbIn.append("(");
			for(int i = 0;i < roles.size() ; i++){
				if(i==(roles.size()-1)){
					sbIn.append(roles.get(i).getRoleid());
				}else{
					sbIn.append(roles.get(i).getRoleid()+",");
				}
			}
			sbIn.append(")");
			
			ComDao dao = SpringContextHolder.getBean("dao");
			StringBuilder sb = new StringBuilder();
			//一级菜单
			sb.append("select distinct b from TAcRoleright a,TAcMenu b, TAcOrg c ")
				.append("where a.menuid=b.menuid and b.orgid=c.orgid and a.roleid in "+sbIn.toString()+" and c.orgid=? ")
				.append("and b.treelevel=? ")
				.append("order by b.parentid,b.sortno");
			
			List<TAcMenu> list = dao.findByHQL(sb.toString(),orgid,new Long(1));
			//二级菜单
			sb = new StringBuilder();
			sb.append("select distinct b from TAcRoleright a,TAcMenu b, TAcOrg c ")
				.append("where a.menuid=b.menuid and b.orgid=c.orgid and a.roleid in  "+sbIn.toString()+"  and c.orgid=? ")
				.append("and b.treelevel=? and b.parentid=? ")
				.append("order by b.parentid,b.sortno");
			List<TAcMenu> listSub = null;
			
			if(list!=null){
					Map menuLev1 = new HashMap();
					Map menuLev2 = new HashMap();
					Map tmp = new HashMap();
					List listMenu1 = new ArrayList();
					List listMenu2 = new ArrayList();
					for(TAcMenu ent : list){
						menuLev1 = new HashMap();
						
						menuLev1.put("menuid", ent.getMenuid().toString());
						menuLev1.put("icon", "icon-sys");
						menuLev1.put("menuname", ent.getMenuname());
						
						listSub = dao.findByHQL(sb.toString(),orgid,new Long(2),ent.getMenuid());
						listMenu2 = new ArrayList();
						for(TAcMenu entSub : listSub){
							
							menuLev2 = new HashMap();
							menuLev2.put("icon", "icon-sys");
							menuLev2.put("menuname", entSub.getMenuname());
							menuLev2.put("url", session.getServletContext().getContextPath()+"/"+entSub.getMenuaction());
//							menuLev1.put("menus", menuLev2);
							listMenu2.add(menuLev2);
						}
						menuLev1.put("menus", listMenu2);
						listMenu1.add(menuLev1);
					}	
					tmp.put("menus", listMenu1);
					System.out.println(JsonUtil.beanToJson(tmp));
					Struts2Util.renderJson(JsonUtil.beanToJson(tmp), "encoding:UTF-8", "no-cache:true");
			}
		}
		
		return null;
	}

	public Object getModel() {
		return null;
	}
	@Override
	protected void prepareModel() throws Exception {
		
	}
	public static void main(String[] args) {
	}
}
