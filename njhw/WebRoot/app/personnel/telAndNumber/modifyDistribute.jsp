﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>电话变更申请单</title>
		<link href="css/dhsq.css" type="text/css" rel="stylesheet" />
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script type="text/javascript">
        	/**
        	*保存电话分配
        	*/
        	function saveTelDisChange(telType){
        		//openAdminConfirmWin(saveTelDisChangeFn,{telType:telType});
				saveTelDisChangeFn(telType);
        	}
        	
	        function saveTelDisChangeFn(params){
	            //alert(11);
	        	var userId = $("#sel_user_id").val();//get present userid
	        	var telId = '${Tel.telId}';//get present telid
	        	var telNum = '${Tel.telNum}';
	        	var permits = "";
	        	var telExt="";
	        	var functions = "";
	        	var oldUserId = "${oldUser.userid}";//get old suerid
	        	$("input[name='permit']:checked").each(function(){
					permits += $(this).val()+",";
				});
				$("input[name='funcType']:checked").each(function(){
					telExt += $(this).val();
				});
				$("input[name='function']:checked").each(function(){
					functions += $(this).val()+",";
				});
				//alert(functions);
	        	var url = "${ctx}/app/personnel/telAndNumber/saveTelDisChange.act";//alert(11);
	        	var data = {userId:userId,telId:telId,permits:permits,functions:functions,telExt:telExt,oldUserId:oldUserId,telType:params.telType,telNum:telNum};
				//alert(11);
				sucFun = function (data){
					if (data.result=='1'){
						easyAlert('提示信息','变更成功！&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp','info');
					} else {
						easyAlert('提示信息','变更失败！&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp','info');
					}
					parent.$("#companyWin").window("close");
				};
				
				errFun = function (data){
					easyAlert('提示信息','加载页面出错!','info');
				};
				ajaxQueryJSON(url,data,sucFun,errFun);
	        }
	        /**
	        /**
	        * 查询用户信息
	        */
	        function chooseUser(telType){
	            
	        	var url = "${ctx}/app/personnel/telAndNumber/orgTreeUser.act?telType="+telType;
	        	openEasyWin("chooseUserWin","更改电话申请",url,"500","300",false);
	        	//windowDialog(url,11,500,300);
	        }
        	function windowDialog(url,top,w,h){
				$("#companyWin").show();
				var left = (document.body.scrollWidth - w) / 2;
				$("#companyWin").window({
					title : '更改电话申请',
					modal : true,
					shadow : false,
					closed : false,
					width : w,
					height : h,
					top : top,
					left : left,
					onBeforeClose:function(){
						
					}
				});
				$("#companyIframe").attr("src", url);
			}
			//刷新用户
			function refreshUser(userId,userName,orgName){
				
			    $("#sel_user_orgname").val(orgName);
				userId = userId.substring(0,userId.indexOf("-"));
				userName = userName.substring(0,userName.indexOf("["));
				$("#sel_user_name").val($.trim(userName));
				$("#sel_user_id").val(userId);
				//var url = "${ctx}/app/personnel/telAndNumber/refreshUser.act";
				//var data = {userId:userId};
				//sucFun = function (data){
					//data.roomId
					//$("#div_room_id").html(data.roomInfo);
					//$("#companyWin").window("close");
					
				//};
				//errFun = function (data){
					//easyAlert('提示信息','加载页面出错!','info');
				//};
				//$("#companyWin").window("close");
				//ajaxQueryJSON(url,data,sucFun,errFun);
			}
		
        </script>
	</head>
	<body>
<div class="from_css" style="width: 700px;">
<!--   	    <div class="bgsgl_right_list_border"> -->
<!-- 		  <div class="bgsgl_right_list_left">电话变更</div> -->
<!-- 		</div> -->
<div class="from_table">
<form action="" method="post">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="from_table_td">用户姓名：</td>
    <td>
        <input id ="sel_user_name" type="text"   class="input_from" value="${oldUser.displayName}" onclick="chooseUser(${telType});"  />
		<input id="sel_user_id" type="hidden" value="${oldUser.userid}"/></td>
		<td class="from_table_td">电话号码：</td>
        <td> <input type="text" disabled="disabled"  class="input_from" value="${Tel.telNum}" /></td>
  </tr>
  <tr>
  <td class="from_table_td">组织机构：</td>
    <td><input id="sel_user_orgname" type="text"  class="input_from"  disabled="disabled" value="${orgName}"/>
    </td>
    
    <td class="from_table_td">话机MAC：</td>
    <td><input type="text" disabled="disabled"  class="input_from" value="${telmac_chg}" /></td>

  </tr>
  <tr>
     <td class="from_table_td">功能：</td>
     <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
         <tr>
            <td >  <input type="checkbox" name="function" value="1" <c:if test="${Tel.telForword=='2'}">checked</c:if> />&nbsp;呼叫转移&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   <input type="checkbox" name="function" value="2" <c:if test="${Tel.telCw=='2'}">checked</c:if> />&nbsp;呼叫等待
            </td>
         </tr>
         </table>
    <td class="from_table_td">权限：</td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr>
              <td> <input name="permit" type="checkbox" value="1" <c:if test="${Tel.telIdd=='2'}">checked</c:if>/>&nbsp;国际&nbsp;&nbsp;&nbsp;
                   <input name="permit" type="checkbox" value="2" <c:if test="${Tel.telddd=='2'}">checked</c:if>/>&nbsp;国内&nbsp;&nbsp;&nbsp;
                   <input name="permit" type="checkbox" value="3" <c:if test="${Tel.telLocal=='2'}">checked</c:if>/>&nbsp;市话&nbsp;&nbsp;&nbsp;
                   <input name="permit" type="checkbox" value="4" <c:if test="${Tel.telCornet=='2'}">checked</c:if>/>&nbsp;内网
              </td>
           </tr>
       </table>
    </td>   
  </tr> 
  <tr>
    <td class="from_table_td">对外公布：</td>
    <td> <input type="radio" name="funcType" class="funcType" value="2" <c:if test="${Tel.telExt=='2'}">checked</c:if> />&nbsp;是&nbsp;&nbsp;&nbsp;
         <input type="radio" name="funcType" class="funcType" value="1" <c:if test="${Tel.telExt=='1'}">checked</c:if> />&nbsp;否
    </td>
  </tr>
</table>
<div class="botton_from"><a href="javascript:void(0)" onclick="saveTelDisChange(${telType});">保&nbsp;&nbsp;存</a> </div>
</form>
</div>
</div>
<div id='companyWin' class='easyui-window' collapsible='false'
			minimizable='false' maximizable='true'
			style='padding: 0px; background: #fafafa; overflow: hidden; display: none;'
			closed='true'>
			<iframe id='companyIframe' name='companyIframe'
				src=""
				style='width: 100%; height: 100%;' frameborder='0'></iframe>
		</div>
</body>
</html>