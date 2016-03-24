<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-03-24
- Description: Ip电话号码分配
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Ip电话号码分配</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript">	
		$(document).ready(function() {
			$("#orgId").focus();
			jQuery.validator.methods.compareValue = function(value, element) {
	           if(value == "0" || value == ""){
	        	   return false;
	           }
	        	return true;
	        };
			$("#inputForm").validate({
			meta :"validate",
			errorElement :"div",
				rules: {
					resName: {
						required: true,
						compareValue:true
					},
					orgId: {
						required: true,
						compareValue:true
					}
				},
				messages: {
					resName: {
						compareValue:"请填写Ip电话号码"
					},
					orgId:{
						compareValue:"请选择委办局"
					}
				}
			});
			
			changebasiccomponentstyle();
			if ("${emOrgRes.eorId }" != "") {
				$("#addtd").hide();
				$("#updatetd").show();
			} else {
				$("#addtd").show();
				$("#updatetd").hide();
			}
		});
		
		function saveData(){
			var resNames = "";
			$(":text[id^='resName']").each(function() {
				var name = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
				if (name != "") resNames += (name + ",");
			});
			var len = (resNames.split(",").length) -1;
			if (len == 0) alert("请输入信息后保存");
			else {
				$.ajax({
					type: "POST",
					url: "${ctx}/app/allotIPNum/validAllIpPhoneNum.act",
					data: "resNames="+resNames,
					dataType: 'json',
					async : false,
					success: function(json){
						 if (json.status == 1) { 	//存在已分配的车配就显示
							 easyConfirm("提示信息", 
								"Ip电话号码："+json.carNums+"已分配给其他委办局<br/>"+
								"点击确认，跳过已分配的Ip电话号码保存<br/>",
								function(r){
									if (r) {
										$("#resName").val(resNames);
										$('#inputForm').submit();
									}
								});
			    		} else {
							$("#resName").val(resNames);
							$('#inputForm').submit();
			    		}
					 }
				 });
			}
		}
		
		function isRepeat(idx){
			var status = false;
			var compID = "resName"+idx;
			var compName = $("#"+compID).val().replace(/(^\s*)|(\s*$)/g, "");
			$(":text[id^='resName']").each(function() {
				if ($(this).attr("id") != compID) {
					var name = $(this).val().replace(/(^\s*)|(\s*$)/g, "");
					if (name != "" && compName == name) status = true;
				}
			});
			return status;
		}
		
		// 根据Ip电话号码码验证是否以分配给其他委办局
		function loadIpPhoneNumInfo(idx) {
			$("#showinfo"+idx).hide();
			$("#savebut").show();
			var resName = $("#resName"+idx).val().replace(/(^\s*)|(\s*$)/g, "");
			if (resName != "") {
				$.ajax({
					type: "POST",
					url: "${ctx}/app/allotIPNum/loadIpPhoneNumInfo.act",
					data: "resName="+resName,
					dataType: 'json',
					async : false,
					success: function(json){
						 if (json.status == 1) { 	//已分配就显示提示信息
			    			$("#showinfo"+idx).text("此Ip电话号码已分配给："+json.orgName);
			    			$("#showinfo"+idx).show();
			    			$("#savebut").hide();
			    			return false;
			    		} else {
							if (isRepeat(idx)) {	// 判断当前Ip电话号码是否重复
								$("#showinfo"+idx).text("Ip电话号码重复");
				    			$("#showinfo"+idx).show();
				    			$("#savebut").hide();
				    			return false;
							}
			    		}
					 }
				 });
			}
		}
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="save.act"  method="post"  autocomplete="off" >
    <table align="center" border="0" width="100%" class="form_table">
	<input type="hidden" name="eorId" value="${emOrgRes.eorId }"/>
	<input type="hidden" name="orgId" value="${orgId }"/>
	<input type="hidden" name="orgName" value="${orgName }"/>
	<input type="hidden" name="resName" id="resName"/>
	<tr>
        <td class="form_label"><font style="color:red">*</font>单位名称：</td>
        <td>　${orgName }</td>
    </tr>
	<tr>
        <td class="form_label"><font style="color:red">*</font>Ip电话号码：</td>
        <td id="addtd"> 
        	　一：<input id="resName1" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('1')"/> 
        	<label id="showinfo1" style="display: none"></label><br/>
        	　二：<input id="resName2" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('2')"/> 
        	<label id="showinfo2" style="display: none"></label><br/>
        	　三：<input id="resName3" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('3')"/> 
        	<label id="showinfo3" style="display: none"></label><br/>
        	　四：<input id="resName4" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('4')"/> 
        	<label id="showinfo4" style="display: none"></label><br/>
        	　五：<input id="resName5" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('5')"/> 
        	<label id="showinfo5" style="display: none"></label><br/>
        	　六：<input id="resName6" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('6')"/> 
        	<label id="showinfo6" style="display: none"></label><br/>
        	　七：<input id="resName7" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('7')"/> 
        	<label id="showinfo7" style="display: none"></label><br/>
        	　八：<input id="resName8" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('8')"/> 
        	<label id="showinfo8" style="display: none"></label><br/>
        	　九：<input id="resName9" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('9')"/> 
        	<label id="showinfo9" style="display: none"></label><br/>
        	　十：<input id="resName10" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('10')"/>
        	<label id="showinfo10" style="display: none"></label>
        </td> 
        <td id="updatetd">
        	<input id="resName1" theme="simple" cssClass="textbox" onblur="loadIpPhoneNumInfo('1')" value="${emOrgRes.resName }"/> 
        	<label id="showinfo1" style="display: none"></label>
        </td> 
    </tr> 
     <!--<tr>
     	<td class="form_label">启用时间：</td>
     	<td>
			<s:date id="format_startTime" name="porSt" format="yyyy-MM-dd" />
			<jsp:include page="/common/include/chooseDateTime.jsp">
				<jsp:param name="date" value="porSt" />
				<jsp:param name="format_date" value="${emOrgRes.porSt}" />
			</jsp:include>
		</td> 
     </tr> 
     <tr>
     	<td class="form_label">停止时间：</td>
     	<td>
			<s:date id="format_endTime" name="porEt" format="yyyy-MM-dd" />
			<jsp:include page="/common/include/chooseDateTime.jsp">
				<jsp:param name="date" value="porEt" />
				<jsp:param name="format_date" value="${emOrgRes.porEt}"/>
			</jsp:include>
		</td>
      </tr>
	--></table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="2">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();" id="savebut">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
</script>
