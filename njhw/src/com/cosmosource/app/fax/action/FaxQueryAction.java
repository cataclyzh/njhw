/**
 * 
 */
package com.cosmosource.app.fax.action;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.cosmosource.app.entity.Users;
import com.cosmosource.app.fax.service.FaxQueryService;
import com.cosmosource.app.personnel.service.PersonnelExpInforManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class FaxQueryAction extends BaseAction<Object> {

	private FaxQueryService faxQueryService;
	private PersonnelExpInforManager personnelExpInforManager;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化函数
	 * 
	 * @param username
	 *            登录帐户名称
	 * @param password
	 *            SHA1(明文口令)
	 * @param page_size
	 *            后续查询每页大小
	 * @return JSON
	 * @throws Exception
	 */
	public String init() throws Exception {

		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		Users user = personnelExpInforManager.getUserByid(uid);
		String loginName = user.getLoginUid();
		String password = user.getLoginPwdMd5();
		// admin账号密码
		//loginName = "admin";
		//password = "011c945f30ce2cbafc452f39840f025693339c42";
		logger.info("init()--loginName:" + loginName + " password:" + password);
		String session_id = null;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", loginName));
		nvps.add(new BasicNameValuePair("password", password));
//		nvps.add(new BasicNameValuePair("username", "admin"));
//		nvps.add(new BasicNameValuePair("password", "011c945f30ce2cbafc452f39840f025693339c42"));
		if (this.getRequest().getParameter("page_size") != null
				&& this.getRequest().getParameter("page_size").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("page_size",
					getParameter("page_size")));
		}
		// 构造返回json数据
		JSONObject jObject = faxQueryService.login(nvps);
		boolean flag = false ;
		

		if (!jObject.get("data").toString().equals("null")) {
			JSONObject dataObj = (JSONObject) jObject.get("data");
			session_id = dataObj.get("session_id").toString();
			flag = true ;
		}
		
		//查询用户是否有分配网络传真号码
		List list = faxQueryService.hasWebFaxNum(loginName);
		
		//有查询到网路传真
		if (flag && list.size() > 0){
			this.getRequest().getSession().removeAttribute("fax_session_id");
			this.getRequest().getSession().setAttribute("fax_session_id", session_id);
			
			//登录成功并且有软传真号码
			jObject.put("hasWebFaxNum",1);
		}else{
			this.getRequest().getSession().setAttribute("fax_session_id", "");
		}

		this.getResponse().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/json");
		this.getResponse().getWriter().write(jObject.toString());
		this.getResponse().getWriter().flush();
		this.getResponse().getWriter().close();

		return null;
	}

	/**
	 * 登陆： POST /Api/login
	 * 
	 * @param username
	 *            登录帐户名称
	 * @param password
	 *            SHA1(明文口令)
	 * @param page_size
	 *            后续查询每页大小
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {

		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		Users user = personnelExpInforManager.getUserByid(uid);
		String loginName = user.getLoginUid();
		String password = user.getLoginPwdMd5();
		
		logger.info("loginName: " + loginName);
		logger.info("password: " + loginName);

		// admin账号密码
		// loginName = "admin";
		// password = "011c945f30ce2cbafc452f39840f025693339c42";

		logger.info("login()--loginName:" + loginName + " password:" + password);

		// 测试传真服务器是否可连接
		String host = "10.254.101.100";
		int timeOut = 3000; // 超时应该在3秒以上
		if (!(InetAddress.getByName(host).isReachable(timeOut) && linkIsValid(
				new URL("http://" + host + "/ipcom/"), timeOut)))
			return ERROR;
		String session_id = null;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", loginName));
		nvps.add(new BasicNameValuePair("password", password));
		
		if (this.getRequest().getParameter("page_size") != null
				&& this.getRequest().getParameter("page_size").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("page_size",
					getParameter("page_size")));
		}

		// 构造返回json数据
		JSONObject jObject = faxQueryService.login(nvps);

		if (!jObject.get("data").toString().equals("null")) {
			JSONObject dataObj = (JSONObject) jObject.get("data");
			session_id = dataObj.get("session_id").toString();
			this.getRequest().getSession().removeAttribute("fax_session_id");
			this.getRequest().getSession()
					.setAttribute("fax_session_id", session_id);
		}

		// if (jObject.get("data").toString().equals("null")) {
		// this.getRequest().getSession().setAttribute("fax_session_id", "");
		// return ERROR;
		// }
		// JSONObject dataObj = (JSONObject) jObject.get("data");
		// session_id = dataObj.get("session_id").toString();
		// this.getRequest().getSession().removeAttribute("fax_session_id");
		// this.getRequest().getSession()
		// .setAttribute("fax_session_id", session_id);

		return SUCCESS;
	}
	
	/**
	 * 获取当前登录用户网络传真号码
	 * 显示于发送传真页面发件人文本框
	 * @param loginUid
	 */
	public void getWebFaxNumByLoginUid() {
		
		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		Users user = personnelExpInforManager.getUserByid(uid);
		String loginName = user.getLoginUid();
		
		List list = faxQueryService.hasWebFaxNum(loginName);
		//[{TEL_NUM=68787485}]
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		this.getResponse().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/json");
		try {
			this.getResponse().getWriter().write(jsonArray.toString());
			this.getResponse().getWriter().flush();
			this.getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取通讯录最近联系人列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getFaxRecentContact() throws Exception {
		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (this.getRequest().getSession().getAttribute("fax_session_id") != null
				&& this.getRequest().getSession()
						.getAttribute("fax_session_id").toString().trim()
						.length() > 0) {
			nvps.add(new BasicNameValuePair("session_id", this.getRequest()
					.getSession().getAttribute("fax_session_id").toString()));
		}
		Map recentFaxList = new HashMap();
		// 构造返回json数据
		JSONObject jObject = null;
		JSONArray jArray = null;

		// 添加收件箱记录
		jObject = faxQueryService.getReceivedFaxesList(nvps);
		if (!((JSONObject) jObject.get("data")).get("list").toString().equals("null")) {
			jArray = JSONArray.fromObject(((JSONObject) jObject.get("data"))
					.get("list"));		
			for (int i = 0; i < jArray.size(); i++) {
				JSONObject oj = jArray.getJSONObject(i);
				Long created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(oj.get("created_on").toString()).getTime();
				// 判断有无记录
				if (!recentFaxList.containsKey(oj.get("caller"))) {
					recentFaxList.put(oj.get("caller"), created_on);
				} else {
					// 更新为最新记录的时间
					if (Long.parseLong(recentFaxList.get(oj.get("caller"))
							.toString()) < created_on)
						recentFaxList.put(oj.get("caller"), created_on);
				}

			}
		}
		
		// 添加发件箱记录
		jObject = faxQueryService.getSentFaxesList(nvps);
		if (!((JSONObject) jObject.get("data")).get("list").toString().equals("null")) {
			jArray = JSONArray.fromObject(((JSONObject) jObject.get("data"))
					.get("list"));
			for (int i = 0; i < jArray.size(); i++) {
				JSONObject oj = jArray.getJSONObject(i);
				Long created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(oj.get("created_on").toString()).getTime();
				// 判断有无记录
				if (!recentFaxList.containsKey(oj.get("called"))) {
					recentFaxList.put(oj.get("called"), created_on);
				} else {
					// 更新为最新记录的时间
					if (Long.parseLong(recentFaxList.get(oj.get("called"))
							.toString()) < created_on)
						recentFaxList.put(oj.get("called"), created_on);
				}
			}
		}
		

		// 按时间倒序排序
		List<Map.Entry<String, Long>> infoIds = new ArrayList<Map.Entry<String, Long>>(
				recentFaxList.entrySet());
		logger.info(infoIds.toString());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Long>>() {
			public int compare(Map.Entry<String, Long> o1,
					Map.Entry<String, Long> o2) {
				// return (o2.getValue() - o1.getValue());
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		logger.info(infoIds.toString());
		// 构造返回json数据

		jObject = new JSONObject();
		jArray = new JSONArray();
		for (int i = 0; i < infoIds.size(); i++) {
			String faxNo = infoIds.get(i).getKey();
			//if(formatTel(faxNo).split("-").length==2)
			String username = faxQueryService.getUsernameFromFax(uid,formatTel(faxNo) );
			if (username.equals("")) {
				username = "（未添加）";
			}
			jObject.put("name", username);
			jObject.put("fax", formatTel(faxNo));
			jArray.add(jObject);
		}

		// for (int i = 0; i < faxNoList.size(); i++) {
		// String username = faxQueryService.getUsernameFromFax(uid,
		// faxNoList.);
		// if (username.equals("")) {
		// username = "（未添加）";
		// }
		// jObject.put("name", username);
		// jObject.put("fax", list.get(i).get("FAX_NO"));
		// jArray.add(jObject);
		// }

		this.getResponse().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/json");
		this.getResponse().getWriter().write(jArray.toString());
		this.getResponse().getWriter().flush();
		this.getResponse().getWriter().close();

		return null;
	}

	/**
	 * 通过传真号码查找联系人姓名
	 * 
	 * @param faxNumber
	 * @param userId
	 */
	public void getUserNameByFaxNumber() {

		String username = null;

		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());

		String faxNumber = this.getRequest().getParameter("faxNumber");

		username = faxQueryService.getUsernameFromFax(uid, faxNumber);

		this.getResponse().setCharacterEncoding("UTF-8");
		this.getResponse().setContentType("text/plain");

		if (null == username || "".equals(username)) {

			username = "UnFound";
		}

		try {
			this.getResponse().getWriter().write(username);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		try {
			this.getResponse().getWriter().flush();
			this.getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

	}

	/**
	 * 获取个人通讯录列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getPersonalContactList() throws Exception {
		long uid = Long.valueOf(this.getRequest().getSession()
				.getAttribute(Constants.USER_ID).toString());
		String searchVal = "";
		if (this.getRequest().getParameter("searchVal") != null
				&& this.getRequest().getParameter("searchVal").trim().length() > 0) {
			searchVal = getParameter("searchVal");
		}
		List<HashMap> list = faxQueryService.getPersonalContactList(uid,
				searchVal);

		// 构造返回json数据

		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			jObject.put("nuaid", list.get(i).get("NUA_ID"));
			jObject.put("gid", list.get(i).get("NUA_GROUP"));
			jObject.put("name", list.get(i).get("NUA_NAME"));
			jObject.put("nickname", list.get(i).get("NUA_NAME1"));
			jObject.put("fax", list.get(i).get("NUA_TEL3"));
			jArray.add(jObject);
		}

		this.getResponse().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/json");
		this.getResponse().getWriter().write(jArray.toString());
		this.getResponse().getWriter().flush();
		this.getResponse().getWriter().close();

		return null;
	}

	/**
	 * 删除个人通讯录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deletePersonalContact() throws Exception {
		Long nuaid = (long) 0;
		if (this.getRequest().getParameter("nuaid") != null
				&& this.getRequest().getParameter("nuaid").trim().length() > 0) {
			nuaid = Long.parseLong(getParameter("nuaid"));
		}
		String result = "{\"result\":\"success\"}";
		try {
			faxQueryService.deletePersonalContact(nuaid);
		} catch (Exception e) {
			// TODO: handle exception
			result = "{\"result\":\"error\"}";
		} finally {
			logger.info(result);
			this.getResponse().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/json");
			this.getResponse().getWriter().write(result);
			this.getResponse().getWriter().flush();
			this.getResponse().getWriter().close();
		}

		return null;
	}

	/**
	 * 查询收到的传真列表: POST /Api/fi_list
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getReceivedFaxesList() throws Exception {

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (this.getRequest().getSession().getAttribute("fax_session_id") != null
				&& this.getRequest().getSession()
						.getAttribute("fax_session_id").toString().trim()
						.length() > 0) {
			nvps.add(new BasicNameValuePair("session_id", this.getRequest()
					.getSession().getAttribute("fax_session_id").toString()));
		}
		if (this.getRequest().getParameter("is_read") != null
				&& this.getRequest().getParameter("is_read").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("is_read", getParameter("is_read")));
		}
		if (this.getRequest().getParameter("time_after") != null
				&& this.getRequest().getParameter("time_after").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("time_after",
					getParameter("time_after")));
		}
		if (this.getRequest().getParameter("time_before") != null
				&& this.getRequest().getParameter("time_before").trim()
						.length() > 0) {
			Date time_before = strToDate(getParameter("time_before"));
			String time_before_str = addDate(time_before);
			nvps.add(new BasicNameValuePair("time_before", time_before_str));
		}
		if (this.getRequest().getParameter("page_no") != null
				&& this.getRequest().getParameter("page_no").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("page_no", getParameter("page_no")));
		}

		// 构造返回json数据
		JSONObject jObject = faxQueryService.getReceivedFaxesList(nvps);

		this.getResponse().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/json");
		this.getResponse().getWriter().write(jObject.toString());
		this.getResponse().getWriter().flush();
		this.getResponse().getWriter().close();

		return null;

	}

	/**
	 * 查询外发的传真列表: POST /Api/fo_list
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getSentFaxesList() throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (this.getRequest().getSession().getAttribute("fax_session_id") != null
				&& this.getRequest().getSession()
						.getAttribute("fax_session_id").toString().trim()
						.length() > 0) {
			nvps.add(new BasicNameValuePair("session_id", this.getRequest()
					.getSession().getAttribute("fax_session_id").toString()));
		}
		if (this.getRequest().getParameter("subject") != null
				&& this.getRequest().getParameter("subject").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("subject", getParameter("subject")));
		}
		if (this.getRequest().getParameter("receiver") != null
				&& this.getRequest().getParameter("receiver").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("receiver",
					getParameter("receiver")));
		}
		if (this.getRequest().getParameter("status") != null
				&& this.getRequest().getParameter("status").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("status", getParameter("status")));
		}
		if (this.getRequest().getParameter("time_after") != null
				&& this.getRequest().getParameter("time_after").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("time_after",
					getParameter("time_after")));
		}
		if (this.getRequest().getParameter("time_before") != null
				&& this.getRequest().getParameter("time_before").trim()
						.length() > 0) {
			Date time_before = strToDate(getParameter("time_before"));
			String time_before_str = addDate(time_before);
			nvps.add(new BasicNameValuePair("time_before", time_before_str));
		}
		if (this.getRequest().getParameter("page_no") != null
				&& this.getRequest().getParameter("page_no").trim().length() > 0) {
			nvps.add(new BasicNameValuePair("page_no", getParameter("page_no")));
		}

		// 构造返回json数据
		JSONObject jObject = faxQueryService.getSentFaxesList(nvps);

		this.getResponse().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/json");
		this.getResponse().getWriter().write(jObject.toString());
		this.getResponse().getWriter().flush();
		this.getResponse().getWriter().close();

		return null;
	}

	/**
	 * 下载收到的传真到服务器端
	 * 
	 * @return
	 * @throws Exception
	 */
	public String fiDownload() throws Exception {
		String session_id = null;
		String id = null;
		if (this.getRequest().getSession().getAttribute("fax_session_id") != null
				&& this.getRequest().getSession()
						.getAttribute("fax_session_id").toString().trim()
						.length() > 0) {
			session_id = this.getRequest().getSession()
					.getAttribute("fax_session_id").toString();
		}
		if (this.getRequest().getParameter("id") != null
				&& this.getRequest().getParameter("id").trim().length() > 0) {
			id = getParameter("id");
		}
		faxQueryService.fiDownload(session_id, id);
		return null;
	}

	/**
	 * 下载发送的传真到服务器端
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public String foDownload() throws Exception {
		String session_id = null;
		String id = null;
		if (this.getRequest().getSession().getAttribute("fax_session_id") != null
				&& this.getRequest().getSession()
						.getAttribute("fax_session_id").toString().trim()
						.length() > 0) {
			session_id = this.getRequest().getSession()
					.getAttribute("fax_session_id").toString();
		}
		if (this.getRequest().getParameter("id") != null
				&& this.getRequest().getParameter("id").trim().length() > 0) {
			id = getParameter("id");
		}
		String zipFile = faxQueryService.foDownload(session_id, id);
		String unzipFile = faxQueryService.unzip(id);
		faxQueryService.getFileList(unzipFile);
		return null;
	}

	/**
	 * 判断链接是否有效
	 * 
	 * @param url
	 *            输入链接
	 * @return 返回true或者false
	 */
	public boolean linkIsValid(URL url, int timeOut) {
		boolean result = true;
		try {
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(timeOut);// 连结超时，单位毫秒
			conn.setReadTimeout(timeOut);// 读超时
			conn.getInputStream();// 获得流
			// InputStream in = url.openStream();

		} catch (Exception e1) {

			url = null;
			return false;
		}
		return result;
	}

	public Date strToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public String addDate(Date localDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(localDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());
	}

	public String formatTel(String tel) {
		String num = tel.replace(".", "-");
		
		// 00853,00886,00852 not treated
		String regex1 = "^010[0-9]{7,8}";
		String regex2 = "^02[0-9]{8,9}"; // 0207777777, 02988888888
		String regex3 = "^0[3-9][0-9]{9,10}"; // 03947777777, 076912345678

		String regex1f = "010";
		String regex2f = "02[0-9]"; // 020,021,022,023,024,025,027,028,029
		String regex3f = "0[3-9][0-9]{2}"; // 0310,0769,0755,0999,...
		String numArray[]=num.split("-");
		if (10 <= numArray[0].length() && numArray[0].length() <= 12) {
			if (numArray[0].matches(regex1)) {
				num = num.replaceFirst(regex1f, "");
			} else if (numArray[0].matches(regex2)) {
				num = num.replaceFirst(regex2f, "");
			} else if (numArray[0].matches(regex3)) {
				num = num.replaceFirst(regex3f, "");
			}
		}
		String prefix = tel.substring(0, tel.length() - num.length());
		if (prefix.equals("")) {
			return  num;
		} else {
			return tel.substring(0, tel.length() - num.length()) + "-" + num;
		}

	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the faxQueryService
	 */
	public FaxQueryService getFaxQueryService() {
		return faxQueryService;
	}

	/**
	 * @param faxQueryService
	 *            the faxQueryService to set
	 */
	public void setFaxQueryService(FaxQueryService faxQueryService) {
		this.faxQueryService = faxQueryService;
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
