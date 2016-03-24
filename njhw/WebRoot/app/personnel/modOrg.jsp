<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>访客预约</title>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript" />
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/js/ajaxfileupload.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
	var isSuc = $("#isSuc").val();
	if(isSuc=="true"){
      alert("修改成功！");
		}
	if(isSuc=="false"){
alert("修改失败！")
		}
		});
		</script>

  </head>
  
  <body>
  	<form id="inputForm" action="saveModOrg.act" method="post"
			autocomplete="off">
			<input type="hidden" name="isSuc" id="isSuc" value="${isSuc}"/>
			<input type="hidden" name="org.orgId" id="id" value="${org.orgId }"/>
     组织机构名称：<input type="text" name="org.name" id="name" value="${org.name}"/>
           <input type="submit" name="submit" id="submit" value="修改"/>
     	</form> 
  </body>
</html>
