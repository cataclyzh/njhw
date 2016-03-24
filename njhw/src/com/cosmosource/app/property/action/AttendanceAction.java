package com.cosmosource.app.property.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.cosmosource.app.entity.GrAttendance;
import com.cosmosource.app.property.service.AttendanceManager;
import com.cosmosource.app.utils.DateUtils;
import com.cosmosource.base.action.ExportAction;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.DateUtil;
/**
 * @description: 考勤管理
 * @author cehngyun
 * @date 2013-07-11
 */
@SuppressWarnings("unchecked")
public class AttendanceAction extends ExportAction<Object> {

	// 定义分页变量
	private Page<HashMap> page = new Page<HashMap>(Constants.PAGESIZE);// 默认每页20条记录
	// 定义注入对象
	private AttendanceManager attendanceManager;
	private GrAttendance view_attendance = new GrAttendance();
	Long userid;
	Long orgId;
	String orgName;
	String outName;
	private String list_attendanceOrgName;
	private String list_attendanceUserName;
	private String list_attendanceScheduleAdminName;
	private String list_attendanceInTime;
	private String list_attendanceOutTime;
	

	private String list_attendanceSta;
	
	private InputStream inputStream;
	private String downloadFileName ;
	/**
	 * 
	* @title: exportAttendance
	* @description: 考勤清单导出
	* @author chengyun
	* @return
	* @date 2013/8/1
	* @throwshttp:
	 */
	
	@SuppressWarnings("unchecked")
	public  String exportAttendance()
	{  
		HashMap map = new HashMap();
		map.put("attendanceInTime", getRequest().getParameter("export_attendanceInTime"));
		map.put("attendanceOutTime", getRequest().getParameter("export_attendanceOutTime"));

	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    //调用service层
        list= attendanceManager.exportAttendance(map);
        //获取模板路径
       // String  sourcePath = this.getRequest().getSession().getServletContext().getRealPath("\\");
		//String templateFileName =  sourcePath+"excelTemplateFile"+"\\"+"exportRoomInfo.xls";
       String templateFileName ="com/cosmosource/app/property/template/exportAttendance.xls";
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
        context.put("list", list);
        context.put("dateStr", dateStr);
        //export excel
		try {
			String fileName = "考勤导出_" + dateStr + ".xls";
			this.downloadFileName = encodeFileName(fileName); 
//					new String(fileName.getBytes(),"ISO8859-1");
			
			//存放路径
			String path = ServletActionContext.getServletContext().getRealPath(File.separator + "exportExcelTemp");
			//本地的文件名
			String localFileName = UUID.randomUUID().toString().replaceAll("\\-", "") + ".xls";
			//先写入本地文件，然后获取输入流
			writeExceltoLocal(templateFileName,context,path,localFileName);
			
			this.inputStream = new FileInputStream(new File(path,localFileName));
//			this.inputStream = exportExcel(templateFileName,context);
//			this.inputStream = new ByteArrayInputStream("HelloWorld!!".getBytes());
			 //调用底层方法
//			executeExportExcel(fileName, templateFileName, context);
			
		} catch (Exception e) {
			logger.info("generate excel failure!");
			logger.debug(e.getMessage());
			e.printStackTrace();
		
		} 
		
		return  SUCCESS;
	}
	

	public InputStream getInputStream() {
		return inputStream;
	}


	public String getDownloadFileName() {
		return downloadFileName;
	}


	public String exportAttendancePrepare(){
		return SUCCESS;
	}
	
	public String showAttendancesList(){
		
		HashMap map =new HashMap();
		
		
		if(this.list_attendanceOrgName!=null){
			map.put("attendanceOrgName", this.list_attendanceOrgName.trim());
		}
		if(this.list_attendanceUserName!=null){
			map.put("attendanceUserName", this.list_attendanceUserName.trim());
		}
		if(this.list_attendanceScheduleAdminName!=null){
			map.put("attendanceScheduleAdminName", this.list_attendanceScheduleAdminName.trim());
		}
		
		if(list_attendanceInTime == null || list_attendanceInTime.trim().length() == 0){
			map.put("attendanceInTime", DateUtils.getFirstDayOfMonth());
			list_attendanceInTime = DateUtils.getFirstDayOfMonth();
		}else{
			map.put("attendanceInTime", this.list_attendanceInTime);
		}
		
		if(list_attendanceOutTime == null || list_attendanceOutTime.trim().length() == 0){
			map.put("attendanceOutTime", DateUtil.getStrMonthLastDay());
			list_attendanceOutTime = DateUtil.getStrMonthLastDay();
		}else{
			map.put("attendanceOutTime", this.list_attendanceOutTime);
		}
		
		String[] attendanceSta = getRequest().getParameterValues(
		"list_attendanceSta");
		if (attendanceSta != null) {
			if (!attendanceSta[0].equalsIgnoreCase("all"))
				map.put("attendanceSta", attendanceSta[0]);
			else
				map.put("attendanceSta", null);
		} else
			map.put("attendanceSta", null);
		
		
		page = attendanceManager.queryAllAttendances(page, map);
		return SUCCESS;
	}
	
	public String queryAttendanceById(){
		Long attendanceId = Long.parseLong(getRequest().getParameter("attendanceId"));
		view_attendance = (GrAttendance)attendanceManager.findAttendanceById(attendanceId);
		
		if(view_attendance!=null){
			getRequest().setAttribute("view_attendance", view_attendance);
			return SUCCESS;
		}else {
			getRequest().setAttribute("view_attendance", null);
			return ERROR;
		}

	}
	
	public String getList_attendanceOrgName() {
		return list_attendanceOrgName;
	}

	public void setList_attendanceOrgName(String listAttendanceOrgName) {
		list_attendanceOrgName = listAttendanceOrgName;
	}

	public String getList_attendanceUserName() {
		return list_attendanceUserName;
	}

	public void setList_attendanceUserName(String listAttendanceUserName) {
		list_attendanceUserName = listAttendanceUserName;
	}

	public String getList_attendanceScheduleAdminName() {
		return list_attendanceScheduleAdminName;
	}

	public void setList_attendanceScheduleAdminName(
			String listAttendanceScheduleAdminName) {
		list_attendanceScheduleAdminName = listAttendanceScheduleAdminName;
	}

	public String getList_attendanceInTime() {
		return list_attendanceInTime;
	}

	public void setList_attendanceInTime(String listAttendanceInTime) {
		list_attendanceInTime = listAttendanceInTime;
	}

	public String getList_attendanceOutTime() {
		return list_attendanceOutTime;
	}

	public void setList_attendanceOutTime(String listAttendanceOutTime) {
		list_attendanceOutTime = listAttendanceOutTime;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public AttendanceManager getAttendanceManager() {
		return attendanceManager;
	}

	public void setAttendanceManager(AttendanceManager attendanceManager) {
		this.attendanceManager = attendanceManager;
	}

	public GrAttendance getView_attendance() {
		return view_attendance;
	}

	public void setView_attendance(GrAttendance viewAttendance) {
		view_attendance = viewAttendance;
	}
	public String getList_attendanceSta() {
		return list_attendanceSta;
	}

	public void setList_attendanceSta(String listAttendanceSta) {
		list_attendanceSta = listAttendanceSta;
	}


	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}


	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
