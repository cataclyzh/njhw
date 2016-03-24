package com.holytax.base.tags;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cosmosource.base.service.Page;
import com.cosmosource.base.util.JsonUtil;
import com.cosmosource.base.util.StringUtil;

/**
 *
 *	分页Tag，包括checkbox，及分页导航的设置，隐藏参数的设置
 * 
 * @author WXJ
 */
@SuppressWarnings({"rawtypes"})
public class PageTag extends TagSupport {

	private static final long serialVersionUID = -1017618325149357862L;
	private String id = null;
	private String width = null;
	private String title = null;
	private String buttons = null;
	private String chkId = null;
	private String pageName = null;
	private String btt = null;
	private String customButton = null;
	private int recordShowNum = 4;
	private int rag = 1;
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
		Page page = (Page)pageContext.getRequest().getAttribute(pageName);
	    org.displaytag.decorator.CheckboxTableDecorator decorator = new org.displaytag.decorator.CheckboxTableDecorator();
	    if(StringUtil.isNotBlank(chkId)){
	    	decorator.setId(chkId);
	    }
        decorator.setFieldName("_chk");
        pageContext.setAttribute("checkboxDecorator", decorator);
        StringBuilder buf = new StringBuilder();
        if(page!=null){
        	buf.append("<input type=\"hidden\" name=\""+pageName+".pageNo\" id=\"pageNo\" value=\""+page.getPageNo()+"\"/>");
        	buf.append("<input type=\"hidden\" name=\""+pageName+".totalPages\" id=\"totalPages\" value=\""+page.getTotalPages()+"\"/>");
        }
		
		buf.append("<table border=\"0\" id=\""+this.getId()+"\" width=\""+this.getWidth()+"\"  cellspacing=\"0\" cellpadding=\"0\" class=\"panel-table\">");
		buf.append("<tr>");
		buf.append("<td height=\"20\">");
		buf.append("<table  border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
		buf.append("<tr>");
		buf.append("<td width=\"4px\"><div class=\"panel-title-left\"></div></td>");
		buf.append("<td width=\"20px\"class=\"panel-title\"></td>");
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
		if(pageName==null){
			pageName = "page";
		}
		Page page = (Page)pageContext.getRequest().getAttribute(pageName);
		StringBuilder buf = new StringBuilder();
		buf.append("<div align=\"left\">");
		buf.append("<table width=\"100%\">");
		buf.append("<tr bgcolor=\"#E0E3EA\">");
		
		buf.append("<td height=\"37px\" id=\"main_peag\">");
//		buf.append("<div style=\"height:37px;width:100%;text-align:left;\">");
//		buf.append("<div style=\"width:20%;text-align:left;padding-top:5px;float:left;position:absolute;z-index:1;\">");
		
		//自定义buttons
		if(customButton!=null){
			List<Map<String, String>> listBut = JsonUtil.jsonToList(customButton);
			if(listBut!=null){
				for(Map<String, String> mapBut:listBut){
					buf.append("<a href=\"#\"  class=\"easyui-linkbutton\" ");
					if(mapBut.get("function")!=null){
						buf.append(" onclick=\"javascript:"+mapBut.get("function")+"\"> ");
					}
					
					if(mapBut.get("name")!=null){
						buf.append(mapBut.get("name")+"</a>&nbsp;");
					}else{
						buf.append("按钮</a>&nbsp;");
					}
				}
			}
		}
		if(buttons!=null){
			StringTokenizer st = new StringTokenizer(buttons,",");
			while (st.hasMoreTokens()) { 
				String token = st.nextToken();
				if("add".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\"  onclick=\"javascript:addRecord();\">新增</a>&nbsp;");
				}else if("del".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\" onclick=\"javascript:delRecord();\">删除</a>&nbsp;");
				}else if("confirm".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\" onclick=\"javascript:confirmRecord();\">确认</a>&nbsp;");
				}else if("unmatch".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\" onclick=\"javascript:unmatch();\">取消匹配</a>&nbsp;");
				}else if("audit".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\" onclick=\"javascript:audit();\">审核</a>&nbsp;");
				}else if("edit".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\" onclick=\"javascript:edit();\">修改</a>&nbsp;");
				}else if("save".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\" onclick=\"javascript:save();\">保存</a>&nbsp;");
				}else if("sign".equals(token)){
					buf.append("<a href=\"#\" class=\"easyui-linkbutton\" onclick=\"javascript:sign();\">签收</a>&nbsp;");
				}else if("setadmin".equals(token)){
					buf.append("<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" onclick=\"javascript:modifySMA(\'s\');\">设定</a>&nbsp;");
				}else if("cancleadmin".equals(token)){
					buf.append("<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" onclick=\"javascript:cancleAdmin();\">设定</a>&nbsp;");
				}

			}
		}
		if(btt!=null&&pageContext.getRequest().getAttribute(btt)!=null){
			buf.append((String)pageContext.getRequest().getAttribute(btt));
		}
		
//		buf.append("</div>");
//		buf.append("<div class='main_peag' style=\"text-align:center;position:absolute;z-index:0;\"align=\"center\">");
		if(page!=null&&page.getTotalPages()>0){
			int pageCount = (int)page.getTotalPages();
//			buf.append("共"+page.getTotalCount()+"条记录,第"+page.getPageNo()+"页, 共"+pageCount+"页  ");
			//buf.append("<button onclick=\"javascript:jumpPage(1)\" class=\"icon-xbjbutton\">首页</button>");
			int pageNo = page.getPageNo();
			//上一页处理  首页处理
			if (pageNo == 1) {
				buf.append("<span class=\"disabled\" id=\"firstPageTag\">&lt;")
					.append("</span>\r\n");
			} else {
				buf.append("<a class=\"page_a\" id=\"firstPageTag\" href=\"javascript:jumpPage(")
				  .append((1))
				  .append(")\">&lt;&lt;</a>\r\n");
				
				buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(")
				  .append((pageNo - 1))
				  .append(")\">&lt;</a>\r\n");
				
			}
			int start = 1; 
			if(pageNo > 4){
				if(pageNo > rag){
					start = pageNo - rag;
				}else{
					start = pageNo - 1;
				}
				buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(1)\">1</a>\r\n");
				buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(2)\">2</a>\r\n");
				buf.append("<a class=\"page_a\" href=\"#\"\">");
				buf.append("&hellip;");
				buf.append("</a>\r\n");
			}
			int end = pageNo + recordShowNum/2;
			if(end > pageCount){
				end = pageCount;
			}
			for(int i = start; i <= end; i++){
				if(pageNo == i){   //当前页号不需要超链接
					buf.append("<span class=\"current\">")
						.append(i)
						.append("</span>\r\n");
				}else{
					buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(")
						.append(i)
						.append(")\">")
						.append(i)
						.append("</a>\r\n");
				}
			}
			//如果后面页数过多,显示"..."
			if(end < pageCount - 2){
				buf.append("<a class=\"page_a\" href=\"#\"\">");
				buf.append("&hellip;");
				buf.append("</a>\r\n");
			}
			if(end < pageCount - 1){
				buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(")
				.append(pageCount - 1)
				.append(")\">")
				.append(pageCount - 1)
				.append("</a>\r\n");
			}
			if(end < pageCount){
				buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(")
				.append(pageCount)
				.append(")\">")
				.append(pageCount)
				.append("</a>\r\n"); 
			}
			
			//下一页处理
			if (pageNo == pageCount) {
				buf.append("<span class=\"disabled\">&gt;")
					.append("</span>\r\n");
			} else {
				buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(")
					.append((pageNo + 1))
					.append(")\">&gt;</a>\r\n");

				buf.append("<a class=\"page_a\" href=\"javascript:jumpPage(")
				.append((pageCount))
				.append(")\">&gt;&gt;</a>\r\n");
			}
//			buf.append("<a href=\"#\" title=\"首页\" class=\"easyui-linkbutton\"  plain=\"true\"  iconCls=\"icon-first\" onclick=\"javascript:jumpPage(1)\"></a>");
//			if(page.isHasPre()){
////				buf.append("<button onclick=\"javascript:jumpPage("+page.getPrePage()+")\" class=\"icon-xbjbutton\">上页</button> ");
//				buf.append("<a href=\"#\" title=\"上一页\" class=\"easyui-linkbutton\"  plain=\"true\"  iconCls=\"icon-prev\" onclick=\"javascript:jumpPage("+page.getPrePage()+")\"></a>");
//			}else{
//				buf.append("<a href=\"#\" title=\"上一页\" disabled=\"true\" class=\"easyui-linkbutton\"  plain=\"true\"  iconCls=\"icon-prev\" onclick=\"javascript:jumpPage("+page.getPrePage()+")\"></a>");
//			}
//			if(page.isHasNext()){
////				buf.append(" <button onclick=\"javascript:jumpPage("+page.getNextPage()+")\" class=\"icon-xbjbutton\">下页</button>");
//				buf.append("<a href=\"#\"  title=\"下一页\" class=\"easyui-linkbutton\"  plain=\"true\"  iconCls=\"icon-next\" onclick=\"javascript:jumpPage("+page.getNextPage()+")\"></a>");
//			}else{// disabled="true" 
//				buf.append("<a href=\"#\"  title=\"下一页\" disabled=\"true\"  class=\"easyui-linkbutton\"  plain=\"true\"  iconCls=\"icon-next\" onclick=\"javascript:jumpPage("+page.getNextPage()+")\"></a>");
//			}
////			buf.append("<button onclick=\"javascript:jumpPage("+page.getTotalPages()+")\" class=\"icon-xbjbutton\" >尾页</button>");
//			buf.append("<a href=\"#\" title=\"尾页\" class=\"easyui-linkbutton\"  plain=\"true\"  iconCls=\"icon-last\" onclick=\"javascript:jumpPage("+page.getTotalPages()+")\"></a>");
////			buf.append("<button onclick=\"javascript:checkJumpNumber($('#jumpNumber').val())\" class=\"icon-xbjbutton\" name=\"jumpButton\">转到</button>");
//			buf.append("<a href=\"#\" title=\"转到\" class=\"easyui-linkbutton\"  plain=\"true\"  iconCls=\"icon-redo\" onclick=\"javascript:checkJumpNumber($('#jumpNumber').val())\"></a>");
//			buf.append("<input type=\"text\" name=\""+pageName+".jumpNumber\" value=\""+page.getPageNo()+"\" maxlength=\"7\" size=\"2\" style=\"width:35px\" id=\"jumpNumber\" onkeyup=\"value=value.replace(/[^\\d]/g,'');\"/>页");	
		}
//		buf.append("</div>");
//		buf.append("</div>");
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("</table>");
		buf.append("</div>");
		buf.append("</td>");
		buf.append("</tr>");
		buf.append("</table>");
		
//		buf.append("<br><br>");
		buf.append("<script type=\"text/javascript\">");
//		buf.append("$('.main_peag').width($('.panel-table').width())");
		buf.append("$.each($('#main_peag .easyui-linkbutton'), function(i, n){");
		buf.append("$(n).css({'float':'left', 'margin-top':'5px', 'margin-left':'5px'});");
		buf.append("});");
		buf.append("$.each($('#main_peag span'), function(i, n){");
		buf.append("$(n).css({'float':'left'});");
		buf.append("});");
		buf.append("$.each($('#main_peag .page_a'), function(i, n){");
		buf.append("$(n).css({'float':'left'});");
		buf.append("});");
		buf.append("var pTageNum = $('#main_peag span').length + $('#main_peag .page_a').length;");
		buf.append("var leftM = $('#main_peag').width()/2 - 17*pTageNum + 5;");
		if(StringUtil.isNotBlank(buttons)){
			buf.append("leftM = leftM - $('#main_peag .easyui-linkbutton').length*65;");
		}
		buf.append("$('#main_peag #firstPageTag').css({'margin-left': leftM});");
		buf.append("</script>");
		return buf.toString();
	}
	public static void main(String[] args) {
		String abcd = "[{\"name\"\"修改\",\"function\":\"update();\",\"class\":\"icon-dbjbutton\"}]";
		System.out.println(JsonUtil.jsonToList(abcd).get(0));
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

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public String getChkId() {
		return chkId;
	}

	public void setChkId(String chkId) {
		this.chkId = chkId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getBtt() {
		return btt;
	}

	public void setBtt(String btt) {
		this.btt = btt;
	}

	public String getCustomButton() {
		return customButton;
	}

	public void setCustomButton(String customButton) {
		this.customButton = customButton;
	}
	
}
