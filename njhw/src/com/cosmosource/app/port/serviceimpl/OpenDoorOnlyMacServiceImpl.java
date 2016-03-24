package com.cosmosource.app.port.serviceimpl;

import java.util.HashMap;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;

import com.cosmosource.app.port.service.IPPhoneManager;
import com.cosmosource.app.port.service.OpenDoorOnlyMacService;
import com.cosmosource.base.util.Constants;

public class OpenDoorOnlyMacServiceImpl implements OpenDoorOnlyMacService {

	private static final String OK = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><stat>OK</stat></response>";
	private static final String ERROR = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><stat>NO RECORD</stat></response>";

	public static void main(String[] args) {
		String tmp = "XC_D_4F01_SW4";
		System.out.println(tmp.substring(0, tmp.length() - 1));
		int j = Integer.valueOf(tmp.substring(tmp.length() - 1)) + 2;
		String network_device_name = tmp.substring(0, tmp.length() - 1) + j;
		System.out.println(network_device_name);
	}

	@Override
	@WebMethod
	public String openDoorOnlyMac(
			@WebParam(name = "macAddress", mode = Mode.IN) String macAddress) {
		String msgId = UUID.randomUUID().toString();
		HashMap<String, String> phoneMacInfo = iPPhoneManager
				.getPhoneMacInfo(macAddress);
		if (phoneMacInfo == null) {
			return ERROR;
		}
		// 开门
		doorControlToAppService.controlDoor(phoneMacInfo.get("roomId"),
				Constants.DBMAP.get("WS_DOORORDER_OPEN"), macAddress, msgId);
		return OK;
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
