/**
* <p>文件名: FilesMgrAction.java</p>
* <p>描述：软件下载Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-3-4 下午06:21:24 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonFileDownload;
import com.cosmosource.common.service.FilesMgrManager;
//import com.cosmosource.base.util.PropertiesUtil;

/**
* @类描述: 软件下载
* @作者： WXJ
*/
public class FilesMgrAction extends BaseAction<TCommonFileDownload> {

	private static final long serialVersionUID = -5696960390509244868L;
	private FilesMgrManager filesMgrManager;
	private String sFilePath;
	//-- 页面属性 --//
	private TCommonFileDownload entity = new TCommonFileDownload();
	private Page<TCommonFileDownload> page = new Page<TCommonFileDownload>(Constants.PAGESIZE);//默认每页20条记录	
	private String _chk[];//选中记录的ID数组
	
	
	//-- ModelDriven 与 Preparable函数 --//
	public TCommonFileDownload getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		if ( entity.getDownid() != null) {
			entity = (TCommonFileDownload)filesMgrManager.findById(TCommonFileDownload.class,  entity.getDownid());
		} else {
			entity = new TCommonFileDownload();
		}
	}
	/**
	 * 查询列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {

		page = filesMgrManager.queryFiles(page, entity);	
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息用于查看或是修改
	 * @return 
	 * @throws Exception
	 */
	public String input() throws Exception {
		return INPUT;
	}

	/**
	 * 保存信息
	 * @return 
	 * @throws Exception
	 */
	public String save() throws Exception {
		try {
			entity.setUploaddate(new Date());
			filesMgrManager.saveFiles(entity);
			setIsSuc("true");
//			addActionMessage("保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			addActionMessage("保存失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}
	/**
	 * 批量删除选中的
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			filesMgrManager.deleteFiles(_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return RELOAD;
	}
	/**
	 * 查询需要下载的文件
	 * @return 
	 * @throws Exception
	 */
	public String queryDown() throws Exception {
		
		entity.setCompany((String)getSessionAttribute(Constants.COMPANY));
		String orgtype = (String)getSessionAttribute(Constants.ORG_TYPE);
		if("2".equals(orgtype)||"4".equals(orgtype) || "1".equals(orgtype)){
			orgtype = "5";
		}
		if("7".equals(orgtype)){
			orgtype = "8";
		}		
		entity.setOrgtype(orgtype);
		page = filesMgrManager.queryFiles(page, entity);
		
		return SUCCESS;
	}
	/**
	 * 下载文件
	 * @return 
	 * @throws Exception
	 */
	public String download() throws Exception {
		if(entity.getDownid()!=null){
			entity = (TCommonFileDownload)filesMgrManager.findById(TCommonFileDownload.class, entity.getDownid());
			String filePath = sFilePath+entity.getCompany().replace("admin", "")+"/"+entity.getFilename();//PropertiesUtil.getDirProperty("soft.download.filepath","common")+"/"+entity.getFilename();
			logger.info("下载文件目录： "+filePath);
			//File myFilePath = new File(new String(filePath.getBytes("GBK"),"iso-8859-1"));
			File myFilePath = new File(filePath);
			if (myFilePath.exists()) {
				//Struts2Util.renderFile(new String(filePath.getBytes("GBK"),"iso-8859-1"), new String(entity.getSoftname().getBytes("GBK"),"iso-8859-1"), "encoding:GBK", "no-cache:true");
				Struts2Util.renderFile(filePath, entity.getSoftname(), "encoding:GBK", "no-cache:true");
			}else{
				Struts2Util.renderText("文件不存在，请与系统管理员联系！", "encoding:UTF-8", "no-cache:true");
			}
		}

		return null;
	}
	/**
	 * 下载文件
	 * @return 
	 * @throws Exception
	 */
	public String downFile() throws Exception {
		String fileName = getParameter("fileName");//new String(getParameter("fileName").getBytes("iso-8859-1"), "UTF-8");
		String showName = getParameter("showName");//new String(getParameter("showName").getBytes("iso-8859-1"), "UTF-8");
		if(StringUtils.isNotBlank(fileName)){
			String filePath = sFilePath+((String)getSessionAttribute(Constants.COMPANY)).replace("admin", "")+"/"+fileName;//PropertiesUtil.getDirProperty("soft.download.filepath","common")+"/"+entity.getFilename();
//			System.out.println(filePath);
			File myFilePath = new File(filePath);
			if (myFilePath.exists()) {
				Struts2Util.renderFile(filePath, showName, "encoding:UTF-8", "no-cache:true");
			}else{
				Struts2Util.renderText("文件不存在，请与系统管理员联系！", "encoding:UTF-8", "no-cache:true");
			}
		}
		
		return null;
	}
	
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示分页列表.
	 */
	public Page<TCommonFileDownload> getPage() {
		return page;
	}
	public void setFilesMgrManager(FilesMgrManager filesMgrManager) {
		this.filesMgrManager = filesMgrManager;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public String getsFilePath() {
		return sFilePath;
	}
	public void setsFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}
	
	
}
