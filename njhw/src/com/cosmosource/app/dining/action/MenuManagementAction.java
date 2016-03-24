package com.cosmosource.app.dining.action;

import java.util.Map;

import com.cosmosource.app.dining.service.MenuManagementManager;
import com.cosmosource.app.entity.FsMenu;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.StringUtil;

@SuppressWarnings("serial")
public class MenuManagementAction extends BaseAction<FsMenu> {

	private FsMenu fsMenu = new FsMenu();
	private Page<FsMenu> page = new Page<FsMenu>(Constants.PAGESIZE); //默认是20页
	private MenuManagementManager menuManagementManager;
	private String fmId;
	private String _chk[];//选中记录的ID数组
	
	@Override
	protected void prepareModel() throws Exception {
	}
	@Override
	public FsMenu getModel() {
		return fsMenu;
	}
	
	/**
	 * 
	* @title: menuManagementInput 
	* @description: 修改菜单信息
	* @author hj
	* @return
	* @date 2013-8-9
	* @throws
	 */
	public String menuManagementInput(){
		try {
			Long fmId = fsMenu.getFmId();
			if(fmId !=null){
				fsMenu = (FsMenu)menuManagementManager.findById(FsMenu.class, fmId);
			}else{
				fsMenu = new FsMenu();
			}
			getRequest().setAttribute("fsMenu", fsMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: inputMenus
	 * @description: 保存或修改菜肴类别信息
	 * @author hj
	 * @return
	 * @date 2013-8-9
	 * @throws
	 */
	public String inputMenus(){
		try {
			String fmId = getParameter("fmId");
			String fmbak1 = getParameter("fmBak1");
			String fmbak2 = getParameter("fmBak2");
			String fmName = getParameter("fmName");
			if(fmId !=null && StringUtil.isNotBlank(fmId)){
				fsMenu = (FsMenu)menuManagementManager.findById(FsMenu.class, Long.parseLong(fmId));
			}else{
				fsMenu = new FsMenu();
			}
			fsMenu.setFmName(fmName);
			String oldOrder = fsMenu.getFmBak1();
			String orderNum = menuManagementManager.updateOrderNum(fmbak1, oldOrder);
			fsMenu.setFmBak1(orderNum);
			fsMenu.setFmBak2(fmbak2);
			menuManagementManager.saveUpdateMenu(fsMenu);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: deleteDishes 
	* @description: 批量删除选中的菜肴
	* @author sqs
	* @return
	* @throws Exception
	* @date 2013-3-19 下午06:19:21     
	* @throws
	 */
	public String deleteMenus() throws Exception {
		try {
			String failMenusName = menuManagementManager.deleteMenus(_chk);
			if (StringUtil.isNotBlank(failMenusName)) {
				addActionMessage(failMenusName+"已在使用，不能删除！");
			} else {
				addActionMessage("删除成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: queryMenus 
	* @description: 查询按钮使用
	* @author hj
	* @return
	* @date 2013-8-9
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryMenus() throws Exception {
		try {
			Map localMap = ConvertUtils.pojoToMap(fsMenu);
			page = menuManagementManager.queryMenus(page, localMap);
			return SUCCESS;
		} catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	* @title: initTemporaryCard 
	* @description: 初始化录入页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	public String initFoodManagement(){
		return INIT;
	}
	
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public FsMenu getFsMenu() {
		return fsMenu;
	}
	public void setFsMenu(FsMenu fsMenu) {
		this.fsMenu = fsMenu;
	}
	public MenuManagementManager getMenuManagementManager() {
		return menuManagementManager;
	}
	public void setMenuManagementManager(MenuManagementManager menuManagementManager) {
		this.menuManagementManager = menuManagementManager;
	}
	public String getFmId() {
		return fmId;
	}
	public void setFmId(String fmId) {
		this.fmId = fmId;
	}
	public Page<FsMenu> getPage() {
		return page;
	}
	public void setPage(Page<FsMenu> page) {
		this.page = page;
	}
}
