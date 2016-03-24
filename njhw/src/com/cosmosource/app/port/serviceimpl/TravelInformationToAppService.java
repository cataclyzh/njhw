package com.cosmosource.app.port.serviceimpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.cosmosource.app.transfer.serviceimpl.TravelInformationToSystemService;
import com.cosmosource.base.service.BaseManager;

/**
 * @ClassName:TravelInformationToAppService
 * @Description：交通信息的操作（路况数据）
 * @Author：qyq
 * @Date:2013-5-01
 */


public class TravelInformationToAppService  extends BaseManager {
	
	private static final Log log = LogFactory.getLog(TravelInformationToAppService.class);
	private static  TravelInformationToSystemService  travelInformationToSystemService;
	
	/**
	 * 
	 * @Description: 路况数据查询 :请求数据体并且解析
	 * @Author：qyq
	 * @Date：2013-5-01
	 * @return           
	 **/
	
	public String requestData(){
		    String  requestResult = null;	
		     try {
		    	 //请求数据体
		    	 requestResult=	travelInformationToSystemService.sendPost("http://zxnj.jtonline.cn/njits_api/gettraffic.jspx");
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e.getMessage());
			}
			System.err.println(requestResult);
	         return  requestResult;
	 }


	public TravelInformationToSystemService getTravelInformationToSystemService() {
		return travelInformationToSystemService;
	}

	public void setTravelInformationToSystemService(
			TravelInformationToSystemService travelInformationToSystemService) {
		this.travelInformationToSystemService = travelInformationToSystemService;
	}

 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
