<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-09-06 15:37:15
- Description: 业务字典明细页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>业务字典列表</title>	
		<%@ include file="/common/include/meta.jsp" %>	
		<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/qbyj_index.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/table.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/common/pages/misc/css/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/app/integrateservice/css/wizard_css.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script src="${ctx}/common/pages/misc/js/ctt.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var chk_options = { 
					all:'#checkAll',
					item:'.checkItem'
				};
				$(this).chk_init(chk_options);
				//聚焦第一个输入框
				$("#dictname").focus();
				changebasiccomponentstyle();			
				changedisplaytagstyle();
				changecheckboxstyle();
			});
			function addRecord(){
				var url = "${ctx}/common/dictMgr/dictdetainput.act?dicttype="+$('#dicttype').val();
				openEasyWin("winId","录入业务字典信息",url,"600","400",true);
			}
			function updateRecord(id){
				var url = "${ctx}/common/dictMgr/dictdetainput.act?dictid="+id;
				openEasyWin("winId","修改业务字典信息",url,"600","400",true);
			}
			function delRecord(){
				if($("input[@type=checkbox]:checked").size()==0){
					easyAlert('提示信息','请勾选要删除的记录！','info');
				}else{
					easyConfirm('提示', '确定删除？', function(r){
						if (r){
							$('#queryForm').attr("action","deleteDeta.act");
							$('#queryForm').submit();
						}
					});		
				}
		    }
    
    	function searchDictdeta(){
   			var dictname = $.trim($("#dictname").val());
	    	var dictcode = $.trim($("#dictcode").val());
	    	var dicttype = $("#dicttype").val();
	    	var url = "${ctx}/common/dictMgr/dictdetaDiaList.act";
	    	var pageNo = $("#pageNoInner").val();
			$.ajax({
				async:false,
			   type: "POST",
			   url: url,
			   data: {
			   	   dictname:dictname,
			   	   dictcode:dictcode,
			   	   dicttype:dicttype,
			   	   "page.pageNo":pageNo
			   },
			   success: function(msg){
			     if(msg){
			     	$("#dictdetaDiaList").html(msg);
			     }
			   }
			}); 	
		   }
    
    		function resetQuery(){
				$('#dictname').val('');
				$('#dictcode').val('');
			}
			
			function BtnSearchDictData(){
				$("#pageNoInner").val(1);
				searchDictdeta();
			}
			
			function jumpPage(pageNo){
				if (null != pageNo){
					$("#pageNoInner").val(pageNo);
				}
				searchDictdeta();
			}
		</script>
<style>
</style>
</head>
<body style="background: #FFFFFF;">
	<input type="hidden" id="pageNoInner" value="1"/>
	<input type="hidden" id="dicttype" value="${dicttype }"/>
 	<div width="100%">	
 	<h:panel id="query_panel" width="100%" title="查询条件">
      <table align="center"  id="hide_table"  border="0" width="100%" class="form_table" style="background-color: #FFFFFF;">
        <tr>
          <td class="form_label" width="20%" align="left">
           	字典名称：
          </td>
          <td width="30%">
           <s:textfield name="dictname" id="dictname" theme="simple" size="10"/>  
          </td>
          <td class="form_label" width="20%" align="left">
           	字典编码：
          </td>
          <td width="30%">
           <s:textfield name="dictcode" id="dictcode" theme="simple" size="10"/>  
          </td>
        </tr>
        <tr style="background-color: #FFFFFF;">
          <td colspan="4" style="padding: 0px 0 0px 8px; border-top:solid 1px #7f90b3;text-align: center;background: #FFFFFF;height: 50px;">
			<a class="fkdj_botton_reset" href="javascript:void(0);" id="resetbutton"  class="images_search" onclick="BtnSearchDictData()">查&nbsp;&nbsp;询</a>
			<a class="fkdj_botton_reset" id="searchbutton" href="javascript:void(0);" onclick="resetQuery()">重&nbsp;&nbsp;置</a>         	
          </td>
        </tr>
      </table>
      </h:panel>
      <div class="qbyj_conter" id="qbyj_conter_div" style="height:100%;">
      	<div id="dictdetaDiaList">
   			<%@ include file="/common/pages/acmgr/dictdetaDiaList.jsp" %>
   	  	</div>
   	  </div>
   </div>
   </body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
