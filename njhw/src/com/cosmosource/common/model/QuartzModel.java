/**
* <p>文件名: QuartzModel.java</p>
* <p>描述：任务管理通用ModelBean</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-11-15 上午10:27:11 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.model;


/**
 * @类描述: 任务管理通用ModelBean
 * @作者： WXJ
 */
public class QuartzModel implements java.io.Serializable {

	private static final long serialVersionUID = 580467009856961442L;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//任务名称
	private String triggerName;
	//重复次数
	private Long repeatCount;
	//间隔时间
	private Long repeatInterval;
	//jobID
	private String jobName;
	//任务分组
	private String triggerGroup;
	//任务类型
	private String triggerType;
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
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public Long getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(Long repeatCount) {
		this.repeatCount = repeatCount;
	}
	public Long getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(Long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	
	
}