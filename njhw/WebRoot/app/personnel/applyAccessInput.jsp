<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: HJ
- Date: 2013-08-16
- Description: 门禁闸机添加修改页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/metaIframe.jsp" %>
    <title>门禁闸机授权申请详细</title>
   
    <script type="text/javascript" src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
    <link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">	

    $(document).ready(function() {
		//聚焦第一个输入框
		$("#name").focus();
		$("#guardIds").val('all');
		$("#accessIds").val('${entity.authIdsA}');
		$("#lockVer").val('${entity.lockVer}');
		$("#userid").val('${entity.userid}');
		$("#opt").val('${entity.nuacExp1}');
		
		if ($("#opt").val() == '1') {
			$("#optName").val("新增");
		} else if ($("#opt").val() == '2') {
			$("#optName").val("更新");
		} else if ($("#opt").val() == '3') {
			$("#optName").val("删除");
		}
		
		if (${not empty entity.nuacId}) {
			$("#name").attr("disabled", "");
		}
		
		if (${status} == '2' || ${status} == '3' || ${status} == '4' ) {
			$("#guard").attr("disabled", "");
			$("#access").attr("disabled", "");
			$(".botton_from").hide();
		}
		
		if (${status} == '3' ) {
			$("#refuseReasonTr").show();
			$("#refuseReason").val('${entity.appBak}');
		}
		
		if (${status} == '1' ) {
			$("#applyReasonTr").show();
			$("#applyReason").val('${entity.appBak}');
		}
		
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				name:{
					required: true
				}
			},
			messages:{
				name:{
                    required: "名称不能为空！"
               	}
			}
		});
	});
    
		function saveData(){
		  $("#inputForm").valid();
		  if ($("#inputForm").valid()) {
		  	if ($("#opt").val() == '1' && '' == $("#access").val() && '' == $("#guard").val()) {
		  		easyAlert("提示信息","新增时请选择门禁闸机","info");
		  	} else {
		  		var frm = $('#inputForm');
 		    	frm.submit();
 		    	window.top.$("#accessInput").window("close");
		  	}
		  }
		}
	 	
	 	function closeWin() {			
			window.top.$("#accessInput").window("close");
		}
		
	function showTree() {
		var url = "${ctx}/app/per/allotAccessUserTree.act?userid="+$("#userid").val();
		openEasyWin("ryxz", "员工选择", url, 400, 400);
	}
	
	function showGuardTree() {
		if ($('#name').val() == '') {
			easyAlert("提示信息","请先选择人员！","info");
		} else {
			var url = "${ctx}/app/per/allotGuardTree.act?ids="+$("#guardIds").val()+"&opt="+$("#opt").val();
			openEasyWin("zjxz", "闸机选择", url, 400, 400);
		}
	}
	
	function showAccessTree() {
		if ($('#name').val() == '') {
			easyAlert("提示信息","请先选择人员！","info");
		} else {
			var url = "${ctx}/app/per/allotAccessTree.act?ids="+$("#accessIds").val()+"&opt="+$("#opt").val();
			openEasyWin("mjxz", "门禁选择", url, 400, 400);
		}
	}

	</script>
  </head>
  
  <body style="background:#fff;">
  <form  id="inputForm"  action="saveAccess.act"  method="post">
     <div class="infrom_ryjbxx">
        <s:hidden name="nuacId"/>
        <s:hidden name="userid" id="userid"/>
        <s:hidden name="lockVer" id="lockVer"/>
        <s:hidden name="accessIds" id="accessIds"/>
        <s:hidden name="guardIds" id="guardIds"/>
        <s:hidden name="opt" id="opt"/>
				<table width="100%">
					<tr>
						<td width="30%">
							<div class="infrom_td">姓名：</div>
						</td>
						<td width="70%">
							<input style="width: 236px; padding-left: 0px; color: #818284;"
								type="text" name="name" id="name"
								onclick="showTree()" value="${name}" readonly="readonly"/>
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td width="30%">
							<div class="infrom_td">操作：</div>
						</td>
						<td width="70%">
							<input style="width: 236px; padding-left: 0px; color: #818284;"
								type="text" name="optName" id="optName" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">闸机：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="guard" id="guard" style="width:246px;resize:none;color: #818284;" readonly="readonly">全部</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">门禁：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="access" id="access" onclick="showAccessTree()" style="width:246px;resize:none;color: #818284;margin-top:10px;" readonly="readonly">${access}</textarea>
						</td>
					</tr>
					<tr id="refuseReasonTr" style="display:none;">
						<td>
							<div class="infrom_td">退回理由：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="refuseReason" id="refuseReason" style="width:246px;resize:none;color: #818284;margin-top:10px;" readonly="readonly"></textarea>
						</td>
					</tr>
					<tr id="applyReasonTr" style="display:none;">
						<td>
							<div class="infrom_td">申请理由：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="applyReason" id="applyReason" style="width:246px;resize:none;color: #818284;margin-top:10px;"></textarea>
						</td>
					</tr>
				</table>
     </div>
     <div class="botton_from" style="margin-top:20px;"><a href="javascript:void(0)" onclick="saveData();" style="float:right;">保&nbsp;&nbsp;存</a></div>
  </form> 
  </body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('accessInput');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
<c:if test="${isSuc=='false1'}">
	easyAlert("提示信息","此条信息正在操作，无法更新!","error");
</c:if>
</script>