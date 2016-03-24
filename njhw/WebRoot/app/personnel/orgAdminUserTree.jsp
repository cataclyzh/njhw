<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: WXJ
- Date: 2010-09-02 13:26:22
- Description: 管理机构及人员树页面
--%>

<style>
.inpu_trst_a{
	margin:0 atuo;
	width: 68px;
	height: 22px;
	background:#8090b2;
	color:#fff;
	line-height:22px;
	display:block;
	text-align:center;
	font-family:"微软雅黑";
	text-decoration:none;
}
</style>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<%@ include file="/common/include/header-meta.jsp" %>
<title>机构及人员树</title>	
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
<link href="${ctx}/app/integrateservice/css/wizard_css.css"	rel="stylesheet" type="text/css" />
<link href="${ctx}/common/pages/misc/css/admin_css.css"
			rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function(){
		changebasiccomponentstyle();
	});		
</script>
</head>
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:auto;background: #fff;padding:0 10px;'>

<form  id="inputForm" action="addOrgAdminRoot.act"  method="post"  onkeydown= "if(event.keycode==13)return false;" >
 <s:hidden name="orgId"/>
 <s:hidden name="roomId"/>
 <s:hidden name="dtype"/>
 <s:hidden name="checkedIds"/>
 <input type="hidden" name="nodeId" value="${param.nodeId }"/>
 <div class="bgsgl_right_list_border">
	<div class="bgsgl_right_list_left">权限分配</div>
 </div>
 <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td colspan="3" style="padding-top:5px;">
           <div id="divTree" style="border:0px;height:310px;width:480px;background:rgb(246, 245, 241);"></div>
        </td> 
      </tr>
 	  <tr class="form_bottom" width="90%">
        <td width="40%">
        <div style="margin-top:5px;border-top:solid 1px #99a7be;padding-top:5px;text-align: center;">
        	<a style="float:right" href="javascript:void(0);" class="fkdj_botton_reset" iconCls="icon-save" onclick="saveData();">保存</a>            
        </div>
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
	//tree.enableThreeStateCheckboxes(true);//级联选中
	tree.setXMLAutoLoading("${ctx}/app/per/orgAdminUserTreeData.act?type=open&nodeId=${param.nodeId}");
	tree.loadXML("${ctx}/app/per/orgAdminUserTreeData.act?id=<c:out value='${_orgid}'/>&type=init&nodeId=${param.nodeId}");
	//菜单事件处理函数----------------------------------------------------////
	function HandleMClk(menuid,zoom){
		if(menuid=="muItem_DELETE"){
			tree.deleteItem(tree.contextID,true);
		}else if(menuid=="muItem_EDIT"){
			
		}else if(menuid=="muItem_ADD"){
			
		}else if(menuid=="muItem_REFRESH"){
			tree.smartRefreshBranch(tree.contextID,"${ctx}/app/per/orgAdminUserTreeData.act?id="+tree.contextID);
		}
	}
	//树链接处理函数------------------------------------------------------////
	function openPath(itemId){
	// alert("itemId"+itemId);
		var url = tree.getUserData(itemId, "url");		
		if(url!=null&&url!=""){
			window.location.href = url;
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
		//openAdminConfirmWin(saveDataFn);
		saveDataFn();
	}
	
	function saveDataFn(){
		var frm = $('#inputForm');
		//$('#checkedIds').val(tree.getAllChecked());
		//alert(tree.getAllCheckedBranches());
		
		$('#checkedIds').val(tree.getAllCheckedBranches());
		
		//alert($('#orgId').val());
		//alert($('#checkedIds').val());
		
		//return;
		//frm.submit();
		var options = {
		    dataType:'text',
		    success: function(responseText) {
		    	//alert(responseText);
		    	var location = parent.parent.document.getElementById("ifrmObjList");
				location.src = parent.$("#ifrmObjList").attr('src');
				parent.closeIframe();
			}
		};
		frm.ajaxSubmit(options);
		
		//alert(1);
		//var location = parent.parent.document.getElementById("ifrmObjList");
		//location.src = parent.$("#ifrmObjList").attr('src');
		//parent.closeIframe();
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
	parent.showDistributeRoom($("#roomId").val());
	parent.showDistributeLock($("#roomId").val());
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存资源失败！','error');
</c:if>
</script>