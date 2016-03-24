<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<title>My JSP 'fax_snd.jsp' starting page</title>

<%@ include file="/common/include/meta.jsp"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
<script type="text/javascript" src="js/queryString.js"></script>



<style>
body {
	background: url(a.jpg) left top repeat-x;
}
.label {
	padding-top: 2px;
	padding-bottom: 2px;
}

h2 {
	font-size: large;
	font-weight: normal;
}

textarea {
	width: 100%;
	font-size: medium;
}

input[type="text"] {
	height: 22px;
	width: 100%;
	font-size: medium;
	font-weight: normal;
}
#preview,#send {
	cursor:pointer;
}
.main3_left_01 table tr td p a {
	text-decoration: none;
}
#fo_to{
	width:90%;
}

.input_botton_sst{
	/*width:610px;*/
	width: 100%;
	border:solid 1px #cdcdcd;
	margin-bottom: 3px;
	padding-right: 2px;
}
.input_table_name{
	width:100%;
	border:none;
	height:22px;
	line-height:22px;
}
a {
    text-decoration: none;
}
</style>
</head>

<body style="height: auto;">
	<div class="main_border_01">
		<div class="main_border_02">发传真</div>
	</div>
	<div class="main_conter" >
		<div style="width: 62%;float: left;margin-top: 10px;">
			<form action="sendFax.act" method="post" class="main3_from"
				id="fax_form" enctype="multipart/form-data" name="fax_form">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">

					<tr>
						<td class="main_from_left">收件人</td>
						<td>
							<div class="input_botton_sst">
								<input name="fax_receiver" id="fo_to" class="input_table_name"  
									type="text" onfocus="this.blur();"/>
								
								
								<a href="javascript:void(0);" id="add-receiver">
									<img src="images/input_botton_lest.jpg" width="19" height="14"/>
								</a>
								
								<a href="javascript:void(0);" id="delete-receiver">
									<img src="images/input_botton_rights.jpg" width="19" height="14" id="delete-receiver"/>
								</a>
							</div>
							
							<input type="hidden" name="fo_to" id="fax-hidden"/>
							
						</td>	
						<!-- <td><input name="fax_receiver" id="fo_to" class="main_input"  
							type="text" size="50" onfocus="this.blur();"/>
							<a href="javascript:void(0);" id="add-receiver">添加</a>
							<a href="javascript:void(0);" id="delete-receiver">删除</a>
							
							<input type="hidden" name="fo_to" id="fax-hidden"/>
						</td> -->
						
						
					</tr>
					
					<tr>
						<td class="main_from_left">发件人</td>
						<td><input name="fo_sender" id="fo_sender" class="main_input" type="text"
							size="70" onfocus="this.blur();" value="${_username}"/></td>
					</tr>
					
					
					
					<tr>
						<td class="main_from_left">主题</td>
						<td><input name="fo_subject" id="fo_subject" class="main_input" type="text"
							size="70"/></td>
					</tr>
					<tr>
						<td valign="top" class="main_from_left">正文</td>
						<td style="padding-top:3px;"><textarea name="fo_body"
								id="fo_body" class="main_input" cols="70" rows="14" ></textarea>
						</td>
					</tr>
					<tr>
						<td class="main_from_left">附件列表</td>
						<td id="fax_td">
							<p style="padding-top:6px;padding-bottom: 6px">
								<input type="file" name="fax_attach" />&nbsp;&nbsp;
								<a href="javascript:void(0)" id="addNewAttach">继续添加</a>&nbsp;&nbsp;
								<a href="javascript:void(0)" id="clearAttach" style="display:none;">清除附件</a>
								
							</p>
						</td>
					</tr>


					<tr>
						<td class="main_from_left">&nbsp;</td>
						<td class="main_from_right1"><input name="urgent"
							type="checkbox" value="1" style="vertical-align: text-bottom;" />&nbsp;加急传真</td>
					</tr>
					<tr>
						<td class="main_from_left">&nbsp;</td>
						<td>
						<!--
						<input name="preview" id="preview" value="预览" type="button" style="width: 120px;font-size: medium;"/>
							&nbsp;
						-->
							<input name="send" id="send"  value="发送" type="button" style="width: 120px;font-size: medium;"  />
							<input name="reset" id="reset"  value="重置" type="button" style="width: 120px;font-size: medium;float: right;"  />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div
			style="width:35%;height:480px; float: left;margin-left: 20px;">
			<div class="clear"></div>

			
			 <iframe src="fax_contact.jsp" width="100%" height="100%" scrolling="no"
				frameborder="0"></iframe> 

			
<%-- 			 <iframe src="${ctx}/app/integrateservice/contact.act" width="100%" height="100%" scrolling="no"
				frameborder="0"></iframe> --%> 
				
			<%-- <jsp:include
				page="fax_public_book.jsp?orgId=70&type=tel&smac=${smac}&stel=${stel}">
				<jsp:param value="" name="" />
			</jsp:include> --%>

			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="js/fax_send.js"></script>
	<script type="text/javascript">
	
		
		
		//如果session中有fo_body 值放到textarea里
		
		//alert(getQueryString("sid"));
		if (getQueryString("sid") == "cancel"){
		
			//textarea 回显
			var fo_body = "${fo_body}";
			$("#fo_body").html(fo_body);
			//主题回显
			var fo_subject = "${fo_subject}";
			$("#fo_subject").val(fo_subject);
			//隐藏域 name=fo_to
			var fo_to = "${fo_to}";
			$("#fax-hidden").val(fo_to);
			//收件人回显 id=fo_to name=fax_receiver
			var fax_receiver = "${fax_receiver}";
			$("#fo_to").val(fax_receiver);
		
			
		}

</script>
</body>
</html>
