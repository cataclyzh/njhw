package com.cosmosource.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TAcCaapply;
import com.cosmosource.common.entity.TAcCauserapply;
import com.cosmosource.common.entity.TAcDictdeta;
import com.cosmosource.common.model.CaapplyModel;
import com.cosmosource.common.service.CAMgrManager;
import com.cosmosource.common.service.IExport;

public class CaApplyAction extends BaseAction<TAcCaapply>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4028674333408979822L;
	
	public static final Long MAX_APPLY_COUNT = 10L;
	
	private IExport export;
	
	private CAMgrManager caMgrManager;
	
	private TAcCaapply entity = new TAcCaapply();
	
	private CaapplyModel applyModel = new CaapplyModel();
	
	private List<?> userInfos; //同一个纳税人识别号下的所有用户集合
	
	private File upload; //上传的印章图片
	
	private String[] loginname; //用户名
	
	private String[] canum; //CA数量
	
	private Long countUserApply; //用户申请CA数量

	public TAcCaapply getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}
	
	/**
	 * 初始化申请单信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String initApply() throws Exception{
	    if(logger.isDebugEnabled()){
	    	logger.debug("init apply...");
	    }
	    entity.setApplydate(DateUtil.getSysDate());
	    entity.setApplytype("0");
	    entity.setCaterm("1");
	    entity.setCatype("0");
	    entity.setOrgidtype("0");
	    entity.setHandleridtype("0");

    	String loginName = (String)getSessionAttribute(Constants.LOGIN_NAME);
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("applyuser", loginName);
		try{
		    countUserApply = caMgrManager.countUserApply(queryParams);
		    logger.info("countUserApply : " + countUserApply);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return ERROR;
		}
		
		if(countUserApply <= MAX_APPLY_COUNT){
			List<?> currentApplyStepList = caMgrManager.findCurrentApplyStep(queryParams);
			if(currentApplyStepList.isEmpty()){
				return INIT;
			}
			else{
				Map currentApplyStep = (Map)currentApplyStepList.get(0);
				String caid = currentApplyStep.get("CAID").toString();
				String stepcode = currentApplyStep.get("STEPCODE").toString();
				String applyno = currentApplyStep.get("APPLYNO").toString();
				String applynum = currentApplyStep.get("APPLYNUM").toString();
				applyModel.setApplyno(applyno);
				applyModel.setCaid(new Long(caid));
				applyModel.setStepcode(stepcode);
				applyModel.setApplynum(new Long(applynum));
				logger.info("caid: " + caid);
				logger.info("stepcode: " + stepcode);
				logger.info("applyno: " + applyno);
				logger.info("applynum: " + applynum);
				if("apply".equals(stepcode)){
					logger.info("enter stamp input...");
					return "stampInput";
				}
				else if("stamp".equals(stepcode)){
					logger.info("enter relation input...");
					String taxno = (String)getSessionAttribute(Constants.ORG_TAXNO);
					logger.info("taxno -> " + taxno);
					Map<String, String> queryUserParams = new HashMap<String, String>();
					queryUserParams.put("taxno", taxno);
					userInfos = caMgrManager.findUsersWithTaxno(queryUserParams);
					logger.info(userInfos.toString());
					return "relationInput";
				}
			}
		}
	    
		return INIT;
	}
	
	/**
	 * 初始化印章
	 * @return
	 * @throws Exception
	 */
	public String initStamp() throws Exception{
	    if(logger.isDebugEnabled()){
	    	logger.debug("init stamp...");
	    }
	    
		return INIT;
	}
	
	/**
	 * 初始化帐户关系
	 * @return
	 * @throws Exception
	 */
	public String initRelation() throws Exception{
	    if(logger.isDebugEnabled()){
	    	logger.debug("init relation...");
	    }

		String taxno = (String)getSessionAttribute(Constants.ORG_TAXNO);
		logger.info("taxno -> " + taxno);
		Map<String, String> queryParams = new HashMap<String, String>();
		List<?> userInfos = caMgrManager.findUsersWithTaxno(queryParams);
		logger.info(userInfos.toString());
	    
		return INIT;
	}
	
	/**
	 * 录入申请单信息
	 * @return
	 * @throws Exception
	 */
	public String saveApply() throws Exception{
	    if(logger.isDebugEnabled()){
	    	logger.debug("save apply...");
	    }
	    try{
	    	entity.setApplydate(new Date());
	    	String loginName = (String)getSessionAttribute(Constants.LOGIN_NAME);
	    	entity.setApplyuser(loginName);
	    	entity.setStepcode("apply");
	    	if("0".equals(entity.getApplytype())){
	    		entity.setCadn(null);
	    	}
	    	if("2".equals(entity.getOrgidname())){
	    		entity.setOrgidname(null);
	    	}
		    caMgrManager.saveApply(entity);	 
		    
		    applyModel.setApplyno(entity.getApplyno());
		    applyModel.setCaid(entity.getCaid());
		    applyModel.setApplynum(entity.getApplynum());
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    	logger.info(e.getMessage());
	    	return ERROR;
	    }
	    
		return "next";
	}
	
	/**
	 * 上传印章
	 * @return
	 * @throws Exception
	 */
	public String saveStamp() throws Exception{
	    if(logger.isDebugEnabled()){
	    	logger.debug("save stamp...");
	    }
	    
	    try{
	        FileInputStream fis = new FileInputStream(upload);
	        byte[] data = new byte[(int)fis.available()];
	        fis.read(data);
	        entity.setCaid(applyModel.getCaid());
	        entity.setHandlerstamp(Hibernate.createBlob(data));
	        entity.setDelstatus("0");
	        entity.setAuditstatus("0");
	        entity.setStepcode("stamp");
	        
	        caMgrManager.saveApplyStamp(entity);
			String taxno = (String)getSessionAttribute(Constants.ORG_TAXNO);
			logger.info("taxno -> " + taxno);
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("taxno", taxno);
			userInfos = caMgrManager.findUsersWithTaxno(queryParams);
			logger.info(userInfos.toString());
        }
        catch(Exception e){
    	    e.printStackTrace();
    	    logger.info(e.getMessage());
	    	return ERROR;
        }

		return "next";
	}
	
	/**
	 * 设置帐户关系
	 * @return
	 * @throws Exception
	 */
	public String saveRelation() throws Exception{
	    if(logger.isDebugEnabled()){
	    	logger.debug("save relation...");
	    }
	    
	    String type = applyModel.getType();
	    String applyno = applyModel.getApplyno();
	    Long caid = applyModel.getCaid();
	    logger.info("type : " + type);
	    logger.info("applyno : " + applyno);
	    logger.info("caid : " + caid);
	    //保存用户申请
	    if("1".equals(type)){
	    	String personalcanum = getParameter("personalcanum");
	    	TAcCauserapply userApply = new TAcCauserapply();
	    	userApply.setLoginname((String)getSessionAttribute(Constants.LOGIN_NAME));
	    	userApply.setCanum(personalcanum);
	    	userApply.setApplyno(applyno);
	    	caMgrManager.saveUserApply(userApply);
	    }
	    else {//保存公司申请    
		    if(loginname != null && loginname.length > 0 &&
		    		canum != null && canum.length > 0){
		    	logger.info("loginname length " + loginname.length);
		    	logger.info("canum length " + canum.length);
		    	List<TAcCauserapply> userApplyList = new ArrayList<TAcCauserapply>();
		    	for(int i = 0; i < loginname.length; i++){
			    	TAcCauserapply userApply = new TAcCauserapply();
			    	userApply.setLoginname(loginname[i]);
			    	userApply.setCanum(canum[i]);
			    	userApply.setApplyno(applyno);
			    	userApplyList.add(userApply);
		    	}
		    	caMgrManager.saveUserApplyList(userApplyList);
		    }
		    else {
		    	logger.info("loginname is empty ");
		    }
	    }
	    
	    //更新申请表状态
	    Map<String, Object> values = new HashMap<String, Object>();
	    values.put("caid", caid);
	    caMgrManager.updateApplySubmit(values);
	    
		return "next";
	}
	
	/**
	 * 下载授权书模板
	 * @return
	 * @throws Exception
	 */
	public String downloadAuthorityBook() throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("enter download authority book... ");
		}
		OutputStream os = this.getResponse().getOutputStream();
		String filename = "授权书模板.doc";
	    String filePath = PropertiesUtil.getConfigProperty("dir.root","common") + "ca/";
	    logger.info(filePath);
		File file = new File(filePath + filename);
		InputStream is = new FileInputStream(file);
	    logger.info(filename);
		try {
			this.getResponse().setContentType("application/msword;charset=gb2312");
			this.getResponse().addHeader("content-type","application/msword");
			this.getResponse().addHeader("Content-Disposition","attachment;filename="+ encodeFilename(filename));
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
	 * 转换中文
	 * @param filename
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String encodeFilename(String filename) throws UnsupportedEncodingException {
		if(filename != null){
	        return new String(filename.getBytes("GBK"),"ISO8859_1");	
		}
		else {
			return "";
		}
    }
	
	/**
	 * 生成导出文件名
	 * @param applyno
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String makeFileName(String applyno) throws UnsupportedEncodingException{
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		date = calendar.getTime();
		String fileName = "CA申请表_" + applyno + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date) + ".pdf";
		
		return encodeFilename(fileName);
	}	
	
	/**
	 * 转换字典为Map
	 * @param dictList
	 * @return
	 */
	private Map<String, String> transforDict(List<TAcDictdeta> dictList){
		Map<String, String> dictSubMap = new HashMap<String, String>();
		for(TAcDictdeta dict : dictList){
			dictSubMap.put(dict.getDictcode(), dict.getDictname());
		}
		
		return dictSubMap;
	}
	
	/**
	 * 转换字典集合
	 * @param dictTypes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> initDict(String[] dictTypes){
		Map<String, Map<String, String>> dictMaps = new HashMap<String, Map<String, String>>();	
		
		for(String dictType : dictTypes){
			dictMaps.put(dictType, this.transforDict((List<TAcDictdeta>)getServletContext().getAttribute("DICT_" + dictType)));
		}
		
		return dictMaps;
	}
	
	/**
	 * 导出CA申请表
	 * @return
	 * @throws Exception
	 */
	public String exportApplyPDF() throws Exception{
		String caid = getParameter("caid");
		logger.info("caid:" + caid);
		TAcCaapply apply = null;
		if(StringUtil.isNotEmpty(caid)){
			apply = caMgrManager.findById(new Long(caid));
		}
		else {
			apply = caMgrManager.findById(applyModel.getCaid());
		}
		String filename = this.makeFileName(apply.getApplyno());
		this.getResponse().setContentType("application/pdf;charset=gb2312");
		this.getResponse().addHeader("content-type","application/pdf");
		this.getResponse().addHeader("Content-Disposition","attachment;filename="+ filename);
		OutputStream os = this.getResponse().getOutputStream();
		
		String[] dictTypes = {"CA_CATERM", "CA_CATYPE" ,
				"CA_APPLYTYPE" , "CA_ORGIDTYPE", "CA_HANDLERIDTYPE"};
		export.setDictMap(this.initDict(dictTypes));
		export.export(apply, os);
		if(os != null){
			os.flush();
		}
		
		return null;
	}
	
	//--------------------------------------property

	public File getUpload() {
		return upload;
	}

	public String[] getLoginname() {
		return loginname;
	}

	public void setLoginname(String[] loginname) {
		this.loginname = loginname;
	}

	public String[] getCanum() {
		return canum;
	}

	public void setCanum(String[] canum) {
		this.canum = canum;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public List<?> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<?> userInfos) {
		this.userInfos = userInfos;
	}

	public CaapplyModel getApplyModel() {
		return applyModel;
	}

	public void setApplyModel(CaapplyModel applyModel) {
		this.applyModel = applyModel;
	}

	public Long getCountUserApply() {
		return countUserApply;
	}

	public void setCountUserApply(Long countUserApply) {
		this.countUserApply = countUserApply;
	}

	//--------------------------------------service

	public void setCaMgrManager(CAMgrManager caMgrManager) {
		this.caMgrManager = caMgrManager;
	}
	
	public void setExport(IExport export) {
		this.export = export;
	}
}
