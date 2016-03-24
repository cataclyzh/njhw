<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="buttons" value="del"/>
<html>
<head>
<%@ include file="/common/include/metaIframe.jsp"%>
<title>查看设备</title>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/property/css.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/property/block.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/property/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/property/css_body.css" rel="stylesheet" />
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#checkAll").bind("click", checkAll);
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		//$(this).chk_init(chk_options);
		changecheckboxstyle();
		
	});
	var checkAll = function(){
		var totalCheckBox = $("input[name='_chk']").size();
		var checkboxSize = $("input[name='_chk']:checked").size();
		if(checkboxSize == totalCheckBox){
			$("#checkAll").attr("checked", false);
			$("input[name='_chk']").attr("checked", false);
		}else{
			$("#checkAll").attr("checked", true);
			$("input[name='_chk']").attr("checked", true);
		}
	}
</script>
<style>
.table_btn_orgframe
{
    width: 68px;
    height: 22px;
    background:#8090b2;
	color:#fff;
	line-height:22px;
	text-align:center;
	font-family:"微软雅黑";
	margin-right:10px;
	float:right;
	cursor:pointer;
	text-decoration: none;
}
.table_btn_orgframe:hover{
	background:#ffc600;
}
.in_sss_left{
	margin:0;
	padding:0;
	border:0;
}
</style>	
</head>

<body style="background: #fff;">
<div class="fkdj_index">
<div class="bgsgl_border_left">
<div class="bgsgl_border_rig_ht">查看设备</div>
</div>
<div class="bgsgl_conter">
<div class="">
<div class="fkdj_from">
<form id="queryForm" name="queryForm" action="deviceListForQuery.act" method="post">
<h:panel id="query_panel" width="100%" title="查询条件">
	<table align="center" id="hide_table" border="0" width="100%">
		<tr>
			<td class="fkdj_name">设备编号</td>
			<td><s:textfield name="device_No" id="device_No"
					cssClass="fkdj_from_input" /></td>
			<td class="fkdj_name">设备类型</td>
			<td><select id="deviceType_Id" class="fkdj_from_input"
				name="deviceType_Id">
					<option value="0">全部</option>
					<c:forEach items="${deviceTypeList }" var="deviceType">
						<option value="${deviceType.deviceTypeId }"
							<c:if test='${deviceType_Id == deviceType.deviceTypeId }'> selected="selected" </c:if>>
							${deviceType.deviceTypeName }</option>
					</c:forEach>
			</select></td>
			<td class="fkdj_name">设备名称</td>
			<td><s:textfield name="device_Name" id="device_Name"
					cssClass="fkdj_from_input" /></td>
			<td><a class="property_query_button"
				href="javascript:void(0);"
				onmousemove="this.style.background='FFC600'"
				onmouseout="this.style.background = '#8090b4'"
				onclick="querySubmit();">查询 </a></td>
		</tr>
	</table>
</h:panel>

<page:pager id="list_panel" width="100%" buttons="${buttons}" title="结果列表">
	<d:table name="page.result" id="row"  export="false" class="table">
		<d:col  style="width:45"   class="display_centeralign"  title="<input type=\"checkbox\" class=\"in_sss_left\" id=\"checkAll\"/>" >
			<input type="checkbox" name="_chk" id="_chk" class="checkItem in_sss_left" value="${row.DEVICE_ID}" onclick="selectCheckbox(this)" />
		</d:col>
		<d:col class="display_centeralign" title="设备编号">
			<a style="cursor: pointer; color: black;" onclick="toViewDevice('${row.DEVICE_ID}');">${row.DEVICE_NO}</a>
		</d:col>
		<d:col class="display_centeralign" title="设备类型">
			<c:forEach items="${deviceTypeList }" var="deviceType">
				<c:if test='${row.DEVICE_TYPE_ID == deviceType.deviceTypeId }'>${deviceType.deviceTypeName }</c:if>
			</c:forEach>
		</d:col>
		<d:col property="DEVICE_NAME" class="display_centeralign" title="设备名称" />
		<d:col property="DEVICE_DESCRIBE" class="display_centeralign" title="备注" />
		<d:col class="display_centeralign" title="操作">
			<span class="display_centeralign" onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color= '#8090b4'" onclick="toEditDevice('${row.DEVICE_ID}');" style="cursor: pointer">[修改]</span>
		</d:col>
	</d:table>
</page:pager>
<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
<div class="clear"></div>
</form>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
	function selectCheckbox(obj){
		var checkSize = $("input[name='_chk']:checked").size();
		var allSize = $("input[name='_chk']").size();
		if(allSize == checkSize){
			$("#checkAll").attr('checked',true);
		}else{
			$("#checkAll").attr('checked',false);
		}
		var items = $("input[name='_chk']:checked");
		if(items){
			if(items.length==1){
				//$("#chaifen").show();
				//$("#hebing").hide();
				$("input[name='_chk']").attr("disabled",false);
			}else if(items.length==2){
				//$("#chaifen").hide();
				var tempItems = $("input[name='_chk']").not("input[name='_chk']:checked");
				for(var i=0;i<tempItems.size();i++){
					tempItems.eq(i).attr("disabled",false);
				}
			}else if(items.length>2){
				$(obj).checked=false;
				$("input[name='_chk']").attr("disabled",false);
			}else{
				$("#chaifen").hide();
				$("#hebing").hide();
				$("input[name='_chk']").attr("disabled",false);
			}
		}else{
			$("#chaifen").hide();
			$("#hebing").hide();
			$("input[name='_chk']").attr("disabled",false);
		}
	}
	function toEditDevice(deviceId) {
		var url = "${ctx}/app/pro/toEditDevice.act?deviceId=" + deviceId;
		openEasyWin("editDevice", "设备-修改", url, "800", "440", true);
	}
	function toViewDevice(deviceId) {
		var url = "${ctx}/app/pro/toViewDevice.act?deviceId=" + deviceId;
		openEasyWin("viewDevice", "设备--查看", url, "800", "350", true);
	}
	function closeWin(winId) {
		closeEasyWin(winId);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
		
			var ids = new Array();
			var pageNo = $("#pageNo").val();
			$.each(($("input[name=_chk]:checked")), function(i, n){
				ids[i] = $(n).val();
			});
			var idStr = ids.join(',');
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
						$.ajax({
							type: "POST",
							url: "${ctx}/app/pro/inventoryDelete.act",
							data: {"idStr": idStr},
							dataType: 'text',
							async : true,
							success: function(json){
								easyAlert("提示信息", json,"info",function(){
									jumpPage(pageNo);									
								});
							},
							error: function(json){
								easyAlert("提示信息", json,"info");
							}
						});
					}
				});
			/*
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					//alert($("input[@type=checkbox]:checked").val());
					//$('#queryForm').attr("action","mergeHomeById.act"); 合并方法
					$("#deleteIds").val($("input[@type=checkbox]:checked").val()); //删除选中的列
					$('#queryForm').attr("action","jzjgDelete.act");  
					$('#queryForm').submit();
				}
			});	*/	
		}
    }
</script>
</html>
<s:actionmessage theme="custom" cssClass="success" />