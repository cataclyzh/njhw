/**
* <p>文件名: HomeManager.java</p>
* <p>:描述：首页manager</p>
* <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
* <p>公司: Holytax Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-2-7 
* @作者： jtm
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
*/
package com.cosmosource.app.common.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.common.entity.TCommonMsgBoard;
import com.cosmosource.common.entity.TCommonMsgBox;
import com.cosmosource.app.common.model.NavigationModel;
import com.cosmosource.app.common.model.TaskInfoModel;
import com.cosmosource.app.common.service.tasklist.TaskListConstants;
import com.cosmosource.app.common.service.tasklist.TaskListStructure;


/**
 * 任务清单管理类
 * 
 * @author jtm
 * 
 */
public class HomeManager extends BaseManager {

	private TaskListStructure taskListStructure;

	public TaskListStructure getTaskListStructure() {
		return taskListStructure;
	}

	public void setTaskListStructure(TaskListStructure taskListStructure) {
		this.taskListStructure = taskListStructure;
	}

	/**
	 * 取得页面显示的任务清单list集合
	 * 
	 * @param session
	 * @param menuAction 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<TaskInfoModel> getTaskList(HttpSession session, String menuAction) throws Exception {
		List<TaskInfoModel> taskInfoModels = taskListStructure.action(new Object[]{session}, menuAction);
		String queryString = "";
		//将所有任务清单合并成一个sql语句
		for (TaskInfoModel taskInfoModel : taskInfoModels) {
			if(!StringUtils.isEmpty(taskInfoModel.getTasksql())){
				queryString +=  taskInfoModel.getTasksql()+" union ";
			}
		}
		if(!StringUtils.isEmpty(queryString)){
			queryString = queryString.substring(0,queryString.lastIndexOf("union"));
			System.out.println(queryString);
			//使用hibernate Query查询
			SQLQuery sqlQuery = dao.getSession().createSQLQuery(queryString);
			//设置sql语句中的查询值类型
			sqlQuery = sqlQuery.addScalar(TaskListConstants.SQL_SHOW, Hibernate.STRING);
			sqlQuery = sqlQuery.addScalar(TaskListConstants.SQL_NAME, Hibernate.STRING);
			//设置返回类型
			Query query = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			try{
				List<HashMap<String, String>> taskInfos = query.list();
				
				//遍历 查询后结果,依据name值对应taskInfoModel.getTaskname()后取出show set taskInfoModel.taskinfo
				for (HashMap<String, String> taskInfo : taskInfos) {
					for (TaskInfoModel taskInfoModel : taskInfoModels) {
						if(StringUtils.equals(taskInfoModel.getTaskname(), taskInfo.get(TaskListConstants.SQL_NAME))){
							String show = taskInfo.get(TaskListConstants.SQL_SHOW);
							//判断查询结果,结果是0的从list中删除
							if(NumberUtils.isNumber(show)){
								if(NumberUtils.toDouble(show) == 0){
									taskInfoModels.remove(taskInfoModel);
									break;
								}
							}
							taskInfoModel.setTaskinfo(show);
							break;
						}
						
					}
				}
				for (TaskInfoModel t : taskInfoModels) {
					if(t.getTaskinfo() == null){
						taskInfoModels.remove(t);
					}
				}
				return taskInfoModels;
			}catch(HibernateException e){
				logger.error("任务清单查询出错",e);
				throw new Exception();
			}
			
		}
		return null;
	}

	/**
	 * 取得页面显示的导航list集合
	 * @param menuAction
	 * @param navigationPath 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NavigationModel> getNavigationList(String menuAction, String navigationPath) {

		List<NavigationModel> list = new ArrayList<NavigationModel>();
		
		//xml dom4j start
		try {
			//解决路径中包含空格或中文
			String path = java.net.URLDecoder.decode(navigationPath, "utf-8");
			SAXReader reader = new SAXReader();
			Document  document = reader.read(new File(path));
			Element rootElm = document.getRootElement();
			List<Element> navigationList = rootElm.elements("navigation");
			for (Element element : navigationList) {
				Element navigationName = element.element("navigationName");
				Element navigationLink = element.element("navigationLink");
				Element navigationImgSrc = element.element("navigationImgSrc");
				Element subsysid = element.element("subsysid");
				Element subsysname = element.element("subsysname");
				Element accordionname = element.element("accordionname");
				if (menuAction.indexOf(navigationLink.getText()) > -1) {
					NavigationModel navigationModel = new NavigationModel();
					navigationModel.setNavigationImgSrc(navigationImgSrc.getText());
					navigationModel.setNavigationLink(navigationLink.getText());
					navigationModel.setNavigationName(navigationName.getText());
					navigationModel.setSubsysid(subsysid.getText());
					navigationModel.setSubsysname(subsysname.getText());
					navigationModel.setAccordionname(accordionname.getText());
					list.add(navigationModel);
				}
			}
		} catch (Exception e) {
			logger.error("读取导航配置文件navigation.xml失败", e);
		}  
		return list;
	}
	
	/**
	 * @描述: 查询首页公告列表
	 * @作者：WXJ
	 * @日期：2012-4-24 16:37:20
	 * @param count 显示总数
	 * @param paraMap 查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCommonMsgBoard> findBulletin(int count, Map<String, Object> paraMap) {
		StringBuilder hql = new StringBuilder();
		hql.append("select b from TCommonMsgBoard b ")
		.append("where exists( ")	
		.append("select u.loginname ")
		.append("from TAcUser u, TAcOrg o ")
		.append("where u.orgid = o.orgid ")
		.append("and u.loginname = b.author ")
		.append("and o.company = :company) ")
		.append("and b.msgtime >= add_months(sysdate, -1) order by b.msgid desc");
		
		Query query = dao.createQuery(hql.toString(), paraMap);
		query.setFirstResult(0);
		query.setMaxResults(count);
		
		return query.list();
	}
	
	/**
	 * @描述: 查询首页知识列表
	 * @作者：WXJ
	 * @日期：2012-4-24 16:37:20
	 * @param count 显示总数
	 * @param paraMap 查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List homeKnowledgeList(int count, Map<String, Object> paraMap){
		final String sql = "select k from TCommonKnowledge k where k.state=:state order by k.knowledgeId desc";
		Query query = dao.createQuery(sql, paraMap);
		query.setFirstResult(0);
		query.setMaxResults(count);
		
		return query.list();
	}

	/**
	 * @描述: 查询首页消息列表
	 * @作者：WXJ
	 * @日期：2012-4-24 16:37:20
	 * @param count 显示总数
	 * @param paraMap 查询参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCommonMsgBox> findMsgByNotRead(int count, Map<String, Object> paraMap) {
		final String sql = "select m from TCommonMsgBox m where m.receiver=:receiver and m.status=:status order by m.msgid desc";
		Query query = dao.createQuery(sql, paraMap);
		query.setFirstResult(0);
		query.setMaxResults(count);
		
		return query.list();
	}
}
