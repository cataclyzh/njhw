package com.cosmosource.base.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cosmosource.common.entity.TAcDictdeta;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2中典型Action的抽象基类.
 * 
 * 主要定义了对Preparable,ModelDriven接口的使用
 *
 * @param <T> BaseAction所管理的对象类型.
 * 
 * @author WXJ
 */
@SuppressWarnings("serial")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>, Preparable {

	/** 进行增删改操作后,以redirect方式重新打开action默认页的result名.*/
	public static final String RELOAD = "reload";
	//进入修改页面
	public static final String EDIT = "edit";
	//进入查询明细页面
	public static final String DETAIL = "detail";
	//进入初始页面
	public static final String INIT = "init";
	//进入列表页面
	public static final String LIST = "list";
	
	public String isSuc;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	//-- Preparable函数 --//
	/**
	 * 实现空的prepare()函数,屏蔽所有Action函数公共的二次绑定.
	 */
	public void prepare() throws Exception {
	}

	/**
	 * 等同于prepare()的内部函数,供prepardMethodName()函数调用. 
	 */
	protected abstract void prepareModel() throws Exception;
	
	/**
	 * 在input()前执行二次绑定.
	 */
	public void prepareInput() throws Exception {
		prepareModel();
	}

	/**
	 * 在detail()前执行二次绑定.
	 */
	public void prepareDetail() throws Exception {
		prepareModel();
	}
	/**
	 * 在edit()前执行二次绑定.
	 */
	public void prepareEdit() throws Exception {
		prepareModel();
	}
	/**
	 * 在edit()前执行二次绑定.
	 */
	public void prepareUpdate() throws Exception {
		prepareModel();
	}
	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareSave() throws Exception {
		prepareModel();
	}

	/**
	 * 取得HttpSession的简化函数.
	 */
	public  HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpSession的简化函数.
	 */
	public  HttpSession getSession(boolean isNew) {
		return ServletActionContext.getRequest().getSession(isNew);
	}

	/**
	 * 取得HttpSession中Attribute的简化函数.
	 */
	public  Object getSessionAttribute(String name) {
		HttpSession session = getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public  HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpRequest中Parameter的简化方法.
	 */
	public  String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public  HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	/**
	 * 取得ctx path的简化函数.
	 */
	public String getContextPath() {
		return getRequest().getContextPath();
	}
	/**
	 * 取得HttpRequest的简化函数.
	 */
	public  ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}
	
	/**
	 * 字典转换,从缓存中取值.
	 */
	@SuppressWarnings("rawtypes")
	public String getDictNameByIdFromCache(String dictTypeId,String value) {
		if(StringUtils.isEmpty(value)){
			return "";
		}
		
    	List obj = (List)getServletContext().getAttribute("DICT_"+dictTypeId);
    	if(obj!=null&&obj.size()>0){
    		int j = obj.size();
    		TAcDictdeta dictBean = new TAcDictdeta();
    		for(int i=0;i<j;i++){
    			dictBean = (TAcDictdeta)obj.get(i);
    			if((value).equals(dictBean.getDictcode())){
    				return dictBean.getDictname();
    			}
    		}
    	}
		return value;
	}
	
	public String getIsSuc() {
		return isSuc;
	}

	public void setIsSuc(String isSuc) {
		this.isSuc = isSuc;
	}
	
}
