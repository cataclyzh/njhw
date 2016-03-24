<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="p" %>
<%--
- Author: zh
- Date: 2013-3-23
- Description: 通讯录成员
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>通讯录成员</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
	<script src="${ctx}/app/integrateservice/js/personalSet.js" type="text/javascript"></script>
	<link href="${ctx}/app/integrateservice/css/personalSet.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/table.css" type="text/css" rel="stylesheet"  />
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
			
			window.parent.setSpanText($("#gid").val(), ${personLength});
			window.parent.loadMenu();
		});
		
		function goPage(pageNo){
			if (pageNo==null || pageNo == undefined) pageNo = 1;
			$("#pageNo").val(pageNo);
			$("#queryForm").submit();
		}
		
		function query() {
			$("#pageNo").val("1");
			$("#queryForm").submit();
		}
		
		function showInfo(id){
			var url = "contactDetail.act?nuaId="+id;
			window.top.showIframe("查看人员", 600, 315, url);
			/* var url = "${ctx}/app/paddressbook/addressShow.act?nuaId="+id;
			showShelter1("600","330",url,"show"); */
		}
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #FFF;">
	<form id="queryForm" action="addressList.act" method="post"  autocomplete="off">
		<input type="hidden" name="gid" id="gid" value="${map.gid}"/>
		<input type="hidden" name="nuaid" id="nuaid"/>
		<input type="hidden" name="pageNo" id="pageNo"/>
		
		<!--table align="center"  id="hide_table" border="0" width="100%" class="form_table">
			<tr>
			  <td class="form_label" width="10%" align="left">当前分组：</td>
	          <td width="30%">${gname }</td>
			　<td class="form_label" width="10%" align="left">姓名或号码：</td>
	          <td width="30%"><input type="text" value="${map.searchVal }" id="searchVal" name="searchVal"/></td>
	          <td>
	          	<a href="#" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="query();">查询</a>
	          </td>
	        </tr>
		</table-->
		<table class="main_table" border="1"  id="main_table" style="border-collapse:collapse;" >
			<tr class="table_header">
			    <td ><input type="checkbox" id="checkAll"/></td>
			    <td align="left">选择</td>
			    <td ><span>姓名</span></td>
			    <td ><span>昵称</span></td>
			    <td ><span>手机</span></td>
			    <td ><span>住宅</span></td>
			    <td ><span>办公</span></td>
			    <td ><span>其他</span></td>
			    <td ><span>邮箱</span></td>
			</tr>
            <c:forEach var="person" items="${page.result}">
            	<tr class="main_table_tr" onmouseover="list_hover(this, 0)" onmouseout="list_hover(this, 1)" onclick="setNUAID(this, '${person.NUA_ID}')">
					<td width="25">
						<input type="checkbox" name="userA_chk" id="userA_chk" class="checkItem" value="${person.NUA_ID}"/>
					</td>
					<td width="20"><div class="man"></div></td>
					<td width="30" align="left" ondblclick="showInfo('${person.NUA_ID}')"><span>${person.NUA_NAME }</span></td>
					<td width="30" align="left" ondblclick="showInfo('${person.NUA_ID}')"><span>${person.NUA_NAME1 }</span></td>
					<td width="50" align="left" ondblclick="showInfo('${person.NUA_ID}')"><span>${fn:substring(person.NUA_PHONE, 0, 11)}<c:if test="${fn:indexOf(person.NUA_PHONE, ',') > 0}">...</c:if></span></td>
					<td width="50" align="left" ondblclick="showInfo('${person.NUA_ID}')"><span>${fn:substring(person.NUA_TEL1, 0, 11)}<c:if test="${fn:indexOf(person.NUA_TEL1, ',') > 0}">...</c:if></span></td>
					<td width="50" align="left" ondblclick="showInfo('${person.NUA_ID}')"><span>${fn:substring(person.NUA_TEL2, 0, 11)}<c:if test="${fn:indexOf(person.NUA_TEL2, ',') > 0}">...</c:if></span></td>
					<td width="50" align="left" ondblclick="showInfo('${person.NUA_ID}')"><span>${fn:substring(person.NUA_TEL3, 0, 11)}<c:if test="${fn:indexOf(person.NUA_TEL3, ',') > 0}">...</c:if></span></td>
					<td width="50" align="left" ondblclick="showInfo('${person.NUA_ID}')"><span>${person.NUA_MAIL }</span></td>
				</tr>
			</c:forEach>
		</table>
		<div class="paging">
			<p:pager recordCount="${personLength}" pageSize="10" pageNo="${page.pageNo}" recordShowNum="4" url="${ctx}/app/personnel/telAndNumber/telAndNumberList.act"/>
		</div>
	</form>
<script type="text/javascript">
	function goPage(a){
		var url = "${ctx}/app/paddressbook/addressList.act?page="+(a);
		window.location.href = url;
	}
	function setNUAID(dom, id){ 
		var domAll = document.getElementById("main_table").getElementsByTagName("tr");
	    	var len = domAll.length;
	    	for(var i = 0; i < len; i++)
	    	{
	    		if(domAll[i].className == "main_table_tr")
	    			domAll[i].style.background = "";
	    	}
	    	if(dom.style)
	    		dom.style.background = "#ffe2a2";
		$("#nuaid").val(id);
	}
	
	function addRecord(){
		var url = "${ctx}/app/paddressbook/addressInput.act?gid="+$("#gid").val();
		showShelter1("700","540",url,"winId");
	}
	
	function updateRecord(){
		var nua_id = $("#nuaid").val();
		if (nua_id == "") {
			window.top.showMessageBox("请选中要编辑的联系人！");
			//easyAlert('提示信息','请选中要编辑的联系人！','info');
		} else {
			var url = "contactEdit.act?nuaId="+nua_id+"&gid="+$("#gid").val()+"&isPopup=1&titleNum=edit";
			window.top.showIframe("编辑联系人", 700, 500, url, function() {
				window.location.reload();
			});
			/* var url = "${ctx}/app/paddressbook/addressInput.act?nuaId="+nua_id+"&gid="+$("#gid").val()+"&isPopup=1&titleNum=edit";
			showShelter1("700","540",url,"winId"); */
		}
	}
	
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			window.top.showMessageBox("请勾选要删除的记录！");
			//easyAlert('提示信息','请勾选要删除的记录！','info');
		} else {
			window.top.showConfirmBox("请确定删除？",function(){
				$('#queryForm').attr("action","contactDelete.act");
				$('#queryForm').submit();						
			});
			/* easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","contactDelete.act");
					$('#queryForm').submit();
				}
			}); */
		}
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
