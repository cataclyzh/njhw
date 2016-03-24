package com.cosmosource.app.parking_lot.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cosmosource.app.parking_lot.service.ParkingLotManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;

@SuppressWarnings("rawtypes")
public class ParkingMonitorAction extends BaseAction<Object> {
	
	private ParkingLotManager parkingLotManager;
	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录

	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询指定日期停车场车辆信息
	 * @param date
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void parkinglotDailyCounts() throws Exception{
		
		String date = this.getRequest().getParameter("date");
		//日期是否为空，如果为空默认查询当天
		if (null == date || "".equals(date)){
			date = DateUtil.date2Str(DateUtil.getSysDate(), "yyyy/MM/dd");
		}else{
			//格式化参数
		}
		logger.info("查询日期：" + date);
		JSONObject jsonObject = new JSONObject();
		List<Map<String, String>> list = parkingLotManager.queryParkinglotDailyCounts(date);
		
		//查看更多里调用
		this.getSession(false).setAttribute("parkingLotList", list);
		
		logger.info(list.size() + "");
		
		//如果有查询到的车辆信息
		if (list != null && list.size() > 0){
			
			//解析数据，分割成当日总流量，出库流量，入库流量，内部车辆，外部车辆
			//入库流量 = 总流量 - 出库流量
			int totalCount = 0 ; // 进门刷卡操作记录总数
			int totalOut = 0;    //出门刷卡记录总数
			int totalInner = 0;   //内部员工刷卡记录总数
			int realTimeCount = 0;
			boolean flag = true;
			List realTimeCarsList = new ArrayList();
			
			String str = null ; //车辆刷卡操作类型
			String checkResult = null ;
			for (Map<String, String> map : list) {
				
				//取前5个作为实时车辆信息
				realTimeCount++;
				if (flag){
					realTimeCarsList.add(map.get("CAR_NO"));
					if (realTimeCount == 5) {
						flag  = false;
					}
				}
				
				 str = map.get("interface_type".toUpperCase());
				 checkResult = map.get("CARD_TYPE");
				 
				 //进门刷卡操作记录为 getCarType 
				//查询车牌号是否存在为 carIsAailable
				if (str.equalsIgnoreCase("getCarType") || str.equalsIgnoreCase("carIsAailable")) {
					totalCount++;
					/**
					 * 分析内外部车辆,A、B 读环保卡 C、D、E、F、G 刷卡操作结果
					 * A 环保卡，不收费 ，内部员工
					 * B 环保卡，不收费，但只能停在地面停车场，大院职工
					 * C 员工市民卡 ，内部员工，占用内部车位，如环保卡A
					 * D 内部员工，但只能停地面停车场 ，如同环保卡B
					 * E 内部员工卡，但未交费，下班或节假日免费停车，工作时间不允许停车
					 * F 其它社会车辆
					 * G 访客
					 */
					
					//不是访客跟社会车辆 就是内部员工
					if (!"F".equalsIgnoreCase(checkResult) && !"G".equalsIgnoreCase(checkResult)){
						totalInner++;
					}
					
					//出门刷卡操作，判断是否收费
				}else if (str.equalsIgnoreCase("checkFee")) {
					totalOut++;
				}
				
				
			}
			jsonObject.put("totalCount", totalCount);
			jsonObject.put("totalOut", totalOut);
			jsonObject.put("totalInner", totalInner);
			jsonObject.put("realTimeCarsList", realTimeCarsList);
			logger.info(jsonObject.toString());
			
			Struts2Util.renderJson(jsonObject.toString(), "encoding:UTF-8", "no-cache:true");
		}
	}
	
	/**
	 * 查询
	 * 
	 * @param carType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void queryParkingLotInfoByCarType() throws Exception{
		//出入车辆、内外部车辆
		String carType = this.getRequest().getParameter("carType");
		
		List<Map> parkingLotList = (List) this.getSession(false).getAttribute("parkingLotList");
		
		List<Map<String,String>> carTypeList = new ArrayList<Map<String,String>>();
		
		if (null != carType && !"".equals(carType)){
			
			//全部车辆
			if (carType.equals("entire")) {
				Struts2Util.renderJson(parkingLotList, "encoding:UTF-8", "no-cache:true");
				return;
			}
			
			// 入闸车辆
			if (carType.equals("intoParking")) {
				for (Map map : parkingLotList) {
					String str = (String) map.get("interface_type".toUpperCase());
					// 进门刷卡操作记录为 getCarType
					if (str.equalsIgnoreCase("getCarType")) {
						//不在已经出去的车辆里
//						String temp = (String) map.get("CAR_NO");
						carTypeList.add(map);
						// 出门刷卡操作，判断是否收费
					}
				}
			}
			
			//出闸车辆
			if (carType.equals("outOfParking")){
				for (Map map : parkingLotList) {
					String str = (String) map.get("interface_type".toUpperCase());
					// // 出门刷卡的车辆
					if (str.equalsIgnoreCase("checkFee")) {
						carTypeList.add(map);
					}
				}
			}
		}
		
		Struts2Util.renderJson(carTypeList, "encoding:UTF-8", "no-cache:true");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void queryParkingLotInfobyCarNum() {
		
		// 根据车牌号查询
		String carNum = this.getRequest().getParameter("carNum");
		// 出入车辆、内外部车辆
		String carType = this.getRequest().getParameter("carType");

		List<Map<String, String>> parkingLotList = (List) this
				.getSession(false).getAttribute("parkingLotList");
		List<Map<String, String>> carNumList = new ArrayList<Map<String, String>>();

		if (null != carNum && !"".equals(carNum)) {

			// 全部车辆中按车牌号查询
			if (carType.equals("entire")) {

				for (Map map : parkingLotList) {
					String str = (String) map.get("CAR_NO");
					// 进门刷卡操作记录为 getCarType
					if (str.equalsIgnoreCase(carNum)
							|| str.contains(carNum.toUpperCase())) {
						carNumList.add(map);
					}
				}

			} else if (carType.equals("intoParking")) {
				// 入闸车辆中按车牌号查询
				for (Map map : parkingLotList) {
					String str = (String) map.get("interface_type"
							.toUpperCase());
					// 进门刷卡操作记录为 getCarType
					if (str.equalsIgnoreCase("getCarType")) {
						String carNumInMap = (String) map.get("CAR_NO");
						if (carNumInMap.equalsIgnoreCase(carNum)
								|| carNumInMap.contains(carNum.toUpperCase())) {
							carNumList.add(map);
						}
					}
				}
			} else {
				// 出闸车辆中按车牌号查询
				if (carType.equals("outOfParking")) {
					for (Map map : parkingLotList) {
						String str = (String) map.get("interface_type"
								.toUpperCase());
						// 出门刷卡的车辆
						if (str.equalsIgnoreCase("checkFee")) {
							String carNumInMap = (String) map.get("CAR_NO");
							if (carNumInMap.equalsIgnoreCase(carNum)
									|| carNumInMap.contains(carNum
											.toUpperCase())) {
								carNumList.add(map);
							}
						}
					}
				}
			}
			Struts2Util.renderJson(carNumList, "encoding:UTF-8",
					"no-cache:true");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONArray jsonObject = new JSONArray();
		List list = new ArrayList();
		Map map = new HashMap();
		map.put("1", "hello");
		map.put("animal", "panda");
		map.put("ip", "10.254.20.206");
		map.put("hello", null);
		list.add(map);
		jsonObject = JSONArray.fromObject(list);
		System.out.println(jsonObject.toString());
	}

	@Override
	public Object getModel() {
		return null;
	}
	@Override
	protected void prepareModel() throws Exception {
		
	}

	public ParkingLotManager getParkingLotManager() {
		return parkingLotManager;
	}

	public void setParkingLotManager(ParkingLotManager parkingLotManager) {
		this.parkingLotManager = parkingLotManager;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

}
