/**
* <p>文件名: KnowledgeModel.java</p>
* <p>:描述：知识查询模型</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-8 16:10:22 
* @作者： csq
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.model;

import java.util.List;

/**
 * @类描述: 知识查询模型
 * @作者：csq
 */
public class KnowledgeModel implements java.io.Serializable {

	private static final long serialVersionUID = -599565036996611765L;
	
	private String knowledgeId; //主键
	
	private String code; //编码
	
	private String subject; //主题
	
	private String content; //内容
	
	private String question; //问题说明
	
	private String answer; //解决办法
	
	private String type; //知识分类
	
	private String restrictLevel; //知识权限
	
	private List<String> restrictLevelList; //知识权限集合
	
	private String displayModel; //显示模式
	
	private String viewType; //查看模式:编辑-edit 购方查看-buyView 销方查看-sellView 通用查看-generalView 发布-publish
	
	private String state; //状态

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRestrictLevel() {
		return restrictLevel;
	}

	public void setRestrictLevel(String restrictLevel) {
		this.restrictLevel = restrictLevel;
	}

	public List<String> getRestrictLevelList() {
		return restrictLevelList;
	}

	public void setRestrictLevelList(List<String> restrictLevelList) {
		this.restrictLevelList = restrictLevelList;
	}

	public String getDisplayModel() {
		return displayModel;
	}

	public void setDisplayModel(String displayModel) {
		this.displayModel = displayModel;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 编辑视图
	 */
	public void initEdit(){
		this.setViewType("edit");
		this.setState(null);
	}
	
	/**
	 * 通用视图
	 */
	public void initGeneralView(){
		this.setViewType("generalView");
		this.setState("2"); //已发布
	}
	
	/**
	 * 购方视图
	 */
	public void initBuyView(){
		this.setViewType("buyView");
		this.setState("2"); //已发布
	}
	
	/**
	 * 销方视图
	 */
	public void initSellView(){
		this.setViewType("sellView");
		this.setState("2"); //已发布
	}
	
	/**
	 * 发布视图
	 */
	public void initPublish(){
		this.setViewType("publish");
		this.setState("1"); //待发布
	}
	
	public boolean isBuyViewType(){
		return "buyView".equals(this.getViewType());
	}
	
	public boolean isSellViewType(){
		return "sellView".equals(this.getViewType());
	}
	
	public boolean isGeneralViewType(){
		return "generalView".equals(this.getViewType());
	}
	
	public boolean isEditType(){
		return "edit".equals(this.getViewType());
	}
	
	public boolean isPublishType(){
		return "publish".equals(this.getViewType());
	}
}
