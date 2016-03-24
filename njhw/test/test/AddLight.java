package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cosmosource.base.dao.ComDaoiBatis;

public class AddLight {
	private static ApplicationContext ctx;
	private static ComDaoiBatis iBatisDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new FileSystemXmlApplicationContext("D:\\workspace3\\njhw2\\src\\com\\cosmosource\\app\\message\\test\\applicationContext1.xml");
		iBatisDao = (ComDaoiBatis) ctx.getBean("iBatisDao");
	}
	
	private void addRoomLight(String[][] lightRoot, String floorName1, String floorName2) throws Exception{
		for(int i=0; i<lightRoot.length; i++){
			String[] ss = lightRoot[i];
			String room = ss[0];
			String obix = ss[1];
			//System.out.println(room + " : " + obix);
			
			HashMap param = new HashMap();
			param.put("name", floorName1 + room);
			List<Map> list = getRoomInfo(param);
			if(list.size() == 1){
				Map m = list.get(0);
				String roomNodeId = String.valueOf(m.get("NODE_ID"));
				System.out.println(room + " : " + roomNodeId + " : " + obix);
				
				Map lightMap = new HashMap();
				lightMap.put("name", floorName2 + room + "房间灯");
				lightMap.put("title", floorName2 + room + "房间灯");
				lightMap.put("obix", obix);
				lightMap.put("roomId", roomNodeId);
				insertLightDate(lightMap);
			}else{
				throw new Exception(room);
			}
		}
	}
	
	private void addFloorLight(String[][] lightFloor, String floorName2, String roomNodeId){
		for(int i=0; i<lightFloor.length; i++){
			String[] ss = lightFloor[i];
			String name = floorName2 + ss[0];
			String obix = ss[1];
			
			Map lightMap = new HashMap();
			
			lightMap.put("name", name);
			lightMap.put("title", name);
			lightMap.put("obix", obix);
			lightMap.put("roomId", roomNodeId);
			insertLightDate(lightMap);
		}
	}
	
	@Test
	public void testAddLight_A15() throws Exception{
		String floor = "15";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层A区";
		
		String[][] lightRoot = new String[][]{
				{"90", "1682"},
				{"92", "1683"},
				{"91", "1684"},
				{"93", "1685"},
				{"95", "1687"},
				{"94", "1688"},
				{"96", "1690"},
				{"98", "1691"},
				{"97", "1692"},
				{"99", "1693"},
				{"100", "1694"},
				{"102", "1695"},
				{"104", "1696"},
				{"101", "1697"},
				{"103", "1698"},
				{"105", "1699"},
				{"106", "1700"},
				{"107", "1701"},
				{"108", "1702"},
				{"109", "1703"},
				{"03", "1704"},
				{"04", "1705"},
				{"05", "1706"},
				{"06", "1707"},
				{"07", "1709"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "1686"},
				{"过道灯", "1689"},
				{"电梯间灯", "1708"},
				{"信息发布灯", "1711"},
				{"热水炉灯", "1712"},
		};
		addFloorLight(lightFloor, floorName2, "6007");
	}
	
	@Test
	public void testAddLight_A16() throws Exception{
		String floor = "16";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层A区";
		
		String[][] lightRoot = new String[][]{
				{"96", "1943"},
				{"97", "1944"},
				{"99", "1945"},
				{"98", "1947"},
				{"100", "1948"},
				{"101", "1950"},
				{"103", "1951"},
				{"102", "1952"},
				{"104", "1953"},
				{"105", "1954"},
				{"106", "1955"},
				{"107", "1956"},
				{"109", "1957"},
				{"108", "1958"},
				{"110", "1959"},
				{"111", "1960"},
				{"112", "1961"},
				{"113", "1962"},
				{"114", "1963"},
				{"115", "1964"},
				{"02", "1965"},
				{"03", "1966"},
				{"04", "1967"},
				{"05", "1968"},
				{"06", "1969"},
				{"07", "1970"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "1949"},
				{"过道灯", "1946"},
				{"电梯间灯", "1973"},
				{"信息发布灯", "1971"},
				{"热水炉灯", "1972"},
		};
		addFloorLight(lightFloor, floorName2, "6008");
	}
	
	@Test
	public void testAddLight_A17() throws Exception{
		String floor = "17";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层A区";
		
		String[][] lightRoot = new String[][]{
				{"96","2103"},
				{"95","2104"},
				{"97","2105"},
				{"98","2106"},
				{"99","2107"},
				{"106","2108"},
				{"101","2109"},
				{"102","2110"},
				{"07","2111"},
				{"103","2112"},
				{"104","2113"},
				{"105","2114"},
				{"113","2115"},
				{"107","2116"},
				{"108","2117"},
				{"109","2118"},
				{"110","2119"},
				{"111","2120"},
				{"112","2121"},
				{"02","2123"},
				{"01","2124"},
				{"06","2126"},
				{"03","2127"},
				{"04","2128"},
				{"05","2129"},
				{"100","2130"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2125"},
				{"过道灯", "2122"},
				{"电梯间灯", "2102"},
//				{"信息发布灯", "1971"},
//				{"热水炉灯", "1972"},
		};
		addFloorLight(lightFloor, floorName2, "6009");
	}
	
	@Test
	public void testAddLight_A18() throws Exception{
		String floor = "18";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层A区";
		
		String[][] lightRoot = new String[][]{
				{"109","2261"},
				{"96","2265"},
				{"97","2267"},
				{"98","2268"},
				{"99","2269"},
				{"100","2270"},
				{"101","2271"},
				{"102","2272"},
				{"103","2273"},
				{"104","2274"},
				{"105","2275"},
				{"106","2276"},
				{"107","2277"},
				{"108","2278"},
				{"110","2279"},
				{"111","2280"},
				{"112","2281"},
				{"113","2282"},
				{"114","2283"},
				{"115","2284"},
				{"01","2286"},
				{"02","2287"},
				{"03","2289"},
				{"04","2290"},
				{"05","2291"},
				{"06","2292"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2288"},
				{"过道灯", "2285"},
				{"电梯间灯", "2266"},
//				{"信息发布灯", "1971"},
//				{"热水炉灯", "1972"},
		};
		addFloorLight(lightFloor, floorName2, "6010");
	}
	
	@Test
	public void testAddLight_B15() throws Exception{
		String floor = "15";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层B区";
		
		String[][] lightRoot = new String[][]{
//				{"08","1721"},
//				{"09","1722"},
				{"10","1723"},
				{"11","1724"},
				{"12","1725"},
				{"13","1726"},
				{"14","1727"},
				{"15","1728"},
				{"16","1729"},
				{"17","1730"},
				{"18","1731"},
				{"19","1732"},
				{"20","1733"},
				{"21","1734"},
				{"22","1735"},
				{"23","1736"},
				{"24","1737"},
				{"25","1738"},
				{"26","1739"},
				{"27","1740"},
				{"28","1741"},
				{"29","1742"},
				{"30","1743"},
				{"31","1744"},
				{"32","1745"},
				{"33","1746"},
				{"34","1747"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "1749"},
				{"过道灯", "1750"},
				{"电梯间灯", "1748"},
				{"信息发布灯", "1752"},
				{"热水炉灯", "1751"},
				{"房间强制控制", "1758"},
				{"公共区强制控制", "1759"}
		};
		addFloorLight(lightFloor, floorName2, "6007");
	}
	
	@Test
	public void testAddLight_B16() throws Exception{
		String floor = "16";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层B区";
		
		String[][] lightRoot = new String[][]{
				{"08","1981"},
				{"09","1982"},
				{"10","1983"},
				{"11","1984"},
				{"12","1985"},
				{"13","1986"},
				{"14","1987"},
				{"15","1988"},
				{"16","1989"},
				{"17","1990"},
				{"18","1991"},
				{"19","1992"},
				{"20","1993"},
				{"21","1994"},
				{"22","1995"},
				{"23","1996"},
				{"24","1997"},
				{"25","1998"},
				{"26","1999"},
				{"27","2000"},
				{"28","2001"},
				{"29","2002"},
				{"30","2003"},
				{"31","2004"},
				{"32","2005"},
				{"33","2006"},
				{"34","2007"},
				{"35","2008"},
				{"36","2009"},
				{"37","2010"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2012"},
				{"过道灯", "2013"},
				{"电梯间灯", "2011"},
				{"信息发布灯", "2015"},
				{"热水炉灯", "2014"},
				{"房间强制控制", "2018"},
				{"公共区强制控制", "2019"}
		};
		addFloorLight(lightFloor, floorName2, "6008");
	}
	
	@Test
	public void testAddLight_B17() throws Exception{
		String floor = "17";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层B区";
		
		String[][] lightRoot = new String[][]{
				{"08","2141"},
				{"09","2142"},
				{"10","2143"},
				{"11","2144"},
				{"12","2145"},
				{"13","2146"},
				{"14","2147"},
				{"15","2148"},
				{"16","2149"},
				{"17","2150"},
				{"18","2151"},
				{"19","2152"},
				{"20","2153"},
				{"21","2154"},
				{"22","2155"},
				{"23","2156"},
				{"24","2157"},
				{"25","2158"},
				{"26","2159"},
				{"27","2160"},
				{"28","2161"},
				{"29","2162"},
				{"30","2163"},
				{"31","2164"},
				{"32","2165"},
				{"33","2166"},
				{"34","2167"},
				{"35","2168"},
				{"36","2169"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2171"},
				{"过道灯", "2172"},
				{"电梯间灯", "2170"},
				{"信息发布灯", "2174"},
				{"热水炉灯", "2173"},
				{"房间强制控制", "2178"},
				{"公共区强制控制", "2179"}
		};
		addFloorLight(lightFloor, floorName2, "6009");
	}
	
	@Test
	public void testAddLight_B18() throws Exception{
		String floor = "18";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层B区";
		
		String[][] lightRoot = new String[][]{
				{"08","2301"},
				{"09","2302"},
				{"10","2303"},
				{"11","2304"},
				{"12","2305"},
				{"13","2306"},
				{"14","2307"},
				{"15","2308"},
				{"16","2309"},
				{"17","2310"},
				{"18","2311"},
				{"19","2312"},
				{"20","2313"},
				{"21","2314"},
				{"22","2315"},
				{"23","2316"},
				{"24","2317"},
				{"25","2318"},
				{"26","2319"},
				{"27","2320"},
				{"28","2321"},
				{"29","2322"},
				{"30","2323"},
				{"31","2324"},
				{"32","2325"},
				{"33","2326"},
				{"34","2327"},
				{"35","2328"},
				{"36","2329"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2331"},
				{"过道灯", "2332"},
				{"电梯间灯", "2330"},
				{"信息发布灯", "2334"},
				{"热水炉灯", "2333"},
				{"房间强制控制", "2338"},
				{"公共区强制控制", "2339"}
		};
		addFloorLight(lightFloor, floorName2, "6010");
	}
	
	@Test
	public void testAddLight_C15() throws Exception{
		String floor = "15";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层C区";
		
		String[][] lightRoot = new String[][]{
				{"60","1764"},
				{"59","1765"},
				{"58","1766"},
				{"57","1767"},
				{"56","1768"},
				{"55","1769"},
				{"53","1770"},
				{"52","1771"},
				{"51","1772"},
				{"50","1773"},
				{"47","1774"},
				{"45","1775"},
				{"49","1776"},
				{"48","1777"},
				{"43","1778"},
				{"41","1779"},
				{"39","1780"},
				{"46","1781"},
				{"44","1782"},
				{"42","1783"},
				{"40","1784"},
				{"37","1787"},
				{"35","1788"},
				{"38","1789"},
				{"36","1790"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "1785"},
				{"过道灯", "1786"},
				{"电梯间灯", "1762"},
//				{"信息发布灯", "1752"},
//				{"热水炉灯", "1751"},
//				{"房间强制控制", "1758"},
//				{"公共区强制控制", "1759"}
		};
		addFloorLight(lightFloor, floorName2, "6007");
	}
	
	@Test
	public void testAddLight_C16() throws Exception{
		String floor = "16";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层C区";
		
		String[][] lightRoot = new String[][]{
				{"67","2024"},
				{"65","2025"},
				{"64","2026"},
				{"63","2027"},
				{"62","2028"},
				{"61","2029"},
				{"60","2030"},
				{"58","2031"},
				{"57","2032"},
				{"56","2033"},
				{"54","2034"},
				{"55","2035"},
				{"53","2036"},
				{"52","2037"},
				{"50","2038"},
				{"51","2039"},
				{"49","2040"},
				{"48","2041"},
				{"46","2042"},
				{"47","2043"},
				{"45","2044"},
				{"44","2045"},
				{"42","2046"},
				{"43","2047"},
				{"41","2050"},
				{"40","2051"},
				{"39","2052"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2048"},
				{"过道灯", "2049"},
				{"电梯间灯", "2022"},
				{"信息发布灯", "2021"},
				{"热水炉灯", "2023"},
//				{"房间强制控制", "2018"},
//				{"公共区强制控制", "2019"}
		};
		addFloorLight(lightFloor, floorName2, "6008");
	}
	
	@Test
	public void testAddLight_C17() throws Exception{
		String floor = "17";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层C区";
		
		String[][] lightRoot = new String[][]{
				{"37","2184"},
				{"40","2185"},
				{"39","2186"},
				{"43","2187"},
				{"42","2188"},
				{"44","2189"},
				{"41","2190"},
				{"45","2191"},
				{"48","2192"},
				{"47","2193"},
				{"49","2194"},
				{"46","2195"},
				{"50","2196"},
				{"52","2197"},
				{"54","2198"},
				{"53","2199"},
				{"55","2200"},
				{"56","2201"},
				{"60","2202"},
				{"57","2203"},
				{"51","2204"},
				{"59","2207"},
				{"61","2208"},
				{"62","2209"},
				{"63","2210"},
				{"64","2211"},
				{"65","2212"},
				{"66","2213"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2205"},
				{"过道灯", "2206"},
				{"电梯间灯", "2181"},
				{"信息发布灯", "2182"},
				{"热水炉灯", "2183"},
//				{"房间强制控制", "2178"},
//				{"公共区强制控制", "2179"}
		};
		addFloorLight(lightFloor, floorName2, "6009");
	}
	
	@Test
	public void testAddLight_C18() throws Exception{
		String floor = "18";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层C区";
		
		String[][] lightRoot = new String[][]{
				{"37","2344"},
				{"40","2345"},
				{"39","2346"},
				{"53","2347"},
				{"46","2348"},
				{"38","2349"},
				{"43","2350"},
				{"47","2351"},
				{"44","2352"},
				{"48","2353"},
				{"41","2354"},
				{"50","2355"},
				{"49","2356"},
				{"51","2357"},
				{"52","2358"},
				{"54","2359"},
				{"45","2360"},
				{"55","2361"},
				{"56","2362"},
				{"57","2363"},
				{"59","2364"},
				{"60","2365"},
				{"61","2366"},
				{"62","2367"},
				{"63","2370"},
				{"64","2371"},
				{"65","2372"},
				{"66","2373"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2341"},
				{"过道灯", "2369"},
				{"电梯间灯", "2368"},
				{"信息发布灯", "2342"},
				{"热水炉灯", "2343"},
//				{"房间强制控制", "2338"},
//				{"公共区强制控制", "2339"}
		};
		addFloorLight(lightFloor, floorName2, "6010");
	}
	
	@Test
	public void testAddLight_D15() throws Exception{
		String floor = "15";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层D区";
		
		String[][] lightRoot = new String[][]{
				{"63","1801"},
				{"64","1802"},
				{"65","1803"},
				{"66","1804"},
				{"68","1805"},
				{"69","1806"},
				{"70","1807"},
				{"71","1808"},
				{"72","1809"},
				{"73","1810"},
				{"74","1811"},
				{"75","1812"},
				{"76","1813"},
				{"77","1814"},
				{"78","1815"},
				{"79","1816"},
				{"80","1817"},
				{"81","1818"},
				{"82","1819"},
				{"83","1820"},
				{"84","1821"},
				{"85","1822"},
				{"86","1823"},
				{"87","1824"},
				{"88","1825"},
				{"89","1826"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "1828"},
				{"过道灯", "1829"},
				{"电梯间灯", "1827"},
				{"信息发布灯", "1831"},
				{"热水炉灯", "1830"},
//				{"房间强制控制", "1758"},
//				{"公共区强制控制", "1759"}
		};
		addFloorLight(lightFloor, floorName2, "6007");
	}
	
	@Test
	public void testAddLight_D16() throws Exception{
		String floor = "16";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层D区";
		
		String[][] lightRoot = new String[][]{
				{"68","2061"},
				{"69","2062"},
				{"70","2063"},
				{"71","2064"},
				{"72","2065"},
				{"73","2066"},
				{"74","2067"},
				{"75","2068"},
				{"76","2069"},
				{"77","2070"},
				{"78","2071"},
				{"79","2072"},
				{"80","2073"},
				{"81","2074"},
				{"82","2075"},
				{"83","2076"},
				{"84","2077"},
				{"85","2078"},
				{"86","2079"},
				{"87","2080"},
				{"88","2081"},
				{"89","2082"},
				{"90","2083"},
				{"91","2084"},
				{"92","2085"},
				{"93","2086"},
				{"94","2087"},
				{"95","2088"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2090"},
				{"过道灯", "2091"},
				{"电梯间灯", "2089"},
				{"信息发布灯", "2093"},
				{"热水炉灯", "2092"},
//				{"房间强制控制", "2018"},
//				{"公共区强制控制", "2019"}
		};
		addFloorLight(lightFloor, floorName2, "6008");
	}
	
	@Test
	public void testAddLight_D17() throws Exception{
		String floor = "17";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层D区";
		
		String[][] lightRoot = new String[][]{
				{"67","2221"},
				{"68","2222"},
				{"69","2223"},
				{"70","2224"},
				{"71","2225"},
				{"72","2226"},
				{"73","2227"},
				{"74","2228"},
				{"75","2229"},
				{"76","2230"},
				{"77","2231"},
				{"78","2232"},
				{"79","2233"},
				{"80","2234"},
				{"81","2235"},
				{"82","2236"},
				{"83","2237"},
				{"84","2238"},
				{"85","2239"},
				{"86","2240"},
				{"87","2241"},
				{"88","2242"},
				{"89","2243"},
				{"90","2244"},
				{"91","2245"},
				{"92","2246"},
				{"93","2247"},
				{"94","2248"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2250"},
				{"过道灯", "2251"},
				{"电梯间灯", "2249"},
				{"信息发布灯", "2253"},
				{"热水炉灯", "2252"},
//				{"房间强制控制", "2178"},
//				{"公共区强制控制", "2179"}
		};
		addFloorLight(lightFloor, floorName2, "6009");
	}
	
	@Test
	public void testAddLight_D18() throws Exception{
		String floor = "18";
		String floorName1 = floor + "F";
		String floorName2 = floor + "层D区";
		
		String[][] lightRoot = new String[][]{
				{"67","2381"},
				{"68","2382"},
				{"69","2383"},
				{"70","2384"},
				{"71","2385"},
				{"72","2386"},
				{"73","2387"},
				{"74","2388"},
				{"75","2389"},
				{"76","2390"},
				{"77","2391"},
				{"78","2392"},
				{"79","2393"},
				{"80","2394"},
				{"81","2395"},
				{"82","2396"},
				{"83","2397"},
				{"84","2398"},
				{"85","2399"},
				{"86","2400"},
				{"87","2401"},
				{"88","2402"},
				{"89","2403"},
				{"90","2404"},
				{"91","2405"},
				{"92","2406"},
				{"93","2407"},
				{"94","2408"},
		};
		addRoomLight(lightRoot, floorName1, floorName2);
		
		//添加非房间的灯
		String[][] lightFloor = new String[][]{
				{"门厅灯", "2410"},
				{"过道灯", "2411"},
				{"电梯间灯", "2409"},
				{"信息发布灯", "2413"},
				{"热水炉灯", "2412"},
//				{"房间强制控制", "2338"},
//				{"公共区强制控制", "2339"}
		};
		addFloorLight(lightFloor, floorName2, "6010");
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("d:/test1.csv"));
		String str = null;
		while((str = br.readLine()) != null){
			String[] ss = str.split(",");
			String room = ss[1];
			if(room.length() == 1){
				room = "0" + room;
			}
			room = room.substring(3);
			System.out.print("{\""+room+"\"" + ",");
			String obix = ss[0].substring(1);
			System.out.println("\""+obix+"\"},");
		}
		
		br.close();
	}
	
	@Test
	public void changeOBIXPoint(){
		List<Map> allLight = getAllLightInfo();
		System.out.println("allLight Num: " + allLight.size());
		
		for(int i=0; i< allLight.size(); i++){
			Map m = allLight.get(i);
			String nodeId = String.valueOf(m.get("NODE_ID"));
			String keyword = String.valueOf(m.get("KEYWORD"));
			int obix = Integer.parseInt(keyword);
			keyword = String.valueOf(obix);
			System.out.println(nodeId + " : " + keyword);
			
			Map param = new HashMap();
			param.put("nodeId", nodeId);
			param.put("keyword", keyword);
			updateOneLightInfo(param);
		}
	}
	
	private void insertLightDate(Map param){
		List<Map> list = new ArrayList<Map>();
		list.add(param);
		iBatisDao.batchInsert("TestSQL.insertLight", list);
	}
	
	private List<Map> getAllLightInfo(){
		return iBatisDao.findList("TestSQL.getAllLightInfo", new HashMap());
//		return this.findListBySql("PersonnelSQL.getAllLightInfo", new HashMap());
	}
	
	private void updateOneLightInfo(Map param){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(param);
		//updateBatchBySql("PersonnelSQL.updateOneLightInfo", list);
		iBatisDao.batchUpdate("TestSQL.updateOneLightInfo", list);
	}
	
	private List<Map> getRoomInfo(Map param){
		return iBatisDao.findList("TestSQL.getObjNodeIdByRoomName", param);
	}
}
