package com.cosmosource.app.port.serviceimpl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;

import org.dom4j.DocumentException;

import com.cosmosource.app.port.service.IPPhoneManager;
import com.cosmosource.app.port.service.IPPhoneRegisterService;
import com.fujitsu.cn.ise.ISEOpenDoor;

public class IPPhoneRegisterServiceImpl implements IPPhoneRegisterService {

	/**
	 * 成功响应
	 */
	private static final String OK = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><stat>OK</stat></response>";

	/**
	 * 异常响应
	 */
	private static final String ERROR = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><stat>NO UPDATE</stat></response>";

	public static void main(String[] args) {
		String tmp = "XC_D_4F01_SW4";
		System.out.println(tmp.substring(0, tmp.length() - 1));
		int j = Integer.valueOf(tmp.substring(tmp.length() - 1)) + 2;
		String network_device_name = tmp.substring(0, tmp.length() - 1) + j;
		System.out.println(network_device_name);
	}

	@Override
	@WebMethod
	public String onlineAlert(
			@WebParam(name = "macAddress", mode = Mode.IN) String macAddress) {
		try {
			HashMap<String, String> clientInfo = ISEOpenDoor
					.getPhoneInfo(macAddress);
			if (clientInfo == null) {
				return ERROR;
			}
			/**
			 * 根据ISE返回结果如何匹配到房间号算法说明: ISE返回数据:
			 * <network_device_name>XC_D_4F01_SW4</network_device_name>
			 * <nas_port_id>GigabitEthernet2/0/4</nas_port_id> 算法:
			 * 只会返回XC_D_4F01_SW1或这XC_D_4F01_SW4,SW1代表第一组交换机,SW4代表第二组交换机,每组交换机有3个
			 * GigabitEthernet2/0/4中第一个数字"2"代表第二个交换机 那返回的结果表示:第2组交换机中的第2个交换机,
			 * 那对应于T_004_SWITCH_INFO表中SWITCH_NAME真实交换机字段值为XC_D_4F01_SW5
			 * XC_D_4F01_SW5就代表第2组交换机中的第2个交换机
			 * GigabitEthernet2/0/4中第三个数字"4"代表端口号,对应于T_004_SWITCH_INFO表中PORT字段
			 * 根据PORT和SWITCH_NAME字段就可以唯一匹配到ROOM_NO字段.
			 */
			HashMap<String, String> queryMap = new HashMap<String, String>();
			String tmp_nas_port_id = clientInfo.get("nas_port_id");
			String nas_port_id = tmp_nas_port_id.substring(15);
			String[] portInfo = nas_port_id.split("/");
			// String nas_ip_address = clientInfo.get("nas_ip_address");
			String tmp_network_device_name = clientInfo
					.get("network_device_name");
			if (tmp_network_device_name.equals("DefaultNetworkDevice")) {
				return ERROR;
			}
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
			// String msgId = UUID.randomUUID().toString();
			String[] tmpMacAddress = macAddress.split(":");
			StringBuilder stringBuilder = new StringBuilder();
			for (String tmp : tmpMacAddress) {
				stringBuilder.append(tmp);
			}
			String formatMacAddress = stringBuilder.toString();
			String doorId = iPPhoneManager.controlDoorISE(roomNo);
			// 话机注册后保留注册信息
			HashMap<String, String> phoneMacInfo = new HashMap<String, String>();
			phoneMacInfo.put("macAddress", formatMacAddress);
			phoneMacInfo.put("rooId", doorId);
			phoneMacInfo.put("roomNo", roomNo);
			iPPhoneManager.updatePhoneMacaddress(phoneMacInfo);
			// doorControlToAppService.controlDoor(doorId,
			// Constants.DBMAP.get("WS_DOORORDER_OPEN"), formatMacAddress,
			// msgId);
			return OK;
		} catch (KeyManagementException e) {
			e.printStackTrace();
			return ERROR;
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
			return ERROR;
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return ERROR;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return ERROR;
		} catch (CertificateException e) {
			e.printStackTrace();
			return ERROR;
		} catch (DocumentException e) {
			e.printStackTrace();
			return ERROR;
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR;
		}
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
