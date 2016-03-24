/**
* <p>文件名: SchedulerManager.java</p>
* <p>描述：任务调试处理</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-05-03 下午07:01:02 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
*/
package com.cosmosource.common.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.SpringContextHolder;
import com.cosmosource.base.util.Constants;
/**
 * @类描述: 任务调试处理
 * @作者： WXJ
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchedulerManager extends BaseManager{

	private Scheduler scheduler;
	
	private String prefix;

	/**
	 * Trigger 参数,以Constants常量为键封装的Map
	 * @param map
	 */	
	public void schedule(String sJob,Map<String,String> map) {
		
		JobDetail jobDetail = SpringContextHolder.getBean(sJob);
		
		String temp = null;
		//实例化SimpleTrigger
		SimpleTrigger SimpleTrigger = new SimpleTrigger();
						
		//这些值的设置也可以从外面传入，这里采用默放值		
		SimpleTrigger.setJobName(jobDetail.getName());		
		SimpleTrigger.setJobGroup(Scheduler.DEFAULT_GROUP);		
		SimpleTrigger.setRepeatInterval(1000L);
		
		//设置名称
		temp = map.get(Constants.TRIGGERNAME);		
		if (StringUtils.isEmpty(StringUtils.trim(temp)) ){
			temp = UUID.randomUUID().toString();
		}else{
			//在名称后添加UUID，保证名称的唯一性
			temp +="&"+UUID.randomUUID().toString();
		}
		SimpleTrigger.setName(temp);
		
		//设置Trigger分组
		temp = map.get(Constants.TRIGGERGROUP);
		if(StringUtils.isEmpty(temp)){
			temp = Scheduler.DEFAULT_GROUP;
		}
		SimpleTrigger.setGroup(temp);
		
		//设置开始时间
		temp = map.get(Constants.STARTTIME);
		if(StringUtils.isNotEmpty(temp)){
			SimpleTrigger.setStartTime(this.parseDate(temp));
		}
		
		//设置结束时间
		temp = map.get(Constants.ENDTIME);
		if(StringUtils.isNotEmpty(temp)){
			SimpleTrigger.setEndTime(this.parseDate(temp));
		}
		
		//设置执行次数
		temp = map.get(Constants.REPEATCOUNT);
		if(StringUtils.isNotEmpty(temp) && NumberUtils.toInt(temp) > 0){
			SimpleTrigger.setRepeatCount(NumberUtils.toInt(temp));
		}else{
			SimpleTrigger.setRepeatCount(-1);
		}
		
		//设置执行时间间隔
		temp = map.get(Constants.REPEATINTERVEL);
		if(StringUtils.isNotEmpty(temp) && NumberUtils.toLong(temp) > 0){
			SimpleTrigger.setRepeatInterval(NumberUtils.toLong(temp)*1000);
		}

		try {
			scheduler.addJob(jobDetail, true);
		
			scheduler.scheduleJob(SimpleTrigger);
			//scheduler.rescheduleJob(SimpleTrigger.getName(), SimpleTrigger.getGroup(), SimpleTrigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据 Quartz Cron Expression 调度任务
	 * @param cronExpression  Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	public void schedule(String sJob,String cronExpression) {
		schedule(sJob,"", cronExpression);
	}
	/**
	 * 根据 Quartz Cron Expression 调度任务
	 * @param name  Quartz CronTrigger名称
	 * @param cronExpression Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	public void schedule(String sJob,String name, String cronExpression) {
		schedule(sJob,name,  cronExpression,Scheduler.DEFAULT_GROUP);
	}
	/**
	 * 根据 Quartz Cron Expression 调度任务
	 * @param name  Quartz CronTrigger名称
	 * @param cronExpression Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	   * @param group Quartz CronExpression Group
	 */	
	public void schedule(String sJob,String name, String cronExpression,String group) {
		try {
			schedule(sJob,name, new CronExpression(cronExpression),group);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据 Quartz Cron Expression 调度任务
	 * @param cronExpression Quartz CronExpression
	 */
	public void schedule(String sJob,CronExpression cronExpression) {
		schedule(sJob,null, cronExpression);
	}
	/**
	 * 根据 Quartz Cron Expression 调度任务
	 * @param name Quartz CronTrigger名称
	 * @param cronExpression Quartz CronExpression
	 */
	public void schedule(String sJob,String name, CronExpression cronExpression) {
		schedule(sJob,name,  cronExpression,Scheduler.DEFAULT_GROUP) ;
	}
	/**
	 * 根据 Quartz Cron Expression 调度任务
	 * @param name Quartz CronTrigger名称
	 * @param cronExpression Quartz CronExpression
	 * @param group Quartz CronExpression Group
	 */	
	public void schedule(String sJob,String name, CronExpression cronExpression,String group) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}else{
			//在名称后添加UUID，保证名称的唯一性
			name +="&"+UUID.randomUUID().toString();
		}
		try {
			JobDetail jobDetail = SpringContextHolder.getBean(sJob);
			scheduler.addJob(jobDetail, true);
			CronTrigger cronTrigger = new CronTrigger(name, group, jobDetail.getName(),
					Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
			//scheduler.rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);			
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 在startTime时执行调度一次
	 * @param startTime 调度开始时间
	 */
	public void schedule(Date startTime) {
		schedule(startTime, Scheduler.DEFAULT_GROUP);
	}
	/**
	 * 在startTime时执行调度一次
	 * @param startTime 调度开始时间
	 * @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(Date startTime,String group) {
		schedule(startTime, null,group);
	}
	/**
	 * 在startTime时执行调度一次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 */
	public void schedule(String name, Date startTime) {
		schedule(name, startTime,Scheduler.DEFAULT_GROUP);
	}
	/**
	 * 在startTime时执行调度一次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(String name, Date startTime,String group) {
		schedule(name, startTime, null,group);
	}
	/**
	 * 在startTime时执行调度，endTime结束执行调度
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 */
	public void schedule(Date startTime, Date endTime) {
		schedule(startTime, endTime, Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 在startTime时执行调度，endTime结束执行调度
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(Date startTime, Date endTime,String group) {
		schedule(startTime, endTime, 0,group);
	}

	/**
	 * 在startTime时执行调度，endTime结束执行调度
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 */	
	public void schedule(String name, Date startTime, Date endTime) {
		schedule(name, startTime, endTime,Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 在startTime时执行调度，endTime结束执行调度
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(String name, Date startTime, Date endTime,String group) {
		schedule(name, startTime, endTime, 0,group);
	}

	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 */	
	public void schedule(Date startTime, Date endTime, int repeatCount) {
		schedule( startTime, endTime, 0,Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(Date startTime, Date endTime, int repeatCount,String group) {
		schedule(null, startTime, endTime, 0,group);
	}

	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 */	
	public void schedule(String name, Date startTime, Date endTime, int repeatCount) {
		schedule(name, startTime, endTime, 0, Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(String name, Date startTime, Date endTime, int repeatCount,String group) {
		schedule(name, startTime, endTime, 0, 1L,group);
	}

	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 */	
	public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		schedule(startTime, endTime, repeatCount, repeatInterval,Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 *  @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval,String group) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval,group);
	}

	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 */	
	public void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		this.schedule( name , startTime,  endTime,  repeatCount,  repeatInterval,  Scheduler.DEFAULT_GROUP);
	}
	
	/**
	 * 在startTime时执行调度，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 *  @param group Quartz SimpleTrigger Group
	 */	
	public void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval,String group ) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}else{
			//在名称后添加UUID，保证名称的唯一性
			name +="&"+UUID.randomUUID().toString();
		}

		try {
			JobDetail jobDetail = SpringContextHolder.getBean("sJob");
			scheduler.addJob(jobDetail, true);

			SimpleTrigger SimpleTrigger = new SimpleTrigger(name, group, jobDetail.getName(),
					Scheduler.DEFAULT_GROUP, startTime, endTime, repeatCount, repeatInterval);
			scheduler.scheduleJob(SimpleTrigger);
			//scheduler.rescheduleJob(SimpleTrigger.getName(), SimpleTrigger.getGroup(), SimpleTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	


	/**
	 * 取得所有调度Triggers
	 * @return
	 */	
	public List getQrtzTriggers() {
		StringBuilder sb = new StringBuilder();
		sb.append("select t.trigger_name,t.next_fire_time, ")
			.append("t.prev_fire_time,t.start_time,t.end_time,t.trigger_state,t.trigger_group ")
			.append("from "+StringUtils.trim(prefix)+"TRIGGERS t order by start_time");
		List<Object[]> results =  dao.createSQLQuery(sb.toString()).list();
		long val = 0;
		String temp = null;
		Map<String, Object> map = null;
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>(); 
		for (Object[] arr : results) {
			map = new HashMap<String, Object>();
			temp = (String)arr[0];
			map.put("trigger_name", temp);
			if(StringUtils.indexOf(temp, "&") != -1){
				map.put("display_name", StringUtils.substringBefore(temp, "&"));
			}else{
				map.put("display_name", temp);
			}
			
			val = ((BigDecimal)arr[1]).longValue();//MapUtils.getLongValue(map, "next_fire_time");
			if (val > 0) {
				map.put("next_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}

			val = ((BigDecimal)arr[2]).longValue();//MapUtils.getLongValue(map, "prev_fire_time");
			if (val > 0) {
				map.put("prev_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}

			val =((BigDecimal)arr[3]).longValue();// MapUtils.getLongValue(map, "start_time");
			if (val > 0) {
				map.put("start_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}
			
			val =((BigDecimal)arr[4]).longValue();// MapUtils.getLongValue(map, "end_time");
			if (val > 0) {
				map.put("end_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}
			
			map.put("trigger_state",Constants.status.get((String)arr[5]));
			map.put("triggerState",(String)arr[5]);
			map.put("trigger_group",(String)arr[6]);
			ret.add(map);
		}

		return ret;
	}
	
	/**
	 * 根据名称和组别暂停Tigger
	 * @param triggerName
	 * @param group
	 */	
	public void pauseTrigger(String triggerName,String group){		
		try {			
			scheduler.pauseTrigger(triggerName, group);//停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 恢复Trigger
	 * @param triggerName
	 * @param group
	 */	
	public void resumeTrigger(String triggerName,String group){		
		try {
			//Trigger trigger = scheduler.getTrigger(triggerName, group);
			
			scheduler.resumeTrigger(triggerName, group);//重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除Trigger
	 * @param triggerName
	 * @param group
	 */	
	public boolean removeTrigger(String triggerName,String group){		
		try {
			scheduler.pauseTrigger(triggerName, group);//停止触发器
			return scheduler.unscheduleJob(triggerName, group);//移除触发器
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}	

	/**
	 * 　恢复调度中所有的job的任务
	 */
	public void resumeAll() {
		try {
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 暂停调度中所有的job任务
	 * 
	 * @throws SchedulerException
	 */
	public void pauseAll() {

		try {
			scheduler.pauseAll();
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Date parseDate(String time){
		try {
			return DateUtils.parseDate(time, new String[]{"yyyy-MM-dd HH:mm:ss"});
			
		} catch (ParseException e) {			
			throw new RuntimeException(e);
		}
	}
	

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
