package com.fujitsu.cn.ise;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class ISEOpenDoor {

	/**
	 * ISE开门测试
	 * 
	 * @param args
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void main(String[] args) throws KeyManagementException,
			UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, DocumentException,
			IOException {
		final String[] macAddresses = { "84:78:AC:ED:DD:94",
				"84:78:AC:ED:E2:39", "84:78:AC:ED:DC:BD", "84:78:AC:EC:D3:E1",
				"84:78:AC:ED:DD:21", "84:78:AC:ED:E0:CB", "84:78:AC:ED:DC:B4",
				"84:78:AC:ED:DD:72", "84:78:AC:ED:E0:AD" };
		for (int j = 0; j < 2; j++) {
			long startTime = System.currentTimeMillis();
			String ISE_RESPONSE = ISEOpenDoor
					.sendRequestToISE("20:BB:C0:20:46:8B");
			Document doc = DocumentHelper.parseText(ISE_RESPONSE);
			Element rootElt = doc.getRootElement();
			Node networkDeviceNameNode = rootElt
					.selectSingleNode("/sessionParameters/network_device_name");
			if (networkDeviceNameNode != null) {
				System.out.println(networkDeviceNameNode.getText().trim());
			}
			Node nasPortIDNode = rootElt
					.selectSingleNode("/sessionParameters/nas_port_id");
			if (nasPortIDNode != null) {
				System.out.println(nasPortIDNode.getText().trim());
			}
			Node nasIPAddressNode = rootElt
					.selectSingleNode("/sessionParameters/nas_ip_address");
			if (nasIPAddressNode != null) {
				System.out.println(nasIPAddressNode.getText().trim());
			}
			long endTime = System.currentTimeMillis();
			System.out.println("第" + j + "个线程执行时间 ：" + (endTime - startTime)
					+ "毫秒");
		}
		// KeyStore trustStore =
		// KeyStore.getInstance(KeyStore.getDefaultType());
		// FileInputStream instream = new FileInputStream(new
		// File("D://keystore"));
		// try {
		// trustStore.load(instream, "123456".toCharArray());
		// } finally {
		// try {
		// instream.close();
		// } catch (Exception ignore) {
		// }
		// }
		// for (int i = 0; i < 3; i++) {
		// final int j = i;
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// try {
		// KeyStore trustStore = KeyStore.getInstance(KeyStore
		// .getDefaultType());
		// FileInputStream instream = new FileInputStream(
		// new File("D://keystore" + j));
		// try {
		// trustStore.load(instream, "123456".toCharArray());
		// } catch (CertificateException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// instream.close();
		// } catch (Exception ignore) {
		// }
		// }
		// DefaultHttpClient httpclient = new DefaultHttpClient();
		// httpclient.getParams().setParameter(
		// ClientPNames.COOKIE_POLICY,
		// CookiePolicy.BROWSER_COMPATIBILITY);
		// SSLSocketFactory socketFactory = new SSLSocketFactory(
		// trustStore);
		// Scheme sch = new Scheme("https", 443, socketFactory);
		// httpclient.getConnectionManager().getSchemeRegistry()
		// .register(sch);
		// CredentialsProvider credsProvider = new BasicCredentialsProvider();
		// credsProvider.setCredentials(new AuthScope(
		// AuthScope.ANY),
		// new UsernamePasswordCredentials("admin",
		// "NJxxzx@123"));
		// httpclient.setCredentialsProvider(credsProvider);
		// HttpGet httpGet = new HttpGet(
		// "https://ise-njxxzx.cisco.loacl/ise/mnt/api/AuthStatus/MACAddress/"
		// + macAddresses[j] + "/120/100/All");
		// long startTime = System.currentTimeMillis();
		// HttpResponse response = httpclient.execute(httpGet);
		// HttpEntity entity = response.getEntity();
		// if (entity != null) {
		// String responseXML = EntityUtils.toString(entity);
		// }
		// httpclient.getConnectionManager().shutdown();
		// long endTime = System.currentTimeMillis();
		// System.out.println("第" + j + "个线程执行时间 ："
		// + (endTime - startTime) + "毫秒");
		// } catch (KeyManagementException e) {
		// e.printStackTrace();
		// } catch (UnrecoverableKeyException e) {
		// e.printStackTrace();
		// } catch (KeyStoreException e) {
		// e.printStackTrace();
		// } catch (NoSuchAlgorithmException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();
		//
		// }
	}

	/**
	 * IP电话向ISE发起Session查询请求方法
	 * 
	 * @param macAddress
	 *            IP电话MAC地址
	 * @return 返回network_device_name,nas_port_id,nas_ip_address
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static HashMap<String, String> getPhoneInfo(String macAddress)
			throws KeyManagementException, UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException,
			DocumentException, IOException {
		HashMap<String, String> clientInfo = new HashMap<String, String>();
		Document doc = null;
		String ISE_RESPONSE = ISEOpenDoor.sendRequestToISE(macAddress);
		// System.out.println("ISE_RESPONSE =" + ISE_RESPONSE);
		doc = DocumentHelper.parseText(ISE_RESPONSE);
		Element rootElt = doc.getRootElement();
		Node networkDeviceNameNode = rootElt
				.selectSingleNode("/sessionParameters/network_device_name");
		if (networkDeviceNameNode == null) {
			return null;
		}
		Node nasPortIDNode = rootElt
				.selectSingleNode("/sessionParameters/nas_port_id");
		if (nasPortIDNode == null) {
			return null;
		}
		Node nasIPAddressNode = rootElt
				.selectSingleNode("/sessionParameters/nas_ip_address");
		if (nasIPAddressNode == null) {
			return null;
		}
		// Node networkDeviceNameNode = rootElt
		// .selectSingleNode("/authStatusOutputList/authStatusList/authStatusElements/network_device_name");
		// if (networkDeviceNameNode == null) {
		// return null;
		// }
		// Node nasPortIDNode = rootElt
		// .selectSingleNode("/authStatusOutputList/authStatusList/authStatusElements/nas_port_id");
		// if (nasPortIDNode == null) {
		// return null;
		// }
		// Node nasIPAddressNode = rootElt
		// .selectSingleNode("/authStatusOutputList/authStatusList/authStatusElements/nas_ip_address");
		// if (nasIPAddressNode == null) {
		// return null;
		// }
		clientInfo.put("network_device_name", networkDeviceNameNode.getText()
				.trim());
		clientInfo.put("nas_port_id", nasPortIDNode.getText().trim());
		clientInfo.put("nas_ip_address", nasIPAddressNode.getText().trim());
		return clientInfo;
	}

	public static String sendRequestToISE(String macAddress)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, KeyManagementException,
			UnrecoverableKeyException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			// keystore文件存放地址
			FileInputStream instream = new FileInputStream(new File(
					"/usr/local/webapps/app_njhw/WEB-INF/keystore"));
			// FileInputStream instream = new FileInputStream(new File(
			// "D://keystore"));
			try {
				trustStore.load(instream, "123456".toCharArray());
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			Scheme sch = new Scheme("https", 443, socketFactory);
			httpclient.getConnectionManager().getSchemeRegistry().register(sch);
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(AuthScope.ANY),
					new UsernamePasswordCredentials("admin", "XXZXise123"));
			httpclient.setCredentialsProvider(credsProvider);
			// HttpGet httpGet = new HttpGet(
			// "https://ise-njxxzx.cisco.loacl/ise/mnt/api/AuthStatus/MACAddress/"
			// + macAddress + "/120/100/All");
			HttpGet httpGet = new HttpGet(
					"https://ise-njxxzx.cisco.loacl/ise/mnt/api/Session/MACAddress/"
							+ macAddress);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String responseXML = EntityUtils.toString(entity);
				return responseXML.length() > 0 ? responseXML : null;
			} else {
				return null;
			}
			// EntityUtils.consume(entity);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}
}
