/**
* <p>文件名: noElecInvTaskList.java</p>
* <p>:描述：(用一句话描述该文件做什么)</p>
* <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
* <p>公司: Holytax Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-8 下午02:06:01 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.app.common.service.tasklist;

import org.apache.struts2.ServletActionContext;

import com.cosmosource.base.util.Constants;
import com.cosmosource.common.service.RestrictDataUtil;
import com.cosmosource.app.common.model.TaskInfoModel;
import com.cosmosource.app.entity.VmVisit;


/**
 * @类描述: 我的来访
 * @作者：WXJ
 */
public class VisitConfirmTaskList extends AbstractTaskList {

	/**
	 * 用此常量判断登录用户是否拥有权限
	 */
	private final String menuaction = "app/myvisit";
	@Override
	public String getMenuAction() {
		return menuaction;
	}

	@Override
	public TaskInfoModel getTaskInfoModel(Object[] objects) {
		Long userid = (Long) ServletActionContext.getRequest().getSession().getAttribute(Constants.USER_ID);
		String sqlFrom = " FROM VM_VISIT WHERE VS_FLAG='"+VmVisit.VS_FLAG_APP+"' and USER_ID="+userid;
		
		TaskInfoModel taskInfoModel = new TaskInfoModel();
		taskInfoModel.setTaskname(TaskListConstants.VISIT_CONFIRM);
		taskInfoModel.setLabname(taskInfoModel.getTaskname());
		taskInfoModel.setTasklink("/app/myvisit/init.act");
		//拼装sql语句必须遵照如下规约：查询后的结果别名为show,name name显示的信息是taskInfoModel.taskname
		String tasksql = "SELECT count(*) AS "+TaskListConstants.SQL_SHOW+", '"+taskInfoModel.getTaskname()+"' AS "+TaskListConstants.SQL_NAME+sqlFrom;
		taskInfoModel.setTasksql(tasksql);
		return taskInfoModel;
	}

}
