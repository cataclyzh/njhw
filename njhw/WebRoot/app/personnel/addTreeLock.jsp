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
<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/styles/property/wizard_css.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<style type="text/css">
.inpu_trst_a{
	width: 68px;
	height: 22px;
	background:#8090b2;
	color:#fff;
	line-height:22px;
	display:block;
	text-align:center;
	font-size:12px;
	font-family:"微软雅黑";
	text-decoration:none;
	margin:0 34%;
}
</style>
</head>
<body STYLE='OVERFLOW:HIDDEN;background:rgb(246, 245, 241)'>
<input type="hidden" id="nodeId" name="nodeId" value="" />
<input type="hidden" id="title" name="title" value="" />
<div id="divTree" style="float:left;border:0px;overflow-x:hidden;overflow-y:auto; height:250px; width:250px"></div>
<div class="clear"></div>
<div style="width:100%;margin:10px;float:left;">
	<a href="javascript:void(0);" class="inpu_trst_a" onclick="saveData();">选择</a>
</div>
<script type="text/javascript">
	var _ctx = '${ctx}';
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
	//tree.enableContextMenu(menu);
	tree.attachEvent("onClick",HandleTreeClk);	
	//tree.enableCheckBoxes(1);
	tree.setOnClickHandler(
		function(id){
		}
	);
	tree.setDragHandler();//设置树结点拖动get
	tree.enableDragAndDrop(false) //设置树结点是否可拖动
	tree.setDragHandler(myDragHandler); //设置树结点拖动时所执行的方法
	tree.setXMLAutoLoading("${ctx}/app/per/jzjgTreeData.act?type=open");
	tree.loadXML("${ctx}/app/per/jzjgTreeData.act?type=init&id=<c:out value='${_orgid}'/>");//
	
	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
		//alert("树节点ID:"+tree.contextID+",菜单ID:"+menuid);
		if(menuid=="muItem_DELETE"){
			//delRecord(tree.contextID);
			//tree.deleteItem(tree.contextID,true);
			
		}else if(menuid=="muItem_EDIT"){
			
		}else if(menuid=="muItem_ADD"){
			
		}else if(menuid=="muItem_REFRESH"){
			tree.smartRefreshBranch(tree.contextID,"${ctx}/app/per/objTreeData.act?nodeId="+tree.contextID);
		}
	}
	//树链接处理函数------------------------------------------------------////
	function openPath(itemId){	
		var url = tree.getUserData(itemId, "url");		
		if(url!=null&&url!=""){
			parent.frames.ifrmOrgList.location.href = url;
		}
	}	
	//树事件处理函数------------------------------------------------------////
	function HandleTreeClk(sId,sPreId){
		$('#nodeId').val(sId);
		//alert(parent.document.getElementById("iframeReLoad").contentWindow.document.getElementById("nodeId").value);
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
		//alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);
		$('#title').val(sTxt);
	}

	// myDragHandler实现树结点拖动时重新指定父子关系。
	function myDragHandler(idFrom,idTo){
	 //alert(idFrom+"  ->  "+idTo);
	 return true;
	}
	
	function save(){
		
	}
	
	function saveData(){
		var nodeId = $("#nodeId").val();
		var title = $("#title").val();
		var url = "${ctx}/app/per/queryExtTypeById.act?nodeId="+nodeId;
		
		$.ajax({
			type:'POST',
			url:url,
			dateType:'json',
			async:false,
			success:function(json){
				if(json.extType != "R"){
					easyAlert('提示信息','请选择具体房间！','error');
					return;
				}else{
					if(nodeId == ""){
						easyAlert('提示信息','请选择具体房间！','error');
						return;
					}else{
						parent.document.getElementById("ifrmComWin").contentWindow.document.getElementById("titleName").value = title;
						parent.document.getElementById("ifrmComWin").contentWindow.document.getElementById("nodeId").value = nodeId;
						closeEasyWin("winIds");
					}
				}
			},
			error:function(json){
				easyAlert('提示信息','AJAX执行出错','error');
				return;
			}
		});
		
	}
</script>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存资源成功！','info',
		function(){
			parent.closeIframe();
			var pageNo = parent.getElementById("pageNo").value;
		   	parent.jumpPage(pageNo);
		}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
</script>