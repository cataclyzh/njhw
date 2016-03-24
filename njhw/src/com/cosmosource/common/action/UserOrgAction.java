/**
 * <p>文件名: UserOrgAction.java</p>
 * <p>描述：(修改用户与机构信息)</p>
 * <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
 * <p>公司: Cosmosource Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间： 2010-12-23 上午11:34:49 
 * @作者：WXJ
 * @版本： V1.0
 * <p>类修改者		 修改日期			修改说明   </p>   
 * 
 */
package com.cosmosource.common.action;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;

import com.cosmosource.app.entity.NjhwTscard;
import com.cosmosource.app.entity.NjhwUsersExp;
import com.cosmosource.app.entity.NjhwUsersPlatenum;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.app.ldap.service.LDAPService;
import com.cosmosource.app.port.model.SmsMessage;
import com.cosmosource.app.port.serviceimpl.CAAccessToLoginService;
import com.cosmosource.app.port.serviceimpl.PersonCardQueryToAppService;
import com.cosmosource.app.port.serviceimpl.SmsSendMessageService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Sendemail;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.entity.TAcUser;
import com.cosmosource.common.service.AuthorityManager;
import com.cosmosource.common.service.OrgMgrManager;
import com.cosmosource.common.service.UserMgrManager;

/**
 * @类描述: (修改用户信息---包括用户表中密码与用户表中的地址电话邮件信息)
 * @作者： WXJ
 */
public class UserOrgAction extends BaseAction<TAcUser> {
	private static final long serialVersionUID = 1L;

	private Long userid;
	private OrgMgrManager orgMgrManager;// 机构的管理类Manager
	private UserMgrManager userMgrManager;// 用户的管理类Manager
	private TAcUser userEntity;// 用户实体
	private TAcOrg orgEntity;// 机构实体
	private String taxno;// 纳税人识别号
	private String loginname;// 供应商编码--改为 --用户登录名
	private String hiddenPwd;// 隐藏密码 add by WXJ
	private String newpw1;// 新密码 add by WXJ
	private String username;// 联系人姓名
	private String phone;// 联系人电话
	private String address;// 联系人地址
	private String email;// 联系人邮箱
	private AuthorityManager authorityManager;// 安全相关实体Manager
	// 注入发送验证码的对象
	private CAAccessToLoginService cAAccessToLoginService;
	private SmsSendMessageService  smsSendMessageService;
	//注入验证市民卡的对象
	private PersonCardQueryToAppService personCardQueryToAppService;

	
	// 创建用户信息的对象
	private Users userInfo = new Users();

	// 创建用户扩展表的对象
	private NjhwUsersExp usersExpInfo;
	// 创建卡的对象
	private NjhwTscard scardInfo;
	//创建车牌号的对象
	  private  NjhwUsersPlatenum  njhwUsersPlatenum ;
	

	public AuthorityManager getAuthorityManager() {
		return authorityManager;
	}

	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	public TAcUser getModel() {
		return this.userEntity;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	/**
	 * 
	 * @描述: 初始化 找回用户密码
	 * @return
	 * @throws Exception
	 *             add by WXJ 2010-12-23
	 */
	public String getPassword() throws Exception {
		return SUCCESS;
	}

	public String sendEmail() throws Exception {
		try {
			taxno = (String) getParameter("taxno");
			loginname = (String) getParameter("loginname");
			TAcUser user = userMgrManager.getOrgidByLoginname(loginname);
			if (user == null || user.getUsercode() == null) {
				getRequest().setAttribute("retMsg", "您输入的用户名不存在!");
				setIsSuc("false");
				return ERROR;
			}

			orgEntity = (TAcOrg) orgMgrManager.findById(TAcOrg.class, user
					.getOrgid());
			// String op = "reps";
			String hostAddress = "mail.Cosmosource.com";
			String longinUserName = "Cosmosource";
			String longinUserPassword = "123456";
			String fromAddress = "service@Cosmosource.com";
			String subject = "密码重置邮件";
			String context1 = "用户名：";
			String context = " 您的密码已重置为";
			String context2 = "。请登录平台修改您的密码";
			if (orgEntity != null) {
				if (taxno.equals(orgEntity.getTaxno())) {
					String email = user.getEmail();// 向用户表中的email地址发信息----update
													// by WXJ 2010-12-27
					if ("".equals(StringUtil.isBlank(email)) || email == null) {
						getRequest().setAttribute("retMsg",
								"系统中没有您的邮箱信息,请与管理员联系!");
						setIsSuc("false");
						return ERROR;
					} else {
						String newpass = userMgrManager.CreatePass(); // 密码明文
						String newdbpass = DigestUtils.md5Hex(newpass); // 存入数据库的md5值密码
						String username = user.getLoginname();
						user.setPassword(newdbpass);
						user.setPlainpassword(newpass);
						userMgrManager.updateUser(user);
						try {
							Sendemail.sendHTML(hostAddress, /* SMTP主机地址 */
							fromAddress, /* 发信人 */
							longinUserName, longinUserPassword, email, /* 收信人 */
							null, /* 抄送人 */
							null, /* 暗送人 */
							subject, /* 主题 */
							context1 + username + context + newpass + context2);
							getRequest().setAttribute("retMsg",
									"密码已发送至您在填写用户信息时的邮箱" + email + ",请查收!");
							setIsSuc("true");
							return SUCCESS;
							// 已成功将密码重置邮件发送至您的邮箱，请查收邮件
						} catch (Exception ex) {
							// 很抱歉，发送邮件过程中出现异常，请与管理员联系
							logger.error(ex.toString());
							getRequest().setAttribute("retMsg",
									"发送邮件过程中出现异常,请与管理员联系!");
							setIsSuc("false");
							return ERROR;
						}

					}
				} else {
					getRequest().setAttribute("retMsg",
							"您输入的纳税人识别号与用户名不符,请重新输入!");
					setIsSuc("false");
					return ERROR;
				}
			} else {
				getRequest().setAttribute("retMsg", "您输入的纳税人识别号与用户名不符,请重新输入!");
				setIsSuc("false");
				return ERROR;
			}

		} catch (Exception e) {
			logger.error(e.toString());
			getRequest().setAttribute("retMsg", "网络故障请与管理员联系!");
			setIsSuc("false");
			return ERROR;
		}
	}

	/**
	 * 
	 * @描述:个人信息的查询显示
	 * @return
	 * @throws Exception
	 * @作者： qiyanqiang
	 */
	@SuppressWarnings("unchecked")
	public String personInfoChreach() throws Exception {
		
		try {
			HashMap map = new HashMap();
		     @SuppressWarnings("unused")
			String  userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
			map.put("userId", Struts2Util.getSession().getAttribute(	Constants.USER_ID).toString());
            //个人信息的查询
			getRequest().setAttribute("personInfoList",	userMgrManager.searchPersonInfo(map));
			// 卡信息的查询
			getRequest().setAttribute("cardsInfoList",	userMgrManager.searchCardInfo(map));
			
			//调用service 层查询用户的车牌 
			//getRequest().setAttribute("userLicensePlateList",userMgrManager.searchUserLicensePlate(map));
			//调用service 层查询用户闸机、门禁、门锁
			getRequest().setAttribute("allFacilityList",userMgrManager.searchAllFacility(map));
			
		} catch (Exception e) {
		        e.printStackTrace();
		}
		
		
		return SUCCESS;

	}

	/** 
	* @title: loadPlatenum
	* @description: 加载车牌信息
	* @author qyq
	* @date 2013-05-05
	*/ 
	public String loadPlatenum() {
		JSONObject json = new JSONObject();
		try {
			Long user_id = Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString());
			List plateNumList = userMgrManager.findByHQL("select t from NjhwUsersPlatenum t where t.userid = ?", user_id);
			Struts2Util.renderJson(plateNumList, "encoding:UTF-8", "no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * @描述: 保存个人信息
	 * @作者：qiyanqiang
	 * @日期：2013-05-10
	 * @return
	 * 
	 */
	public String savePersonInfo()

	{ 	
		String userId = Struts2Util.getSession().getAttribute(
				Constants.USER_ID).toString();
		String loginId = Struts2Util.getParameter("loginId");
		boolean res = userMgrManager.findUserName(Long.valueOf(userId),loginId);
		
		if (res) {
		JSONObject resultJSON = new JSONObject();
	    resultJSON.put("msg", "failure");
	// 得到用户的id
	String userID = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
	// 进行保存的操作
	try {
		Users user = (Users) authorityManager.findById(Users.class, Long.parseLong(userID));
		
		String loginUidOld = user.getLoginUid();
		
		// 登陆名
		user.setLoginUid(Struts2Util.getParameter("loginId"));
		// 用户名
		user.setDisplayName(getRequest().getParameter("username"));
		authorityManager.updateUsers(user);
		usersExpInfo = userMgrManager.findUsersExpById(Long.parseLong(userID));
		if (usersExpInfo != null) {
			// 用户的性别
			usersExpInfo.setUepSex(getRequest().getParameter("sex"));
			// 用户的手机号码
			usersExpInfo.setUepMobile(getRequest().getParameter("phone"));
			// 电子邮箱
			usersExpInfo.setUepMail(getRequest().getParameter("email"));
			userMgrManager.updateUserExp(usersExpInfo);
		} else {

			NjhwUsersExp usersExpInfo1 = new NjhwUsersExp();
			usersExpInfo1.setUserid(Long.parseLong(userID));
			// 用户的性别
			usersExpInfo1.setUepSex(getRequest().getParameter("sex"));
			// 用户的手机号码
			usersExpInfo1.setUepMobile(getRequest().getParameter("phone"));
			// 电子邮箱
			usersExpInfo1.setUepMail(getRequest().getParameter("email"));
			userMgrManager.updateUserExp(usersExpInfo1);
		}

		resultJSON.put("msg", "success");
		
		LDAPService ldapService = new LDAPService();
		
		UserInfo userInfo = ldapService.findUserByLoginUid(loginUidOld);

		ldapService.deleteUserByLoginUid(loginUidOld);
		
		userInfo.setLoginUid(Struts2Util.getParameter("loginId"));
		userInfo.setDisplayName(StringUtil.empty2Null(getRequest().getParameter("username")));
		userInfo.setUepSex(StringUtil.empty2Null(getRequest().getParameter("sex")));
		userInfo.setUepMobile(StringUtil.empty2Null(getRequest().getParameter("phone")));
		userInfo.setUepMail(StringUtil.empty2Null(getRequest().getParameter("email")));
		
		ldapService.addUser(userInfo);

		Struts2Util.getSession().setAttribute(Constants.USER_NAME, getRequest().getParameter("username"));
	} catch (Exception e) {

		e.printStackTrace();
	}
	Struts2Util.renderJson(resultJSON.toString(), "encoding:UTF-8",
	"no-cache:true");
		}
	return null;

	}

	/**
	 * @描述: 手机号码变更弹出框
	 * @作者：qiyanqiang
	 * @日期：2013-05-10
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String sendIphoneVerificationCode() {
//		HashMap map = new HashMap();
//		map.put("userId", Struts2Util.getSession().getAttribute(
//				Constants.USER_ID).toString());
//
//		getRequest().setAttribute("phoneInfo",
//				userMgrManager.searchPersonInfo(map));
		return SUCCESS;
	}

	/**
	 * @描述: 发送手机验证码
	 * @作者：qiyanqiang
	 * @日期：2013-05-10
	 * @return
	 * 
	 */
	public String sendPhoneNumberVerificationCode() {
		JSONObject json = new JSONObject();
		try {
			// 获取登录用户的用户id
			String userID = Struts2Util.getSession().getAttribute(
					Constants.USER_ID).toString();
			// 获取ajax传过来的手机号码
			String verificationTelPhone = getRequest().getParameter("phones");
			// 调用发送手机的接口方法，获取手机的验证码
			String code = cAAccessToLoginService.randomValidateCode();
			SmsMessage sm = new SmsMessage();
			sm.setPhoneNumber(verificationTelPhone);
			sm.setSendContent("验证码："+code +"。请不要把此验证码泄露给任何人。"+"【智慧政务社区信息系统】");
			String telPhoneVerificationCode = smsSendMessageService.sendToQuarz(sm);
//			String telPhoneVerificationCode = "qqq22";
			if (code != null) {
				json.put("list", code);
				json.put("state", 1);
			} else {
				json.put("state", 0);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;

	}

	/**
	 * @描述: 手机号码的变更
	 * @作者：qiyanqiang
	 * @日期：2013-05-10
	 * @return
	 * 
	 */
	public String upDatePhoneNumber() {
		JSONObject json = new JSONObject();
		// 获取登录用户的用户id
		String userID = Struts2Util.getSession()
				.getAttribute(Constants.USER_ID).toString();
		// 获取用户扩展表的userID
		usersExpInfo = userMgrManager.findUsersExpById(Long.parseLong(userID));
		try {
			if (usersExpInfo != null) {
				usersExpInfo.setUepMobile(getRequest().getParameter(
						"verificationPhones"));
				userMgrManager.saveUserTel(usersExpInfo);
				json.put("state", 1);
			} else {
				json.put("state", 0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;

	}

	/**
	 * @描述: 查询更新登录用户名字
	 * @作者：qiyanqiang
	 * @日期：2013-05-11
	 * @return
	 */
	public String upDateLoginUserName() {
		JSONObject json = new JSONObject();
		try {
			// 获取登录的用户的
			String userId = Struts2Util.getSession().getAttribute(
					Constants.USER_ID).toString();
			String loginId = Struts2Util.getParameter("loginId");
			boolean res = userMgrManager.findUserName(Long.valueOf(userId),loginId);
			if (res) {
				json.put("state", 1);
				// 进行更新
				Users user = userMgrManager.getUsersById(Long.valueOf(userId));
				if (user != null){  
					user.setLoginUid(loginId);
					this.userMgrManager.saveLoginUser(user);
				}
			} else {
				json.put("state", 0);			
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;

	}
	
	/**
	 * @描述: 查询更新登录用户名字
	 * @作者：qiyanqiang
	 * @日期：2013-05-11
	 * @return
	 */
	public String checkLoginId() {
		try {
			// 获取登录的用户的
			String userId = Struts2Util.getSession().getAttribute(
					Constants.USER_ID).toString();
			String loginId = Struts2Util.getParameter("loginId");
			boolean res = userMgrManager.findUserName(Long.valueOf(userId),loginId);
			Struts2Util.getResponse().getWriter().print(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @描述: 查询更新登录用户名字
	 * @作者：qiyanqiang
	 * @日期：2013-05-11
	 * @return
	 */
	public String checkPassword() {
		try {
			// 获取登录的用户的
			Long userId = (Long) getSession().getAttribute(Constants.USER_ID);
			String oldpw = Struts2Util.getParameter("oldpw").trim();
			String oldpass = DigestUtils.md5Hex(oldpw).trim().substring(0, 20).toUpperCase();
			Users user = (Users) authorityManager.findById(Users.class, userId);
			Struts2Util.getResponse().getWriter().print(oldpass.equals(user.getLoginPwd().trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @描述: 修改用户密码
	 * @作者：WXJ
	 * @日期：2010-12-23
	 * @return
	 * @throws Exception
	 *             update by WXJ 2010-12-23 校验都改在前台 更新方法写入manager
	 */
	public String updatePwd() throws Exception {
		// 获取登录的用户的
		Long userId = (Long) getSession().getAttribute(Constants.USER_ID);
		String oldpw = Struts2Util.getParameter("oldpw").trim();
		String oldpass = DigestUtils.md5Hex(oldpw).trim().substring(0, 20).toUpperCase();
		Users user = (Users) authorityManager.findById(Users.class, userId);
		if (oldpass.equals(user.getLoginPwd().trim())) {
			JSONObject resultJSON = new JSONObject();
			try {
				Long userid = (Long) getSession().getAttribute(
						Constants.USER_ID);// userid
				
				if(userid == 2131){
					System.out.println("2131: " + newpw1);
				}

				String newdbpass = DigestUtils.md5Hex(newpw1); // 存入数据库的md5值密码
				
				user.setLoginPwd(newdbpass.substring(0, 20).toUpperCase());
				user.setLastUpdateDate(DateUtil
						.getDateTime("yyyy-MM-dd hh:mm:ss"));
				authorityManager.updateUsers(user);
				resultJSON.put("msg", "success");
			} catch (Exception e) {
				resultJSON.put("msg", "error");
				logger.error(e.toString());
			}
			
			new LDAPService().changePasswordByLoginUid(user.getLoginUid(), oldpw, newpw1);
			
			Struts2Util.renderJson(resultJSON.toString());
		}
		return null;
	}

	/**
	 * @描述: 检查旧密码
	 * @作者：WXJ
	 * @日期：2011-02-23
	 * @return
	 * @throws Exception
	 */
	public String checkOldPwd() throws Exception {
		Long userid = (Long) getSession().getAttribute(Constants.USER_ID);// userid
																			// 用户表id
		Users user = (Users) authorityManager.findById(Users.class, userid);
		String oldpass = DigestUtils.md5Hex(getParameter("oldpass"));

		if (oldpass.substring(0, 20).toUpperCase().equals(user.getLoginPwd())) {
			Struts2Util.renderText("true");
		} else {
			Struts2Util.renderText("false");
		}
		return null;
	}

	public void prepareInit() throws Exception {
		userid = (Long) getSession().getAttribute(Constants.USER_ID);
		if (userid != null) {
			userEntity = (TAcUser) userMgrManager.findById(TAcUser.class,
					userid);
		} else {
			userEntity = new TAcUser();
		}
	}

	/**
	 * *
	 * 
	 * @描述: 修改用户信息初始化
	 * @return
	 * @throws Exception
	 *             add by WXJ 2010-12-23
	 */
	public String init() throws Exception {
		try {
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.toString());
			return SUCCESS;
		}
	}

	/**
	 * 
	 * @描述: 修改用户信息
	 * @return
	 * @throws Exception
	 *             add by WXJ 2010-12-23
	 */
	public String update() throws Exception {
		try {
			userid = (Long) getSession().getAttribute(Constants.USER_ID);
			userEntity = (TAcUser) userMgrManager.findById(TAcUser.class,
					userid);
			userEntity.setAddress(address);
			userEntity.setUsername(username);
			userEntity.setPhone(phone);
			userEntity.setEmail(email);
			userMgrManager.updateUser(userEntity);
			setIsSuc("true");
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.toString());
			setIsSuc("false");
			return SUCCESS;
		}
	}
	
	
	/**
	 * 查询用户闸机 门禁 门锁  车牌的 操作
	 * @return 
	 * @throws Exception
	 * 作者：qiyanqiang 
	 * 时间：2013-05-17
	 
	@SuppressWarnings("unchecked")
	public String  searchAllFacilityList() throws Exception {

		HashMap map = new HashMap();
		//获取用户的id
		map.put("userId", Struts2Util.getSession().getAttribute(
				Constants.USER_ID).toString());
		//调用service 层查询用户的车牌 
		getRequest().setAttribute("userLicensePlateList",
				userMgrManager.searchPersonInfo(map));
		//调用service 层查询用户闸机、门禁、门锁
		getRequest().setAttribute("allFacilityList",
				userMgrManager.searchPersonInfo(map));
		
		return SUCCESS;
	}
	*/
	
	/**
	 * 验证市民卡
	 * 
	 * @title: checkCityCar
	 * @description: TODO
	 * @author qyq
	 * @return
	 * @date 2013-05 -17 
	 * @throws
	 */
	public String checkCityCar() {
		String cardId = Struts2Util.getParameter("nvrCard");
		try {
			JSONObject json = new JSONObject();
			// 调用市民卡接口
		
			NjhwTscard nt =  personCardQueryToAppService.queryPersonCard(cardId);
			
			if (nt == null) {
				json.put("state", "error");
			} else {
				json.put("state", "success");
			}
			Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
					"no-cache:true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/** 
	* @title: modifyCardIsLosted
	* @description: 挂失
	* @author qyq
	* @date 2013-05-05
	*/ 
	public String modifyCardIsLosted() {
		String optType = getParameter("optType");
		String cityCard = getParameter("cityCard");
		
		int num = this.userMgrManager.modifyCardIsLosted(optType, cityCard);
		if (num == 0) Struts2Util.renderText("success", "encoding:UTF-8", "no-cache:true");
		else if (num == 1) Struts2Util.renderText("cardNoExist", "encoding:UTF-8", "no-cache:true");
		else Struts2Util.renderText("fail", "encoding:UTF-8", "no-cache:true");
		return null;
	}	
	
	/** 
	* @title: adminConfirm
	* @description: 管理员确认页面
	* @author nj
	* @date 2013-07-26
	*/ 
	public String adminConfirm() {
		return SUCCESS;
	}
	
	/** 
	* @title: checkAdmin
	* @description: 管理员确认页面
	* @author hj
	* @date 2013-07-26
	*/ 
	public void checkAdmin() {
		JSONObject json = new JSONObject();
		String cardId  = getParameter("cardId");
		boolean isAdmin = userMgrManager.checkAdmin(cardId);
		json.put("isAdmin", isAdmin);

		Struts2Util.renderJson(json.toString(),"encoding:UTF-8", "no-cache:true");
	}
	

	public void setUserMgrManager(UserMgrManager userMgrManager) {
		this.userMgrManager = userMgrManager;
	}

	public void setOrgMgrManager(OrgMgrManager orgMgrManager) {
		this.orgMgrManager = orgMgrManager;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getTaxno() {
		return taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getHiddenPwd() {
		return hiddenPwd;
	}

	public void setHiddenPwd(String hiddenPwd) {
		this.hiddenPwd = hiddenPwd;
	}

	public String getNewpw1() {
		return newpw1;
	}

	public void setNewpw1(String newpw1) {
		this.newpw1 = newpw1;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Users getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Users userInfo) {
		this.userInfo = userInfo;
	}

	public NjhwUsersExp getUsersExpInfo() {
		return usersExpInfo;
	}

	public void setUsersExpInfo(NjhwUsersExp usersExpInfo) {
		this.usersExpInfo = usersExpInfo;
	}

	public NjhwTscard getScardInfo() {
		return scardInfo;
	}

	public void setScardInfo(NjhwTscard scardInfo) {
		this.scardInfo = scardInfo;
	}

	public CAAccessToLoginService getcAAccessToLoginService() {
		return cAAccessToLoginService;
	}

	public void setcAAccessToLoginService(
			CAAccessToLoginService cAAccessToLoginService) {
		this.cAAccessToLoginService = cAAccessToLoginService;
	}
	public PersonCardQueryToAppService getPersonCardQueryToAppService() {
		return personCardQueryToAppService;
	}

	public void setPersonCardQueryToAppService(
			PersonCardQueryToAppService personCardQueryToAppService) {
		this.personCardQueryToAppService = personCardQueryToAppService;
	}

	public SmsSendMessageService getSmsSendMessageService() {
		return smsSendMessageService;
	}

	public void setSmsSendMessageService(SmsSendMessageService smsSendMessageService) {
		this.smsSendMessageService = smsSendMessageService;
	}



}
