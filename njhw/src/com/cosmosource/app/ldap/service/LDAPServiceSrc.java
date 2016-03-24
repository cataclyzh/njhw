/**
 * 
 */
package com.cosmosource.app.ldap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;

import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.base.service.LDAPManager;

/**
 * @author Administrator
 * 
 */
public class LDAPServiceSrc extends LDAPManager {
	private String LDAP_SERVER_PROVIDER_URL = getProperties("LDAP_SERVER_PROVIDER_URL");
	private String LDAP_PROTOCOL = getProperties("LDAP_PROTOCOL");
	private String LDAP_USER_SEARCHBASE = getProperties("LDAP_USER_SEARCHBASE");
	private String LDAP_ADMIN_DN = getProperties("LDAP_ADMIN_DN");
	private String LDAP_ADMIN_PWD = getProperties("LDAP_ADMIN_PWD");

	/**
	 * 添加目录
	 * 
	 * @param distinguishedName
	 *            节点路径
	 * @param map
	 *            键值对
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean addDirectory(String distinguishedName, Map map) {
		Attribute objclass = new BasicAttribute("objectClass");
		objclass.add("organizationalUnit");

		logger.info("Creating Directory for: " + distinguishedName);
		return super.addEntry(distinguishedName, map, objclass);
	}

	/**
	 * 添加用户
	 * 
	 * @param distinguishedName
	 *            节点路径
	 * @param map
	 *            键值对
	 * @return
	 */
	public boolean addUser(UserInfo UserInfo) {

		if (super.connect(LDAP_SERVER_PROVIDER_URL, LDAP_PROTOCOL,
				LDAP_ADMIN_DN, LDAP_ADMIN_PWD)) {
			try {
				/* FOLLOWING ATTRIBUTES ARE REQUIRED */
				// ---loginUid---
				// Primary Key
				// ---uid---
				// userid
				// ---sn---
				// This is the X.500 surname attribute, which contains the family
				// name
				// of a person.
				// ---cn---
				// This is the X.500 commonName attribute, which contains a name of
				// an
				// object. If the object corresponds to a person, it is typically
				// the
				// person's full name.
				// ---userPassword---
				// Passwords are stored using an Octet String syntax and are not
				// encrypted. Transfer of cleartext passwords are strongly
				// discouraged
				// where the underlying transport service cannot guarantee
				// confidentiality and may result in disclosure of the password to
				// unauthorized parties.

				// the following attribute has three values
				Attribute objclass = new BasicAttribute("objectClass");
				objclass.add("njhwUser");
				objclass.add("uidObject");
				objclass.add("person");

				logger.info("Creating User for: " + "loginUid="
						+ UserInfo.getLoginUid() + "," + LDAP_USER_SEARCHBASE);
				boolean rs = super.addEntry(
						"loginUid=" + UserInfo.getLoginUid() + ","
								+ LDAP_USER_SEARCHBASE, UserInfo.toHashMap(),
						objclass);
				super.disconnect();
				return rs;
			} catch (Exception e) {
				logger.error("Problem adding user: " + e);
			}
		}

		return false;
	}

	/**
	 * 更新用户
	 * 
	 * @param UserInfo
	 *            用户对象
	 * @return
	 */
	public boolean updateUser(UserInfo UserInfo) {
		if (super.connect(LDAP_SERVER_PROVIDER_URL, LDAP_PROTOCOL,
				LDAP_ADMIN_DN, LDAP_ADMIN_PWD)) {
			try {
				logger.info("Updating User for: " + "loginUid="
						+ UserInfo.getLoginUid() + "," + LDAP_USER_SEARCHBASE);
				boolean rs = super.setAttributes(
						"loginUid=" + UserInfo.getLoginUid() + ","
								+ LDAP_USER_SEARCHBASE, UserInfo.toHashMap());
				super.disconnect();
				return rs;
			} catch (Exception e) {
				logger.error("Problem updating user: " + e);
			}
		}

		return false;
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
	 * 删除用户
	 * 
	 * @param distinguishedName
	 *            节点路径
	 * @return
	 */
	public boolean deleteUserByLoginUid(String loginUid) {
		logger.info("Deleting User for: " + "loginUid=" + loginUid + ","
				+ LDAP_USER_SEARCHBASE);
		if (super.connect(LDAP_SERVER_PROVIDER_URL, LDAP_PROTOCOL,
				LDAP_ADMIN_DN, LDAP_ADMIN_PWD)) {
			boolean rs = super.deleteEntry("loginUid=" + loginUid + ","
					+ LDAP_USER_SEARCHBASE);
			super.disconnect();
			return rs;
		}
		return false;
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
		logger.info("Changing Password for: " + "loginUid=" + loginUid + ","
				+ LDAP_USER_SEARCHBASE);
		// Perform the update
		if (super.connect(LDAP_SERVER_PROVIDER_URL, LDAP_PROTOCOL, "loginUid="
				+ loginUid + "," + LDAP_USER_SEARCHBASE, oldPassword)) {
			boolean rs = super.setAttribute("loginUid=" + loginUid + ","
					+ LDAP_USER_SEARCHBASE, "userPassword", newPassword);
			super.disconnect();
			return rs;
		}
		return false;
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
		logger.info("Reseting Password for: " + loginUid + ","
				+ LDAP_USER_SEARCHBASE);
		if (super.connect(LDAP_SERVER_PROVIDER_URL, LDAP_PROTOCOL,
				LDAP_ADMIN_DN, LDAP_ADMIN_PWD)) {
			boolean rs = super.setAttribute("loginUid=" + loginUid + ","
					+ LDAP_USER_SEARCHBASE, "userPassword", newPassword);
			super.disconnect();
			return rs;
		}
		return false;

	}

	/**
	 * 条件搜索
	 * 
	 * @param filter
	 *            过滤条件
	 * @return
	 */
	public List<UserInfo> findUser(String filter) {
		List<UserInfo> list = new ArrayList<UserInfo>();
		if (super.connect(LDAP_SERVER_PROVIDER_URL, LDAP_PROTOCOL,
				LDAP_ADMIN_DN, LDAP_ADMIN_PWD)) {
			list = super.search(LDAP_USER_SEARCHBASE, filter);
			super.disconnect();
		}
		return list;
	}

	/**
	 * 按LoginUid搜索用户
	 * 
	 * @param loginUid
	 *            loginUid
	 * @return
	 * 
	 */

	public UserInfo findUserByLoginUid(String loginUid) {
		UserInfo UserInfo = null;
		if (super.connect(LDAP_SERVER_PROVIDER_URL, LDAP_PROTOCOL,
				LDAP_ADMIN_DN, LDAP_ADMIN_PWD)) {
			UserInfo = super.lookup("loginUid=" + loginUid + ","
					+ LDAP_USER_SEARCHBASE);
			super.disconnect();
		}
		return UserInfo;
	}

	

	public static void main(String[] args) throws Exception {
		UserInfo UserInfo = new UserInfo();
		UserInfo.setCn("commonName"); // 必须
		UserInfo.setSn("surname"); // 必须
		UserInfo.setUid("1000"); // 必须
		UserInfo.setLoginUid("testEntry"); // 必须，唯一登陆号
		UserInfo.setUserPassword("123456"); // 新增时必须
		UserInfo.setUepBak1("uepBak1"); // 非必须

		System.out.println("--------------添加用户---------------");
		new LDAPServiceSrc().addUser(UserInfo);
		System.out.println("--------------修改密码---------------");
		new LDAPServiceSrc().changePasswordByLoginUid("testEntry", "123456",
				"admin");
		System.out.println("--------------重置密码---------------");
		new LDAPServiceSrc().resetPasswordByLoginUid("testEntry", "123456789");
		System.out.println("--------------修改用户---------------");
		UserInfo UserInfo2 = new UserInfo();
		UserInfo2.setCn("commonName1"); // 必须
		UserInfo2.setSn("surname2"); // 必须
		UserInfo2.setUid("10001"); // 必须
		UserInfo2.setLoginUid("testEntry"); // 必须，唯一登陆号
		UserInfo2.setUserPassword("1234561"); // 修改时为无效属性，即密码不会因属性值而变化，修改秘密需要调用修改密码函数！

		System.out.println(UserInfo2.toString());
		new LDAPServiceSrc().updateUser(UserInfo2);

		UserInfo UserInfo3 = new UserInfo();
		UserInfo3.setCn("commonName3"); // 必须
		UserInfo3.setSn("surname3"); // 必须
		UserInfo3.setUid("100013"); // 必须
		UserInfo3.setLoginUid("testEntry"); // 必须，唯一登陆号
		UserInfo3.setUepBak1("sdadasd"); // 非必须，若未设置值，则LDAP中相应条目会删除。
		System.out.println(UserInfo3.toString());
		new LDAPServiceSrc().updateUser(UserInfo3);
		System.out.println("--------------批量修改---------------");
		List<UserInfo> list = new ArrayList<UserInfo>();
		for (int i = 0; i < 20; i++) {
			list.add(UserInfo3);
		}
		double start=System.currentTimeMillis();
		new LDAPServiceSrc().updateAll(list);
		System.out.println("耗时"+(System.currentTimeMillis()-start));
		System.out.println("--------------条件搜索---------------");
		new LDAPServiceSrc().findUser("(uepBak1=sdadasd)");
		System.out.println("--------------搜索用户---------------");
		new LDAPServiceSrc().findUserByLoginUid("infoAdmin");
		System.out.println("--------------删除用户---------------");
		new LDAPServiceSrc().deleteUserByLoginUid("testEntry");

	}
}
