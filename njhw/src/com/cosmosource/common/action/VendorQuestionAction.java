/**
* <p>文件名: VendorQuestionAction.java</p>
* <p>描述：问题帖Action</p>
* <p>公司: Cosmosource Beijing Office</p>
* @创建时间： 2012-7-9
* @作者： XieRX
*/
package com.cosmosource.common.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.common.entity.TCommonVendorAns;
import com.cosmosource.common.entity.TCommonVendorAnsfile;
import com.cosmosource.common.entity.TCommonVendorQuest;
import com.cosmosource.common.service.QuestionManager;
import com.opensymphony.xwork2.Action;

/**
* @类描述: 问题帖Action
* @作者： XieRX
*/
public class VendorQuestionAction extends BaseAction<TCommonVendorQuest> {

	
	private static final long serialVersionUID = 8497952646128136433L;
	private QuestionManager questionManager;
	//-- 页面属性 --//
	private Long questId ;
	private TCommonVendorQuest entity = new TCommonVendorQuest();
	private TCommonVendorAns entityAns;
	private TCommonVendorAnsfile entityFile;
	protected static Map<String,String> contentTypes = new HashMap<String,String>();
	private String dateStart;
	private String dateEnd;
	private File file;
	private Page<TCommonVendorQuest> page = new Page<TCommonVendorQuest>(Constants.PAGESIZE);//默认每页50条记录
	private String _chk[];//选中记录的ID数组
	static{
	    contentTypes.put("doc","application/msword");
	    contentTypes.put("docx","application/msword");
	    contentTypes.put("xls","application/vnd.ms-excel");
	    contentTypes.put("xlsx","application/vnd.ms-excel");
	    contentTypes.put("ppt","application/vnd.ms-powerpoint");
	    contentTypes.put("pdf","application/pdf");
	    contentTypes.put("exe","application/octet-stream");
	    contentTypes.put("bin","application/octet-stream");
	    contentTypes.put("rar","application/octet-stream");
	    contentTypes.put("zip","application/zip");
	    contentTypes.put("htm","text/html");
	    contentTypes.put("html","text/html");
	    contentTypes.put("gif","image/gif");
	    contentTypes.put("bmp","image/bmp");
	    contentTypes.put("jpeg","image/jpeg");
	    contentTypes.put("jpg","image/jpeg");
	    contentTypes.put("png","image/png");
	    contentTypes.put("mpeg","video/jpeg");
	    contentTypes.put("mp3","audio/mpeg");
	}
	
	
	
	public TCommonVendorAns getEntityAns() {
		return entityAns;
	}

	public void setEntityAns(TCommonVendorAns entityAns) {
		this.entityAns = entityAns;
	}

	public TCommonVendorAnsfile getEntityFile() {
		return entityFile;
	}

	public void setEntityFile(TCommonVendorAnsfile entityFile) {
		this.entityFile = entityFile;
	}

	//-- ModelDriven 与 Preparable函数 --//
	public TCommonVendorQuest getModel() {
		return entity;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	private String content;
	
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @描述: 跳到新建问题帖页面
	 * @作者：XieRX
	 * @日期：2012-7-9
	 * @return String
	 */
	public String input() {
		this.entity.setQuestUser((String)this.getSession().getAttribute(Constants.LOGIN_NAME));
		return INPUT;
	}
	
	
	/**
	 * @描述: 查看发帖内容
	 * @作者：XieRX
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String showProblem() {
		questId = entity.getQuestId();
		if (questId != null) {
			entity = (TCommonVendorQuest)questionManager.findById(TCommonVendorQuest.class, questId);
		} else {
			entity = new TCommonVendorQuest();
		}
		//发帖内容
		List<TCommonVendorAns> tls = questionManager.findByHQL("from TCommonVendorAns t where t.ansType=0 and t.questId=?",entity.getQuestId());
		TCommonVendorAns tv = tls.get(0);
		//回帖内容
		List<TCommonVendorAns> tlsList = questionManager.findByHQL("from TCommonVendorAns t where t.ansType=1 and t.questId=?",entity.getQuestId());
		List<TCommonVendorAnsfile> tlsFileList = questionManager.findByHQL("from TCommonVendorAnsfile t where t.ansId=?",tv.getAnsId());
		if(tlsFileList!=null&&tlsFileList.size()>0){
			entityFile = tlsFileList.get(0);
		}
		this.getRequest().setAttribute("entityContent",tv);
		this.getRequest().setAttribute("entityList",tlsList);
		this.getRequest().setAttribute("entity",entity);
		this.getRequest().setAttribute("entityFile", entityFile);
		return SUCCESS;
	}
	
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		questId = entity.getQuestId();
		if (questId != null) {
			entity = (TCommonVendorQuest)questionManager.findById(TCommonVendorQuest.class, questId);
		} else {
			entity = new TCommonVendorQuest();
		}
	}
	
	/**
	 * @描述: 发布问题帖子
	 * @作者：XieRX
	 * @日期：2012-7-9
	 * @return String
	 */
	public String save() {
		boolean isSuccess = true;
		String actionMsg = "帖子发布成功.";
		try {
			if(null!=file){
				long len = file.length();
				if(len>1024*1024*2){
					isSuccess = false;
					actionMsg = "帖子发布失败, 文件大小大于2M.";
					this.addActionMessage(actionMsg);
					return isSuccess ? Action.SUCCESS : Action.ERROR;
				}
			}
			this.entity.setQuestUser((String)this.getSession().getAttribute(Constants.LOGIN_NAME));
			this.entity.setQuestTime(new Date());
			this.entity.setQuestCompany((String)this.getSession().getAttribute(Constants.COMPANY));
			this.entity.setQuestStatus("0");//帖子状态  0待审核
			this.entity.setDcFlag("00");//帖子权限
			this.entity.setLoginName((String)this.getSession().getAttribute(Constants.LOGIN_NAME));
			this.questionManager.saveVenQuestion(entity,entityAns,entityFile,file);
			
			StringBuffer stemp=new StringBuffer();
			stemp.append("T000000");
			stemp.append(entity.getQuestId().toString());
			this.entity.setQuestCode(stemp.toString());
			this.questionManager.saveVenQuestionCode(entity);
		} catch (Exception e) {
			isSuccess = false;
			actionMsg = "帖子发布失败, 请稍后再试.";
			e.printStackTrace();
		}
		this.addActionMessage(actionMsg);
		return isSuccess ? Action.SUCCESS : Action.ERROR;
	}
	
	/**
	 * 删除页的查询列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
		page = questionManager.queryVendorQuest(page, entity,dateStart,dateEnd);
		return SUCCESS;
	}
	/**
	 * 已结帖查询列表
	 * @return 
	 * @throws Exception
	 */
	public String listClose() throws Exception {
		page = questionManager.queryCloseVendorQuest(page, entity,dateStart,dateEnd);
		return SUCCESS;
	}
	
	/**
	 * 批量删除选中
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			if(_chk!=null){
				questionManager.deleteVendorQuest(_chk);
				addActionMessage("删除成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
			
		}
		return SUCCESS;
	}
	
	/**
	 * @描述: 下载文件
	 * @return
	 * @throws Exception
	 */
	public String download() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("enter download... ");
		}
		String fileId = getParameter("fileId");
		entityFile = (TCommonVendorAnsfile)this.questionManager.findById(TCommonVendorAnsfile.class,Long.parseLong(fileId));
		OutputStream os = this.getResponse().getOutputStream();
		InputStream is = entityFile.getFileContent().getBinaryStream();
		String fileName = entityFile.getFileName();
		String fileExtension = entityFile.getFileExtension();
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
	@SuppressWarnings("static-access")
	public String getContentType(String fileExtension) {
        for(String txt : contentTypes.keySet()){
            if(fileExtension.toLowerCase().endsWith(txt)){
                return this.contentTypes.get(txt);
            }
		}
		    
        return "application/octet-stream";
    }
	public String encodeFileName(String fileName) throws Exception{
		return new String(fileName.getBytes("gbk"), "ISO-8859-1");
	}
	/**
	 * list页面显示分页列表.
	 */
	public Page<TCommonVendorQuest> getPage() {
		return page;
	}
	public Long getQuestId() {
		return questId;
	}

	public void setQuestId(Long questId) {
		this.questId = questId;
	}

	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] _chk) {
		this._chk = _chk;
	}

	public QuestionManager getQuestionManager() {
		return questionManager;
	}

	public void setQuestionManager(QuestionManager questionManager) {
		this.questionManager = questionManager;
	}

}
