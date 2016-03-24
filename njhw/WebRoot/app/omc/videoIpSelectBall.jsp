<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>球机点位</title>
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
		
	</head>
	<body style="height: auto; background: #fff;">
		<!-- 分页一览页面 -->
		<div style="overflow-y:auto; height:600px">
		<div class="fkdj_index">
			<div class="oper_border_right">
				<div class="oper_border_left">
					球机点位
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="fkdj_lfrycx">
				    <table>
				    <tr>
				    <td valign="top" style="padding-right:10px;">
				    <label><strong style="color:#77abba;">19层球机点位</strong></label>
				    <br/>
				    <div style="font-size:12px;color:#808080;padding-top:4px;"><input type="checkbox" id="10.250.247.166" onclick="changeVedioByIp('10.250.247.166')"/>A座南</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.167" onclick="changeVedioByIp('10.250.247.167')"/>A座东</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.168" onclick="changeVedioByIp('10.250.247.168')"/>B座西</div>
				 	<div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.175" onclick="changeVedioByIp('10.250.248.175')"/>B座西南角</div>
				    
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.166" onclick="changeVedioByIp('10.250.248.166')">C座西北角</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.176" onclick="changeVedioByIp('10.250.248.176')"/>C座西</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.170" onclick="changeVedioByIp('10.250.248.170')"/>D座北</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.90" onclick="changeVedioByIp('10.250.248.171')"/>D座东</div>
            	    </td>
				    
				    <td valign="top" style="padding-right:10px;">
				    <label><strong style="color:#77abba;">3层球机点位</strong></label>
				    <br/>
				    <div style="font-size:12px;color:#808080;padding-top:4px;"><input type="checkbox" id="10.250.247.158" onclick="changeVedioByIp('10.250.247.158')"/>东门厅南</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.159" onclick="changeVedioByIp('10.250.247.159')"/>东门厅北</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.247" onclick="changeVedioByIp('10.250.248.247')"/>A座南</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.248" onclick="changeVedioByIp('10.250.248.248')"/>A座东</div>
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.244" onclick="changeVedioByIp('10.250.248.244')"/>B座南</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.245" onclick="changeVedioByIp('10.250.248.245')"/>B座西</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.248.246" onclick="changeVedioByIp('10.250.248.246')"/>B座西南角</div>
				    <br/>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.244" onclick="changeVedioByIp('10.250.247.244')"/>C座西</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.245" onclick="changeVedioByIp('10.250.247.245')"/>C座西北角</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.246" onclick="changeVedioByIp('10.250.247.246')"/>C座北</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.247" onclick="changeVedioByIp('10.250.247.247')"/>D座北</div>
				    <div style="font-size:12px;color:#808080;"><input type="checkbox" id="10.250.247.248" onclick="changeVedioByIp('10.250.247.248')"/>D座东</div>
				    
				   
				    </td>
				
				    </tr>
				    </table>		    
				</div>
			</div>
		</div>
	</div>
</body>
</html>
