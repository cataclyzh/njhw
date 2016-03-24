<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-3-18 10:12:11
- Description: 已发短信列表
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>已发短信列表</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<%@ include file="/common/include/meta.jsp" %>
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
		});
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="queryForm" action="list.act"  method="post"  autocomplete="off" >
	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
		<tr>
			<td class="form_label">接收人：</td>
			<td><s:textfield name="receiver"></s:textfield></td>
			<td class="form_label" > <font style="color: red"></font>手机号： </td>
			<td><s:textfield name="receivermobile"></s:textfield></td>
		</tr>
		<tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
	  </table>
	</h:panel>

	<h:page id="list_panel" width="100%" title="结果列表" buttons="add,del">
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:50px;" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col style="width:65px;"  class="display_centeralign" title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.smsid}"/>
			</d:col>
		    <d:col property="receiver" style="width:13%;"   class="display_leftalign" 	title="接收人"/>
		    <d:col property="receivermobile" style="width:13%;" class="display_leftalign" 	title="手机号"/>
		    <d:col property="content"  maxLength="50" class="display_leftalign" 	title="内容"/>
		    <d:col property="smstime" style="width:13%;"   class="display_leftalign" format="{0,date,yyyy-MM-dd HH:mm}" title="发送时间"/>
			<d:col class="display_centeralign" style="width: 60px;" media="html" title="操作">
				<a href="javascript:void(0);" onclick="show('${row.smsid}')">查看</a>&nbsp;
			</d:col>
		</d:table>
   	</h:page>
   </form>
   
<script type="text/javascript">
   	function show(smsid){
		var url = "${ctx}/app/sendsms/showSMS.act?smsid="+smsid;
		openEasyWin("winId","查看短信",url,"650","400",false);
	}
	
	function addRecord(){
		var url = "${ctx}/app/sendsms/inputSMS.act";
		openEasyWin("winId","发送短信",url,"650","400",true);
	}
	
	// 删除
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteSMS.act");
					$('#queryForm').submit();
				}
			});
		}
    }
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success" />