<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: sqs
- Date: 2013-3-19 10:12:11
- Description: 已经分配房间的页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>物业房间分配</title>
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script type="text/javascript">
	    var NEWARR = [1];
		function addNode(id){
		  var str  = id.split("::R,");
			if(str.length == 2){
				//alert("1:"+str[0]);
				$('#treeRoom').append('<input type="checkbox" name="id[]" value="" />');
			}else{
				for(var i=1;i<str.length;i++){
					//alert("2:"+str[i-1]);
					$('#treeRoom').append('<input type="checkbox" name="id[]" value="" />');
				}
			}
		}

		//得到勾选房间的ID
		function saveNode(){
			var ids = "";
			var checks ="";
			$("#pagdiv input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("disabled")!="disabled")	{		
					ids += $(this).val()+",";
					if ($(item).attr("checked")=="checked"){
						checks += "1"+",";
					} else {
						checks += "0"+",";
					}
				}
			});
			//$("#chk").val(ids);
			//$("#chk").val(checks);
			//$("#orgName").val($("#orgId option:selected").text());
			//$('#inputForm').submit();
			//$("#inputForm").ajaxSubmit({   
            //        type: 'post',   
            //        url: "allotIpSave.act" ,   
            //        success: function(data){   
            //        },   
            //        error: function(XmlHttpRequest, textStatus, errorThrown){   
            //            alert( "error");   
            //        }   
            // });  
		}

		//取消已分配的房间
		function delRoomAllot(){
			var ids = "";
			var checks ="";
			$("#check_Room input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("disabled")!="disabled")	{		
					ids += $(this).val()+",";
					if ($(item).attr("checked")=="checked"){
						checks += "1"+",";
					} else {
						checks += "0"+",";
					}
				}
			});
			  alert(ids);
			  alert(checks);
		}
		
	</script>
</head>
<body  >
	<table id="check_Room">
		<c:forEach items="${emList}" var="org" varStatus="sta">
			<tr>
				<td>
				<c:if test="${org.ISYN eq 'y'}">
					<input type="hidden" checked="checked" disabled="disabled" value="${org.RES_ID}" name="chk" id="chk"/>${org.ROOMNAME}&nbsp;(有人)
				</c:if>
				<c:if test="${org.ISYN eq 'n'}">
					<input type="checkbox" onclick="delRoomAllot();" value="${org.RES_ID}" name="chk_" id="chk_"/>${org.ROOMNAME}&nbsp;(无人)
				</c:if>
				</td>
			</tr>
		<c:if test="${sta.count%16==0}">
		</c:if> 
		</c:forEach>
		<tr>
			<td id="treeRoom" name="treeRoom"></td>
		</tr>
	</table>
</body>
</html>