<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>食堂监控点位</title>
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
					食堂监控点位
				</div>
			</div>
			<div class="bgsgl_conter">
				<div class="fkdj_lfrycx">
				    <table>
				    <tr>
				    <td valign="top" style="padding-right:10px;">
				    <label><strong style="color:#77abba;">餐厅</strong></label>
				    <br/>
				    <div style="font-size:12px;color:#808080;width:115px;padding-top:4px;"><input type="checkbox" id="10.250.248.166" onclick="changeVedioByIp('10.250.248.166')">一号餐厅打饭处西</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.170" onclick="changeVedioByIp('10.250.248.170')"/>一号餐厅中</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.90" onclick="changeVedioByIp('10.250.248.171')"/>一号餐厅西南角</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.176" onclick="changeVedioByIp('10.250.248.176')"/>一号餐厅打饭处东</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.248.175" onclick="changeVedioByIp('10.250.248.175')"/>一号餐厅东北角</div>

				    <br/>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.166" onclick="changeVedioByIp('10.250.247.166')"/>二号餐厅打饭处西</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.167" onclick="changeVedioByIp('10.250.247.167')"/>二号餐厅中</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.168" onclick="changeVedioByIp('10.250.247.168')"/>二号餐厅打饭处东</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.169" onclick="changeVedioByIp('10.250.247.169')"/>二号餐厅东南角</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.174" onclick="changeVedioByIp('10.250.247.174')"/>二号餐厅西北角</div>

				    <br/>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.170" onclick="changeVedioByIp('10.250.247.170')"/>三号餐厅东北角</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.172" onclick="changeVedioByIp('10.250.247.172')"/>三号餐厅东南角</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.171" onclick="changeVedioByIp('10.250.247.171')"/>三号餐厅西北角</div>
				    <div style="font-size:12px;color:#808080;width:115px;"><input type="checkbox" id="10.250.247.173" onclick="changeVedioByIp('10.250.247.173')"/>三号餐厅西南角</div>
				    
				    </td>
				    
				  
				    
				
				
				    </tr>
				    </table>		    
				</div>
			</div>
		</div>
	</div>
</body>
</html>
