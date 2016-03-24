package com.cosmosource.app.personnel.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.personnel.service.PersonnelOrgAssignManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * 
* @description: 获得机构
* @author SQS
* @date 2013-3-17 下午06:25:49
 */
@SuppressWarnings({ "unchecked", "serial" })
public class PersonnelOrgAssignAction extends BaseAction {
    public static final int PHONE_PAGE_NO = 100;
	private Org org = new Org();
	private TcIpTel tcIpTel = new TcIpTel();
	private EmOrgRes emOrgRes = new EmOrgRes();
	private EmOrgRes emOrgResinfo = new EmOrgRes();
	private PersonnelOrgAssignManager personnelOrgAssignManager;
	private Page page = new Page(Constants.PAGESIZE);
	private Page orgPage = new Page(50);
	private String _chk[];
	
	@Override
	protected void prepareModel() throws Exception {
	}
	public Org getModel() {
		return org;
	}
	
	/**
	 * 
	* @title: 
	* @description: 获取机构清单列表 --分配IP电话时用
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	public String initOrgan() throws Exception {
		String name = getParameter("name");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("LEVELNUM_2", Org.LEVELNUM_2);
		page.setPageSize(1000);
		page = personnelOrgAssignManager.initOrgan(page, map);
		
		// getRequest().setAttribute("page", orgPage);
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: init 
	* @description: 初始化页面
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
    public String initDoorBar(){
    	return SUCCESS;
    }
    
    /**
	 * 
	* @title: 
	* @description: 分配IP电话
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String inputIp() throws Exception {
		getRequest().setAttribute("orgList", personnelOrgAssignManager.loadOrg());
		getRequest().setAttribute("ipPhoneList", personnelOrgAssignManager.loadPhone());
		return SUCCESS;
	}
    
	/** 
	* @title: list
	* @description: 查询Ip电话分配情况信息 查询按钮
	* @author SQS
	* @date 2013-03-19
	*/ 
	public String ipTelllist() throws Exception {
		getRequest().setAttribute("orgList", personnelOrgAssignManager.loadOrg());
		return SUCCESS;
	}
	
	/** 
	* @title: list
	* @description:  查询按钮
	* @author SQS
	* @date 2013-03-19
	*/ 
	public String ipAssignlist() throws Exception {
		HashMap<String, Long> map = new HashMap<String, Long>();
		map.put("orgId", Long.parseLong(getParameter("orgId")));
		map.put("EOR_TYPE_PHONE",Long.parseLong(EmOrgRes.EOR_TYPE_PHONE));
		page = personnelOrgAssignManager.queryIpTellNumList(page, map);
		getRequest().setAttribute("map", map);
		//getRequest().setAttribute("orgList", personnelOrgAssignManager.loadOrg());
		return SUCCESS;
	}
	
	/** 
	 * @title: list
	 * @description: 查询Ip电话分配信息
	 * @author SQS 1
	 * @date 2013-03-19
	 */ 
	public String ipTellAssign() throws Exception {
		String orgId = getParameter("orgId");
		
		int ipCount = 0;
		int faxCount = 0;
		int webFaxCount = 0;
		int totalCount = 0;
		
		
		HashMap<String, String> telMap = new HashMap<String, String>();
		telMap.put("orgId", orgId);
		telMap.put("resStatus", "1");
		
		telMap.put("resType", "1");
		List l1 = personnelOrgAssignManager.loadIpPhone(telMap);
		if (l1 != null && l1.size() > 0) {
			ipCount = l1.size();
		}
		
		telMap.put("resType", "2");
		List l2 = personnelOrgAssignManager.loadIpPhone(telMap);
		if (l2 != null && l2.size() > 0) {
			faxCount = l2.size();
		}
		
		telMap.put("resType", "3");
		List l3 = personnelOrgAssignManager.loadIpPhone(telMap);
		if (l3 != null && l3.size() > 0) {
			webFaxCount = l3.size();
		}
		
		totalCount = ipCount + faxCount + webFaxCount;
		
		this.getRequest().setAttribute("ipCount", ipCount);
		this.getRequest().setAttribute("faxCount", faxCount);
		this.getRequest().setAttribute("webFaxCount", webFaxCount);
		this.getRequest().setAttribute("totalCount", totalCount);
		
		return SUCCESS;
	}
	
	
	/** 
	 * @title: list
	 * @description: 查询Ip电话分配信息
	 * @author SQS 1
	 * @date 2013-03-19
	 */ 
	public String ipTellAssignForAdd() throws Exception {
		String orgId = getParameter("orgId");
		this.getRequest().setAttribute("orgId",orgId);
		
		int ipCount = 0;
		int faxCount = 0;
		int webFaxCount = 0;
		int totalCount = 0;
		
		
		HashMap<String, String> telMap = new HashMap<String, String>();
		telMap.put("orgId", orgId);
		telMap.put("resStatus", "2");
		
		telMap.put("resType", "1");
		List l1 = personnelOrgAssignManager.loadIpPhone(telMap);
		if (l1 != null && l1.size() > 0) {
			ipCount = l1.size();
		}
		
		telMap.put("resType", "2");
		List l2 = personnelOrgAssignManager.loadIpPhone(telMap);
		if (l2 != null && l2.size() > 0) {
			faxCount = l2.size();
		}
		
		telMap.put("resType", "3");
		List l3 = personnelOrgAssignManager.loadIpPhone(telMap);
		if (l3 != null && l3.size() > 0) {
			webFaxCount = l3.size();
		}
		
		totalCount = ipCount + faxCount + webFaxCount;
		
		this.getRequest().setAttribute("ipCount", ipCount);
		this.getRequest().setAttribute("faxCount", faxCount);
		this.getRequest().setAttribute("webFaxCount", webFaxCount);
		this.getRequest().setAttribute("totalCount", totalCount);
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: ajaxRefreshTell 
	* @description: 异步刷新电话号码
	* @author herb 2
	* @return
	* @throws Exception
	* @date Apr 4, 2013 3:29:14 PM     
	* @throws
	 */
	public String ajaxRefreshTell() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String orgId = getParameter("orgId");
	    String telNum = getParameter("telNum").trim();
	    String resType =getParameter("resType");
	    String resStatus =getParameter("resStatus");
	    map.put("orgId", orgId);
		map.put("telNum", telNum);
		map.put("resType", resType);
		map.put("resStatus", resStatus);
		List result = personnelOrgAssignManager.loadIpPhone(map);
		
		this.getRequest().setAttribute("result",result);
		this.getRequest().setAttribute("orgId",orgId);
		this.getRequest().setAttribute("resType", resType);
		
		return SUCCESS;
	}


/** 
	 * @title: list
	 * @description: 查询Ip电话分配信息
	 * @author SQS 1
	 * @date 2013-03-19
	 */ 
	public String ipTellAssignAll() throws Exception {
		
		// ip电话总数
		int ipTotalCount = listCount("1","");
		
		// 已经分配的ip电话数
		int ipDisCount= listCount("1","10");
		 
		// 传真总数
		int faxTotalCount = listCount("2", "");
		
		// 已经传真分配的数目
		int faxDisCount = listCount("2", "10");
		
		// 网络传真总数
		int webFaxTotalCount = listCount("3", "");
		
		// 已经网络传真分配的数目
		int webFaxDisCount = listCount("3", "10");
		
		HashMap<String,Integer> countMap = new HashMap<String, Integer>();
		countMap.put("ipTotalCount", ipTotalCount);
		countMap.put("ipDisCount", ipDisCount);
		countMap.put("faxTotalCount", faxTotalCount);
		countMap.put("faxDisCount", faxDisCount);
		countMap.put("webFaxTotalCount", webFaxTotalCount);
		countMap.put("webFaxDisCount", webFaxDisCount);
		
		this.getRequest().setAttribute("countMap",countMap);
		
		return SUCCESS;
	}
	
	
	private int listCount(String resType,String status)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orgId", "70");
		map.put("resType", resType);
		if (StringUtil.isNotEmpty(status))
		{
			map.put("resStatus", status);
		}
		List resultCount = personnelOrgAssignManager.loadIpPhoneAll(map);
		int ipTotalCount = resultCount.size();
		return ipTotalCount;
	}
	
		/** 
		* @title: ajaxRefreshTell 
		* @description: 异步刷新电话号码
		* @author herb 2
		* @return
		* @throws Exception
		* @date Apr 4, 2013 3:29:14 PM     
		* @throws
		 */
		public String ajaxRefreshTellAll() throws Exception {
			HashMap<String, String> map = new HashMap<String, String>();
			String orgId = getParameter("orgId");
		    String telNum = getParameter("telNum").trim();
		    String resType =getParameter("resType");
		    String resStatus = getParameter("resStatus");
		    int pageSize = PHONE_PAGE_NO;
		    if (!StringUtil.isEmpty(getParameter("pageSize")))
		    {
		    	 pageSize = Integer.parseInt(getParameter("pageSize"));	
		    }
		    // 为了达到sql公用  default 70
		    map.put("orgId", "70");
			map.put("telNum", telNum);
			map.put("resType", resType);
			map.put("resStatus", resStatus);
			page.setPageSize(pageSize);
			String p1 = this.getRequest().getParameter("pageNo");
			page.setPageNo(Integer.valueOf(p1));
			page = personnelOrgAssignManager.loadIpPhone(page,map);
			this.getRequest().setAttribute("orgId",orgId);
			this.getRequest().setAttribute("resType", resType);
			return SUCCESS;
		}
	
	
	/**
	 * 
	* @title: 
	* @description: 保存IP电话到机构
	* @author SQS 3
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String allotIpSave() {
		String orgId = null;
		try {
			orgId = getParameter("orgId");
			String ids = getParameter("ipTellids");
			personnelOrgAssignManager.saveAllotIpTel(ids,orgId);
			Struts2Util.renderText("success");
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @title: 
	* @description: 删除已经分配的ip电话
	* @author SQS 3
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String allotIpSaveDelete() {
		String orgId = null;
		try {
			orgId = getParameter("orgId");
			String ids = getParameter("ipTellids");
			String failName = personnelOrgAssignManager.saveDeleteAllotIpTel(ids, orgId);
			
			if (StringUtil.isNotBlank(failName)) {
				Struts2Util.renderText(failName);
			} else {
				Struts2Util.renderText("success");
			}
		} catch (Exception e) {
			Struts2Util.renderText("error");
			e.printStackTrace();
		}
		return null;
	}
    
	/**
	 * 
	* @title: 
	* @description: 分配IP电话
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	public String assignPhone(){
		String orgId = getParameter("orgId");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orgId", orgId);
		map.put("EOR_TYPE_PHONE", EmOrgRes.EOR_TYPE_PHONE);
		page = personnelOrgAssignManager.getPhone(page, map);
		getRequest().setAttribute("orgId", orgId);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: 
	 * @description: 保存门禁到机构
	 * @author SQS
	 * @return
	 * @throws Exception
	 * @date 2013-3-17 下午06:27:26     
	 * @throws  
	 */
	public String doorCardSave(){
		String nodeId = getParameter("nodeId");
		String orgId = getParameter("orgId");
		Org org = (Org) personnelOrgAssignManager.findById(Org.class, Long.valueOf(orgId));
		//org和objtank放在 em_org_res
		try {
			// 删除
			this.personnelOrgAssignManager.deleteEmOrgResByOrgId(Long.valueOf(orgId), nodeId);
				
			String nodeIds[] = nodeId.split(",");
				for(int i=0; i<nodeIds.length; i++){
					EmOrgRes er = new EmOrgRes();
					er.setOrgId(Long.parseLong(orgId));
					er.setOrgName(org.getName());
					er.setPorFlag("1");
					er.setInsertDate(DateUtil.getSysDate());
					er.setEorType(EmOrgRes.EOR_TYPE_GLASS);
					if(nodeId == null || "".equals(nodeId)){
						er.setResId(null);
					}else{
						er.setResId(Long.valueOf(nodeIds[i]));
						this.personnelOrgAssignManager.save(er);
					}
					setIsSuc("true");
				}
			} catch (Exception e) {
				setIsSuc("false");
				e.printStackTrace();
			}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: 
	* @description: 获取机构清单列表 
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
//	public String queryOrganList() throws Exception {
//		String name = getParameter("name");
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("name", name);
//		page = personnelOrgAssignManager.getOrgan(page, map);
//		return SUCCESS;
//	}
	
	/**
	 * 
	* @title: 
	* @description: 获取机构清单列表 --分配门禁时用
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	public String queryOrgan() throws Exception {
		String name = getParameter("name");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("LEVELNUM_2", Org.LEVELNUM_2);
		orgPage = personnelOrgAssignManager.getOrgan(orgPage, map);
		getRequest().setAttribute("page", orgPage);
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: 
	* @description: 获取门禁清单列表 --分配门禁时用(点分配按钮)
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	public String assignDoorCard(){
		String orgId = getParameter("orgId");
		page.setPageSize(10);
		HashMap map = new HashMap();
		map.put("orgId", orgId);
		map.put("EXT_RES_TYPE_2",Objtank.EXT_RES_TYPE_2);
		this.getRequest().setAttribute("orgId", orgId);
		page = personnelOrgAssignManager.getDoorCard(page, map);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @title: 
	 * @description: 获取门禁清单列表 --分配门禁时用(点下一页按钮)
	 * @author SQS
	 * @return
	 * @throws Exception
	 * @date 2013-3-17 下午06:27:26     
	 * @throws
	 */
	public String getPageList(){
		String orgId = getRequest().getParameter("orgId");
		page.setPageSize(10);
		HashMap map = new HashMap();
		map.put("orgId", orgId);
		map.put("EXT_RES_TYPE_2",Objtank.EXT_RES_TYPE_2);
		page = personnelOrgAssignManager.getPageList(page, map);
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: 
	* @description: 批量删除选中的IP电话
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	public String deleteIpTell() throws Exception {
		try {
			personnelOrgAssignManager.deleteIpTellNum(_chk);
			addActionMessage("删除IP电话分配信息成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除IP电话分配信息失败");
		}
		return RELOAD;
	}
	
	public Page<HashMap> getPage() {
		return page;
	}
	public void setPage(Page<HashMap> page) {
		this.page = page;
	}
	public PersonnelOrgAssignManager getPersonnelOrgAssignManager() {
		return personnelOrgAssignManager;
	}
	public void setPersonnelOrgAssignManager(
			PersonnelOrgAssignManager personnelOrgAssignManager) {
		this.personnelOrgAssignManager = personnelOrgAssignManager;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public EmOrgRes getEmOrgRes() {
		return emOrgRes;
	}
	public void setEmOrgRes(EmOrgRes emOrgRes) {
		this.emOrgRes = emOrgRes;
	}
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public EmOrgRes getEmOrgResinfo() {
		return emOrgResinfo;
	}
	public void setEmOrgResinfo(EmOrgRes emOrgResinfo) {
		this.emOrgResinfo = emOrgResinfo;
	}
	public TcIpTel getTcIpTel() {
		return tcIpTel;
	}
	public void setTcIpTel(TcIpTel tcIpTel) {
		this.tcIpTel = tcIpTel;
	}

	public Page getOrgPage() {
		return orgPage;
	}
	public void setOrgPage(Page orgPage) {
		this.orgPage = orgPage;
	}
}
