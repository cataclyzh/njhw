package user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUser {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() throws Exception{
		AddUserServer addUser = new AddUserServer();
		addUser.getFileUserInfo();
//		addUser.selectUsers();
		addUser.testUserCardId();
//		addUser.showAddList();
		addUser.executeOrg();
	}
	
	@Test
	public void test2(){
		String[] ss = {"997167687581","1880352199997167687581"};
		for(String s : ss){
			if(s.length() > 12){
				int len = s.length();
				String result = s.substring(len-12);
				System.out.println("result: " + result);
			}
		}
	}

}
