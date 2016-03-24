/**
* <p>文件名: AccessMgrAction.java</p>
* <p>描述：门禁闸机管理Action</p>
* @创建时间： 2013-8-16
* @作者： HJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.app.personnel.action;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.cosmosource.app.entity.FsDishes;
import com.cosmosource.app.entity.NjhwUsersAccess;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.personnel.service.AccessMgrManager;
import com.cosmosource.app.personnel.service.ObjMgrManager;
import com.cosmosource.app.utils.PictureUtils;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.PropertiesUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONObject;


/**
* @类描述: 管理局门禁闸机管理Action
* @作者：HJ
*/
public class NjhwAccessMgrAction extends BaseAction<NjhwUsersAccess> {

	private static final long serialVersionUID = 8497952646128196433L;
	private AccessMgrManager accessMgrManager;
	//-- 页面属性 --//

	private NjhwUsersAccess entity = new NjhwUsersAccess();
	private Page<NjhwUsersAccess> page = new Page<NjhwUsersAccess>();//默认每页20条记录
	
	private List<Users> usersList = new ArrayList<Users>();
	

	private String _chk[];//选中记录的ID数组
	private String parentOrgname;//上级资源名称
	private long nodeId ;
	private String resType;
		
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	//-- ModelDriven 与 Preparable函数 --//
	public NjhwUsersAccess getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
	}

	/**
	 * 查询单位人员门禁申请信息
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryNjhwAccess() throws Exception {
		page.setPageSize(12);
		String ids = getParameter("ids");
		ArrayList idsList = new ArrayList();
		if (!"".equals(ids) && ids != null) {
			for (String id : ids.split(",")) {
				if (!"".equals(id)) idsList.add(Long.parseLong(id));
			}
		}
		
		HashMap map = new HashMap();
		map.put("name", getParameter("name"));
		map.put("appStatus", getParameter("appStatus"));
		map.put("appTime", getParameter("appTime"));
		map.put("orgName", getParameter("orgName"));
		map.put("opt", getParameter("opt"));
		
		if("4".equals(getParameter("userType"))) {
			map.put("isTmp", "true");
		} else if ("1".equals(getParameter("userType"))) {
			map.put("isNor", "true");
		}
		map.put("isNjhw", "true");
		page = accessMgrManager.queryUserAccess(page, map);
		map.put("ids", ids);
		map.put("userType", getParameter("userType"));
		getRequest().setAttribute("map", map);
		Map<String, String> userType = new LinkedHashMap<String, String>();
		userType.put("1", "内部员工");
		userType.put("4", "临时员工");
		getRequest().setAttribute("userType", userType);
		Map<String, String> optType = new LinkedHashMap<String, String>();
		optType.put("1", "新增");
		optType.put("2", "更新");
		optType.put("3", "删除");
		getRequest().setAttribute("optType", optType);
		return SUCCESS;
	}

	/**
	 * 添加或者更改单位人员门禁申请信息Input页面
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String accessApplyInput() throws Exception {
		try {
			Long id = entity.getNuacId();
			if(id != null){
				entity = (NjhwUsersAccess)accessMgrManager.findById(NjhwUsersAccess.class, id);
				Users user = (Users)accessMgrManager.findById(Users.class, entity.getUserid());
				getRequest().setAttribute("name", user.getDisplayName());
				List<Map> l = accessMgrManager.getAccessGuardInfo(id);
				if (l != null && l.size() > 0) {
					getRequest().setAttribute("access", l.get(0).get("ACCESS_AUTH") == null? "" : l.get(0).get("ACCESS_AUTH").toString());
					getRequest().setAttribute("guard", l.get(0).get("GUARD_AUTH") == null? "" : l.get(0).get("GUARD_AUTH").toString());
				}
			}
			getRequest().setAttribute("status", getParameter("status"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: refuseAccessApply 
	* @description: 退回门禁闸机申请信息
	* @author hj
	* @return
	* @date 2013-8-21
	* @throws
	 */
	public String refuseAccessApply() throws Exception{
		JSONObject json = new JSONObject();
		try {
			String id = getParameter("id");
			String lockVer = getParameter("lockVer");
			String refuseReason =  new String(getParameter("refuseReason").getBytes(
			"ISO-8859-1"), "utf-8");
			String s = accessMgrManager.refuseAccessApply(id, lockVer, refuseReason);
			if(StringUtil.isNotBlank(s)) {
				json.put("msg", "用户" + s + "的申请信息正在操作，退回失败！");
			} else {
				json.put("msg", "退回申请信息成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("msg", "退回申请信息失败！");
		}
		Struts2Util.render("text/html", json.toString(), "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: refuseAccessApplyBatch
	* @description: 批量退回门禁闸机申请信息
	* @author hj
	* @return
	* @date 2013-8-21
	* @throws
	 */
	public String refuseAccessApplyBatch() throws Exception{
		try {
			String refuseReason =  new String(getParameter("refuseReason").getBytes(
			"ISO-8859-1"), "utf-8");
			String s = accessMgrManager.refuseAccessApplyBatch(_chk, refuseReason);
			if(StringUtil.isNotBlank(s)) {
				addActionMessage("用户" + s + "的申请信息正在操作，退回失败！");
			} else {
				addActionMessage("退回申请信息成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("退回申请信息失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: refuseAccessApply 
	* @description: 同意门禁闸机申请信息
	* @author hj
	* @return
	* @date 2013-8-21
	* @throws
	 */
	public String agreeAccessApply() throws Exception{
		JSONObject json = new JSONObject();
		try {
			String id = getParameter("id");
			String lockVer = getParameter("lockVer");
			Map m = accessMgrManager.agreeAccessApply(id, lockVer);
			if(!m.isEmpty()) {
				if (m.get("msg") == null) {
					json.put("msg", "用户" + m.get("name").toString() + "的申请信息正在操作，同意失败！");
				} else {
					json.put("msg", "用户" + m.get("name").toString() + "的申请信息同意失败， 失败原因：" + m.get("msg").toString());
				}
			} else {
				json.put("msg", "同意申请信息成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("msg", "同意申请信息失败！");
		}
		Struts2Util.render("text/html", json.toString(), "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: agreeAccessApplyBatch
	* @description: 批量同意门禁闸机申请信息
	* @author hj
	* @return
	* @date 2013-8-22
	* @throws
	 */
	public String agreeAccessApplyBatch() throws Exception{
		try {
			List<Map> l = accessMgrManager.agreeAccessApplyBatch(_chk);
			
			if (l != null && l.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (Map m : l) {
					if (m.get("msg") == null) {
						sb.append("用户").append(m.get("name").toString()).append("的申请信息正在操作，同意失败！</br>");
					} else {
						sb.append("用户").append(m.get("name").toString()).
							append("的申请信息同意失败， 失败原因：").append(m.get("msg").toString()).append("</br>");
					}
				}
				addActionMessage(sb.toString());
			} else {
				addActionMessage("同意申请信息成功！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("同意申请信息失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: saveAccessAuth 
	* @description: 保存或修改门禁授权信息
	* @author hj
	* @return
	* @date 2013-8-16
	* @throws
	 */
	public String saveAccess(){
		try {
			String userid = getParameter("userid");
			String nuacId = getParameter("nuacId");
			String accessIds = getParameter("accessIds");
			String guardIds = getParameter("guardIds");
			String lockVer = getParameter("lockVer");
			String loginId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			if(StringUtil.isNotBlank(nuacId)){
				entity = (NjhwUsersAccess)accessMgrManager.findById(NjhwUsersAccess.class, Long.parseLong(nuacId));
				entity.setLockVer(Long.parseLong(lockVer) + 1);
			}else{
				entity = new NjhwUsersAccess();
				entity.setLockVer(0l);
				entity.setInsertId(Long.parseLong(loginId));
		    	entity.setInsertDate(DateUtil.getSysDate());
		    	entity.setAppTime(DateUtil.getSysDate());
			}
			if (StringUtil.isBlank(lockVer) || Long.parseLong(lockVer)+1 == entity.getLockVer().longValue()) {
				entity.setUserid(Long.parseLong(userid));
				entity.setAuthIdsG(guardIds);
				entity.setAuthIdsA(accessIds);
				entity.setAppStatus("1");
				
		    	entity.setUpdateId(Long.parseLong(loginId));
		    	entity.setUpdateDate(DateUtil.getSysDate());
		    	accessMgrManager.saveUpdateAccess(entity);
				setIsSuc("true");
			} else {
				setIsSuc("false1");
			}
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: allotAccessUserTree 
	* @Description: 取得人员树的数据以xml的形式传送到页面
	* @author HJ
	* @date 2013-8-19
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String allotAccessUserTree() {
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: allotAccessUserTreeData 
	* @Description: 取得机构树的数据以xml的形式传送到页面
	* @author HJ
	* @date 2013-8-19
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String allotAccessUserTreeData() throws Exception {
		Struts2Util.renderXml(
				accessMgrManager.getOrgUserTreeData(Struts2Util.getSession().getAttribute(Constants.ORG_ID).toString()),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	/**
	 * 
	* @Title: allotGuardTree 
	* @Description: 取得闸机树的数据以xml的形式传送到页面
	* @author HJ
	* @date 2013-8-19
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String allotGuardTree() {
		String ids = getParameter("ids") != null ? getParameter("ids").toString() : "";
		getRequest().setAttribute("ids", ids);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: allotGuardTreeData 
	* @Description: 取得闸机树的数据以xml的形式传送到页面
	* @author HJ
	* @date 2013-8-19
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String allotGuardTreeData() throws Exception {
		String ids = getParameter("ids");
		Struts2Util.renderXml(
				accessMgrManager.getGuardTreeData(ids),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	/**
	 * 
	* @Title: allotAccessUserTree 
	* @Description: 取得门禁树的数据以xml的形式传送到页面
	* @author HJ
	* @date 2013-8-19
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String allotAccessTree() {
		String ids = getParameter("ids") != null ? getParameter("ids").toString() : "";
		getRequest().setAttribute("ids", ids);
		return SUCCESS;
	}
	
	/**
	 * 取得资源树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String allotAccessTreeData() throws Exception {
		String ids = getParameter("ids");
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			Struts2Util.renderXml( accessMgrManager.getObjTreeSelectData(id,ids, type), "encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @title: 
	* @description: 批量注销选中的申请信息
	* @author HJ
	* @return
	* @throws Exception
	* @date 2013-8-20
	* @throws
	 */
	public String deleteAccess() throws Exception {
		try {
			String s = accessMgrManager.deleteAccess(_chk);
			if(StringUtil.isNotBlank(s)) {
				addActionMessage("用户" + s + "的申请信息正在操作，注销失败！");
			} else {
				addActionMessage("注销门禁闸机申请信息成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("注销门禁闸机申请信息失败");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @title: refuseInput
	* @description: 退回理由输入页面
	* @author HJ
	* @return
	* @throws Exception
	* @date 2013-8-23
	* @throws
	 */
	public String refuseInput() {
		getRequest().setAttribute("type", getParameter("type"));
		getRequest().setAttribute("id", getParameter("id"));
		getRequest().setAttribute("lockVer", getParameter("lockVer"));
		return SUCCESS;
	}
	
	public Page<NjhwUsersAccess> getPage() {
		return page;
	}
	public void setPage(Page<NjhwUsersAccess> page) {
		this.page = page;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public String getParentOrgname() {
		return parentOrgname;
	}
	public void setParentOrgname(String parentOrgname) {
		this.parentOrgname = parentOrgname;
	}
	public NjhwUsersAccess getEntity() {
		return entity;
	}
	public void setEntity(NjhwUsersAccess entity) {
		this.entity = entity;
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public List<Users> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}
	public void setAccessMgrManager(AccessMgrManager accessMgrManager) {
		this.accessMgrManager = accessMgrManager;
	}
	public AccessMgrManager getAccessMgrManager() {
		return accessMgrManager;
	}
}
