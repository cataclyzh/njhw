/**
* <p>文件名: HomeAction.java</p>
* <p>描述：首页Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-4-19 上午11:10:24 
* @作者：csq
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.listener.UserCounterListener;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcSubsys;
import com.cosmosource.common.service.AuthorityManager;

/**
 * @类描述: 首页Action
 * @作者： WXJ
 */
public class HomeAction extends BaseAction<Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8624881342121965306L;
	
	private AuthorityManager authorityManager;

	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}
	
	/**
	 * @描述:加载主菜单
	 * @作者：WXJ
	 * @日期：2012-4-19
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String loadRootMenu(){
		if(logger.isDebugEnabled()){
			logger.debug("enter loadRootMenu...");
		}
		
		String subSysId = getParameter("subSysId");
		if(logger.isDebugEnabled()){
			logger.debug("subSysId:" + subSysId);
		}
		
//		HttpSession session = getSession();
//		List<TAcRole> roles = (List<TAcRole>)session.getAttribute("_roles");
//		Long orgid = (Long)session.getAttribute("_orgRoot");
//		if(roles != null && !roles.isEmpty() && orgid != null){
//			StringBuilder roleIds = new StringBuilder();
//			roleIds.append("(");
//			for(int i = 0;i < roles.size() ; i++){
//				if(i==(roles.size()-1)){
//					roleIds.append(roles.get(i).getRoleid());
//				}else{
//					roleIds.append(roles.get(i).getRoleid()+",");
//				}
//			}
//			roleIds.append(")");
//			
//			List<TAcMenu> list = authorityManager.getRootMenu(subSysId, roleIds.toString(), orgid.toString());
			List<TAcMenu> list = new ArrayList<TAcMenu>();
			TAcMenu menu = new TAcMenu();
			menu.setMenuid(200421L);
			menu.setParentid(1L);
			menu.setMenuname("智慧政务社区");
			menu.setMenulabel("智慧政务社区");
			menu.setMenuaction("");
			menu.setIsbottom("1");
			menu.setSubsysid(4L);	
			list.add(menu);
			Struts2Util.renderJson(JsonUtil.beanToJson(list), "encoding:UTF-8", "no-cache:true");
//		}
		
		return null;
	}
	
	/**
	 * @描述:加载菜单
	 * @作者：WXJ
	 * @日期：2012-4-19
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String loadMenu(){
		if(logger.isDebugEnabled()){
			logger.debug("enter loadRootMenu...");
		}
		
		String subSysId = getParameter("subSysId");
		if(logger.isDebugEnabled()){
		    logger.debug("subSysId:" + subSysId);
		}
		
		HttpSession session = getSession();
		List<TAcRole> roles = (List<TAcRole>)session.getAttribute("_roles");
		List<TAcSubsys> subSyses = (List<TAcSubsys>)session.getAttribute("_orgsubsys");
		Long orgid = (Long)session.getAttribute("_orgRoot");
		if(roles != null && !roles.isEmpty() && orgid != null){
			StringBuilder roleIds = new StringBuilder();
			roleIds.append("(");
			for(int i = 0;i < roles.size() ; i++){
				if(i==(roles.size()-1)){
					roleIds.append(roles.get(i).getRoleid());
				}else{
					roleIds.append(roles.get(i).getRoleid()+",");
				}
			}
			roleIds.append(")");
			
			List<TAcMenu> list = authorityManager.getRootMenu(subSysId, roleIds.toString(), orgid.toString());
			int count = 0;
			StringBuilder menu = new StringBuilder();
			for(TAcMenu ent : list){
				menu.append("<div title=\""+ent.getMenulabel()+"\" ");
				menu.append(" id=\"menuLeve1_"+count+"\"");
				menu.append(" icon=\"icon-sys\"><ul>");
				List<TAcMenu> subList = authorityManager.getSubMenu(ent.getMenuid().toString(), roleIds.toString(), orgid.toString());
				for(TAcMenu entSub : subList){
					getSession().setAttribute(entSub.getMenuaction(), entSub.getMenulabel());
					menu.append("<li><div>\n")
					.append("<a href=\"#\" ");
					if(subSyses.size() == 0){
						menu.append("onclick=\"javascript:addTab('"+entSub.getMenulabel()+"','"+getServletContext().getContextPath()+"/"+entSub.getMenuaction()+"')\" class=\"leftMenu\">");
					}
					else{
						menu.append("onclick=\"javascript:addTabs('"+entSub.getMenulabel()+"','"+getServletContext().getContextPath()+"/"+entSub.getMenuaction()+"','center-div"+ent.getSubsysid()+"','main-center"+ent.getSubsysid()+"')\" class=\"leftMenu\">");
					}
					
					menu.append("<span class=\"icon icon-nav\"></span>"+entSub.getMenulabel()+"</a> \n")
					.append("</div>\n</li>\n");
				}
				menu.append("</ul></div>");
				count++;
			}
			
			Struts2Util.renderText(menu.toString());
		}
		
		return null;
	}
	
	/**
	 * @描述:加载子菜单
	 * @作者：WXJ
	 * @日期：2012-4-19
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String loadSubMenu(){
		if(logger.isDebugEnabled()){
			logger.debug("enter loadSubMenu...");
		}
		String rootMenuId = getParameter("rootMenuId");
		if(logger.isDebugEnabled()){
			logger.info("rootMenuId:" + rootMenuId);
		}
		
//		HttpSession session = getSession();
//		List<TAcRole> roles = (List<TAcRole>)session.getAttribute("_roles");
//		Long orgid = (Long)session.getAttribute("_orgRoot");
//		if(roles!=null&&!roles.isEmpty()&&orgid!=null){
//			StringBuilder roleIds = new StringBuilder();
//			roleIds.append("(");
//			for(int i = 0;i < roles.size() ; i++){
//				if(i==(roles.size()-1)){
//					roleIds.append(roles.get(i).getRoleid());
//				}else{
//					roleIds.append(roles.get(i).getRoleid()+",");
//				}
//			}
//			roleIds.append(")");
//			
//			List<TAcMenu> list = authorityManager.getSubMenu(rootMenuId, roleIds.toString(), orgid.toString());
//			TAcMenu entSub = null;
//			for (Iterator<TAcMenu> iterator = list.iterator(); iterator.hasNext();) {
//				entSub = iterator.next();
//				getSession().setAttribute(entSub.getMenuaction(), entSub.getMenulabel());
//			}
			
			List<TAcMenu> list = new ArrayList<TAcMenu>();
			TAcMenu menu = new TAcMenu();
			menu.setMenuid(200421L);
			menu.setParentid(1L);
			menu.setMenuname("运营管理中心");
			menu.setMenulabel("运营管理中心");
			menu.setMenuaction("app/portal/portal.act");
			menu.setIsbottom("1");
			menu.setSubsysid(4L);	
			list.add(menu);

			menu = new TAcMenu();
			menu.setMenuid(200421L);
			menu.setParentid(1L);
			menu.setMenuname("业务字典管理");
			menu.setMenulabel("业务字典管理");
			menu.setMenuaction("common/dictMgr/dictFrame.act");
			menu.setIsbottom("1");
			menu.setSubsysid(4L);	
			list.add(menu);
			
			menu = new TAcMenu();
			menu.setMenuid(200421L);
			menu.setParentid(1L);
			menu.setMenuname("常量管理");
			menu.setMenulabel("常量管理");
			menu.setMenuaction("common/constants/list.act");
			menu.setIsbottom("1");
			menu.setSubsysid(4L);	
			list.add(menu);
			Struts2Util.renderJson(JsonUtil.beanToJson(list), "encoding:UTF-8", "no-cache:true");
//		}
		
		return null;
	}
	
	/**
	 * @描述:统计当前在线人数
	 * @作者：WXJ
	 * @日期：2012-6-6
	 * @return
	 * @throws Exception
	 */
	public String countOnlineUser() throws Exception{
		String counter = (String)this.getServletContext().getAttribute(UserCounterListener.COUNT_KEY);
		logger.info("当前在线人数:" + counter);
		Struts2Util.renderText("counter");
	
		return null;
	}

	//-- service
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}
	
}
