package com.cosmosource.base.util;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class LockCommandData implements Delayed {
	
	public LockCommandData(boolean readBlock, String msg, Long delay) {
		this.setReadBlock(readBlock);
		this.setMsg(msg);
		this.setDelayTime(delay + System.nanoTime());
		this.setTimeout(delay);
	}

	private Long timeout;
	
	private String msg;
	
	private Long delayTime;
	
	private boolean readBlock;

	private String cardId;
	
	private String operationPerson;
	
	private String siteId;
	
	private String nodeId;
	
	private String msgId;
	
	private String userId;
	
	private String roomName;
	
	private boolean deny;
	
	private boolean force;

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(delayTime-System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	@Override
	public int compareTo(Delayed other) {
		if (other == this) // compare zero ONLY if same object
			return 0;

		LockCommandData t = (LockCommandData) other;
		long d = (getDelay(TimeUnit.NANOSECONDS) - t
				.getDelay(TimeUnit.NANOSECONDS));
		return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
	}

	public void setReadBlock(boolean readBlock) {
		this.readBlock = readBlock;
	}

	public boolean isReadBlock() {
		return readBlock;
	}


	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}

	public Long getDelayTime() {
		return delayTime;
	}


//	public void setRtnFlag(String rtnFlag) {
//		this.rtnFlag = rtnFlag;
//	}
//
//	public String getRtnFlag() {
//		return rtnFlag;
//	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setOperationPerson(String operationPerson) {
		this.operationPerson = operationPerson;
	}

	public String getOperationPerson() {
		return operationPerson;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setDeny(boolean deny) {
		this.deny = deny;
	}

	public boolean isDeny() {
		return deny;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public boolean isForce() {
		return force;
	}

}
