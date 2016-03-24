<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/pages/main/layout.js"></script>

<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
<title>查看失物招领</title>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/property/block.js" type="text/javascript"></script>
<script src="${ctx}/scripts/property/calendarCompare.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/common/main/layoutnew.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<script src="${ctx}/app/property/lostFound/js/showLostFoundList.js" type="text/javascript"></script>
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
	function showLostFoundsList(){
		$('#queryForm').submit();
	}
	
	function viewLostFound(lostFoundId){
		var url = "${ctx}/app/pro/queryLostFoundById.act?lostFoundId="+lostFoundId;
		openEasyWin("viewLostFound", "查看失物详细信息", url, "900", "650", false);
	}
	
	function addOneLostFoundPrepare(){
		var url = "${ctx}/app/pro/addOneLostFoundPrepare.act";
		openEasyWin("addOneLostFoundPrepare", "失物登记", url, "900", "600", false);
	}
	
	function modifyOneLostFoundPrepare(lostFoundId){
		var url = "${ctx}/app/pro/modifyOneLostFoundPrepare.act?lostFoundId="+lostFoundId;
		openEasyWin("modifyOneLostFoundPrepare", "失物信息修改", url, "900", "450", false);
	}
	
	function claimOneLostFoundPrepare(lostFoundId){
		var url = "${ctx}/app/pro/claimOneLostFoundPrepare.act?lostFoundId="+lostFoundId;
		openEasyWin("claimOneLostFoundPrepare", "失物认领", url, "800", "540", false);
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
<body>
<div class="main_index">
		<c:if test="${param.type eq 'A'}">
<jsp:include page="/app/integrateservice/headerWy.jsp">
	<jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
</jsp:include>
</c:if>
<c:if test="${param.type eq 'B'}">
<jsp:include page="/app/integrateservice/headerWy.jsp">
	<jsp:param value="/app/pro/propertyIndex7.act" name="gotoParentPath"/>
</jsp:include>
</c:if>
<div class="index_bgs" style="padding: 0px 10px 10px 10px;">

<div class="bgsgl_border_left">
<!--此处写页面的标题 -->
<div class="bgsgl_border_right">查看失物招领</div>
</div>
<div class="bgsgl_conter" style="min-height: 300px">
<!--此处写页面的内容 -->
<div class="fkdj_from">
<form id="queryForm" name="queryForm" action="showLostFoundsList1.act" method="post">
	<h:panel id="query_panel" width="100%" title="查询条件">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hide_table">
			<tr>
				<td class="fkdj_name" height="40px">标题</td>
				<td style="width: 168px; text-align: left;"><s:textfield name="list_lostFoundTitle" id="list_lostFoundTitle" cssClass="fkdj_from_input" /></td>
				<td class="fkdj_name" height="40px">失物名称</td>
				<td><s:textfield name="list_lostFoundThingName" id="list_lostFoundThingName" cssClass="fkdj_from_input" /></td>
			</tr>
			<tr>
				<td class="fkdj_name" height="40px">登记时间</td>
				<td>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><s:textfield name="list_lostFoundIntime" id="list_lostFoundIntime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
							<td class="fkdj_name_lkf" style="padding: 4px 6px;">至</td>
							<td><s:textfield name="list_lostFoundOvertime" id="list_lostFoundOvertime" cssClass="fkdj_from_input" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
						</tr>
					</table>
				</td>
				<td class="fkdj_name" height="40px">是否已经认领</td>
				<td>
					<select class="fkdj_select" id="list_lostFoundState" name="list_lostFoundState">
						<option id="list_allState" value="all">全部</option>
						<option id="list_hasClaimed" value="1"
						<c:if test="${list_lostFoundState=='1'}">selected="selected"</c:if>>已认领</option>
							<option id="list_noClaimed" value="0"
						<c:if test="${list_lostFoundState=='0'}">selected="selected"</c:if>>未认领</option>
					</select>
				</td>
				<td><a class="property_query_button" onclick="javascript:saveData()">查询 </a></td>
			</tr>
		</table>
	</h:panel>
	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="display:none" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />
	<page:pager id="list_panel" width="100%" buttons="${buttons}" title="结果列表">
		<d:table name="page.result" id="row" cellspacing="0" cellpadding="0" class="table">
			<d:col class="display_centeralign" title="失物标题">
				<a style="cursor: pointer; color: black;" onclick="viewLostFound('${row.LOST_FOUND_ID}')">${row.LOST_FOUND_TITLE} </a>
			</d:col>
			<d:col property="LOST_FOUND_THINGNAME" class="display_centeralign" title="失物名称" />
			<d:col property="LOST_FOUND_LOCATION" class="display_centeralign" title="捡到失物地点" />
			<d:col property="LOST_FOUND_PICKUSER" class="display_centeralign" title="失物上交者" />
			<d:col property="LOST_FOUND_KEEPER" class="display_centeralign" title="失物登记者" />
			<d:col property="LOST_FOUND_INTIME" class="display_centeralign" title="登记时间" />
			<d:col class="display_centeralign" title="认领状态">
				<c:if test="${row.LOST_FOUND_STATE == 0}">
					<span class="display_centeralign">未认领</span>
				</c:if>
				<c:if test="${row.LOST_FOUND_STATE == 1}">
					<span class="display_centeralign">已认领</span>
				</c:if>
			</d:col>
			<d:col class="display_centeralign" title="操作">
				<span class="display_centeralign" onclick="javascript:viewLostFound('${row.LOST_FOUND_ID}')" onmousemove="this.style.color='#ffae00'" onmouseout="this.style.color='#8090b4'" style="cursor: pointer">&nbsp;[详细]&nbsp;</span>
			</d:col>
		</d:table>
	</page:pager>
</form>
</div>
</div>
</div>
<jsp:include page="/app/integrateservice/footer.jsp" />
</div>
</body>
</html>
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
							url: "${ctx}/app/pro/deleteOneLostFound.act",
							data: {"idStr": idStr},
							dataType: 'text',
							async : true,
							success: function(json){
								easyAlert("提示信息", json,"info",function(){
									jumpPage(pageNo);
								});
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