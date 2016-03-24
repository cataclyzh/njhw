package com.cosmosource.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @类描述:常量类
 * @作者： WXJ
 * @USER_ID 用户ID
 * @USER_NAME 用户名称
 * @LOGIN_NAME 登录标识 
 * @ORG_ID 机构ID 
 * @ORG_NAME 机构名称
 * @USER_TEL_MAC 话机MAC
 * @USER_CARD_NO 市民卡号或临时卡号
 */
public class Constants {
	//大厦四个角每层面积946平方米 1~14层
	public static final Long EDIFICE_HORN_AREA = 946*4l;
	//大厦平层面积5100平方米 15~19层
	public static final Long EDIFICE_FLAT_AREA = 5100l;
	//三维能耗级别
	public static final String ENERGY_LEVEL_ONE = "1";
	public static final String ENERGY_LEVEL_TWO = "2";
	public static final String ENERGY_LEVEL_THREE = "3";
	public static final String ENERGY_LEVEL_FOUR = "4";
	public static final String ENERGY_LEVEL_FIVE = "5";
	//不同级别对应的颜色
	public static final String ENERGY_LEVEL_ONE_COLOR ="51,159,250,255";
	public static final String ENERGY_LEVEL_TWO_COLOR = "105,216,14,255";
	public static final String ENERGY_LEVEL_THREE_COLOR ="228,224,1,255";
	public static final String ENERGY_LEVEL_FOUR_COLOR = "249,100,0,255";
	public static final String ENERGY_LEVEL_FIVE_COLOR ="249,0,0,255";
	
	//三维能耗单位(electric：电 water:水 gas:气)
	public static final String ENERGY_ELECTRIC_UNIT = "Kw/h";
	public static final String ENERGY_WATER_UNIT = "m³";
	public static final String ENERGY_GAS_UNIT = "m³/h";
	
	//能耗类型
	public static final String ENERGY_TYPE_WATER = "水";
	public static final String ENERGY_TYPE_FLOW = "气";
	public static final String ENERGY_TYPE_ELECTRIC = "电";

	
	public static final String USER_TEL_MAC = "_usertelmac";
	public static final String USER_CARD_NO = "_usercardno";
	
	public static final String USER = "_user";
	public static final String USER_ID = "_userid";
	public static final String LOGIN_NAME = "_loginname";
	public static final String USER_NAME = "_username";
	public static final String USER_CODE = "_usercode";
	public static final String LOGININFO = "_logininfo";
	public static final String COMPANY_ID = "_companyid";
	public static final String ORG_ROOT = "_orgRoot";
	public static final String ROLES = "_roles";
	public static final String AUTH = "_auth";
	public static final String ORG_ID = "_orgid";
	public static final String ORG_PARENTID = "_orgparentid";
	public static final String ORG_CODE = "_orgcode";
	public static final String ORG_NAME = "_orgname";
	public static final String ORG_TYPE = "_orgtype";
	public static final String ORG_LAYER = "_treelayer";
	public static final String ORG_TAXNO = "_orgtaxno";
	public static final String ORG_TAXNAME = "_orgtaxname";
	public static final String SUBORG_TAXNOS = "_suborgtaxnos";
	public static final String COMPANY = "_company";
	public static final String COMPANY_Min = "_company_min";
	public static final String ROLEID = "_roleid";
	public static final String USERROLELIST = "_userrole";
	public static final String USERMENULIST = "_usermenu";
    public static final String BUNDLE_KEY = "ApplicationResources";
    public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;
    public static final String CONFIG = "appConfig";
	public static final String TRIGGERNAME = "triggerName";
	public static final String TRIGGERGROUP = "triggerGroup";
	public static final String STARTTIME = "startTime";
	public static final String ENDTIME = "endTime";
	public static final String REPEATCOUNT = "repeatCount";
	public static final String REPEATINTERVEL = "repeatInterval";
	public static final String FILES_ROOT = "_filesRootDir";
	public static final String TAXNO_DIR = "_taxnoDir";
	public static final String USER_STATUS = "_userstatus";
	public static final String ROLES_MENUS = "_rolesmenus";
	public static final String ORG_SUBSYS = "_orgsubsys";
	public static final String USER_TYPE = "_usertype";
	public static final int PAGESIZE =  10;
	public static final int EXPORT_MAXSIZE =  30000;
	//DefaultPage
	public static final String DEFAULTPAGE = "_defaultpage";

	/**
	 * True
	 */
	public static final boolean TRUE_FLAG = true;
	/**
	 * FALSE
	 */
	public static final boolean FALSE_FLAG = false;

	/**
	 * Socket 读取长度
	 */
	public static final int READ_LENGTH = 64 * 64;

	/**
	 * 发送队列最大长度
	 */
	public static final int MAX_SEND_LIST_LENGTH = 1024;

	/**
	 * 发送线程等待时间
	 */
	public static final int SEND_THREAD_WAIT_TIME = 2000;

	/**
	 * 链路检测时间
	 */
	public static final int CHECK_LINK_TIME = 60000;
	
	/**
	 * 同步时间间隔
	 */
	public static final int CHECK_TIME_TIME = 300000;
	
	/**
	 * 读取字节长度
	 */
	public static final int READ_BUFFER_SIZE = 256;
	
////	************************接口定义*************************************
//	
////	***********************市民卡****************************************
//	public static final String WS_PERSONNALCARD_URL ="personCardWsUrl";
//	public static final String WS_PERSONNALCARD_METHOD="personCardWsMethod";
//	
////	***********************智慧南京**************************************
//	public static final String WS_WISDOMNJWE_URL = "wisdomNjWEWsUrl";
//	public static final String WS_WISDOMNJWEATHER_METHOD = "wisdomNjWeatherMethod";
//	public static final String WS_WISDOMNJENVIROMENT_METHOD="wisdomNjEnvironmentMethod";
//	public static final String WS_WISDOMNJTRAFFIC_URL = "wisdomNjTrafficWsUrl";
//	public static final String WS_WISDOMNJTRAFFIC_METHOD = "wisdomNjRoadMethod";
//	
////	***********************门锁******************************************
//	public static final String WS_DOORLOCK_URL ="doorLockUrl";
//	public static final String WS_DOORLOCK_PORT = "doorLockPort";
//	public static final String WS_DOORLOCK_CHECKTIMEORDER="checkTimeOrder";
//	public static final String WS_DOORLOCK_INITDELAUTHORDER="initDelAuthOrder";
//	public static final String WS_DOORLOCK_OPEN = "doorOpen";
//	public static final String WS_DOORLOCK_CLOSE = "doorClose";
//	
////	************************短信****************************************
//	public static final String WS_SMS_USERNAME = "smsUsername";
//	public static final String WS_SMS_PASSWORD = "smsPassword";
//	public static final String WS_SMS_IPADDRESS="smsIpAddress";
//	public static final String WS_SMS_APPLICATIONID="smsApplicationId";
//	public static final String WS_SMS_JDBCADDRESS="smsJdbcAddress";
//	public static final String WS_SMS_EXTCODE="smsExtcode";
//	
//	
////	************************接口定义*************************************
	
	
	public static final Map<String,String> status = new HashMap<String,String>();
	public static final Map<String,String> DBMAP = new HashMap<String,String>();
	public static final Map<String,String> CAACTIONMAP = new HashMap<String,String>();//KEY-(loginname+actioncode) VALUE-(0 OR 1)
	public static final Map<String,String> CAUSERMAP = new HashMap<String,String>();//KEY-(loginname+cano) VALUE-(0 OR 1)
	public static final int OBJTANK_NODEID = 6;
	static{
		status.put("ACQUIRED", "运行中");
		status.put("PAUSED", "暂停中");
		status.put("WAITING", "等待中");		
		status.put("ERROR", "异常");	
		status.put("BLOCKED", "堵塞");	
	}

}
