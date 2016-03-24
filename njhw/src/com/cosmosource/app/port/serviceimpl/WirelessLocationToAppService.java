package com.cosmosource.app.port.serviceimpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.cosmosource.app.transfer.serviceimpl.WirelessLocationToSystemService;
import com.cosmosource.base.service.BaseManager;
import com.cosmosource.app.entity.ExtInRfid;
import com.cosmosource.app.port.model.WirelessLocation;

/**
 * @ClassName:WirelessLocationToAppService
 * @Description：无线定位的操作
 * @Author：qyq
 * @Date:2013-4-10
 */


public class WirelessLocationToAppService extends BaseManager {

	private static final Log log = LogFactory.getLog(WirelessLocationToAppService.class);
	private WirelessLocationToSystemService wirelessLocationToSystemService;

	/**
	 * 
	 * @Description: 获得单个标签的当前详细信息
	 * @Author：qyq
	 * @Date：2013-4-10
	 * @param tagMac  标签Mac地址
	 * @return           
	 **/

	@SuppressWarnings("unchecked")
	public ExtInRfid SelectTagStatus(String tagMac) {
		ExtInRfid rfid = new ExtInRfid();
		try {
			Map result = null;
			if (StringUtils.isBlank(tagMac)) {
				log.info("标签的Mac地址不能为空！");
				return null;
			}

			result = wirelessLocationToSystemService.getWirelessLocation(tagMac);
			rfid =parseMapToEntity(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return rfid;
	}

	/**
	 * @Description：根据条件获得多个或所有标签的当前详细信息
	 * @Author：qyq
	 * @Date：2013-4-10
	 * @return
	 **/

	@SuppressWarnings("unchecked")
	public List<ExtInRfid> SelectTagStatusList(WirelessLocation wirelessLocation) {
		List<ExtInRfid> rfids = new ArrayList<ExtInRfid>();
		try {
			List<Map> result = null;
			result = wirelessLocationToSystemService.getWirelessLocationList(wirelessLocation);
			rfids = parseMapToEntity(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		
		return rfids;

	}

	
	/*** 
	* @Description：插入数据库操作
	* @Author：qyq
	* @Date：2013-4-11
	* @param map
	
	**/
	@SuppressWarnings("unchecked")
	public ExtInRfid parseMapToEntity(Map map){
		//解析map插入数据库
		ExtInRfid rfid = new ExtInRfid();
		try {
			rfid.setTagid(map.get("eirId").toString());
			rfid.setTagid(map.get("eventId").toString());
			rfid.setTagid(map.get("tagid").toString());
			rfid.setTagid(map.get("mac").toString());
			rfid.setTagid(map.get("status").toString());
			rfid.setTagid(map.get("x").toString());
			rfid.setTagid(map.get("y").toString());
			rfid.setTagid(map.get("z").toString());
			rfid.setTagid(map.get("uptime").toString());
			rfid.setTagid(map.get("exp1").toString());
			rfid.setTagid(map.get("exp2").toString());
			rfid.setTagid(map.get("exp3").toString());
			rfid.setTagid(map.get("exp4").toString());
			rfid.setTagid(map.get("exp5").toString());
			rfid.setTagid(map.get("exp6").toString());
			rfid.setTagid(map.get("exp7").toString());
			rfid.setTagid(map.get("exp8").toString());
			rfid.setTagid(map.get("exp9").toString());
			rfid.setTagid(map.get("insertId").toString());
			rfid.setTagid(map.get("insertDate").toString());
			dao.save(rfid);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return rfid;
	}
	
	/*** 
	* @Description：将List<Map>插入数据库操作
	* @Author：qyq
	* @Date：2013-4-11
	* @param map
	
	**/
	
	@SuppressWarnings("unchecked")
	public List<ExtInRfid>   parseMapToEntity(List<Map> map){
		List<ExtInRfid> rfids = new ArrayList<ExtInRfid>();
		try {
			for (Map map2 : map) {
				ExtInRfid rfid = parseMapToEntity(map2);
				rfids.add(rfid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		
		return rfids;
	}
	

	public WirelessLocationToSystemService getWirelessLocationToSystemService() {
		return wirelessLocationToSystemService;
	}

	public void setWirelessLocationToSystemService(
			WirelessLocationToSystemService wirelessLocationToSystemService) {
		this.wirelessLocationToSystemService = wirelessLocationToSystemService;
	}

}
