/**
* <p>文件名: ConstantsManager.java</p>
* <p>描述：公共的常量类Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-11-08 下午01:25:51 
* @作者： WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.service;

import java.util.ArrayList;
import java.util.List;

import com.cosmosource.base.service.BaseManager;
import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.StringUtil;
import com.cosmosource.common.entity.TCommonConstants;

/**
 * @类描述:  公共的常量管理类
 * 主要提供通用的查询方法
 * 
 * @作者： WXJ
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ConstantsManager extends BaseManager{
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveCtt(TCommonConstants entity){
		dao.saveOrUpdate(entity);
	}
    /**
	 * 查询机构列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TCommonConstants> queryCtts(final Page<TCommonConstants> page, final TCommonConstants model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TCommonConstants t ")
		.append("where 1=1 ");
		
		if(StringUtil.isNotBlank(model.getCttKey())){
			sbHql.append("and t.cttKey like ? ");
			lPara.add("%"+model.getCttKey()+"%");
		}
		if(StringUtil.isNotBlank(model.getCttType())){
			sbHql.append("and t.cttType = ? ");
			lPara.add(model.getCttType());
		}
		sbHql.append(" order by t.cttid ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	/**
	 * @描述: 删除选中的机构
	 * 如果机构下有用户，角色，权限，或是子机构则此机构不能被删除
	 * 整个过程是一个事务
	 * @param ids　选中的机构ＩＤ数组
	 */
	public void deleteCtts(String[] ids){
		dao.deleteByIds(TCommonConstants.class, ids);
	}
	

}
