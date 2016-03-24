package com.cosmosource.app.transfer.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.fax.util.IPTimeStamp;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;

public class FaxToSystemService {
	
	private static final Log log = LogFactory.getLog(FaxToSystemService.class);
	private final String beginTime =DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd")+" 07:00:00";
	private final String endTime = DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss");
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 
	* @title: sendPostForLogin 
	* @description: 登陆对方的传真系统
	* @author cjw
	* @param httpPath
	* @return
	* @throws Exception
	* @date 2013-5-6 下午02:24:52     
	* @throws 
	*/ 
	public String sendPostForLogin(String httpPath,String userName,String password) throws Exception {
		String uigHttp =httpPath ;
		String str = "";
		PostMethod post = new PostMethod(uigHttp);
		RequestEntity entity = new StringRequestEntity(str,
				"application/x-www-form-urlencoded", "utf-8");
		
		/**
		 * 2013-07-08 CJ
		 * 查询每页大小
		 * 默认为30改为25
		 */
		String page_size = "25";
		NameValuePair[] postData = { new NameValuePair("username",userName),
				new NameValuePair("password",password),new NameValuePair("page_size",page_size) };
		
		post.setRequestEntity(entity);
		post.setRequestBody(postData);
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		String result = null;
		try {
			int statusCode =httpClient.executeMethod(post);
			log.info(String.valueOf(statusCode) + "  --> 如果是200表面正确访问到了榕海的服务器！");
			result = post.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		//result = parseGZipRes(post);
		return result;
	}

	
	
	/** 
	* @title: sendPostForLogin 
	* @description: 发送传真
	* @author cjw
	* @param httpPath
	* @return
	* @throws Exception
	* @date 2013-5-6 下午02:24:52     
	* @throws 
	*/ 
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public String sendFax(String httpPath, String session_id, String fo_to,
			String urgent, String fo_subject, String fo_body, File[] fax_attach,
				List fax_attachFileName,List fax_attachContentType,String need_confirm)
			throws Exception {
		
		
		//String uigHttp =httpPath ;
		String send_fax = "http://10.254.101.100/ipcom/index.php/Api/send_fax";
		/**
		 * 测试用接收上传文件
		 */
		String path = ServletActionContext.getRequest().getRealPath(File.separator + "faxUpload") + File.separator + session_id;
		logger.info(path);
		
		File file = new File(path);
		if (!file.isDirectory()){
			//file.mkdir();
			file.mkdirs();
		}
		
		List<String> names = new ArrayList<String>();
		String IPAddress = ServletActionContext.getRequest().getRemoteAddr();
		//传真参数
		MultipartEntity reqEntity = new MultipartEntity();

		//判断是否有附件
		boolean flag = false ;
		
		if (fax_attach != null){
			
			flag = true ;
		}
		
		if (flag){
			for (int i=0;i<fax_attach.length;i++){
				
				InputStream is = new FileInputStream(fax_attach[i]);
				
				//名字保存到list之中
				
				String str = (String)fax_attachFileName.get(i);
				String ext = str.split("\\.")[1];
				String saveName = new IPTimeStamp(IPAddress).getIPTimeRand() + "." + ext ;
				names.add(saveName);
				//File destFile = new File(path,(String)fax_attachFileName.get(i));
				File destFile = new File(path,saveName);
				
				logger.info((String)fax_attachFileName.get(i));
				
				OutputStream os = new FileOutputStream(destFile);
				
				byte[] buffer = new byte[400];
				int length = 0;
				
				while(-1 != (length = is.read(buffer)))	{
					os.write(buffer, 0, length);
				}
				is.close();
				os.close();	
			}
		
		
			//PostMethod post = new PostMethod(uigHttp);
			//RequestEntity entity = new StringRequestEntity(str,
					//"application/x-www-form-urlencoded", "utf-8");
			
			for (int i=0;i<fax_attach.length;i++){
				/**
				 * fax_attach[i] 指向的是临时文件
				 * upload__151ef1b1_13fc202190c__8000_00000004.tmp
				 * 重命名或者二次读写
				 * file1.renameTo(file2)
				 */
				//InputStream is = new FileInputStream(fax_attach[i]);
				
				//fax_attach[i].renameTo(new File(fax_attach[i].getAbsolutePath(),(String)fax_attachFileName.get(i)));
				
				//logger.info(fax_attach[i].getAbsolutePath());
				
				//File file = new File(fax_attach[i].getAbsolutePath());
				//String root = ServletActionContext.getRequest().getRealPath("/upload");
				
				
				//FileBody fileBody = new FileBody(new File(path + "\\" + (String)fax_attachFileName.get(i)));
				FileBody fileBody = new FileBody(new File(path + File.separator + names.get(i)));
				logger.info(fileBody.getFilename());
				reqEntity.addPart("fax_attach[]",fileBody);
			}
		} // end of if(flag)
		else{
			
			FileBody fileBody = new FileBody(new File(ServletActionContext.getRequest().getRealPath(File.separator + "faxUpload") + File.separator + "noattach.txt" ));
			logger.info(fileBody.getFilename());
			reqEntity.addPart("fax_attach[]",fileBody);
			
		}
		
		/*
		logger.info(fo_subject);
		logger.info((new StringBody(fo_subject)).getCharset());
		logger.info((new StringBody(fo_subject,Charset.forName("UTF-8"))).getCharset());
		*/
		//StringBody.create(fo_subject, "UTF-8");
		
		reqEntity.addPart("session_id", new StringBody(session_id));
		reqEntity.addPart("fo_to", new StringBody(fo_to,Charset.forName("UTF-8")));
		reqEntity.addPart("fo_subject", new StringBody(fo_subject,Charset.forName("UTF-8")));
		reqEntity.addPart("urgent", new StringBody(urgent,Charset.forName("UTF-8")));
		reqEntity.addPart("need_confirm", new StringBody(need_confirm,Charset.forName("UTF-8")));
		reqEntity.addPart("fo_body", new StringBody(fo_body,Charset.forName("UTF-8")));
		reqEntity.addPart("render_as", new StringBody("json",Charset.forName("UTF-8"))); //json 形式返回结果
		
		HttpPost httpPost = new HttpPost(send_fax);
		httpPost.setEntity(reqEntity);
		
        
		org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
		
		//httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		//httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = null ;
		
        //HttpResponse response = httpClient.execute(httpPost);
        //HttpEntity resEntity = response.getEntity();
		

		/*NameValuePair[] postData = { new NameValuePair("session_id", session_id),
				new NameValuePair("fo_to", fo_to),
				new NameValuePair("urgent", String.valueOf(urgent)),
				new NameValuePair("fo_subject", fo_subject),
				new NameValuePair("fo_body", fo_body),
				new NameValuePair("fax_attach[]", null),
				//new NameValuePair("send_date", ""),
				//new NameValuePair("send_time", ""),
				};*/
		//post.setRequestEntity(entity);
		//post.setRequestBody(postData);
		
		try {
			responseBody = httpClient.execute(httpPost, responseHandler);
			logger.info(responseBody);
			//int statusCode = httpClient.executeMethod(post);
			//result = post.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//post.releaseConnection();
			try { 
            	httpClient.getConnectionManager().shutdown(); 
            	} catch (Exception ignore) {
            		ignore.printStackTrace();
            	}
		}
		//result = parseGZipRes(post);
		return responseBody;
	}
	
	/** 
	* @title: sendPostForLogin 
	* @description: 接收传真
	* @author cjw
	* @param httpPath
	* @return
	* @throws Exception
	* @date 2013-5-6 下午02:24:52     
	* @throws 
	*/ 
	public String receiveFax(String httpPath,String filePath) throws Exception {
		String uigHttp =httpPath ;
		String str = "";
		PostMethod post = new PostMethod(uigHttp);
		RequestEntity entity = new StringRequestEntity(str,
				"application/x-www-form-urlencoded", "utf-8");
//		NameValuePair[] postData = { new NameValuePair("session_id", sessionId),
//				new NameValuePair("fo_to", sessionId),
//				};
		post.setRequestEntity(entity);
//		post.setRequestBody(postData);
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		//String result = null;
		try {
			int statusCode =httpClient.executeMethod(post);
			FileUtils.writeByteArrayToFile(new File(filePath),post.getResponseBody());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		//System.err.println(result);
//		result = parseGZipRes(post);
//		System.err.println(result);
		return "";
	}
	
	
	
	/** 
	* @title: sendPostForLogin 
	* @description: 查询收到的传真列表
	* @author cjw
	* @param httpPath
	* @return
	* @throws Exception
	* @date 2013-5-6 下午02:24:52     
	* @throws 
	*/ 
	public String sendPostForReceiveSearch(String httpPath,String sessionId) throws Exception {
		String uigHttp =httpPath ;
		String str = "";
		PostMethod post = new PostMethod(uigHttp);
		RequestEntity entity = new StringRequestEntity(str,
				"application/x-www-form-urlencoded", "utf-8");
		NameValuePair[] postData = { new NameValuePair("session_id", sessionId),
				//new NameValuePair("time_after", beginTime),
				//new NameValuePair("time_before", endTime)
				};
		
		//
		//
		post.setRequestEntity(entity);
		post.setRequestBody(postData);
		HttpClient httpClient = new HttpClient();
		//httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(
			//	timeout);
		//httpClient.getHttpConnectionManager().getParams().setSoTimeout(
				//readtimeout);
		
		
//		HttpClientParams clientParams = new HttpClientParams();
//		clientParams.setParameter("username", "admin");
//		clientParams.setParameter("password", "011c945f30ce2cbafc452f39840f025693339c42");
//		//clientParams.setParameter("page_size", "30");
//		httpClient.setParams(clientParams);
		
		
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		String result = null;
		try {
			int statusCode =httpClient.executeMethod(post);
			log.info("query fax list statusCode = " + statusCode);
			result = post.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		//result = parseGZipRes(post);
		return result;
	}
	
	/** 
	* @title: sendPostForLogin 
	* @description: 查询外发的传真列表
	* @author cjw
	* @param httpPath
	* @return
	* @throws Exception
	* @date 2013-5-6 下午02:24:52     
	* @throws 
	*/ 
	public String currentFaxForSendSearch(String httpPath, String sessionId,
			String receiver, String subject) throws Exception {
		
		String uigHttp = httpPath ;
		String str = "";
		PostMethod post = new PostMethod(uigHttp);
		
		//logger.info("subject  = " + subject);
		logger.info("receiver = " + receiver);
		//logger.info("receiver test - " + receiver.contains(";"));
		
		//如果有多个收件人
		if (receiver.contains(";")){
			receiver = receiver.split("\\;")[0];
		}
		
		RequestEntity entity = new StringRequestEntity(str,
				"application/x-www-form-urlencoded", "utf-8");
		NameValuePair[] postData = { 
				new NameValuePair("session_id", sessionId),
				//new NameValuePair("time_after", beginTime),
				//new NameValuePair("time_before", endTime),
				new NameValuePair("receiver", receiver),
				new NameValuePair("subject", subject)
				};
		/*for (int i=0;i<postData.length;i++){
			logger.info(postData[i].getName() + "=" + postData[i].getValue());
		}*/
		post.setRequestEntity(entity);
		post.setRequestBody(postData);
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		String result = null;
		try {
			int statusCode =httpClient.executeMethod(post);
			log.info(statusCode);
			result = post.getResponseBodyAsString();
			log.info(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		//result = parseGZipRes(post);
		return result;
	}
	
	/** 
	* @title: sendPostForLogin 
	* @description: 下载发送的传真 附件
	* @author cjw
	* @param httpPath
	* @return
	* @throws Exception
	* @date 2013-5-6 下午02:24:52     
	* @throws 
	*/ 
	public boolean sendFaxForDownload(String httpPath,String id,String subject) throws Exception {
		boolean res = false;
		String uigHttp =httpPath ;
		String str = "";
		PostMethod post = new PostMethod(uigHttp);
		RequestEntity entity = new StringRequestEntity(str,
				"application/x-www-form-urlencoded", "utf-8");
//		NameValuePair[] postData = { new NameValuePair("session_id", sessionId),
//				new NameValuePair("fo_to", sessionId),
//				};
		post.setRequestEntity(entity);
//		post.setRequestBody(postData);
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		//String result = null;
		try {
			int statusCode =httpClient.executeMethod(post);
			log.info(statusCode);
//		 常量的配置	Constants.DBMAP.get("WS_DOORLOCK_CHECKTIME")
			if(post.getResponseBody()!=null){
				FileUtils.writeByteArrayToFile(new File(Constants.DBMAP.get("FAX_FILEPATH")+id+subject+".zip"),post.getResponseBody());
				res = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		//System.err.println(result);
//		result = parseGZipRes(post);
//		System.err.println(result);
		return res;
	}
	
	/** 
	* @title: sendPostForLogin 
	* @description: 下载发送的传真 附件
	* @author cjw
	* @param httpPath
	* @return
	* @throws Exception
	* @date 2013-5-6 下午02:24:52     
	* @throws 
	*/ 
	public String sendFaxForDownload(String httpPath,String filePath) throws Exception {
		String uigHttp =httpPath ;
		String str = "";
		//filePath = "E:\\hello\\attach.zip";
		String session_id = httpPath.split("=")[1].split("&&")[0];
		logger.info(session_id);
		filePath = ServletActionContext.getServletContext().getRealPath("/faxAttach");
		//String path = ServletActionContext.getRequest().getRealPath("/faxUpload/" + session_id);
		
		PostMethod post = new PostMethod(uigHttp);
		RequestEntity entity = new StringRequestEntity(str,
				"application/x-www-form-urlencoded", "utf-8");
//		NameValuePair[] postData = { new NameValuePair("session_id", sessionId),
//				new NameValuePair("fo_to", sessionId),
//				};
		post.setRequestEntity(entity);
//		post.setRequestBody(postData);
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		//String result = null;
		try {
			int statusCode =httpClient.executeMethod(post);
			logger.info(String.valueOf(statusCode));
			
			/**
			 * 如果存在则显清空目录
			 */
			File fileToRemove = new File(filePath + File.separator + session_id);
			
			if (fileToRemove.exists() && fileToRemove.isDirectory()){
				
				//FileUtil.deleteDir(file);
				File[] files = fileToRemove.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						File tempFile = files[i];
						if (tempFile.isFile()) {
							tempFile.delete();
						} 
					}
				}
			}
			
			
			
			FileUtils.writeByteArrayToFile(new File(filePath + File.separator + session_id + File.separator + "attach.zip"),post.getResponseBody());
								//new File(path + "\\" + fax_attachFileName.get(i))
			str = "200&" + session_id ;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		//System.err.println(result);
//		result = parseGZipRes(post);
//		System.err.println(result);
		return str;
	}
	
	public static String parseGZipRes(PostMethod req) {
		StringBuilder strBuf = new StringBuilder();
		try {
			GZIPInputStream gzipis = new GZIPInputStream(req
					.getResponseBodyAsStream());
			InputStreamReader isr = new InputStreamReader(gzipis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				strBuf.append(line);
			}
		} catch (IOException ex) {

		}
		return strBuf.toString();
	}
	
	 
}
