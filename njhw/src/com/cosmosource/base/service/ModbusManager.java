/**
 * 
 */
package com.cosmosource.base.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.util.BitVector;

/**
 * @author Administrator
 * 
 */
public class ModbusManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private ModbusTCPMaster tcpMaster;

	public boolean connect(String addr, int port) {
		try {
			logger.info("Connecting server " + addr + ":" + port);
			tcpMaster = new ModbusTCPMaster(addr, port);
			tcpMaster.connect();
			logger.info("Success");
		} catch (Exception e) {
			logger.error("Problem connecting server: " + e);
			return false;
		}
		return true;
	}

	public boolean disconnect() {
		try {
			logger.info("Closing server");
			tcpMaster.disconnect();
			logger.info("Success");
		} catch (Exception e) {
			logger.error("Problem closing server: " + e);
			return false;
		}
		return true;
	}

	public BitVector readCoils(int ref, int count) {
		BitVector bitVector = null;
		try {
			bitVector = tcpMaster.readCoils(ref, count);
			for (int i = 0; i < bitVector.size(); i++) {
				logger.info("Address " + (ref++) + ":"
						+ String.valueOf(bitVector.getBit(i)));
			}
		} catch (Exception e) {
			logger.error("Problem reading coils: " + e);
		}
		return bitVector;
	}

	public BitVector readInputDiscretes(int ref, int count) {
		BitVector bitVector = null;
		try {
			bitVector = tcpMaster.readInputDiscretes(ref, count);
			for (int i = 0; i < bitVector.size(); i++) {
				logger.info("Address " + (ref++) + ":"
						+ String.valueOf(bitVector.getBit(i)));
			}
		} catch (Exception e) {
			logger.error("Problem reading input discretes: " + e);
		}
		return bitVector;
	}

	protected String getProperties(String name) {
		InputStream in = getClass().getResourceAsStream(
				"../config/modbus.properties");
		Properties pro = new Properties();
		String value = null;
		try {
			pro.load(in);
			value = pro.getProperty(name).trim();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
