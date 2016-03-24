package com.cosmosource.app.caller.action;

import java.util.Map;

import com.cosmosource.app.caller.service.VisitorsTraceManager;
import com.cosmosource.app.entity.VmVisit;
import com.cosmosource.app.entity.VmVisitorinfo;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.ConvertUtils;

/**
 * 
* @description: 访客统计
* @author SQS
* @date 2013-3-17 下午06:25:49
 */
@SuppressWarnings("unchecked")
public class VisitorsTraceAction extends BaseAction<VmVisit> {

    private static final long serialVersionUID = 4227875753301925460L;
	private Long viId;
	private String vsId;
	private VmVisit vmVisit = new VmVisit();
	private VmVisitorinfo vmVisitorinfo;
	private Page<VmVisit> page = new Page<VmVisit>(Constants.PAGESIZE);//默认每页20条记录  访客事务
	private String beginTime;
	private String endTime;
	private String isLeave;
	private String userName;
	
	private String viName;
	
	public String getViName() {
		return viName;
	}

	public void setViName(String viName) {
		this.viName = viName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	* @title: init 
	* @description: 初始化
	* @author SQS
	* @return
	* @date 2013-3-19 上午11:11:33
	* @throws
	 */
	public String init() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: 
	* @description: 获取访客清单列表 
	* @author SQS
	* @return
	* @throws Exception 
	* @date 2013-3-17 下午06:27:26     
	* @throws
	 */
	public String queryVisitors() throws Exception {
		try {
			Map localMap = ConvertUtils.pojoToMap(vmVisit);
			page = visitorsTraceManager.getRegister(page, localMap,beginTime,endTime,isLeave);
			return SUCCESS;
		} catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	* @title: 
	* @description: 统计报表 
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:28:30     
	* @throws
	 */
	public String visitorsReport() throws Exception {
//		String cardId = getParameter("cardId");
//		String userName = getParameter("userName");
//		String vsFlag = getParameter("vsFlag");
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("cardId", cardId);
//		map.put("viName", userName);
//		map.put("vsFlag", vsFlag);
//		page = visitorsTraceManager.getRegister(page, map);
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: 
	* @description:显示访客详细信息   用于查看
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:28:51     
	* @throws
	 */
	public String lookVisitors() throws Exception {
		try {
			String id = getParameter("vsId");
			vmVisit = (VmVisit)visitorsTraceManager.findById(VmVisit.class,Long.valueOf(id));
			this.vmVisitorinfo = (VmVisitorinfo)this.visitorsTraceManager.findById(VmVisitorinfo.class, this.vmVisit.getViId());
			getRequest().setAttribute("vmVisitorinfo", vmVisitorinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "lookvi";
	}
	
	/**
	 * 
	 * @title: 
	 * @description:显示访问事务详细信息   用于查看
	 * @author SQS
	 * @return
	 * @throws Exception
	 * @date 2013-3-19 下午06:28:51     
	 * @throws
	 */
	public String visitorsTransaction() throws Exception {
		try {
			String id = getParameter("vsId");
			vmVisit = (VmVisit)visitorsTraceManager.findById(VmVisit.class,Long.valueOf(id));
			getRequest().setAttribute("vmVisit", vmVisit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "visitorsTransaction";
	}
	
	/**
	 * 
	* @title: 
	* @description: 条件查询
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:29:18     
	* @throws
	 */
	public String selectVmVisit() throws Exception{
		page = visitorsTraceManager.selectVmVisit(page, vmVisit);
		return "selectVmVisit";
	}
	
	/**
	 * 
	* @title:  
	* @description: 查看访客轨迹
	* @author SQS
	* @return
	* @throws Exception
	* @date 2013-3-17 下午06:29:41     
	* @throws
	 */
	public String showVideo() throws Exception {
		// 取得访客的信息
		long vsId = Long.parseLong(getParameter("vsId"));
		VmVisitorinfo visitorInfo = (VmVisitorinfo)this.visitorsTraceManager.findById(VmVisitorinfo.class, vsId);
		// 取得访客所经过的位置点ID
		//List<HashMap> pointList = visitorsTraceManager.loadVisitorPoints(visitorName);
		//String points = ;
		//for (HashMap map : pointList) points += map.get("") + ",";
		getRequest().setAttribute("visitorInfo", visitorInfo);
		getRequest().setAttribute("points", "1,2,3,4,5,6");
		return SUCCESS;
	}
	
	public Page<VmVisit> getPage() {
		return page;
	}
	public void setPage(Page<VmVisit> page) {
		this.page = page;
	}
	private VisitorsTraceManager visitorsTraceManager;
	
	@Override
	protected void prepareModel() throws Exception {
	}
	public VmVisit getModel() {
		return vmVisit;
	}
	
	public VmVisitorinfo getVmVisitorinfo() {
		return vmVisitorinfo;
	}
	public void setVmVisitorinfo(VmVisitorinfo vmVisitorinfo) {
		this.vmVisitorinfo = vmVisitorinfo;
	}
	public VisitorsTraceManager getVisitorsTraceManager() {
		return visitorsTraceManager;
	}
	public void setVisitorsTraceManager(VisitorsTraceManager visitorsTraceManager) {
		this.visitorsTraceManager = visitorsTraceManager;
	}
	public Long getViId() {
		return viId;
	}
	public void setViId(Long viId) {
		this.viId = viId;
	}
	public String getVsId() {
		return vsId;
	}
	public void setVsId(String vsId) {
		this.vsId = vsId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsLeave() {
		return isLeave;
	}
	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

}
