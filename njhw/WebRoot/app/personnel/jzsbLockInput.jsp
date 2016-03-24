<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 资源管理资源树页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
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
</head>
<body leftmargin="0" topmargin="0" STYLE='OVERFLOW:HIDDEN;background:rgb(246, 245, 241)'>
    <div class="bgsgl_right_list_border">
		  <div class="bgsgl_right_list_left">设备维护</div>
	</div>
<div id="divTree" style="float:left;border:0px;overflow-x:hidden;overflow-y:auto; height:250px; width:250px"></div>
<form id="queryForm" action="jzsbLockInputSave.act" method="post"  autocomplete="off" >
	<input type="hidden" id="nodeId" name="nodeId" value="" />
	<div style="float:left">
		<table align="center" border="0" width="100%" class="form_table">
			<tr>
				<td class="form_label"><font style="color:red">*</font>门锁名称</td>
				<td><input type="text" id="name" name="name" value=""></input></td>
			</tr>
			<tr>
				<td class="form_label"><font style="color:red">*</font>通讯机</td>
				<td>
					<select style="width: 152px;" id="NjhwDoorComm">
						<script type="text/javascript">
							var url = "${ctx}/app/per/queryNjhwDoorCommJSON.act";	
							$.ajax({
								type:'POST',
								url:url,
								dateType:'json',
								async:false,
								success:function(json){
									if(json.length > 0) {
										$.each(json, function(i) {
											var options = "<option id='"+json[i].COMM_ID+"' value='"+json[i].ID+"'>"+json[i].MEMO+"</option>";
											document.write(options);
										});
									}
								},
								error:function(json){
									easyAlert('提示信息','下拉框AJAX 操作错误！','error');
								}
							});
						</script>
					</select>
				</td>
			</tr>
			<tr>
				<td class="form_label"><font style="color:red">*</font>门锁地址</td>
				<td><input type="text" id="adrsStore" name="adrsStore" value=""></input></td>
			</tr>
			<tr>
				<td class="form_label"><font style="color:red"></font>备注</td>
				<td><input type="text" id="title" name="title" value=""></input></td>
			</tr>
		</table>
		<table align="center" border="0" width="100%" class="form_table" >  
			<tr style="padding: 10px 0 5px 8px;text-align: center;background: #fffff;height: 50px;">
				<td colspan="6">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>
				</td>
			</tr>
		</table>
		<input type="hidden" id="selectValue" name="selectValue" value="" />
	</div>
</form>

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
						parent.frames.ifrmOrgList.location.href = url;
					}
				}
			}else{
				var url = tree.getUserData(id, "url");		
				if(url!=null&&url!=""){
					parent.frames.ifrmOrgList.location.href = url;
				}
			}
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
		$("#nodeId").val(sId);
		var sTxt=tree.getItemText(sId);
		var sTxtP=tree.getItemText(sPreId);
		//alert("你点击了节点:"+sTxt+",上次点击节点:"+sTxtP);
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
					}
					if($('#name').val() == ""){
						easyAlert('提示信息','请填写名称','error');
						return;
					}
					if($('#NjhwDoorComm').val() == ""){
						easyAlert('提示信息','请选择通讯机','error');
						return;
					}
					if($('#adrsStore').val() == ""){
						easyAlert('提示信息','请填写门锁地址','error');
						return;
					}
					if($('#title').val() == ""){
						easyAlert('提示信息','请填写备注','error');
						return;
					}
					$("#selectValue").val($("#NjhwDoorComm").val());
					var reg=new RegExp("[0-9]+");
					if(!reg.test($("#adrsStore").val())){
						alert("门锁请输入数字!");
						return;
					}
					
					if(!(parseInt($("#adrsStore").val())>=0 && parseInt($("#adrsStore").val()) <=255)){
						alert("门锁区间为0-255之间！");
						return ;
					}
					
					var urls = "${ctx}/app/per/queryLockCountJSON.act?adrsStore="+$('#adrsStore').val()+"&selectValue="+$('#NjhwDoorComm').val();
					$.ajax({
						type:'POST',
						url:urls,
						dateType:'json',
						async:false,
						success:function(json){
							if(json.flag == 'N'){
								easyAlert('提示信息','该通讯机下的门锁地址已被占用，请换个通讯机地址！','error');
								return;
							}else{
								$("#queryForm").submit();
							}
						},
						error:function(json){
							easyAlert('提示信息','AJAX执行出错','error');
							return;
						}
					});
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