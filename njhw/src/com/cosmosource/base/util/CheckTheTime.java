package com.cosmosource.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author PTERO 同步门锁时间线程
 */
public class CheckTheTime implements Runnable {

	/**
	 * 日志Log4j
	 */
	private static final Log log = LogFactory.getLog(CheckTheTime.class);

	/**
	 * 同步门锁时间间隔
	 */
	private long checkTimeTime;
	
	private static final String EIGHTAUTHORITY = "347A6B3EFE86DBB8";//八字节授权码

	/**
	 * @param checkLinkTime
	 *            同步门锁时间间隔
	 */
	public CheckTheTime(long checkTimeTime) {
		this.checkTimeTime = checkTimeTime;
	}

	/**
	 * 线程活动标志
	 */
	private boolean aliveFlag;

	/**
	 * @return 线程活动标志
	 */
	public synchronized boolean isAliveFlag() {
		return aliveFlag;
	}

	/**
	 * @param aliveFlag
	 *            线程活动标志
	 */
	public synchronized void setAliveFlag(boolean aliveFlag) {
		this.aliveFlag = aliveFlag;
	}

	/**
	 * 线程运行体
	 */
	public void run() {
		setAliveFlag(Constants.TRUE_FLAG);
		while (isAliveFlag()) {
			try {
				Thread.sleep(checkTimeTime);
				Set<Entry<String, Client>> clientSet = SocketPool.commSocketThreadMap
						.entrySet();
				Iterator<Entry<String, Client>> clientIterator = clientSet
						.iterator();
				Client client = null;
				while (clientIterator.hasNext()) {
					Entry<String, Client> clientEntry = clientIterator.next();
					client = clientEntry.getValue();
					if (client != null && client.isAliveFlag()) {
						StringBuffer sb = new StringBuffer();
						String date  = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
						String orders = sb.append(Constants.DBMAP.get("WS_DOORLOCK_CHECKTIME")).append(EIGHTAUTHORITY).append(date).toString();
						orders = orders + getXorVerificationCode(orders);
						
						LockCommandData data = new LockCommandData(false, sb.toString(), 0L);
						client.addMsgToQueue(data);
					} 
				}
			} catch (InterruptedException e) {
				log.error("CheckTimeThread is Quit!");
				setAliveFlag(Constants.FALSE_FLAG);
				return;
			}
		}
	}
	
	/**
	* @Description：生成异或验证码
	* @Author：hp
	* @Date：2013-3-19
	* @param xorText
	* @return
	**/
	public static String getXorVerificationCode(String xorText){
		char[] b = xorText.toCharArray();
		int t = 0;
		int a = 0;
		int c = 0;
		String hexadecimal = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F";
		for (int i = 0; i < b.length; i++) {
			if(StringUtils.contains(hexadecimal, b[i])){
				if (i % 2 == 0) {
					t = Integer.parseInt(String.valueOf(b[i]), 16);
					a^=t;
				}else{
					t = Integer.parseInt(String.valueOf(b[i]), 16);
					c^=t;
				}
			}
		}
		String result = Integer.toHexString(a)+Integer.toHexString(c);
		return result.toUpperCase();
	}
}
