<%--
  - Author: ycw
  - Date: 2012-7-10 
  - Description: 打开审批初始化界面
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,java.text.*"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>打开审批初始化界面</title>
<%@ include file="/common/include/meta.jsp" %>
<script src="${ctx}/scripts/custom/display.js" type="text/javascript"></script>
<script src="${ctx}/scripts/custom/format.js" type="text/javascript"></script>
<script src="${ctx}/scripts/custom/querySubmit.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
$(function() {
	$("#approveResult").focus();
	changebasiccomponentstyle();
});
var options1 = {
	onkeyup: false,
	rules: {
		approveResult : {
			required : true
		},
		roleId : {
			required : true
		},
		ansContent : {
			required: true,
			maxlength: 1500,
			minlength: 1
		}
	},
	messages: {
		approveResult : {
				required : " 请选择审批结果"
		},
		roleId : {
				required : " 请选择转处理人"
		},
		ansContent: {
			required: " 请输入不同意的原因",
			minlength: " 不同意的原因最少为一个字符"
		}
	}
}

var options2= {
	onkeyup: false,
	rules: {
		approveResult : {
			required : true
		},
		roleId : {
			required : true
		}
	},
	messages: {
		approveResult : {
				required : " 请选择审批结果"
		},
		roleId : {
				required : " 请选择转处理人"
		}
	}
}

</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="isInputReason()">
    <h:panel id="query_panel" width="100%" title="问题贴明细" pageName="filePage">
   		<table align="center" id="hide_table" border="0" width="100%" class="form_table">
	        <tr>
	        	<td class="form_label">问题贴编号：</td>
	            <td>${vendorQuestion.questCode }</td>
	        	<td class="form_label" width="120px" nowrap>问题贴标题：</td>
				<td colspan="4">${vendorQuestion.questTitle }</td>
	        </tr>
	        <tr>
	            <td class="form_label" width="25%">发&nbsp;帖&nbsp;人：</td>
	            <td><s:property value="vendorQuestion.questUser"/></td>
	            <td class="form_label" width="15%">发帖时间：</td>
	            <td><f:formatDate value='${vendorQuestion.questTime}' pattern="yyyy年MM月dd日 HH时mm分ss秒"/></td>
	            <td class="form_label" width="120px" nowrap>发帖公司：</td>
				<td>${vendorQuestion.questCompany }</td>
	        </tr> 
	        <tr>
				<td class="form_label" width="120px" nowrap>发帖内容：</td>
				<td colspan="5">${ansContent}</td>
			</tr>
	    </table>
    </h:panel>
    
    <h:panel id="query_panel2" width="100%" title="审核处理" pageName="filePage">
     	<form id="approve_form" action="approveQuestion.act" method="post"  autocomplete="off" > 
     	<input type="hidden" name="questId" id="questId" value="${vendorQuestion.questId }"/>
	   		<table align="center" id="hide_table" border="0" width="100%" class="form_table">
		        <tr>
		        	<td class="form_label">审批结果：</td>
		             <td>
		             	<select id="approveResult" name="approveResult" class="input180box" onchange="isInputReason()">
			        		 	<option value="">--请选择--</option>
			        		 	<option value="0">不通过</option>
			        		 	<option value="1">通过</option>
			        	</select><span style="color: red;">&nbsp;*&nbsp;</span> 
					</td>
		        	<td class="form_label">转处理人：</td>
		        	<c:if test="${vendorQuestion.dcFlag=='00' }">
		        		 <td>
			        		 <select id="roleId" name="roleId" class="input180box">
			        		 	<option value="">--请选择--</option>
			        		 	<option value="${Holy400RoleId}">Cosmosource 400</option>
			        		 	<option value="${TescoAPRoleId}">Tesco AP</option>
			        		 </select><span style="color: red;">&nbsp;*&nbsp;</span> 
						</td>
		            </c:if>
		            <c:if test="${vendorQuestion.dcFlag=='01' }">
						 <td>
			        		 <select id="roleId" name="roleId" class="input180box">
			        		 	<option value="${TescoAPRoleId}">Tesco AP</option>
			        		 </select>
						</td>
		            </c:if>
		        </tr>
		        <tr>
					<td class="form_label" width="120px" nowrap>审核人：</td>
					<td>${loginUser}</td>
					<td class="form_label" width="120px" nowrap>审核时间：</td>
					<td><f:formatDate  value='${currentTime}'  pattern="yyyy年MM月dd日 HH时mm分ss秒"/></td>
				</tr>
				<tr>
					<td>
						<div id="isHiddenInput1" style="display:block;">
							不同意的原因:
						</div>
					</td>
					<td colspan="30">
						<div id="isHiddenInput2" style="display:block;">
						<textarea name="ansContent" id="ansContent" cols="79" rows="5"></textarea><span style="color: red;">&nbsp;&nbsp;*</span> 
						</div>
					</td>
				</tr>
				<tr><td colspan="8">
					
						<p align="left">
							<span style="color: red;">请注意：<br/><br/>
	            				1.审核结果=不通过时，必须详细写清楚原因；<br/><br/>
	            				2.转处理人栏：如果400不能答复的问题帖，才可以转Tesco AP部门处理。
	            			</span> 
	            		</p>
            		
            	</td></tr>
		        
		    </table>
		    <table>
		    	<tr>
					<td width="62%"></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript: formSubmit();">确认</a></td>
					<td width="1%"></td>
					<td><a href="javascript:void(0);" id="closeBtn" class="easyui-linkbutton" iconCls="icon-save" onclick="closeWin();">关闭</a></td>
				</tr>
		    </table>
		  
		 </form>
    </h:panel>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
    initQueryToggle("listCnt", "hide_table", "footId");
});

//关闭当前页面
function closeWin(){
	closeEasyWin('approve_question_init');
}

//根据审批结果，决定是否隐藏不通过原因的输入框。
function isInputReason(){
	var approveResult = document.forms[0].approveResult.value;
	if(approveResult=="1"){
		document.getElementById("isHiddenInput1").style.display="none";
		document.getElementById("isHiddenInput2").style.display="none";
	}
	if(approveResult=="0"){
		document.getElementById("isHiddenInput1").style.display="block";
		document.getElementById("isHiddenInput2").style.display="block";
	}
}


//确认审批操作
function formSubmit() {
	var url="${ctx}/common/question/approveQuestion.act";
	var approveResultValue=document.getElementById("approveResult").value;
	if(approveResultValue=='1'){ //表示通过
		$("#approve_form").validate(options2);
	}else{
		$("#approve_form").validate(options1);
	}
	$("#approve_form").attr("action", url);
		$("#approve_form").submit();
}
	

<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','审批成功！','info',
	   function(){closeEasyWin('approve_question_init');}
	);
</c:if>

<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','审批失败！','error');
</c:if>
  
</script>