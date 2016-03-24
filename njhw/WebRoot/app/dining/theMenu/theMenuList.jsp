<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 菜单发布管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>菜单发布列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});

		function reset(){
			var cbox=$("input:checked[name='_chk']");        
			$.each(cbox, function(i,item){
			   	if($(this).parent().parent().attr("class") == "oddselected"){
			   		$(item).parent().parent().attr("class","odd");
			   	}
				if($(this).parent().parent().attr("class") == "evenselected"){
			 		$(item).parent().parent().attr("class","even");
				}					    
		    });	
		}


		//发布菜单
		function saveData(){
			var ids = "";
			$("#pageDiv input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("checked")=="checked")	{		
					ids += $(this).val()+",";
				}
			});
			  $("#fdiFlagFlag").val(${fdiFlag});
			  $("#fabufdiIdact").val($("#fabufdiId").val());
			  $("#fdiTypeType").val(${fdiType});
			  $("#cfdiId").val(ids);
			  $("#inputForm").ajaxSubmit({   
                    type: 'post',   
                    url: "theMenuSave.act" ,
                    success: function(data){   
                  		alert("发布成功！");
                      },   
                      error: function(XmlHttpRequest, textStatus, errorThrown){   
                         alert( "error");   
                      }   
               });  
			
		}

		// 选中全部
		function chk_all() {
			$("#pageDiv input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("disabled")!="disabled")	{		
					if ($("#phone_check_all").attr("checked") == "checked"){
						$(item).attr("checked", "true");
					} else {
						$(item).removeAttr("checked");
					}
				}
			});
		}

		/**
		* 实现翻页的方法
		*/
		function goPage(pageNo){
			$("#page_search_form input[id='pageNo']").val(pageNo);
			var orgId = $("#page_search_form input[id='orgId']").val();
			var url = $("#page_search_form").attr("action");
			var sucFun = function (data){
				$("#pageDiv").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data =  "pageNo="+pageNo+"&orgId="+orgId;
			ajaxQueryHTML(url,data,sucFun,errFun);
		}
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="queryTheMenu.act" method="post"  autocomplete="off">
	<s:hidden name="FDI_ID" id="FDI_ID"/>
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
      <tr>
    	<td colspan="5" class="form_label" width="100%">
    		<a href="javascript:void(0);" style="color:#330000;" onclick="javascript:parent.open_main3('10208' ,'菜单管理' ,'${ctx}/app/din/queryDishes.act');">[菜单管理]</a>
    	</td>
   	  </tr>
        <tr>
          <td class="form_label" width="20%" align="left">
           	星期：
          </td>
          <td width="30%">
           <s:select list="#{'1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六','7':'星期日'}" listKey="key" listValue="value" onchange="javaScript:querySubmit();" name="fdiType"/>  
          </td>
          <td class="form_label" width="20%" align="left">
           	三餐：
          </td>
          <td width="22%">
          	<s:select list="#{'1':'早餐','2':'午餐','3':'晚餐'}" listKey="key" listValue="value" name="fdiFlag" onchange="javaScript:querySubmit();"/>
          </td>
          <%--
          <td class="form_label" width="20%" align="left">
           	类型：
          </td>
          <td width="30%">
          	<s:select list="#{'0':'全部','1':'大荤','2':'小荤','3':'主食','4':'饮料','5':'素菜'}" listKey="key" listValue="value" name="fdClass"/>
          </td>
         
          <td class="form_label" width="20%" align="left">
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          </td>	
          --%>
        </tr>     
      </table>      
</form>
    <table align="center" border="0" width="100%" class="form_table">
	<tr>
        <td class="form_label">发布列表：</td>
        <td colspan="5">
       		    <c:if test="${fdiType == '1' }">
            		星期一
            	</c:if>
            	<c:if test="${fdiType == '2' }">
            		星期二
            	</c:if>
            	<c:if test="${fdiType == '3' }">
            		星期三
            	</c:if>
            	<c:if test="${fdiType == '4' }">
            		星期四
            	</c:if>
            	<c:if test="${fdiType == '5' }">
            		星期五
            	</c:if>
            	<c:if test="${fdiType == '6' }">
            		星期六
            	</c:if>
            	<c:if test="${fdiType == '7' }">
            		星期日
            	</c:if>
            	<c:if test="${fdiFlag  == '1' }">
            		早餐
            	</c:if>
            	<c:if test="${fdiFlag  == '2' }">
            		午餐
            	</c:if>
            	<c:if test="${fdiFlag  == '3' }">
            		晚餐
            	</c:if>
        </td>
        <td>
        <form  id="inputForm" action=""  method="post"  autocomplete="off" >
    	<input type="hidden" name="cfdiId" id="cfdiId"/>
    	<input type="hidden" name="checksId" id="checksId"/>
    	<input type="hidden" name="fdiId" id="fdiId"/>
    	<input type="hidden" name="fdiFlagFlag" id="fdiFlagFlag"/>
    	<input type="hidden" name="fabufdiIdact" id="fabufdiIdact"/>
    	<input type="hidden" name="fdiTypeType" id="fdiTypeType"/>
       	<span id="span_orgName"></span>
		</form>
        </td>
	</tr>
	 
    <tr id="roomTR">   
        <td class="form_label" style="width:199px;">
        	菜单列表：<br /><input type ="checkbox" id="phone_check_all" onclick="javascript:chk_all();"/>
        </td>
        <td style="width:89%;">
        	<div id="pageDiv">
				<table width="100%" border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;"align="center">
					<tr>    
					<c:forEach var="ipp" items="${page.result}" varStatus="stat">
						<td style="border: solid 1px #a0c6e5; height: 20px;">
						<c:if test="${ipp.YN eq 'Y'}">
						<input type="hidden" value="${ipp.FDI_ID}" name="fabufdiId" id="fabufdiId"/>
						<input type="checkbox" checked="checked" value="${ipp.FD_ID}" name="chk" id="chk_"/>${ipp.FD_NAME}
						</c:if>
						<c:if test="${ipp.YN eq 'N'}">
						<input type="checkbox" value="${ipp.FD_ID}" name="chk" id="chk_"/>${ipp.FD_NAME}
						</c:if>
						<!-- 不够四列的行用空列补齐 -->  
						<c:if test="${stat.last && stat.count%4 != 0}">  
						<c:set value="${4-(stat.count%4)}" var="restTd"/>                                    
						<c:forEach var="x" begin="1" end="${restTd}" step="1">  
						<td width="22%"></td>  
						</c:forEach>  
						</c:if>  
						<!-- 4列换一行 -->  
						<c:if test="${stat.count%4==0}">  
						</tr><tr>  
						</c:if>
						</td>
	                 </c:forEach>  
	                </tr>     
				</table>
				<%--
	        	<jsp:include page="/common/include/paging/gopage.jsp" flush="true">
					<jsp:param name="type" value="0" />
					<jsp:param name="position" value="down" />
				</jsp:include>
				 --%>
				<form id="page_search_form" name="searchForm" action="${ctx}/app/per/ajaxRefreshTheMenu.act" method="post">
					<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
					<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }" />
					<input type="hidden" id="fdiId" name="fdiId" value="${fdiId}" />
				</form>
				
			</div>
        </td> 
     </tr> 
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="2">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">发布</a>&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>
  </table>
</body>

</html>
<s:actionmessage theme="custom" cssClass="success"/>