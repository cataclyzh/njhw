package com.cosmosource.app.personnel.action;

import java.util.HashMap;
import java.util.List;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.personnel.service.AllotManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/** 
* @description: 分配车位给部门
* @author sqs
* @date 2013-03-19
*/ 
public class AllotAction extends BaseAction<Object> {
	
	// 定义全局变量
	private static final long serialVersionUID = 4227875753301925460L;
	// 定义实体变量
	private EmOrgRes emOrgRes = new EmOrgRes();
	// 定义分页变量
	@SuppressWarnings("unchecked")
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
	// 定义分页变量
	@SuppressWarnings("unchecked")
	private Page<HashMap> orgPage = new Page<HashMap>(50);// 默认每页50条记录
	// 定义注入对象
	private AllotManager allotManager;
	
	private String _chk[];//选中记录的ID数组
	private NjhwUsersExp njhwUsersExp;
	
	/** 
	* @title: init
	* @description: 初始化
	* @author zh
	* @date 2013-03-19 
	*/ 
	public String init() {
		orgPage = allotManager.queryOrgList(orgPage, null);
		getRequest().setAttribute("page", orgPage);
		return SUCCESS;
	}
	
	/** 
	* @title: list
	* @description: 分配按钮
	* @author sqs
	* @date 2013-03-19
	*/ 
	@SuppressWarnings("unchecked")
	public String list() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("orgId", getParameter("orgId"));
		map.put("eorType", EmOrgRes.EOR_PARKING);
		page = allotManager.queryCarNumList(page, map);
				
		Org org = (Org)allotManager.findById(Org.class, Long.parseLong(getParameter("orgId")));
		List<EmOrgRes> list = allotManager.findByHQL(" from EmOrgRes e where e.orgId = ? and e.eorType =? ", Long.parseLong(getParameter("orgId")),EmOrgRes.EOR_PARKING);
		if(list.size()>0){
			EmOrgRes emRes = list.get(0);
			getRequest().setAttribute("carPortsNum", emRes.getResName());
		}
		map.put("orgName", org.getName());
		getRequest().setAttribute("map", map);
		return SUCCESS;
	}
	
	/** 
	 * @title: saveCarPorts
	 * @description: 保存车位总数
	 * @author sqs
	 * @date 2013-03-19
	 */ 
	@SuppressWarnings("unchecked")
	public String saveCarPorts() throws Exception {
		try {
			String orgId = getParameter("orgId");
			String orgName = getParameter("orgName");
			String carPortsNum= getParameter("carPortsNum");
			
			if(orgId!=null){
				List<EmOrgRes> list = allotManager.findByHQL(" from EmOrgRes e where e.orgId = ? and e.eorType =? ", Long.parseLong(orgId),EmOrgRes.EOR_PARKING);
					if(list.size()>0){
					for(int i =0;i<list.size();i++){
						EmOrgRes emOrgRes = list.get(i);
							emOrgRes.setEorType(EmOrgRes.EOR_PARKING); //停车位
							emOrgRes.setOrgId(Long.parseLong(orgId));
							emOrgRes.setOrgName(orgName);
							emOrgRes.setResName(carPortsNum); //停车位总数
							emOrgRes.setPorFlag("1");         //有效标志
							emOrgRes.setInsertDate(DateUtil.getSysDate());
							allotManager.saveCarPorts(emOrgRes);
						  }
						}else{
							EmOrgRes emOrgRes = new EmOrgRes();
								
							emOrgRes.setEorType(EmOrgRes.EOR_PARKING); //停车位
							emOrgRes.setOrgId(Long.parseLong(orgId));
							emOrgRes.setOrgName(orgName);
							emOrgRes.setResName(carPortsNum); //停车位总数
							emOrgRes.setPorFlag("1");         //有效标志
							emOrgRes.setInsertDate(DateUtil.getSysDate());
							allotManager.saveCarPorts(emOrgRes);
							}
						getRequest().setAttribute("map",emOrgRes);
						getRequest().setAttribute("carPortsNum",carPortsNum);
					}
						addActionMessage("保存成功！");
						list(); //调用查询按钮的方法
			} catch (Exception e) {
				e.printStackTrace();
				addActionMessage("保存失败！");
			}
				return SUCCESS;
		}
	
	/**
	 * 批量删除
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			allotManager.deleteCarNum(_chk);
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
			emOrgRes = (EmOrgRes)allotManager.findById(EmOrgRes.class, Long.parseLong(getParameter("resId")));
			getRequest().setAttribute("emOrgRes", emOrgRes);
		}
		getRequest().setAttribute("orgId", getParameter("orgId"));
		Org org = (Org)this.allotManager.findById(Org.class, Long.parseLong(getParameter("orgId")));
		getRequest().setAttribute("orgName", org.getName());
		return SUCCESS;
	}
	
	/**
	 * 验证车牌是否已分配
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String loadCarNumInfo() throws Exception {
		JSONObject json = new JSONObject();
		List list = allotManager.loadCarNumInfo(getParameter("resName"));
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
	 * 提交前验证所有车牌是否存在已分配的
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String validAllCarNum() throws Exception {
		JSONObject json = new JSONObject();
		List<HashMap> list = allotManager.validAllCarNum(getParameter("resNames"));
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
			allotManager.save(emOrgRes);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		getRequest().setAttribute("orgList", allotManager.loadOrg());
		return SUCCESS;
	}
	
	/**
	 * 进入用户扩展页面gxh
	 * @return
	 */
	/*public String addPersonnelExinfo(){
		//String userid = this.getRequest().getParameter("userid");
		String userid = getSession().getAttribute(Constants.USER_ID).toString().trim();
		if(userid != null){
		
			njhwUsersExp=allotManager.getpsByid(Long.parseLong(userid));
			return SUCCESS;
		}else{
			return SUCCESS;	
			
		}	
	}*/
	/**
	 * 保存扩展信息gxh
	 * @return
	 */
/*	public String savePersonnelExinfo(){
		
		try{
			allotManager.saveEntity(njhwUsersExp);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		
		}
	     
		return SUCCESS;
	}*/
	
	
	
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

	@SuppressWarnings("unchecked")
	public Page<HashMap> getPage() {
		return page;
	}

	@SuppressWarnings("unchecked")
	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public AllotManager getAllotManager() {
		return allotManager;
	}

	public void setAllotManager(AllotManager allotManager) {
		this.allotManager = allotManager;
	}

	public NjhwUsersExp getNjhwUsersExp() {
		return njhwUsersExp;
	}

	public void setNjhwUsersExp(NjhwUsersExp njhwUsersExp) {
		this.njhwUsersExp = njhwUsersExp;
	}
	
	@SuppressWarnings("unchecked")
	public Page<HashMap> getOrgPage() {
		return orgPage;
	}

	@SuppressWarnings("unchecked")
	public void setOrgPage(Page<HashMap> orgPage) {
		this.orgPage = orgPage;
	}
}
