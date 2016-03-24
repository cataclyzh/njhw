/**
 * <p>文件名: ExcelToDateTemplete.java</p>
 * <p>:描述：(用一句话描述该文件做什么)</p>
 * <p>版权: Copyright (c) 2010 Beijing Holytax Co. Ltd.</p>
 * <p>公司: Holytax Beijing Office</p>
 * <p>All right reserved.</p>
 * @创建时间： 2012-4-24 下午05:44:21 
 * @作者： hp
 * @版本： V1.0
 * <p>类修改者		 修改日期			修改说明   </p>   
 * 
 */

package com.cosmosource.common.service;

import java.util.List;

import com.cosmosource.app.entity.Users;
import com.simple.tool.imp.IDataImporter;
import com.simple.tool.imp.IImportSource;
import com.simple.tool.imp.excel.ExcelDataLoader;
import com.simple.tool.imp.excel.ExcelSource;
import com.simple.tool.imp.model.Mapping;
import com.simple.tool.imp.service.ImportException;

/**
 * @类描述: 发票导入的manager类
 * @作者： hp
 */
public class ExcelToDateTemplete implements IDataImporter {
	
	private ImportManager importManager;
	@SuppressWarnings("unchecked")
	public void out(Object obj, IImportSource iimportsource) throws Exception {
		List list = (List) obj;
		try {
			importManager.save(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("导入的数据重复!");
		}
	}

	public ImportManager getImportManager() {
		return importManager;
	}

	public void setImportManager(ImportManager importManager) {
		this.importManager = importManager;
	}

}
