<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: SQS
- Date: 2013-05-17 13:26:22
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
<meta http-equiv="X-UA-Compatible" content="IE=8" />
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
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:auto;background: #f1f8fd;'>

<form  id="inputForm" action="orgUserSelectMes.act"  method="post"  autocomplete="off" >
 <s:hidden name="orgId"/>
 <s:hidden name="roomId"/>
 <s:hidden name="dtype"/>
 <s:hidden name="checkedIds"/>
 <table align="center" border="0" width="100%" class="form_table">
 	  <tr class="form_bottom">
        <td >
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">确定</a>            
        </td>
      </tr>
      <tr>
        <td >
           <div id="divTree" style="border:0px;overflow:hidden;"></div>
        </td> 
      </tr>
      <tr class="form_bottom">
        <td >
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">确定</a>            
        </td>
      </tr>
</table>
</form>

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
	tree.setOnClickHandler(function(id){openPath(id);});
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(true);
	tree.setXMLAutoLoading("${ctx}/app/per/orgTreeUserListData.act?type=open");
	tree.loadXML("${ctx}/app/per/orgTreeUserListData.act?type=init&id=<c:out value='${_orgid}'/>");//

	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
		if(menuid=="muItem_DELETE"){
			tree.deleteItem(tree.contextID,true);
		}else if(menuid=="muItem_EDIT"){
			
		}else if(menuid=="muItem_ADD"){
			
		}else if(menuid=="muItem_REFRESH"){
			
		}
	}
	//树链接处理函数------------------------------------------------------////
	function openPath(itemId){		
		var url = tree.getUserData(itemId, "url");		
		if(url!=null&&url!=""){
			parent.frames.ifrmMenuSubList.location.href = url;
		}
		
	}	
	//树事件处理函数------------------------------------------------------////
	function HandleTreeClk(sId,sPreId){
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
		//alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);
	}
	
	function test(){
		alert(tree.getAllChecked());
	}
	function ajaxTest(){
  		$.post("/app/per/orgUserSelectSave.act", {"txt": "123" },function(data){
   			alert(data);
  		});
 	}
 	
	function saveData(){
		
		if (tree.getAllChecked() != ""){
			var idsArray = tree.getAllChecked().split(",");
			var treeUId="", treeUserName="";
			for ( var i = 0; i < idsArray.length; i++) {
				if(idsArray[i].indexOf("-o")!=-1){
			  		continue;
				}
	        	treeUId += idsArray[i].replace("-u", "")+",";
				treeUserName += tree.getItemText(idsArray[i]).replace(/(^\s*)|(\s*$)/g, "")+",";
			}
			parent.$('#messReceiverId').val(treeUId);
			parent.$('#messReceiverName').val(parent.$('#messReceiverName').val()+","+treeUserName);
  			parent.$("#orgTreeWin").window("close");
		} else {
			parent.$("#orgTreeWin").window("close");
		}
		
	}
</script>
</body>
</html>
	