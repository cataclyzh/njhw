package com.cosmosource.app.integrateservice.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.TCommonSmsBox;
import com.cosmosource.app.integrateservice.service.SendSMSManager;
import com.cosmosource.app.personnel.service.PersonRegOutManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings({ "serial", "unchecked" })
public class SendSMSAction extends BaseAction<TCommonSmsBox> {
	
	private Page<TCommonSmsBox> page = new Page<TCommonSmsBox>(Constants.PAGESIZE);//默认每页20条记录
	private TCommonSmsBox tCommonSmsBox;
	private SendSMSManager sendSMSManager;			// 短信manager
	private PersonRegOutManager personROManager;
	
	private String _chk[];	//选中记录的ID数组	
	/**
	* @title: init 
	* @description: 初始化
	* @author zh
	* @return
	* @date 2013-5-24 上午11:11:33
	* @throws
	 */
	public String init(){
		return SUCCESS;
	}
	
	/**
	* @title: init 
	* @description: 初始化
	* @author zh
	* @return
	* @date 2013-5-24 上午11:11:33
	* @throws
	 */
	public String list(){
		HashMap parMap = new HashMap();
		parMap.put("receiverName", getParameter("receiver"));
		parMap.put("receiverMobile", getParameter("receivermobile"));
		parMap.put("senderId", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		page = this.sendSMSManager.querySendSMS(page, parMap);
		return SUCCESS;
	}
	
	/**
	* @title: inputSMS 
	* @description: 短信编辑页面
	* @author zh
	* @return
	* @date 2013-5-16 下午05:20:40     
	* @throws
	 */
    public String inputSMS(){
    	String info="", name="", type="";
		try {
			if(getParameter("info") != null && StringUtil.isNotEmpty(getParameter("info")))
				info = new String(getParameter("info").getBytes("ISO-8859-1"),"utf-8");
			
			if(getParameter("name") != null && StringUtil.isNotEmpty(getParameter("name")))
				name = new String(getParameter("name").getBytes("ISO-8859-1"),"utf-8");
			
			if(getParameter("type") != null && StringUtil.isNotEmpty(getParameter("type")))
				type = getParameter("type");
			
			getRequest().setAttribute("info", info);
			getRequest().setAttribute("name", name);
			getRequest().setAttribute("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return SUCCESS;
    }
    
    /**
    * @title: showSMS
    * @description: 查看
    * @author zh
    * @return
    * @date 2013-5-24 下午01:56:08     
    * @throws
     */
    public String showSMS(){
    	TCommonSmsBox smsBox = (TCommonSmsBox) this.sendSMSManager.findById(TCommonSmsBox.class, Long.parseLong(getParameter("smsid")));
    	getRequest().setAttribute("smsBox", smsBox);
    	return SUCCESS;
    }
	
	/**
	* @title: saveSendSms 
	* @description: 发送
	* @author zh
	* @return
	* @date 2013-5-24 下午01:56:08 
	* @throws
	 */
	public String saveSendSms() throws JSONException{
//		String addressreceiverids = getParameter("addressreceiverids");
//		String addressreceivernames = getParameter("addressreceivernames");
//		String addressreceivermobiles = getParameter("addressreceivermobiles");
//		String unitreceiverids = getParameter("unitreceiverids");
//		String unitreceivernames = getParameter("unitreceivernames");
//		String unitreceivermobiles = getParameter("unitreceivermobiles");
		
		String unitReceiverInfo = getParameter("unitReceiverInfo");
		String addressReceiverInfo = getParameter("addressReceiverInfo");
		String foreignReceiverInfo = getParameter("foreignReceiverInfo");
		
		String [] ReceiverArray = new String[]{unitReceiverInfo, addressReceiverInfo, foreignReceiverInfo};
		String content = getParameter("content");
		String isSave = getParameter("isSave");
		
		JSONObject json = new JSONObject();
		try {
			String rst = this.sendSMSManager.save(ReceiverArray, content, isSave);
			//String rst = "海军,13898989999";	// testData
			json.put("rst", rst);
			json.put("status", "success");
		} catch (Exception e) {
			json.put("status", "fail");
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	* @Title: deleteSMS 
	* @Description: 删除发件箱短信
	* @author SQS
	* @date 2013-5-17 上午11:11:21 
	* @return String 
	 */
	public String deleteSMS(){
		try {
			sendSMSManager.delete(_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return RELOAD;
	}
	
	/** 
	* @title: showUnitTree
	* @description: 初始化单位树
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String showUnitTree() {
		String ids = getParameter("unitUserids") != null ? getParameter("unitUserids").toString() : "";
		getRequest().setAttribute("ids", ids);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Title: getUnitTreeData 
	 * @Description: 取得单位树的数据以xml的形式传送到页面
	 * @author zh
	 * @return String 
	 * @throws
	 */
	public String getUnitTreeData() throws Exception {
		String unitids = getParameter("ids");
		System.out.println("unitids:"+unitids);
		String gid  = null;
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) gid = list.get(0).get("TOP_ORG_ID") != null ? list.get(0).get("TOP_ORG_ID").toString() : null;
		String type = getParameter("type");
		Struts2Util.renderXml( sendSMSManager.getUnitTreeData(gid, unitids, getContextPath(), type), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: showUnitTree
	* @description: 初始化通讯录树
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String showAddressTree() {
		String ids = getParameter("addressUserIds") != null ? getParameter("addressUserIds").toString() : "";
		getRequest().setAttribute("ids", ids);
		return SUCCESS;
	}
	
	/**
	 * 取得通讯录树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String getAddressTreeData() throws Exception {
		String ids = getParameter("ids");
		long userid = Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		try {
			Struts2Util.renderXml( this.sendSMSManager.getAddressSMSTree(userid,ids), "encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void prepareModel() throws Exception {}
	@Override
	public TCommonSmsBox getModel() {
		return tCommonSmsBox;
	}
	public SendSMSManager getSendSMSManager() {
		return sendSMSManager;
	}
	public void setSendSMSManager(SendSMSManager sendSMSManager) {
		this.sendSMSManager = sendSMSManager;
	}
	public TCommonSmsBox gettCommonSmsBox() {
		return tCommonSmsBox;
	}
	public void settCommonSmsBox(TCommonSmsBox tCommonSmsBox) {
		this.tCommonSmsBox = tCommonSmsBox;
	}
	public Page<TCommonSmsBox> getPage() {
		return page;
	}
	public void setPage(Page<TCommonSmsBox> page) {
		this.page = page;
	}
	public String[] get_chk() {
		return _chk;
	}
	public void set_chk(String[] chk) {
		_chk = chk;
	}
	public PersonRegOutManager getPersonROManager() {
		return personROManager;
	}
	public void setPersonROManager(PersonRegOutManager personROManager) {
		this.personROManager = personROManager;
	}
}
