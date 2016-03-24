<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>B座</title>
		<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet"
			type="text/css" />
			<link href="${ctx}/app/omc/css/omc.css" rel="stylesheet" type="text/css" />
			
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script src="${ctx}/app/omc/js/changeVideo.js"
			type="text/javascript"></script>
		<script type="text/javascript">
         document.domain = "njnet.gov.cn";
        </script>
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div style="overflow-y:auto; height:600px">
		<div class="fkdj_index">
			<div class="oper_border_right">
				<div class="oper_border_left">
					B座
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="fkdj_lfrycx">
				    <table>
				    <tr>
				    <td valign="top" style="padding-right:10px;">
				    </td>
				    
				    <td valign="top" style="padding-right:10px;">
				    </td>
				    <td valign="top">
				    <br/>
				    <div style="font-size:12px;color:#808080;padding-top:4px;"><input type="checkbox" id="10.250.248.135" onclick="changeVedioByIp('10.250.248.135')"/>B座1层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.114" onclick="changeVedioByIp('10.250.248.114')"/>B座2层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.120" onclick="changeVedioByIp('10.250.248.120')"/>B座4层中</div>
				   	 <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.142" onclick="changeVedioByIp('10.250.248.142')"/>B座5层中</div>
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.115" onclick="changeVedioByIp('10.250.248.115')"/>B座6层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.121" onclick="changeVedioByIp('10.250.248.121')"/>B座7层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.126" onclick="changeVedioByIp('10.250.248.126')"/>B座8层中</div>
				  	<div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.127" onclick="changeVedioByIp('10.250.248.127')"/>B座9层中</div>
				  
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.140" onclick="changeVedioByIp('10.250.248.140')"/>B座10层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.107" onclick="changeVedioByIp('10.250.248.107')"/>B座11层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.105" onclick="changeVedioByIp('10.250.248.105')"/>B座12层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.110" onclick="changeVedioByIp('10.250.248.110')"/>B座13层中</div>
				    
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.106" onclick="changeVedioByIp('10.250.248.106')"/>B座14层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.104" onclick="changeVedioByIp('10.250.248.104')"/>B座15层中</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.102" onclick="changeVedioByIp('10.250.248.102')"/>B座16层中</div>
				   	 <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.123" onclick="changeVedioByIp('10.250.248.123')"/>B座17层中</div>
				   
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.108" onclick="changeVedioByIp('10.250.248.108')"/>B座18层中</div>
				    </td>
				    </tr>
				    </table>		    
				</div>
			</div>
		</div>
	</div>
</body>
</html>
