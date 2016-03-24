package com.cosmosource.common.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TCommonVendorAns;
import com.cosmosource.common.entity.TCommonVendorQuest;
import com.cosmosource.common.model.TCommonVendorQuestModel;
import com.cosmosource.common.service.QuestionManager;

/**
 * 功能：结贴处理Action
 * 时间: 2012-7-11
 * @author ycw
 */
@SuppressWarnings("rawtypes")
public class DealQuestionAction extends BaseAction{
	private static final long serialVersionUID = 1048706182727231999L;
	private TCommonVendorQuest vendorQuestion; //问题贴对象
	private Page page = new Page(Constants.PAGESIZE);
	private TCommonVendorQuestModel tquestModel=new TCommonVendorQuestModel();
	private QuestionManager questionManager;
	private String questionIds;//选中记录的ID数组
	
	public TCommonVendorQuest getModel() {
		if(vendorQuestion==null){
			vendorQuestion=new TCommonVendorQuest();
		}
		return vendorQuestion;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}
	
	/**
	 * @描述:打开初始化页面
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String init(){
		//问题贴提出的起始日期
		tquestModel.setQuestTimeStart(DateUtil.getStrMonthFirstDay());
		//问题贴提出的结束日期
		tquestModel.setQuestTimeEnd(DateUtil.getDateTime("yyyy-MM-dd"));
		String orgType=(String)(this.getSession().getAttribute(Constants.ORG_TYPE));
		if(orgType.equals("1")){ //orgType为1，说明当前是tesco公司登录,转向待处理问题贴主页面
			return "dealingQuestionInit";
		}else{
			return "dealQuestionInit";
		}
	}
	
	
	/**
	 * @描述:查询状态时已回复的问题贴
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String query(){
		if(getRequest().getAttribute("flag")!=null){
			vendorQuestion.setQuestLevel(null);
			vendorQuestion.setQuestType(null);
			vendorQuestion.setQuestStatus(null);
			tquestModel.setQuestTimeStart(DateUtil.getStrMonthFirstDay());
			//问题贴提出的结束日期
			tquestModel.setQuestTimeEnd(DateUtil.getDateTime("yyyy-MM-dd"));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		String orgType=(String)(this.getSession().getAttribute(Constants.ORG_TYPE));
		String sql="CommonSQL.selectQuestionList";
		String loginName=(String)(this.getSession().getAttribute(Constants.LOGIN_NAME));
		if(orgType.equals("1")){ //orgType为1，说明当前是tesco公司登录,转向待处理问题贴主页面
			map.put("questStatus", vendorQuestion.getQuestStatus());
			map.put("loginName",loginName);
			page= questionManager.findPageListBySql(page,sql, vendorQuestion, tquestModel, map);
			return "allDealingQuestionsList";
		}else if(orgType.equals("0")){ //orgType为0，说明当前是Cosmosource公司登录
			map.put("questStatus", "2");
			map.put("loginName",loginName);
			page= questionManager.findPageListBySql(page,sql, vendorQuestion, tquestModel, map);
			return "allDealQuestionsList";
		}else{ //说明当前是供应商登录
			map.put("questStatus", "2");
			page= questionManager.findPageListBySql(page,sql, vendorQuestion, tquestModel, map);
			return "allDealQuestionsList";
		}
		
	}
	
	

	/**
	 * @描述:查看明细
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	@SuppressWarnings({"unchecked"})
	public String detail(){
		vendorQuestion = (TCommonVendorQuest) questionManager.findById(TCommonVendorQuest.class, vendorQuestion.getQuestId());
		getRequest().setAttribute("vendorQuestion", vendorQuestion);
		Map  param = new HashMap();
		param.put("questId", vendorQuestion.getQuestId());// 得到问题贴的内容
		String content=questionManager.findQuestContent(param);
		getRequest().setAttribute("ansContent", content);
		List ansList=questionManager.findAnsList(param);//得到回复帖列表
		getRequest().setAttribute("ansList", ansList);
		String orgType=(String)(this.getSession().getAttribute(Constants.ORG_TYPE));
		getRequest().setAttribute("orgType", orgType);
		getRequest().setAttribute("ansList", ansList);
		
		Map<String,Object> TescoAPRoleNameMap=new HashMap<String,Object>();
		TescoAPRoleNameMap.put("roleName","Tesco AP");
		String TescoAPRoleId=questionManager.findRoleByRoleName(TescoAPRoleNameMap);
		getRequest().setAttribute("TescoAPRoleId",TescoAPRoleId);
		return "dealQuestionDetail";
	}
	
	
	/**
	 * @描述:结贴操作
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String deal(){
		boolean flag=false;
		try {
			String[] questIdss = questionIds.split(",");
			for(String questId:questIdss){
				vendorQuestion=(TCommonVendorQuest)questionManager.findById(TCommonVendorQuest.class, Long.parseLong(questId));
				vendorQuestion.setClosedTime(new Date());
				vendorQuestion.setClosedUser((String)(this.getSession().getAttribute(Constants.LOGIN_NAME)));
				vendorQuestion.setQuestStatus("3");
				questionManager.saveOrUpdate(vendorQuestion);
			}
			flag=true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("flag",flag);
		return "allDealQuestionsList";
	}
	
	
	/**
	 * @描述:回复帖子
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String dealAnswer() {
		TCommonVendorAns ans=new TCommonVendorAns();
		String loginName=(String)(this.getSession().getAttribute(Constants.LOGIN_NAME));
		String ansCompany=(String)(this.getSession().getAttribute(Constants.COMPANY));
		String ansContent=getParameter("ansContent");
		long questId=vendorQuestion.getQuestId();
		ans.setAnsUser(loginName);
		ans.setAnsCompany(ansCompany);
		ans.setAnsContent(ansContent);
		ans.setAnsTime(new Date()); 
		ans.setQuestId(questId);
		ans.setAnsType("1");
		
		vendorQuestion=(TCommonVendorQuest)questionManager.findById(TCommonVendorQuest.class,questId);
		try {
			questionManager.saveOrUpdate(ans);
			String questStatus=vendorQuestion.getQuestStatus();
			if(!questStatus.equals("2") && !questStatus.equals("3") && !questStatus.equals("4") ){
				vendorQuestion.setQuestStatus("2"); 
				questionManager.saveOrUpdate(vendorQuestion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dealQuestionDetail";
	}

	
	
	
	/**
	 * @描述:转乐购处理
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String dealtoTescoDeal(){
		boolean flag=false;
		try {
			String TescoAPRoleId=getParameter("TescoAPRoleId");
			Map<String,Object> pMap=new HashMap<String,Object>();
			pMap.put("roleId",TescoAPRoleId);
			//首先查询出客服部门目前问题贴最少的员工的loginName
			String loginNameWithMinQuestion=questionManager.findLoginNameWithMinQuestion(pMap);
			long questId=vendorQuestion.getQuestId();
			vendorQuestion=(TCommonVendorQuest)questionManager.findById(TCommonVendorQuest.class,questId);
			vendorQuestion.setDcFlag("01");
			vendorQuestion.setLoginName(loginNameWithMinQuestion);//将登录名更新为有最少问题贴的那个员工。
			questionManager.saveOrUpdate(vendorQuestion);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("flag",flag);
		return "dealQuestionDetail";
	}
	
	
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public TCommonVendorQuestModel getTquestModel() {
		return tquestModel;
	}
	public void setTquestModel(TCommonVendorQuestModel tquestModel) {
		this.tquestModel = tquestModel;
	}
	public String getQuestionIds() {
		return questionIds;
	}
	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	public TCommonVendorQuest getVendorQuestion() {
		return vendorQuestion;
	}

	public void setVendorQuestion(TCommonVendorQuest vendorQuestion) {
		this.vendorQuestion = vendorQuestion;
	}
	public QuestionManager getQuestionManager() {
		return questionManager;
	}

	public void setQuestionManager(QuestionManager questionManager) {
		this.questionManager = questionManager;
	}
	

}
