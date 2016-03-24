/**
\* <p>文件名: OrgMgrAction.java</p>
* <p>描述：机构管理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.app.personnel.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.app.ldap.service.LDAPService;
import com.cosmosource.app.personnel.service.OrgMgrManager;
import com.cosmosource.app.personnel.service.PersonRegOutManager;
import com.cosmosource.app.personnel_e.service.E4PersonnelManager;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

/**
* @类描述: 机构管理Action,用于机构的CRUD，机构树的显示
* @作者： WXJ
*/
public class OrgMgrAction extends BaseAction<Org> {

	private static final long serialVersionUID = 8497952646128136433L;
	private OrgMgrManager orgMgrManager;
	//-- 页面属性 --//
	private Org entity = new Org();
	private Page<Org> page = new Page<Org>(Constants.PAGESIZE);//默认每页20条记录
	private Page<HashMap<String,String>> pageLimit = new Page<HashMap<String,String>>(Constants.PAGESIZE);//默认每页20条记录
	private PersonRegOutManager personROManager;
	private String _chk[];//选中记录的ID数组
	private String parentOrgtype;//父机构的类型
	private String parentOrgname;//上级机构名称
	private String orgtypename;//机构类型名称
	
	private String roomId;//房间id
	private String dtype;//保存类型：
	private String checkedIds;
	private String displayName;
	private String idStr;
	private DoorControlToAppService doorControlToAppService;
	
	private E4PersonnelManager e4PersonnelManager;
	
	public void setE4PersonnelManager(E4PersonnelManager e4PersonnelManager) {
		this.e4PersonnelManager = e4PersonnelManager;
	}
	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}
	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}
	public String getDtype() {
		return dtype;
	}
	
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getCheckedIds() {
		return checkedIds;
	}
	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}	
	
	//-- ModelDriven 与 Preparable函数 --//
	public Org getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		if (entity.getOrgId() != null) {
			entity = (Org)orgMgrManager.findById(Org.class, entity.getOrgId());			
		} else {
			entity = new Org();
		}
	}

	/**
	 * 
	* @Title: list 
	* @Description: 组织机构（一级单位）管理list
	* @author WXJ
	* @date 2013-5-3 下午01:46:05 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String list() throws Exception {
		if (entity.getLevelNum()!=null && !"".equals(entity.getLevelNum()) && Integer.valueOf(entity.getLevelNum())>2) {
			return INPUT;
		}
		page.setPageSize(14);
		page = orgMgrManager.queryOrgs(page, entity);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: chechinList 
	* @Description: 组织机构-人员登记
	* @author WXJ
	* @date 2013-5-10 下午08:12:27 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String chechinList() throws Exception {		
		page = orgMgrManager.queryOrgs(page, entity);
		return SUCCESS;
	}
	
	/**
	 * 显示机构详细信息用于查看或是修改
	 * @return 
	 * @throws Exception
	 */
	
	public String input() throws Exception {
		try {	
			Org org = (Org)orgMgrManager.findById(Org.class,entity.getPId());		
			this.setParentOrgname(org.getName());
		} catch(Exception e){
			e.printStackTrace();
		}
		return INPUT;
	}

	/**
	 * 保存信息
	 * @return 
	 * @throws Exception
	 */
	public String save() throws Exception {
		try {
			//保存机构信息
			orgMgrManager.saveOrg(entity);
			
		    setParentOrgname(orgMgrManager.parentOrgName(entity.getPId()));	
			//addActionMessage("保存机构成功");
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//addActionMessage("保存机构失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	public String rzdwSave() throws Exception {
		try {
			String name = entity.getName();
			String orgId = getRequest().getParameter("orgId");
			if(name != null && name != ""){
				Map param = new HashMap();
				param.put("name", name);
				if(orgId != null)
					param.put("orgId", orgId);
				List<Map> list = orgMgrManager.findListBySql("PersonnelSQL.queryOrgCountByName", param);
				if(Integer.parseInt(list.get(0).get("CUN").toString())!=0){
					setIsSuc("error");
					return SUCCESS;
				}
			}
			//保存机构信息
			orgMgrManager.saveOrg(entity);
			
		    setParentOrgname(orgMgrManager.parentOrgName(entity.getPId()));	
			//addActionMessage("保存机构成功");
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//addActionMessage("保存机构失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}

	public 	String orgIsRepate()
	{    
		String pId = Struts2Util.getParameter("PId");
		String name = Struts2Util.getParameter("name"); 
		String orgId =Struts2Util.getParameter("orgId");
		JSONObject json = new JSONObject();
		List<Org> list = orgMgrManager.findByHQL("select t from Org t where t.PId = ? and t.name =? ", Long.parseLong(pId),name);
		// 修改
		if (StringUtil.isNotEmpty(orgId))
		{   

			if (null != list && list.size() > 0)
			{
				for (int i = 0; i < list.size(); i++)
				{
					if (!orgId.equals(list.get(i).getOrgId()))
					{
						try
						{
							json.put("isRepate", "Y");
						}
						catch (JSONException e)
						{
							logger.error("校验部门是否重复出错："+e.toString());
							e.printStackTrace();
						}
						Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
						return null;
					}
				}
			}
		}
		// 新增
		else
		{
			
			if (null != list && list.size() > 0)
			{
				try
				{
					json.put("isRepate", "Y");
				}
				catch (JSONException e)
				{   
					logger.error("校验部门是否重复出错："+e.toString());
					e.printStackTrace();
				}
			}
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		}
		
		return null;
	}
	
	/**
	 * 批量删除选中的机构
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			String[] ids = idStr.split(",");
			//Long valOrg = orgMgrManager.deleteOrgs(_chk);
			Long valOrg = orgMgrManager.deleteOrgs(ids);
			if(valOrg.longValue()>0){
				//addActionMessage("请删除机构的关联信息");
				
				getResponse().getWriter().write("请删除机构的关联信息");
			}else{
				//addActionMessage("删除机构成功");
				getResponse().getWriter().write("删除机构成功");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//addActionMessage("删除机构失败");
			getResponse().getWriter().write("删除机构失败");
			
		}
		return null;
	}
	
	/**
	 * 入驻单位删除
	 * @return
	 */
	public String rzdwDelete(){
		try {
			String[] ids = idStr.split(",");
			//Long valOrg = orgMgrManager.deleteOrgs(_chk);
			Long valOrg = orgMgrManager.deleteOrgs(ids);
			if(valOrg.longValue()>0){
				//addActionMessage("请删除机构的关联信息");
				
				Struts2Util.renderText("请删除机构的关联信息");
			}else{
				//addActionMessage("删除机构成功");
				Struts2Util.renderText("删除机构成功");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//addActionMessage("删除机构失败");
			Struts2Util.renderText("删除机构失败", "");
			
		}
		return null;
	}
	
	/**
	 * 删除单个组织机构
	* @title: deleteOneOrg 
	* @description: TODO
	* @author gxh
	* @return
	* @throws Exception
	* @date 2013-5-10 下午09:11:18     
	* @throws
	 */
	public String deleteOneOrg() throws Exception {
		JSONObject json = new JSONObject();
		String _chk1=Struts2Util.getParameter("_chk");
		String[] chks = {_chk1};
		try {
			Long valOrg = orgMgrManager.deleteOrgs(chks);
			if(valOrg.longValue()>0){
				json.put("message", 1);
			}else{
				json.put("message", 0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	public String userDelete() throws JSONException{
		JSONObject json = new JSONObject();
		String _chk1=Struts2Util.getParameter("_chk");
		try {
			boolean isAdmin = orgMgrManager.isAdmin(Long.parseLong(_chk1));
			
			if (isAdmin) {
				json.put("message", 1);
			} else {
				String loginId =Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
				boolean res=orgMgrManager.deleteUser(Long.parseLong(_chk1),loginId,doorControlToAppService);
				if(res){
					json.put("message", 0);
				}else {
					json.put("message", 2);
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();			
		}
		
		
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 
	* @Title: orgTree 
	* @Description: 组织机构（一级单位）管理
	* @author WXJ
	* @date 2013-4-26 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTree() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTreeData 
	* @Description: 组织机构（一级单位）管理
	* @author WXJ
	* @date 2013-4-26 上午11:11:55 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeData() throws Exception {
		
		String type = getParameter("type");
		String id = getParameter("id");
		
		String xmlString = orgMgrManager.getOrgTreeData(id, getContextPath(),type);
		Struts2Util.renderXml(xmlString,"encoding:UTF-8", "no-cache:true");

		return null;
	}
	

	/**
	* @Title: orgTreeData 
	* @Description: 组织机构（一级单位）管理
	* @author WXJ
	* @date 2013-4-26 上午11:11:55 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeDataAdmin() throws Exception {
		String type = getParameter("type");
		String id = getParameter("id");
		String xmlString = orgMgrManager.getOrgTreeDataAdmin(id, getContextPath(),type);
		Struts2Util.renderXml(xmlString,"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	
	/**
	 * 
	* @Title: orgTreeUserListFrame 
	* @Description: 组织机构-人员信息查询
	* @author WXJ
	* @date 2013-5-6 下午02:24:56 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeUserListFrame() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTree 
	* @Description: 组织机构-人员信息查询
	* @author WXJ
	* @date 2013-5-3 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String orgTreeUserList() throws Exception {
		String ids = getParameter("treeUserId") != null ? getParameter("treeUserId").toString() : "";
		getRequest().setAttribute("ids", ids);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTreeData 
	* @Description: 组织机构-人员信息查询
	* @author WXJ
	* @date 2013-5-3 上午11:11:55 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeUserListData() throws Exception {
		String type = getParameter("type");
		String id = getParameter("id");
		Struts2Util.renderXml(
				orgMgrManager.getOrgTreeUserListData(id, getContextPath(),type),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	/**
	 * 
	* @Title: indexFrame 
	* @Description: 组织机构-人员登记
	* @author WXJ
	* @date 2013-5-10 下午06:50:39 
	* @param @return    
	* @return String 
	* @throws
	 */
	public String indexFrame(){
	   String OrgId =this.getRequest().getParameter("orgId");
	   String opt = this.getRequest().getParameter("opt");
	   this.getRequest().setAttribute("orgId", OrgId);
	   this.getRequest().setAttribute("opt", opt);
	   return SUCCESS;
	} 
	
	/**
	 * 
	* @Title: orgTreeUserCheckinFrame 
	* @Description: 组织机构-人员登记
	* @author WXJ
	* @date 2013-5-8 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeUserCheckinFrame() throws Exception {
		String userIdStr = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		getRequest().setAttribute("loginId", userIdStr);

		long uid = 0;
		
		try{
			uid = Long.parseLong(userIdStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		if(uid !=0 && e4PersonnelManager.isE4Personnel(uid)){
			return "e4Page";
		}else{
			return "oldPage";
		}
	}
	
	/**
	 * 
	* @Title: orgTreeUserCheckinFrame 
	* @Description: 组织机构-人员登记
	* @author WXJ
	* @date 2013-5-8 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeUserCheckinFrameAdmin() throws Exception {
		this.getRequest().setAttribute("loginId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTreeUserCheckinFrameOther 
	* @Description: 组织机构-人员登记
	* @author hj
	* @date 2013-9-12
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeUserCheckinFrameOther() throws Exception {
		this.getRequest().setAttribute("loginId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return SUCCESS;
	}
	
	public String orgTreeUserCheckinFrameOther1() throws Exception {
		this.getRequest().setAttribute("loginId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTree 
	* @Description: 组织机构-人员登记
	* @author WXJ
	* @date 2013-5-8 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String orgTreeUserCheckin() throws Exception {
		String selectedNode =this.getRequest().getParameter("selectedNode");
		this.getRequest().setAttribute("selectedNode", selectedNode);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTree 
	* @Description: 组织机构-人员登记
	* @author WXJ
	* @date 2013-5-8 上午11:11:21 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String orgTreeUserCheckinAdmin() throws Exception {
		String selectedNode =this.getRequest().getParameter("selectedNode");
		this.getRequest().setAttribute("selectedNode", selectedNode);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTreeUserCheckinOther 
	* @Description: 组织机构-人员登记
	* @author HJ
	* @date 2013-9-12
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws																																																																																			
	 */
	public String orgTreeUserCheckinOther() throws Exception {
		String selectedNode =this.getRequest().getParameter("selectedNode");
		this.getRequest().setAttribute("selectedNode", selectedNode);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgTreeData 
	* @Description: 组织机构-人员登记
	* @author WXJ
	* @date 2013-5-8 上午11:11:55 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeUserCheckinData() throws Exception {
		Integer telType=Integer.valueOf(getParameter("telType"));
		String type = getParameter("type");
		String id = getParameter("id");
		
		Struts2Util.renderXml(
				orgMgrManager.getOrgTreeUserCheckinData(id, getContextPath(),type,telType),
				"encoding:UTF-8", "no-cache:true");


		return null;
	}
	
	/**
	 * 
	* @Title: orgOtherUserCheckinData 
	* @Description: 组织机构-人员登记
	* @author hj
	* @date 2013-9-12
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgOtherUserCheckinData() throws Exception {
		String type = getParameter("type");
		String id = getParameter("id");
		Struts2Util.renderXml(
				orgMgrManager.getOrgOtherUserCheckinData(id, type, getContextPath()),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	public String orgOtherUserCheckinData1() throws Exception {
		String type = getParameter("type");
		String id = getParameter("id");
		Struts2Util.renderXml(
				orgMgrManager.getOrgOtherUserCheckinData1(id, type, getContextPath()),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	/**
	 * 新城一期
	 * @return
	 * @throws Exception
	 */
	public String orgOtherUserCheckinDataMetro() throws Exception {
		String type = getParameter("type");
		String id = getParameter("id");
		Struts2Util.renderXml(
				orgMgrManager.getOrgOtherUserCheckinDataMetro(id, type, getContextPath()),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	/**
	 * 政务服务中心
	 */
	public String orgOtherUserCheckinDataAdminService() throws Exception {
		String type = getParameter("type");
		String id = getParameter("id");
		Struts2Util.renderXml(
				orgMgrManager.getOrgOtherUserCheckinDataAdminService(id, type, getContextPath()),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	
	/**
	 * 
	* @Title: orgUserTree 
	* @Description: 取得机构树的数据以xml的形式传送到页面
	* @author WXJ
	* @date 2013-5-5 上午11:11:11 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgUserTree() throws Exception {
		this.getRequest().setAttribute("orgIdForSelect", Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
		return SUCCESS;
	}
	
	 /**
	* @Description 取得机构树的数据以xml的形式传送到页面 超级管理员
	* @Author：zhujiabiao
	* @Date 2013-8-8 上午10:03:47 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String orgAdminUserTree() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgUserTree 
	* @Description: 取得机构树的数据以xml的形式传送到页面
	* @author WXJ
	* @date 2013-5-5 上午11:11:11 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgUserTreeData() throws Exception {
		Struts2Util.renderXml(
				orgMgrManager.getOrgUserTreeData(entity.getOrgId()==null?null:entity.getOrgId().toString(), getContextPath(),roomId,dtype),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	
	public String attendanceApprovers() {
		this.getRequest().setAttribute("orgid", orgMgrManager.getTopOrgId().get(0).get("TOP_ORG_ID").toString());
		return SUCCESS;
	}
	
	/**
	 * 这里的roomid是userid
	 * dtype 是orgid
	 * @return
	 */
	public String attendanceApproversData() {
		Struts2Util.renderXml(
				orgMgrManager.getOrgUserTreeData(dtype, getContextPath(),roomId,"approvers"),
				"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	public String buildingAtts() {
		return SUCCESS;
	}
	
	/**
	 * 这里的roomid是userid
	 * dtype 是orgid
	 * @return
	 */
	public String buildingAttsData() {
		Struts2Util.renderXml(
				orgMgrManager.getNjhwWyUserTree(getContextPath()),
				"encoding:UTF-8", "no-cache:true");
		return null;
	}

	 /**
	* @Description 取得机构树的数据以xml的形式传送到页面 admin
	* @Author：zhujiabiao
	* @Date 2013-8-8 上午10:08:21 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String orgAdminUserTreeData() throws Exception {
		String type = getParameter("type");
		String orgId = getParameter("id");
		String nodeId = getParameter("nodeId");
		Struts2Util.renderXml(
				orgMgrManager.getOrgAdminUserTreeData(orgId==null?"1":orgId, getContextPath(),type, nodeId),
				"encoding:UTF-8", "no-cache:true");
		
		return null;
		
	}
	
	/**
	 * 
	* @Title: treeDrag 
	* @Description: 托拽机构树中的部门
	* @author SQS
	* @date 2013-5-5 上午11:13:25 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String treeDrag() throws Exception {
		try {
			String selectFrom = getParameter("selectFrom"); // 托拽前的机构ID
			String selectTo = getParameter("selectTo");     // 托拽后的ID
			String selectPerToOrg = getParameter("selectPerToOrg");//托拽人到机构
			
			if(selectFrom!=null && selectTo!=null && !"".equals(selectFrom))
			{
		    List list = orgMgrManager.findByHQL(" from Org o where o.orgId = ? ", Long.parseLong(selectFrom));
		    
		    if(list.size() > 0){
		    Org listTo = (Org) orgMgrManager.findById(Org.class, Long.parseLong(selectTo)); //托拽到机构
		    int lisNum = Integer.parseInt(listTo.getLevelNum().trim());   
		    
		    int isElseTo = Integer.parseInt(listTo.getLevelNum().trim());                     
		    isElseTo++;																		//跨多级 或一级的 托拽到的机构 的LEVELNUM
				for(int i=0;i<list.size();i++){
				    Org org = (Org)list.get(i);
				    int levelNumNext = Integer.parseInt(org.getLevelNum().trim());
				    levelNumNext++;
				    int moreLevelNext = Integer.parseInt(org.getLevelNum().trim());
				    moreLevelNext--;
				    
				    List listNext = orgMgrManager.findByHQL(" from Org o where o.PId = ? and trim(o.levelNum) = ? ",org.getOrgId(),levelNumNext+""); //PID LEVELNUM 是第一个的
				    if(listNext.size()>0){
				    	 Org orgNext = (Org)listNext.get(i);
						    int num = Integer.parseInt(orgNext.getLevelNum().trim());
						   //levelNumNext=levelNumNext+2;
						    if(num <= lisNum){
						    	orgNext.setLevelNum(moreLevelNext+ "");
						    	orgNext.setPId(org.getOrgId());
						    	orgMgrManager.updateTreeDrag(orgNext);
						    }else{
						    	orgNext.setLevelNum( num+ "");
						    	orgNext.setPId(org.getOrgId());
						    	orgMgrManager.updateTreeDrag(orgNext);
						    }
				    }
						    org.setLevelNum(isElseTo+"");
						    org.setPId(Long.parseLong(selectTo)); // 设置托拽后的 PID 值
						    orgMgrManager.updateTreeDrag(org);
				}
			}
		}
		//人托拽到机构
		else
		{        
			List list = orgMgrManager.findByHQL(" from Users u where u.userid = ? ", Long.parseLong(selectPerToOrg));
			List<Org> listorg = orgMgrManager.findByHQL(" from Org o where o.orgId = ? ", Long.parseLong(selectTo));
			if(list.size() > 0){
				for(int i=0;i<list.size();i++){
					Users users = (Users)list.get(i);
					users.setOrgId(Long.parseLong(selectTo)); // 设置托拽后的 PID 值
					orgMgrManager.updatePerDragOrg(users);
					UserInfo userInfo = new LDAPService().findUserByLoginUid(users.getLoginUid());
					userInfo.setOrgId(selectTo);
					userInfo.setOrgName(listorg.get(0).getName());
					new LDAPService().updateUser(userInfo);
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String saveBuildingAtts(){
		orgMgrManager.saveBuildingAtt(checkedIds);
		setIsSuc("true");
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Title: orgUserSelectSave 
	 * @Description: 保存人员房间、人员门锁对应关系
	 * @author WXJ
	 * @date 2013-5-5 上午11:13:25 
	 * @param @return
	 * @param @throws Exception    
	 * @return String 
	 * @throws
	 */
	public String orgUserSelectSave() throws Exception {
		try {
			String loginId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			
			if(checkedIds!=null){
				if ("room".equals(dtype)){
					orgMgrManager.saveRoomUser(roomId, checkedIds, loginId);
				}
				if ("lock".equals(dtype)){
					doorControlToAppService.initDoorAuthority(roomId, checkedIds, loginId);
				}
				// 这里的roomid 相当于userid
				if("approvers".equals(dtype)){
					if(checkedIds.length()<=2)
					{ 	
						addActionMessage("请选择考勤审批人员.");
						setIsSuc("false");
					}
					else{
						orgMgrManager.saveApprovers(roomId, checkedIds);
						this.getRequest().setAttribute("orgid", Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString());
						setIsSuc("true");
					}
					return ERROR;
				}
			}
			addActionMessage("保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("保存失败");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 
	* @Title: orgUserTree 
	* @Description: 机构树checkbox
	* @author WXJ
	* @date 2013-5-6 上午11:14:11 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeSelect() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: orgUserTree 
	* @Description: 机构数checkbox
	* @author WXJ
	* @date 2013-5-6 上午11:14:11 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String orgTreeSelectData() throws Exception {
		String orgid = getParameter("orgid");
		Struts2Util.renderXml(
				orgMgrManager.getOrgTreeSelectData(orgid, getContextPath(),roomId),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	/**
	 * 
	 * @Title: getMesOrgUserTreeData 
	 * @Description: 机构数 发消息
	 * @author SQS
	 * @date 2013-5-6 上午11:11:11 
	 * @param @return
	 * @param @throws Exception    
	 * @return String 
	 * @throws
	 */
	public String getMesOrgUserTreeData() throws Exception {
		String ids = getParameter("ids");
		String gid  = null;
		// 找到当前用户的顶级部门
		List<HashMap> list = this.personROManager.getTopOrgId();
		if (list.size() > 0) gid = list.get(0).get("TOP_ORG_ID") != null ? list.get(0).get("TOP_ORG_ID").toString() : null;
		
		String type = getParameter("type");
		Struts2Util.renderXml(
				orgMgrManager.getMesOrgUserTreeData(gid, ids, getContextPath(), type),
				"encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 机构维护主页面
	 * @return 
	 * @throws Exception
	 */
	public String orgFrame() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * gxh 上移组织
	 */
   public String upOrg(){
		JSONObject json = new JSONObject();
		String orgId=Struts2Util.getParameter("_chk");
	   int suNum =orgMgrManager.updateUpOrg(Long.parseLong(orgId));
	  
			try {
				 if(suNum == 0){//成功
				json.put("message", 0);
		    	 }
				 if(suNum == 1){//失败
					 json.put("message", 1);
				 }
				 if(suNum == 2){//不能上移
					 json.put("message", 2);
				 }
				 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
	   
	  return null; 
   }
   /**
    * 组织向下移
   * @title: downOrg 
   * @description: TODO
   * @author gxh
   * @return
   * @date 2013-5-23 下午02:51:30     
   * @throws
    */
    public String downOrg(){
		JSONObject json = new JSONObject();
		String orgId=Struts2Util.getParameter("_chk");
	   int suNum =orgMgrManager.updateDownOrg(Long.parseLong(orgId));
	  
			try {
				 if(suNum == 0){//成功
				json.put("message", 0);
		    	 }
				 if(suNum == 1){//失败
					 json.put("message", 1);
				 }
				 if(suNum == 2){//不能下移
					 json.put("message", 2);
				 }
				 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
	   
	  return null; 
   }
   
    
   /**
    *  判断是否能添加组织机构
   * @title: isOrNoaddOrg 
   * @description: TODO
   * @author gxh
   * @return
   * @date 2013-5-14 下午04:29:24     
   * @throws
    */
   public String isOrNoaddOrg(){
		JSONObject json = new JSONObject();
		String orgId=Struts2Util.getParameter("orgId");
		
			 try {
				 if (orgId!=null && !"".equals(orgId) && Integer.valueOf(orgId)>2) {
				   json.put("message", 1);//失败
				 }else{
					json.put("message", 0);//成功
				 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	    Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
	   
	  return null; 
   }
   /**
    * 模糊查找用户
   * @title: findUsers 
   * @description: TODO
   * @author gxh
   * @return
   * @date 2013-5-14 下午10:36:37     
   * @throws
    */
   
   public String findUsers(){
		Map pMap = new HashMap();
		Org og = null;
		List orgIdList=null;
		pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		List<Org> list = orgMgrManager.findListBySql("PersonnelSQL.getOrgByManager", pMap);
		if(list!=null&&list.size()>0){
			orgIdList = new ArrayList();
			for(int i=0;i<list.size();i++){
				 og = list.get(i);
				 orgIdList.add(og.getOrgId());
			}
		
			
		}
		
		String displayName=Struts2Util.getParameter("displayName");
		if(displayName!=null&&og.getOrgId()!=null){
	     List<Map> listMap =  orgMgrManager.findUsers(displayName,orgIdList);
	  
	   
	   if(listMap!=null&&listMap.size()>0){
		   Struts2Util.renderJson(listMap, "encoding:UTF-8",
			"no-cache:true");
	   }
		}
	  		return null;
	}
   
   
	   /** 
	* @title: usersLimitInit 
	* @description: 人员权限查询初始页面
	* @author cjw
	* @return
	* @date 2013-5-19 下午11:18:24     
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public String usersLimitInit(){
		Map<String, Object> pMap = new HashMap<String, Object>();
		List orgIdList = null;
		pMap.put("userid", Struts2Util.getSession().getAttribute(
				Constants.USER_ID).toString());
		List<Org> list = orgMgrManager.findListBySql(
				"PersonnelSQL.getOrgByManager", pMap);
		if (list != null && list.size() > 0) {
			orgIdList = new ArrayList();
			for (Org o : list) {
				orgIdList.add(o.getOrgId());
			}
		}
		pageLimit = orgMgrManager.queryUsersLimit(pageLimit, displayName,
				orgIdList);
	   return SUCCESS;
   }
	   /** 
	* @title: usersLimitList 
	* @description: 人员权限查询
	* @author cjw
	* @return
	* @date 2013-5-19 下午11:18:07     
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public String usersLimitList(){
		Map<String,Object> pMap = new HashMap<String,Object>();
		List orgIdList=null;
		pMap.put("userid", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		List<Org> list = orgMgrManager.findListBySql("PersonnelSQL.getOrgByManager", pMap);
		if(list!=null&&list.size()>0){
			orgIdList = new ArrayList();
			for(Org o : list){
				orgIdList.add(o.getOrgId());
			}
		}	
		pageLimit = orgMgrManager.queryUsersLimit(pageLimit, displayName, orgIdList);
		//Struts2Util.getRequest().setAttribute("page", pageLimit);
		return SUCCESS;
   }
   
   
	 /**
     * 人员上移
    * @title: upUsers 
    * @description: TODO
    * @author gxh
    * @return
    * @date 2013-5-23 下午02:53:11     
    * @throws
     */
	public String upUsers(){
		JSONObject json = new JSONObject();
		String userId=Struts2Util.getParameter("_chk");
	   int suNum =orgMgrManager.updateUpUser(Long.parseLong(userId));
	  
			try {
				 if(suNum == 0){//成功
				json.put("message", 0);
		    	 }
				 if(suNum == 1){//失败
					 json.put("message", 1);
				 }
				 if(suNum == 2){//不能上移
					 json.put("message", 2);
				 }
				 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		
		
		
		return null;
	}
    /**
     * 人员下移
    * @title: downUsers 
    * @description: TODO
    * @author gxh
    * @return
    * @date 2013-5-23 下午04:40:19     
    * @throws
     */
	public String downUsers(){
		JSONObject json = new JSONObject();
		String userId=Struts2Util.getParameter("_chk");
	   int suNum =orgMgrManager.updateDownUser(Long.parseLong(userId));
	  
			try {
				 if(suNum == 0){//成功
				json.put("message", 0);
		    	 }
				 if(suNum == 1){//失败
					 json.put("message", 1);
				 }
				 if(suNum == 2){//不能下移
					 json.put("message", 2);
				 }
				 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		
		
		return null;
	}
	
	
	public void setOrgMgrManager(OrgMgrManager orgMgrManager) {
		this.orgMgrManager = orgMgrManager;
	}
	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public String getParentOrgtype() {
		return parentOrgtype;
	}
	public void setParentOrgtype(String parentOrgtype) {
		this.parentOrgtype = parentOrgtype;
	}
	public String getOrgtypename() {
		return orgtypename;
	}
	public void setOrgtypename(String orgtypename) {
		this.orgtypename = orgtypename;
	}

	public Page<HashMap<String, String>> getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(Page<HashMap<String, String>> pageLimit) {
		this.pageLimit = pageLimit;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示机构分页列表.
	 */
	public Page<Org> getPage() {
		return page;
	}
	public PersonRegOutManager getPersonROManager() {
		return personROManager;
	}
	public void setPersonROManager(PersonRegOutManager personROManager) {
		this.personROManager = personROManager;
	}
	
	/** 
	 * parentOrgname 
	 * 
	 * @return the parentOrgname 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getParentOrgname()
	{
		return parentOrgname;
	}

	/** 
	 * @param parentOrgname the parentOrgname to set 
	 */
	
	public void setParentOrgname(String parentOrgname)
	{
		this.parentOrgname = parentOrgname;
	}
	
	
}
