package com.cosmosource.base.dao;

public interface DialectiBatis {
	public boolean supportsLimit();

	public String getLimitString(String sql, boolean hasOffset);

	public String getLimitString(String sql, int offset, int limit);
}