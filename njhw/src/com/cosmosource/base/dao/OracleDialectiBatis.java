package com.cosmosource.base.dao;

  
/**  
 * 实现Oracle 方言
 * @author WXJ
 * 
 */   
public class OracleDialectiBatis implements DialectiBatis {  
  
    protected static final String SQL_END_DELIMITER = ";";   
    /**  
     * oracle里根据rownum来限制查询当前页的数据  
     *   
     * @param String sql   
     * @param int offset 
     * @param int limit 
     * @return  String 拼装好的SQL
     */  
    public String getLimitString(String sql, int offset, int limit) {   
        if (offset == 1) {   
            offset = 0;   
        }   
        StringBuilder pageStr = new StringBuilder();   
        pageStr   
                .append("select * from ( select row_limit.*, rownum rownum_ from (");   
        pageStr.append(this.trim(sql));   
        pageStr.append(" ) row_limit where rownum <= ");   
        pageStr.append(limit + offset);   
        pageStr.append(" ) where rownum_ >");   
        pageStr.append(offset);   
        return pageStr.toString();   
    }   
  
    /**  
     * 去掉当前SQL 后分号  
     *   
     * @param sql  
     * @return  
     */   
    private String trim(String sql) {   
        sql = sql.trim();   
        if (sql.endsWith(SQL_END_DELIMITER)) {   
            sql = sql.substring(0, sql.length() - 1   
                    - SQL_END_DELIMITER.length());   
        }   
        return sql;   
    }

	public String getLimitString(String sql, boolean hasOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsLimit() {
		// TODO Auto-generated method stub
		return false;
	}

	
}   