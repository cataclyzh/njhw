package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.app.buildingmon.service.BuildMonManager;
import com.cosmosource.app.message.service.MessageService;
import com.cosmosource.app.message.service.OAService;
import com.cosmosource.app.threedimensional.service.VisitorManager;
import com.cosmosource.app.wirelessLocation.service.WirelessManager;
import com.cosmosource.app.wirelessLocation.service.WirelessSqlManager;
import com.cosmosource.base.dao.ComDaoiBatis;
import com.cosmosource.base.service.Page;

public class TestClientTraceMap {
	private static ApplicationContext ctx;
	private static MessageService msgService;
	private static OAService oaService;
	
	private static BuildMonManager buildMonManager;
	private static WirelessManager wirelessManager;
	private static VisitorManager visitorManager;
	
	private static WirelessSqlManager wirelessSqlManager;
	
	private static ComDaoiBatis iBatisDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("c:\\wk\\njhw3\\src\\test\\applicationContext1.xml");
		buildMonManager = (BuildMonManager) ctx.getBean("buildMonManager");
		wirelessManager = (WirelessManager) ctx.getBean("wirelessManager");
		visitorManager = (VisitorManager) ctx.getBean("visitorManager");
		iBatisDao = (ComDaoiBatis) ctx.getBean("iBatisDao");
		wirelessSqlManager = (WirelessSqlManager) ctx.getBean("wirelessSqlManager");
	}
	
	@Test
	public void testLoadVisitorInfo(){
//		buildMonManager.loadVisitorInfo("NONE", "2522");
		List result = buildMonManager.loadVisitorInfo("王卓岩", "NONE");
		System.out.println("result: " + result);
	}

	@Test
	public void testWirelessPoint1() {
//		List<Map<String ,String>> list = objMgrManager.loadVisitorInfo("", "");
//		objMgrManager.getLocationNoByCardNum();
//		String tagMac = wirelessManager.getqueryTagMacByIDCradNum("0000997165721861");
//		System.out.println("=== " + tagMac + " ===");
		
		//B0:8E:1A:01:00:1E
		//B0:8E:1A:01:00:4E
		//B0:8E:1A:01:00:1C XB0055
		JSONObject jsonObj = wirelessManager.wirelessLocation("B0:8E:1A:01:00:1E");
		System.out.println("result: " + jsonObj.toString()); 
	}

	@Test
	public void testWirelessPoint2() {
		//B0:8E:1A:01:00:1E
		JSONObject jsonObj = wirelessManager.wirelessLocation("B0:8E:1A:01:00:1E");
		System.out.println("result: " + jsonObj.toString()); 
	}
	
	@Test
	public void updateBeaconTable(){
		Map param = new HashMap();
//		param.put("cardId", cardId);
		List<Map> list = iBatisDao.findList("TestSql.selectAllBeacon", param);
		for(int i=0; i<list.size(); i++){
//			System.out.println(list.get(i));
			Map m = list.get(i);
			long id = Long.valueOf(m.get("ID").toString());
			String placeId = String.valueOf(m.get("PLACEID")).trim();
			String placeNewId = changePlaceId(placeId);
			System.out.println(id + " : " + placeId + " : " + placeNewId);
			
			List updateList = new ArrayList();
			Map updateParam = new HashMap();
			updateParam.put("id", id);
			updateParam.put("placeId", placeNewId);
			updateList.add(updateParam);
			iBatisDao.batchUpdate(
					"TestSql.updateBeacon",
					updateList);
		}
	}
	
	private String changePlaceId(String placeId){
		String result = placeId;
		while(result.length() < 3){
			result = "0"+result;
		}
		return result;
	}
	
	@Test
	public void testVisitorList(){
		Page<HashMap> page = new Page<HashMap>(5);
		page.setPageNo(1);
		page = visitorManager.getVisitorList(page);
		List<HashMap> list = page.getResult();
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i));
		}
	}
	
	@Test
	public void testGetMacByCardId(){
		String tagMac = getMacByCardId("0000997165721861");
		System.out.println("tagMac: " + tagMac);
	}

	@Test
	public void insertVisCardIdMac(){
		String[][] cardId_mac = new String[][]{
				{"992572089826 ","11"},
				{"992572089827 ","OF"},
				{"992572089828 ","3A"},
				{"992572089829 ","OC"},
				{"992572089830 ","4E"},
				{"992572089831 ","O6"},
				{"992572089832 ","29"},
				{"992572089833 ","22"},
				{"992572089834 ","2F"},
				{"992572089835 ","119"},
				{"992572089836 ","23"},
				{"992572089837 ","2A"},
				{"992572089838 ","3F"},
				{"992572089839 ","14"},
				{"992572089840 ","24"},
				{"992572089841 ","1A"},
				{"992572089842 ","4A"},
				{"992572089843 ","OB"},
				{"992572089844 ","20"},
				{"992572089845 ","18"},
				{"992572089846 ","1E"},
				{"992572089847 ","1C"},
				{"992572089848 ","28"},
				{"992572089849 ","O9"},
				{"992572089850 ","4B"},
				{"992572089851 ","1B"},
				{"992572089852 ","O5"},
				{"992572089853 ","16"},
				{"992572089854 ","OD"},
				{"992572089855 ","4C"},
				{"992572089856 ","3E"},
				{"992572089857 ","4D"},
				{"992572089858 ","13"},
				{"992572089859 ","12"},
				{"992572089860 ","OA"},
				{"992572089861 ","10"},
				{"992572089862 ","O3"},
				{"992572089863 ","1D"},
				{"992572089864 ","OE"},
				{"992572089865 ","7B"},
				{"992572089866 ","2A"},
				{"992572089867 ","3D"},
				{"992572089868 ","7A"},
				{"992572089869 ","16"},
				{"992572089870 ","F2"},
				{"992572089871 ","OB"},
				{"992572089872 ","94"},
				{"992572089873 ","4K"},
				{"992572089874 ","33"},
				{"992572089875 ","E6"},
				{"992572089876 ","27"},
				{"992572089877 ","B9"},
				{"992572089878 ","43"},
				{"992572089879 ","DO"},
				{"992572089880 ","3O"},
				{"992572089881 ","GA"},
				{"992572089882 ","8B"},
				{"992572089883 ","A9"},
				{"992572089884 ","CB"},
				{"992572089885 ","24"},
				{"992572089886 ","D9"},
				{"992572089887 ","9A"},
				{"992572089888 ","1B"},
				{"992572089889 ","36"},
				{"992572089890 ","8K"},
				{"992572089891 ","O9"},
				{"992572089892 ","5C"},
				{"992572089893 ","5G"},
				{"992572089894 ","39"},
				{"992572089895 ","B4"},
				{"992572089896 ","FD"},
				{"992572089897 ","C3"},
				{"992572089898 ","AC"},
				{"992572089899 ","CF"},
				{"992572089900 ","K5"},
				{"992572089901 ","57"},
				{"992572089902 ","56"},
				{"992572089903 ","O2"},
				{"992572089904 ","FA"},
				{"992572089905 ","5F"},
				{"992572089906 ","19"},
				{"992572089907 ","OH"},
				{"992572089908 ","C5"},
				{"992572089909 ","AK"},
				{"992572089910 ","F7"},
				{"992572089911 ","4F"},
				{"992572089912 ","B9"},
				{"992572089913 ","5O"},
				{"992572089914 ","6B"},
				{"992572089915 ","38"},
				{"992572089916 ","FH"},
				{"992572089917 ","D9"},
				{"992572089918 ","45"},
				{"992572089919 ","BD"},
				{"992572089920 ","77"},
				{"992572089921 ","CH"},
				{"992572089922 ","9E"},
				{"992572089923 ","7C"},
				{"992572089924 ","OC"},
				{"992572089925 ","8F"},
		};
		
		for(int i=0; i<cardId_mac.length; i++){
			String[] line = cardId_mac[i];
			String cardId = line[0].trim();
			String mac = line[1].trim();
			String mac1 = "B0:8E:1A:01:00:" + mac;
			
//			wirelessManager.insertVisCardIdMac("992572089925", "8F");
			insertVisCardIdMac(cardId, mac, mac1);
//			break;
		}
	}
	
	private void insertVisCardIdMac(String cardId, String mac, String mac1){
		Map param = new HashMap();
		param.put("cardId", cardId);
		param.put("mac", mac1);
		param.put("status", 1);
		param.put("name", mac);
//		param.put("bak3", "");
		List<Map> list = new ArrayList();
		list.add(param);
		iBatisDao.batchInsert("TestSql.insertVisCardIdMac", list);
	}
	
	private String getMacByCardId(String cardId){
		String result = null;
		try{
			Map param = new HashMap();
			param.put("cardId", cardId);
			List<Map> list = iBatisDao.findList("TestSql.selectTagMacByIDCradNum", param);
			if(list != null && list.size() != 0){
				result = String.valueOf(list.get(0).get("mac"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@Test
	public void testErrorVisitorList(){
		visitorManager.updateErrorVisitor();
	}
	
	@Test
	public void testVisitorHistoryList(){
		String tagMac = "B0:8E:1A:01:00:1C";
		String beginTime = "2014-01-27 10:00:00";
		String endTime = "2014-01-27 18:00:00";
		Map history = wirelessSqlManager.getVisitHistory(tagMac, beginTime, endTime);
		Iterator it = history.entrySet().iterator();
		String placeIds = "";
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			placeIds = placeIds + entry.getKey() + ",";
		}
		System.out.println("placeIds: "+placeIds);
		
		history = wirelessSqlManager.changePointResult(history);
		it = history.entrySet().iterator();
		String deviceIds = "";
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			deviceIds = deviceIds + entry.getKey() + ",";
		}
		System.out.println("deviceIds: "+deviceIds);
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("d:/test2.csv"));
		String str = null;
		while((str = br.readLine()) != null){
			String[] ss = str.split(",");
			String cardId = ss[0];
			String mac = ss[1];
			System.out.print("{\""+cardId+"\"" + ",");
			System.out.println("\""+mac+"\"},");
		}
		
		br.close();
	}
}
