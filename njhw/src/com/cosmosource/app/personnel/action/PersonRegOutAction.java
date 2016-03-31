package com.cosmosource.app.personnel.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.cosmosource.app.entity.AttendanceApprovers;
import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.LeaderLevel;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersAccess;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.NjhwUsersPlatenum;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.app.ldap.service.LDAPService;
import com.cosmosource.app.personnel.model.PersonModel;
import com.cosmosource.app.personnel.service.AccessMgrManager;
import com.cosmosource.app.personnel.service.PersonRegOutManager;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.EncodeUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings("unchecked")
public class PersonRegOutAction extends BaseAction {
	
	private static final int UNIT_ROOM_NO = 20;
	
	// 定义全局变量
	private static final long serialVersionUID = 4227875753301925460L;
	// 注入对象
	private PersonRegOutManager personROManager;
	private AccessMgrManager accessMgrManager;
	private PersonCardQueryToAppService personCardQueryToAppService;
	// 定义模型
	private PersonModel personModel = new PersonModel();
	
	private Page<HashMap> page = new Page<HashMap>(UNIT_ROOM_NO);//默认每页20条记录
	
	/**
	 * 加载E座用户注册信息
	 * @return
	 */
	public String inputRegisterE4Personnel() {
		String orgId = getParameter("orgId");
		String userId = getParameter("userId");
		NjhwUsersExp exp = null;
		LeaderLevel ll = null;
		Users user = null;
		String cardId = null;
		if (StringUtil.isNotEmpty(userId)) {	// 编辑时加载用户各项信息
			// 用户扩展信息 
			List expList = personROManager.findByHQL("select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?", Long.parseLong(userId));
			if (expList.size() > 0 && expList != null)  exp = (NjhwUsersExp) expList.get(0);
			getRequest().setAttribute("exp", exp);
			List lll = personROManager.findByHQL("select ll from  LeaderLevel ll where ll.userid = ?",  Long.valueOf(userId));
			if (lll.size() > 0 && lll != null)  
			{
				ll  = (LeaderLevel) lll.get(0);
				getRequest().setAttribute("stage", ll.getStage());
			}
			else{
				getRequest().setAttribute("stage", 0);
			}
			
			// 考勤审批员
			Object  aa = personROManager.findById(AttendanceApprovers.class, Long.valueOf(userId));
			if(null != aa){
				List<String> apprNames = personROManager.findByHQL("select u.displayName from  Users u where u.userid in ("+((AttendanceApprovers)aa).getApprovers()+")");
				if(null != apprNames && apprNames.size() > 0){
					StringBuffer apprNamesBu = new StringBuffer();
					for(String appName:apprNames){
						apprNamesBu.append(appName.trim()).append(",");
					}
					getRequest().setAttribute("approvers", apprNamesBu.substring(0, apprNamesBu.length()-1));
				}
			}
			
			if(!personROManager.findByHQL("select oa from OrgAttendanceAdmin oa where oa.userid = ?", Long.valueOf(userId)).isEmpty()){
				this.getRequest().setAttribute("isOrgAttAdmin", "1");
			}else{
				this.getRequest().setAttribute("isOrgAttAdmin", "0");
			}

			if(exp!=null){
				/*if(exp.getTelNum()!=null){
					List telList = personROManager.findByHQL("select tel from  TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getTelNum()));
					if(telList!=null&&telList.size()>0){
						tel1 = (TcIpTel)telList.get(0);
					}
				}
				if(exp.getUepFax()!=null){
					List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getUepFax()));
					if(telList!=null&&telList.size()>0){
						tel2 = (TcIpTel)telList.get(0);
					}
				}*/
				TcIpTel tel3 = null;
				if(exp.getWebFax()!=null){
					List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getWebFax()));
					if(telList!=null&&telList.size()>0){
						tel3 = (TcIpTel)telList.get(0);
					}
				}
				getRequest().setAttribute("tel3", tel3);
			}
			// 用户基本信息 
			user = (Users)personROManager.findById(Users.class, Long.parseLong(userId));
			getRequest().setAttribute("user", user);
			if (null != user.getOrgId())
			{
				orgId = user.getOrgId().toString();
			}
			
			if (exp != null ) {
				// 根据扩展信息中保存的用户所拥有的卡类型去查询市民卡表
				if (exp.getCardType() != null && "1".equals(exp.getCardType())) {	// 1.市民卡2.临时卡
					List cardList = personROManager.findByHQL("select t from NjhwTscard t where t.userId = ?", Long.parseLong(userId));
					if (cardList.size() > 0 && cardList != null) {
						getRequest().setAttribute("card", cardList.get(cardList.size()-1));
						cardId = ((NjhwTscard)cardList.get(cardList.size()-1)).getCardId();
					}
				
				}
				else if (exp.getCardType() != null && "2".equals(exp.getCardType())) 
				{
					cardId= exp.getTmpCard();
				}
				// 读取用户上传照片
				if (StringUtil.isNotEmpty(exp.getUepPhoto())) {
					getRequest().setAttribute("imgSrc", showPic(exp.getUepPhoto().toString()));
				}
			}
			StringBuffer strAcc = new StringBuffer();
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			// 门禁 2013年8月22日16:26:05
			Long userid = Long.valueOf(userId);
			
			List<NjhwUsersAccess> list = accessMgrManager.findByHQL("select t from NjhwUsersAccess t where t.userid = ? and (t.appStatus = '1' or t.appStatus = '5')",
					userid);
			
			if (list != null && list.size() > 0) {
				NjhwUsersAccess entity = list.get(0);
				getRequest().setAttribute("name", user.getDisplayName());
				List<Map> l = accessMgrManager.getAccessGuardInfo(entity.getNuacId());
				if (l != null && l.size() > 0) {
					getRequest().setAttribute("strAcc", l.get(0).get("ACCESS_AUTH") == null? "正在删除申请中..." : l.get(0).get("ACCESS_AUTH").toString() + "    正在申请中...");
					getRequest().setAttribute("strGate", l.get(0).get("GUARD_AUTH") == null? "正在删除申请中..." : l.get(0).get("GUARD_AUTH").toString() + "    正在申请中...");
				}
			} else {
				Map<String, String> m = accessMgrManager.initUserAccessInfo(userid.toString());
				if (!"true".equals(m.get("isNone"))) {
					getRequest().setAttribute("strAcc", m.get("access"));
					getRequest().setAttribute("strGate", m.get("gate"));
				}
			}
		}
		long topOrgId = 0;
		// 当前选定的部门
		Org org = (Org)this.personROManager.findById(Org.class, Long.parseLong(orgId));
		getRequest().setAttribute("org", org);
		
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
		
		// 判断是否弹出框编辑
		getRequest().setAttribute("ispopup",getParameter("ispopup"));
		getRequest().setAttribute("backList",getParameter("backList"));
		// 筛选电话号码
		//if (exp != null && StringUtil.isNotEmpty(exp.getTelNum()))  getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("update", exp.getTelNum(), topOrgId));
		//else getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("add","", topOrgId));
		// 筛选IP电话机
		//if (exp != null && exp.getTelId() != null) getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("update",exp.getTelId(), topOrgId));
		//else getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("add",(long)0, topOrgId));

		// 分配至部门的房间列表
		//getRequest().setAttribute("roomList", this.personROManager.findByHQL("select t from EmOrgRes t where t.orgId = ? and t.eorType = ?", topOrgId, EmOrgRes.EOR_TYPE_ROOM));
		// 门锁权限
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("cardId", cardId);
		/*
		List<Map<String, Object>> roomList = personROManager.findListBySql("PersonnelSQL.getRoomIdByCardId", pMap);
		StringBuffer dRoomName = new StringBuffer();
		StringBuffer dRoomId = new StringBuffer();
		if (null!=roomList && roomList.size()>0)
		{   
			for (int i = 0; i < roomList.size(); i++)
			{   
				dRoomName.append(roomList.get(i).get("ROOM_NAME"));
				dRoomName.append("，");
				dRoomId.append(roomList.get(i).get("NODE_ID").toString());
				dRoomId.append(",");
			}
		}
		if (dRoomId.length()>0)
		{   
			getRequest().setAttribute("dRoomName", dRoomName.toString().substring(0, dRoomName.toString().length()-1));
			getRequest().setAttribute("dRoomId", dRoomId.toString().substring(0, dRoomId.toString().length()-1) );
		}
		*/
		return SUCCESS;
	}
	
	private void processUserEdit(String userId, Users user, NjhwUsersExp exp, String cardId) {
		
		LeaderLevel ll = null;
		TcIpTel tel1 = null;//ip电话
		TcIpTel tel2 = null;//传真
		TcIpTel tel3 = null;//网络传真
		
		
		// 用户扩展信息
//		List expList = personROManager.findByHQL(
//				"select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?",
//				Long.parseLong(userId));
//		if (expList.size() > 0 && expList != null)
//			exp = (NjhwUsersExp) expList.get(0);
		getRequest().setAttribute("exp", exp);
		List lll = personROManager.findByHQL("select ll from  LeaderLevel ll where ll.userid = ?",
				Long.valueOf(userId));
		if (lll.size() > 0 && lll != null) {
			ll = (LeaderLevel) lll.get(0);
			getRequest().setAttribute("stage", ll.getStage());
		} else {
			getRequest().setAttribute("stage", 0);
		}

		// 考勤审批员
		Object aa = personROManager.findById(AttendanceApprovers.class, Long.valueOf(userId));
		if (null != aa) {
			List<String> apprNames = personROManager.findByHQL("select u.displayName from  Users u where u.userid in ("
					+ ((AttendanceApprovers) aa).getApprovers() + ")");
			if (null != apprNames && apprNames.size() > 0) {
				StringBuffer apprNamesBu = new StringBuffer();
				for (String appName : apprNames) {
					apprNamesBu.append(appName.trim()).append(",");
				}
				getRequest().setAttribute("approvers", apprNamesBu.substring(0, apprNamesBu.length() - 1));
			}
		}

		if (!personROManager.findByHQL("select oa from OrgAttendanceAdmin oa where oa.userid = ?", Long.valueOf(userId))
				.isEmpty()) {
			this.getRequest().setAttribute("isOrgAttAdmin", "1");
		} else {
			this.getRequest().setAttribute("isOrgAttAdmin", "0");
		}

		if (exp != null) {
			if (exp.getTelNum() != null) {
				List telList = personROManager.findByHQL("select tel from  TcIpTel tel where tel.telId = ?",
						Long.valueOf(exp.getTelNum()));
				if (telList != null && telList.size() > 0) {
					tel1 = (TcIpTel) telList.get(0);
				}
			}
			if (exp.getUepFax() != null) {
				List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?",
						Long.valueOf(exp.getUepFax()));
				if (telList != null && telList.size() > 0) {
					tel2 = (TcIpTel) telList.get(0);
				}
			}
			if (exp.getWebFax() != null) {
				List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?",
						Long.valueOf(exp.getWebFax()));
				if (telList != null && telList.size() > 0) {
					tel3 = (TcIpTel) telList.get(0);
				}
			}
			if (null == exp.getRoomId()) {
				exp.setRoomInfo(null);

			} else if (null != exp.getRoomId()) {
				List<Objtank> ob = personROManager.findByHQL("select ob from Objtank ob where ob.nodeId = ?",
						Long.valueOf(exp.getRoomId()));
				if (ob != null && ob.size() > 0) {
					exp.setRoomInfo(ob.get(0).getName());
				}
			}
			getRequest().setAttribute("tel1", tel1);
			getRequest().setAttribute("tel2", tel2);
			getRequest().setAttribute("tel3", tel3);
		}
		// 用户基本信息
//		user = (Users) personROManager.findById(Users.class, Long.parseLong(userId));
//		getRequest().setAttribute("user", user);
//		if (null != user.getOrgId()) {
//			orgId = user.getOrgId().toString();
//		}


		StringBuffer strAcc = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		// 门禁 2013年8月22日16:26:05
		Long userid = Long.valueOf(userId);

		List<NjhwUsersAccess> list = accessMgrManager.findByHQL(
				"select t from NjhwUsersAccess t where t.userid = ? and (t.appStatus = '1' or t.appStatus = '5')",
				userid);

		if (list != null && list.size() > 0) {
			NjhwUsersAccess entity = list.get(0);
			getRequest().setAttribute("name", user.getDisplayName());
			List<Map> l = accessMgrManager.getAccessGuardInfo(entity.getNuacId());
			if (l != null && l.size() > 0) {
				getRequest().setAttribute("strAcc", l.get(0).get("ACCESS_AUTH") == null ? "正在删除申请中..."
						: l.get(0).get("ACCESS_AUTH").toString() + "    正在申请中...");
				getRequest().setAttribute("strGate", l.get(0).get("GUARD_AUTH") == null ? "正在删除申请中..."
						: l.get(0).get("GUARD_AUTH").toString() + "    正在申请中...");
			}
		} else {
			Map<String, String> m = accessMgrManager.initUserAccessInfo(userid.toString());
			if (!"true".equals(m.get("isNone"))) {
				getRequest().setAttribute("strAcc", m.get("access"));
				getRequest().setAttribute("strGate", m.get("gate"));
			}
		}
	}
	
	/** 
	* @title: inputRegister
	* @description: 显示登记页面
	* @author zh
	* @date 2013-05-05
	*/ 
	public String inputRegister() {
		String orgId = getParameter("orgId");
		String userId = getParameter("userId");
		
		// 用户基本信息
		Users user = (Users) personROManager.findById(Users.class, Long.parseLong(userId));
		getRequest().setAttribute("user", user);
		if (null != user.getOrgId()) {
			orgId = user.getOrgId().toString();
		}
		
		// 用户扩展信息
		NjhwUsersExp exp = null;
		List expList = personROManager.findByHQL(
				"select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?",
				Long.parseLong(userId));
		if (expList.size() > 0 && expList != null){
			exp = (NjhwUsersExp) expList.get(0);
		}
		
		String cardId = null;
		if (exp != null) {
			// 根据扩展信息中保存的用户所拥有的卡类型去查询市民卡表
			if (exp.getCardType() != null && "1".equals(exp.getCardType())) { // 1.市民卡2.临时卡
				List cardList = personROManager.findByHQL("select t from NjhwTscard t where t.userId = ?",
						Long.parseLong(userId));
				if (cardList.size() > 0 && cardList != null) {
					getRequest().setAttribute("card", cardList.get(cardList.size() - 1));
					cardId = ((NjhwTscard) cardList.get(cardList.size() - 1)).getCardId();
				}

			} else if (exp.getCardType() != null && "2".equals(exp.getCardType())) {
				cardId = exp.getTmpCard();
			}
			// 读取用户上传照片
			if (StringUtil.isNotEmpty(exp.getUepPhoto())) {
				getRequest().setAttribute("imgSrc", showPic(exp.getUepPhoto().toString()));
			}
		}
		
		
		if (StringUtil.isNotEmpty(userId)) {	// 编辑时加载用户各项信息
			processUserEdit(userId, user, exp, cardId);
		}
		
		
//		long topOrgId = 0;
		// 当前选定的部门
		Org org = (Org)this.personROManager.findById(Org.class, Long.parseLong(orgId));
		getRequest().setAttribute("org", org);
		
		// 找到当前用户的顶级部门
//		List<HashMap> list = this.personROManager.getTopOrgId();
//		if (list.size() > 0) 
//			topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
		
		// 判断是否弹出框编辑
		getRequest().setAttribute("ispopup",getParameter("ispopup"));
		getRequest().setAttribute("backList",getParameter("backList"));
		// 筛选电话号码
		//if (exp != null && StringUtil.isNotEmpty(exp.getTelNum()))  getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("update", exp.getTelNum(), topOrgId));
		//else getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("add","", topOrgId));
		// 筛选IP电话机
		//if (exp != null && exp.getTelId() != null) getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("update",exp.getTelId(), topOrgId));
		//else getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("add",(long)0, topOrgId));

		// 分配至部门的房间列表
		//getRequest().setAttribute("roomList", this.personROManager.findByHQL("select t from EmOrgRes t where t.orgId = ? and t.eorType = ?", topOrgId, EmOrgRes.EOR_TYPE_ROOM));
		// 门锁权限
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("cardId", cardId);
		
		List<Map<String, Object>> roomList = personROManager.findListBySql("PersonnelSQL.getRoomIdByCardId", pMap);
		StringBuffer dRoomName = new StringBuffer();
		StringBuffer dRoomId = new StringBuffer();
		if (null!=roomList && roomList.size()>0)
		{   
			for (int i = 0; i < roomList.size(); i++)
			{   
				dRoomName.append(roomList.get(i).get("ROOM_NAME"));
				dRoomName.append("，");
				dRoomId.append(roomList.get(i).get("NODE_ID").toString());
				dRoomId.append(",");
			}
		}
		if (dRoomId.length()>0)
		{   
			getRequest().setAttribute("dRoomName", dRoomName.toString().substring(0, dRoomName.toString().length()-1));
			getRequest().setAttribute("dRoomId", dRoomId.toString().substring(0, dRoomId.toString().length()-1) );
		}
		
		return SUCCESS;
	}
	
	/** 
	* @title: inputRegister
	* @description: 显示登记页面
	* @author zh
	* @date 2013-05-05
	*/ 
	public String inputRegisterAdmin() {
		String orgId = getParameter("orgId");
		String userId = getParameter("userId");
		NjhwUsersExp exp = null;
		TcIpTel tel1 = null;//ip电话
		TcIpTel tel2 = null;//传真
		TcIpTel tel3 = null;//网络传真
		Users user = null;
		String cardId = null;
		if (StringUtil.isNotEmpty(userId)) {	// 编辑时加载用户各项信息
			// 用户扩展信息 
			List expList = personROManager.findByHQL("select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?", Long.parseLong(userId));
			if (expList.size() > 0 && expList != null)  exp = (NjhwUsersExp) expList.get(0);
			getRequest().setAttribute("exp", exp);
			if(exp!=null){
				if(exp.getTelNum()!=null){
					List telList = personROManager.findByHQL("select tel from  TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getTelNum()));
					if(telList!=null&&telList.size()>0){
						tel1 = (TcIpTel)telList.get(0);
					}
				}
				if(exp.getUepFax()!=null){
					List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getUepFax()));
					if(telList!=null&&telList.size()>0){
						tel2 = (TcIpTel)telList.get(0);
					}
				}
				if(exp.getWebFax()!=null){
					List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getWebFax()));
					if(telList!=null&&telList.size()>0){
						tel3 = (TcIpTel)telList.get(0);
					}
				}
			    if (null == exp.getRoomId())
				{   
					exp.setRoomInfo(null);
					
				}
				else if (null != exp.getRoomId())
				{
					List<Objtank> ob = personROManager.findByHQL("select ob from Objtank ob where ob.nodeId = ?", Long.valueOf(exp.getRoomId()));
					if(ob!=null&&ob.size()>0){
						exp.setRoomInfo(ob.get(0).getName());
					}
				}
				getRequest().setAttribute("tel1", tel1);
				getRequest().setAttribute("tel2", tel2);
				getRequest().setAttribute("tel3", tel3);
			}
			// 用户基本信息 
			user = (Users)personROManager.findById(Users.class, Long.parseLong(userId));
			getRequest().setAttribute("user", user);
			if (null != user.getOrgId())
			{
				orgId = user.getOrgId().toString();
			}
			
			if (exp != null ) {
				// 根据扩展信息中保存的用户所拥有的卡类型去查询市民卡表
				if (exp.getCardType() != null && "1".equals(exp.getCardType())) {	// 1.市民卡2.临时卡
					List cardList = personROManager.findByHQL("select t from NjhwTscard t where t.userId = ?", Long.parseLong(userId));
					if (cardList.size() > 0 && cardList != null) {
						getRequest().setAttribute("card", cardList.get(cardList.size()-1));
						cardId = ((NjhwTscard)cardList.get(cardList.size()-1)).getCardId();
					}
				
				}
				else if (exp.getCardType() != null && "2".equals(exp.getCardType())) 
				{
					cardId= exp.getTmpCard();
				}
				// 读取用户上传照片
				if (StringUtil.isNotEmpty(exp.getUepPhoto())) {
					getRequest().setAttribute("imgSrc", showPic(exp.getUepPhoto().toString()));
				}
			}
		}
		
		long topOrgId = 0;
		// 当前选定的部门
		Org org = (Org)this.personROManager.findById(Org.class, Long.parseLong(orgId));
		getRequest().setAttribute("org", org);
		
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
		
		// 判断是否弹出框编辑
		getRequest().setAttribute("ispopup",getParameter("ispopup"));
		getRequest().setAttribute("backList",getParameter("backList"));
		// 筛选电话号码
		//if (exp != null && StringUtil.isNotEmpty(exp.getTelNum()))  getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("update", exp.getTelNum(), topOrgId));
		//else getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("add","", topOrgId));
		// 筛选IP电话机
		//if (exp != null && exp.getTelId() != null) getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("update",exp.getTelId(), topOrgId));
		//else getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("add",(long)0, topOrgId));

		// 分配至部门的房间列表
		//getRequest().setAttribute("roomList", this.personROManager.findByHQL("select t from EmOrgRes t where t.orgId = ? and t.eorType = ?", topOrgId, EmOrgRes.EOR_TYPE_ROOM));
		// 门锁权限
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("cardId", cardId);
		
		List<Map<String, Object>> roomList = personROManager.findListBySql("PersonnelSQL.getRoomIdByCardId", pMap);
		StringBuffer dRoomName = new StringBuffer();
		StringBuffer dRoomId = new StringBuffer();
		if (null!=roomList && roomList.size()>0)
		{   
			for (int i = 0; i < roomList.size(); i++)
			{   
				dRoomName.append(roomList.get(i).get("ROOM_NAME"));
				dRoomName.append("，");
				dRoomId.append(roomList.get(i).get("NODE_ID").toString());
				dRoomId.append(",");
			}
		}
		
		if (dRoomId.length()>0)
		{   
			
			getRequest().setAttribute("dRoomName", dRoomName.toString().substring(0, dRoomName.toString().length()-1));
			getRequest().setAttribute("dRoomId", dRoomId.toString().substring(0, dRoomId.toString().length()-1) );
		}
		
		return SUCCESS;
	}
	
	
	/** 
	* @title: inputRegisterOther
	* @description: 显示其他人员登记页面
	* @author hj
	* @date 2013-09-13
	*/ 
	public String inputRegisterOther() {
		String orgId = getParameter("orgId");
		String userId = getParameter("userId");
		NjhwUsersExp exp = null;
		TcIpTel tel1 = null;//ip电话
		TcIpTel tel2 = null;//传真
		TcIpTel tel3 = null;//网络传真
		Users user = null;
		String cardId = null;
		if (StringUtil.isNotEmpty(userId)) {	// 编辑时加载用户各项信息
			// 用户扩展信息 
			List expList = personROManager.findByHQL("select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?", Long.parseLong(userId));
			if (expList.size() > 0 && expList != null)  exp = (NjhwUsersExp) expList.get(0);
			getRequest().setAttribute("exp", exp);
			if(exp!=null){
				if(exp.getTelNum()!=null){
					List telList = personROManager.findByHQL("select tel from  TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getTelNum()));
					if(telList!=null&&telList.size()>0){
						tel1 = (TcIpTel)telList.get(0);
					}
				}
				if(exp.getUepFax()!=null){
					List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getUepFax()));
					if(telList!=null&&telList.size()>0){
						tel2 = (TcIpTel)telList.get(0);
					}
				}
				if(exp.getWebFax()!=null){
					List telList = personROManager.findByHQL("select tel from TcIpTel tel where tel.telId = ?", Long.valueOf(exp.getWebFax()));
					if(telList!=null&&telList.size()>0){
						tel3 = (TcIpTel)telList.get(0);
					}
				}
			    if (null == exp.getRoomId())
				{   
					exp.setRoomInfo(null);
					
				}
				else if (null != exp.getRoomId())
				{
					List<Objtank> ob = personROManager.findByHQL("select ob from Objtank ob where ob.nodeId = ?", Long.valueOf(exp.getRoomId()));
					if(ob!=null&&ob.size()>0){
						exp.setRoomInfo(ob.get(0).getName());
					}
				}
				getRequest().setAttribute("tel1", tel1);
				getRequest().setAttribute("tel2", tel2);
				getRequest().setAttribute("tel3", tel3);
			}
			// 用户基本信息 
			user = (Users)personROManager.findById(Users.class, Long.parseLong(userId));
			getRequest().setAttribute("user", user);
			if (null != user.getOrgId())
			{
				orgId = user.getOrgId().toString();
			}
			
			if (exp != null ) {
				// 根据扩展信息中保存的用户所拥有的卡类型去查询市民卡表
				if (exp.getCardType() != null && "1".equals(exp.getCardType())) {	// 1.市民卡2.临时卡
					List cardList = personROManager.findByHQL("select t from NjhwTscard t where t.userId = ?", Long.parseLong(userId));
					if (cardList.size() > 0 && cardList != null) {
						getRequest().setAttribute("card", cardList.get(cardList.size()-1));
						cardId = ((NjhwTscard)cardList.get(cardList.size()-1)).getCardId();
					}
				
				}
				else if (exp.getCardType() != null && "2".equals(exp.getCardType())) 
				{
					cardId= exp.getTmpCard();
				}
				// 读取用户上传照片
				if (StringUtil.isNotEmpty(exp.getUepPhoto())) {
					getRequest().setAttribute("imgSrc", showPic(exp.getUepPhoto().toString()));
				}
			}
			StringBuffer strAcc = new StringBuffer();
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			// 门禁 2013年8月22日16:26:05
			Long userid = Long.valueOf(userId);
			
			List<NjhwUsersAccess> list = accessMgrManager.findByHQL("select t from NjhwUsersAccess t where t.userid = ? and (t.appStatus = '1' or t.appStatus = '5')",
					userid);
			
			if (list != null && list.size() > 0) {
				NjhwUsersAccess entity = list.get(0);
				getRequest().setAttribute("name", user.getDisplayName());
				List<Map> l = accessMgrManager.getAccessGuardInfo(entity.getNuacId());
				if (l != null && l.size() > 0) {
					getRequest().setAttribute("strAcc", l.get(0).get("ACCESS_AUTH") == null? "正在删除申请中..." : l.get(0).get("ACCESS_AUTH").toString() + "    正在申请中...");
					getRequest().setAttribute("strGate", l.get(0).get("GUARD_AUTH") == null? "正在删除申请中..." : l.get(0).get("GUARD_AUTH").toString() + "    正在申请中...");
				}
			} else {
				Map<String, String> m = accessMgrManager.initUserAccessInfo(userid.toString());
				if (!"true".equals(m.get("isNone"))) {
					getRequest().setAttribute("strAcc", m.get("access"));
					getRequest().setAttribute("strGate", m.get("gate"));
				}
			}
		}
		long topOrgId = 0;
		// 当前选定的部门
		Org org = (Org)this.personROManager.findById(Org.class, Long.parseLong(orgId));
		getRequest().setAttribute("org", org);
		
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
		
		// 判断是否弹出框编辑
		getRequest().setAttribute("ispopup",getParameter("ispopup"));
		getRequest().setAttribute("backList",getParameter("backList"));
		// 筛选电话号码
		//if (exp != null && StringUtil.isNotEmpty(exp.getTelNum()))  getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("update", exp.getTelNum(), topOrgId));
		//else getRequest().setAttribute("telNumList", this.personROManager.loadNoAllotPhoneNum("add","", topOrgId));
		// 筛选IP电话机
		//if (exp != null && exp.getTelId() != null) getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("update",exp.getTelId(), topOrgId));
		//else getRequest().setAttribute("IpTelList", this.personROManager.loadNoAllotIpTel("add",(long)0, topOrgId));

		// 分配至部门的房间列表
		//getRequest().setAttribute("roomList", this.personROManager.findByHQL("select t from EmOrgRes t where t.orgId = ? and t.eorType = ?", topOrgId, EmOrgRes.EOR_TYPE_ROOM));
		// 门锁权限
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("cardId", cardId);
		
		List<Map<String, Object>> roomList = personROManager.findListBySql("PersonnelSQL.getRoomIdByCardId", pMap);
		StringBuffer dRoomName = new StringBuffer();
		StringBuffer dRoomId = new StringBuffer();
		if (null!=roomList && roomList.size()>0)
		{   
			for (int i = 0; i < roomList.size(); i++)
			{   
				dRoomName.append(roomList.get(i).get("ROOM_NAME"));
				dRoomName.append("，");
				dRoomId.append(roomList.get(i).get("NODE_ID").toString());
				dRoomId.append(",");
			}
		}
		if (dRoomId.length()>0)
		{   
			getRequest().setAttribute("dRoomName", dRoomName.toString().substring(0, dRoomName.toString().length()-1));
			getRequest().setAttribute("dRoomId", dRoomId.toString().substring(0, dRoomId.toString().length()-1) );
		}
		
		return SUCCESS;
	}
	
	/** 
	* @title: refreshCarNum
	* @description: 刷新车位信息
	* @author zh
	* @date 2013-05-05
	*/ 
	public String refreshCarNum() {
		JSONObject json = new JSONObject();
		try {
			long topOrgId = 0;
			// 找到当前用户的顶级部门
			List<HashMap> list = this.personROManager.getTopOrgId();
			if (list.size() > 0) topOrgId = list.get(0).get("TOP_ORG_ID") != null ? Long.parseLong(list.get(0).get("TOP_ORG_ID").toString()) : 0;
			
			// 统计单位所有车辆
			int countAll = 0;
			List<EmOrgRes> listFasten = this.personROManager.findByHQL("select t from EmOrgRes t where t.orgId = ? and t.eorType = ?", topOrgId, EmOrgRes.EOR_PARKING);
			for (EmOrgRes res : listFasten) {
				countAll += Integer.parseInt(res.getResName().toString());
			}
			
			// 统计已分配的固定车辆
			HashMap pMap = new HashMap();
			pMap.put("topOrgId", topOrgId);
			pMap.put("nupFlag", NjhwUsersPlatenum.PLATENUM_FASTEN);
			int countAllot = this.personROManager.findListBySql("PersonnelSQL.queryAllotCarFasten", pMap).size();
			
			json.put("countAll", countAll);
			json.put("countAllot", countAllot);
			json.put("countSurplus", (countAll - countAllot));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: refreshCarNum
	* @description: 刷新车位信息
	* @author zh
	* @date 2013-05-05
	*/ 
	public String refreshCarNumOther() {
		JSONObject json = new JSONObject();
		try {
			long topOrgId = Long.parseLong(getParameter("orgId"));
			
			// 统计单位所有车辆
			int countAll = 0;
			List<EmOrgRes> listFasten = this.personROManager.findByHQL("select t from EmOrgRes t where t.orgId = FN_GET_PARKING_ORGID(?) and t.eorType = ?", topOrgId, EmOrgRes.EOR_PARKING);
			for (EmOrgRes res : listFasten) {
				countAll += Integer.parseInt(res.getResName().toString());
			}
			
			// 统计已分配的固定车辆
			HashMap pMap = new HashMap();
			pMap.put("topOrgId", topOrgId);
			pMap.put("nupFlag", NjhwUsersPlatenum.PLATENUM_FASTEN);
			int countAllot = this.personROManager.findListBySql("PersonnelSQL.queryAllotCarFasten", pMap).size();
			
			json.put("countAll", countAll);
			json.put("countAllot", countAllot);
			json.put("countSurplus", (countAll - countAllot));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: loadPlatenum
	* @description: 加载车牌信息
	* @author zh
	* @date 2013-05-05
	*/ 
	public String loadPlatenum() {
		JSONObject json = new JSONObject();
		try {
			List plateNumList = personROManager.findByHQL("select t from NjhwUsersPlatenum t where t.userid = ?", Long.parseLong(getParameter("userid")));
			Struts2Util.renderJson(plateNumList, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 读取照片
	* @title: showPic 
	* @description: TODO
	* @author gxh
	* @param userPhoto
	* @return
	* @date 2013-4-17 上午11:07:05     
	* @throws
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
				System.out.println("文件不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}
	
	// 递归处理机构名称
	private void playOrgList(List<Org> orgs, List<Org> orderList, Long orgId, String level) {
		try {
			level += "--";
			for (Org org : orgs) {
				if (org.getPId() != null && org.getPId().equals(orgId)) {
					org.setShortName(level + org.getShortName());
					orderList.add(org);
					playOrgList(orgs, orderList, org.getOrgId(), level); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* @title: modifyCardIsLosted
	* @description: 挂失
	* @author zh
	* @date 2013-05-05
	*/ 
	public String modifyCardIsLosted() {
		String optType = getParameter("optType");
		String cityCard = getParameter("cityCard");
		
		int num = this.personROManager.saveModifyCardIsLosted(optType, cityCard);
		if (num == 0) Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		else if (num == 1) Struts2Util.renderText("cardNoExist", "encoding:UTF-8", "no-cache:true");
		else Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		return null;
	}	
	
	/**
	 * 
	 * @return
	 */
	public String orgIsContainAttendanceAdmin(){
		Users user = personROManager.getOrgAttAdminName(Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
		if(!StringUtil.isEmpty(getParameter("userId"))){
			// 去掉本身
			if(null != user && user.getUserid() != Long.parseLong(getParameter("userId"))){
				Struts2Util.renderText(user.getDisplayName(), "encoding:UTF-8", "no-cache:true");
			}else{
				Struts2Util.renderText("", "encoding:UTF-8", "no-cache:true");
			}
		}else{
			if(null != user){
				Struts2Util.renderText(user.getDisplayName(), "encoding:UTF-8", "no-cache:true");
			}else{
				Struts2Util.renderText("", "encoding:UTF-8", "no-cache:true");
			}
		}
		
		
		return null;
	}
	
	/** 
	* @title: modifySMA
	* @description: 设置成系统管理员
	* @author zh
	* @date 2013-05-05
	*/ 
	public String modifySMA() {
		String optType = getParameter("optType");
		String userid = getParameter("userid");
		
		int num = this.personROManager.saveModifySMA(optType, userid);
		if (num == 0) Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		else Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: modifySMAdmin
	* @description: 批量设置成系统管理员
	* @author：张权威
	* @date 2013年8月13日11:21:46
	*/ 
	public String modifySMAdmin() {
		int num = 0;
		// String userid = getParameter("userid");
		String ids = getParameter("ids");
		String checks =getParameter("checks");
	    if (!StringUtil.isEmpty(ids) && !StringUtil.isEmpty(checks))
		{
			String[] users  = ids.split(",");
			String[] optTypes = checks.split(",");
			if (null != users && users.length>0 && users.length == optTypes.length && !StringUtil.isEmpty(users[0]))
			{   
				String  optType = null;
				String userid= null;
				for (int i = 0; i < users.length; i++)
				{
					optType=optTypes[i];
					userid = users[i];
					if (!StringUtil.isEmpty(optType) && !StringUtil.isEmpty(userid))
					{   if("1".equals(optType)) 
							optType = "confirm";
						else
							optType = "cancel";
					    num = this.personROManager.saveModifySMA(optType, userid);	
					}
				}
				
			}
		}
	
		if (num == 0) Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		else Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: loadCardNum
	* @description: 根据卡类型的切换，加载对应的卡号
	* @author zh
	* @date 2013-05-05
	*/ 
	public String loadCardNum() {
		String cardType = getParameter("cardType");
		String userid = getParameter("userid");
		JSONObject json = new JSONObject();
		try {
			if ("1".equals(cardType) && StringUtil.isNotBlank(userid)) {	// 市民卡
				List cardList = this.personROManager.findByHQL("select t from NjhwTscard t where t.userId = ?", Long.parseLong(userid));
				if (cardList.size() > 0 && cardList != null) {
					NjhwTscard tscard = (NjhwTscard) cardList.get(0);
						json.put("rstCardNum", tscard.getCardId());
						json.put("cardLostedStatus", tscard.getCardLosted());
						json.put("cardStatus", tscard.getCardstatus());
						json.put("puicStatus", tscard.getPuicstatus());
						json.put("systemLosted", tscard.getSystemLosted());
				}
			} else if ("2".equals(cardType)) {	// 临时卡
				if (StringUtil.isNotBlank(userid)) {
					// 用户扩展信息 
					List expList = personROManager.findByHQL("select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.userid = ?", Long.parseLong(userid));
					if (expList.size() > 0 && expList != null) {
						NjhwUsersExp exp = (NjhwUsersExp) expList.get(0);
						json.put("rstCardNum", exp.getTmpCard() != null ? exp.getTmpCard().toString() : "");
					}
				}
				String orgId = Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString();
				List tmpExpList = personROManager.findByHQL("select t from NjhwUsersExp t, Users u where u.userid = t.userid and u.orgId = ?", Long.parseLong(orgId));
				
				if (tmpExpList != null && tmpExpList.size() >= Long.parseLong(Constants.DBMAP.get("ORG_TMP_CARD_OVER_NUM"))) {
					json.put("isTmpOver", true);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: loadCityCard
	* @description: 加载市民卡信息
	* @author zh
	* @date 2013-05-05
	*/ 
	public String loadCityCard() {
		String userId = getParameter("userId").toString();
		String orgId = getParameter("orgId");
		
		JSONObject json = new JSONObject();
		NjhwTscard card = null;
		try {
			card = personCardQueryToAppService.queryPersonCard(personModel.getCityCard(),orgId);
			if (card != null) {
				json.put("cardstatus", card.getCardstatus());		// 卡状态
				json.put("cardlostedstatus", card.getCardLosted());	// 挂失状态
				json.put("cardpuicstatus", card.getPuicstatus());	// 通卡状态
				json.put("status", 0);
				if (card != null) {
					if (card.getUserId() == null) {
						json.put("binding", 0);		// 绑定自己或者未绑定
					} else if (StringUtil.isNotEmpty(userId) && card.getUserId() == Long.parseLong(userId)) {
						json.put("binding", 0);		// 绑定自己或者未绑定
					} else {
						json.put("binding", 1);
						json.put("username", card.getUserName());
					}
				}
				json.put("residentNo", card.getResidentNo());
				json.put("name", card.getUserName());
				json.put("sex", card.getCustsex());
				json.put("phone", card.getMoblie());
				json.put("cardType", card.getCardType());
				json.put("status", 0);
			} else {
				json.put("status", 1);
			}
		} catch (Exception e) {
			try {
				json.put("status", 1);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: validTmpCard
	* @description: 检测临时卡的唯一性
	* @author zh
	* @date 2013-05-05
	*/ 
	public String validTmpCard() {
		int num = 0;
		String val = getParameter("cityCard");
		List<Map> listOrg = this.getPersonROManager().getRootOrgId(getParameter("orgId"));
		String orgId =getParameter("orgId");
		if (null != listOrg && listOrg.size()>0) 
		{
			orgId = String.valueOf(listOrg.get(0).get("ROOTORGID"));
		}
		
		
		if (StringUtil.isEmpty(getParameter("userId"))) {
			num = this.personROManager.findByHQL("select t from Users t, NjhwUsersExp t1 where t.userid = t1.userid and t1.tmpCard = ?", val).size();
		} else {
			num = this.personROManager.findByHQL("select t from Users t, NjhwUsersExp t1 where t.userid = t1.userid and t1.tmpCard = ? and t.userid != ?", val, Long.parseLong(getParameter("userId"))).size();
		}
		
		
		if (num == 0 && StringUtil.isEmpty(getParameter("userId")))
		{
			num = this.personROManager.findByHQL("select t from NjhwTscard t where t.cardId=?", val).size();
		}
		else if (num == 0 && StringUtil.isNotEmpty(getParameter("userId")))
		{
			num = this.personROManager.findByHQL("select t from NjhwTscard t where t.cardId=? and  t.userId != ?", val,Long.parseLong(getParameter("userId"))).size();
		}
		
		if (num > 0)
			Struts2Util.renderText("exist", "encoding:UTF-8", "no-cache:true");
		else
			Struts2Util.renderText("noExist", "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: checkUniqueId
	* @description: 检测用户名和用户编码的唯一性
	* @author zh
	* @date 2013-05-05
	*/ 
	public String checkUniqueId() {
		int num = 0;
		String val = getParameter("val");
		String id = getParameter("id");
		if (StringUtil.isEmpty(getParameter("userId"))) {
			if ("loginUid".equals(id)) num = this.personROManager.findByHQL("select t from Users t where t.loginUid = ?", val).size();
			else if ("ucode".equals(id)) num = this.personROManager.findByHQL("select t from Users t where trim(t.UCode) = ?", val).size();	
		} else {
			if ("loginUid".equals(id))  num = this.personROManager.findByHQL("select t from Users t where t.loginUid = ? and t.userid != ?", val, Long.parseLong(getParameter("userId"))).size();
			else if ("ucode".equals(id)) num = this.personROManager.findByHQL("select t from Users t where t.UCode = ? and t.userid != ?", val, Long.parseLong(getParameter("userId"))).size();
		}
		if (num > 0) Struts2Util.renderText("exist", "encoding:UTF-8", "no-cache:true");
		else Struts2Util.renderText("noExist", "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: validCarNum
	* @description: 检测车牌号的唯一性
	* @author zh
	* @date 2013-05-05
	*/ 
	public String validCarNum() {
		int num = 0;
		if (StringUtil.isEmpty(getParameter("userId"))) {
			 num = this.personROManager.findByHQL("select t from NjhwUsersPlatenum t where t.nupPn = ?", getParameter("carNum")).size();
		} else {
			 num = this.personROManager.findByHQL("select t from NjhwUsersPlatenum t where t.nupPn = ? and t.userid != ?", getParameter("carNum"), Long.parseLong(getParameter("userId"))).size();
		}
		if (num > 0) Struts2Util.renderText("exist", "encoding:UTF-8", "no-cache:true");
		else Struts2Util.renderText("noExist", "encoding:UTF-8", "no-cache:true");
		return null;
	}	
	
	/** 
	* @title: doorDis
	* @description:给人员分配权限
	* @author zh
	* @date 2013年7月15日14:34:19
	*/ 
	public String doorDis() {   
		String loginId =Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		String userid = getParameter("userId");
		String isNormal = getParameter("isNormal");
		if (null != getParameter("cardChange")  &&  "N".equals(getParameter("cardChange"))){
			// 分配授权
			String dRoomIds = getParameter("dRoomIds");
			String cardId = getParameter("cardId");
			if (null != userid) {   
				String[] dRoomIdArray = null;
				if (!StringUtil.isEmpty(dRoomIds)) {
					dRoomIdArray = dRoomIds.split(",");
				}
				this.personROManager.doorDis(userid,dRoomIdArray,cardId,loginId);
			}
		}else {
			// 卡号变动
			String cardCityOld = getParameter("cityCardold");
			String cardCity = getParameter("cardId");
			// 卡号变动  删除老卡的门锁权限
			if (!StringUtil.isEmpty(cardCityOld) && !StringUtil.isEmpty(userid) && !StringUtil.isEmpty(cardCity)) {
				this.personROManager.cardDoorAuth(cardCityOld,cardCity,loginId, userid,isNormal);
			}
			
		}
		return null;
	}
	
	/** 
	* @title: queryTelMacByTelID
	* @description: 切换电话号码时，切换对应的MAC地址
	* @author zh
	* @date 2013-05-05
	*/ 
	public String queryTelMacByTelID() {
		TcIpTel ipTel = (TcIpTel)this.personROManager.findById(TcIpTel.class, Long.parseLong(getParameter("telId")));
		Struts2Util.renderText(ipTel.getTelMac().toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/** 
	* @title: saveRegister
	* @description: 保存登记信息
	* @author zh
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	* @date 2013-05-05
	*/ 
	public String saveRegister() throws IllegalArgumentException, IllegalAccessException {
		int num =0;
		try {
			NjhwTscard card = null;
			// 当选中的卡类型是市民卡就根据卡号调用市民卡接口提取市民卡信息
			if (StringUtil.isNotEmpty(personModel.getCityCard()) && "1".equals(personModel.getCityCardType()))
				card = personCardQueryToAppService.queryPersonCard(personModel.getCityCard(),String.valueOf(personModel.getOrgId()));
			
			// 根据telid 查询telmac
			if(null != personModel.getIpTelId())
			{
				TcIpTel ipTel = (TcIpTel)this.personROManager.findById(TcIpTel.class, personModel.getIpTelId());
				if (null != ipTel  && !StringUtil.isEmpty(ipTel.getTelMac()))
				{
					personModel.setIpTelMac(ipTel.getTelMac());
				}
				
			}
			
			if (personModel.getUserid() != null && StringUtil.isNotBlank(personModel.getCityCardold())
					&& !personModel.getCityCardold().equals(personModel.getCityCard())) {
				accessMgrManager.accessAndGateOpt(personModel.getUserid(), "2");
				
				
				//在卡号变更的情况下，先删除旧卡的门锁权限
				personROManager.delUserOldDoorControl(personModel.getUserid());
			}
			
			num = personROManager.saveRegister(personModel, card, getParameter("carNums"), getParameter("isFastenCarNums"));
		} catch (Exception e) {
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
			num = 1;
		}
		
		if (num == 0) 
		{
			personModel.setCarNum(getParameter("carNums"));
			UserInfo userInfo = personROManager.personModel2UserInfo(personModel);
			try
			{   
				if (null != personModel.getUserid())
				{   
					LDAPService ldapService = new LDAPService();
					String pwd = ldapService.findUserByLoginUid(personModel.getLoginUidOld()).getUserPassword();
					ldapService.deleteUserByLoginUid(personModel.getLoginUidOld());
					userInfo.setUserPassword(pwd);
					ldapService.addUser(userInfo);
				}
				else
				{
					new LDAPService().addUser(userInfo);
				}
			}
			catch (IllegalArgumentException e)
			{
				Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
				e.printStackTrace();
				return null;
			}
			catch (NamingException e)
			{
				Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
				e.printStackTrace();
				return null;
			}
			Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
			return null;
		}
		else 
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 保存E座人员信息
	 * @return
	 */
	public String saveRegisterE(){
		personModel.setPId(1070l);
		int num =0;
		try {
			NjhwTscard card = null;
			// 当选中的卡类型是市民卡就根据卡号调用市民卡接口提取市民卡信息
			if (StringUtil.isNotEmpty(personModel.getCityCard()) && "1".equals(personModel.getCityCardType()))
				card = personCardQueryToAppService.queryPersonCard(personModel.getCityCard(),String.valueOf(personModel.getOrgId()));
			num = personROManager.saveRegisterE(personModel, card, getParameter("carNums"), getParameter("isFastenCarNums"));
		} catch (Exception e) {
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
			num = 1;
		}
		
		if (num == 0) 
		{
			personModel.setCarNum(getParameter("carNums"));
			UserInfo userInfo = personROManager.personModel2UserInfo(personModel);
			try
			{   
				if (null != personModel.getUserid())
				{   
					LDAPService ldapService = new LDAPService();
					String pwd = ldapService.findUserByLoginUid(personModel.getLoginUidOld()).getUserPassword();
					ldapService.deleteUserByLoginUid(personModel.getLoginUidOld());
					userInfo.setUserPassword(pwd);
					ldapService.addUser(userInfo);
				}
				else
				{
					new LDAPService().addUser(userInfo);
				}
			}
			catch (IllegalArgumentException e)
			{
				Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
				e.printStackTrace();
				return null;
			}
			catch (NamingException e)
			{
				Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
				e.printStackTrace();
				return null;
			}
			Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
			return null;
		}
		else 
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/** 
	* @title: saveRegisterOther
	* @description: 保存其他人员登记信息
	* @author hj
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	* @date 2013-09-13
	*/ 
	public String saveRegisterOther() throws IllegalArgumentException, IllegalAccessException {
		int num =0;
		try {
			NjhwTscard card = null;
			// 当选中的卡类型是市民卡就根据卡号调用市民卡接口提取市民卡信息
			if (StringUtil.isNotEmpty(personModel.getCityCard()) && "1".equals(personModel.getCityCardType()))
				card = personCardQueryToAppService.queryPersonCard(personModel.getCityCard(),String.valueOf(personModel.getOrgId()));
			
			// 根据telid 查询telmac
			if(null != personModel.getIpTelId())
			{
				TcIpTel ipTel = (TcIpTel)this.personROManager.findById(TcIpTel.class, personModel.getIpTelId());
				if (null != ipTel  && !StringUtil.isEmpty(ipTel.getTelMac()))
				{
					personModel.setIpTelMac(ipTel.getTelMac());
				}
				
			}
			
			if (personModel.getUserid() != null && StringUtil.isNotBlank(personModel.getCityCardold())
					&& !personModel.getCityCardold().equals(personModel.getCityCard())) {
				accessMgrManager.accessAndGateOpt(personModel.getUserid(), "2");
			}
			
			num = personROManager.saveRegisterOther(personModel, card, getParameter("carNums"), getParameter("isFastenCarNums"));
		} catch (Exception e) {
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
			num = 1;
		}
		
		if (num == 0) 
		{
			Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
			return null;
		}
		else 
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: updateOut
	* @description: 保存迁出信息
	* @author zh
	* @date 2013-05-05
	*/ 
	public String updateOut() {
		try {
			personROManager.updateOut(Long.parseLong(getParameter("userid")), getParameter("optType"));
			Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* @title: roomDis
	* @description: 查询出本单位所有的房间 
	* @author zhangqw
	* @date 2013年7月3日14:28:44
	*/ 
	public String roomDis()
	{
		String orgId = getParameter("orgId");
		String userId = getParameter("userId");
		List roomList = personROManager.getPlayCardRoomsLocks(orgId,userId,getParameter("ROOM_TYPE"));
		getRequest().setAttribute("roomList",roomList);
		getRequest().setAttribute("ROOM_TYPE", getParameter("ROOM_TYPE"));
		getRequest().setAttribute("userId", getParameter("userId"));
		getRequest().setAttribute("cardId", getParameter("cardId"));
		return  SUCCESS;
	}
	
	/** 
	* @title: showDisDoor
	* @description:查看授权状态
	* @author zhangqw
	* @date 2013年7月13日11:10:16
	* @return null
	*/ 
	public String showDisDoor()
	{
		String cardNo = getParameter("cardNo");
		String isAuth = getParameter("isAuth");
		net.sf.json.JSONObject jsonArray = personROManager.showDistributeLock(cardNo,isAuth);
		Struts2Util.renderJson(jsonArray,"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	public String disResultPage()
	{
		getRequest().setAttribute("cardNo", getParameter("cardNo"));
		return SUCCESS;
	}
	
	/** 
	* @title: roomDis
	* @description: 未被分配的电话 传真 网络传真
	* @author zhangqw
	* @date 2013年7月3日14:28:44
	*/ 
	public String telDis()
	{
		// page.setPageSize(16);
		List telList = personROManager.getPlayCardTel(getParameter("orgId"),getParameter("TEL_TYPE"));
		getRequest().setAttribute("TEL_TYPE", getParameter("TEL_TYPE"));
		getRequest().setAttribute("telList", telList);
		return SUCCESS;
	}

	@Override
	public PersonModel getModel() {
		return personModel;
	}
	
	@Override
	protected void prepareModel() throws Exception {}

	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}

	public void setPersonCardQueryToAppService(PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}

	public PersonRegOutManager getPersonROManager() {
		return personROManager;
	}

	public void setPersonROManager(PersonRegOutManager personROManager) {
		this.personROManager = personROManager;
	}

	public PersonModel getPersonModel() {
		return personModel;
	}

	public void setPersonModel(PersonModel personModel) {
		this.personModel = personModel;
	}

	/** 
	 * page 
	 * 
	 * @return the page 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public Page<HashMap> getPage()
	{
		return page;
	}

	/** 
	 * @param page the page to set 
	 */
	
	public void setPage(Page<HashMap> page)
	{
		this.page = page;
	}

	public void setAccessMgrManager(AccessMgrManager accessMgrManager) {
		this.accessMgrManager = accessMgrManager;
	}

	public AccessMgrManager getAccessMgrManager() {
		return accessMgrManager;
	}
	
}
