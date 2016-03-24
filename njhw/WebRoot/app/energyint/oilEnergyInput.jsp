<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: hj
- Date: 2013-09-24
- Description: 油耗采集录入
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>油耗采集录入</title>
	<%@ include file="/common/include/metaIframe.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">	
		$(document).ready(function() {
			
		});
		
		function saveData(){
		  $("#inputForm").valid();
		  if ($("#inputForm").valid()) {
		  	var frm = $('#inputForm');
		  	$('#inputForm').attr("action","queryOilEnergyInputSave.act");
 		    frm.submit();
 		    window.top.$("#oilInput").window("close");
		  }
		}
		
function CheckInputIntFloat(oInput)
{
    if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
    {
        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
    }
}
	</script>
</head>
<body style="background:#fff;">
<form  id="inputForm" action="queryOilEnergyInput.act"  method="post"  autocomplete="off" >
	<div style="height: 20px;"></div>
	<div style="height: 440px; overflow: auto;">
	<d:table name="result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col title="单位" style="width:80" class="display_centeralign" property="ORG_NAME"></d:col>
			<d:col class="display_centeralign"  title="${energyOpt['-3']}(汽油)">
				<input style="width:70px; padding-left: 10px; color: #818284;"
					type="text" name="oilG_${row.ORG_ID}_3" value="${row.MEASURE_3}"
								size="20" maxlength="20" onkeyup="javascript:CheckInputIntFloat(this);"/>
			</d:col>
			<d:col class="display_centeralign"  title="${energyOpt['-3']}(柴油)">
				<input style="width:70px; padding-left: 10px; color: #818284;"
					type="text" name="oilD_${row.ORG_ID}_3" value="${row.MEASURE_HEAT_3}"
								size="20" maxlength="20" onkeyup="javascript:CheckInputIntFloat(this);"/>
			</d:col>
		    <d:col class="display_centeralign"  title="${energyOpt['-2']}(汽油)">
				<input style="width:70px; padding-left: 10px; color: #818284;"
					type="text" name="oilG_${row.ORG_ID}_2" value="${row.MEASURE_2}"
								size="20" maxlength="20" onkeyup="javascript:CheckInputIntFloat(this);"/>
			</d:col>
			<d:col class="display_centeralign"  title="${energyOpt['-2']}(柴油)">
				<input style="width:70px; padding-left: 10px; color: #818284;"
					type="text" name="oilD_${row.ORG_ID}_2" value="${row.MEASURE_HEAT_2}"
								size="20" maxlength="20" onkeyup="javascript:CheckInputIntFloat(this);"/>
			</d:col>
			<d:col class="display_centeralign"  title="${energyOpt['-1']}(汽油)">
				<input style="width:70px; padding-left: 10px; color: #818284;"
					type="text" name="oilG_${row.ORG_ID}_1" value="${row.MEASURE_1}"
								size="20" maxlength="20" onkeyup="javascript:CheckInputIntFloat(this);"/>
			</d:col>
			<d:col class="display_centeralign"  title="${energyOpt['-1']}(柴油)">
				<input style="width:70px; padding-left: 10px; color: #818284;"
					type="text" name="oilD_${row.ORG_ID}_1" value="${row.MEASURE_HEAT_1}"
								size="20" maxlength="20" onkeyup="javascript:CheckInputIntFloat(this);"/>
			</d:col>
	</d:table>
	</div>
    <div class="botton_from">
        	<a href="javascript:void(0)" onclick="saveData();" style="float:right;">保&nbsp;&nbsp;存</a>
    </div>
  </form>
</body>
</html>

