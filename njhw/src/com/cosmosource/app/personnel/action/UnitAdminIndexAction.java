package com.cosmosource.app.personnel.action;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.personnel.service.UnitAdminManager;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.action.ExportAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/** 
* @description: 委办局管理员首页
* @author herb
* @date 2013-03-23
*/ 
public class UnitAdminIndexAction extends ExportAction<Object> {
	private static final int UNIT_ROOM_NO = 20;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6680076522371166415L;
	//委办局管理
	private UnitAdminManager unitAdminManager;
	
	private PersonCardQueryToAppService personCardQueryToAppService;
	@SuppressWarnings("unchecked")
	private Page<Map> page = new Page<Map>(UNIT_ROOM_NO);//默认每页20条记录
	private Objtank tank = new Objtank();
	private List<Map> roomInfos = new ArrayList<Map>();
	
    //cardId
	private   String cardId;
	// 创建卡的对象
	private NjhwTscard scardInfo;
	
	private String opt;

	private List<Map> pubCardList;
	
	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public Object getModel() {
		return tank;
	}
	/**
	 * 
	* @title: index 
	* @description: 初始化首页
	* @author herb
	* @return
	* @date May 3, 2013 3:41:13 PM     
	* @throws
	 */
	public String index() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: disResultPage 
	* @description: 资源分配结果页面
	* @author herb
	* @return
	* @date Jun 1, 2013 7:12:59 PM     
	* @throws
	 */
	public String disResultPage(){
		Objtank room = (Objtank)unitAdminManager.findById(Objtank.class, Long.valueOf(this.getRequest().getParameter("roomId")));
		this.getRequest().setAttribute("type", this.getRequest().getParameter("type"));
		this.getRequest().setAttribute("roomId", this.getRequest().getParameter("roomId"));
		this.getRequest().setAttribute("room", room);
		return SUCCESS;
	}
	
	
	
	/**
	 * 
	* @title: roomDistribute 
	* @description: 房间分配新规
	* @author huangyongfa
	* @return
	* @date 2013-07-10   
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String roomDistributeNew() {
		//查询分配给单位(委办局的房间)
		Long orgId = Long.valueOf(Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
		int pageNo = 1;
		if (null != this.getRequest().getParameter("pageNo")) {
			pageNo = Integer.valueOf(this.getRequest().getParameter("pageNo"));
		}
		Map pMap = new HashMap();
		pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		List<Org> orgList = unitAdminManager.findListBySql("PersonnelSQL.getOrgByManager", pMap);
		if (null != orgList && orgList.size() > 0) {
			orgId = orgList.get(0).getOrgId();
		}
		
		page.setPageSize(Integer.MAX_VALUE);
		page.setPageNo(pageNo);
		page = unitAdminManager.getUnitResRooms(page,orgId,EmOrgRes.EOR_TYPE_ROOM);
		
		/*处理房间信息*/
		/*if (page.getResult() != null && page.getResult().size() > 0) {
			for (Map map : page.getResult()){
				房间电话是否正确接入INNER_STATUS[1:无接入;2:正确接入;3:错误接入]
				String roomId = map.get("RES_ID").toString();
				if (null != roomId && roomId.trim().length() > 0) {
					//得到分配到房间的ip电话[mac]列表
					List<Map> roomTelMacList = unitAdminManager.findRoomTelMacList(roomId);
					//得到接入的ip电话[mac]列表
					List<Map> innerTelMacList = unitAdminManager.findInnerTelMacList(roomId);
					map.put("INNER_STATUS", "1");
					if (innerTelMacList != null && innerTelMacList.size() > 0) {
						map.put("INNER_STATUS", "2");
					}
					
					//加载房间人员
					map.put("USER_NAMES","");
					List<Map> personList = unitAdminManager.findRoomUserList(roomId);
					if (null != personList && personList.size() > 0){
						String userNames = "";
						for (Map m : personList) {
							userNames += m.get("DISPLAY_NAME").toString().trim() + ",";
						}
						userNames = userNames.substring(0, userNames.lastIndexOf(","));
						map.put("USER_NAMES",userNames);
					}
					
				}
				
			}
		}*/
		
		//不足够UNIT_ROOM_NO个房间的，补充空数据
		int needEmpty = 0;
		if (page.getResult() == null || page.getResult().size() == 0) {
			needEmpty = UNIT_ROOM_NO;
			return "noRoom";
		} else if (page.getResult().size() < UNIT_ROOM_NO) {
			needEmpty = UNIT_ROOM_NO - page.getResult().size();
		}
		if (needEmpty > 0) {
			List<Map> list = page.getResult();
			for (int i = 0 ; i < needEmpty ; i++) {
				Map emptyMap = new HashMap();
				emptyMap.put("RES_ID", "0");
				list.add(emptyMap);
			}
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 
	* @title: roomDistribute 
	* @description: 资源分配
	* @author herb
	* @return
	* @date May 4, 2013 11:53:56 AM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String roomDistribute() {
		//查询分配给单位(委办局的房间)
		Long orgId = Long.valueOf(Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
		int pageNo = 1;
		if (null != this.getRequest().getParameter("pageNo")) {
			pageNo = Integer.valueOf(this.getRequest().getParameter("pageNo"));
		}
		Map pMap = new HashMap();
		pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		List<Org> orgList = unitAdminManager.findListBySql("PersonnelSQL.getOrgByManager", pMap);
		if (null != orgList && orgList.size() > 0) {
			orgId = orgList.get(0).getOrgId();
		}
		
		page.setPageSize(Integer.MAX_VALUE);
		page.setPageNo(pageNo);
		page = unitAdminManager.getUnitResRooms(page,orgId,EmOrgRes.EOR_TYPE_ROOM);
		
		/*处理房间信息*/
		if (page.getResult() != null && page.getResult().size() > 0) {
			for (Map map : page.getResult()){
				/*房间电话是否正确接入INNER_STATUS[1:无接入;2:正确接入;3:错误接入]*/
				String roomId = map.get("RES_ID").toString();
				if (null != roomId && roomId.trim().length() > 0) {
					//得到分配到房间的ip电话[mac]列表
					List<Map> roomTelMacList = unitAdminManager.findRoomTelMacList(roomId);
					//得到接入的ip电话[mac]列表
					List<Map> innerTelMacList = unitAdminManager.findInnerTelMacList(roomId);
					
//					if ((null == roomTelMacList || roomTelMacList.size() < 1 )&&(innerTelMacList == null || innerTelMacList.size() < 1)) {
//						map.put("INNER_STATUS", "1");
//					} else if (innerTelMacList.size() > roomTelMacList.size()) {
//						map.put("INNER_STATUS", "3");
//					} else if (innerTelMacList.size() == roomTelMacList.size())  {//比较两个mac是否完全一致
//						map.put("INNER_STATUS", "2");
//						for (Map inn : innerTelMacList){
//							boolean innerFlag = false;
//							for (Map room : roomTelMacList){
//								if (inn.get("TEL_MAC").equals(room.get("TEL_MAC"))) {
//									innerFlag = true;
//									break;
//								} 
//							}
//							if (innerFlag == false) {
//								map.put("INNER_STATUS", "3");
//								break;
//							}
//						}
//					} else {
//						map.put("INNER_STATUS", "2");
//					}
					
					map.put("INNER_STATUS", "1");
					if (innerTelMacList != null && innerTelMacList.size() > 0) {
						map.put("INNER_STATUS", "2");
					}
					
					
					//加载房间人员
					map.put("USER_NAMES","");
					List<Map> personList = unitAdminManager.findRoomUserList(roomId);
					if (null != personList && personList.size() > 0){
						String userNames = "";
						for (Map m : personList) {
							userNames += m.get("DISPLAY_NAME").toString().trim() + ",";
						}
						userNames = userNames.substring(0, userNames.lastIndexOf(","));
						map.put("USER_NAMES",userNames);
					}
					
				}
				
			}
		}
		
		
		//不足够UNIT_ROOM_NO个房间的，补充空数据
		int needEmpty = 0;
		if (page.getResult() == null || page.getResult().size() == 0) {
			needEmpty = UNIT_ROOM_NO;
		} else if (page.getResult().size() < UNIT_ROOM_NO) {
			needEmpty = UNIT_ROOM_NO - page.getResult().size();
		}
		if (needEmpty > 0) {
			List<Map> list = page.getResult();
			for (int i = 0 ; i < needEmpty ; i++) {
				Map emptyMap = new HashMap();
				emptyMap.put("RES_ID", "0");
				list.add(emptyMap);
			}
		}
		
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: resourceDistribute 
	* @description: 资源分配
	* @author herb
	* @return
	* @date May 4, 2013 11:53:56 AM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String resourceDistribute() {
		//查询分配给单位(委办局的房间)
		Long orgId = Long.valueOf(Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
		int pageNo = 1;
		if (null != this.getRequest().getParameter("pageNo")) {
			pageNo = Integer.valueOf(this.getRequest().getParameter("pageNo"));
		}
		Map pMap = new HashMap();
		pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		List<Org> orgList = unitAdminManager.findListBySql("PersonnelSQL.getOrgByManager", pMap);
		if (null != orgList && orgList.size() > 0) {
			orgId = orgList.get(0).getOrgId();
		}
		
		page.setPageSize(Integer.MAX_VALUE);
		page.setPageNo(pageNo);
		//page = unitAdminManager.getUnitRes(page,orgId,EmOrgRes.EOR_TYPE_ROOM);
		
		/*处理房间信息*/
		if (page.getResult() != null && page.getResult().size() > 0) {
			for (Map map : page.getResult()){
				/*房间电话是否正确接入INNER_STATUS[1:无接入;2:正确接入;3:错误接入]*/
				String roomId = map.get("RES_ID").toString();
				if (null != roomId && roomId.trim().length() > 0) {
					//得到分配到房间的ip电话[mac]列表
					List<Map> roomTelMacList = unitAdminManager.findRoomTelMacList(roomId);
					//得到接入的ip电话[mac]列表
					List<Map> innerTelMacList = unitAdminManager.findInnerTelMacList(roomId);
					map.put("INNER_STATUS", "1");
					if (innerTelMacList != null && innerTelMacList.size() > 0) {
						map.put("INNER_STATUS", "2");
					}
					//加载房间人员
					map.put("USER_NAMES","");
					List<Map> personList = unitAdminManager.findRoomUserList(roomId);
					if (null != personList && personList.size() > 0){
						String userNames = "";
						for (Map m : personList) {
							userNames += m.get("DISPLAY_NAME").toString().trim() + ",";
						}
						userNames = userNames.substring(0, userNames.lastIndexOf(","));
						map.put("USER_NAMES",userNames);
					}
					
				}
				
			}
		}
		//不足够UNIT_ROOM_NO个房间的，补充空数据
		int needEmpty = 0;
		if (page.getResult() == null || page.getResult().size() == 0) {
			needEmpty = UNIT_ROOM_NO;
		} else if (page.getResult().size() < UNIT_ROOM_NO) {
			needEmpty = UNIT_ROOM_NO - page.getResult().size();
		}
		if (needEmpty > 0) {
			List<Map> list = page.getResult();
			for (int i = 0 ; i < needEmpty ; i++) {
				Map emptyMap = new HashMap();
				emptyMap.put("RES_ID", "0");
				list.add(emptyMap);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: getOrgUserTree 
	* @description: 加载组织人员树
	* @author herb
	* @return
	* @date May 3, 2013 9:54:48 PM     
	* @throws
	 */
	public String getOrgUserTree() {
		//查询分配给单位(委办局的房间)
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		String nodeId  = getParameter("roomId");
		orgId = "109";
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: editRoomInfo 
	* @description: 修改房间信息
	* @author herb
	* @return
	* @date May 4, 2013 5:24:03 PM     
	* @throws
	 */
	public String editRoomInfo(){
		String nodeId  = getParameter("roomId");
		if (nodeId != null && nodeId.trim().length() > 0){
			tank = (Objtank)unitAdminManager.findById(Objtank.class, Long.valueOf(nodeId));
		}
		this.getRequest().setAttribute("pageNo", this.getRequest().getParameter("pageNo"));
		return SUCCESS;
	}
	
	
	/**
	 * 
	* @title: getRoomById 
	* @description: 根据房间ID，查询房间信息
	* @author huangyongfa
	* @return
	* @date     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String getRoomById(){
		JSONObject jo = null; 
		Objtank roomInfo = null;
		String roomId  = getParameter("roomId");
		try {
			if (null != roomId) {
				roomInfo = (Objtank)unitAdminManager.findById(Objtank.class, Long.valueOf(roomId));
				jo = new JSONObject();
				jo.put("room", roomInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(jo,"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: roomEditSave 
	* @description: 保存房间编辑
	* @author herb
	* @return
	* @date May 4, 2013 6:35:17 PM     
	* @throws
	 */
	public String roomEditSave(){
		try {
			if (tank.getNodeId() > 0) {
				Objtank dbTank = (Objtank)unitAdminManager.findById(Objtank.class, tank.getNodeId());
				dbTank.setTitle(tank.getTitle());
				dbTank.setLastUpdateBy(Long.valueOf(this.getRequest().getSession().getAttribute(Constants.USER_ID).toString()));
				dbTank.setLastUpdateDate(new Date());
				unitAdminManager.roomEditSave(dbTank);
				tank = dbTank;
			}
			setIsSuc("true");
		} catch(Exception e){
			setIsSuc("false");
			e.printStackTrace();
		}
		this.getRequest().setAttribute("pageNo", this.getRequest().getParameter("pageNo"));
		return SUCCESS;
		
	}
	
	/**
	 * 
	* @title: showDistributeRoom 
	* @description: 得到房间人员分配
	* @author herb
	* @return
	* @date May 5, 2013 1:38:22 PM     
	* @throws
	 */
	public String showDistributeRoom(){
		List<Map> list = null;
		try{
			String nodeId  = getParameter("roomId");
			if (null != nodeId) {
				list = unitAdminManager.findRoomUserList(nodeId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		Struts2Util.renderJson(list,"encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 
	* @title: showIPPhoneInner 
	* @description: 显示ip电话接入
	* @author herb
	* @return
	* @date May 5, 2013 1:39:02 PM     
	* @throws
	 */
	public String showIPPhoneInner(){
		String roomId  = getParameter("roomId");
		//得到分配到房间的ip电话[mac]列表
		List<Map> roomTelMacList = null;
		//得到接入的ip电话[mac]列表
		List<Map> innerTelMacList = null;
		JSONObject json = new JSONObject();
		try {
			if (null != roomId) {
				roomTelMacList = unitAdminManager.findRoomTelMacList(roomId);
				innerTelMacList = unitAdminManager.findInnerTelMacList(roomId);
				if (null != roomTelMacList && roomTelMacList.size() > 0) {
					JSONArray roomTel = new JSONArray();
					for (Map m : roomTelMacList) {
						if (null != m.get("TEL_MAC") && m.get("TEL_MAC").toString().trim().length() > 0){
							JSONObject mJson = new JSONObject();
							mJson.put("TEL_MAC",m.get("TEL_MAC"));
							roomTel.add(mJson);
						}
					}
					json.put("roomTelMacList", roomTel);
				}
				if (null != innerTelMacList && innerTelMacList.size() > 0) {
					JSONArray innerTel = new JSONArray();
					for (Map m : innerTelMacList) {
						if (null != m.get("TEL_MAC") && m.get("TEL_MAC").toString().trim().length() > 0){
							JSONObject mJson = new JSONObject();
							mJson.put("TEL_MAC",m.get("TEL_MAC"));
							innerTel.add(mJson);
						}
					}
					json.put("innerTelMacList", innerTel);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		Struts2Util.renderJson(json,"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: showDistributeLock 
	* @description: 显示房间门锁分配
	* @author herb
	* @return
	* @date May 5, 2013 1:40:39 PM     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String showDistributeLock(){
		JSONArray json = null; 
		String roomId  = getParameter("roomId");
		try {
			if (null != roomId) {
				//得到房间的全部门锁
				List<Map> resList = unitAdminManager.getRoomRes("3",roomId);
				if (null != resList && resList.size() > 0) {
					json = new JSONArray();
					for (Map m : resList) {
						if (null != m.get("NODE_ID") && m.get("NODE_ID").toString().trim().length() > 0) {
							JSONObject mObj = new JSONObject();
							mObj.put("NODE_ID",  m.get("NODE_ID").toString().trim());
							mObj.put("NAME",  m.get("NAME").toString().trim());
							String nodeId = m.get("NODE_ID").toString().trim();
							
							//清除所有添加授权失败和删除授权失败的信息
							if (!"YES".equals(getParameter("isAuth"))) {
								unitAdminManager.removeErrorDoorInfo(nodeId);
							}
							//得到门锁的全部权限人
							List<Map> userList = unitAdminManager.getResPermission("user", nodeId);
							if (null != userList && userList.size() > 0) {
								JSONArray userArr = new JSONArray();
								for (Map u : userList) {
									JSONObject uObj = new JSONObject();
									uObj.put("USERID", u.get("USERID"));
									uObj.put("DISPLAY_NAME", u.get("DISPLAY_NAME"));
									if (null != u.get("NOCARDUSERID") && !String.valueOf(u.get("NOCARDUSERID")).isEmpty()) {
										uObj.put("ID", "9");
									} else if (null == u.get("CARDSTATUS") || String.valueOf(u.get("CARDSTATUS")).isEmpty()) {
										uObj.put("ID", u.get("ID"));
									} else if ("0".equals(String.valueOf(u.get("CARDSTATUS")))) {
										uObj.put("ID", "5");
									} else if ("1".equals(String.valueOf(u.get("CARDSTATUS")))) {
										uObj.put("ID", "6");
									} else if ("2".equals(String.valueOf(u.get("CARDSTATUS")))) {
										uObj.put("ID", "7");
									} else if ("3".equals(String.valueOf(u.get("CARDSTATUS")))) {
										uObj.put("ID", "8");
									}
									userArr.add(uObj);
								}
								mObj.put("userList", userArr);
							}
							json.add(mObj);
							//只处理一个门锁
							break;
						}
						
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json,"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: showDisLockStatus 
	* @description: 显示远程开门是否成功
	* @author herb
	* @return
	* @date May 5, 2013 1:40:39 PM     
	* @throws
	 */
	public String checkOpenDoorResult()
	{  
		String msgId  = getParameter("msgId");
		JSONObject exp4Mapsjs = new JSONObject();
		List<Map> bm = unitAdminManager.getDoorStatus(msgId);
		if (bm != null && !bm.isEmpty()) {
			exp4Mapsjs.put("msg", String.valueOf(bm.get(0).get("BM_EXP4")));
		} else {
			exp4Mapsjs.put("msg", "NONE");
		}

		Struts2Util.renderJson(exp4Mapsjs.toString(),"encoding:UTF-8", "no-cache:true");
		return null;
		
	}
	
	/**
	 * 
	* @title: exportRoomInfo
	* @description: 房间分配及授权管理清单导出
	* @author qiyanqiang
	* @return
	* @date May 5
	* @throwshttp://mail.cosmosource.com:8080/webmail/login9.php
	 */
	
	@SuppressWarnings("unchecked")
	public  String exportRoomInfo ()
	{  
		HashMap map = new HashMap();
		// 获取机构ID 
		map.put("orgId", Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    //调用service层
        list= unitAdminManager.exportRoomInfo(map);
        //获取模板路径
       // String  sourcePath = this.getRequest().getSession().getServletContext().getRealPath("\\");
		//String templateFileName =  sourcePath+"excelTemplateFile"+"\\"+"exportRoomInfo.xls";
       String templateFileName ="com/cosmosource/app/template/excel/exportRoomInfo.xls";
		//判断模板文件是否存在
		//File file = new File(templateFileName);
		//if (file.exists()){
		//	System.out.println("文件存在！");
		//}
        Map<String,Object> context = new HashMap<String,Object>();
        if(list.size()==0){
        	list = null;
        }
        String dateStr = DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd");
        context.put("list", list);
        context.put("dateStr", dateStr);
        //export excel
		try {
			String filename = "exportRoomInfo12";
			 //调用底层方法
			executeExportExcel(filename, templateFileName, context);
			
		} catch (Exception e) {
			logger.info("generate excel failure!");
			logger.debug(e.getMessage());
			e.printStackTrace();
		
		} 
		
	return  null;
	}
	
	/**
	 * 
	 * @描述:读取卡权限的操作
	 * @return
	 * @throws Exception
	 * @作者： qiyanqiang
	 */
	@SuppressWarnings("unchecked")
	public String  readCardPrivileges() throws Exception {
		try {
		HashMap map = new HashMap();
		//调用service层查询userId
		if (null == cardId || cardId.trim().length() < 1) {
			return SUCCESS;
		}
		long  userId = unitAdminManager.searchUserId(cardId);
		if(userId != 0)
		{   
			Users curUser = (Users)unitAdminManager.findById(Users.class, userId);
			Org curUserOrg = (Org)unitAdminManager.findById(Org.class, curUser.getOrgId());
			NjhwUsersExp curUserExp = unitAdminManager.findUserExp(userId);
			NjhwTscard curUserCard = unitAdminManager.findUserCard(userId);
			HashMap curUserUnit = unitAdminManager.getTopOrgByUserId(userId);
			getRequest().setAttribute("curUser", curUser);
			getRequest().setAttribute("curUserOrg", curUserOrg);
			getRequest().setAttribute("curUserExp", curUserExp);
			getRequest().setAttribute("curUserCard", curUserCard);
			getRequest().setAttribute("curUserUnit", curUserUnit);
			
			
			map.put("userId", userId);
			//调用service 层查询用户的车牌 
			String plates = "";
			List<HashMap> plateList = unitAdminManager.searchUserLicensePlate(map);
			if (null != plateList && plateList.size() > 0){
				String ptype = "";
				for (int i = 0 ; i < plateList.size() ; i++){
					HashMap m = plateList.get(i);
					if(m.get("NUP_PN").toString().trim().equals("2")){
						ptype = "(内部车位)";
					} 
					if (i!=0){
						plates += ",";
					}
					plates += m.get("NUP_PN")+ptype;
				}
			}
			getRequest().setAttribute("plates",plates);
			//调用service 层查询用户闸机、门禁、门锁
			List<HashMap> allFacilityList = unitAdminManager.searchAllFacility(map);
			String zjPermits = "";
			String mjPermits = "";
			String msPermits = "";
			if (null != allFacilityList && allFacilityList.size() > 0){
				for (HashMap m : allFacilityList) {
					if (m.get("RES_TYPE").toString().trim().equals("1")){//闸机
						zjPermits += m.get("NAME").toString()+",";
					} else if (m.get("RES_TYPE").toString().trim().equals("2")){//门禁
						mjPermits += m.get("NAME").toString()+",";
					} else if (m.get("RES_TYPE").toString().trim().equals("3")){//门锁
						msPermits += m.get("NAME").toString()+",";
					}
				}
				if (!zjPermits.equals(""))
				zjPermits = zjPermits.substring(0,zjPermits.lastIndexOf(","));
				if (!mjPermits.equals(""))
				mjPermits = mjPermits.substring(0,mjPermits.lastIndexOf(","));
				if (!msPermits.equals(""))
				msPermits = msPermits.substring(0,msPermits.lastIndexOf(","));
			}
			
			getRequest().setAttribute("zjPermits",zjPermits);
			getRequest().setAttribute("mjPermits",mjPermits);
			getRequest().setAttribute("msPermits",msPermits);
			
		}
		
		} catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;	

	}
	
	
	/**
	 * 
	* @Title: playCardIndex 
	* @Description: 通卡一览页面
	* @author HJ
	* @date 2013-7-5  
	* @return String 
	* @throws
	 */
	public String playCardIndex() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: playCardAllIndex 
	* @Description: 全楼通卡一览页面
	* @author HJ
	* @date 2013-9-5  
	* @return String 
	* @throws
	 */
	public String playCardAllIndex() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: playCardInit 
	* @Description: TODO
	* @author HJ
	* @date 2013-8-29
	* @param @return    
	* @return String 
	* @throws
	 */
	public String playCardInit() {
		return SUCCESS;
	}
	
	public String playCardInit2() {
		setOpt(getParameter("opt"));
		return SUCCESS;
	}
	
	public String playCardData() {
		String cardId  = getParameter("cardId");
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		roomInfos = unitAdminManager.getPlayCardRoomsLocks(orgId, cardId);
		return SUCCESS;
	}
	
	public void checkPubCardNo() {
		String cardId  = getParameter("cardId");
		String opt  = getParameter("opt");
		JSONObject json = new JSONObject();
		try {
			String rtnCard = personCardQueryToAppService.queryPersonCardForPubCard(cardId);
			
			if ("8004".equals(rtnCard)) {
				String errorCode = this.unitAdminManager.checkPubCard(cardId, opt);
				if (errorCode == null) {
					json.put("isRight", "true");
				} else {
					json.put("isRight", "false");
					json.put("error", errorCode);
				}
			} else {
				json.put("isRight", "false");
				json.put("error", "A");
			}
		} catch (Exception e) {
			json.put("isRight", "false");
			json.put("error", "B");
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	public void checkPubCardNoAll() {
		String cardId  = getParameter("cardId");
		JSONObject json = new JSONObject();
		try {
			String rtnCard = personCardQueryToAppService.queryPersonCardForPubCard(cardId);
			
			if ("8004".equals(rtnCard)) {
				String errorCode = this.unitAdminManager.checkPubCard(cardId, "add");
				if (errorCode == null) {
					json.put("isRight", "true");
				} else {
					json.put("isRight", "false");
					json.put("error", errorCode);
				}
			} else {
				json.put("isRight", "false");
				json.put("error", "A");
			}
		} catch (Exception e) {
			json.put("isRight", "false");
			json.put("error", "B");
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	public void checkAdminCard() {
		JSONObject json = new JSONObject();
		String cardId  = getParameter("cardId");

		boolean isAdmin = unitAdminManager.isAdmin(cardId);
		json.put("isAdmin", isAdmin);

		
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	public void checkAdminCardForAll() {
		JSONObject json = new JSONObject();
		String cardId  = getParameter("cardId");

		boolean isAdmin = unitAdminManager.isAdmin(cardId);
		json.put("isAdmin", isAdmin);
		
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	/**
	 * 
	* @Title: showPubCardConfirm 
	* @Description: 通卡一览操作结果确认
	* @author HJ
	* @date 2013-7-5 
	* @return void 
	* @throws
	 */
	public void showPubCardConfirm() {
		JSONObject json = new JSONObject();
		String nodeId  = getParameter("nodeId");
		String cardId  = getParameter("cardId");
		String opt  = getParameter("opt");
		boolean isSuccess = unitAdminManager.confirmPubCard(cardId, nodeId, opt);

		json.put("isSuccess", isSuccess);
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	/**
	 * 
	* @Title: showPubCardConfirm 
	* @Description: 全楼通卡一览操作结果确认
	* @author HJ
	* @date 2013-8-29
	* @return void 
	* @throws
	 */
	public void showPubCardConfirmForAll() {
		JSONObject json = new JSONObject();
		String cardId  = getParameter("cardId");
		String nodeIds  = getParameter("nodeIds");
		
		List success = unitAdminManager.getPubCardConfirmInfo(cardId, nodeIds, "1");
		List fail = unitAdminManager.getPubCardConfirmInfo(cardId, nodeIds, "2");
		
		json.put("success", success);
		json.put("fail", fail);
		
		List l = unitAdminManager.getPubCardConfirmInfo(cardId, nodeIds, "3");
		if (l != null && l.size() == success.size() + fail.size()) {
			json.put("complete", "true");
		} else {
			json.put("complete", "false");
		}
		
		
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	
	/**
	 * 
	* @Title: showDeletePubCardConfirmForAll 
	* @Description: 全楼通卡一览操作删除结果确认
	* @author HJ
	* @date 2013-8-29
	* @return void 
	* @throws
	 */
	public void showDeletePubCardConfirmForAll() {
		JSONObject json = new JSONObject();
		String cardId  = getParameter("cardId");
		
		int num = unitAdminManager.getPubCardDoorNum(cardId);
		if (num > 0) {
			json.put("complete", "false");
		} else {
			json.put("complete", "true");
		}
		
		
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	/**
	 * 
	* @Title: showPubCardInfo 
	* @Description: 通卡一览页面显示
	* @author HJ
	* @date 2013-7-5 
	* @return String 
	* @throws
	 */
	public String showPubCardInfo() {
		pubCardList = unitAdminManager.getPubCardInfos();
		int emptyLine = 10-pubCardList.size();
		if (emptyLine > 0) {
			for (int i=0; i < emptyLine; i++) {
				pubCardList.add(null);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: showPubCardAllInfo 
	* @Description: 通卡一览页面显示
	* @author HJ
	* @date 2013-7-5 
	* @return String 
	* @throws
	 */
	public String showPubCardAllInfo() {
		pubCardList = unitAdminManager.getPubCardInfosForAll();
		int emptyLine = 16-pubCardList.size();
		if (emptyLine > 0) {
			for (int i=0; i < emptyLine; i++) {
				pubCardList.add(null);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: retryPubCard 
	* @Description: 通卡授权失败房间重新授权
	* @author HJ
	* @date 2013-9-9 
	* @return String 
	* @throws
	 */
	public String retryPubCard() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: delPubCard 
	* @Description: 通卡删除页面显示
	* @author HJ
	* @date 2013-7-5
	* @return String 
	* @throws
	 */
	public String delPubCard() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: deletePubCardAll 
	* @Description: 全楼通卡删除页面显示
	* @author HJ
	* @date 2013-9-9
	* @return String 
	* @throws
	 */
	public String deletePubCardAll() {
		String cardId  = getParameter("cardId");
		getRequest().setAttribute("cardId", cardId);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: delPubCard 
	* @Description: 通卡删除
	* @author HJ
	* @date 2013-7-5
	* @return String 
	* @throws
	 */
	public void deletePubCard() {
		String cardId  = getParameter("cardId");
		unitAdminManager.deletePubCard(cardId);
	}
	
	/**
	 * 
	* @Title: playCard 
	* @Description: TODO
	* @author WXJ
	* @date 2013-5-25 下午11:22:52 
	* @param @return    
	* @return String 
	* @throws
	 */
	public String playCard() {
		String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
		String cardId = getParameter("cardId");

		//根据cardId找到userId
		String userIdStr = unitAdminManager.getUserIdByCardId(cardId);
		if (userIdStr==null || "".equals(userIdStr)){
			setIsSuc("1");
			return SUCCESS;
		}
		Long userId = new Long(userIdStr);
			
		Users curUser = (Users)unitAdminManager.findById(Users.class, userId);
		Org curUserOrg = (Org)unitAdminManager.findById(Org.class, curUser.getOrgId());
		NjhwUsersExp curUserExp = unitAdminManager.findUserExp(userId);
		NjhwTscard curUserCard = unitAdminManager.findUserCard(userId);
		HashMap curUserUnit = unitAdminManager.getTopOrgByUserId(userId);
		getRequest().setAttribute("curUser", curUser);
		getRequest().setAttribute("curUserOrg", curUserOrg);
		getRequest().setAttribute("curUserExp", curUserExp);
		getRequest().setAttribute("curUserCard", curUserCard);
		getRequest().setAttribute("curUserUnit", curUserUnit);
		
		page.setPageSize(Integer.MAX_VALUE);
		// page = unitAdminManager.getPlayCardRoomsLocks(page,orgId,cardId);
		
		setIsSuc("0");
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: autoCreateUser 
	* @Description: 自动创建通卡用户
	* @author WXJ
	* @date 2013-5-25 下午11:22:52 
	* @param @return    
	* @return String 
	* @throws
	 */
//	public String autoCreateUser() {
//		try {
//			int num = this.unitAdminManager.saveAutoCreateUser(getParameter("cityCard"));
//			if (num == 0) Struts2Util.renderText("success");
//			else Struts2Util.renderText("fail");
//		} catch (Exception e) {
//			Struts2Util.renderText("error");
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public String checkCardId() {
		try {
			String cardId = getParameter("cardId");
			String userIdStr = unitAdminManager.getUserIdByCardId(cardId);
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: playCard 
	* @Description: TODO
	* @author WXJ
	* @date 2013-5-25 下午11:23:06 
	* @param @return    
	* @return String 
	* @throws
	 */
	public void playCardSave() {
		String loginId = Struts2Util.getSession().getAttribute(
				Constants.USER_ID).toString();
		String cardId = getParameter("cardId");
		String checked = getParameter("checked");
		String opt = getParameter("opt");
		String chk[] = null;
		if (!"del".equals(opt)) {
			if (null != checked && !StringUtil.isBlank(checked)) {
				chk = StringUtil.split(StringUtil.chop(checked), ',');
			}
		}
		
		if ("add".equals(opt)) {
			this.unitAdminManager.saveAutoCreateUser(cardId);
		}
		unitAdminManager.savePlayCard(cardId, chk, loginId);
	}
	
	/**
	 * 
	* @Title: retryPlayCardSave 
	* @Description: TODO
	* @author HJ
	* @date 2013-9-9
	* @param @return    
	* @return String 
	* @throws
	 */
	public void retryPlayCardSave() {
		String loginId = Struts2Util.getSession().getAttribute(
				Constants.USER_ID).toString();
		String cardId = getParameter("cardId");
		String nodeIds = getParameter("roomIds");
		unitAdminManager.retryPlayCardSave(cardId, nodeIds, loginId);
	}
	
	/**
	 * 
	* @Title: deleteCardSave 
	* @Description: TODO
	* @author HJ
	* @date 2013-9-9
	* @param @return    
	* @return void 
	* @throws
	 */
	public void deleteCardSave() {
		String loginId = Struts2Util.getSession().getAttribute(
				Constants.USER_ID).toString();
		String cardId = getParameter("cardId");
		unitAdminManager.deleteCardSave(cardId, loginId);
	}
	
	
	/**
	 * 
	* @Title: playCard 
	* @Description: TODO
	* @author HJ
	* @date 2013-8-29
	* @param @return    
	* @return String 
	* @throws
	 */
	public void playCardSaveForAll() {
		String cardId = getParameter("cardId");
		
		this.unitAdminManager.saveAutoCreateUserForAll(cardId);

	}
	
	/**
	 * 
	* @Title: playCard 
	* @Description: TODO
	* @author HJ
	* @date 2013-8-29
	* @param @return    
	* @return String 
	* @throws
	 */
	public void playCardSaveForAuth() {
		String loginId = Struts2Util.getSession().getAttribute(
				Constants.USER_ID).toString();
		String cardId = getParameter("cardId");

		unitAdminManager.savePlayCardForAll(cardId, loginId);
	}
	
	public UnitAdminManager getUnitAdminManager() {
		return unitAdminManager;
	}

	public void setUnitAdminManager(UnitAdminManager unitAdminManager) {
		this.unitAdminManager = unitAdminManager;
	}

	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}

	public void setPersonCardQueryToAppService(PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}
	
	public Page<Map> getPage() {
		return page;
	}

	public void setPage(Page<Map> page) {
		this.page = page;
	}

	public Objtank getTank() {
		return tank;
	}

	public void setTank(Objtank tank) {
		this.tank = tank;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public NjhwTscard getScardInfo() {
		return scardInfo;
	}

	public void setScardInfo(NjhwTscard scardInfo) {
		this.scardInfo = scardInfo;
	}

	public void setRoomInfos(List<Map> roomInfos) {
		this.roomInfos = roomInfos;
	}

	public List<Map> getRoomInfos() {
		return roomInfos;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getOpt() {
		return opt;
	}

	public void setPubCardList(List<Map> pubCardList) {
		this.pubCardList = pubCardList;
	}

	public List<Map> getPubCardList() {
		return pubCardList;
	}
	
}
