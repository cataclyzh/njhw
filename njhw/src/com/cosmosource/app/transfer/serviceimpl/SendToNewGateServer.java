package com.cosmosource.app.transfer.serviceimpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class SendToNewGateServer {

	private static final String BASE_URL = "http://localhost:8081/gateserver/";

	public String addRight(String userName, String cardId, String right) throws Exception {
		try {
			String params = "userName=" + userName 
					+ "&cardId=" + cardId 
					+ "&ioFlag=" + right;
			String responseBody = post(BASE_URL+"insert", params);
			JSONObject result = JSONObject.fromObject(responseBody);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	public String deleteRight(String cardId) {
		try {
			String params = "cardId=" + cardId; 
			String responseBody = post(BASE_URL+"delete", params);
			JSONObject result = JSONObject.fromObject(responseBody);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}

	private static String post(String strURL, String params) {
		System.out.println(strURL);
		System.out.println(params);
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			//connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(5000);
			// connection.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded"); // 设置发送数据的格式

			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8编码
				System.out.println(result);
				return result;
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return "error"; // 自定义错误信息
	}

	public static void main(String[] args) throws Exception {
//		SendToNewGateServer sgs = new SendToNewGateServer();
//		String result = sgs.addRight("aa1", "0000996164203344", "ALL");
//		System.out.println("result: " + result);
		
		SendToNewGateServer sgs = new SendToNewGateServer();
		String result = sgs.deleteRight("0000996164203344");
		System.out.println("result: " + result);
	}

}
