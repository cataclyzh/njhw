<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 菜肴管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/metaIframe.jsp" %>
    <title>菜名录入</title>
   
    <script type="text/javascript" src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
    <link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">	

    $(document).ready(function() {
		//聚焦第一个输入框
		$("#fdName").focus();
		$("#fdClass").val('${fsDishes.fdClass}');
		$("#fdPhoto1").val('${fdPhoto1}')
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				fdName:{
					required: true,
					maxlength:20
				},
				fdDesc:{
					maxlength:200
				},
				fdClass:{
					required: true
				}
			},
			messages:{
				fdName:{
                    required: "名称不能为空！",
                    maxlength:"名称最大长度不能超过4字符"
               	},
				fdDesc:{
					maxlength:"描述最大长度不能超过200字符"
				},
				fdClass:{
					required:"类别不能为空！"
				}
			}
		});
	});
    
		function saveData(){
		  $("#inputForm").valid();
		  if ($("#inputForm").valid()) {
		  	var frm = $('#inputForm');
 		    frm.submit();
 		    window.top.$("#winUpdateDining").window("close");
		  }
		}

		//上传菜的图片1
	     function ajaxFileUpload1() {
	    	if (!isJpg1()) {
	    		return false;
	    	}
			var fdId = $("#fdId").val();
			var img1 = $("#img1").val();
			if(img1 == null || img1 == ""){
				alert("请选择图片！");
				return false;
			}
			$.ajaxFileUpload({
				url : '${ctx}/app/din/fileupload/updateImg.act?type=1&fdId=' +  fdId  ,
				secureuri : false,
				fileElementId : 'img1',
				dataType : 'json',//返回值类型 一般设置为json
				success : function(json) {
					var tre = json.truee;
					$("#img5").attr("src", json.imgSrc);
					$("#fdPhoto1").val(json.fdPhoto1);
					if(json.error=="error"){
		                alert("请先填写名称！");
						}
				},
				error : function(data, status, e) {
				alert(e);
				}
			});
			
		}

	     function isJpg1() {
	     	var isJpg = true;
	 		var img1 = $("#img1").val();
	 		if (img1!=null&&img1!="") {
	 			if (checkPic(img1)) {
	 				alert("文件只能上传JPG,GIF,BMP,PNG,JPEG格式");
	 				isJpg = false;
	 			}
	 		}
	 		return isJpg;
	 	  }

	     function checkPic(obj) {

	 		var reslut = true;
	 		var picType = obj.substring(obj.length - 3, obj.length);
	 		if (picType == ("jpg") || picType == ("JPG")) {
	 			reslut = false;
	 		}
	 		if (picType == ("gif") || picType == ("GIF")) {
	 			reslut = false;
	 		}
	 		if (picType == ("bmp") || picType == ("BMP")) {
	 			reslut = false;
	 		}
	 		if (picType == ("png") || picType == ("PNG")) {
	 			reslut = false;
	 		}
	 		if (picType == ("jpeg") || picType == ("JPEG")) {
	 			reslut = false;
	 		}
	 		return reslut;
	 	}
	 	
	 	function closeWin() {
			//closeEasyWin('winUpdateDining');
			
			window.top.$("#winUpdateDining").window("close");
		}

	</script>
  </head>
  
  <body style="background:#fff;">
  <form  id="inputForm"  action="inputDishes.act"  method="post">
     <div class="infrom_ryjbxx">
        
        <s:hidden name="fdId"/>
        <s:hidden name="fdPhoto1" id="fdPhoto1"/>
				<table width="100%">
					<tr>
						<td width="30%">
							<div class="infrom_td">名称：</div>
						</td>
						<td width="70%">
							<input style="width: 236px; padding-left: 10px; color: #818284;"
								type="text" name="fdName" value="${fsDishes.fdName}"
								size="20" maxlength="20" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">备注：</div>
						</td>
						<td>
							<input type="text"
								style="width: 236px; padding-left: 10px; color: #818284;"
								name="fdBak1" value="${fsDishes.fdBak1}" theme="simple"
								size="20" maxlength="20" />
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">描述：</div>
						</td>
						<td>
							<textarea rows="4" cols="60" name="fdDesc" id="fdDesc" style="width:246px;resize:none;">${fsDishes.fdDesc}</textarea>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">类型：</div>
						</td>
						<td>
							<div style="width:160px;display:inline-block;">
							<s:select list="#request.fsClassMap" headerKey="" headerValue="请选择" id="fdClass" listKey="key" listValue="value" name="fdClass" cssStyle="width:150px;"/>
							</div>
							<font style="color: red;display:inline-block;">*</font>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">图片：</div>
						</td>
						<td>
							<input type="file" name="file" id="img1"
								style="width: 248px; padding-left: 10px;"
								size="29" />
							<a href="javascript:void(0);" class="infrom_a" style="float:none;margin-left:10px;width:70px;" onclick="ajaxFileUpload1();">上传</a>
						</td>
					</tr>
					<tr>
						<td>
							<div class="infrom_td">预览：</div>
						</td>
						<td style="height: 110px;" valign="top">
							<div id="result">
								<c:if test="${empty fsDishes.fdPhoto1}">
									<img id="img5" src="${ctx}/app/integrateservice/images/food_no.jpg"
										style="width: 130px; height: 110px;" />
								</c:if>
								<c:if test="${not empty fsDishes.fdPhoto1}">
									<img id="img5" src="${fsDishes.fdPhoto1}"
										style="width: 130px; height: 110px;" />
								</c:if>
							</div>
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
	   function(){closeEasyWin('winUpdateDining');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
</script>