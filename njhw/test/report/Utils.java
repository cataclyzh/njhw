package report;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Utils {
	private static Logger log = Logger.getLogger(Utils.class.getName());
	
	private static final SimpleDateFormat FORMAT_1 = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private static final SimpleDateFormat FORMAT_2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	private static final SimpleDateFormat FORMAT_3 = new SimpleDateFormat(
			"HH:mm");

	public static String getStringReqParameter(HttpServletRequest request,
			String name) {
		String result = null;
		try {
			result = String.valueOf(request.getParameter(name));
		} catch (Exception e) {
			log.error("get request parameter[" + name + "] fail.");
		}
		return result;
	}

	public static String getTimeString(Date date) {
		return FORMAT_2.format(date);
	}
	public static String getTimeString1(Date date) {
		return FORMAT_3.format(date);
	}
	
	public static Date parseTime(String time) throws ParseException{
		return FORMAT_2.parse(time);
	}
	
	public static String getDateString(Date date) {
		return FORMAT_1.format(date);
	}
	
//	public static String getTimeString(Date date) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		return sdf.format(date);
//	}

	public static void responseText(HttpServletResponse response, String text)
			throws IOException {
		response.setContentType("text/html");
		int len = text.getBytes("UTF-8").length;
		log.debug("response length: " + len);
		response.setHeader("Content-Length", String.valueOf(len));
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(text);
		out.flush();
		out.close();
	}

	public static String getMD5(String text) {
		byte[] source = text.getBytes();
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static boolean hasUser_No(String user_no) {
		return true;
	}

	public static long getLongParameter(HttpServletRequest request, String name) {
		long result = 0;
		String str = request.getParameter(name);
		if (str != null && str.trim().length() != 0) {
			try {
				result = Long.parseLong(str);
			} catch (NumberFormatException e) {
				log.error("parameter[" + name + "] error.");
			}
		} else {
			log.error("parameter[" + name + "] empty.");
		}
		return result;
	}
	
	public static String getStringFromResultSet(ResultSet rs, String name){
		String result = "";
		
		try{
			result = rs.getString(name);
			if(result == null || result.trim().length() == 0){
				result = "";
			}
		}catch(Exception e){
			log.error(name, e);
		}
		
		return result;
	}
	
	public static String[] getMonthRestDays(String date) {
		String[] dateInfo = date.split("-");
		Calendar calendar = new GregorianCalendar(Integer.valueOf(dateInfo[0]),
				Integer.valueOf(dateInfo[1]) - 1, Integer.valueOf(dateInfo[2]));
		System.out.println(FORMAT_1.format(calendar.getTime()));
		int rest = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
				- Integer.valueOf(dateInfo[2]);
		String[] days = new String[rest + 1];
		days[0] = date;
		for (int i = 0; i < rest; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			days[i + 1] = FORMAT_1.format(calendar.getTime());
			System.out.println(days[i + 1]);
		}
		return days;
	}

	public static String changeMapToJSONString(Map map) {
		net.sf.json.JSONObject obj = null;
		try {
			obj = new net.sf.json.JSONObject();
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				obj.put(entry.getKey(), entry.getValue());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return String.valueOf(obj);
	}

	public static String changeListToJSONString(List<Map> list) {
		JSONArray arr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Map m = list.get(i);
			arr.add(JSONObject.fromObject(m));
		}
		return String.valueOf(arr);
	}

	public static void responseTextFromMap(HttpServletResponse response, Map map)
			throws IOException {
		responseText(response, changeMapToJSONString(map));
	}

	public static void responseTextFromList(HttpServletResponse response,
			List<Map> list) throws IOException {
		responseText(response, changeListToJSONString(list));
	}
	
	public static boolean isIdCorrect(String id){
		if(id.length() == 15 ){
			Pattern idNumPattern = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");  
	        Matcher idNumMatcher = idNumPattern.matcher(id);
	        if(idNumMatcher.matches()){
	        	return true;
	        }
		}else if(id.length() == 18){
			Pattern idNumPattern = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$");  
	        Matcher idNumMatcher = idNumPattern.matcher(id);
	        if(idNumMatcher.matches()){
	        	return true;
	        }
		}
		return false;
	}
}
