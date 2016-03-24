/**
* <p>文件名: DictMgrManager.java</p>
* <p>描述：业务字典类型管理Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-9-6 下午06:21:22 
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
import com.cosmosource.common.entity.TAcDictdeta;
import com.cosmosource.common.entity.TAcDicttype;

/**
 * @类描述:  业务字典类型的管理类
 * 调用通用DAO对业务字典类型的CRUD
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class DictMgrManager extends BaseManager{
	
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveDicttype(TAcDicttype entity){
		dao.saveOrUpdate(entity);
		
	}
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveDictdeta(TAcDictdeta entity){
		dao.saveOrUpdate(entity);
		
	}
	/**
	 * 查询业务字典类型列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcDicttype> queryDicttypes(final Page<TAcDicttype> page, final TAcDicttype model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcDicttype t ")
		.append("where 1=1 ");
		if(StringUtil.isNotBlank(model.getDicttypename())){
			sbHql.append(" and t.dicttypename like ? ");
			lPara.add("%"+model.getDicttypename()+"%");
		}
		if(StringUtil.isNotBlank(model.getDicttypecode())){
			sbHql.append(" and t.dicttypecode like ? ");
			lPara.add("%"+model.getDicttypecode()+"%");
		}	
		sbHql.append(" order by t.dicttypeid ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	/**
	 * 查询业务字典类型列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TAcDictdeta> queryDictdetas(final Page<TAcDictdeta> page, final TAcDictdeta model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TAcDictdeta t ")
		.append("where t.dicttype=? ");
		lPara.add(model.getDicttype());
		if(StringUtil.isNotBlank(model.getDictname())){
			sbHql.append(" and t.dictname like ? ");
			lPara.add("%"+model.getDictname()+"%");
		}
		if(StringUtil.isNotBlank(model.getDictcode())){
			sbHql.append(" and t.dictcode like ? ");
			lPara.add("%"+model.getDictcode()+"%");
		}
		sbHql.append(" order by t.sortno ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	/**
	 * 
	 * @描述: 删除选中的字典类型
	 * @param ids
	 */
	public void deleteDicttypes(String[] ids){
		dao.deleteByIds(TAcDicttype.class, ids);
	}
	/**
	 * 
	 * @描述: 删除选中的字典明细
	 * @param ids
	 */	
	public void deleteDictdetas(String[] ids){
		dao.deleteByIds(TAcDictdeta.class, ids);
	}
	
	/**
	 * 检查字典编码是否唯一.
	 *
	 * @return dicttypecode在数据库中唯一或等于oldDicttypecode时返回true.
	 */
	public boolean isDicttypecodeUnique(String newDicttypecode, String oldDicttypecode) {
		return dao.isPropertyUnique(TAcDicttype.class,"dicttypecode", newDicttypecode, oldDicttypecode);
	}
}
