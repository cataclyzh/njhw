package test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.app.property.job.AttendanceJob;
import com.cosmosource.app.property.service.AttendanceScheduleManager;

public class TestWyfwzxAttendance {
	
	private static ApplicationContext ctx;
	
	private static AttendanceJob attendanceJob;
	
	private static AttendanceScheduleManager attenManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("c:\\wk\\njhw\\njhw\\src\\test\\applicationContext1.xml");
		attendanceJob = (AttendanceJob) ctx.getBean("attendanceJob");
		attenManager = (AttendanceScheduleManager) ctx.getBean("attendanceScheduleManager");
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAttendanceJog(){
		attendanceJob.execute();
	}
	
	@Test
	public void testGetBeforeDayRecord(){
		String currentDay = "2014-06-28";
		String d = attendanceJob.getBeforeDay(currentDay);
		String card_no = "0000997163321582";
		List<Map> list = attenManager.getBeforeDayRecord(d, card_no);
		for(Map m : list){
			System.out.println(m);
		}
	}
	
	@Test
	public void testGetAfterDayRecord(){
		String currentDay = "2014-06-28";
		String d = attendanceJob.getNextDay(currentDay);
		String card_no = "0000997168835563";
		List<Map> list = attenManager.getAfterDayRecord(d, card_no);
		for(Map m : list){
			System.out.println(m);
		}
	}

}
