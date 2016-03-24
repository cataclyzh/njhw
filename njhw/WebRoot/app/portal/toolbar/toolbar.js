
/**
* 修改密码
*/
function openPwdDiv(){
	var addUrl = _ctx+"/common/userOrgMgr/personInfoChreach.act";
	openEasyWin("winId","个人信息维护",addUrl,"700","450");
}
/**
* 退出功能系统
*/
function edit()
{
	var url = _ctx + "/j_spring_security_logout" + '?t=' + Math.random();
	//var url = _ctx + "/j_spring_cas_security_logout" + '?t=' + Math.random();
	window.open(url, "_self");
}
/**
*更新工作清单
*/
function refreshTaskList(){
    $.getJSON(_ctx + "/app/home/loadTaskList.act",
        function(data){
	        $('#user_task_list_content').empty();
	        var totalMsg = 0;
            $.each(data,function(i,k){
                var link = data[i].tasklink;
                var labName = data[i].labname;
                var taskName = data[i].taskname;
                var taskInfo = data[i].taskinfo;
                var refresh = data[i].refresh;
                totalMsg =  parseInt(totalMsg) + parseInt(taskInfo);
                var str = "<div style=\"width:90%;position:relative;margin-top:4px;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 12px;\">";
                str += "<span style=\"float: left;\">";
                str += "<img src=\""+ _ctx + "/images/home_tasklist.png\" width=\"16\" height=\"16\" style=\"vertical-align: middle;margin-right:5px;float:left\"></img>&nbsp;&nbsp;";
                if(link == ''){
                	str += taskName;
                }
                else if(link != ''){
                	var TaskList = "TaskList" + Math.random();
                	str += "<a href=\"#\" title='" + taskName + "' mainurl='"+ link + "' id='" + TaskList + "' style=\"text-decoration:none;float:left;color: blue;\" ";
                	if(refresh == "Y"){ //refresh list
                    	str += "onclick=\"javascript:showWindowTaskList('" + labName + "','" + link + "',this,this.id);\">" + taskName + "</a>";
                    	str2 = "onclick=\"javascript:showWindowTaskList('" + labName + "','" + link + "',this,this.id);\">";
                	} else {//default not refresh list
                		str += "onclick=\"javascript:showWindowTaskList('" + labName + "','" + link + "',this,this.id);\">" + taskName + "</a>";
                		str2 = "onclick=\"javascript:showWindowTaskList('" + labName + "','" + link + "',this,this.id);\">";
                	}
                }
                str += "</span>";
                str += "<span style=\"float: right;\">";
                str += "<a href=\"#\"  title='" + taskName + "' mainurl='"+ link + "' id='" + TaskList + "' style=\"text-decoration:none;color: blue;\"" + str2 + taskInfo + "</a>";
                str += "</span>";
                str += "</div>";
                if ($.trim(str)=="") {
                	
                	str = "您当前没有任务!";
                }
                $('#user_task_list_content').append(str);
                
                if (null != totalMsg && totalMsg > 0 ){
		        	$("#msg_task_size span").html(totalMsg)
		        	$("#msg_task_size").show();
		        	$("#footer_btn5").show();
		        	$("#footer_btn5_user").hide();
		        } else {
		        	$("#msg_task_size span").html(0)
		        	$("#msg_task_size").hide();
		        	$("#footer_btn5_user").show();
		        	$("#footer_btn5").hide();
		        }
        });
    });
}
/**
* 在主页页面查看任务
*/
function showWindowTaskList(label,link,obj,aId){
	if (label == '我的来访') {
		var url = _ctx+"/app/myvisit/queryPopup.act";
		showShelter('950','600',url)
	} else if (label == '我的派单' || label == '报修清单') {
		var url = _ctx+"/app/portal/index.act?id="+Math.random()+"&title="+label+"&url="+_ctx+link;//+_ctx+"/app/pro/init1.act";
		//window.location.href = url;
		var id = "";
		var title = label;
		var linkUrl = _ctx+link;
		if (label == '报修清单') {
			id = "10093";
		} else {
			id = "10149";
		}
		if (id != "" && title != "" && linkUrl != "") {
			open_main3(id,title,linkUrl+"?isIndexPage=y");
		}
		
	}
}

/*
* 如果有任务弹出任务窗口
*/
function showTaskWindow(){
	if (parseInt($("#msg_task_size span").html()) > 1){
		 playWin("open");
	}
}

function playWin(type){
	if (type=='close'){
		$('#user_task_list').hide();
		$('#user_task_list_iframe').hide();
	} else if (type=='open'){
		$('#user_task_list').show();
		$('#user_task_list_iframe').show();
	}
}