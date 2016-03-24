package com.cosmosource.base.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.common.service.CommonManager;
import com.cosmosource.app.entity.NjhwDoorcontrolExp;
import com.cosmosource.app.personnel.service.OrgMgrManager;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.app.transfer.serviceimpl.LogControlRecord;
import com.cosmosource.base.service.SpringContextHolder;

/**
 * @author PTERO 通讯线程
 */
public class Client implements Runnable {
	
	/**
	 * 客户端Socket
	 */
	private Socket socket;
	
	/**
	 * 输入流
	 */
	private InputStream in;

	/**
	 * 输出流
	 */
	private OutputStream out;
	
	/**
	 * 该通信机下所有门锁信息
	 */
	private List<Map> doorControlSetList = new ArrayList<Map>();

	/**
	 * 通信机返回的字节数
	 */
	private int readlength;

	/**
	 * 当前需要查询刷卡记录的门锁计数器
	 */
	private int count;
	
	/**
	 * 空闲时间
	 */
	private Long spareTime;
	
	/**
	 * 心跳连续无返回次数
	 */
	private int checkTimeoutTimes;

	/**
	 * @param CommObject
	 *            commObject
	 */
	public Client(CommObject commObject) {
		this.commObject = commObject;
	}

	/**
	 * @return 线程活动标志
	 */
	public boolean isAliveFlag() {
		return aliveFlag;
	}

	/**
	 * @param aliveFlag
	 *            线程活动标志
	 */
	public void setAliveFlag(boolean aliveFlag) {
		this.aliveFlag = aliveFlag;
	}
	
	private ArrayBlockingQueue<LockCommandData> incomingQueue = new ArrayBlockingQueue<LockCommandData>(Constants.MAX_SEND_LIST_LENGTH, true);
	
	private ArrayBlockingQueue<LockCommandData> workQueue = new ArrayBlockingQueue<LockCommandData>(Constants.MAX_SEND_LIST_LENGTH, true);
	
	private DelayQueue<LockCommandData> delayQueue = new DelayQueue<LockCommandData>();  

	public void run() {
		// 设置线程标志位
		setAliveFlag(Constants.TRUE_FLAG);

		try {
			socket = new Socket(commObject.getIP(), commObject.getPort());
			in = socket.getInputStream();
			out = socket.getOutputStream();
			
			DoorControlToAppService doorControlToAppService = SpringContextHolder.getBean("doorControlToAppService");
			doorControlSetList = doorControlToAppService.queryDoorEntityByComm(commObject.getIndex());
			log.info(commObject.getIndex() + "号通信机正常启动！");
		} catch (UnknownHostException e1) {
			closeSocket();
			e1.printStackTrace();
			log.error(e1);
		} catch (IOException e1) {
			closeSocket();
			e1.printStackTrace();
			log.error(e1);
		}
		try {
			Thread.sleep(2000);
			
			// count值初始化
			count = 0;
			// 空闲时间初始化
			spareTime = 0L;
			// 心跳连续无返回次数初始化
			checkTimeoutTimes = 0;
			// 发送消息
			while (isAliveFlag()) {
				synchronized(incomingQueue) {
					if (incomingQueue.poll() == null) {
						incomingQueue.wait(1000);
					}
					handleSend();
				}
			}
		} catch (Exception e) {
			closeSocket();
			e.printStackTrace();
			log.error(e);
		}
	}
	
	private void closeSocket() {
		try {
			if (socket != null) {
				socket.close();
			}
			setAliveFlag(Constants.FALSE_FLAG);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void handleSend() {
		LockCommandData dataDelay = delayQueue.poll();

		if (dataDelay == null) {
			LockCommandData data = workQueue.poll();
			if (data == null && System.nanoTime() - spareTime > TimeUnit.NANOSECONDS
					.convert(2000, TimeUnit.MILLISECONDS)) {
				spareTime = System.nanoTime();
				handleQueryMsg();
			} else if (data != null) {
				spareTime = System.nanoTime();
				if (data.getTimeout() == 0) {
					if (data.isReadBlock()) {
						handleBlockMsg(data, true);
					} else {
						handleBlockMsg(data, false);
					}
				} else {
					delayQueue.offer(data);
				}
			}
		} else {
			spareTime = System.nanoTime();
			handleBlockMsg(dataDelay, true);
		}
	}

	/**
	 * 空闲时查询门锁刷卡记录
	 * @param data
	 */
	private void handleQueryMsg() {
		DoorControlToAppService doorControlToAppService = SpringContextHolder.getBean("doorControlToAppService");
		Map doorInfo = doorControlSetList.get(count);
		//Map doorInfo = doorControlSetList.get(10);
		String msg = doorControlToAppService.convertOrderInfo(String.valueOf(doorInfo.get("NODEID")), "3C",
				String.valueOf(doorInfo.get("ADRSSTORE")), String.valueOf(doorInfo.get("ADRSCOMM")));
		StringBuffer sb = new StringBuffer();
		sb.append(msg).append(getXorVerificationCode(msg));
		msg = sb.toString();
		LockCommandData commandData = new LockCommandData(true, msg, 0L);
		handleBlockMsg(commandData, true);
		if (count == doorControlSetList.size() - 1) {
			count = 0;
		} else {
			count++;
		}
	}

	private void handleBlockMsg(LockCommandData data, boolean block) {
		setRoomInfo(data);
		try {
			log.info("************向门锁发送消息开始**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			checkAddOrder(data);

			if (in.available() > 0) {
				byte[] queryBytes = null;
				// 处理响应
				queryBytes = new byte[Constants.READ_BUFFER_SIZE];
				in.read(queryBytes);
			}
				
			out.write(hexStringToByte(data.getMsg()));
			log.info("消息内容：       " + data.getMsg());
			if (block) {
				handleBlock(data);
			}
		} catch (IOException e) {
			log.error(e);
			closeSocket();
			int bak = 0;
			if (!"32".equals(getOrder(data.getMsg()))) {
				bak = 1;
			}
			log.error(commObject.getIndex() + " COMM"
					+ " Thread  Quit!");
			CommonManager commonManager = SpringContextHolder.getBean("commonManager");
			if ("3602".equals(getOrder(data.getMsg()))) {
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
				commonManager.updateDoor(map, "2", null);
				String tsCard = data.getCardId();
				recordLogInfo(data, "添加授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
			} else if ("36FF".equals(getOrder(data.getMsg()))) {
				OrgMgrManager orgMgrManager = SpringContextHolder.getBean("orgPerMgrManager");
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
				
				if (data.isForce()) {
					commonManager.deleteDoor(map);
					if (data.isDeny()) {
						orgMgrManager.updateDenyForObjPermMap(data.getUserId(),
								data.getNodeId(), 0L);
					} else {
						orgMgrManager.deleteObjPermMapForDoor(data.getUserId(),
								data.getNodeId());
					}

					String tsCard = data.getCardId();
					recordLogInfo(data, "删除授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
				} else {
					commonManager.updateDoor(map, "3", null);
					String tsCard = data.getCardId();
					recordLogInfo(data, "添加授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
				}
			} else if ("3A".equals(getOrder(data.getMsg()))) {
				CSVUtil csvUtil = SpringContextHolder.getBean("csvUtil");
				String adrsComm = Integer.valueOf(data.getMsg().substring(4, 6).toUpperCase(), 16) + "";
				String fileName = "checkDoorAuth";
				csvUtil.WriteOneLine(new String[]{adrsComm, "NG", "", "", "", "", "", ""}, fileName, true);				
			}
		} catch (Exception e) {
			log.error(e);
			closeSocket();
			int bak = 0;
			if (!"32".equals(getOrder(data.getMsg()))) {
				bak = 3;
			}
			log.error(commObject.getIndex() + " COMM"
					+ " Thread  Quit!");
			CommonManager commonManager = SpringContextHolder.getBean("commonManager");
			if ("3602".equals(getOrder(data.getMsg()))) {
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
				commonManager.updateDoor(map, "1", null);
				String tsCard = data.getCardId();
				recordLogInfo(data, "添加授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
			} else if ("36FF".equals(getOrder(data.getMsg()))) {
				OrgMgrManager orgMgrManager = SpringContextHolder.getBean("orgPerMgrManager");
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
				
				if (data.isForce()) {
					commonManager.deleteDoor(map);
					if (data.isDeny()) {
						orgMgrManager.updateDenyForObjPermMap(data.getUserId(),
								data.getNodeId(), 0L);
					} else {
						orgMgrManager.deleteObjPermMapForDoor(data.getUserId(),
								data.getNodeId());
					}

					String tsCard = data.getCardId();
					recordLogInfo(data, "删除授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
				} else {
					commonManager.updateDoor(map, "4", null);
					String tsCard = data.getCardId();
					recordLogInfo(data, "添加授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
				}
			} else if ("3A".equals(getOrder(data.getMsg()))) {
				CSVUtil csvUtil = SpringContextHolder.getBean("csvUtil");
				String adrsComm = Integer.valueOf(data.getMsg().substring(4, 6).toUpperCase(), 16) + "";
				String fileName = "checkDoorAuth";
				csvUtil.WriteOneLine(new String[]{adrsComm, "NG", "", "", "", "", "", ""}, fileName, true);
			}
		}
	}

	private void handleBlock(LockCommandData data) throws IOException, Exception {
		log.info("************从门锁中读出结果开始**********"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()));

		byte[] readBytes = null;
		readlength = -1;

		Long startTime = System.nanoTime();
		while (true) {
			if (in.available() > 0) {
				// 处理响应
				readBytes = new byte[Constants.READ_BUFFER_SIZE];
				readlength = in.read(readBytes);
				log.info("Read Response Lenght = " + readlength);

				processDecode(readBytes, data);
				break;
			} else if (System.nanoTime() - startTime > TimeUnit.NANOSECONDS
					.convert(1000, TimeUnit.MILLISECONDS)) {
				readBytes = hexStringToByte("FF");

				processDecode(readBytes, data);
				break;
			}
		}
	}

	/**
	 * @param message
	 *            消息
	 */
	public void addMsgToQueue(LockCommandData data) {
		synchronized(incomingQueue) {
			if (incomingQueue.offer(data)) {
				incomingQueue.notify();
				if (workQueue.offer(data)) {
				} else {
					// 发送队列满
					log.error("Attention Please, SendMsgList is out of control!");
					try {
						Thread.sleep(Constants.SEND_THREAD_WAIT_TIME);
					} catch (InterruptedException e) {
						log.error(e);
					}
				}
			} else {
				incomingQueue.notify();
				// 发送队列满
				log.error("Attention Please, SendMsgList is out of control!");
				try {
					Thread.sleep(Constants.SEND_THREAD_WAIT_TIME);
				} catch (InterruptedException e) {
					log.error(e);
				}
			}
		}
	}
	

	/**
	 * 对接收到的通讯服务器发送的包进行解析
	 * @param data 
	 * @throws Exception 
	 */
	private void processDecode(byte[] readBytes, LockCommandData data) throws IOException, Exception {
		OrgMgrManager orgMgrManager = SpringContextHolder.getBean("orgPerMgrManager");
		CommonManager commonManager = SpringContextHolder.getBean("commonManager");
		
		
		log.info("Receive Bytes from Comm server = " + readBytes
				+ " length = " + readBytes.length);
		
		String errCode = Integer.toHexString(readBytes[0] & 0xFF);
		String order = getOrder(data.getMsg());
		
		if ("30".equals(errCode)) {
			if ("38".equals(order)) {
				recordLogInfo(data, "开门：成功", 0, true);
			} else if ("39".equals(order)) {
				recordLogInfo(data, "关门：成功", 0, true);
			} else if ("3602".equals(order)) {
				int adrsNum = Integer.parseInt(data.getMsg().substring(38, 40), 16);
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
				commonManager.updateDoor(map, "", adrsNum + "");
				
				if (data.isDeny()) {
					orgMgrManager.updateDenyForObjPermMap(data.getUserId(), data.getNodeId(), 1L);
				} else {
					orgMgrManager.addObjPermMapForDoor(data.getUserId(), data.getNodeId());
				}
				recordLogInfo(data, "添加授权：成功； 房间号：" + data.getRoomName() + "；市民卡号："
						+ data.getCardId(), 0, true);
			} else if ("36FF".equals(order)) {
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
				commonManager.deleteDoor(map);
				
				if (data.isDeny()) {
					orgMgrManager.updateDenyForObjPermMap(data.getUserId(), data.getNodeId(), 0L);
				} else {
					orgMgrManager.deleteObjPermMapForDoor(data.getUserId(), data.getNodeId());
				}
				recordLogInfo(data, "删除授权：成功； 房间号：" + data.getRoomName() + "；市民卡号：" + data.getCardId(), 0, true);
			}
			
		} else if ("3C".equals(errCode.toUpperCase())) {
			checkTimeoutTimes = 0;
			Long startTime = System.nanoTime();
			while (readlength != 18) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (in.available() > 0) {
					byte[] queryBytes = null;
					int querylength = -1;
					// 处理响应
					queryBytes = new byte[Constants.READ_BUFFER_SIZE];
					querylength = in.read(queryBytes);

					byte[] copy = readBytes;
					readBytes = new byte[readlength + querylength];
					System.arraycopy(copy,0,readBytes,0,readlength);
					System.arraycopy(queryBytes,0,readBytes,readlength,querylength);
					readlength += querylength;
					log.info("Read Response Lenght = " + querylength);
				} else if (System.nanoTime() - startTime > TimeUnit.NANOSECONDS
						.convert(1000, TimeUnit.MILLISECONDS)) {
					return;
				}
			}
			
			DoorControlToAppService doorControlToAppService = SpringContextHolder.getBean("doorControlToAppService");
			
			StringBuffer response = new StringBuffer();
			for (int i = 0; i < 18; i++) {
				String result = Integer.toHexString(readBytes[i] & 0xFF);
				if (result.length() == 1) {
					response.append("0" + Integer.toHexString(readBytes[i] & 0xFF));
				} else {
					response.append(Integer.toHexString(readBytes[i] & 0xFF));
				}
			}
			
			log.info("Read Response Info = " + response.toString());
			doorControlToAppService.saveDoorStatus(response.toString(), data.getOperationPerson());
		} else if ("3A".equals(errCode.toUpperCase())) {
			Long startTime = System.nanoTime();
			while (readlength != 13) {
				if (in.available() > 0) {
					byte[] queryBytes = null;
					int querylength = -1;
					// 处理响应
					queryBytes = new byte[Constants.READ_BUFFER_SIZE];
					querylength = in.read(queryBytes);

					byte[] copy = readBytes;
					readBytes = new byte[readlength + querylength];
					System.arraycopy(copy,0,readBytes,0,readlength);
					System.arraycopy(queryBytes,0,readBytes,readlength,querylength);
					readlength += querylength;
					log.info("Read Response Lenght = " + querylength);
				} else if (System.nanoTime() - startTime > TimeUnit.NANOSECONDS
						.convert(1000, TimeUnit.MILLISECONDS)) {
					CSVUtil csvUtil = SpringContextHolder.getBean("csvUtil");
					String adrsComm = Integer.valueOf(data.getMsg().substring(4, 6).toUpperCase(), 16) + "";
					String fileName = "checkDoorAuth";
					csvUtil.WriteOneLine(new String[]{adrsComm, "OK", data.getRoomName(), "NG", "", "", "", ""}, fileName, true);
				}
			}
			
			StringBuffer response = new StringBuffer();
			for (int i = 0; i < 13; i++) {
				String result = Integer.toHexString(readBytes[i] & 0xFF);
				if (result.length() == 1) {
					response.append("0" + Integer.toHexString(readBytes[i] & 0xFF));
				} else {
					response.append(Integer.toHexString(readBytes[i] & 0xFF));
				}
			}
			
			String adrsNum = Integer.valueOf(data.getMsg().substring(22, 24).toUpperCase(), 16) + "";
			String adrsComm = Integer.valueOf(data.getMsg().substring(4, 6).toUpperCase(), 16) + "";
			Map map = new HashMap();
			map.put("ADRSNUM", adrsNum);
			map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
			List<NjhwDoorcontrolExp> expList = commonManager.searchDoorCard(map);
			
			String cardId = "";
			if (!"FFFFFFFFFFFFFFFF".equals(response.substring(6, 22).toUpperCase())) {
				cardId = response.substring(6, 22);
			}
			
			String cardIdFromDB = "";
			
			if (expList != null && expList.size() > 0) {
				for (int i = 0; i < expList.size(); i++) {
					if (i == 0) {

						CSVUtil csvUtil = SpringContextHolder.getBean("csvUtil");
						if (expList.get(0).getCardId() != null) {
							cardIdFromDB = expList.get(0).getCardId().toString().trim();
						}
						if (!cardId.equals(cardIdFromDB)) {
							String fileName = "checkDoorAuth";
							csvUtil.WriteOneLine(new String[]{adrsComm, "OK", data.getRoomName(), "OK", adrsNum, cardId, cardIdFromDB, "NG"}, fileName, true);
						}
					} else {
						commonManager.deleteDoor(expList.get(i).getId());
					}
				}
			}
			
		} else if ("FF".equals(errCode.toUpperCase())) {
			if ("3C".equals(order)) {
				checkTimeoutTimes++;
			}
			
			if (checkTimeoutTimes > 10) {
				closeSocket();
			}
			
			int bak = 2;
			log.info(commObject.getIndex() + " COMM"
					+ " 门锁连接超时");
			log.info("命令:" + order);
			
			if ("38".equals(order)) {
				recordLogInfo(data, "开门：失败", bak, false);
			} else if ("39".equals(order)) {
				recordLogInfo(data, "关门：失败", bak, false);
			} else if ("36FF".equals(order)) {
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));

				commonManager.deleteDoor(map);
				if (data.isDeny()) {
					orgMgrManager.updateDenyForObjPermMap(data.getUserId(),
							data.getNodeId(), 0L);
				} else {
					orgMgrManager.deleteObjPermMapForDoor(data.getUserId(),
							data.getNodeId());
				}

				String tsCard = data.getCardId();
				recordLogInfo(data, "删除授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
			} else if ("3602".equals(order)) {
				Map map = new HashMap();
				map.put("CARD", data.getCardId());
				map.put("NODEID", NumberUtil.strToLong(data.getNodeId()));
				commonManager.updateDoor(map, "2", null);
				String tsCard = data.getCardId();
				recordLogInfo(data, "添加授权：失败； 房间号：" + data.getRoomName() + "；市民卡号：" + tsCard, bak, false);
			} else if ("3A".equals(order)) {
				CSVUtil csvUtil = SpringContextHolder.getBean("csvUtil");
				String adrsComm = Integer.valueOf(data.getMsg().substring(4, 6).toUpperCase(), 16) + "";
				String fileName = "checkDoorAuth";
				csvUtil.WriteOneLine(new String[]{adrsComm, "OK", data.getRoomName(), "NG", "", "", "", ""}, fileName, true);
			}
		} else {
			throw new Exception("返回结果不正确！");
		}
		
		log.info("************从门锁中读出结果："+errCode+" 结束**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		log.info("Error Code = " + errCode);
	}

	/**
	 * 添加授权需要重新查找可用位置
	 * @param data
	 */
	public void checkAddOrder(LockCommandData data) {
		String msg = data.getMsg();
		if ("3602".equals(getOrder(msg))) {
			Map map = new HashMap();
			map.put("CARD", data.getCardId());
			map.put("NODEID", data.getNodeId());
			CommonManager commonManager = SpringContextHolder.getBean("commonManager");
			Map adrsnuMap = commonManager.findAdrsNum(map);
			String newMsg = msg.substring(0, 38) + convertHex(adrsnuMap.get("ADRSNUM").toString()) + "02";
			data.setMsg(newMsg + getXorVerificationCode(newMsg));
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
	
	/**
	* @Description：把十进制字符串转换成16进制字符串,传递命令需要
	* @Author：hp
	* @Date：2013-5-7
	* @param addr
	* @return
	**/
	public static String convertHex(String order) {
		if(order == null || order == ""){
			log.info("参数不能为空!");
			return null;
		}
//		String od = Integer.toHexString(Integer.parseInt(order));
		String od = Long.toHexString(Long.parseLong(order));
		String result = "";
		if (od.length() == 1) {
			result = "0" + od;
		} else {
			result = od;
		}
		return result.toUpperCase();
	}
	
	/**
	 * 查询数据库中ROOM_ID以及门锁的NODE_ID的值
	 * @param data
	 */
	public void setRoomInfo(LockCommandData data) {
		String msg = data.getMsg();
		if (!"32".equals(getOrder(msg)) && !"AA".equals(getOrder(msg))) {
			String adrsStore = Integer.valueOf(msg.substring(2, 4).toUpperCase(), 16) + "";
			String adrsComm = Integer.valueOf(msg.substring(4, 6).toUpperCase(), 16) + "";
			CommonManager commonManager1 = SpringContextHolder.getBean("commonManager");
			Map roominfo = commonManager1.getRoomInfo(adrsStore, adrsComm);
			data.setNodeId((String) roominfo.get("nodeId"));
			data.setSiteId((String) roominfo.get("roomId"));
			String lockName = (String) roominfo.get("name");
			data.setRoomName(lockName.substring(0, lockName.length()-3));
		}
	}
	
	public String getOrder(String msg) {
		String order = msg.substring(0, 2).toUpperCase();
		
		if ("36".equals(order)) {
			order += msg.substring(40, 42);
		}
		return order;
	}
	
	public void recordLogInfo(LockCommandData data, String infoDetail, int bak, boolean isSuccess) {
		LogControlRecord logControlRecord = SpringContextHolder.getBean("logControlRecord");
		logControlRecord.recordLog(data.getOperationPerson(), data.getUserId(), data.getSiteId(),
				infoDetail, getOrder(data.getMsg()), data.getMsgId(), bak, isSuccess);
	}
	
	/**
	 * @return the commObject
	 */
	public CommObject getCommObject() {
		return commObject;
	}

	/**
	 * @param commObject
	 *            the commObject to set
	 */
	public void setCommObject(CommObject commObject) {
		this.commObject = commObject;
	}


	public void setDelayQueue(DelayQueue<LockCommandData> delayQueue) {
		this.delayQueue = delayQueue;
	}

	public DelayQueue<LockCommandData> getDelayQueue() {
		return delayQueue;
	}

	/**
	 * 日志Log4j
	 */
	private static final Log log = LogFactory.getLog(Client.class);

	/**
	 * 通讯服务器信息
	 */
	private CommObject commObject;


	/**
	 * 线程活动标志
	 */
	private boolean aliveFlag = true;
	
	private static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] hexChars = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
					.indexOf(hexChars[pos + 1]));
		}
		return result;
	}

	private static final String HEX_NUMS_STR = "0123456789ABCDEF";

}
