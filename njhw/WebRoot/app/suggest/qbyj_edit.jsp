<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.cosmosource.base.util.DateUtil" %>	
<%@page import="java.util.Date;" %>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>编辑答复</title>
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css"
			rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/suggest/css/qbyj_index.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/app/integrateservice/css/wizard_css.css"
			rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script src="${ctx}/app/suggest/js/suggesst.js" type="text/javascript"></script>
		<script type="text/javascript">
		
			function saveReply(repid,sugid){
				var contentLength=0,
               	charCode=0,
              	 i,
              	 len=0;
		   		var content=$("#content").val();
		   		contentLength=content.replace(/(^\s*)|(\s*$)/g, "").length;
		      	 content=content.replace(/(^\s*)|(\s*$)/g, "");
		      	 len = content.length
		      	 for(i = 0; i < len; i++){
                   charCode = content.charCodeAt(i);
                   if(charCode <= 0x007f) {
                     contentLength += 1;
                   }else if(charCode <= 0x07ff){
                     contentLength += 2;
                   }else if(charCode <= 0xffff){
                     contentLength += 3;
                   }else{
                     contentLength += 4;
                   }
                }
                if(content==null || content=="")
				{	
					easyAlert('提示信息','请输入内容！','info');
					return;
				}
				if(contentLength>2000){
		            easyAlert('提示信息','提交回复超过500字！','info');
		          	return;
		        }
				openAdminConfirmWin(saveReplyFn,{repid:repid,sugid:sugid});
			}
			function saveReplyFn(params) {
				
				var content=$("#content").val();
				var sugCreatetime=$("#sugCreatetime").val();
				var repCreatetime=$("#repCreatetime").val();
				var pageNo = $("#pageNo").val();
				var url="saveOrUpdateSuggestReply.act?repid="+params.repid+"&suggestCreatetime="+sugCreatetime+"&replyCreatetime="+repCreatetime+"&sugid="+params.sugid;
				//$("#editReplyForm").submit();
				$("#editReplyForm").attr("action",url);
				//$("#editReplyForm").submit();
				
				var options = {
					async:false,
				    dataType:'text',
				    success: function(responseText) { 
						easyAlert("提示信息", "处理成功！","info", function(){
							parent.frameDialogClose(responseText,params.sugid);
						});
					}
				};
				$('#editReplyForm').ajaxSubmit(options);
			}
			
			
			function resetContent(){
				$("#content").val("");
			}
		</script>
	</head>
   
	<body  STYLE="OVERFLOW:AUTO;background: #fff;">
	<div class="bgsgl_right_list_border">
		<div class="bgsgl_right_list_left">
			<c:if test="${not empty repid}">
			编辑答复
			</c:if>
			<c:if test="${empty repid}">
			答复意见
			</c:if>
		</div>
	</div>
		<div class="qbyj_index" style="width: 90%;height:100%">
			<div class="khfw_wygl">
				<form action="" method="post" id="editReplyForm" name="editReplyForm">
					<input id="pageNo" value="${pageNo }" type="hidden">
					<div class="show_from" style="margin-top: 10px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="13%">
									提出时间：
								</td>
								<td width="87%">
									<input name="" type="text" class="show_inupt_qbyj" readonly="readonly" value="${suggest.createtimeString}" />
								</td>
							</tr>
							<tr>
								<td width="13%">
									答复时间：
								</td>
								<td width="87%">
								<c:choose>
									<c:when test="${suggestReply == null}">
										<input name="repCreatetime" id="repCreatetime" type="text" class="show_inupt_qbyj" readonly="readonly" value="<%=DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss") %>" />
									</c:when>
									<c:otherwise>
										<input name="" type="text" class="show_inupt_qbyj" readonly="readonly" value="${suggestReply.createtimeString}" />
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<td valign="top">
									答复内容：
								</td>
								<td colspan="3">
									<textarea name="content" id="content" class="qbyj_textarea_list" style="float:left" cols="80" rows="15">${suggestReply.content }</textarea>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<div class="qbyj_sqs_color">
				<a class="fkdj_botton_left" href="javascript:void(0);" onclick="saveReply(${suggestReply.repid==null?"''":suggestReply.repid},${suggest.sugid})">保&nbsp;&nbsp;存</a>
				<a class="fkdj_botton_right" href="javascript:void(0);" onclick="resetContent();">重&nbsp;&nbsp;置</a>
			</div>
		</div>
	</body>
</html>

