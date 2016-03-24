package com.cosmosource.app.omc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import javax.enterprise.inject.New;

import org.apache.directory.api.ldap.aci.UserClass.ThisEntry;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.cosmosource.app.omc.model.OMCDeviceInfo;
import com.cosmosource.app.omc.model.OMCRepairInfo;
import com.cosmosource.app.omc.model.OMCSluiceFlowInfo;
import com.cosmosource.app.omc.model.OMCVisitorInfo;
import com.cosmosource.app.omc.service.OMCManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;


@SuppressWarnings("unchecked")
public class OMCReportAction extends BaseAction
{

    private OMCManager omcManager;
    private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页10条记录
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String videoIp;
    
    private String interfaceType; //出入闸车辆查询
    private String carNo; //车牌号
    private String carBelongs;  //内外部车辆
    

    @Override
    protected void prepareModel()
        throws Exception
    {
        
    }

    @Override
    public Object getModel()
    {
        return null;
    }
    
    /**
     * 初始化页面报表数据
     */
    public void initReportData()
    {
        //设备状态
        OMCDeviceInfo omcDeviceInfo = omcManager.queryDeviceInfo();
        
        //报修
        OMCRepairInfo omcRepairInfo = omcManager.queryrRepairInfo();
        
        //闸机流量
        OMCSluiceFlowInfo omcSluiceFlowInfo = omcManager.queryrGateFlow();
        
        //访客
        OMCVisitorInfo omcVisitorInfo = omcManager.queryVisitorInfo();
        
        //访客top
        List<OMCVisitorInfo> visitorList = omcManager.queryTopVisitor(4);
        
        //图片base64压缩
        for (OMCVisitorInfo omcVisitorInfo2 : visitorList)
        {
            
             if(StringUtil.isNotBlank(omcVisitorInfo2.getRes_bak2()))
             {
                 omcVisitorInfo2.setRes_bak2(omcVisitorInfo2.getRes_bak2());
             }
             else
             {
                 omcVisitorInfo2.setRes_bak2("images/nohead.JPG");
             }
        }
        
        //坏设备
        int [] badDeviceCount = new int[4];
        
        //门锁
        badDeviceCount[0] = omcDeviceInfo.getBadLock();
        
        //通信机
        badDeviceCount[1] = omcDeviceInfo.getBadTelegraph();
        
        //闸机
        badDeviceCount[2] =omcDeviceInfo.getBadGate();
        
        //门禁
        badDeviceCount[3] = omcDeviceInfo.getBadDoorcontrol()%2==0?omcDeviceInfo.getBadDoorcontrol():omcDeviceInfo.getBadDoorcontrol()+1;
        
        //正常设备
        int [] normallyDeviceCount = new int[4];
        normallyDeviceCount[0] = omcDeviceInfo.getAllLock() - badDeviceCount[0];
        normallyDeviceCount[1] = omcDeviceInfo.getAllTelegraph() - badDeviceCount[1];
        normallyDeviceCount[2] = omcDeviceInfo.getAllGate() - badDeviceCount[2];
        normallyDeviceCount[3] = omcDeviceInfo.getAllDoorcontrol() - badDeviceCount[3];
        
        JSONObject json = new JSONObject();
        try {
            
            //=====设备状态监控=====
            //受损设备
            json.put("brokeDevice", badDeviceCount);
            
            //正常设备
            json.put("normallyDevice", normallyDeviceCount);
            
            //========报修情况统计=====
            //保修总数
            json.put("allCount", omcRepairInfo.getAllCount());
            //已经解决
            json.put("accomplishCount", omcRepairInfo.getAccomplishCount());
            //已派单
            json.put("dispatchedCount", omcRepairInfo.getDispatchedCount());
            //已回访
            json.put("returnVisitCount", omcRepairInfo.getReturnVisitCount());
            
            //========闸机流量=====
            //今日总量
            json.put("todayTotal", omcSluiceFlowInfo.getTodayTotal());
            //内部人员
            json.put("insiderTotal", omcSluiceFlowInfo.getInsiderTotal());
            //进闸
            json.put("enterTotal", omcSluiceFlowInfo.getEnterTotal());
            //外部人员
            json.put("outsiderTotal", omcSluiceFlowInfo.getOutsiderTotal());
            //出闸总量
            json.put("outTotal", omcSluiceFlowInfo.getOutTotal());
            
            //========访客信息========
            //今天总共上访人数
            json.put("visitorCount", omcVisitorInfo.getVisitorCount());
            //公网预约
            json.put("outerNetVisitorCount", omcVisitorInfo.getOuterNetVisitorCount());
            //主动约访
            json.put("accordVisitorCount", omcVisitorInfo.getAccordVisitorCount());
            //前台登记
            json.put("receptionVisitorCount", omcVisitorInfo.getReceptionVisitorCount());
            //目前访客数
            json.put("nowVisitorCount", omcVisitorInfo.getNowVisitorCount());
            //展示访客
            json.put("visitorList", JSONArray.fromObject(visitorList));
            
        } catch (JSONException e) {
            logger.error(e.getMessage());
        }
        
        Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
        "no-cache:true","content-type:application/json");
    }

    /**
     * 
     * @param result
     * @return
     */
    private List createJsonArr(Map<String,Integer> result)
    {
        Iterator<Entry<String, Integer>> iterator = result.entrySet().iterator();
        
        List returnResult = new LinkedList();
        String [] key = new String[result.size()];
        int [] value = new int[result.size()];
        int i = 0;
        while(iterator.hasNext())
        {
            Entry<String, Integer> entry = iterator.next();
            key[i] = entry.getKey();
            value[i] = entry.getValue();
        }
        
        returnResult.add(key);
        returnResult.add(value);
      return null;   
    }
    
    public static void main(String[] args)
    {
        List<OMCVisitorInfo> resultList = new ArrayList<OMCVisitorInfo>();
        OMCVisitorInfo a = new OMCVisitorInfo();
        a.setCard_id("123123");
        a.setNowVisitorCount(1231);
        a.setOrg_name("123123");
        resultList.add(a);
        resultList.add(a);
        JSONArray cc = JSONArray.fromObject(resultList);
        
        System.out.println(cc);
    }
    
    /**
     * 查询设备状态
     * @return
     */
    public String queryDeveiceToView()
    {
        //获得参数
        String deviceType = Struts2Util.getParameter("deviceType");
        String status = Struts2Util.getParameter("status");
        
        if(StringUtil.isBlank(deviceType) && StringUtil.isBlank(status))
        {
            deviceType = "1";
            status = "1";
        }
       
        //转换为参数
        Map<String,Integer> map = new HashMap<String,Integer>();
        
        map.put("type", Integer.parseInt(deviceType));
        map.put("status", Integer.parseInt(status));
        
        getRequest().setAttribute("deviceType", deviceType);
        getRequest().setAttribute("status", status);
        //1,2  : 3,2
//        if(Integer.parseInt(deviceType)==1&&Integer.parseInt(status)==2
//        ||Integer.parseInt(deviceType)==3&&Integer.parseInt(status)==2	
//        ||Integer.parseInt(deviceType)==4&&Integer.parseInt(status)==2	
//        ){
//        	
//        }else{
        	page = omcManager.queryDeviceInfoToView(page, map);
//        }
        return SUCCESS;
    }
    
    //查询停车场信息，打开弹出窗口
    @SuppressWarnings("rawtypes")
	public String vehicleAccessInfo()
    {
    	Map map = new HashMap();
    	List list = new ArrayList();
    	//有车牌号输入
    	if (null != this.carNo && !"".equals(this.carNo)){
    		
    		map.put("carNo", this.carNo.toUpperCase());
    		
    		if ("outOfParking".equalsIgnoreCase(this.interfaceType)){
    			//查找出闸 checkFee
    			map.put("interface_type", "checkFee");
    			
    		}
    		if ("intoParking".equalsIgnoreCase(this.interfaceType)) {
    			//查找入闸 !checkFee
    			map.put("off_interface_type", "checkFee");
			}
			//如果查询条件是内部、外部车辆信息
			if ("Internal".equalsIgnoreCase(this.carBelongs)){
				// CARD_TYPE 属于 A、B、C、D、E 类型
				list.add("A");
				list.add("B");
				list.add("C");
				list.add("D");
				list.add("E");
				map.put("cardTypes", list);
			}
			if ("External".equalsIgnoreCase(this.carBelongs)){
				// CARD_TYPE 属于 F、G 类型
				list.add("F");
				list.add("G");
				map.put("cardTypes", list);
			}
    		
    	}else{
    		//无车牌号,出闸
    		if ("outOfParking".equalsIgnoreCase(this.interfaceType)){
    			//查找出闸 checkFee
    			map.put("interface_type", "checkFee");
    		}
    		if ("intoParking".equalsIgnoreCase(this.interfaceType)) {
    			//查找入闸 !checkFee
    			map.put("off_interface_type", "checkFee");
			}
			//如果查询条件是内部、外部车辆信息
			if ("Internal".equalsIgnoreCase(this.carBelongs)){
				// CARD_TYPE 属于 A、B、C、D、E 类型
				list.add("A");
				list.add("B");
				list.add("C");
				list.add("D");
				list.add("E");
				map.put("cardTypes", list);
			}
			if ("External".equalsIgnoreCase(this.carBelongs)){
				// CARD_TYPE 属于 F、G 类型
				list.add("F");
				list.add("G");
				map.put("cardTypes", list);
			}
    	}
    	
    	logger.info("车牌号：" + carNo + "	出入闸：" + interfaceType + "	内外部：" + carBelongs);
    	logger.info("查询条件为：" + map.toString());
    	//判断查询条件
    	page = omcManager.queryAllParkinglotInfo(page,map);
    	
    	System.out.println(page);
    	
        return SUCCESS;
    }
    
    
    /**
     * 
     * @param omcManager
     */
    public void setOmcManager(OMCManager omcManager)
    {
        this.omcManager = omcManager;
    }
    
    public String queryDeviceInfo()
    {
        return SUCCESS;
    }
    
    public String toVideoSurveillance(){
    	String videoIpParam = getRequest().getParameter("videoIp");
    	this.videoIp = videoIpParam;
    	return SUCCESS;
    }

	public String getVideoIp() {
		return videoIp;
	}

	public void setVideoIp(String videoIp) {
		this.videoIp = videoIp;
	}

    public Page<HashMap> getPage()
    {
        return page;
    }

    public void setPage(Page<HashMap> page)
    {
        this.page = page;
    }

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarBelongs() {
		return carBelongs;
	}

	public void setCarBelongs(String carBelongs) {
		this.carBelongs = carBelongs;
	}
}
