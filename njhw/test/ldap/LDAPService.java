/**
 * 
 */
package ldap;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.app.ldap.model.UserInfo;
import com.cosmosource.app.ldap.wsClient.SynUserService;
import com.cosmosource.base.service.BaseManager;

/**
 * @author Administrator
 * 
 */
public class LDAPService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
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

	public static void main(String[] args){
		List<UserInfo> userList = new LDAPService().findUser("(loginUid=zg_wut)");
		System.out.println(userList.size());
		for(UserInfo u : userList){
			System.out.println(u.getDisplayName());
			System.out.println(u.getLoginPwdMD5());
		}
	}
}
