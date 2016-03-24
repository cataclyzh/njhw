package user;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class AddUserServer {
	protected static SqlMapClient sqlMapClient;
	
	private List<UserInfo> userList;
	
	private List<UserInfo> addList;
	
	private Set<String> orgNameSet;
	
	public AddUserServer() throws Exception{
		Reader reader = Resources.getResourceAsReader ("user/UserSqlMapConfig.xml");
		sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		userList = new ArrayList<UserInfo>();
		addList = new ArrayList<UserInfo>();
	}
	
	public void executeOrg() throws Exception{
		orgNameSet = new HashSet<String>();
		for(UserInfo u : addList){
			orgNameSet.add(u.getOrgName());
		}
		System.out.println(orgNameSet.size());
		for(String s : orgNameSet){
			System.out.println(s);
		}
	}
	
	public void selectUsers() throws Exception{
		for(UserInfo u:userList){
			queryUserByCardNo(u.getCitizenNo());
		}
	}
	
	private Map queryUserByCardNo(String no) throws Exception{
		Map result = null;
		List<Map> list = sqlMapClient.queryForList("user.queryUserInfoByCardNo", no);
		if(list != null && list.size() > 0){
			Map m = list.get(0);
			//System.out.println(m);
			result = m; 
		}
		return result;
	}
	
	private Map queryUserInfoByCardNo(String no) throws Exception{
		Map result = null;
		List<Map> list = sqlMapClient.queryForList("user.queryCardUser", no);
		if(list != null && list.size() > 0){
			Map m = list.get(0);
			//System.out.println(m);
			result = m; 
		}
		return result;
	}
	
	public void testUserCardId() throws Exception{
		for(UserInfo u:userList){
			String s = u.getCitizenNo();
			if(s.length() > 12){
				int len = s.length();
				String pureCitizenNo = s.substring(len-12);
				if(pureCitizenNo.indexOf("9971") == -1){
					//System.out.println("result: " + u + " | " + pureCitizenNo);
					u.setCitizenNo("");
				}else{
					String cardNo = "0000"+pureCitizenNo;
					Map m = queryUserByCardNo(cardNo);
					if(m != null){
						//System.out.println(m);
						//System.out.println(u + " | " + pureCitizenNo);						
					}else{
						addList.add(u);
					}
				}
			}
			
//			if(s.length() < 12){
//				String result = u.getCitizenNo(); 
//				System.out.println("result: " + result);
//			}
		}
	}
	
	public void showAddList() throws Exception{
		for(UserInfo u : addList){
			System.out.println(u);
		}
	}
	
	public void testUserOrg() throws Exception {
		for(UserInfo u : userList){
			String orgName = u.getOrgName();
		}
	}
	
	public void getFileUserInfo() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream("c:\\11\\user.csv"),"UTF-8"));

		String tmp = null;
		while ((tmp = br.readLine()) != null) {
			String[] userInfo = tmp.split(",");
//			System.out.println(userInfo[0]);
//			System.out.println(userInfo[1]);
//			System.out.println(userInfo[2]);
//			System.out.println(userInfo[3]);
			UserInfo u = new UserInfo();
			u.setName(userInfo[1].trim());
			u.setOrgName(userInfo[2].trim());
			u.setCitizenNo(userInfo[3].trim());
			userList.add(u);
		}
		br.close();
	}
}
