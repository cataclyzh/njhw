package com.cosmosource.app.integrateservice.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.entity.NjhwAdlistGroup;
import com.cosmosource.app.entity.NjhwUsersAlist;
import com.cosmosource.app.integrateservice.service.PAddressBookManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONObject;

@SuppressWarnings("unchecked")
public class PAddressBookAction extends BaseAction {
	
	private static final long serialVersionUID = 4227875753301925460L;
	
	private NjhwAdlistGroup njhwAdlistGroup = new NjhwAdlistGroup();
	private Page<NjhwAdlistGroup> groupApage = new Page<NjhwAdlistGroup>(100);//默认每页100条记录	
	String group_chk[];//选中记录的ID数组
	
	//private long gid;
	private String gid;
	private NjhwUsersAlist njhwUsersAlist = new NjhwUsersAlist();
	private Page<NjhwUsersAlist> userApage = new Page<NjhwUsersAlist>(10);
	private String userA_chk[];//选中记录的ID数组
	
	private PAddressBookManager pAddressBookManager;
	
	/** 
	* @title: init
	* @description: 初始化
	* @author zh
	* @date 2013-05-1
	*/ 
	public String init() {
		return SUCCESS;
	}
	
	/** 
	* @title: loadGroupList
	* @description: 加载通讯录分组
	* @author zh
	* @date 2013-05-1
	*/ 
	public String loadGroupList() {
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("gid", 0);
		JSONObject json = new JSONObject();
		try {
			List groupList = pAddressBookManager.loadGroupList(map);
			List personList = this.pAddressBookManager.loadNoGroupPersonList(map);
			json.put("groupList", groupList);
			json.put("personList", personList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	private String pageNo;
	
	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @title: addressList
	 * @description:加载指定分组通讯录详情
	 * @author zh
	 * @date 2013-05-3
	 */
	public String addressList() {
		long gid = 0L;
		String gname = "未分组";
		if(getParameter("gid") != null && !"0".equals(getParameter("gid"))) {
			gid = Long.parseLong(getParameter("gid"));
			NjhwAdlistGroup group = (NjhwAdlistGroup)this.pAddressBookManager.findById(NjhwAdlistGroup.class, gid);
			gname = group.getNagName();
		}
		getRequest().setAttribute("gname", gname);
		
		Map map = new HashMap();
		map.put("gid", gid);
		map.put("searchVal", getParameter("searchVal"));
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		try {
			String page = getParameter("page");
			//String pageNo = getParameter("pageNo");
			if (page == null || "".equals(page))  userApage.setPageNo(1);
			else userApage.setPageNo(Integer.parseInt(page));
			userApage = this.pAddressBookManager.loadAddListByGId(userApage, map);
			getRequest().setAttribute("page", userApage);
			//List list = this.pAddressBookManager.loadAddListByGId(map);
			getRequest().setAttribute("pageNo",userApage.getPageNo());
			getRequest().setAttribute("personLength", userApage.getTotalCount());
//			getRequest().setAttribute("personList", list);
			getRequest().setAttribute("map", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * @title: addressInput
	 * @description: 显示通讯录编辑页面
	 * @author zh
	 * @date 2013-05-3
	 */
	public String addressInput() {
		String nuaId = getParameter("nuaId");
		NjhwUsersAlist usersA = null;
		if (StringUtil.isNotEmpty(nuaId))
			usersA = (NjhwUsersAlist) this.pAddressBookManager.findById(NjhwUsersAlist.class, Long.parseLong(nuaId));
		
		// 加载通讯录分组
		Map map = new HashMap();
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		getRequest().setAttribute("groupList", pAddressBookManager.loadGroupList(map));
		
		getRequest().setAttribute("gid", getParameter("gid"));
		getRequest().setAttribute("isPopup", getParameter("isPopup"));
		String title;
		if(getParameter("titleNum") != null && getParameter("titleNum") != ""){
			if(getParameter("titleNum").equals("edit")){
				title = "编辑";
			}else{
				title = "新增";
			}
		}else{
			title = "新增";
		}
		getRequest().setAttribute("title", title);
		getRequest().setAttribute("addEntity", usersA);
		return INPUT;
	}
	
	/**
	 * @title: addressInput
	 * @description: 显示通讯录编辑页面
	 * @author zh
	 * @date 2013-05-3
	 */
	public String editressInput() {
		String nuaId = getParameter("nuaId");
		NjhwUsersAlist usersA = null;
		if (StringUtil.isNotEmpty(nuaId))
			usersA = (NjhwUsersAlist) this.pAddressBookManager.findById(NjhwUsersAlist.class, Long.parseLong(nuaId));
		
		// 加载通讯录分组
		Map map = new HashMap();
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		getRequest().setAttribute("groupList", pAddressBookManager.loadGroupList(map));
		
		getRequest().setAttribute("gid", getParameter("gid"));
		getRequest().setAttribute("isPopup", getParameter("isPopup"));
		String title;
		if(getParameter("titleNum") != null && getParameter("titleNum") != ""){
			if(getParameter("titleNum").equals("edit")){
				title = "编辑";
			}else{
				title = "新增";
			}
		}else{
			title = "新增";
		}
		getRequest().setAttribute("title", title);
		getRequest().setAttribute("addEntity", usersA);
		return INPUT;
	}
	
	/**
	 * @title: addressShow
	 * @description: 查看联系人信息
	 * @author zh
	 * @date 2013-05-3
	 */
	public String addressShow() {
		String nuaId = getParameter("nuaId");
		NjhwUsersAlist usersA = null;
		if (StringUtil.isNotEmpty(nuaId))
			usersA = (NjhwUsersAlist) this.pAddressBookManager.findById(NjhwUsersAlist.class, Long.parseLong(nuaId));
		
		String groupName = "无分组";
		List<NjhwAdlistGroup> groupList = this.pAddressBookManager.findByHQL("select t from NjhwAdlistGroup t where t.nagId = ?", usersA.getNuaGroup());
		if (groupList !=null && groupList.size() > 0) groupName = groupList.get(0).getNagName();
		
		getRequest().setAttribute("groupName", groupName);
		getRequest().setAttribute("addEntity", usersA);
		return SUCCESS;
	}
	
	/**
	 * @title: addressSave
	 * @description: 保存通讯录信息
	 * @author zh
	 * @date 2013-05-3
	 */
	public String addressSave() {
		try {
			this.pAddressBookManager.saveAddress(njhwUsersAlist);
			Struts2Util.renderText("success");
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Util.renderText("error");
		}
		return null;
	}
	
	/**
	 * 批量删除
	 * @return 
	 * @throws Exception
	 */
	public String addressDelete() throws Exception {
		int num = 0;
		try {
			if (userA_chk == null) {
				num = 1;
				userA_chk = new String[]{ getParameter("PID") };
			}
			pAddressBookManager.deleteAddress(userA_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		if (num == 0)	return RELOAD;
		else return SUCCESS;
	}
	
	/**
	 * @title: groupInput
	 * @description: 显示通讯录分组编辑页面
	 * @author zh
	 * @date 2013-05-3
	 */
	public String groupInput() {
		String gid = getParameter("gid");
		String gTitle = getParameter("gTitle");
		String gName;
		NjhwAdlistGroup group = null;
		if (StringUtil.isNotEmpty(gid))
			group = (NjhwAdlistGroup) this.pAddressBookManager.findById(NjhwAdlistGroup.class, Long.parseLong(gid));
		if(gTitle == null){
			gName = "添加";
		}else if("edit".equals(gTitle)){
			gName = "编辑";
		}else{
			gName = "添加";
		}
		getRequest().setAttribute("gTitle",gName);
		getRequest().setAttribute("groupEntity", group);
		return INPUT;
	}
	
	/**
	 * @title: groupSave
	 * @description: 保存通讯录分组信息
	 * @author zh
	 * @date 2013-05-3
	 */
	public String groupSave() {
		String groupName = getParameter("nagName");
		String flag = "false";
		Map map = new HashMap();
		map.put("userId", Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
		List<Map> groupList = pAddressBookManager.loadGroupList(map);
		try {
			if("未分组".equals(groupName)){
				setIsSuc("false");
				flag = "true";
			}else{
				for(Map param : groupList){
					if(param.get("NAG_NAME").equals(groupName)){
						flag = "true";
						break;
					}else{
						flag = "false";
					}
				}
			}
			if("false".equals(flag)){
				this.pAddressBookManager.saveGroup(getParameter("nagId") ,getParameter("nagName"));
				setIsSuc("true");
			}else{
				setIsSuc("false");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * @return 
	 * @throws Exception
	 */
	public String groupDelete() throws Exception {
		try {
			group_chk = new String[]{ getParameter("GID") };
			pAddressBookManager.deleteGroup(group_chk);
			addActionMessage("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionMessage("删除失败");
		}
		return RELOAD;
	}	

	@Override
	public NjhwUsersAlist getModel() {
		return njhwUsersAlist;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public PAddressBookManager getpAddressBookManager() {
		return pAddressBookManager;
	}

	public void setpAddressBookManager(PAddressBookManager pAddressBookManager) {
		this.pAddressBookManager = pAddressBookManager;
	}

	public NjhwAdlistGroup getNjhwAdlistGroup() {
		return njhwAdlistGroup;
	}

	public void setNjhwAdlistGroup(NjhwAdlistGroup njhwAdlistGroup) {
		this.njhwAdlistGroup = njhwAdlistGroup;
	}

	public NjhwUsersAlist getNjhwUsersAlist() {
		return njhwUsersAlist;
	}

	public void setNjhwUsersAlist(NjhwUsersAlist njhwUsersAlist) {
		this.njhwUsersAlist = njhwUsersAlist;
	}

	public Page<NjhwAdlistGroup> getGroupApage() {
		return groupApage;
	}

	public void setGroupApage(Page<NjhwAdlistGroup> groupApage) {
		this.groupApage = groupApage;
	}

	public String[] getGroup_chk() {
		return group_chk;
	}

	public void setGroup_chk(String[] groupChk) {
		group_chk = groupChk;
	}

//	public Page<NjhwUsersAlist> getUserApage() {
//		return userApage;
//	}
//
//	public void setUserApage(Page<NjhwUsersAlist> userApage) {
//		this.userApage = userApage;
//	}

	public String[] getUserA_chk() {
		return userA_chk;
	}

	public void setUserA_chk(String[] userAChk) {
		userA_chk = userAChk;
	}

	public String getGid() {
		return gid;
	}

//	public void setGid(long gid) {
//		this.gid = gid;
//	}
//	
	public void setGid(String gid){
			this.gid = gid;
	}
}
