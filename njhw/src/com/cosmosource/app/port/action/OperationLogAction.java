package com.cosmosource.app.port.action;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cosmosource.app.message.vo.User;
import com.cosmosource.app.port.service.OperationLogManager;
import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.DateUtil;
import com.simple.tool.elapse.TimeFormatorZh;
import com.simple.tool.elapse.WatchElapse;
import com.simple.tool.export.ExportExcelExecutor;

@SuppressWarnings({ "serial", "unchecked" })
public class OperationLogAction extends BaseAction{
	
	private static final Log log = LogFactory.getLog(OperationLogAction.class);
	protected static Map<String,String> contentTypes = new HashMap<String,String>();
	private OperationLogManager operationManager;
	private Page<Map> page = new Page<Map>();
	
	private String insertName;
	private String insertDate;
	private String resName;
	private String bmType;
	private String startTime;
	private String endTime;
	private String _chk[];

	@Override
	protected void prepareModel() throws Exception {
	}

	@Override
	public Object getModel() {
		return null;
	}

	/**
	* @Description：初始化页面
	* @Author：hp
	* @Date：2013-6-1
	* @return
	**/
	public String init(){
		return INIT;
	}
	
	public String index(){
		return SUCCESS;
	}
	
	
	 /**
	* @Description 导出EXCEL
	* @Author：pingxianghua
	* @Date 2013-8-14 下午09:01:39 
	* @param
	* @return
	* @throws Exception
	* @version V1.0   
	**/
	public  String exportLog()
	{  
		Map map = new HashMap();
		Map<String,Object> resultMap = null;
		map.put("insertName",getParameter("insertName"));
		map.put("resName", getParameter("resName"));
		map.put("bmType", getParameter("bmType"));
		map.put("startTime", getParameter("startTime"));
		map.put("endTime", getParameter("endTime"));
		
		List<Map<String,Object>> resultList = new ArrayList();
	    List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	    User u = null;
	    try {
			//调用service层
			list = operationManager.findLogList(map);
			if (list != null && list.size() > 0){
			    for(Map map1 : list){
	                resultMap = new HashMap();
	                resultMap.put("RESNAME", (String)map1.get("RESNAME"));
	                resultMap.put("BMDETAIL", (String)map1.get("BMDETAIL"));
	                resultMap.put("INSERTNAME", (String)map1.get("INSERTNAME"));
	                resultMap.put("INSERTDATE", (String)map1.get("INSERTDATE"));
	                resultList.add(resultMap);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	//获取模板路径
       // String  sourcePath = this.getRequest().getSession().getServletContext().getRealPath("\\");
		//String templateFileName =  sourcePath+"excelTemplateFile"+"\\"+"exportRoomInfo.xls";
       String templateFileName ="com/cosmosource/app/port/template/exportLog.xls";
		//判断模板文件是否存在
		//File file = new File(templateFileName);
		//if (file.exists()){
		//	System.out.println("文件存在！");
		//}
        Map<String,Object> context = new HashMap<String,Object>();
        if(list==null){
        	list = null;
        }
        String dateStr = DateUtil.date2Str(DateUtil.getSysDate(), "yyyy-MM-dd");
        context.put("list", resultList);
        context.put("dateStr", dateStr);
        //export excel
		try {
			String filename = "exportLog_"+dateStr;
			 //调用底层方法
			executeExportExcel(filename, templateFileName, context);
		} catch (Exception e) {
			logger.info("generate excel failure!");
			logger.debug(e.getMessage());
			e.printStackTrace();
		
		} 
		
		return  null;
	}
	
	public void executeExportExcel(String fileName, String templateFileName, 
			Map<String, Object> dataMap) throws Exception{
		String xlsFilename = fileName + ".xls";

		this.getResponse().setContentType(this.getContentType(xlsFilename));
		this.getResponse().addHeader("Content-disposition", "attachment;filename="
				+ encodeFileName(xlsFilename));
		OutputStream os = this.getResponse().getOutputStream();
		
		this.executeExportTemplate(templateFileName, dataMap, os);
	}
	
	public String encodeFileName(String fileName) throws Exception{
		return new String(fileName.getBytes("gbk"), "ISO-8859-1");
	}
	
	public String getContentType(String fileExtension) {
        for(String txt : contentTypes.keySet()){
            if(fileExtension.toLowerCase().endsWith(txt)){
                return this.contentTypes.get(txt);
            }
		}
		    
        return "application/octet-stream";
    }
	
	public void executeExportTemplate(String templateFileName, 
			Map<String, Object> dataMap, OutputStream os) throws Exception{
        try {
        	WatchElapse we = new WatchElapse(new TimeFormatorZh());
			we.start();
			ExportExcelExecutor executor = new ExportExcelExecutor();
			executor.evaluate(templateFileName, dataMap, os);
			we.end();
			logger.info("generate excel elapsed time:" + we.getElapsedTime());
		} catch (Exception e) {
			logger.error("导出Excel失败");
			e.printStackTrace();
			throw new RuntimeException("导出Excel失败");
		} finally {
			os.flush();
			os.close();
		}
	}
	
	/**
	* @Description：查询数据结果
	* @Author：hp
	* @Date：2013-6-1
	* @return
	**/
	public String query(){
		Map map = new HashMap();
		//map.put("insertDate", insertDate);
		map.put("insertName", insertName);
		map.put("resName", resName);
		map.put("bmType", bmType);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		getRequest().setAttribute("insertName", insertName);
		getRequest().setAttribute("resName", resName);
		getRequest().setAttribute("bmType", bmType);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		try {
			page = operationManager.findLogs(map,page);
			log.info("操作日志查询成功，查询的个数为："+page.getResult().size());
		} catch (Exception e) {
			addActionMessage("查询数据失败!");
			log.info("操作日志查询数据失败!");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	* @Description：删除操作日志
	* @Author：hp
	* @Date：2013-6-2
	* @return
	**/
	public String delete(){
		try {
			operationManager.deleteLogs(_chk);
			setIsSuc("true");
		} catch (Exception e) {
			setIsSuc("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getInsertName() {
		return insertName;
	}

	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String[] get_chk() {
		return _chk;
	}

	public void set_chk(String[] chk) {
		_chk = chk;
	}

	public Page<Map> getPage() {
		return page;
	}

	public void setPage(Page<Map> page) {
		this.page = page;
	}

	public OperationLogManager getOperationManager() {
		return operationManager;
	}

	public void setOperationManager(OperationLogManager operationManager) {
		this.operationManager = operationManager;
	}
	
	public Map<String,String> getDeviceTypeMap(){
		return com.cosmosource.app.entity.BmMonitor.getDeviceTypeMap();
	}

	public String getBmType() {
		return bmType;
	}

	public void setBmType(String bmType) {
		this.bmType = bmType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
}
