package com.cosmosource.common.action;

import java.util.HashMap;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.common.entity.TAcCaapply;
import com.cosmosource.common.model.CaModel;
import com.cosmosource.common.service.CAMgrManager;
/**
* @类描述: CA信息查询记录
* @作者： yc
*/
public class CaQueryAction extends BaseAction<TAcCaapply> {
	private static final long serialVersionUID = 7764556646268559271L;

	//CA管理类注入
	private CAMgrManager caMgrManager;
	
	//CA信息模型
	private TAcCaapply entity = new TAcCaapply();	
	
	private Page<TAcCaapply> page = new Page<TAcCaapply>(Constants.PAGESIZE);//默认每页20条记录
	//CA附属模型
	private CaModel caModel = new CaModel();
	
	//购方/销方标志
	private String flog;
	
	/*
	 * 初始化查询页面
	 */
	public String init() throws Exception {
		//申请日期
		caModel.setApplyDateStart(DateUtil.getStrMonthFirstDay());
		caModel.setApplyDateEnd(DateUtil.getDateTime("yyyy-MM-dd"));
		return INIT;
	}
	
	/*
	 * 查询CA信息列表
	 */
	public String query() throws Exception {
		//当为销方时
		if(flog!=null&&flog.trim().equals("1")){
			//输入申请人
			String loginname = (String)getSession().getAttribute(Constants.LOGIN_NAME);
			entity.setApplyuser(loginname);
			//当销方 不输入任何申请人后 返回一个空的list集合
			if(entity.getApplyuser()==null||entity.getApplyuser().trim().equals("")){
				return LIST;
			}
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		entity.setDelstatus("0");//正常 未删除状态
		map.put("dateType", "1");//设置日期查询方式为申请日期
		page = caMgrManager.findCaList(page, entity,caModel,map);
		return LIST;
	}
	
	/**
	 * @描述:查询CA明细
	 */
	public String detail()  throws Exception{
		if(entity.getCaid()==null) {
			return ERROR;
		}
		return DETAIL;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		if(entity.getCaid()!=null){
			entity = caMgrManager.findCaById(entity.getCaid());
		}	
	}
	public TAcCaapply getEntity() {
		return entity;
	}

	public void setEntity(TAcCaapply entity) {
		this.entity = entity;
	}

	public Page<TAcCaapply> getPage() {
		return page;
	}

	public void setPage(Page<TAcCaapply> page) {
		this.page = page;
	}

	public CaModel getCaModel() {
		return caModel;
	}

	public void setCaModel(CaModel caModel) {
		this.caModel = caModel;
	}

	public CAMgrManager getCaMgrManager() {
		return caMgrManager;
	}

	public void setCaMgrManager(CAMgrManager caMgrManager) {
		this.caMgrManager = caMgrManager;
	}

	public TAcCaapply getModel() {
		return entity;
	}

	public String getFlog() {
		return flog;
	}

	public void setFlog(String flog) {
		this.flog = flog;
	}
}
