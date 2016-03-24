/**
* <p>文件名: MenuMgrAction.java</p>
* <p>描述：功能菜单管理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午06:21:24 
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
import com.cosmosource.common.entity.TAcMenu;
import com.cosmosource.common.service.MenuMgrManager;

/**
* @类描述: 功能菜单管理Action,用于功能菜单的CRUD，机构树的显示
* @作者： WXJ
*/
public class MenuMgrAction extends BaseAction<TAcMenu> {

	private static final long serialVersionUID = 1L;

	private MenuMgrManager menuMgrManager;
	//-- 页面属性 --//
	private Long menuid ;
	private TAcMenu entity = new TAcMenu();
	private Page<TAcMenu> page = new Page<TAcMenu>(Constants.PAGESIZE);//默认每页20条记录	
	private Long nodeId;//机构树节点ID
	private String _chk[];//选中记录的ID数组
	
	
	//-- ModelDriven 与 Preparable函数 --//
	public TAcMenu getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		menuid = entity.getMenuid();
		if (menuid != null) {
			entity = (TAcMenu)menuMgrManager.findById(TAcMenu.class, menuid);
		} else {
			entity = new TAcMenu();
		}
	}
	/**
	 * 查询功能菜单列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {

		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setParentid(new Long(nodeId));
		}
		page = menuMgrManager.queryMenus(page, entity);	
		
		return SUCCESS;
	}
	
	/**
	 * 显示功能菜单详细信息用于查看或是修改
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
			menuMgrManager.saveMenu(entity);
			setIsSuc("true");
//			addActionMessage("保存功能菜单成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
//			addActionMessage("保存功能菜单失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}
	/**
	 * 批量删除选中的功能菜单
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			Long valMenu =  menuMgrManager.deleteMenus(_chk);
			
			if(valMenu.longValue()>0){
				addActionMessage("请删除功能菜单的关联信息");
			}else{
				addActionMessage("删除功能菜单成功");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除功能菜单失败");
		}
		return RELOAD;
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
	 * @描述: 功能菜单树
	 * @throws Exception
	 * @return String
	 */
	public String menuTree() throws Exception {
//		getRequest().setAttribute("orgid", orgid);
		return SUCCESS;
	}
	/**
	 * 取得机构树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String orgTreeData() throws Exception {
		
		Struts2Util.renderXml(menuMgrManager.getOrgTreeData(nodeId,getContextPath()), 
		"encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 取得菜单树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String menuTreeData() throws Exception {
		String sOrgid = getParameter("orgid");
		
		Struts2Util.renderXml(menuMgrManager.getMenuTreeData(nodeId,new Long(sOrgid),getContextPath()), 
				"encoding:UTF-8", "no-cache:true");
		
		return null;
	}
	/**
	 * 菜单维护主页面
	 * @return 
	 * @throws Exception
	 */
	public String menuFrame() throws Exception {
		return SUCCESS;
	}
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示功能菜单分页列表.
	 */
	public Page<TAcMenu> getPage() {
		return page;
	}
	public void setMenuMgrManager(MenuMgrManager menuMgrManager) {
		this.menuMgrManager = menuMgrManager;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
}
