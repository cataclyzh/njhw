package com.cosmosource.app.transfer.serviceimpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.base.util.StringUtil;


/**
 * @ClassName:TravelInformationToSystemService
 * @Description：交通信息的操作（路况数据）
 * @Author：qyq
 * @Date:2013-5-01
 */

public class TravelInformationToSystemService {
	
	private static final Log log = LogFactory.getLog( TravelInformationToSystemService.class);
	
	public String  sendPost(String httpPath ) throws Exception {
		if(StringUtil.isBlank(httpPath)){
			log.info("参数不能为空!");
			return null;
		}
		String uigHttp =httpPath ;
		String str = "";
		PostMethod post = new PostMethod(uigHttp);
		RequestEntity entity = new StringRequestEntity(str,	"application/x-www-form-urlencoded", "utf-8");
		post.setRequestEntity(entity);
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"	Mozilla/5.0 (Windows NT 5.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
		String result = null;
		try {
			
			result = post.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		result = parseGZipRes(post);
		return result;
	}

	public static String parseGZipRes(PostMethod req) {
		StringBuilder strBuf = new StringBuilder();
		try {
			GZIPInputStream gzipis = new GZIPInputStream(req.getResponseBodyAsStream());
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
