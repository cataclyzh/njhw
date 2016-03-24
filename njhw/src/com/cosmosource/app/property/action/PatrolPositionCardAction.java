package com.cosmosource.app.property.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.cosmosource.app.entity.GrPatrolPositionCard;
import com.cosmosource.app.entity.GrPatrolStick;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.VisRfidandid;
import com.cosmosource.app.property.service.PatrolLineManager;
import com.cosmosource.app.property.service.PatrolPositionCardManager;
import com.cosmosource.app.property.service.PatrolScheduleManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("unchecked")
public class PatrolPositionCardAction extends BaseAction {

	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	private GrPatrolPositionCard patrolPositionCard;
	private List<GrPatrolPositionCard> patrolPositionCardList = new ArrayList<GrPatrolPositionCard>();
	private List<Org> orgList = new ArrayList<Org>();
	private PatrolPositionCardManager patrolPositionCardManager;
	private PatrolScheduleManager patrolScheduleManager;
	private Long patrolPositionCardId;
	private Long patrolStickUserId;
	private Long patrolStickId;
	private Long orgId;
	private String orgName;
	private Long userId;
	private String userName;
	private String patrolPositionCardNo;
	private Long orgIdSelect;
	private String visId;
	private PatrolLineManager patrolLineManager;
	private String startTime;
	private String endTime;
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public PatrolLineManager getPatrolLineManager() {
		return patrolLineManager;
	}

	public void setPatrolLineManager(PatrolLineManager patrolLineManager) {
		this.patrolLineManager = patrolLineManager;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}

	/**
	 * 
	 * @title: patrolIndex
	 * @description: 巡查首页
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String patrolIndex() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: initPatrolLineList
	 * @description: 初始化获取员工定位卡信息
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 14:27:55
	 * @throws
	 */
	public String initPatrolPositionCardList() {
		patrolPositionCardList = patrolPositionCardManager
				.initGrPatrolPositionCardInfo();
		return SUCCESS;
	}

	//定位卡配置信息查询  positionCardList.act
	public String getPatrolPositionCardPage(){
		HashMap parMap = new HashMap();
		String userName = getRequest().getParameter("userName");
		String[] orgIdArray = getRequest().getParameterValues("orgIdSelect");
		if (userName != null && !"".equals(userName)) {
			parMap.put("userName", userName);
		}

		if (orgIdArray != null) {
			if (!orgIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("orgId", Long.parseLong(orgIdArray[0]));
			}
		}
		page = patrolPositionCardManager
				.getGrPatrolPositionCardPage(page, parMap);
		return SUCCESS;
	}
	
	public String getPatrolStickRecord(){
		String userNameStr = getRequest().getParameter("userName");
		String startTimeStr = getRequest().getParameter("startTime");
		String endTimeStr = getRequest().getParameter("endTime");
		
		if(startTimeStr == null || startTimeStr.trim().length() == 0){
			startTimeStr = DateUtil.getPreDate("yyyy-MM-dd") + " 00:00:00";
		}
		
		if(endTimeStr == null || endTimeStr.trim().length() == 0){
			endTimeStr = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
		}
		
		String type = getRequest().getParameter("type");
		
		Map param = new HashMap();
		param.put("userNameStr", userNameStr);
		param.put("startTimeStr", startTimeStr);
		param.put("endTimeStr", endTimeStr);
		
		if(type != null && type.equals("export")){
			page.setResult(patrolPositionCardManager.getPatrolStickRecord(param));
		}else{
			page = patrolPositionCardManager.getPatrolStickRecord(page, param);
		}
		
		setStartTime(startTimeStr);
		setEndTime(endTimeStr);
		
		return SUCCESS;
	}
	
	public String queryHistoryLine(){
		String userNameStr = getRequest().getParameter("userName");
		String startTimeStr = getRequest().getParameter("startTime");
		String endTimeStr = getRequest().getParameter("endTime");
		
		if(startTimeStr == null || startTimeStr.trim().length() == 0){
			startTimeStr = DateUtil.getPreDate("yyyy-MM-dd") + " 00:00:00";
		}
		
		if(endTimeStr == null || endTimeStr.trim().length() == 0){
			endTimeStr = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
		}
		
		String type = getRequest().getParameter("type");
		List<Map> users = patrolLineManager.getPositionUser(userNameStr);
		
		try{
			List<HashMap> resultList = new ArrayList<HashMap>();
			for(Map u : users){
				String userName = String.valueOf(u.get("USER_NAME"));
				String tagMacStr = String.valueOf(u.get("PATROL_POSITION_CARD_NO"));
	//			System.out.println(userName + " | " + tagMacStr);
				logger.info(userName + " | " + tagMacStr);
				Map param = new HashMap();
				param.put("tagMac", tagMacStr);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (StringUtils.isNotEmpty(startTimeStr)) {
					Date st = DateUtils.parseDate(startTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
					param.put("startTime", st);
				}
				if (StringUtils.isNotEmpty(endTimeStr)) {
					Date et = DateUtils.parseDate(endTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
					param.put("endTime", et);
				}
				
				List<Map> result = null;
				
				try{
					result = patrolLineManager.getHistoryPosition(param);
				}catch(Exception e){
					String errorInfo = "定位服务器出错! ";
					errorInfo += "详细错误信息如下: " + e.getMessage();
					getRequest().setAttribute("errorInfo", errorInfo);
					return "error";
				}
				
				if(result != null){
					for(Map m: result){
						String coordinatesName = String.valueOf(m.get("COORDINATESNAME"));
						String tagMac = String.valueOf(m.get("TAGMAC"));
						String time = String.valueOf(m.get("WRITETIME"));
						
						String d1Str = "";
						
						try {
							Date d1 = (Date)m.get("WRITETIME");
							d1Str = format.format(d1);
						} catch (Exception e) {
							d1Str = m.get("WRITETIME").toString();
						}
						
						//通过tagMac获取位置信息
						Map positionMap = patrolLineManager.getPositionName(coordinatesName);
						
						String placeId = String.valueOf(positionMap.get("pointId"));
						String placeName = String.valueOf(positionMap.get("pointName"));
						
						HashMap resultMap = new HashMap();
						resultMap.put("userName", userName);
						resultMap.put("time", d1Str);
						resultMap.put("placeName", placeName);
						resultMap.put("placeId", placeId);
						
						if(placeName != null && placeName.trim().length() != 0
								&& !placeName.equals("null")){
							resultList.add(resultMap);
						}
					}
				}
				
				int pageSize = page.getPageSize();
				int startIndex = page.getFirst() - 1;
				int endIndex = startIndex + pageSize;
				long totalCount = resultList.size();
				page.setTotalCount(totalCount);
				
				List<HashMap> pageResult = new ArrayList<HashMap>();
				if ("export".equals(type)) {
					page.setResult((List<HashMap>)resultList);
				}else {
					for(int i=startIndex; i<endIndex; i++){
						if(i < resultList.size()){
							pageResult.add((HashMap)resultList.get(i));
						}
					}
					page.setResult(pageResult);
				}
			}
		}catch(Exception e){
			logger.error("查询物业巡更定位出错", e);
		}
		getRequest().setAttribute("startTime", startTimeStr);
		setStartTime(startTimeStr);
		getRequest().setAttribute("endTime", endTimeStr);
		setEndTime(endTimeStr);
		return SUCCESS;
	}
	/**
	 * @description: 分页得到员工巡更棒信息
	 * @author zhujiabiao
	 * @return
	 */
	public String queryPatrolStickList() {
		HashMap parMap = new HashMap();
		String[] orgIdArray = getRequest().getParameterValues("orgIdSelect");
		String userName = getRequest().getParameter("userName");
		if (orgIdArray != null) {
			if (!orgIdArray[0].equalsIgnoreCase("0")) {
				parMap.put("orgId", Long.parseLong(orgIdArray[0]));
			}
		}
		
		if (userName != null && !"".equals(userName)) {
			parMap.put("userName", userName);
		}
		page = patrolPositionCardManager.getGrPatrolStickList(page, parMap);
		return SUCCESS;
		
	}

	/**
	 * 
	 * @title: addPatrolPositionCard
	 * @description: 员工定位卡制定
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String addPatrolPositionCard() {
		try {
			String userIds = getRequest().getParameter("treeUserId");
//			String visIdStr = getRequest().getParameter("visId");
			String cardNo = getRequest().getParameter("cardNo");
			
			if(userIds == null || userIds.trim().length() == 0){
				getRequest().setAttribute("cardNo", cardNo);
				getRequest().setAttribute("userIds_error", "请选择用户");
				return "error";
			}
			
			if(cardNo == null || cardNo.trim().length() == 0){
				getRequest().setAttribute("cardNo", cardNo);
				getRequest().setAttribute("cardNo_error", "请填写卡号");
				return "error";
			}
			
			//检查卡号是否在
			String cardMac = patrolPositionCardManager.getPositionCardMac(cardNo); 
			if(cardMac == null){
				getRequest().setAttribute("cardNo", cardNo);
//				getRequest().setAttribute("receivers", userIds);
				getRequest().setAttribute("cardNo_error", "此卡号无效");
				return "error";
			}
			
			//卡号不能重复使用
			if(patrolPositionCardManager.isUsedGrPatrolPositionCard(cardMac)){
				getRequest().setAttribute("cardNo", cardNo);
//				getRequest().setAttribute("receivers", userIds);
				getRequest().setAttribute("cardNo_error", "此卡号已被使用");
				return "error";
			}
			
			logger.info("userIds: " + userIds);
			logger.info("cardNo: " + cardNo);
			logger.info("cardMac: " + cardMac);
			
			String[] usersArray = userIds.split(",");
			Long[] uId = new Long[usersArray.length];
			for(int i=0;i<usersArray.length;i++){
				uId[i] = Long.valueOf(usersArray[i]);
			}
			
			//已绑定的用户不能重复绑定
			String userName = patrolPositionCardManager.checkBrPatrolPositionUser(uId);
			if(userName != null){
				getRequest().setAttribute("treeUserId", userIds);
				getRequest().setAttribute("cardNo", cardNo);
				getRequest().setAttribute("userIds_error", "用户["+userName+"]已绑定过定位卡");
				return "error";
			}
			
			patrolPositionCardManager.addGrPatrolPositionCard(cardMac, uId);
			setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");
		}
		
		getRequest().setAttribute("userIds_error", "");
		getRequest().setAttribute("cardNo_error", "");
		return SUCCESS;
	}

	/**
	 * zhujiabiao
	 * 2013-9-23
	 * @return
	 */
	public String addPatrolStick() {
		try {
			String userIds = getRequest().getParameter("treeUserId");
			String sIdStr = getRequest().getParameter("sId");
			
			String[] usersArray = userIds.split(",");
			Long[] uId = new Long[usersArray.length];
			for(int i=0;i<usersArray.length;i++){
				uId[i] = Long.valueOf(usersArray[i]);
			}
			Long sId = Long.valueOf(sIdStr);
		
			patrolPositionCardManager.addGrPatrolStickUser(sId,uId);
			setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: editPatrolPositionCard
	 * @description: 员工定位卡编辑，提交
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:12
	 * @throws
	 */
	public String editPatrolPositionCard() {
		try {
			Long patrolPositionCardId = Long.parseLong(getRequest().getParameter("patrolPositionCardId"));
//			String visId1 = StringUtil.trim(getRequest().getParameter("visId"));
			String cardNo = StringUtil.trim(getRequest().getParameter("cardNo"));
			
			//检查卡号是否在
			String cardMac = patrolPositionCardManager.getPositionCardMac(cardNo); 
			if(cardMac == null){
				getRequest().setAttribute("cardNo", cardNo);
//				getRequest().setAttribute("receivers", userIds);
				getRequest().setAttribute("cardNo_error", "此卡号无效");
				return "error";
			}
			
			//卡号不能重复使用
			if(patrolPositionCardManager.isUsedGrPatrolPositionCard(cardMac)){
				getRequest().setAttribute("cardNo", cardNo);
				getRequest().setAttribute("cardNo_error", "此卡号已被使用");
				return "error";
			}
			
			patrolPositionCardManager.editGrPatrolPositionCardInfo(patrolPositionCardId, cardMac);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public String editPatrolStick() {
		try {
			Long suId = Long.parseLong(getRequest().getParameter("patrolStickUserId"));
			Long sId = Long.parseLong(getRequest().getParameter("sId"));
			patrolPositionCardManager.editGPatrolStick(suId, sId);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: deletePatrolPositionCard
	 * @description: 删除员工定位卡
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 15:18:48
	 * @throws
	 */
	public String deletePatrolPositionCard() {
		try {
			String[] patrolPositionCardIds = getRequest().getParameterValues("patrolPositionCardIds");
			patrolPositionCardManager.deleteGrPatrolLineInfo(patrolPositionCardIds);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	public String deletePatrolStick() {
		try {
			String[] patrolStickIds = getRequest().getParameterValues("patrolStickIds");
			patrolPositionCardManager.deleteGrPatrolStick(patrolStickIds);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toAddPatrolPositionCard
	 * @description: 跳转至制定员工定位卡页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:56:47
	 * @throws
	 */
	public String toAddPatrolPositionCard() {
		return SUCCESS;
	}
	
	/**
	 * @description: 跳转至制定员工定位卡页面
	 * @author zhujiabiao
	 * @return
	 */
	public String toAddPatrolStick() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: toEditPatrolPositionCard
	 * @description: 跳转至编辑员工定位卡页面
	 * @author tangtq
	 * @return
	 * @date 2013-7-10 16:57:21
	 * @throws
	 */
	public String toEditPatrolPositionCard() {
		try {
			this.patrolPositionCardId = Long.parseLong(getRequest()
					.getParameter("patrolPositionCardId"));
			Map map = patrolPositionCardManager.findPatrolPositionCardById(patrolPositionCardId);
			if(map!=null){
				this.userId = map.get("USERID")==null?null:Long.valueOf(map.get("USERID").toString());
				this.userName = map.get("DISPLAY_NAME")==null?null:map.get("DISPLAY_NAME").toString();
				this.visId = map.get("VISID") == null ? null : map.get("VISID").toString();
				String cardNo = map.get("TAGNICKNAME").toString();
				
				//检查卡号是否在
				String cardId = patrolPositionCardManager.getPositionCardId(cardNo); 
//				if(cardId == null){
//					setIsSuc("false");
//					return SUCCESS;
//				}
				getRequest().setAttribute("cardNo", cardId);
			}
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	
	public String toEditPatrolStick() {
		try {
			this.patrolStickUserId = Long.parseLong(getRequest().getParameter("patrolStickUserId"));
			Map map = patrolPositionCardManager.findPatrolStickById(patrolStickUserId);
			if(map!=null){
				this.userId = map.get("USERID")==null?null:Long.valueOf(map.get("USERID").toString());
				this.userName = map.get("DISPLAY_NAME")==null?null:map.get("DISPLAY_NAME").toString();
				this.patrolStickId = map.get("S_ID") == null ? null : Long.valueOf(map.get("S_ID").toString());
			}
			setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public GrPatrolPositionCard getPatrolPositionCard() {
		return patrolPositionCard;
	}

	public void setPatrolPositionCard(GrPatrolPositionCard patrolPositionCard) {
		this.patrolPositionCard = patrolPositionCard;
	}

	public List<GrPatrolPositionCard> getPatrolPositionCardList() {
		return patrolPositionCardList;
	}

	public void setPatrolPositionCardList(
			List<GrPatrolPositionCard> patrolPositionCardList) {
		this.patrolPositionCardList = patrolPositionCardList;
	}

	public PatrolPositionCardManager getPatrolPositionCardManager() {
		return patrolPositionCardManager;
	}

	public void setPatrolPositionCardManager(
			PatrolPositionCardManager patrolPositionCardManager) {
		this.patrolPositionCardManager = patrolPositionCardManager;
	}

	public Long getPatrolPositionCardId() {
		return patrolPositionCardId;
	}

	public void setPatrolPositionCardId(Long patrolPositionCardId) {
		this.patrolPositionCardId = patrolPositionCardId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPatrolPositionCardNo() {
		return patrolPositionCardNo;
	}

	public void setPatrolPositionCardNo(String patrolPositionCardNo) {
		this.patrolPositionCardNo = patrolPositionCardNo;
	}

	public PatrolScheduleManager getPatrolScheduleManager() {
		return patrolScheduleManager;
	}

	public void setPatrolScheduleManager(
			PatrolScheduleManager patrolScheduleManager) {
		this.patrolScheduleManager = patrolScheduleManager;
	}

	public List<Org> getOrgList() {
		return orgList = patrolPositionCardManager.getOrgInfo();
	}

	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}

	public Long getOrgIdSelect() {
		return orgIdSelect;
	}

	public void setOrgIdSelect(Long orgIdSelect) {
		this.orgIdSelect = orgIdSelect;
	}
	
	public List<GrPatrolStick> getGrPatrolStickList(){
		return patrolPositionCardManager.getGrPatrolStickList();
	}

	public List<VisRfidandid> getVisRfidandidList(){
		return patrolPositionCardManager.getVisRfidandidList();
	}

	public String getVisId() {
		return visId;
	}

	public void setVisId(String visId) {
		this.visId = visId;
	}
	
	public String checkUsers(){
		String treeUIdsStr = getRequest().getParameter("treeuid")==null?null:getRequest().getParameter("treeuid").toString();
		String msg = "";
		if(treeUIdsStr !=null){
			if(treeUIdsStr.endsWith(",")){
				treeUIdsStr = treeUIdsStr.substring(0, treeUIdsStr.lastIndexOf(","));
			}
			treeUIdsStr.split(",");
			List list = null;
			
			try {
				list =	patrolPositionCardManager.getCheckPatrolPositionCardList(treeUIdsStr.split(","));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Map m = (Map)list.get(i);
					msg+=m.get("DISPLAY_NAME")==null?"":StringUtil.trim(m.get("DISPLAY_NAME").toString());
				}
				msg+="已经分配了定位卡请重新选择";
			}
		}
		Struts2Util.renderText(msg,  "encoding:UTF-8","no-cache:true");
		return null;
	}
	
	public String checkStick(){
		String treeUIdsStr = getRequest().getParameter("treeuid")==null?null:getRequest().getParameter("treeuid").toString();
		String msg = "";
		if(treeUIdsStr !=null){
			if(treeUIdsStr.endsWith(",")){
				treeUIdsStr = treeUIdsStr.substring(0, treeUIdsStr.lastIndexOf(","));
			}
			treeUIdsStr.split(",");
			List list = null;
			
			try {
				list =	patrolPositionCardManager.getCheckPatrolStickList(treeUIdsStr.split(","));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Map m = (Map)list.get(i);
					msg+=m.get("DISPLAY_NAME")==null?"":StringUtil.trim(m.get("DISPLAY_NAME").toString());
				}
				msg+="已经分配了巡更棒请重新选择";
			}
		}
		Struts2Util.renderText(msg,"encoding:UTF-8","no-cache:true");
		return null;
	}

	public Long getPatrolStickUserId() {
		return patrolStickUserId;
	}

	public void setPatrolStickUserId(Long patrolStickUserId) {
		this.patrolStickUserId = patrolStickUserId;
	}

	public Long getPatrolStickId() {
		return patrolStickId;
	}

	public void setPatrolStickId(Long patrolStickId) {
		this.patrolStickId = patrolStickId;
	}
}
