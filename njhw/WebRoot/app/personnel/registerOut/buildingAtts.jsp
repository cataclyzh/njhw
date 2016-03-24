<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: zhangqw
- Date: 2014年4月3日13:13:55
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
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:auto;background: #fff;'>

<form  id="inputForm" action="saveBuildingAtts.act"  method="post"  onkeydown= "if(event.keycode==13)return false;" >
 <s:hidden name="orgId"/>
 <s:hidden name="roomId"/>
 <s:hidden name="dtype" value="approvers"/>
 <s:hidden name="checkedIds"/>
 <table align="center" border="0" width="100%" class="form_table">
 	  <tr class="form_bottom" width="90%">
        <td width="40%">
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>            
        </td>
        <td width="30%">
        	<input type="text" id="displayName" size="35" style="width:100%;inline-height:22px;" onkeypress="return searchBtn(event)"/>
      	</td>
      	<td width="20%">
        	<a href="javascript:void(0);" id="searchBtn" class="easyui-linkbutton" iconCls="icon-search" onclick="finduser();">搜索</a>
        </td>
      </tr>
      <tr>
        <td colspan="3">
           <div id="divTree" style="border:0px;height:310px;width:480px;"></div>
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
	tree.loadXML("${ctx}/app/per/buildingAttsData.act");

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
	// alert("itemId"+itemId);
		var url = tree.getUserData(itemId, "url");		
		if(url!=null&&url!=""){
			parent.frames.ifrmMenuSubList.location.href = url;
		}
		
	}	
	//树事件处理函数------------------------------------------------------////
	function HandleTreeClk(sId,sPreId){
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
		// alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);
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
		saveDataFn();
	}
	
	function saveDataFn(){
		var frm = $('#inputForm');
		//$('#checkedIds').val(tree.getAllChecked());
		//alert(tree.getAllCheckedBranches());
	
		$('#checkedIds').val(tree.getAllCheckedBranches());
		if(null == $("#checkedIds").val() || '' == $("#checkedIds").val()){
			easyAlert('提示信息','请选择大厦考勤人员！','success');
			return false;
		}
		frm.submit();
		
		var apps = $("#checkedIds").val().split(',');
		var appsName = '';
		for(var i = 0;i<apps.length;i++)
		{  
			if(apps[i].indexOf('-u') > 0)
			{	
				appsName += tree.getItemText(apps[i]);
				appsName += ',';
			}
		}
		
		$(window.top.document.getElementById("building_attendance")).val(appsName.substring(0,appsName.length-1));  
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
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','success');
	window.top.$("#buildingAttInput").window("close");
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
</script>