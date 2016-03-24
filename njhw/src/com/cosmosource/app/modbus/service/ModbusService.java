/**
 * 
 */
package com.cosmosource.app.modbus.service;

import com.cosmosource.base.service.ModbusManager;
import com.ghgande.j2mod.modbus.util.BitVector;

/**
 * @author Administrator
 * 
 */
public class ModbusService extends ModbusManager {
	private String MODBUS_SERVER_PROVIDER_URL = getProperties("MODBUS_SERVER_PROVIDER_URL");
	private int MODBUS_SERVER_PORT = Integer
			.parseInt(getProperties("MODBUS_SERVER_PORT"));

	public static int ERROR = -1;
	public static int LOCKED = 0;
	public static int EMPTY = 1;
	public static int FULL = 2;

	public int getStatus(int controlId, int statusId) {
		BitVector readInputDiscretesVector = null;
		BitVector readCoilsVector = null;
		int status = ERROR;
		if (super.connect(MODBUS_SERVER_PROVIDER_URL, MODBUS_SERVER_PORT)) {
			readInputDiscretesVector = super.readInputDiscretes(statusId, 1);
			readCoilsVector = super.readCoils(controlId, 1);
			if (readInputDiscretesVector != null && readCoilsVector != null) {
				if (readInputDiscretesVector.getBit(0)) {
					status = LOCKED;
				} else {
					status = (readCoilsVector.getBit(0) ? FULL : EMPTY);
				}
			}
			super.disconnect();
		}
		return status;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 1351; i < 1370; i++) {
			int status = new ModbusService().getStatus(i, i+10000);

			if (status == ModbusService.ERROR) {
				System.out.println("访问出错");
			}
			if (status == ModbusService.LOCKED) {
				System.out.println("传感器已锁定");
			}
			if (status == ModbusService.EMPTY) {
				System.out.println("房间无人");
			}
			if (status == ModbusService.FULL) {
				System.out.println("房间有人");
			}
		}
	}
}
