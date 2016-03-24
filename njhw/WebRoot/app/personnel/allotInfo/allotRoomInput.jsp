<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-03-24
- Description: 房间分配
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>房间分配</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
	<script type="text/javascript">	
		$(document).ready(function() {
			jQuery.validator.methods.compareValue = function(value, element) {
	           if(value == "0" || value == ""){
	        	   return false;
	           }
	        	return true;
	        };
	        $("#orgId").focus();
			$("#inputForm").validate({
				meta :"validate",
				errorElement :"div",
				rules: {
					orgId: {
						required: true,
						compareValue:true
					},
					bid:{
						required: true,
						compareValue:true
					},
					fid:{
						required: true,
						compareValue:true
					},
					roomids: {
						compareValue:true
					}
				},
				messages: {
					orgId:{
						compareValue:"请选择委办局"
					},
					bid:{
						compareValue:"请选择楼座"
					},
					fid:{
						compareValue:"请选择楼层"
					},
					roomids:{
						compareValue:"请选择房间"
					}
				}
			});
		});
		
		function saveData(){
			var ids = "";
			$("input[id^='chk_']:checked").each(function(i) {
				ids += ($(this).val() + ",");
			});
			$("#roomids").val(ids);
			$("#orgName").val($("#orgId option:selected").text());
			// 提交前验证
			$.ajax({
				type: "POST",
				url: "${ctx}/app/room/validAllotInfo.act",
				data: {"orgId": $("#orgId").val(), "ids": ids},
				dataType: 'json',
				async : false,
				success: function(json){
					  if(json.status == 0){
						$('#inputForm').submit();
					  } else if(json.status == 1){
						easyConfirm("提示信息", 
							"房间："+json.names+"已分配给其他委办局！<br/>"+
							"如果您点击确认按钮，本次操作将会跳过已分配的房间，退出分配请点击取消",
							function(r){
							if (r) {
								$("#skipRoomIds").val(json.roomids);
								$('#inputForm').submit();
							}
						});
					  }
				 },
				 error: function(msg, status, e){
					 easyAlert("提示信息", "验证分配出错！","info");
				 }
			 });
		}
		
		// 根据楼座ID，加载楼层信息
		function loadFloor() {
			var bid = $("#bid").val();
			if (bid > 0) {
		    	$.getJSON("${ctx}/app/room/loadFloorByBid.act",{bid: bid}, function(json){
		    		if (json.status == 0) {
		    			$("#fid").empty();
		    			$("#fid").append("<option value=0>请选择...</option>");
						$.each(json.list,function(i){
							$("#fid").append("<option value="+json.list[i][0]+">"+json.list[i][1]+"</option>") 
						})
		    		}
				});
			}
		}
		
		// 显示指定页的数据
		function showRooms(idx) {
			$("tr[id^=roomTr_]").hide();		// 隐藏
			$("tr[id^=roomTr_"+idx+"]").show();	// 显示指定页
			$("#showPageNum").text("第"+idx+"页");
		}
		
		// 检查是否全选
		function ucAll() {
			var isNotChecked = 0;
			$("input[id^='chk_']").each(function(i) {
				if ($(this).attr("checked") != "checked") isNotChecked = 1;
			});
			if (isNotChecked == 1) $("#check_all").removeAttr("checked");
			else $("#check_all").attr("checked", "checked");
		}
		
		// 根据楼层ID，加载房间 
		function loadRoom() {
			var fid = $("#fid").val();
			if (fid > 0) {
		    	$.getJSON("${ctx}/app/room/loadRoomByFid.act",{fid: fid}, function(json){
	    			$("#roomTR").show();
	    			$("#showInfo").hide();
	    			$("#roomTab").empty();
	    			
	    			$("#optTab").show();
	    			$("#optTab").empty();
		    		if (json.status == 0) {
		    			var len = json.list.length / PAGE_ROOM_NUM;	// 页数
		    			var lenY = json.list.length % PAGE_ROOM_NUM;
		    			if (lenY > 0)	len += 1;
		    			var trNum = 0, pageNum = 0;			// 行数ID, 记录页数ID
		    			$("#optTab").append("<tr id='opt_btn'></tr>");
	    				$.each(json.list,function(i){
	    					if ((i+1) % PAGE_ROOM_NUM == 1) {			// 每页显示的房间数量
	    						pageNum += 1;
	    						$("#opt_btn").append("<td style='padding: 5px; border: 0px;'><input type='button' id='showBut_"+pageNum+"' onclick='showRooms("+pageNum+")' value='第"+pageNum+"页'/></td>");
	    					}
	    					if ((i+1) % ROW_ROOM_NUM == 1)  {			// 每行显示的房间数量
	    						trNum += 1;
	    						$("#roomTab").append("<tr id='roomTr_"+pageNum+"_"+trNum+"'></tr>");
	    					}
							$("#roomTr_"+pageNum+"_"+trNum).append("<td style='padding: 5px;'><input type='checkbox' onclick='ucAll()' id='chk_"+json.list[i].NODE_ID+"' value='"+json.list[i].NODE_ID+"_"+json.list[i].NAME+"' />"+json.list[i].NAME+"</td>");
						});
						$("#opt_btn").append("<td style='padding: 5px; border: 0px;' id='showPageNum'>第1页</td>");
						
						if (len >= 2) {						// 当使用到分页时
							for (var idx = 2; idx <= len; idx++) {
								$("tr[id^=roomTr_"+idx+"]").hide();	// 除了第一页，属于其他页的数据隐藏
							}
						} else {
							$("#optTab").hide();
						}
		    		} else {
		    			$("#roomTR").hide();
	    				$("#showInfo").show();
	    			}
				});
			}
		}
		
		// 选中全部
		function chk_all() {
			if ($("#check_all").attr("checked") == "checked")
				$("input[id^='chk_']").attr("checked", "true");
			else  
				$("input[id^='chk_']").removeAttr("checked");
		}
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="allotSave.act"  method="post"  autocomplete="off" >
    <table align="center" border="0" width="100%" class="form_table">
    <input type="hidden" name="orgName" id="orgName"/>
    <input type="hidden" name="skipRoomIds" id="skipRoomIds"/>
	<tr>
        <td class="form_label"><font style="color:red">*</font>委办局：</td>
        <td>
        	<select id="orgId" name="orgId">
	        	<option value="0">请选择...</option>
		        <c:forEach items="${orgList}" var="org">
					<c:if test="${org.orgId == orgId }"><option value="${org.orgId }" selected="selected">${org.name }</option></c:if>
					<c:if test="${org.orgId != orgId }"><option value="${org.orgId }">${org.name }</option></c:if>
				</c:forEach>
			</select>
        </td>
	</tr>
	<tr>
		<td class="form_label"> <font style="color: red">*</font>楼座： </td>
		<td><select id="bid" name="bid" onchange="loadFloor()">
			<option value="0">请选择...</option>
			<c:forEach items="${balconyList}" var="balcony">
				<option value="${balcony.nodeId }">${balcony.name}</option>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td class="form_label"> <font style="color: red">*</font>楼层： </td>
		<td>
			<select id="fid" name="fid" onchange="loadRoom()">
				<option value="0">请选择...</option>
			</select>
			<div id="showInfo" style="display: none;"><lable>此楼层无待分配房间</lable></div>
			<input type="hidden" id="roomids" name="roomids" />
		</td>
	</tr>
    <tr id="roomTR" style="display: none;">
        <td class="form_label">
        	<font style="color:red">*</font>待分配房间：<br />
        	(选中本层所有房间)<input type="checkbox" name="check_all" id="check_all" onclick="chk_all()"/>
        </td>
        <td>
        	<table id="optTab"></table>
        	<table id="roomTab"></table>
        </td> 
     </tr> 
     <!--<tr>
     	<td class="form_label">启用时间：</td>
     	<td>
			<s:date id="format_startTime" name="porSt" format="yyyy-MM-dd" />
			<jsp:include page="/common/include/chooseDateTime.jsp">
				<jsp:param name="date" value="porSt" />
				<jsp:param name="format_date" value="${emOrgRes.porSt}" />
			</jsp:include>
		</td> 
     </tr> 
     <tr>
     	<td class="form_label">停止时间：</td>
     	<td>
			<s:date id="format_endTime" name="porEt" format="yyyy-MM-dd" />
			<jsp:include page="/common/include/chooseDateTime.jsp">
				<jsp:param name="date" value="porEt" />
				<jsp:param name="format_date" value="${emOrgRes.porEt}"/>
			</jsp:include>
		</td>
      </tr>
	--></table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="2">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info', function(){closeEasyWin('winId');});
</c:if>
<c:if test="${isSuc=='alreadyAllot'}">
	easyAlert('提示信息','房间：${names}已经分配给其他委办局，请确认！','info');
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
</script>
