/**
* <p>文件名: OrgMgrAction.java</p>
* <p>描述：资源管理Action</p>
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.personnel.model.DoorControllerModel;
import com.cosmosource.app.personnel.service.ObjMgrManager;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;


/**
* @类描述: 资源管理Action,用于资源的CRUD，资源树的显示
* @作者： WXJ
*/
public class ObjMgrAction extends BaseAction<Objtank> {

	private static final long serialVersionUID = 8497952646128136433L;
	private ObjMgrManager objMgrManager;
	private DoorControlToAppService doorControlToAppService;
	//-- 页面属性 --//
	private Page<HashMap<String, String>> pageDevice = new Page<HashMap<String, String>>(Constants.PAGESIZE);//默认每页20条记录
	private Objtank entity = new Objtank();
	private Page<Objtank> page = new Page<Objtank>(Constants.PAGESIZE);//默认每页20条记录

	private Page<HashMap<String, Object>> pageUsers = new Page<HashMap<String, Object>>(Constants.PAGESIZE);//默认每页20条记录
	
	private List<Users> usersList = new ArrayList<Users>();
	
	private Page<DoorControllerModel> pageDoorControllerModel = new Page<DoorControllerModel>();

	private String _chk[];//选中记录的ID数组
	private String parentOrgname;//上级资源名称
	private long nodeId ;
	private String resType;
		
	// 设备授权管理查询参数
	private String  displayName;
	private String  status;
	private String  orgName;
	private String  appTime;
	/** 
	 * displayName 
	 * 
	 * @return the displayName 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getDisplayName()
	{
		return displayName;
	}
	/** 
	 * @param displayName the displayName to set 
	 */
	
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
	/** 
	 * status 
	 * 
	 * @return the status 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getStatus()
	{
		return status;
	}
	/** 
	 * @param status the status to set 
	 */
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	/** 
	 * orgName 
	 * 
	 * @return the orgName 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getOrgName()
	{
		return orgName;
	}
	/** 
	 * @param orgName the orgName to set 
	 */
	
	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}
	/** 
	 * appTime 
	 * 
	 * @return the appTime 
	 * @since   CodingExample Ver(编码范例查看) 1.0 
	 */
	
	public String getAppTime()
	{
		return appTime;
	}
	/** 
	 * @param appTime the appTime to set 
	 */
	
	public void setAppTime(String appTime)
	{
		this.appTime = appTime;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	//-- ModelDriven 与 Preparable函数 --//
	public Objtank getModel() {
		return entity;
	}
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
		Long nodeId = entity.getNodeId();
		if (nodeId != null) {
			entity = objMgrManager.findByNodeId(nodeId);
		} else {
			entity = new Objtank();
		}
	}
	/**
	 * 查询资源列表
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
			entity.setPId(Long.parseLong(nodeId));
		}
		page = objMgrManager.queryOrgs(page, entity);
		Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
	}
	
	public String jzsbList(){
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setPId(Long.parseLong(nodeId));
		}
		page = objMgrManager.queryOrgs(page, entity);
		Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
	}
	
	 /**
	* @Description 查询通讯机列表
	* @Author：zhujiabiao
	* @Date 2013-8-16 下午02:30:55 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String jzsbDoorControllerList(){
//		String nodeId = Struts2Util.getParameter("nodeId");
//		if(nodeId!=null){
//			entity.setPId(Long.parseLong(nodeId));
//		}
//		page = objMgrManager.queryOrgs(page, entity);
//		Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
//		getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
//		getRequest().setAttribute("resType",getParameter("res") );
		String commId = getRequest().getParameter("commId");
		String commIp = getRequest().getParameter("commIp");
		String memo = getRequest().getParameter("memo");
		Map map = new HashMap();
		map.put("commId", commId==""?null:commId);
		map.put("commIp", commIp==""?null:commIp);
		map.put("memo", memo==""?null:memo);
		getRequest().setAttribute("commId", commId);
		getRequest().setAttribute("commIp", commIp);
		getRequest().setAttribute("memo", memo);
		try {
			page.setPageSize(14);
			pageDoorControllerModel = objMgrManager.getDoorControllerPage(page,map);
			getRequest().setAttribute("page", pageDoorControllerModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Description 新增修改页
	 * @Author：zhujiabiao
	 * @Date 2013-8-16 下午02:30:55 
	 * @param
	 * @return
	 * @throws Exception
	 * @version V1.0   
	 **/
	public String jzsbDoorControllerInput(){
		String id = getRequest().getParameter("id");
		DoorControllerModel tempDoorControllerModel = new DoorControllerModel();
		if(id!=null&&!id.equals("")){
			tempDoorControllerModel = objMgrManager.getDoorControllerModelById(Long.valueOf(id));
		}
		getRequest().setAttribute("doorControllerModel", tempDoorControllerModel);
		return SUCCESS;
	}
	
	/**
	 * @Description 上传授权码
	 * @Author：hejun
	 * @Date 2013-10-9
	 * @param
	 * @return
	 * @throws Exception
	 * @version V1.0   
	 **/
	public void jzsbDoorControllerUpload(){
		String commId = getRequest().getParameter("commId");
		boolean flag = doorControlToAppService.uploadDoorAuthCode(commId);

		JSONObject json = new JSONObject();
		
		json.put("isSuccess", flag);
		
		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	
	public String jzsbDoorControllerSaveOrUpdate(){
		String id = getRequest().getParameter("id");
		String commIdStr = getRequest().getParameter("commId");
		String commIpStr = getRequest().getParameter("commIp");
		String memoStr = getRequest().getParameter("memo");
		
		Long commId = commIdStr==null||commIdStr.equals("")?null:Long.valueOf(commIdStr);
		String commIp = commIpStr==null||commIpStr.equals("")?null:commIpStr;
		String memo = memoStr==null||memoStr.equals("")?null:memoStr;
		
		String bak = null;
		String comm_bak1 = null;
		String comm_bak2 = null;
		String comm_bak3 = null;

		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("commId", commId);
		map.put("commIp", commIp);
		map.put("memo", memo);
		map.put("bak", bak);
		map.put("commbak1", comm_bak1);
		map.put("commbak2", comm_bak2);
		map.put("commbak3", comm_bak3);
		
		
		try {
			if(id!=null&&id!=""){
				objMgrManager.updateDoorControllerComm(map);
			}else{
				objMgrManager.saveDoorControllerComm(map);
			}
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String checkCommId(){
		String commIdStr = getRequest().getParameter("commId");
		String commIdOldStr = getRequest().getParameter("commIdOld");
		Long commId = commIdStr==null||commIdStr.equals("")?null:Long.valueOf(commIdStr);
		Long commIdOld = commIdOldStr==null||commIdOldStr.equals("")?null:Long.valueOf(commIdOldStr);
		
		if(commIdOld!=null&&commId.equals(commIdOld)){
			try {
				Struts2Util.getResponse().getWriter().print(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Struts2Util.getResponse().getWriter().print(objMgrManager.checkCommId(commId));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String checkCommIp(){
		String commIp = getRequest().getParameter("commIp");
		String commIpOld = getRequest().getParameter("commIdOld");
		
		if(commIpOld!=null&&commIp.equals(commIpOld)){
			try {
				Struts2Util.getResponse().getWriter().print(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Struts2Util.getResponse().getWriter().print(objMgrManager.checkCommIp(commIp));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String jzsbElectronicLockList(){
		String nodeId = Struts2Util.getParameter("nodeId");
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyWord = Struts2Util.getParameter("keyword");
		String memo = Struts2Util.getParameter("memo");
		String link = Struts2Util.getParameter("link");
		int paged = page.getPageNo();   //当前第几页
		int pages = 1;   //当前第几页 整型
		if(paged == 0){
			pages = 1;
		}else{
			pages = paged;
		}
		int pageCount = 1;		//分页总数
		//int num ;
		try {
			page.setPageSize(14);	//每页查询10条。貌似没效果，因为sql语句拥有pageMax pageMin
			page.setPageNo(pages);	//记录当前页数， 该函数有用
			
			Map param = new HashMap();
			param.put("nodeId", nodeId);
			param.put("pageMax", page.getPageNo() * 14);
			param.put("pageMin", (page.getPageNo() - 1) * 14);
			
			//条件查询专用
			param.put("name", name);
			param.put("title", title);
			param.put("keyword", keyWord);
			param.put("memo", memo);
			param.put("link", link);
			
			List<Objtank> list = objMgrManager.getQueryLockListByType(param); //list为查询出的集合
			
			if(list.size() == 0){
				page.setPageNo(pages-1);
				param.put("nodeId", nodeId);
				param.put("pageMax", (pages -1 < 1 ? 1 : pages - 1) * 14);
				param.put("pageMin", (pages - 2 < 0 ? 0 : pages - 2) * 14);
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", keyWord);
				param.put("memo", memo);
				param.put("link", link);
				list = objMgrManager.getQueryLockListByType(param); //list为查询出的集合
			}
			
			List<Map> listCount = objMgrManager.getQueryLockListCount(param);  //listCount为查询出总数
			pageCount = Integer.parseInt(listCount.get(0).get("CUN").toString());
			
			//房间 楼层 集合遍历。
			for(int i=0;i<list.size();i++){
				if(!"R".equals(list.get(i).getIco())){
					list.get(i).setLink(list.get(i).getMemo());
					list.get(i).setMemo("");
				}
			}
			
			for(int i=0;i<list.size();i++){
				if(list.get(i).getObjAttrib() == "" || list.get(i).getObjAttrib() == null){
					list.get(i).setObjAttrib("请关联通讯机");
				}else{
					list.get(i).setObjAttrib(list.get(i).getObjAttrib() + "号通讯机");
				}
			}
			
			page.setResult(list);	//将查询的列表set到page里
			page.setTotalCount(pageCount);  //记录总条数    		这函数有效果
			
			getRequest().setAttribute("page", page);  //集合查询
			getRequest().setAttribute("extResType", 1);
			//getRequest().setAttribute("page.result", list);  //集合查询
			//getRequest().setAttribute("pageCount", pageCount);  //总数据
			//getRequest().setAttribute("num", num);  //总页数
			//getRequest().setAttribute("page", pages);  //当前页
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//List resultList = objMgrManager.getQueryLightListByType(Long.parseLong(nodeId));
		//if(nodeId!=null){
		//	entity.setPId(Long.parseLong(nodeId));
		//}
		//page = objMgrManager.queryOrgs(page, entity);
		//Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		//getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		//getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
		/*
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setPId(Long.parseLong(nodeId));
		}
		page = objMgrManager.queryOrgs(page, entity);
		Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
		*/
	}
	
	/**
	 * 设备维护数据查询
	 * @return
	 */
	public String jzsbLightList(){
		String nodeId = Struts2Util.getParameter("nodeId");
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyWord = Struts2Util.getParameter("keyword");
		String memo = Struts2Util.getParameter("memo");
		String link = Struts2Util.getParameter("link");
		int paged = page.getPageNo();   //当前第几页
		int pages = 1;   //当前第几页 整型
		if(paged == 0){
			pages = 1;
		}else{
			pages = paged;
		}
		int pageCount = 1;		//分页总数
		//int num ;
		try {
			page.setPageSize(14);	//每页查询10条。貌似没效果，因为sql语句拥有pageMax pageMin
			page.setPageNo(pages);	//记录当前页数， 该函数有用
			
			Map param = new HashMap();
			param.put("nodeId", nodeId);
			param.put("pageMax", page.getPageNo() * 14);
			param.put("pageMin", (page.getPageNo() - 1) * 14);
			
			//条件查询专用
			param.put("name", name);
			param.put("title", title);
			param.put("keyword", keyWord);
			param.put("memo", memo);
			param.put("link", link);
			
			List<Objtank> list = objMgrManager.getQueryLightListByType(param); //list为查询出的集合
			if(list.size() == 0){
				page.setPageNo(pages-1);
				param.put("nodeId", nodeId);
				param.put("pageMax", (pages -1 < 1 ? 1 : pages - 1) * 14);
				param.put("pageMin", (pages - 2 < 0 ? 0 : pages - 2) * 14);
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", keyWord);
				param.put("memo", memo);
				param.put("link", link);
				list = objMgrManager.getQueryLightListByType(param); //list为查询出的集合
			}
			List<Map> listCount = objMgrManager.getQueryLightListCount(param);  //listCount为查询出总数
			pageCount = Integer.parseInt(listCount.get(0).get("CUN").toString());
			
			//房间 楼层 集合遍历。
			for(int i=0;i<list.size();i++){
				if(list.get(i).getIco() != null){
					if(!"R".equals(list.get(i).getIco())){
						list.get(i).setLink(list.get(i).getMemo());
						list.get(i).setMemo("");
					}
				}
			}
			
			/**
			if(pageCount % 10 != 0 )
				num = pageCount / 10 + 1;
			else
				num = pageCount / 10;
			*/
			page.setResult(list);	//将查询的列表set到page里
			page.setTotalCount(pageCount);  //记录总条数    		这函数有效果
			
			getRequest().setAttribute("page", page);  //集合查询
			getRequest().setAttribute("extResType", 1);
			//getRequest().setAttribute("page.result", list);  //集合查询
			//getRequest().setAttribute("pageCount", pageCount);  //总数据
			//getRequest().setAttribute("num", num);  //总页数
			//getRequest().setAttribute("page", pages);  //当前页
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//List resultList = objMgrManager.getQueryLightListByType(Long.parseLong(nodeId));
		//if(nodeId!=null){
		//	entity.setPId(Long.parseLong(nodeId));
		//}
		//page = objMgrManager.queryOrgs(page, entity);
		//Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		//getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		//getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
	}
	
	/**
	 * 查询通讯机编号下拉框
	 * @return
	 */
	public String queryNjhwDoorCommJSON(){
		Map param = new HashMap();
		List list = objMgrManager.findObjTankAll("PersonnelSQL.queryNjhwDoorComm", param);
		Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 删除设备信息
	 * @return
	 */
	public String delJzsbCheck(){
		try {
			String idStr = getParameter("idStr");
			Long valOrg = objMgrManager.deleteOrgs(idStr.split(","));
			//= objMgrManager.deleteOrgs(_chk);
			if(valOrg.longValue()>0){
				Struts2Util.renderText("请删除机构的关联信息");
			}else{
				Struts2Util.renderText("删除设备成功");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Struts2Util.renderText("删除设备失败");
			
		}
		return null;
	}
	
	public String delJzsbLockCheck(){
		String setId = Struts2Util.getParameter("deletes");
		String [] setIds = setId.split(",");
		String idStr = getParameter("idStr");
		try {
			List list = new ArrayList();
			if(setIds != null){
				for(int i=0;i<setIds.length;i++){
					Map param = new HashMap();
					param.put("setId", setIds[i]);
					list.add(param);
				}
			}
			
			Long valOrg = objMgrManager.deleteLock(idStr.split(","), list);
			if(valOrg.longValue()>0){
				Struts2Util.renderText("请删除机构的关联信息");
			}else{
				Struts2Util.renderText("删除设备成功");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Struts2Util.renderText("删除设备失败");
			
		}
		return null;
	}
	
	/**
	 * 根据ID 查询设备type 返回JSON
	 * @return
	 */
	public String queryExtTypeJSON(){
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId != ""){
			Objtank objtank = objMgrManager.findByNodeId(Long.parseLong(nodeId));
			Map param = new HashMap();
			param.put("extType", objtank.getExtResType());
			Struts2Util.renderJson(param, "encoding:UTF-8", "no-cache:true");
		}
		
		return null;
	}
	
	/**
	 * 根据条件查询是否存在数据
	 * @param sqlMap
	 * @param param
	 * Y:true N:false
	 * @return
	 */
	public String queryLockCountJSON(){
		String adrsStore = Struts2Util.getParameter("adrsStore");
		String selectValue = Struts2Util.getParameter("selectValue");
		String setId = Struts2Util.getParameter("setId");
		String flag = null;
		try {
			Map param = new HashMap();
			param.put("adrsStore", adrsStore);
			param.put("adrsComm", selectValue);
			if(setId != null)
				param.put("setId", setId);
			List list = objMgrManager.queryListLockCount("PersonnelSQL.queryListLockCount", param);
			if(list.size() > 0){
				flag = "N";
			}else{
				flag = "Y";
			}
			Map par = new HashMap();
			par.put("flag",flag);
			Struts2Util.renderJson(par, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 根据ID 查询该设备的信息
	 * @return
	 */
	public String queryInfoLightInput(){
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId != ""){
			Objtank objtank = objMgrManager.findByNodeId(Long.parseLong(nodeId));
			getRequest().setAttribute("objtank", objtank);
		}
		return SUCCESS;
	}
	/**
	 * 查询设备信息 空调
	 */
	public String queryInfoAirCondInput(){
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId != ""){
			Objtank objtank = objMgrManager.findByNodeId(Long.parseLong(nodeId));
			getRequest().setAttribute("objtank", objtank);
		}
		return SUCCESS;
	}
	
	public String queryInfoLockInput(){
		String nodeId = Struts2Util.getParameter("nodeId");
		String setId = Struts2Util.getParameter("lockId");
		if(nodeId != ""){
			Map param = new HashMap();
			param.put("commId", Long.parseLong(setId));
			List list = new ArrayList(); 
			list = objMgrManager.findObjTankAll("PersonnelSQL.queryNjhwDoorCommById", param);
			Map par = new HashMap();
			par.put("nodeId", nodeId);
			
			List resultList = objMgrManager.findObjTankAll("PersonnelSQL.queryJzsbByUpdate", par);
			if(list.size()!=0)
				getRequest().setAttribute("list", list.get(0));
			else{
				list.add(new HashMap().put("COMM_ID", ""));
				getRequest().setAttribute("list", list.get(0));
			}
			getRequest().setAttribute("resultList", resultList.get(0));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID 修改设备信息
	 * @return
	 */
	public String jzsbInfoLightUpdate(){
		String nodeId = Struts2Util.getParameter("nodeId");
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyword = Struts2Util.getParameter("keyword");
		
		try {
			Map param = new  HashMap();
			param.put("nodeId", nodeId);
			param.put("name", name);
			param.put("title", title);
			param.put("keyword", keyword);
			objMgrManager.updateObjTankAll("PersonnelSQL.updateJzsbInfoLight", param);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return INPUT;
	}
	
	public String jzsbInfoLockUpdate(){
		String nodeId = Struts2Util.getParameter("nodeId"); //OBJTANK 表ID
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String selectValue = Struts2Util.getParameter("selectValue");
		String setId = Struts2Util.getParameter("lockId"); //njhw_doorcontrol_set 表ID
		String adrsStore = Struts2Util.getParameter("adrsStore");
		
		try {
			Map param = new  HashMap();
			if(nodeId != null){
				param.put("nodeId", Long.parseLong(nodeId));
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", name);
			}
			Map par = new HashMap();
			if(setId != null){
				par.put("setId", Long.parseLong(setId));
				par.put("name", name);
				par.put("title", name);
				par.put("keyword", name);
				par.put("adrsStore", adrsStore);
				par.put("adrsComm", selectValue);
			}
			objMgrManager.updateObjTankSecond(param, par);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return INPUT;
	}
	
	/**
	 * 设备信息更新 空调
	 */
	public String jzsbInfoAirCondUpdate(){
		String nodeId = Struts2Util.getParameter("nodeId");
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyword = Struts2Util.getParameter("keyword");
		
		try {
			Map param = new  HashMap();
			if(nodeId != null){
				param.put("nodeId", Long.parseLong(nodeId));
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", keyword);
			}
			objMgrManager.updateObjTankAll("PersonnelSQL.updateJzsbInfoLight", param);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return INPUT;
	}
	
	/**
	 *设备信息 空调列表
	 */
	public String jzsbAirConditionList(){
		String nodeId = Struts2Util.getParameter("nodeId");
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyWord = Struts2Util.getParameter("keyword");
		String memo = Struts2Util.getParameter("memo");
		String link = Struts2Util.getParameter("link");
		int paged = page.getPageNo();   //当前第几页
		int pages = 1;   //当前第几页 整型
		if(paged == 0){
			pages = 1;
		}else{
			pages = paged;
		}
		int pageCount = 1;		//分页总数
		//int num ;
		try {
			page.setPageSize(14);	//每页查询10条。貌似没效果，因为sql语句拥有pageMax pageMin
			page.setPageNo(pages);	//记录当前页数， 该函数有用
			
			Map param = new HashMap();
			param.put("nodeId", nodeId);
			param.put("pageMax", page.getPageNo() * 14);
			param.put("pageMin", (page.getPageNo() - 1) * 14);
			
			//条件查询专用
			param.put("name", name);
			param.put("title", title);
			param.put("keyword", keyWord);
			param.put("memo", memo);
			param.put("link", link);
			
			List<Objtank> list = objMgrManager.getQueryAirCondListByType(param); //list为查询出的集合
			if(list.size() == 0){
				page.setPageNo(pages-1);
				param.put("nodeId", nodeId);
				param.put("pageMax", (pages -1 < 1 ? 1 : pages - 1) * 14);
				param.put("pageMin", (pages - 2 < 0 ? 0 : pages - 2) * 14);
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", keyWord);
				param.put("memo", memo);
				param.put("link", link);
				list = objMgrManager.getQueryAirCondListByType(param); //list为查询出的集合
			}
			List<Map> listCount = objMgrManager.getQueryAirCondListCount(param);  //listCount为查询出总数
			pageCount = Integer.parseInt(listCount.get(0).get("CUN").toString());
			
			//房间 楼层 集合遍历。
			for(int i=0;i<list.size();i++){
				if(!"R".equals(list.get(i).getIco())){
					list.get(i).setLink(list.get(i).getMemo());
					list.get(i).setMemo("");
				}
			}
			
			/**
			if(pageCount % 10 != 0 )
				num = pageCount / 10 + 1;
			else
				num = pageCount / 10;
			*/
			page.setResult(list);	//将查询的列表set到page里
			page.setTotalCount(pageCount);  //记录总条数    		这函数有效果
			
			getRequest().setAttribute("page", page);  //集合查询
			getRequest().setAttribute("extResType", 1);
			//getRequest().setAttribute("page.result", list);  //集合查询
			//getRequest().setAttribute("pageCount", pageCount);  //总数据
			//getRequest().setAttribute("num", num);  //总页数
			//getRequest().setAttribute("page", pages);  //当前页
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//List resultList = objMgrManager.getQueryLightListByType(Long.parseLong(nodeId));
		//if(nodeId!=null){
		//	entity.setPId(Long.parseLong(nodeId));
		//}
		//page = objMgrManager.queryOrgs(page, entity);
		//Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		//getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		//getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
		/*
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setPId(Long.parseLong(nodeId));
		}
		page = objMgrManager.queryOrgs(page, entity);
		Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
		*/
	}
	
	public String jzjgList(){
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setPId(Long.parseLong(nodeId));
		}
		page.setPageSize(14);
		page = objMgrManager.queryJzjg(page, entity);
		Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
	}

	/**
	 * 查询资源列表
	 * @return 
	 * @throws Exception
	 */
	public String menulist() throws Exception {
		
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId!=null){
			entity.setPId(Long.parseLong(nodeId));
		}
		String title = getRequest().getParameter("title");
		getRequest().setAttribute("title", title);
		
		page = objMgrManager.queryOrgsMenu(page, entity);
		if((page.getResult()!=null && page.getResult().size()==0 && page.getPageNo()>1)||page.getResult()==null){
			page.setPageNo(page.getPageNo()-1);
			page = objMgrManager.queryOrgsMenu(page, entity);
		}
		Objtank obj = page.getResult()==null||page.getResult().size()==0?null:page.getResult().get(0);
		getRequest().setAttribute("ExtResType", obj==null?null:obj.getExtResType());
		getRequest().setAttribute("resType",getParameter("res") );
		return SUCCESS;
	} 
	
	/**
	 * 资源权限管理
	 * @return
	 * @throws Exception
	 */
	public String listAdmin() throws Exception {
		String nodeId = Struts2Util.getParameter("nodeId");
		if(nodeId == null)
			return ERROR;
		List resultUser = objMgrManager.queryAdminUser(Long.parseLong(nodeId));
		List resultOrg = objMgrManager.queryAdminOrg(Long.parseLong(nodeId));
		List list = new ArrayList();
		list.addAll(resultUser);
		list.addAll(resultOrg);
		getRequest().setAttribute("list", list);
		return SUCCESS;
	}
	
	public String objAdmin(){
		return SUCCESS;
	}
	
	/**
	 * 删除该权限下的用户，支持批量删除
	 * 开发者：ywl
	 */
	public String delAdminUserRoot(){
		String ids = Struts2Util.getParameter("ids");
		String nodeId = Struts2Util.getParameter("nodeId");
		String [] userids = ids.split(",");
		if(userids != null){
			for(int i=0;i<userids.length;i++){
				List list = new ArrayList();
				Map param = new HashMap();
				param.put("userid", userids[i]);
				param.put("nodeId", nodeId);
				list.add(param);
				objMgrManager.delAdminUser(list);
			}
		}
		return SUCCESS;
	}

	 /**
	* @Description 删除通讯机
	* @Author：zhujiabiao
	* @Date 2013-8-17 上午10:42:44 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String jzsbDoorControllerDel(){
		//String ids = Struts2Util.getParameter("_chk");
		//String ids = getRequest().getParameter("_chk");
		String[] idsArray = getRequest().getParameterValues("_chk");
		//String[] idsArray = ids.split(",");
		if(idsArray != null){ 
			List list = new ArrayList();
			for(int i=0;i<idsArray.length;i++){
				list.add(idsArray[i]);
			}
			Map map = new HashMap();
			map.put("ids", list);
			objMgrManager.deleteDoorControllerComm(map);
		}
		return SUCCESS;
	}
	
	/**
	 * 设备授权管理list
	 * @author zhangquanwei
	 * @return success
	 * @date 2013年8月17日14:49:26
	 */
	public String deviceAuthList()
	{   
		HashMap<String,String> conMap = new HashMap<String, String>();
		conMap.put("displayName", displayName);
		conMap.put("status", status);
		conMap.put("orgName", orgName);
		conMap.put("appTime", appTime);
		pageDevice = objMgrManager.deviceAuthMangerPage(pageDevice,conMap);
		getRequest().setAttribute("page", pageDevice);
		return SUCCESS;
	}
	
	/**
	 * 给权限分配用户
	 * @return
	 */
	public String addOrgAdminRoot(){
		
		//List listUser = new ArrayList();
		String userIds = Struts2Util.getParameter("checkedIds");
		String nodeId = Struts2Util.getParameter("nodeId");
		String [] ids = userIds.split(",");
		Map param = null;
		if(userIds != null){
			for(int i=0;i<ids.length;i++){
				//System.out.println(ids[i].substring(0, 1));
				if(ids[i].substring(0, 1).equals("u")){
					List listUser = new ArrayList();
					param = new HashMap();
					//System.out.println(ids[i].substring(5,ids[i].length()));
					param.put("userid", ids[i].substring(5,ids[i].length()));
					param.put("nodeid", nodeId);
					param.put("type", "user");
					listUser.add(param);
	
					objMgrManager.insertBatchBySql("PersonnelSQL.insertAdminUserRoot", listUser);
				}else{
					List listOrg = new ArrayList();
					param = new HashMap();
					//System.out.println(ids[i]);
					param.put("userid", ids[i]);
					param.put("nodeid", nodeId);
					param.put("type", "org");
					listOrg.add(param);
					objMgrManager.insertBatchBySql("PersonnelSQL.insertAdminUserRoot", listOrg);
				}
			}
		}
		//objMgrManager.insertMenuPermMap(listOrg, listUser);
		return SUCCESS;
	}
	
	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午06:00:23 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String adminList() throws Exception {
		String nodeid = Struts2Util.getParameter("nodeId");
		try {
			usersList = objMgrManager.queryUsers(Long.valueOf(nodeid));
			getRequest().setAttribute("usersList", usersList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	/**
	 * 显示资源详细信息用于查看或是修改
	 * @return 
	 * @throws Exception
	 */
	
	public String input() throws Exception {
		try {	
			Objtank obj = objMgrManager.findByNodeId(entity.getPId());		
			getRequest().setAttribute("resType", getParameter("res"));
			this.setParentOrgname(obj.getTitle());
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
			//保存资源信息
			String title=entity.getTitle();
			entity.setName(title);
			entity.setKeyword(title);
			objMgrManager.saveOrg(entity);
			//addActionMessage("保存资源成功");
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//addActionMessage("保存资源失败");
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	public String jzsbLightSave(){
		try {
			String name = entity.getName(); //获取门牌号
			entity.setKeyword(name);//给key赋值
			objMgrManager.saveOrg(entity);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	/**
	 * 建筑设备维护保存方法，
	 * @return
	 */
	public String jzjgSave(){
		try {
			String name = entity.getName(); //获取门牌号
			entity.setKeyword(name);//给key赋值
			long nodeid;
			if(entity.getNodeId()==null)
			{
				nodeid=0l;
			}else{
				nodeid=entity.getNodeId();
			}
				 boolean flag=objMgrManager.findByName(name,nodeid);
				if(flag)
				{
					setIsSuc("error");
					return SUCCESS;
				}
			objMgrManager.saveOrg(entity);
			setIsSuc("true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}
	
	/**
	 * 批量删除选中的资源
	 * @return 
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String[] black={"个人用户","单位管理员","超级管理员首页"};
		try {
			String deleteIds = getParameter("deleteIds");
			String blackName=getParameter("blackName");
			for(int j=0;j<black.length;j++){
			for(int i=0;i<blackName.split(",").length;i++)
			{
				if(deleteIds == null || blackName.split(",")[i].equals(black[j])){
					deleteIds = "";
					break;
				}
			}
			}
			Long valOrg = objMgrManager.deleteOrgs(deleteIds.split(","));
			if(valOrg.longValue() > 0){
				Struts2Util.renderText("请删除菜单的关联信息","");
			}else{
				Struts2Util.renderText("删除菜单成功","");
//				addActionMessage("删除资源成功");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Struts2Util.renderText("删除菜单失败!包含有不能删除的节点!","");
//			Struts2Util.renderText(text, "3333")
//			addActionMessage("删除资源失败");
		}
		return null;
	}
	
	public String jzjgDelete() throws Exception {
		try {
			String idStr = getParameter("idStr");
			Long valOrg = objMgrManager.deleteOrgs(idStr.split(","));
			if(valOrg.longValue() > 0){
				Struts2Util.renderText("请删除设备的关联信息","");
			}else{
				Struts2Util.renderText("删除建筑设备成功","");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Struts2Util.renderText("删除建筑设备失败","");
		}
		return null;
	}
	
	/**
	 * 设备维护添加设备功能
	 * @return
	 */
	public String jzjgLightInputSave(){
		String pId =getRequest().getParameter("nodeId");
		try {
			if(pId != null || pId != ""){
				entity.setPId(Long.parseLong(pId));
				objMgrManager.saveJzsbLight(entity);
				setIsSuc("true");
			}
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 设备信息空调保存
	 */
	public String jzjgAirCondInputSave(){
		String pId =getRequest().getParameter("nodeId");
		try {
			if(pId != null || pId != ""){
				entity.setPId(Long.parseLong(pId));
				objMgrManager.saveJzsbAirCond(entity);
				setIsSuc("true");
			}
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String jzsbLockInputSave(){
		String pId =getRequest().getParameter("nodeId");
		String njhwDoorComm = getRequest().getParameter("selectValue");
		String adrsStore = getRequest().getParameter("adrsStore");
		String name = getRequest().getParameter("name");
		String title = getRequest().getParameter("title");
		try {
			if(pId != null || pId != ""){
				entity.setPId(Long.parseLong(pId));
				entity.setName(name);
				entity.setTitle(title);
				entity.setKeyword(name);
				Map param = new HashMap();
				
				param.put("commId", Long.parseLong(njhwDoorComm));
				
				List<Map> list = objMgrManager.findListBySql("PersonnelSQL.queryNjhwDoorComm", param);
				Map par = new HashMap();
				if(list.get(0).get("COMM_IP") != null)
					par.put("adrsIp", list.get(0).get("COMM_IP"));
				par.put("adrsComm", njhwDoorComm);
				par.put("name", name);
				par.put("title", name);
				par.put("keyword", name);
				par.put("adrsStore", adrsStore);
				
				objMgrManager.saveJzsbLock(entity,par);
				//long nodeId = entity.getNodeId();
				
				//插入 第二个表的数据。事物回滚，则将方法放入到 一个save 方法里。通过参数全部传入进去。
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
	* @Title: orgTree 
	* @Description: TODO
	* @author WXJ
	* @date 2013-4-16 下午06:03:59 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String objTree() throws Exception {
		return SUCCESS;
	}

	
	/**
     * 
    * @Title: orgTree 
    * @Description: TODO
    * @author WXJ
    * @date 2013-4-16 下午06:03:59 
    * @param @return
    * @param @throws Exception    
    * @return String 
    * @throws
     */
    public String objTreeMenu() throws Exception {
        return SUCCESS;
    }
	
	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午05:31:31 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String objAdminTree() throws Exception {
		return SUCCESS;
	}
	/**
	 * 
	* @Title: orgTreeData 
	* @Description: TODO
	* @author WXJ
	* @date 2013-4-16 下午06:04:04 
	* @param @return
	* @param @throws Exception    
	* @return String 
	* @throws
	 */
	public String objTreeData() throws Exception {
		
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			if ("S".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}			
			if ("D".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjDevTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			if ("M".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjMenuTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String jzjgTreeData(){
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			String jzjgXmlStr = objMgrManager.getJzjgTreeData(id, getContextPath(), type, "S");
			Struts2Util.renderXml(jzjgXmlStr, "encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public String jzsbTreeData(){
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			String jzjgXmlStr = objMgrManager.getJzsbTreeData(id, getContextPath(), type, "D");
			Struts2Util.renderXml(jzjgXmlStr, "encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	public String jzsbTreeData(){
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			String jzjgXmlStr = objMgrManager.getJzsbTreeData(id, getContextPath(), type, "D");
			Struts2Util.renderXml(jzjgXmlStr, "encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String objTreeDataDev() throws Exception {
		
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			if ("S".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}			
			if ("D".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjDevTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			if ("M".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjMenuTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-12 下午08:31:47 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String menuTreeDataDev() throws Exception {
		
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			if ("S".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getMenuTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}			
			if ("D".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getMenuDevTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			if ("M".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getMenuMenuTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 /**
	* @Description
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午05:53:48 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String objAdminTreeDataDev() throws Exception {
		
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			if ("S".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}			
			if ("D".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjAdminDevTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			if ("M".equals(resType)) {
				Struts2Util.renderXml(
						objMgrManager.getObjAdminMenuTreeData(id, getContextPath(),type,resType),
						"encoding:UTF-8", "no-cache:true");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String jzsbLightListFath(){
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyWord = Struts2Util.getParameter("keyword");
		String memo = Struts2Util.getParameter("memo");
		String link = Struts2Util.getParameter("link");
		int paged = page.getPageNo();   //当前第几页
		int pages = 1;   //当前第几页 整型
		if(paged == 0){
			pages = 1;
		}else{
			pages = paged;
		}
		int pageCount = 1;		//分页总数
		try {
			page.setPageSize(14);	//每页查询10条。貌似没效果，因为sql语句拥有pageMax pageMin
			page.setPageNo(pages);	//记录当前页数， 该函数有用
			
			Map param = new HashMap();
			param.put("pageMax", page.getPageNo() * 14);
			param.put("pageMin", (page.getPageNo() - 1) * 14);
			
			//条件查询专用
			param.put("name", name);
			param.put("title", title);
			param.put("keyword", keyWord);
			param.put("memo", memo);
			param.put("link", link);
			
			List<Objtank> list = objMgrManager.findObjTankAll("PersonnelSQL.queryLightListByFath", param); //list为查询出的集合
			if(list.size() == 0){
				page.setPageNo(pages-1);
				param.put("pageMax", (pages -1 < 1 ? 1 : pages - 1) * 14);
				param.put("pageMin", (pages - 2 < 0 ? 0 : pages - 2) * 14);
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", keyWord);
				param.put("memo", memo);
				param.put("link", link);
				list = objMgrManager.findObjTankAll("PersonnelSQL.queryLightListByFath", param); //list为查询出的集合
			}
			List<Map> listCount = objMgrManager.queryListLockCount("PersonnelSQL.queryLightCountByFath",param);  //listCount为查询出总数
			pageCount = Integer.parseInt(listCount.get(0).get("CUN").toString());
			
			//房间 楼层 集合遍历。
			for(int i=0;i<list.size();i++){
				if(!"R".equals(list.get(i).getIco())){
					list.get(i).setLink(list.get(i).getMemo());
					list.get(i).setMemo("");
				}
			}
			page.setResult(list);	//将查询的列表set到page里
			page.setTotalCount(pageCount);  //记录总条数    		这函数有效果
			
			getRequest().setAttribute("page", page);  //集合查询
			getRequest().setAttribute("extResType", 1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String jzsbAirCondListFath(){
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyWord = Struts2Util.getParameter("keyword");
		String memo = Struts2Util.getParameter("memo");
		String link = Struts2Util.getParameter("link");
		int paged = page.getPageNo();   //当前第几页
		int pages = 1;   //当前第几页 整型
		if(paged == 0){
			pages = 1;
		}else{
			pages = paged;
		}
		int pageCount = 1;		//分页总数
		try {
			page.setPageSize(14);	//每页查询10条。貌似没效果，因为sql语句拥有pageMax pageMin
			page.setPageNo(pages);	//记录当前页数， 该函数有用
			
			Map param = new HashMap();
			param.put("pageMax", page.getPageNo() * 14);
			param.put("pageMin", (page.getPageNo() - 1) * 14);
			
			//条件查询专用
			param.put("name", name);
			param.put("title", title);
			param.put("keyword", keyWord);
			param.put("memo", memo);
			param.put("link", link);
			
			List<Objtank> list = objMgrManager.findObjTankAll("PersonnelSQL.queryAirCondListByFath", param); //list为查询出的集合
			if(list.size() == 0){
				page.setPageNo(pages-1);
				param.put("pageMax", (pages -1 < 1 ? 1 : pages - 1) * 14);
				param.put("pageMin", (pages - 2 < 0 ? 0 : pages - 2) * 14);
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", keyWord);
				param.put("memo", memo);
				param.put("link", link);
				list = objMgrManager.findObjTankAll("PersonnelSQL.queryAirCondListByFath", param); //list为查询出的集合
			}
			List<Map> listCount = objMgrManager.queryListLockCount("PersonnelSQL.queryAirCondCountByFath",param);  //listCount为查询出总数
			pageCount = Integer.parseInt(listCount.get(0).get("CUN").toString());
			
			//房间 楼层 集合遍历。
			for(int i=0;i<list.size();i++){
				if(!"R".equals(list.get(i).getIco())){
					list.get(i).setLink(list.get(i).getMemo());
					list.get(i).setMemo("");
				}
			}
			page.setResult(list);	//将查询的列表set到page里
			page.setTotalCount(pageCount);  //记录总条数    		这函数有效果
			
			getRequest().setAttribute("page", page);  //集合查询
			getRequest().setAttribute("extResType", 1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String jzsbLockListFath(){
		String name = Struts2Util.getParameter("name");
		String title = Struts2Util.getParameter("title");
		String keyWord = Struts2Util.getParameter("keyword");
		String memo = Struts2Util.getParameter("memo");
		String link = Struts2Util.getParameter("link");
		int paged = page.getPageNo();   //当前第几页
		int pages = 1;   //当前第几页 整型
		if(paged == 0){
			pages = 1;
		}else{
			pages = paged;
		}
		int pageCount = 1;		//分页总数
		try {
			page.setPageSize(14);	//每页查询10条。貌似没效果，因为sql语句拥有pageMax pageMin
			page.setPageNo(pages);	//记录当前页数， 该函数有用
			
			Map param = new HashMap();
			param.put("pageMax", page.getPageNo() * 14);
			param.put("pageMin", (page.getPageNo() - 1) * 14);
			
			//条件查询专用
			param.put("name", name);
			param.put("title", title);
			param.put("keyword", keyWord);
			param.put("memo", memo);
			param.put("link", link);
			
			List<Objtank> list = objMgrManager.findObjTankAll("PersonnelSQL.queryLockListByFath", param); //list为查询出的集合
			if(list.size() == 0){
				page.setPageNo(pages-1);
				param.put("pageMax", (pages -1 < 1 ? 1 : pages - 1) * 14);
				param.put("pageMin", (pages - 2 < 0 ? 0 : pages - 2) * 14);
				param.put("name", name);
				param.put("title", title);
				param.put("keyword", keyWord);
				param.put("memo", memo);
				param.put("link", link);
				list = objMgrManager.findObjTankAll("PersonnelSQL.queryLockListByFath", param); //list为查询出的集合
			}
			List<Map> listCount = objMgrManager.queryListLockCount("PersonnelSQL.queryLockCountByFath",param);  //listCount为查询出总数
			pageCount = Integer.parseInt(listCount.get(0).get("CUN").toString());
			
			//房间 楼层 集合遍历。
			for(int i=0;i<list.size();i++){
				if(!"R".equals(list.get(i).getIco())){
					list.get(i).setLink(list.get(i).getMemo());
					list.get(i).setMemo("");
				}
			}
			for(int i=0;i<list.size();i++){
				if(list.get(i).getObjAttrib() == "" || list.get(i).getObjAttrib() == null){
					list.get(i).setObjAttrib("请关联通讯机");
				}else{
					list.get(i).setObjAttrib(list.get(i).getObjAttrib() + "号通讯机");
				}
			}
			
			page.setResult(list);	//将查询的列表set到page里
			page.setTotalCount(pageCount);  //记录总条数    		这函数有效果
			
			getRequest().setAttribute("page", page);  //集合查询
			getRequest().setAttribute("extResType", 1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String objTreeSelect() throws Exception {
		return SUCCESS;
	}
	/**
	 * 取得资源树的数据以xml的形式传送到页面
	 * @return 
	 * @throws Exception
	 */	
	public String objTreeSelectData() throws Exception {
		
		String type = getParameter("type");
		String id = getParameter("id");
		try {
			Struts2Util.renderXml(
					objMgrManager.getObjTreeSelectData(id, getContextPath(),type),
					"encoding:UTF-8", "no-cache:true");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 开发者：ywl
	 * 时间：2013-8-7
	 */
	public String mergeHomeById(){
		Map param = new HashMap();
		if(Integer.parseInt(_chk[0]) > Integer.parseInt(_chk[1])){ //如果602 > 601
			param.put("minrid", _chk[1]);  // 601 id
			param.put("maxrid", _chk[0]);    // 602 id
		}else{
			param.put("minrid", _chk[0]);    // 601 id 
			param.put("maxrid", _chk[1]);  // 602 id
		}
			
		try {
			objMgrManager.updateHomeById(param); //合并房间 及设备
		} catch (Exception e) {
			e.printStackTrace();
		}
		//objMgrManager.updateObjPid(param);   //合并房间下的设备
		objMgrManager.delHomeByNid(param);   //删除被合并的房间
		return RELOAD;
	}
	
	/**
	 * 资源维护主页面
	 * @return 
	 * @throws Exception
	 */
	public String orgFrame() throws Exception {
		return SUCCESS;
	}

	 /**
	* @Description 功能菜单
	* @Author：zhujiabiao
	* @Date 2013-8-12 上午11:09:52 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String orgIndex(){
		return SUCCESS;
	}
	
	public String objMenu(){
		return SUCCESS;
	}
	
	 /**
	* @Description admin 人员管理 
	* @Author：zhujiabiao
	* @Date 2013-8-7 下午05:03:43 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public String orgAdminFrame() {
		return SUCCESS;
	}

	public Page<Objtank> getPage() {
		return page;
	}
	public void setPage(Page<Objtank> page) {
		this.page = page;
	}
	public ObjMgrManager getObjMgrManager() {
		return objMgrManager;
	}
	public void setObjMgrManager(ObjMgrManager objMgrManager) {
		this.objMgrManager = objMgrManager;
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
	public Objtank getEntity() {
		return entity;
	}
	public void setEntity(Objtank entity) {
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
	public Page<DoorControllerModel> getPageDoorControllerModel() {
		return pageDoorControllerModel;
	}
	public void setPageDoorControllerModel(
			Page<DoorControllerModel> pageDoorControllerModel) {
		this.pageDoorControllerModel = pageDoorControllerModel;
	}
	public void setDoorControlToAppService(DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}
	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}
}
