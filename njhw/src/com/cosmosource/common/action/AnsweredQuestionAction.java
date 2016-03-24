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
 * @类描述: 已回复问题贴Action
 * @作者： ycw
 * @时间：2012-7-10
 */
@SuppressWarnings("rawtypes")
public class AnsweredQuestionAction extends BaseAction {
	private static final long serialVersionUID = 4221950256830595688L;
	private TCommonVendorQuest vendorQuestion; // 问题贴对象
	private Page page = new Page(Constants.PAGESIZE);
	private TCommonVendorQuestModel tquestModel = new TCommonVendorQuestModel();
	private QuestionManager questionManager;

	public TCommonVendorQuest getModel() {
		if (vendorQuestion == null) {
			vendorQuestion = new TCommonVendorQuest();
		}
		return vendorQuestion;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	/**
	 * @描述:打开已回复问题贴追踪的初始化页面
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String init() {
		// 问题贴提出的起始日期
		tquestModel.setQuestTimeStart(DateUtil.getStrMonthFirstDay());
		// 问题贴提出的结束日期
		tquestModel.setQuestTimeEnd(DateUtil.getDateTime("yyyy-MM-dd"));
		return "answeredQuestionInit";
	}

	
	/**
	 * @描述:查询已回复问题贴
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String queryAllQuestions() {
		Map map = new HashMap();
		map.put("questStatus", "2");
		page= questionManager.findPageListBySql(page,"CommonSQL.selectQuestionList", vendorQuestion, tquestModel, map);
		return "allQuestionsList";
	}

	/**
	 * @描述:查询问题贴明细
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String detail() {
		vendorQuestion = (TCommonVendorQuest) questionManager.findById(TCommonVendorQuest.class, vendorQuestion.getQuestId());
		getRequest().setAttribute("vendorQuestion", vendorQuestion);
		Map  param = new HashMap();
		param.put("questId", vendorQuestion.getQuestId());// 得到问题贴的内容
		String content=questionManager.findQuestContent(param);
		getRequest().setAttribute("ansContent", content);
		List ansList=questionManager.findAnsList(param);//得到回复帖列表
		getRequest().setAttribute("ansList", ansList);
		getRequest().setAttribute("receiver",((TCommonVendorAns)ansList.get(ansList.size()-1)).getAnsUser());
		return "answeredQuestionDetail";
	}

	/**
	 * @描述:回复帖子
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String answer() {
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
		vendorQuestion.setQuestStatus("2");
		try {
			questionManager.saveOrUpdate(ans);
			//对问题贴做了回复之后，需要 将问题贴的状态设置为已回复
			questionManager.saveOrUpdate(vendorQuestion); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "answeredQuestionDetail";
	}

	/**
	 * @描述:关帖操作
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String closeQuestion() {
		boolean flag = false;
		try {
			long questId = vendorQuestion.getQuestId();
			vendorQuestion = (TCommonVendorQuest) questionManager.findById(TCommonVendorQuest.class, questId);
			vendorQuestion.setQuestStatus("4");// 关帖即把状态设为已关闭
			vendorQuestion.setClosedTime(new Date());
			String loginUser = (String) (this.getSession()
					.getAttribute(Constants.LOGIN_NAME));
			vendorQuestion.setClosedUser(loginUser);
			questionManager.saveOrUpdate(vendorQuestion);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("flag", flag);
		return "answeredQuestionDetail";
	}

	
	public TCommonVendorQuest getVendorQuestion() {
		return vendorQuestion;
	}

	public void setVendorQuestion(TCommonVendorQuest vendorQuestion) {
		this.vendorQuestion = vendorQuestion;
	}

	
	public Page getPage() {
		return page;
	}

	public TCommonVendorQuestModel getTquestModel() {
		return tquestModel;
	}

	public void setTquestModel(TCommonVendorQuestModel tquestModel) {
		this.tquestModel = tquestModel;
	}

	public QuestionManager getQuestionManager() {
		return questionManager;
	}

	public void setQuestionManager(QuestionManager questionManager) {
		this.questionManager = questionManager;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	

}
