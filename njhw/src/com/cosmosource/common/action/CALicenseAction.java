package com.cosmosource.common.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcCaapply;
import com.cosmosource.common.entity.TAcCauserapply;
import com.cosmosource.common.model.CALicenseModel;
import com.cosmosource.common.model.CAUserLicenseModel;
import com.cosmosource.common.service.CAMgrManager;

/**
 * @类描述:  CA发证
 * 
 */
public class CALicenseAction extends BaseAction<TAcCaapply>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8637659523450451342L;
	
	private TAcCaapply entity = new TAcCaapply();
	private Page<TAcCaapply> page = new Page<TAcCaapply>(Constants.PAGESIZE);
	//查询实体类
	private CALicenseModel caLicenseModel = new CALicenseModel();
	//用户与key信息实体集合类
	private List<CAUserLicenseModel> caUserLicenseModels = new ArrayList<CAUserLicenseModel>();
	private CAMgrManager camgrManager;

	/**
	 * 进入ca资料页面
	 * @return
	 */
	public String init() {
		
		caLicenseModel.setStartTime(DateUtil.getStrMonthFirstDay());
		caLicenseModel.setEndTime(DateUtil.getDateTime("yyyy-MM-dd"));
		return INIT;
	}
	
	/**
	 * ca资料查询列表（分页）
	 * @return
	 */
	public String query() {
		
		entity.setDelstatus("0");
		entity.setAuditstatus("1");
		entity.setIsgenca("0");
		entity.setApplytype("0");
		entity.setIssubmit("1");
		page = camgrManager.findCaApply4LicenseList(page, entity, caLicenseModel, null);
		return LIST;
	}
	/**
	 * ca发证页面初始化
	 * @return
	 */
	public String license(){
		
		entity.setGencauser(getSession().getAttribute(Constants.USER_NAME).toString());
		entity.setGencadate(DateUtil.getSysDate());
		List<TAcCauserapply> tAcCauserapplies = camgrManager.findCaUserApplayByNo(entity.getApplyno());
		for (TAcCauserapply tAcCauserapply : tAcCauserapplies) {
			String canum = tAcCauserapply.getCanum();
			if(NumberUtils.isNumber(canum)){
				Number ncanum = NumberUtils.createNumber(canum);
				for(int i = 0; i<ncanum.intValue();i++){
					CAUserLicenseModel caUserLicenseModel = new CAUserLicenseModel();
					caUserLicenseModel.setLoginname(tAcCauserapply.getLoginname());
					caUserLicenseModels.add(caUserLicenseModel);
				}
			}
		}
		return "license";
	}
	/**
	 * ca发证
	 * @return
	 */
	public String update(){
		
		try {
			camgrManager.saveTAcCaapplyAndUser(caUserLicenseModels,entity);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error("CAMgrManager.saveTAcCaapplyAndUser异常",e);
			setIsSuc("false");
		}
		return "success";
	}
	
	public String getImg() throws Exception{
		
		String caid = getParameter("caid");
		TAcCaapply entitys = camgrManager.findById(Long.parseLong(caid));
		Blob blob = entitys.getHandlerstamp();
		if (blob != null) {
			getResponse().setContentType("image/jpg");
			OutputStream outs = getResponse().getOutputStream();
			InputStream pi = blob.getBinaryStream();
			int blobsize = (int) blob.length();
			byte[] blobbytes = new byte[blobsize];
			int bytesRead = 0;
			while ((bytesRead = pi.read(blobbytes)) != -1) {
				outs.write(blobbytes, 0, bytesRead);
			}
			pi.close();
			outs.flush();
		}
		return null;
	}
	
	@Override
	protected void prepareModel() {
		if(entity.getCaid() != null){
			entity = camgrManager.findById(entity.getCaid());
		}
	}
	
	/**
	 * 在license()前执行二次绑定.
	 */
	public void prepareLicense() throws Exception {
		prepareModel();
	}

	public TAcCaapply getModel() {
		return entity;
	}

	public Page<TAcCaapply> getPage() {
		return page;
	}

	public void setPage(Page<TAcCaapply> page) {
		this.page = page;
	}

	public CALicenseModel getCaLicenseModel() {
		return caLicenseModel;
	}

	public void setCaLicenseModel(CALicenseModel caLicenseModel) {
		this.caLicenseModel = caLicenseModel;
	}

	public TAcCaapply getEntity() {
		return entity;
	}

	public void setEntity(TAcCaapply entity) {
		this.entity = entity;
	}

	public List<CAUserLicenseModel> getCaUserLicenseModels() {
		return caUserLicenseModels;
	}

	public void setCaUserLicenseModels(List<CAUserLicenseModel> caUserLicenseModels) {
		this.caUserLicenseModels = caUserLicenseModels;
	}

	public CAMgrManager getCamgrManager() {
		return camgrManager;
	}

	public void setCamgrManager(CAMgrManager camgrManager) {
		this.camgrManager = camgrManager;
	}

}


