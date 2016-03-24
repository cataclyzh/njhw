/**
* <p>文件名: TaskInfoModel.java</p>
* <p>:描述：首页任务清单</p>
* <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
* <p>公司: Holytax Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-7 
* @作者： jtm
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
*/
package com.cosmosource.app.common.model;
/**
 * 任务信息model
 * @author jtm
 *
 */
public class TaskInfoModel {
	
	// 任务名称
	private String taskname;
	// 任务信息
	private String taskinfo;
	// 任务链接
	private String tasklink;
	// 执行任务的sql语句,sql语句有格式要求
	private String tasksql;
	//lab名称
	private String labname;
	//是否刷新列表
	private String refresh = "N"; //Y:刷新,N:不刷新

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskinfo() {
		return taskinfo;
	}

	public void setTaskinfo(String taskinfo) {
		this.taskinfo = taskinfo;
	}

	public String getTasklink() {
		return tasklink;
	}

	public void setTasklink(String tasklink) {
		this.tasklink = tasklink;
	}

	public String getTasksql() {
		return tasksql;
	}

	public void setTasksql(String tasksql) {
		this.tasksql = tasksql;
	}

	public String getLabname() {
		return labname;
	}

	public void setLabname(String labname) {
		this.labname = labname;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}

}
