/**
* <p>文件名: UserMgrAction.java</p>
* <p>描述：用户管理Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-8-30 下午02:28:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcUser;
import com.cosmosource.common.security.SecurityConstant;
import com.cosmosource.common.service.UserMgrManager;

/**
* @类描述: 用户管理Action,用于用户的CRUD，机构树的显示
* @作者： WXJ
*/
public class UserMgrAction extends BaseAction<TAcUser> {

	private static final long serialVersionUID = 956159071191655177L;
	private UserMgrManager userMgrManager;
	//-- 页面属性 --//
	private Long userid ;
	private TAcUser entity = new TAcUser();
	private TAcOrg entityOrg = new TAcOrg();
	private Page<TAcUser> page = new Page<TAcUser>(Constants.PAGESIZE);//默认每页20条记录
	private Page<TAcOrg> pageOrg = new Page<TAcOrg>(Constants.PAGESIZE);//默认每页20条记录
	private String nodeId;//机构树节点ID
	private String _chk[];//选中记录的ID数组
	private String orgname;
	private String tempLoginname;	
	//-- ModelDriven 与 Preparable函数 --//
	public TAcUser getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		userid = entity.getUserid();
		if (userid != null) {
			entity = (TAcUser)userMgrManager.findById(TAcUser.class, userid);
		} else {
			entity = new TAcUser();
		}
		String sOrgid = getParameter("orgid");
		if(sOrgid!=null){
			TAcOrg org = (TAcOrg)userMgrManager.findById(TAcOrg.class, new Long(sOrgid));
			entity.setOrgid(new Long(sOrgid));
			setOrgname(org.getOrgname());
		}
		setTempLoginname(entity.getLoginname());
	}
	/**
	 * 查询用户列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setOrgid(new Long(nodeId));
		}
		page = userMgrManager.queryUsers(page, entity);	
		return SUCCESS;
	}
	
	/**
	 * 显示用户详细信息用于查看或是修改
	 * @return 
	 * @throws Exception
	 */
	public String input() throws Exception {
		return INPUT;
	}

	/**
	 * 保存信息
	 * @return 
	 */
	public String save()  {
		try {
//			userMgrManager.initUser(entity);
			entity.setUsertype("0");
			userMgrManager.saveUser(entity);
			setTempLoginname(entity.getLoginname());
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");	
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 批量删除选中的用户
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			userMgrManager.deleteUsers(_chk);
			addActionMessage("删除用户成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除用户失败");
		}
		return RELOAD;
	}
	public String orgTree() throws Exception {
		
		return SUCCESS;
	}
	public String orgFrame() throws Exception {
		
		getRequest().setAttribute("nodeId", nodeId);
	
		return SUCCESS;
	}
	public String orgList() throws Exception {
		if(StringUtil.isNotBlank(nodeId)){
			entityOrg.setParentid(Long.parseLong(nodeId));
		}
		
		pageOrg = userMgrManager.queryOrgs(pageOrg, entityOrg);
		return SUCCESS;
	}
	/**
	 * 取得机构树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String orgTreeData() throws Exception {
		String type = getParameter("type");
		String id = getParameter("id");
		Struts2Util.renderXml(userMgrManager.getOrgTreeData(id,getContextPath(),type), 
				"encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 支持使用Jquery.validate Ajax检验.
	 */
	public String checkLoginname() {
		String loginname = getParameter("loginname");
		String tempLoginname = getParameter("tempLoginname");
		
		if (userMgrManager.isLoginNameUnique(loginname, tempLoginname)) {
			Struts2Util.renderText("true");
		} else {
			Struts2Util.renderText("false");
		}
	
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
	/**
	 * 重置用户密码
	 * @return 
	 */
	public String resetPwd()  {
		TAcUser entUser = userMgrManager.getOrgidByLoginname(entity.getLoginname());
		if("4".equals(entUser.getStatus())){
			addActionMessage("此用户已停用，请与管理员联系！");
			return SUCCESS;
		}
		
		if(entUser==null||entUser.getUserid()==null){
			addActionMessage("登录名不存在，请重新输入！");
		}else{
			if(!StringUtil.equals(entUser.getUsername(), entity.getUsername())){
				addActionMessage("用户名与登录名不符，请重新输入！");
			}else{
				try {
					entUser.setPassword(DigestUtils.md5Hex(entity.getPassword()));
					entUser.setPlainpassword(entity.getPassword());
					entUser.setPwdModifyTime(new Date());
					entUser.setPwdWrongCount(0L);
					entUser.setStatus(SecurityConstant.ACCOUNT_INIT);
//					entUser.setPlainpassword(entity.getPassword());
					userMgrManager.saveUser(entUser);
					addActionMessage("重置密码成功！");	
				} catch (Exception e) {
					addActionMessage("重置密码失败！");			
				}	
			}

		}
		return SUCCESS;
	}
	/**
	 * 重置用户密码
	 * @return 
	 */
	public String initResetPwd()  {
		return SUCCESS;
	}
	/**
	 * 用户维护主页面
	 * @return 
	 * @throws Exception
	 */
	public String userFrame() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * @描述:用户解锁
	 * @作者：WXJ
	 * @日期：2012-6-5
	 * @return
	 */
	public String unlock() {
		String loginname = getParameter("loginname");
		logger.info("解锁用户:" + loginname);
		int result = userMgrManager.updateUserUnLock(loginname);
		logger.info("更新用户状态结果:" + result);
		if (result > 0) {
			Struts2Util.renderText("true");
		} else {
			Struts2Util.renderText("false");
		}
	
		return null;
	}
	
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示用户分页列表.
	 */
	public Page<TAcUser> getPage() {
		return page;
	}
	public void setUserMgrManager(UserMgrManager userMgrManager) {
		this.userMgrManager = userMgrManager;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getTempLoginname() {
		return tempLoginname;
	}
	public void setTempLoginname(String tempLoginname) {
		this.tempLoginname = tempLoginname;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public Page<TAcOrg> getPageOrg() {
		return pageOrg;
	}
	public void setPageOrg(Page<TAcOrg> pageOrg) {
		this.pageOrg = pageOrg;
	}
	public TAcOrg getEntityOrg() {
		return entityOrg;
	}
	public void setEntityOrg(TAcOrg entityOrg) {
		this.entityOrg = entityOrg;
	}
}
