<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>电梯内监控点位</title>
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
					电梯内监控点位
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="fkdj_lfrycx">
				    <table>
				    <tr>
				    <td valign="top" style="padding-right:10px;">
				    <label><strong style="color:#77abba;">A、B座电梯监控</strong></label>
				    <br/>
				    <div style="font-size:12px;color:#808080;padding-top:4px;"><input type="checkbox" id="10.250.248.180" onclick="changeVedioByIp('10.250.248.180')"/>A座一号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.181" onclick="changeVedioByIp('10.250.248.181')"/>A座二号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.182" onclick="changeVedioByIp('10.250.248.182')"/>A座三号电梯</div>
				 	<div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.183" onclick="changeVedioByIp('10.250.248.183')"/>A座四号电梯</div>
				 	<div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.184" onclick="changeVedioByIp('10.250.248.184')"/>A座消防电梯</div>
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.185" onclick="changeVedioByIp('10.250.248.185')">B座一号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.186" onclick="changeVedioByIp('10.250.248.186')"/>B座二号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.187" onclick="changeVedioByIp('10.250.248.187')"/>B座三号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.188" onclick="changeVedioByIp('10.250.248.188')"/>B座四号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.189" onclick="changeVedioByIp('10.250.248.189')"/>B座消防电梯</div>
				    
            	    </td>
				    <td valign="top" style="padding-right:10px;">
				    <label><strong style="color:#77abba;">C、D座电梯监控</strong></label>
				    <br/>
				 
				     <div style="font-size:12px;color:#808080;padding-top:4px;"><input type="checkbox" id="10.250.247.180" onclick="changeVedioByIp('10.250.247.180')">C座一号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.181" onclick="changeVedioByIp('10.250.247.181')"/>C座二号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.182" onclick="changeVedioByIp('10.250.247.182')"/>C座三号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.183" onclick="changeVedioByIp('10.250.247.183')"/>C座四号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.184" onclick="changeVedioByIp('10.250.247.184')"/>C座消防电梯</div>
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.185" onclick="changeVedioByIp('10.250.247.185')">D座一号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.186" onclick="changeVedioByIp('10.250.247.186')"/>D座二号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.187" onclick="changeVedioByIp('10.250.247.187')"/>D座三号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.188" onclick="changeVedioByIp('10.250.247.188')"/>D座四号电梯</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.189" onclick="changeVedioByIp('10.250.247.189')"/>D座消防电梯</div>
				    <br/>
            	    </td>
				 
				
				    </tr>
				    </table>		    
				</div>
			</div>
		</div>
	</div>
</body>
</html>
