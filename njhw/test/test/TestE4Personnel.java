package test;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.app.personnel_e.service.E4PersonnelManager;

public class TestE4Personnel {
	private static ApplicationContext ctx;
	private static E4PersonnelManager e4PersonnelManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("D:\\workspace3\\njhw3\\src\\test\\applicationContext1.xml");
		e4PersonnelManager = (E4PersonnelManager) ctx.getBean("e4PersonnelManager");
	}

	@Test
	public void testAddAttendance() {
		List result = e4PersonnelManager.getE4List(7196);
		System.out.println("result: " + result.size());
		System.out.println("result: " + result);
	}
	
	
}
