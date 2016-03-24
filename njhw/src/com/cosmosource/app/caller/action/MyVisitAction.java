package com.cosmosource.app.caller.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.caller.model.VisitModel;
import com.cosmosource.app.caller.service.MyVisitManager;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.app.message.service.MessageService;
import com.cosmosource.app.message.utils.MessageUtils;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.EncodeUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/** 
* @description: 我的来访列表、确认来访、拒绝来访、被访者预约功能
* @author zh
* @date 2013-03-19 
*/ 
public class MyVisitAction extends BaseAction<Object> {

	// 定义全局变量
	private static final long serialVersionUID = 4227875753301925460L;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private String flag;
	// 定义实体变量
	private VmVisit vmVisit = new VmVisit();
	private VmVisitorinfo visitorinfo = new VmVisitorinfo();
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录
	// 定义注入对象
	private MyVisitManager myVisitManager;
	private PersonCardQueryToAppService personCardQueryToAppService;
	// 定义模型
	private VisitModel visitModel = new VisitModel();
	
	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	/** 
	* @title: init
	* @description: 初始化
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String init() {
		flag = VmVisit.VS_FLAG_APP;
		page = myVisitManager.queryVisit(page, flag, "", "");
		return SUCCESS;
	}

	/** 
	* @title: list
	* @description: 查询来访记录
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String list() throws Exception {
		String endTime = "";
		if (StringUtil.isNotEmpty(getParameter("endTime"))) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtil.str2Date(getParameter("endTime"),"yyyy-MM-dd"));
			cal.add(Calendar.DATE, 1);
			endTime = DateUtil.date2Str(cal.getTime(), "yyyy-MM-dd");
		}
		page = myVisitManager.queryVisit(page, flag, getParameter("beginTime"), endTime);
		
		getRequest().setAttribute("beginTime", getParameter("beginTime"));
		getRequest().setAttribute("endTime", getParameter("endTime"));
		return SUCCESS;
	}

	/** 
	* @title: repulse
	* @description: 拒绝访问申请，并发送消息
	* @author zh
	* @date 2013-03-19 
	*/
	public String repulse() {
		int status = 0;
		try {
			long vsid = Long.parseLong(getParameter("vsId"));
			String vsComments = getParameter("vsCommets");
			Date vsSt = DateUtil.str2Date(getParameter("vsSt"), "yyyy-MM-dd HH:mm");
			Date vsEt = DateUtil.str2Date(getParameter("vsEt"), "yyyy-MM-dd HH:mm");
			
			status = myVisitManager.savevisitRepulse(vsid,vsComments,vsSt,vsEt);
			
			if (status == 0) Struts2Util.renderText("success");
			else Struts2Util.renderText("statusError");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* @title: affirm
	* @description: 确认访问申请，并发送消息
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String affirm() {
		int status = 0;
		try {
			long vsid = Long.parseLong(getParameter("vsId"));
			String vsComments = getParameter("vsCommets");
			Date vsSt = DateUtil.str2Date(getParameter("vsSt"), "yyyy-MM-dd HH:mm");
			Date vsEt = DateUtil.str2Date(getParameter("vsEt"), "yyyy-MM-dd HH:mm");
			
			status = myVisitManager.savevisitAffirm(vsid, vsComments, vsSt, vsEt);
			
			if (status == 0) Struts2Util.renderText("success");
			else Struts2Util.renderText("statusError");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* @title: showOptCancel
	* @description: 显示取消操作页面
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String showOptCancel(){
		VmVisit vmVisitInfo = (VmVisit)this.myVisitManager.findById(VmVisit.class, Long.parseLong(getParameter("vsid")));
		getRequest().setAttribute("vmVisitInfo", vmVisitInfo);
		getRequest().setAttribute("vsSt", DateUtil.date2Str(vmVisitInfo.getVsSt(), "yyyy-MM-dd HH:mm"));
		getRequest().setAttribute("vsEt", DateUtil.date2Str(vmVisitInfo.getVsEt(), "yyyy-MM-dd HH:mm"));
		return SUCCESS;
	}
	
	/** 
	* @title: cancel
	* @description: 取消本次访问，并发送消息
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String cancel() {
		int status = 0;
		try {
			long vsid = Long.parseLong(getParameter("vsId"));
			String cancelInfo = getParameter("cancelInfo");
			status = myVisitManager.savevisitCancel(vsid, cancelInfo);
			
			if (status == 0) Struts2Util.renderText("success");
			else Struts2Util.renderText("statusError");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* @title: save
	* @description: 被访者预约
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String save(){
		try{
			String visitType = (String) visitModel.getVisitTypeFlag();
			if(visitType.equals("0")){
				log.info("市民卡预约");
				visitByCityCard();
			}else if (visitType.equals("1")){
				log.info("身份证预约");
				visitByCardId();
			}else{
				Struts2Util.renderText("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Util.renderText("error");
		}
		
		return null;
	}
	
	/**
	 * 使用市民卡预约
	 */
	private void visitByCityCard(){
		String vs_st = getParameter("beginTime");
		String vs_et = vs_st.substring(0,10) + " 18:00";//getParameter("endTime");
		Date begin = DateUtil.str2Date(vs_st, "yyyy-MM-dd HH:mm");
		Date end = DateUtil.str2Date(vs_et, "yyyy-MM-dd HH:mm");
		visitModel.setBeginTime(begin);
		visitModel.setEndTime(end);
		
		//Map checkResult = myVisitManager.checkVisitorTime(visitModel.getCardId(), visitModel.getCityCard(), vs_st, vs_et);
		//String status = (String) checkResult.get("STATUS");
//		if(!status.equals("0")){
//			String responseStr = (String) checkResult.get("responseStr");
//			Struts2Util.renderText(responseStr);
//		}else{		
			try {
				int flag = myVisitManager.saveVisitForCityCard(visitModel);
				if(flag == 0){
					Struts2Util.renderText("success");
				}else if (flag == 1){
					Struts2Util.renderText("预约信息保存成功,短消息发送失败!");
				}
			} catch (Exception e) {				
				e.printStackTrace();
				Struts2Util.renderText("error");
			}
//		}
	}
	
	/**
	 * 使用身份证预约
	 */
	private void visitByCardId(){
		String vs_st = getParameter("beginTime");
		String vs_et = vs_st.substring(0,10) + " 18:00";;//getParameter("endTime");
		Date begin = DateUtil.str2Date(vs_st, "yyyy-MM-dd HH:mm");
		Date end = DateUtil.str2Date(vs_et, "yyyy-MM-dd HH:mm");
		visitModel.setBeginTime(begin);
		visitModel.setEndTime(end);
		
//		Map checkResult = myVisitManager.checkVisitorTime(visitModel.getCardId(), visitModel.getCityCard(), vs_st, vs_et);
//		String status = (String) checkResult.get("STATUS");
//		if(!status.equals("0")){
//			String responseStr = (String) checkResult.get("responseStr");
//			Struts2Util.renderText(responseStr);
//		}else{
			try {
				int flag = myVisitManager.saveVisitForCardId(visitModel);
				if(flag == 0){
					Struts2Util.renderText("success");
				}else if (flag == 1){
					Struts2Util.renderText("预约信息保存成功,短消息发送失败!");
				}
			} catch (Exception e) {				
				e.printStackTrace();
				Struts2Util.renderText("error");
			}
//		}
	}
	
	/** 
	* @title: byVisitInput
	* @description: 显示被访者预约
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String byVisitInput(){
		getRequest().setAttribute("nowDate", DateUtil.getDateTime("yyyy-MM-dd HH:mm"));
		return SUCCESS;
	}
	
	/** 
	* @title: showOpt
	* @description: 显示操作页面
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String showOpt(){
		HttpServletRequest request;
		String id;
		long vId;
		try {
			request = ServletActionContext.getRequest();
			id = request.getParameter("vsid");
			vId = Long.parseLong(id);
			messageService.updateVisitRead(vId); //修改已读未读状态
			
			VmVisit vmVisitInfo = (VmVisit)this.myVisitManager.findById(VmVisit.class, Long.parseLong(getParameter("vsid")));
//			if("00".equals(vmVisitInfo.getVsFlag()))
//					vmVisitInfo.setVsFlag("初始预约");
//			else if("01".equals(vmVisitInfo.getVsFlag()))
//				vmVisitInfo.setVsFlag("预约申请");
//			else if("02".equals(vmVisitInfo.getVsFlag()))
//				vmVisitInfo.setVsFlag("申请确认");
//			else if("03".equals(vmVisitInfo.getVsFlag()))
//				vmVisitInfo.setVsFlag("申请拒绝");
//			else if("04".equals(vmVisitInfo.getVsFlag()))
//				vmVisitInfo.setVsFlag("到访");
//			else if("05".equals(vmVisitInfo.getVsFlag()))
//				vmVisitInfo.setVsFlag("正常结束");
//			else if("06".equals(vmVisitInfo.getVsFlag()))
//				vmVisitInfo.setVsFlag("异常结束");
//			else if("99".equals(vmVisitInfo.getVsFlag()))
//				vmVisitInfo.setVsFlag("取消预约");
			String status = vmVisitInfo.getVsFlag();
			//vmVisitInfo.setVsFlag(MessageUtils.getVsFlag(status));
			getRequest().setAttribute("vmVsType", vmVisitInfo.getVsType());
			getRequest().setAttribute("vmFlagStr", MessageUtils.getVsFlag(status));
			getRequest().setAttribute("vmTypeFlag", MessageUtils.getVsType(vmVisitInfo.getVsType()));
			getRequest().setAttribute("vmVisitInfo", vmVisitInfo);
			getRequest().setAttribute("vsSt", DateUtil.date2Str(vmVisitInfo.getVsSt(), "yyyy-MM-dd HH:mm"));
			getRequest().setAttribute("vsEt", DateUtil.date2Str(vmVisitInfo.getVsEt(), "yyyy-MM-dd HH:mm"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			request = null;
			id = null;
			vId = 0;
		}
		//return SUCCESS;
		return "newSuccess";
	}
		
	/**
	* @title: showPic
	* @description: 读取绝对路径的图片
	* @author zh
	* @date 2013-04-09 
	*/ 
	public String showPic(String userPhoto){
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

	/** 
	* @title: showOpt
	* @description: 弹出版操作界面
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String showPopupOpt(){
		HashMap vmVisitInfo = this.myVisitManager.loadVisitByVsid(Long.parseLong(getParameter("vsid")));
		getRequest().setAttribute("vmVisitInfo", vmVisitInfo);
		
		String imgSrc = "";
		if (vmVisitInfo.get("RES_BAK1") != null) {
			NjhwTscard tsCard = (NjhwTscard)this.myVisitManager.findById(NjhwTscard.class, vmVisitInfo.get("RES_BAK1").toString());
			if (tsCard != null && StringUtil.isNotEmpty(tsCard.getUserPhoto())) {
				imgSrc = showPic(tsCard.getUserPhoto().toString());
			}
		}
		getRequest().setAttribute("imgSrc",imgSrc);
		return SUCCESS;
	}
	
	
	/** 
	* @title: loadVisitorInfo
	* @description: 根据身份证号加载访客信息
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String loadVisitorInfo() {
		JSONObject json = new JSONObject();
		visitorinfo = myVisitManager.loadVisitorInfo(getParameter("cardId"));
		NjhwTscard njhwTscard = myVisitManager.loadNjhwTscard(getParameter("cardId"));
		
		try {
			if (visitorinfo != null) {
				json.put("VIName", visitorinfo.getViName());
				
				String cityCardId = visitorinfo.getResBak1();
				if(cityCardId == null || cityCardId.trim().length() == 0){
					cityCardId = njhwTscard.getCardId();
				}
				
				json.put("ResBak1", cityCardId);
				json.put("PlateNum", visitorinfo.getPlateNum());
				json.put("ViMobile", visitorinfo.getViMobile());
				json.put("ViMail", visitorinfo.getViMail());
				json.put("status", 0);
			}else if(njhwTscard != null){
				json.put("ResBak1", njhwTscard.getCardId());
				json.put("VIName", njhwTscard.getUserName());
				json.put("status", 0);
			}else{
				json.put("status", 1);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/** 
	* @title: validCityCard
	* @description: 根据市民卡号从接口中查找
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String validCityCard() {
		JSONObject json = new JSONObject();
		try {
			Map card = personCardQueryToAppService.queryPersonCardForVisitor(getParameter("cityCard"));
			if (card != null) {
				String userDiff = (String) card.get("userDiff");
				String userName = (String) card.get("CustName");
				String residentNo = (String) card.get("SociCode");
				String loseStatus = (String) card.get("LoseStatus");
				String puicStatus = (String) card.get("PuicStatus");
				
				// 当市民卡用户类型为：访客或者未定义时
				if (userDiff == null || "2".equals(userDiff)) {
					json.put("VIName", userName);
					json.put("residentNo", residentNo);
					json.put("diff", "y");
					json.put("status", 0);
					json.put("LoseStatus", loseStatus);
				} else {
					json.put("status", 0);
					json.put("diff", "n");
				}
				
				if(puicStatus.equals("0")){
					json.put("status", 2);
				}
			} else {
				json.put("status", 1);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			try {
				json.put("status", 9);
			} catch (JSONException ex) {
				log.error(ex.getMessage());
			}
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 确认申请状态  	FLAG = 02
	 * @开发者：ywl
	 * @时间：2013-7-9
	 * @return
	 */
	public String newAffirm(){
		int status = 0;
		try {
			long vsid = Long.parseLong(getParameter("vsId"));
			//String vsComments = getParameter("vsCommets");
			//Date vsSt = DateUtil.str2Date(getParameter("vsSt"), "yyyy-MM-dd HH:mm");
			//Date vsEt = DateUtil.str2Date(getParameter("vsEt"), "yyyy-MM-dd HH:mm");
			
			status = myVisitManager.updateMyCallFlag(vsid, "02", "01");
			
			if (status == 0) Struts2Util.renderText("success");
			else if(status == 2) Struts2Util.renderText("ser1");
			else if(status == 3) Struts2Util.renderText("ser2");
			else Struts2Util.renderText("statusError");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 拒绝申请状态	FLAG = 03
	 * @开发者：ywl
	 * @时间：2013-7-9
	 * @return
	 */
	public String newRepulse() {
		int status = 0;
		try {
			long vsid = Long.parseLong(getParameter("vsId"));
//			String vsComments = getParameter("vsCommets");
//			Date vsSt = DateUtil.str2Date(getParameter("vsSt"), "yyyy-MM-dd HH:mm");
//			Date vsEt = DateUtil.str2Date(getParameter("vsEt"), "yyyy-MM-dd HH:mm");
//			
			// 03 为状态值  02为判断值   <>01
			status = myVisitManager.updateMyCallFlag(vsid, "03", "02");
			
			if (status == 0) Struts2Util.renderText("successRefuse");
			else if(status == 2) Struts2Util.renderText("ser1");
			else if(status == 3) Struts2Util.renderText("ser2");
			else Struts2Util.renderText("statusError");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取消预约
	 */
	public String newCannel() {
		int status = 0;
		try {
			long vsid = Long.parseLong(getParameter("vsId"));
			status = myVisitManager.updateMyCallCanner(vsid, "99");
			
			if(status == 4) Struts2Util.renderText("ser4");
			else if(status == 5) Struts2Util.renderText("successCannel");
			else Struts2Util.renderText("statusError");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* @title: visitorStatistics
	* @description: 访客统计
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String visitorStatistics() {
		return SUCCESS;
	}
	
	/** 
	* @title: visitorStatistics
	* @description: 访客导航
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String visitorNavigation() {
		return SUCCESS;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public MyVisitManager getMyVisitManager() {
		return myVisitManager;
	}

	public VisitModel getVisitModel() {
		return visitModel;
	}

	public void setVisitModel(VisitModel visitModel) {
		this.visitModel = visitModel;
	}

	public void setMyVisitManager(MyVisitManager myVisitManager) {
		this.myVisitManager = myVisitManager;
	}
	
	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	@Override
	public VisitModel getModel() {
		return visitModel;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		
	}

	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}

	public void setPersonCardQueryToAppService(
			PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}

	public VmVisit getVmVisit() {
		return vmVisit;
	}

	public void setVmVisit(VmVisit vmVisit) {
		this.vmVisit = vmVisit;
	}
}
