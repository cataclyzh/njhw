package com.cosmosource.app.transfer.serviceimpl;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

import com.cosmosource.base.service.BaseManager;

/**
 * @ClassName:AccessAndGateService
 * @Description：门禁闸机的授权操作
 * @Author：hj
 * @Date:2013-7-30
 */


public class AccessAndGateService extends BaseManager{

	//private static String serviceUrl = "http://10.101.1.37/quartz_njhw/services/accessAndGatesSiteService";
	private String serviceUrl;
	private static String domainName = "http://tempuri.org/";
	private static String method = "ReciveAndOperateExchangeData";
	private LogControlRecord logControlRecord;

	private static final Log log = LogFactory.getLog(AccessAndGateService.class);
	 
	/**
	 * @ClassName:AccessAndGateOperation
	 * @Description：闸机门禁添加、删除和更改授权
	 * @Author：hj
	 * @Date:2013-7-30
	 */
	@SuppressWarnings("unchecked")
	public String accessAndGateOperation(Map userInfo, String opt) {
		serviceUrl = com.cosmosource.base.util.Constants.DBMAP.get("ACCESSGATE_GRANT_URL").toString();
		Service service = new Service();
		String operator = "001";
		String password = "";
		String xml1 = parseToXml(userInfo, opt);
		
		//推送权限操作到地铁口闸机控制器
		//opt: delete, add
		
		
		String result = "";
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(serviceUrl));
			call.addParameter(new QName(domainName, "xml"),
					Constants.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(domainName, "operatorNo"),
					Constants.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(domainName, "password"),
					Constants.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName(domainName, "type"),
					Constants.XSD_STRING, ParameterMode.IN);
			call.setOperationName(new QName(domainName, method));
			call.setReturnType(XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(domainName + method);
			String ret = (String) call.invoke(new Object[] {xml1,operator,password,opt});
			
			//记录闸机返回原信息
			result = ret;
			
			Document document =  DocumentHelper.parseText(ret);
			Element rootElt = document.getRootElement(); // 获取根节点
			Element infoElt = (Element) rootElt.elements().get(0);
			try{
				result = infoElt.elementTextTrim("交换结果信息");
				
				if(opt.equals("modify") && result.equalsIgnoreCase("找不到该卡片信息")){
					return result;
				}
				
				List<Map> whiteMsgList = sqlDao.getSqlMapClientTemplate().queryForList("CommonSQL.getGateWhiteMsg");
				for(Map m : whiteMsgList){
					String msg = m.get("MSG").toString();
					if(msg.equalsIgnoreCase(result)){
						result = "";
						break;
					}
				}
			}catch(Exception e){
				log.error("调用闸机授权接口时,返回信息处理异常", e);
			}
			
			userInfo.put("result", result);
			userInfo.put("opt", opt);
			try{
				logControlRecord.recordAccessLog(userInfo);
			}catch(Exception e){
				log.error("调用闸机授权接口时,记录日志异常", e);
			}
		} catch (Exception e) {
			log.error("调用闸机授权接口出错", e);
		}
		return result;
	}

	/**
	 * @ClassName:parseToXml
	 * @Description：将用户信息转换成xml格式
	 * @Author：hj
	 * @Date:2013-7-30
	 */
	public String parseToXml(Map userInfo, String opt) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?><数据交换任务 id='1'><数据交换任务项 id='2'>");
		if ("add".equals(opt)) {
			sb.append("<交换行为>添加</交换行为>");
		} else if ("delete".equals(opt)) {
			sb.append("<交换行为>删除</交换行为>");
		} else if ("modify".equals(opt)) {
			sb.append("<交换行为>修改</交换行为>");
		}
		sb.append("<交换数据><交换数据项 id='1'>");
		sb.append("<部门名称>").append(userInfo.get("ORGNAME")).append("</部门名称>");
		sb.append("<人员编号>").append(userInfo.get("ACCESSID")).append("</人员编号>");
		if ("add".equals(opt) || "modify".equals(opt)) {
			sb.append("<人员名称>").append(userInfo.get("DISPLAYNAME")).append("</人员名称>");
		}
		if ("add".equals(opt)) {
			sb.append("<性别>").append(userInfo.get("SEX")).append("</性别>");
		}
		sb.append("<身份证号码>").append(userInfo.get("RESIDENTNO")).append("</身份证号码>");
		sb.append("<市民卡号>").append(userInfo.get("CARDID")).append("</市民卡号>");
		
		String rlStr = "19";
		Object rlObj = userInfo.get("RIGHTLEVEL");
		if(rlObj != null){
			if(rlObj.toString().trim().length() != 0){
				rlStr = rlObj.toString().trim();
				
				if(rlStr.equalsIgnoreCase("null")){
					rlStr = "19";
				}
			}
		}
//		sb.append("<权限串>").append(userInfo.get("RIGHTLEVEL")).append("</权限串>");
		sb.append("<权限串>").append(rlStr).append("</权限串>");
		sb.append("</交换数据项></交换数据></数据交换任务项 ></数据交换任务>");
		String xml1 = sb.toString();
		return xml1;
	}

	public void setLogControlRecord(LogControlRecord logControlRecord) {
		this.logControlRecord = logControlRecord;
	}

	public LogControlRecord getLogControlRecord() {
		return logControlRecord;
	}

}
