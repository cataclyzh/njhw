<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 增加菜肴页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
	<link href="${ctx}/app/personnel/telAndNumber/css/dhsq.css" type="text/css" rel="stylesheet" />
    <title>菜单录入</title>
    
    <script type="text/javascript" src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
    <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">	

    $(document).ready(function() {
		$("#fdName").focus();
		$("#inputForm").validate({		// 为inputForm注册validate函数
			meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
			rules: {
				fdName:{
					required: true,
					maxlength:4
				},
				fdDesc:{
					maxlength:200
				}
			},
			messages:{
				fdName:{
                    required: "名称不能为空！",
                    maxlength:"名称最大长度不能超过4字符"
               	},
				fdDesc:{
					maxlength:"描述最大长度不能超过200字符"
				}
			}
		});
	});

	function saveData(){
	  var fName = $("#fdName").val();
	  if(fName == null || fName ==""){
		alert('请输入名称！');
		return;
	  }
	  $("#fdame").val($("#fdName").val());
	  $("#submit_fdDesc").val($("#fdDesc").val());
	  var frm = $('#inputForm');
 	  frm.submit();
 	  window.top.$("#winAddDining").window("close");
	}

	 //上传菜的图片1
     function ajaxFileUpload1() {
    	isJpg1();
		var fdName = $("#fdName").val();
		var fdId = $("#fdId").val();
		var img1 = $("#img1").val();
		if(fdName == null || fdName == ""){
			alert("请输入名称！");
			return false;
		}
		if(img1 == null || img1 == ""){
			alert("请选择图片！");
			return false;
		}
		$.ajaxFileUpload({
			url : '${ctx}/app/din/fileupload/saveImg.act?type=1&fdName=' + fdName + '&fdId=' + fdId  ,
			secureuri : false,
			fileElementId : 'img1',
			dataType : 'json',//返回值类型 一般设置为json
			success : function(json) {
				var tre = json.truee;
				$("#fdId").val(json.fdId);
				$("#fdame").val(json.fdame);
				$("#img5").attr("src", json.imgSrc);
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
 		var img1 = $("#img1").val();
 		if (img1!=null&&img1!="") {
 			if (checkPic(img1)) {
 				alert("文件只能上传JPG,GIF,BMP格式");
 				return false;
 			}
 		}
 	  }
	  
	 //上传菜的图片2
     function ajaxFileUpload2() {
    	isJpg2();
		var fdName = $("#fdName").val();
		var fdId = $("#fdId").val();
		var img2 = $("#img2").val();
		if(fdName == null || fdName == ""){
			alert("请输入名称！");
		    return false;
	    }
		if(img2 == null || img2 == ""){
			alert("请选择图片！");
			return false;
		}
		$.ajaxFileUpload({
			url : '${ctx}/app/din/fileupload/saveImg.act?type=2&fdName=' + fdName + '&fdId=' + fdId ,
			secureuri : false,
			fileElementId : 'img2',
			dataType : 'json',//返回值类型 一般设置为json  
			success : function(json) {
				var tree = json.truee;
				$("#fdId").val(json.fdId);
				$("#fdame").val(json.fdame);
				$("#img6").attr("src", json.imgSrc);
				if(json.error=="error"){
	                alert("请先填写名称！");
					}

			},
			error : function(data, status, e) {
			alert(e);
			}
		});
		
	}

     function isJpg2() {
 		var img2 = $("#img2").val();
 		if (img2!=null&&img2!="") {
 			if (checkPic(img2)) {
 				alert("文件只能上传JPG,GIF,BMP格式");
 				return false;
 			}
 		}
 	  }
	 //上传菜的图片3
     function ajaxFileUpload3() {
    	isJpg3();
		var fdName = $("#fdName").val();
		var fdId = $("#fdId").val();
		var img3 = $("#img3").val();
		if(fdName == null || fdName == ""){
			alert("请输入名称！");
	    	return false;
        	}
		if(img3 == null || img3 == ""){
			alert("请选择图片！");
			return false;
		}
		$.ajaxFileUpload({
			url : '${ctx}/app/din/fileupload/saveImg.act?type=3&fdName=' + fdName + '&fdId=' + fdId ,
			secureuri : false,
			fileElementId : 'img3',
			dataType : 'json',//返回值类型 一般设置为json
			success : function(json) {
				var trre = json.truee;
				$("#fdId").val(json.fdId);
				$("#fdame").val(json.fdame);
				$("#img7").attr("src", json.imgSrc);
				if(json.error=="error"){
	                alert("请先填写名称！");
					}

			},
			error : function(data, status, e) {
			alert(e);
			}
		});
		
	}

     function isJpg3() {
 		var img3 = $("#img3").val();
 		if (img3!=null&&img3!="") {
 			if (checkPic(img3)) {
 				alert("文件只能上传JPG,GIF,BMP格式");
 				return false;
 			}
 		}
 	  }

     function checkPic(obj) {

 		var reslut = true;
 		var picType = obj.substring(obj.length - 3, obj.length);
 		if (picType == ("jpg")) {
 			reslut = false;
 		}
 		if (picType == ("gif")) {
 			reslut = false;
 		}
 		if (picType == ("bmp")) {
 			reslut = false;
 		}
 		return reslut;
 	}
		
	</script>
  </head>
  
  <body>
   <div class="form_css" >
         <table align="center" border="0" width="100%" class="form_table">
      <form  id="inputForm" action="saveDishes.act"  method="post"  autocomplete="off" >
      <tr class="form_table_tr">
	    <input type ="hidden" id="fdId"  name="fdId" /> 
	    <input type ="hidden" id="fdame"  name="fdame" /> 
	    <input type = "hidden" name="fdDesc" id = "submit_fdDesc" />
        <td  class="from_table_td2">名称：</td>
        <td class="from_table_td1" colspan="3">
          <input  style=" width:236px;padding-left:10px;color:#818284;" type="text" id="fdName" theme="simple" size="20" maxlength="4" /><font style="color:red">*</font>
        </td> 
     </tr>
     <tr class="form_table_tr">
        <td  class="from_table_td2">备注：</td>
        <td class="from_table_td1" colspan="3">
         <input type="text"   style=" width:236px;padding-left:10px;color:#818284;" id="fdBak1"  theme="simple" size="20" maxlength="20" />
        </td> 
     </tr>
     <tr class="form_table_tr">  
        <td  class="from_table_td2">描述：</td>
        <td class="from_table_td1" colspan="3">
         <textarea rows="4" cols="60" name="fdDesc" id="fdDesc"></textarea>
        </td> 
      </tr> 
     </form>  
      <tr  class="from_table_td2">
      <td  class="from_table_td2">图片1：</td>
		<td class="from_table_td1" colspan="3">
			<input  style=" width:236px;padding-left:10px;color:#818284;" type="file" name="file" id="img1" size="18"/>
			<input  style=" width:100px;padding-left:10px;color:#818284;" type="button" id="upc1" name="upc1" value="上传" onclick="ajaxFileUpload1();" />
	    </td>
	  </tr>		
      <tr class="form_table_tr">
      <td  class="from_table_td2">图片2： </td>
		<td class="from_table_td1" colspan="3">
			<input  style=" width:236px;padding-left:10px;color:#818284;" type="file" name="file" id="img2" size="18"/>
			<input  style=" width:100px;padding-left:10px;color:#818284;" type="button" id="upc2" name="upc2" value="上传" onclick="ajaxFileUpload2();" />
	    </td>
	  </tr>				
      <tr class="form_table_tr">
      <td  class="from_table_td2">图片3： </td>
		<td class="from_table_td1" colspan="3">
			<input  style=" width:236px;padding-left:10px;color:#818284;" type="file" name="file" id="img3" size="18"/>
			<input  style=" width:100px;padding-left:10px;color:#818284;" type="button" id="upc3" name="upc3" value="上传" onclick="ajaxFileUpload3();" />
	    </td>
	  </tr>	
	  <tr class="form_table_tr">
	       <td  class="from_table_td2">预览：</td>
	       <td style="width:222px;height:110px;" valign="top">
				<div id="result">
			        <img id="img5" src="${imgSrc}" style="width:130px;height:110px;" />
				</div>
			</td>
	       <td style="width:222px;height:110px;" valign="top">
				<div id="result">
			        <img id="img6" src="${imgSrc}" style="width:130px;height:110px;" />
				</div>
			</td>
	       <td style="width:222px;height:110px;" valign="top">
				<div id="result">
			        <img id="img7" src="${imgSrc}" style="width:130px;height:110px;" />
				</div>
			</td>
	  </tr>					
	</table>
   <div class="botton_from"><a href="javascript:void(0)" onclick="saveData();">保&nbsp;&nbsp;存</a> <a href="javascript:void(0)" onclick="closeWin();">关&nbsp;&nbsp;闭</a></div>
   
   </div>
  </body>
</html>
<script type="text/javascript">
function closeWin() {
	closeEasyWin('winAddDining');
}
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('winAddDining');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
</script>