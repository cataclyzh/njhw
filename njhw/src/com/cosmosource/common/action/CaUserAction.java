package com.cosmosource.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.common.entity.TAcCauser;
import com.cosmosource.common.model.CaModel;
import com.cosmosource.common.service.CAMgrManager;
/**
* @类描述: CA用户查询记录
* @作者： yc
*/
public class CaUserAction extends BaseAction<TAcCauser>{
	private static final long serialVersionUID = 7764556646268559271L;

	//CA管理类注入
	private CAMgrManager caMgrManager;
	
	//CAUSER信息模型
	private TAcCauser entity = new TAcCauser();	
	
	private Page<TAcCauser> page = new Page<TAcCauser>(Constants.PAGESIZE);//默认每页20条记录
	//CA附属模型
	private CaModel caModel = new CaModel();
	
	/*
	 * 初始化查询页面
	 */
	public String init() throws Exception {
		entity.setLoginname((String)getSession().getAttribute(Constants.LOGIN_NAME));
		return INIT;
	}
	
	/*
	 * 查询CAUSER信息列表
	 */
	public String query() throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		page = caMgrManager.findCauserList(page, entity,caModel,map);
		return LIST;
	}
	
	/**
	 * @描述:查询CAUSER明细
	 */
	public String detail()  throws Exception{
		if(entity.getCauserid()==null) {
			return ERROR;
		}
		return DETAIL;
	}
	
	/**
	 * @描述:更改CAUSER明细
	 */
	public String update()  throws Exception{
		try{
			caMgrManager.update(entity);
			this.setIsSuc("true");
		}catch(Exception e){
			e.printStackTrace();
			this.setIsSuc("false");
		}
		return SUCCESS;
	}
	
	/**
	 * @描述:删除CAUSER明细
	 */
	public String delete()  throws Exception{
		if(entity.getCauserid()==null) {
			return ERROR;
		}
		String message = "";
		try{
			caMgrManager.delete(entity);
			message = "CA用户关联信息删除成功";
		}catch(Exception e){
			e.printStackTrace();
			message = "CA用户关联信息删除失败";
		}
		this.addActionMessage(message);
		return SUCCESS;
	}
	
		
	@Override
	protected void prepareModel() throws Exception {
		if(entity.getCauserid()!=null){
			entity = caMgrManager.findCauserById(entity.getCauserid());
		}	
	}

	public TAcCauser getModel() {
		// TODO Auto-generated method stub
		return entity;
	}

	public CAMgrManager getCaMgrManager() {
		return caMgrManager;
	}

	public void setCaMgrManager(CAMgrManager caMgrManager) {
		this.caMgrManager = caMgrManager;
	}

	public TAcCauser getEntity() {
		return entity;
	}

	public void setEntity(TAcCauser entity) {
		this.entity = entity;
	}

	public Page<TAcCauser> getPage() {
		return page;
	}

	public void setPage(Page<TAcCauser> page) {
		this.page = page;
	}

	public CaModel getCaModel() {
		return caModel;
	}

	public void setCaModel(CaModel caModel) {
		this.caModel = caModel;
	}

}
