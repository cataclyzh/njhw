package com.cosmosource.app.transfer.serviceimpl;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * 本线程设置了一个超时时间 该线程开始运行后，经过指定超时时间， 该线程会抛出一个未检查异常通知调用该线程的程序超时
 * 在超时结束前可以调用该类的cancel方法取消计时
 * 
 * @author solonote
 */
public class TimeoutThread extends Thread {
	/**
	 * 计时器超时时间
	 */
	private long timeout;
	/**
	 * 计时是否被取消
	 */
	private boolean isCanceled = false;
	
	private DataInputStream disInputStream;
	

	/**
	 * 构造器
	 * 
	 * @param timeout
	 *            指定超时的时间
	 */
	public TimeoutThread(long timeout,DataInputStream disInputStream) {
		super();
		this.timeout = timeout;
		this.disInputStream = disInputStream;
		// 设置本线程为守护线程
		this.setDaemon(true);
	}

	/**
	 * 取消计时
	 */
	public synchronized void cancel() {
		isCanceled = true;
	}

	/**
	 * 启动超时计时器
	 */
	public void run() {
		try {
			Thread.sleep(timeout);
			if (!isCanceled)
				disInputStream.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
