<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: SQS
- Date: 2013-05-05 13:22:22
- Description: 管理机构及人员树页面
--%>

<style type="text/css">
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

 li {list-style-type:none;}
 </style>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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

<!--  <s:hidden name="orgId"/>
 <s:hidden name="roomId"/>
 <s:hidden name="dtype"/>
 <s:hidden name="checkedIds"/> -->

 <input type="hidden" id="orgId" name="orgId">
 <input type="hidden" id="orgName" name="orgName">
 <table align="center" border="0" width="100%" >
 	<!--    <tr class="form_bottom">
        <td >
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">确定</a>            
        </td>
      </tr> -->
      <tr>
      <td>
      <input type="text" name="displayName" id="displayName" size="35" style="width: 120px;height: 18px;"  />&nbsp;&nbsp;
      <span style="cursor:hand" id="search" title="搜索" href="javascript:void(0);" onclick="search1();"><img src="${ctx}/styles/icons/search.png"/></span></td>
      </tr>
       <tr>
        <td >
           <div id="divTree" style="border:0px;overflow:hidden;"></div>
        </td> 
      </tr> 
      <tr class="form_bottom" id="saveDatatr">
        <td >
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">确定</a>            
        </td>
      </tr> 
</table>
<div id="searchUserOrOrg" style="border:0px;overflow:hidden;float: left; display: none;" />
<ul id="searchUserOrOrg1">

</ul>
</div>

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
	tree.setOnDblClickHandler(function(){saveData();})
	//tree.enableCheckBoxes(1);
 	//tree.enableThreeStateCheckboxes(true);
	//enableRadioButtons(openPath(id),true);
	//setCheck(tree.getSelectedItemId(),false);//不选
	
   //tree.enableRadiobuttons(true);
	tree.setXMLAutoLoading("${ctx}/app/visitor/frontReg/selectRespondents.act?type=open&state=<c:out value='${state}'/>");
	tree.loadXML("${ctx}/app/visitor/frontReg/selectRespondents.act?type=init&id=<c:out value='${_orgid}'/>&state=<c:out value='${state}'/>");//

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
		
				var useridu = tree.getSelectedItemId();	//选中id节点
				if (useridu=="") {
					alert("请先选择后再操作");
					
					return;
				}		
		       if(useridu.indexOf("-o")>0){
		    	  alert("请选择人员！");
		    	
					return;
			       }

				var userid = "";
				var userName ="";
				
		        userName = tree.getSelectedItemText()		    
				userid = useridu.substring(0,useridu.indexOf("-u"));

				var tell = byUseridtel(userid);
				byUseridOrgIdesd(userid);
		    	var orgName=$("#orgName").val();
		    	var orgId=$("#orgId").val();
		    	parent.$("#orgName").val(orgName);
		    	parent.$("#orgId").val(orgId);
				parent.$('#outName').val(userName);			
				parent.$('#userid').val(userid);
				parent.$("#phonespan").val(tell);
				parent.$('#outName').focus();	
				parent.$('#orgName').focus();		
		 		parent.$("#companyWin").window("close");
		 		

	}




	/**
	 * 获取电话
	 */
		function byUseridtel(userid){
			
			var tell =""; 
		
			if(userid!=""){
			
		  $.ajax({
		             type:"POST",
		             url:"${ctx}/app/visitor/frontReg/phoneByUserid.act",
		             data:{userid:userid},
		             dataType: 'json',
				     async : false,
				     success: function(json){
		            	
			        	 if(json.tell != null&&json.tell != ""){	        		
			              tell = json.tell;
			       
			         }
			 }
		  });
			
			}
			return tell;
			
		}

	/**
	 * 获取部门
	 */
		  function byUseridOrgIdesd(userid){
               
				if(userid!=""){
	    	  $.ajax({
		             type:"POST",
		             url:"${ctx}/app/visitor/frontReg/byUseridOrgIdesd.act",
		             data:{userid:userid},
		             dataType: 'json',
				     async : false,
				     success: function(json){
		            	 $("#orgName").val(json.name);
		                $("#orgId").val(json.orgId);
				    	
			         }
		      });
			
				}
				
	        }

		 
			   
			    function search1(){
			        var displayName = $("#displayName").val();
			        if(displayName==""){
			            alert("请输入搜索内容");
			            var url = "${ctx}/app/visitor/frontReg/respondentsTreeSelect.act?state=all";
			            window.location.href=url;
			         return false; 
			            }
			        var content ="";
			     
			    	$.ajax({
						   type: "POST",
						   url:"${ctx}/app/visitor/frontReg/findUsers.act?id=${_orgid}&type=${state}",
						   data: {displayName:displayName},
						   dataType: 'json',
						   async : false,
						   success: function(json){
							
			               if(null!=json){
			            	  $("#saveDatatr").hide();
			            	   $("#divTree").hide();
			            	   $("#searchUserOrOrg1").empty();
			            	   $("#searchUserOrOrg").show();
			            	    var sname = tree.getSelectedItemText() 
			            	 
			            	 
			            		$.each(json,function(i,item){

			               
			                       content+='<li><img border="0" align="absmiddle" style="padding: 0px; margin: 0px; width: 18px; height: 18px;" src="${ctx}/scripts/widgets/dtree/images/DhtxTree/default/user.gif">'
			                           +'<a href="javaScript:findPlace('+item.USERID+')" id="outName1'+item.USERID+'" class="standartTreeRow" style="padding-left: 5px; padding-right: 5px;text-decoration: none;">'+item.DISPLAY_NAME+'</a>'
			                           +'&nbsp;&nbsp;<span id="orgName1'+item.USERID+'" class="standartTreeRow" style="padding-left: 5px; padding-right: 5px;text-decoration: none;">'+item.ORG_NAME+'</span><span id="orgId1'+item.USERID+'" style="display: none;">'+item.ORG_ID+'</span></li>';
			                          
			            		});

			            		$("#searchUserOrOrg1").append(content);
						   }
						   }
						}); 

			          }


			   function findPlace(uId){
				   var tell = byUseridtel(uId);//电话
                   var userName = $("#outName1"+uId).text();
                   var orgName = $("#orgName1"+uId).text();
                   var orgId = $("#orgId1"+uId).text();
				 
				    parent.$('#outName').val(userName);	
				    parent.$('#userid').val(uId);
				    parent.$("#orgName").val(orgName);
			    	parent.$("#orgId").val(orgId);
					parent.$("#phonespan").val(tell);
					parent.$('#outName').focus();	
				    parent.$('#orgName').focus();				
			 		parent.$("#companyWin").window("close");

				   }
</script>
</body>
</html>
	