package test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.app.message.service.MessageService;
import com.cosmosource.app.message.service.OAService;
import com.cosmosource.app.message.vo.MsgBox;
import com.cosmosource.app.personnel.service.ObjMgrManager;
import com.cosmosource.app.property.job.PatrolExceptionJob;
import com.cosmosource.app.property.job.PatrolRecordJob;

public class TestService {
	
	private static ApplicationContext ctx;
	private static MessageService msgService;
	private static OAService oaService;
	
	private static ObjMgrManager objMgrManager;
	
	private static PatrolRecordJob patrolRecordJob;
	
	private static PatrolExceptionJob patrolExceptionJob;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("C:\\wk\\njhw\\njhw\\src\\test\\applicationContext1.xml");
		msgService = (MessageService) ctx.getBean("messageService");
		oaService = (OAService) ctx.getBean("oaService");
		objMgrManager = (ObjMgrManager) ctx.getBean("objMgrManager");
		
		patrolRecordJob = (PatrolRecordJob) ctx.getBean("patrolRecordJob");
		patrolExceptionJob = (PatrolExceptionJob) ctx.getBean("patrolRecordJob");
	}
	
	@Test
	public void testPatrolExceptionJob() throws Exception{
		patrolExceptionJob.execute();
	}
	
	@Test
	public void testPatrolRecordJob(){
		patrolRecordJob.execute();
	}
	
	@Test
	public void testOA(){
		System.out.println(oaService.getShouWenNum("zhouying"));
		
		List<Map<String, String>> list = oaService.oaShouWen("zhouying");
		for(int i=0; i<list.size(); i++){
			Map<String,String> m = list.get(i);
			System.out.println("==================== " + (i+1));
			Iterator it = m.entrySet().iterator(); 
			while(it.hasNext()){
				Entry e = (Entry) it.next();
				System.out.println(e.getKey() + " : " + e.getValue());
			}
		}
	}
	
	@Test
	public void testOA1(){
		System.out.println(oaService.getShouWenNum("zhouying"));
		
		List<Map<String, String>> list = oaService.oaShouWen("zhouying");
		for(int i=0; i<list.size(); i++){
			Map<String,String> m = list.get(i);
			System.out.println("==================== " + (i+1));
			Iterator it = m.entrySet().iterator(); 
			while(it.hasNext()){
				Entry e = (Entry) it.next();
				System.out.println(e.getKey() + " : " + e.getValue());
			}
		}
	}
	
	@Test
	public void testOA2(){
		System.out.println(oaService.getFaWenNum("zhouying"));
		
		List<Map<String, String>> list = oaService.oaFaWen("zhouying");
		for(int i=0; i<list.size(); i++){
			Map<String,String> m = list.get(i);
			System.out.println("==================== " + (i+1));
			Iterator it = m.entrySet().iterator(); 
			while(it.hasNext()){
				Entry e = (Entry) it.next();
				System.out.println(e.getKey() + " : " + e.getValue());
			}
		}
	}
	
	@Test
	public void testUCode(){
		System.out.println(msgService.getUcode("2105"));
	}
	
	@Test
	public void testAddMsg(){
		List<Map> list = new ArrayList<Map>();
		for(int i=0; i<100; i++){
			Map m = new HashMap();
			m.put("receiver", "InfoAdmin");
			m.put("sender", "InfoAdmin");
			m.put("msgTime", new Date());
			m.put("title", "标题"+(i+1));
			m.put("content", "消息内容"+(i+1));
			m.put("status", "0");
			m.put("receiverId", "1033");
			m.put("senderId", "1033");
			m.put("msgType", MsgBox.TYPE_NOTIFICATION);
			list.add(m);
		}
		msgService.addMsgs(list);
	}
	
	@Test
	public void testVisitor(){
		List<Map> list = msgService.queryVisitSortForTilte("1033");
		for(Map m: list){
			System.out.println(m.get("VS_FLAG"));
		}
	}
	
}
