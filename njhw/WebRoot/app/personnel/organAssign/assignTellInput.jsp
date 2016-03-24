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
	<%@ include file="/common/include/metaIframe.jsp" %>
	<title>IP电话分配</title>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(document).ready(function() {
			var orgId = parent.document.getElementById("sel_org_id").value;
			var orgName = parent.document.getElementById("sel_org_name").value;
			$("#sel_orgId").val(orgId);
			$("#sel_orgName").val(orgName);
			$("#span_orgName").html(orgName);
			goPage1(1);
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

		});
		
			function btnSearchTelAndNum(){
				goPage1(1);
			}
			
		function addTel(){
			var url = "${ctx}/app/per/ipTellAssignForAdd.act?orgId="+$('#sel_orgId').val();
			openEasyWin("addTel", "分配电话传真号码", url, 800, 620, false, null, 'addTelIframe');	
		}
		
		function deleteTel(){
			var ids = "";
			var checks ="";
			$("#roomTR input[type='checkbox']").each(function(i,item) {
				if ($(item).attr("checked")=="checked"){	
					ids += $(this).val()+",";
				}
			});
			$("#ipTellids").val(ids);
			if (ids == "") {
				easyAlert("提示信息", "请选择需要删除的号码！","info");
				return false;
			}
			$("#inputForm").ajaxSubmit({   
                    type: 'post',   
                    url: "allotIpSaveDelete.act" ,   
                    success: function(msg){
						if(msg == 'success') {
							easyAlert("提示信息", "删除资源完成！","info", function() {
								goPage1(1);
							});
			  			} else if (msg == 'error') {
							easyAlert("提示信息", "删除资源出错！","info");
			  			} else {
			  				easyAlert("提示信息", '资源'+msg + '已经被分配到个人，无法删除！',"info", function() {
			  					goPage1(1);
							});
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
		 	var url = "${ctx}/app/per/ajaxRefreshTell.act";
			var telNum = $("#telNum").val();
			var resType = $("#resType").val();
			var pageSize = $("#pageSize").val();
			var resStatus = '1';
			var sucFun = function (data){
				$("#page_div").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data = {pageNo:pageNo,orgId:orgId,telNum:telNum,resType:resType,pageSize:pageSize,resStatus:resStatus};
			ajaxQueryHTML(url,data,sucFun,errFun);
		}
		function reset()
		{
			 $("#telNum").val('');
			 $("#resType").val('1');
			 $("#resStatus").val('');
		}

		/**
		* 实现翻页的方法
		*/
		function goPage(pageNo){
			var orgId = $("#sel_orgId").val();
		 	var url = "${ctx}/app/per/ajaxRefreshTell.act";
			var telNum = $("#telNum").val();
			var resType = $("#resType").val();
			var resStatus = '1';
			var sucFun = function (data){
				$("#page_div").empty().html(data);
			};
			var errFun = function (){
				alert("加载数据出错!");
			};
			var data = {pageNo:pageNo,orgId:orgId,telNum:telNum,resType:resType,resStatus:resStatus};
			ajaxQueryHTML(url,data,sucFun,errFun);
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
</style>
</head>

<body style="background: #fff;">	
	<div class="bgsgl_right_list_border">
		<div class="bgsgl_right_list_left" id="span_orgName"></div>
	</div>
	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
	<td width="20%" align="left" class="form_label">
     	<div class="infrom_td">资源类型： </div>
     </td>
     <td width="30%">
     	<s:select list="#application.DICT_TEL_TYPE" cssStyle="width: 151px;list-style:none;"  listKey="dictcode" listValue="dictname" name="resType" id="resType"/>
     </td>
      <td width="20%" align="left" class="form_label">
     	<div class="infrom_td">号码： </div>
     </td>
     <td width="30%">
		<input id="pageNo" value="1" type="hidden"/>
		<input id="telNum" style="height: 17px;" type="text" size="15"/>
     </td>
    </tr>
   	<tr>
     <td class="form_bottom"  style="color:#808080;font-size:13px;" colspan="4">
     	<div class="infrom_td" style="width:60%;float:left;text-align:left;margin-left:60px;">单位电话传真分配总数：${totalCount};&nbsp;&nbsp;&nbsp;IP电话：${ipCount};&nbsp;&nbsp;&nbsp;传真：${faxCount};&nbsp;&nbsp;&nbsp;网络传真：${webFaxCount}</div>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="resetbutton" onclick="reset();">重置</a>
            <a href="javascript:void(0);" class="infrom_a" style="width:70px;margin:0 5px;" id="searchbutton" onclick="btnSearchTelAndNum();">查询</a>
     </td>
    </tr>
      </table>      
   </h:panel>

	<div style="background:#fff;height:8px;"></div>

        <form  id="inputForm" action=""  method="post"  autocomplete="off" >
        <input type="text" name="ipTellids" id="ipTellids"  style="display: none;"/>
    	<input type="hidden" name="ipTellids" id="ipTellids"/>
    	<input type="hidden" name="orgId" id="sel_orgId"/>
    	<input type="hidden" name="orgName" id="sel_orgName"/>
    	<input type="hidden" name="resType" id="resType"/>
        <span></span>
		</form>
	<div id="page_div">
		
	</div>
	
	<a id="delBtn" class="buttonFinish" style="float:right;" href="javascript:void(0);" onclick="javascript:deleteTel()">删除资源</a>
  	<a id="saveBtn" class="buttonFinish" style="float:right;margin-right:20px;" href="javascript:void(0);" onclick="javascript:addTel();">添加资源</a>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','删除成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	var msg = "删除失败";
	<c:if test="${not empty failName}">
		msg = '资源'+'${failName}' + '已经被分配到个人，无法删除！'
	</c:if>
	easyAlert('提示信息',msg,'error');
</c:if>
</script>
