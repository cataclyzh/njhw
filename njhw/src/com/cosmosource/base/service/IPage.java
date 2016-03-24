package com.cosmosource.base.service;

import java.util.List;

/**
 * 分页接口
 * @author WXJ
 *
 * @param <T>
 */
public interface IPage<T> {
	
	/**
	 * 当前页号
	 * @return
	 */
	public int getPageNo();
	
	/**
	 * 每页记录数
	 * @return
	 */
	public int getPageSize();
	
	/**
	 * 当前页第一条记录在总结果集中的位置
	 * @return
	 */
	public int getFirst();
	
	/**
	 * 排序字段
	 * @return
	 */
	public String getOrderBy();
	
	/**
	 * 排序方向
	 * @return
	 */
	public String getOrder();
	
	/**
	 * 是否设置排序
	 * @return
	 */
	public boolean isOrderBySetted();
	
	/**
	 * 是否自动统计总数
	 * @return
	 */
	public boolean isAutoCount();
	
	/**
	 * 分页结果集
	 * @return
	 */
	public List<T> getResult();
	
	public void setResult(final List<T> result);
	
	/**
	 * 记录总数
	 * @return
	 */
	public long getTotalCount();
	
	/**
	 * 总页数
	 * @return
	 */
	public long getTotalPages();
	
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean isHasNext();
	
	/**
	 * 下一页页号
	 * @return
	 */
	public int getNextPage();
	
	/**
	 * 是否有前一页
	 * @return
	 */
	public boolean isHasPre();
	
	/**
	 * 前一页号
	 * @return
	 */
	public int getPrePage();
	
	/**
	 * 隐藏分页列表设置
	 * @return
	 */
	public String getTogglestatus();
	
	/**
	 * 跳转页号
	 * @return
	 */
	public int getJumpNumber();
	
}
