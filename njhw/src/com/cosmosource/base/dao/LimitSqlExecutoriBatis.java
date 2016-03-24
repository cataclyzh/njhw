package com.cosmosource.base.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;
/**
 * 
 * 扩展iBatis的SQL查询器
 * 注入Oracle方言查询，用于分页
 * @author WXJ
 *
 */
public class LimitSqlExecutoriBatis extends SqlExecutor {

	private DialectiBatis dialect;
	private boolean enableLimit = true;
	@Override
	public void executeQuery(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback) throws SQLException {
		if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)
				&& supportsLimit()) {
			sql = dialect.getLimitString(sql, skipResults, maxResults);
			skipResults = NO_SKIPPED_RESULTS;
			maxResults = NO_MAXIMUM_RESULTS;
		}
		super.executeQuery(statementScope, conn, sql, parameters, skipResults,
				maxResults, callback);
	}
	public boolean supportsLimit() {
		if (enableLimit && dialect != null) {
			return true;//dialect.supportsLimit();
		}
		return false;
	}
	public DialectiBatis getDialect() {
		return dialect;
	}
	public void setDialect(DialectiBatis dialect) {
		this.dialect = dialect;
	}
	public boolean isEnableLimit() {
		return enableLimit;
	}
	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}
}