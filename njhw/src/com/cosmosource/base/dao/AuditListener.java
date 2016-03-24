package com.cosmosource.base.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.event.DeleteEvent;
import org.hibernate.event.MergeEvent;
import org.hibernate.event.MergeEventListener;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.base.dao.AuditableEntity;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TCommonLogAudit;
import com.cosmosource.common.security.SpringSecurityUtils;

/**
 * @类描述: 在自动为entity添加审计信息的Hibernate EventListener.
 * 在hibernate执行saveOrUpdate()时,自动为AuditableEntity的子类添加审计信息.
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class AuditListener implements SaveOrUpdateEventListener,MergeEventListener,
PostInsertEventListener,PostUpdateEventListener,PostDeleteEventListener{
	
	private static final long serialVersionUID = -7481545873785342485L;
	private static Logger logger = LoggerFactory.getLogger(AuditListener.class);
	/**
	 * @描述:记录新增、修改操作日志，在当前记录中添加审计信息
	 * @作者：WXJ
	 * @日期：2011-03-28
	 * @return
	 */
	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		try {
			Object object = event.getObject();
			if (object instanceof AuditableEntity) {
				//登录用户
				String loginName = SpringSecurityUtils.getCurrentUserName();
				if(StringUtil.isBlank(loginName)){
					loginName = "systemauto";
				}
				
				Class entitytClass = Class.forName(object.getClass().getName());
				AbstractEntityPersister meta = (SingleTableEntityPersister)event.getSession().getSessionFactory().getClassMetadata(entitytClass);
				AuditableEntity entity = (AuditableEntity) object;
				if (PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()) == null) {
					//创建新对象
					entity.setCreateTime(new Date());
					entity.setCreateBy(loginName);
				} else {
					//修改旧对象
					entity.setLastModifyTime(new Date());
					entity.setLastModifyBy(loginName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void onDelete(DeleteEvent event, Set arg1) throws HibernateException {
	}
	/**
	 * @描述:记录新增、修改操作日志，在当前记录中添加审计信息
	 * @作者：WXJ
	 * @日期：2011-03-28
	 * @return 
	 */
	public void onMerge(MergeEvent event) throws HibernateException {
		try {
			Object object = event.getOriginal();
			if (object instanceof AuditableEntity) {
				//登录用户
				String loginName = SpringSecurityUtils.getCurrentUserName();
				if(StringUtil.isBlank(loginName)){
					loginName = "systemauto";
				}
				Class entitytClass = Class.forName(object.getClass().getName());
				AbstractEntityPersister meta = (SingleTableEntityPersister)event.getSession().getSessionFactory().getClassMetadata(entitytClass);
				AuditableEntity entity = (AuditableEntity) object;
				if (PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()) == null) {
					//创建新对象
					entity.setCreateTime(new Date());
					entity.setCreateBy(loginName);
				} else {
					//修改旧对象
					entity.setLastModifyTime(new Date());
					entity.setLastModifyBy(loginName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public void onMerge(MergeEvent event, Map map) throws HibernateException {
	}
	/**
	 * @描述:记录修改操作日志
	 * @作者：WXJ
	 * @日期：2011-03-28
	 * @return 
	 */
	
	public void onPostUpdate(PostUpdateEvent event) {
		try {
			Object object = event.getEntity();
			if(object instanceof AuditableEntity){
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
				log.setOperateType("UPDATE");
				log.setTablename( meta.getTableName().toUpperCase());
				
				Map mapJson = new HashMap();
				String[] pn = meta.getPropertyNames();
				for(int k = 0 ;k<pn.length;k++){
					if(!(meta.getPropertyType(pn[k])instanceof org.hibernate.type.BagType)){
						mapJson.put(pn[k], PropertyUtils.getProperty(object,pn[k]));
					}
				}
				
				log.setRowdata(JsonUtil.beanToJson(mapJson));
				log.setTableid((Long)PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()));
				saveAuditLog(event.getSession(),log);
				logger.info("修改>>>表：[{}] ID：[{}] 操作人：[{}] 操作时间：[{}]", new Object[] { meta.getTableName().toUpperCase(), 
						PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()),loginName, DateUtil.getDateTime() });
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * @描述:插入记录操作日志
	 * @作者：WXJ
	 * @日期：2011-03-28
	 * @return 
	 */
	public void onPostInsert(PostInsertEvent event) {
		try {
			Object object = event.getEntity();
			if(object instanceof AuditableEntity){
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
				log.setOperateType("INSERT");
				log.setTablename( meta.getTableName().toUpperCase());
				
				Map mapJson = new HashMap();
				String[] pn = meta.getPropertyNames();
				for(int k = 0 ;k<pn.length;k++){
					if(!(meta.getPropertyType(pn[k])instanceof org.hibernate.type.BagType)){
						mapJson.put(pn[k], PropertyUtils.getProperty(object,pn[k]));
					}
				}
				log.setRowdata(JsonUtil.beanToJson(mapJson));
				log.setTableid((Long)PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()));
				saveAuditLog(event.getSession(),log);
				logger.info("插入>>>表：[{}] ID：[{}]   操作人：[{}]    操作时间：[{}]", new Object[] { meta.getTableName().toUpperCase(), 
						PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()),loginName, DateUtil.getDateTime() });
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * @描述:删除记录操作日志
	 * @作者：WXJ
	 * @日期：2011-03-28
	 * @return 
	 */
	public void onPostDelete(PostDeleteEvent event) {
		try {
			Object object = event.getEntity();
			if(object instanceof AuditableEntity){
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

				Map mapJson = new HashMap();
				String[] pn = meta.getPropertyNames();
				for(int k = 0 ;k<pn.length;k++){
					if(!(meta.getPropertyType(pn[k])instanceof org.hibernate.type.BagType)){
						mapJson.put(pn[k], PropertyUtils.getProperty(object,pn[k]));
					}
				}
				
				
				log.setRowdata(JsonUtil.beanToJson(mapJson));
				log.setTableid((Long)PropertyUtils.getProperty(object, meta.getIdentifierPropertyName()));
				saveAuditLog(event.getSession(),log);
				
				logger.info("删除>>>表：[{}] ID：[{}] 操作人：[{}] 操作时间： [{}]", new Object[] { meta.getTableName().toUpperCase(), 
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
	private void saveAuditLog(Session session, TCommonLogAudit log) {
		Session logSession = session.getSessionFactory().openSession();
		Transaction tx = logSession.beginTransaction();
		try {
			tx.begin();
			logSession.save(log);
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