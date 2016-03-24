package com.cosmosource.app.personnel.action;

import java.util.List;

import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.personnel.service.TcIpTelManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("serial")
public class TcIpTelAction extends BaseAction<TcIpTel> {
    
	private TcIpTel tcIpTel = new TcIpTel();;
	private NjhwUsersExp njhwUsersExp = new NjhwUsersExp();;
	private Page<TcIpTel> page = new Page<TcIpTel>(Constants.PAGESIZE); //默认是20页
	private TcIpTelManager tcIpTelManager;
	private Long telId;
	private String _chk[];//选中记录的ID数组
	@Override
	protected void prepareModel() throws Exception {
		telId = tcIpTel.getTelId();
		if(telId !=null){
			tcIpTel = (TcIpTel) this.tcIpTelManager.findById(TcIpTel.class, telId);
		}else{
			tcIpTel = new TcIpTel();
		}
	}
	@Override
	public TcIpTel getModel() {
		return tcIpTel;
	}

	public String input(){
		return INPUT;
	}
	
	/**
	 * 
	* @title: init 
	* @description: 初始化录入页面
	* @author cjw
	* @return
	* @date 2013-3-23 下午09:12:42     
	* @throws
	 */
	public String init(){
		return INIT;
	}
	
	/**
	 * 
	* @title: saveIpTel 
	* @description: 保存或修改ip电话信息
	* @author cjw
	* @return
	* @date 2013-3-23 下午09:12:58     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String saveIpTel(){
		try {
			String usersExpTelId = getParameter("telId");
			String telNum = getParameter("telNum");
			String telMac = getParameter("telMac");
			tcIpTelManager.saveUpdateIpTel(tcIpTel);
			if(StringUtil.isNotBlank(usersExpTelId)){
				List<NjhwUsersExp> list = tcIpTelManager.findByHQL(" from NjhwUsersExp n where n.telNum = ? ",usersExpTelId);
				if(list.size()>0){
					njhwUsersExp = list.get(0);
					//njhwUsersExp.setTelNum(telNum);
					njhwUsersExp.setTelMac(telMac);
					tcIpTelManager.saveUpdateIpTel(njhwUsersExp);
				}
			}
			
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: listTcIpTels 
	* @description: 通过电话号码和mac地址查询ip电话
	* @author cjw
	* @return
	* @date 2013-3-23 下午09:19:42     
	* @throws
	 */
	public String listTcIpTels(){
		try {
			page.setPageSize(12);
			page = this.tcIpTelManager.getTcIpTels(page, this.tcIpTel);
			
			Long count = (Long)tcIpTelManager.findUnique("select count(*) from TcIpTel tit");
			Long countIpTel = (Long)tcIpTelManager.findUnique("select count(*) from TcIpTel tit where tit.telType = ?", "1");
			Long countFax = (Long)tcIpTelManager.findUnique("select count(*) from TcIpTel tit where tit.telType = ?", "2");
			Long countWebFax = (Long)tcIpTelManager.findUnique("select count(*) from TcIpTel tit where tit.telType = ?", "3");
			
			getRequest().setAttribute("count", count);
			getRequest().setAttribute("countIpTel", countIpTel);
			getRequest().setAttribute("countFax", countFax);
			getRequest().setAttribute("countWebFax", countWebFax);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: delete 
	* @description: 批量删除选中的ip电话
	* @author cjw
	* @return
	* @throws Exception
	* @date 2013-3-20 下午06:20:21     
	* @throws
	 */
	public String delete() throws Exception {
		try {
			String rtnVal = tcIpTelManager.deleteTcIpTels(_chk);
			
			if (StringUtil.isNotBlank(rtnVal)) {
				addActionMessage("号码为" + rtnVal + "的资源已经被分配，删除失败！");
			} else {
				addActionMessage("删除成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return SUCCESS;
	}
	
	/**
	 * @描述: 检查电话号码是否存在
	 * @作者：hj
	 * @日期：2013-08-16
	 * @return
	 */
	public String checkTelNum() {
		try {
			String telId = getParameter("telId");
			String telNum = getParameter("telNum");
			boolean res = tcIpTelManager.checkTelNum(telId, telNum);
			Struts2Util.getResponse().getWriter().print(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @描述: 检查电话MAC是否存在
	 * @作者：hj
	 * @日期：2013-08-16
	 * @return
	 */
	public String checkTelMac() {
		try {
			String telId = getParameter("telId");
			String telMac = getParameter("telMac");
			boolean res = tcIpTelManager.checkTelMac(telId, telMac);
			Struts2Util.getResponse().getWriter().print(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Long getTelId() {
		return telId;
	}
	public void setTelId(Long telId) {
		this.telId = telId;
	}
	public TcIpTelManager getTcIpTelManager() {
		return tcIpTelManager;
	}

	public void setTcIpTelManager(TcIpTelManager tcIpTelManager) {
		this.tcIpTelManager = tcIpTelManager;
	}

	public TcIpTel getTcIpTel() {
		return tcIpTel;
	}

	public void setTcIpTel(TcIpTel tcIpTel) {
		this.tcIpTel = tcIpTel;
	}

	public Page<TcIpTel> getPage() {
		return page;
	}

	public void setPage(Page<TcIpTel> page) {
		this.page = page;
	}
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public NjhwUsersExp getNjhwUsersExp() {
		return njhwUsersExp;
	}
	public void setNjhwUsersExp(NjhwUsersExp njhwUsersExp) {
		this.njhwUsersExp = njhwUsersExp;
	}
	
}
