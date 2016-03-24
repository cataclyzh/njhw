/**
* <p>文件名: CttMgrAction.java</p>
* <p>描述：常量管理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.common.entity.TAcDictdeta;
import com.cosmosource.common.entity.TCommonConstants;
import com.cosmosource.common.service.ConstantsManager;

/**
* @类描述: 常量管理Action
* @作者： WXJ
*/
public class CttMgrAction extends BaseAction<TCommonConstants> {

	private static final long serialVersionUID = 8497952646128136433L;
	private ConstantsManager constantsManager;
	//-- 页面属性 --//
	private Long cttid ;
	private TCommonConstants entity = new TCommonConstants();
	private Page<TCommonConstants> page = new Page<TCommonConstants>(Constants.PAGESIZE);//默认每页20条记录
	private String _chk[];//选中记录的ID数组
	
	//-- ModelDriven 与 Preparable函数 --//
	public TCommonConstants getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		cttid = entity.getCttid();
		if (cttid != null) {
			entity = (TCommonConstants)constantsManager.findById(TCommonConstants.class, cttid);
		} else {
			entity = new TCommonConstants();
		}
	}
	/**
	 * 查询列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
		String cttKey = getRequest().getParameter("cttKey");
		String cttType = getRequest().getParameter("cttType");
		getRequest().setAttribute("cttKey", cttKey);
		getRequest().setAttribute("cttType", cttType);
		
		page = constantsManager.queryCtts(page, entity);
		List<TCommonConstants> list = page.getResult();
		TCommonConstants c = null;
		String type = "";
		for(int i = 0;i < list.size();i++){
			c = list.get(i);
			type = c.getCttType();
			type = getDictNameByIdFromCache("COMPANY", type);
			c.setCttType(type);
		}
		return SUCCESS;
	}
	
	public String index(){
		return SUCCESS;
	}
	/**
	 * 录入页面
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
			//保存机构信息
			if(entity.getCttKey()!= null||entity.getCttKey().length() == 0)
			{
				constantsManager.saveCtt(entity);
				setIsSuc("true");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 批量删除选中
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			String deleteIds = getParameter("deleteIds");
			if(deleteIds == null){
				deleteIds = "";
			}
			constantsManager.deleteCtts(deleteIds.split(","));
			
			addActionMessage("删除机构成功");
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除机构失败");
			
		}
		return RELOAD;
	}
	
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示分页列表.
	 */
	public Page<TCommonConstants> getPage() {
		return page;
	}
	public ConstantsManager getConstantsManager() {
		return constantsManager;
	}
	public void setConstantsManager(ConstantsManager constantsManager) {
		this.constantsManager = constantsManager;
	}
	public Long getCttid() {
		return cttid;
	}
	public void setCttid(Long cttid) {
		this.cttid = cttid;
	}
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] _chk) {
		this._chk = _chk;
	}

}
