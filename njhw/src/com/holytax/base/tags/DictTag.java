package com.holytax.base.tags;
import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cosmosource.common.entity.TAcDictdeta;


/**
 *
 *	根据字典类型和字典编号，显示字典对应名称
 * 
 * @author WXJ
 */
public class DictTag extends TagSupport {

	private static final long serialVersionUID = -1017618325149357862L;
	/**
	 * 字典类型
	 */
	private String dicttype = null;
	
	/**
	 * 字典编号
	 */
	private String dictid = null;
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print(this.getDictName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;		
	}
	
	/**
	 * @描述: 通过字典类型及字典ID取得字典名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getDictName() {
    	List<TAcDictdeta> dictList = (List<TAcDictdeta>)pageContext.getServletContext().getAttribute("DICT_" + dicttype);
    	if(dictList != null && !dictList.isEmpty()){
    		for(TAcDictdeta dictBean : dictList){
    			if(dictBean.getDictcode().equals(dictid)){
    				return dictBean.getDictname();
    			}
    		}
    	}
		
		return "";
	}

	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getDictid() {
		return dictid;
	}

	public void setDictid(String dictid) {
		this.dictid = dictid;
	}
	
	
}
