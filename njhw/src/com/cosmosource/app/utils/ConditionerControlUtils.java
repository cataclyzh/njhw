package com.cosmosource.app.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import obix.Obj;
import obix.Real;
import obix.Uri;
import obix.net.ObixSession;
import obix.net.SessionWatch;

import com.cosmosource.app.integrateservice.model.LightObixModel;


public class ConditionerControlUtils {
	
	private static final  Log log = LogFactory.getLog(ConditionerControlUtils.class);
	/**
	 * 用来控制空调温度
	 * @param lm
	 * @param flag
	 */
	public static void controlConditionerPoint(LightObixModel lm,Double temperature){
		String uri = lm.getUri();
		String username = lm.getUsername();
		String password = lm.getPassword();
		String operUri = lm.getOperUri();
		Uri u = new Uri(uri);
		ObixSession os = new ObixSession(u, username, password);
		Uri u3 = new Uri(operUri);
		Obj obj = new Real(temperature);
		try {
			Obj result = os.invoke(u3, obj);
		} catch (Exception e) {
			log.info("控制空调："+e);
		}
	}
	
	
	/**
	 * 取得空调对应的房间当前温度
	 * @param lm
	 * @return
	 */
	public static  String getConditionerTemperature(LightObixModel lm){
		String uri = lm.getUri();
		String username = lm.getUsername();
		String password = lm.getPassword();
		String operUri = lm.getOperUri();
		String temperature = "0";
		Uri u = new Uri(uri);
		ObixSession os = new ObixSession(u, username, password);
		//"http://10.250.250.168/obix/config/Drivers/NxModbusTcpNetwork/ModbusTcpDevice/points/BooleanWritable1360/out/"
		//watch对象
		Uri uWatch = new Uri(uri+"/obix/");
		Uri u3 = new Uri(operUri);
		try {
			ObixSession osWatch = new ObixSession(uWatch, username, password);
			osWatch.open();
			SessionWatch sw =  osWatch.makeWatch("watchConditionerTemperature", 120000);
			sw.add(u3);
			sw.pollRefresh();
			Real objs1 = (Real)os.read(u3);
			temperature = objs1.toString();
		} catch (Exception e) {
			log.info("取空调的温度："+e);
		}
        return	temperature;
	}
	
	/**
	 * 取得空调当前运行的状态
	 * @param lm
	 * @return
	 */
	public static  float  getConditionerStatus(LightObixModel lm){
		String uri = lm.getUri();
		String username = lm.getUsername();
		String password = lm.getPassword();
		String operUri = lm.getOperUri();
		float status = 0;
		Uri u = new Uri(uri);
		ObixSession os = new ObixSession(u, username, password);
		//watch对象
		Uri uWatch = new Uri(uri+"/obix/");
		Uri u3 = new Uri(operUri);
		try {
			ObixSession osWatch = new ObixSession(uWatch, username, password);
			osWatch.open();
			SessionWatch sw =  osWatch.makeWatch("watchConditionerStatus", 120000);
			sw.add(u3);
			sw.pollRefresh();
			Real objs1 = (Real)os.read(u3);
			status = Float.parseFloat(objs1.toString());
		} catch (Exception e) {
			log.info("取空调的状态："+e);
		}
        return	status;
	}
}
