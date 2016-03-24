package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.app.port.service.IPPhoneSiteService;
import com.cosmosource.app.port.serviceimpl.DoorControlToAppService;
import com.cosmosource.app.port.serviceimpl.IPPhoneSiteServiceImpl;

public class TestDoorControl {
	
	private static ApplicationContext ctx;
	
	private static DoorControlToAppService doorControlToAppService;
	private static IPPhoneSiteService iPPhoneSiteServiceImpl;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("C:\\wk\\njhw\\njhw\\src\\test\\applicationContext1.xml");
		doorControlToAppService = (DoorControlToAppService) ctx.getBean("doorControlToAppService");
		iPPhoneSiteServiceImpl = (IPPhoneSiteService) ctx.getBean("testPhoneSite");
	}
	
//	@Test
//	public void testPatrolExceptionJob() throws Exception{
//		String msgId = UUID.randomUUID().toString();
//		doorControlToAppService.controlDoor("8172", "38", "1234567890", msgId);
//	}
	
	@Test
	public void testDoorControlWs(){
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version='1.0' encoding='UTF-8'?><root><param>");
		xml.append("<personId>1</personId>");
		xml.append("<roomName>D411</roomName>");
		xml.append("</param></root>");
		iPPhoneSiteServiceImpl.controlDoor_wx(xml.toString());
	}
	
}

