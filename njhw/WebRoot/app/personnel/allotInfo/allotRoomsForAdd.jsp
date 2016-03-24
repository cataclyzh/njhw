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
			var chk_options = {
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
			loadFloor();
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
	<form id="queryForm" action="queryAllotRoomsForAdd.act" method="post"  autocomplete="off">
	<input type="hidden" name="orgId" id="orgId" value="${map.orgId }"/>
	<input type="hidden" name="ids" id="ids" value="${map.ids }"/>
	<s:hidden name="page.pageSize" id="pageSize" />
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
      	  <td class="form_label" width="13%" align="left"><div class="infrom_td">楼层：</div></td>
          <td width="20%"><input type="text" id="names" name="names" onclick="showTree()" value="${map.names }" readonly="readonly"/></td>
		  <td class="form_label" width="33%" align="left"><div class="infrom_td">未分配房间总数：${map.count}</div></td>
          <td class="form_bottom"  style="color:#808080;font-size:13px;">
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="resetFormForAllot();">重置</a>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="querySubmit();">查询</a>
          </td>
        </tr>
      </table>      
   </h:panel>
		<h:page id="list_panel" width="100%" title="结果列表">
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:50px;"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.NODE_ID}_${row.NAME}"/>
			</d:col>
		    <d:col property="NAME"  class="display_centeralign" title="门牌号"/>
		    <d:col property="FLOOR" class="display_centeralign" title="楼层"/>
		</d:table>
  	 </h:page>
  	 <a id="saveBtn" class="buttonFinish" style="float:right;" href="javascript:void(0);" onclick="javascript:addRoom()">添加房间</a>
	</form>
</body>
<script type="text/javascript">
	function resetFormForAllot() {
		$('#names').val("");
		$('#ids').val("");
	}

	function addRoom(){
		var chkNodeId = "";
		$(":checkbox").each(function() {
			if ($(this).attr("checked") == "checked")
			chkNodeId += ($(this).val() + ",");
		});
		
		$.ajax({
			type: "POST",
			url: "${ctx}/app/room/allotRoomSave.act",
			data: {
				"chkNodeId": chkNodeId, 
				"orgId": parent.njhwAdmin.ifrmOrgList.$("#orgId").val()},
			dataType: 'text',
			async : false,
			success: function(msg){
			if(msg == 'success') {
				easyAlert("提示信息", "添加房间完成！","info", function() {
					parent.njhwAdmin.ifrmOrgList.$('#queryForm').submit();
					parent.$("#tjfj").window("close");
				});
			  } else if (msg == 'error') {
				easyAlert("提示信息", "添加房间出错！","info",function() {
					parent.$("#tjfj").window("close");
				});
			  }
			 },
			 error: function(msg, status, e){
				 easyAlert("提示信息", "添加房间出错！","info",function() {
				 	parent.$("#tjfj").window("close");
				 });
			 }
		 });
	}
	
	function showTree() {
		var url = "${ctx}/app/room/showTree.act?type=add&ids="+$("#ids").val();
        var params = "";
	    url += params;
		openEasyWin("lz", "楼座选择", url, 400, 400);
	}
</script>
</html>