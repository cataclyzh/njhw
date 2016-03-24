package fax;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.cosmosource.app.utils.Unicode;

import net.sf.json.JSONObject;

public class TestFax {

	public static void main(String[] args) throws Exception{
		TestFax tf = new TestFax();
//		tf.faxLogin("infoAdmin", "21232F297A57A5A743894A0E4A801FC3");
//		tf.faxLogin("njhwAdmin", "E10ADC3949BA59ABBE56E057F20F883E");
		tf.faxLogin("zhangjunx", "bb");
	}
	
	public void faxLogin(String username, String password) throws Exception{
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		//nvps.add(new BasicNameValuePair("page_size", "10"));
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://10.254.101.100/ipcom/index.php/Api/login");
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpclient.execute(httpPost, responseHandler);
		
//		System.out.println(Unicode.decodeUnicode(responseBody));

		// 构造返回json数据
		JSONObject jObject = JSONObject.fromObject(responseBody);
		System.out.println(jObject);
	}

}
