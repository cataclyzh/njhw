package com.cosmosource.base.dao;

//import java.beans.PropertyDescriptor;
//import java.util.Collection;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.HibernateTree;
import com.cosmosource.common.entity.TCommonLogAudit;
import com.cosmosource.common.security.SpringSecurityUtils;

/**
 * @类描述: 在自动为entity添加审计信息的Hibernate EventListener.
 * 在hibernate执行saveOrUpdate()时,自动为HibernateTree的子类添加审计信息.
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"rawtypes"})
public class TreeEntityListener implements 
PostInsertEventListener,PostUpdateEventListener,PostDeleteEventListener{
	
	private static final long serialVersionUID = -7481545873785342485L;
	private static Logger logger = LoggerFactory.getLogger(TreeEntityListener.class);

	protected SessionFactory sessionFactory;

	protected SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			sessionFactory = (SessionFactory) wac.getBean("sessionFactory",
					SessionFactory.class);
		}
		return sessionFactory;
	}

	protected Session getSession() {
		return getSessionFactory().getCurrentSession();
	}
	/**
	 * @描述:记录修改操作日志
	 * @作者：WXJ
	 * @日期：2011-04-28
	 * @return 
	 */
	public void onPostUpdate(PostUpdateEvent event) {
		try {
			Object object = event.getEntity();
			if(object instanceof HibernateTree){
				HibernateTree tree = (HibernateTree) object;
				
				
				
				getSession().save(tree);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * @描述:插入记录操作日志
	 * @作者：WXJ
	 * @日期：2011-04-28
	 * @return 
	 */
	public void onPostInsert(PostInsertEvent event) {
		try {
			Object object = event.getEntity();
			if(object instanceof HibernateTree){

				
				HibernateTree tree = (HibernateTree) object;
				Class entitytClass = Class.forName(object.getClass().getName());
				AbstractEntityPersister meta = (SingleTableEntityPersister)event.getSession().getSessionFactory().getClassMetadata(entitytClass);

			//	String beanName = tree.getClass().getName();
				tree.setIsbottom("0");
				
				HibernateTree parent = (HibernateTree)getSession().get(entitytClass,tree.getParentid());
				parent.setIsbottom("1");
				
				tree.setTreelayer(parent.getTreelayer()+"."+(Long)PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()));
				
				updateX(event.getSession(),tree);
				updateX(event.getSession(),parent);
	
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * @描述:删除记录操作日志
	 * @作者：WXJ
	 * @日期：2011-04-28
	 * @return 
	 */
	public void onPostDelete(PostDeleteEvent event) {
		try {
			Object object = event.getEntity();
			if(object instanceof HibernateTree){
				String entityName = event.getEntity().getClass().getName();
				Class entitytClass = Class.forName(entityName);
				AbstractEntityPersister meta = (SingleTableEntityPersister)event.getSession().getSessionFactory().getClassMetadata(entitytClass);
				String loginName = SpringSecurityUtils.getCurrentUserName();
				if(StringUtil.isBlank(loginName)){
					loginName = "systemauto";
				}
				String ip = SpringSecurityUtils.getCurrentUserIp();
				//记录日志到数据库
				TCommonLogAudit log = new TCommonLogAudit();
				log.setOperateBy(loginName);
				log.setOperateIp(ip);
				log.setOperateTime(new Date());
				log.setOperateType("DELETE");
				log.setTablename( meta.getTableName().toUpperCase());
				log.setRowdata(JsonUtil.beanToJson(object));
				log.setTableid((Long)PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()));
//				saveAuditLog(event.getSession(),log);
				
				logger.info("delete>>>表：[{}] ID：[{}] 操作人：[{}] 操作时间： [{}]", new Object[] { meta.getTableName().toUpperCase(), 
						PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()),loginName, DateUtil.getDateTime() });
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * @描述:记录操作日志
	 * @作者：WXJ
	 * @日期：2011-03-28
	 * @return 
	 */
	private void updateX(Session session, Object log) {
		Session logSession = session.getSessionFactory().openSession();
		Transaction tx = logSession.beginTransaction();
		try {
			tx.begin();
			logSession.update(log);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			if(logSession!=null){
				logSession.close();
			}
		}
	}
}