<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-17 15:37:15
- Description: 页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>访客列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0"  style="min-width: 1000px;">
	<form id="queryForm" action="queryVisitors.act" method="post"  autocomplete="off">
	<s:hidden name="viId"/>
	<s:hidden name="vsId"/>
    	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label">卡号：</td>
	        <td><s:textfield name="cardId" theme="simple" size="18"/></td> 
	        <td class="form_label">访客姓名：</td>
	        <td><s:textfield name="viName" theme="simple" size="18"/></td> 
	        <td class="form_label" >访问时间： </td>
			<td cssStyle="width:150px;">
			<table class="form_table" >
	        <tr class="form_table">
	        <td>
            	<s:textfield onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="beginTime" name="beginTime" cssClass="Wdate" ></s:textfield>
	        </td>
			<td align="center">-</td>
			<td>	
				<s:textfield onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="endTime" name="endTime" cssClass="Wdate"></s:textfield>
			</td>	
			</tr>
			</table>
			</td>
			</td>
        </tr> 
        <td class="form_label">预约类型：</td>
	        <td>
			 <s:select list="#application.DICT_VS_TYPE" headerKey="0" headerValue="全部" listKey="dictcode" listValue="dictname" name="vsType" id="vsType"/>
	        </td> 
	        <td class="form_label">访客状态：</td>
	        <td>
			 <s:select list="#application.DICT_VISIT_STATUS" headerKey="0" headerValue="全部" listKey="dictcode" listValue="dictname" name="vsFlag" id="vsFlag"/>
	        </td> 
	        <td class="form_label">是否离开：</td>
	        <td>
				 <select name="isLeave" id="isLeave">
					<option value="0" selected>全部</option>
					<option value="1">是</option>
					<option value="2">否</option>
				 </select>
	        </td>
        <tr>
          
        </tr>     
        <tr>
          <td colspan="11" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:100px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<%--
			<d:col style="width:45" class="display_centeralign" title="选择" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.userId}"/>
			</d:col>	
			 --%>
			<td class="form_label"><font style="color:red">*</font>：</td>
	        <td><s:textfield name="USER_NAME" theme="simple" size="18" maxlength="20"/></td>  
		    <d:col property="CARD_ID" class="display_centeralign"   title="市民卡号"/>
		    <d:col property="CARD_TYPE" dictTypeId="CARD_TYPE" class="display_centeralign" title="卡类型"/>
		    <d:col property="VI_NAME" class="display_centeralign" title="访客姓名"/>
		    <d:col property="USER_NAME" class="display_centeralign" title="受访者姓名"/>
		    <d:col property="VS_NUM" class="display_centeralign" title="访问人数"/>
		    <d:col property="VS_TYPE" dictTypeId="VS_TYPE" class="display_centeralign" title="预约类型"/>
		    <d:col property="VS_RETURN" dictTypeId="VS_RETURN" class="display_centeralign" title="是否归还临时卡"/>
		    <d:col property="VS_FLAG" dictTypeId="VISIT_STATUS" class="display_centeralign" title="访客状态"/>
		    <d:col property="VS_ST" class="display_centeralign"  format="{0,date,yyyy年MM月dd日 hh时mm分}"  title="访问开始时间"/>
			<d:col class="display_centeralign"  media="html" title="访问事务">				
				<a href="javascript:void(0);" onclick="lookTransaction('${row.VS_ID}')">[查看]</a>&nbsp;	
			</d:col>
			<d:col class="display_centeralign"  media="html" title="访客资料">				
				<a href="javascript:void(0);" onclick="lookRecord('${row.VS_ID}')">[查看]</a>&nbsp;	
			</d:col>				
		    <d:col class="display_centeralign"  media="html" title="访问轨迹">				
				<a href="javascript:void(0);" onclick="showVideo('${row.VS_ID}')">[查看]</a>&nbsp;	
			</d:col>		
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
    function lookTransaction(vsId){
		var url = "${ctx}/app/caller/visitorsTransaction.act?vsId="+vsId;
		openEasyWin("winId","访问事务信息",url,"700","500",false);
    }
	function lookRecord(vsId){
		var url = "${ctx}/app/caller/lookVisitors.act?vsId="+vsId;
		openEasyWin("winId","访客信息",url,"700","500",true);
	}
	function showVideo(vsId){
		var url = "${ctx}/app/caller/showVideo.act?vsId="+vsId;
		openEasyWin("winId","访客访问线路",url,"900","600",true);
	}
	
	$(document).ready(function(){
			//日期比较
			 jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            if(beginTime=="" || value==""){
	            	return true;
	            }else{
	            	return beginTime < value;
	            }
	        };
			
			$("#queryForm").validate({
				meta :"validate",
				errorElement :"div",
				rules: {
					endTime:{
						compareDate: "#beginTime"
					}
				},
				messages:{ 
	                endTime:{
	                    compareDate: "结束日期必须大于开始日期!"
	               	}
				}
			});
		});
</script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>