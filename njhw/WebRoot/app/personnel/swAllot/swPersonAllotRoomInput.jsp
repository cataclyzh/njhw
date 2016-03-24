<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-03-24
- Description: 房间分配
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>房间分配</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript">	
		$(document).ready(function(){
			selectUsersByOrgId();
		});
		
		function saveData(){
			var userIds = new Array();
			$("input[name='person_checkbox']:checked").each(function(i) {
				userIds.push($(this).val());
			});
			$("#userIds").val(userIds);
			if (userIds=="") {
				alert('请选择用户');
				//easyAlert('提示信息','请选择用户','error');
			} else {
				$('#inputForm').submit();
			}
		}

		function selectUsersByOrgId(){
			var orgId = $("#orgId").val();
			if(orgId != "" && orgId != null){
				var url = "${ctx}/app/swallot/swLoadUsersByOrgId.act";
				var data = {orgId: orgId ,roomId: "${roomId}"};
				
				sucFun = function (json){
					if(json.state =='1'){
						$("#tr_person").empty();
						var div="";
						//分配了该房间的信息
						$.each(json.allotThisUsers,function(i){
							if(i%7==0 && i!=0){
								div+="<span style='width:120px;'><input type='checkbox' checked='checked' check name='person_checkbox' value='"+json.allotThisUsers[i].userid+"'/>"+json.allotThisUsers[i].displayName+"</span><br/>";
							}else{
								div+="<span style='width:120px;'><input type='checkbox' checked='checked' name='person_checkbox' value='"+json.allotThisUsers[i].userid+"'/>"+json.allotThisUsers[i].displayName+"</span>";
							}
						});
						div+="<br/>";
						//没有分配房间的信息
						$.each(json.unAllotUsers,function(i){
							if(i%7==0 && i!=0){
								div+="<span style='width:120px;'><input type='checkbox' name='person_checkbox' value='"+json.unAllotUsers[i].userid+"'/>"+json.unAllotUsers[i].displayName+"</span><br/>";
							}else{
								div+="<span style='width:120px;'><input type='checkbox' name='person_checkbox' value='"+json.unAllotUsers[i].userid+"'/>"+json.unAllotUsers[i].displayName+"</span>";
							}
						});
						div+="<br/>";
						//分配了其他房间的信息
						$.each(json.allotOtherUsers,function(i){
							if(i%7==0 && i!=0){
								div+="<span style='width:120px;'><input type='checkbox' disabled='disabled' name='person_checkbox_other' value='"+json.allotOtherUsers[i].userid+"'/>"+json.allotOtherUsers[i].displayName+"</span><br/>";
							}else{
								div+="<span style='width:120px;'><input type='checkbox' disabled='disabled' name='person_checkbox_other' value='"+json.allotOtherUsers[i].userid+"'/>"+json.allotOtherUsers[i].displayName+"</span>";
							}
						});
						$("#tr_person").append(div);
					}else{
						$("#tr_person").empty();
						$("#tr_person").append("该部门已经分配完毕!");
					}
				};
				errFun = function (data){
					
				};
				ajaxQueryJSON(url,data,sucFun,errFun);
			}
		}
		// 选中全部
		function chk_all() {
			if ($("#check_all").attr("checked") == "checked")
				$("input[name='person_checkbox']").attr("checked", "true");
			else  
				$("input[name='person_checkbox']").removeAttr("checked");
		}
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="swPersonAllotRoomSave.act"  method="post"  autocomplete="off" >
    <table align="center" border="0" width="100%" class="form_table">
    <input type="hidden" name="roomId" value="${roomId}"/>
    <input type="hidden" name="userIds" id="userIds"/>
	<tr>
        <td class="form_label"><font style="color:red">*</font>委办局：</td>
        <td>
        	<select id="orgId" onchange="javascript:selectUsersByOrgId();">
		        <c:forEach items="${orgList}" var="org">
					<option value="${org.orgId }">${org.name }</option>
				</c:forEach>
			</select>
        </td>
	</tr>
	<tr>
        <td class="form_label" width="15%">
        	<font style="color:red">*</font>请选择人员: <input type="checkbox" name="check_all" id="check_all" onclick="chk_all()"/>
        </td>
        <td id="tr_person">
        
        </td>
	</tr>
   </table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="2">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">分配</a>&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
	<c:if test="${isSuc=='true'}">
		easyAlert('提示信息','分配成功！','info', function(){closeEasyWin('winId');});
	</c:if>
	<c:if test="${isSuc=='false'}">
		easyAlert('提示信息','分配失败！','error');
	</c:if>
</script>
