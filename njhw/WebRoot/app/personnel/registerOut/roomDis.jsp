<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/scripts/widgets/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>	
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript">
	roomList = new Array();
	roomInfo = '';
	$(document).ready(function(){	
    	$("#page_div input[type='checkbox']").each(function(i,item) {
    		var roomObj = new Object();
    		roomObj.nodeId = $(item).val();
			if ($(item).attr("checked"))	{
				roomObj.checked = true;
			} else {
				roomObj.checked = false;
				isCheckAll = false;
			}
			roomObj.opt = "none";
			roomList[i] = roomObj;
		});
	});
	
	function cancleWindow()
	{   
		 $(window.top.ifrmOrgList.document.getElementById("roomId")).val('');
	     $(window.top.ifrmOrgList.document.getElementById("roomNum")).val('');
		parent.$("#telDis").window("close");
	}
    function closeWindow()
    {
       if("R" == '${ROOM_TYPE}')
       {
	       var list= $('input:radio[name="_chk"]:checked').val(); 
           if(null == list){ 
                easyAlert("提示信息", "请选择一个办公房间！","info");
                // return false; 
           } 
		   $("input[name='_chk']").each(function(index,domEle){
		      if(domEle.checked)
		      {   
					 $(window.top.ifrmOrgList.document.getElementById("roomId")).val(this.value);
				     $(window.top.ifrmOrgList.document.getElementById("roomNum")).val($(":radio:checked + span").text());
			  		 parent.$("#telDis").window("close");
			  }
		   }
	      )
       }
       else
       {  
          // openAdminConfirmWin(doorAuthConfirm);
		   doorAuthConfirm();
       }
	}
	
	function doorAuthConfirm() {
		   $("#confirmBtn").hide();
           $(".room_list_container").height(240);
           $('#confirm_msg').show();
           $(".auth_color").show();
           $('#confirm_msg').html("正在授权中...");
           var dRoomId  = "";
           var dRoomName = "";
		   var roomLength = $("#page_div input[type='checkbox']").length;
		   
		   $("input[name='_chk']").each(function(index,domEle){
		      $("#page_div td:eq(" + index + ") input[type='checkbox']").remove();
		      if(domEle.checked)
		      {     
		         dRoomId+=this.value+",";
		         if (!roomList[index].checked) {
				     roomList[index].opt = "add";
				 }
			  } else {
			     if (roomList[index].checked) {
					 roomList[index].opt = "del";
				 }
			  }
		   })
		 
		          $.ajax({
		            type: "POST",
		            url: "${ctx}/app/per/doorDis.act",
		            data: {
		             "userId": '${userId}',
		             "dRoomIds": dRoomId.substr(0,dRoomId.length-1),
		             "cardId":'${cardId}', 
		             "cardChange":'N'
		            },
		            dataType: 'text',
		            async : false
				});
		 	
		 	var i = 0;
		    function dealPubCard() {
				if (parseInt(i) >= roomLength) {
      				$('#confirm_msg').html("授权完成");
      				$("#completeBtn").show();
      				return;
      			}
      			if (roomList[i].opt == "none") {
      				if (roomList[i].checked) {
      					var roomName = $("#page_div td:eq(" + i + ") span").html();
      					roomInfo += roomName.substring(0,roomName.length-3) + "，";
      				}
      				i = parseInt(i) + parseInt(1);
					dealPubCard();
      			} else {
      				$.ajax({
      				 
						type: "POST",
						url: "${ctx}/app/personnel/unit/showPubCardConfirm.act?",
						data: {
						    "userId": '${userId}',
						    "cardId": '${cardId}',
							"nodeId": roomList[i].nodeId,
							"opt": roomList[i].opt
							},
						dataType: 'text',
						success: function(json){
							json = eval('(' + json + ')');
							if (json.isSuccess) {
								if (roomList[i].opt == "add") {
									var roomName = $("#page_div td:eq(" + i + ") span").html();
      								roomInfo += roomName.substring(0,roomName.length-3) + "，";
								}
								$("#page_div td:eq(" + i + ")").addClass("ok");
							} else {
								if (roomList[i].opt == "del") {
									var roomName = $("#page_div td:eq(" + i + ") span").html();
      								roomInfo += roomName.substring(0,roomName.length-3) + "，";
								}
								$("#page_div td:eq(" + i + ")").addClass("ng");
							}
							i = parseInt(i) + parseInt(1);
							dealPubCard();
						} 
					});
      			}
      		}
      		dealPubCard();
	}

	function closeWindowForDoor() {
		$(window.top.ifrmOrgList.document.getElementById("dRoomName")).text(roomInfo.substring(0, roomInfo.length-1));
		parent.$("#telDis").window("close");
	}
</script>
<title>分配房间</title>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #fff;">
		<div class="win_title" id="confirm_msg" style="display:none;"></div>
		<div id="page_div" class="room_list_container">
					<form id="page_search_form" name="searchForm" method="post">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"align="center">
						<tr class="odd">    
						<c:forEach var="roomLock" items="${roomList}" varStatus="stat">
							<td width="25%">
							<c:if test="${roomLock.IS_CHECKED ne '1' && ROOM_TYPE eq 'R'}">
							<input type="radio" value="${roomLock.ROOM_ID}" name="_chk" id="_chkid"><span>${roomLock.NAME}</span></input>
							</c:if>
							<c:if test="${roomLock.IS_CHECKED eq '1'  && ROOM_TYPE eq 'R'}">
							<input type="radio" value="${roomLock.ROOM_ID}" name="_chk" id="_chkid" checked><span>${roomLock.NAME}</span></input>
							</c:if>
							
							
							
							<c:if test="${roomLock.IS_CHECKED ne '1' && ROOM_TYPE eq 'D'}">
							<input type="checkbox" value="${roomLock.NODE_ID}" name="_chk" id="_chkid"><span>${roomLock.NAME}</span></input>
							</c:if>
							<c:if test="${roomLock.IS_CHECKED eq '1' && ROOM_TYPE eq 'D'}">
							<input type="checkbox" value="${roomLock.NODE_ID}" name="_chk" id="_chkid" checked><span>${roomLock.NAME}</span></input>
							</c:if>
							<!-- 不够四列的行用空列补齐 -->  
							<c:if test="${stat.last && stat.count%4 != 0}">  
							<c:set value="${4-(stat.count%4)}" var="restTd"/>                                    
							<c:forEach var="x" begin="1" end="${restTd}" step="1">  
							<td width="25%"></td>  
							</c:forEach>  
							</c:if>  
							<!-- 4列换一行 -->
							<c:if test="${stat.count%4==0 && stat.count%8==0}">
								</tr>
								<tr class="odd">
							</c:if>
							<c:if test="${stat.count%4==0 && stat.count%8!=0}">
								</tr>
								<tr class="even">
							</c:if>
							</td>
		                 </c:forEach>  
		                </tr>     
					</table>
				</form>
				</div>
				<div class="auth_color" style="display:none;text-align: center;">
					<span class="wizard_grbne ok"></span>
					<span>授权成功</span>
					<span class="wizard_grbne ng"></span>
					<span>授权失败</span>
				</div>
				<div   id="confirmBtn">
					<table align="left" border="0" style="width:100%;height:30px;">
						<tr>
							<td  style="text-align:right;">
								<c:if test="${ROOM_TYPE == 'R'}">
									<a href="javascript:void(0);" class="buttonFinish"  style="float:right;margin-right:10px"  onclick="cancleWindow();">取消</a>
								</c:if>
								<a href="javascript:void(0);" class="buttonFinish"  style="float:right;margin-right:10px" onclick="closeWindow();">确定</a>
								
							</td>
							
								
						</tr>
					</table>
				</div>
				<br>
				<br>
				
				
				<div id="completeBtn" style="display:none;">
					<a href="javascript:void(0);" class="buttonFinish" onclick="closeWindowForDoor();">完成</a>
				</div>
			</body>
</html>

