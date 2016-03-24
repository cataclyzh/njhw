/**
* <p>文件名: DictMgrAction.java</p>
* <p>描述：业务字典管理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-9-6 下午07:01:02 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.common.entity.TAcDictdeta;
import com.cosmosource.common.service.DictMgrManager;

/**
* @类描述: 业务字典明细管理Action,用于业务字典明细的CRUD
* @作者： WXJ
*/
public class DictdetaMgrAction extends BaseAction<TAcDictdeta> {

	
	private static final long serialVersionUID = 959135426857335860L;
	private DictMgrManager dictMgrManager;
	//-- 页面属性 --//
	private Long dictdetaid ;
	private TAcDictdeta entity = new TAcDictdeta();
	private Page<TAcDictdeta> page = new Page<TAcDictdeta>(Constants.PAGESIZE);//默认每页20条记录	
	private String nodeId;//机构树节点ID
	private String orgid;
	private String _chk[];//选中记录的ID数组
	private String dictname;
	private String dictcode;
	public String getDictcode() {
		return dictcode;
	}
	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}
	public String getDictname() {
		return dictname;
	}
	public void setDictname(String dictname) {
		this.dictname = dictname;
	}
	
	//-- ModelDriven 与 Preparable函数 --//
	public TAcDictdeta getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		dictdetaid = entity.getDictid();
		if (dictdetaid != null) {
			entity = (TAcDictdeta)dictMgrManager.findById(TAcDictdeta.class, dictdetaid);
		} else {
			entity = new TAcDictdeta();
		}
	}
	/**
	 * 查询业务字典明细列表
	 * @return 
	 * @throws Exception
	 */
	public String querylist() throws Exception {
		String dictname=getParameter("dictname");
		String dictcode=getParameter("dictcode");
		if(dictname!=null){
			entity.setDictname(dictname);
		}
		if(dictcode!=null){
			entity.setDictcode(dictcode);
		}
		page = dictMgrManager.queryDictdetas(page, entity);	
		
		return "query";
	}
	
	public String list() throws Exception {
		String dictname=getParameter("dictname");
		String dictcode=getParameter("dictcode");
		if(dictname!=null){
			entity.setDictname(dictname);
		}
		if(dictcode!=null){
			entity.setDictcode(dictcode);
		}
		try {
			page = dictMgrManager.queryDictdetas(page, entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return SUCCESS;
	}
	
	/**
	 * 显示业务字典明细详细信息用于查看或是修改
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
			dictMgrManager.saveDictdeta(entity);
//			addActionMessage("保存业务字典明细成功");
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
//			addActionMessage("保存业务字典明细失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}
	/**
	 * 批量删除选中的业务字典明细
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			dictMgrManager.deleteDictdetas(_chk);
			addActionMessage("删除业务字典明细成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除业务字典明细失败");
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @描述: 机构树
	 * @throws Exception
	 * @return String
	 */
	public String orgTree() throws Exception {
		return SUCCESS;
	}
	/**
	 * 
	 * @描述: 菜框架
	 * @throws Exception
	 * @return String
	 */
	public String menuSubFrame() throws Exception {
		getRequest().setAttribute("orgid", nodeId);
		return SUCCESS;
	}
	/**
	 * 
	 * @描述: 业务字典明细树
	 * @throws Exception
	 * @return String
	 */
	public String menuTree() throws Exception {
		getRequest().setAttribute("orgid", orgid);
		return SUCCESS;
	}
	
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示业务字典明细分页列表.
	 */
	public Page<TAcDictdeta> getPage() {
		return page;
	}
	public void setDictMgrManager(DictMgrManager dictMgrManager) {
		this.dictMgrManager = dictMgrManager;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public Long getDictdetaid() {
		return dictdetaid;
	}

	public void setDictdetaid(Long dictdetaid) {
		this.dictdetaid = dictdetaid;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

}
