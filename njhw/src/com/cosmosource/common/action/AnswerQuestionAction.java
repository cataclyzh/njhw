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
 * @类描述: 问题贴回复Action
 * @作者： ycw
 * @时间：2012-7-10
 */
@SuppressWarnings("rawtypes")
public class AnswerQuestionAction extends BaseAction {
	private static final long serialVersionUID = -7791042776635046587L;
	private TCommonVendorQuest vendorQuestion; // 问题贴对象
	@SuppressWarnings("unchecked")
	private Page<TCommonVendorQuest> page = new Page(Constants.PAGESIZE);
	private TCommonVendorQuestModel tquestModel = new TCommonVendorQuestModel();
	private QuestionManager questionManager; // 公用的管理类


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
	 * @描述:打开初始化页面
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String init() {
		// 问题贴提出的起始日期
		tquestModel.setQuestTimeStart(DateUtil.getStrMonthFirstDay());
		// 问题贴提出的结束日期
		tquestModel.setQuestTimeEnd(DateUtil.getDateTime("yyyy-MM-dd"));
		return "answerQuestionInit";
	}

	/**
	 * @描述:查询所有问题帖
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String queryAllQuestions() {
		Map map = new HashMap();
		map.put("questStatus","1");//状态处于：待回复
		String loginName=(String)(this.getSession().getAttribute(Constants.LOGIN_NAME));
		map.put("loginName",loginName);
		page= questionManager.findPageListBySql(page, "CommonSQL.selectQuestionList",vendorQuestion, tquestModel, map);
		return "allQuestionsList";
	}



	/**
	 * @描述:查看问题贴明细
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	@SuppressWarnings({"unchecked" })
	public String detail() {
		vendorQuestion=(TCommonVendorQuest) questionManager.findById(TCommonVendorQuest.class, vendorQuestion.getQuestId());
		getRequest().setAttribute("vendorQuestion", vendorQuestion);
		Map  param = new HashMap();
		param.put("questId", vendorQuestion.getQuestId());// 得到问题贴的内容
		String content=questionManager.findQuestContent(param);
		getRequest().setAttribute("ansContent", content);
		
		List ansList=questionManager.findAnsList(param);//得到回复帖列表
		getRequest().setAttribute("ansList", ansList);
		
		Map<String,Object> TescoAPRoleNameMap=new HashMap<String,Object>();
		TescoAPRoleNameMap.put("roleName","Tesco AP");
		String TescoAPRoleId=questionManager.findRoleByRoleName(TescoAPRoleNameMap);
		getRequest().setAttribute("TescoAPRoleId",TescoAPRoleId);
		return "answerQuestionDetail";
	}

	/**
	 * @描述:问题贴回复
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String answer() {
		TCommonVendorAns ans=new TCommonVendorAns();
		//根据当前登录人的名称，得到其所属的公司
		String loginName=(String)(this.getSession().getAttribute(Constants.LOGIN_NAME));
		String ansCompany=(String)(this.getSession().getAttribute(Constants.COMPANY));
		String ansContent=getParameter("ansContent");
		long questId=vendorQuestion.getQuestId();
		ans.setAnsUser(loginName);
		ans.setAnsCompany(ansCompany);
		ans.setAnsContent(ansContent);
		ans.setAnsTime(new Date());  //系统当前日期
		ans.setQuestId(questId);
		ans.setAnsType("1");
		
		vendorQuestion=(TCommonVendorQuest)questionManager.findById(TCommonVendorQuest.class,questId);
		vendorQuestion.setQuestStatus("2");
		try {
			questionManager.saveOrUpdate(ans);
			questionManager.saveOrUpdate(vendorQuestion); //对问题贴做了回复之后，需要 将问题贴的状态设置为已回复
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "answerQuestionDetail";
	}

	
	
	
	/**
	 * @描述:转乐购处理
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String toTescoDeal(){
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
		return "answerQuestionDetail";
	}
	

	
	public TCommonVendorQuest getVendorQuestion() {
		return vendorQuestion;
	}

	public void setVendorQuestion(TCommonVendorQuest vendorQuestion) {
		this.vendorQuestion = vendorQuestion;
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<TCommonVendorQuest> page) {
		this.page = page;
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
	

}
