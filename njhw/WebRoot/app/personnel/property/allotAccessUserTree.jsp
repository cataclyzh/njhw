<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: hj
- Date: 2010-09-02 13:26:22
- Description: 管理机构及人员树页面
--%>

<style>
<!--
.icon-xbjbutton{
	background:url('${ctx}/styles/images/xbj.gif') no-repeat;
	background-color: Transparent;
	width:46;
	height: 22;
	border:none;
	font-size:12px;
	color:#585858;
	padding:5px 5px;margin:0;outline:0;
	overflow:visible;vertical-align:top;cursor:pointer;
}
-->
</style>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>机构及人员树</title>	
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/scripts/widgets/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>	
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			changebasiccomponentstyle();   
		});		
	</script>
</head>
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:auto;background: #fff;'>
 <s:hidden name="orgId"/>
 <s:hidden name="roomId"/>
 <s:hidden name="dtype"/>
 <s:hidden name="checkedIds"/>
 <table align="center" border="0" width="100%" class="form_table">
 	  <tr class="form_bottom" width="90%">
        <td width="30%"> 
        	<a href="javascript:void(0);" id="selectBtn" class="easyui-linkbutton" onclick="selectUser();">选择</a>
        </td>
        <td>
        	<a href="javascript:void(0);" id="searchBtn" class="easyui-linkbutton" style="float:right;" iconCls="icon-search" onclick="finduser();">搜索</a>
        	<input type="text" id="displayName" style="width:141px;inline-height:22px;float:right;margin-right:10px;" onkeypress="return searchBtn(event)"/>
        </td>
      </tr>
      <tr>
        <td colspan="2">
           <div id="divTree" style="border:0px;height:280px;width:370px;"></div>
        </td> 
      </tr>
</table>

<script type="text/javascript">
	var menu;
	//初始化menu---------------------------------------------------------////
	menu=new dhtmlXMenuObject();
	menu.setImagePath("${ctx}/scripts/widgets/dtree/images/DhtxMenu/");
	menu.setIconsPath("${ctx}/scripts/widgets/dtree/images/MenuIcon/");
	menu.renderAsContextMenu();
	menu.attachEvent("onClick",HandleMClk);
	menu.loadXML("${ctx}/scripts/widgets/dtree/data/dhtmlxmenu.xml"); 
	var tree;
	//初始化tree----------------------------------------------------------////
	tree=new dhtmlXTreeObject("divTree","100%","100%",0);
	tree.setImagePath("${ctx}/scripts/widgets/dtree/images/DhtxTree/default/");
	tree.enableHighlighting(true);
	
	//绑定时间处理函数,注意一定要放在loadXML之前否则右键处理函数在ie下失效
//	tree.enableContextMenu(menu);
	tree.attachEvent("onClick",HandleTreeClk);
	//tree.setOnClickHandler(function(id){openPath(id);});
	//tree.enableCheckBoxes(1);
	//tree.enableThreeStateCheckboxes(true);
	tree.loadXML("${ctx}/app/per/allotAccessUserTreeData.act?", function() {
		if (${not empty userid}) {
			tree.selectItem("${userid}" + "-u");
			var position = $("#treeNode_"+"${userid}" + "-u").position();
          	$(".containerTableStyle").scrollTop(position.top-10);
		}
	});

	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
		if(menuid=="muItem_DELETE"){
			tree.deleteItem(tree.contextID,true);
		}else if(menuid=="muItem_EDIT"){
			
		}else if(menuid=="muItem_ADD"){
			
		}else if(menuid=="muItem_REFRESH"){
			
		}
	}

	//树事件处理函数------------------------------------------------------////
	function HandleTreeClk(sId,sPreId){
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
	}

	function selectUser(){ 	
		var selectedNode = tree.getSelectedItemId();
		if (selectedNode=="") {
			easyAlert('提示信息','请先选择人员！','warning');
			return;
		}else{
			
			if (selectedNode.indexOf("-u")<0) {
				easyAlert('提示信息','请先选择人员！','warning');
				return;
			}
		}
		var name = tree.getItemText(selectedNode);
	    selectedNode = selectedNode.substring(0,selectedNode.indexOf("-u"));
	    var url = "${ctx}/app/per/json/initUserAccessInfo.act";
		var sucFun = function (json){
			var map = json.map;
			if (map.error == "error") {
				easyAlert("提示信息","该人员为黑名单或者未开卡，无法授予门禁权限！","info");
			} else if (map.error == "repeat") {
				easyAlert("提示信息","该人员存在正在申请中的门禁授权，无法重复申请！","info");
			} else {
				top.aiFrame.$('#accessIds').val(map.idsA);
				top.aiFrame.$('#guardIds').val(map.idsG);
				top.aiFrame.$('#access').val(map.access);
				top.aiFrame.$('#guard').val(map.gate);
				if ("1" == map.opt) {
					top.aiFrame.$('#opt').val(map.opt);
					top.aiFrame.$('#optName').val("新增");
				} else {
					top.aiFrame.$('#opt').val("2");
					top.aiFrame.$('#optName').val("更新");
				}
				top.aiFrame.$('#userid').val(selectedNode);
				top.aiFrame.$('#name').val(name);
				window.top.$("#ryxz").window("close");
			}
		};
		var errFun = function (){
			alert("初始化人员门禁闸机信息失败!");
		};
		var data = {userid: selectedNode};
		$.ajax({
			url : url,
			data: data,
			dataType : 'json',//返回值类型 一般设置为json
			success : sucFun,
			error : errFun,
			async : false
		});
	}
	
	function searchBtn(e) {
		if (window.event) {
            keynum = e.keyCode
            if (keynum == 13) {
            	event.returnvalue=false;
                $("#searchBtn").click();
                return false;
            }
        }
	}
	
	 function finduser()
	 {  
	 	var displayName = $.trim(document.getElementById("displayName").value);

	 	tree.closeAllItems();
	 	$(".containerTableStyle").scrollTop(0);
	 	$(".containerTableStyle").css("overflow-x", "hidden");
        //获取所有无子节点的节点id
        var itemArry =tree.getAllChildless().split(',');
        tree.openItem("${orgIdForSelect}" + "-o");
        
        if (displayName == "") {
	 		return false;
	 	}
        for(var i=0;i<itemArry.length;i++)
        {  
          	var itemtext = tree.getItemText(itemArry[i]);
          
          	if(itemtext.indexOf(displayName) != -1)
          	{ 
          	  var node = $("#treeNode_"+itemArry[i]);
          	  if (node.attr("searched") != "0") {
			    var parItemId=tree.getParentId(itemArry[i]);
			    // 打开父节点
          	    tree.openItem(parItemId);
          	    tree.selectItem(itemArry[i]);
          	    node.attr("searched", "0");
          	    var position = $("#treeNode_"+itemArry[i]).position();
          	    $(".containerTableStyle").scrollTop(position.top-10);
          	    return false;
          	  }
			}
		  }
		var flag = false;
		for(var i=0;i<itemArry.length;i++)
        {  
          	var itemtext = tree.getItemText(itemArry[i]).replace(/(^\s*)|(\s*$)/g,'');
          
          	if(!flag && itemtext.indexOf(displayName) != -1)
          	{ 
			  var parItemId=tree.getParentId(itemArry[i]);
			  // 打开父节点
          	  tree.openItem(parItemId);
          	  tree.selectItem(itemArry[i]);
          	  var node = $("#treeNode_"+itemArry[i]);
          	  var position = $("#treeNode_"+itemArry[i]).position();
          	  $(".containerTableStyle").scrollTop(position.top-10);
          	  flag = true;
			} else if (itemtext.indexOf(displayName) != -1) {
			  var node = $("#treeNode_"+itemArry[i]);
			  node.removeAttr("searched");
			}
		  }
     }

</script>
</body>
</html>