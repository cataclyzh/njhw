/**
* <p>文件名: RoleMgrAction.java</p>
* <p>描述：角色管理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午05:28:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.service.RoleMgrManager;

/**
* @类描述: 角色管理Action,用于角色的CRUD，机构树的显示
* @作者： WXJ
*/
public class RoleMgrAction extends BaseAction<TAcRole> {

	private static final long serialVersionUID = 1L;

	private RoleMgrManager roleMgrManager;
	//-- 页面属性 --//
	private Long roleid ;
	private TAcRole entity = new TAcRole();
	private Page<TAcRole> page = new Page<TAcRole>(Constants.PAGESIZE);//默认每页20条记录	
	private String nodeId;//机构树节点ID
	private String _chk[];//选中记录的ID数组
	
	//-- ModelDriven 与 Preparable函数 --//
	public TAcRole getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		roleid = entity.getRoleid();
		if (roleid != null) {
			entity = (TAcRole)roleMgrManager.findById(TAcRole.class, roleid);
		} else {
			entity = new TAcRole();
		}
	}
	/**
	 * 查询角色列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
		String nodeId = getParameter("nodeId");
		if(nodeId!=null){
			entity.setOrgid(new Long(nodeId));
		}
		page = roleMgrManager.queryRoles(page, entity);	
		
		return SUCCESS;
	}
	
	/**
	 * 显示角色详细信息用于查看或是修改
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
//			roleMgrManager.initRole(entity);
			roleMgrManager.saveRole(entity);
//			addActionMessage("保存角色成功");
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			addActionMessage("保存角色失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}
	/**
	 * 批量删除选中的角色
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			roleMgrManager.deleteRoles(_chk);
			addActionMessage("删除角色成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除角色失败");
		}
		return RELOAD;
	}
	public String orgTree() throws Exception {
		return SUCCESS;
	}
	/**
	 * 取得机构树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String orgTreeData() throws Exception {
		Struts2Util.renderXml(roleMgrManager.getOrgTreeData(nodeId,getContextPath()), 
				"encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 角色维护主页面
	 * @return 
	 * @throws Exception
	 */
	public String roleFrame() throws Exception {
		return SUCCESS;
	}
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示角色分页列表.
	 */
	public Page<TAcRole> getPage() {
		return page;
	}
	public void setRoleMgrManager(RoleMgrManager roleMgrManager) {
		this.roleMgrManager = roleMgrManager;
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

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}


	
}
