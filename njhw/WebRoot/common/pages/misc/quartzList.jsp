<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2011-01-26 15:37:15
- Description: 任务
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>任务</title>	
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
			//setInterval("document.forms[0].submit();","120000");
		});
	</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #f1f8fd;">
	<form id="queryForm" action="list.act" method="post"  autocomplete="off">
	<s:hidden name="pageSize" id="98"></s:hidden>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
        <%-- <td class="form_label" width="20%" align="left">
           	任务分组：
          </td>
          <td width="30%">
        <s:select name="triggerGroup" list="#{'DEFAULT':'默认','group_0':'苏宁接口导入','group_1':'苏宁接口经销导出','group_2':'苏宁接口代销导出','group_3':'苏宁接口认证导出'}" theme="simple" ></s:select>
        </td>  --%> 
          <td class="form_label" width="20%" align="left">
           	任务名称：
          </td>
          <td width="80%" colspan="3">
           <s:textfield name="triggerName" theme="simple" size="25"/>  
          </td>
        </tr>
        <tr>
          <td colspan="4" class="form_bottom">
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="document.forms[0].submit();">查询</a>&nbsp;&nbsp;
          	 <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="javascript:document.forms[0].reset();">重置</a> &nbsp;&nbsp;
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add" title="结果列表">
		<d:table name="qList" id="map"  export="false" class="table">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${map_rowNum}
			</d:col>
			<%--<d:col property="trigger_group" class="display_centeralign" title="任务分组"/> --%>
		    <d:col property="display_name" class="display_centeralign" title="名称"/>
		    <d:col property="trigger_state" class="display_centeralign" title="状态"/>
		    <d:col property="prev_fire_time" class="display_centeralign" title="上次执行时"/>
		    <d:col property="next_fire_time" class="display_centeralign" title="下次执行时间"/>
		    <d:col property="start_time" class="display_centeralign" title="开始时间 "/>
		    <d:col property="end_time" class="display_centeralign" title="结束时间 "/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<a href="javascript:void(0);" onclick="doCmd('pause','${map.trigger_name}','${map.trigger_group}','${map.triggerState}')">[暂停]</a>
				<a href="javascript:void(0);" onclick="doCmd('resume','${map.trigger_name}','${map.trigger_group}','${map.triggerState}')">[恢复]</a>
				<a href="javascript:void(0);" onclick="doCmd('remove','${map.trigger_name}','${map.trigger_group}','${map.triggerState}')">[删除]</a>
			</d:col>	
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var	url = "${ctx}/common/quartz/input.act";
		openEasyWin("winId","添加任务",url,"780","450",true);
	}
	function doCmd(state,triggerName,group,triggerState){
		if(state == 'pause' && triggerState=='PAUSED'){
			easyAlert("提示信息", "该Trigger己经暂停！","info");
			return;
		}
	    if(state == 'resume' && triggerState != 'PAUSED'){
			easyAlert("提示信息", "该Trigger正在运行中！","info");
			return;
		}
		//客户端两次编码，服务端再解码，否测中文乱码 
		triggerName = encodeURIComponent(encodeURIComponent(triggerName));
		group = encodeURIComponent(encodeURIComponent(group));
         $.ajax({
         	type: "POST",
             url: '${ctx}/common/quartz/doCmd.act',
             data: 'jobtype=200&action='+state+'&triggerName='+triggerName+'&group='+group,
             dataType: 'json',
            // timeout: 3000,
             error: function(){
                easyAlert("提示信息", "执行出错","error");
             },
             success: function(msg){
		 if(msg['flag']=="0"){
			easyConfirm('提示', msg['smsg'], function(r){
				if (r){
					document.forms[0].submit();
				}
			});	
		 }else{
			 easyAlert("提示信息", msg['smsg'],"info");
		 }
             }
         });
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
