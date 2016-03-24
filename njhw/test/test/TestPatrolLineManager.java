package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.app.property.service.PatrolLineManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;

public class TestPatrolLineManager {
	
	private static ApplicationContext ctx;
	
	private static PatrolLineManager patrolLineManager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("c:\\wk\\njhw3\\src\\test\\applicationContext1.xml");
		patrolLineManager = (PatrolLineManager) ctx.getBean("patrolLineManager");
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetPositionUser() {
		List<Map> users = patrolLineManager.getPositionUser("维修人员");
		for(Map u : users){
			String userName = String.valueOf(u.get("USER_NAME"));
			String tagMac = String.valueOf(u.get("TAGMAC"));
			System.out.println(userName + " | " + tagMac);
		}
	}

	@Test
	public void testGetPositionHistory() throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String startTimeStr = "2014-07-01 00:00:00";
		String endTimeStr = "2014-07-31 23:59:59";
		Date st = DateUtils.parseDate(startTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
		Date et = DateUtils.parseDate(endTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
		Map param = new HashMap();
		param.put("tagMac", "B0:8E:1A:01:00:4A");
//		param.put("tagMac", "B0:8E:1A:01:00:22");
		
		param.put("startTime", st);
		param.put("endTime", et);
		List<Map> result = patrolLineManager.getHistoryPosition(param);
		
		//对result List 分页
		
		
		for(Map m: result){
			String coordinatesName = String.valueOf(m.get("COORDINATESNAME"));
			String tagMac = String.valueOf(m.get("TAGMAC"));
			String time = String.valueOf(m.get("WRITETIME"));
			Date d1 = (Date)m.get("WRITETIME");
			
			String d1Str = format.format(d1);
			
			//通过tagMac获取位置信息
			Map positionMap = patrolLineManager.getPositionName(coordinatesName);
			
			String placeId = String.valueOf(positionMap.get("pointId"));
			String placeName = String.valueOf(positionMap.get("pointName"));
			
			System.out.println(coordinatesName + " | "
					+ tagMac + " | "
					+ time + " | "
					+ d1Str + " | "
					+ placeId + " | "
					+ placeName);
			
		}
		System.out.println("count: " + result.size());
		
	}
	
	@Test
	public void getUserPosition() throws Exception{
		List<Map> users = patrolLineManager.getPositionUser("维修人员");
		String startTimeStr = "2014-07-01 00:00:00";
		String endTimeStr = "2014-07-31 23:59:59";
		
		Page<HashMap> page = new Page<HashMap>(10);
		
		List<Map> resultList = new ArrayList<Map>();
		for(Map u : users){
			String userName = String.valueOf(u.get("USER_NAME"));
			String tagMacStr = String.valueOf(u.get("TAGMAC"));
//			System.out.println(userName + " | " + tagMacStr);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date st = DateUtils.parseDate(startTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
			Date et = DateUtils.parseDate(endTimeStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
			Map param = new HashMap();
			param.put("tagMac", tagMacStr);
			param.put("startTime", st);
			param.put("endTime", et);
			List<Map> result = patrolLineManager.getHistoryPosition(param);
			for(Map m: result){
				String coordinatesName = String.valueOf(m.get("COORDINATESNAME"));
				String tagMac = String.valueOf(m.get("TAGMAC"));
				String time = String.valueOf(m.get("WRITETIME"));
				Date d1 = (Date)m.get("WRITETIME");
				
				String d1Str = format.format(d1);
				
				//通过tagMac获取位置信息
				Map positionMap = patrolLineManager.getPositionName(coordinatesName);
				
				String placeId = String.valueOf(positionMap.get("pointId"));
				String placeName = String.valueOf(positionMap.get("pointName"));
//				System.out.println(coordinatesName + " | "
//						+ tagMac + " | "
//						+ time + " | "
//						+ d1Str + " | "
//						+ placeId + " | "
//						+ placeName + " | "
//						+ userName);
				
				Map resultMap = new HashMap();
				resultMap.put("userName", userName);
				resultMap.put("time", d1Str);
				resultMap.put("placeName", placeName);
				resultMap.put("placeId", placeId);
				
				if(placeName != null && placeName.trim().length() != 0
						&& !placeName.equals("null")){
					resultList.add(resultMap);
				}
				
			}
			
			page.setPageSize(10);
			page.setPageNo(2);
			
			int pageSize = page.getPageSize();
			int startIndex = page.getFirst() - 1;
			int endIndex = startIndex + pageSize;
			long totalCount = resultList.size();
			page.setTotalCount(totalCount);
			
//			page.setResult(list)
			
			for(Map m : resultList){
				System.out.println(m);
			}
			System.out.println("count: " + resultList.size());
			
			List<Map> pageResult = new ArrayList<Map>();
			for(int i=startIndex; i<endIndex; i++){
				pageResult.add(resultList.get(i));
			}
			
			System.out.println("===================");
			
			for(Map m : pageResult){
				System.out.println(m);
			}
		}
		
		
	}
}
