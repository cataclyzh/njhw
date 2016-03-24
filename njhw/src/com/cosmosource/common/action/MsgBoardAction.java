/**
* <p>文件名: MsgBoardAction.java</p>
* <p>描述：物业通知Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间：2010-11-19 20:42:13 
* @作者： ls
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.entity.TCommonMsgBoard;
import com.cosmosource.common.entity.TCommonMsgBoardRead;
import com.cosmosource.common.service.MsgBoardManager;
import com.opensymphony.xwork2.Action;
/**
 * @类描述: 物业通知Action
 * @作者： ls
 */
@SuppressWarnings("all")
public class MsgBoardAction extends BaseAction {
	private HttpServletRequest request;
	private static final long serialVersionUID = 6209591262157835766L;
	
	private MsgBoardManager msgBoardManager; 
	
	private TCommonMsgBoard entity; 
	
	private Page<HashMap> page = new Page<HashMap>(20); 
	
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TCommonMsgBoard getModel() {
		return this.entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		this.entity = new TCommonMsgBoard();
	}

	/**
	 * @描述: 初始页面
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 */
	public String init() {
		return Action.INPUT;
	}
	
	public String msgIndex(){
		return SUCCESS;
	}
	
	public String msgInput(){
		return SUCCESS;
	}
	
	/**
	 * @描述: 发布物业通知
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 */
	public String save() {
		boolean isSuccess = true;
		try {
			
			this.entity.setAuthor((String)this.getSession().getAttribute(Constants.USER_NAME));
			this.msgBoardManager.saveBulletin(this.entity);
			setIsSuc("true");
		} catch (Exception e) {
			isSuccess = false;
			setIsSuc("false");
		}

		this.page.setPageSize(15);
		String company = (String)this.getSession().getAttribute(Constants.COMPANY);
		this.page = msgBoardManager.findBulletin(this.page, company);
		List<HashMap> list = page.getResult();
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("userid", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		
		return  Action.SUCCESS;
	}
		
	public String save1() {
		boolean isSuccess = true;
		try {
			
			this.entity.setAuthor((String)this.getSession().getAttribute(Constants.USER_NAME));
			this.msgBoardManager.saveBulletin(this.entity);
			setIsSuc("true");
		} catch (Exception e) {
			isSuccess = false;
			setIsSuc("false");
		}

		this.page.setPageSize(15);
		String company = (String)this.getSession().getAttribute(Constants.COMPANY);
		this.page = msgBoardManager.findBulletin(this.page, company);
		List<HashMap> list = page.getResult();
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("userid", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		
		return  "save1";
	}


	
	public String updateMsg() {
		Long msgid = Long.parseLong(getRequest().getParameter(
				"msgid"));
	
		String title = getRequest().getParameter(
				"title");
		String content = getRequest().getParameter("content");

		Map map = new HashMap();
		map.put("msgid", msgid);
		map.put("title", title);
		map.put("content", content);
		

		msgBoardManager.updateMsgInfo(map);
		return SUCCESS;

	}

	public String msgList(){
		boolean isSuccess = true;
		try {
			this.entity.setAuthor((String)this.getSession().getAttribute(Constants.LOGIN_NAME));
			this.msgBoardManager.saveBulletin(this.entity);
			setIsSuc("true");
		} catch (Exception e) {
			isSuccess = false;
			setIsSuc("false");
//			e.printStackTrace();
		}
		
		
		String company = (String)this.getSession().getAttribute(Constants.COMPANY);
		this.page = msgBoardManager.findBulletin(this.page, company);
		List<HashMap> list = page.getResult();
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("userid", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		
		return  SUCCESS;
	}

	/**
	 * @描述: 查询物业通知
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 * @throws Exception
	 */
	public String query() throws Exception {
		String company = (String)this.getSession().getAttribute(Constants.COMPANY);
		this.page = msgBoardManager.findBulletin(this.page, company);
		List<HashMap> list = page.getResult();
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("userid", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		return "notiMoveList";
	}
	
	/**
	 * 查询物业通知(新版方法，首页显示专用)
	 * @开发者：ywl
	 * @时间：2013-7-13
	 * @return
	 */
	public String queryBoardJSON(){
		String userId = Struts2Util.getSession().getAttribute(Constants.USER_ID).toString();
		Map m = new HashMap();
		m.put("receiverId", userId);
		String company = (String)this.getSession().getAttribute(Constants.COMPANY);
		this.page = msgBoardManager.findBulletin(this.page, company);
		List<HashMap> list = page.getResult();
		Struts2Util.renderJson(list, "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	public void prepareDetail() throws Exception {
		String msgId = Struts2Util.getParameter("msgId");
		this.entity = this.msgBoardManager.findBulletinById(new Long(msgId));
	}
	
	/**
	 * @描述: 物业通知详细
	 * @作者：ls
	 * @日期：2010-12-01
	 * @return String
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public String detail(){
		HashMap map = new HashMap();
		map.put("msgId", Long.parseLong(Struts2Util.getParameter("msgId")));
		this.entity = this.msgBoardManager.findBulletinById(Long.parseLong(Struts2Util.getParameter("msgId")));
		Date msgTimeString=entity.getMsgtime();
		  SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		  String msgTime=dateformat.format(msgTimeString);
		getRequest().setAttribute("msgTime", msgTime);

		
		TCommonMsgBoardRead boardRead =  new TCommonMsgBoardRead();
		boardRead.setMsgid(Long.parseLong(Struts2Util.getParameter("msgId")));
		boardRead.setRdate(DateUtil.getSysDate());
		boardRead.setUserid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		this.msgBoardManager.saveRead(boardRead);
		return "detail";
	}
	
	public String msgModify(){
		HashMap map = new HashMap();
		map.put("msgId", Long.parseLong(Struts2Util.getParameter("msgId")));
		this.entity = this.msgBoardManager.findBulletinById(Long.parseLong(Struts2Util.getParameter("msgId")));
		Date msgTimeString=entity.getMsgtime();
		  SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		  String msgTime=dateformat.format(msgTimeString);
		getRequest().setAttribute("msgTime", msgTime);

		
		TCommonMsgBoardRead boardRead =  new TCommonMsgBoardRead();
		boardRead.setMsgid(Long.parseLong(Struts2Util.getParameter("msgId")));
		boardRead.setRdate(DateUtil.getSysDate());
		boardRead.setUserid(Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
		this.msgBoardManager.saveRead(boardRead);
		return SUCCESS;
	}

	public TCommonMsgBoard getEntity() {
		return entity;
	}

	public void setEntity(TCommonMsgBoard entity) {
		this.entity = entity;
	}

	/**
	 * @描述:物业通知的查询操作
	 * @作者： qiyanqiang
	 * @日期：2013- 05-29
	 * @return String
	 * @throws Exception
	 */
	public String queryAndDelete() throws Exception {
		this.page = msgBoardManager.findBulletinManager(this.page);
		return "deleteList";
	}
	
	/**
	 * @描述: 物业通知删除操作
	 * @作者：qiyanqiang
	 * @日期：2013-05-29
	 * @return String
	 * @throws Exception
	 */
	public String deleteData() throws Exception {
		
		try {
			String msgId =Struts2Util.getParameter("msgId");
			
			msgBoardManager.deleteData(msgId.split(","));
			Struts2Util.renderText("删除通知成功");
		} catch (Exception e) {
			Struts2Util.renderText("删除通知失败");
		}
		
		/*String company = (String)this.getSession().getAttribute(Constants.COMPANY);
		this.page = msgBoardManager.findBulletin(this.page, company);
		List<HashMap> list = page.getResult();
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("userid", Long.parseLong(Struts2Util.getSession().getAttribute(Constants.USER_ID).toString()));
	*/
		return  null;
	}
	
	public void setMsgBoardManager(MsgBoardManager msgBoardManager) {
		this.msgBoardManager = msgBoardManager;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}
}
