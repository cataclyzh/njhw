package com.cosmosource.app.transfer.serviceimpl;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Schema;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.Iterator;
import com.cosmosource.app.port.model.WirelessLocation;

/**
 * @ClassName:WirelessLocationToSystemService
 * @Description：无线定位的操作
 * @Author：qyq
 * @Date:2013-4-10
 */


public class WirelessLocationToSystemService {

	/**
	 * @ClassName:WirelessLocationToSystemService
	 * @Description：获得单个标签的当前详细信息
	 * @Author：qyq
	 * @Date:2013-4-11
	 */

	@SuppressWarnings("unchecked")
	public Map getWirelessLocation(String macId) {
		Service service = new Service();
		String urlServices = com.cosmosource.base.util.Constants.DBMAP.get("11");
		String methodLocationServices = com.cosmosource.base.util.Constants.DBMAP.get("22");
		String nameSpace = "http://tempuri.org/";

		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(urlServices));
			call.setOperationName(new QName(nameSpace,"methodLocationServices"));
			call.addParameter("macId", Constants.XSD_STRING, ParameterMode.IN);// 设置接口参数
			call.setReturnType(Constants.XSD_STRING);// 返回值类型
			call.setUseSOAPAction(true);// 利用soapAction
			call.setSOAPActionURI("http://tempuri.org/GetOrgInfo");	// 这个如果对方有设定的话，就设置这个参数。还有下面的uri
			call.setOperationName(methodLocationServices);// 设置要访问的方法名
			Map xmlStr = (Map) call.invoke(new Object[] { macId });// 执行方法
			System.out.println(xmlStr);// 打印返回值
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	/**
	 * @ClassName:WirelessLocationToSystemService
	 * @Description：根据条件获得多个或所有标签的当前详细信息
	 * @Author：qyq
	 * @Date:2013-4-11
	 */

	@SuppressWarnings("unchecked")
	public List<Map> getWirelessLocationList(WirelessLocation wirelessLocation) {
		Service service = new Service();
		String urlServices = com.cosmosource.base.util.Constants.DBMAP.get("11");
		String methodServices = com.cosmosource.base.util.Constants.DBMAP.get("22");
		String nameSpace = "http://tempuri.org/";
		String tagNameKeyword = wirelessLocation.getTagNameKeyword();
		int[] tagGroupIdArray = wirelessLocation.getTagGroupIdArray();
		int mapId = wirelessLocation.getMapId();
		Boolean locatingOnly = wirelessLocation.getLocatingOnly();
		Boolean absentOnly = wirelessLocation.getAbsentOnly();
		Boolean lowerBatteryOnly = wirelessLocation.getLowerBatteryOnly();
		Boolean areaEventOnly = wirelessLocation.getAreaEventOnly();
		Boolean buttonPressedOnly = wirelessLocation.getButtonPressedOnly();
		Boolean wristletBrokenOnly = wirelessLocation.getWristletBrokenOnly();
		String sortField = wirelessLocation.getSortField();
		int sortDirection = wirelessLocation.getSortDirection();
		int fetchCount = wirelessLocation.getFetchCount();
		int skipOffset = wirelessLocation.getSkipOffset();
		try {

			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(urlServices));
			call.setOperationName(new QName(nameSpace, "methodServices"));
			// 设置参数
			call.addParameter(tagNameKeyword, Constants.XSD_STRING,	ParameterMode.IN);
			call.addParameter(String.valueOf(tagGroupIdArray),Constants.SOAP_ARRAY, ParameterMode.IN);
			call.addParameter(String.valueOf(mapId), Constants.XSD_INT,	ParameterMode.IN);
			call.addParameter(String.valueOf(locatingOnly),Constants.XSD_BOOLEAN, ParameterMode.IN);
			call.addParameter(String.valueOf(absentOnly),Constants.XSD_BOOLEAN, ParameterMode.IN);
			call.addParameter(String.valueOf(lowerBatteryOnly),	Constants.XSD_BOOLEAN, ParameterMode.IN);
			call.addParameter(String.valueOf(areaEventOnly),Constants.XSD_BOOLEAN, ParameterMode.IN);
			call.addParameter(String.valueOf(buttonPressedOnly),Constants.XSD_BOOLEAN, ParameterMode.IN);
			call.addParameter(String.valueOf(wristletBrokenOnly),Constants.XSD_BOOLEAN, ParameterMode.IN);
			call.addParameter(sortField, Constants.XSD_STRING,ParameterMode.IN);
			call.addParameter(String.valueOf(sortDirection), Constants.XSD_INT,	ParameterMode.IN);
			call.addParameter(String.valueOf(fetchCount), Constants.XSD_INT,ParameterMode.IN);
			call.addParameter(String.valueOf(skipOffset), Constants.XSD_INT,ParameterMode.IN);
			call.setReturnType(Constants.XSD_SCHEMA);// 返回值类型
			call.setOperationName(methodServices);// 设置要访问的方法名
			@SuppressWarnings("unused")
			Map res = (Map) call.invoke(new Object[] { tagNameKeyword,
					tagGroupIdArray, mapId, locatingOnly, absentOnly,
					lowerBatteryOnly, areaEventOnly, buttonPressedOnly,
					wristletBrokenOnly, sortField, sortDirection, fetchCount,
					skipOffset });

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
	
	
	
	
	/*** 
	* @Description：schema对象写入到一个xml文件中
	* @Author：qyq
	* @Date：2013-4-11
	* @param res  
	
	**/
	@SuppressWarnings("unchecked")
	public  void invoke( Map res)
	{
		Schema schema = (Schema) res;
		MessageElement[] msgele = schema.get_any();
		@SuppressWarnings("unused")
		List FOCElementHead = msgele[0].getChildren();// 消息头,DataSet对象
		List FOCElementBody = msgele[1].getChildren();// 消息体信息,DataSet对象

		if (FOCElementBody.size() <= 0) {
			System.out.println("无消息体");
		}

		String nn = FOCElementBody.get(0).toString();// 消息体的字符串形式
		try {
			this.saveXMLString(nn, "c://test.xml");// 保存为XML形式
			this.parserXml("c://test.xml"); 
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
	}
	
	/*** 
	* @Description：把读出来的xml数据写成文件
	* @Author：qyq
	* @Date：2013-4-11
	* @param XMLString
	* @param fileName
	* @throws IOException
	
	**/

	private void saveXMLString(String XMLString, String fileName)
			throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		if (file.canWrite()) {
			FileWriter fileOut = new FileWriter(file);
			fileOut.write(XMLString);
			fileOut.close();
		}
	}

	/*** 
	* @Description：解析xml文件
	* @Author：qyq
	* @Date：2013-4-11
	* @param fileName
	* @throws MalformedURLException
	
	**/
	@SuppressWarnings("unchecked")
	private void parserXml(String fileName) throws MalformedURLException {

		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element node1 = document.getRootElement();
			List<String> param = new  ArrayList<String>();

			// 遍历xml文件
			for (Iterator i = node1.elementIterator(); i.hasNext();) {
				Element node2 = (Element) i.next();

				Iterator node = node2.elementIterator("no");
				Element ele = (Element) node.next();

				for (Iterator j = node2.elementIterator(); j.hasNext();) {
					Element node3 = (Element) j.next();

					System.out.println(node3.getName() + ":" + node3.getText());

					param.add(node3.getText());// 把元素装入一个数组param

				}
			}

			// 在这里 将param数组里的元素封装到entity里

			/*
			 * 添加封装的代码
			 */

		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
