package com.cosmosource.base.util;

/**
 * 通讯机信息
 * 
 * @author ptero
 * 
 */
public class CommObject {

	/**
	 * 服务器序号
	 */
	private String index;

	/**
	 * 通讯机IP
	 */
	private String IP;

	/**
	 * 通讯机端口
	 */
	private int port;

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the iP
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * @param iP
	 *            the iP to set
	 */
	public void setIP(String iP) {
		IP = iP;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
