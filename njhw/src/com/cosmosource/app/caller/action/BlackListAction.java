package com.cosmosource.app.caller.action;

import java.util.Map;

import com.cosmosource.app.caller.service.BlackListManager;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;

/**
 * 
* @description: 访客管理(黑名单管理)
* @author SQS
* @date 2013-3-17 下午09:55:55
 */
@SuppressWarnings({"serial" })
public class BlackListAction extends BaseAction<VmVisitorinfo> {

	private Page<VmVisitorinfo> page = new Page<VmVisitorinfo>(Constants.PAGESIZE);//默认每页20条记录  
	private VmVisitorinfo vmVisitorinfo = new VmVisitorinfo();
	
	private BlackListManager blackListManager;
	
	@Override
	protected void prepareModel() throws Exception {
	}
	
	/**
	 * 
	* @title: init 
	* @description: 初始化黑名单管理
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String initBlackList() {
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: init 
	 * @description: 查询按钮(使用)--访客信息
	 * @author SQS
	 * @return
	 * @date 2013-3-19 上午11:11:33
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryBlackList() {
		try {
			Map localMap = ConvertUtils.pojoToMap(vmVisitorinfo);
			page = blackListManager.selectBlackList(page, localMap);
			return SUCCESS;
		} catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	 * @title: init
	 * @description: 加入黑名单按钮(使用)
	 * @author SQS
	 * @return
	 * @date 2013-3-19 上午11:11:33
	 * @throws
	 */
	public String addBlackList() {
		String viId = getParameter("viId");
		VmVisitorinfo vm = (VmVisitorinfo)blackListManager.findById(VmVisitorinfo.class, Long.parseLong(viId));
		getRequest().setAttribute("viName", vm.getViName());
		getRequest().setAttribute("blackReson", vm.getBlackReson());
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: init 
	 * @description: 保存按钮  (使用) 
	 * @author SQS
	 * @return
	 * @date 2013-3-19 上午11:11:11
	 * @throws
	 */
	public String saveBlackList() {
		try {
			String viId = getParameter("viId");
			String blackReson = getParameter("blackReson");
			blackListManager.saveBlackList(vmVisitorinfo,Long.parseLong(viId),blackReson);
			setIsSuc("true");
		} catch(Exception e){
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: init 
	 * @description: 取消黑名单按钮(使用)
	 * @author SQS
	 * @return
	 * @date 2013-3-19 上午11:11:33
	 * @throws
	 */
	public String delBlackList() {
		String viId = getParameter("viId");
		VmVisitorinfo vm = (VmVisitorinfo)blackListManager.findById(VmVisitorinfo.class, Long.parseLong(viId));
		getRequest().setAttribute("viName", vm.getViName());
		getRequest().setAttribute("residentNo",vm.getResidentNo());
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: init 
	 * @description: 取消黑名单按钮(使用)
	 * @author SQS
	 * @return
	 * @date 2013-3-19 上午11:11:33
	 * @throws
	 */
	public String delBlackListButton() {
		try {
			String viId = getParameter("viId");
			blackListManager.updateBlackListCancel(vmVisitorinfo,Long.parseLong(viId));
			setIsSuc("true");
		} catch(Exception e){
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Override
	public VmVisitorinfo getModel() {
		return vmVisitorinfo;
	}
	
	public BlackListManager getBlackListManager() {
		return blackListManager;
	}
	public void setBlackListManager(BlackListManager blackListManager) {
		this.blackListManager = blackListManager;
	}
	public Page<VmVisitorinfo> getPage() {
		return page;
	}
	public void setPage(Page<VmVisitorinfo> page) {
		this.page = page;
	}
	public VmVisitorinfo getVmVisitorinfo() {
		return vmVisitorinfo;
	}
	public void setVmVisitorinfo(VmVisitorinfo vmVisitorinfo) {
		this.vmVisitorinfo = vmVisitorinfo;
	}
	
}
