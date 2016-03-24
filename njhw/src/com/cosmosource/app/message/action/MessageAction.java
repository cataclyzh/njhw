package com.cosmosource.app.message.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.message.service.FaxService;
import com.cosmosource.app.message.service.MessageService;
import com.cosmosource.app.message.service.OAService;
import com.cosmosource.app.message.utils.MessageUtils;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;

public class MessageAction{
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private MessageService messageService;
	
	private FaxService faxService;
	
	private OAService oaService;
	
	private long recordCount;

	private long pageNo;
	
	private long vsId;
	
	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public void setOaService(OAService oaService) {
		this.oaService = oaService;
	}
	
	public long getVsId() {
		return vsId;
	}

	public void setVsId(long vsId) {
		this.vsId = vsId;
	}

	public void setFaxService(FaxService faxService) {
		this.faxService = faxService;
	}

	private HttpServletRequest request;
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	/**
	 * Ajax请求Message信息
	 * @return
	 */
	public String queryMsgJSON(){
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		log.debug("Receiver ID: " + userId);
		
		Map m = new HashMap();
		m.put("receiverId", userId);
		
		List<Map> list = messageService.queryTitleMessage(m);
		List<Map> result = new ArrayList<Map>();
		for(int i=0; i<5; i++){
			try {
				result.add(list.get(i));
			} catch (Exception e) {
				break;
			}
		}
			
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/**
	 * Ajax查询待办事项信息
	 * @return
	 */
	public String queryMatter(){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		String uCode = null;
		try {
			uCode = getUcode();
		} catch (Exception e) {
			log.info("user has not uCode.");
		}
		
		if(uCode != null){
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			if(messageService.isFgwUser(userId)){
				list.addAll(messageService.getFgwOA(uCode.trim()));
			}else{
				try{
					List<Map<String, String>> listShouWen = oaService.oaShouWen(uCode);
					list.addAll(listShouWen);
				}catch(Exception e){
					log.error(e.getMessage());
				}
				
				try{
					List<Map<String, String>> listFaWen = oaService.oaFaWen(uCode);
					list.addAll(listFaWen);
				}catch(Exception e){
					log.error(e.getMessage());
				}
			}
		}

		Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * Ajax请求Visitor列表信息
	 * @开发者：ywl
	 * @时间：2013-7-8
	 * @return String
	 */
	public String queryVisitJSON(){
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		log.debug("USER_ID:" + userId);
		
		Map param = new HashMap();
		param.put("userId", userId);
		
		List<Map> listResult = messageService.queryVisitSortForTilte(userId);
		Struts2Util.renderJson(listResult, "encoding:UTF-8","no-cache:true");
		return null;
	}
	
	/**
	 * Ajax查询未读的Visitor数量
	 * @return
	 */
	public String getVisitorNotReadCount(){
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		log.debug("Receiver ID: " + userId);
		
		long id = Long.parseLong(userId);
		long num = messageService.getVisitorNotReadCount(id);
		Map result = new HashMap();
		result.put("NUM", num);
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * Ajax查询未读的Fax数量
	 * @return
	 */
	public String queryFaxUnReadNum(){
		long num = 0;
		
		try{
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			Map param = new HashMap();
			param.put("userId", userId);
			Map usrPwd = messageService.getUsrPwdByUserId(param);
			if(usrPwd == null){
				return null;
			}
//			String pwd;
			String usr = usrPwd.get("USR").toString();
//			if(usrPwd.get("PWD") == null){
//				pwd = "011c945f30ce2cbafc452f39840f025693339c42";
//			}else{
//				pwd = usrPwd.get("PWD").toString();
//			}
			
			num = faxService.getGetUnReadNum(usr, "");
		}catch(Exception e){
			log.error(e.getMessage());
		}
		Map result = new HashMap();
		result.put("NUM", num);
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 查询传真数据
	 * @return
	 */
	public List queryFaxList(){
		List<Map> resultList = new ArrayList<Map>();		
		try {
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			Map param = new HashMap();
			param.put("userId", userId);
			Map usrPwd = messageService.getUsrPwdByUserId(param);
			if(usrPwd == null){
				return null;
			}
			
//			String pwd;
			String usr = usrPwd.get("USR").toString();
//			if(usrPwd.get("PWD") == null){
//				pwd = "011c945f30ce2cbafc452f39840f025693339c42";
//			}else{
//				pwd = usrPwd.get("PWD").toString();
//			}
			resultList = faxService.getTitleInfo(usr, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Struts2Util.renderJson(resultList, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * Ajax读取待办事项数量
	 * @开发者：ywl
	 * @时间：2013-7-9
	 * @return
	 */
	public String queryMyMatterCount(){
		long num = 0;
		String uCode = null;
		try {
			uCode = getUcode();
		} catch (Exception e) {
			log.info("user has not uCode.");
		}
		if(uCode != null){
			
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			if(messageService.isFgwUser(userId)){
				//num += messageService.getFgwOA(userId).size();
				num = messageService.getFgwOACount(uCode.trim());
			}else{
				try{			
					List<Map<String, String>> listShouWen = oaService.oaShouWen(uCode);
					num += listShouWen.size();
				}catch(Exception e){
					log.error(e.getMessage());
				}
				try{
					List<Map<String, String>> listFaWen = oaService.oaFaWen(uCode);
					num += listFaWen.size();
				}catch(Exception e){
					log.error(e.getMessage());
				}
			}
		}
		
		Map result = new HashMap();
		result.put("NUM", num);
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 查询Message列表
	 * @开发者：ywl
	 * @return String
	 */
	public String queryMsgList(){
		request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		if(page == null || page == ""){
			page = "1";
		}
		int pageCount = 1;
		int num ;
		int pages = 1;
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		List<Map> list;
		try {
			pageCount  = Integer.parseInt(page);
			Map params = new HashMap();
			params.put("pageMax", pageCount * 10);
			params.put("pageMin", (pageCount - 1) * 10 + 1);
			params.put("receiverId", userId);
			
			list = messageService.queryMessage(params);
			Map par = new HashMap();
			par.put("receiverId", userId);
			long ls =messageService.queryMsgCount(par);
			long senderId;
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("SENDERID") != "" && list.get(i).get("SENDERID") != null){
					senderId = Long.parseLong(list.get(i).get("SENDERID").toString());
					if(senderId != 0){
						list.get(i).put("USERNAME",messageService.getUserName(senderId));
					}
				}
			}
			setRecordCount(ls);
			setPageNo(pageCount);
			
			num = Integer.parseInt(ls + "");
			if(num % 10 != 0 )
				pages = num / 10 + 1;
			else
				pages = num / 10;
			
			List<Map> listPage = new ArrayList();
			for(int i=0;i<pages;i++){
				Map mapPage = new HashMap();
				mapPage.put("pageNum", i);
				listPage.add(mapPage);
			}
			request.setAttribute("list", list);
			request.setAttribute("pagesCount", pages);
			request.setAttribute("listPage", listPage);
			
			request.setAttribute("recordCount", ls);
			request.setAttribute("pageNo", pageCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryMsgList";
	}
	
	/**
	 * 查询Visitor列表
	 * @return String
	 */
	public String queryMyCallList(){
		List<Map> list = new ArrayList<Map>();
		request = ServletActionContext.getRequest();
		String page = request.getParameter("page");
		String vsFlag = request.getParameter("statusFlag"); //取得当前页面所查询的状态，
		String vsTypeFlag = request.getParameter("vsTypeFlag"); //取得当前页面所查询的状态，
		int pageCount = 1;
		int num ;
		int pages = 1;
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		long id = Long.parseLong(userId);
		try {
			if(page == null || page == "")
				page = "1";
			if(vsFlag == null || vsFlag == "")
				vsFlag = "all";
			if(vsTypeFlag == null || vsTypeFlag == "")
				vsTypeFlag = "all";
			pageCount  = Integer.parseInt(page);
			
			Map param = new HashMap();
			param.put("userId", id);;
			param.put("pageMax", pageCount * 10);
			param.put("pageMin", (pageCount - 1) * 10 + 1);
			if(!vsFlag.equals("all"))
				param.put("vsFlag", vsFlag);
			if(!vsTypeFlag.equals("all"))
				param.put("vsType", vsTypeFlag);
			
			list = messageService.queryVisitorDetailInfo(param); //查询Visitor列表
			
			Map paramMap = new HashMap();
			paramMap.put("userId", id);
			if(!vsFlag.equals("all"))
				paramMap.put("vsFlag", vsFlag);
			if(!vsTypeFlag.equals("all"))
				param.put("vsType", vsTypeFlag);
			
			List ls = messageService.queryCountBroad(paramMap); //查询Visitor数量
			Map map = (Map) ls.get(0);
			num = Integer.parseInt(map.get("COU").toString());
			if(num % 10 != 0 )
				pages = num / 10 + 1;
			else
				pages = num / 10;
			
			List<Map> listPage = new ArrayList();
			for(int i=0;i<pages;i++){
				Map mapPage = new HashMap();
				mapPage.put("pageNum", i);
				listPage.add(mapPage);
			}
			
			for(int i=0; i<list.size(); i++){
				Map m = (Map) list.get(i);
				String status = m.get("VS_FLAG").toString();
				m.put("VSFLAG", MessageUtils.getVsFlag(status));
			}
			
			for(int i=0;i<list.size();i++){
				Map pm = (Map) list.get(i);
				String vtStatus = pm.get("VS_TYPE").toString();
				pm.put("VSTYPE", MessageUtils.getVsType(vtStatus));
			}
			
			request.setAttribute("queryVisitorDetailInfo", list);
			request.setAttribute("pagesCount", pages);
			request.setAttribute("listPage", listPage);
			
			request.setAttribute("recordCount",num);
			request.setAttribute("pageNo", pageCount);
			request.setAttribute("flag", vsFlag); //存放当前页面选择的状态，定位select框
			request.setAttribute("vtFlag", vsTypeFlag); //存放当前页面选择的状态，定位select框
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryMyCallList";
	}
	
	/**
	 * 查询OA列表
	 * @开发者：ywl
	 * @return
	 */
	public String queryMyMatter(){
		request = ServletActionContext.getRequest();
		String uCode = null;
		String page = request.getParameter("page");
		int num = 0;
		try {
			uCode = getUcode();
		} catch (Exception e) {
			log.info("user has not uCode.");
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		if(messageService.isFgwUser(userId)){
			list = messageService.getFgwOA(uCode.trim());
		}else{
			if(uCode != null){
				List<Map<String, String>> listShouWen = oaService.oaShouWen(uCode);
				List<Map<String, String>> listFaWen = oaService.oaFaWen(uCode);
				list.addAll(listShouWen);
				list.addAll(listFaWen);
			}
		}
		if(list.size() % 20 != 0)
			num = list.size() / 20 + 1;
		else
			num = list.size();
		if(page == null)
			page = "1";
		if(Integer.parseInt(page) * 20 > list.size())
			request.setAttribute("listMatter", list.subList((Integer.parseInt(page)- 1) * 20, list.size()));
		else
			request.setAttribute("listMatter", list.subList((Integer.parseInt(page) - 1) * 20, Integer.parseInt(page) * 20));
		request.setAttribute("page",page);
		request.setAttribute("recordCount", list.size());
		request.setAttribute("pageNo", page);
		request.setAttribute("pageSize", num);
		return "queryMyMatter";
	}
	
	/**
	 * 查询待办事项相信信息。没有数据库表，根据id 和list进行判断来取出id的详细信息
	 * @开发者：ywl
	 * @时间：2013-7-9
	 * @return
	 */
	public String queryMyMatterInfo(){
		try {
			request = ServletActionContext.getRequest();
			String id = request.getParameter("MessageItemGuid");
			
			String uCode = null;
			try {
				uCode = getUcode();
			} catch (Exception e) {
				log.info("user has not uCode.");
			}
			
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			
			//messageService.getFgwOA(userId).size();
			
			Map map = new HashMap();
			
			if(messageService.isFgwUser(userId)){
				Map<String,String> matterInfo = messageService.getFgwOAById(id.trim());
				
				map.put("MessageItemGuid", matterInfo.get("MESSAGEITEMGUID"));
				map.put("FromDispName", "发改委");
				map.put("Title", matterInfo.get("TITLE"));
				map.put("GenerateDate", matterInfo.get("GENERATEDATE"));
				map.put("LastFileDate", matterInfo.get("LASTFILEDATE"));
				map.put("ArchiveNo", matterInfo.get("ARCHIVENO"));
			}else{
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				if(uCode != null){
					List<Map<String, String>> listShouWen = oaService.oaShouWen(uCode);
					List<Map<String, String>> listFaWen = oaService.oaFaWen(uCode);
					list.addAll(listShouWen);
					list.addAll(listFaWen);
					for (int i=0;i<list.size();i++) {
						if(list.get(i).get("MESSAGEITEMGUID").equals(id)){
							map.put("MessageItemGuid", list.get(i).get("MESSAGEITEMGUID"));
							map.put("FromDispName", list.get(i).get("FROMDISPNAME"));
							map.put("Title", list.get(i).get("TITLE"));
							map.put("GenerateDate", list.get(i).get("GENERATEDATE"));
							map.put("LastFileDate", list.get(i).get("LASTFILEDATE"));
							map.put("ArchiveNo", list.get(i).get("ARCHIVENO"));
							break;
						}
					}
				}
			}
			request.setAttribute("mapMatter", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "queryMyMatterInfo";
	}
	
	/**
	 * 删除指定ID的Message数据
	 * @开发者：ywl
	 * @return String
	 */
	public String delMess(){
		request = ServletActionContext.getRequest();
		String id = request.getParameter("msgIds");
		String [] ids = null;
		ids = id.split(",");
		messageService.deleteMsgs(ids);
		
		return "delMess";
	}
	
	/*
	public String input(){
		return "input";
	}
	
	public String list(){
		return "list";
	}*/
	
	/**
	 * 更新访客信息已读未读状态
	 * @param Id
	 * @return
	 */
	/*
	public String changeVisitorInfoRead(String Id){
		long vs_Id = Long.parseLong(Id);
		messageService.updateVisitRead(vs_Id);
		return null;
	}*/
	
	/**
	 * Ajax查询未读消息数量
	 * @return
	 */
	public String queryMsgNotReadNum(){
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		log.debug("Receiver ID: " + userId);
		long id = Long.parseLong(userId);
		long num = messageService.queryMsgNotRead(id);
		Map result = new HashMap();
		result.put("NUM", num);
		Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	private String getUcode(){
		String result = null;
		try {
			String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			result = messageService.getUcode(userId);
			if(result == null){
				result = Struts2Util.getSession().getAttribute(Constants.LOGIN_NAME).toString();
			}
		} catch (Exception e) {
			result = Struts2Util.getSession().getAttribute(Constants.LOGIN_NAME).toString();
		}
		
		if(result != null && result.trim().length() == 0){
			result = null;
		}
		
		return result;
	}
}

