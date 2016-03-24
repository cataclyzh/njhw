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
	
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree3_5/dhtmlxcommon.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree3_5/dhtmlxtree.js" type="text/javascript"></script>	
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree3_5/dhtmlxmenu.js" type="text/javascript"></script>
	<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
	
</head>
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:HIDDEN;background:rgb(246, 245, 241)'>
<div id="divTree" style="border:0px;overflow:hidden;height: 440px;"></div>
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
//	tree.enableContextMenu(menu);
	tree.attachEvent("onClick",HandleTreeClk);
	tree.setOnClickHandler(
		function(id){
			//alert(tree.getSelectedItemId());
			if("${resType}"=="D"){
				if(!this.hasChildren(id)){
					var res = tree.getUserData(id, "res");		
					if(res!=null&&res!=""){
						//parent.frames.ifrmOrgList.location.href = url;
						updateRecord(id,res);
					}
				}else{
					var url = tree.getUserData(id, "url");		
					if(url!=null&&url!=""){
						parent.resetListRes(url);
					}
				}
			}else{
					var url = tree.getUserData(id, "url");		
					if(url!=null&&url!=""){
						parent.resetListRes(url);
					}
			}
		}
	);
	tree.setDragHandler();//设置树结点拖动get
	tree.enableDragAndDrop(false) //设置树结点是否可拖动
	tree.setDragHandler(myDragHandler); //设置树结点拖动时所执行的方法
	tree.setXMLAutoLoading("${ctx}/app/per/menuTreeDataDev.act?resType=${resType}&type=open");
	tree.loadXML("${ctx}/app/per/menuTreeDataDev.act?resType=${resType}&type=init&id=<c:out value='${_orgid}'/>");//
	tree.setOnLoadingEnd(function(){
		if(tree.getSelectedItemId())
		{
			tree.selectItem(tree.getSelectedItemId());
		}else
		{
			tree.selectItem('30');
		}
		tree.openItem('30');
	});
	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
	//	alert("树节点ID:"+tree.contextID+",菜单ID:"+menuid);
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
			parent.resetListRes(url);
		}
	}	
	//树事件处理函数------------------------------------------------------////
	function HandleTreeClk(sId,sPreId){
		//tree.selectItem(sId);
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
		//alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);
	}

	function delRecord(id){
		$.ajax({
		   type: "POST",
		   url: "${ctx}/app/per/objDelete.act",
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
	
	function updateRecord(id,res){
		var url = "${ctx}/app/per/objInput.act?nodeId="+id +"&res="+res;
		//showShelter1("800","450",url,"upd");
		showShelter("800","450",url);
	}
</script>
</body>
</html>
	