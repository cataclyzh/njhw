<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: SQS
- Date: 2013-03-19 15:37:15
- Description: 菜名管理页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>菜名列表</title>	
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

		function resetFormForAllot(){
			$("#fdName").val("");
			$("#fdClass").val("");
		}
			
	</script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<style>
</style>
</head>
<body style="background:#fff;">
	<form id="queryForm" action="queryDishes.act" method="post"  autocomplete="off">
	<s:hidden name="FD_ID"/>
	<s:hidden name="page.pageSize" id="pageSize" />
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="15%" align="left">
           	<div class="infrom_td">菜名：</div>
          </td>
          <td width="35%">
           <s:textfield name="fdName" id="fdName" theme="simple" size="18"/>  
          </td>
          <td class="form_label" width="15%" align="left">
           	<div class="infrom_td">类型：</div>
          </td>
          <td width="35%">
           <div style="width:160px;display:inline-block;">
           <s:select list="#request.fsClassMap" headerKey="" headerValue="全部"  listKey="key" listValue="value"  id="fdClass" name="fdClass" cssStyle="width:150px;"/>
           </div>
           <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;display:inline-block;" id="editMenu" onclick="editDishType();">编辑</a>
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="resetFormForAllot();">重置</a>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="querySubmit();">查询</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add,del" title="菜单列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table" requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col   style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.FD_ID}"/>
			</d:col>
		    <d:col property="FD_NAME" class="display_leftalign" title="菜名"/>
		    <d:col property="FD_CLASS" dictTypeId="FD_CLASS" class="display_leftalign" title="类型"/>
		    <d:col property="YN" maxLength="55" dictTypeId="YN" class="display_centeralign" style="color:#877777;" title="图片"/>
			<d:col class="display_centeralign"  media="html" title="修改">				
				<a href="javascript:void(0);" onclick="updateDishes('${row.FD_ID}')">[修改]</a>&nbsp;	
			</d:col>				
		</d:table>
	</h:page>
	</form>
<script type="text/javascript">

	function editDishType() {
		var url = "${ctx}/app/din/queryMenus.act?";
		openEasyWin("dishTypeEdit","类型编辑",url,"700","600",true);
	}
	
	function addRecord(){
		var url = "${ctx}/app/din/foodManagementInput.act?fdId="+$('#FD_ID').val();
		openEasyWin("winUpdateDining","录入菜单信息",url,"700","450",true);
	}
	function updateDishes(id){
		var url = "${ctx}/app/din/foodManagementInput.act?fdId="+id;
		openEasyWin("winUpdateDining","修改菜单信息",url,"700","450",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteDishes.act");
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