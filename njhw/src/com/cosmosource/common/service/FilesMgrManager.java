/**
* <p>文件名: FilesMgrManager.java</p>
* <p>描述：文件下载Manager</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2011-3-4 下午05:27:12 
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
import com.cosmosource.common.entity.TCommonFileDownload;

/**
 * @类描述:  文件下载
 * 
 * @作者： WXJ
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class FilesMgrManager extends BaseManager{
	
	/**
	 * 新增或是更新保存对象
	 * @param entity
	 */
	public void saveFiles(TCommonFileDownload entity){
		dao.saveOrUpdate(entity);
	}
	/**
	 * 查询列表数据
	 * @param page
	 * @param model
	 * @return
	 */
	public Page<TCommonFileDownload> queryFiles(final Page<TCommonFileDownload> page, final TCommonFileDownload model) {
		List lPara = new ArrayList();
	
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select t from TCommonFileDownload t ")
		.append("where 1=1 ");
		if(StringUtil.isNotBlank(model.getSofttype())){
			sbHql.append(" and t.softtype = ? ");
			lPara.add(model.getSofttype());
		}
		if(StringUtil.isNotBlank(model.getFiletype())){
			sbHql.append(" and t.filetype = ? ");
			lPara.add(model.getFiletype());
		}
		if(StringUtil.isNotBlank(model.getOrgtype())){
			sbHql.append(" and t.orgtype = ? ");
			lPara.add(model.getOrgtype());
		}
		if(StringUtil.isNotBlank(model.getSoftver())){
			sbHql.append(" and t.softver like ? ");
			lPara.add(model.getSoftver());
		}	
		if(StringUtil.isNotBlank(model.getCompany())){
			sbHql.append(" and (t.company = ? or t.company='general')");
			lPara.add(model.getCompany());
		}	
		sbHql.append(" order by t.releasedate ");

		return dao.findPage(page,sbHql.toString(),lPara.toArray());
	}
	public void deleteFiles(String[] ids){
		dao.deleteByIds(TCommonFileDownload.class, ids);
	}
	
	
}
