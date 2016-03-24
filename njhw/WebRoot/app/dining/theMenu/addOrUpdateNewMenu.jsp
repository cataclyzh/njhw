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
	<link href="css/dhsq.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
		$(document).ready(function(){
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			//changebasiccomponentstyle();
			//changedisplaytagstyle();
			//changecheckboxstyle();
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

		function saveData(){
			var ids = "";
			$("#saveDiv input[type='checkbox']").each(function(i,item) {
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
                    url: "theNewMenuSave.act" ,
                    success: function(data){   
                  	 //alert("发布成功！");$("#"+winId).window('close');
                  	 //closeEasyWin('winDining');
                  	 window.top.$("#winDining").window("close");
                    },   
                    error: function(XmlHttpRequest, textStatus, errorThrown){   
                      alert( "error");   
                    }   
               });  
			}

		function returnGo(){
			window.top.$("#winDining").window("close");
		}
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
<form  id="inputForm" action=""  method="post"  autocomplete="off" style="background: #f6f9ff; height:400px;">
		<input type="hidden" name="cfdiId" id="cfdiId"/>
    	<input type="hidden" name="checksId" id="checksId"/>
    	<input type="hidden" name="fdiId" id="fdiId"/>
    	<input type="hidden" name="fdiFlagFlag" id="fdiFlagFlag"/>
    	<input type="hidden" name="fabufdiIdact" id="fabufdiIdact"/>
    	<input type="hidden" name="fdiTypeType" id="fdiTypeType"/>
<div id="saveDiv">

	<table width="100%" border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;"align="center">
		<tr>    
			<c:forEach var="typeList" items="${list}" varStatus="stat">
				<td style="border: solid 1px #a0c6e5; height: 20px; width: 50%">
				<c:if test="${typeList.YN eq 'Y'}">
					<input type="hidden" value="${typeList.FDI_ID}" name="fabufdiId" id="fabufdiId"/>
					<input type="checkbox" checked="checked" value="${typeList.FD_ID}" name="chk" id="chk_"/>
					<span title="${typeList.FD_DESC}" style="cursor: pointer;">
						${typeList.FD_NAME}
						<c:if test="${typeList.FD_BAK1 != '' && typeList.FD_BAK1 !=null}">：${typeList.FD_BAK1}</c:if>
					</span>
				</c:if>
				<c:if test="${typeList.YN eq 'N'}">
					<input type="checkbox" value="${typeList.FD_ID}" name="chk" id="chk_"/>
					<span title="${typeList.FD_DESC}" style="cursor: pointer;">
						${typeList.FD_NAME}
						<c:if test="${typeList.FD_BAK1 != '' && typeList.FD_BAK1 !=null}">：${typeList.FD_BAK1}</c:if>
					</span>
				</c:if>
				<!-- 不够四列的行用空列补齐 -->  
				<c:if test="${stat.last && stat.count%2 != 0}">  
					<c:set value="${2-(stat.count%2)}" var="restTd"/>                                    
					<c:forEach var="x" begin="1" end="${restTd}" step="1">  
					<td style="border: solid 1px #a0c6e5; height: 20px; width: 50%"></td>  
					</c:forEach>  
				</c:if>  
				<!-- 4列换一行 -->  
				<c:if test="${stat.count%2==0}">  
				</tr><tr>  
				</c:if>
				</td>
	            </c:forEach>  
	            </tr>     
	     <tr class="form_bottom" style=" background: #f6f9ff;">
	        <td colspan="6" style="border: solid 1px #a0c6e5; height: 20px; width: 50%">
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="javaScript:saveData();">发布</a>&nbsp;&nbsp;&nbsp;&nbsp;
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" onclick="javaScript:returnGo();">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
	        </td>
      	</tr>
	</table>

</div>	
</form>
</body>

</html>
<s:actionmessage theme="custom" cssClass="success"/>