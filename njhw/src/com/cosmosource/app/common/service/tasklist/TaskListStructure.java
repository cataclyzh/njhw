package com.cosmosource.app.common.service.tasklist;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.cosmosource.app.common.model.TaskInfoModel;

/**
 * TaskList 结构类，用来遍历执行所有的任务清单类，方便扩展，增加新德任务清单类后注入到taskLists属性中即可
 * 
 * @author jtm
 * 
 */
public class TaskListStructure {

	private List<AbstractTaskList> taskLists;

	public List<AbstractTaskList> getTaskLists() {
		return taskLists;
	}

	public void setTaskLists(List<AbstractTaskList> taskLists) {
		this.taskLists = taskLists;
	}

	/**
	 * 交给manager调用,遍历执行任务清单类
	 * 
	 * @param objects 交给AbstractTaskList的实现类，可以将taskList需要的内容封装到object数组
	 * @param menuActionStr 检查权限所用
	 * @return 返回验证权限通过的任务清单实现类返回值集合
	 */
	public List<TaskInfoModel> action(Object[] objects, String menuActionStr) {

		List<TaskInfoModel> list = new ArrayList<TaskInfoModel>();
		if(CollectionUtils.isEmpty(taskLists)){
			return null;
		}
		for (AbstractTaskList taskList : taskLists) {
			if (menuActionStr.indexOf(taskList.getMenuAction()) > -1) {
				TaskInfoModel taskInfo = taskList.getTaskInfoModel(objects);
				if(taskInfo != null){
					list.add(taskInfo);
				}
			}
		}
		return list;
	}
}
