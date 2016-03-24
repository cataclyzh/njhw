package com.cosmosource.common.action;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cosmosource.base.action.BaseAction;
import com.cosmosource.base.util.Constants;
import com.cosmosource.base.util.Struts2Util;
/**
 * 是否使用签章,签章序列号是否正确Ajax调用   Action
 * @author dawn
 *
 * @param <T>
 */
public class VerdictIsUseCaAction<T> extends BaseAction<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5039728736429896204L;

	@Override
	protected void prepareModel() throws Exception {
		
	}

	public T getModel() {
		return null;
	}
	/**
	 * 判断用户对应操作是否需要key  isuseca取得值为0表示需要
	 * @return
	 */
	public String isUseCa(){
		
		Map<String,String> caactionmap = Constants.CAACTIONMAP;
		String loginname = getParameter("loginname");
		String actioncode = getParameter("actioncode");
		
		String isuseca = caactionmap.get(loginname+actioncode);
		if(StringUtils.isEmpty(isuseca)){
			Struts2Util.renderText("");
		} else {
			Struts2Util.renderText(isuseca);
		}
		return null;
	}
	/**
	 * 判断用户所使用可以信息是否正确  isrightca取得值为0表示通过
	 * @return
	 */
	public String isRightCa(){
		
		Map<String,String> causermap = Constants.CAUSERMAP;
		String loginname = getParameter("loginname");
		String cano = getParameter("cano");
		
		String isrightca = causermap.get(loginname+cano);
		if(StringUtils.isEmpty(isrightca)){
			Struts2Util.renderText("");
		} else {
			Struts2Util.renderText(isrightca);
		}
		return null;
	}
}
