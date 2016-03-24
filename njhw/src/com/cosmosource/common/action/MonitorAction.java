/**
* <p>文件名: MonitorAction.java</p>
* <p>描述：监控应用Action</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2012-7-29 上午11:10:24 
* @作者：WXJ
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.common.action;

import java.sql.Timestamp;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.DateUtil;
import com.cosmosource.base.util.Struts2Util;
import com.cosmosource.common.service.ComManager;

/**
 * @类描述: 监控应用Action
 * @作者： WXJ
 */
public class MonitorAction extends BaseAction<Object> {
	
	private static final long serialVersionUID = -8624881342121965306L;
	
	private ComManager comMgr;

	public Object getModel() {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
	}
	
	/**
	 * @描述:取数据库时间
	 * @作者：WXJ
	 * @日期：2012-7-29
	 * @return
	 * @throws Exception
	 */
	public String getDBTime(){
		Timestamp dbTime = comMgr.getDBTime();
		String strTime = DateUtil.date2Str(dbTime, "yyyyMMddHHmmss");
		Struts2Util.renderText(strTime,"encoding:gb2312", "no-cache:true", "DBTime:"+ strTime);
		return null;
	}

	public ComManager getComMgr() {
		return comMgr;
	}

	public void setComMgr(ComManager comMgr) {
		this.comMgr = comMgr;
	}
	
}
