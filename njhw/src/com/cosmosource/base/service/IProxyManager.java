/**
 * <p>文件名: IProxyManager.java</p>
 * <p>描述：Manager</p>
 * <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
 * <p>公司: Cosmosource Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间： 2010-11-08 下午01:25:51 
 * @作者： WXJ
 * @版本： V1.0
 * <p>类修改者		 修改日期			修改说明   </p>   
 * 
 */
package com.cosmosource.base.service;

import java.io.Serializable;
import java.util.List;


/**
 * @类描述: 公共的管理类 主要提供通用的查询方法
 * 
 * @作者： WXJ
 */
@SuppressWarnings("rawtypes")
public interface IProxyManager {
	
	public Object findById(final Class entityClass,final Serializable id);
	public  List findByHQL(final String hql, final Object... values);
}
