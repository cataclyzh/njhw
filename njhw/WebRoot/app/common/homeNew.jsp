<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: jitm
- Date: 2011-02-27
--%>
<html>
<head>
<title>首页导航</title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>  
<script src="${ctx}/scripts/basic/common.js.gzip" type="text/javascript"></script>

<script src="${ctx}/scripts/juiportal/jquery.ui.core.js"></script>
<script src="${ctx}/scripts/juiportal/jquery.ui.widget.js"></script>
<script src="${ctx}/scripts/juiportal/jquery.ui.mouse.js"></script>
<script src="${ctx}/scripts/juiportal/jquery.ui.sortable.js"></script>
<script src="${ctx}/scripts/juiportal/jquery.ui.button.js"></script>
<script src="${ctx}/scripts/juiportal/json2.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/scripts/juiportal/jquery.ui.portal.css"/>

<script type="text/javascript">
var custom_json;
var flag = "${_loginname}";
$(function() {
	
	loadPortlet();
});


function loadPortlet(){
	loadBulletin();

	loadMessage();

	loadTaskList();
}

function clearPortlet(id){
    $('#portal_div_prototype #' + id).empty();
    $('#portal_div #' + id).empty();
}

function fillPortlet(id, content){
    $('#portal_div_prototype #' + id).append(content);
    $('#portal_div #' + id).append(content);
}

function trimString(text){
	var maxLength = 12;
	if(text != null){
        if(text.length > maxLength){
            return text.substring(0,maxLength) + "...";
        }
        else {
            return text;
        }
	}

	return "";
}



//加载工作清单
function loadTaskList(){
    $.getJSON("${ctx}/app/home/loadTaskList.act",
        function(data){
	        clearPortlet('taskList');
            $.each(data,function(i,k){
                var link = "${ctx}" + data[i].tasklink;
                var labName = data[i].labname;
                var taskName = data[i].taskname;
                var taskInfo = data[i].taskinfo;
                var refresh = data[i].refresh;
                
                var str = "<div style=\"width:90%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 12px;\">";
                str += "<span style=\"float: left;\">";
                str += "<img src=\"${ctx}/images/home_tasklist.png\" width=\"16\" height=\"16\" style=\"vertical-align: middle;\"></img>&nbsp;&nbsp;";
                if(link == ''){
                	str += taskName;
                }
                else if(link != ''){
                	var TaskList = "TaskList" + Math.random();
                	str += "<a href=\"#\" title='" + taskName + "' mainurl='"+ link + "' id='" + TaskList + "' style=\"text-decoration:none;color: blue;\" ";
                	if(refresh == "Y"){ //refresh list
                    	str += "onclick=\"javascript:showTaskList('" + labName + "','" + link + "',this,this.id);\">" + taskName + "</a>";
                    	str2 = "onclick=\"javascript:showTaskList('" + labName + "','" + link + "',this,this.id);\">";
                	
                	}
                	else {//default not refresh list
                		str += "onclick=\"javascript:showTaskListNoRefresh('" + labName + "','" + link + "',this,this.id);\">" + taskName + "</a>";
                		str2 = "onclick=\"javascript:showTaskListNoRefresh('" + labName + "','" + link + "',this,this.id);\">";
                	
                	}
                }
                str += "</span>";
                str += "<span style=\"float: right;\">";
                str += "<a href=\"#\"  title='" + taskName + "' mainurl='"+ link + "' id='" + TaskList + "' style=\"text-decoration:none;color: blue;\"" + str2 + taskInfo + "</a>";
                str += "</span>";
                str += "</div>";
                
                fillPortlet('taskList', str);
        });
    });
}


//加载公告
function loadBulletin(){
    $.getJSON("${ctx}/app/home/bulletinList.act",
        function(data){
            clearPortlet('bulletin');
            $.each(data,function(i){
                var id = data[i].msgid;
                var title = data[i].title;
                var content = data[i].content;
                
                var str = "<div style=\"width:90%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 12px;\">";
                str += "<span style=\"float: left;\">";
                str += "<img src=\"${ctx}/images/home_bulletin.png\" width=\"16\" height=\"16\" style=\"vertical-align: middle;\"></img>&nbsp;&nbsp;";
                str += "<a href=\"#\" style=\"text-decoration:none;color: blue;\" ";
                str += "title=\"" + title + "\" ";
            	str += "onclick=\"javascript:showBulletin('" + id + "');\">" + trimString(title) + "</a>";
                str += "</span>";
                str += "</div>";
                
                fillPortlet('bulletin', str);
            });
            var more = "<div style=\"width:90%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 14px;\">";
            more += "<span style=\"float: right;\">";
            more += "<a href=\"#\" id=\"AllBulletin\" style=\"text-decoration:none;color: blue;\" title=\"全部公告\" ";          
            more += "mainurl=\"${ctx}/common/bulletinMessage/msgBoardAction_query.act\"";
            more += "onclick=\"javascript:showAllBulletin(this, this.id);\">more&gt;&gt;</a>";
            more += "</span>";
            more += "</div>";

            fillPortlet('bulletin', more);
    });
}

//加载消息
function loadMessage(){
    $.getJSON("${ctx}/app/home/messageList.act",
        function(data){
    	    clearPortlet('message');
            $.each(data,function(i){  
                var id = data[i].msgid;
                var title = data[i].title;
                var content = data[i].content;
                
                var str = "<div style=\"width:90%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 12px;\">";
                str += "<span style=\"float: left;\">";
                str += "<img src=\"${ctx}/images/home_message.png\" width=\"16\" height=\"16\" style=\"vertical-align: middle;\"></img>&nbsp;&nbsp;";
                str += "<a href=\"#\" style=\"text-decoration:none;color: blue;\" ";
                str += "title=\"" + title + "\" ";
            	str += "onclick=\"javascript:showMessage('" + id + "');\">" + trimString(title) + "</a>";
                str += "</span>";
                str += "</div>";
                
                fillPortlet('message', str);
            });
            var more = "<div style=\"width:90%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 14px;\">";
            more += "<span style=\"float: right;\">";
            more += "<a href=\"#\" id=\"AllMessage\" style=\"text-decoration:none;color: blue;\" title=\"全部消息\" ";
            more += "mainurl=\"${ctx}/common/bulletinMessage/msgBoxAction_queryReceiverbox.act\"";
            more += "onclick=\"showAllMessage(this, this.id);\">more&gt;&gt;</a>";
            more += "</span>";
            more += "</div>";
            
            fillPortlet('message', more);
    });
}

function showMessage(id) {
	var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + id;
	openEasyWin("winId","消息内容", url, "700", "400", false, "refreshMessage");
}

//查看公告明细
function showBulletin(id){
	var url = "${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId=" + id;
	openEasyWin("winId","公告内容", url, "700", "400", false, "refreshBulletin");
}

//查看任务明细(关闭时刷新列表)
function showTaskList(taskname,tasklink,ent,id){
	//openEasyWin("tasklist_window",taskname,tasklink,"980","550",false,"refreshTaskList");
	window.parent.open_main2(ent, id)
}

//查看任务明细(关闭时不刷新列表)
function showTaskListNoRefresh(taskname,tasklink,ent,id){
	//openEasyWin("tasklist_window",taskname,tasklink,"980","550");
	window.parent.open_main2(ent, id)
}

//显示全部消息
function showAllMessage(ent, id){
	//addTabThis("收件箱", "${ctx}/common/bulletinMessage/msgBoxAction_queryReceiverbox.act", "4", "公共管理系统", "消息管理");
	window.parent.open_main2(ent, id)
}

//显示全部公告
function showAllBulletin(ent, id){

	//addTabThis("公告板", "${ctx}/common/bulletinMessage/msgBoardAction_query.act", "4", "公共管理系统", "消息管理");
	window.parent.open_main2(ent, id)
}



</script>
<style type="text/css">
body {
	font-size: 70.5%;
}
.info ul{margin:0;padding:0;list-style:none;}
.info img{border:0px solid #ccc;}
.info li{padding:2px;float:left;}
.info li .img{padding:3px;border:1px solid white;display:block;cursor:pointer;}
.info li .img:link,.info li .img:visited{border:1px solid #cbcbcb;}
.info li .img:hover{border:1px solid #cbcbcb;background:#f0f0f0;color:red;}
.info span{display:block;width:100%;clear:both;text-align:center;width: 60px;height: 14px;overflow: hidden;font-size: 12px;}
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">	
	<div id="portal_div_prototype">
		<div class="portlet" id="portlet1">
		    <button id="refreshBulletin" onclick="loadBulletin();" style="display:none;"></button>
			<div class="portlet-header">公告栏</div>
			<div class="portlet-content" id="bulletin">正在加载...</div>
		</div>
		<div class="portlet" id="portlet2">
		    <button id="refreshMessage" onclick="loadMessage();" style="display:none;"></button>
			<div class="portlet-header">消息</div>
			<div class="portlet-content" id="message">正在加载...</div>
		</div>		
		<div class="portlet" id="portlet4">
		    <button id="refreshTaskList" onclick="loadTaskList();" style="display:none;"></button>
			<div class="portlet-header">工作清单</div>
			<div class="portlet-content" id="taskList">正在加载...</div>	
		</div>		
	</div> 
</body>
</html>

