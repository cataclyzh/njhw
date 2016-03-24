package com.cosmosource.common.action;

import java.util.ArrayList;
import java.util.List;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcCaaction;
import com.cosmosource.common.entity.TAcCauseraction;
import com.cosmosource.common.model.CafunctionQueryModel;
import com.cosmosource.common.service.CAMgrManager;

/**
 * @类描述: CA操作
 * 
 * @作者： fengfj
 */
public class CaFunctionAction extends BaseAction<TAcCaaction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9200533912794519314L;

	private TAcCaaction entity = new TAcCaaction();
	private Long actionid;
	private Page<TAcCaaction> page = new Page<TAcCaaction>(Constants.PAGESIZE);
	private CafunctionQueryModel cafunctionQueryModel = new CafunctionQueryModel();
	private CAMgrManager camgrManager;
	private String _chk[];// 选中记录的ID数组

	/**
	 * 进入ca操作界面
	 * 
	 * @return ffj 2011-12-14
	 */
	public String init() {
		return INIT;
	}

	/**
	 * ca操作维护查询列表（分页）
	 * 
	 * @return ffj 2011-12-14
	 */
	public String query() {
		try {
			page = camgrManager.findCaFunctionList(page, entity,
					cafunctionQueryModel, null);
			return LIST;
		} catch (Exception e) {
			logger.error("CA操作查询异常", e);
			return ERROR;
		}
	}

	/**
	 * 进入添加页面
	 * 
	 * @return ffj 2011-12-15
	 */
	public String input() {
		return INPUT;
	}

	/**
	 * 保存ca操作
	 * 
	 * @return ffj 2011-12-15
	 */
	public String save() {
		try {
			camgrManager.saveCaFunction(entity);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error("CA操作录入异常", e);
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 修改方法
	 * @return
	 * ffj 2011-12-15
	 */
	public String update() {
		try {
			TAcCaaction nentity = camgrManager.findByActionId(actionid);
			if (nentity != null) {
				camgrManager.updateCaFunction(entity);
				setIsSuc("true");
			} else {
				setIsSuc("del");
			}
		} catch (Exception e) {
			logger.error("CA操作修改异常", e);
			setIsSuc("del");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 查看明细
	 * 
	 * @return ffj 2011-12-15
	 */
	public String detail() {
		return DETAIL;
	}

	/**
	 * 删除(可批量)
	 * 
	 * @return
	 * @throws Exception
	 *             ffj 2011-12-15
	 */
	@SuppressWarnings("unchecked")
	public String delete() throws Exception {
		try {
			List list = new ArrayList();
			for(String id : _chk){
				TAcCaaction entityActionCode = camgrManager.findByActionId(Long.parseLong(id));
				TAcCauseraction entity = camgrManager.findCauseractionByactioncode(entityActionCode.getActioncode());
				//判断actioncode在表T_AC_CAUSERACTION中是否存在，如果存在，则不能删除，否则可以删除
				if (entity != null) {
					list.add(entityActionCode.getActioncode());
				} else {
					camgrManager.deleteCaFunction(Long.parseLong(id));
				}
			}
			addActionMessage("删除CA操作数据成功，其中操作代码为"+list+"正在使用，不能删除！");
		} catch (Exception e) {
			addActionMessage("删除CA操作失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 验证actionid是否唯一
	 * @return
	 * ffj 2011-12-19
	 */
	public String actioncodeValidate(){
		String actioncode = getParameter("actioncode");
		if (camgrManager.isActionCodeUnique(actioncode)) {
			Struts2Util.renderText("true");
		} else {
			Struts2Util.renderText("false");
		}
		return null;
	}
	
	

	@Override
	protected void prepareModel() throws Exception {
		actionid = entity.getActionid();
		if (actionid != null) {
			entity = camgrManager.findByActionId(entity.getActionid());
		} else {
			entity = new TAcCaaction();
		}
	}

	public TAcCaaction getModel() {
		return entity;
	}

	public Page<TAcCaaction> getPage() {
		return page;
	}

	public void setPage(Page<TAcCaaction> page) {
		this.page = page;
	}

	public CafunctionQueryModel getCafunctionQueryModel() {
		return cafunctionQueryModel;
	}

	public void setCafunctionQueryModel(
			CafunctionQueryModel cafunctionQueryModel) {
		this.cafunctionQueryModel = cafunctionQueryModel;
	}

	public CAMgrManager getCamgrManager() {
		return camgrManager;
	}

	public void setCamgrManager(CAMgrManager camgrManager) {
		this.camgrManager = camgrManager;
	}

	public Long getActionid() {
		return actionid;
	}

	public void setActionid(Long actionid) {
		this.actionid = actionid;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}


}
