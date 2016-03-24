/**
 * <p>文件名: MsgBoxAction.java</p>
 * <p>描述：公告板Action</p>
 * <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
 * <p>公司: Cosmosource Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间：2010-11-20 16:30:57
 * @作者： ls
 * @版本： V1.0
 * <p>类修改者		 修改日期			修改说明   </p>   
 * 
 */
package com.cosmosource.common.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mondrian.spi.impl.IngresDialect;

import org.apache.http.message.BasicNameValuePair;
import org.apache.struts2.ServletActionContext;

import com.cosmosource.app.entity.Users;
import com.cosmosource.app.personnel.service.PersonnelExpInforManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonMsgBox;
import com.cosmosource.common.service.MsgBoxManager;

/**
 * @类描述: 消息箱Action
 * @作者： ls
 */
public class MsgBoxAction extends BaseAction<TCommonMsgBox> {

	private static final long serialVersionUID = -7899881363598951235L;
	private MsgBoxManager msgBoxManager;// 消息箱Manager
	private PersonnelExpInforManager personnelExpInforManager;
	private TCommonMsgBox entity;// 消息箱模型
	private Page<TCommonMsgBox> page = new Page<TCommonMsgBox>(25);// 消息箱Page对象
	private String replyTitle;
	private String replyer;
	private Long replyerId;
	private String replyContent;
	private File vendorsFile;// 所上传的文件供应商号txt
	private String vendorsFileFileName;// 所上传的文件名称
	private String _chk[];// 选中记录的ID数组

	@Override
	protected void prepareModel() throws Exception {
		this.entity = new TCommonMsgBox();
	}

	/**
	 * @描述: 消息发送初始页面
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 */
	public String init() {
		return INPUT;
	}

	/**
	 * @描述: 消息发送
	 * @作者：SQS
	 * @日期：
	 * @return String
	 */
	public String save() {
		try {
			String treeUserId = getParameter("treeUserId");// ID
			String treeUserName = getParameter("receiver");// NAME
			String title = getParameter("title");
			String content = getParameter("content");
			String userIdTree[] = treeUserId.split(",");
			String userName[] = treeUserName.split(",");
			String userId;
			String userIdName;
			TCommonMsgBox tCommonMsgBox;

			Long notificaId = msgBoxManager.getMessageId();

			for (int i = 0; i < userIdTree.length; i++) {
				tCommonMsgBox = new TCommonMsgBox();
				userId = userIdTree[i];
				userIdName = userName[i];
				tCommonMsgBox.setSender((String) this.getSession()
						.getAttribute(Constants.LOGIN_NAME));
				tCommonMsgBox.setSenderId(Long.parseLong(Struts2Util
						.getSession().getAttribute(Constants.USER_ID)
						.toString()));
				tCommonMsgBox.setMsgtime(DateUtil.getSysDate());
				tCommonMsgBox.setTitle(title);
				tCommonMsgBox.setContent(content);
				tCommonMsgBox.setReceiver(userIdName);
				tCommonMsgBox.setReceiverid(Long.parseLong(userId));
				tCommonMsgBox.setMsgtype(TCommonMsgBox.MSG_TYPE_SYS);
				tCommonMsgBox.setStatus("0");

				tCommonMsgBox.setNotificationId(notificaId);
				msgBoxManager.saveTCommonMsgBox(tCommonMsgBox);
			}
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @描述: 上传文件
	 * @作者：WXJ
	 * @日期：2011-8-17 下午04:59:05
	 * @return
	 * @return String
	 */
	public String uploadVendorsFiles() {
		try {
			if (vendorsFileFileName != null) {
				if (!vendorsFileFileName.endsWith(".txt")) {
					addActionMessage("选择文件格式不正确，请重新上传！");
					return INPUT;
				} else {
					String receivers = msgBoxManager.parserFile(vendorsFile);
					getRequest().setAttribute("receivers", receivers);
				}
			} else {
				addActionMessage("请选择文件后，点击上传！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage("上传文件失败, 请稍后再试！");
		}
		return INPUT;
	}

	/**
	 * @描述: 判断收件人在发件人的所属机构是否存在
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 */
	public String checkReciver() throws Exception {
		String reciver = Struts2Util.getParameter("receiver");
		if (this.checkReciver(reciver)) {
			Struts2Util.renderText("true");
		} else {
			Struts2Util.renderText("false");
		}
		return null;
	}

	/**
	 * @描述: 判断收件人在发件人的所属机构是否存在
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 */
	private boolean checkReciver(String pReciver) throws Exception {
		String loginname = (String) this.getSession().getAttribute(
				Constants.LOGIN_NAME);
		if (pReciver != null && pReciver.equals(loginname)) {
			return true;
		}
		String company = (String) this.getSession().getAttribute(
				Constants.COMPANY);
		return this.msgBoxManager.checkReciver(pReciver, company);
	}

	/**
	 * @描述: 查询收件人消息箱
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String queryReceiverbox() throws Exception {
		// String loginname =
		// (String)this.getSession().getAttribute(Constants.LOGIN_NAME);
		// this.page = msgBoxManager.findReceiverbox(this.page, loginname);
		try {
			String userId = this.getSession().getAttribute(Constants.USER_ID)
					.toString();
			this.page = msgBoxManager.findReceiverbox(this.page, userId);
			for (TCommonMsgBox obj : this.page.getResult()) {
				Object senderId = obj.getSenderId();
				List<Map> cList;
				if (senderId == null) {
					cList = msgBoxManager.getOrgById((long) 1033);
				} else {
					cList = msgBoxManager.getOrgById(Long.parseLong(senderId
							.toString()));
				}

				for (Map m : cList) {

					obj.setSender(m.get("DISPLAY_NAME").toString());

				}
				if ("0".equals(obj.getStatus())) {
					obj.setStatus("未读");

				} else {
					obj.setStatus("已读");
				}
			}
			return "rcvlist";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * @描述: 删除收件人消息
	 * @作者：sqs
	 * @日期：2013-5-17
	 * @return String
	 * @throws Exception
	 */
	public String delMess() throws Exception {
		try {
			msgBoxManager.deleteMoreMessage(_chk);
			if ("re".equals(getParameter("msgtype").toString()))
				queryReceiverbox();
			else if ("se".equals(getParameter("msgtype").toString()))
				querySenderbox();

			setIsSuc("true");
			return "delMess";
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
			return "";
		}
	}
	public String delSentMsg() {
		if (this.getRequest().getParameter("notificationId") != null && this.getRequest().getParameter("notificationId").trim().length() > 0) {
			try {
				String notificationId = getParameter("notificationId");
				logger.info(notificationId);
				msgBoxManager.deleteSentMsg(notificationId);
				return "sdrlist";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
				return "";
			}
			
		}
		
		return "";
	}

	public void prepareDetailReciverbox() throws Exception {
		String msgId = Struts2Util.getParameter("msgId");
		this.entity = this.msgBoxManager.findMessageById(new Long(msgId));
		this.setReplyer(entity.getSender());
		this.setReplyTitle("Re:" + entity.getTitle());
		this.setReplyerId(entity.getSenderId());
	}

	/**
	 * @描述: 收件箱消息详细
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String detailReciverbox() throws Exception {
		this.entity.setStatus("1");
		this.msgBoxManager.updateMessage(this.entity);
		Map param = new HashMap();
		if(this.entity.getSenderId() !=null){
			param.put("userId", this.entity.getSenderId());
			try{
				Users userName = msgBoxManager.queryUserName(this.entity.getSenderId()); //取得用户对象，再取得用户名
				this.entity.setSender(userName.getDisplayName());
			}catch(Exception e){
				logger.error("显示收件箱消息详细错误", e);
				this.entity.setSender("");
			}
		}
		
		String date = this.entity.getMsgtime().toLocaleString();
		ServletActionContext.getRequest().setAttribute("sendTime", date);
		//this.entity.setMsgtime(msgtime);
		logger.info(date);
		
		return "rcvdetail";
	}
	
	
	
	
	/**
	 * Prepare Interceptor 
	 * if the action class have prepare{MethodName}(), it will be invoked
	 * else if the action class have prepareDo(MethodName()}(), it will be invoked
	 * @throws Exception
	 */
	public void prepareCheckSendMsg() throws Exception {
		String msgId = Struts2Util.getParameter("msgId");
		this.entity = this.msgBoxManager.findMessageById(new Long(msgId));
		this.setReplyer(entity.getSender());
		this.setReplyTitle("Re:" + entity.getTitle());
		this.setReplyerId(entity.getSenderId());
	}
	/**
	 * @描述: 查看已发通知详情
	 * @作者：CJ
	 * @日期：2013-07-24
	 * @return String
	 * @throws Exception
	 * 不改变消息已读或者未读状态
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String checkSendMsg() throws Exception {
		//this.entity.setStatus("1");
		//this.msgBoxManager.updateMessage(this.entity);
		Map param = new HashMap();
		if(this.entity.getSenderId() !=null){
			param.put("userId", this.entity.getSenderId());
			Users userName = msgBoxManager.queryUserName(this.entity.getSenderId()); //取得用户对象，再取得用户名
			this.entity.setSender(userName.getDisplayName());
		}
		
		String date = this.entity.getMsgtime().toLocaleString();
		ServletActionContext.getRequest().setAttribute("sendTime", date);
		//this.entity.setMsgtime(msgtime);
		logger.info(date);
		
		return "sendMsgDetail";
	}
	
	
	

	/**
	 * @描述: 查询发件人消息箱
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 * @throws Exception
	 */
	public String querySenderbox() throws Exception {
		
		String userId = this.getSession().getAttribute(Constants.USER_ID)
				.toString();
		this.page = msgBoxManager.findSenderbox(this.page,Long.parseLong(userId) );
		logger.info(String.valueOf(this.page.getTotalCount()));
		return "sdrlist";
	}

	public void prepareDetailSenderbox() throws Exception {
		String msgId = Struts2Util.getParameter("msgId");
		this.entity = this.msgBoxManager.findMessageById(new Long(msgId));
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String queryOutboxStatus() throws Exception {
		Long notificationId = (long) 0;
		String status = "";
		String userId = this.getSession().getAttribute(Constants.USER_ID)
				.toString();
		if (this.getRequest().getParameter("notificationId") != null
				&& this.getRequest().getParameter("notificationId").trim()
						.length() > 0) {
			notificationId = Long.parseLong(getParameter("notificationId"));

		}
		if (this.getRequest().getParameter("status") != null
				&& this.getRequest().getParameter("status").trim().length() > 0) {
			status = getParameter("status");

		}
		this.page.setPageSize(1000);
		this.page = msgBoxManager.getOutboxStatus(this.page, notificationId,
				status);
		
		logger.info(String.valueOf("getTotalCount:"+this.page.getTotalCount()));
		
		try{
			for (TCommonMsgBox obj : this.page.getResult()) {
	
				List<Map> cList = msgBoxManager.getOrgById(Long.parseLong(obj
						.getReceiverid().toString()));
				for (Map m : cList) {
					obj.setBusnId(m.get("ORG_NAME").toString());
					obj.setReceiver(m.get("DISPLAY_NAME").toString());
					// logger.info(cList.toString());
				}
	
				if ("0".equals(obj.getStatus())) {
					obj.setStatus("[<span  class=\"orange\">未读</span>]");
	
				} else {
					obj.setStatus("[<span>已读</span>]");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "sdrdetail";
	}

	/**
	 * @描述: 消息回复
	 * @作者：WXJ
	 * @日期：2011-03-18
	 * @return String
	 */
	@SuppressWarnings("unused")
	public void reply() {

		logger.info("reply() method invoked !");

		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");

		/**
		 * 接收参数
		 */
		/**
		 * replyer:$("input[name=replyer]").val(),
		 * title:$("input[name=replyTitle]").val(),
		 * receiverid:$("input[name=receiverid]").val(),
		 * replyContent:$(".show_textarea").html()
		 */
		// 收件人
		String receiver = request.getParameter("replyer");
		// 信息内容
		String replyContent = request.getParameter("replyContent");
		// 信息主体
		String replyTitle = request.getParameter("title");
		
		// 回复人ID
		long replyerId = Long.parseLong(request.getParameter("replyerId"));
		
		//收件人ID
		long senderId = Long.parseLong(request.getParameter("senderId"));

		try {
			TCommonMsgBox ent = new TCommonMsgBox();
			ent.setContent(replyContent);
			ent.setTitle(replyTitle);
			ent.setReceiver(receiver);

			// ent.setReceiverid(replyerId);
			ent.setSender((String) this.getSession().getAttribute(
					Constants.LOGIN_NAME));
			ent.setSenderId(Long.parseLong(this.getSession()
					.getAttribute(Constants.USER_ID).toString()));
			ent.setStatus("0");

			//long receiverid = msgBoxManager.findIDbyUsername(receiver);
			//ent.setReceiverid(receiverid);
			ent.setReceiverid(senderId);

			Long notificaId = msgBoxManager.getMessageId();
			ent.setNotificationId(notificaId);

			msgBoxManager.saveTCommonMsgBox(ent);
			setIsSuc("true");

			try {
				response.getWriter().write("ok");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.getWriter().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			setIsSuc("false");
			try {
				response.getWriter().write("failed!");
				response.getWriter().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					response.getWriter().close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			e.printStackTrace();
		}

		// return "reply";
	}

	/**
	 * @描述: 发件箱消息详细
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 * @throws Exception
	 */
	public String detailSenderbox() throws Exception {

		return "sdrdetail";
	}

	/**
	 * 
	 * @title: initMsgBox
	 * @description: 初始化消息框架页面
	 * @author herb
	 * @return
	 * @date May 13, 2013 1:10:00 PM
	 * @throws
	 */
	public String msgBox() {
		return "msgBox";
	}

	public void setMsgBoxManager(MsgBoxManager msgBoxManager) {
		this.msgBoxManager = msgBoxManager;
	}

	public Page<TCommonMsgBox> getPage() {
		return page;
	}

	public void setPage(Page<TCommonMsgBox> page) {
		this.page = page;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getReplyer() {
		return replyer;
	}

	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}

	public File getVendorsFile() {
		return vendorsFile;
	}

	public void setVendorsFile(File vendorsFile) {
		this.vendorsFile = vendorsFile;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public TCommonMsgBox getModel() {
		return this.entity;
	}

	public String getVendorsFileFileName() {
		return vendorsFileFileName;
	}

	public void setVendorsFileFileName(String vendorsFileFileName) {
		this.vendorsFileFileName = vendorsFileFileName;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public Long getReplyerId() {
		return replyerId;
	}

	public void setReplyerId(Long replyerId) {
		this.replyerId = replyerId;
	}

	/**
	 * @return the personnelExpInforManager
	 */
	public PersonnelExpInforManager getPersonnelExpInforManager() {
		return personnelExpInforManager;
	}

	/**
	 * @param personnelExpInforManager
	 *            the personnelExpInforManager to set
	 */
	public void setPersonnelExpInforManager(
			PersonnelExpInforManager personnelExpInforManager) {
		this.personnelExpInforManager = personnelExpInforManager;
	}
}
