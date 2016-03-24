<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 菜单类型管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>菜肴类型列表</title>	
	<%@ include file="/common/include/metaIframe.jsp" %>
	<link href="css/dhsq.css" type="text/css" rel="stylesheet" />
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
	</script>
<style>
</style>
</head>
<body style="background:#fff;">
	<form id="queryForm" action="queryMenus.act" method="post"  autocomplete="off">
	<s:hidden name="FM_ID"/>
 	<h:panel id="query_panel" width="100%" title="菜单类型说明">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" align="left">
           	<div class="infrom_td" style="text-align:left;">菜单类型的排序码会决定菜单显示时候的顺序</div>
          </td>
        </tr>     
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add,del" title="菜单类型列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table" requestURI="list.act?exFile=1">	
			<d:col   style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.FM_ID}"/>
			</d:col>
			<d:col property="FM_BAK1" class="display_leftalign" title="排序码"/>
		    <d:col property="FM_NAME" class="display_leftalign" title="类型"/>
		    <d:col property="FM_BAK2" class="display_leftalign" title="备注"/>
			<d:col class="display_centeralign"  media="html" title="修改">				
				<a href="javascript:void(0);" onclick="updateMenus('${row.FM_ID}')">[修改]</a>&nbsp;	
			</d:col>				
		</d:table>
	</h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/app/din/menuManagementInput.act?fmId="+$('#FM_ID').val();
		openEasyWin("winAddMenu","录入菜单信息",url,"500","250", true);
	}
	function updateMenus(id){
		var url = "${ctx}/app/din/menuManagementInput.act?fmId="+id;
		openEasyWin("winAddMenu","修改菜单信息",url,"500","250", true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteMenus.act");
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