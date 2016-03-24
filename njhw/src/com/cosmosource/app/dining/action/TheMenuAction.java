package com.cosmosource.app.dining.action;

import java.util.HashMap;


import com.cosmosource.app.dining.service.TheMenuManager;
import com.cosmosource.app.entity.FsDishesIssue;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;



@SuppressWarnings("serial")
public class TheMenuAction extends BaseAction<FsDishesIssue> {

	public static final int PHONE_PAGE_NO = 100;
	private FsDishesIssue fsDishesIssue = new FsDishesIssue();
	private Page<FsDishesIssue> page = new Page<FsDishesIssue>(Constants.PAGESIZE); //默认是20页
	private TheMenuManager theMenuManager;
	private String fdiId;
	private String _chk[];//选中记录的ID数组
	@Override
	protected void prepareModel() throws Exception {
	}
	@Override
	public FsDishesIssue getModel() {
		return fsDishesIssue;
	}
	
	/**
	 * 
	* @title: theMenuInput 
	* @description: 修改菜肴信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String theMenuInput(){
		try {
			Long fdiId = fsDishesIssue.getFdiId();
			if(fdiId !=null){
				fsDishesIssue = (FsDishesIssue)theMenuManager.findById(FsDishesIssue.class, fdiId);
//				List list = (List) theMenuManager.findByHQL(" from fsDishes f,fsDishesIssue fs where f.fdId = fs.fdId and fs.fdiId = ?", fdiId);
//				System.out.println("1111111111111111111111111111111="+list.length());
			}else{
				fsDishesIssue = new FsDishesIssue();
			}
			getRequest().setAttribute("fsDishesIssue", fsDishesIssue);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INPUT;
	}
	
	/**
	 * 
	* @title: theMenuAdd 
	* @description: 增加菜肴信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String theMenuAdd(){
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: initTheMenu 
	* @description: 初始化录入页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	public String initTheMenu(){
		return INIT;
	}
	
	/**
	 * 
	* @title: saveTheMenu 
	* @description: 保存或修改菜肴信息
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:58     
	* @throws
	 */
	public String saveTheMenu(){
		try {
			theMenuManager.saveUpdateTheMenu(fsDishesIssue);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: inputTheMenu 
	 * @description: 保存或修改菜肴信息
	 * @author sqs
	 * @return
	 * @date 2013-3-19 下午09:12:58     
	 * @throws
	 */
	public String inputTheMenu(){
		try {
			theMenuManager.saveUpdateTheMenu(fsDishesIssue);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: queryTheMenu 
	* @description: 查询按钮使用
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:19:19     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryTheMenu() throws Exception {
		try {
			HashMap localMap =  (HashMap) ConvertUtils.pojoToMap(fsDishesIssue);
			page.setPageSize(PHONE_PAGE_NO);
		  //String fdClass = getParameter("fdClass");
		  //localMap.put("fdClass", fdClass);
		  //getRequest().setAttribute("fdClass", fdClass);
			getRequest().setAttribute("fdiType",localMap.get("fdiType").toString());
			getRequest().setAttribute("fdiFlag",localMap.get("fdiFlag").toString());
			page = theMenuManager.queryTheMenu(page,localMap);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	* @title: deleteTheMenu 
	* @description: 批量删除选中的菜肴
	* @author sqs
	* @return
	* @throws Exception
	* @date 2013-3-19 下午06:19:21     
	* @throws
	 */
	public String deleteTheMenu() throws Exception {
		try {
			theMenuManager.deleteTheMenu(_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: theMenuSave 
	 * @description: 保存发布的菜单
	 * @author sqs
	 * @return
	 * @throws Exception
	 * @date 2013-3-19 下午06:19:21     
	 * @throws
	 */
	public String theMenuSave() throws Exception {
		String ids = getParameter("cfdiId");	//菜ID 数组
		String fdiFlag = getParameter("fdiFlagFlag");	//三餐
		String fdiType = getParameter("fdiTypeType");	//星期
		String fabufdiIdact = getParameter("fabufdiIdact");	//发布表ID
		String pageNo = null;
		try {
			theMenuManager.saveTheMenu(fsDishesIssue,ids,fdiFlag,fdiType,fabufdiIdact);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		getRequest().setAttribute("pageNo", pageNo);
		return null;
	}
	
	/**
	 * 
	* @title: ajaxRefreshTell 
	* @description: 异步刷新
	* @author sqs
	* @return
	* @throws Exception
	* @date Apr 9, 2013 3:29:16 PM     
	* @throws
	 */
	public String ajaxRefreshTell() throws Exception {
		HashMap<String, Long> map = new HashMap<String, Long>();
		long fdiId = Long.parseLong(getParameter("fdiId"));
		map.put("fdiId", fdiId);
		
		page.setPageSize(PHONE_PAGE_NO);
		String p1 = this.getRequest().getParameter("pageNo");
		page.setPageNo(Integer.valueOf(p1));
		//page = theMenuManager.loadTheMenu(page,map);
		this.getRequest().setAttribute("fdiId",fdiId);
		return SUCCESS;
	}
	
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public TheMenuManager getTheMenuManager() {
		return theMenuManager;
	}
	public void setTheMenuManager(TheMenuManager theMenuManager) {
		this.theMenuManager = theMenuManager;
	}
	public FsDishesIssue getFsDishesIssue() {
		return fsDishesIssue;
	}
	public void setFsDishesIssue(FsDishesIssue fsDishesIssue) {
		this.fsDishesIssue = fsDishesIssue;
	}
	public Page<FsDishesIssue> getPage() {
		return page;
	}
	public void setPage(Page<FsDishesIssue> page) {
		this.page = page;
	}
	public String getFdiId() {
		return fdiId;
	}
	public void setFdiId(String fdiId) {
		this.fdiId = fdiId;
	}
	
}
