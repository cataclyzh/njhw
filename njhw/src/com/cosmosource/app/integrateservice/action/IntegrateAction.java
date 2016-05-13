package com.cosmosource.app.integrateservice.action;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.common.action.HomeAction;
import com.cosmosource.app.common.model.TaskInfoModel;
import com.cosmosource.app.common.service.HomeManager;
import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.FsDishes;
import com.cosmosource.app.entity.FsDishesIssue;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.integrateservice.service.IntegrateManager;
import com.cosmosource.app.personnel.service.PersonRegOutManager;
import com.cosmosource.app.personnel.service.PersonnelExpInforManager;
import com.cosmosource.app.personnel_e.service.E4PersonnelManager;
import com.cosmosource.app.port.serviceimpl.BuildAutoToAppService;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.app.port.serviceimpl.IPPhoneSiteToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.EncodeUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.service.AuthorityManager;
import com.cosmosource.common.service.MsgBoardManager;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings("unchecked")
public class IntegrateAction extends BaseAction {
	
	private static final long serialVersionUID = 4227875753301925460L;
	
	private IntegrateManager integrateManager;
	private BuildAutoToAppService buildAutoToAppService;
	private DoorControlToAppService doorControlToAppService;
	private IPPhoneSiteToAppService iPPhoneSiteToAppService;
	private PersonRegOutManager personROManager;
	private MsgBoardManager msgBoardManager;
	private HomeManager appHomeManager;
	private HomeAction appHomeAction;
	private PersonnelExpInforManager personExpManager;
	
	private AuthorityManager authorityManager;
	
	private E4PersonnelManager e4PersonnelManager;
	
	public void setE4PersonnelManager(E4PersonnelManager e4PersonnelManager) {
		this.e4PersonnelManager = e4PersonnelManager;
	}
	
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private String fdId;
	private Page<HashMap> page = new Page<HashMap>(50);	//公告板Page对象
	
	//----------------------------------------数据加载部分------------------------------------
	/** 
	* @title: init
	* @description: 初始化
	* @author zh
	* @date 2013-04-18
	*/ 
	public String init() {
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		
		//1 代表 跳转到 APP 首页  0 表示到运营管理中心首页
		String isApp = Struts2Util.getSession().getAttribute("IS_APP").toString();
		
		/**
		 * E座用户首页面
		 */
		long uid = 0;
		try{
			uid = Long.parseLong(userId);
		}catch(Exception e){
		}
		
		if(uid == 15503){
			return "video";
		}
		
		//njhw_wy
//		if(uid != 0){
//			//"/app/pro/setParkings.act"
//			return "setParkings";
//		}
		
		if(uid != 0 && e4PersonnelManager.isE4Personnel(uid)){
			return "e4Personnel";
		}
		
		//测试E座账号
//		if(uid == 13786){
//			return "e4Personnel";
//		}
		
		/**
		 * 运营管理中心导航
		 * 只有登录的时候启用
		 */
		if (null != isApp && isApp.equals("0")){
			//修改 session 中状态标志 IS_APP
			Struts2Util.getSession().setAttribute("IS_APP", 1);
			return "operationManageIndex";
		}
		
		//超级管理员导航
		if(userId!=null && userId.trim().equals("1")){
			return "adminPage";
		}
		
		// 查询电灯的状态		
		NjhwUsersExp ue = personExpManager.getpsByid(new BigDecimal(userId).longValue());
		String roomName = "";
		if(ue != null&& ue.getRoomId()!= null&& ue.getRoomInfo() != null){
			roomName = ue.getRoomInfo();
		}
		getRequest().setAttribute("roomName", roomName);
		// 查询门锁的状态
		getRequest().setAttribute("doorStatus", "off");	// testdData
		// 查询空调的状态
		getRequest().setAttribute("airConditionStatus", "off");	// testdData
		
		
		long topOrgId = 0;
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
		getRequest().setAttribute("orgId", topOrgId);
		
		// 取得用户的IP电话信息
		String hql = "select t from TcIpTel t, NjhwUsersExp e, Users u where e.telId = t.telId and e.userid = u.userid and u.userid=?";
		List<TcIpTel> ipTelList = this.integrateManager.findByHQL(hql, Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		if (ipTelList.size() > 0 && ipTelList != null) {
			getRequest().setAttribute("smac", ipTelList.get(0).getTelMac());
			getRequest().setAttribute("stel", ipTelList.get(0).getTelNum());
		}
		//菜单
		queryMenus();
		
		//
		String loginName = Struts2Util.getSession().getAttribute(Constants.LOGIN_NAME).toString();
		if(loginName.startsWith("baoxiu")){
			return "propertyA1";
		}else if(loginName.startsWith("shebeike")){
			return "propertyA2";
		}else if(loginName.startsWith("fuwuke")){
			return "propertyA3";
		}else if(loginName.startsWith("cheweishenpi")){
			return "parkingNew";
		}else if(loginName.equals("zhajishenpi")){
			return "property12";
		}
		
//		if(loginName.startsWith("zhangjun")){
//			return "metroOne";
//		}
		
		
		List<HashMap> orgNameList = personROManager.getGongYongAccountOrgName();
		if(orgNameList.size() != 0){
			Map m = orgNameList.get(0);
			String orgName = m.get("ORGNAME").toString().trim();
			
			if(orgName.equals("前台")){
				return "property1";
			}else if(orgName.equals("设备维护")){
				return "property2";
			}else if(orgName.equals("主管1")){
				return "property3";
			}else if(orgName.equals("主管2")){
				return "property4";
			}else if(orgName.equals("科长1")){
				return "property5";
			}else if(orgName.equals("科长2")){
				return "property6";
			}else if(orgName.equals("管理员")){
				//return "property7";
			}else if(orgName.equals("房产处")){
				return "njhwAdminFcc";
			}else if(orgName.equals("电话班")){
				return "njhwAdminDhb";
			}else if(orgName.equals("节能处")){
				return "njhwAdminJnc";
			}else if(orgName.equals("食堂")){
				return "njhwAdminSt";
			}else if(orgName.equals("保卫处")){
				return "njhwAdminBwc";
			}else if(orgName.equals("其他人员管理")){
				return "property8";
			}else if(orgName.equals("外来单位管理")){
				return "property9";
			}else if(orgName.equals("新城一期")){
				return "metro";
			}else if(orgName.equals("政务服务中心")){
				return "adminService";
			}
		}
		
		try {
			setUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private void setUserInfo(){
		String loginname = (String)this.getSession().getAttribute(Constants.LOGIN_NAME);
		String userName = (String)this.getSession().getAttribute(Constants.USER_NAME);
		Users user = (Users)authorityManager.findUserByLoginName(loginname);
		String username = userName + " (" + loginname + ") "; 
		getRequest().setAttribute("username", username);
		getRequest().setAttribute("loginname", loginname);
		getRequest().setAttribute("loginpwd", user.getLoginPwd());
	}
	
	/**
	 * @description:取得实时温度
	 * @author zh
	 */
	public String getWD() {
		try {  
	        String sCurrentLine;  
	        String sTotalString;  
	        sCurrentLine = "";  
	        sTotalString = "";  
	        java.io.InputStream l_urlStream;  
	        java.net.URL l_url = new java.net.URL("http://www.weather.com.cn/data/sk/101190101.html");  
	        java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url.openConnection();  
	        l_connection.connect();  
	        l_urlStream = l_connection.getInputStream();  
	        java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(l_urlStream, "utf-8"));
	        while ((sCurrentLine = l_reader.readLine()) != null) {  
	        	sTotalString += sCurrentLine;
	        }
	        if (!"".equals(sTotalString)) {
	        	sTotalString = sTotalString.replace("{\"weatherinfo\":", "");
		        sTotalString = sTotalString.replace("\"}}", "\"}");
	        }
	        Struts2Util.renderText(sTotalString.toString(), "encoding:UTF-8", "no-cache:true");
	    } catch (Exception e) {
	        e.printStackTrace();  
	    }
	    return null;
	}
	
	/** 
	* @title: refreshDoorStatus
	* @description: 刷新门状态
	* @author zh
	* @date 2013-04-18
	*/ 
	public String refreshDoorStatus() {
		long topOrgId = 0;
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
		
		HashMap map = new HashMap();	// 参数Map
		String checkType = getParameter("checkType");	// 查询分类
		List<Map> lockList = null;		// 门锁列表
		
		if ("many".equals(checkType)) {			// 查一组门的状态
			map.put("orgid", topOrgId);
			map.put("eortype", EmOrgRes.EOR_TYPE_ROOM);
			lockList = this.integrateManager.findListBySql("IntegrateSQL.queryDoorStatusByUserTopOrgID", map);
		} else if("one".equals(checkType)) {	// 查一个门
			map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			map.put("deviceType", 3);
			lockList = this.integrateManager.findListBySql("IntegrateSQL.queryDoorStatusByUserID", map);
		}
		//接口返回的结果集不定
		List<Map> statusList = doorControlToAppService.queryDoorStatus(lockList, "3C", this.getRequest().getSession().getAttribute(Constants.USER_ID).toString());
		if (null != statusList && statusList.size() > 0 && null != lockList && lockList.size() > 0){
			for (Map m : lockList) {
				for (Map s : statusList){
					String key = s.keySet().toArray()[0].toString().trim();
					if (m.get("NODEID").toString().trim().equals(key)){
						m.put("lockStatus", s.get(key));
					}
				}
			}
		}
		Struts2Util.renderJson(lockList, "encoding:UTF-8", "no-cache:true");
		return null;
	}

	/**
	 * @description:我的系统消息
	 * @author zh
	 */
	public String queryMsgBox() {
		try {
			List<HashMap> rstList = new ArrayList<HashMap>();
			List<HashMap> list = integrateManager.queryMsgBox();
			
			List<TaskInfoModel> taskList = appHomeManager.getTaskList(getSession(), appHomeAction.getMenuActionNew());
			if (taskList != null) {
				for (TaskInfoModel taskInfoModel : taskList) {
					HashMap map = new HashMap();
					map.put("TITLE", taskInfoModel.getLabname());
					map.put("CONTENT", taskInfoModel.getTaskinfo());
					map.put("MSGTYPE", "task");
					map.put("STATUS", "0");
					map.put("TASKLINK", taskInfoModel.getTasklink());
					rstList.add(map);
				}
			}
			
			for (HashMap map : list) {
				rstList.add(map);
			}
			Struts2Util.renderJson(rstList, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description: 加载交通
	 * @author zh
	 */
	public String loadTraffic() {
		JSONObject json = new JSONObject();
		try {
			List<HashMap> list = integrateManager.queryTraffic();
			json.put("list", list);
			json.put("refreshTime", integrateManager.getLastTime());
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String treeUnitType;
	
	public String getTreeUnitType() {
		return treeUnitType;
	}

	public void setTreeUnitType(String treeUnitType) {
		this.treeUnitType = treeUnitType;
	}

	/**
	 * @description:加载下级信息(部门及直属人员)
	 * @author zh
	 */
	public String loadSonInfo() {
		try {
			
			long orgId = Long.parseLong(getParameter("orgId"));
			List<HashMap> sonOrgList = null;
			List<HashMap> sonPersonList = null;
			if (orgId == 0) {
				if(treeUnitType != null && treeUnitType.trim().equals("e4List")){
					sonOrgList = this.integrateManager.loadE4List();
				}else{
					sonOrgList = this.integrateManager.loadFirstOrg();				// 单位
				}
			} else if (orgId > 0) {
				sonOrgList = this.integrateManager.loadOrg(orgId);				// 下级部门
				sonPersonList = this.integrateManager.loadPerson(orgId);		// 直属人员
			}
			if("tree".equals(treeUnitType)){
				for(int i=0;i<sonPersonList.size();i++){
					boolean isShow = false;
					
					Object objTelExt = sonPersonList.get(i).get("TEL_EXT");
					if(objTelExt != null && objTelExt.toString().equals("2")){
						isShow = true;
					}
					
					if(!isShow){
						sonPersonList.get(i).put("TEL", "");
					}
				}
			}
			JSONObject json = new JSONObject();
			json.put("sonOrgList", sonOrgList);
			json.put("sonPersonList", sonPersonList);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description:加载个人通讯录信息
	 * @author zh
	 */
	public String loadAddressInfo() {
		try {
			long gid = Long.parseLong(getParameter("gId"));
			List groupList = null, personList = null;
			if (gid == 0) {
				groupList = this.integrateManager.loadGroup();
				personList = this.integrateManager.loadPersonByGid(gid);
			} else {
				personList = this.integrateManager.loadPersonByGid(gid);
			}
			JSONObject json = new JSONObject();
			json.put("groupList", groupList);
			json.put("personList", personList);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description:筛选大厦/部门通讯录
	 * @author zh
	 */
	public String queryContact() {
		try {
			List<HashMap> sonPersonList = null;
			
			if(treeUnitType != null && treeUnitType.trim().equals("e4List")){
				sonPersonList = integrateManager.queryE4Contact(
						getParameter("tjVal"), getParameter("type"), getParameter("orgId"));
			}else{
				sonPersonList = integrateManager.queryContact(
						getParameter("tjVal"), getParameter("type"), getParameter("orgId"));
			}
				
			if("tree".equals(treeUnitType)){
				for(int i=0;i<sonPersonList.size();i++){
					boolean isShow = false;
					
					Object objTelExt = sonPersonList.get(i).get("TEL_EXT");
					if(objTelExt != null && objTelExt.toString().equals("2")){
						isShow = true;
					}
					
					if(!isShow){
						sonPersonList.get(i).put("TEL", "");
					}
				}
			}
			Struts2Util.renderJson(sonPersonList, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description:筛选个人通讯录
	 * @author zh
	 */
	public String queryAddress() {
		try {
			List list = this.integrateManager.queryAddress(getParameter("tjVal"));
			Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description:我的来访
	 * @author zh
	 */
	/*public String queryVmVisit() {
		try {
			List<HashMap> list = integrateManager.queryVmVisit();
			Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	/**
	 * @description:物业通知
	 * @author zh
	 */
	public String queryMsgBoard() {
		try {
			List<HashMap> list = msgBoardManager.findBulletin(page, "").getResult();
			Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description:报修单
	 * @author zh
	 */
	public String queryUntreatedSheet() {
		try {
			List<HashMap> list = integrateManager.queryUntreatedSheet();
			Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* 菜单查询
	* @title: queryMenus 
	* @description: TODO
	* @author gxh
	* @date 2013-5-3 下午08:05:45     
	* @throws
	*/
	@SuppressWarnings("all")
    private void queryMenus(){
		String fdiType1 = "";
		String fdiType = null;
		final String dayNames[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}; 
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			date = format.parse("2014-04-16");
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
		
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		fdiType1 = dayNames[dayOfWeek - 1];
		if(fdiType1.equals("星期五")){
			fdiType = "5";
		} else if(fdiType1.equals("星期四")){
			fdiType = "4";
		} else if(fdiType1.equals("星期三")){
			fdiType = "3";
		} else if(fdiType1.equals("星期二")){
			fdiType = "2";
		} else if(fdiType1.equals("星期一")){
			fdiType = "1";
		} else if(fdiType1.equals("星期六")){
			fdiType = "6";
		} else if(fdiType1.equals("星期日")){
			fdiType = "7";
		}
		
		try{
			String noFoodPic = getRequest().getContextPath()+"/app/integrateservice/images/food_no.jpg";
		    // 上午 
			List<List> listBigMeatAM = integrateManager.queryMenus(fdiType, FsDishesIssue.FSD_FLAG_AM);	
			if (listBigMeatAM != null) {
				List<Map> listMenuPhotoAM = new ArrayList<Map>();
				for (List<Map> l : listBigMeatAM) {
					for (Map m : l) {
						if(null != m.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO1").toString()))){
							Map pm = new HashMap();
							pm.put("SRC", showPic(m.get("FD_PHOTO1").toString()));
							listMenuPhotoAM.add(pm);
							m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
						}else{
							m.put("FD_PHOTO1", noFoodPic);
						}
					}
				}
				this.getRequest().setAttribute("listMenuPhotoAM", listMenuPhotoAM);	
				this.getRequest().setAttribute("listBigMeatAM", listBigMeatAM);	
			}
						
			//中午
			List<List> listBigMeatNOON =   integrateManager.queryMenus(fdiType,FsDishesIssue.FSD_FLAG_NOON);//中午
			if(listBigMeatNOON!=null){
				List<Map> listMenuPhotoNOON = new ArrayList<Map>();
				for (List<Map> l : listBigMeatNOON) {
					for (Map m : l) {
						if(null != m.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO1").toString()))){
							Map pm = new HashMap();
							pm.put("SRC", showPic(m.get("FD_PHOTO1").toString()));
							listMenuPhotoNOON.add(pm);
							m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
						}else{
							m.put("FD_PHOTO1", noFoodPic);
						}
					}
				}
				this.getRequest().setAttribute("listMenuPhotoNOON", listMenuPhotoNOON);	
				this.getRequest().setAttribute("listBigMeatNOON", listBigMeatNOON);	
			}
			
			//晚上的
			List<List> listBigMeatPM =   integrateManager.queryMenus(fdiType,FsDishesIssue.FSD_FLAG_PM);//晚上
			if(listBigMeatPM!=null){
				List<Map> listMenuPhotoPM = new ArrayList<Map>();
				for (List<Map> l : listBigMeatPM) {
					for (Map m : l) {
						if(null != m.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO1").toString()))){
							Map pm = new HashMap();
							pm.put("SRC", showPic(m.get("FD_PHOTO1").toString()));
							listMenuPhotoPM.add(pm);
							m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
						}else{
							m.put("FD_PHOTO1", noFoodPic);
						}
					}
				}
				this.getRequest().setAttribute("listMenuPhotoPM", listMenuPhotoPM);	
				this.getRequest().setAttribute("listBigMeatPM", listBigMeatPM);	
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

   
	/**
	 * 循环所有菜单
	 * 
	 * @title: repeat
	 * @description: TODO
	 * @author gxh
	 * @param list
	 * @param fdname
	 * @return
	 * @date 2013-5-4 上午10:16:21
	 * @throws
	 */
	private String repeat(List<Map> list, String fdname) {
		String titleName = "";
		for (int i = 0; i < list.size(); i++) {
			titleName += list.get(i).get(fdname) + " ";
		}
		return titleName;
	}

	/**
	 * 获取当天的是周几
	 * 
	 * @title: week
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-4 下午01:50:59
	 * @throws
	 */
	private String week() {
		String fdiType1 = "";
		String fdiType = null;
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		fdiType1 = dayNames[dayOfWeek - 1];
		if (fdiType1.equals("星期五")) {
			fdiType = "5";
		} else if (fdiType1.equals("星期四")) {
			fdiType = "4";
		} else if (fdiType1.equals("星期三")) {
			fdiType = "3";
		} else if (fdiType1.equals("星期二")) {
			fdiType = "2";
		} else if (fdiType1.equals("星期一")) {
			fdiType = "1";
		} else if (fdiType1.equals("星期六")) {
			fdiType = "6";
		} else if (fdiType1.equals("星期日")) {
			fdiType = "7";
		}
		String time = DateUtil.getDateTime("yyyy年MM月dd日");
		this.getRequest().setAttribute("time","今天是 "+time+ " "+fdiType1);
		return fdiType;
	}
	
	
	
	
	
	
	
	/**
	 * 刷新菜单
	* @title: refreshMenu 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-5-20 下午06:52:44     
	* @throws
	 */
	public String refreshMenu(){
		String fdiType =week();
		String fdiFlag = Struts2Util.getParameter("fdiFlag");
		try{
			String noFoodPic = getRequest().getContextPath()+"/app/integrateservice/images/food_no.jpg";
		    // 上午 
			List<List> listBigMeatAM = integrateManager.queryMenus(fdiType,fdiFlag);
		
			if(listBigMeatAM!=null){
				for (List<Map> l : listBigMeatAM) {
					for (Map m : l) {
						if(null != m.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO1").toString()))){
							m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
						}else{
							m.put("FD_PHOTO1", noFoodPic);
						}
					}
				}
				Struts2Util.renderJson(listBigMeatAM, "encoding:UTF-8",
				"no-cache:true");
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String s[])
	{
		for(int i = 0;i<7;i++)
		{
			// setDate(-i);
		}
		
		System.out.print(DateUtil.getSysCalendar().get(Calendar.HOUR_OF_DAY));
		
	}
	
	private  void setDate(int fdi)
	{   
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果      
        calendar.add(calendar.DATE,fdi);
        for(int i = 1; i <8;i++)
        {   
        	this.getRequest().setAttribute("date"+i, formatter.format(calendar.getTime()));
        	calendar.add(calendar.DATE,1);//2
        	
        }
     }
	
	/**
	 * 查找本周菜单
	 * 
	 * @title: weekMenusQuery
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-4 上午11:53:35
	 * @throws
	 */
	 @SuppressWarnings("all")
	public String weekMenusQuery() {
		String fdiType = week();
		this.getRequest().setAttribute("fdiType", fdiType);
		DateUtil.getSysCalendar().get(Calendar.HOUR_OF_DAY);
        //  SimpleDateFormat formatter = new SimpleDateFormat("yyyy月MM日dd");
        if ("1".equals(fdiType))
		{
        	setDate(0);
		}
        else if ("2".equals(fdiType))
        {
        	setDate(-1);
		}
        else if ("3".equals(fdiType))
        {
        	setDate(-2);
		} 
        else if ("4".equals(fdiType))
        {
        	setDate(-3);
		} 
        else if ("5".equals(fdiType))
        {
        	setDate(-4);
		} 
        else if ("6".equals(fdiType))
        {
        	setDate(-5);
		} 
        else if ("7".equals(fdiType))
        {
        	setDate(-6);
		} 
        
        int hour =  DateUtil.getSysCalendar().get(Calendar.HOUR_OF_DAY);
       
        String fsd = FsDishesIssue.FSD_FLAG_AM;
        this.getRequest().setAttribute("FSD_FLAG", 1);
        
	    if (hour>=9 &&  hour<13)
		{
	    	fsd = FsDishesIssue.FSD_FLAG_NOON;
	    	this.getRequest().setAttribute("FSD_FLAG", 2);
		}
	    else if(hour >=13 && hour<=23)
	    {
	    	fsd = FsDishesIssue.FSD_FLAG_PM;
	    	this.getRequest().setAttribute("FSD_FLAG", 3);
	    }
		
		try {
			// 上午
			String noFoodPic = getRequest().getContextPath()+"/app/integrateservice/images/food_no.jpg";
//			List<Map> list = integrateManager.queryMenus(fdiType, fsd);		  
//			if (list != null) {
//				for (Map m : list) {
//					if(null != m.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO1").toString()))){
//						
//						m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
//					}else{
//						
//						m.put("FD_PHOTO1", noFoodPic);
//					}
//					if(null != m.get("FD_PHOTO2")  && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO2").toString()))){
//						
//						m.put("FD_PHOTO2", showPic(m.get("FD_PHOTO2").toString()));
//					}else{
//						
//						m.put("FD_PHOTO2", noFoodPic);
//					}
//					if(null != m.get("FD_PHOTO3") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO3").toString()))){
//						
//						m.put("FD_PHOTO3", showPic(m.get("FD_PHOTO3").toString()));
//					}else{
//						
//						m.put("FD_PHOTO3", noFoodPic);
//					}
//					
//				}
//					
//				this.getRequest().setAttribute("list", list);
//			}
//			else 
//			{  
//				if (FsDishesIssue.FSD_FLAG_AM.equals(fsd))
//				{
//					this.getRequest().setAttribute("msg", "早餐尚未发布"); 
//				}
//				else if (FsDishesIssue.FSD_FLAG_NOON.equals(fsd))
//				{
//					this.getRequest().setAttribute("msg", "午餐尚未发布"); 
//				}
//				else if (FsDishesIssue.FSD_FLAG_PM.equals(fsd))
//				{
//					this.getRequest().setAttribute("msg", "晚餐尚未发布"); 
//				}
//				else
//				{
//					this.getRequest().setAttribute("msg", "食堂尚未发布饭菜"); 
//				}
//			}

			
		/*	// 中午
		List<Map> listNOON = integrateManager.queryMenus(fdiType,
					FsDishesIssue.FSD_FLAG_NOON);
			
			if (listNOON != null) {
				for (Map m : listNOON) {
					if(null != m.get("FD_PHOTO1")){
						m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
					}else{
						
						m.put("FD_PHOTO1", "");
					}
					if(null != m.get("FD_PHOTO2")){
						m.put("FD_PHOTO2", showPic(m.get("FD_PHOTO2").toString()));
					}else{
						
						m.put("FD_PHOTO2", "");
					}
					if(null != m.get("FD_PHOTO3")){
						m.put("FD_PHOTO3", showPic(m.get("FD_PHOTO3").toString()));
					}else{
						
						m.put("FD_PHOTO3", "");
					}
					
				}
				
				
				this.getRequest().setAttribute("listNOON", listNOON);
			}

			// 晚上的
			List<Map> listtPM = integrateManager.queryMenus(fdiType,
					FsDishesIssue.FSD_FLAG_PM);
		
			if (listtPM != null) {
				for (Map m : listtPM) {
					if(null != m.get("FD_PHOTO1")){
						m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
					}else{
						
						m.put("FD_PHOTO1", "");
					}
					if(null != m.get("FD_PHOTO2")){
						m.put("FD_PHOTO2", showPic(m.get("FD_PHOTO2").toString()));
					}else{
						
						m.put("FD_PHOTO2", "");
					}
					if(null != m.get("FD_PHOTO3")){
						m.put("FD_PHOTO3", showPic(m.get("FD_PHOTO3").toString()));
					}else{
						
						m.put("FD_PHOTO3", "");
					}
					
				}
				
				
				this.getRequest().setAttribute("listtPM", listtPM);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
  /**
   * 根据时间来查找菜单
  * @title: ajaxWeekMenusQuery 
  * @description: TODO
  * @author gxh
  * @return
  * @date 2013-5-9 下午06:58:34     
  * @throws
   */
	 @SuppressWarnings("all")
/*	public String ajaxWeekMenusQuery(){
		JSONObject  json = new JSONObject();
		String fdiType = Struts2Util.getParameter("fdiType");
		try {
			// 上午
			List<Map> listAM = integrateManager.queryMenus(fdiType,
					FsDishesIssue.FSD_FLAG_AM);
			Map<String,String>  imgSrcAM = null;
			if (listAM != null) {
				for (Map m : listAM) {
					if(null != m.get("FD_PHOTO1")){
						m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
					}else{
						
						m.put("FD_PHOTO1", "");
					}
					if(null != m.get("FD_PHOTO2")){
						m.put("FD_PHOTO2", showPic(m.get("FD_PHOTO2").toString()));
					}else{
						
						m.put("FD_PHOTO2", "");
					}
					if(null != m.get("FD_PHOTO3")){
						m.put("FD_PHOTO3", showPic(m.get("FD_PHOTO3").toString()));
					}else{
						
						m.put("FD_PHOTO3", "");
					}
					
				}
				
				
			        json.put("listAM", listAM);
				
			}else{
				
				 json.put("listAM", "");
			}

			// 中午
			List<Map> listNOON = integrateManager.queryMenus(fdiType,
					FsDishesIssue.FSD_FLAG_NOON);
			
			if (listNOON != null) {
				for (Map m : listNOON) {
					if(null != m.get("FD_PHOTO1")){
						m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
					}else{
						
						m.put("FD_PHOTO1", "");
					}
					if(null != m.get("FD_PHOTO2")){
						m.put("FD_PHOTO2", showPic(m.get("FD_PHOTO2").toString()));
					}else{
						
						m.put("FD_PHOTO2", "");
					}
					if(null != m.get("FD_PHOTO3")){
						m.put("FD_PHOTO3", showPic(m.get("FD_PHOTO3").toString()));
					}else{
						
						m.put("FD_PHOTO3", "");
					}
					
				}
				
				json.put("listNOON", listNOON);
			}else{
				
				 json.put("listNOON", "");
			}
			 //晚上
			List<Map> listtPM =   integrateManager.queryMenus(fdiType,
					FsDishesIssue.FSD_FLAG_PM);//
			
			if(listtPM!=null){
				for (Map m : listtPM) {
					if(null != m.get("FD_PHOTO1")){
						m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
					}else{
						
						m.put("FD_PHOTO1", "");
					}
					if(null != m.get("FD_PHOTO2")){
						m.put("FD_PHOTO2", showPic(m.get("FD_PHOTO2").toString()));
					}else{
						
						m.put("FD_PHOTO2", "");
					}
					if(null != m.get("FD_PHOTO3")){
						m.put("FD_PHOTO3", showPic(m.get("FD_PHOTO3").toString()));
					}else{
						
						m.put("FD_PHOTO3", "");
					}
					
				}
				
					json.put("listtPM", listtPM);
				
			}else{
				
				 json.put("listtPM", "");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}*/
		public String ajaxWeekMenusQuery(){
			String fdiType = getRequest().getParameter("id");
			String fsdFlag = getRequest().getParameter("FSD_FLAG");
			if (StringUtil.isEmpty(fsdFlag))
			{
				fsdFlag = FsDishesIssue.FSD_FLAG_AM;
			}
			this.getRequest().setAttribute("fdiType", fdiType);
			try {
				List<List> list = null;
				list = integrateManager.queryMenus(fdiType, fsdFlag);	
				String noFoodPic = getRequest().getContextPath()+"/app/integrateservice/images/food_no.jpg";
				if (list != null) {
					for (List<Map> l : list) {
						for (Map m : l) {
							if(null != m.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO1").toString()))){
								m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
							}else{
								m.put("FD_PHOTO1", noFoodPic);
							}
						}
					}
					this.getRequest().setAttribute("list", list);
				}
				else
				{
				 
					if (FsDishesIssue.FSD_FLAG_AM.equals(fsdFlag))
					{
						this.getRequest().setAttribute("msg", "早餐尚未发布"); 
					}
					else if (FsDishesIssue.FSD_FLAG_NOON.equals(fsdFlag))
					{
						this.getRequest().setAttribute("msg", "午餐尚未发布"); 
					}
					else if (FsDishesIssue.FSD_FLAG_PM.equals(fsdFlag))
					{
						this.getRequest().setAttribute("msg", "晚餐尚未发布"); 
					}
					else
					{
						this.getRequest().setAttribute("msg", "食堂尚未发布饭菜"); 
					}
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return SUCCESS;
		}

	 
	/**
	* 读取照片
	* @title: showPic 
	* @description: TODO
	* @author gxh
	* @param userPhoto
	* @return
	* @date 2013-4-17 上午11:07:05     
	*/
	private String showPic(String userPhoto){
		String contents= "" ;
		try {
			File f=new File(userPhoto);
			if(f.exists()) {
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(userPhoto));
		        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		        byte[] temp = new byte[1024*1024];
		        int size = 0;         
		        while ((size = in.read(temp)) != -1) {
		            out.write(temp, 0, size);         
		        }
		        in.close();
		        byte[] content = out.toByteArray();
		        contents = EncodeUtil.base64Encode(content);
		        contents = "data:image/x-icon;base64,"+contents;
			} else {
				// System.out.println("文件不存在"); 去掉ER B 代码
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}
	
	/**
	* 
	* @title: amplify 
	* @description: 放大机构树
	* @author zh
	* @date 2013-5-11
	*/
	public String amplify(){
//		System.out.println(getRequest().getParameter("showinfo"));
//		getRequest().setAttribute("showinfo", getRequest().getParameter("showinfo"));
		getRequest().setAttribute("type", "tel");
		getRequest().setAttribute("orgId", Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
		// 取得用户的IP电话信息
		String hql = "select t from TcIpTel t, NjhwUsersExp e, Users u where e.telId = t.telId and e.userid = u.userid and u.userid=?";
		List<TcIpTel> ipTelList = this.integrateManager.findByHQL(hql, Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		if (ipTelList.size() > 0 && ipTelList != null) {
			getRequest().setAttribute("smac", ipTelList.get(0).getTelMac());
			getRequest().setAttribute("stel", ipTelList.get(0).getTelNum());
		}
		return SUCCESS;
	}
	
	
	/**
	 * Ajax查询空调
	 * @return
	 */
	public String queryConditionerTemperature(){
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		NjhwUsersExp ne = personExpManager.getpsByid(new BigDecimal(userId).longValue());
		//默认是管理员房间，D410，还是没有房间空调就不显示
		long roomId = 6626;
		if(ne.getRoomId() != null){
			roomId = ne.getRoomId();
		}
		// 查询室内空调温度
		List<Objtank> coolList = buildAutoToAppService.loadObjTankByRoomId(roomId+"",Objtank.EXT_RES_TYPE_7);
		String[] coolNos = new String[]{};
		String floorType = "A";//楼标识
		//判断房间是否只有一个空调还是两个空调
		if(coolList!=null && coolList.size() > 0){
			String title = coolList.get(0).getTitle();
			if(title!=null){
				if(title.startsWith("A")){
					floorType = "A";
				}else if(title.startsWith("B")){
					floorType = "B";
				}else if(title.startsWith("C")){
					floorType = "C";
				}else if(title.startsWith("D")){
					floorType = "D";
				}
			}
			if(coolList.size() == 1 || coolList.size() > 2)
			{	
				coolNos  = new String[1];
				coolNos[0] = coolList.get(0).getKeyword();
			}else{
				coolNos  = new String[2];
				coolNos[0] = coolList.get(0).getKeyword();
				coolNos[1] = coolList.get(1).getKeyword();
			}
		}
		
		//coolNos  = new String[1];
		//coolNos[0] = "F4/F4_411";
		
		String temperature = buildAutoToAppService.getBuildConditionerTemperature(coolNos,"room",floorType).get("value");
		// 查询空调温度
		Map<String,String> m = buildAutoToAppService.getBuildConditionerTemperature(coolNos,"conditioner",floorType);
		String conditionerTemperature = m.get("value");
		String coolId = m.get("coolId");
		Map result = new HashMap();
		result.put("temperature", temperature);
		result.put("conditionerTemperature", conditionerTemperature);
		result.put("coolId", coolId);
		if(m.get("openStatus") == null || "close".equals(m.get("openStatus"))){
			result.put("openStatus", "close");
		}else{
			result.put("openStatus", "open");
		}
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	* 查找一个菜
	* @title: menusOneQuery 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-5-13 上午11:43:25     
	* @throws
	 */
	public String menusOneQuery(){
		FsDishes fs=integrateManager.menusOneQuery(Long.parseLong(fdId));
		if(fs!=null){
			this.getRequest().setAttribute("fs", fs);
			if(fs.getFdPhoto1()!=null){
			String src1=showPic(fs.getFdPhoto1());
			this.getRequest().setAttribute("src1", src1);
			}
			if(fs.getFdPhoto2()!=null){
				String src2=showPic(fs.getFdPhoto2());
				this.getRequest().setAttribute("src2", src2);
			}
		    if(fs.getFdPhoto3()!=null){
		    	String src3=showPic(fs.getFdPhoto3());
				this.getRequest().setAttribute("src3", src3);
		       }
		}
		return SUCCESS;
	}
	
	
	
	//----------------------------------------接口调用部分------------------------------------
	/**
	 * @description:开关控制
	 * @author zh
	 * @param deviceType	控制设备
	 * @param optId			设备id
	 * @param optType		on-开/off-关
	 * @param user_id		当前登陆人员id
	 * @return null
	 */
	public String controllerDevice() {
		try 
		{
			String deviceType = getParameter("deviceType").toString();
			String optType = getParameter("optType").toString();
			String temperature = getParameter("temperature");
			if(temperature == null){
				temperature = "0";
			}
			String coolId = getParameter("coolId");
			if(coolId == null){
				coolId = "";
			}
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			String doorId = "";		// 门锁ID
			String conResult = "";	// 返回结果
			String roomId = getParameter("roomId");
			if(roomId == null){
			    roomId = personExpManager.getpsByid(new BigDecimal(userId).longValue()).getRoomId()+"";
			}
			HashMap map = new HashMap();
			map.put("userId", userId);
			
			if ("light".equals(deviceType)) {	// 根据不同的设备调用对应的操作方法,第一个参数是灯的编号
				conResult = buildAutoToAppService.controlBuildLight(roomId, optType, userId);
				//conResult = "success"; 	// testdData
			} else if ("door".equals(deviceType)) {
				map.put("deviceType", 3);
				
				if (roomId != null && !"".equals(roomId)) {	// 直接根据房间ID查询出对应的门锁ID
					map.put("roomId", roomId);
					List<HashMap> list = integrateManager.loadDeviceByRoomId(map);
					if (list.size() > 0 && list != null) doorId = list.get(0).get("NODE_ID").toString();
				} else {	// 根据用户ID查询出对应的房间的门锁ID
					List<HashMap> list = integrateManager.loadDeviceId(map);
					if (list.size() > 0 && list != null) doorId = list.get(0).get("NODE_ID").toString();
				}
				
				UUID uuid = UUID.randomUUID();
				String msgId = uuid.toString();
				
				doorControlToAppService.controlDoor(doorId, optType, userId, msgId);
				
				JSONObject json = new JSONObject();
				json.put("msgId", msgId);
				Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
			}else if("conditioner".equals(deviceType)){//空调调节方法
				Double temp = Double.parseDouble(temperature);
				if("add".equals(optType)){
					temp = temp + 0.5;
				}else{
					temp = temp - 0.5;
				}
				String[] coolNos = coolId.split(",");
				conResult = buildAutoToAppService.controlBuildAirCondition(coolNos, optType, userId,temp);
			}
			Struts2Util.renderText(conResult, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			try {
				json.put("result", "false");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
		}
		return null;
	}
	
	public String jsonLight()
	{
		JSONObject json = new JSONObject();
		String conResult = lightOnOff();
		
		try {
			// 判断结果
			if ("true".equals(conResult)) 
			{
				json.put("status", "success");
			}
			else
			{
				json.put("status", "fail");
			}
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String jsonLightStatus()
	{
		JSONObject json = new JSONObject();
		
		try 
		{
			String roomId = getParameter("roomId").toString();
			List<Objtank> lightList = buildAutoToAppService.loadObjTankByRoomId(roomId,Objtank.EXT_RES_TYPE_6);
			String lightNo = "";
			if(lightList.size()>0){
				lightNo = lightList.get(0).getKeyword();
			}
			String status = buildAutoToAppService.getBuildLightStatus(lightNo);
			json.put("status", status);
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String lightOnOff()
	{
		String doorId = "";		// 门锁ID
		String conResult = "";	// 返回结果
		
		try 
		{
			String deviceType = getParameter("deviceType").toString();
			String optType = getParameter("optType").toString();
			
			//System.out.println("a:"+deviceType+"  b:"+optType);
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			String roomId =  getParameter("roomId").toString().trim();//personExpManager.getpsByid(new BigDecimal(userId).longValue()).getRoomId()+"";	//房间ID
			List<Objtank> lightList = buildAutoToAppService.loadObjTankByRoomId(roomId,Objtank.EXT_RES_TYPE_6);
			String lightNo = "";
			if(lightList.size()>0){
				lightNo = lightList.get(0).getKeyword();
			}
			HashMap map = new HashMap();
			map.put("userId", userId);
			
			if ("light".equals(deviceType)) {	// 根据不同的设备调用对应的操作方法,第一个参数是灯的编号
				conResult = buildAutoToAppService.controlBuildLight(lightNo, optType, userId);
				//conResult = "success"; 	// testdData
			} else if ("door".equals(deviceType)) {
				map.put("deviceType", 3);
				
				if (roomId != null && !"".equals(roomId)) {	// 直接根据房间ID查询出对应的门锁ID
					map.put("roomId", roomId);
					List<HashMap> list = integrateManager.loadDeviceByRoomId(map);
					if (list.size() > 0 && list != null) doorId = list.get(0).get("NODE_ID").toString();
				} else {	// 根据用户ID查询出对应的房间的门锁ID
					List<HashMap> list = integrateManager.loadDeviceId(map);
					if (list.size() > 0 && list != null) doorId = list.get(0).get("NODE_ID").toString();
				}
				UUID uuid = UUID.randomUUID();
				String msgId = uuid.toString();
				doorControlToAppService.controlDoor(doorId, optType, userId, msgId);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return conResult;
	}
	
	/**
	* @title: inputCallPhone
	* @description: 拨号页面
	* @author zh
	* @return
	* @date 2013-5-16 下午05:20:40
	* @throws
	 */
    public String inputCallPhone() throws Exception{
    	String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
    	Map param = new HashMap();
    	if(userId != null)
    		param.put("userid",userId);
    	
    	List<Map> list = this.integrateManager.findListBySql("IntegrateSQL.selectTelNum",param);
		getRequest().setAttribute("selfMac", getParameter("selfMac"));
		getRequest().setAttribute("selfTel", getParameter("selfTel"));
		getRequest().setAttribute("called", getParameter("called"));
		String pname = "";
		String orgName = "";
		try {
			List<HashMap> telList = new ArrayList();
			HashMap parMap = new HashMap();
			if(getParameter("called") != null && getParameter("called") != ""){
				parMap.put("telNum", getParameter("called"));
				telList = this.integrateManager.findListBySql("IntegrateSQL.selectTelNum",parMap);
				if(telList.size()!=0){
					pname = telList.get(0).get("DISPLAY_NAME").toString();
					orgName = telList.get(0).get("NAME").toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("pname",  pname);
		getRequest().setAttribute("orgName",  orgName);
		if(list.size() != 0)
			getRequest().setAttribute("userCallId",list.get(0).get("TEL_NUM"));
		else
			getRequest().setAttribute("userCallId","");
    	return SUCCESS;
    }
    
    /**
	* @title: loadMACByTel
	* @description: 根据IP电话号加载对应的MAC地址
	* @author zh
	* @return
	* @date 2013-5-16 下午05:20:40
	* @throws
	 */
    public String loadMACByTel(){
    	JSONObject json = new JSONObject();
    	try {
			HashMap parMap = new HashMap();
			parMap.put("inputTel", getParameter("inputTel").toString());
			
			List<HashMap> telList = this.integrateManager.findListBySql("IntegrateSQL.loadMacByTelNum", parMap);
			if (telList != null && telList.size() > 0)  {
				json.put("status", 0);
				if(telList.get(0).get("TEL_MAC") == null)
					json.put("mac", 0);
				else  
					json.put("mac", telList.get(0).get("TEL_MAC").toString());
				json.put("name", telList.get(0).get("DISPLAY_NAME").toString());
				json.put("shortName", telList.get(0).get("SHORT_NAME").toString());
			} else {
				json.put("status", 1);
			}
			
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			Struts2Util.renderText("error", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * @description:IP拨号
	 * @author zh
	 * @param selfMac 		本机MAC	
	 * @param selfTel		本机号码
	 * @param called		被呼叫号码
	 * @return boolean
	 */
	public String ipCall() {
		try {
			String selfMac = getParameter("selfMac").toString();
			String selfTel = getParameter("selfTel").toString();
			String called = getParameter("called").toString();
			
	    	String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
	    	Map param = new HashMap();
	    	if(userId != null){
	    		param.put("userId", userId);
	    	}
	    	List<Map> list = new ArrayList();
	    	if(selfTel == null){
	    		list = this.integrateManager.findListBySql("IntegrateSQL.queryCall",param);
	    		if(list.size() != 0){
		    		if(list.get(0).get("TEL_MAC") != "")
		    			selfMac = list.get(0).get("TEL_MAC").toString();
		    		if(list.get(0).get("TEL_NUM") != "")
		    			selfTel = list.get(0).get("TEL_NUM").toString();
		    	}
	    	}else{
	    		param.put("telNum", selfTel);
	    		list = this.integrateManager.findListBySql("IntegrateSQL.queryCallByTelNum", param);
	    		if(list.get(0).get("TEL_MAC") != "")
	    			selfMac = list.get(0).get("TEL_MAC").toString();
	    		if(list.get(0).get("TEL_NUM") != "")
	    			selfTel = list.get(0).get("TEL_NUM").toString();
	    	}

	    	boolean isSuccess = iPPhoneSiteToAppService.dail(selfTel, selfMac, called);
			
			if (isSuccess)  Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
			else  Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			Struts2Util.renderText("error", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 没有权限返回错误页面
	 * @return
	 */
	public String error(){
		return SUCCESS;
	}
	
	/*管理员7专用*/
	public String contactBy7(){
String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		
		//1 代表 跳转到 APP 首页  0 表示到运营管理中心首页
		String isApp = Struts2Util.getSession().getAttribute("IS_APP").toString();
		
		/**
		 * 运营管理中心导航
		 * 只有登录的时候启用
		 */
		if (null != isApp && isApp.equals("0")){
			//修改 session 中状态标志 IS_APP
			Struts2Util.getSession().setAttribute("IS_APP", 1);
			return "operationManageIndex";
		}
		
		//超级管理员导航
		if(userId!=null && userId.trim().equals("1")){
			return "adminPage";
		}
		
		List<HashMap> orgNameList = personROManager.getGongYongAccountOrgName();
		if(orgNameList.size() != 0){
			Map m = orgNameList.get(0);
			String orgName = m.get("ORGNAME").toString().trim();
			
			if(orgName.equals("前台")){
				return "property1";
			}else if(orgName.equals("设备维护")){
				return "property2";
			}else if(orgName.equals("主管1")){
				return "property3";
			}else if(orgName.equals("主管2")){
				return "property4";
			}else if(orgName.equals("科长1")){
				return "property5";
			}else if(orgName.equals("科长2")){
				return "property6";
			}else if(orgName.equals("管理员")){
				//return "property7";
			}
		}
		
		// 查询电灯的状态		
		NjhwUsersExp ue = personExpManager.getpsByid(new BigDecimal(userId).longValue());
		String roomName = "";
		if(ue != null&& ue.getRoomId()!= null&& ue.getRoomInfo() != null){
			roomName = ue.getRoomInfo();
		}
		getRequest().setAttribute("roomName", roomName);
//		List<Objtank> lightList = buildAutoToAppService.loadObjTankByRoomId(roomId+"",Objtank.EXT_RES_TYPE_6);
//		String lightNo = "";
//		if(lightList.size()>0){
//			lightNo = lightList.get(0).getKeyword();
//		}
//		String status = buildAutoToAppService.getBuildLightStatus(lightNo);
//		getRequest().setAttribute("lightStatus", status);	// testdData
		
		// 查询室内空调温度
//		List<Objtank> coolList = buildAutoToAppService.loadObjTankByRoomId(roomId+"",Objtank.EXT_RES_TYPE_7);
//		String[] coolNos = new String[]{};
//		//判断房间是否只有一个空调还是两个空调
//		if(coolList!=null && coolList.size() > 0){
//			if(coolList.size() == 1 || coolList.size() > 2)
//			{	
//				coolNos  = new String[1];
//				coolNos[0] = coolList.get(0).getKeyword();
//			}else{
//				coolNos  = new String[2];
//				coolNos[0] = coolList.get(0).getKeyword();
//				coolNos[1] = coolList.get(1).getKeyword();
//			}
//		}
//		String temperature = buildAutoToAppService.getBuildConditionerTemperature(coolNos,"room");
//		// 查询空调温度
//		String conditionerTemperature = buildAutoToAppService.getBuildConditionerTemperature(coolNos,"conditioner");
//		getRequest().setAttribute("temperature", temperature);
//		getRequest().setAttribute("conditionerTemperature", conditionerTemperature);	
		// 查询门锁的状态
		getRequest().setAttribute("doorStatus", "off");	// testdData
		// 查询空调的状态
		getRequest().setAttribute("airConditionStatus", "off");	// testdData
		
//		HashMap map = new HashMap();
//		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
//		map.put("deviceType", 3);
//		List<HashMap> list = integrateManager.loadDeviceId(map);
//		if (list.size() > 0 && list != null) {
//			String doorId = list.get(0).get("NODE_ID").toString();
//			getRequest().setAttribute("doorStatus", doorControlToAppService.queryDoorStatus(doorId, "3C"));
//		}
		
		long topOrgId = 0;
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
		getRequest().setAttribute("orgId", topOrgId);
		
		// 取得用户的IP电话信息
		String hql = "select t from TcIpTel t, NjhwUsersExp e, Users u where e.telId = t.telId and e.userid = u.userid and u.userid=?";
		List<TcIpTel> ipTelList = this.integrateManager.findByHQL(hql, Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		if (ipTelList.size() > 0 && ipTelList != null) {
			getRequest().setAttribute("smac", ipTelList.get(0).getTelMac());
			getRequest().setAttribute("stel", ipTelList.get(0).getTelNum());
		}
		//菜单
		queryMenus();
		
		try {
			setUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public IntegrateManager getIntegrateManager() {
		return integrateManager;
	}

	public void setIntegrateManager(IntegrateManager integrateManager) {
		this.integrateManager = integrateManager;
	}

	public BuildAutoToAppService getBuildAutoToAppService() {
		return buildAutoToAppService;
	}

	public void setBuildAutoToAppService(BuildAutoToAppService buildAutoToAppService) {
		this.buildAutoToAppService = buildAutoToAppService;
	}

	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}

	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}

	public IPPhoneSiteToAppService getiPPhoneSiteToAppService() {
		return iPPhoneSiteToAppService;
	}

	public void setiPPhoneSiteToAppService(
			IPPhoneSiteToAppService iPPhoneSiteToAppService) {
		this.iPPhoneSiteToAppService = iPPhoneSiteToAppService;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public MsgBoardManager getMsgBoardManager() {
		return msgBoardManager;
	}

	public void setMsgBoardManager(MsgBoardManager msgBoardManager) {
		this.msgBoardManager = msgBoardManager;
	}

	public PersonRegOutManager getPersonROManager() {
		return personROManager;
	}
	
	public void setPersonROManager(PersonRegOutManager personROManager) {
		this.personROManager = personROManager;
	}
	public HomeManager getAppHomeManager() {
		return appHomeManager;
	}
	public void setAppHomeManager(HomeManager appHomeManager) {
		this.appHomeManager = appHomeManager;
	}

	public HomeAction getAppHomeAction() {
		return appHomeAction;
	}

	public void setAppHomeAction(HomeAction appHomeAction) {
		this.appHomeAction = appHomeAction;
	}

	public PersonnelExpInforManager getPersonExpManager() {
		return personExpManager;
	}

	public void setPersonExpManager(PersonnelExpInforManager personExpManager) {
		this.personExpManager = personExpManager;
	}
}
