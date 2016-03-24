<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp" %>
<title>菜单信息录入</title>
<style type="text/css">
body{background:#f6f5f1;}
</style>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<link href="${ctx}/app/integrateservice/css/wizard_css.css"	rel="stylesheet" type="text/css" />
<link href="${ctx}/common/pages/misc/css/admin_css.css"
			rel="stylesheet" type="text/css" />
	<script type="text/javascript">
	$(document).ready(function() {
		$("#title").focus();
		$("#inputForm").validate({
			errorElement :"div",
			rules: {
				title: "required",
				name: "required"
			},
			messages: {
				title: {
					required: "请输入菜单名称!"
				},
				name: {
					required: "请输入资源编码!"
				}
			}
		});					
		changebasiccomponentstyle();
		$("#extResType").val("${entity.extResType}".replace(/\s+/g, ""));
		$("#blank").val("${entity.blank}".replace(/\s+/g, ""));
		$("#sort").val("${entity.sort}".replace(/\s+/g, ""));
	});
	
	function showMinfo() {
	   //alert($("#extResType").val());
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
		var reg=new RegExp("[0-9]+");
		if(!reg.test($("#sort").val())){
			alert("请输入排序号码!");
			return;
		}
		$('#inputForm').submit();
	}
	
</script>
</head>
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:auto;background: #fff;padding:0 10px; width: 97%;'>
<div class="bgsgl_right_list_border">
	<div class="bgsgl_right_list_left">功能菜单设置</div>
</div>
<form  id="inputForm" action="objSave.act"  method="post"  autocomplete="off" >
 <s:hidden name="PId"/>
 <s:hidden name="nodeId"/>
 <input id="res" name="res" value="${res}" type="hidden"/>
 <div class="containerTableStyle" style="width: 100%; height: 100%;">
    <table align="center" border="0" width="100%" class="form_table" style="background: rgb(246,245,241);">
      <tr>
		 <td class="form_label" width="20%">菜单名称：</td>
		 <c:choose>
			 <c:when test="${title == '超级管理员首页' || title =='单位管理员' || title=='个人用户'}">
	         	<td> <s:textfield name="title" theme="simple" size="30" maxlength="20" disabled="true"/><font style="color:red">*</font></td>
	         </c:when>
	         <c:otherwise>
	         	<td> <s:textfield name="title" id="title" theme="simple" size="30" maxlength="20"/><font style="color:red">*</font></td>
	         </c:otherwise>
         </c:choose>
      </tr>
      <tr style="display:none">
         <td class="form_label"><font style="color:red"></font>资源KEY：</td>
         <td><s:textfield name="keyword" theme="simple" size="30" maxlength="20"/></td>   
      </tr>
      <tr style="display:none">
		 <td class="form_label">上级资源：</td>
		 <td> <s:textfield name="parentOrgname" theme="simple" size="30" readonly="true"/></td>
      </tr>
      <tr style="display:none">   
        <td class="form_label">资源类型：</td>
        <s:select list="#application.DICT_RES_MENU_TYPE" headerKey=""  listKey="dictcode" listValue="dictname" name="extResType" id="extResType" onchange="showMinfo()"/>
        </td>
     </tr>
     <tr>
   		 <td class="form_label" width="20%">图片路径：</td>
		 <td> <s:textfield name="ico" id="ico" theme="simple" size="30"/></td>
	 </tr>
     <tr>
     	<td class="form_label" width="20%">链接文件：</td>
		<td> <s:textfield name="link" id="link" theme="simple" size="30"/></td>
     </tr>
     <tr>
     	<td class="form_label" width="20%">排序：</td>
		<td ><input name="sort" maxlength="4" id="sort" size="30"><font style="color:red">*</font>
			<span style="float: right;margin-right: 44px">是否弹出：
				<select id="blank" name="blank">
					<option value="0">不弹</option>
					<option value="1">弹出</option>
					<option value="2">全屏显示</option>
				</select>
			</span>
		</td>
    </tr>
    <tr style="display:none">
        <td class="form_label">属性：</td>
        <td> <s:textfield name="objAttrib" theme="simple" size="60" maxlength="10"/></td>
	</tr>
    <tr>        
        <td class="form_label" width="20%">备注：</td>
        <td><s:textarea name="memo" theme="simple" rows="6" cols="52" /></td>
    </tr>
	</table>
	</div>
   <table align="center" border="0" width="100%" class="form_table" >  
      <tr style="padding: 10px 0 5px 8px;text-align: center;background: #fffff;height: 50px;">
        <td colspan="6">
        <a style="float:right" href="javascript:void(0);" class="fkdj_botton_reset" iconCls="icon-search" onclick="saveData();">保存</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存资源成功！','info',
	   function(){
	   		window.top[0].tree.refreshItem(window.top[0].tree.getSelectedItemId());
	   		//alert(window.top[0].tree.getSelectedItemId());
	   		parent.frameDialogClose();
	   		//parent.updateCallBack();
	   		
	   }
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
</script>

