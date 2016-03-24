package com.cosmosource.app.port.action;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.cosmosource.app.port.service.UploadManager;
import com.cosmosource.base.action.BaseAction;

@SuppressWarnings("unchecked")
public class UploadOaUsersAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(UploadOaUsersAction.class);
	
	private File file;
	
	private UploadManager uploadManager;

	@Override
	protected void prepareModel() throws Exception {
	}

	@Override
	public Object getModel() {
		return null;
	}
	
	
	/**
	* @Description： 初始化上传页面
	* @Author：hp
	* @Date：2013-5-24
	* @return
	**/
	public String init(){
		return INIT;
	}
	
	
	/**
	* @Description：上传文件
	* @Author：hp
	* @Date：2013-5-24
	* @return
	**/
	public String upload(){
		try {
			//解析xml然后保存入库
			uploadManager.saveUploadXml(file);
			setIsSuc("true");
		} catch (DocumentException e) {
			setIsSuc("false");
			log.info(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			setIsSuc("false");
			log.info(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			setIsSuc("false");
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public UploadManager getUploadManager() {
		return uploadManager;
	}

	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
