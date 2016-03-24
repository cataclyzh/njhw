package com.cosmosource.app.portal.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Query;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.common.service.AuthorityManager;
import java.net.URLEncoder;

/**
 * 
 * @description: 智慧城市导航页
 * @author herb
 * @date Mar 22, 2013 11:18:53 AM
 */
@SuppressWarnings("unchecked")
public class CityNavAction extends BaseAction {
	
	private DevicePermissionToAppService devicePermissionToApp;
	private AuthorityManager authorityManager;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8008556164184840497L;

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}
	/**
	 * 
	* @title: citynav 
	* @description: 智慧城市导航菜单方法
	* @author herb
	* @return
	* @date Apr 6, 2013 11:35:47 AM     
	* @throws
	 */
	public String citynav(){
		String menuKeywordStr = "";
		String urlStr = "";
		try {
			String userId = getRequest().getSession().getAttribute(Constants.USER_ID).toString().trim();
			List<Objtank> menuList = devicePermissionToApp.getAuthAllMenuCollection(userId);
			StringBuilder menuKeywordSB = new StringBuilder();
			
			StringBuilder urlSB = new StringBuilder();
			if (null != menuList && menuList.size() > 0){
				String path = getRequest().getContextPath();
				String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path+"/";
				for (Objtank menu : menuList){
					String keyword = (null == menu.getKeyword())?"":menu.getKeyword().trim();
					String link = (null == menu.getLink())?"":menu.getLink().trim();
					//换成绝对路径
					if (!link.startsWith("http://")&&(link.startsWith("app")||link.startsWith("/app"))){
						link = basePath + link;
					}
					String navLink = basePath + "/app/portal/index.act";
					String title = menu.getTitle().trim();
					title = URLEncoder.encode(title,"utf-8");
					if (!(null != menu.getBlank() && menu.getBlank().trim().equals("2"))){//全屏首页显示
						link = navLink+"?id=cityMenu"+menu.getNodeId()+"&title=" +title +"&url="+link;
					} 
					
					menuKeywordSB.append(keyword).append("@_@");
					urlSB.append(link).append("@_@");
				}
			}
//			menuKeywordStr = "citynav_2@_@citynav_1@_@xx_1";
//			urlStr = "/app/portal/citynav/citynav.act@_@null@_@/app/portal/citynav/citynav.act";
			menuKeywordStr = menuKeywordSB.toString();
			menuKeywordStr.substring(0, menuKeywordStr.lastIndexOf("@_@"));
			urlStr = urlSB.toString();
			urlStr.substring(0, urlStr.lastIndexOf("@_@"));
			this.getRequest().setAttribute("menuKeywordStr", menuKeywordStr);
			this.getRequest().setAttribute("urlStr", urlStr);
			
			//首页 用户名与登录名
			String loginname = (String)this.getSession().getAttribute(Constants.LOGIN_NAME);
			String userName = (String)this.getSession().getAttribute(Constants.USER_NAME);
			Users user = (Users)authorityManager.findUserByLoginName(loginname);
			String username = userName + " (" + loginname + ") "; 
			getRequest().setAttribute("username", username);
			getRequest().setAttribute("loginname", loginname);
			getRequest().setAttribute("loginpwd", user.getLoginPwd());
		} catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}

	public AuthorityManager getAuthorityManager() {
		return authorityManager;
	}

	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}
}
