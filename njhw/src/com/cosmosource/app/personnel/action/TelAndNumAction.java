package com.cosmosource.app.personnel.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;

import com.cosmosource.app.entity.EmOrgRes;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.entity.TcIpTel;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.app.ldap.service.LDAPService;
import com.cosmosource.app.personnel.service.OrgMgrManager;
import com.cosmosource.app.personnel.service.TelAndNumManager;
import com.cosmosource.app.personnel_e.service.E4PersonnelManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * 
 * @description: 话机和号码管理
 * @author herb
 * @date May 15, 2013 11:11:26 AM
 */
@SuppressWarnings("unchecked")
public class TelAndNumAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1950819664028191368L;
	// 电话或者
	private TelAndNumManager telAndNumManager;
	private Page<HashMap> page = new Page<HashMap>();
	private OrgMgrManager orgMgrManager;
	
	private E4PersonnelManager e4PersonnelManager;
	
	public void setE4PersonnelManager(E4PersonnelManager e4PersonnelManager) {
		this.e4PersonnelManager = e4PersonnelManager;
	}
	
	
	/**电话类型
	 * author zhujiabiao 
	 * 2013-7-15 下午2:46:00 
	 */
	private Integer telType;

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @title: index
	 * @description: 首页
	 * @author herb
	 * @return
	 * @date May 17, 2013 1:27:43 PM
	 * @throws
	 */
	public String index() {
		return SUCCESS;
	}

	/**
	 * 
	 * @title: index
	 * @description:
	 * @author herb
	 * @return
	 * @date May 16, 2013 1:13:51 PM
	 * @throws
	 */
	public String telAndNumber() {
		try {
			String unitOrgId = getRequest().getSession().getAttribute(
					Constants.ORG_ID).toString();
			// 查询全部的处室
			List<Org> orgList = telAndNumManager.findUnitSub(Long
					.valueOf(unitOrgId));
			this.getRequest().setAttribute("orgList", orgList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long uid = 0;
		
		try{
			uid = Long.parseLong(getRequest().getSession().getAttribute(Constants.USER_ID).toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		if(uid != 0 && e4PersonnelManager.isE4Personnel(uid)){
			return "newPage";
		}
		
		return SUCCESS;
	}

	/**
	 * 
	 * @title: telAndNumber
	 * @description: 话机或分配
	 * @author herb
	 * @return
	 * @date May 15, 2013 5:01:17 PM
	 * @throws
	 */
	public String telAndNumberList() {
		try {
			String telNum = getRequest().getParameter("telNum");
			String disStatus = getRequest().getParameter("disStatus");
			String orgId = getRequest().getParameter("orgId");
			String permit = getRequest().getParameter("permit");
			String resType = getRequest().getParameter("resType");
			String displayName = getRequest().getParameter("displayName");
			
			getRequest().setAttribute("telNum", telNum);
			getRequest().setAttribute("disStatus", disStatus);
			getRequest().setAttribute("orgId", orgId);
			getRequest().setAttribute("permit", permit);
			getRequest().setAttribute("resType", resType);
			getRequest().setAttribute("displayName", displayName);
			
			if (null == orgId || orgId.trim().equals("")) {

			}
			String unitOrgId = getRequest().getSession().getAttribute(
					Constants.ORG_ID).toString();
			Map condition = new HashMap();
			condition.put("telNum", telNum);
			condition.put("disStatus", disStatus);
			condition.put("orgId", orgId);
			condition.put("unitOrgId", unitOrgId);
			condition.put("permit", permit);
			condition.put("resType", resType);
			condition.put("displayName", displayName);
			condition.put("eorType", EmOrgRes.EOR_TYPE_PHONE);
			page.setPageNo(Integer.valueOf(getRequest().getParameter("pageNo")));
			page.setPageSize(Integer.valueOf(getRequest().getParameter("pageSize")));
			// 得到分页的机构列表
			page = telAndNumManager.selectTelAndNumPageList(page, condition);
			if ((null == page.getResult() || page.getResult().size() < 1)
					&& page.getPageNo() > 2) {// 如果当前页没有数据,pageNo-1
				page.setPageNo(page.getPageNo() - 1);
				page = telAndNumManager
						.selectTelAndNumPageList(page, condition);
			}
			
			Map map = new HashMap();
			map.put("eorType", EmOrgRes.EOR_TYPE_PHONE);
			map.put("unitOrgId", unitOrgId);
			Integer unAllocatedTel = telAndNumManager.getUnallocatedTel(map);
			Integer allocatedTel = telAndNumManager.getAllocatedTel(map);
			Integer totalTel = 0;
			totalTel = allocatedTel + unAllocatedTel; 
			getRequest().setAttribute("unAllocatedTel", unAllocatedTel);
			getRequest().setAttribute("allocatedTel", allocatedTel);
			getRequest().setAttribute("unAllocatedTel", unAllocatedTel);
			getRequest().setAttribute("allocatedTel", allocatedTel);
			getRequest().setAttribute("totalTel", totalTel);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long uid = 0;
		
		try{
			uid = Long.parseLong(getRequest().getSession().getAttribute(Constants.USER_ID).toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		if(uid != 0 && e4PersonnelManager.isE4Personnel(uid)){
			return "E-buildingTelAndNumberList";
		}
		
		return SUCCESS;
	}

	/**
	 * 
	 * @title: distribute
	 * @description: ip话机分配
	 * @author herb
	 * @return
	 * @date May 16, 2013 7:34:53 PM
	 * @throws
	 */
	public String distribute() {
		try {
			/* 委办局人员信息 */
			//String userId = this.getRequest().getSession().getAttribute(
					//Constants.USER_ID).toString();
			//Users user = (Users) telAndNumManager.findById(Users.class, Long
					//.valueOf(userId));
			// 申请日期
			//String sysDate = (new SimpleDateFormat("yyyy-MM-dd"))
					//.format(new Date());
			//NjhwUsersExp exp = (NjhwUsersExp) telAndNumManager
					//.findUserAllInfo(Long.valueOf(userId));
			//exp.setUepBak1(sysDate);
			//this.getRequest().setAttribute("unitUserExp", exp);
			//this.getRequest().setAttribute("unitUserInfo", user);

			String telID = this.getRequest().getParameter("telID");
			//String oldUserId = this.getRequest().getParameter("userId");
			//NjhwUsersExp oldUserExp = (NjhwUsersExp) telAndNumManager
					//.findUserAllInfo(Long.valueOf(oldUserId));
			//Users oldUser = (Users) telAndNumManager.findById(Users.class, Long
					//.valueOf(oldUserId));
			TcIpTel Tel = (TcIpTel) telAndNumManager.findById(TcIpTel.class,
					Long.valueOf(telID));
			//TcIpTel oldTel = (TcIpTel) telAndNumManager.findById(TcIpTel.class,
					//oldUserExp.getTelId());
			//String orgName = "";
			//if(oldUser != null){
				//Org orgUnit = telAndNumManager.findUnitById(oldUser.getOrgId());
				//orgName = orgUnit.getName();
			//}
			String telmac_chg=Tel.getTelMac();//将telmac按照— — —显示
			telmac_chg=StringUtils.replace(telmac_chg, "SEP", "");
			telmac_chg=StringUtil.SplitWithChar(telmac_chg, 2, "-");
			//newTel.setTelMac(telmac_chg);
			//this.getRequest().setAttribute("orgName", orgName);
			this.getRequest().setAttribute("telmac_chg", telmac_chg);
			this.getRequest().setAttribute("Tel", Tel);
			//this.getRequest().setAttribute("oldTel", oldTel);
			//this.getRequest().setAttribute("oldUserExp", oldUserExp);
			//this.getRequest().setAttribute("oldUser", oldUser);
			//mac = StringUtils.replace(mac, "SEP", "");
			//mac = StringUtil.SplitWithChar(mac, 2, "-");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long uid = 0;
		
		try{
			uid = Long.parseLong(getRequest().getSession().getAttribute(Constants.USER_ID).toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		if(uid != 0 && e4PersonnelManager.isE4Personnel(uid)){
			return "E-buildingTelAndNumberDistribute";
		}
		
		
		
		
		return SUCCESS;
	}

	/**
	 * 
	 * @title: modifyDistrbute
	 * @description: 重新分配话机
	 * @author herb
	 * @return
	 * @date May 16, 2013 7:36:38 PM
	 * @throws
	 */
	public String modifyDistrbute() {
		try {
			/* 委办局人员信息 */
			/*String userId = this.getRequest().getSession().getAttribute(
					Constants.USER_ID).toString();
			Users user = (Users) telAndNumManager.findById(Users.class, Long
					.valueOf(userId));*/
			// 申请日期
			//String sysDate = (new SimpleDateFormat("yyyy-MM-dd"))
					//.format(new Date());
			//NjhwUsersExp exp = (NjhwUsersExp) telAndNumManager
					//.findUserAllInfo(Long.valueOf(userId));
			//exp.setUepBak1(sysDate);
			//this.getRequest().setAttribute("unitUserExp", exp);
			//this.getRequest().setAttribute("unitUserInfo", user);

			String telID = this.getRequest().getParameter("telID");
			String oldUserId = this.getRequest().getParameter("userId");
			//NjhwUsersExp oldUserExp = (NjhwUsersExp) telAndNumManager
					//.findUserAllInfo(Long.valueOf(oldUserId));
			Users oldUser = (Users) telAndNumManager.findById(Users.class, Long
					.valueOf(oldUserId));
			TcIpTel Tel = (TcIpTel) telAndNumManager.findById(TcIpTel.class,
					Long.valueOf(telID));
			//TcIpTel oldTel = (TcIpTel) telAndNumManager.findById(TcIpTel.class,
					//oldUserExp.getTelId());
			String orgName = "";
			if(oldUser != null){
				Org orgUnit = telAndNumManager.findUnitById(oldUser.getOrgId());
				orgName = orgUnit.getName();
			}
			String telmac_chg=Tel.getTelMac();//split telMac with '-'
			telmac_chg=StringUtils.replace(telmac_chg, "SEP", "");
			telmac_chg=StringUtil.SplitWithChar(telmac_chg, 2, "-");
			//newTel.setTelMac(telmac_chg);
			this.getRequest().setAttribute("orgName", orgName);
			this.getRequest().setAttribute("telmac_chg", telmac_chg);
			this.getRequest().setAttribute("Tel", Tel);
			//this.getRequest().setAttribute("oldTel", oldTel);
			//this.getRequest().setAttribute("oldUserExp", oldUserExp);
			this.getRequest().setAttribute("oldUser", oldUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @title: revertDistrbute
	 * @description: 取消话机分配
	 * @author herb
	 * @return
	 * @date May 16, 2013 7:38:15 PM
	 * @throws
	 */
	public String revertDistrbute() {
		JSONObject result = new JSONObject();
		result.put("result", "0");
		try {
			String telID = this.getRequest().getParameter("telID");
			telAndNumManager.updateCancelUserTel(telID);
			result.put("result", "1");
			Struts2Util.renderJson(result.toString(), "encoding:UTF-8",
					"no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @title: refreshUser
	 * @description: 查询异步刷新页面用户信息
	 * @author herb
	 * @return
	 * @date May 18, 2013 9:04:10 PM
	 * @throws
	 */
	public String refreshUser() {
		String userId = this.getRequest().getParameter("userId");
		try {
			JSONObject result = new JSONObject();
			if (null != userId && userId.trim().length() > 1) {
				NjhwUsersExp exp = (NjhwUsersExp) telAndNumManager
						.findUserAllInfo(Long.valueOf(userId));
				if (null != exp){
					result.put("userid", exp.getUserid());
					result.put("roomInfo", exp.getRoomInfo());
					result.put("roomId", exp.getRoomId());
				}
				Struts2Util.renderJson(result.toString(), "encoding:UTF-8",
						"no-cache:true");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @title: unitDistribute 
	* @description: 单位话机申请
	* @author herb
	* @return
	* @date May 19, 2013 1:59:08 PM     
	* @throws
	 */
	public String unitDistribute(){
		/* 委办局人员信息 */
		String userId = this.getRequest().getSession().getAttribute(
				Constants.USER_ID).toString();
		Users user = (Users) telAndNumManager.findById(Users.class, Long
				.valueOf(userId));
		// 申请日期
		String sysDate = (new SimpleDateFormat("yyyy-MM-dd"))
				.format(new Date());
		NjhwUsersExp exp = (NjhwUsersExp) telAndNumManager
				.findUserAllInfo(Long.valueOf(userId));
		exp.setUepBak1(sysDate);
		this.getRequest().setAttribute("unitUserExp", exp);
		this.getRequest().setAttribute("unitUserInfo", user);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Title: orgTree
	 * @Description: 人员选择树
	 * @author WXJ
	 * @date 2013-5-8 上午11:11:21
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public String orgTreeUser() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: orgTreeData
	 * @Description: 人员选择树
	 * @author WXJ
	 * @date 2013-5-8 上午11:11:55
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public String orgTreeUserData() throws Exception {
		Integer telType=Integer.valueOf(getParameter("telType"));
		String type = getParameter("type");
		String id = getParameter("id");
		Struts2Util.renderXml(orgMgrManager.getOrgTreeUserCheckinData(id,
				getContextPath(), type,telType), "encoding:UTF-8", "no-cache:true");

		return null;
	}
	/**
	 * 
	* @title: saveUnitApp 
	* @description: 保存单位电话申请
	* @author herb
	* @return
	* @date May 19, 2013 11:52:51 PM     
	* @throws
	 */
	public String saveUnitApp(){
		String telMac = this.getRequest().getParameter("telMac").trim();
		String telNum = this.getRequest().getParameter("telNum").trim();
		String permits = this.getRequest().getParameter("permits");
		String functions = this.getRequest().getParameter("functions");
		String [] permitArr = permits.split(","); 
		String [] functionArr = functions.split(","); 
		if (null != telMac || null != telNum) {
			TcIpTel tel = new TcIpTel();
			tel.setTelMac(telMac);
			tel.setTelNum(telNum);
			tel.setTelIdd("1");
			tel.setTelddd("1");
			tel.setTelLocal("1");
			tel.setTelCornet("1");
			tel.setTelForword("1");
			tel.setTelCw("1");
			for (String p : permitArr){
				if (p.equals("1")){
					tel.setTelIdd("2");
				} else if (p.equals("2")){
					tel.setTelddd("2");
				} else if (p.equals("3")){
					tel.setTelLocal("2");
				} else if (p.equals("4")){
					tel.setTelCornet("2");
				}
			}
			for (String f : functionArr){
				if (f.equals("1")){
					tel.setTelForword("2");
				} else if (f.equals("2")){
					tel.setTelCw("2");
				}
			}
			tel.setReqDate(new Date());
			tel.setActiveFlag("0");
			telAndNumManager.saveUnitTel(tel);
		}
		return SUCCESS;
	}
	/**
	 * 
	* @title: saveTelDisChange 
	* @description: 保存电话变更分配
	* @author herb
	* @return
	* @date May 18, 2013 10:31:58 PM     
	* @throws
	 */
	public String saveTelDisChange(){
		JSONObject result = new JSONObject();
		result.put("result", 0);
		try {
			String userId = this.getRequest().getParameter("userId");
			String telId = this.getRequest().getParameter("telId");
			String permits = this.getRequest().getParameter("permits");
			String functions = this.getRequest().getParameter("functions");
			String telExt = this.getRequest().getParameter("telExt");
			String oldUserId = this.getRequest().getParameter("oldUserId");
			String telType = this.getRequest().getParameter("telType");
			String telNum = this.getRequest().getParameter("telNum");
			String [] permitArr = permits.split(","); 
			String [] functionArr = functions.split(","); 
			String loginUid;
			String oldLoginUid;
			LDAPService ldapservice=new LDAPService();
			if (userId != null) {				
				if (!oldUserId.equals(userId)){
					telAndNumManager.updateUserTel(userId,telId);//update UserTel
					telAndNumManager.saveRemoveUserTel(oldUserId,telType);//delete oldUserTel;
					Users  u=telAndNumManager.findUsersByUserId(userId);
					Users  oldu=telAndNumManager.findUsersByUserId(oldUserId);
					loginUid=u.getLoginUid();
					oldLoginUid=oldu.getLoginUid();
					UserInfo uinfo= ldapservice.findUserByLoginUid(loginUid);
					UserInfo olduinfo= ldapservice.findUserByLoginUid(oldLoginUid);
					if(telType.equals("1")){
						uinfo.setTelNum(telNum);
						olduinfo.setTelNum(null);
					}else if(telType.equals("2")){
						uinfo.setUepFax(telNum);
						olduinfo.setUepFax(null);
					}else if(telType.equals("3")){
						uinfo.setWebFax(telNum);
						olduinfo.setWebFax(null);
					}
					new LDAPService().updateUser(uinfo);
					new LDAPService().updateUser(olduinfo);
					
				}else{
					Users  u=telAndNumManager.findUsersByUserId(userId);
					loginUid=u.getLoginUid();
					UserInfo uinfo= ldapservice.findUserByLoginUid(loginUid);
					if(telType.equals("1")){
						uinfo.setTelNum(telNum);
					}else if(telType.equals("2")){
						uinfo.setUepFax(telNum);
					}else if(telType.equals("3")){
						uinfo.setWebFax(telNum);
					}
					new LDAPService().updateUser(uinfo);
				}
			}
            //update tc_ip_tel every time no matter what happened
			if (null != telId) {
				TcIpTel tel = (TcIpTel)telAndNumManager.findById(TcIpTel.class, Long.valueOf(telId));
				tel.setTelIdd("1");
				tel.setTelddd("1");
				tel.setTelLocal("1");
				tel.setTelCornet("1");
				tel.setTelForword("1");
				tel.setTelCw("1");
				for (String p : permitArr){
					if (p.equals("1")){
						tel.setTelIdd("2");
					} else if (p.equals("2")){
						tel.setTelddd("2");
					} else if (p.equals("3")){
						tel.setTelLocal("2");
					} else if (p.equals("4")){
						tel.setTelCornet("2");
					}
				}
				for (String f : functionArr){
					if (f.equals("1")){
						tel.setTelForword("2");
					} else if (f.equals("2")){
						tel.setTelCw("2");
					}
				}
				tel.setTelExt(telExt);
				tel.setReqDate(new Date());
				tel.setActiveFlag("0");
				telAndNumManager.updateTelInfo(tel);
			}
			result.put("result", 1);
			Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @title: saveTelDis 
	* @description: 保存新分配电话
	* @author herb
	* @return
	* @date May 19, 2013 1:25:04 PM     
	* @throws
	 */
	public String saveTelDis(){
		JSONObject result = new JSONObject();
		result.put("result", 0);
		try {
			String userId = this.getRequest().getParameter("userId");
			String telId = this.getRequest().getParameter("telId");
			String telNum = this.getRequest().getParameter("telNum");
			String permits = this.getRequest().getParameter("permits");
			String functions = this.getRequest().getParameter("functions");
			String telExt=this.getRequest().getParameter("telExt");
			String [] permitArr = permits.split(","); 
			String [] functionArr = functions.split(","); 
			if (userId != null) {//保存用户话机分配,新ip电话号
				telAndNumManager.updateUserTel(userId,telId);//覆盖掉原来号码的信息
			}
			
			String loginUid;
			LDAPService ldapservice=new LDAPService();
			Users  u=telAndNumManager.findUsersByUserId(userId);
			loginUid=u.getLoginUid();
			UserInfo uinfo= ldapservice.findUserByLoginUid(loginUid);
			if(telType.equals(1)){
				uinfo.setTelNum(telNum);
			}else if(telType.equals(2)){
				uinfo.setUepFax(telNum);
			}else if(telType.equals(3)){
				uinfo.setWebFax(telNum);
			}
			new LDAPService().updateUser(uinfo);
			
			
			if (null != telId) {
				TcIpTel tel = (TcIpTel)telAndNumManager.findById(TcIpTel.class, Long.valueOf(telId));
				tel.setTelIdd("1");
				tel.setTelddd("1");
				tel.setTelLocal("1");
				tel.setTelCornet("1");
				tel.setTelForword("1");
				tel.setTelCw("1");
				for (String p : permitArr){
					if (p.equals("1")){
						tel.setTelIdd("2");
					} else if (p.equals("2")){
						tel.setTelddd("2");
					} else if (p.equals("3")){
						tel.setTelLocal("2");
					} else if (p.equals("4")){
						tel.setTelCornet("2");
					}
				}
				for (String f : functionArr){
					if (f.equals("1")){
						tel.setTelForword("2");
					} else if (f.equals("2")){
						tel.setTelCw("2");
					}
				}
				tel.setTelExt(telExt);
				tel.setActiveFlag("0");
				tel.setReqDate(new Date());
				telAndNumManager.updateTelInfo(tel);
			}
			result.put("result", 1);
			Struts2Util.renderJson(result, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public TelAndNumManager getTelAndNumManager() {
		return telAndNumManager;
	}

	public void setTelAndNumManager(TelAndNumManager telAndNumManager) {
		this.telAndNumManager = telAndNumManager;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public OrgMgrManager getOrgMgrManager() {
		return orgMgrManager;
	}

	public void setOrgMgrManager(OrgMgrManager orgMgrManager) {
		this.orgMgrManager = orgMgrManager;
	}

	public Integer getTelType() {
		return telType;
	}

	public void setTelType(Integer telType) {
		this.telType = telType;
	}

}
