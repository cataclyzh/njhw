package com.cosmosource.app.transfer.serviceimpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.common.service.CommonManager;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.HexAndByte;


/**
 * @ClassName:DoorControlToSystemService
 * @Description：和门锁系统交互的service
 * @Author：hp 
 * @Date:2013-3-23
 */
public class DoorControlToSystemService{
	
	private LogControlRecord logControlRecord;
	private CommonManager commonManager;
	
	private static final String EIGHTAUTHORITY = "347A6B3EFE86DBB8";//八字节授权码
	private static final String DELETELOCK = "FFFFFFFFFFFFFFFF";//删除锁中卡的指令
	private static final String FIRSTEXTCODE = "FF";//1字节备用
	private static final String FIRSTCARDTYPE = "02";//1字节卡类
	public static final String INIT = "init";//初始化数据权限
	public static final String DELETE = "delete";//删除数据权限
	public static final String QUERY = "query";//查询状态
	
	private static final Log log = LogFactory.getLog(DoorControlToSystemService.class);

	/**
	* @Description：初始化门锁信息
	* @Author：hp
	* @Date：2013-3-29
	* @param siteId
	* @param order
	* @param ip
	* @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	**/
	@SuppressWarnings("unchecked")
	public List<Map> initDoor(String ip,List<Map> datas,String type) throws NumberFormatException, UnknownHostException, IOException{
		if(ip.equals(null) || datas == null){
			log.info("ip地址、命令不能为空");
			return null;
		}
		Socket socket = null;
		DataInputStream dis =null;
		DataOutputStream dos =null;
		String newOrder = null;
		List<Map> orderList = new ArrayList<Map>();
		try{
			socket = new Socket(ip,Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
			log.info("***********连接已经成功*************"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			for (Map order : datas) {
				Map map = null;
				if(type.equals("init")){
					map = commonManager.findAdrsNum(order);
				}else{
					map = commonManager.findAdrsNumCancel(order);
				}
				if(map.get("NODEID") != null && map.get("ADRSSTORE") != null && map.get("ADRSCOMM") != null && map.get("CARD") != null && map.get("ADRSNUM") != null){
					newOrder = convertOrderInfo(map.get("NODEID").toString(), Constants.DBMAP.get("WS_DOORLOCK_INITDELAUTHORDER"), map.get("ADRSSTORE").toString(),map.get("ADRSCOMM").toString(),map.get("CARD").toString(),type,map.get("ADRSNUM").toString());
					try {
						dos.write(HexAndByte.hexStringToBytes(newOrder));
						dos.flush();
						log.info("************把权限命令写入到门锁系统中**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						//构造一个时间保护线程
						TimeoutThread time = new TimeoutThread(1000,dis);
						Thread.sleep(500);
						log.info("************从门锁中读出结果开始**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						time.start();//线程开始
						byte t = dis.readByte();
						time.cancel();//取消线程
						String br = Integer.toHexString(t & 0xFF);
						log.info("************从门锁中读出结果："+br+" 结束**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						if(br.equals("30")){
							//成功
							map.put("status", "0");
							if(type.equals(DoorControlToSystemService.INIT)){
								commonManager.saveDoor(map);
							}else{
								commonManager.deleteDoor(map);
							}
							log.info("命令为:"+newOrder+" 的操作成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+br);
						}else{
							//失败
							map.put("status", "1");
							log.info("命令为:"+newOrder+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+br);
						}
						log.info("***********门锁返回信息**************");
					} catch (UnknownHostException e) {
						map.put("status", "1");
						e.printStackTrace();
					} catch (IOException e) {
						map.put("status", "1");
						log.info("命令为:"+newOrder+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						//如果命令不正确或是门锁损坏等造成通信不通时，关闭连接和套接字连接，然后重新连接已确保其他命令的执行
						socket = new Socket(ip,Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
						dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
						dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					} catch (InterruptedException e) {
						map.put("status", "1");
						e.printStackTrace();
					} 
				}
				orderList.add(map);
			}
		}catch(ConnectException e){
			log.info("通讯机连接失败......"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			for (Map map2 : datas) {
				map2.put("status", "1");
				orderList.add(map2);
			}
		}
		finally
		{
			try{
				log.info("***************client close**************");
				IOUtils.closeQuietly(dis);
				IOUtils.closeQuietly(dos);
				if(null != socket){
					socket.close();
				}
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
			
		return orderList;
	}
	
	
	/**
	* @Description：校对时间
	* @Author：hp
	* @Date：2013-3-29
	* @param order
	* @param ip
	**/
	public boolean checkTime(String order,String ip){
		boolean flag = false;
		if(ip.equals(null)  || order.equals(null)){
			log.info("ip地址、命令不能为空");
			return flag;
		}
		Socket socket = null;
		DataOutputStream dos =null;
		try {
			socket = new Socket(ip,Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
			log.info("***********连接已经成功*************"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			dos = new DataOutputStream(socket.getOutputStream());
			dos.write(HexAndByte.hexStringToBytes(order));
			dos.flush();
			log.info("************把校对时间的命令写入到门锁系统中**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			flag =true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			log.info("***************client close**************");
			try {
				dos.close();
				socket.close();
			} catch (IOException e) {
				flag = false;
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	

	/**
	* @Description：控制门锁操作
	* @Author：hp
	* @Date：2013-3-26
	* @param siteId  设备ID
	* @param order   命令
	* @param loginId 登录人的Id
	* @param ip
	* @return String 返回成功与否    成功32H ---- true  失败为无 ---- false
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	**/
	public String controlDoor(final String siteId,String order,final String shutdown,final String loginId,final String ip) throws NumberFormatException, UnknownHostException, IOException{
		if(ip.equals(null) || order.equals(null)){
			log.info("ip地址、设备id、命令不能为空");
			return null;
		}
		
		Socket socket = null;
		try {
			socket = new Socket(ip,Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
		} catch (Exception e) {
			log.info("***********连接失败*************"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return "fail";
		}
		
		 log.info("***********连接已经成功*************"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		 DataInputStream dis = new DataInputStream(socket.getInputStream());
		 DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		
		// 执行开门动作
		String result = openDoor(siteId, order, loginId, ip, socket, dis, dos);
		
		// 开门成功
		if("true".equals(result))
		{   
			Timer  timer = new Timer();
		
			// 当开门成功6s后 自动关门
			timer.schedule(new TimerTask() {
				
					@Override
					public void run() {
						String flagString = "false";
						Socket socket = null;
						DataInputStream dis = null;
						DataOutputStream dos = null;
						try
						{
							socket = new Socket(ip,Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
							dis = new DataInputStream(socket.getInputStream());
							dos = new DataOutputStream(socket.getOutputStream());
							flagString =closeDoor(siteId, shutdown,loginId, ip, dis, dos, socket);
						}
						catch (UnknownHostException e)
						{
							log.error("第1次关门失败!!");
							e.printStackTrace();
						}
						catch (IOException e)
						{
							log.error("第1次关门失败!!");
							e.printStackTrace();
						}
						finally
						{    
							
							// 如果关门失败 则启动保护线程 继续执行关门动作
							if ("false".equals(flagString)||flagString.endsWith("false"))
							{
								TimeoutThread time = new TimeoutThread(2000,dis);
								
								for(int i=1;i<5;i++)
								{   
									try {
										Thread.sleep(400);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									if ("false".equals(flagString)||flagString.endsWith("false")) 
									{  
										try {
											socket = new Socket(ip,Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
											dis = new DataInputStream(socket.getInputStream());
											dos = new DataOutputStream(socket.getOutputStream());
											flagString = closeDoor(siteId, shutdown,loginId, ip, dis, dos, socket);
											if ("false".equals(flagString)||flagString.endsWith("false")) 
												log.error("第"+(i+1)+"次关门失败!!");
										} catch (NumberFormatException e) {
											flagString = "false";
											log.error("第"+(i+1)+"次关门失败!!");
											e.printStackTrace();
										} catch (UnknownHostException e) {
											flagString = "false";
											log.error("第"+(i+1)+"次关门失败!!");
											e.printStackTrace();
										} catch (IOException e) {
											flagString = "false";
											log.error("第"+(i+1)+"次关门失败!!");
											e.printStackTrace();
										} 
									}
									if("true".equals(flagString))
										break;
									}
							        time.cancel();
								}
						}
		        }
			}, Integer.parseInt(Constants.DBMAP.get("WS_DOOR_TIMEOUT")));
			
		}
		return result;
	}
	
	/**
	* @Description： 开门操作
	* @Author：hp
	* @Date：2013-5-17
	* @param siteId
	* @param order
	* @param shutdown
	* @param loginId
	* @param ip
	* @param socket
	* @param dis
	* @param dos
	* @return
	* @throws NumberFormatException
	* @throws UnknownHostException
	* @throws IOException
	**/
	public String openDoor(final String siteId,String order,final String loginId,final String ip,final Socket socket,
			final DataInputStream dis ,final DataOutputStream dos) throws NumberFormatException, UnknownHostException, IOException{
		if(ip.equals(null) || order.equals(null)){
			log.info("ip地址、设备id、命令不能为空");
			return null;
		}
		StringBuffer result = new StringBuffer();
		try {
			dos.write(HexAndByte.hexStringToBytes(order));
			dos.flush();
			log.info("************把开门命令写入到门锁系统中**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			//构造一个时间保护线程
			TimeoutThread time = new TimeoutThread(1000,dis);
			Thread.sleep(500);
			log.info("************从门锁中读出结果开始**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			time.start();//线程开始
			byte t = dis.readByte();
			time.cancel();//取消线程
			String br = Integer.toHexString(t & 0xFF);
			log.info("************从门锁中读出结果："+br+" 结束**********");
			if(br.equals("30")){
				if(StringUtils.isNotBlank(loginId)){
					logControlRecord.recordLog(loginId, siteId,"发送门锁开门指令","开门：成功");
				}
				result.append("true");
				log.info("命令为:"+order+" 的操作成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+br);
			}else{
				if(StringUtils.isNotBlank(loginId)){
					logControlRecord.recordLog(loginId, siteId,"发送门锁开门指令","开门：失败");
				}
				result.append("false");
				log.info("命令为:"+order+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+br);
			}
		} catch (IOException e) {
			result.append("false");
			logControlRecord.recordLog(loginId, siteId,"读取开门返回状态","读取返回值：失败");
			//调用门锁除了result=false外,还有一些异常的情况
			log.info("命令为:"+order+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			log.info(e.getMessage());
		} catch (InterruptedException e) {
			log.info(e.getMessage());
		}
		finally
		{       
			log.info("***************client close**************");
			try 
			{
			    IOUtils.closeQuietly(dis);
				IOUtils.closeQuietly(dos);
				if (null != socket )
				{
					socket.close();
				}
				
			} catch (IOException e) {  
				log.info(e.getMessage());
			}
		
		}
		
//		socket.shutdownInput();
//		socket.shutdownOutput();
		return result.toString();
	}
	
	
	/**
	* @Description：关门的操作
	* @Author：hp
	* @Date：2013-5-13
	* @param siteId
	* @param order
	* @param shutdown
	* @param loginId
	* @param ip
	* @param dis
	* @param dos
	* @param socket
	* @throws UnknownHostException
	* @throws IOException
	**/
	public String closeDoor(String siteId,String shutdown,String loginId,String ip,DataInputStream dis,DataOutputStream dos,Socket socket){
		StringBuffer result = new StringBuffer();
		try 
		{
			dos.write(HexAndByte.hexStringToBytes(shutdown));
			dos.flush();
			log.info("************把关门命令写入到门锁系统中**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			//构造一个时间保护线程
			TimeoutThread time = new TimeoutThread(1000,dis);
			Thread.sleep(500);
			log.info("************从门锁中读出结果开始**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			time.start();//线程开始
			byte tt = dis.readByte();
			time.cancel();//取消线程
			String brr = Integer.toHexString(tt & 0xFF);
			log.info("************从门锁中读出结果："+brr+"结束**********");
			if(brr.equals("30")){
				
				if(StringUtils.isNotBlank(loginId)){
					result.append("true");
					logControlRecord.recordLog(loginId, siteId,"发送门锁关门指令","关门：成功");
					log.info("命令为:"+shutdown+" 的操作成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+brr);
				}
			
			}else{
				if(StringUtils.isNotBlank(loginId)){
					result.append("false");
					logControlRecord.recordLog(loginId, siteId,"发送门锁关门指令","关门：失败");
					log.info("命令为:"+shutdown+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+brr);
				}
			}
			log.info("***********门锁返回信息**************");
		} catch (NumberFormatException e) {
			log.info(e.getMessage());
		} catch (InterruptedException e) {
			log.info(e.getMessage());
		} catch (IOException e) {
			if(!result.toString().equals("true") )
				result.append("false");
			logControlRecord.recordLog(loginId, siteId,"读取关门返回状态","读取返回值：失败");
			log.info("命令为:"+shutdown+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			log.info(e.getMessage());
		}
		catch(NullPointerException e)
		{   
			if(!result.toString().equals("true") )
				result.append("false");
			log.info(e.getMessage());
		}
		finally
		{
			try {
				IOUtils.closeQuietly(dis);
			    IOUtils.closeQuietly(dos);
			    if (null != socket )
				{
					socket.close();
				}
				
			 } catch (IOException e) {  
				log.info(e.getMessage());
				if(!result.toString().equals("true") )
					result.append("false");
			 }
		}
		return result.toString();
	}
	
	
	
	
	/**
	* @Description：查看门的状态
	* @Author：hp
	* @Date：2013-5-10
	* @param order
	* @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	**/
	@SuppressWarnings("unchecked")
	public List<Map> findDoorStatus(String ip,List<Map> order,String logid) throws NumberFormatException, UnknownHostException, IOException{
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket socket = null;
		StringBuffer sb = new StringBuffer();
		boolean flag = true;
		int count = 0;
		try{
			socket = new Socket(ip, Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
		}catch(ConnectException e){
			log.info("通讯机连接失败......"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return null;
		}
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		List<Map> statusRes = new ArrayList<Map>();
		for (Map map : order) {
			while(flag){
				if(count == 5){
					count = 0;
					break;
				}
				Map mapStatus = new HashMap();
				if(map.get("NODEID") != null && Constants.DBMAP.get("WS_DOORORDER_FINDSTATUS") != null && map.get("ADRSSTORE") != null && map.get("ADRSCOMM") != null){
					String newOrder = convertOrderInfo(map.get("NODEID").toString(), Constants.DBMAP.get("WS_DOORORDER_FINDSTATUS"), map.get("ADRSSTORE").toString(), map.get("ADRSCOMM").toString());
					try {
						dos.write(HexAndByte.hexStringToBytes(newOrder));
						dos.flush();
						log.info("************把查看门状态的命令写入到门锁系统中**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						Thread.sleep(500);
						System.out.println("写入通讯机");
						System.out.println();
						System.out.println("读出通讯机返回状态数据"); 
						//构造一个时间保护线程
						TimeoutThread time = new TimeoutThread(1000,dis);
						time.start();//线程开始
						byte[] b = new byte[18];
						time.cancel();//取消线程
						dis.read(b, 0, 18);
						for (byte c : b) {
							String result = Integer.toHexString(c & 0xFF);
							if(result.length() == 1){
								sb.append("0"+Integer.toHexString(c & 0xFF));
							}else{
								sb.append(Integer.toHexString(c & 0xFF));
							}
						}
						
						if (sb.toString().toUpperCase().substring(8, 24).equals(
							"FFFFFFFFFFFFFFFF") && sb.toString().toUpperCase().substring(0, 2).equals("3C")) {
							break;
						}
						//查询门的状态
						String status = findStatus(sb.toString());
						//保存查询出来的记录
						commonManager.saveDoorStatus(sb.toString(), logid);
						mapStatus.put(map.get("NODEID").toString(), status);
						statusRes.add(mapStatus);
						log.info("命令为:"+newOrder+" 的操作成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+sb.toString());
					} catch (IOException e) {
						count = count +1;
						log.info("命令为:"+newOrder+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						//如果命令不正确或是门锁损坏等造成通信不通时，关闭连接和套接字连接，然后重新连接已确保其他命令的执行
						socket = new Socket(ip, Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
						dis = new DataInputStream(socket.getInputStream());
						dos = new DataOutputStream(socket.getOutputStream());
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					finally
					{
						try {
							IOUtils.closeQuietly(dis);
							IOUtils.closeQuietly(dos);
							if (null != socket) {
								socket.close();
							}
						
						} catch (IOException e) {  
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		try {
			IOUtils.closeQuietly(dis);
			IOUtils.closeQuietly(dos);
			if (null != socket) {
				socket.close();
			}
		
		} catch (IOException e) {  
			e.printStackTrace();
		}
		
		return statusRes;
	}
	
	
	/**
	* @Description：app调用查询接口
	* @Author：hp
	* @Date：2013-6-1
	* @param ip
	* @param order
	* @param logid
	* @return
	* @throws NumberFormatException
	* @throws UnknownHostException
	* @throws IOException
	**/
	@SuppressWarnings("unchecked")
	public List<Map> findDoorStatusToApp(String ip,List<Map> order,String logid) throws NumberFormatException, UnknownHostException, IOException{
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket socket = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		try{
			socket = new Socket(ip, Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
		}catch(ConnectException e){
			log.info("通讯机连接失败......"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			return null;
		}
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
		List<Map> statusRes = new ArrayList<Map>();
		for (Map map : order) {
			if(count == 5){
				count = 0;
				break;
			}
			Map mapStatus = new HashMap();
			if(map.get("NODEID") != null && Constants.DBMAP.get("WS_DOORORDER_FINDSTATUS") != null && map.get("ADRSSTORE") != null && map.get("ADRSCOMM") != null){
				String newOrder = convertOrderInfo(map.get("NODEID").toString(), Constants.DBMAP.get("WS_DOORORDER_FINDSTATUS"), map.get("ADRSSTORE").toString(), map.get("ADRSCOMM").toString());
				try {
					dos.write(HexAndByte.hexStringToBytes(newOrder));
					dos.flush();
					log.info("************把查看门状态的命令写入到门锁系统中**********"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					Thread.sleep(500);
					System.out.println("写入通讯机");
					System.out.println();
					System.out.println("读出通讯机返回状态数据"); 
					//构造一个时间保护线程
					TimeoutThread time = new TimeoutThread(1000,dis);
					time.start();//线程开始
					byte[] b = new byte[18];
					time.cancel();//取消线程
					dis.read(b, 0, 18);
					for (byte c : b) {
						String result = Integer.toHexString(c & 0xFF);
						if(result.length() == 1){
							sb.append("0"+Integer.toHexString(c & 0xFF));
						}else{
							sb.append(Integer.toHexString(c & 0xFF));
						}
					}
					//查询门的状态
					String status = findStatus(sb.toString());
					//保存查询出来的记录
					commonManager.saveDoorStatus(sb.toString(), logid);
					mapStatus.put(map.get("NODEID").toString(), status);
					statusRes.add(mapStatus);
					log.info("命令为:"+newOrder+" 的操作成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  操作结果为："+sb.toString());
				} catch (IOException e) {
					log.info("命令为:"+newOrder+" 的操作不成功! 时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					//如果命令不正确或是门锁损坏等造成通信不通时，关闭连接和套接字连接，然后重新连接已确保其他命令的执行
					socket = new Socket(ip, Integer.valueOf(Constants.DBMAP.get("WS_DOORLOCK_PORT")));
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally
				{
					try {
						IOUtils.closeQuietly(dis);
						IOUtils.closeQuietly(dos);
						if (null!=socket) {
							socket.close();
						}
					} catch (IOException e) {  
						e.printStackTrace();
					}
				}
			}
		}
		try {
			IOUtils.closeQuietly(dis);
			IOUtils.closeQuietly(dos);
			if (null != socket) {
				socket.close();
			}
		
		} catch (IOException e) {  
			e.printStackTrace();
		}
		
		return statusRes;
	}
	
	
	/**
	* @Description：转换命令信息
	* @Author：hp
	* @Date：2013-3-30
	* @param doorId
	* @param order
	* @param doorAddress
	* @param commMachineAddress
	* @param card
	* @return
	**/
	public String convertOrderInfo(String doorId,String order,String doorAddress,String commMachineAddress,String card,String type,String arsnum){
		if(StringUtils.isBlank(doorId) || StringUtils.isBlank(order)){
			log.info("传入的参数为空!!");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(order).append(convertHex(doorAddress)).append(convertHex(commMachineAddress)).append(DoorControlToSystemService.EIGHTAUTHORITY);
		if(type.equals(DoorControlToAppService.INIT)){
			if(card.length() == 16){
				sb.append(card).append(convertHex(arsnum));
			}else if(card.length() == 12){
				sb.append("0000"+card).append(convertHex(arsnum));
			}
			sb.append(DoorControlToSystemService.FIRSTCARDTYPE);
		}
		if(type.equals(DoorControlToSystemService.DELETE)){
			sb.append(DoorControlToSystemService.DELETELOCK).append(convertHex(arsnum));
			sb.append(DoorControlToSystemService.FIRSTEXTCODE);
		}
		sb.append(getXorVerificationCode(sb.toString()));
		return sb.toString();
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
	* @Description：转换指令信息        -------主要针对的是所有功能的公共部分
	* @Author：hp
	* @Date：2013-3-30
	* @param doorId        门的id
	* @param order		        命令
	* @param doorAddress   门锁地址
	* @param commMachineAddress  通讯机地址
	* @return
	**/
	public String convertOrderInfo(String doorId,String order,String doorAddress,String commMachineAddress){
		if(StringUtils.isBlank(doorId) || StringUtils.isBlank(order)){
			log.info("传入的参数为空!!");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(order).append(convertHex(doorAddress)).append(convertHex(commMachineAddress)).append(DoorControlToSystemService.EIGHTAUTHORITY).toString();
		return sb.toString();
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
	* @Description：解析传递的结果得到门的状态    00表示关门  ，01表示开门
	* @Author：hp
	* @Date：2013-5-6
	* @param result
	* @return  刷卡开门：3C011F0100009701700488394D8BD6DD01BC,,远程开门：3C011F0100000000000000014D8BD72E011C
	**/                 
	public String findStatus(String result){
		StringBuffer sb = new StringBuffer();
		sb.append(result.substring(6, 8));
		String order = sb.toString();
		if(order.equals("00") || order.equals("01")){
			return sb.toString();
		}else{
			return "";
		}
	}
	

	public LogControlRecord getLogControlRecord() {
		return logControlRecord;
	}


	public void setLogControlRecord(LogControlRecord logControlRecord) {
		this.logControlRecord = logControlRecord;
	}


	public CommonManager getCommonManager() {
		return commonManager;
	}


	public void setCommonManager(CommonManager commonManager) {
		this.commonManager = commonManager;
	}
	
	
}
