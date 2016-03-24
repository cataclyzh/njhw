<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 临时卡管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>临时卡列表</title>	
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
			$("#queryForm").resetForm();
		}
			
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="queryTcTempcard.act" method="post"  autocomplete="off">
	<s:hidden name="CARD_ID"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	临时卡号：
          </td>
          <td width="30%">
           <s:textfield name="cardId" theme="simple" size="18"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	RFID：       	  	
          </td>
          <td width="30%">
            <s:textfield name="rfid" theme="simple" size="18"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="reset();">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col   style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.CARD_ID}"/>
			</d:col>	
		    <d:col property="CARD_ID"  class="display_centeralign" title="临时卡号"/>
		    <d:col property="RFID" class="display_leftalign" title="RFID"/>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<a href="javascript:void(0);" onclick="updateTemporaryCard('${row.CARD_ID}')">[修改]</a>&nbsp;	
			</d:col>				
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">

	function addRecord(){
		var url = "${ctx}/app/per/temporaryCardAdd.act?cardId="+$('#CARD_ID').val();
		openEasyWin("winId","录入临时卡信息",url,"800","400",true);
	}
	function updateTemporaryCard(id){
		var url = "${ctx}/app/per/temporaryCardInput.act?cardId="+id;
		openEasyWin("winId","修改临时卡信息",url,"399","400",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteTemporaryCard.act");
					$('#queryForm').submit();
				}
			});		
		}
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