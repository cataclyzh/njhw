package com.cosmosource.common.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.cosmosource.base.action.ExportAction;
import com.cosmosource.base.service.DBManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TCommonVendorAns;
import com.cosmosource.common.entity.TCommonVendorAnsfile;
import com.cosmosource.common.entity.TCommonVendorQuest;
import com.cosmosource.common.model.TCommonVendorQuestModel;
import com.cosmosource.common.service.ComManager;
import com.cosmosource.common.service.QuestionManager;

/**
 * @类描述: 审批问题贴Action
 * @作者： ycw
 * @时间：2012-7-10
 */
public class ApproveQuestionAction extends ExportAction<TCommonVendorQuest>{
	private static final long serialVersionUID = 26708848550903414L;
	private TCommonVendorQuest vendorQuestion; //问题贴对象
	private Page<TCommonVendorQuest> page = new Page<TCommonVendorQuest>(Constants.PAGESIZE);
	private TCommonVendorQuestModel tquestModel=new TCommonVendorQuestModel();
	private QuestionManager questionManager;
	private String questionIds;//选中记录的ID数组
	private String approveResult;//审核结果
	private String roleId; //角色Id
		
	
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
		return "init";
	}
	
	
	/**
	 * @描述:查询所有待审批的问题贴
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public String queryApprovingQuestions(){
		Map map=new HashMap();
		map.put("questStatus","0");//状态处于：待审核
		page= questionManager.findPageListBySql(page,"CommonSQL.selectQuestionList", vendorQuestion, getTquestModel(), map);
		return "approvingQuestionsList";
	}
	

	/**
	 * @描述:问题贴明细
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public String approveQuestionDetail(){
		try {
			// 得到问题贴信息
			vendorQuestion = (TCommonVendorQuest) questionManager.findById(TCommonVendorQuest.class, vendorQuestion.getQuestId());
			Map  param = new HashMap();
			param.put("questId", vendorQuestion.getQuestId());// 得到问题贴的内容
			String content=questionManager.findQuestContent(param);
			
			List fileList=questionManager.findAnsFileList(param);//查询发帖时上传的文件列表
			getRequest().setAttribute("ansContent", content);
			getRequest().setAttribute("vendorQuestion", vendorQuestion);
			getRequest().setAttribute("fileList",fileList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "approveQuestionDetail";
	}
	
	
	/**
	 * @功能：文件的下载
	 * @author ycw
	 * @return
	 * @throws Exception
	 */
	public String download() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter download... ");
		}
		String fileId = getParameter("fileId");
		TCommonVendorAnsfile tVendorAnsfile = (TCommonVendorAnsfile)questionManager.findById(TCommonVendorAnsfile.class, new Long(fileId));
		OutputStream os = this.getResponse().getOutputStream();
		InputStream is = tVendorAnsfile.getFileContent().getBinaryStream();
		String fileName = tVendorAnsfile.getFileName();
		String fileExtension = tVendorAnsfile.getFileExtension();
	    logger.info("fileName:" + fileName);
	    logger.info("fileExtension:" + fileExtension);
		try {
			String contentType = this.getContentType(fileExtension);
			this.getResponse().setContentType(contentType + " ;charset=gb2312");
			this.getResponse().addHeader("content-type",contentType);
			this.getResponse().addHeader("Content-Disposition","attachment;filename="+ encodeFileName(fileName));
			byte[] buffer = new byte[4096];
          int len = 0;
          while ((len = is.read(buffer)) > 0) {
              os.write(buffer, 0, len);
          }
			os.flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(os); 
			IOUtils.closeQuietly(is); 
		}
	    return null;
	}
	
	
	/**
	 * @描述:打开审批的初始化界面
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String approveInit(){
		//得到问题贴信息
		vendorQuestion=(TCommonVendorQuest) questionManager.findById(TCommonVendorQuest.class, vendorQuestion.getQuestId());
		getRequest().setAttribute("vendorQuestion", vendorQuestion);
		
		//得到问题贴的内容
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("questId", vendorQuestion.getQuestId());
		String content=questionManager.findQuestContent(params);
		getRequest().setAttribute("ansContent",content);
		String loginUser=(String)(this.getSession().getAttribute(Constants.LOGIN_NAME));
		getRequest().setAttribute("loginUser", loginUser);
		getRequest().setAttribute("currentTime",new Date());
		
		//将角色名称对应的角色Id放到jsp页面
		Map<String,Object> HloytaxRoleNameMap=new HashMap<String,Object>();
		HloytaxRoleNameMap.put("roleName","Cosmosource 400");
		String Holy400RoleId=questionManager.findRoleByRoleName(HloytaxRoleNameMap);
		getRequest().setAttribute("Holy400RoleId",Holy400RoleId);
		
		Map<String,Object> TescoAPRoleNameMap=new HashMap<String,Object>();
		TescoAPRoleNameMap.put("roleName","Tesco AP");
		String TescoAPRoleId=questionManager.findRoleByRoleName(TescoAPRoleNameMap);
		getRequest().setAttribute("TescoAPRoleId",TescoAPRoleId);
		return "approveQuestionInit";
	}
	
	
	/**
	 * @描述:审核问题贴
	 * @作者：ycw
	 * @日期：2012-7-10
	 * @return 
	 */
	public String approveQuestion(){
		try {
			Map<String,Object> pMap=new HashMap<String,Object>();
			pMap.put("roleId",roleId);
			//首先查询出客服部门目前问题贴最少的员工的loginName
			String loginNameWithMinQuestion=questionManager.findLoginNameWithMinQuestion(pMap);
			long questId=vendorQuestion.getQuestId();
			vendorQuestion=(TCommonVendorQuest) questionManager.findById(TCommonVendorQuest.class, questId);
			//如果审核结果是通过，那么将问题贴状态设置为待回复(待审核 0  待回复1  已回复2   已结帖3   已关闭4)
			if(approveResult.equals("1")){
				vendorQuestion.setQuestStatus("1");//设为待回复
			}
			//如果审核结果是不通过，将审核的内容插入到回复表中。
			if(approveResult.equals("0")){
				TCommonVendorAns ans=new TCommonVendorAns();
				String loginName=(String)(this.getSession().getAttribute(Constants.LOGIN_NAME));
				String ansCompany=(String)(this.getSession().getAttribute(Constants.COMPANY));
				String ansContent=getParameter("ansContent");
				ans.setAnsUser(loginName);
				ans.setAnsCompany(ansCompany);
				ans.setAnsContent(ansContent);
				ans.setAnsTime(new java.sql.Date(new Date().getTime()));
				ans.setQuestId(questId);
				ans.setAnsType("1");
				questionManager.saveOrUpdate(ans);
			}
			vendorQuestion.setLoginName(loginNameWithMinQuestion);//将登录名更新为有最少问题贴的那个员工。
			questionManager.saveOrUpdate(vendorQuestion);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error("审批失败！", e);
			setIsSuc("false");
		}
		return "approvingQuestionsList";
	}

	
	
	public TCommonVendorQuest getVendorQuestion() {
		return vendorQuestion;
	}
	public void setVendorQuestion(TCommonVendorQuest vendorQuestion) {
		this.vendorQuestion = vendorQuestion;
	}


	public Page<TCommonVendorQuest> getPage() {
		return page;
	}

	public void setPage(Page<TCommonVendorQuest> page) {
		this.page = page;
	}

	public String getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	public TCommonVendorQuestModel getTquestModel() {
		return tquestModel;
	}

	public void setTquestModel(TCommonVendorQuestModel tquestModel) {
		this.tquestModel = tquestModel;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public QuestionManager getQuestionManager() {
		return questionManager;
	}

	public void setQuestionManager(QuestionManager questionManager) {
		this.questionManager = questionManager;
	}

	
	
	
	

	


}
