package com.cosmosource.app.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cosmosource.app.common.model.Video;
import com.cosmosource.app.common.service.OperationManager;
import com.cosmosource.app.entity.CsRepairFault;
import com.cosmosource.app.entity.EiEnergy;
import com.cosmosource.app.entity.Objtank;
import com.cosmosource.app.entity.Org;
import com.cosmosource.app.port.model.SiteStatus;
import com.cosmosource.app.port.model.WisdomNj;
import com.cosmosource.app.property.service.CallCenterManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.service.AuthorityManager;

@SuppressWarnings("serial")
public class OperationAction extends BaseAction<Object>{
	public static final String PATH_VIDEO_XML = "dataconfig/video.xml";
	private OperationManager operationManager;
	private CallCenterManager callCenterManager;
	private List<CsRepairFault> csRepairFaults= new ArrayList<CsRepairFault>();
	 private AuthorityManager authorityManager;
 	
	public AuthorityManager getAuthorityManager() {
		return authorityManager;
	}
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}
		
	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public Object getModel() {
		return null;
	}
	
	
	
	public String init(){
		//首页 用户名与登录名
		String loginname = (String)this.getSession().getAttribute(Constants.LOGIN_NAME);
		String userName = (String)this.getSession().getAttribute(Constants.USER_NAME);		
		String username = userName + " (" + loginname + ") "; 
		getRequest().setAttribute("username", username);
		getRequest().setAttribute("orgList", this.operationManager.findByHQL("select t from Org t where t.levelNum = ?", Org.LEVELNUM_2));
		return SUCCESS;
	}
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 物业呼叫中心
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String propertyMonitor(){
		JSONObject json = new JSONObject();
		try {
			csRepairFaults= callCenterManager.loadCsRepairFaultsTop5();
			if (csRepairFaults != null &&  csRepairFaults.size() > 0) {
				json.put("list", JsonUtil.listToJsonb(csRepairFaults));
				json.put("state", 1);
			} else {
				json.put("state", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
		
	}
	
	
	/**
	 * 
	* @title: energyMonitor 
	* @description: 能耗监控
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String energyMonitor(){
		JSONObject json = new JSONObject();
		List<EiEnergy> lists = operationManager.loadEnergyMessage();
		if(lists!=null && lists.size()>0){
			json.put("state", 1);
			for(EiEnergy e: lists){
				if(e.getEeType().equals(EiEnergy.EETYPE_W)){
					json.put("water", e.getEeNum());
				}else if(e.getEeType().equals(EiEnergy.EETYPE_D)){
					json.put("electric", e.getEeNum());
				}else{
					json.put("air",e.getEeNum());
				}
			}
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 环境监控
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String enviromentMonitor(){
		JSONObject json = new JSONObject();
		Collection<WisdomNj> collection = operationManager.loadEnviromentMessage();
		
		if(collection!=null){
			for(WisdomNj w: collection){
				json.put("temperature", w.getTemperature());
				json.put("co2",w.getCo2());
				json.put("humidity",w.getRainfall());
			}
			json.put("", "");
			
		}
		json.put("state", 1);
		json.put("temperature", "23");
		json.put("co2", "15");
		json.put("humidity", "50");
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 摄像头监控
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String videoMonitor(){
		List<Video> list = new ArrayList<Video>();
		JSONObject json = new JSONObject();
		try {
			SAXReader reader = new SAXReader();
			String shortcutPath = this.getRequest().getSession().getServletContext().getRealPath("/")+PATH_VIDEO_XML;
			Document document = reader.read(new FileInputStream(new File(shortcutPath)));
			Element rootElt = document.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("Node"); // 获取根节点下的子节点
			while (iter.hasNext()) {
				 Element recordEle = (Element) iter.next();
				 String videoId = recordEle.elementTextTrim("VideoId"); 
				 String link = recordEle.elementTextTrim("Link"); 
				 String videoName = recordEle.elementTextTrim("VideoName"); 
				 Video video = new Video();
				 video.setVideoId(videoId);
				 video.setVideoName(videoName);
				 video.setLink(link);
				 list.add(video);
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	//	String msg = operationManager.loadVideoMessage();
		if(list!=null){
			json.put("state", 1);
			json.put("list", JsonUtil.listToJsonb(list));
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 餐厅加载
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String dinlingMonitor(){
		JSONObject json = new JSONObject();
		Collection<WisdomNj> collection = operationManager.loadEnviromentMessage();
		
		json.put("state", 1);
		json.put("freeMeals", "23");//空余餐位
		json.put("pflow", "15");//人员流量
		json.put("humidity", "50");
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		
		return SUCCESS;
	}
	
	
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 文印加载
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String printMonitor(){
		return SUCCESS;
	}
	
	
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 天气加载
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String weatherMonitor(){
		JSONObject json = new JSONObject();
		String msg = operationManager.loadWeatherMessage();
		if(StringUtil.isNotEmpty(msg)){
			json.put("", msg);
		}
		json.put("state", 1);
		json.put("msg", "南京多云 8——23℃");
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 停车场信息加载
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String parkingMonitor(){
		JSONObject json = new JSONObject();
		String msg = operationManager.loadParkingMessage();
		if(StringUtil.isNotEmpty(msg)){
			json.put("", msg);
		}
		json.put("state", 1);
		json.put("num1", "28");
		json.put("num2", "29");
		json.put("roadE", "奥体东路");
		json.put("roadW", "奥体西路");
		json.put("roadE_info", "畅通");
		json.put("roadW_info", "拥堵");
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 异常事件处理
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String uneventMonitor(){
		return SUCCESS;
	}

	/**
	 * 
	* @title: propertyMonitor 
	* @description: 闸机口加载
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String gatesMonitor(){
		List<Objtank> objtanks = operationManager.loadGatesMessage();
		JSONObject json = new JSONObject();
		if(objtanks != null){
			json.put("state", 1);
			json.put("list", JsonUtil.listToJsonb(objtanks));
		}
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 闸机口加载
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String gatesMonitorByGatesId(){
		String gatesId = Struts2Util.getParameter("gatesId");
		SiteStatus siteStatus = operationManager.loadGatesMessageByGateId(gatesId);
		JSONObject json = new JSONObject();
		if(siteStatus == null){
			json.put("state", 0);
		}
		json.put("state", 1);
		json.put("name", "模拟闸机");
		json.put("work", "正常");
		json.put("num", "200人");
		Struts2Util.renderJson(json.toString(), "encoding:UTF-8", "no-cache:true");
		return null;
	}
	
	/**
	 * 
	* @title: propertyMonitor 
	* @description: 三维地图加载
	* @author cjw
	* @return
	* @date 2013-4-3 下午10:47:09     
	* @throws
	 */
	public String swMonitor(){
		return SUCCESS;
	}
	public OperationManager getOperationManager() {
		return operationManager;
	}

	public void setOperationManager(OperationManager operationManager) {
		this.operationManager = operationManager;
	}

	public CallCenterManager getCallCenterManager() {
		return callCenterManager;
	}

	public void setCallCenterManager(CallCenterManager callCenterManager) {
		this.callCenterManager = callCenterManager;
	}

	public List<CsRepairFault> getCsRepairFaults() {
		return csRepairFaults;
	}

	public void setCsRepairFaults(List<CsRepairFault> csRepairFaults) {
		this.csRepairFaults = csRepairFaults;
	}

	
}
