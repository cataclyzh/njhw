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
	
	$(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all").find(".portlet-header")
	.addClass("ui-widget-header ui-corner-all").prepend("<span class='ui-icon ui-icon-minusthick'></span>")
	.prepend("<span class='ui-icon ui-icon-closethick'></span>");

	$( ".portlet-header .ui-icon-minusthick" ).click(function() {
		$( this ).toggleClass( "ui-icon-minusthick" ).toggleClass("ui-icon-plusthick" );
		$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
	});
	$("#portal_div_prototype .portlet").hide();
	$("#portal_set_panel").css({  border:"2px solid #CCCCFF" }).hide();
	
	$(".portlet-header .ui-icon-closethick" ).click(function() {
		$("#portal_div").find( this ).parents( ".portlet:first" ).remove();
		
		var portlet_id = $( this ).parents( ".portlet:first" ).attr("id");
		//控制设置面板中的模块状态
		$("input[type='checkbox'][title='"+portlet_id+"']").attr("checked",false);
		$("input[type='checkbox'][title='"+portlet_id+"']").button("refresh");
		
		var portlets_map =
		$.map( custom_json.column1.portlet, function(n){
			if( n == portlet_id){
				return null;
			}
			return n;
		});
		custom_json.column1.portlet = portlets_map;
		var portlets_map = [[],[]];
		portlets_map[0] =
		$.map( custom_json.column2.portlet[0], function(n){
			if( n == portlet_id){
				return null;
			}
			return n;
		});
		portlets_map[1] =
		$.map( custom_json.column2.portlet[1], function(n){
			if( n == portlet_id){
				return null;
			}
			return n;
		});
		custom_json.column2.portlet = portlets_map;

		var portlets_map = [[],[],[]];
		portlets_map[0] =
		$.map( custom_json.column3.portlet[0], function(n){
			if( n == portlet_id){
				return null;
			}
			return n;
		});
		portlets_map[1] =
		$.map( custom_json.column3.portlet[1], function(n){
			if( n == portlet_id){
				return null;
			}
			return n;
		});
		portlets_map[2] =
		$.map( custom_json.column3.portlet[2], function(n){
			if( n == portlet_id){
				return null;
			}
			return n;
		});
		custom_json.column3.portlet = portlets_map;
		update_custom_json();
		
		//alert($( this ).parents( ".portlet:first" ).attr("id"));
	});
	
	
	//var username = $("#username").val();
	var custom_json_text = getCookie(flag);

	if (custom_json_text!=null && custom_json_text!="") {
		custom_json = JSON.parse(custom_json_text,null);
	} else  {
		custom_json = {"column1":{"custom":"","algin":"","portlet":[]},"column2":{"custom":"","algin":"","portlet": [[],[]]},"column3":{"custom":"","algin":"","portlet":[[],[],[]]}};
		custom_json.column3.custom = "y";
		custom_json.column3.algin = "center";
		$("#portal_div_prototype .portlet").each(function(i){
			if(i < 2){
				custom_json.column2.portlet[0].push(this.id);
				custom_json.column3.portlet[0].push(this.id);
			}else if(i < 4){
				custom_json.column2.portlet[1].push(this.id);
				custom_json.column3.portlet[1].push(this.id);
			} else {
				custom_json.column2.portlet[1].push(this.id);
				custom_json.column3.portlet[2].push(this.id);
			}
			custom_json.column1.portlet.push(this.id);
		});
	}
	show_custom();
	
	//显示定制面板
	$("#portal_set_btn").toggle(
	  function () {
		$("#portal_set_panel").slideDown("fast");
	  },
	  function () {
		$("#portal_set_panel").slideUp("fast");
	  }
	);
	//设置面板中的checkbox状态
	$( "input[type='checkbox']").attr("checked",false);
	$.each( custom_json.column1.portlet, function(i, n){
		
		$( "input[type='checkbox'][title='"+n+"']").attr("checked","checked");
	});
	
	//定制是否显示模块
	$( "input[type='checkbox']" ).button().click(function(){
		if($(this).attr("checked") == "checked" || $(this).attr("checked") == true){
			var portlet_id = $(this).attr("title");
			var portlet_temp = new Array(portlet_id);
			custom_json.column1.portlet = portlet_temp.concat(custom_json.column1.portlet);
			custom_json.column2.portlet[0] = portlet_temp.concat(custom_json.column2.portlet[0]);
			custom_json.column3.portlet[0] = portlet_temp.concat(custom_json.column3.portlet[0]);
			show_custom();
		} else {
			var portlet_id = $(this).attr("title");
			$( "#"+portlet_id+" .portlet-header .ui-icon-closethick" ).click();
		}
	});

	loadPortlet();
});


function loadPortlet(){
	
	loadShortcutButton();

	loadKnowledgeList();

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

//加载快捷按钮
function loadShortcutButton(){
    $.getJSON("${ctx}/app/home/loadShortcutButtonList.act",
        function(data){
    	    clearPortlet('shortcutBtn');
    	    
            $.each(data,function(i){
                var imgSrc = data[i].navigationImgSrc;
                var name = data[i].navigationName;
                var link = data[i].navigationLink;
                var subSysId = data[i].subsysid;
                var subSysName = data[i].subsysname;
                var accordionName = data[i].accordionname;
                
                var str = "<li>";
                str += "<div class='img'>";
                str += "<img src='${ctx}/" + imgSrc + "' title='" + name + "' width='60px', height='63px' ";
                str += "onclick='javascript:addTabThis(\"" + name + "\",\"" + link + "\", \"" + subSysId + "\", \"" + subSysName + "\", \"" + accordionName + "\")' ";
                str += "></img>";
                str += "<span title='" + data[i].navigationName + "'>" + (data[i].navigationName) + "</span>";
                str += "</li>";
                
                fillPortlet('shortcutBtn', str);
        });
    });
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
                	str += "<a href=\"#\" style=\"text-decoration:none;color: blue;\" ";
                	if(refresh == "Y"){ //refresh list
                    	str += "onclick=\"javascript:showTaskList('" + labName + "','" + link + "');\">" + taskName + "</a>";
                	}
                	else {//default not refresh list
                		str += "onclick=\"javascript:showTaskListNoRefresh('" + labName + "','" + link + "');\">" + taskName + "</a>";
                	}
                }
                str += "</span>";
                str += "<span style=\"float: right;\">";
                str += "<a href=\"#\" style=\"text-decoration:none;color: blue;\">" + taskInfo + "</a>";
                str += "</span>";
                str += "</div>";
                
                fillPortlet('taskList', str);
        });
    });
}

//加载知识列表
function loadKnowledgeList(){
    $.getJSON("${ctx}/app/home/knowledgeList.act",
        function(data){
            clearPortlet('knowledge');

            $.each(data,function(i){ 
                var id = data[i].knowledgeId;
                var code = data[i].code;
                var subject = data[i].subject;
                
                var str = "<div style=\"width:90%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 12px;\">";
                str += "<span style=\"float: left;\">";
                str += "<img src=\"${ctx}/images/home_knowledge.png\" width=\"16\" height=\"16\" style=\"vertical-align: middle;\"></img>&nbsp;&nbsp;";
                str += "<a href=\"#\" style=\"text-decoration:none;color: blue;\" ";
                str += "title=\"" + subject + "\" ";
            	str += "onclick=\"javascript:showKnowledge('" + id + "');\">" + trimString(subject) + "</a>";
                str += "</span>";
                str += "</div>";

                fillPortlet('knowledge', str);
            });
            var more = "<div style=\"width:90%;position:relative;left: 10px;height: 22px;border-bottom: 1px solid gainsboro;font-size: 14px;\">";
            more += "<span style=\"float: right;\">";
            more += "<a href=\"#\" style=\"text-decoration:none;color: blue;\" title=\"点击查看全部知识\" ";
            more += "onclick=\"javascript:showAllKnowledge();\">more&gt;&gt;</a>";
            more += "</span>";
            more += "</div>";
            
            fillPortlet('knowledge', more);
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
            more += "<a href=\"#\" style=\"text-decoration:none;color: blue;\" title=\"点击查看全部公告\" ";
            more += "onclick=\"javascript:showAllBulletin();\">more&gt;&gt;</a>";
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
            more += "<a href=\"#\" style=\"text-decoration:none;color: blue;\" title=\"点击查看全部消息\" ";
            more += "onclick=\"javascript:showAllMessage();\">more&gt;&gt;</a>";
            more += "</span>";
            more += "</div>";
            
            fillPortlet('message', more);
    });
}

function showMessage(id) {
	var url = "${ctx}/common/bulletinMessage/msgBoxAction_detailReciverbox.act?msgId=" + id;
	openEasyWin("winId","消息内容", url, "700", "600", false, "refreshMessage");
}

//查看公告明细
function showBulletin(id){
	var url = "${ctx}/common/bulletinMessage/msgBoardAction_detail.act?msgId=" + id;
	openEasyWin("winId","公告内容", url, "700", "400", false, "refreshBulletin");
}

//查看知识明细
function showKnowledge(id){
	var url = "${ctx}/common/knowledge/detail.act?knowledgeId=" + id;
	openEasyWin("knowledgeDetail","知识内容", url, "800", "550");
}

//查看任务明细(关闭时刷新列表)
function showTaskList(taskname,tasklink){
	openEasyWin("tasklist_window",taskname,tasklink,"980","550",false,"refreshTaskList");
}

//查看任务明细(关闭时不刷新列表)
function showTaskListNoRefresh(taskname,tasklink){
	openEasyWin("tasklist_window",taskname,tasklink,"980","550");
}

//显示全部消息
function showAllMessage(){
	addTabThis("收件箱", "${ctx}/common/bulletinMessage/msgBoxAction_queryReceiverbox.act", "4", "公共管理系统", "消息管理");
}

//显示全部公告
function showAllBulletin(){
	addTabThis("公告板", "${ctx}/common/bulletinMessage/msgBoardAction_query.act", "4", "公共管理系统", "消息管理");
}

//显示全部知识
function showAllKnowledge(){
	addTabThis("知识查询", "${ctx}/common/knowledge/initQuery.act", "4", "公共管理系统", "知识管理");
}

//恢复默认设置
function resetPortal(){
	setCookie(flag, "");
	this.location.reload();
}

function update_custom_json(){
	var custom_json_text =  JSON.stringify(custom_json, null, 3);
	//alert(custom_json_text);
	setCookie(flag,custom_json_text,365);
}
function show_custom(){
	$("#portal_div").children().remove();
	if(custom_json.column1.custom == "y"){
		var column1 = $("#portal_div").append("<div class='column' id='column1' style='width:100%'></div>");
		var portlets = custom_json.column1.portlet;
		$.each( portlets, function(i, n){
		   $("#portal_div_prototype #"+portlets[i]+"").clone(true).show().appendTo("#column1");
		});
		$("#btn_layout1").addClass("btn-md");
	}
	if(custom_json.column2.custom == "y"){
		var width1 = 50;
		var width2 = 50;
		if(custom_json.column2.algin == "left"){
			width1 = 40;
			width2 = 60;
			$("#btn_layout2_l").addClass("btn-md");
		}
		if(custom_json.column2.algin == "right"){
			width1 = 60;
			width2 = 40;
			$("#btn_layout2_r").addClass("btn-md");
		}
		if(custom_json.column2.algin == ""){
			$("#btn_layout2").addClass("btn-md");
		}
		var column1 = $("#portal_div").append("<div class='column' id='column1' style='width:"+width1+"%'></div>");
		var column2 = $("#portal_div").append("<div class='column' id='column2' style='width:"+width2+"%'></div>");
		
		if(custom_json.column2.portlet[0].join() != "" || custom_json.column2.portlet[1].join() != ""){
			
		} else {
			custom_json.column2.portlet[0] = custom_json.column1.portlet;
		}
		var portlet0 = custom_json.column2.portlet[0];
		if(portlet0 != null && portlet0.join() != ""){
			$.each( portlet0, function(i, n){
			   $("#portal_div_prototype #"+portlet0[i]+"").clone(true).show().appendTo("#column1");
			});
		}
		var portlet1 = custom_json.column2.portlet[1];
		if(portlet1 != null && portlet1.join() != ""){
			$.each( portlet1, function(i, n){
			   $("#portal_div_prototype #"+portlet1[i]+"").clone(true).show().appendTo("#column2");
			});
		}
		
	}
	if(custom_json.column3.custom == "y"){
		var width1 = 33;
		var width2 = 33;
		var width3 = 33;
		if(custom_json.column3.algin == "center"){
			width1 = 20;
			width2 = 60;
			width3 = 19;
			$("#btn_layout3_c").addClass("btn-md");
		}
		if(custom_json.column3.algin == ""){
			$("#btn_layout3").addClass("btn-md");
		}
		var column1 = $("#portal_div").append("<div class='column' id='column1' style='width:"+width1+"%'></div>");
		var column2 = $("#portal_div").append("<div class='column' id='column2' style='width:"+width2+"%'></div>");
		var column3 = $("#portal_div").append("<div class='column' id='column3' style='width:"+width3+"%'></div>");
		
		if(custom_json.column3.portlet[0].join() != "" || custom_json.column3.portlet[1].join() != "" || custom_json.column3.portlet[2].join() != ""){
			
		} else {
			if(custom_json.column2.portlet[0].join() != "" && custom_json.column2.portlet[1].join() != ""){
				custom_json.column3.portlet[0] = custom_json.column2.portlet[0];
				custom_json.column3.portlet[1] = custom_json.column2.portlet[1];
			} else {
				custom_json.column3.portlet[0] = custom_json.column1.portlet;
			}
			
		}
		var portlet0 = custom_json.column3.portlet[0];
		if(portlet0 != null && portlet0.join() != ""){
			$.each( portlet0, function(i, n){
			   $("#portal_div_prototype #"+portlet0[i]+"").clone(true).show().appendTo("#column1");
			});
		}
		var portlet1 = custom_json.column3.portlet[1];
		if(portlet1 != null && portlet1.join() != ""){
			$.each( portlet1, function(i, n){
			   $("#portal_div_prototype #"+portlet1[i]+"").clone(true).show().appendTo("#column2");
			});
		}
		var portlet2 = custom_json.column3.portlet[2];
		if(portlet2 != null && portlet2.join() != ""){
			$.each( portlet2, function(i, n){
			   $("#portal_div_prototype #"+portlet2[i]+"").clone(true).show().appendTo("#column3");
			});
		}
	}
	set_sortable();
	update_custom_json();
}
function setWidgetLayout(count,align,btn){
	if($(btn).attr("class").indexOf("btn-md")>0){
		return;
	}
	$(".btn-icon").filter(".btn-md").removeClass("btn-md");
	//$(btn).addClass("btn-md");
	custom_json.column1.custom="";
	custom_json.column2.custom="";
	custom_json.column3.custom="";
	switch(count){
		case 1:
			custom_json.column1.custom = "y";
			break;
		case 2:
			custom_json.column2.algin = align;
			custom_json.column2.custom = "y";
			break;
		case 3:
			custom_json.column3.algin = align;
			custom_json.column3.custom = "y";
			break;
	}
	show_custom();
}
function set_sortable (){
	$( ".column" ).sortable({
		connectWith: ".column",
		handle:".portlet-header" 
	});
	$( ".column" ).sortable({
	   stop: function(event, ui) { 
			var portlet_id = ui.item.attr("id");
			var portlet_parent_id = ui.item.parent().attr("id");
			var portlet_next_id = ui.item.next().attr("id");
			var portlet_prev_id = ui.item.prev().attr("id");
			
			if(custom_json.column1.custom == "y"){
				var portlets = custom_json.column1.portlet;
				var portlets_map = 
					$.map( portlets, function(n){
						if(portlet_next_id != "" && portlet_next_id != null){
							if( n == portlet_next_id){
								return [ portlet_id, n];
							}
						} else {
							if( n == portlet_prev_id){
								return [n,portlet_id];
							}
						}
						if( n == portlet_id){
							return null;
						}
						return n;
					});					 
				custom_json.column1.portlet = portlets_map;
			}
			if(custom_json.column2.custom == "y"){
				var portlets = custom_json.column2.portlet;
				var portlets_map = [[],[]];
				if(portlet_parent_id == "column1"){
					if(portlets[0] == null || portlets[0].length == 0){
						portlets_map[0].push(portlet_id);
					} else {
						portlets_map[0] = 
						$.map( portlets[0], function(n){
							if(portlet_next_id != "" && portlet_next_id != null){
								if( n == portlet_next_id){
									return [ portlet_id, n];
								}
							} else {
								if( n == portlet_prev_id){
									return [n,portlet_id];
								}
							}
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if(portlets[1] != null){
						portlets_map[1] = 
						$.map( portlets[1], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
				}
				if(portlet_parent_id == "column2"){
					if(portlets[1] == null || portlets[1].length == 0){
						portlets_map[1].push(portlet_id);
					} else {
						portlets_map[1] = 
						$.map( portlets[1], function(n){
							if(portlet_next_id != "" && portlet_next_id != null){
								if( n == portlet_next_id){
									return [ portlet_id, n];
								}
							} else {
								if( n == portlet_prev_id){
									return [n,portlet_id];
								}
							}
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if(portlets[0] != null){
						portlets_map[0] = 
						$.map( portlets[0], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
				}
				custom_json.column2.portlet = portlets_map;
			}
			if(custom_json.column3.custom == "y"){
				var portlets = custom_json.column3.portlet;
				var portlets_map = [[],[],[]];
				if(portlet_parent_id == "column1"){
					if(portlets[0] == null || portlets[0].length==0){
						portlets_map[0].push(portlet_id);
					} else {
						portlets_map[0] = 
						$.map( portlets[0], function(n){
							if(portlet_next_id != "" && portlet_next_id != null){
								if( n == portlet_next_id){
									return [ portlet_id, n];
								}
							} else {
								if( n == portlet_prev_id){
									return [n,portlet_id];
								}
							}
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if(portlets[1] != null){
						portlets_map[1] = 
						$.map( portlets[1], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if(portlets[2] != null){
						portlets_map[2] = 
						$.map( portlets[2], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
				}
				if(portlet_parent_id == "column2"){
					if(portlets[1] == null || portlets[1].length == 0){
						portlets_map[1].push(portlet_id);
					} else {
						portlets_map[1] = 
						$.map( portlets[1], function(n){
							if(portlet_next_id != "" && portlet_next_id != null){
								if( n == portlet_next_id){
									return [ portlet_id, n];
								}
							} else {
								if( n == portlet_prev_id){
									return [n,portlet_id];
								}
							}
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if(portlets[0] != null){
						portlets_map[0] = 
						$.map( portlets[0], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if(portlets[2] != null) {
						portlets_map[2] = 
						$.map( portlets[2], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});
					}
				}
				if(portlet_parent_id == "column3"){
					//数组问题，必须加上或条件
					if(portlets[2] == null || portlets[2].length == 0){
						portlets_map[2].push(portlet_id);
					} else {
						portlets_map[2] = 
						$.map( portlets[2], function(n){
							if(portlet_next_id != "" && portlet_next_id != null){
								if( n == portlet_next_id){
									return [ portlet_id, n];
								}
							} else {
								if( n == portlet_prev_id){
									return [n,portlet_id];
								}
							}
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if(portlets[0] != null) {
						portlets_map[0] = 
						$.map( portlets[0], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});	
					}
					if (portlets[1] != null){
						portlets_map[1] = 
						$.map( portlets[1], function(n){
							if( n == portlet_id){
								return null;
							}
							return n;
						});
					}
				}
				custom_json.column3.portlet = portlets_map;
			}
			//var portlets_map_text = JSON.stringify(custom_json.column3.portlet, null, 0);
			//$("#test").text(portlets_map_text);
			update_custom_json();
		}
	});
}
function setCookie(c_name,value,expiredays)	{
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function getCookie(c_name){
	if (document.cookie.length>0) {
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1){ 
			c_start=c_start + c_name.length+1 ;
			c_end=document.cookie.indexOf(";",c_start);
			if (c_end==-1) 
			c_end=document.cookie.length;
			return unescape(document.cookie.substring(c_start,c_end));
		}
	}
	return "";
}
function addTabThis(navigationName, navigationLink,subsysid,subsysname,accordionname) {
	window.top.navigation_click(navigationName, navigationLink,subsysid,subsysname,accordionname);
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
	<div class="select-operation">
		<div class="operation">
			<a class="btn-icon btn" id="portal_set_btn"><span class="add-btn"></span>门户定制</a>
		</div>
		<div class="operation">
			<div class="btn-icon btn-l" id="btn_layout1" onclick="setWidgetLayout(1, '',this)">
				<span class="layout-1"></span>
			</div>
			<div class="btn-icon btn-c" id="btn_layout2_l" onclick="setWidgetLayout(2, 'left',this)">
				<span class="layout-2-left"></span>
			</div>
			<div class="btn-icon btn-c" id="btn_layout2" onclick="setWidgetLayout(2, '',this)">
				<span class="layout-2"></span>
			</div>
			<div class="btn-icon btn-c" id="btn_layout2_r" onclick="setWidgetLayout(2, 'right',this)">
				<span class="layout-2-right"></span>
			</div>
			<div class="btn-icon btn-c" id="btn_layout3_c" onclick="setWidgetLayout(3, 'center',this)">
				<span class="layout-3-center"></span>
			</div>
			<div class="btn-icon btn-r" id="btn_layout3" onclick="setWidgetLayout(3, '',this)">
				<span class="layout-3"></span>
			</div>
		</div>
		<div class="operation">
			<a class="btn-icon btn" id="portal_reset_btn" onclick="javascript:resetPortal();"><span class="add-btn"></span>恢复默认设置</a>
		</div>
	</div>
	<div id="portal_set_panel">
		<input type="checkbox" id="check" title="portlet1" /><label for="check">公告栏</label> 
		<input type="checkbox" id="check2" title="portlet2" /><label for="check2">消息</label> 
		<input type="checkbox" id="check3" title="portlet3" /><label for="check3">快捷按钮</label> 
		<input type="checkbox" id="check4" title="portlet4" /><label for="check4">工作清单</label> 
		<input type="checkbox" id="check5" title="portlet5" /><label for="check5">知识中心</label>
	</div>
	<div id="portal_div">		
	</div>
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
		<div class="portlet" id="portlet3">
			<div class="portlet-header">快捷按钮</div>
			<div class="portlet-content info">
				<ul id="shortcutBtn">正在加载...</ul>
			</div>
		</div>
		<div class="portlet" id="portlet4">
		    <button id="refreshTaskList" onclick="loadTaskList();" style="display:none;"></button>
			<div class="portlet-header">工作清单</div>
			<div class="portlet-content" id="taskList">正在加载...</div>	
		</div>
		<div class="portlet" id="portlet5">
		    <button id="refreshKnowledge" onclick="loadKnowledgeList();" style="display:none;"></button>
			<div class="portlet-header">知识中心</div>
			<div id="knowledge" class="portlet-content" style="font-size: 12px;">正在加载...</div>
		</div>
	</div> 
</body>
</html>
<s:actionmessage theme="custom" cssClass="success" />
