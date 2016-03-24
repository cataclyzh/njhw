package com.cosmosource.app.dining.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.app.dining.service.NewTheMenuManager;
import com.cosmosource.app.entity.FsDishesIssue;
import com.cosmosource.app.integrateservice.service.IntegrateManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.ConvertUtils;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.EncodeUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.base.util.Struts2Util;
import com.opensymphony.webwork.dispatcher.json.JSONException;
import com.opensymphony.webwork.dispatcher.json.JSONObject;



@SuppressWarnings("serial")
public class NewTheMenuAction extends BaseAction<FsDishesIssue> {

	private FsDishesIssue fsDishesIssue = new FsDishesIssue();
	private NewTheMenuManager newTheMenuManager = new NewTheMenuManager();
	
	private IntegrateManager integrateManager;
	
	public IntegrateManager getIntegrateManager() {
		return integrateManager;
	}
	public void setIntegrateManager(IntegrateManager integrateManager) {
		this.integrateManager = integrateManager;
	}
	
	@Override
	protected void prepareModel() throws Exception {
	}
	@Override
	public FsDishesIssue getModel() {
		return fsDishesIssue;
	}
	
	/**
	 * 
	* @title: initNewTheMenu 
	* @description: 初始化录入页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String initNewTheMenu(){
		try {
			List<HashMap> list = new ArrayList();
			HashMap localMap =  (HashMap) ConvertUtils.pojoToMap(fsDishesIssue);
			list = newTheMenuManager.queryNewTheMenu(list,localMap);
			ArrayList<ArrayList<HashMap>> typeList = new ArrayList<ArrayList<HashMap>>(21);
			for (int i = 0 ;i<21;i++) {
				typeList.add(new ArrayList<HashMap>());
			}
			for(int i=0;i<list.size();i++){
				HashMap map = list.get(i);
				if (map.get("FDI_TYPE").equals("1") && map.get("FDI_FLAG").equals("1")){
					
					typeList.get(0).add(map);
					
				}
				if (map.get("FDI_TYPE").equals("1") && map.get("FDI_FLAG").equals("2")){
					typeList.get(1).add(map);
				}
				if (map.get("FDI_TYPE").equals("1") && map.get("FDI_FLAG").equals("3")){
					typeList.get(2).add(map);
				}
				if (map.get("FDI_TYPE").equals("2") && map.get("FDI_FLAG").equals("1")){
					typeList.get(3).add(map);
				}
				if (map.get("FDI_TYPE").equals("2") && map.get("FDI_FLAG").equals("2")){
					typeList.get(4).add(map);
				}
				if (map.get("FDI_TYPE").equals("2") && map.get("FDI_FLAG").equals("3")){
					typeList.get(5).add(map);
				}
				if (map.get("FDI_TYPE").equals("3") && map.get("FDI_FLAG").equals("1")){
					typeList.get(6).add(map);
				}
				if (map.get("FDI_TYPE").equals("3") && map.get("FDI_FLAG").equals("2")){
					typeList.get(7).add(map);
				}
				if (map.get("FDI_TYPE").equals("3") && map.get("FDI_FLAG").equals("3")){
					typeList.get(8).add(map);
				}
				if (map.get("FDI_TYPE").equals("4") && map.get("FDI_FLAG").equals("1")){
					typeList.get(9).add(map);
				}
				if (map.get("FDI_TYPE").equals("4") && map.get("FDI_FLAG").equals("2")){
					typeList.get(10).add(map);
				}
				if (map.get("FDI_TYPE").equals("4") && map.get("FDI_FLAG").equals("3")){
					typeList.get(11).add(map);
				}
				if (map.get("FDI_TYPE").equals("5") && map.get("FDI_FLAG").equals("1")){
					typeList.get(12).add(map);
				}
				if (map.get("FDI_TYPE").equals("5") && map.get("FDI_FLAG").equals("2")){
					typeList.get(13).add(map);
				}
				if (map.get("FDI_TYPE").equals("5") && map.get("FDI_FLAG").equals("3")){
					typeList.get(14).add(map);
				}
				if (map.get("FDI_TYPE").equals("6") && map.get("FDI_FLAG").equals("1")){
					typeList.get(15).add(map);
				}
				if (map.get("FDI_TYPE").equals("6") && map.get("FDI_FLAG").equals("2")){
					typeList.get(16).add(map);
				}
				if (map.get("FDI_TYPE").equals("6") && map.get("FDI_FLAG").equals("3")){
					typeList.get(17).add(map);
				}
				if (map.get("FDI_TYPE").equals("7") && map.get("FDI_FLAG").equals("1")){
					typeList.get(18).add(map);
				}
				if (map.get("FDI_TYPE").equals("7") && map.get("FDI_FLAG").equals("2")){
					typeList.get(19).add(map);
				}
				if (map.get("FDI_TYPE").equals("7") && map.get("FDI_FLAG").equals("3")){
					typeList.get(20).add(map);
				}
			}
		
			this.getRequest().setAttribute("typeList", typeList);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	* @title: initNewWeekMenu 
	* @description: 初始化录入页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	public String initNewWeekMenu(){
		String fdiType = week();
		this.getRequest().setAttribute("fdiType", fdiType);
		DateUtil.getSysCalendar().get(Calendar.HOUR_OF_DAY);
        //  SimpleDateFormat formatter = new SimpleDateFormat("yyyy月MM日dd");
        if ("1".equals(fdiType))
		{
        	setDate(0);
		}
        else if ("2".equals(fdiType))
        {
        	setDate(-1);
		}
        else if ("3".equals(fdiType))
        {
        	setDate(-2);
		} 
        else if ("4".equals(fdiType))
        {
        	setDate(-3);
		} 
        else if ("5".equals(fdiType))
        {
        	setDate(-4);
		} 
        else if ("6".equals(fdiType))
        {
        	setDate(-5);
		} 
        else if ("7".equals(fdiType))
        {
        	setDate(-6);
		} 
        
        int hour =  DateUtil.getSysCalendar().get(Calendar.HOUR_OF_DAY);
       
        String fsd = FsDishesIssue.FSD_FLAG_AM;
        this.getRequest().setAttribute("FSD_FLAG", 1);
        
	    if (hour>=9 &&  hour<13)
		{
	    	fsd = FsDishesIssue.FSD_FLAG_NOON;
	    	this.getRequest().setAttribute("FSD_FLAG", 2);
		}
	    else if(hour >=13 && hour<=23)
	    {
	    	fsd = FsDishesIssue.FSD_FLAG_PM;
	    	this.getRequest().setAttribute("FSD_FLAG", 3);
	    }

		return SUCCESS;
	}
	
	/**
	 * 获取当天的是周几
	 * 
	 * @title: week
	 * @description: TODO
	 * @author gxh
	 * @return
	 * @date 2013-5-4 下午01:50:59
	 * @throws
	 */
	private String week() {
		String fdiType1 = "";
		String fdiType = null;
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		fdiType1 = dayNames[dayOfWeek - 1];
		if (fdiType1.equals("星期五")) {
			fdiType = "5";
		} else if (fdiType1.equals("星期四")) {
			fdiType = "4";
		} else if (fdiType1.equals("星期三")) {
			fdiType = "3";
		} else if (fdiType1.equals("星期二")) {
			fdiType = "2";
		} else if (fdiType1.equals("星期一")) {
			fdiType = "1";
		} else if (fdiType1.equals("星期六")) {
			fdiType = "6";
		} else if (fdiType1.equals("星期日")) {
			fdiType = "7";
		}
		String time = DateUtil.getDateTime("yyyy年MM月dd日");
		this.getRequest().setAttribute("time","今天是 "+time+ " "+fdiType1);
		return fdiType;
	}
	
	private  void setDate(int fdi)
	{   
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果      
        calendar.add(calendar.DATE,fdi);
        for(int i = 1; i <15;i++)
        {   
        	this.getRequest().setAttribute("date"+i, formatter.format(calendar.getTime()));
        	calendar.add(calendar.DATE,1);//2
        }
     }
	
	/**
	* 读取照片
	* @title: showPic 
	* @description: TODO
	* @author gxh
	* @param userPhoto
	* @return
	* @date 2013-4-17 上午11:07:05     
	*/
	private String showPic(String userPhoto){
		String contents= "" ;
		try {
			File f=new File(userPhoto);
			if(f.exists()) {
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(userPhoto));
		        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		        byte[] temp = new byte[1024*1024];
		        int size = 0;         
		        while ((size = in.read(temp)) != -1) {
		            out.write(temp, 0, size);         
		        }
		        in.close();
		        byte[] content = out.toByteArray();
		        contents = EncodeUtil.base64Encode(content);
		        contents = "data:image/x-icon;base64,"+contents;
			} else {
				// System.out.println("文件不存在"); 去掉ER B 代码
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}
	
	public String ajaxInitNewWeekMenu(){
		String fdiType = getRequest().getParameter("id");
		String fsdFlag = getRequest().getParameter("FSD_FLAG");
		if (StringUtil.isEmpty(fsdFlag))
		{
			fsdFlag = FsDishesIssue.FSD_FLAG_AM;
		}
		this.getRequest().setAttribute("fdiType", fdiType);
		this.getRequest().setAttribute("fsdFlag", fsdFlag);
		try {
			List<List> list = null;
			list = integrateManager.queryMenus(fdiType, fsdFlag);	
			String noFoodPic = getRequest().getContextPath()+"/app/integrateservice/images/food_no.jpg";
			if (list != null) {
				for (List<Map> l : list) {
					for (Map m : l) {
						if(null != m.get("FD_PHOTO1") && !StringUtil.isEmpty(showPic(m.get("FD_PHOTO1").toString()))){
							m.put("FD_PHOTO1", showPic(m.get("FD_PHOTO1").toString()));
						}else{
							m.put("FD_PHOTO1", noFoodPic);
						}
					}
				}
				this.getRequest().setAttribute("list", list);
			}
			else
			{
			 
				if (FsDishesIssue.FSD_FLAG_AM.equals(fsdFlag))
				{
					this.getRequest().setAttribute("msg", "早餐尚未发布"); 
				}
				else if (FsDishesIssue.FSD_FLAG_NOON.equals(fsdFlag))
				{
					this.getRequest().setAttribute("msg", "午餐尚未发布"); 
				}
				else if (FsDishesIssue.FSD_FLAG_PM.equals(fsdFlag))
				{
					this.getRequest().setAttribute("msg", "晚餐尚未发布"); 
				}
				else
				{
					this.getRequest().setAttribute("msg", "食堂尚未发布饭菜"); 
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	/**
	 * 
	* @title: addOrUpdateNewTheMenu 
	* @description: 进入编辑页面
	* @author sqs
	* @return
	* @date 2013-3-19 下午09:12:39     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String addOrUpdateNewTheMenu(){
			try {
				HashMap localMap =  (HashMap) ConvertUtils.pojoToMap(fsDishesIssue);
				List list = new ArrayList();
				list = newTheMenuManager.selectNewTheMenu(list,localMap);
				getRequest().setAttribute("list", list);
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
	}
	
	/**
	 * 
	 * @title: theNewMenuSave 
	 * @description: 保存发布的菜单 -发布按钮
	 * @author sqs
	 * @return
	 * @throws Exception
	 * @date 2013-3-19 下午06:19:21     
	 * @throws
	 */
	public String theNewMenuSave() throws Exception {
		String ids = getParameter("cfdiId");	//菜ID 数组
		String fdiFlag = getParameter("fdiFlagFlag");	//三餐
		String fdiType = getParameter("fdiTypeType");	//星期
		String fabufdiIdact = getParameter("fabufdiIdact");	//发布表ID
		String pageNo = null;
		try {
			newTheMenuManager.saveTheNewMenu(fsDishesIssue,ids,fdiFlag,fdiType,fabufdiIdact);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		getRequest().setAttribute("pageNo", pageNo);
		return null;
	}
	/**
	 * 菜单上移
	* @title: upMenu 
	* @description: TODO
	* @author gxh
	* @return
	* @date 2013-5-28 下午06:45:10     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public String upMenu(){
		JSONObject json = new JSONObject();
		String fdiId=Struts2Util.getParameter("fdiId");
	   int suNum =newTheMenuManager.updateUpMenu(Long.parseLong(fdiId));
	  
			try {
				 if(suNum == 0){//成功
					 FsDishesIssue exp = newTheMenuManager.fsDishesIssueByid(Long.parseLong(fdiId));
						List<Map> vmList = newTheMenuManager.ajaxqueryMenu(exp.getFdiType(), exp.getFdiFlag());
						if(vmList!=null){
							
							Struts2Util.renderJson(vmList, "encoding:UTF-8",
							"no-cache:true");
						}
		    	 }else if(suNum == 2){
		    		 json.put("status", "false");
		    		 Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
						"no-cache:true");
		    		 
		    	 }
				
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		
		
			return null;
	}

	
	

	
	
	public String downMenu(){
		JSONObject json = new JSONObject();
		String fdiId=Struts2Util.getParameter("fdiId");
	   int suNum =newTheMenuManager.updateDownMenu(Long.parseLong(fdiId));
	  
			try {
				 if(suNum == 0){//成功
					 FsDishesIssue exp = newTheMenuManager.fsDishesIssueByid(Long.parseLong(fdiId));
						List<Map> vmList = newTheMenuManager.ajaxqueryMenu(exp.getFdiType(), exp.getFdiFlag());
						if(vmList!=null){
							
							Struts2Util.renderJson(vmList, "encoding:UTF-8",
							"no-cache:true");
						}
		    	 }else if(suNum == 2){
		    		 json.put("status", "false");
		    		 Struts2Util.renderJson(json.toString(), "encoding:UTF-8",
						"no-cache:true");
		    		 
		    	 }
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		
		
			return null;
	}

	
	private List<Map> ajaxqueryMenu(String fdiType,String fdiFlag){
		
		List<Map> vmList = newTheMenuManager.ajaxqueryMenu(fdiType, fdiFlag);
		if(vmList!=null){
			
			return vmList;
		}else {
			return null;
		}
		
		
	}
	
	public FsDishesIssue getFsDishesIssue() {
		return fsDishesIssue;
	}
	public void setFsDishesIssue(FsDishesIssue fsDishesIssue) {
		this.fsDishesIssue = fsDishesIssue;
	}
	public NewTheMenuManager getNewTheMenuManager() {
		return newTheMenuManager;
	}
	public void setNewTheMenuManager(NewTheMenuManager newTheMenuManager) {
		this.newTheMenuManager = newTheMenuManager;
	}
	
}
