package com.cosmosource.base.util;

import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author PTERO 新IO链路检测线程
 */
public class NewCheckLink implements Runnable {

	/**
	 * 日志Log4j
	 */
	private static final Log log = LogFactory.getLog(NewCheckLink.class);

	/**
	 * 链路检测时间间隔
	 */
	private long checkLinkTime;

	/**
	 * @param checkLinkTime
	 *            链路检测时间间隔
	 */
	public NewCheckLink(long checkLinkTime) {
		this.checkLinkTime = checkLinkTime;
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
				Thread.sleep(checkLinkTime);
				Set<Entry<String, Client>> clientSet = SocketPool.commSocketThreadMap.entrySet();
				Iterator<Entry<String, Client>> clientIterator = clientSet.iterator();
				String key = null;
				Client client = null;
				while (clientIterator.hasNext()) {
					Entry<String, Client> clientEntry = clientIterator.next();
					key = clientEntry.getKey();
					client = clientEntry.getValue();
					if (client != null && client.isAliveFlag()) {
						log.info("通讯序号服务器序号: " + key + " 线路畅通");
					} else {
						// 断链重新建链
						Client otherNewClient = new Client(
								client.getCommObject());
						Thread tmpNewClientThread = new Thread(otherNewClient,
								client.getCommObject().getIndex() + "_COMM_RECEIVE_THREAD");
						tmpNewClientThread.start();
						SocketPool.commSocketThreadMap.put(key, otherNewClient);
					}
				}
			} catch (Exception e) {
//				log.error("CheckLinkThread is Quit!");
//				setAliveFlag(Constants.FALSE_FLAG);
//				return;
				log.error("通讯维护线程出错", e);
			}
		}
	}
}
