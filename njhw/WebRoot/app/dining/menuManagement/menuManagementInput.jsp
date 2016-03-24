<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: HJ
- Date: 2013-08-09
- Description: 菜肴类型管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/metaIframe.jsp" %>
    <title>菜肴类型录入</title>
   
    <script type="text/javascript" src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
    <link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">	

    $(document).ready(function() {
		//聚焦第一个输入框
		$("#fmName").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				fmName:{
					required: true
				},
				fmBak1:{
					required: true,
					digits:true
				}
			},
			messages:{
				fmName:{
                    required: "名称不能为空！"
               	},
               	fmName:{
                    required: "名称不能为空！",
                    digits:"排序码必须是数字！"
               	}
			}
		});
	});
    
function saveData(){
	$("#inputForm").valid();
	if ($("#inputForm").valid()) {
	    $("#inputForm").submit();
	}
}
		
	 	function closeWin() {
			window.top.$("#winAddMenu").window("close");
		}

	</script>
  </head>
  
  <body style="background:#fff;">
  <form  id="inputForm"  action="inputMenus.act"  method="post"  autocomplete="off" >
     <div class="infrom_ryjbxx">
        
        <s:hidden name="fmId"/>
				<table width="100%">
					<tr>
						<td width="30%">
							<div class="infrom_td">类型名称：</div>
						</td>
						<td width="70%">
							<input style="width: 236px; padding-left: 10px; color: #818284;"
								type="text" name="fmName" value="${fsMenu.fmName}"
								theme="simple" size="20" maxlength="4" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">排序码：</div>
						</td>
						<td>
							<input style="width: 236px; padding-left: 10px; color: #818284;"
								type="text" name="fmBak1" value="${fsMenu.fmBak1}"
								theme="simple" size="20" maxlength="4" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">备注：</div>
						</td>
						<td>
							<input style="width: 236px; padding-left: 10px; color: #818284;"
								type="text" name="fmBak2" value="${fsMenu.fmBak2}"
								theme="simple" size="20" maxlength="4" />
						</td>
					</tr>
				</table>
     </div>
     <div class="botton_from">
     	<a href="javascript:void(0)" onclick="closeWin();" style="float:right;">关&nbsp;&nbsp;闭</a>
     	<a href="javascript:void(0)" onclick="saveData();" style="float:right;">保&nbsp;&nbsp;存</a>
     </div>
  </form> 
  </body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('winAddMenu');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error",
	   function(){closeEasyWin('winAddMenu');}
	);
</c:if>
</script>