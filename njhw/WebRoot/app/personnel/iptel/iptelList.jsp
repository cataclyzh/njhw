<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>IP电话列表</title>	
	<%@ include file="/common/include/metaIframe.jsp" %>
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
		});

		function reset(){
			var cbox=$("input:checked[name='_chk']");        
			$.each(cbox, function(i,item){
			   	if($(this).parent().parent().attr("class") == "oddselected"){
			   		$(item).parent().parent().attr("class","odd");
			   	}
				if($(this).parent().parent().attr("class") == "evenselected"){
			 		$(item).parent().parent().attr("class","even");
				}					    
		    });	
		}
			
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background:#fff;">
	<div class="bgsgl_border_left">
		<!--此处写页面的标题 -->
		<div class="bgsgl_border_right">
			电话传真管理
		</div>
	</div>
	<div class="bgsgl_conter" style="min-height: 700px">
	<form id="queryForm" action="listTcIpTels.act" method="post"  autocomplete="off">
	 <s:hidden name="telId"/>
	 <s:hidden name="page.pageSize" id="pageSize" />
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
         <td class="form_label" width="10%" align="left">
           	<div class="infrom_td">资源类型：</div>
          </td>
          <td width="23%">
          	<s:select list="#application.DICT_TEL_TYPE" cssStyle="width:150px;" headerKey="" headerValue="全部" listKey="dictcode" listValue="dictname" name="telType" id="telType"/>
		 </td>
         <td class="form_label" width="10%" align="left">
           	<div class="infrom_td">电话号码：</div>
          </td>
          <td width="23%">
          <s:textfield name="telNum" theme="simple" size="18"/>
         </td>
          <td class="form_label" width="10%" align="left">
       	  	<div class="infrom_td">MAC地址：</div>
          </td>
          <td width="24%">
            <s:textfield name="telMac" theme="simple" size="18"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="6" class="form_bottom" style="color:#808080;font-size:13px;">
          	<font style="float:left;margin-left:20px;">总资源数：${count}&nbsp;&nbsp;&nbsp;IP电话总数：${countIpTel}&nbsp;&nbsp;&nbsp;传真总数：${countFax}&nbsp;&nbsp;&nbsp;网络传真总数：${countWebFax} </font>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="resetFormForAllot();">重置</a>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="querySubmit();">查询</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" buttons="add,del" width="100%" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col   style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.telId}"/>
			</d:col>	
		    <d:col property="telNum" class="display_centeralign"   title="电话号码"/>
		    <d:col property="telMac" class="display_leftalign" title="mac地址"/>
		    <d:col property="telType" dictTypeId="TEL_TYPE" class="display_leftalign" title="资源类型"/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<a href="javascript:void(0);" onclick="updateRecord('${row.telId}')">[修改]</a>&nbsp;	
			</d:col>				
		</d:table>
   </h:page>
	</form>
	</div>
<script type="text/javascript">

	function addRecord(){
		var url = "${ctx}/app/per/ipTelInput.act?telId="+$('#telId').val();
		openEasyWin("winId","录入IP电话信息",url,"500","250",true);
	}
	function updateRecord(id){
		var url = "${ctx}/app/per/ipTelInput.act?telId="+id;
		openEasyWin("winId","修改IP电话信息",url,"500","250",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteIpTel.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
    
    function resetFormForAllot() {
		$('#telType').val("");
		$('#telNum').val("");
		$('#telMac').val("");
	}
</script>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
</script>
<s:actionmessage theme="custom" cssClass="success"/>
