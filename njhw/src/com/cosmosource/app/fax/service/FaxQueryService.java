/**
 * 
 */
package com.cosmosource.app.fax.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cosmosource.app.utils.Unicode;
import com.cosmosource.base.service.BaseManager;

/**
 * @author Administrator
 * 
 */
public class FaxQueryService extends BaseManager {
	private String webRoot = "E:\\Workspace\\app_njhw\\WebRoot\\";
	private String foSavePath = "attachment\\Fo\\";
	private String fiSavePath = "attachment\\Fi\\";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * 登陆： POST /Api/login
	 * 
	 * @param nvps
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public JSONObject login(List<NameValuePair> nvps) throws Exception {
		// "admin", "011c945f30ce2cbafc452f39840f025693339c42"
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://10.254.101.100/ipcom/index.php/Api/login");
		JSONObject jObject = null;
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpPost, responseHandler);
			logger.info("http://10.254.101.100/ipcom/index.php/Api/login");
			logger.info(Unicode.decodeUnicode(responseBody));

			// 构造返回json数据
			jObject = JSONObject.fromObject(responseBody);

		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
			httpPost.releaseConnection();
		}

		return jObject;
	}

	/**
	 * 查询收到的传真列表: POST /Api/fi_list
	 * 
	 * @param nvps
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public JSONObject getReceivedFaxesList(List<NameValuePair> nvps)
			throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://10.254.101.100/ipcom/index.php/Api/fi_list");
		JSONObject jObject = null;
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpPost, responseHandler);
			logger.info("http://10.254.101.100/ipcom/index.php/Api/fi_list");
			logger.info(Unicode.decodeUnicode(responseBody));

			// 构造返回json数据

			jObject = JSONObject.fromObject(responseBody);

		} finally {
			httpPost.releaseConnection();
		}

		return jObject;

	}

	/**
	 * 查询外发的传真列表: POST /Api/fo_list
	 * 
	 * @param nvps
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public JSONObject getSentFaxesList(List<NameValuePair> nvps)
			throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://10.254.101.100/ipcom/index.php/Api/fo_list");
		JSONObject jObject = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpPost, responseHandler);
			logger.info("http://10.254.101.100/ipcom/index.php/Api/fo_list");
			logger.info(Unicode.decodeUnicode(responseBody));

			// 构造返回json数据
			jObject = JSONObject.fromObject(responseBody);

		} finally {
			httpPost.releaseConnection();
		}

		return jObject;
	}

	public void fiDownload(String session_id, String id) throws Exception {
		int BUFFER = 1024;

		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(
				"http://10.254.101.100/ipcom/index.php/Api/fi_download?session_id="
						+ session_id + "&id=" + id);
		try {
			client.executeMethod(httpGet);
			logger.info("http://10.254.101.100/ipcom/index.php/Api/fi_download?session_id="
					+ session_id + "&id=" + id);
			InputStream in = httpGet.getResponseBodyAsStream();
			File saveFile = new File(webRoot + fiSavePath + id + ".tif");

			File parent = saveFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}

			FileOutputStream out = new FileOutputStream(saveFile);
			byte[] b = new byte[BUFFER];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			in.close();
			out.close();

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		logger.info("download, success!!");
	}

	public String foDownload(String session_id, String id) throws Exception {
		int BUFFER = 1024;

		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(
				"http://10.254.101.100/ipcom/index.php/Api/fo_download?session_id="
						+ session_id + "&id=" + id);
		try {
			client.executeMethod(httpGet);
			logger.info("http://10.254.101.100/ipcom/index.php/Api/fo_download?session_id="
					+ session_id + "&id=" + id);
			InputStream in = httpGet.getResponseBodyAsStream();
			File saveFile = new File(webRoot + foSavePath + id + ".zip");

			File parent = saveFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}

			FileOutputStream out = new FileOutputStream(saveFile);
			byte[] b = new byte[BUFFER];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			in.close();
			out.close();

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		logger.info("download, success!!");
		return webRoot + foSavePath + id + ".zip";
	}

	public String unzip(String id) throws Exception {
		File file = new File(webRoot + foSavePath + id + ".zip");
		// 压缩文件
		ZipFile zipFile = new ZipFile(file);
		// 实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		// 实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(
				file));
		ZipEntry zipEntry = null;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			File temp = new File(webRoot + foSavePath + id + "\\" + fileName);
			if (!temp.getParentFile().exists())
				temp.getParentFile().mkdirs();
			OutputStream os = new FileOutputStream(temp);
			// 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
			InputStream is = zipFile.getInputStream(zipEntry);
			int len = 0;
			while ((len = is.read()) != -1)
				os.write(len);
			os.close();
			is.close();
		}
		zipInputStream.close();
		return webRoot + foSavePath + id + "\\";

	}

	public void getFileList(String path) {
		File fileDir = new File(path);
		logger.info("-----------File List Start------------");
		if (fileDir.isDirectory()) {
			File files[] = fileDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File currFile = files[i];
				if (currFile.isFile()) {
					logger.info(currFile.getName());
				}
			}
		}
		logger.info("-----------File List End--------------");
	}

	/**
	 * 获取个人通讯录
	 * 
	 * @param userid
	 * @param searchVal
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HashMap> getPersonalContactList(Long userid, String searchVal) {
		HashMap map = new HashMap(); // 参数Map
		map.put("userid", userid);
		if (!searchVal.equals(""))
			map.put("searchVal", searchVal);
		return sqlDao.findList("FaxSQL.getPersonalContactList", map);
	}

	/**
	 * 获取最近列表标识符
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long getFaxRecentId() {
		Long id = (long) 0;

		HashMap map = new HashMap(); // 参数Map
		List<Map> cList = null; // 列表
		cList = this.findListBySql("FaxSQL.getFaxRecentId", map);
		for (Map m : cList) {
			id = Long.parseLong(m.get("NEXTVAL").toString());

		}

		return id;
	}

	/**
	 * 插入最近列表记录
	 * 
	 * @param fc_id
	 * @param userid
	 * @param fax_id
	 * @param fax_type
	 * @param fax_no
	 * @param fax_time
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertFaxRecentContact(Long fc_id, Long userid, Long fax_id,
			int fax_type, String fax_no, String fax_time) {
		HashMap map = new HashMap(); // 参数Map
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		map.put("fc_id", fc_id);
		map.put("userid", userid);
		map.put("fax_id", fax_id);
		map.put("fax_type", fax_type);
		map.put("fax_no", fax_no);
		map.put("fax_time", fax_time);
		map.put("insert_time", df.format(new Date()));
		List<Map> list = new ArrayList<Map>();
		list.add(map);
		sqlDao.batchInsert("FaxSQL.insertFaxRecentContact", list);
	}

	/**
	 * 获取最近列表
	 * 
	 * @param userid
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HashMap> getFaxRecentList(Long userid) {
		HashMap map = new HashMap(); // 参数Map
		map.put("userid", userid);

		return sqlDao.findList("FaxSQL.getFaxRecentList", map);
	}

	/**
	 * 根据传真号获取个人通讯录名字
	 * 
	 * @param userid
	 * @param fax_no
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getUsernameFromFax(Long userid, String fax_no) {
		List<HashMap> list = null;
		HashMap map = new HashMap(); // 参数Map
		String name = "";
		map.put("userid", userid);
		map.put("fax_no", fax_no);
		list = sqlDao.findList("FaxSQL.getUsernameFromFax", map);
		//logger.info("aaaaaaaaaaaaaaaaaaaaaa"+list.size());
		for (int i = 0; i < list.size(); i++) {
			//logger.info(list.get(i).get("NUA_NAME").toString());
			//logger.info(list.get(i).get("NUA_TEL3").toString());
			String[] faxArray=list.get(i).get("NUA_TEL3").toString().split(",");
			for (int j = 0; j < faxArray.length; j++) {
				if (faxArray[j].equals(fax_no)) {
					name = list.get(i).get("NUA_NAME").toString();
				}
			}
			
			
		}

		//logger.info(name);
		return name;
	}

	/**
	 * 删除个人通讯录
	 * 
	 * @param nuaid
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deletePersonalContact(Long nuaid) {

		HashMap map = new HashMap(); // 参数Map
		map.put("nuaid", nuaid);
		List<Map> list = new ArrayList<Map>();
		list.add(map);
		sqlDao.batchDelete("FaxSQL.deletePersonalContact", list);
	}
	
	/**
	 * 数据库查询当前用户是否有网络传真号码
	 * @param loginName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List hasWebFaxNum(String loginName) {
		//数据库查询网络传真号码
		HashMap map = new HashMap();
		map.put("loginUid", loginName);
		List list = sqlDao.findList("FaxSQL.queryWebFaxNumByLoginUid", map);
		logger.info(list.toString());
		return list;
	}

}
