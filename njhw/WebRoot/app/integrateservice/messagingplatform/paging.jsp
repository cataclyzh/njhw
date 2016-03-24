<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = request.getParameter("pageurl");
%>
<c:if test='${page!=null}'>
	<!--第一种翻页类型开始-->

	<table align='center' border="0" width="100%">
		<tr>
			<td nowrap style="font-size: 12px;border:0;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr align="right">
						
						<td valign="middle" height="28px;" style="font-size: 12px;border:0;" nowrap>
						
							共
							<font color="#CB5C0B">${page.pageNo}/${page.totalPages}</font>页&nbsp;&nbsp;
							<!--每页${pageDao.pageSize}条&nbsp;&nbsp;-->
							<c:if test='${page.pageNo > 1}'>
							
		 						<a href='javascript:previousPage()'>上一页</a>&nbsp;&nbsp;
		 					</c:if>
							<c:if test='${page.pageNo==1}'>
		  				 	
		   						上一页&nbsp;&nbsp;
		 					</c:if>
							<c:if test='${page.pageNo==page.totalPages}'>
		   							下一页&nbsp;&nbsp;
		   					
		 					</c:if>
							<c:if test='${page.pageNo < page.totalPages}'>
								<a href='javascript:behindPage()'>下一页</a>&nbsp;&nbsp;
		 					
		 					</c:if>
						</td>
                  </tr>
				</table>

			</td>
		</tr>
	</table>
	<script language="javascript">
  //第一页
  	function firstPage(){
  		goPage(1);
  	}
  	//最后一页
  	function lastPage(){
  		goPage(${page.totalPages});
  	}
  	//前一页
  	function previousPage(){
  		goPage(${page.pageNo-1});
  	}
  	//后一页
  	function behindPage(){
  		goPage("${page.pageNo+1}");
  	}
  //跳转
    function GOPage(position){
   		var re = /^[1-9]*[1-9][0-9]*$/ ;//正整数正则表达式(不带双引号)
     	var obj = null;
	    obj=document.getElementById("gopageNo");
	  if(!re.test(obj.value)){
	    alert("请输入一个正整数!");
	  }else if(parseInt(obj.value)<=0 || parseInt(obj.value)>${page.totalPages}){
	    alert("请输入范围内的页码！");
	  }else if(parseInt(obj.value)==${page.pageNo}){
	    alert("已在当前页！");
	  }else{
	     var pageNo = obj.value.replace(/(^\s*)|(\s*$)/g, "");
	     goPage(pageNo);
	  }
	}
	
 </script>
</c:if>