package com.holytax.base.tags;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cosmosource.base.service.Page;

/**
 *	通用panel的设置
 * @author WXJ
 */
@SuppressWarnings({"rawtypes"})
public class PanelTag extends TagSupport {

	private static final long serialVersionUID = -1017618325149357862L;
	private String id = null;
	private String width = null;
	private String title = null;
	private String pageName = null;
	private String panelButton = null;
	private String hideTableId = null;
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().print(this.getPanelStart());
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().print(this.getPanelEnd());
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
		return EVAL_PAGE;		
	}
	/**
	 * 
	 * 生成Panel
	 * 
	 **/	
	
	private String getPanelStart() {
		if(pageName==null){
			pageName = "page";
		}
		if(panelButton == null){
			panelButton = "panel1_button";
		}
		if(hideTableId == null){
			hideTableId = "hide_table";
		}
		Page page = (Page)pageContext.getRequest().getAttribute(pageName);
		StringBuilder buf = new StringBuilder();	
		if(page!=null){
			buf.append("<input type=\"hidden\" name=\""+pageName+".togglestatus\" id=\"togglestatus\" value=\""+page.getTogglestatus()+"\"/>");
		}
		
		buf.append("<script type=\"text/javascript\">");
		buf.append("$(function(){");
		buf.append("ToggelPanel(\""+panelButton+"\",\""+hideTableId+"\");");
		buf.append("});");
		buf.append("</script>");
		buf.append("<table border=\"0\" id=\""+this.getId()+"\" width=\""+this.getWidth()+"\"  cellspacing=\"0\" cellpadding=\"0\" class=\"panel-table\">");
		buf.append("<tr>");
		buf.append("<td height=\"20\">");
		buf.append("<table  border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
		buf.append("<tr>");
		buf.append("<td width=\"4px\"><div class=\"panel-title-left\"></div></td>");
		buf.append("<td width=\"20px\"class=\"panel-title\"><div id=\""+panelButton+"\" class=\"PANEL_OPEN_BUTTON\"></div></td>");
		buf.append("<td class=\"panel-title\">"+this.getTitle()+"</td>");
		buf.append("</tr>");
		buf.append("</table>");
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("<tr>");
		buf.append("<td  class=\"panel-body\"  valign=\"top\" >");
		return buf.toString();
	}
	/**
	 * 
	 * 生成Panel
	 * 
	 **/	
	private String getPanelEnd() {
		StringBuilder buf = new StringBuilder();
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("</table>");   
		return buf.toString();
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPanelButton() {
		return panelButton;
	}

	public void setPanelButton(String panelButton) {
		this.panelButton = panelButton;
	}

	public String getHideTableId() {
		return hideTableId;
	}

	public void setHideTableId(String hideTableId) {
		this.hideTableId = hideTableId;
	}
	
	
}
