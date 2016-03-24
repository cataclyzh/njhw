<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 组织机构信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<title>组织机构信息录入</title>
	<script type="text/javascript">
	$(document).ready(function() {
		
		$("#title").focus();
		$("#inputForm").validate({
			errorElement :"div",
			rules: {
				shortName: {
					required: true,
					maxlength: 30
				},
				name: {
					required: true,
					maxlength: 30
				},
				memo:{
					maxlength: 30
				},
				telId:{
               		isTelePhone:true
               	},
               	telFaxid:{
               		isTelePhone:true
               	}
			},
			messages: {
				shortName: {
					required: "请输入简称!",
					maxlength: "简称长度不能大于30个字符"
				},
				name: {
					required: "请输入名称!",
					maxlength: "名称长度不能大于30个字符"
				},
				memo:{
					maxlength: "备注长度不能大于30个字符"
				},
				telId:{
					isTelePhone:"请输入正确电话号码，例如025-88888888或99999999"
				},
				telFaxid:{
					isTelePhone:"请输入正确传真号码，例如025-88888888或99999999"
				}
			}
		});
	
		jQuery.validator.addMethod("isTelePhone", function(value, element) {
			var telephone = /^([0-9]{3,4}\-){0,1}[0-9]{7,8}$/;
			if (value != "") return telephone.test(value);
			else return true;
		});
		 		
		changebasiccomponentstyle();
		$("#extResType").val("${entity.extResType}".replace(/\s+/g, ""));
		$("#blank").val("${entity.blank}".replace(/\s+/g, ""));
		$("#sort").val("${entity.sort}".replace(/\s+/g, ""));
		showMinfo();
	});
	
	function showMinfo() {
		if ($("#extResType").val() == "M") $("#M_Info").show();
		else {
			$("#M_Info").hide();
			$("#ico").val("");
			$("#link").val("");
			$("#sort").val("0");
			$("#blank").val("0");
		}
	}
	
	function saveData(){
		if(!isRepate())
		{
			openAdminConfirmWin(saveDataFn);
		}
		
	}
	
	function saveDataFn() {
		$('#inputForm').submit();
	}
	
	function closeWin() {
	
		parent.window.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckin.act";
		parent.window.top.$("#orgInput").window("close");
		
	}
	
		function isRepate()
		{   
		    var isRepate = false;
			var name =$('#name').val();
			var PId = $('#PId').val();
			var orgId = $('#orgId').val();
			$("#exist_name").hide();
			$.ajax({
	            type: "POST",
	            url: "${ctx}/app/per/orgIsRepate.act",
	            data: {"name": name,"PId":PId,"orgId":orgId},
	            dataType: 'json',
	            async : false,
	            success: function(json){
		    		if(json.isRepate == 'Y')
	                {    
	                	$("#exist_name").show();
	                	isRepate =  true;
	                }
				},
	            error: function(msg, status, e){
	            	isRepate =  true;
	            	easyAlert("提示信息", "加载市民卡信息出错！","info");
	            }
	         });
	         return isRepate;
		}
		
</script>
</head>
<body topmargin="0" leftmargin="0" style="background:#fff !important;">
<form  id="inputForm" action="orgSave.act"  method="post"  autocomplete="off" >
 <s:hidden name="PId" id="PId"/>
 <s:hidden name="orgId" id="orgId"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
		<td class="form_label"><font style="color:red">*</font>名称：</td>
        <td> <s:textfield name="name" theme="simple" size="30" maxlength="30"/>
        <div id="exist_name" style="display: none;" generated="true" class="error">该机构名已存在！</div></td>
      </tr>
      <tr>  
        <td class="form_label"><font style="color:red">*</font>简称：</td>
        <td> <s:textfield name="shortName" theme="simple" size="30" maxlength="30"/></td> 
      </tr>
      <!--<tr>
        <td class="form_label"><font style="color:red"></font>排序码：</td>
        <td><s:textfield name="orderNum" theme="simple" size="30" maxlength="6"/></td>
      </tr>
      --><tr>
        <td class="form_label">电话号码：</td>
        <td> <s:textfield name="telId" theme="simple" size="30" maxlength="12"/></td>  
           </tr>
              <tr>
         <td class="form_label">传真号码：</td>
        <td> <s:textfield name="telFaxid" theme="simple" size="30" maxlength="12"/></td> 
           </tr>
              <tr> 
        
        <td class="form_label"><font style="color:red"></font>备注:</td>
        <td><s:textfield name="memo" theme="simple" size="30" maxlength="30"/></td>
        
      </tr>
      
      <tr>
		<td class="form_label">上级组织机构：</td>
		<td> <s:text name="parentOrgname"/></td>
      </tr>
      
	</table>
	
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
        <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="saveData();">保存</a>　　
        <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" onclick="closeWin()">关闭</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	parent.easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('orgInput');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	parent.easyAlert('提示信息','保存失败！','error');
</c:if>
</script>

