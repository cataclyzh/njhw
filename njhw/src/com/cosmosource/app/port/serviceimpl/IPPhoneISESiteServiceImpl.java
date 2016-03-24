package com.cosmosource.app.port.serviceimpl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;

import org.dom4j.DocumentException;

import com.cosmosource.app.port.service.IPPhoneISESiteService;
import com.cosmosource.app.port.service.IPPhoneManager;
import com.cosmosource.base.util.Constants;
import com.fujitsu.cn.ise.ISEOpenDoor;

public class IPPhoneISESiteServiceImpl implements IPPhoneISESiteService {

	public static void main(String[] args) {
		String tmp = "XC_D_4F01_SW4";
		System.out.println(tmp.substring(0, tmp.length() - 1));
		int j = Integer.valueOf(tmp.substring(tmp.length() - 1)) + 2;
		String network_device_name = tmp.substring(0, tmp.length() - 1) + j;
		System.out.println(network_device_name);
	}

	@Override
	@WebMethod
	public String controlDoor(
			@WebParam(name = "macAddress", mode = Mode.IN) String macAddress) {
		try {
			HashMap<String, String> clientInfo = ISEOpenDoor
					.getPhoneInfo(macAddress);
			HashMap<String, String> queryMap = new HashMap<String, String>();
			String tmp_nas_port_id = clientInfo.get("nas_port_id");
			String nas_port_id = tmp_nas_port_id.substring(15);
			String[] portInfo = nas_port_id.split("/");
			// String nas_ip_address = clientInfo.get("nas_ip_address");
			String tmp_network_device_name = clientInfo
					.get("network_device_name");
			String network_device_name = null;
			int i = Integer.valueOf(portInfo[0]);
			int j = 0;
			if (i == 1) {
				network_device_name = tmp_network_device_name;
			} else if (i == 2) {
				j = Integer.valueOf(tmp_network_device_name
						.substring(tmp_network_device_name.length() - 1)) + 1;
				network_device_name = tmp_network_device_name.substring(0,
						tmp_network_device_name.length() - 1) + j;
			} else if (i == 3) {
				j = Integer.valueOf(tmp_network_device_name
						.substring(tmp_network_device_name.length() - 1)) + 2;
				network_device_name = tmp_network_device_name.substring(0,
						tmp_network_device_name.length() - 1) + j;
			}
			queryMap.put("nas_port_id", portInfo[2]);
			queryMap.put("network_device_name", network_device_name);
			String roomNo = iPPhoneManager.getRoomNo(queryMap);
			String msgId = UUID.randomUUID().toString();
			String[] tmpMacAddress = macAddress.split(":");
			StringBuilder stringBuilder = new StringBuilder();
			for (String tmp : tmpMacAddress) {
				stringBuilder.append(tmp);
			}
			String doorId = iPPhoneManager.controlDoorISE(roomNo);
			doorControlToAppService.controlDoor(doorId,
					Constants.DBMAP.get("WS_DOORORDER_OPEN"),
					stringBuilder.toString(), msgId);
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return null;
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
			return null;
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><stat>OK</stat></response>";
	}

	@Override
	@WebMethod
	public String checkDoorAuth() {
		return null;
	}

	@Override
	@WebMethod
	public void checkCommAndDoorStatus() {

	}

	/**
	 * get getDoorControlToAppService
	 * 
	 * @return getDoorControlToAppService
	 */
	public DoorControlToAppService getDoorControlToAppService() {
		return doorControlToAppService;
	}

	/**
	 * set doorControlToAppService
	 * 
	 * @param doorControlToAppService
	 *            doorControlToAppService
	 */
	public void setDoorControlToAppService(
			DoorControlToAppService doorControlToAppService) {
		this.doorControlToAppService = doorControlToAppService;
	}

	/**
	 * @return the iPPhoneManager
	 */
	public IPPhoneManager getiPPhoneManager() {
		return iPPhoneManager;
	}

	/**
	 * @param iPPhoneManager
	 *            the iPPhoneManager to set
	 */
	public void setiPPhoneManager(IPPhoneManager iPPhoneManager) {
		this.iPPhoneManager = iPPhoneManager;
	}

	/**
	 * doorControlToAppService
	 */
	private DoorControlToAppService doorControlToAppService;

	/**
	 * iPPhoneManager
	 */
	private IPPhoneManager iPPhoneManager;

}
