import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class TestCityCard {
	
	private static final String TRADETYPE = "03";//行业类型
	private static final String OPTYPE = "01";//行业类型
	private static final String COMPANYID = "80000000001";//公司代码
	private static final String STATIONID = "8010001";//网点代码
	private static final String TERMID = "80010001";//终端号
	private static final String OPERATORID ="11111"; //操作员号

	public static void main(String[] args) throws Exception {
//		list.add(makeVo("姚伯祥","zz025","0000997165323988"));
//		String cardNo = "0000997166552179";
		//
		String[] cardNos = {
				"0000970400017988",
		};
		
		for(String s : cardNos){
			execute(s);
		}
	}
	
	public static void execute(String cardNo) throws Exception{
		Date time = new Date();
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
		
		//这里整理解析结果值放在数据库中或是返回给tcard对象
		String paramCard = buildPersonCardParam(now);
		paramCard = paramCard+",AliasCardNo="+cardNo+",QueryTime="+now;
		String cardInfo = queryPersonCardInfo(paramCard);
		System.out.println("cardInfo: " + cardInfo);
		if(cardInfo == null){
			System.out.println("error:"+cardNo);
		}
		Map m = parsePersonCardResult(cardInfo);
		System.out.print(cardNo + ":" + m.get("CustName")+"\t ");
	}
	
	public static Map parsePersonCardResult(String resultXml){//这个地方有可能只返回几个数字
		String status = resultXml.substring(0,4);
		Map map = null;
		if("0000".equals(status)){
			map = new HashMap();
			map.put("errorCode", status);
			String result = resultXml.substring(5,resultXml.length());
			String[] results = result.split(",");
			for (String res : results) {
				String[] result2 = res.split(":");
				String key = result2[0]==""?"":result2[0];
				String value = result2[1]==""?"":result2[1];
				map.put(key, value);
			}
		}
		
		return map;
	}
	
	public static String queryPersonCardInfo(String paramCard) throws Exception{
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setOperationName(new QName("http://govServer.webserviceServer.servers.server.njcc.com/",
				"PubInterFace"));
		call.setTargetEndpointAddress(new URL("http://59.201.2.32:8091/njccweb/GovServer"));
		call.addParameter("pubpara", Constants.XSD_STRING, ParameterMode.IN);
		call.setReturnType(Constants.XSD_STRING);
		String xmlStr = call.invoke(new Object[] {paramCard}).toString();
		return xmlStr;
	}
	
	public static String buildPersonCardParam(String QueryTime) throws NoSuchAlgorithmException {
		String text = TRADETYPE+OPTYPE+"001"+"001"+QueryTime;
		String ValidateCode = Md5(text);//验证码 md5加密
		String buildStr = "tradetype="+TRADETYPE+",optype="+OPTYPE
				+",companyid="+COMPANYID+",stationid="+STATIONID
				+",termid="+TERMID+",operatorid="+OPERATORID+",validatecode="+ValidateCode;
		return buildStr;
	}
	
	public static String Md5(String plainText) throws NoSuchAlgorithmException {
		StringBuffer buf = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes());
		byte b[] = md.digest();
		int i;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
//			System.out.println("result: " + buf.toString());// 32位的加密
//			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
		return buf.toString();
	}

}
