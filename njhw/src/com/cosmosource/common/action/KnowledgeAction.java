/**
* <p>文件名: KnowledgeAction.java</p>
* <p>:描述：知识查询</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-9 9:21:30 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;

import com.cosmosource.base.action.ExportAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonKnowledge;
import com.cosmosource.common.entity.TCommonKnowledgeFile;
import com.cosmosource.common.model.KnowledgeModel;
import com.cosmosource.common.service.KnowledgeManager;

/**
 * 
 * @类描述: 知识查询Action
 * @作者： WXJ
 *
 */
public class KnowledgeAction extends ExportAction<TCommonKnowledge> {
	
	private static final long serialVersionUID = -8474411204171601108L;
	
	private KnowledgeManager knowledgeManager;
	
	private TCommonKnowledge knowledge = new TCommonKnowledge();
	
	private KnowledgeModel knowledgeModel = new KnowledgeModel();
	
	private Page<TCommonKnowledge> page = new Page<TCommonKnowledge>(Constants.PAGESIZE);
	
	@SuppressWarnings("rawtypes")
	private Page<?> filePage = new Page(Constants.PAGESIZE);
	
	private File file;
	
	private String _chk[];
	  
	  
	public TCommonKnowledge getModel() {
		return knowledge;
	}

	@Override
	protected void prepareModel() throws Exception {
		Long knowledgeId = knowledge.getKnowledgeId();
		if (knowledgeId != null) {
			knowledge = (TCommonKnowledge)knowledgeManager.findById(TCommonKnowledge.class, knowledgeId);
		} else {
			knowledge = new TCommonKnowledge();
		}
	}
	
	/**
	 * @描述: 初始化编辑知识查询列表
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter init...");
		}
		
		knowledgeModel.initEdit();
		
		return INIT;
	}
	
	/**
	 * @描述: 初始化通用知识查询列表
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initQuery() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter initQuery...");
		}
		
		knowledgeModel.initGeneralView();
		
		return LIST;
	}
	
	/**
	 * @描述: 初始化销方知识查询列表
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initSellQuery() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter initSellQuery...");
		}
		
		knowledgeModel.initSellView();
		
		return LIST;
	}

	/**
	 * @描述: 初始化购方知识查询列表
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initBuyQuery() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter initBuyQuery...");
		}
		
		knowledgeModel.initBuyView();
		
		return LIST;
	}

	/**
	 * @描述: 初始化编辑文件列表
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initEditList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter initEditList...");
		}
		
		knowledgeModel.initEdit();
		
		return LIST;
	}
	
	/**
	 * @描述: 初始化发布文件列表
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initPublishList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter initPublishList...");
		}
		
		knowledgeModel.initPublish();
		
		return LIST;
	}
	
	/**
	 * @描述: 发布文件
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String publish() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter publish...");
		}
		
		try {
			String loginName = (String)getSessionAttribute(Constants.LOGIN_NAME);
		    knowledgeManager.publishKnowledges(_chk, loginName);
		    return LIST;
		} catch (Exception e) {
			logger.error("发布知识异常！", e);
			return ERROR;
		}
	}
	
	/**
	 * @描述: 查询文件列表
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter query...");
		}
		
		try {
			if(knowledgeModel.isBuyViewType()){
				List<String> restrictLevelList = new ArrayList<String>();
				restrictLevelList.add("1");
				restrictLevelList.add("2");
				
				knowledgeModel.setRestrictLevelList(restrictLevelList);
			}
			else if(knowledgeModel.isSellViewType()){
				List<String> restrictLevelList = new ArrayList<String>();
				restrictLevelList.add("1");
				restrictLevelList.add("3");

				knowledgeModel.setRestrictLevelList(restrictLevelList);
			}
			
			knowledge.setCompany((String)getSessionAttribute(Constants.COMPANY));
			page = knowledgeManager.findKnowledgeList(page, knowledge, knowledgeModel, null);
			return LIST;
		} catch (Exception e) {
			logger.error("查询知识异常！", e);
			return ERROR;
		}
	}
	
	/**
	 * @描述: 初始化录入文件信息页面
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String input() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter input...");
		}
		
		return INPUT;
	}
	
	/**
	 * @描述: 文件详细信息页面
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String detail() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter detail...");
		}
		Long knowledgeId = knowledge.getKnowledgeId();
		if(knowledgeId != null){
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("knowledgeId", knowledgeId.toString());
			
			List fileList = knowledgeManager.findKnowledgeFileList(paraMap);
			filePage.setResult(fileList);
		}
		
		return DETAIL;
	}
	
	/**
	 * @描述: 文件列表页面
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String fileList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter fileList...");
		}
		Long knowledgeId = knowledge.getKnowledgeId();
		if(knowledgeId != null){
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("knowledgeId", knowledgeId.toString());
			
			List fileList = knowledgeManager.findKnowledgeFileList(paraMap);
			filePage.setResult(fileList);
		}
		
		return LIST;
	}
	
	/**
	 * @描述: 保存文件信息
	 * @作者： WXJ
	 * @日期：2012-2-9
	 *
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter save...");
		}
		
		String next = getParameter("next");
		try {
			String loginName = (String)getSessionAttribute(Constants.LOGIN_NAME);
			if(knowledge.getKnowledgeId() == null){
				knowledge.setCreateTime(new Date());
				knowledge.setCreateUser(loginName);
				knowledge.setState("1");
			}
			knowledgeManager.saveKnowledge(knowledge);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error("保存知识失败！", e);
			setIsSuc("false");
		}

		if("ok".equals(next)){
			return SUCCESS;
		}
		else if("upload".equals(next)){
			return "next";
		}
		else {
			return SUCCESS;
		}
	}
	
	/**
	 * @描述: 更新文件信息
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter update...");
		}
		
		try {
			knowledgeManager.saveKnowledge(knowledge);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error("更新知识失败！", e);
			setIsSuc("false");
		}
		
		return SUCCESS;
	}
	
	/**
	 * @描述: 删除文件
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter delete...");
		}
		
		try {
			knowledgeManager.deleteKnowledges(_chk);
			addActionMessage("删除知识数据成功！");
		} catch (Exception e) {
			addActionMessage("删除知识数据失败！");
		}
		
		return SUCCESS;
	}
	
	/**
	 * @描述: 删除文件
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteKnowledgeFile() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter deleteKnowledgeFile...");
		}
		
		String fileId = getParameter("fileId");	
		boolean result = false;
		try {
			int count = knowledgeManager.deleteKnowledgeFile(fileId);
			if(count > 0){
				result = true;
			}
		} catch (Exception e) {
			result = false;
			logger.error(e.getMessage());
		}

		Struts2Util.renderJson(result);
		return null;
	}
	
	/**
	 * @描述: 上传文件
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean upload() throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("enter upload...");
		}
	    try{
	    	String fileName = getParameter("fileName");
	    	String fileType= getParameter("fileType");
	        FileInputStream fis = new FileInputStream(file);
	        byte[] data = new byte[(int)fis.available()];
	        fis.read(data);
	        String knowledgeId = getParameter("knowledgeId");
	        
	        TCommonKnowledgeFile knowledgeFile = new TCommonKnowledgeFile();
	        knowledgeFile.setFileContent(Hibernate.createBlob(data));
	        knowledgeFile.setFileName(fileName);
	        knowledgeFile.setFileSize(file.length());
	        knowledgeFile.setFileExtension(fileType);
	        knowledgeFile.setKnowledgeId(knowledgeId);
	        
	        knowledgeManager.saveKnowledgeFile(knowledgeFile);
	        return true;
        }
        catch(Exception e){
    	    e.printStackTrace();
    	    logger.info(e.getMessage());
    	    return false;
        }
	}
	
	/**
	 * @描述: 下载文件
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	public String download() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter download... ");
		}
		
		String fileId = getParameter("fileId");
		
		TCommonKnowledgeFile knowledgeFile = (TCommonKnowledgeFile)knowledgeManager.findById(TCommonKnowledgeFile.class, new Long(fileId));
		OutputStream os = this.getResponse().getOutputStream();
		InputStream is = knowledgeFile.getFileContent().getBinaryStream();
		String fileName = knowledgeFile.getFileName();
		String fileExtension = knowledgeFile.getFileExtension();
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
	 * @描述: 获取文件列表json串
	 * @作者： WXJ
	 * @日期：2012-2-9
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String getJsonFileList() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter getJsonFileList...");
		}
		
		String knowledgeId = getParameter("knowledgeId");
		if(knowledgeId != null){
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("knowledgeId", knowledgeId);
			
			List fileList = knowledgeManager.findKnowledgeFileList(paraMap);
		    Struts2Util.renderJson(JsonUtil.beanToJson(fileList), "encoding:UTF-8", "no-cache:true");
		}
		
		return null;
	}

	//--property
	public TCommonKnowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(TCommonKnowledge knowledge) {
		this.knowledge = knowledge;
	}

	public KnowledgeModel getKnowledgeModel() {
		return knowledgeModel;
	}

	public void setKnowledgeModel(KnowledgeModel knowledgeModel) {
		this.knowledgeModel = knowledgeModel;
	}

	public Page<TCommonKnowledge> getPage() {
		return page;
	}

	public void setPage(Page<TCommonKnowledge> page) {
		this.page = page;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] _chk) {
		this._chk = _chk;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Page<?> getFilePage() {
		return filePage;
	}

	public void setFilePage(Page<?> filePage) {
		this.filePage = filePage;
	}

	//--service
	public void setKnowledgeManager(KnowledgeManager knowledgeManager) {
		this.knowledgeManager = knowledgeManager;
	}
}
