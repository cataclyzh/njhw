<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: zh
- Date: 2013-4-7
- Description: 通讯录分组
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>通讯录分组</title>
	<%@ include file="/common/include/meta.jsp" %>
	
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
	<script src="${ctx}/app/integrateservice/js/personalSet.js" type="text/javascript"></script>
	<link href="${ctx}/app/integrateservice/css/personalSet.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/app/integrateservice/css/table.css" type="text/css" rel="stylesheet"  />
	
	<link href="${ctx}/app/integrateservice/css/tongxunlu.css" type="text/css" rel="stylesheet"  />
</head>
<body style="height:100%;width:100%;" class="easyui-layout">
<form id="queryForm" action="init.act" method="post"  autocomplete="off">
	<div class="tongxunlu_main">
        <div class="tongxunlu_main_top">
        
        <div class="tongxunlu_tools">
            <div style="cursor: pointer;" class="tongxunlu_top_btn4" onclick="addPerson()">增加联系人</div>
            <div style="cursor: pointer;" class="tongxunlu_top_btn5" onclick="delPerson()">删除联系人</div>
            <div style="cursor: pointer;" class="tongxunlu_top_btn6" onclick="editPerson()">编辑联系人</div>
            
            <div style="cursor: pointer;" class="tongxunlu_top_btn1" onclick="addGroup()">添加组</div>
            <div style="cursor: pointer;" class="tongxunlu_top_btn2" onclick="delGroup()">删除组</div>
            <div style="cursor: pointer;" class="tongxunlu_top_btn3" onclick="editGroup()">编辑组</div>
            <div class="tongxunlu_right3_input">
				<input type="text" id="searchTJ" onfocus="inputFun(this, 0)" onblur="inputFun(this, 1)" value="请输入姓名或电话"  class="input_txt"/><input name="" class="input_button" type="button"  onclick="searchInfo()" id="search"/>
			</div>
        </div>
<!--<div name="main_top_btn7" class="main_top_btn7" onmouseover="btn_hover(this)" onmouseout="btn_out(this)"></div>-->
        </div>
        <div class="tongxunlu_conter_div">
        	<input type="hidden" name="GID" id="GID"/>
        	<input type="hidden" name="PID" id="PID"/>
            <div class="tongxunlu_main_main_left" id="main_main_left">
            	<div style="overflow-y:auto; overflow-x:hidden; height: 300px;">
                	<div id="showList"></div>
               	</div>
            </div>
            <div class="tongxunlu_main_main_right" style="overflow: hidden;">
            	<iframe frameborder="0"  name="ifrmOrgList" id="ifrmObjList" src="${ctx}/common/include/body.jsp" style="width:100%;height:100%;padding:0px;overflow:hidden;"></iframe>
            </div>
        </div>
    </div>
</form>
	<script type="text/javascript">
		$(function() {
			$("input :hidden").val("");
			loadMenu();
			loadAddByGId(null,0); //默认加载未分组数据
		});
		
		function loadMenu() {
	  		$.getJSON("${ctx}/app/paddressbook/loadGroupList.act", function(json){
		        $("#showList").children().remove();
		        $("#showList").append("<div name='main_top_btn8' class='main_main_right_div' onmouseover='list_hover(this,0)' onmouseout='list_hover(this,1)' onclick='loadAddByGId(null, 0)'><div class='main_main_right_img'></div><div class='main_main_right_span'><span>未分组</span>(<span id='noGroupNum'>0</span>)</div>");
		        // 分组
		        $.each(json.groupList, function(i) {
		        	var nagid = json.groupList[i].NAG_ID;
		        	var nagname = json.groupList[i].NAG_NAME.length >= 8 ? json.groupList[i].NAG_NAME.substring(0,7) : json.groupList[i].NAG_NAME;
		        	var groupNum = json.groupList[i].GROUP_NUM;
		        	$("#showList").append("<div class='main_main_right_div' onmouseover='list_hover(this, 0)' onmouseout='list_hover(this, 1)' onclick='loadAddByGId(this, "+nagid+")'><div class='main_main_right_img'></div><div class='main_main_right_span'><span>"+nagname+" (</span><span id='group_num_span_"+nagid+"'>"+groupNum+"</span><span>)</span></div></div>");
				});
				// 人员
		        $.each(json.personList, function(i) {
		        	$("#noGroupNum").text(json.personList.length);
		        	//var nuaid = json.personList[i].NUA_ID;
		        	//var nuaname = json.personList[i].NUA_NAME;
		        	//$("#showList").append("<div class='main_main_right_div' onmouseover='list_hover(this, 0)' onmouseout='list_hover(this, 1)' onclick='showPersonEdit(this, "+nuaid+")'><div class='main_main_right_img_person'></div><div class='main_main_right_span'><span>"+nuaname+"</span></div></div>");
				});
	        });	
	  	}
		
		function setSpanText(gid, num) {
			$("#group_num_span_"+gid).text(num);
		}
		
	    function loadAddByGId(dom ,gId) {		// 查看组内人员信息
	   		var domAll = document.getElementById("main_main_left").getElementsByTagName("div");
    		var len = domAll.length;
    		for(var i = 0; i < len; i++) {
	    		if(domAll[i].className == "main_main_right_div")
	    			domAll[i].style.background = "";
	    	}
	    	if (gId > 0) {
	    		if(dom.style) dom.style.background = "#ffe2a2";
	    	}
	    	
	    	$("#GID").val(gId);
	    	$("#PID").val("");
	    	var url = "contactList.act?gid="+gId;
	    	$("#ifrmObjList").attr("src",url);
	    }
	    
	    function showPersonEdit(dom ,nua_id){		// 编辑选定人员
	    	var domAll = document.getElementById("main_main_left").getElementsByTagName("div");
	    	var len = domAll.length;
	    	for(var i = 0; i < len; i++) {
	    		if(domAll[i].className == "main_main_right_div")
	    			domAll[i].style.background = "";
	    	}
	    	if(dom.style) dom.style.background = "#ffe2a2";
	    	
	    	$("#GID").val("");
	    	$("#PID").val(nua_id);
	    	var url = "${ctx}/app/paddressbook/addressInput.act?nuaId="+nua_id+"&isPopup=0&titleNum=edit";
	    	//$("#ifrmObjList").attr("src",url);
	    }
	    
	    function showPersonInfo(dom ,nua_id){		// 显示联系人信息
	    	var domAll = document.getElementById("main_main_left").getElementsByTagName("div");
	    	var len = domAll.length;
	    	for(var i = 0; i < len; i++) {
	    		if(domAll[i].className == "main_main_right_div")
	    			domAll[i].style.background = "";
	    	}
	    	if(dom.style) dom.style.background = "#ffe2a2";
	    	
	    	$("#GID").val("");
	    	$("#PID").val(nua_id);
	    	var url = "${ctx}/app/paddressbook/addressShow.act?nuaId="+nua_id;
	    	$("#ifrmObjList").attr("src",url);
	    }
	    
	    function addGroup(){
		    var url ="groupInput.act";	
			window.top.showIframe("新增分组信息", 584, 170, url);
			/* var url = "${ctx}/app/paddressbook/groupInput.act";
			//openEasyWin("winId","新增分组信息",url,"400","150",true);
			showShelter1('584','170',url); */
		}
		
		function editGroup(){
			var gid = $("#GID").val();
			if(gid =="0"){
				window.top.showMessageBox("该组不能编辑！");
				//easyAlert('提示信息','该组不能编辑','info');
				return ;
			}
			if (gid == "" || gid == "0") {
				window.top.showMessageBox("请选中要编辑的组！");
				//easyAlert('提示信息','请选中要编辑的组！','info');
			} else {
				var url = "groupInput.act?gid="+gid+"&gTitle=edit";
				showShelter1('584','170',url);
			}
		}
		
		function delGroup(){
			if($("#GID").val() =="0"){
				window.top.showMessageBox("该组不能删除！");
				//easyAlert('提示信息','该组不能删除','info');
				return ;
			}
			if ($("#GID").val() == "" || $("#GID").val() == "0") {
				window.top.showMessageBox("请选中要删除的组！");
				//easyAlert('提示信息','请选中要删除的组！','info');
			} else {
				window.top.showConfirmBox("确定删除？",function(){
						$('#queryForm').attr("action","groupDelete.act");
						$('#queryForm').submit();						
				});
				/* easyConfirm('提示', '确定删除？', function(r){
					if (r){
						$('#queryForm').attr("action","groupDelete.act");
						$('#queryForm').submit();
					}
				}); */
			}
	    }
	    
	    // 操作联系人
	    var frames = document.getElementById("ifrmObjList"); 
	    function delPerson() {
	    	if ($("#GID").val() != ""){
	    		frames.contentWindow.delRecord();
	    	} else {
	    		if ($("#PID").val() == "") {
	    			window.top.showMessageBox("请选中要删除的联系人！");
					//easyAlert('提示信息','请选中要删除的联系人！','info');
				} else {
					window.top.showConfirmBox("请确定删除？",function(){
						$('#queryForm').attr("action","addressDelete.act");
						$('#queryForm').submit();						
					});
					/* easyConfirm('提示', '确定删除？', function(r){
						if (r){
							$('#queryForm').attr("action","addressDelete.act");
							$('#queryForm').submit();
						}
					}) */;
				}
	    	}
	    }
	    
	    function addPerson() {
	    	if ($("#GID").val() != "0"){
	    		frames.contentWindow.addRecord();
	    	} else {
		    	var url = "contactEdit.act?gid=0&titleNum=add";				
				window.top.showIframe("新建联系人", 700, 500, url, function() {
					window.location.reload();
				});
	    		/* var url = "${ctx}/app/paddressbook/addressInput.act?gid=0&titleNum=add";
				//openEasyWin("winId","新增联系人",url,"600","355",true);
				showShelter1('744','536',url,"win"); */
	    	}
	    }
	    
	    function editPerson() {
	    	if ($("#GID").val() != ""){
	    		frames.contentWindow.updateRecord();
	    		//var url = "${ctx}/app/paddressbook/addressInput.act?nuaId="+$("#PID").val();
				//showShelter("600","370",url,"winId");
	    	} else {
	    	
		    	var url = "contactEdit.act?nuaId="+$("#PID").val()+"&titleNum=edit";
				window.top.showIframe("编辑联系人", 700, 500, url);
	    		//showPersonEdit(null, $("#PID").val());
	    		/* var url = "${ctx}/app/paddressbook/editressInput.act?nuaId="+$("#PID").val()+"&titleNum=edit";
				showShelter1("700","540",url,"winId"); */
	    	}
	    }
	</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>