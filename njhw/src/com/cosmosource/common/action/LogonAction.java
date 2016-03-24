/**
* <p>文件名: LogonAction.java</p>
* <p>描述：用户登录Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-9-6 下午07:01:02 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

//import java.util.Date;
//import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import com.cosmosource.app.entity.Users;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.dao.ComDaoiBatis;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TAcOrg;
import com.cosmosource.common.service.AuthorityManager;
import com.cosmosource.common.service.MsgBoardManager;
import com.cosmosource.common.service.MsgBoxManager;
import com.cosmosource.common.service.UserMgrManager;
//import com.cosmosource.common.service.OrgMgrManager;

/**
* @类描述: 用户登录Action
* 登录成功后跳转到系统主页面
* @作者： WXJ
*/
public class LogonAction extends BaseAction<Users> {

	private static final long serialVersionUID = 1L;
	private AuthorityManager authorityManager;//安全相关实体Manager
	private UserMgrManager userMgrManager;//用户的管理类Manager
//	private OrgMgrManager orgMgrManager;//机构管理类Manager
	private Users entity;//用户实体
	private TAcOrg orgEntity;//机构实体
	private MsgBoardManager msgBoardManager;//公告板Manager
	private MsgBoxManager msgBoxManager;//消息Manager
	private String bulletinCount = "0";//公告数目
	private String messageCount = "0";//消息数目
	private String orgId;//机构编号
	private String userid;//用户编号
	private String hiddenTaxno;//隐藏购方纳税人识别号
	private String hiddenPwd;//隐藏密码
	private String newpw1;//新密码
	private String linkman;//联系人姓名
	private String phone;//联系人电话
	private String address;//联系人地址
	private String email;//联系人邮箱
	private String isInputQA;
	private boolean showOnlineUserCount;
//	private TLotusVendorQa modelQA;
	/**
	 * @描述: 登录成功后跳转到系统主页面
	 * @作者：WXJ
	 * @日期：2010-11-19
	 * @return 
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public String logon() throws Exception {		
		String loginname = (String)this.getSession().getAttribute(Constants.LOGIN_NAME);
		
			this.messageCount = String.valueOf(this.msgBoxManager.findMsgCntByNotRead(loginname));
			
			if(!"0".equals(messageCount)||!"0".equals(bulletinCount)){
				StringBuilder sbMsg = new StringBuilder();
				sbMsg.append("<p>尊敬的用户：<br /><br />");
				if(!"0".equals(messageCount)){
					sbMsg.append("您有<a href='#' onclick='getMsgCount()'>"+messageCount+"</a> 条信息还未读取，请及时查收。<br />");
				}
				if(!"0".equals(bulletinCount)){
					sbMsg.append("最近一个月有<a href='#' onclick='getBltnCount()'>"+bulletinCount+"</a> 条公告通知，请确认查看。");
				}
				sbMsg.append("</p>");
				getRequest().setAttribute("msgMb", sbMsg.toString());
			}
			//首页上显示 用户名与登录名--add by sjy 2010-12-10
			String userName = (String)this.getSession().getAttribute(Constants.USER_NAME);
		
			String username = userName + " (" + loginname + ") "; 
			getRequest().setAttribute("username", username);
			
			
//			this.getSession().setAttribute(Constants.LOGGED,"1");
			boolean logged = (Boolean) this.getSessionAttribute("_logged");
			logger.info(String.valueOf(logged));
			
			
			Map<String,Object> map = null ;
			List<Map<String,Object>> list = null ;
			
			if (logged){
				/**
				 * 首次登录,默认为true
				 * 插入数据库
				 * 修改变量
				 */
				map = new HashMap<String,Object>();
				map.put("RECORD_DATE", DateUtil.getDateTime("yyyy/MM/dd"));
				list = authorityManager.queryWebCountByCurrentDate(map);
				logger.info(list.toString());
				
				if (list != null && list.size()>0 ){
					//有记录则更新
					map = new HashMap<String,Object>();
					/**
					 * 从 list 获取当前日期记录
					 * [{TOTAL_COUNT=200, ID=2, RECORD_DATE=2013-11-14}]
					 */
					Object record_date = list.get(0).get("RECORD_DATE");
					logger.info(record_date.toString());
					int total_count = Integer.parseInt((String)list.get(0).get("TOTAL_COUNT"));
					map.put("RECORD_DATE", record_date.toString());
					map.put("TOTAL_COUNT", ++total_count);
					logger.info(total_count + "");
					int updateCount = authorityManager.updateWebCount(map);
					
					if (updateCount != 0){
						logger.info("update sucess!!");
					}else{
						logger.info("update failed ...");
						throw new RuntimeException("更新失败...");
					}
				}else{
					//没有记录则插入新记录
					map = new HashMap<String,Object>();
					map.put("RECORD_DATE", DateUtil.getDateTime("yyyy/MM/dd"));
					map.put("TOTAL_COUNT", 1);
					int insertCount = authorityManager.insertWebCount(map);
					
					if (insertCount != 0){
						logger.info("insert sucess!!");
					}else{
						logger.info("insert failed ...");
						throw new RuntimeException("插入记录失败...");
					}
				}
				
				this.getSession(false).setAttribute("_logged", false);
				
			}
			
			logger.info(String.valueOf((Boolean) this.getSessionAttribute("_logged")));
			
			
			return SUCCESS;
	
	}

	@Override
	protected void prepareModel() throws Exception {
	}
	
	/**
	 * @描述: 如果登录密码过于简单，那么重置密码
	 * @作者：ls
	 * @日期：2010-12-17
	 * update by sjy 根据company可以判断出lotus用户
	 * 2010-12-22
	 * @return 
	 * @throws Exception
	 */	
	public String checkPassword() throws Exception {
		
		String loginname = (String)this.getSession().getAttribute(Constants.LOGIN_NAME);//机构表中得到登录名
		Users user = authorityManager.findUserByLoginName(loginname);
		if (user != null){
			hiddenPwd = user.getLoginPwd();//页面隐藏属性,校验旧密码输入正确性
			linkman = user.getDisplayName();//页面属性
			userid = String.valueOf(user.getUserid());//页面隐藏属性，保存用户信息时用
			
			boolean isOk = true;//校验密码复杂度标志
			Pattern p5 = Pattern.compile("[`_~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
			if (p5.matcher(hiddenPwd).matches()) {
				isOk = true;
			}			
			Pattern p1 = Pattern.compile("[0-9]*");
			if (p1.matcher(hiddenPwd).matches()) {
				isOk = false; 
			}			
			Pattern p2 = Pattern.compile("[a-zA-Z]*");
			if (p2.matcher(hiddenPwd).matches()) {
				isOk = false;     
			}
			if (hiddenPwd.length() < 8) {
				isOk = false;
			}			
			if (!isOk) {
				
				entity.setLoginPwd("");//旧密码置空
				return INPUT;
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * @描述: 验证用户名
	 * @作者：WXJ
	 * @日期：2011-01-10
	 * @return 
	 * @throws Exception
	 */	
	public String checkUsername() throws Exception {
		String username = new String(getRequest().getParameter("j_username").getBytes("ISO8859_1"),"UTF-8");
		Users user = authorityManager.findUserByLoginName(username);
		if (user == null) {
			Struts2Util.renderText("false");
		}else{
			Struts2Util.renderText("true");
		}
		return null;
	}
	
	/**
	 * @描述: 验证用户名及密码
	 * @作者：WXJ
	 * @日期：2011-01-10
	 * @return 
	 * @throws Exception
	 */	
	public String checkPwd() throws Exception {
		String username = new String(getRequest().getParameter("j_username").getBytes("ISO8859_1"),"UTF-8");
		String uspwd = getRequest().getParameter("j_password");		
		Users user = authorityManager.findUserByLoginName(username);
		if (user == null) {
			Struts2Util.renderText("false");
		}else{
			if(user.getLoginPwd().equals(DigestUtils.md5Hex(uspwd))){
				Struts2Util.renderText("true");
			}else{
				Struts2Util.renderText("false");
			}
		}
		

		return null;
	}
	/**
	 * @描述: 验证用户名及密码
	 * @作者：WXJ
	 * @日期：2011-01-10
	 * @return 
	 * @throws Exception
	 */	
//	public String saveQa() throws Exception {
//		try{
//			modelQA.setLoginname( (String)this.getSession().getAttribute(Constants.LOGIN_NAME));
//			modelQA.setInputdate(new Date());
//			userMgrManager.saveLotusQa(modelQA);
//			setIsSuc("true");
//			return SUCCESS;
//		}catch(Exception e){
//			logger.error(e.toString());
//			setIsSuc("false");
//			return SUCCESS;
//		}	
//	}
	public Users getModel() {
		return this.entity;
	}

	public void setMsgBoardManager(MsgBoardManager msgBoardManager) {
		this.msgBoardManager = msgBoardManager;
	}

	public void setMsgBoxManager(MsgBoxManager msgBoxManager) {
		this.msgBoxManager = msgBoxManager;
	}

	public String getBulletinCount() {
		return bulletinCount;
	}

	public String getMessageCount() {
		return messageCount;
	}

	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	public void setUserMgrManager(UserMgrManager userMgrManager) {
		this.userMgrManager = userMgrManager;
	}
	
//	public void setOrgMgrManager(OrgMgrManager orgMgrManager) {
//		this.orgMgrManager = orgMgrManager;
//	}

	public Users getEntity() {
		return entity;
	}

	public void setEntity(Users entity) {
		this.entity = entity;
	}
	
	public TAcOrg getOrgEntity() {
		return orgEntity;
	}

	public void setOrgEntity(TAcOrg orgEntity) {
		this.orgEntity = orgEntity;
	}

	
	public String getHiddenTaxno() {
		return hiddenTaxno;
	}

	public void setHiddenTaxno(String hiddenTaxno) {
		this.hiddenTaxno = hiddenTaxno;
	}
	
	public String getHiddenPwd() {
		return hiddenPwd;
	}

	public void setHiddenPwd(String hiddenPwd) {
		this.hiddenPwd = hiddenPwd;
	}

	public void setNewpw1(String newpw1) {
		this.newpw1 = newpw1;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
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

	public String getIsInputQA() {
		return isInputQA;
	}

	public void setIsInputQA(String isInputQA) {
		this.isInputQA = isInputQA;
	}

	public boolean isShowOnlineUserCount() {
		return showOnlineUserCount;
	}

	public void setShowOnlineUserCount(boolean showOnlineUserCount) {
		this.showOnlineUserCount = showOnlineUserCount;
	}

//	public TLotusVendorQa getModelQA() {
//		return modelQA;
//	}
//
//	public void setModelQA(TLotusVendorQa modelQA) {
//		this.modelQA = modelQA;
//	}
	
	
}
