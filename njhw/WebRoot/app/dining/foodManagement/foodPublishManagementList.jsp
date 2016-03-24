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
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<link   type="text/css"  rel="stylesheet" href="${ctx}/app/personnel/unit/css/TelMessage.css" />
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script type="text/javascript">
		var selectArr = new Array();
		$(document).ready(function(){
			$("#fdiFlagFlag").val('${fdiFlagFlag}');
			$("#fdiTypeType").val('${fdiTypeType}');
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();

			selectArr = '${selectArr}'.split(',');
			goPage1(1);
		});
		
		function btnSearch(){
			goPage1(1);
		}
				/**
		* 实现翻页的方法
		*/
		function goPage1(pageNo){
		 	var url = "${ctx}/app/din/ajaxRefreshMenu.act";
			var fdClass = $("#fdClass").val();
			var pageSize = $("#pageSize").val();
			var sucFun = function (data){
				$("#page_div").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data = {pageNo:pageNo,fdClass:fdClass,pageSize:pageSize,selectArr:selectArr.join(',')};
			ajaxQueryHTML(url,data,sucFun,errFun);
		}

		/**
		* 实现翻页的方法
		*/
		function goPage(pageNo){
		 	var url = "${ctx}/app/din/ajaxRefreshMenu.act";
			var fdClass = $("#fdClass").val();
			var sucFun = function (data){
				$("#page_div").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data = {pageNo:pageNo,fdClass:fdClass,selectArr:selectArr.join(',')};
			ajaxQueryHTML(url,data,sucFun,errFun);
		}
		
		function resetFormForPublish() {
			$("#fdClass").val("");
			goPage1(1);
		}
	</script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<style type="text/css" charset="utf-8">
.disabled {
	width: 24px;
	height: 24px;
	line-height: 24px;
	display: inline-block;
	background: #ccc;
	margin: 5px;
	color: #fff;
	text-decoration: none;
	font-weight: bold;
	font-family: "寰蒋闆呴粦";
	overflow: hidden;
	text-align: center;
}
</style>
</head>
<body style="background:#fff;">
	<div style="width:100%;height:100%;position:absolute;z-index:0">
	<form id="inputForm" action="" method="post"  autocomplete="off">
	<s:hidden id="fdiFlagFlag" name="fdiFlagFlag"/>
	<s:hidden id="fdiTypeType" name="fdiTypeType"/>
	<input type="hidden" name="cfdiId" id="cfdiId"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="15%" align="left">
           	<div class="infrom_td">类型：</div>
          </td>
          <td width="35%">
           <s:select list="#request.fsClassMap" headerKey="" headerValue="全部"  listKey="key" listValue="value"  id="fdClass" name="fdClass" cssStyle="width:150px;"/>
          </td>
          <td width="50%">
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="resetFormForPublish();">重置</a>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="btnSearch();">查询</a>
          </td>
        </tr>
      </table>      
   </h:panel>
   <div id="page_div" style="height:550px;width:100%;position:absolute;z-index:-1;background:#F6F5F1;">
	</div>
	</form>
	<div style="width:100%;height:40px;position:absolute;z-index:0;margin-top:510px;">
	<a href="javascript:void(0);" class="buttonFinish" style="float:right;margin-left:10px;" id="publishDish" onclick="publishDish();">发布</a>
   	<a href="javascript:void(0);" class="buttonFinish" style="float:right" id="preview" onclick="previewDish();">预览</a>
   	</div>
	</div>
	<div style="width:100%;height:100%;position:absolute;z-index:1;display:none;background:#F6F5F1;" id="preView">
		<div id="menuPreView" style="width:100%;height:585px;"></div>
		<a href="javascript:void(0);" class="buttonFinish" style="float:right;margin-left:10px;" id="publishDish" onclick="publishDish();">发布</a>
		<a href="javascript:void(0);" class="buttonFinish" style="float:right" id="publishDish" onclick="closePreview();">继续选择</a>
	</div>
<script type="text/javascript">

	function publishDish() {
			  $("#cfdiId").val(selectArr.join(','));
			  $("#inputForm").ajaxSubmit({   
                    type: 'post',   
                    url: "theNewMenuSave.act" ,
                    success: function(data){   
                  		easyAlert("提示信息","保存成功!","info",
	   						function(){
	   						var url = "${ctx}/app/din/ajaxInitNewWeekMenu.act";
	   						top.njhwAdmin.$("#div_uid").load(url, {id: $('#fdiTypeType').val(), FSD_FLAG: $('#fdiFlagFlag').val()});
	   						closeEasyWin('cdfb');}
						);
                    },   
                    error: function(XmlHttpRequest, textStatus, errorThrown){   
                        easyAlert("提示信息","保存失败!","error");
                    }   
               });
	}
	
	function previewDish() {
		$('#preView').show();
		var url = "${ctx}/app/din/showMenuPreview.act";
		$('#menuPreView').load(url, {fdIds: selectArr.join(',')});
	}
	
	function closePreview() {
		$('#preView').hide();
		$("#fdClass").val("");
		goPage1(1);
	}

</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>