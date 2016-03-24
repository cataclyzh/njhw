package com.cosmosource.app.taglib;import java.io.IOException;import java.util.Enumeration;import javax.servlet.http.HttpServletRequest;import javax.servlet.jsp.JspException;import javax.servlet.jsp.tagext.TagSupport;


/**
 * 分页标签处理类
 */
public class PagerTag extends TagSupport {
	private static final long serialVersionUID = 5729832874890369508L;
	private String url;         //请求URI
	private int pageSize = 10;  //每页要显示的记录数
	private int pageNo = 1;     //当前页号
	private int recordCount;    //总记录数
    private int recordShowNum = 4;
	public int getRecordShowNum() {
		return recordShowNum;
	}

	public void setRecordShowNum(int recordShowNum) {
		this.recordShowNum = recordShowNum;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		int pageCount = (recordCount + pageSize - 1) / pageSize;  //计算总页数
		
		//拼写要输出到页面的HTML文本
		StringBuilder sb = new StringBuilder();
		
		
//		sb.append("<style type=\"text/css\">");
//		sb.append(".pagination {padding: 5px;float:right;font-size:12px;}");
//		sb.append(".pagination a, .pagination a:link, .pagination a:visited {padding:2px 5px;margin:2px;border:1px solid #aaaadd;text-decoration:none;color:#006699;}");
//		sb.append(".pagination a:hover, .pagination a:active {border: 1px solid #ff0000;color: #000;text-decoration: none;}");
//		sb.append(".pagination span.current {padding: 2px 5px;margin: 2px;border: 1px solid #ff0000;font-weight: bold;background-color: #ff0000;color: #FFF;}");
//		sb.append(".pagination span.disabled {padding: 2px 5px;margin: 2px;border: 1px solid #eee; color: #ddd;}");
//		sb.append("</style>\r\n");
		sb.append("<div class=\"pagination main_right_bottom_bottom\" >\r\n");
		sb.append("<div class=\"main_peag\">\r\n");
		if(recordCount == 0){
			sb.append("<strong>没有可显示的项目</strong>\r\n");
		}else{
			//页号越界处理
			if(pageNo > pageCount){		pageNo = pageCount;	}
			if(pageNo < 1){		pageNo = 1;	}
			
			sb.append("<form method=\"post\" action=\"").append(this.url)
				.append("\" name=\"qPagerForm\">\r\n");
			
			//获取请求中的所有参数
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			Enumeration<String> enumeration = request.getParameterNames();
			String name = null;  //参数名
			String value = null; //参数值
			//把请求中的所有参数当作隐藏表单域
			while (enumeration.hasMoreElements()) {
				name =  enumeration.nextElement();
				value = request.getParameter(name);
				// 去除页号
				if (name.equals("pageNo")) {
					if (null != value && !"".equals(value)) {
						pageNo = Integer.parseInt(value);
					}
					continue;
				}
				sb.append("<input type=\"hidden\" name=\"")
				  .append(name)
				  .append("\" value=\"")
				  .append(value)
				  .append("\"/>\r\n");
			}
	
			// 把当前页号设置成请求参数
			sb.append("<input type=\"hidden\" name=\"").append("pageNo")
				.append("\" value=\"").append(pageNo).append("\"/>\r\n");
			
			// 输出统计数据
//			sb.append("<span class=\"current_left\">&nbsp;共<strong>").append(recordCount)
//				.append("</strong>项")
//				.append(",<strong>")
//				.append(pageCount)
//				.append("</strong>页:&nbsp;</span>\r\n");
			
			//上一页处理  首页处理
			if (pageNo == 1) {
				sb.append("<span class=\"disabled\">&lt;")
					.append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:goPage(")
				  .append((1))
				  .append(")\">&lt;&lt;</a>\r\n");
				
				sb.append("<a href=\"javascript:goPage(")
				  .append((pageNo - 1))
				  .append(")\">&lt;</a>\r\n");
				
			}
			
			//如果前面页数过多,显示"..."
			int start = 1; 
			int rag = 0;
			if(recordShowNum % 2 == 0){
				rag = recordShowNum/2 - 1;			
			}else{
				rag = recordShowNum/2;
			}
			if(this.pageNo > 4){
				if(this.pageNo > rag){
					start = this.pageNo - rag;
				}else{
					start = this.pageNo - 1;
				}
				sb.append("<a href=\"javascript:goPage(1)\">1</a>\r\n");
				sb.append("<a href=\"javascript:goPage(2)\">2</a>\r\n");
				sb.append("<a href=\"#\"\">");
				sb.append("&hellip;");
				sb.append("</a>\r\n");
			}
			//显示当前页附近的页
			int end = this.pageNo + recordShowNum/2;
			if(end > pageCount){
				end = pageCount;
			}
			for(int i = start; i <= end; i++){
				if(pageNo == i){   //当前页号不需要超链接
					sb.append("<span class=\"current\">")
						.append(i)
						.append("</span>\r\n");
				}else{
					sb.append("<a href=\"javascript:goPage(")
						.append(i)
						.append(")\">")
						.append(i)
						.append("</a>\r\n");
				}
			}
			//如果后面页数过多,显示"..."
			if(end < pageCount - 2){
				sb.append("<a href=\"#\"\">");
				sb.append("&hellip;");
				sb.append("</a>\r\n");
			}
			if(end < pageCount - 1){
				sb.append("<a href=\"javascript:goPage(")
				.append(pageCount - 1)
				.append(")\">")
				.append(pageCount - 1)
				.append("</a>\r\n");
			}
			if(end < pageCount){
				sb.append("<a href=\"javascript:goPage(")
				.append(pageCount)
				.append(")\">")
				.append(pageCount)
				.append("</a>\r\n"); 
			}
			
			//下一页处理
			if (pageNo == pageCount) {
				sb.append("<span class=\"disabled\">&gt;")
					.append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:goPage(")
					.append((pageNo + 1))
					.append(")\">&gt;</a>\r\n");

				sb.append("<a href=\"javascript:goPage(")
				.append((pageCount))
				.append(")\">&gt;&gt;</a>\r\n");
			}
			sb.append("</form>\r\n");
	
			// 生成提交表单的JS
			sb.append("<script language=\"javascript\">\r\n");
			sb.append("  function turnOverPage(no){\r\n");
			sb.append("    if(no>").append(pageCount).append("){");
			sb.append("      no=").append(pageCount).append(";}\r\n");
			sb.append("    if(no<1){no=1;}\r\n");
			sb.append("    document.qPagerForm.pageNo.value=no;\r\n");
			sb.append("    document.qPagerForm.submit();\r\n");
			sb.append("  }\r\n");
			sb.append("</script>\r\n");
		}
		sb.append("</div>\r\n");
		sb.append("</div>\r\n");
		
		//把生成的HTML输出到响应中
		try {
			pageContext.getOut().println(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;  //本标签主体为空,所以直接跳过主体
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
}