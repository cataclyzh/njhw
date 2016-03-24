<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 组织机构管理组织机构树页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/include/header-meta.jsp" %>
<title>组织机构树</title>	
<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/styles/easyui.css" rel="stylesheet" type="text/css"/>

<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
<style type="text/css">
li {list-style-type:none;}
</style>
</head>
<body leftmargin="0" topmargin="0" STYLE="OVERFLOW:AUTO;background: #fff;">
<div style="height:5%;background: #FFFFFF;">
<table width="100%"><tr>
<td align="center"><span style="cursor:hand" id="neworg" title="新增机构" href="javascript:void(0);" onclick="addOrg();"><img src="${ctx}/styles/icons/neworg.gif"/></span></td>
<td align="center"><span style="cursor:hand" id="addUser" title="新增人员" href="javascript:void(0);" onclick="addUser();"><img src="${ctx}/styles/icons/newuser.gif"/></span></td>
<td align="center"><span style="cursor:hand" id="delRecord" title="删除机构或人员" href="javascript:void(0);" onclick="delRecord();"><img src="${ctx}/styles/icons/del.gif"/></span></td>
<td align="center"><span style="cursor:hand" id="up" title="上移" href="javascript:void(0);" onclick="up();"><img src="${ctx}/styles/icons/up.gif"/></span></td>
<td align="center"><span style="cursor:hand" id="down" title="下移" href="javascript:void(0);" onclick="down();"><img src="${ctx}/styles/icons/down.gif"/></span></td>
<td align="center"><span style="cursor:hand" id="reload" title="刷新" href="javascript:void(0);" onclick="reload();"><img src="${ctx}/styles/icons/reload.png"/></span></td>
<td align="center"><span style="cursor:hand" id="search" title="搜索" href="javascript:void(0);" onclick="search();"><img src="${ctx}/styles/icons/search.png"/></span></td>
 
</tr>
<tr style="display: none; height: 20px; float: left;" id="soso" >

<td colspan="7"><input type="text" name="displayName" id="displayName" size="35" style="width: 120px;height: 18px;"  />&nbsp;&nbsp;
<img src="${ctx}/styles/icons/serach.gif" onclick="search1()" style="cursor:pointer;"/>&nbsp;&nbsp;
<img src="${ctx}/styles/icons/no.png" style="width: 15px; height: 15px;cursor:pointer;" onclick="search2()"/>

</td>

</tr>

</table>
<div id="searchUserOrOrg" style="border:0px;overflow:hidden;float: left; display: none;" />
<ul id="searchUserOrOrg1">

</ul>
</div>
</div>
<div id="divTree" style="height:95%;border:0px;overflow:hidden;"></div>


<script type="text/javascript"><!--
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
	tree.setXMLAutoLoading("${ctx}/app/per/orgOtherUserCheckinData1.act?type=open");
	tree.loadXML("${ctx}/app/per/orgOtherUserCheckinData1.act?type=init&id=<c:out value='${_orgid}'/>");//
	//var selectedNode = tree.getSelectedItemId();
	//selectedNode = selectedNode.substring(0,selectedNode.indexOf("-o"));
	$('#divTree').ready(function(){
		if ("${selectedNode}" != '') {
			var iId = setInterval(function(){ initTree(); }, 500);
			function initTree() {
				if (tree.getIndexById('${selectedNode}') != null) {
					tree.selectItem('${selectedNode}');
					openPath('${selectedNode}');
					clearInterval(iId);
				}
			};
		}
	});
	
	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
	//	alert("树节点ID:"+tree.contextID+",菜单ID:"+menuid);
		if(menuid=="muItem_DELETE"){			
			
		}else if(menuid=="muItem_EDIT"){
			
		}else if(menuid=="muItem_ADD"){
			
		}else if(menuid=="muItem_REFRESH"){
			
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
	
	function addOrg(){
		var selectedNode = tree.getSelectedItemId();
		
		if (selectedNode=="") {
			easyAlert('提示信息','请先选择上级机构再新增机构！','warning');
			return;
		}else{
			
			if (selectedNode.indexOf("-o")<0) {
				easyAlert('提示信息','不允许此操作！','warning');
				return;
			}
		}
	    selectedNode = selectedNode.substring(0,selectedNode.indexOf("-o"));
		var url = "${ctx}/app/per/orgInput.act?PId=" + selectedNode;
		openEasyWin("orgInput","新增组织机构",url,"900","350");
		
	}
	
	function addUser(){
		var selectedNode = tree.getSelectedItemId();
		if (selectedNode=="") {
			easyAlert('提示信息','请先选择上级机构再新增人员！','warning');
			return;
		}else{
			if (selectedNode.indexOf("-o")<0) {
				easyAlert('提示信息','不允许此操作！','warning');
				return;
			}
		}
	    selectedNode = selectedNode.substring(0,selectedNode.indexOf("-o"));
		var url = "${ctx}/app/per/inputRegisterOther.act?ispopup=n&orgId=" + selectedNode;
		parent.frames.ifrmOrgList.location.href = url;
		// openEasyWin("winId","人员登记",url,"900","500",true);
	}
	
	function reload(){
		var selectedNode = tree.getSelectedItemId();
		if (selectedNode==""){
			 location.reload();
			 parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act";
		}else{
			//parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act";
			parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act?selectedNode=" + selectedNode;
		}
	}
	
	function delRecord(){
		var selectedNodeOld = tree.getSelectedItemId();
		if (selectedNodeOld=="") {
			easyAlert('提示信息','请先选择后再操作！','warning');
			return;
		}	
		//openAdminConfirmWin(delRecordFn);
		delRecordFn();
    }
    
    function delRecordFn() {
    	var selectedNodeOld = tree.getSelectedItemId();
		var selectedNode = "";
		var surl;
		if (selectedNodeOld.indexOf("-o")>0) {
	    	selectedNode = selectedNodeOld.substring(0,selectedNodeOld.indexOf("-o"));
	    	surl = "${ctx}/app/per/deleteOneOrg.act";
	    	easyConfirm('提示', '确定删除？', function(r){
				if (r){
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNode},
						   dataType: 'json',
						   async : false,
						   success: function(msg){
							   if(msg.message=="1"){
								   alert("请先删除机构的关联信息！");
								   }else if(msg.message=="0"){
										alert("删除机构成功！");
								   		tree.deleteItem(selectedNodeOld,true);	
								   }else{
									   	alert("删除机构失败！");
								   }
						    
						   }
						}); 
						
				}
			});		
	    }else{
	    	selectedNode = selectedNodeOld.substring(0,selectedNodeOld.indexOf("-u"));
	    	surl = "${ctx}/app/per/userDelete.act";
	    	easyConfirm('提示', '确定删除？', function(r){
				if (r){
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNode},
						   dataType: 'json',
						   async : false,
						   success: function(msg){
							   if(msg.message=="0"){
							   		alert("删除用户成功！");
							   		tree.deleteItem(selectedNodeOld,true);	
							   }else if (msg.message=="1") {
								   alert("用户是单位管理员，无法删除！");
							   }else {
							       alert("删除用户失败！");
							   }
						    
						   }
						}); 
						
				}
			});		
	    }
		
    }
   
   var isDra = true;
	// myDragHandler实现树结点拖动时重新指定父子关系。
	function myDragHandler(idFrom,idTo){
	    if(isDra)
	    {
		     //	alert(idFrom+"  ->  "+idTo);
			if(idFrom == null || idFrom == ""){
				alert("请选择机构！");
				return false;
			}
			
			
			if(idTo.indexOf("-u")>0){
				alert("请选择机构！");
				return false;
			}
			
			parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act?selectedNode=" + idFrom;
			selectFrom = idFrom.substring(0,idFrom.indexOf("-o"));//托拽机构
			selectTo = idTo.substring(0,idTo.indexOf("-o"));      //托拽机构
			selectPerToOrg = idFrom.substring(0,idFrom.indexOf("-u"));//托拽人到机构  被托的ID
			$.ajax({
				url : '${ctx}/app/per/treeDrag.act?selectFrom=' + selectFrom +'&selectTo='+ selectTo +'&selectPerToOrg=' + selectPerToOrg ,
				secureuri : false,
				type: "POST",
				fileElementId : 'idFrom',
				dataType : 'json',//返回值类型 一般设置为json
			
				success : function(json) {
				
				}
			});
			return true;
	    }
	    else
	    {
	    	return false;
	    }
		
	}

	function up(){
		var selectedNodeOld = tree.getSelectedItemId();
		var selectedNode = "";
		var surl;
		if (selectedNodeOld=="") {
			easyAlert('提示信息','请先选择后再操作！','warning');
			return;
		}		
		
		if (selectedNodeOld.indexOf("-o")>0) {
	    	selectedNode = selectedNodeOld.substring(0,selectedNodeOld.indexOf("-o"));
	    	surl = "${ctx}/app/per/upOrg.act";
	   			
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNode},
						   dataType: 'json',
						   async : false,
						   success: function(msg){

							   if(msg.message=="0"){
							   		isDra = false;
							   		tree.moveItem(selectedNodeOld,"up");
							   		parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act?selectedNode=" + selectedNodeOld;						
							   		
							   }
							   
							   // add by zhangqw 2013年7月2日14:28:14
							   if("2"==msg.message)
							   {
							   		alert("已是机构顶部,不能上移!");
							   		return false;
							   }
							   if("1"==msg.message)
							   {
							   		alert("移动失败!");
							   		return false;
							   }
							
						    
						   }
						}); 
			
	    }else{
		    
	    	selectedNode = selectedNodeOld.substring(0,selectedNodeOld.indexOf("-u"));
	    	
	    	surl = "${ctx}/app/per/upUsers.act";
	    					
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNode},
						   dataType: 'json',
						   async : false,
						   success: function(msg){
							   if(msg.message=="0"){
								   //alert(msg.message);
								   isDra = false;
								   tree.moveItem(selectedNodeOld,"up");
								   parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act?selectedNode=" + selectedNodeOld;
								}
								// add by zhangqw 2013年7月2日14:28:14
							   if("2"==msg.message)
							   {
							   		alert("已是部门顶部,不能上移!");
							   		return false;
							   }
							    if("1"==msg.message)
							   {
							   		alert("移动失败!");
							   		return false;
							   }
						    
						   }
						}); 
						           
				
		    }
		
		
    }
	function down(){
		var selectedNodeOld = tree.getSelectedItemId();
		var selectedNode = "";
		var surl;
		if (selectedNodeOld=="") {
			easyAlert('提示信息','请先选择后再操作！','warning');
			return;
		}		
		if (selectedNodeOld.indexOf("-o")>0) {
	    	selectedNode = selectedNodeOld.substring(0,selectedNodeOld.indexOf("-o"));
	    	surl = "${ctx}/app/per/downOrg.act";
	  
						$.ajax({
						   type: "POST",
						   url: surl,
						   data: {_chk:selectedNode},
						   dataType: 'json',
						   async : false,
						   success: function(msg){
							   if(msg.message=="0"){
							  	   isDra = false;
								   tree.moveItem(selectedNodeOld,"down");
								  parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act?selectedNode=" + selectedNodeOld;
							   }
							   // add by zhangqw 2013年7月2日14:28:14
							   if("2"==msg.message)
							   {
							   		alert("已是机构底部,不能下移!");
							   		return false;
							   }
							   if("1"==msg.message)
							   {
							   		alert("移动失败!");
							   		return false;
							   }
							 
						    
						   }
						}); 
	           }else{
	   		    
	   	    	selectedNode = selectedNodeOld.substring(0,selectedNodeOld.indexOf("-u"));
	   	    	
	   	    	surl = "${ctx}/app/per/downUsers.act";
	   	    					
	   						$.ajax({
	   						   type: "POST",
	   						   url: surl,
	   						   data: {_chk:selectedNode},
	   						   dataType: 'json',
	   						   async : false,
	   						   success: function(msg){
	   							   if(msg.message=="0"){
	   							        isDra = false;
	   							   		tree.moveItem(selectedNodeOld,"down");
	   							        parent.frames.ifrmObjTree.location.href = "${ctx}/app/per/orgTreeUserCheckinOther1.act?selectedNode=" + selectedNodeOld;
										
	   								}
	   								// add by zhangqw 2013年7月2日14:28:14
	   							   if("2"==msg.message)
								   {
								   		alert("已是部门底部,不能下移!");
								   		return false;
								   }
								   if("1"==msg.message)
								   {
								   		alert("移动失败!");
								   		return false;
								   }
	   						    
	   						   }
	   						});            
					
	   		    }
	    
		
		
    }
    function search(){
  //  if( $("#soso").css("display")=="none"){
       $("#displayName").val("");
    	$("#soso").css("display","block");
   // }
  //  else if( $("#soso").css("display")=="block"){
    	// $("#displayName").val("");
    	//$("#soso").css("display","none");
       // }

        }
   function search2(){
	   $("#displayName").val("");
	   $("#soso").css("display","none");
	   }
    function search1(){
        var displayName = $("#displayName").val();
        if(displayName==""){
            alert("请输入搜索内容");
         return false; 
            }
        var content ="";
     
    	$.ajax({
			   type: "POST",
			   url:"${ctx}/app/per/findUsers.act" ,
			   data: {displayName:displayName},
			   dataType: 'json',
			   async : false,
			   success: function(json){
				
               if(null!=json){
            	   $("#divTree").hide();
            	   $("#searchUserOrOrg1").empty();
            	   $("#searchUserOrOrg").show();
            	    var sname = tree.getSelectedItemText() 
            	 
            	 
            		$.each(json,function(i,item){

               
                       content+='<li><img border="0" align="absmiddle" style="padding: 0px; margin: 0px; width: 18px; height: 18px;" src="${ctx}/scripts/widgets/dtree/images/DhtxTree/default/user.gif">'
                           +'<a href="javaScript:findPlace('+item.USERID+')" class="standartTreeRow" style="padding-left: 5px; padding-right: 5px;text-decoration: none;" id="'+item.USERID+'" orgId="'+item.ORG_ID+'">'+item.DISPLAY_NAME+'</a></li>' ;
            		});

            		$("#searchUserOrOrg1").append(content);
			   }
			   }
			}); 

            }
    
    function findPlace(id){
    	 var itemId = id+'-u';
    	var orgId= $("#"+id).attr("orgId");
    
    	$("#searchUserOrOrg").hide();
  	   $("#divTree").show();
        var url = "${ctx}/app/per/inputRegisterOther.act?ispopup=n&orgId="+orgId+"&userId="+id;
        		
		parent.frames.ifrmOrgList.location.href = url;
	
    
        }
--></script>
</body>
</html>