/**
* <p>文件名: QuartzAction.java</p>
* <p>描述：任务管理Action</p>
* <p>版权: Copyright (c) 2011 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-5-25 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.model.QuartzModel;
import com.cosmosource.common.service.SchedulerManager;

/**
* @类描述: 任务管理Action
* @作者： WXJ
*/
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QuartzAction extends BaseAction {

	private static final long serialVersionUID = 8497952646128136433L;
	private SchedulerManager schedulerManager;
	private QuartzModel model =  new QuartzModel();
	
	/**
	* @描述: 查询注册码列表
	* @作者：WXJ
	* @日期：2011-5-25 下午04:55:25
	* @return
	* @throws Exception
	* @return String
	*/
	public String list() throws Exception {
		List list = schedulerManager.getQrtzTriggers();
		getRequest().setAttribute("qList", list);
		return SUCCESS;
	}
	/**
	* @描述: 显示录入页面
	* @作者：WXJ
	* @日期：2011-5-25 下午04:55:25
	* @return
	* @throws Exception
	* @return String
	*/
	public String input() throws Exception {
		return INPUT;
	}
	/**
	* @描述: 保存信息
	* @作者：WXJ
	* @日期：2011-5-25 下午04:55:25
	* @return
	* @throws Exception
	* @return String
	*/
	public String save() throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		map.put("startTime", model.getStartTime());
		map.put("triggerGroup", model.getTriggerGroup());
		map.put("endTime", model.getEndTime());
		map.put("triggerName", model.getTriggerName());
		if(model.getRepeatCount()!=null){
			map.put("repeatCount", model.getRepeatCount().toString());
		}
		map.put("repeatInterval", model.getRepeatInterval().toString());
		try {
			// 添加任务调试
			schedulerManager.schedule(model.getJobName(),map);
//			schedulerManager.schedule("jobInB","AGDEF工", "0 20 17 * * ?");
			setIsSuc("true");
		} catch (Exception e) {
			e.printStackTrace();
			setIsSuc("false");
		}
		return SUCCESS;
	}
	/**
	* @描述:　执行对任务的操作
	* @作者：WXJ
	* @日期：2011-5-25 下午04:55:25
	* @return
	* @throws Exception
	* @return String
	*/
	public String doCmd() throws Exception {
		String action = getRequest().getParameter("action");
		String triggerName = URLDecoder.decode(getRequest().getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(getRequest().getParameter("group"), "utf-8");
		if (action.equals("pause")) {
			HashMap map = new HashMap();
			schedulerManager.pauseTrigger(triggerName, group);
			map.put("flag", "0");
			map.put("smsg", "暂停成功！");
			Struts2Util.renderJson(map, "encoding:UTF-8","no-cache:true");
		} else if (action.equals("resume")) {
			schedulerManager.resumeTrigger(triggerName, group);
			HashMap map = new HashMap();
			map.put("flag", "0");
			map.put("smsg", "恢复成功！");
			Struts2Util.renderJson(map,"encoding:UTF-8","no-cache:true");
		} else if (action.equals("remove")) {
			HashMap map = new HashMap();
			boolean rs = schedulerManager.removeTrigger(triggerName, group);
			if(rs){
				map.put("flag", "0");
				map.put("smsg", "删除成功！");
				Struts2Util.renderJson(map,"encoding:UTF-8", "no-cache:true");
			}else{
				map.put("flag", "1");
				map.put("smsg", "删除失败！");
				Struts2Util.renderJson(map,"encoding:UTF-8", "no-cache:true");
			}
		}
		
		return null;
	}
	
	//-- 页面属性访问函数 --//
	/**
	 * 在查看和保存之前执行此方法，初始化实体对象
	 * @return 
	 * @throws Exception
	 */
	@Override
	protected void prepareModel() throws Exception {
	}
	

	public SchedulerManager getSchedulerManager() {
		return schedulerManager;
	}

	public void setSchedulerManager(SchedulerManager schedulerManager) {
		this.schedulerManager = schedulerManager;
	}

	public QuartzModel getModel() {
		return model;
	}
}
