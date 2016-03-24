package com.cosmosource.base.service;

import java.util.Map;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionService {
	PlatformTransactionManager platformTransactionManager;
	
	HibernateTransactionManager  htm;//适用于使用Hibernate进行数据持久化操作的情况。
	JpaTransactionManager jtm;//适用于使用JPA进行数据持久化操作的情况。
	DataSourceTransactionManager dstm;//适用于使用JDBC和iBatis进行数据持久化操作的情况。

	public void unitDelJob(Map<String, Object> arguments) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = platformTransactionManager
				.getTransaction(def);
		try {
			System.out.println("VVVVVVVVVVV");
		} catch (RuntimeException e) {
			platformTransactionManager.rollback(status);
			e.printStackTrace();
		} finally {
			platformTransactionManager.commit(status);
		}
	}
}
