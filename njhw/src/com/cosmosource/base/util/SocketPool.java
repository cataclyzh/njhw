package com.cosmosource.base.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接池
 * 
 * @author ptero
 * 
 */
public final class SocketPool {
	/**
	 * 接收线程池
	 */
	public static ConcurrentHashMap<String, Client> commSocketThreadMap = new ConcurrentHashMap<String, Client>();
}
