package com.cosmosource.app.portal.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.app.portal.service.NavManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.NewCheckLink;
import com.cosmosource.base.util.Struts2Util;

/**
 * 
 * @description: 平台index 导航页
 * @author herb
 * @date Mar 22, 2013 11:18:53 AM
 */
@SuppressWarnings("rawtypes")
public class NavAction extends BaseAction {
	//shortcut 配置文件
	public static final String PATH_SHUTCUT_XML = "app/portal/shortcut.xml";
	private DevicePermissionToAppService devicePermissionToApp;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6159784016827307749L;
	public NavManager navManager;
    private int messageCount=10;
    
    private static String defaultNavImg = "app/integrateservice/images/navPics/list_fest00.jpg";
    
    private static final Log log = LogFactory.getLog(NavAction.class);
  
	@Override
	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	/**
	 * 
	 * @title: getMainMenu
	 * @description: 查询主菜单
	 * @author herb
	 * @return
	 * @date Mar 22, 2013 11:20:01 AM
	 * @throws
	 */
	public String mainMenu() {
		Long pid = null;
		if(!getRequest().getParameter("pid").toString().trim().equals("-1")){
			pid = Long.valueOf(getRequest().getParameter("pid").toString().trim());
		}
		List<Objtank> mlist = null;
		try {
			mlist = devicePermissionToApp.getAuthMenuCollection(pid, this.getRequest().getSession().getAttribute(Constants.USER_ID).toString().trim());
		} catch(Exception e){
			e.printStackTrace();
		}
		Document document = DocumentHelper.createDocument();
		// 生成一个接点
		Element root = document.addElement("root");
		try {
			if (null != mlist && mlist.size() > 0) {
				for (Objtank menu : mlist) {
					Element categoryID = root.addElement("CategoryID");
					categoryID.addText(String.valueOf(menu.getNodeId()));

					Element categoryName = root.addElement("CategoryName");
					categoryName.addText((null == menu.getTitle()) ? ""
							: menu.getTitle().trim());

					Element ico = root.addElement("ico");
					ico.addText((null == menu.getTitle()) ? "" : menu.getTitle());
					//是否有子节点
//					Element isChild = root.addElement("isChild");
//					if (menu.getExtIsBottom().equals("1")){
//						isChild.addText("0");
//					} else {
//						isChild.addText("1");
//					}

					Element fatherID = root.addElement("FatherID");
					fatherID.addText(String.valueOf(menu.getPId()));
					//是否有链接
					Element isLink = root.addElement("IsLink");
					if (menu.getExtIsBottom().trim().equals("0")||(menu.getLink()!=null&&menu.getLink().trim().length() > 1)){
						isLink.addText("1");
					} else {
						isLink.addText("0");
					}
					
					Element url = root.addElement("url");
					String localLink = menu.getLink();
					String localPath = "";
					if (localLink!=null){
						if (localLink.indexOf("http://")!=0){
							String localUrl = this.getRequest().getRequestURL().toString();
							String localContext = this.getRequest().getContextPath();
							localPath = localUrl.substring(0,localUrl.indexOf(localContext)+localContext.length())+"/";					
						}	
						url.addText(localPath+localLink);
					}
					

					Element target = root.addElement("Target");
					String blank = menu.getBlank();
					String targetStr = " target='mainFrame' ";
					if (blank == "1")//弹出页面
						targetStr = " target='_blank' ";
					target.addText(targetStr);

				}
			}
//			List<SLeftmenu> menuList = authManager.getMenuByPid(getRequest()
//					.getParameter("pid").toString());
//			System.out.println(menuList.size());
//		SAXReader reader = new SAXReader();
//		Document document = DocumentHelper.createDocument();
//		// 生成一个接点
//		Element root = document.addElement("root");
//		try {
//			if (null != menuList && menuList.size() > 0) {
//				for (SLeftmenu menu : menuList) {
//
//					Element categoryID = root.addElement("CategoryID");
//					categoryID.addText(String.valueOf(menu.getId()));
//
//					Element categoryName = root.addElement("CategoryName");
//					categoryName.addText((null == menu.getTitleName()) ? ""
//							: menu.getTitleName());
//
//					Element ico = root.addElement("ico");
//					ico.addText((null == menu.getIco()) ? "" : menu.getIco());
//
//					Element isChild = root.addElement("isChild");
//					List<SLeftmenu> subList = authManager.getMenuByPid(String
//							.valueOf(menu.getId()));
//					if (null == menu || subList.size() < 1) {
//						isChild.addText("0");
//					} else {
//						isChild.addText("1");
//					}
//
//					Element fatherID = root.addElement("FatherID");
//					fatherID.addText(String.valueOf(menu.getParentId()));
//
//					Element isLink = root.addElement("IsLink");
//					String link = menu.getLink();
//					String strrankfirst = menu.getRankfirst();
//					if (null == strrankfirst) {
//						strrankfirst = "";
//					}
//					if (link == "" && strrankfirst == "") {
//						isLink.addText("0");
//					} else {
//						isLink.addText("1");
//					}
//					Element url = root.addElement("url");
//					url.addText(String.valueOf(menu.getLink()));
//
//					Element target = root.addElement("Target");
//					String blank = menu.getBlank();
//					String targetStr = " target='mainFrame' ";
//					if (blank == "Y")
//						targetStr = " target='_blank' ";
//					target.addText(targetStr);
//
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String menuXml = document.asXML();
//		System.out.println(menuXml);
		menuXml.substring(0, menuXml
				.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
		Struts2Util.renderText(menuXml);
		return null;
	}
	
	private String getMapValue(Map m, String key){
		String result = (String) m.get(key);
		if(result == null){
			return result;
		}
		return result.trim();
	}
	
	/**
	 * 针对[中心管理员]的导航黑名单
	 * @param title
	 * @return
	 */
	private boolean isNotNeed(String title){
		//"菜单发布",
		String [] arr = {"房间分配","我的派单","来访列表","客服首页","人员信息批量导入","电话管理",
				"各单位房间分配情况","我的报修","电话分配","车位管理",
				"综合门户","报修统计","单位房间分配(三维)","一键报修","访客跟踪",
				"入驻单位","报修列表","门禁授权","IP电话帮助","设备设施维护","个人设置",
				"物业通知管理","建筑结构维护","安全监控主页"
				};
		List list = Arrays.asList(arr);
		if(list.contains(title)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 全局导航黑名单
	 * @param title
	 * @return
	 */
	private boolean isNotNeedAll(String title){
		String [] arr = {"综合门户"};
		List list = Arrays.asList(arr);
		if(list.contains(title)){
			return true;
		}else{
			return false;
		}
	}
	
	public String ajaxQueryNavMenu() {
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		//String userId = "101";
		List<Map> result = getNavResult(userId);
		if(result != null){
			Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		}
		return null;
	}
	
	public List<Map> testAjaxQueryNavMenu(){
		//101
		return getNavResult("5240");
	}
	
	public List<Map> test2(){
		ServletContext sc = Struts2Util.getSession().getServletContext();
//		System.out.println(sc);
		WebApplicationContext ct = WebApplicationContextUtils.getWebApplicationContext(sc);
		
		
		DevicePermissionToAppService aa = (DevicePermissionToAppService) ct.getBean("devicePermissionToApp");
		return aa.queryNavMenuByUserId("1033");
		
		
		//101
		//return getNavResult("1033");
	}
	
	private List<Map> getOrderList(List<Map> list){
		List<Map> result = new LinkedList<Map>();
		int total = list.size();
		for(int j=0; j<total; j++){
			Map m = list.get(0);
			int minIndex = 0;
			int sortNum = Integer.parseInt(m.get("SORT").toString());
			for(int i=1; i< list.size(); i++){
				Map tmp = list.get(i);
				int sortNumTmp = Integer.parseInt(tmp.get("SORT").toString());
				if(sortNumTmp < sortNum){
					sortNum = sortNumTmp;
					minIndex = i;
				}
			}
			result.add(list.get(minIndex));
			list.remove(minIndex);
		}
		
		return result;
	}
	
	private List<Map> getNavResult(String userId){
		List<Map> result = new ArrayList<Map>();
		try {
			List<Map> tmpResult1 = devicePermissionToApp.queryNavMenuByUserId(userId);			
			List<Map> tmpResult2 = new ArrayList<Map>();						
			for(Map m : tmpResult1){
				String title = getMapValue(m, "TITLE");
				String link = getMapValue(m, "LINK");
				String img = getMapValue(m, "ICO");
				String blank = getMapValue(m, "BLANK");
				
				if(link == null || link.length() == 0 ){
					continue;
				}
				
				if(title.equals("资源权限管理")){
					m.put("isZYGL",80);
				}
				
				if(img != null && img.trim().equalsIgnoreCase("none")){
					continue;
				}
				
				if(img == null || img.trim().length() == 0){
					m.put("ICO", defaultNavImg);
					img = defaultNavImg;
				}
				m.put("imgHover", getHoverPictureName(img));
				
				//用来表示弹出窗口的NavMenu. openStatus表示窗口大小的不同设置
				//blank=10是默认弹出窗口
				if(blank != null){
					if("大厦指南".equals(title)){
						m.put("openStatus", 0);
					}else if("主动约访".equals(title)){
						m.put("openStatus", 1);
					}else if (blank.equals("1")){
						m.put("openStatus", 10);
					}else if (blank.equals("5")){
						m.put("openStatus", 3);
					}else if (blank.equals("6")){
						m.put("openStatus", 4);
					}else{
						m.put("openStatus",99);
					}
				}else{
					m.put("openStatus",99);
				}
				
				tmpResult2.add(m);
			}
			
			//去除重复的NavMenu
			//如果NavMenu的Link相同,则认为相同
			result = getNoRepeatMenu(tmpResult2);
			
			log.debug("navMenu number: " + result.size());
			log.debug("===============================");
			for(Map m : result){				
				log.debug(getMapValue(m,"TITLE") + " : " 
						+ getMapValue(m,"LINK") + " : " 
						+ getMapValue(m,"ICO") + " : " 
						+ m.get("NODEID"));
			}
			log.debug("===============================");
		} catch(Exception e){
			e.printStackTrace();
		}
		result = getOrderList(result);
		return result;
	}
	
	private List<Map> getNoRepeatMenu(List<Map> list){
		Map<Object, Map> tmp = new HashMap<Object, Map>();
		for(Map m : list){
			Object key = m.get("LINK"); 
			if(tmp.containsKey(key)){
				log.error("重复的NavMenu");
				log.error(m.get("TITLE")+" : " + m.get("LINK"));
			}
			tmp.put(key, m);
		}
		
		List<Map> result = new ArrayList<Map>();
		result.addAll(tmp.values());
		return result;
	}
	
	
	/**
	 * @description 查询用户信息
	 * @author herb
	 */
	public String userInfo() {
		Document document = DocumentHelper.createDocument();
		// 生成一个接点
		Element root = document.addElement("root");
		// 生成root的一个接点
		Element category = root.addElement("display_name");
		category.addText("herongbing");
		String menuXml = document.asXML();
		menuXml.substring(0, menuXml
				.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
		Struts2Util.renderXml(menuXml);
		return null;
	}

	/**
	 * @description 快捷图标
	 * @author herb
	 */
	public String shortcut() {
		String menuXml = "";
		
		try {
			SAXReader reader = new SAXReader();
			String shortcutPath = this.getRequest().getSession().getServletContext().getRealPath("/")+PATH_SHUTCUT_XML;
			Document document = reader.read(new FileInputStream(new File(shortcutPath)));
			menuXml = document.getRootElement().asXML();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderText(menuXml);
		return null;
	}
    
    //为图片地址添加_hover后缀,用来做Menu的反色效果
    public String getHoverPictureName(String str){
    	String hover = "_hover";
    	String s4;
    	int i1;
    	if(str != null && str.trim().length() != 0){
    		i1 = str.lastIndexOf(".");
	    	String s2 = str.substring(0, i1);
	    	String s3 = str.substring(i1);
	    	s4 = s2 + hover + s3;
	    	return s4;
    	}
    	return null;
    }
	
    /**
     * 验证该用户名是否为管理员
     * @return
     */
    public String queryAdminUserByIdJSON(){
    	String loginName = getParameter("loginName");
    	Map map = new HashMap();
    	
    	
    	Map param = new HashMap();
    	param.put("loginUid",loginName);
    	List list = devicePermissionToApp.queryAdminUserByLoginId(param);

    	if(list.size() > 0){
    		map.put("isAdmin", "false");
    	}else{
    		map.put("isAdmin", "true");
    	}
    	
    	Struts2Util.renderJson(map, "encoding:UTF-8", "no-cache:true");
    	return null;
    }
    
    /**
     * 单点登录根据userId查询密码
     * @return
     */
    public void queryPasswordByUserID(){
    	String userId = getParameter("userId").replaceAll(" *",""); 	
    	Map param = new HashMap();
    	param.put("userId",userId);
    	List list = devicePermissionToApp.queryPasswordByUserID(param);    	
    	log.info("查询到的结果：" + list);
    	Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
    }
	
	/**
	 * 
	* @title: logout 
	* @description: 退出
	* @author herb
	* @return
	* @date Mar 26, 2013 2:41:26 PM     
	* @throws
	 */
	public String logout(){
		return SUCCESS;
	}
	

	public NavManager getNavManager() {
		return navManager;
	}

	public void setNavManager(NavManager navManager) {
		this.navManager = navManager;
	}

	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	
	public static void main(String[] args){
		String hover = "_hover";
		String s1 = "/app/integrateservice/images/navPics/list_fest17.jpg";
		int i1 = s1.lastIndexOf(".");
		String s2 = s1.substring(0, i1);
		System.out.println(s2);
		String s3 = s1.substring(i1);
		System.out.println(s3);
		String s4 = s2 + hover + s3;
		System.out.println(s4);
	}
	
}
