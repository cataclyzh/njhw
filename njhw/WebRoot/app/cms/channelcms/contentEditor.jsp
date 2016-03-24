<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>CMS内容编辑</title>
		<%@ include file="/common/include/meta.jsp"%>
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
						min:1
					}
				},
				messages:{
					title:{
						required: "请输入标题",
					  	maxlength: "标题长度不能大于20个字符"
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
	</head>
	<body>
		<form id="inputForm" action="${ctx}/app/cms/channelcms/saveArticle.act" method="post" autocomplete="off">
			<input type="hidden" name="id" id="id" value="${article.id}" />
			<input type="hidden" name="mid" id ="mid" value="${mid}"/>
			<table align="center" border="0" width="100%" class="form_table">
				<tr>

					<td class="form_label" style="width:50px;">
						<font style="color: red">*</font>标题：
					</td>
					<td>
						<input name="title"   id="title" theme="simple" size="100" maxlength="40" value="${article.title}"/>
					</td>
				</tr>
				<c:if test="${mid==3}">
				<tr>
					<td class="form_label">
						<font style="color: red">*</font>排序：
					</td>
					<td>
						<input name="weight"   id ="weight"   theme="simple" size="18" maxlength="20" value="${article.weight}">(只能输入数据,列表根据数字大小排序)
					</td>
				</tr>
				</c:if>
								
				<tr id="table_count" id="table_count">
					<td class="form_label" style="width:50px;">
						内容：
					</td>
					<td >
						<textarea name="content" id ="content" theme="simple" cols="80" rows="20">${article.content }</textarea>
						<script type="text/javascript">
				            var editor = CKEDITOR.replace('content', { height: 200, width: 838 });
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
				<tr class="form_bottom">
					<td colspan="2">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>　
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" onclick="closeWin()">关闭</a>
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
	   	closeEasyWin('winId');
	   	parent.closeRefresh();
	   }
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>


	function saveData(){
		//alert(inputForm.content.value);
		//var oEditor =CKEDITOR.instances.Text;//获得编辑器对象 
		//alert(editor.document.getBody().getHtml());
		
		//var text = oEditor.getData();//获取编辑器的数据 
		//alert("222"+text);
		//text =CKEDITOR.tools.htmlEncode(text);//进行HTMLEncode编码 
		//alert(text);
		//alert(inputForm.content1.value);
		var content1html = editor.document.getBody().getHtml();
		inputForm.content1.value=content1html;
		$('#inputForm').submit();
		}
</script>