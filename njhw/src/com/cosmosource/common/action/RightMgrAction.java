/**
* <p>文件名: RightMgrAction.java</p>
* <p>描述：角色权限管理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-9-1 下午01:12:14 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.util.HashMap;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcRole;
import com.cosmosource.common.entity.TAcUser;
import com.cosmosource.common.service.RightMgrManager;

/**
* @类描述: 权限管理Action,用于角色功能的分配，用户角色的分配
* @作者： WXJ
*/
@SuppressWarnings({"unchecked","rawtypes"})
public class RightMgrAction extends BaseAction<TAcRole> {

	private static final long serialVersionUID = 4824882986461867253L;
	private RightMgrManager rightMgrManager;
	//-- 页面属性 --//
//	private Long roleid ;
	private TAcRole entity = new TAcRole();
	private Page<TAcRole> page = new Page<TAcRole>(Constants.PAGESIZE);//默认每页20条记录	
	private Page<TAcUser> pageUsers = new Page<TAcUser>(Constants.PAGESIZE);//默认每页20条记录
	private Page<TAcUser> pageUsersNoRight = new Page<TAcUser>(15);//默认每页20条记录
	private String nodeId;//机构树节点ID
//	private Long orgid;
	private String _chk[];//选中记录的ID数组
	private String checkedIds;
	
	private String usercode;
	private String username;
	private String company;
	
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
		if (entity.getRoleid() != null) {
			entity = (TAcRole)rightMgrManager.findById(TAcRole.class, entity.getRoleid());
		} else {
			entity = new TAcRole();
		}
	}
	/**
	 * 查询角色列表
	 * @return 
	 * @throws Exception
	 */
	public String roleList() throws Exception {
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setOrgid(new Long(nodeId));
		}
		page = rightMgrManager.queryRoles(page, entity);	
		
		return SUCCESS;
	}
	/**
	 * 权限管理主页面
	 * @return 
	 * @throws Exception
	 */
	public String rightFrame() throws Exception {
		
		Long companyId = (Long)getSessionAttribute("_companyid");	
		if(companyId.doubleValue()!=1){
			return "company";
		}
		return SUCCESS;
	}	
	/**
	 * 查询用户列表
	 * @return 
	 * @throws Exception
	 */
	
	public String userList() throws Exception {
		String tt = getParameter("roleid");
		Map map = new HashMap();
		map.put("roleid", new Long(tt));
		map.put("usercode", usercode);
		map.put("username", username);
		
		pageUsers = rightMgrManager.queryUsers(pageUsers, map);	
		return SUCCESS;
	}	
	/**
	 * 查询未分配角色的用户列表
	 * @return 
	 * @throws Exception
	 */
	public String userListNoRight() throws Exception {
		String tt = getParameter("roleid");
		Map map = new HashMap();
		map.put("roleid", new Long(tt));
		map.put("usercode", usercode);
		map.put("username", username);
		map.put("orgid", entity.getOrgid());
		TAcOrg org = (TAcOrg)rightMgrManager.findById(TAcOrg.class, entity.getOrgid());
		
		map.put("company", org.getCompany());
		map.put("proleid", new Long(tt));
		pageUsersNoRight = rightMgrManager.queryNoRightUsers(pageUsersNoRight, map);
		return SUCCESS;
	}	
	/**
	 * 保存用户信息到角色
	 * @return 
	 * @throws Exception
	 */
	public String saveUsersToRole() throws Exception {
		try {
			String srole = Struts2Util.getParameter("roleid");
			rightMgrManager.saveUsersToRole(new Long(srole), _chk);
//			addActionMessage("保存成功");
			setIsSuc("true");
		} catch (Exception e) {
//			addActionMessage("保存失败");
			setIsSuc("true");
		}
		return SUCCESS;
	}
	/**
	 * 从角色中删除用户信息
	 * @return 
	 * @throws Exception
	 */
	public String deleteUsersFromRole() throws Exception {
		try {
			String srole = Struts2Util.getParameter("roleid");
			rightMgrManager.deleteUsersFromRole(new Long(srole), _chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("删除失败");
		}
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
	public String saveRights() throws Exception {
		try {
			if(checkedIds!=null){
				rightMgrManager.saveRolerights(entity.getRoleid(), checkedIds);
			}
			addActionMessage("保存权限成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("保存权限失败");
		}
		return SUCCESS;
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
		Struts2Util.renderXml(rightMgrManager.getOrgTreeData(nodeId,getContextPath()), 
				"encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 取得菜单树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String menuTreeData() throws Exception {
		try {
			Struts2Util.renderXml(rightMgrManager.getMenuTreeData(nodeId,entity.getOrgid(),entity.getRoleid()), 
					"encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return null;
	}
	/**
	 * 
	 * @描述: 功能菜单树
	 * @throws Exception
	 * @return String
	 */
	public String menuTree() throws Exception {
		return SUCCESS;
	}	
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示角色分页列表.
	 */
	public Page<TAcRole> getPage() {
		return page;
	}
	public void setRightMgrManager(RightMgrManager rightMgrManager) {
		this.rightMgrManager = rightMgrManager;
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


	public String getCheckedIds() {
		return checkedIds;
	}
	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public String getNodeId() {
		return nodeId;
	}
	public Page<TAcUser> getPageUsers() {
		return pageUsers;
	}
	public void setPageUsers(Page<TAcUser> pageUsers) {
		this.pageUsers = pageUsers;
	}
	public void setPage(Page<TAcRole> page) {
		this.page = page;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Page<TAcUser> getPageUsersNoRight() {
		return pageUsersNoRight;
	}
	public void setPageUsersNoRight(Page<TAcUser> pageUsersNoRight) {
		this.pageUsersNoRight = pageUsersNoRight;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
