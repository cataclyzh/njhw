<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/meta.jsp"%>
<title>CMS内容编辑</title>
<script type="text/javascript" src="${ctx }/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx }/ckfinder/ckfinder.js"></script>
<script type="text/javascript">	
      
 		$(document).ready(function() {
 		 /*if($("#mid").val()!="3"){
           $("#table_count").hide();
           $("#table_count1").show();
         
            }else{
          		  $("#table_count").show();
                    $("#table_count1").hide();
                 
            }*/
          
       	$("#table_count").show();
       	$("#table_count1").hide(); 
          
		$("#inputForm").validate({		// 为inputForm注册validate函数
			meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
			rules: {
				title:{
					required: true,
					maxlength: 20
				},
				weight:{
					required: true,
					digits:true,
					min:1,
					maxlength:10
				}
			},
			messages:{
				title:{
					required: "请输入标题",
				  	maxlength: "标题长度不能大于20个字符"
				},
				weight:{
					required: "请输入排序号",
					maxlength: "排序号长度不能大于10个字符"
				}
			}
		});
	});
   
	

	function changeMid(){
             if($("#mid").val()=="3"){
                $("#table_count").hide();
                $("#table_count1").show();
                $("#content").text(" ");
                $("#content1").text(" ");
                 }else{
               		  $("#table_count").show();
                         $("#table_count1").hide();
                         $("#content1").text(" ");
                         $("#content").text(" ");
                 }

		}
		
	function closeWin() {
		closeEasyWin('winId');
	}
</script>
<style>
.inpu_trst_a{
	margin:0 atuo;
	width: 68px;
	height: 22px;
	background:#8090b2;
	color:#fff;
	line-height:22px;
	display:block;
	text-align:center;
	font-family:"微软雅黑";
	text-decoration:none;
	margin:0 0 0 auto;
}
</style>
</head>
<body style="background: #FFF">
	<form id="inputForm" action="${ctx}/app/cms/channelcms/ipHelpUpdate.act" method="post" autocomplete="off">
		<input type="hidden" name="id" id="id" value="${article.id}" />
		<input type="hidden" name="mid" id ="mid" value="${mid}"/>
		<table align="center" border="0" width="100%" class="form_table">
			<tr>
				<td width="10%">
					<font style="color: red">*</font>标题：
				</td>
				<td>
					<input name="title" id="title" theme="simple" size="50" maxlength="40" value="${article.title}"/>
				</td>
			</tr>
			<tr>
				<td width="10%">
					<font style="color: red">*</font>排序：
				</td>
				<td>
					<input name="weight" id ="weight" theme="simple" size="18" maxlength="11" value="${article.weight}">(只能输入数据,列表根据数字大小排序)
					<input type="hidden" id="hiddenWeight" name="hiddenWeight" value="${article.weight}" />
				</td>
			</tr>
			<tr id="table_count">
				<td width="10%">
					内容：
				</td>
				<td >
					<textarea name="content" id ="content" theme="simple" cols="80" rows="20">${article.content }</textarea>
					<script type="text/javascript">
			            var editor = CKEDITOR.replace('content', { height: 180, width: 738 });
			        </script>
				</td>
			</tr>
			<tr id="table_count1">
				<td class="form_label" style="width:80px;">
					内容：
				</td>
				<td >
					<textarea name="content1" id ="content1" theme="simple" cols="110" rows="20">${article.content }</textarea>
				</td>
			</tr>
			
			
		</table>
		<table align="center" border="0" width="100%" class="form_table">
			<tr class="form_bottom" style="height: 35px;line-height: 0px;">
				<td colspan="2">
					<a href="javascript:void(0);" class="inpu_trst_a" iconCls="icon-save" onclick="saveData();">保存</a>　
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){
			parent.closeEasyWin('winId');
	   //parent.closeRefresh();
	   }
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>


function saveData()
{
	var content1html = editor.document.getBody().getHtml();
	inputForm.content1.value=content1html;
	$('#inputForm').submit();
}
</script>