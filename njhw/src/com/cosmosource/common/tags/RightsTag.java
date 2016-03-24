
package com.cosmosource.common.tags;

import java.io.IOException;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.cosmosource.base.dao.ComDao;
import com.cosmosource.base.service.SpringContextHolder;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcSubsys;

/**
 * 说明：根据当前登录人员的角色显示不同的功能
 * @author WXJ 
 **/
@SuppressWarnings("unchecked")
public class RightsTag extends TagSupport {

	private static final long serialVersionUID = -1017618325149357862L;
	
	private String menuType;
	
	private String subSysId;

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
	
		String funcMenu = this.getFuncMenu();		
		if(StringUtils.isBlank(funcMenu)){
			funcMenu = "该人员没有此权限！";
		}
		try {
			pageContext.getOut().print(funcMenu);
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
		return EVAL_PAGE;		
	}
   
    /**
     * 取得生成功能目录的string
     * @return 生成功能目录的string                                     
     */
	
	private String getFuncMenu() {
		// 构造功能目录
		StringBuilder buf = new StringBuilder();
		
		HttpSession session = pageContext.getSession();
		List<TAcRole> roles = (List<TAcRole>)session.getAttribute("_roles");
		
		List<TAcSubsys> syses = (List<TAcSubsys>)session.getAttribute("_orgsubsys");
	
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
				.append("and b.treelevel=? ");
				if(StringUtil.isNotBlank(subSysId)){
					sb.append("and b.subsysid = "+subSysId);
				}
				sb.append(" order by b.parentid,b.sortno");
			
			List<TAcMenu> list = dao.findByHQL(sb.toString(),orgid,new Long(1));
			//二级菜单
			sb = new StringBuilder();
			sb.append("select distinct b from TAcRoleright a,TAcMenu b, TAcOrg c ")
				.append("where a.menuid=b.menuid and b.orgid=c.orgid and a.roleid in  "+sbIn.toString()+"  and c.orgid=? ")
				.append("and b.treelevel=? and b.parentid=? ")
				.append("order by b.parentid,b.sortno");
			List<TAcMenu> listSub = null;
			HttpServletRequest request =
				   (HttpServletRequest)this.pageContext.getRequest();

			if(list!=null){
				if("accordion".equals(menuType)){
					int i=0;
//					buf.append("<script type=\"text/javascript\"> $.blockUI({ message: '正在加载中..........' });</script>");
					for(TAcMenu ent : list){
						buf.append("<div title=\""+ent.getMenulabel()+"\" ");
						buf.append(" id=\"menuLeve1_"+i+"\"");
						buf.append(" icon=\"icon-sys\"><ul>");
						listSub = dao.findByHQL(sb.toString(),orgid,new Long(2),ent.getMenuid());
						for(TAcMenu entSub : listSub){
							pageContext.getSession().setAttribute(entSub.getMenuaction(), entSub.getMenulabel());
							buf.append("<li><div>\n")
							.append("<a href=\"#\" ");
							if(syses.size()==0){
								buf.append("onclick=\"javascript:addTab('"+entSub.getMenulabel()+"','"+request.getContextPath()+"/"+entSub.getMenuaction()+"')\" class=\"leftMenu\">");
							}else{
								buf.append("onclick=\"javascript:addTabs('"+entSub.getMenulabel()+"','"+request.getContextPath()+"/"+entSub.getMenuaction()+"','center-div"+ent.getSubsysid()+"','main-center"+ent.getSubsysid()+"')\" class=\"leftMenu\">");
							}
							
							buf.append("<span class=\"icon icon-nav\"></span>"+entSub.getMenulabel()+"</a> \n")
							.append("</div>\n</li>\n");
						}
						buf.append("</ul></div>");
						i++;
					}	
//					buf.append("<script type=\"text/javascript\"> $.unblockUI();</script>");
				} else if ("menubutton".equals(menuType)){
					buf.append("<div>")
						.append("<a href=\"javascript:void(0)\" id=\"mb1\" class=\"easyui-menubutton\" menu=\"#mm1\" plain=\"false\" iconCls=\"icon-search\">")
						.append("功能导航菜单</a></div>")
						.append("<div id=\"mm1\" style=\"width:150px;\"> ");
					
					for(TAcMenu ent : list){
						buf
						.append("<div iconCls=\"icon-redo\">")
						.append("<span>"+ent.getMenulabel()+"</span>")
						.append("<div style=\"width:150px;\">");
						listSub = dao.findByHQL(sb.toString(),orgid,new Long(2),ent.getMenuid());
						for(TAcMenu entSub : listSub){
							buf.append("<div");
							if(syses.size()==0){
								buf.append("onclick=\"javascript:addTab('"+entSub.getMenulabel()+"','"+request.getContextPath()+"/"+entSub.getMenuaction()+"')\" class=\"leftMenu\">");
							}else{
								buf.append("onclick=\"javascript:addTabs('"+entSub.getMenulabel()+"','"+request.getContextPath()+"/"+entSub.getMenuaction()+"','center-div"+ent.getSubsysid()+"','main-center"+ent.getSubsysid()+"')\" class=\"leftMenu\">");
							}
							
							buf.append(">"+entSub.getMenulabel()+"</div>");
						//	pageContext.getSession().setAttribute(entSub.getMenuaction(), entSub.getMenulabel());
						}
						buf.append("</div>");
						buf.append("</div>");
						buf.append("<div class=\"menu-sep\"></div>");
					}
					buf.append("</div>");

				} else if ("menu".equals(menuType)) {

					buf.append("<div>");
					for(TAcMenu ent : list){
						buf
						.append("<a href=\"javascript:void(0)\" id=\"mb1\" class=\"easyui-menubutton\" menu=\"#mm"+ent.getMenuid()+"\" plain=\"true\">")
						.append(ent.getMenulabel()+"</a>");
					}
					buf.append("</div>");
					
					for(TAcMenu ent : list){
						buf.append("<div id=\"mm"+ent.getMenuid()+"\" style=\"width:150px;\"> ");

						
						listSub = dao.findByHQL(sb.toString(),orgid,new Long(2),ent.getMenuid());
						for(TAcMenu entSub : listSub){

							buf.append("<div iconCls=\"icon-redo\"");
							if(syses.size()==0){
								buf.append("onclick=\"javascript:addTab('"+entSub.getMenulabel()+"','"+request.getContextPath()+"/"+entSub.getMenuaction()+"')\" class=\"leftMenu\">");
							}else{
								buf.append("onclick=\"javascript:addTabs('"+entSub.getMenulabel()+"','"+request.getContextPath()+"/"+entSub.getMenuaction()+"','center-div"+ent.getSubsysid()+"','main-center"+ent.getSubsysid()+"')\" class=\"leftMenu\">");
							}
							buf.append("><span>"+entSub.getMenulabel()+"</span>");
							//pageContext.getSession().setAttribute(entSub.getMenuaction(), entSub.getMenulabel());
							buf.append("</div>");
						}
						
						buf.append("</div>");
					}
				} else if ("tree".equals(menuType)) {
					
				} else{
					
				}
			}
		}
		return buf.toString();
	}
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getSubSysId() {
		return subSysId;
	}

	public void setSubSysId(String subSysId) {
		this.subSysId = subSysId;
	}
	
}
