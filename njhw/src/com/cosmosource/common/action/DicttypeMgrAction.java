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

import java.util.List;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcDicttype;
import com.cosmosource.common.service.DictMgrManager;

/**
* @类描述: 业务字典类型管理Action,用于业务字典类型的CRUD
* @作者： WXJ
*/
@SuppressWarnings({"rawtypes"})
public class DicttypeMgrAction extends BaseAction<TAcDicttype> {

	private static final long serialVersionUID = -6009836376548324262L;
	private DictMgrManager dictMgrManager;
	//-- 页面属性 --//
	private Long dicttypeid ;
	private TAcDicttype entity = new TAcDicttype();
	private Page<TAcDicttype> page = new Page<TAcDicttype>(15);//默认每页20条记录	
	private String _chk[];//选中记录的ID数组
	
	private String tempDicttypecode;
	
	//-- ModelDriven 与 Preparable函数 --//
	public TAcDicttype getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		dicttypeid = entity.getDicttypeid();
		if (dicttypeid != null) {
			entity = (TAcDicttype)dictMgrManager.findById(TAcDicttype.class, dicttypeid);
			this.setTempDicttypecode(entity.getDicttypecode());
		} else {
			entity = new TAcDicttype();
		}
	}
	/**
	 * 查询业务字典类型列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {

		String dicttypename = getRequest().getParameter("dicttypename");
		String dicttypecode = getRequest().getParameter("dicttypecode");
		getRequest().setAttribute("dicttypename", dicttypename);
		getRequest().setAttribute("dicttypecode", dicttypecode);
		page = dictMgrManager.queryDicttypes(page, entity);	
		
		return SUCCESS;
	}
	
	 /**
	* @Description 业务字典
	* @Author：pingxianghua
	* @Date 2013-8-15 上午09:50:44 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String Index() throws Exception {

		return SUCCESS;
	}
	
	/**
	 * 显示业务字典类型详细信息用于查看或是修改
	 * @return 
	 * @throws Exception
	 */
	public String input() throws Exception {
//		List list = new ArrayList();
//		entity.setDicttypedesc("aa");
//		entity.setDicttypename("bbb");
//		list.add(entity);
//		getRequest().setAttribute("_dictt", list);
//		System.out.println("<<<<<<<<<<<<<<<<<");
//		page = dictMgrManager.queryDicttypes(page, entity);	
//		
//		getSession().getServletContext().setAttribute("_page", page);
		return INPUT;
	}
	/**
	 * 刷新业务字典缓存
	 * @return 
	 * @throws Exception
	 */
		
	/**
	 * 保存信息
	 * @return 
	 * @throws Exception
	 */
	public String save() throws Exception {
		try {
			dictMgrManager.saveDicttype(entity);
//			addActionMessage("保存业务字典类型成功");
			setIsSuc("true");
		} catch (Exception e) {
//			addActionMessage("保存业务字典类型失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}
	/**
	 * 批量删除选中的业务字典类型
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			dictMgrManager.deleteDicttypes(_chk);
			addActionMessage("删除业务字典类型成功");
		} catch (Exception e) {
			addActionMessage("删除业务字典类型失败");
		}
		return SUCCESS;
	}

	/**
	 * 支持使用Jquery.validate Ajax检验.
	 */
	public String checkDicttypecode() {
		String code = getParameter("dicttypecode");
		String temp = getParameter("tempDicttypecode");
		
		if (dictMgrManager.isDicttypecodeUnique(code, temp)) {
			Struts2Util.renderText("true");
		} else {
			Struts2Util.renderText("false");
		}
	
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
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
	
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示业务字典类型分页列表.
	 */
	public Page<TAcDicttype> getPage() {
		return page;
	}
	public void setDictMgrManager(DictMgrManager dictMgrManager) {
		this.dictMgrManager = dictMgrManager;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public Long getDicttypeid() {
		return dicttypeid;
	}

	public void setDicttypeid(Long dicttypeid) {
		this.dicttypeid = dicttypeid;
	}
	public String getTempDicttypecode() {
		return tempDicttypecode;
	}
	public void setTempDicttypecode(String tempDicttypecode) {
		this.tempDicttypecode = tempDicttypecode;
	}

}
