/**
* <p>文件名: WelcomeAction.java</p>
* <p>描述：检测系统是否响应Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-07-28 下午07:01:02 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.PropertiesUtil;
/**
* @类描述: 检测系统是否响应
* @作者： WXJ
*/
@SuppressWarnings("rawtypes")
public class WelcomeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	/**
	 * @描述: 登录成功后跳转到系统主页面
	 * @作者：WXJ
	 * @日期：2011-07-29
	 * @return 
	 * @throws Exception
	 */	
	public String init() throws Exception {
		String ajax = getParameter("ajax");
		if (ajax != null) {
			try {
				String Actionurl = "";
				getRequest().setCharacterEncoding("UTF-8");
				String strName = getRequest().getParameter("s");
				String[] strS = strName.split("_");
				String strSU = PropertiesUtil.getConfigProperty("webserverurl", "common");
				String strSP = PropertiesUtil.getConfigProperty("webserverport", "common");
				String strHost = "http://" + strSU + ":" + strSP;// + request.getRequestURI());
				String strServer = "";
				for (int i = 0; i < strS.length; i++) {
					strServer = PropertiesUtil.getConfigProperty("servers" + strS[i], "common");//rb.getString("servers" + strS[i]);
					
					String url = strHost + strServer + "/common/gettime.html"; 
					try {
						HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
						//设置延时时间为2000毫秒，随着服务器的增加，此时间可减少，以避免最差情况（应登录的第一个服务器挂了，只有最后一个服务器正常，将轮询等待尝试其他所有的服务器连接失败）出现
						httpConn.setConnectTimeout(2000);
						httpConn.setReadTimeout(2000);
						if (HttpURLConnection.HTTP_OK == httpConn.getResponseCode()) {
							Actionurl = strServer;
							break;
						}
					} catch (Exception e) {
						System.out.println("用户试图登录的服务器：" + strServer + "连接失败");
						//这里可调用发送短信或邮件的模块来监控
						//e.printStackTrace();
					}
				}
				if (Actionurl.equals("")) {
					
				} else {
					Actionurl = strHost + Actionurl;
				}
				//System.out.println("formurl:" + Actionurl);
				PrintWriter out = getResponse().getWriter();
				getRequest().setCharacterEncoding("GB2312");
				getResponse().setContentType("text/xml;charset=gb2312");
				getResponse().setHeader("Cache-Control", "no-cache");
				getResponse().setHeader("Charset", "GB2312");
				out.println("<response>");
				out.println("<url>" + Actionurl + "</url>");
				out.println("</response>");
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}

	public Object getModel() {
		return null;
	}
	

	
}
