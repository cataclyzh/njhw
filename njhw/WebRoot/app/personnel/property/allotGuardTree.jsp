<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html>
<head>
	<title>资源对象树</title>	
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<script type="text/javascript">
		var _ctx = '${ctx}';
	</script>


	<link href="/app_njhw/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
	<link href="/app_njhw/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
	<link href="/app_njhw/scripts/widgets/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script src="/app_njhw/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="/app_njhw/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="/app_njhw/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
	<script src="/app_njhw/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
	<script src="/app_njhw/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
	<script src="/app_njhw/scripts/basic/jquery.js.gzip" type="text/javascript"></script>	
	<script src="/app_njhw/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			changebasiccomponentstyle();   
		});		
	</script>
</head>
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:auto;background: #fff;'>
 <input type="hidden" name="checkedIds" value="" id="checkedIds"/>
 <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td>
           <div id="divTree" style="border:0px;overflow:hidden;"></div>
        </td> 
      </tr>
      <tr class="form_bottom">
        <td colspan="6">
        	<a class="buttonFinish" href="javascript:void(0);" style="float:left;" onclick="saveData();">确&nbsp;&nbsp;定</a>   
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
	tree.setOnClickHandler(function(id){openPath(id);});
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(true);
//tree.setXMLAutoLoading("/app_njhw/app/room/objTreeSelectData.act?type=open");
	tree.loadXML("${ctx}/app/per/allotGuardTreeData.act?ids=${ids}");

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
 	
	function saveData(){
		var ids="", names="";
		if (tree.getAllChecked() != ""){
			var idsArray = tree.getAllChecked().split(",");
			for ( var i = 0; i < idsArray.length; i++) {
				if (idsArray[i] != "guard") {	//楼层
					ids += idsArray[i]+",";
					names += tree.getItemText(idsArray[i])+",";
				}
			}
			
		}
		
		var isAll = true;
		
		var itemArry =tree.getAllChildless().split(',');
        for(var i=0;i<itemArry.length;i++)
        {  
			if (tree.isItemChecked(itemArry[i]) == 0) {	//楼层
				isAll = false;
				break;
			}
		}
		
		ids = ids.substring(0, ids.length-1);
		names = names.substring(0, names.length-1);
		top.aiFrame.$('#guardIds').val(ids);
		if (${opt} == '2' && ids == '' && top.aiFrame.$('#access').val() == '') {
			top.aiFrame.$('#opt').val("3");
			top.aiFrame.$('#optName').val("删除");
		}
		if (${opt} == '3' && ids != '') {
			top.aiFrame.$('#opt').val("2");
			top.aiFrame.$('#optName').val("更新");
		}
		if (isAll) {
			top.aiFrame.$('#guardIds').val("all");
			top.aiFrame.$('#guard').val("全部");
		} else {
			top.aiFrame.$('#guard').val(names);
		}
		parent.$("#zjxz").window("close");
	}
</script>
</body>
</html>
	