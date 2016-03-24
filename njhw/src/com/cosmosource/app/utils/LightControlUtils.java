package com.cosmosource.app.utils;

import obix.Bool;
import obix.Obj;
import obix.Uri;
import obix.net.ObixSession;

import com.cosmosource.app.integrateservice.model.LightObixModel;


public class LightControlUtils {
	
	/**
	 * 用来控制开关灯
	 * @param lm
	 * @param flag
	 */
	public static void controlLight(LightObixModel lm,boolean flag){
		String uri = lm.getUri();
		String username = lm.getUsername();
		String password = lm.getPassword();
		String operUri = lm.getOperUri();
		Uri u = new Uri(uri);
		ObixSession os = new ObixSession(u, username, password);
		//"http://10.250.250.168/obix/config/Drivers/NxModbusTcpNetwork/ModbusTcpDevice/points/BooleanWritable1360/set/
		Uri u3 = new Uri(operUri);
		Obj obj = new Bool(flag);
		try {
			Obj result = os.invoke(u3, obj);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		flag = !flag;
	}
	
	
	/**
	 * 取得灯的状态，是否开着
	 * @param lm
	 * @return
	 */
	public static  boolean getLightStatus(LightObixModel lm){
		String uri = lm.getUri();
		String username = lm.getUsername();
		String password = lm.getPassword();
		String operUri = lm.getOperUri();
		boolean status = false;
		Uri u = new Uri(uri);
		ObixSession os = new ObixSession(u, username, password);
		//"http://10.250.250.168/obix/config/Drivers/NxModbusTcpNetwork/ModbusTcpDevice/points/BooleanWritable1360/out/"
		Uri u3 = new Uri(operUri);
		try {
			Bool objs = (Bool)os.read(u3);
			status = objs.getBool();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return	status;
	}
}
