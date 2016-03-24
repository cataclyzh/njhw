<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,java.text.*"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: XieRX
- Date: 2012-7-9
- Description: 问题帖新建页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>创建问题贴</title>
<%@ include file="/common/include/meta.jsp"%>
<script src="../../scripts/custom/querySubmit.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#receiver").focus();

		changebasiccomponentstyle();
	});
	var options = {
		onkeyup : false,
		rules : {
			questTitle : {
				required : true,
				minlength : 1
			},
			vendorRegion : {
				required : true
			},
			questCompany : {
				required : true
			},
			questType : {
				required : true
			},
			questLevel : {
				required : true
			},
			content : {
				required : true,
				maxlength : 500,
				minlength : 1
			},
			receiver : {
				required : true
			/**,
			remote: {
				url: 'msgBoxAction_checkReciver.act',
				data: {
					receiver: function() { 
						return $("#receiver").val(); 
					}
				}
			}*/
			}
		},
		messages : {
			questTitle : {
				required : " 请输入问题贴标题",
				minlength : " 消息标题最少为一个字符"
			},
			vendorRegion : {
				required : " 请输入您所在的区域"
			},
			questCompany : {
				required : " 请选择供应商"
			},
			questType : {
				required : " 请选择问题贴类型"
			},
			questLevel : {
				required : " 请选择问题贴优先级"
			},
			content : {
				required : " 请输入问题贴内容",
				maxlength : " 消息内容最多支持五百个字符",
				minlength : " 消息内容最少为一个字符"
			}
		}
	}
	function getFileName(path){
		var pos1 = path.lastIndexOf('/');
		var pos2 = path.lastIndexOf('\\');
		var pos  = Math.max(pos1, pos2)
		if( pos<0 )
		return path;
		else
		return path.substring(pos+1);
	}
	function formSubmit() {
		$("#message_form").validate(options);
		$('#message_form').attr('action', 'save.act');
		var filepath=document.getElementById("file").value;
		//获取文件后缀名并转成小写
	　　var ext=filepath.substring(filepath.lastIndexOf(".")).toLowerCase();
		$("#fileName").val(getFileName(filepath));
		$("#fileExtension").val(ext);
		
		$('#message_form').submit();
	}
	function upFile() {
		$("#message_form").validate(options);
		$("#questTitle").rules("remove");
		$("#vendorRegion").rules("remove");
		$("#questCompany").rules("remove");
		$("#questType").rules("remove");
		$("#questLevel").rules("remove");
		$("#content").rules("remove");
		$("#receiver").rules("remove");
		$('#message_form')
				.attr('action', 'msgBoxAction_uploadVendorsFiles.act');
		$('#message_form').submit();
	}
</script>
<style type="text/css">
.textfield {
	width: 240px;
}

.textarea {
	width: 360px;
	height: 120px;
}
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0">
	<div id='parentVendorWin' class='easyui-window' collapsible='false' minimizable='false' maximizable='true'
		style='padding: 0px; background: #fafafa; overflow: hidden;display: none;' closed='true'>
	<iframe id='parentVendorIframe' name='parentVendorIframe' style='width:100%; height:100%;' frameborder='0'></iframe>
</div>
	<h:panel id="bulletin_panel" width="100%" title="写公告">
		<div>&nbsp;</div>

		<form id="message_form" action="save.act"
			method="post" autocomplete="off" enctype="multipart/form-data">
			<table align="center" border="0" width="800px" class="form_table">
				<!-- 
					<tr>
					<td class="form_label" width="120px">&nbsp;&nbsp;供应商：</td>
					<td class="form_label">
						<s:textfield name="questCompany" theme="simple" readonly="true" cssClass="input180box" cssStyle="background-color: aliceblue;"/><span style="color: red;">*</span>
       					<input type="button" id="parentVendorBtn" name="parentVendorBtn" onclick="queryParentVendor();" value="选择" />
       					<input type="button" id="clearParentVendorBtn" name="clearParentVendorBtn" onclick="clearParentVendor();" value="清空" />
		           </td>
		           <!-- <s:hidden name="questCode" id="questCode"/> -->
				</tr>
				<tr>
					<td class="form_label" width="120px">&nbsp;&nbsp;供应商所属区域：</td>
					<td class="form_label">
						<s:select list="#application.DICT_VENDOR_REGION" name="vendorRegion" cssClass="input180box" emptyOption="false" headerKey=""  headerValue="全部" 
		           			listKey="dictcode" listValue="dictname"/> <span style="color: red;">* 请根据您所在区域选择 </span>
		           </td>
				</tr>
				<tr>
					<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;录入人：</td>
					<td>&nbsp;<s:property value="questUser"/></td>
				</tr>
				<tr>
					<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;录入时间：</td>
					<td>&nbsp;<%=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())%></td>
				</tr>
				<tr>
					<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;问题分类：</td>
					<td class="form_label">
						<s:select list="#application.DICT_QUEST_TYPE" name="questType" cssClass="input180box" emptyOption="false" headerKey=""  headerValue="全部" 
		           			listKey="dictcode" listValue="dictname"/> <span style="color: red;">* 请确定您的问题属于的分类，以便问题处理人更快地处理回复。</span>
					</td>
				</tr>
				<tr>
					<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;优先级：</td>
					<td class="form_label">
						<s:select list="#application.DICT_QUEST_LEVEL" name="questLevel" cssClass="input180box" emptyOption="false" headerKey=""  headerValue="全部" 
		           			listKey="dictcode" listValue="dictname"/><span style="color: red;">*</span>
					</td>
				</tr>
				<tr>
					<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;问题标题：</td>
					<td><textarea name="questTitle" class="textfield" id="questTitle"></textarea>
						<span style="color: red;">*</span></td>
				</tr>
				<tr>
					<td width="120px" height="600px" nowrap class="form_label">&nbsp;&nbsp;问题内容：</td>
					<td><textarea name="entityAns.ansContent" cols="" rows="" id="content"
							class="textarea"></textarea><span style="color: red;">*</span></td>
				</tr>
				<tr>
					<td class="form_label" width="120px" nowrap>&nbsp;&nbsp;附件：</td>
					<td><s:file name="file" id="file" cssClass="input180box"/><span style="color: red;">如果有附件说明，请上传；单个文件大小不超过2M。</span>
					<s:hidden name="entityFile.fileName" id="fileName"></s:hidden>
					<s:hidden name="entityFile.fileExtension" id="fileExtension"></s:hidden>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="form_bottom"><a href="javascript:void(0);"
						class="easyui-linkbutton" iconCls="icon-ok" id="submitbutton"
						onclick="javascript: formSubmit();">创建</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton"
						iconCls="icon-reload" onclick="$('#message_form').resetForm();">重置</a>
					</td>
				</tr>
			</table>
			<input type="hidden" name="status" value="0" id="status" />
		</form>

	</h:panel>

</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
<script type="text/javascript">
    //供应商信息
	function queryParentVendor() {
	    var url = "${pageContext.request.contextPath}/tesco/sp/parentVendorQryAction/init.act?seed";
	    var params = "";
	    url += params;
		var width = 800;
		var left = (document.body.scrollWidth - width) / 2;
		$("#parentVendorWin").show();
		$("#parentVendorWin").window( {
			title : '供应商信息',
			modal : true,
			shadow : false,
			closed : false,
			width : width,
			height : 400,
			top : 20,
			left : left
		});
		$("#parentVendorIframe").attr("src", url);
	}

	function showParentVendorInfo(info){
		$("#questCompany").val(info[1]);
		$("#questCode").val(info[0]);
	}

	function clearParentVendor(){
		$("#questCompany").val("");
	}
</script>
