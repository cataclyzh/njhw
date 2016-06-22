/**
 * 
 */
package com.cosmosource.app.ldap.service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.codec.binary.Base64;

import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.app.ldap.wsClient.SynUserService;
import com.cosmosource.base.service.BaseManager;

/**
 * @author Administrator
 * 
 */
public class LDAPService extends BaseManager {
	/**
	 * 添加用户
	 * 
	 * @param distinguishedName
	 *            节点路径
	 * @param map
	 *            键值对
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public boolean addUser(UserInfo UserInfo) {
		logger.info("Creating User for: " + "loginUid="
				+ UserInfo.getLoginUid());
		boolean rs = false;
		try {
			SynUserService synUserService = new SynUserService();
			rs = synUserService.getSynUserPort().insertUser(UserInfo);
			logger.info(rs == true ? "success" : "failed");
		} catch (Exception e) {
			logger.error("Problem creating user: " + e);
		}
		return rs;

	}

	public boolean updateUser(UserInfo UserInfo) {
		logger.info("Updating User for: " + "loginUid="
				+ UserInfo.getLoginUid());
		boolean rs = false;
		try {
			SynUserService synUserService = new SynUserService();
			rs = synUserService.getSynUserPort().updateUser(UserInfo);
			logger.info(rs == true ? "success" : "failed");
		} catch (Exception e) {
			logger.error("Problem updating user: " + e);
		}
		return rs;

	}

	/**
	 * 批量修改用户
	 * 
	 * @param list
	 *            用户对象列表
	 */
	public int updateAll(List<UserInfo> list) {
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (updateUser(list.get(i)))
				count++;
		}
		logger.info("Update users complete: " + count + " of " + list.size()
				+ " operation(s) success.");
		return count;
	}

	/**
	 * 定时更新
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doScheduler() {
		List<HashMap> list = new ArrayList(); // 参数Map
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String importTime = df.format(new Date());
		String ipAddr = new Base64().encodeToString(getIP().getBytes());
		list = sqlDao.findList("LDAPSQL.getUserList", null);
		logger.info("Task started!");
		logger.info("----------------Start import----------------");
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		logger.info(String.valueOf(list.size()));
		logger.info(ipAddr);
		double start = System.currentTimeMillis();
		for (int i = 0; i < list.size(); i++) {
			if((i+1)%1000==0)
				logger.info("Prepareing Data......" + (((i+1)*100)/list.size())+ "%");
			if((i+1)==list.size()&&(list.size()%1000)!=0)
				logger.info("Prepareing Data......100%");
			UserInfo userInfo = new UserInfo();
			// required
			userInfo.setSn(ipAddr);
			userInfo.setCn(importTime);
			userInfo.setUserPassword("123456");
			userInfo.setUid(set(list.get(i).get("USERID")));
			userInfo.setLoginUid(set(list.get(i).get("LOGIN_UID")));

			// optional
			userInfo.setActiveFlag(set(list.get(i).get("ACTIVE_FLAG")));
			userInfo.setCardType(set(list.get(i).get("CARD_TYPE")));
			userInfo.setCardId(set(list.get(i).get("CARD_ID")));
			userInfo.setCardUid(set(list.get(i).get("CARD_UID")));
			userInfo.setChangeLoginPwdFlag(set(list.get(i).get(
					"CHANGE_LOGIN_PWD_FLAG")));
			userInfo.setDisplayName(set(list.get(i).get("DISPLAY_NAME")));
			userInfo.setFeeType(set(list.get(i).get("FEE_TYPE")));
			userInfo.setIsSystem(set(list.get(i).get("IS_SYSTEM")));
			userInfo.setJobId(set(list.get(i).get("JOB_ID")));
			userInfo.setLoginPwd(set(list.get(i).get("LOGIN_PWD")));
			userInfo.setLoginPwdMD5(set(list.get(i).get("LOGIN_PWD_MD5")));
			userInfo.setMemo(set(list.get(i).get("MEMO")));
			userInfo.setOrderNum(set(list.get(i).get("ORDER_NUM")));

			List<HashMap> orgNameRs = new ArrayList<HashMap>();
			Map orgMap = new HashMap();
			orgMap.put("orgid", list.get(i).get("ORG_ID"));
			orgNameRs = sqlDao.findList("LDAPSQL.getOrgInfo", orgMap);
			String orgName = null;
			String pName = null;
			String pId = null;
			for (int j = 0; j < orgNameRs.size(); j++) {
				orgName = set(orgNameRs.get(j).get("NAME"));
				pId = set(orgNameRs.get(j).get("P_ID"));
				List<HashMap> pNameRs = new ArrayList<HashMap>();
				orgMap = new HashMap();
				orgMap.put("orgid", orgNameRs.get(j).get("P_ID"));				
				pNameRs = sqlDao.findList("LDAPSQL.getOrgInfo", orgMap);
				for (int k = 0; k < pNameRs.size(); k++) {
					pName =set(pNameRs.get(k).get("NAME"));
				}
			}

			userInfo.setOrgId(set(list.get(i).get("ORG_ID")));
			userInfo.setOrgName(orgName);
			userInfo.setPId(pId);
			userInfo.setPName(pName);
			
			List<HashMap> plateNumRs = new ArrayList<HashMap>();
			Map plateNumMap = new HashMap();
			plateNumMap.put("userid", list.get(i).get("USERID"));
			plateNumRs = sqlDao.findList("LDAPSQL.getPlateNum", plateNumMap);
			String plateNum = null;
			for (int j = 0; j < plateNumRs.size(); j++) {
				plateNum = set(plateNumRs.get(j).get("PLATE_NUM"));
			}
			userInfo.setPlateNum(plateNum);
			userInfo.setReqTelId(set(list.get(i).get("REQ_TEL_ID")));
			userInfo.setResidentNo(set(list.get(i).get("RESIDENT_NO")));
			userInfo.setRoomId(set(list.get(i).get("ROOM_ID")));
			userInfo.setRoomInfo(set(list.get(i).get("ROOM_INFO")));
			userInfo.setStationId(set(list.get(i).get("STATION_ID")));
			userInfo.setStationName(set(list.get(i).get("STATION_NAME")));
			userInfo.setTelId(set(list.get(i).get("TEL_ID")));
			userInfo.setTelMAC(set(list.get(i).get("TEL_MAC")));
			
			List<HashMap> telNumRs = new ArrayList<HashMap>();
			Map telNumMap = new HashMap();
			telNumMap.put("telId", list.get(i).get("TEL_NUM"));
			telNumRs = sqlDao.findList("LDAPSQL.getTelInfo", telNumMap);
			String telNum = null;
			for (int j = 0; j < telNumRs.size(); j++) {
				telNum = set(telNumRs.get(j).get("TEL_NUM"));
			}
			userInfo.setTelNum(telNum);
			userInfo.setTmpCard(set(list.get(i).get("TMP_CARD")));
			userInfo.setUCode(set(list.get(i).get("U_CODE")));
			userInfo.setUepBak1(set(list.get(i).get("UEP_BAK1")));
			userInfo.setUepBak2(set(list.get(i).get("UEP_BAK2")));
			userInfo.setUepBak3(set(list.get(i).get("UEP_BAK3")));
			userInfo.setUepBak4(set(list.get(i).get("UEP_BAK4")));
			
			List<HashMap> uepFaxRs = new ArrayList<HashMap>();
			Map uepFaxMap = new HashMap();
			uepFaxMap.put("telId", list.get(i).get("UEP_FAX"));
			uepFaxRs = sqlDao.findList("LDAPSQL.getTelInfo", uepFaxMap);
			String uepFax = null;
			for (int j = 0; j < uepFaxRs.size(); j++) {
				uepFax = set(uepFaxRs.get(j).get("TEL_NUM"));
			}
			userInfo.setUepFax(uepFax);
			userInfo.setUepFlag(set(list.get(i).get("UEP_FLAG")));
			userInfo.setUepHobby(set(list.get(i).get("UEP_HOBBY")));
			userInfo.setUepMail(set(list.get(i).get("UEP_MAIL")));
			userInfo.setUepMobile(set(list.get(i).get("UEP_MOBILE")));
			userInfo.setUepPhoto(set(list.get(i).get("UEP_PHOTO")));
			userInfo.setUepSex(set(list.get(i).get("UEP_SEX")));
			userInfo.setUepSkill(set(list.get(i).get("UEP_SKILL")));
			userInfo.setUepType(set(list.get(i).get("UEP_TYPE")));
			userInfo.setUType(set(list.get(i).get("U_TYPE")));
			userInfo.setValidity(set(list.get(i).get("VALIDITY")));
			
			List<HashMap> webFaxRs = new ArrayList<HashMap>();
			Map webFaxMap = new HashMap();
			webFaxMap.put("telId", list.get(i).get("WEB_FAX"));
			webFaxRs = sqlDao.findList("LDAPSQL.getTelInfo", webFaxMap);
			String webFax = null;
			for (int j = 0; j < webFaxRs.size(); j++) {
				webFax = set(webFaxRs.get(j).get("TEL_NUM"));
			}
			userInfo.setWebFax(webFax);
			userInfos.add(userInfo);
		}
		int success = new LDAPService().updateAll(userInfos);
		logger.info("-----Total:" + list.size() + ", Success: " + success
				+ ", Fail: " + String.valueOf(list.size() - success) + "-----");
		logger.info("-----------------End import-----------------");
		logger.info("耗时" + (System.currentTimeMillis() - start));
	}

	/**
	 * 删除用户
	 * 
	 * @param distinguishedName
	 *            节点路径
	 * @return
	 */
	public boolean deleteUserByLoginUid(String loginUid) {
		logger.info("Deleting User for: " + "loginUid=" + loginUid);
		boolean rs = false;
		try {
			SynUserService synUserService = new SynUserService();
			rs = synUserService.getSynUserPort().deleteUser(loginUid);
			logger.info(rs == true ? "success" : "failed");
		} catch (Exception e) {
			logger.error("Problem deleting user: " + e);
		}
		return rs;
	}

	/**
	 * 修改密码
	 * 
	 * @param userName
	 *            用户名
	 * @param oldPassword
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return
	 */
	public boolean changePasswordByLoginUid(String loginUid,
			String oldPassword, String newPassword) {
		logger.info("Changing Password for: " + "loginUid=" + loginUid);
		boolean rs = false;
		try {
			SynUserService synUserService = new SynUserService();
			rs = synUserService.getSynUserPort().changePwd(loginUid,
					oldPassword, newPassword);
			logger.info(rs == true ? "success" : "failed");
		} catch (Exception e) {
			logger.error("Problem changing password: " + e);
		}
		return rs;
	}

	/**
	 * 重置密码
	 * 
	 * @param userName
	 *            用户名
	 * @param newPassword
	 *            新密码
	 * @return
	 */
	public boolean resetPasswordByLoginUid(String loginUid, String newPassword) {
		logger.info("Reseting Password for: " + loginUid);
		boolean rs = false;
		try {
			SynUserService synUserService = new SynUserService();
			rs = synUserService.getSynUserPort()
					.resetPwd(loginUid, newPassword);
			logger.info(rs == true ? "success" : "failed");
		} catch (Exception e) {
			logger.error("Problem reseting password: " + e);
		}
		return rs;

	}

	/**
	 * 条件搜索
	 * 
	 * @param filter
	 *            过滤条件
	 * @return
	 */
	public List<UserInfo> findUser(String filter) {
		List<UserInfo> rs = new ArrayList<UserInfo>();
		try {
			SynUserService synUserService = new SynUserService();
			rs = synUserService.getSynUserPort().findUser(filter);
			logger.info(rs.toString());
			logger.info("Search complete: " + rs.size() + " result(s)");
		} catch (Exception e) {
			logger.error("Problem finding user: " + e);
		}
		return rs;
	}

	/**
	 * 按LoginUid搜索用户
	 * 
	 * @param loginUid
	 *            loginUid
	 * @return
	 * @throws NamingException
	 */

	public UserInfo findUserByLoginUid(String loginUid) throws NamingException {
		UserInfo rs = null;
		try {
			SynUserService synUserService = new SynUserService();
			rs = synUserService.getSynUserPort().findUserByLoginUid(loginUid);
			logger.info(rs.toString());
		} catch (Exception e) {
			logger.error("Problem finding user: " + e);
		}
		return rs;
	}

	private String set(Object obj) {
		if (obj == null || obj.toString().trim().equals("")) {
			return null;
		} else {
			return obj.toString().trim();
		}
	}

	@SuppressWarnings("rawtypes")
	private String getIP() {
		String resultIP = "";
		try {
			Enumeration allNetInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				// logger.info(netInterface.getName());
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						if (resultIP != null && !resultIP.equals(""))
							resultIP += ";";
						resultIP += ip.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (resultIP == null || resultIP.equals(""))
			resultIP = "Problem getting IP Address";
		return resultIP;
	}

	public static void main1(String[] args) throws Exception {
		UserInfo UserInfo = new UserInfo();
		UserInfo.setCn("commonName"); // 必须
		UserInfo.setSn("surname"); // 必须
		UserInfo.setUid("1000"); // 必须
		UserInfo.setLoginUid("testEntry"); // 必须，唯一登陆号
		UserInfo.setUserPassword("123456"); // 新增时必须
		UserInfo.setUepBak1("uepBak1"); // 非必须

		System.out.println("--------------添加用户---------------");
		new LDAPService().addUser(UserInfo);
		System.out.println("--------------修改密码---------------");
		new LDAPService().changePasswordByLoginUid("testEntry", "123456",
				"admin");
		System.out.println("--------------重置密码---------------");
		new LDAPService().resetPasswordByLoginUid("testEntry", "123456789");
		System.out.println("--------------修改用户---------------");
		UserInfo UserInfo2 = new UserInfo();
		UserInfo2.setCn("commonName1"); // 必须
		UserInfo2.setSn("surname2"); // 必须
		UserInfo2.setUid("10001"); // 必须
		UserInfo2.setLoginUid("testEntry"); // 必须，唯一登陆号
		UserInfo2.setUserPassword("1234561"); // 修改时为无效属性，即密码不会因属性值而变化，修改秘密需要调用修改密码函数！

		System.out.println(UserInfo2.toString());
		new LDAPService().updateUser(UserInfo2);

		UserInfo UserInfo3 = new UserInfo();
		UserInfo3.setCn("commonName3"); // 必须
		UserInfo3.setSn("surname3"); // 必须
		UserInfo3.setUid("100013"); // 必须
		UserInfo3.setLoginUid("testEntry"); // 必须，唯一登陆号
		UserInfo3.setUepBak1("sdadasd"); // 非必须，若未设置值，则LDAP中相应条目会删除。
		UserInfo3.setRoomInfo("asdadasdasdasd");
		System.out.println(UserInfo3.toString());
		new LDAPService().updateUser(UserInfo3);
		System.out.println("--------------批量修改---------------");
		List<UserInfo> list = new ArrayList<UserInfo>();
		for (int i = 0; i < 20; i++) {
			list.add(UserInfo3);
		}
		double start = System.currentTimeMillis();
		new LDAPService().updateAll(list);
		System.out.println("耗时" + (System.currentTimeMillis() - start));
		System.out.println("--------------条件搜索---------------");
		new LDAPService().findUser("(uepBak1=sdadasd)");
		System.out.println("--------------搜索用户---------------");
		new LDAPService().findUserByLoginUid("infoAdmin");
		System.out.println("--------------删除用户---------------");
		new LDAPService().deleteUserByLoginUid("testEntry");

	}
	
	public static void main(String[] args){
		List<UserInfo> userList = new LDAPService().findUser("(loginUid=zg_wut)");
		System.out.println(userList.size());
		for(UserInfo u : userList){
			System.out.println(u.getDisplayName());
			System.out.println(u.getLoginPwdMD5());
		}
	}
}
