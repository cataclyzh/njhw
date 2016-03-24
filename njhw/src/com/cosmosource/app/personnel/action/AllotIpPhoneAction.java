package com.cosmosource.app.personnel.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.personnel.service.AllotIpPhoneManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/** 
* @description: 分配Ip电话号码给部门
* @author zh
* @date 2013-03-23
*/ 
public class AllotIpPhoneAction extends BaseAction<Object> {
	
	// 定义全局变量
	private static final long serialVersionUID = 4227875753301925460L;
	// 定义实体变量
	private EmOrgRes emOrgRes = new EmOrgRes();
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	// 定义分页变量
	private Page<HashMap> orgPage = new Page<HashMap>(50);// 默认每页50条记录
	// 定义注入对象
	private AllotIpPhoneManager allotIpPhoneManager;
	
	private String _chk[];//选中记录的ID数组
	
	/** 
	* @title: init
	* @description: 初始化
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String init() {
		orgPage = allotIpPhoneManager.queryOrgList(orgPage, null);
		getRequest().setAttribute("page", orgPage);
		return SUCCESS;
	}
	
	/** 
	* @title: list
	* @description: 查询Ip电话号码分配信息
	* @author zh
	* @date 2013-03-21
	*/ 
	public String list() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orgId", getParameter("orgId"));
		map.put("resName", getParameter("resName"));
		page = allotIpPhoneManager.queryCarNumList(page, map);
		
		Org org = (Org)this.allotIpPhoneManager.findById(Org.class, Long.parseLong(getParameter("orgId")));
		map.put("orgName", org.getName());
		getRequest().setAttribute("map", map);
		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			allotIpPhoneManager.deleteCarNum(_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return RELOAD;
	}
	
	/**
	 * 显示用于编辑
	 * @return 
	 * @throws Exception
	 */
	public String input() throws Exception {
		if (!"".equals(getParameter("resId")) && getParameter("resId") != null) {
			emOrgRes = (EmOrgRes)allotIpPhoneManager.findById(EmOrgRes.class, Long.parseLong(getParameter("resId")));
			getRequest().setAttribute("emOrgRes", emOrgRes);
		}
		getRequest().setAttribute("orgId", getParameter("orgId"));
		Org org = (Org)this.allotIpPhoneManager.findById(Org.class, Long.parseLong(getParameter("orgId")));
		getRequest().setAttribute("orgName", org.getName());
		return SUCCESS;
	}
	
	/**
	 * 验证Ip电话号码是否已分配
	 * @return 
	 * @throws Exception
	 */
	public String loadIpPhoneNumInfo() throws Exception {
		JSONObject json = new JSONObject();
		List list = allotIpPhoneManager.loadIpPhoneNumInfo(getParameter("resName"));
		try {
			if (list != null &&  list.size() > 0) {
				System.out.println(list.get(0));
				json.put("orgName", list.get(0));
				json.put("status", 1);
			} else {
				json.put("status", 0);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 提交前验证所有Ip电话号码是否存在已分配的
	 * @return 
	 * @throws Exception
	 */
	public String validAllIpPhoneNum() throws Exception {
		JSONObject json = new JSONObject();
		List<HashMap> list = allotIpPhoneManager.validAllIpPhoneNum(getParameter("resNames"));
		try {
			if (list != null &&  list.size() > 0) {
				String carNums = "";
				for (HashMap map : list) {
					carNums += map.get("RES_NAME").toString() + ",";
				}
				json.put("status", 1);
				json.put("carNums", carNums);
			} else {
				json.put("status", 0);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/** 
	* @title: save
	* @description: 保存
	* @author zh
	* @date 2013-03-23
	*/ 
	public String save() {
		try {
			allotIpPhoneManager.save(emOrgRes);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		getRequest().setAttribute("orgList", allotIpPhoneManager.loadOrg());
		return SUCCESS;
	}
	
	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}
	
	@Override
	public EmOrgRes getModel() {
		// TODO Auto-generated method stub
		return emOrgRes;
	}
	
	@Override
	protected void prepareModel() throws Exception {
	}

	public EmOrgRes getEmOrgRes() {
		return emOrgRes;
	}

	public void setEmOrgRes(EmOrgRes emOrgRes) {
		this.emOrgRes = emOrgRes;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public AllotIpPhoneManager getAllotIpPhoneManager() {
		return allotIpPhoneManager;
	}

	public void setAllotIpPhoneManager(AllotIpPhoneManager allotIpPhoneManager) {
		this.allotIpPhoneManager = allotIpPhoneManager;
	}
	
	public Page<HashMap> getOrgPage() {
		return orgPage;
	}

	public void setOrgPage(Page<HashMap> orgPage) {
		this.orgPage = orgPage;
	}
}
