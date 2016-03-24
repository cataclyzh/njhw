<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>食堂厨房监控点位</title>
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
					食堂厨房监控点位
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="fkdj_lfrycx">
				    <table>
				    <tr>    
				    <td valign="top" style="padding-right:10px;">
				    <label><strong style="color:#77abba;">中心厨房、热厨区</strong></label>
				    <br/>
				    <div style="font-size:12px;color:#808080;width:115px;padding-top:4px;"><input type="checkbox" id="10.250.248.162" onclick="changeVedioByIp('10.250.248.162')"/>中心厨房切配间</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.163" onclick="changeVedioByIp('10.250.248.163')"/>中心厨房粗加工</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.165" onclick="changeVedioByIp('10.250.248.165')"/>米饭生产间</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.175" onclick="changeVedioByIp('10.250.247.175')"/>米饭生产间外过道</div>
				    <br/>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.167" onclick="changeVedioByIp('10.250.248.167')"/>热厨区南</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.172" onclick="changeVedioByIp('10.250.248.172')"/>热厨区北</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.164" onclick="changeVedioByIp('10.250.247.164')"/>热厨区</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.162" onclick="changeVedioByIp('10.250.247.162')"/>热厨区外过道</div>
				
				    
				    </td>
				
				    <td valign="top" style="padding-right:10px;">
				    <label><strong style="color:#77abba;">面点间及其他</strong></label>
				    <br/>

				    <div style="font-size:12px;color:#808080;width:115px;padding-top:4px;"><input type="checkbox" id="10.250.248.173" onclick="changeVedioByIp('10.250.248.173')"/>面点间南</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.160" onclick="changeVedioByIp('10.250.247.160')"/>面点间北</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.163" onclick="changeVedioByIp('10.250.247.163')"/>西点间西</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.165" onclick="changeVedioByIp('10.250.247.165')"/>西点间东</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.161" onclick="changeVedioByIp('10.250.247.161')"/>西点间外过道</div>
				    
				    <br/>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.168" onclick="changeVedioByIp('10.250.248.168')"/>蒸烤间</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.169" onclick="changeVedioByIp('10.250.248.169')"/>盒饭包装间</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.161" onclick="changeVedioByIp('10.250.248.161')"/>库管办公室外过道</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.164" onclick="changeVedioByIp('10.250.248.164')"/>会计室外过道</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.160" onclick="changeVedioByIp('10.250.248.160')"/>垃圾房门口</div>
				    
				    </td>
				
				
				    </tr>
				    </table>		    
				</div>
			</div>
		</div>
	</div>
</body>
</html>
