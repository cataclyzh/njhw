<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-4-7
- Description: 房间分配
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>房间分配</title>	
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<script type="text/javascript">var _ctx = '${ctx}';</script>
	<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>

	<link href="${ctx}/app/integrateservice/css/fex_center.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(document).ready(function(){
			loadFloor();
			
			$("#isAllot").val("${map.isAllot}");
		});
		
		// 根据楼座ID，加载楼层信息
		function loadFloor() {
			var grandId = $("#grandId").val();
			if (grandId > 0) {
		    	$.getJSON("${ctx}/app/room/loadFloorByBid.act",{grandId: grandId}, function(json){
		    		if (json.status == 0) {
		    			$("#parentId").empty();
		    			$("#parentId").attr("disabled", false);
		    			$("#parentId").append("<option value=0>全部</option>");
						$.each(json.list,function(i){
							if (json.list[i][0] == "${map.parentId}")
								$("#parentId").append("<option value="+json.list[i][0]+" selected='selected'>"+json.list[i][1]+"</option>")
							else 
								$("#parentId").append("<option value="+json.list[i][0]+">"+json.list[i][1]+"</option>") 
						})
		    		}
				});
			}
		}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #fff;">
	<form id="queryForm" action="queryAllRooms.act" method="post"  autocomplete="off">
	<input type="hidden" name="ids" id="ids" value="${map.ids }"/>
	<s:hidden name="page.pageSize" id="pageSize" />
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
      	  <td class="form_label" width="20%" align="left"><div class="infrom_td">楼层：</div></td>
          <td width="30%"><input type="text" id="names" name="names" onclick="showTree()" value="${map.names}" readonly="readonly"/></td>
		  <td class="form_label" width="20%" align="left"><div class="infrom_td">状态：</div></td>
          <td width="30%">
          <s:select list="#request.isAllot" headerKey="3" headerValue="全部" id="isAllot" listKey="key" listValue="value" name="isAllot" cssStyle="width:150px;"/>
          </td>
        </tr>
        <tr>
          <td class="form_label" colspan="3" style="color:#808080;font-size:13px;">
          	<div class="infrom_td" style="text-align:left;padding-left:95px;">房间总数：${map.count}&nbsp;&nbsp;&nbsp;已分配房间总数：${map.countAllot}&nbsp;&nbsp;&nbsp;未分配房间总数：${map.countNotAllot}</div>
          </td>
          <td class="form_bottom" style="color:#808080;font-size:13px;">
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="resetFormForAllot();">重置</a>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="querySubmit();">查询</a>
          </td>
        </tr>
      </table>      
   </h:panel>
		<h:page id="list_panel" width="100%" title="结果列表">
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col property="SHORT_NAME"  class="display_centeralign" title="单位名称"/>
		    <d:col property="NAME"  class="display_centeralign" title="门牌号"/>
		    <d:col property="COUNT_PERSON"  class="display_centeralign" title="现有人数"/>
		    <d:col property="FLOOR" class="display_centeralign" title="楼层"/>
		</d:table>
  	 </h:page>
	</form>
</body>
<script type="text/javascript">
	function resetFormForAllot() {
		$('#names').val("");
		$('#isAllot').val("3");
		$('#ids').val("");
	}
	
	function showTree() {
		var url = "${ctx}/app/room/showTree.act?ids="+$("#ids").val();
        var params = "";
	    url += params;
		openEasyWin("lz", "楼座选择", url, 400, 400);
	}
</script>
</html>