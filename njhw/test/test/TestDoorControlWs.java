package test;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class TestDoorControlWs {

	public static void main(String[] args) throws Exception {
		TestDoorControlWs tdas = new TestDoorControlWs();
		tdas.testUploadRealTimeRecord();
	}

	public void testUploadRealTimeRecord() throws Exception{
		String method = "controlDoor_wx";
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?><root><param>");
		sb.append("<personId>").append("1").append("</personId>");
		sb.append("<roomName>").append("D411").append("</roomName>");
		sb.append("</param></root>");
		
		System.out.println(sb.toString());
		
//		String nameSpace = "http://ws.fnjhw.org/";
		String serviceUrl = "http://10.101.1.33/services/iPPhoneSiteServices";
		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(serviceUrl));
//			call.addParameter(new QName(nameSpace, "record"), Constants.XSD_STRING, ParameterMode.IN);
			call.addParameter("xml", Constants.XSD_STRING, ParameterMode.IN);
//			call.setOperationName(new QName(nameSpace, method));
			call.setOperationName(new QName("http://ws.fnjhw.org/", method));
			call.setReturnType(XMLType.XSD_STRING);
//			call.setUseSOAPAction(true);
//			call.setSOAPActionURI(nameSpace + method);

			String xmlParam = sb.toString();
			System.out.println("xmlParam: " + xmlParam);
			
			String ret = (String) call.invoke(new Object[] {xmlParam });
			System.out.println("result: " + ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
