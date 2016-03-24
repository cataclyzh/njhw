/**
* <p>文件名: OrgMgrAction.java</p>
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
package com.cosmosource.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.service.OrgMgrManager;

/**
* @类描述: 机构管理Action,用于机构的CRUD，机构树的显示
* @作者： WXJ
*/
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OrgMgrAction extends BaseAction<TAcOrg> {

	private static final long serialVersionUID = 8497952646128136433L;
	private OrgMgrManager orgMgrManager;
	//-- 页面属性 --//
	private Long orgid ;
	private TAcOrg entity = new TAcOrg();
	private Page<TAcOrg> page = new Page<TAcOrg>(Constants.PAGESIZE);//默认每页20条记录
//	private String nodeId;//机构树节点ID
	private String _chk[];//选中记录的ID数组
	private String parentOrgtype;//父机构的类型
	private String parentOrgname;//上级机构名称
	private String orgtypename;//机构类型名称
	private String tempTaxno;//用于验证纳税人识别号是否重复
	
	//-- ModelDriven 与 Preparable函数 --//
	public TAcOrg getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		orgid = entity.getOrgid();
		if (orgid != null) {
			entity = (TAcOrg)orgMgrManager.findById(TAcOrg.class, orgid);
			this.setTempTaxno(entity.getTaxno());
			String exType = entity.getExtracttype();
			List list = new ArrayList();
			if(exType!=null){
				StringTokenizer token = new StringTokenizer(exType,",");
				String currentToken = null;
				while (token.hasMoreTokens()) {
					currentToken = token.nextToken();
					list.add(currentToken.trim());
				}
			}
			getRequest().setAttribute("selectList", list);
		} else {
			entity = new TAcOrg();
		}
	}
	/**
	 * 查询机构列表
	 * @return 
	 * @throws Exception
	 */
	public String list() throws Exception {
//		System.out.println("登录人数：     "+getServletContext().getAttribute("userCounter"));
//		Set<User> users = (Set<User>)getServletContext().getAttribute("userNames");
//		
//		for(User user : users){
//			System.out.println("登录人员："+user.getUsername());
//		}
//		
//		System.out.println("登录人员：     "+(Set<User>)getServletContext().getAttribute("userNames"));
		
		
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setParentid(new Long(nodeId));
		}
		page = orgMgrManager.queryOrgs(page, entity);
		return SUCCESS;
	}
	/**
	 * 显示机构详细信息用于查看或是修改
	 * @return 
	 * @throws Exception
	 */
	
	public String input() throws Exception {
		if(entity.getTreelevel()==1){
			entity.setOrgtype("1");
		}
		if("3".equals(parentOrgtype)){
			entity.setOrgtype("8");
		}
		this.setOrgtypename(orgMgrManager.findDictNameByid("ORG_TYPE", entity.getOrgtype()));
		this.setParentOrgname(((TAcOrg)orgMgrManager.findById(TAcOrg.class, entity.getParentid())).getOrgname());
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
			//更新机构层级信息，所有上级以.分隔开
			orgMgrManager.updatetreelayer(entity);
			
			String exType = entity.getExtracttype();
			List list = new ArrayList();
			if(exType!=null){
				StringTokenizer token = new StringTokenizer(exType,",");
				String currentToken = null;
				while (token.hasMoreTokens()) {
					currentToken = token.nextToken();
					list.add(currentToken.trim());
				}
			}
			getRequest().setAttribute("selectList", list);
			//addActionMessage("保存机构成功");
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//addActionMessage("保存机构失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}

	/**
	 * 批量删除选中的机构
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			Long valOrg = orgMgrManager.deleteOrgs(_chk);
			if(valOrg.longValue()>0){
				addActionMessage("请删除机构的关联信息");
			}else{
				addActionMessage("删除机构成功");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除机构失败");
			
		}
		return RELOAD;
	}
	public String orgTree() throws Exception {
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
		Struts2Util.renderXml(
				orgMgrManager.getOrgTreeData(id, getContextPath(),type),
				"encoding:UTF-8", "no-cache:true");

		return null;
	}
	
	/**
	 * 支持使用Jquery.validate Ajax检验.
	 */
	public String checkTaxnoInput() {
		String code = getParameter("taxno");
		String temp = getParameter("tempTaxno");
		
		if (orgMgrManager.isTaxnoUnique(code, temp)) {
			Struts2Util.renderText("true");
		} else {
			Struts2Util.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
	
	/**
	 * 取得数据以json的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String getJson() throws Exception {
		String company = getParameter("company");
		String type = getParameter("type");
		if(StringUtil.isBlank(company)){
			return null;
		}
			 
		Map map  = new HashMap();
		if(orgMgrManager.genJsonFiles(company,type)){
			map.put("1", "true");
			map.put("msg", "生成Json文件成功！");
			Struts2Util.renderJson(map, "encoding:UTF-8", "no-cache:true");
		}else{
			map.put("err", "true");
			map.put("msg", "生成Json文件成功！");
			Struts2Util.renderJson(map, "encoding:UTF-8", "no-cache:true");
		}

		return null;
	}
	public String orgTypeSelect() throws Exception {
		return SUCCESS;
	}
	/**
	 * 机构维护主页面
	 * @return 
	 * @throws Exception
	 */
	public String orgFrame() throws Exception {
		return SUCCESS;
	}
	//-- 页面属性访问函数 --//
	/**
	 * list页面显示机构分页列表.
	 */
	public Page<TAcOrg> getPage() {
		return page;
	}
	public void setOrgMgrManager(OrgMgrManager orgMgrManager) {
		this.orgMgrManager = orgMgrManager;
	}
//	public void setNodeId(String nodeId) {
//		this.nodeId = nodeId;
//	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public String getParentOrgtype() {
		return parentOrgtype;
	}
	public void setParentOrgtype(String parentOrgtype) {
		this.parentOrgtype = parentOrgtype;
	}
	public String getParentOrgname() {
		return parentOrgname;
	}
	public void setParentOrgname(String parentOrgname) {
		this.parentOrgname = parentOrgname;
	}
	public String getOrgtypename() {
		return orgtypename;
	}
	public void setOrgtypename(String orgtypename) {
		this.orgtypename = orgtypename;
	}
	public String getTempTaxno() {
		return tempTaxno;
	}
	public void setTempTaxno(String tempTaxno) {
		this.tempTaxno = tempTaxno;
	}
}
