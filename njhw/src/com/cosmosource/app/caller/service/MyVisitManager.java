package com.cosmosource.app.caller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.caller.model.VisitModel;
import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.port.serviceimpl.DevicePermissionToAppService;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.app.port.serviceimpl.SmsSendMessageService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonMsgBox;

/** 
* @description: 我的来访列表、确认来访、拒绝来访、被访者预约功能
* @author zh
* @date 2013-03-19 
*/ 
@SuppressWarnings("unchecked")
public class MyVisitManager extends BaseManager {
	
	private PersonCardQueryToAppService personCardQueryToAppService;
	private DevicePermissionToAppService devicePermissionToApp;
	private SmsSendMessageService smsSendMessage;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * @description:查询来访列表数据
	 * @author zh
	 * @param page
	 * @param flag
	 * @param beginTime
	 * @param endTime
	 * @return Page<HashMap>
	 */
	public Page<HashMap> queryVisit(final Page<HashMap> page, String flag, String beginTime, String endTime) {
		Map<String,String> visitMap = new HashMap<String,String>();
		visitMap.put("flag", flag);
		visitMap.put("beginTime", beginTime);
		visitMap.put("endTime", endTime);
		visitMap.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return sqlDao.findPage(page, "CallerSQL.selectVisitList", visitMap);
	}
	
	/**
	 * @description:弹出版-根据vsid查询访客及访问事物
	 * @author zh
	 * @param vsid
	 * @return HashMap
	 */
	public HashMap loadVisitByVsid(long vsId) {
		Map<String,Long> visitMap = new HashMap<String,Long>();
		visitMap.put("vsId", vsId);
		visitMap.put("userId", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		HashMap map = (HashMap)sqlDao.findList("CallerSQL.loadVisitByVsid", visitMap).get(0);
		return (HashMap)sqlDao.findList("CallerSQL.loadVisitByVsid", visitMap).get(0);
	}
	
	/**
	 * @description:拒绝指定访问申请
	 * @author zh
	 * @param vsid 访问申请ID
	 * @return 
	 */
	public int savevisitRepulse(long vsId, String vsComments, Date vsSt, Date vsEt){
		int status = 0;
		VmVisit vmVisit = (VmVisit) dao.get(VmVisit.class, vsId);
		if (vmVisit.getVsFlag().equals(VmVisit.VS_FLAG_APP)) {
			vmVisit.setVsFlag(VmVisit.VS_FLAG_REFUSED);
			vmVisit.setVsCommets(vsComments);
			vmVisit.setVsSt(vsSt);
			vmVisit.setVsEt(vsEt);
			dao.update(vmVisit);
			
			TCommonMsgBox msgBox = new TCommonMsgBox();
			VmVisitorinfo visitorinfo = (VmVisitorinfo) dao.get(VmVisitorinfo.class, vmVisit.getViId());
			// 发送消息给注册过的访客
			if (visitorinfo.getNvrId() != null && StringUtil.isNotEmpty(visitorinfo.getViWname())) {
				String messageText = "访问通知";
				msgBox.setSender("Administrator");
				msgBox.setReceiver(visitorinfo.getViWname());	// 使用登录名关联
				msgBox.setReceiverid(visitorinfo.getNvrId());
				msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
				msgBox.setTitle(messageText.substring(0, messageText.length() > 30 ? 30 : messageText.length()) + "...");
				msgBox.setContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请被工作人员："+Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString()+"拒绝，拒绝理由："+vsComments);
				msgBox.setStatus("0");			// 未读
				dao.save(msgBox);
			}
			// 发送消息给被访者
//			msgBox = new TCommonMsgBox();
//			String messageText1 = "访问通知";
//			msgBox.setSender("Administrator");
//			msgBox.setReceiver(Struts2Util.getSession().getAttribute(Constants.LOGIN_NAME).toString());	// 使用登录名关联
//			msgBox.setReceiverid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
//			msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
//			msgBox.setTitle(messageText1.substring(0, messageText1.length() > 30 ? 30 : messageText1.length()) + "...");
//			msgBox.setContent("您好，"+Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString()+"您于"+DateUtil.getDateTime("yyyy-MM-dd hh:mm")+"拒绝"+visitorinfo.getViName()+"来访，拒绝理由："+vsComments);
//			msgBox.setStatus("0");			// 未读
//			dao.save(msgBox);
			
			// 给访客发送提示短信
			SmsMessage sms = new SmsMessage();
			sms.setPhoneNumber(visitorinfo.getViMobile());
			sms.setSendContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请被拒绝");
			smsSendMessage.send(sms);
			//dao.flush();
		} else {
			status = 1;
		}
		return status;
	}
	
	
	/**
	 * @description:确认指定访问申请
	 * @author zh
	 * @param vsid 访问申请ID
	 * @return 
	 */
	public int savevisitAffirm(long vsId, String vsComments, Date vsSt, Date vsEt){
		int status = 0;
		VmVisit vmVisit = (VmVisit) dao.get(VmVisit.class, vsId);
		if (vmVisit.getVsFlag().equals(VmVisit.VS_FLAG_APP)) {
			vmVisit.setVsFlag(VmVisit.VS_FLAG_SURE);
			vmVisit.setVsCommets(vsComments);
			vmVisit.setVsSt(vsSt);
			vmVisit.setVsEt(vsEt);
			dao.update(vmVisit);
			
			TCommonMsgBox msgBox = new TCommonMsgBox();
			VmVisitorinfo visitorinfo = (VmVisitorinfo) dao.get(VmVisitorinfo.class, vmVisit.getViId());
			// 发送消息给注册过的访客
			if (visitorinfo.getNvrId() != null && StringUtil.isNotEmpty(visitorinfo.getViWname())) {
				String messageText = "访问通知";
				msgBox.setSender("Administrator");
				msgBox.setReceiver(visitorinfo.getViWname());	// 使用登录名关联
				msgBox.setReceiverid(visitorinfo.getNvrId());
				msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
				msgBox.setTitle(messageText.substring(0, messageText.length() > 30 ? 30 : messageText.length()) + "...");
				msgBox.setContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请已被确认，请您于"+DateUtil.date2Str(vmVisit.getVsSt(), "yyyy-MM-dd hh:mm")+"按时来访,逾期无效");
				msgBox.setStatus("0");			// 未读
				dao.save(msgBox);
			}
			// 给被访者添加操作信息提示
//			msgBox = new TCommonMsgBox();
//			String messageText1 = "访问通知";
//			msgBox.setSender("Administrator");
//			msgBox.setReceiver(Struts2Util.getSession().getAttribute(Constants.LOGIN_NAME).toString());	// 使用登录名关联
//			msgBox.setReceiverid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
//			msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
//			msgBox.setTitle(messageText1.substring(0, messageText1.length() > 30 ? 30 : messageText1.length()) + "...");
//			msgBox.setContent("您好，"+Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString()+"您于"+DateUtil.getDateTime("yyyy-MM-dd hh:mm")+"确认了"+visitorinfo.getViName()+"提交的来访");
//			msgBox.setStatus("0");			// 未读
//			dao.save(msgBox);
			
			// 给访客发送提示短信
			SmsMessage sms = new SmsMessage();
			sms.setPhoneNumber(visitorinfo.getViMobile());
			sms.setSendContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请已确认，请您于"+DateUtil.date2Str(vmVisit.getVsSt(), "yyyy-MM-dd hh:mm")+"按时来访,逾期无效");
			smsSendMessage.send(sms);
			//dao.flush();
			
			// 调用接口给市民卡进出设备权限
//			if (StringUtil.isNotEmpty(vmVisit.getCardId())) {
//			
//			}
		} else {
			status = 1;
		}
		return status;
	}
	
	/**
	 * @description:取消指定访问申请
	 * @author zh
	 * @param vsid 访问申请ID
	 * @param cancelInfo 取消原因
	 * @return 
	 */
	public int savevisitCancel(long vsId, String cancelInfo){
		int status = 0;
		VmVisit vmVisit = (VmVisit) dao.get(VmVisit.class, vsId);
		if (vmVisit.getVsFlag().equals(VmVisit.VS_FLAG_SURE)) {
			vmVisit.setVsFlag(VmVisit.VS_FLAG_CANCELED);
			vmVisit.setCancelInfo(cancelInfo);
			dao.update(vmVisit);
			
			TCommonMsgBox msgBox = new TCommonMsgBox();
			VmVisitorinfo visitorinfo = (VmVisitorinfo) dao.get(VmVisitorinfo.class, vmVisit.getViId());
			// 发送消息给注册过的访客
			if (visitorinfo.getNvrId() != null && StringUtil.isNotEmpty(visitorinfo.getViWname())) {
				String messageText = "访问通知";
				msgBox.setSender("Administrator");
				msgBox.setReceiver(visitorinfo.getViWname());	// 使用登录名关联
				msgBox.setReceiverid(visitorinfo.getNvrId());
				msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
				msgBox.setTitle(messageText.substring(0, messageText.length() > 30 ? 30 : messageText.length()) + "...");
				msgBox.setContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请被工作人员："+Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString()+"取消，取消理由："+cancelInfo);
				msgBox.setStatus("0");			// 未读
				dao.save(msgBox);
			}
			// 给被访者添加操作信息提示
//			msgBox = new TCommonMsgBox();
//			String messageText1 = "访问通知";
//			msgBox.setSender("Administrator");
//			msgBox.setReceiver(Struts2Util.getSession().getAttribute(Constants.LOGIN_NAME).toString());	// 使用登录名关联
//			msgBox.setReceiverid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
//			msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
//			msgBox.setTitle(messageText1.substring(0, messageText1.length() > 30 ? 30 : messageText1.length()) + "...");
//			msgBox.setContent("您好，"+Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString()+"您于"+DateUtil.getDateTime("yyyy-MM-dd hh:mm")+"取消了"+visitorinfo.getViName()+"提交的来访，取消理由："+cancelInfo);
//			msgBox.setStatus("0");			// 未读
//			dao.save(msgBox);
			
			// 给访客发送提示短信
			SmsMessage sms = new SmsMessage();
			sms.setPhoneNumber(visitorinfo.getViMobile());
			sms.setSendContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请由于"+ cancelInfo +"已取消，请您谅解");
			smsSendMessage.send(sms);
			//dao.flush();
		} else {
			status = 1;
		}
		return status;
	}
	
	/**
	 * @description:根据身份证验证
	 * @author zh
	 * @param cardId
	 * @return  VmVisitorinfo
	 */
	public VmVisitorinfo loadVisitorInfo(String cardId) {
		List<VmVisitorinfo> list = dao.findByHQL("select t from VmVisitorinfo t where t.residentNo=?", cardId);
		if (list.size() > 0 && list != null)	return list.get(0);
		else return null;
	}
	
	public NjhwTscard loadNjhwTscard(String cardId){
		List<NjhwTscard> list = dao.findByHQL("select t from NjhwTscard t where t.residentNo=?", cardId);
		if (list.size() > 0 && list != null)	return list.get(0);
		else return null;
	}
	
	/**
	 * @description:根据市民卡验证
	 * @author zh
	 * @param cardId
	 * @return  VmVisitorinfo
	 */
	public VmVisitorinfo loadTscardInfo(String cityCard) {
		List<VmVisitorinfo> list = dao.findByHQL("select t from VmVisitorinfo t where t.resBak1=?", cityCard);
		if (list.size() > 0 && list != null)	return list.get(0);
		else return null;
	}
	
	/**
	 * @description:根据机构ID加载机构对象
	 * @author zh
	 * @param orgId
	 * @return  VmVisitorinfo
	 */
	public Org loadOrgInfo(Long orgId) {
		return (Org)dao.findById(Org.class, orgId);
	}
	
	/**
	 * 添加或者更新访客资料
	 */
	private void changeVMVisitorInfo(VmVisitorinfo vi, VisitModel model, String residentNo, boolean isNewVisitor){
		long userId = Long.parseLong(Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString());		
		vi.setViName(model.getUserName());
		vi.setResidentNo(residentNo);
		vi.setViMail(model.getEmail());
		vi.setViMobile(model.getPhone());
		vi.setPlateNum(model.getCarId());
		vi.setUseFlag("1");
		vi.setIsBlack(VmVisitorinfo.IS_BLACK_NO);
		String cityCard = model.getCityCard();
		if(cityCard != null && cityCard.trim().length() == 16){
			vi.setResBak1(cityCard);
		}
		if(!isNewVisitor){
			vi.setModifyDate(DateUtil.getSysDate());
			vi.setModifyId(userId);
			dao.update(vi);
		} else {
			vi.setInsertDate(DateUtil.getSysDate());
			vi.setInsertId(userId);
			dao.save(vi);
		}
	}
	
	/**
	 * 
	 * @param cardId
	 * @param vs_st
	 * @return
	 * @throws Exception 
	 */
	public Map checkVisitorTime(String cardId, String cityId, String vs_st, String vs_et){
		Map result = new HashMap();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long vs_stL;
		long vs_etL;
		try {
			vs_stL = sdf.parse(vs_st).getTime();
			vs_etL = sdf.parse(vs_et).getTime();
		} catch (ParseException e) {
			result.put("STATUS", "1");
			result.put("responseStr", "预约时间格式错误");
			return result;
		}
		
//		Map param = new HashMap();
//		param.put("cardId", cardId);
//		param.put("cityId", cityId);
//		List<Map> list = sqlDao.findList("CallerSQL.getUserVisitInfos", param);
//		
//		for(Map m : list){
//			String st = (String) m.get("VS_ST");
//			String et = (String) m.get("VS_ET");
//			String userName = (String) m.get("USER_NAME");
//			
//			long stL;
//			long etL;
//			try {
//				stL = sdf.parse(st).getTime();
//				etL = sdf.parse(et).getTime();
//			} catch (ParseException e) {
//				log.error(e.getMessage());
//				continue;
//			}
//			
//			log.info("vs_st" + vs_st);
//			log.info("vs_et" + vs_et);
//			log.info("st" + st);
//			log.info("et" + et);
//			
//			if((stL >= vs_stL && stL <= vs_etL) || 
//					(etL >= vs_stL && etL <= vs_etL) || 
//					(vs_stL >= stL && vs_stL <= etL) || 
//					(vs_etL >= stL && vs_etL <= etL)){
//				String responseStr = "此用户在"+st+"到"+et+"时间段已经被[" 
//						+ userName.trim() + "]预约.";
//				result.put("STATUS", "1");
//				result.put("responseStr", responseStr);
//				return result;
//			}
//		}
		result.put("STATUS", "0");
		return result;
	}
	
	/**
	 * 处理市民卡预约
	 * @param visitModel
	 */
	public int saveVisitForCityCard(VisitModel visitModel) throws Exception {
		Map card = personCardQueryToAppService.queryPersonCardForVisitor(visitModel.getCityCard());
		String residentNo = null;
		Object residentNoObj = card.get("SociCode");
		if(residentNoObj != null){
			residentNo = card.get("SociCode").toString();
		}
		
		List<VmVisitorinfo> listVmVisitorinfo = dao.findByHQL("select t from VmVisitorinfo t where t.residentNo=?", 
				residentNo);
		
		VmVisitorinfo visitorinfo = null;
		if (listVmVisitorinfo != null && listVmVisitorinfo.size() > 0){
			visitorinfo = listVmVisitorinfo.get(0);
			changeVMVisitorInfo(visitorinfo, visitModel, residentNo, false);
		}else{
			visitorinfo = new VmVisitorinfo();
			changeVMVisitorInfo(visitorinfo, visitModel, residentNo, true);
		}
	
		// 新建本次预约记录
		VmVisit vmVisit = new VmVisit();
		/**
		 * 预约方式设定
		 * cardType 1: 市民卡
		 * cardType 2: 临时卡
		 * resBak1 1: 市民卡预约
		 * resBak1 2: 身份证预约
		 */
		vmVisit.setCardType("1");
		vmVisit.setResBak1("1");
		vmVisit.setViId(visitorinfo.getViId());
		vmVisit.setViName(visitorinfo.getViName());
		vmVisit.setUserId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		vmVisit.setUserName(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
		vmVisit.setVsNum(visitModel.getComeNum());		
		Org org = loadOrgInfo(Long.parseLong(Struts2Util.getSession()
				.getAttribute(Constants.ORG_ID).toString()));
		vmVisit.setOrgId(org.getOrgId());
		vmVisit.setOrgName(org.getName());
		vmVisit.setVsInfo(visitModel.getComeWhy());
		vmVisit.setVsSt(visitModel.getBeginTime());
		
		vmVisit.setVsEt(visitModel.getEndTime());
		vmVisit.setVsFlag(VmVisit.VS_FLAG_SURE);
		//设置1表示主动约访
		vmVisit.setVsType("1");
		vmVisit.setInsertDate(DateUtil.getSysDate());
		vmVisit.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		
		vmVisit.setCardId(visitModel.getCityCard());
		
		//默认设置状态字为有效
		vmVisit.setStatus("1");
		dao.save(vmVisit);
		
		// 给访客发送提示短信
		try {
			String result = sendSmsMsg(visitModel);
			if(result != null && result.trim().length() != 0){
				return 1;
			}
		} catch (Exception e) {			
			return 1;
		}
		
		return 0;
	}
	
	private String sendSmsMsg(VisitModel visitModel){
		SmsMessage sms = new SmsMessage();
		sms.setPhoneNumber(visitModel.getPhone());
		String userName = Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString();
		Date visitDate = visitModel.getBeginTime();
		Calendar c = Calendar.getInstance();
		c.setTime(visitDate);
        sms.setSendContent("您好，"+visitModel.getUserName()
        		+" 您已受到 "+ userName
				+"的邀约 " +" 于[" + c.get(Calendar.YEAR) + "年"
				+(c.get(Calendar.MONTH)+1) + "月"
				+c.get(Calendar.DATE) + "日"
				+c.get(Calendar.HOUR_OF_DAY) + "时"
				+c.get(Calendar.MINUTE) + "分"
				+ "]到新城大厦访问");
		return smsSendMessage.send(sms);
	}

	/**
	 * 处理身份证预约
	 * @param visitModel
	 */
	public int saveVisitForCardId(VisitModel visitModel) {
		//来访信息记录
		VmVisit vmVisit = new VmVisit();
		// 访客资料
		VmVisitorinfo visitorinfo = new VmVisitorinfo();
		// 根据身份证号验证
		String cardId = visitModel.getCardId();

		List<VmVisitorinfo> listVmVisitorinfo = dao.findByHQL("select t from VmVisitorinfo t where t.residentNo=?", 
				visitModel.getCardId());
		if (listVmVisitorinfo != null && listVmVisitorinfo.size() > 0){
			visitorinfo = listVmVisitorinfo.get(0);
			changeVMVisitorInfo(visitorinfo, visitModel, cardId, false);
		}else{
			changeVMVisitorInfo(visitorinfo, visitModel, cardId, true);
		}
		
		/**
		 * 预约方式设定
		 * cardType 1: 市民卡
		 * cardType 2: 临时卡
		 * resBak1 1: 市民卡预约
		 * resBak1 2: 身份证预约
		 */
		vmVisit.setCardType("2");
		vmVisit.setResBak1("2");
		
		vmVisit.setViId(visitorinfo.getViId());
		vmVisit.setViName(visitorinfo.getViName());
		vmVisit.setUserId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		vmVisit.setUserName(Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString());
		vmVisit.setVsNum(visitModel.getComeNum());		
		Org org = loadOrgInfo(Long.parseLong(Struts2Util.getSession()
				.getAttribute(Constants.ORG_ID).toString()));
		vmVisit.setOrgId(org.getOrgId());
		vmVisit.setOrgName(org.getName());
		vmVisit.setVsInfo(visitModel.getComeWhy());
		vmVisit.setVsSt(visitModel.getBeginTime());
		
		vmVisit.setVsEt(visitModel.getEndTime());
		vmVisit.setVsFlag(VmVisit.VS_FLAG_SURE);
		//设置1表示主动约访
		vmVisit.setVsType("1");
		vmVisit.setInsertDate(DateUtil.getSysDate());
		vmVisit.setInsertId(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		
		vmVisit.setCardId(visitModel.getCityCard());
		
		vmVisit.setStatus("1");
		dao.save(vmVisit);
		
		// 给访客发送提示短信
		try {
			String result = sendSmsMsg(visitModel);
			if(result != null && result.trim().length() != 0){
				return 1;
			}
		} catch (Exception e) {			
			return 1;
		}
		
		return 0;
	}
	
	/**
	 * 根据ID修改该条信息的申请状态 （确认/拒绝）
	 * @开发者：ywl
	 * @时间：2013-7-9
	 * @param id
	 * @param flag
	 */
	public int updateMyCallFlag(long id,String flag,String vs_flag_value){
		int status = 0;
		try {
			VmVisit vmVisit = (VmVisit) dao.get(VmVisit.class, id);
			//vmVisit.getVsFlag().equals(VmVisit.VS_FLAG_APP) && 
			if ("01".equals(vs_flag_value)) {
				TCommonMsgBox msgBox = new TCommonMsgBox();
				VmVisitorinfo visitorinfo = (VmVisitorinfo) dao.get(VmVisitorinfo.class, vmVisit.getViId());
				// 发送消息给注册过的访客
				if (visitorinfo.getNvrId() != null && StringUtil.isNotEmpty(visitorinfo.getViWname())) {
					String messageText = "访问通知";
					msgBox.setSender("Administrator");
					msgBox.setReceiver(visitorinfo.getViWname());	// 使用登录名关联
					msgBox.setReceiverid(visitorinfo.getNvrId());
					msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
					msgBox.setTitle(messageText.substring(0, messageText.length() > 30 ? 30 : messageText.length()) + "...");
					msgBox.setContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请已被确认，请您于"+DateUtil.date2Str(vmVisit.getVsSt(), "yyyy-MM-dd hh:mm")+"按时来访,逾期无效");
					msgBox.setStatus("0");			// 未读
					dao.save(msgBox);
				}
				// 给访客发送提示短信
				SmsMessage sms = new SmsMessage();
				sms.setPhoneNumber(visitorinfo.getViMobile());
				
				Calendar c = Calendar.getInstance();
				c.setTime(vmVisit.getVsSt());
				String visitTimeStr = + c.get(Calendar.YEAR) + "年"
						+(c.get(Calendar.MONTH)+1) + "月"
						+c.get(Calendar.DATE) + "日"
						+c.get(Calendar.HOUR_OF_DAY) + "时"
						+c.get(Calendar.DATE) + "分";
				
				sms.setSendContent("您好，您向"+vmVisit.getOrgName()
						+"提交的访问申请已确认，请您于["+visitTimeStr+"]按时来访,逾期无效");
				try {
					String result = smsSendMessage.send(sms);
					if(result != null && result.trim().length() != 0){
						status = 2;
					}
				} catch (Exception e) {
					status = 2;
				}
			}else{
				TCommonMsgBox msgBox = new TCommonMsgBox();
				VmVisitorinfo visitorinfo = (VmVisitorinfo) dao.get(VmVisitorinfo.class, vmVisit.getViId());
				// 发送消息给注册过的访客
				if (visitorinfo.getNvrId() != null && StringUtil.isNotEmpty(visitorinfo.getViWname())) {
					String messageText = "访问通知";
					msgBox.setSender("Administrator");
					msgBox.setReceiver(visitorinfo.getViWname());	// 使用登录名关联
					msgBox.setReceiverid(visitorinfo.getNvrId());
					msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
					msgBox.setTitle(messageText.substring(0, messageText.length() > 30 ? 30 : messageText.length()) + "...");
					msgBox.setContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请被工作人员："+Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString()+"拒绝，拒绝理由：此访问申请状态已变更，不能继续操作！");
					msgBox.setStatus("0");			// 未读
					dao.save(msgBox);
				}
				// 给访客发送提示短信
				SmsMessage sms = new SmsMessage();
				sms.setPhoneNumber(visitorinfo.getViMobile());
				sms.setSendContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请被拒绝");
				try {
					String result = smsSendMessage.send(sms);
					if(result != null && result.trim().length() != 0){
						status = 3;
					}
				} catch (Exception e) {
					status = 3;
				}
			}
			
			Map map = new HashMap();
			map.put("vsId", id);
			map.put("vsFlag", flag);
			List<Map> list = new ArrayList<Map>();
			list.add(map);
			sqlDao.batchUpdate("CallerSQL.updateFlagMyCall", list);
		} catch (Exception e) {
			status = 1;
			e.printStackTrace();
		}
		return status;
	}
	
	private String getStrDateInfo(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String visitTimeStr = + c.get(Calendar.YEAR) + "年"
				+(c.get(Calendar.MONTH)+1) + "月"
				+c.get(Calendar.DATE) + "日"
				+c.get(Calendar.HOUR_OF_DAY) + "时"
				+c.get(Calendar.DATE) + "分";
		return visitTimeStr;
	}
	
	public int updateMyCallCanner(long id,String flag){
		int status = 0;
		try {
			VmVisit vmVisit = (VmVisit) dao.get(VmVisit.class, id);
			//vmVisit.getVsFlag().equals(VmVisit.VS_FLAG_APP) && 
			TCommonMsgBox msgBox = new TCommonMsgBox();
			VmVisitorinfo visitorinfo = (VmVisitorinfo) dao.get(VmVisitorinfo.class, vmVisit.getViId());
			// 发送消息给注册过的访客
//			if (visitorinfo.getNvrId() != null && StringUtil.isNotEmpty(visitorinfo.getViWname())) {
//				String messageText = "访问通知";
//				msgBox.setSender("Administrator");
//				msgBox.setReceiver(visitorinfo.getViWname());	// 使用登录名关联
//				msgBox.setReceiverid(visitorinfo.getNvrId());
//				msgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_VISIT);
//				msgBox.setTitle(messageText.substring(0, messageText.length() > 30 ? 30 : messageText.length()) + "...");
//				msgBox.setContent("您好，您向"+vmVisit.getOrgName()+"提交的访问申请被工作人员："+Struts2Util.getSession().getAttribute(Constants.USER_NAME).toString()+"拒绝，拒绝理由：此访问申请状态已变更，不能继续操作！");
//				msgBox.setStatus("0");			// 未读
//				dao.save(msgBox);
//			}
			// 给访客发送提示短信
			SmsMessage sms = new SmsMessage();
			sms.setPhoneNumber(visitorinfo.getViMobile());
			String stime = getStrDateInfo(vmVisit.getVsSt());
			String etime = getStrDateInfo(vmVisit.getVsEt());
			sms.setSendContent("您好，"+vmVisit.getOrgName()+"已经取消您从"+stime+"到"+etime+"的预约！");
			try {
				String result = smsSendMessage.send(sms);
				if(result != null && result.trim().length() != 0){
					status = 4;
				}else{
					status = 5;
				}
			} catch (Exception e) {
				status = 4;
			}
			
			Map map = new HashMap();
			map.put("vsId", id);
			map.put("vsFlag", flag);
			List<Map> list = new ArrayList<Map>();
			list.add(map);
			sqlDao.batchUpdate("CallerSQL.updateFlagMyCall", list);
		} catch (Exception e) {
			status = 1;
			e.printStackTrace();
		}
		return status;
	}
	
	public DevicePermissionToAppService getDevicePermissionToApp() {
		return devicePermissionToApp;
	}

	public void setDevicePermissionToApp(
			DevicePermissionToAppService devicePermissionToApp) {
		this.devicePermissionToApp = devicePermissionToApp;
	}

	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}

	public void setPersonCardQueryToAppService(
			PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}

	public SmsSendMessageService getSmsSendMessage() {
		return smsSendMessage;
	}

	public void setSmsSendMessage(SmsSendMessageService smsSendMessage) {
		this.smsSendMessage = smsSendMessage;
	}
}