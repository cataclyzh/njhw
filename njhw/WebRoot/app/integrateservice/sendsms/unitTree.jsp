<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<html>
<head>
	<title>单位通讯录</title>	
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
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:auto;background: #f1f8fd;'>

<form  id="inputForm" action=""  method="post"  autocomplete="off" >
 <input type="hidden" name="checkedIds" value="" id="checkedIds"/>
 <table align="center" border="0" width="100%" class="form_table">
      <tr>
        <td>
           <div id="divTree" style="border:0px;overflow:hidden;"></div>
        </td> 
      </tr>
      <tr class="form_bottom">
        <td colspan="6">
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">确定</a>            
        </td>
      </tr>
</table>
</form>

<script type="text/javascript">
	var menu;
	//初始化menu---------------------------------------------------------////
	menu=new dhtmlXMenuObject();
	menu.setImagePath("/app_njhw/scripts/widgets/dtree/images/DhtxMenu/");
	menu.setIconsPath("/app_njhw/scripts/widgets/dtree/images/MenuIcon/");
	menu.renderAsContextMenu();
	menu.attachEvent("onClick",HandleMClk);
	menu.loadXML("/app_njhw/scripts/widgets/dtree/data/dhtmlxmenu.xml"); 
	var tree;
	//初始化tree----------------------------------------------------------////
	tree=new dhtmlXTreeObject("divTree","100%","100%",0);
	tree.setImagePath("/app_njhw/scripts/widgets/dtree/images/DhtxTree/default/");
	tree.enableHighlighting(true);
	
	//绑定时间处理函数,注意一定要放在loadXML之前否则右键处理函数在ie下失效
//	tree.enableContextMenu(menu);
	tree.attachEvent("onClick",HandleTreeClk);
	tree.setOnClickHandler(function(id){openPath(id);});
	tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(true);
//tree.setXMLAutoLoading("/app_njhw/app/room/objTreeSelectData.act?type=open");
	tree.loadXML("/app_njhw/app/sendsms/getUnitTreeData.act?ids=${ids}&type=init");

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
		var ids="", names="", mobiles="", chooseText="", info ="", infos="";
		if (tree.getAllChecked() != ""){
			var idsArray = tree.getAllChecked().split(",");
			for ( var i = 0; i < idsArray.length; i++) {
				if (idsArray[i].indexOf("_u")>0) {	//人员
					var idArray = idsArray[i].split("_");
					ids += idArray[0]+",";
					names += idArray[1]+",";
					mobiles +=  idArray[2]+",";
					info = idArray[0]+"_"+idArray[1]+"_"+idArray[2]+",";
					infos += info;
					chooseText += "<div style='float: left; margin-right: 10px;'>"+idArray[1]+"<img src='${ctx}/app/integrateservice/images/close.png' title='删除联系人' onclick='del(this, &quot;unitTR&quot;, &quot;unitAlreadyChoose&quot;, &quot;"+info+"&quot;, &quot;unitReceiverInfo&quot;)'/></div>";
				}
			}
		}
		//alert("ids:"+ids);
		//alert("names:"+names);
		//alert("mobiles:"+mobiles);
		
		if (chooseText != "")  parent.$('#unitTR').show();
		else parent.$('#unitTR').hide();
		
		parent.$('#unitAlreadyChoose').children().remove();
		parent.$('#unitAlreadyChoose').append(chooseText);
		
		//parent.$('#receiverid').val(ids);
		//parent.$('#receiver').val(names);
		//parent.$('#receivermobile').val(mobiles);
		parent.$('#unitReceiverInfo').val(infos);
		
		parent.$("#companyWin").window("close");
	}
</script>
</body>
</html>
	