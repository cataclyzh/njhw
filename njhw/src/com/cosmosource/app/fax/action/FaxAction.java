package com.cosmosource.app.fax.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cosmosource.app.entity.ExtInFaxList;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.fax.model.Fax;
import com.cosmosource.app.fax.service.FaxManger;
import com.cosmosource.app.fax.util.FaxUtil;
import com.cosmosource.app.fax.util.QueryThread;
import com.cosmosource.app.fax.util.ZipUtil;
import com.cosmosource.app.personnel.service.PersonnelExpInforManager;
import com.cosmosource.app.port.serviceimpl.FaxToAppService;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.FileUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;

/**
 * @description: 网络传真操作
 * @author qiyanqiang
 * @date 2013-05-07
 */

@SuppressWarnings("unchecked")
public class FaxAction extends BaseAction<Object> {

	// 定义全局变量
	private static final long serialVersionUID = 1L;
	// 定义注入对象
	private FaxManger faxManager;

	// 定义实体变量
	private ExtInFaxList extInFaxList = new ExtInFaxList();
	// 定义分页变量

	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);
	// 选中记录的ID数组
	private String _chk[];

	private PersonnelExpInforManager personnelExpInforManager;

	private FaxToAppService faxToAppService;

	private File[] fax_attach; // 传真附件
	private List<String> fax_attachFileName; // 上传文件名
	private List<String> fax_attachContentType; // 上传文件类型
	private String fo_to; // 收件人传真号
	private String fo_subject; // 主题
	private String urgent = "0"; // 是否加急,0代表否,1代表是
	private String need_confirm = "0"; // 是否要预览
	private String fo_body; // 传真正文
	private String fax_receiver; //收件人姓名

	/**
	 * 
	 * @title: init
	 * @description: 初始化页面
	 * @author qiyanqiang
	 * @return
	 * @date 2013-5-07
	 * @throws
	 */
	public String init() {
		logger.info("init method invoked!");
		String faxSessionId = "";
		try {
			String loginName = (String) getSession().getAttribute(
					Constants.LOGIN_NAME);
			String password = "";
			long userId = Long.valueOf(this.getRequest().getSession()
					.getAttribute(Constants.USER_ID).toString());
			Users user = (Users) personnelExpInforManager.findById(Users.class,
					userId);

			/**
			 * 测试用 登录名 跟 密码
			 */
			loginName = "admin";
			password = "011c945f30ce2cbafc452f39840f025693339c42";

			// loginName = user.getLoginUid();
			// password = user.getLoginPwdMd5();

			try {
				faxSessionId = faxManager.faxToAppService(loginName, password);
			} catch (Exception e) {
				faxSessionId = "";
				e.printStackTrace();
			}
			/**
			 * 保存榕海服务器返回的登录 session_id 到session
			 */
			this.getRequest().getSession().removeAttribute("fax_session_id");
			this.getRequest().getSession()
					.setAttribute("fax_session_id", faxSessionId);

			// String userId =
			// getSession().getAttribute(Constants.USER_ID).toString();
			// NjhwUsersExp userExp =
			// personnelExpInforManager.getpsByid(Long.valueOf(userId));
			// this.getRequest().setAttribute("userFax", userExp.getUepFax());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (faxSessionId == null || "".equals(faxSessionId)) {

			HttpServletResponse response = this.getResponse();
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("Error");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("登录传真系统失败！");
			return null;
		} else {
			return INIT;
		}
	}

	/**
	 * 
	 * @title: sendFaxList
	 * @description: 查询已经发送传真的操作
	 * @author qiyanqiang
	 * @return
	 * @date 2013-5-07
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes" })
	public String sendFaxList() {

		logger.info("sendFaxList() method invoked!!");

		/**
		 * 获取session_id 获取页数
		 */
		String session_id = (String) getSession()
				.getAttribute("fax_session_id");
		String jsonString = null;
		/***
		 * 查看传真列表 required session_id optional status : Integer optional
		 * receiver : String optional subject : String optional time_after :
		 * String (2013-5-1) optional time_before : String (2013-5-2) optional
		 * page_no : Integer
		 * 
		 * @return like
		 *         {"error":null,"data":{"total":"2","list":[{"id":"38","created_on"
		 *         :"2013-05-04 ... 默认每页查询30条记录，登录的时候可设置此参数
		 */

		// jsonString = faxToAppService.requestDataForSendSearch(session_id);
		jsonString = "";
		logger.info(jsonString);

		JSONObject jsonObject = JSONObject.fromObject(jsonString);

		// 查询到的总数
		String totalCount = jsonObject.getJSONObject("data").getString("total");
		logger.info("totalCount = " + totalCount);
		getSession().setAttribute("totalCount", totalCount);

		/**
		 * 解析返回json到List 每页25条记录，按今天、昨天、更早来分3个模块
		 */
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray(
				"list");
		logger.info("jsonArray = " + jsonArray.toString());

		List<Fax> todayFaxes = new ArrayList<Fax>();
		List<Fax> yesterdayFaxes = new ArrayList<Fax>();
		List<Fax> earlierFaxes = new ArrayList<Fax>();

		Iterator iter = jsonArray.iterator();
		while (iter.hasNext()) {
			/*
			 * {"id":"100","created_on":"2013-06-26 16:12:32","tel_line":"29",
			 * "user_id":"0",
			 * "caller":"02558112185","called":"68786204","extn":""
			 * ,"from_csid":"", "from_name":null,"duration":"456","pages":"0",
			 * "path"
			 * :"C:/rh-uc/fax_attach/2013/06/26/faxin/02920130626160456391.tif",
			 * "status":"400","forward_status":"0", "reason":
			 * "Remote has disconnected, Phase E status 191: Receive communication error"
			 * , "is_read":"1","userName":null,"statusName":"失败","bg_color":"",
			 * "fg_color"
			 * :"red","ffStatusName":"邮件未转发","ff_bg_color":"","ff_fg_color":""}
			 */
			JSONObject json = JSONObject.fromObject(iter.next());
			Fax fax = new Fax();

			try {
				fax.setCalled(json.getString("called"));
				fax.setId(Integer.valueOf(json.getString("id")));
				fax.setCaller(json.getString("caller"));
				fax.setCreateTime(json.getString("created_on").split(" ")[0]);
				// fax.setExtn(json.getInt("extn")); //extn 分机号 有可能为空
				fax.setFfStatusName(json.getString("ffStatusName"));
				fax.setForward_status(json.getInt("forward_status"));
				fax.setIs_read(json.getInt("is_read") == 1 ? true : false);
				fax.setPages(json.getInt("pages"));
				fax.setPath(json.getString("path"));
				fax.setReason(json.getString("reason"));
				fax.setStatus(json.getInt("status"));
				// fax.setSubject(json.getString("subject")); //非软传真没有主题
				fax.setUser_id(json.getInt("user_id"));
				// fax.setUsername(json.getString("username"));

				/**
				 * 传真分类 昨天、今天、更早
				 */
				String time = FaxUtil.dateCompare(json.getString("created_on"));
				if ("TODAY".equalsIgnoreCase(time)) {
					todayFaxes.add(fax);
				} else if (("YESTERDAY").equalsIgnoreCase(time)) {
					yesterdayFaxes.add(fax);
				} else {
					earlierFaxes.add(fax);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug(e.toString());
			}
		}

		// 数据存放到session里
		getSession().setAttribute("yesterday", yesterdayFaxes);
		getSession().setAttribute("today", todayFaxes);
		getSession().setAttribute("earlier", earlierFaxes);

		if ("null".equals(jsonObject.getString("data"))) {
			logger.info("获取传真列表失败！");
			return null;
		}

		HttpServletResponse response = this.getResponse();
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject object = new JSONObject();
			object.put("yesterday", yesterdayFaxes);
			object.put("today", todayFaxes);
			object.put("earlier", earlierFaxes);
			out.write(object.toString());
			logger.info(object.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

		/*
		 * try {
		 * 
		 * HashMap map = new HashMap(); map.put("sendFaxStartDate",
		 * getParameter("sendFaxStartDate")); map.put("sendFaxEndtDate",
		 * getParameter("sendFaxEndtDate")); map.put("sendAccount",
		 * getParameter("sendAccount")); map.put("receive",
		 * getParameter("receive")); map.put("title", getParameter("title"));
		 * map.put("sendsStauts",getParameter("sendsStauts")); map.put("userId",
		 * this.getRequest().getSession().getAttribute(Constants.USER_ID));
		 * String p1 = this.getRequest().getParameter("pageNo");
		 * 
		 * logger.info("pageNo == " + p1); page.setPageNo(Integer.valueOf(p1));
		 * page= faxManager.querySendFaxList(page, map);
		 * getRequest().setAttribute( "pageList", page.getResult()); }
		 * catch(Exception e){ e.printStackTrace(); } return SUCCESS;
		 */
	}

	/**
	 * 
	 * @title: receiveFaxList
	 * @description: 查询已经接收传真的操作
	 * @author qiyanqiang
	 * @return
	 * @date 2013-5-07
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes" })
	public String receiveFaxList() {
		logger.info("receiveFaxList() method invoked!!");

		/**
		 * 获取session_id 获取页数
		 */
		String session_id = (String) getSession()
				.getAttribute("fax_session_id");
		String jsonString = null;
		/***
		 * 查看传真列表 required session_id optional is_read : Integer (0/1) optional
		 * time_after : String (2013-5-1) optional time_before : String
		 * (2013-5-2) optional page_no : Integer
		 * 
		 * @return like
		 *         {"error":null,"data":{"total":"2","list":[{"id":"38","created_on"
		 *         :"2013-05-04 ... 默认每页查询30条记录，登录的时候可设置此参数
		 */

		jsonString = faxToAppService.requestDataForReceiveSearch(session_id);
		logger.info(jsonString);

		JSONObject jsonObject = JSONObject.fromObject(jsonString);

		// 查询到的总数
		String totalCount = jsonObject.getJSONObject("data").getString("total");
		logger.info("totalCount = " + totalCount);
		getSession().setAttribute("totalCount", totalCount);

		/**
		 * 解析返回json到List 每页25条记录，按今天、昨天、更早来分3个模块
		 */
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray(
				"list");
		logger.info("jsonArray = " + jsonArray.toString());

		List<Fax> todayFaxes = new ArrayList<Fax>();
		List<Fax> yesterdayFaxes = new ArrayList<Fax>();
		List<Fax> earlierFaxes = new ArrayList<Fax>();

		Iterator iter = jsonArray.iterator();
		while (iter.hasNext()) {
			/*
			 * {"id":"100","created_on":"2013-06-26 16:12:32","tel_line":"29",
			 * "user_id":"0",
			 * "caller":"02558112185","called":"68786204","extn":""
			 * ,"from_csid":"", "from_name":null,"duration":"456","pages":"0",
			 * "path"
			 * :"C:/rh-uc/fax_attach/2013/06/26/faxin/02920130626160456391.tif",
			 * "status":"400","forward_status":"0", "reason":
			 * "Remote has disconnected, Phase E status 191: Receive communication error"
			 * , "is_read":"1","userName":null,"statusName":"失败","bg_color":"",
			 * "fg_color"
			 * :"red","ffStatusName":"邮件未转发","ff_bg_color":"","ff_fg_color":""}
			 */
			JSONObject json = JSONObject.fromObject(iter.next());
			Fax fax = new Fax();

			try {
				fax.setCalled(json.getString("called"));
				fax.setId(Integer.valueOf(json.getString("id")));
				fax.setCaller(json.getString("caller"));
				fax.setCreateTime(json.getString("created_on").split(" ")[0]);
				// fax.setExtn(json.getInt("extn")); //extn 分机号 有可能为空
				fax.setFfStatusName(json.getString("ffStatusName"));
				fax.setForward_status(json.getInt("forward_status"));
				fax.setIs_read(json.getInt("is_read") == 1 ? true : false);
				fax.setPages(json.getInt("pages"));
				fax.setPath(json.getString("path"));
				fax.setReason(json.getString("reason"));
				fax.setStatus(json.getInt("status"));
				// fax.setSubject(json.getString("subject")); //非软传真没有主题
				fax.setUser_id(json.getInt("user_id"));
				// fax.setUsername(json.getString("username"));

				/**
				 * 传真分类 昨天、今天、更早
				 */
				String time = FaxUtil.dateCompare(json.getString("created_on"));
				if ("TODAY".equalsIgnoreCase(time)) {
					todayFaxes.add(fax);
				} else if (("YESTERDAY").equalsIgnoreCase(time)) {
					yesterdayFaxes.add(fax);
				} else {
					earlierFaxes.add(fax);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug(e.toString());
			}
		}

		// 数据存放到session里
		getSession().setAttribute("yesterday", yesterdayFaxes);
		getSession().setAttribute("today", todayFaxes);
		getSession().setAttribute("earlier", earlierFaxes);

		if ("null".equals(jsonObject.getString("data"))) {
			logger.info("获取传真列表失败！");
			return null;
		}

		HttpServletResponse response = this.getResponse();
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONObject object = new JSONObject();
			object.put("yesterday", yesterdayFaxes);
			object.put("today", todayFaxes);
			object.put("earlier", earlierFaxes);
			out.write(object.toString());
			logger.info(object.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

		/*
		 * try { HashMap map = new HashMap(); map.put("receiveFaxStartDate",
		 * getParameter("receiveFaxStartDate")); map.put("receiveFaxEndtDate",
		 * getParameter("receiveFaxEndtDate")); map.put("callerNumber",
		 * getParameter("callerNumber")); map.put("calledNumber",
		 * getParameter("calledNumber")); map.put("receiveNumber",
		 * getParameter("receiveNumber"));
		 * map.put("sendsStauts",getParameter("sendsStauts")); map.put("userId",
		 * this.getRequest().getSession().getAttribute(Constants.USER_ID));
		 * String p1 = this.getRequest().getParameter("pageNo");
		 * 
		 * logger.info("pageNo" + p1); page.setPageNo(Integer.valueOf(p1));
		 * page= faxManager.queryReceiveFaxList(page, map);
		 * getRequest().setAttribute("map", map); getRequest().setAttribute(
		 * "pageList", page.getResult()); } catch(Exception e){
		 * e.printStackTrace(); } return SUCCESS;
		 */
	}

	/**
	 * @session_id : required
	 * @render_as : optional (返回html或json)
	 * @fo_to : required (收件人传真号)
	 * @urgent : optional （是否加急） type : integer default(0)
	 * @need_confirm : optional (是否确认预览) type : integer default(0)
	 * @fo_subject : optional (主题)
	 * @fo_header : optional (页眉)
	 * @fo_body : optional (正文)
	 * @fax_attach[] : required （附件） type: Files
	 * @send_date : optional （发送日期）
	 * @send_time : optional （发送时间）
	 * @return {"error":null,"data":{"committed":"1"}} commited: 已经提交到传真发送队列中的个数
	 */
	public String sendFax() {
		logger.info("sendFax() method invoked!!");
		/**
		 * 获取session_id 获取页数
		 */

		String session_id = (String) getSession()
				.getAttribute("fax_session_id");
		logger.info(session_id);
		String jsonString = null;

		/**
		 * 调用榕海接口发送传真
		 * 
		 * public String sendFax( String sessionId,String fo_to,int
		 * urgent,String fo_subject,String fo_body,File[] fax_attach)
		 * 
		 */
		jsonString = faxToAppService.sendFax(session_id, fo_to, urgent,
				fo_subject, fo_body,
				fax_attach, fax_attachFileName, fax_attachContentType,
				need_confirm);
		
		logger.info(jsonString);

		JSONObject json = JSONObject.fromObject(jsonString);
		String status = null;
		if ("null".equals(json.getString("data"))) {
			logger.info("发送失败！");
			status = "404";
		} else {
			status = "200";
		}
		ServletActionContext.getRequest().setAttribute("status", status);

		/*
		 * HttpServletResponse response = this.getResponse();
		 * response.setContentType("text/html");
		 * response.setCharacterEncoding("UTF-8"); PrintWriter out = null ; try
		 * { out = response.getWriter(); } catch (IOException e) {
		 * e.printStackTrace(); } out.println(
		 * "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		 * out.println("<HTML>");
		 * out.println("  <HEAD><TITLE>A Servlet</TITLE>");
		 * out.println("<script type='text/javascript'>"); out.println("alert("
		 * + status + ");"); out.println("window.open('fax_index.html');");
		 * out.println("</script></HEAD>"); out.println("  <BODY>");
		 * out.println("  </BODY>"); out.println("</HTML>"); out.flush();
		 * out.close();
		 */
		return SUCCESS;
	}

	/**
	 * @title 传真预览
	 */
	public String faxPreview() {
		/**
		 * 1,提交 2,等待转换，查询状态 3,下载附件，解压保存本地 4,传附件地址 5,取消=删除此传真 6,发送时带参数
		 * need_confirm =1
		 */
		logger.info("faxPreview() method invoked!!");
		/**
		 * 获取session_id 发送传真到队列 页面参数暂时保存到session里
		 */
		String fax_receiver = this.getRequest().getParameter("fax_receiver");
		
		String session_id = (String) getSession()
				.getAttribute("fax_session_id");

		getSession().removeAttribute("fo_body");
		getSession().removeAttribute("fo_to");
		getSession().removeAttribute("fo_subject");
		getSession().removeAttribute("fax_receiver");

		getSession().setAttribute("fo_to", fo_to);
		getSession().setAttribute("fo_subject", fo_subject);
		getSession().setAttribute("fo_body", fo_body);
		getSession().setAttribute("fax_receiver", fax_receiver);

		logger.info(session_id + "//" + fo_to + "//" + fo_body + "//"
				+ fo_subject + "//" + fax_receiver);
		String jsonString = null;

		need_confirm = "1";
		jsonString = faxToAppService.sendFax(session_id, fo_to, urgent,
				fo_subject, fo_body, fax_attach, fax_attachFileName,
				fax_attachContentType, need_confirm);
		logger.info(jsonString);

		/**
		 * 返回结果
		 */
		JSONObject json = JSONObject.fromObject(jsonString);
		String status = null;
		if ("null".equals(json.getString("data"))) {
			logger.info("发送失败！");
			status = "404";
		} else {
			status = "200";

		}
		ServletActionContext.getRequest().setAttribute("status", status);
		return SUCCESS;
	}

	public void queryCurrentFax() {
		logger.info("queryCurrentFax() method invoked!!");
		/**
		 * 调用榕海接口发送传真 获取发送列表里当前传真ID,状态
		 */
		String session_id = (String) getSession()
				.getAttribute("fax_session_id");
		String fo_to = (String) getSession().getAttribute("fo_to");
		String fo_subject = (String) getSession().getAttribute("fo_subject");

		logger.info(session_id + "----" + fo_to + "----" + fo_subject);

		String jsonString = null;
		jsonString = faxToAppService.currentFaxForSendSearch(session_id, fo_to,
				fo_subject);
		logger.info(jsonString);

		JSONObject json = JSONObject.fromObject(jsonString);

		/**
		 * {"error":null,"data":{"total":"0","list":null}}
		 */

		HttpServletResponse response = this.getResponse();
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		/**
		 * 如果发送失败
		 */
		if ("null".equals(json.getString("data"))) {
			logger.info("发送失败！");
			try {
				out = response.getWriter();
				out.write("预览失败！");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			try {
				out = response.getWriter();
				out.write(jsonString);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 下载传真
	 * 
	 * @param ID
	 * @param session_id
	 *            GET /Api/fo_download?session_id={sessId}&id={fo_id}
	 */
	public void downloadFaxAttach() {
		String session_id = (String) getSession()
				.getAttribute("fax_session_id");
		String fo_id = ServletActionContext.getRequest().getParameter("fo_id");
		getSession().setAttribute("fo_id", fo_id);

		logger.info(fo_id + "---" + session_id);

		String jsonString = null;
		jsonString = faxToAppService.sendFaxForDownLoad(session_id, fo_id);
		logger.info(jsonString);
		// 200&5937d1d32b4aed2d5ad63666f4ca4b89

		if ("200".equals(jsonString.split("&")[0])) {
			logger.info("附件下载成功！");
		} else {
			logger.info("附件下载失败！");
		}
		/**
		 * 下载文件解压 返回地址，个数，名字 fax_attachFileName 保存文件名
		 */
		String basePath = ServletActionContext.getServletContext().getRealPath(
				File.separator + "faxAttach");
		// File file = new File(filePath + session_id + "attach.zip");
		String filePath = basePath + File.separator + session_id + File.separator + "attach.zip";

		String destDir = ServletActionContext.getServletContext()
				.getContextPath();

		logger.info(destDir);
		logger.info(filePath);

		try {
			/**
			 * 解压附件
			 */
			ZipUtil.unzip(filePath, basePath + File.separator + session_id);
			// ZipUtil.unzip(filePath, destDir);

			logger.info("附件成功解压！");
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		File deskFile = new File(basePath + File.separator + session_id);
		/**
		 * 查找目录下所有文件
		 */
		File[] contents = deskFile.listFiles();

		// Map<String,Object> fileNames = new HashMap<String,Object>();
		JSONArray jsonArray = new JSONArray();
		int count = 0;
		if (contents != null) {
			for (File file : contents) {

				String name = file.getName();

				if ("tif".equals(name.substring(name.lastIndexOf(".") + 1))) {

					// jsonArray.add(count++, basePath + "\\" + session_id +
					// "\\" + name);
					jsonArray.add(count++, "faxAttach" + File.separator+ session_id + File.separator
							+ name);
				}

			}
		}
		// jsonArray.add(count++,session_id);
		// jsonArray.add(count++, String.valueOf(fo_id));
		logger.info(jsonArray.toString());
		/**
		 * 返回结果给页面
		 */
		// JSONArray jsonArray = JSONArray.fromObject(fileNames);
		HttpServletResponse response = this.getResponse();
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.write(jsonArray.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 取消队列中传真 预览取消
	 * 
	 * @param session_id
	 * @param id
	 * @throws Exception
	 */
	public void faxCancel() throws Exception {
		logger.info("faxCancel() method invoked!");

		String session_id = (String) getSession()
				.getAttribute("fax_session_id");
		String id = (String) getSession().getAttribute("fo_id");

		DefaultHttpClient httpClient = new DefaultHttpClient();
		// httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
		// "UTF-8");
		HttpPost httpPost = new HttpPost(
				"http://10.254.101.100/ipcom/index.php/Api/fo_cancel");

		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("session_id", session_id));
		formParams.add(new BasicNameValuePair("id", id));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams,
				"UTF-8");
		httpPost.setEntity(formEntity);

		// HttpResponse response = httpClient.execute(httpPost);

		// HttpEntity entity = response.getEntity();
		// response.getStatusLine());

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpClient.execute(httpPost, responseHandler);

		HttpServletResponse response = this.getResponse();
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.write(responseBody);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		httpClient.getConnectionManager().shutdown();

	}

	/**
	 * 确认发送 队列中的传真确认发送
	 */
	public void faxConfirm() throws Exception {
		logger.info("faxConfirm() method invoked!");

		String session_id = (String) getSession()
				.getAttribute("fax_session_id");
		String id = (String) getSession().getAttribute("fo_id");

		DefaultHttpClient httpClient = new DefaultHttpClient();
		// httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
		// "UTF-8");
		HttpPost httpPost = new HttpPost(
				"http://10.254.101.100/ipcom/index.php/Api/fo_confirm");

		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("session_id", session_id));
		formParams.add(new BasicNameValuePair("id", id));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams,
				"UTF-8");
		httpPost.setEntity(formEntity);

		// HttpResponse response = httpClient.execute(httpPost);

		// HttpEntity entity = response.getEntity();
		// response.getStatusLine());

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpClient.execute(httpPost, responseHandler);

		HttpServletResponse response = this.getResponse();
		response.setContentType("json/application");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.write(responseBody);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		httpClient.getConnectionManager().shutdown();
	}

	/**
	 * 
	 * @title: synSendFax
	 * @description: 同步发送的传真
	 * @author herb
	 * @date May 13, 2013 9:56:37 PM
	 * @throws
	 */
	public String synSendFax() {
		JSONObject result = new JSONObject();
		try {
			String userName = this.getRequest().getSession()
					.getAttribute(Constants.LOGIN_NAME).toString();
			long userId = Long.valueOf(this.getRequest().getSession()
					.getAttribute(Constants.USER_ID).toString());
			Users user = (Users) personnelExpInforManager.findById(Users.class,
					userId);
			String password = user.getLoginPwdMd5();
			String mac = "";
			if (null != this.getRequest().getSession()
					.getAttribute(Constants.USER_TEL_MAC))
				;
			mac = this.getRequest().getSession()
					.getAttribute(Constants.USER_TEL_MAC).toString();
			// userName = "admin11";
			// password = "011c945f30ce2cbafc452f39840f025693339c42";
			faxToAppService.saveSynchronousSendeFaxInfo(userName, password,
					userId, mac);
			result.put("result", "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(result.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 
	 * @title: synReceiveFax
	 * @description: 同步收到的传真
	 * @author herb
	 * @date May 13, 2013 9:56:37 PM
	 * @throws
	 */
	public String synReceiveFax() {
		JSONObject result = new JSONObject();
		try {

			result.put("result", "0");
			String userName = this.getRequest().getSession()
					.getAttribute(Constants.LOGIN_NAME).toString();
			long userId = Long.valueOf(this.getRequest().getSession()
					.getAttribute(Constants.USER_ID).toString());
			Users user = (Users) personnelExpInforManager.findById(Users.class,
					userId);
			String password = user.getLoginPwdMd5();
			String mac = "";
			if (null != this.getRequest().getSession()
					.getAttribute(Constants.USER_TEL_MAC))
				mac = this.getRequest().getSession()
						.getAttribute(Constants.USER_TEL_MAC).toString();

			// userName = "admin11";
			// password = "011c945f30ce2cbafc452f39840f025693339c42";

			faxToAppService.saveSynchronousReceiveFaxInfo(userName, password,
					userId, mac);
			result.put("result", "1");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(result.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	/**
	 * 
	 * @title: deleteFax
	 * @description: 删除已经发送的fax
	 * @author qiyanqiang
	 * @return
	 * @throws
	 */
	public String deleteSendFax() throws Exception {
		try {
			_chk = getParameter("ids").split(",");
			faxManager.deleteSendFax(_chk);
			Struts2Util.renderText("success");
		} catch (Exception e) {
			Struts2Util.renderText("fail");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @title: downLoadAttach
	 * @description: 外发附件下载
	 * @author herb
	 * @return
	 * @throws Exception
	 * @date May 12, 2013 10:49:31 AM
	 * @throws
	 */
	public String downLoadAttach() throws Exception {
		boolean isOnLine = false;
		String filePath = this.getRequest().getParameter("filePath");
		File f = new File(filePath);
		if (!f.exists()) {
			this.getResponse().sendError(404, "File not found!");
			return null;
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;

		this.getResponse().reset(); // 非常重要
		if (isOnLine) { // 在线打开方式
			URL u = new URL("file:///" + filePath);
			this.getResponse().setContentType(
					u.openConnection().getContentType());
			this.getResponse().setHeader("Content-Disposition",
					"inline; filename=" + f.getName());
			// 文件名应该编码成UTF-8
		} else {// 纯下载方式
			this.getResponse().setContentType("application/x-msdownload");
			this.getResponse().setHeader("Content-Disposition",
					"attachment; filename=" + f.getName());
		}
		OutputStream out = this.getResponse().getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
		return null;
	}

	/**
	 * 
	 * @title: deleteFax
	 * @description: 删除已经接收的fax
	 * @author qiyanqiang
	 * @date May 17, 2013 10:56:37 PM
	 * @return
	 * @throws
	 */
	public String deleteReceiveFax() throws Exception {
		try {
			_chk = getParameter("ids").split(",");
			faxManager.deleteReceiveFax(_chk);
			Struts2Util.renderText("success");
		} catch (Exception e) {
			Struts2Util.renderText("fail");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @title: upDateReadMark
	 * @description: 当点击附件的时候更新已读标记
	 * @author qiyanqiang
	 * @date May 17, 2013 10:56:37 PM
	 * @return
	 * @throws
	 */
	public String upDateReadMark() throws Exception {
		JSONObject resultJSON = new JSONObject();
		try {
			String id = this.getRequest().getParameter("id");
			if (StringUtil.isNotBlank(id)) {
				faxManager.updateReadMark(Long.valueOf(id));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		resultJSON.put("qq", "qq");
		Struts2Util.renderJson(resultJSON.toString(), "encoding:UTF-8",
				"no-cache:true");
		return null;
	}

	public FaxManger getFaxManager() {
		return faxManager;
	}

	public void setFaxManager(FaxManger faxManager) {
		this.faxManager = faxManager;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public ExtInFaxList getExtInFaxList() {
		return extInFaxList;
	}

	public void setExtInFaxList(ExtInFaxList extInFaxList) {
		this.extInFaxList = extInFaxList;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public ExtInFaxList getModel() {

		return extInFaxList;
	}

	public PersonnelExpInforManager getPersonnelExpInforManager() {
		return personnelExpInforManager;
	}

	public void setPersonnelExpInforManager(
			PersonnelExpInforManager personnelExpInforManager) {
		this.personnelExpInforManager = personnelExpInforManager;
	}

	public FaxToAppService getFaxToAppService() {
		return faxToAppService;
	}

	public void setFaxToAppService(FaxToAppService faxToAppService) {
		this.faxToAppService = faxToAppService;
	}

	public File[] getFax_attach() {
		return fax_attach;
	}

	public void setFax_attach(File[] fax_attach) {
		this.fax_attach = fax_attach;
	}

	public String getFo_to() {
		return fo_to;
	}

	public void setFo_to(String fo_to) {
		this.fo_to = fo_to;
	}

	public String getFo_subject() {
		return fo_subject;
	}

	public void setFo_subject(String fo_subject) {
		this.fo_subject = fo_subject;
	}

	public String getUrgent() {
		return urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	public String getFo_body() {
		return fo_body;
	}

	public void setFo_body(String fo_body) {
		this.fo_body = fo_body;
	}

	public List<String> getFax_attachFileName() {
		return fax_attachFileName;
	}

	public void setFax_attachFileName(List<String> fax_attachFileName) {
		this.fax_attachFileName = fax_attachFileName;
	}

	public List<String> getFax_attachContentType() {
		return fax_attachContentType;
	}

	public void setFax_attachContentType(List<String> fax_attachContentType) {
		this.fax_attachContentType = fax_attachContentType;
	}

	public String getFax_receiver() {
		return fax_receiver;
	}

	public void setFax_receiver(String fax_receiver) {
		this.fax_receiver = fax_receiver;
	}

}
