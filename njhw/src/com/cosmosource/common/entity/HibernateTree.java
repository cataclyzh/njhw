package com.cosmosource.common.entity;

/**
 * Hibernate实现父子结构tree
 * 
 * @author liufang
 * 
 */
public interface HibernateTree {
	public static final String TREELEVEL = "treelevel";
	public static final String TREELAYER = "treelayer";
	public static final String PARENT = "parent";
	public static final String ISBOTTOM = "isbottom";

	public Long getTreelevel();

	public void setTreelevel(Long treelevel);

	public String getTreelayer();

	public void setTreelayer(String treelayer);

	public Long getParentid();
	
	public void setParentid(Long parentid);
	
	public String getIsbottom();
	
	public void setIsbottom(String isbottom);
	
}
