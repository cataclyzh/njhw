<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新增意见</title>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
	</head>
	<body>
		<div class="main_index">
			<jsp:include page="/app/integrateservice/header.jsp">
				<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
			<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
				<div class="bgsgl_border_left">
				 <!--此处写页面的标题 -->
					<div class="qbyj_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">
						意见箱管理
					</div>
				</div>
				<div class="bgsgl_conter" style="min-height: 300px">
				    <div class="fkdj_index">
					    <div class="qbyj_left">
					    	<%@ include file="/app/suggest/suggesstLeft.jsp" %>
						</div>
						<div class="qbyj_right">
							<div class="bgsgl_border_left">
								<div class="qbyj_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">
									新增意见
								</div>
							</div>
							<div class="bgsgl_conter">
								<form action="${ctx}/app/suggest/saveSuggest.act" id="saveForm" method="post">
							     <table width="100%" border="0" cellspacing="0" cellpadding="0">
									  <tr>
									    <td valign="top" class="qbyj_valign">正文</td>
									    <td style="text-align:right;"><textarea id="qbyj_textarea" name="suggest.content" class="qbyj_textarea" name="" cols="" rows=""></textarea></td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td style="padding:10px 0;"><a class="fkdj_botton_left" href="javascript:void(0);" onclick="publishSug();">发布意见</a><a class="fkdj_botton_right"  href="javascript:void(0);" onclick="javascript:saveForm.reset()">重置意见</a></td>
									  </tr>
							      </table>
							     </form>
							</div>
						</div>
				    </div>
				    <!--此处写页面的内容 -->
					<div class="bgsgl_clear"></div>
				</div>
			</div>
			<jsp:include page="/app/integrateservice/footer.jsp" />
		</div>
		
	<script type="text/javascript">
		function publishSug(){
		   var contentLength=0,
               charCode=0,
               i,
               len=0;
		   var content=$("#qbyj_textarea").val();
		   contentLength=content.replace(/(^\s*)|(\s*$)/g, "").length;
		   if(contentLength>0){
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
                if(contentLength>3500){
		            easyAlert('提示信息','提交意见过长！','info');
		          return;
		        }else{
		            $("#saveForm").get(0).submit();
		        }
		   }else{
		        easyAlert('提示信息','请输入内容！','info');
		        return;
		   }
		}
		
	</script>	
	</body>
</html>