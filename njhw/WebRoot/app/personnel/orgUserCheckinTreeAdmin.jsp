<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header-meta.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 组织机构管理组织机构树页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>组织机构树</title>	
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>

<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>

<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
</head>
<div style="background: #FFFFFF;">
<table width="70%">
	<tr>
		<!-- td align="center">
			<span style="cursor:hand" id="neworg" title="新增机构" href="javascript:void(0);" onclick="addOrg1();">
				<img src="${ctx}/styles/icons/neworg.gif"/>
			</span>
		</td>
		<td align="center">
			<span style="cursor:hand" id="delRecord" title="删除机构" href="javascript:void(0);" onclick="delRecord();">
				<img src="${ctx}/styles/icons/del.gif"/>
			</span>
		</td> 
		<td align="center">
			<span style="cursor:hand" id="up" title="上移" href="javascript:void(0);" onclick="up();">
				<img src="${ctx}/styles/icons/up.gif"/>
			</span>
		</td>
		<td align="center">
			<span style="cursor:hand" id="down" title="下移" href="javascript:void(0);" onclick="down();">
				<img src="${ctx}/styles/icons/down.gif"/>
			</span>
		</td>
		<td align="center">
			<span style="cursor:hand" id="addUser" title="新增单位管理员" href="javascript:void(0);" onclick="addUser();">
				<img src="${ctx}/styles/icons/newuser.gif"/>
			</span>
		</td>-->
	</tr>
	<tr></tr>
</table>
</div>
<div id="divTree" style="border:0px;overflow-y:hidden;height:488px;"></div>
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
	tree.setOnClickHandler(function(id){
		if(!this.hasChildren(id)){
			var url = tree.getUserData(id, "url");		
			if(url!=null&&url!=""){
				parent.frames.ifrmOrgList.location.href = url;
			}
		}else{
			var url = tree.getUserData(id, "url");		
			if(url!=null&&url!=""){
				parent.frames.ifrmOrgList.location.href = url;
			}
		}
		
		
	});
	//tree.setDragHandler();//设置树结点拖动
	//tree.enableDragAndDrop(false) //设置树结点是否可拖动
	//tree.setDragHandler(myDragHandler); //设置树结点拖动时所执行的方法
	tree.setXMLAutoLoading("${ctx}/app/per/orgTreeDataAdmin.act?type=open");
	tree.loadXML("${ctx}/app/per/orgTreeDataAdmin.act?type=init&id=<c:out value='${_orgid}'/>");//
	
	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
		//alert("树节点ID:"+tree.contextID+",菜单ID:"+menuid);
		if(menuid=="muItem_DELETE"){
			//delRecord(tree.contextID);
			//tree.deleteItem(tree.contextID,true);
			
		}else if(menuid=="muItem_EDIT"){
			
		}else if(menuid=="muItem_ADD"){
			
		}else if(menuid=="muItem_REFRESH"){
			tree.smartRefreshBranch(tree.contextID,"${ctx}/app/per/orgTreeData.act?orgId="+tree.contextID);
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
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
		//alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);
	}

	function delRecord(id){
		$.ajax({
		   type: "POST",
		   url: "${ctx}/app/per/orgDelete.act",
		   data: "_chk="+id,
		   success: function(msg){
		     alert("删除成功！");
		   }
		}); 	
    }
	// myDragHandler实现树结点拖动时重新指定父子关系。
	function myDragHandler(idFrom,idTo){
	// alert(idFrom+"  ->  "+idTo);
	
	 return true;
	}
	// 新增组织机构--------------------gxh-----------
	function addOrg1(){
		
		var selectedNode = tree.getSelectedItemId();
		
		if (selectedNode=="") {
			easyAlert('提示信息','请先选择上级机构再新增机构！','warning');
			return;
		}else{
			$.ajax({
				type: "POST",
			   url: "${ctx}/app/per/isOrNoaddOrg.act",
			   data: {orgId:selectedNode},
			   dataType: 'json',
			   async : false,
			   success: function(msg){
				   if(msg.message=="1"){
					   easyAlert('提示信息','请先选择上级机构再新增机构！','warning');
					   }else if(msg.message=="0"){
						   var url = "${ctx}/app/per/orgInput.act?PId=" + selectedNode;
						  openEasyWin("orgInput","新增组织机构",url,"900","500",true);
					   }   
			   }
			}); 
	
		}   
	}
	function delRecord(){
		var selectedNodeOld = tree.getSelectedItemId();

		var surl;
		if (selectedNodeOld=="") {
			easyAlert('提示信息','请先选择后再操作！','warning');
			return;
		}		  
	    	surl = "${ctx}/app/per/deleteOneOrg.act";
	    	easyConfirm('提示', '确定删除？', function(r){
				if (r){
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNodeOld},
						   dataType: 'json',
						   async : false,
						   success: function(msg){
							   if(msg.message=="1"){
								   alert("请先删除机构的关联信息！");
								   }else if(msg.message=="0"){
								   		alert("删除机构成功！");
								   }else{
									   	alert("删除机构失败！");
									}
						    
						   }
						}); 
						tree.deleteItem(selectedNodeOld,true);	
				}
			});		
	    
		
		
    }
    
	function addUser(){
		var selectedNode = tree.getSelectedItemId();
		if (selectedNode=="") {
			easyAlert('提示信息','请先选择单位再新增人员！','warning');
			return;
		}else{
			if (selectedNode.indexOf("-o")<0) {
				easyAlert('提示信息','请先选择单位再新增人员！','warning');
				return;
			}
		}
	    selectedNode = selectedNode.substring(0,selectedNode.indexOf("-o"));
		var url = "${ctx}/app/per/inputRegister.act?ispopup=n&orgId=" + selectedNode;
		parent.frames.ifrmOrgList.location.href = url;
		// openEasyWin("winId","人员登记",url,"900","500",true);
	}
	
	function up(){
		
		var selectedNode = tree.getSelectedItemId();
		
		var surl;
		if (selectedNode=="") {
			easyAlert('提示信息','请先选择后再操作！','warning');
			return;
		}		
	    	
	    	surl = "${ctx}/app/per/upOrg.act";
	   			
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNode},
						   dataType: 'json',
						   async : false,
						   success: function(msg){

							   if(msg.message=="0"){
								   tree.moveItem(selectedNode,"up");
							   }
							
						    
						   }
						}); 

		
    }
	function down(){
		var selectedNode = tree.getSelectedItemId();
		
		var surl;
		if (selectedNode=="") {
			easyAlert('提示信息','请先选择后再操作！','warning');
			return;
		}		
			
	    	surl = "${ctx}/app/per/downOrg.act";
	  
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNode},
						   dataType: 'json',
						   async : false,
						   success: function(msg){
							   if(msg.message=="0"){
								   tree.moveItem(selectedNode,"down");
							   }
							 
						    
						   }
						}); 
		
    }

	function reload(){
		var selectedNode = tree.getSelectedItemId();
		if (selectedNode==""){
			 location.reload();
		}else{
			if(selectedNode=="2")
			tree.refreshItem(selectedNode);
		}
	}

  // function search(){

	   //  var itemId= "40";//获取选中的节点Id	   
	 //   var name=tree._globalIdStorageFind(itemId) ;//根据id获取节点
	 //   tree.setItemStyle(itemId,"text-decoration:underline; background-color : navy; color:white; font-family : Verdana, Geneva, Arial, Helvetica, sans-serif; 		font-size : 12px;  -moz-user-select: none; ");
	     
		//  }
</script>
</body>
</html>
	