package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.app.attendance.service.NewCityAttendanceManager;
import com.cosmosource.app.entity.Users;
import com.cosmosource.app.message.service.MessageService;
import com.cosmosource.app.property.job.AttendanceJob;
import com.cosmosource.app.utils.DateUtils;

public class TestAttendance {
	private static ApplicationContext ctx;
	private static NewCityAttendanceManager attendanceManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("c:\\wk\\njhw3\\src\\test\\applicationContext1.xml");
		attendanceManager = (NewCityAttendanceManager) ctx.getBean("newCityAttendanceManager");
	}

	@Test
	public void testAddAttendance() {
		Map param = new HashMap();
		param.put("USER_DESC", "AAAAAAAAA");
		param.put("USER_ID", "5265");
		param.put("APPROVER_ID", "5265");
		param.put("STATUS", "0");
		param.put("START_DATE", "2014-04-06");
		param.put("END_DATE", "2014-04-08");
		param.put("TYPE", "4");
		param.put("START_TYPE", "0");
		param.put("END_TYPE", "1");
		param.put("APPROVER_TIME", DateUtils.getCurrentTime());
		
		attendanceManager.addApproversInfo(param, 3, 1);
	}
	
	@Test
	public void testSyncAttendancePerDay(){
		attendanceManager.syncAttendancePerDayById("1", 2, 2, false);
	}

	@Test
	public void testQueryApproversByUId(){
		List<Users> list = attendanceManager.queryApproversByUId(2095);
		for(Users u : list){
			System.out.println(u.getUserid() + "|" + u.getDisplayName());
		}
	}
	
	@Test
	public void testGetApprovesForTimeCheck1(){
		Map param = new HashMap();
		param.put("USERID", 5265);
		param.put("TIME", "2014-04-01");
		List list = attendanceManager.getApprovesForTimeCheck(param);
		System.out.println("list size: " + list.size());
	}
	
	@Test
	public void testGetApprovesForTimeCheck2(){
		System.out.println(attendanceManager.isApproveTimeConflict(5265, "2014-04-01"));
	}
	
	@Test
	public void testGetApprovedDays(){
		Map<String,String> map = attendanceManager.getApprovedDays(9948, "2014-03-07", "2014-04-28");
		System.out.println("GetApprovedDays: " + map);
	}
	
//	@Test
//	public void testCheckTimeConflictForApprove(){
//		String msg = attendanceManager.checkTimeConflictForApprove(5265, "", "", "" ,"");
//		System.out.println("msg: " + msg);
//	}
	
	@Test
	public void testCheckAttendanceTimeConflict(){
		String result = attendanceManager.checkAttendanceTimeConflict(2904, "2014-03-07", "2014-03-28", 0, 1);
		
	}
	
	@Test
	public void testGetGongChuJia(){
		Map result = attendanceManager.getGongChuJia("807");
		System.out.println(result.get("LOCATION_LABEL"));
		System.out.println(result.get("CAPTURE_IMG"));
	}
	
}