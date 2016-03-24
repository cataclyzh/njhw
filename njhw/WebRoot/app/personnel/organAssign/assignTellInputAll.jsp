<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<%--
- Author: sqs
- Date: 2013-03-19 Description: Ip电话分配
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>IP电话分配</title>
	<%@ include file="/common/include/meta.jsp" %>
	<link   type="text/css"  rel="stylesheet" href="${ctx}/app/personnel/telAndNumber/css/TelMessage.css" />
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			jQuery.validator.methods.compareValue = function(value, element) {
	           if(value == "0" || value == ""){
	        	   return false;
	           }
	        	return true;
	        };
	        $("#orgId").focus();
			$("#inputForm").validate({
				meta :"validate",
				errorElement :"div",
				rules: {
					orgId: {
						required: true,
						compareValue:true
					},
					ipTellids: {
						compareValue:false
					}
				},
				messages: {
					orgId:{
						compareValue:"请选择委办局"
					},
					ipTellids:{
						compareValue:"请选择IP电话"
					}
				}
			});
			var orgId = parent.document.getElementById("sel_org_id").value;
			var orgName = parent.document.getElementById("sel_org_name").value;
			$("#sel_orgId").val(orgId);
			$("#sel_orgName").val(orgName);
			$("#span_orgName").html(orgName);
				goPage1(1)
		});
		
			function btnSearchTelAndNum(){
				goPage1(1);
			}
			
		function saveData(type){
			var ids = "";
			var checks ="";
			$("#roomTR input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("disabled")!="disabled")	{		
					ids += $(this).val()+",";
					if ($(item).attr("checked")=="checked"){
						checks += "1"+",";
					} else {
						checks += "0"+",";
					}
				}
			});
			$("#ipTellids").val(ids);
			$("#ipTellChecks").val(checks);
			$("#orgName").val($("#orgId option:selected").text());
			//$('#inputForm').submit();
			$("#inputForm").ajaxSubmit({   
                    type: 'post',   
                    url: "allotIpSave.act" ,   
                    success: function(data){
	                     if('save' == type)
						 {
						 	parent.parent.easyAlert('提示信息','保存成功。','info');
						 }
					},   
                    error: function(XmlHttpRequest, textStatus, errorThrown){   
                        alert( "error");   
                    }   
             });  
			
		}
		
		// 选中全部
		function chk_all() {
			$("#page_div input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("disabled")!="disabled")	{		
					if ($("#phone_check_all").attr("checked") == "checked"){
						$(item).attr("checked", "true");
					} else {
						$(item).removeAttr("checked");
					}
				}
			});
		}
		
		/**
		* 实现翻页的方法
		*/
		function goPage1(pageNo){
			var orgId = $("#sel_orgId").val();
		 	var url = "${ctx}/app/per/ajaxRefreshTellAll.act";
			var telNum = $("#telNum").val();
			var resType = $("#resType").val();
			if(resType =="2")
			{
				$("#fax").show();
				$("#faxWeb").hide();
				$("#ipPhone").hide();
			}
			else if(resType =="3")
			{
				$("#faxWeb").show();
				$("#fax").hide();
				$("#ipPhone").hide();
			}
			else
			{
				$("#ipPhone").show();
				$("#faxWeb").hide();
				$("#fax").hide();
			}
			var pageSize = $("#pageSize").val();
			var resStatus = $("#resStatus").val();
			var sucFun = function (data){
				$("#page_div").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data = {pageNo:pageNo,orgId:orgId,telNum:telNum,resType:resType,pageSize:pageSize,resStatus:resStatus};
			ajaxQueryHTML(url,data,sucFun,errFun);
			
			$.each(($("td[id^=td_]")), function(i, n){
				$(n).mouseover(function(e) {
        			// 判断当前是否鼠标停留在对象上,可以修正ie6下出现的意外Bug;
					mouseon=true;
        			// 清空内容，因为title属性浏览器默认会有提示;
					$(this).attr('title','');
				
					var tel_id = $(this).attr('id').substring(3);
				
        			// 取得鼠标坐标来指定提示容器的位置;
        			var left = e.pageX + 30;
        			if (left + $('#tooltip_'+tel_id).width() > $("body").width()) {
        				left = e.pageX - $('#tooltip_'+tel_id).width() - 20;
        			}
					$('#tooltip_'+tel_id).css({
              			top:e.pageY + 10,
              			left:left
        			}).show();
				}).mousemove(function(e) {
					var tel_id = $(this).attr('id').substring(3);
					var left = e.pageX + 30;
        			if (left + $('#tooltip_'+tel_id).width() > $("body").width()) {
        				left = e.pageX - $('#tooltip_'+tel_id).width() - 20;
        			}
					$('#tooltip_'+tel_id).css({
              			top:e.pageY + 10,
              			left:left
        			}).show();
				}).mouseout(function() {
					var tel_id = $(this).attr('id').substring(3);
					if(mouseon==true){
						$('#tooltip_'+tel_id).hide();
						mouseon=false;
					}
				});
			});
		}
		function reset()
		{
			 $("#telNum").val('');
			 $("#resType").val('1');
			 $("#resStatus").val('');
			 goPage1(1);
		}
		
	function checkPaSize() {
		var a = $("#pageSize").val();
		if (!isInt(a) || a < 10 || a > 120) {
			parent.easyAlert("提示", "您好!请在每页显示记录数中输入10-120之间的数字！", "warning");
			return false
		}
		return true
	}

		/**
		* 实现翻页的方法
		*/
		function goPage(pageNo){
			var orgId = $("#sel_orgId").val();
		 	var url = "${ctx}/app/per/ajaxRefreshTellAll.act";
			var telNum = $("#telNum").val();
			var resType = $("#resType").val();
			var resStatus = $("#resStatus").val();
			var sucFun = function (data){
				$("#page_div").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data = {pageNo:pageNo,orgId:orgId,telNum:telNum,resType:resType,resStatus:resStatus};
			ajaxQueryHTML(url,data,sucFun,errFun);
			
			$.each(($("td[id^=td_]")), function(i, n){
				$(n).mouseover(function(e) {
        			// 判断当前是否鼠标停留在对象上,可以修正ie6下出现的意外Bug;
					mouseon=true;
        			// 清空内容，因为title属性浏览器默认会有提示;
					$(this).attr('title','');
				
					var tel_id = $(this).attr('id').substring(3);
				
        			// 取得鼠标坐标来指定提示容器的位置;
        			var left = e.pageX + 30;
        			if (left + $('#tooltip_'+tel_id).width() > $("body").width()) {
        				left = e.pageX - $('#tooltip_'+tel_id).width() - 20;
        			}
					$('#tooltip_'+tel_id).css({
              			top:e.pageY + 10,
              			left:left
        			}).show();
				}).mousemove(function(e) {
					var tel_id = $(this).attr('id').substring(3);
					var left = e.pageX + 30;
        			if (left + $('#tooltip_'+tel_id).width() > $("body").width()) {
        				left = e.pageX - $('#tooltip_'+tel_id).width() - 20;
        			}
					$('#tooltip_'+tel_id).css({
              			top:e.pageY + 10,
              			left:left
        			}).show();
				}).mouseout(function() {
					var tel_id = $(this).attr('id').substring(3);
					if(mouseon==true){
						$('#tooltip_'+tel_id).hide();
						mouseon=false;
					}
				});
			});
		}
		
		function hideCovDis()
		{   
			if($("#queryId").is(":hidden"))
			{   
				$("#panel1_button").removeClass("PANEL_CLOSE_BUTTON");
				$("#panel1_button").addClass("PANEL_OPEN_BUTTON");
			}
			else
			{     
				$("#panel1_button").removeClass("PANEL_OPEN_BUTTON");
				$("#panel1_button").addClass("PANEL_CLOSE_BUTTON");
			}
			$("#queryId").slideToggle(300);
		}
	</script>
<style type="text/css" charset="utf-8">
	.current_left {
	width:108px;
	height:24px;
	line-height:24px;
	display:inline-block;
	margin:5px 0;
	overflow:hidden;
}

.buttonFinish{
	width:90px;
	height:25px;
	line-height:25px;
	text-decoration:none !important;
	text-align:center;
	display:block;
	color:#fff;
	background:#ffc600;
	font-family:"微软雅黑";
	font-weight:bold;
	font-size:14px;
	margin:auto;
}

.disabled {
	width:24px;
	height:24px;
	line-height:24px;
	display:inline-block;
	background:#ccc;
	margin:5px;
	color:#fff;
	text-decoration:none;
	font-weight:bold;
	font-family:"微软雅黑";
	overflow:hidden;
}

.tooltip {
	display:none;
	width:150px;
	height:60px;
	position:absolute;
	z-index:9999;
	border:1px solid #333;
	/*background:#f7f5d1;*/
	background:#fff;
	padding:1px;
	color:#808080;
	font-size:13px;
	text-align:left;
}
</style>
</head>

<body topmargin="0" leftmargin="0" class="layout-body panel-body" style="margin-top:5px;width:100%; height:99%; overflow: auto; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 0px; background-image: none; background-attachment: scroll; background-repeat: repeat; background-position-x: 0%; background-position-y: 0%; background-color:rgb(246, 245, 241););">
					<table class="panel-table">
					<tr>
						 <td height="20">
						  <table width="100%" border="0" cellSpacing="0" cellPadding="0">
						   <tbody>
						    <tr>
						     <td width="4">
						      <div class="panel-title-left"></div>
						     </td>
						     <td width="20" class="panel-title">
						      <div class="PANEL_OPEN_BUTTON" id="panel1_button" onclick="hideCovDis();"></div>
						     </td>
						     <td class="panel-title">
						      查询条件
						     </td>
						    </tr>
						   </tbody>
						  </table>
						 </td>
					</tr>
					<tr>

 <td class="panel-body" vAlign="top">
 	<div id="queryId">
	<table width="720px" align="center" class="form_table" id="hide_table" style="display: table;" border="0" >
   <tbody>
    <tr>
	<td width="12%" align="left" class="form_label">
     	资源类型： 
     </td>
     <td width="20%">
     	<s:select list="#application.DICT_TEL_TYPE" cssStyle="width:70%;list-style:none;"  listKey="dictcode" listValue="dictname" name="resType"/>
     </td>
      <td width="12%" align="left" class="form_label">
  		   号码： 
     </td>
     <td width="20%">
		<input id="pageNo" value="1" type="hidden"/>
		<input id="telNum" style="height: 17px;" type="text" size="15"/>
     </td>
     <td width="12%" align="left" class="form_label">
     	状态： 
     </td>
     <td width="20%">
     	<s:select list="#application.DICT_ISALLOT" headerKey="" headerValue="全部" cssStyle="width:70%;list-style:none;"  listKey="dictcode" listValue="dictname" name="resStatus"/>
     </td>
    </tr>
    <tr>
     <td class="form_label" colspan="4" style="color:#808080;font-size:13px;">
          	<div class="infrom_td" id="ipPhone" style="text-align:left;padding-left:95px;">IP电话总数：${countMap["ipTotalCount"]}&nbsp;&nbsp;&nbsp;已分配IP电话数：${countMap["ipDisCount"]}&nbsp;&nbsp;&nbsp;未分配IP电话数：${countMap["ipTotalCount"] - countMap["ipDisCount"]}</div>
            <div class="infrom_td" id="fax" style="text-align:left;padding-left:95px;display:none;">传真总数：${countMap["faxTotalCount"]}&nbsp;&nbsp;&nbsp;已分配传真数：${countMap["faxDisCount"]}&nbsp;&nbsp;&nbsp;未分配传真数：${countMap["faxTotalCount"] - countMap["faxDisCount"]}</div>
          	<div class="infrom_td" id="faxWeb" style="text-align:left;padding-left:95px;display:none;">网络传真总数：${countMap["webFaxTotalCount"]}&nbsp;&nbsp;&nbsp;已分配网路传真数：${countMap["webFaxDisCount"]}&nbsp;&nbsp;&nbsp;未分配网络传真数：${countMap["webFaxTotalCount"] - countMap["webFaxDisCount"]}</div>
     </td>
    
     <td  colspan="2">
     <table>
     <tr>
	     <td width="80%">
	     </td>
     <td class="form_bottom" style="text-align:left padding-right: 0px;"  width="10%">
      <div class="table_btn" style="height: 22px; line-height: 22px; margin-top: 0px; float: left;cursor:hand" onclick="btnSearchTelAndNum();">
      	 查询
      </div>
     </td>	
     <td class="form_bottom" style="text-align: right; padding-right: 20px;"  width="10%">
      <div class="table_btn" style="height: 22px; line-height: 22px; margin-top: 0px; float:right;cursor:hand" onclick="reset();">
     	 重置
      </div>
     </td>
     </tr>
     </table>
     </td>
    </tr>
   </tbody>
  </table>
   </div>
 </td>

</tr>
					

				</table>

	<div style="background:#fff;height:8px;"></div>
	<div class="bgsgl_conter" style="padding-bottom:0px;min-height: 00px;border:none;">
    <!--<table align="center" border="0" width="100%" class="form_table">
	<tr>
        <td class="form_label"  style="font-weight:bold;" width="25px"></td>
        <td width="82%">
        <form  id="inputForm" action=""  method="post"  autocomplete="off" >
        <input type="text" name="ipTellids" id="ipTellids"  style="display: none;"/>
        <input type="text" name="ipTellChecks" id="ipTellChecks"  style="display: none;"/>
    	<input type="hidden" name="ipTellids" id="ipTellids"/>
    	<input type="hidden" name="orgId" id="sel_orgId"/>
    	<input type="hidden" name="orgName" id="sel_orgName"/>
    	<input type="hidden" name="resType" id="resType"/>
		</form>
        </td>
	</tr>
	</table>
	--><div id="page_div">
		
	</div>
	
	</div>
   <table align="center" border="0" width="100%" class="form_table"><!--  
      <tr class="form_bottom" style="height:30px">
        <td colspan="2">
            <div style="margin: 0px;">
				<a onclick="saveData('save');" href="javascript:void(0);"  id="savebut" class="buttonFinish">保&nbsp;&nbsp;存</a>
			</div>
        </td>
    
      </tr>
  --></table>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>
</script>
