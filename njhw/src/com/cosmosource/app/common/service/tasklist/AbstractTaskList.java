package com.cosmosource.app.common.service.tasklist;

import com.cosmosource.app.common.model.TaskInfoModel;


/**
 * 任务清单抽象类，要实现一个任务清单，继承此类，并注入到TaskListStructure.java中的list中即可
 * @author jtm
 *
 */
public abstract class AbstractTaskList {
	
	public abstract String getMenuAction();
	/**
	 * TaskInfoModel的tasksql语句必须遵照如下规约：查询后的结果别名为show,name name显示的信息是taskInfoModel.taskname
	 * @param objects
	 * @return
	 */
	public abstract TaskInfoModel getTaskInfoModel(Object[] objects);
}
