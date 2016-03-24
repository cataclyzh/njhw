<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源管理资源树页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>资源树</title>	 
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
	
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/constants.js" type="text/javascript"></script>
	
</head>
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:HIDDEN;background: #f1f8fd;'>
<div style="height:100%;overflow:auto;width:100%;">
	<table style="width:100%;height:95%;" ID="Table1">
	<tr style="height:100%;">
		<td style="width:276px;height:100%;" valign=top>
			<table style="width:266px;"cellspacing="0" cellpadding="0" border="0" ID="Table2">
				<tr id="bar_td" class="ibackground top_tr"> 
					<td>
						<span style="font-size:12px;padding-left:5px">展示:</span>
						<select id="display_type" NAME="perm_code_s" onchange="javascript:changeDisplay();" title="查询">
							<option selected value="html">
								列表展示
							</option>
							<option value="sw">
								三纬展示
							</option>
						</select>
					</td>
				</tr>
				<tr class="iframe_tr">
					<td colspan=7>
						<!--FF下 iframe的边框占用了一个像素-->
						<div id="divTree" style="border:0px;overflow:hidden;"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
	//当前选中的资源id
	var curItemId;
	//B,F
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
	tree.setOnClickHandler(function(id){openPath(id);});
	tree.setDragHandler();//设置树结点拖动
	tree.enableDragAndDrop(true) //设置树结点是否可拖动
	tree.setDragHandler(myDragHandler); //设置树结点拖动时所执行的方法
	tree.setXMLAutoLoading("${ctx}/app/buildingmon/objTreeData.act?type=open&floor=${floor}&actionName=${actionName}");
	tree.loadXML("${ctx}/app/buildingmon/objTreeData.act?type=init&id="+_buildingRootID+"&floor=${floor}&actionName=${actionName}");
	
	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
	//	alert("树节点ID:"+tree.contextID+",菜单ID:"+menuid);
		if(menuid=="muItem_DELETE"){
			//delRecord(tree.contextID);
			//tree.deleteItem(tree.contextID,true);
			
		}else if(menuid=="muItem_EDIT"){
			
		}else if(menuid=="muItem_ADD"){
			
		}else if(menuid=="muItem_REFRESH"){
			tree.smartRefreshBranch(tree.contextID,"${ctx}/app/buildingmon/objTreeData.act?nodeId="+tree.contextID+"&floor=${floor}&actionName=${actionName}");
		}
	}
	//树链接处理函数------------------------------------------------------////
	function openPath(itemId){
		curItemId = itemId;
		var url = tree.getUserData(itemId, "url");	
		if(url!=null&&url!=""){
			if ($("#display_type").val()=="html"){
				//刷新右侧页面信息
				parent.frames.ifrmOrgList.location.href = url;
				//parent.document.getElementById("ifrmObjList").location.href = url;;
				$(window.parent.parent.document.getElementById("sw_display")).hide();
				
			}else if($("#display_type").val()=="sw") {
				//资源itemId
				initMonSW(itemId)
			}
		}
	}	
	//树事件处理函数------------------------------------------------------////
	function HandleTreeClk(sId,sPreId){
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
		//alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);
	}

	function delRecord(id){
		$.ajax({
		   type: "POST",
		   url: "${ctx}/app/per/delete.act",
		   data: "_chk="+id,
		   success: function(msg){
		     alert("删除成功！");
		   }
		}); 	
    }
	// myDragHandler实现树结点拖动时重新指定父子关系。
	function myDragHandler(idFrom,idTo){
	 //alert(idFrom+"  ->  "+idTo);
	 return true;
	}
	/*
	*@description 初始化监控三维
	*@author herb 
	*@nodeId 资源id
	*/
	function initMonSW(nodeId) {
		var monType = parent.getMonType();
		$(window.parent.parent.document.getElementById("sw_display")).show();
		alert("初始化三纬页面:监控项"+monType+"||nodeId:"+nodeId);
	}
	
	
	/*
	*@description 切换展示形式
	*@author herb 
	*@nodeId 资源id
	*/
	function changeDisplay() {
		openPath(curItemId);
	}
	
</script>
</body>
</html>
	