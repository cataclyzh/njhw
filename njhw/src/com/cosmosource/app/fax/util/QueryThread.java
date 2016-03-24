package com.cosmosource.app.fax.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryThread implements Runnable{
	
	/**
	 * 日志Log4j
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	@Override
	public void run() {
		logger.info("QueryThread run() method invoked!!");
	}

}
